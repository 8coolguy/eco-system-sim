
package coolguy.maven;





import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.TimeSeries;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class DynamicPopulationTracker extends ApplicationFrame  {
	

	XYDataset data;
	XYSeries population;
	XYSeries hydoDeaths;
	XYSeries ageDeaths;
	XYSeries heathDeaths;
	XYSeriesCollection collection;
	
	
	//needed for the stats
	public int hydration =0;
	public int age=0;
	public int health=0; 
	//private ones for this class
	int lastHydration =0;
	int lastAge=0;
	int lastHealth=0;
	
	public DynamicPopulationTracker(String title) {
		super(title);
//		this.series = new 
		init();
	}
	void init() {
		this.data =CreateDataSet();
		JFreeChart chart= createChart(this.data);
		
		//chart.
		ChartPanel chartPanel=new ChartPanel(chart);
		
		chartPanel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		chartPanel.setBackground(Color.black);
		setContentPane(chartPanel);
	}
	
	
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart returnChart =ChartFactory.createXYLineChart("Population Chart", "Move Num", "Rabbit Count ", dataset, PlotOrientation.VERTICAL, true, true, false);
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
        yaxis.setRange(0.0, 300.0);
       
		
		
		return returnChart;
	}
	private XYDataset CreateDataSet() {
		final XYSeries population =new XYSeries("Population Tracker");
		this.population =population;
		final XYSeries ageDeaths =new XYSeries("Death from old Age");
		this.ageDeaths =ageDeaths;
		final XYSeries hydrationDeaths =new XYSeries("Death from thirst");
		this.hydoDeaths =hydrationDeaths;
		final XYSeries hDeaths =new XYSeries("Death from enviorment");
		this.heathDeaths =hDeaths;
		
		
		
		final XYSeriesCollection collection =new XYSeriesCollection();
		collection.addSeries(population);
		collection.addSeries(hydrationDeaths);
		collection.addSeries(hDeaths);
		collection.addSeries(ageDeaths);
				
		return collection;
	}
	//i could make this hold less data if wanted
	public void updatePop(int moveNum,int rabbitCount) {
		try {
			
			//death stats
			this.collection =collection;
			this.lastHydration=this.hydration;
			this.hydration=0;
			this.lastHealth=this.health;
			this.health=0;
			this.lastAge=this.age;
			this.age=0;
			
			
			this.population.add((double)moveNum,(double)rabbitCount);
			this.ageDeaths.add((double)moveNum,(double)this.lastAge);
			this.hydoDeaths.add((double)moveNum,(double)this.lastHydration);
			this.heathDeaths.add((double)moveNum,(double)this.lastHealth);
			
			this.collection.addSeries(this.population);
			this.collection.addSeries(this.hydoDeaths);
			this.collection.addSeries(this.heathDeaths);
			this.collection.addSeries(this.ageDeaths);
		} catch(Exception i) {
			//infoSystem.out.println("Weird Error");
			}

		}
		
	
	

}

