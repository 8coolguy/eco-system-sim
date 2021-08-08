package coolguy.maven;

import java.awt.Color;
import java.awt.Toolkit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DynamicStatsTracker extends ApplicationFrame {

	
	XYDataset data;
	private XYSeries avgFert;
	private XYSeries avgColor;
	private XYSeries mvf;
	private XYSeries avgAge;
	private XYSeries avgSize;
	private XYSeries avgSpeed;

	public DynamicStatsTracker(String title) {
		super(title);
		init();
	}
	void init() {
		this.data =createDataSet();
		JFreeChart chart = createChart(this.data);
		
		ChartPanel panel = new ChartPanel(chart);
		
		panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		panel.setBackground(Color.black);
		setContentPane(panel);
	}
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart returnChart =ChartFactory.createXYLineChart("Statistics Chart", "Move Num", "Stat ", dataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot =returnChart.getXYPlot();
		
		
		plot.setBackgroundPaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.lightGray);
                
        ValueAxis xaxis = plot.getDomainAxis();
        xaxis.setAutoRange(true);
       
        //Domain axis would show data of 60 seconds for a time
         // 60 seconds
        
       
        ValueAxis yaxis = plot.getRangeAxis();
        yaxis.setRange(0.0, 3.0);
       
		
		
		return returnChart;
	}
	XYDataset createDataSet() {
		this.avgFert =new XYSeries("Average Fertility(0-1)");
		this.avgColor=new XYSeries("Average Color(0-3)");
		this.mvf =new XYSeries("Male to Female Ratio");
		this.avgAge =new XYSeries("Average Age");
		this.avgSize =new XYSeries("Average Size");
		this.avgSpeed =new XYSeries("Average Speed");
		
		
		final XYSeriesCollection collection =new XYSeriesCollection();
		collection.addSeries(avgFert);
		collection.addSeries(avgColor);
		collection.addSeries(mvf);
		collection.addSeries(avgAge);
		collection.addSeries(avgSize);
		collection.addSeries(avgSpeed);
		return collection;
		
	}
	public void updateStats(int moveNum,double avgFert,double avgColor,double mvf,double avgAge, double avgSize,double avgSpeed) {
		try {
			double i =(double)moveNum;
			this.avgFert.add(i,avgFert);
			this.avgColor.add(i,avgColor);
			this.mvf.add(i,mvf);
			//this.avgAge.add(i,avgAge);
			this.avgSize.add(i,avgSize);
			this.avgSpeed.add(i,avgSpeed);
			
			
		}
		catch(Exception i) {
			
		}
	}

}
