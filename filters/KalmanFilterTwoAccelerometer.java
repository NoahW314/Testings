package filters;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

public class KalmanFilterTwoAccelerometer implements Filter<Double> {
	
	private SimpleMatrix[] X = new SimpleMatrix[3];
	private SimpleMatrix[] pX = new SimpleMatrix[3];
	private SimpleMatrix[] pP = new SimpleMatrix[3];
	private SimpleMatrix[] P = new SimpleMatrix[3];
	private SimpleMatrix[] Z = new SimpleMatrix[3];
	private SimpleMatrix[] G = new SimpleMatrix[3];
	private SimpleMatrix A = new SimpleMatrix(1, 1);
	private SimpleMatrix C = new SimpleMatrix(2, 1);
	private SimpleMatrix[] Q = new SimpleMatrix[3];
	private SimpleMatrix[] R = new SimpleMatrix[3];
	
	public KalmanFilterTwoAccelerometer(double a, double[] c, double[] q, double[] r) {
		for(int i = 0; i < 3; i++) {
			X[i] = new SimpleMatrix(1, 1);
			pX[i] = new SimpleMatrix(1, 1);
			pP[i] = new SimpleMatrix(new double[][] {{1}});
			P[i] = new SimpleMatrix(1, 1);
			Z[i] = new SimpleMatrix(2, 1);
			G[i] = new SimpleMatrix(1, 2);
			Q[i] = new SimpleMatrix(new double[][] {{q[i]}});
			R[i] = SimpleMatrix.diag(r);
		}
		A = new SimpleMatrix(new double[][] {{a}});
		C = new SimpleMatrix(new double[][] {{c[0]}, {c[1]}});
	}
	
	private void predict(int i) {
		X[i] = (A.mult(pX[i]));
		P[i] = A.mult(pP[i]).mult(A.transpose()).plus(Q[i]);
	}
	private void update(int i) {
		G[i] = P[i].mult(C.transpose()).mult((C.mult(P[i]).mult(C.transpose()).plus(R[i]).invert()));
		X[i] = pX[i].plus(G[i].mult(Z[i].minus(C.mult(X[i]))));
		P[i] = (SimpleMatrix.identity(1).minus(G[i].mult(C))).mult(P[i]);
		pP[i] = P[i];
	    pX[i] = X[i];
	}

	@Override
	public Double run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		Z[txyz-1].set(0, 0, accelerations.get(measNum)[txyz]+100*(txyz-1));
		Z[txyz-1].set(1, 0, accelerations.get(measNum)[txyz+3]+100*(txyz-1));
		
		if(measNum == 0) {
			pX[txyz-1] = new SimpleMatrix(new double[][] {{(Z[txyz-1].get(0, 0)+Z[txyz-1].get(1, 0))/2}});
		}
		predict(txyz-1);
		update(txyz-1);
		
		return X[txyz-1].get(0, 0);
	}

}