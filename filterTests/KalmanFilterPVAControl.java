package filterTests;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

import filters.Filter;

public class KalmanFilterPVAControl implements Filter<Double> {
	
	private SimpleMatrix[] X = new SimpleMatrix[3];
	private SimpleMatrix[] pX = new SimpleMatrix[3];
	private SimpleMatrix[] pP = new SimpleMatrix[3];
	private SimpleMatrix[] P = new SimpleMatrix[3];
	private SimpleMatrix[] Z = new SimpleMatrix[3];
	private SimpleMatrix[] G = new SimpleMatrix[3];
	private SimpleMatrix A = new SimpleMatrix(3, 3);
	private SimpleMatrix B = new SimpleMatrix(3, 1);
	private SimpleMatrix U = new SimpleMatrix(1, 1);
	private SimpleMatrix C = new SimpleMatrix(2, 3);
	private SimpleMatrix Q = new SimpleMatrix(3, 3);
	private SimpleMatrix[] R = new SimpleMatrix[3];
	
	public KalmanFilterPVAControl(double[][] a, double[][] c, double[][] q, double[] r, double u) {
		for(int i = 0; i < 3; i++) {
			X[i] = new SimpleMatrix(3, 1);
			pX[i] = new SimpleMatrix(3, 1);
			pP[i] = new SimpleMatrix(new double[][] {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}});
			P[i] = new SimpleMatrix(3, 3);
			Z[i] = new SimpleMatrix(2, 1);
			G[i] = new SimpleMatrix(3, 2);
			R[i] = SimpleMatrix.diag(r);
		}
		A = new SimpleMatrix(a);
		C = new SimpleMatrix(c);
		Q = new SimpleMatrix(q);
	}
	
	private void predict(int i) {
		X[i] = (A.mult(pX[i]));
		P[i] = A.mult(pP[i]).mult(A.transpose()).plus(Q);
	}
	private void update(int i) {
		G[i] = P[i].mult(C.transpose()).mult((C.mult(P[i]).mult(C.transpose()).plus(R[i]).invert()));
		X[i] = pX[i].plus(G[i].mult(Z[i].minus(C.mult(X[i]))));
		P[i] = (SimpleMatrix.identity(3).minus(G[i].mult(C))).mult(P[i]);
		pP[i] = P[i];
	    pX[i] = X[i];
	}

	@Override
	public Double run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		Z[txyz-1].set(0, 0, accelerations.get(measNum)[txyz]+100*(txyz-1));
		Z[txyz-1].set(1, 0, accelerations.get(measNum)[txyz+3]+100*(txyz-1));
		
		double time = accelerations.get(measNum)[0];
		A.set(1, 0, time);
		A.set(2, 1, time);
		A.set(2, 0, 0.5*Math.pow(time, 2));
		
		if(measNum == 0) {
			pX[txyz-1] = new SimpleMatrix(new double[][] {{(Z[txyz-1].get(0, 0)+Z[txyz-1].get(1, 0))/2}, {0}, {0}});
		}
		
		if(measNum == 0 && txyz == 3) {
			Z[txyz-1].print();
			pX[txyz-1].print();
		}
		predict(txyz-1);
		if(measNum == 0 && txyz == 3) {
			System.out.println("Predicted");
			X[txyz-1].print();
		}
		update(txyz-1);
		
		if(measNum == 0 && txyz == 3) {
			System.out.println("Updated");
			X[txyz-1].print();
		}
		
		return X[txyz-1].get(0, 0);
	}

}
