package org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilterTestsAccel;

import java.io.IOException;

import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilePath;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FiltersTest;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.Filters.NoFilter;
import org.jfree.ui.RefineryUtilities;

public class NoFilterTest extends FiltersTest<NoFilter> {
	
	public static void main(String[] args) throws IOException {
      NoFilterTest chart = new NoFilterTest("Filter Test","No Filter", new NoFilter(), FilePath.path);

      chart.pack();
      RefineryUtilities.centerFrameOnScreen(chart);
      chart.setVisible(true);
   }
	
	private static final long serialVersionUID = 2780949740655076103L;

	public NoFilterTest(String title, String chartTitle, NoFilter filt, String filePath) throws IOException {
		super(title, chartTitle, filt, filePath);
	}

}
