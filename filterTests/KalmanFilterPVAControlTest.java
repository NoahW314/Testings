package filterTests;

import java.io.IOException;
import java.util.ArrayList;

import org.jfree.ui.RefineryUtilities;

import filters.Filter;
import filters.KalmanFilterPVAControl;

public class KalmanFilterPVAControlTest extends IndividualAccelerationsFilterTest<ArrayList<Double>, Filter<ArrayList<Double>>> {

	private static final long serialVersionUID = 228402347124222017L;
	
	public static void main(String[] args) throws IOException {
		//used as a space holder
		double t = 0;
		for(int i = 0; i < 1; i++) {
			KalmanFilterPVAControlTest chart = new KalmanFilterPVAControlTest("Kalman PVA Test", "Kalman PVA", 
					new KalmanFilterPVAControl(new double[][] {{1, 0, 0}, {t, 1, 0}, {0.5*Math.pow(t, 2), t, 1}},
										new double[][] {{1, 0, 0}, {1, 0, 0}, {0, 1, 0}}, 
										new double[][] {{50, 0, 0}, {0, 0, 0}, {0, 0, 0}}, 
										new double[] {2500/12, 2500/12, 0},
										new double[][] {{0}, {0}, {0}}), i);
			chart.pack();
			RefineryUtilities.centerFrameOnScreen(chart);
			chart.setVisible(true);
		}
	}

	public KalmanFilterPVAControlTest(String title, String chartTitle, Filter<ArrayList<Double>> filt, int i) throws IOException {
		super(title, chartTitle, filt, i);
	}

}
