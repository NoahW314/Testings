package filterTests;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import filters.Filter;

public class IndividualAccelerationsFilterTest<D, F extends Filter<D>> extends ApplicationFrame {
	
	private static final long serialVersionUID = 6731105177727373158L;
	
	public F filter;
	public ArrayList<Double[]> filteredAccelerations = new ArrayList<Double[]>();
	public double[] position;

	public IndividualAccelerationsFilterTest(String title, String chartTitle, F filt, int i) throws IOException {
		super(title);
		filter = filt;
		JFreeChart chart = ChartFactory.createXYLineChart(
			chartTitle+" "+i,
			"Time","Acceleration",
			createDataset(i),
			PlotOrientation.VERTICAL,
			true,true,false);
      
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1250, 900));
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setRangeGridlinePaint(Color.BLACK);
  
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		
		renderer.setSeriesPaint(0, Color.RED);//filtered accel
		renderer.setSeriesPaint(1, Color.PINK);//accel 1
		renderer.setSeriesPaint(2, Color.MAGENTA);//accel 2
		renderer.setSeriesPaint(3, Color.BLACK);//estimated velocity
		renderer.setSeriesPaint(4, Color.GRAY);//recorded velocity
		renderer.setSeriesPaint(5, Color.BLUE);//estimated position
		renderer.setSeriesPaint(6, Color.DARK_GRAY);//filtered accel integrated to velo
		
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesShapesVisible(1, false);
		renderer.setSeriesShapesVisible(2, false);
		renderer.setSeriesShapesVisible(3, false);
		renderer.setSeriesShapesVisible(4, false);
		renderer.setSeriesShapesVisible(5, false);
		renderer.setSeriesShapesVisible(6, false);
	
		plot.setRenderer(renderer); 
		plot.setSeriesRenderingOrder(SeriesRenderingOrder.REVERSE);
		setContentPane(chartPanel);
		
	}

	private XYDataset createDataset(int i) throws IOException {
		final XYSeries filtAccel = new XYSeries("filtered acceleration");
		final XYSeries accel1 = new XYSeries("accel1");
		final XYSeries accel2 = new XYSeries("accel2");
		final XYSeries estVelo = new XYSeries("estimated velocity");
		final XYSeries recVelo = new XYSeries("recorded velocity");
		final XYSeries estPos = new XYSeries("estimated position");
		final XYSeries intVelo = new XYSeries("intVelo");
		
		ArrayList<Double[]> acceleration = parseFile("F:\\TwoAccelVelo.txt");
		
		double time = 0;
		int counter = 0;
		for(int j = 0; j < acceleration.size(); j++) {
			Double[] accel = acceleration.get(j);
			time+=accel[0];
			ArrayList<Double> fAVP = (ArrayList<Double>) filter.run(acceleration, counter, i+1);
			filtAccel.add(time, fAVP.get(0));
			accel1.add(time, accel[1+i]);
			accel2.add(time, accel[4+i]);
			estVelo.add(time, fAVP.get(1)/8);
			recVelo.add(time, accel[7]/8);
			estPos.add(time, fAVP.get(2)/240);
			if(j > 0) {
				intVelo.add(time, time*(fAVP.get(0)+(Double)filtAccel.getY(j-1))/2/8);
			}
			else {
				intVelo.add(time, fAVP.get(0)/8);
			}
			
			counter++;
		}
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(filtAccel);
		dataset.addSeries(accel1);
		dataset.addSeries(accel2);
		dataset.addSeries(estVelo);
		dataset.addSeries(recVelo);
		dataset.addSeries(estPos);
		dataset.addSeries(intVelo);
		return dataset;
	}
	
	ArrayList<Double[]> parseFile(String filePath) throws IOException {
		FileReader fr = new FileReader(filePath);
		String text = readAllLines(fr);
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(text.split("\n")));
		lines.remove(0);
		
		ArrayList<Double[]> accelerations = new ArrayList<Double[]>();
		
		for(String str : lines){
			String[] measurements = str.split(",");
			accelerations.add(stringsToDoubles(measurements));
		}
		
		return accelerations;
	}
	private Double[] stringsToDoubles(String[] strings) {
		Double[] doubles = new Double[strings.length];
		for(int j = 0; j < strings.length; j++) {
			doubles[j] = Double.parseDouble(strings[j]);
		}
		return doubles;
	}
	private static String readAllLines(FileReader fr) throws IOException {
		String str = "";
		int i;
		while((i=fr.read()) != -1) {
			str+=(char)i;
		}
		return str;
	}

}
