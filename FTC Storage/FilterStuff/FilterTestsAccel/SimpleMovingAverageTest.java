package org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilterTestsAccel;

import java.io.IOException;

import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilePath;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FiltersTest;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.Filters.SimpleMovingAverage;
import org.jfree.ui.RefineryUtilities;

public class SimpleMovingAverageTest extends FiltersTest<SimpleMovingAverage> {

	private static final long serialVersionUID = -172886545774556561L;

	public static void main(String[] args) throws IOException {
		int[] sampleSize = new int[] {5, 5, 5};
		SimpleMovingAverageTest chart = new SimpleMovingAverageTest("SMA Test", "SMA", 
				new SimpleMovingAverage(sampleSize), FilePath.path);
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
	
	public SimpleMovingAverageTest(String title, String chartTitle, SimpleMovingAverage filter, String filePath) throws IOException {
		super(title, chartTitle, filter, filePath);
	}

}
