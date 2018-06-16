package filterTests;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import filters.Filter;
import filters.KalmanFilterTwoAccelerometer;

public class KalmanFilterTwoAccelerometerTest extends TwoAccelerometerFilterTest<Double, Filter<Double>>{
	
	private static final long serialVersionUID = -3918220757311853572L;

	public static void main(String[] args) throws IOException {
		KalmanFilterTwoAccelerometerTest chart = new KalmanFilterTwoAccelerometerTest("Kalman Double Test", "Kalman Double", 
				new KalmanFilterTwoAccelerometer(1, new double[]{1,1}, new double[] {50, 50, 50}, new double[] {2500/12, 2500/12}));
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}

	public KalmanFilterTwoAccelerometerTest(String title, String chartTitle, Filter<Double> filt) throws IOException {
		super(title, chartTitle, filt);
	}

}
