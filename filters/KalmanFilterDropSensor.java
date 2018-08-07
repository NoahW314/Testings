package filters;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

public class KalmanFilterDropSensor implements Filter<ArrayList<Double>> {
	
	private int sensorNum = 5;
	private int stateNum = 3;
	
	private boolean Vuforia = true;
	private boolean ultrasonic = true;
	
	private double lastUltra = 0;
	private double prevLastUltra = 0;
	
	/**Current State Matrix*/
	private SimpleMatrix[] X = new SimpleMatrix[3];
	/**Previous State Matrix*/
	private SimpleMatrix[] pX = new SimpleMatrix[3];
	/**Previous Prediction Error*/
	private SimpleMatrix[] pP = new SimpleMatrix[3];
	/**Current Prediction Error*/
	private SimpleMatrix[] P = new SimpleMatrix[3];
	/**Noisy Measurements*/
	private SimpleMatrix[] Z = new SimpleMatrix[3];
	/**Kalman Gain (variable to store value for use in two places)*/
	private SimpleMatrix[] G = new SimpleMatrix[3];
	/**State Transition Model*/
	private SimpleMatrix A = new SimpleMatrix(stateNum, stateNum);
	/**How much each control input contributes to each state*/
	private SimpleMatrix B = new SimpleMatrix(stateNum, 1);
	/**How much each sensor contributes to each state*/
	private SimpleMatrix C = new SimpleMatrix(sensorNum, stateNum);
	/**Control input*/
	private SimpleMatrix U = new SimpleMatrix(1, 1);
	/**Covariance of Process Noise*/
	private SimpleMatrix Q = new SimpleMatrix(stateNum, stateNum);
	/**Covariance of Sensor Noise (Diagonal contains sensor variance)*/
	private SimpleMatrix[] R = new SimpleMatrix[3];
	
	private SimpleMatrix fullC = new SimpleMatrix(sensorNum, stateNum);
	private SimpleMatrix[] fullR = new SimpleMatrix[3];
	
	public KalmanFilterDropSensor(double[][] a, double[][] c, double[][] q, double[] r, double[][] b) {
		for(int i = 0; i < 3; i++) {
			X[i] = new SimpleMatrix(stateNum, 1);
			pX[i] = new SimpleMatrix(stateNum, 1);
			pP[i] = new SimpleMatrix(fillDoubleArray(1, stateNum, stateNum));
			P[i] = new SimpleMatrix(stateNum, stateNum);
			Z[i] = new SimpleMatrix(sensorNum, 1);
			G[i] = new SimpleMatrix(stateNum, sensorNum);
			R[i] = SimpleMatrix.diag(r);
			fullR[i] = SimpleMatrix.diag(r);
		}
		A = new SimpleMatrix(a);
		B = new SimpleMatrix(b);
		C = new SimpleMatrix(c);
		fullC = new SimpleMatrix(c);
		Q = new SimpleMatrix(q);
	}
	
	private double[][] fillDoubleArray(int value, int rows, int columns) {
		double[][] a = new double[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				a[i][j] = value;
			}
		}
		return a;
	}

	public void dropVuforia() {
		sensorNum--;
		Vuforia = false;
		
		for(int i = 0; i < 3; i++) {
			if(ultrasonic) {
				R[i] = SimpleMatrix.diag(fullR[i].get(0,0), fullR[i].get(1,1), fullR[i].get(2,2), fullR[i].get(4,4));
			}
			else {
				R[i] = R[i].extractMatrix(0, 3, 0, 3);
			}
			Z[i] = new SimpleMatrix(sensorNum, 1);
		}
		if(ultrasonic) {
			for(int i = 0; i < 3; i++) {
				C.set(3, i, C.get(4, i));
			}
		}
		C = C.extractMatrix(0, sensorNum, 0, stateNum);
	}
	public void addVuforia() {
		if(!Vuforia) {
			sensorNum++;
			Vuforia = true;
			
			for(int i = 0; i < 3; i++) {
				R[i] = fullR[i].copy();
				Z[i] = new SimpleMatrix(sensorNum, 1);
			}
			C = fullC.copy();
			if(!ultrasonic) {
				sensorNum++;
				dropUltrasonic(-1);
			}
		}
	}
	
	public void dropUltrasonic(int measNum) {
		System.out.println(measNum);
		sensorNum--;
		ultrasonic = false;
		
		for(int i = 0; i < 3; i++) {
			R[i] = R[i].extractMatrix(0, sensorNum, 0, sensorNum);
			Z[i] = new SimpleMatrix(sensorNum, 1);
		}
		C = C.extractMatrix(0, sensorNum, 0, stateNum);
	}
	public void addUltrasonic() {
		if(!ultrasonic) {
			sensorNum++;
			ultrasonic = true;
			
			for(int i = 0; i < 3; i++) {
				R[i] = fullR[i].copy();
				Z[i] = new SimpleMatrix(sensorNum, 1);
			}
			C = fullC.copy();
			if(!Vuforia) {
				sensorNum++;
				dropVuforia();
			}
		}
	}

	private void predict(int i) {
		X[i] = (A.mult(pX[i])).plus(B.mult(U));
		P[i] = A.mult(pP[i]).mult(A.transpose()).plus(Q);
	}
	private void update(int i) {
		G[i] = P[i].mult(C.transpose()).mult((C.mult(P[i]).mult(C.transpose()).plus(R[i])).invert());
		X[i] = X[i].plus(G[i].mult(Z[i].minus(C.mult(X[i]))));
		P[i] = (SimpleMatrix.identity(3).minus(G[i].mult(C))).mult(P[i]);
		
		pP[i] = P[i];
	    pX[i] = X[i];
	}

	@Override
	public ArrayList<Double> run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		if(ultrasonic) {
			double lastJump = lastUltra-prevLastUltra;
			double currentJump = accelerations.get(measNum)[9]-lastUltra;
			if(Math.abs(currentJump-lastJump) >= 5000) {
				dropUltrasonic(-1);
			}
		}
		if(ultrasonic) {
			if(Math.abs(accelerations.get(measNum)[9]-X[txyz-1].get(2, 0)) >= 9600) {
				dropUltrasonic(measNum);
			}
		}
		
		Z[txyz-1].set(0, 0, accelerations.get(measNum)[txyz]);
		Z[txyz-1].set(1, 0, accelerations.get(measNum)[txyz+3]);
		Z[txyz-1].set(2, 0, accelerations.get(measNum)[7]);
		if(Vuforia) {
			Z[txyz-1].set(3, 0, accelerations.get(measNum)[8]);
		}
		if(ultrasonic) {
			Z[txyz-1].set(Z[txyz-1].numRows()-1, 0, accelerations.get(measNum)[9]);
		}
		
		
		double time = accelerations.get(measNum)[0];
		A.set(1, 0, time);
		A.set(2, 1, time);
		A.set(2, 0, 0.5*Math.pow(time, 2));
		
		U.set(0, 0, 0);
		
		if(measNum == 0) {
			lastUltra = accelerations.get(measNum)[9];
			pX[txyz-1] = new SimpleMatrix(new double[][] {{(Z[txyz-1].get(0, 0)+Z[txyz-1].get(1, 0))/2}, {0}, {0}});
		}
		
		predict(txyz-1);
		update(txyz-1);
		
		ArrayList<Double> values = new ArrayList<Double>();
		for(int i = 0; i < 3; i++) {
			values.add(X[txyz-1].get(i, 0));
		}
		
		prevLastUltra = lastUltra;
		lastUltra = accelerations.get(measNum)[9];
		
		return values;
	}

}
