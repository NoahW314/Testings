package filterTests;

import java.io.IOException;
import java.util.ArrayList;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import filters.IterantFilter;
import filters.TotalVariationFilter;

public class TotalVariationTest extends FiltersTest<ArrayList<Double>, IterantFilter<ArrayList<Double>>> {
	
	public static void main(String[] args) throws IOException {
		TotalVariationTest chart = new TotalVariationTest("TV Test", "TV", new TotalVariationFilter(50));
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}

	private static final long serialVersionUID = 4436600638624381763L;

	public TotalVariationTest(String title, String chartTitle, IterantFilter<ArrayList<Double>> filt) throws IOException {
		super(title, chartTitle, filt);
	}
	
	@Override
	public XYDataset createDataset() throws IOException {
		final XYSeries filteredX = new XYSeries("FX");
		final XYSeries filteredY = new XYSeries("FY");
		final XYSeries filteredZ = new XYSeries("FZ");
		final XYSeries x = new XYSeries("X");
		final XYSeries y = new XYSeries("Y");
		final XYSeries z = new XYSeries("Z");
		
		ArrayList<Double[]> acceleration = parseFile("F:\\Accel.txt");
		
		double time = 0;
		for(int i = 0; i < acceleration.size(); i++) {
			time+=acceleration.get(i)[0];
			x.add(time, acceleration.get(i)[1]);
			y.add(time, acceleration.get(i)[2]+100);
			z.add(time, acceleration.get(i)[3]+200);
		}
		
		time = 0;
		int times = 1;
		filter.setIterations(times);
		ArrayList<Double> filtx = filter.run(acceleration, acceleration.size(), 1);
		ArrayList<Double> filty = filter.run(acceleration, acceleration.size(), 2);
		ArrayList<Double> filtz = filter.run(acceleration, acceleration.size(), 3);
		for(int i = 0; i < acceleration.size(); i++) {
			time+=acceleration.get(i)[0];
			filteredX.add(time, filtx.get(i));
			filteredY.add(time, filty.get(i));
			filteredZ.add(time, filtz.get(i));
			filteredAccelerations.add(new Double[] {time, filtx.get(i), filty.get(i), filtz.get(i)});
		}
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(filteredX);
		dataset.addSeries(filteredY);
		dataset.addSeries(filteredZ);
		dataset.addSeries(x);          
		dataset.addSeries(y);
		dataset.addSeries(z);
		
	    return dataset;
	}

}
