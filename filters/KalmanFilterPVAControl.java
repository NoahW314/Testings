package filters;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

import filters.Filter;

public class KalmanFilterPVAControl implements Filter<ArrayList<Double>> {
	
	public int sensorNum = 3;
	public int stateNum = 3;
	
	private SimpleMatrix[] X = new SimpleMatrix[3];
	private SimpleMatrix[] pX = new SimpleMatrix[3];
	private SimpleMatrix[] pP = new SimpleMatrix[3];
	private SimpleMatrix[] P = new SimpleMatrix[3];
	private SimpleMatrix[] Z = new SimpleMatrix[3];
	private SimpleMatrix[] G = new SimpleMatrix[3];
	private SimpleMatrix A = new SimpleMatrix(stateNum, stateNum);
	private SimpleMatrix B = new SimpleMatrix(stateNum, 1);
	private SimpleMatrix C = new SimpleMatrix(sensorNum, stateNum);
	private SimpleMatrix U = new SimpleMatrix(1, 1);
	private SimpleMatrix Q = new SimpleMatrix(stateNum, stateNum);
	private SimpleMatrix[] R = new SimpleMatrix[3];
	
	public KalmanFilterPVAControl(double[][] a, double[][] c, double[][] q, double[] r, double[][] b) {
		for(int i = 0; i < 3; i++) {
			X[i] = new SimpleMatrix(stateNum, 1);
			pX[i] = new SimpleMatrix(stateNum, 1);
			pP[i] = new SimpleMatrix(fillDoubleArray(1, stateNum, stateNum));
			P[i] = new SimpleMatrix(stateNum, stateNum);
			Z[i] = new SimpleMatrix(sensorNum, 1);
			G[i] = new SimpleMatrix(stateNum, sensorNum);
			R[i] = SimpleMatrix.diag(r);
		}
		A = new SimpleMatrix(a);
		B = new SimpleMatrix(b);
		C = new SimpleMatrix(c);
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

	private void predict(int i) {
		X[i] = (A.mult(pX[i])).plus(B.mult(U));
		P[i] = A.mult(pP[i]).mult(A.transpose()).plus(Q);
	}
	private void update(int i) {
		G[i] = P[i].mult(C.transpose()).mult((C.mult(P[i]).mult(C.transpose()).plus(R[i]).invert()));
		X[i] = X[i].plus(G[i].mult(Z[i].minus(C.mult(X[i]))));
		P[i] = (SimpleMatrix.identity(3).minus(G[i].mult(C))).mult(P[i]);
		
		pP[i] = P[i];
	    pX[i] = X[i];
	}

	@Override
	public ArrayList<Double> run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		Z[txyz-1].set(0, 0, accelerations.get(measNum)[txyz]);
		Z[txyz-1].set(1, 0, accelerations.get(measNum)[txyz+3]);
		Z[txyz-1].set(2, 0, accelerations.get(measNum)[7]);
		
		
		double time = accelerations.get(measNum)[0];
		A.set(1, 0, time);
		A.set(2, 1, time);
		A.set(2, 0, 0.5*Math.pow(time, 2));
		
		U.set(0, 0, 0);
		
		if(measNum == 0) {
			pX[txyz-1] = new SimpleMatrix(new double[][] {{(Z[txyz-1].get(0, 0)+Z[txyz-1].get(1, 0))/2}, {Z[txyz-1].get(2, 0)}, {0}});
		}
		
		predict(txyz-1);
		update(txyz-1);
		
		ArrayList<Double> values = new ArrayList<Double>();
		for(int i = 0; i < 3; i++) {
			values.add(X[txyz-1].get(i, 0));
		}
		
		return values;
	}

}
