package com.egil.pts.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.util.SortOrder;

public class GenericJFreeChart extends ApplicationFrame {

	private String title;
	private String xAxisLabel;
	private String yAxisLabel;

	public GenericJFreeChart(String title) {
		super(title);
		this.title = title;
	}

	public GenericJFreeChart(String title, String xAxisLabel, String yAxisLabel) {
		super(title);
		this.title = title;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
	}

	private static final long serialVersionUID = 1L;

	private CategoryDataset createDataset(List<ChartOptions> chartOptions) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (ChartOptions chartOpts : chartOptions) {
			if (chartOpts.getComparableRowKey() != null
					&& chartOpts.getComparableColumnKey() != null) {
				dataset.addValue(chartOpts.getValue(),
						chartOpts.getComparableRowKey(),
						chartOpts.getComparableColumnKey());
			}
		}

		return dataset;
	}

	private PieDataset createPieDataset(List<ChartOptions> chartOptions) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (ChartOptions chartOpts : chartOptions) {
			dataset.setValue(chartOpts.getComparableKey(), chartOpts.getValue());
		}
		return dataset;
	}

	private Date date(final int day, final int month, final int year) {

		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		final Date result = calendar.getTime();
		return result;

	}

	private XYDataset createXYDataset() {
		XYSeries firefox = new XYSeries("Firefox");
		firefox.add(1.0, 1.0);
		firefox.add(2.0, 4.0);
		firefox.add(3.0, 3.0);
		XYSeries chrome = new XYSeries("Chrome");
		chrome.add(1.0, 4.0);
		chrome.add(2.0, 5.0);
		chrome.add(3.0, 6.0);
		XYSeries iexplorer = new XYSeries("InternetExplorer");
		iexplorer.add(3.0, 4.0);
		iexplorer.add(4.0, 5.0);
		iexplorer.add(5.0, 4.0);
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(firefox);
		dataset.addSeries(chrome);
		dataset.addSeries(iexplorer);
		return dataset;
	}

	public IntervalCategoryDataset createDataset() {

		final TaskSeries s1 = new TaskSeries("Scheduled");
		s1.add(new Task("Write Proposal", new SimpleTimePeriod(date(1,
				Calendar.APRIL, 2001), date(5, Calendar.APRIL, 2001))));
		s1.add(new Task("Obtain Approval", new SimpleTimePeriod(date(9,
				Calendar.APRIL, 2001), date(9, Calendar.APRIL, 2001))));
		s1.add(new Task("Requirements Analysis", new SimpleTimePeriod(date(10,
				Calendar.APRIL, 2001), date(5, Calendar.MAY, 2001))));
		s1.add(new Task("Design Phase", new SimpleTimePeriod(date(6,
				Calendar.MAY, 2001), date(30, Calendar.MAY, 2001))));
		s1.add(new Task("Design Signoff", new SimpleTimePeriod(date(2,
				Calendar.JUNE, 2001), date(2, Calendar.JUNE, 2001))));
		s1.add(new Task("Alpha Implementation", new SimpleTimePeriod(date(3,
				Calendar.JUNE, 2001), date(31, Calendar.JULY, 2001))));
		s1.add(new Task("Design Review", new SimpleTimePeriod(date(1,
				Calendar.AUGUST, 2001), date(8, Calendar.AUGUST, 2001))));
		s1.add(new Task("Revised Design Signoff", new SimpleTimePeriod(date(10,
				Calendar.AUGUST, 2001), date(10, Calendar.AUGUST, 2001))));
		s1.add(new Task("Beta Implementation", new SimpleTimePeriod(date(12,
				Calendar.AUGUST, 2001), date(12, Calendar.SEPTEMBER, 2001))));
		s1.add(new Task("Testing", new SimpleTimePeriod(date(13,
				Calendar.SEPTEMBER, 2001), date(31, Calendar.OCTOBER, 2001))));
		s1.add(new Task("Final Implementation", new SimpleTimePeriod(date(1,
				Calendar.NOVEMBER, 2001), date(15, Calendar.NOVEMBER, 2001))));
		s1.add(new Task("Signoff", new SimpleTimePeriod(date(28,
				Calendar.NOVEMBER, 2001), date(30, Calendar.NOVEMBER, 2001))));

		final TaskSeries s2 = new TaskSeries("Actual");
		s2.add(new Task("Write Proposal", new SimpleTimePeriod(date(1,
				Calendar.APRIL, 2001), date(5, Calendar.APRIL, 2001))));
		s2.add(new Task("Obtain Approval", new SimpleTimePeriod(date(9,
				Calendar.APRIL, 2001), date(9, Calendar.APRIL, 2001))));
		s2.add(new Task("Requirements Analysis", new SimpleTimePeriod(date(10,
				Calendar.APRIL, 2001), date(15, Calendar.MAY, 2001))));
		s2.add(new Task("Design Phase", new SimpleTimePeriod(date(15,
				Calendar.MAY, 2001), date(17, Calendar.JUNE, 2001))));
		s2.add(new Task("Design Signoff", new SimpleTimePeriod(date(30,
				Calendar.JUNE, 2001), date(30, Calendar.JUNE, 2001))));
		s2.add(new Task("Alpha Implementation", new SimpleTimePeriod(date(1,
				Calendar.JULY, 2001), date(12, Calendar.SEPTEMBER, 2001))));
		s2.add(new Task("Design Review", new SimpleTimePeriod(date(12,
				Calendar.SEPTEMBER, 2001), date(22, Calendar.SEPTEMBER, 2001))));
		s2.add(new Task("Revised Design Signoff", new SimpleTimePeriod(date(25,
				Calendar.SEPTEMBER, 2001), date(27, Calendar.SEPTEMBER, 2001))));
		s2.add(new Task("Beta Implementation", new SimpleTimePeriod(date(27,
				Calendar.SEPTEMBER, 2001), date(30, Calendar.OCTOBER, 2001))));
		s2.add(new Task("Testing", new SimpleTimePeriod(date(31,
				Calendar.OCTOBER, 2001), date(17, Calendar.NOVEMBER, 2001))));
		s2.add(new Task("Final Implementation", new SimpleTimePeriod(date(18,
				Calendar.NOVEMBER, 2001), date(5, Calendar.DECEMBER, 2001))));
		s2.add(new Task("Signoff", new SimpleTimePeriod(date(10,
				Calendar.DECEMBER, 2001), date(11, Calendar.DECEMBER, 2001))));

		final TaskSeriesCollection collection = new TaskSeriesCollection();
		collection.add(s1);
		collection.add(s2);

		return collection;
	}

	public JFreeChart generatePieChart(String chartTitle, String xAxisLabel,
			String yAxisLabel, List<ChartOptions> chartOptions) {
		JFreeChart barChart = ChartFactory.createPieChart(chartTitle,
				createPieDataset(chartOptions), true, true, false);

		return barChart;

	}

	public JFreeChart generatePieChart3D(String chartTitle, String xAxisLabel,
			String yAxisLabel, List<ChartOptions> chartOptions) {
		JFreeChart barChart = ChartFactory.createPieChart3D(chartTitle,
				createPieDataset(chartOptions), true, true, false);

		return barChart;

	}

	public JFreeChart generateBarChart(String chartTitle, String xAxisLabel,
			String yAxisLabel, List<ChartOptions> chartOptions) {
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle,
				xAxisLabel, yAxisLabel, createDataset(chartOptions),
				PlotOrientation.VERTICAL, true, true, false);
		return barChart;

	}

	public JFreeChart generateLayeredBarChart(String chartTitle,
			String xAxisLabel, String yAxisLabel,
			List<ChartOptions> chartOptions) {
		JFreeChart chart = ChartFactory.createBarChart(chartTitle, xAxisLabel,
				yAxisLabel, createDataset(chartOptions),
				PlotOrientation.VERTICAL, true, true, false);

		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);

		// set the range axis to display integers only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		LayeredBarRenderer renderer = new LayeredBarRenderer();
		renderer.setDrawBarOutline(false);
		
		renderer.setSeriesBarWidth(0, 0.5);
		renderer.setSeriesBarWidth(1, 0.25);
        renderer.setItemMargin(0.01);
        
		plot.setRenderer(renderer);

		// for this renderer, we need to draw the first series last...
		plot.setRowRenderingOrder(SortOrder.DESCENDING);

		// set up gradient paints for series...
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f,
				0.0f, new Color(0, 0, 64));
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f,
				0.0f, new Color(0, 64, 0));
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f,
				0.0f, new Color(64, 0, 0));
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		return chart;

	}

	public JFreeChart generateLineChart(String chartTitle, String xAxisLabel,
			String yAxisLabel, List<ChartOptions> chartOptions) {
		JFreeChart barChart = ChartFactory.createLineChart(chartTitle,
				xAxisLabel, yAxisLabel, createDataset(chartOptions),
				PlotOrientation.VERTICAL, true, true, false);

		return barChart;

	}

	public JFreeChart generateGanttChart(String chartTitle, String xAxisLabel,
			String yAxisLabel, List<ChartOptions> chartOptions) {
		JFreeChart chart = ChartFactory.createGanttChart(chartTitle,
				xAxisLabel, yAxisLabel, createDataset(), true, true, false);

		return chart;
	}

	public JFreeChart generateXYLineChart(String chartTitle, String xAxisLabel,
			String yAxisLabel, List<ChartOptions> chartOptions) {
		JFreeChart barChart = ChartFactory.createXYLineChart(chartTitle,
				xAxisLabel, yAxisLabel, createXYDataset(),
				PlotOrientation.VERTICAL, true, true, false);

		return barChart;

	}

	public File createChart(String fileName, String typeOfChart,
			List<ChartOptions> chartOptions) throws Exception {
		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */
		String filePath = "/applications/tomcat/webapps/pts/charts/";
		BufferedImage bufferedImage = null;

		File chart = new File(filePath+fileName+".gif");
		JFreeChart jfreeChart = null;
		
		System.setProperty("java.awt.headless", "true");

		switch (typeOfChart) {
		case "barChart":
			System.out.println("Bar Chart");
			jfreeChart = generateBarChart(title, xAxisLabel, yAxisLabel,
					chartOptions);
			break;
		case "layeredBarChart":
			System.out.println("Layered Bar Chart");
			jfreeChart = generateLayeredBarChart(title, xAxisLabel, yAxisLabel,
					chartOptions);
			break;
		case "pieChart":
			System.out.println("Pie Chart");
			jfreeChart = generatePieChart(title, xAxisLabel, yAxisLabel,
					chartOptions);
			break;
		case "pieChart3D":
			System.out.println("Pir Chart 3D");
			jfreeChart = generatePieChart3D(title, xAxisLabel, yAxisLabel,
					chartOptions);
			break;
		case "lineChart":
			System.out.println("Line Chart");
			jfreeChart = generateLineChart(title, xAxisLabel, yAxisLabel,
					chartOptions);
			break;
		case "ganttChart":
			System.out.println("Gantt Chart");
			jfreeChart = generateGanttChart(title, xAxisLabel, yAxisLabel,
					chartOptions);
			break;
		case "xyLineChart":
			System.out.println("XY Line Chart");
			jfreeChart = generateXYLineChart(title, xAxisLabel, yAxisLabel,
					chartOptions);
			break;
		default:
			System.out.println("Pie Chart");
			jfreeChart = generatePieChart(title, xAxisLabel, yAxisLabel,
					chartOptions);
			break;
		}
		ChartPanel chartPanel = new ChartPanel(jfreeChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);

		//ChartUtilities.saveChartAsJPEG(chart, jfreeChart, width, height);
		bufferedImage = jfreeChart.createBufferedImage(width, height);
        ImageIO.write(bufferedImage, "gif", new FileOutputStream(chart));


		return chart;

	}
}
