package filters;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import filterTests.KalmanFilterPVAControl;
import filterTests.TwoAccelerometerFilterTest;

public class KalmanFilterPVAControlTest extends TwoAccelerometerFilterTest<Double, Filter<Double>> {

	private static final long serialVersionUID = 228402347124222017L;
	
	public static void main(String[] args) throws IOException {
		//used as a space holder
		double t = 0;
		KalmanFilterPVAControlTest chart = new KalmanFilterPVAControlTest("Kalman PVA Test", "Kalman PVA", 
				new KalmanFilterPVAControl(new double[][] {{1, 0, 0}, {t, 1, 0}, {0.5*Math.pow(t, 2), t, 1}},
									new double[][] {{1, 0, 0}, {1, 0, 0}}, 
									new double[][] {{50, 50, 50}, {50, 50, 50}, {50, 50, 50}}, 
									new double[] {2500/12, 2500/12}));
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}

	public KalmanFilterPVAControlTest(String title, String chartTitle, Filter<Double> filt) throws IOException {
		super(title, chartTitle, filt);
	}

}
