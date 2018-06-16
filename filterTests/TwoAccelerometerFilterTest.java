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

public abstract class TwoAccelerometerFilterTest<D, F extends Filter<D>> extends ApplicationFrame {

	private static final long serialVersionUID = 2901742548433234755L;
	
	public F filter;
	public ArrayList<Double[]> filteredAccelerations = new ArrayList<Double[]>();
	public double[] position;

	public TwoAccelerometerFilterTest(String title, String chartTitle, F filt) throws IOException {
		super(title);
		filter = filt;
		JFreeChart chart = ChartFactory.createXYLineChart(
			chartTitle,
			"Time","Acceleration",
			createDataset(),
			PlotOrientation.VERTICAL,
			true,true,false);
      
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(750, 500));
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setRangeGridlinePaint(Color.BLACK);
  
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		
		renderer.setSeriesPaint(0, Color.RED);//filtered x
		renderer.setSeriesPaint(1, Color.BLUE);//filtered y
		renderer.setSeriesPaint(2, Color.BLACK);//filtered z
		renderer.setSeriesPaint(3, Color.MAGENTA);//x1
		renderer.setSeriesPaint(4, Color.CYAN);//y1
		renderer.setSeriesPaint(5, Color.GRAY);//z1
		renderer.setSeriesPaint(6, Color.PINK);//x2
		float[] hsb = new float[3];
		Color.RGBtoHSB(100, 150, 255, hsb);
		renderer.setSeriesPaint(7, Color.getHSBColor(hsb[0], hsb[1], hsb[2]));//y2
		renderer.setSeriesPaint(8, Color.LIGHT_GRAY);//z2
		
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesShapesVisible(1, false);
		renderer.setSeriesShapesVisible(2, false);
		renderer.setSeriesShapesVisible(3, false);
		renderer.setSeriesShapesVisible(4, false);
		renderer.setSeriesShapesVisible(5, false);
		renderer.setSeriesShapesVisible(6, false);
		renderer.setSeriesShapesVisible(7, false);
		renderer.setSeriesShapesVisible(8, false);
		
		plot.setRenderer(renderer); 
		plot.setSeriesRenderingOrder(SeriesRenderingOrder.REVERSE);
		setContentPane(chartPanel);
		
		position = calculatePosition(filteredAccelerations);
		for(int k = 0; k < 3; k++) {
			System.out.println(position[k]);
		}
	}

	private XYDataset createDataset() throws IOException {
		final XYSeries filtX = new XYSeries("filtered x");
		final XYSeries filtY = new XYSeries("filtered y");
		final XYSeries filtZ = new XYSeries("filtered z");
		final XYSeries accel1x = new XYSeries("Accel 1x");
		final XYSeries accel1y = new XYSeries("Accel 1y");
		final XYSeries accel1z = new XYSeries("Accel 1z");
		final XYSeries accel2x = new XYSeries("Accel 2x");
		final XYSeries accel2y = new XYSeries("Accel 2y");
		final XYSeries accel2z = new XYSeries("Accel 2z");
		
		ArrayList<Double[]> acceleration = parseFile("F:\\AccelCompare.txt");
		
		double time = 0;
		int counter = 0;
		for(Double[] accel : acceleration) {
			time+=accel[0];
			double fX = (double) filter.run(acceleration, counter, 1);
			double fY = (double) filter.run(acceleration, counter, 2);
			double fZ = (double) filter.run(acceleration, counter, 3);
			filteredAccelerations.add(new Double[]{time, fX, fY, fZ});
			filtX.add(time, fX);
			filtY.add(time, fY);
			filtZ.add(time, fZ);
			accel1x.add(time, accel[1]);
			accel1y.add(time, accel[2]+100);
			accel1z.add(time, accel[3]+200);
			accel2x.add(time, accel[4]);
			accel2y.add(time, accel[5]+100);
			accel2z.add(time, accel[6]+200);
			
			counter++;
		}
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(filtX);
		dataset.addSeries(filtY);
		dataset.addSeries(filtZ);
		dataset.addSeries(accel1x);
		dataset.addSeries(accel1y);
		dataset.addSeries(accel1z);
		dataset.addSeries(accel2x);
		dataset.addSeries(accel2y);
		dataset.addSeries(accel2z);
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
