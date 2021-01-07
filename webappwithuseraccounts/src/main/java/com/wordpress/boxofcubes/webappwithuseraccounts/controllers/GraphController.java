package com.wordpress.boxofcubes.webappwithuseraccounts.controllers;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javassist.SerialVersionUID;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import javax.swing.JFrame;

import com.wordpress.boxofcubes.webappwithuseraccounts.data.DatasetRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xml.DatasetReader;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtils;
//import org.jfree.chart.ChartUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.ServletRegistrationBean;


@Controller
public class GraphController {

    @Autowired
    DatasetRepository datasetRepository;

    @GetMapping("data/graph")
    public String showGraph(@RequestParam(required=false) Integer user_id, Integer dataset_id,
    Model model, HttpServletRequest request, HttpServletResponse response){

        Optional<Dataset> data = datasetRepository.findById(dataset_id);

        
        if(data.isPresent()){
            // Create the dataset from the Data object
            XYDataset theDataset = createDataset(data.get());
            // Create chart
            JFreeChart chart = ChartFactory.createScatterPlot("My Dataset", "X", "Y", theDataset);
            // Create plot and set background color
            XYPlot plot = (XYPlot)chart.getPlot();
            plot.setBackgroundPaint(new Color(240,240,240));

            //save the graph
            try {


                String file = ServletUtilities.saveChartAsJPEG(chart, 700, 400, null, request.getSession());
                //String url = request.getContextPath()+"?graph="+file;
                //String url = request.getContextPath()+"/viewgraph="+file;
                String url = request.getContextPath()+"$graph="+file;


                /*ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes(); 
                String file = ServletUtilities.saveChartAsJPEG(chart, 700, 400, null, attr.getRequest().getSession(true));
                String url = attr.getRequest().getContextPath()+"?graph="+file;*/
                model.addAttribute("graphOfDataset", url);
                
            }catch (IOException e) {
                System.out.println("ERROR LOADING GRAPH");
            }
        }


        return "data/graph";
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