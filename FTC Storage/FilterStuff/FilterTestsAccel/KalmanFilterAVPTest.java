package org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilterTestsAccel;

import java.io.IOException;

import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilePath;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FiltersTest;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.Filters.KalmanFilterAVP;
import org.jfree.ui.RefineryUtilities;

public class KalmanFilterAVPTest extends FiltersTest<KalmanFilterAVP> {

	public static void main(String[] args) throws IOException {
		//space holder for time
		double t = 0;
		KalmanFilterAVPTest chart = new KalmanFilterAVPTest("Kalman Test", "Kalman", 
				new KalmanFilterAVP(new double[][] {{1, 0, 0}, {t, 1, 0}, {0.5*Math.pow(t, 2), t, 1}},
						new double[][] {{1, 0, 0}}, 
						new double[][] {{50, 0, 0}, {0, 0, 0}, {0, 0, 0}}, 
						new double[] {2500/12},
						new double[][] {{0}, {0}, {0}}), FilePath.path);
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
	
	private static final long serialVersionUID = 8225195695128160787L;

	public KalmanFilterAVPTest(String title, String chartTitle, KalmanFilterAVP filt, String filePath) throws IOException {
		super(title, chartTitle, filt, filePath);
	}

}
