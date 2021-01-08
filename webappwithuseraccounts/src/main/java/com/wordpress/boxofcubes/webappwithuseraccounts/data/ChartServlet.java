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

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(name="chartServlet")
public class ChartServlet extends HttpServlet{
    //@Autowired
    //DatasetRepository datasetRepository;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //response.setContentType("text/html");
        //PrintWriter out = response.getWriter();
        //out.println("<p>Hello World!</p>");


        //String dataset_id = request.getParameter("dataset_id");
        //Optional<Dataset> data = datasetRepository.findById(Integer.parseInt(dataset_id));

        /*response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<p>hey there partner</p>");*/

        //if(data.isPresent()){
            String myObjectId = request.getParameter("myObjectId");
            System.out.println("Servlet got myObjectId: "+myObjectId);
            System.out.println("Servlet got req: "+request.getSession().getAttribute(myObjectId));
            Dataset data = (Dataset)request.getSession().getAttribute(myObjectId);
            System.out.println("IS DATA NULL IN SERVLET: "+data);
            response.setContentType("image/png");
            OutputStream outputStream = response.getOutputStream();

            JFreeChart chart = Chart.getChart(data);
            ChartUtils.writeChartAsPNG(outputStream, chart, 700, 400);
        //}

    }
}
