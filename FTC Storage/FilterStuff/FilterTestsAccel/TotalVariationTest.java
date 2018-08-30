package org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilterTestsAccel;

import java.io.IOException;
import java.util.ArrayList;

import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FilePath;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.FiltersTestTemplate;
import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.Filters.TotalVariationFilter;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class TotalVariationTest extends FiltersTestTemplate<TotalVariationFilter> {
	
	public static void main(String[] args) throws IOException {
		TotalVariationTest chart = new TotalVariationTest("TV Test", "TV", 
				new TotalVariationFilter(new double[] {50, 50, 50}), FilePath.path);
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}

	private static final long serialVersionUID = 4436600638624381763L;

	public TotalVariationTest(String title, String chartTitle, TotalVariationFilter filt, String filePath) throws IOException {
		super(title, chartTitle, filt, filePath);
	}
	
	@Override
	public XYDataset createDataset(String filePath) throws IOException {
		final XYSeries filteredX = new XYSeries("FX");
		final XYSeries filteredY = new XYSeries("FY");
		final XYSeries filteredZ = new XYSeries("FZ");
		final XYSeries x = new XYSeries("X");
		final XYSeries y = new XYSeries("Y");
		final XYSeries z = new XYSeries("Z");
		
		ArrayList<Double[]> acceleration = parseFile(filePath);
		
		double time = 0;
		for(int i = 0; i < acceleration.size(); i++) {
			time = acceleration.get(i)[0];
			x.add(time, acceleration.get(i)[1]);
			y.add(time, acceleration.get(i)[2]+100);
			z.add(time, acceleration.get(i)[3]+200);
		}
		
		time = 0;
		double pTime = 0;
		int times = 1;
		filter.setIterations(times);
		ArrayList<Double> filtx = filter.run(acceleration, acceleration.size(), 0);
		ArrayList<Double> filty = filter.run(acceleration, acceleration.size(), 1);
		ArrayList<Double> filtz = filter.run(acceleration, acceleration.size(), 2);
		for(int i = 0; i < acceleration.size(); i++) {
			time = acceleration.get(i)[0];
			filteredX.add(time, filtx.get(i));
			filteredY.add(time, filty.get(i)+100);
			filteredZ.add(time, filtz.get(i)+200);
			filteredAccelerations.add(new Double[] {time-pTime, filtx.get(i), filty.get(i), filtz.get(i)});
			pTime = time;
		}
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(x);  
		dataset.addSeries(filteredX);
		dataset.addSeries(y);
		dataset.addSeries(filteredY);
		dataset.addSeries(z);
		dataset.addSeries(filteredZ);
		
	    return dataset;
	}

}
