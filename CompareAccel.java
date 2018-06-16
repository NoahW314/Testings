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
import org.jfree.ui.RefineryUtilities;

public class CompareAccel extends ApplicationFrame {
	
	private static final long serialVersionUID = 6564773338279201679L;

	public CompareAccel(String title, String chartTitle) throws IOException {
		super(title);
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
		plot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		setContentPane(chartPanel);
	}

	private XYDataset createDataset() throws IOException {
		final XYSeries accel1x = new XYSeries("Accel 1x");
		final XYSeries accel1y = new XYSeries("Accel 1y");
		final XYSeries accel1z = new XYSeries("Accel 1z");
		final XYSeries accel2x = new XYSeries("Accel 2x");
		final XYSeries accel2y = new XYSeries("Accel 2y");
		final XYSeries accel2z = new XYSeries("Accel 2z");
	
		ArrayList<Double[]> acceleration = parseFile("F:\\AccelCompare.txt");
		acceleration.remove(1);
		
		double time = 0;
		for(Double[] accel : acceleration) {
			time+=accel[1];
			accel1x.add(time, accel[2]);
			accel1y.add(time, accel[3]+100);
			accel1z.add(time, accel[4]+200);
			accel2x.add(time, accel[5]);
			accel2y.add(time, accel[6]+100);
			accel2z.add(time, accel[7]+200);
		}
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(accel1x);
		dataset.addSeries(accel1y);
		dataset.addSeries(accel1z);
		dataset.addSeries(accel2x);
		dataset.addSeries(accel2y);
		dataset.addSeries(accel2z);
		
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

	public static void main(String[] args) throws IOException {
      CompareAccel chart = new CompareAccel(
         "Graph Test",
         "Graph");

      chart.pack();
      RefineryUtilities.centerFrameOnScreen(chart);
      chart.setVisible(true);
   }
}
