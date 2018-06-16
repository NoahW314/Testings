package filters;

import java.util.ArrayList;

public class KalmanFilterSimple implements Filter<Double> {
	
	
	private double[] X = new double[3];
	private double[] pX = new double[3];
	private double[] P = new double[3];
	private double G, Z;
	private double A, C, Q, R;
	public KalmanFilterSimple(double a, double c, double q, double r) {
		A = a;
		C = c;
		Q = q;
		R = r;
	}
	public double[] pP = {1, 1, 1};
	private void predict(int i) {
		X[i] = A*pX[i];
	    P[i] = A*pP[i]*A+Q;
	}
	private void update(int i) {
		G = P[i]*C/(C*P[i]*C+R);
	    X[i] = X[i]+G*(Z-C*X[i]);
	    P[i] = (1-G*C)*P[i];
	    
	    pP[i] = P[i];
	    pX[i] = X[i];
	}

	@Override
	public Double run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		Z = accelerations.get(measNum)[txyz]+100*(txyz-1);
		if(measNum == 0) {
			pX[txyz-1] = Z;
		}
		predict(txyz-1);
		update(txyz-1);
		return X[txyz-1];
	}

}
