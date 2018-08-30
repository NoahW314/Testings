package org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff;

import java.io.IOException;
import java.util.ArrayList;

import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.Filters.Filter;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public abstract class FiltersTest<F extends Filter<Double[]>> extends FiltersTestTemplate<F> {
	
	private static final long serialVersionUID = -8985121128668181591L;

	/*public static void main(String[] args) throws IOException {
      FiltersTest chart = new FiltersTest(
         "Graph Test" ,
         "Graph");

      chart.pack();
      RefineryUtilities.centerFrameOnScreen(chart);
      chart.setVisible(true);
   }*/

	public FiltersTest(String title, String chartTitle, F filt, String filePath) throws IOException {
		super(title, chartTitle, filt, filePath);
	}
	
	public XYDataset createDataset(String filePath) throws IOException {
		final XYSeries filteredX = new XYSeries("FX");
		final XYSeries filteredY = new XYSeries("FY");
		final XYSeries filteredZ = new XYSeries("FZ");
		final XYSeries x = new XYSeries("X");
		final XYSeries y = new XYSeries("Y");
		final XYSeries z = new XYSeries("Z");
		
		ArrayList<Double[]> acceleration = parseFile(filePath);
		
		double time = 0;
		double pTime = 0;
		for(int i = 0; i < acceleration.size(); i++) {
			time = acceleration.get(i)[0];
			Double[] f = filter.run(acceleration, i);
			double fX = f[0];
			double fY = f[1];
			double fZ = f[2];
			filteredAccelerations.add(new Double[]{time-pTime, fX, fY, fZ});
			filteredX.add(time, fX);
			filteredY.add(time, fY+100);
			filteredZ.add(time, fZ+200);
			x.add(time, acceleration.get(i)[1]);
			y.add(time, acceleration.get(i)[2]+100);
			z.add(time, acceleration.get(i)[3]+200);
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
