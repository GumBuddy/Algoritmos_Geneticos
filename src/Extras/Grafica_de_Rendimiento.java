package Extras;

import Controlador.Variables_de_Control;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.LinkedHashMap;

/**
 * Plots the performance graph of the best fitness value so far versus the 
 * number of function calls. 
 */
public abstract class Grafica_de_Rendimiento
{
    /**
     * Plots the performance graph of the best fitness value so far versus the
     * number of function calls (NFC).
     *
     * @param bestFitness A linked hashmap mapping the NFC to the best fitness value
     * found so far.
     * @param fitnessFunction The name of the fitness function, used for the title and the
     * name of the file that is saved, e.g. "De Jong".
     */
    public static void plot(LinkedHashMap<Integer, Double> bestFitness, String fitnessFunction)
    {
        /* Create an XYSeries plot */
        XYSeries series = new XYSeries("El Mejor Valor ");

        /* Add the NFC and best fitness value data to the series */
        for (Integer NFC : bestFitness.keySet())
        {
            /* Jfreechart crashes if double values are too large! */
            if (bestFitness.get(NFC) <= 10E12)
            {
                series.add(NFC.doubleValue(), bestFitness.get(NFC).doubleValue());
            }
        }

        /* Add the x,y series data to the dataset */
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        /* Plot the data as an X,Y line chart */
        JFreeChart chart = ChartFactory.createXYLineChart(
                "El mejor valor ",
                "NFC(Numero de llamadas de funcion)",
                "El valor mas Apto",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        /* Configure the chart settings such as anti-aliasing, background colour */
        chart.setAntiAlias(true);

        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.black);
        plot.setDomainGridlinePaint(Color.black);


        /* Set the domain range from 0 to NFC */
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(0.0, Variables_de_Control.LLAMADAS_A_FUNCION_GENERACIONES.doubleValue());

        /* Logarithmic range axis */
        plot.setRangeAxis(new LogAxis());


        /* Set the thickness and colour of the lines */
        XYItemRenderer renderer = plot.getRenderer();
        BasicStroke thickLine = new BasicStroke(3.0f);
        renderer.setSeriesStroke(0, thickLine);
        renderer.setPaint(Color.BLACK);


        /* Display the plot in a JFrame */
        ChartFrame frame = new ChartFrame(fitnessFunction + " Valor mas Optimo", chart);
        frame.setVisible(true);
        frame.setSize(1000, 600);
   
  /* Save the plot as an image named after fitness function
  try 
  { 
   ChartUtilities.saveChartAsJPEG(new File("plots/" + fitnessFunction + ".jpg"), chart, 1600, 900); 
  } 
  catch (IOException e) 
  { 
   e.printStackTrace(); 
  }*/
    }
}