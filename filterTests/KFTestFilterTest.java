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

public class KFTestFilterTest<D, F extends Filter<D>> extends ApplicationFrame {

	private static final long serialVersionUID = 2781530988174234618L;
	
	public F filter;
	public ArrayList<Double[]> filteredAccelerations = new ArrayList<Double[]>();
	public double[] position;

	public KFTestFilterTest(String title, String chartTitle, F filt, int i) throws IOException {
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
		renderer.setSeriesPaint(3, Color.BLACK);//estimated position
		renderer.setSeriesPaint(4, Color.GRAY);//recorded encoder position
		renderer.setSeriesPaint(5, Color.GREEN);//recorded vuforia position
		renderer.setSeriesPaint(6, Color.BLUE);//recorded ultrasonic position
		
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

	@SuppressWarnings("unchecked")
	private XYDataset createDataset(int i) throws IOException {
		final XYSeries filtAccel = new XYSeries("filt accel");
		final XYSeries accel1 = new XYSeries("accel1");
		final XYSeries accel2 = new XYSeries("accel2");
		final XYSeries estPos = new XYSeries("est pose");
		final XYSeries rEPos = new XYSeries("rec enc pose");
		final XYSeries rVPos = new XYSeries("rec Vuf pose");
		final XYSeries rUPos = new XYSeries("rec ultra pose");
		
		ArrayList<Double[]> acceleration = parseFile("F:\\2A2P.txt");
		
		int time = 0;
		double prevPos = 0;
		int counter = 0;
		for(int j = 0; j < acceleration.size(); j++) {
			Double[] accel = acceleration.get(j);
			acceleration.get(j)[7]+=prevPos;
			time+=accel[0];
			ArrayList<Double> fAVP = null;
			try{
				fAVP = (ArrayList<Double>) filter.run(acceleration, counter, i+1);
			}
			catch(Exception e) {
				System.out.println(j);
				System.out.println(counter);
				throw e;
			}
			filtAccel.add(time, fAVP.get(0));
			accel1.add(time, accel[1+i]);
			accel2.add(time, accel[4+i]);
			estPos.add(time, fAVP.get(2)/240);
			rEPos.add(time, accel[7]/240);
			rVPos.add(time, accel[8]/240);
			rUPos.add(time, accel[9]/240);
			
			counter++;
			prevPos = fAVP.get(2);
		}
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(filtAccel);
		dataset.addSeries(accel1);
		dataset.addSeries(accel2);
		dataset.addSeries(estPos);
		dataset.addSeries(rEPos);
		dataset.addSeries(rVPos);
		dataset.addSeries(rUPos);
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
