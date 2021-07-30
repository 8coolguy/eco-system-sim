
package coolguy.maven;





import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;


import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;

import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

public class DynamicPopulationTracker extends ApplicationFrame implements ActionListener  {
	
	TimeSeries series;
	int lastValue;
	
	 
	public DynamicPopulationTracker(String title) {
		super(title);
//		this.series = new 
		init();
	}
	void init() {
		JFreeChart chart= createChart(null);
		//chart.
			
	}
	
	
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart returnChart =ChartFactory.createTimeSeriesChart("Population Chart", "MoveNum", "Population Count", dataset, false, false, false);
		XYPlot plot =returnChart.getXYPlot();
		
		
		plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.lightGray);
                
        ValueAxis xaxis = plot.getDomainAxis();
        xaxis.setAutoRange(true);
       
        //Domain axis would show data of 60 seconds for a time
        xaxis.setFixedAutoRange(60000.0);  // 60 seconds
        xaxis.setVerticalTickLabels(true);
       
        ValueAxis yaxis = plot.getRangeAxis();
        yaxis.setRange(0.0, 300.0);
       
		
		
		return returnChart;
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
	}
}

