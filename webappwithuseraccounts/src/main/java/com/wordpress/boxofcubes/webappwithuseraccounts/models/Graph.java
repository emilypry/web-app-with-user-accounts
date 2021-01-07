package com.wordpress.boxofcubes.webappwithuseraccounts.models;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtils;
//import org.jfree.chart.ChartUtilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Graph extends JFrame {
    private static final long serialVersionUID = 1L;

    public Graph(String name){
        super(name);
    }

    public void getGraph(Dataset data){
        // Create the dataset from the Data object
        XYDataset theDataset = createDataset(data);
        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot("My Dataset", "X", "Y", theDataset);
        // Create plot and set background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(240,240,240));
        // Create panel and set default size
        ChartPanel panel = new ChartPanel(chart);
        //panel.setPreferredSize(new java.awt.Dimension(700, 500));
        setContentPane(panel);

        // Display the graph
        //pack();
        //setVisible(true);

        //save the graph
        try {
            FileOutputStream out = new FileOutputStream("newFile");
            ChartUtils.writeChartAsJPEG(out, chart, panel.getWidth(), panel.getHeight());
            String file = ServletUtilities.saveChartAsJPEG(chart, 700, 400, null, null);
        }catch (IOException e) {
            System.out.println(e);
        }
    }

    private XYDataset createDataset(Dataset data){
        XYSeries pairs = new XYSeries("Item");
        for(int i=0; i<data.getX().length; i++){
          pairs.add(data.getX()[i], data.getY()[i]);
        }
        XYSeriesCollection theDataset = new XYSeriesCollection();
        theDataset.addSeries(pairs);
        return theDataset;
    }
}

