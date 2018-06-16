package filterTests;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import filters.ExponentialMovingAverage;
import filters.Filter;

public class ExponentialMovingAverageTest extends FiltersTest<Double, Filter<Double>> {
	
	private static final long serialVersionUID = 4962163293350402068L;

	public ExponentialMovingAverageTest(String title, String chartTitle, Filter<Double> filt) throws IOException {
		super(title, chartTitle, filt);
	}

	public static void main(String[] args) throws IOException {
		ExponentialMovingAverageTest chart = new ExponentialMovingAverageTest("EMA Test", "EMA", new ExponentialMovingAverage(0.4));
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
}
