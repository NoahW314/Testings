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
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import filters.Filter;

public abstract class FiltersTest<D, F extends Filter<D>> extends ApplicationFrame {
	
	public F filter;
	public ArrayList<Double[]> filteredAccelerations = new ArrayList<Double[]>();
	public double[] position;
	
	private static final long serialVersionUID = -8985121128668181591L;

	/*public static void main(String[] args) throws IOException {
      FiltersTest chart = new FiltersTest(
         "Graph Test" ,
         "Graph");

      chart.pack();
      RefineryUtilities.centerFrameOnScreen(chart);
      chart.setVisible(true);
   }*/

	public FiltersTest(String title, String chartTitle, F filt) throws IOException {
		super(title);
		filter = filt;
		JFreeChart chart = ChartFactory.createXYLineChart(
			chartTitle,
			"Time","Acceleration",
			createDataset(),
			PlotOrientation.VERTICAL,
			true,true,false);
        //chart.DEFAULT_BACKGROUND_PAINT = Color.WHITE;
      
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(750, 500));
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setRangeGridlinePaint(Color.BLACK);
  
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesPaint(2, Color.BLACK);
		renderer.setSeriesPaint(3, Color.MAGENTA);
		renderer.setSeriesPaint(4, Color.CYAN);
		renderer.setSeriesPaint(5, Color.GRAY);
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesShapesVisible(1, false);
		renderer.setSeriesShapesVisible(2, false);
		renderer.setSeriesShapesVisible(3, false);
		renderer.setSeriesShapesVisible(4, false);
		renderer.setSeriesShapesVisible(5, false);
		plot.setRenderer(renderer); 
		setContentPane(chartPanel);
		
		position = calculatePosition(filteredAccelerations);
		for(int k = 0; k < 3; k++) {
			System.out.println(position[k]);
		}
	}
	
	public XYDataset createDataset() throws IOException {
		final XYSeries filteredX = new XYSeries("FX");
		final XYSeries filteredY = new XYSeries("FY");
		final XYSeries filteredZ = new XYSeries("FZ");
		final XYSeries x = new XYSeries("X");
		final XYSeries y = new XYSeries("Y");
		final XYSeries z = new XYSeries("Z");
		
		ArrayList<Double[]> acceleration = parseFile("F:\\Accel.txt");
		
		double time = 0;
		for(int i = 0; i < 80; i++) {
			time+=acceleration.get(i)[0];
			double fX = (double) filter.run(acceleration, i, 1);
			double fY = (double) filter.run(acceleration, i, 2);
			double fZ = (double) filter.run(acceleration, i, 3);
			filteredAccelerations.add(new Double[]{time, fX, fY, fZ});
			filteredX.add(time, fX);
			filteredY.add(time, fY);
			filteredZ.add(time, fZ);
			x.add(time, acceleration.get(i)[1]);
			y.add(time, acceleration.get(i)[2]+100);
			z.add(time, acceleration.get(i)[3]+200);
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
	
	private double[] calculatePosition(ArrayList<Double[]> filtAccel) {
		double[] pVelocity = {0,0,0};
		double[] velocity = {0,0,0};
		double[] position = {0,0,0};
		
		for(int i = 0; i < filtAccel.size()-1; i++) {
			for(int j = 0; j < 3; j++) {
				//is time i or i+1?
				double time = filtAccel.get(i)[0];
				velocity[j]+=(filtAccel.get(i)[j+1]+filtAccel.get(i+1)[j+1])/2*time;
				position[j]+=(pVelocity[j]+velocity[j])/2*time;
			}
			pVelocity = velocity;
		}
		
		return position;
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
