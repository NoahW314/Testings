package org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.firstinspires.ftc.teamcode.teamcalamari.FilterStuff.Filters.Filter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

public abstract class FiltersTestTemplate<F extends Filter<?>> extends ApplicationFrame {
	
	public F filter;
	public ArrayList<Double[]> filteredAccelerations = new ArrayList<Double[]>();
	public double[] position;
	
	private static final long serialVersionUID = 2315131740376413437L;
	public FiltersTestTemplate(String title, String chartTitle, F filt, String filePath) throws IOException {
		super(title);
		filter = filt;
		JFreeChart chart = ChartFactory.createXYLineChart(
			chartTitle,
			"Time","Acceleration",
			createDataset(filePath),
			PlotOrientation.VERTICAL,
			true,true,false);
      
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1250, 900));
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setRangeGridlinePaint(Color.BLACK);
  
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.PINK);
		renderer.setSeriesPaint(1, Color.RED);
		renderer.setSeriesPaint(2, Color.GRAY);
		renderer.setSeriesPaint(3, Color.BLACK);
		renderer.setSeriesPaint(4, Color.CYAN);
		renderer.setSeriesPaint(5, Color.BLUE);
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
	
	private double[] calculatePosition(ArrayList<Double[]> filtAccel) {
		double[] pVelocity = {0,0,0};
		double[] velocity = {0,0,0};
		double[] position = {0,0,0};
		
		for(int i = 0; i < filtAccel.size()-1; i++) {
			for(int j = 0; j < 3; j++) {
				//is time i or i+1?
				double time = filtAccel.get(i+1)[0];
				velocity[j]+=(filtAccel.get(i)[j+1]+filtAccel.get(i+1)[j+1])/2*time;
				position[j]+=(pVelocity[j]+velocity[j])/2*time;
			}
			pVelocity = velocity;
		}
		
		return position;
	}
	
	
	protected abstract XYDataset createDataset(String filePath) throws IOException;


	protected ArrayList<Double[]> parseFile(String filePath) throws IOException {
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
