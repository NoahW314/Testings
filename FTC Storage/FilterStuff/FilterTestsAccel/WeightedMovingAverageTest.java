package org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilterTestsAccel;

import java.io.IOException;

import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilePath;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FiltersTest;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.Filters.WeightedMovingAverage;
import org.jfree.ui.RefineryUtilities;

public class WeightedMovingAverageTest extends FiltersTest<WeightedMovingAverage> {

	private static final long serialVersionUID = 3335273766621691777L;
	
	public static void main(String[] args) throws IOException {
		int[] sampleSize = new int[] {5, 5, 5};
		WeightedMovingAverageTest chart = new WeightedMovingAverageTest("SMA Test", "SMA", 
				new WeightedMovingAverage(sampleSize), FilePath.path);
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}

	public WeightedMovingAverageTest(String title, String chartTitle, WeightedMovingAverage filt, String filePath) throws IOException {
		super(title, chartTitle, filt, filePath);
	}

}
