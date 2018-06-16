package filterTests;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import filters.Filter;
import filters.SimpleMovingAverage;

public class SimpleMovingAverageTest extends FiltersTest<Double, Filter<Double>> {

	private static final long serialVersionUID = -172886545774556561L;

	public static void main(String[] args) throws IOException {
		SimpleMovingAverageTest chart = new SimpleMovingAverageTest("SMA Test", "SMA", new SimpleMovingAverage(5));
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
	
	public SimpleMovingAverageTest(String title, String chartTitle, Filter<Double> filter) throws IOException {
		super(title, chartTitle, filter);
	}

}
