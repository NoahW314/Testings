package filterTests;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import filters.Filter;
import filters.NoFilter;

public class NoFilterTest extends FiltersTest<Double, Filter<Double>> {
	
	public static void main(String[] args) throws IOException {
      NoFilterTest chart = new NoFilterTest("Filter Test","No Filter", new NoFilter());

      chart.pack();
      RefineryUtilities.centerFrameOnScreen(chart);
      chart.setVisible(true);
   }
	
	private static final long serialVersionUID = 2780949740655076103L;

	public NoFilterTest(String title, String chartTitle, Filter<Double> filt) throws IOException {
		super(title, chartTitle, filt);
	}

}
