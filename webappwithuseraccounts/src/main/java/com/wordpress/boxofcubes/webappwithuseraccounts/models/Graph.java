package com.wordpress.boxofcubes.webappwithuseraccounts.models;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtils;
//import org.jfree.chart.ChartUtilities;

public class Graph extends JFrame {
    private static final long serialVersionUID = 1L;

    public Graph(String name){
        super(name);
    }

    public void displayData(Dataset data, String title, String xAxis, String yAxis, String itemName){
        // Create the dataset from the Data object
        XYDataset theDataset = createDataset(data, itemName);
        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(title, xAxis, yAxis, theDataset);
        // Create plot and set background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(240,240,240));
        // Create panel and set default size
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(700, 500));
        setContentPane(panel);

        // Display the graph
        pack();
        setVisible(true);

        //save the graph
        try {
            FileOutputStream out = new FileOutputStream(title);
            //ChartUtilities.writeChartAsJPEG(out, chart, panel.getWidth(), panel.getHeight());
            ChartUtils.writeChartAsJPEG(out, chart, panel.getWidth(), panel.getHeight());
        }catch (IOException e) {
            System.out.println(e);
        }
    }

    private XYDataset createDataset(Dataset data, String itemName){
        XYSeries pairs = new XYSeries(itemName);
        for(int i=0; i<data.getX().length; i++){
          pairs.add(data.getX()[i], data.getY()[i]);
        }
        XYSeriesCollection theDataset = new XYSeriesCollection();
        theDataset.addSeries(pairs);
        return theDataset;
    }
}

