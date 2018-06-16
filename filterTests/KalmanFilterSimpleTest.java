package filterTests;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import filters.Filter;
import filters.KalmanFilterSimple;

public class KalmanFilterSimpleTest extends FiltersTest<Double, Filter<Double>> {

	public static void main(String[] args) throws IOException {
		KalmanFilterSimpleTest chart = new KalmanFilterSimpleTest("Kalman Test", "Kalman", 
				new KalmanFilterSimple(1,1,50,2500/12));
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
	
	private static final long serialVersionUID = 8225195695128160787L;

	public KalmanFilterSimpleTest(String title, String chartTitle, Filter<Double> filt) throws IOException {
		super(title, chartTitle, filt);
	}

}
