package org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilterTestsAccel;

import java.io.IOException;

import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilePath;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FiltersTest;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.Filters.ExponentialMovingAverage;
import org.jfree.ui.RefineryUtilities;

public class ExponentialMovingAverageTest extends FiltersTest<ExponentialMovingAverage> {
	
	private static final long serialVersionUID = 4962163293350402068L;

	public ExponentialMovingAverageTest(String title, String chartTitle, ExponentialMovingAverage filt, String filePath) throws IOException {
		super(title, chartTitle, filt, filePath);
	}

	public static void main(String[] args) throws IOException {
		double[] smoothingFactor = new double[] {0.4, 0.4, 0.4};
		ExponentialMovingAverageTest chart = new ExponentialMovingAverageTest("EMA Test", "EMA", 
				new ExponentialMovingAverage(smoothingFactor), FilePath.path);
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
}
