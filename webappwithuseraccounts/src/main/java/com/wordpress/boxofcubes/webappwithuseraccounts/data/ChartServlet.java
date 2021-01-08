package com.wordpress.boxofcubes.webappwithuseraccounts.data;

import com.wordpress.boxofcubes.webappwithuseraccounts.models.Chart;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(name="chartServlet")
public class ChartServlet extends HttpServlet{
    //@Autowired
    //DatasetRepository datasetRepository;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String dataObjectId = request.getParameter("dataObjectId");
        System.out.println("Servlet got dataObjectId: "+dataObjectId);
        System.out.println("Servlet got req: "+request.getSession().getAttribute(dataObjectId));
        Dataset data = (Dataset)request.getSession().getAttribute(dataObjectId);
        System.out.println("IS DATA NULL IN SERVLET: "+data);
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();

        //JFreeChart chart = Chart.getChart(data);
        //ChartUtils.writeChartAsPNG(outputStream, chart, 700, 400);

        JFreeChart chart = getChart(data);
        ChartUtils.writeChartAsPNG(outputStream, chart, 700, 400);


    }

    private JFreeChart getChart(Dataset data){
        XYSeries pairs = new XYSeries("Item");
        for(int i=0; i<data.getX().length; i++){
          pairs.add(data.getX()[i], data.getY()[i]);
        }
        XYSeriesCollection theDataset = new XYSeriesCollection();
        theDataset.addSeries(pairs);
        XYDataset theSet = theDataset;

        JFreeChart chart = ChartFactory.createScatterPlot("My Dataset", "X", "Y", theSet);
        return chart;   
    }
}
