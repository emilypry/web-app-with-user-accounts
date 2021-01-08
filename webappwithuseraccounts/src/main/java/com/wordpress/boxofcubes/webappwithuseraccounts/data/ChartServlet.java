package com.wordpress.boxofcubes.webappwithuseraccounts.data;

import com.wordpress.boxofcubes.webappwithuseraccounts.models.Chart;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

@WebServlet
public class ChartServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<p>Hello World!</p>");
        /*response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();

        JFreeChart chart = Chart.getChart(data);
        ChartUtils.writeChartAsPNG(outputStream, chart, 700, 400);*/

    }
}
