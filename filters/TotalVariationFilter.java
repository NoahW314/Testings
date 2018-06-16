package filters;

import java.util.ArrayList;

import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.sparse.csc.CommonOps_DSCC;

public class TotalVariationFilter extends IterantFilter<ArrayList<Double>> {
	
	private double smoothingFactor = 100;
	private boolean iterating = false;
	ArrayList<ArrayList<Double>> y = new ArrayList<ArrayList<Double>>();
	ArrayList<ArrayList<Double>> dy = new ArrayList<ArrayList<Double>>();
	
	public TotalVariationFilter(double smoothing) {
		smoothingFactor = smoothing;
	}
	public TotalVariationFilter(){}

	public ArrayList<Double> run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		if(!iterating) {
			y.add(new ArrayList<Double>());
			dy.add(new ArrayList<Double>());
		}
		double[] differences = new double[measNum-1];
		double[][] ya = new double[measNum][1];
		double[][] dya = new double[measNum-1][1];
		for(int i = 0; i < measNum-1 && !iterating; i++) {
			dy.get(txyz-1).add(accelerations.get(i+1)[txyz]-accelerations.get(i)[txyz]);
		}
		for(int i = 0; i < measNum && !iterating; i++) {
			y.get(txyz-1).add(accelerations.get(i)[txyz]+100*(txyz-1));
		}
		
		for(int i = 0; i < measNum-1; i++){
			differences[i] = Math.abs(accelerations.get(i+1)[txyz]-accelerations.get(i)[txyz])/smoothingFactor+2;
			dya[i][0] = dy.get(txyz-1).get(i);
			ya[i][0] = y.get(txyz-1).get(i);
		}
		ya[measNum-1][0] = y.get(txyz-1).get(measNum-1);
		
		DMatrixSparseCSC diagM = CommonOps_DSCC.diag(differences);
		
		DMatrixSparseCSC DDT = new DMatrixSparseCSC(measNum-1, measNum-1);
		DMatrixSparseCSC DT = new DMatrixSparseCSC(measNum, measNum-1);
		for(int i = 0; i < measNum-2; i++) {
			DDT.set(i, i+1, -1);
			DDT.set(i+1, i, -1);
		}
		for(int i = 0; i < measNum-1; i++) {
			DT.set(i, i, -1);
			DT.set(i+1, i, 1);
		}
		DMatrixSparseCSC addM = new DMatrixSparseCSC(measNum-1, measNum-1);
		CommonOps_DSCC.add(1, diagM, 1, DDT, addM, null, null);
		DMatrixRMaj invM = new DMatrixRMaj(measNum-1, measNum-1);
		CommonOps_DSCC.invert(addM, invM);
		DMatrixRMaj prod1 = new DMatrixRMaj(measNum, measNum-1);
		CommonOps_DSCC.mult(DT, invM, prod1);
		DMatrixRMaj prod2 = new DMatrixRMaj(measNum, 1);
		DMatrixRMaj Dy = new DMatrixRMaj(dya);
		CommonOps_DDRM.mult(prod1, Dy, prod2);
		DMatrixRMaj Xk1 = new DMatrixRMaj(measNum, 1);
		DMatrixRMaj Y = new DMatrixRMaj(ya);
		CommonOps_DDRM.subtract(Y, prod2, Xk1);
		
		iterations[txyz-1]-=1;
		if(iterations[txyz-1] > 0) {
			iterating = true;
			System.out.println("Iterating "+(txyz-1));
			return this.run(accelerations, measNum, txyz);
		}
		
		ArrayList<Double> accelerationList = new ArrayList<Double>();
		for(int i = 0; i < measNum; i++) {
			accelerationList.add(Xk1.get(i, 0));
		}
		iterating = false;
		return accelerationList;
	}

}
