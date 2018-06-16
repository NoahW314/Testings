package filterTests;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import filters.Filter;
import filters.WeightedMovingAverage;

public class WeightedMovingAverageTest extends FiltersTest<Double, Filter<Double>> {

	private static final long serialVersionUID = 3335273766621691777L;
	
	public static void main(String[] args) throws IOException {
		WeightedMovingAverageTest chart = new WeightedMovingAverageTest("SMA Test", "SMA", new WeightedMovingAverage(5));
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}

	public WeightedMovingAverageTest(String title, String chartTitle, Filter<Double> filt) throws IOException {
		super(title, chartTitle, filt);
	}

}
