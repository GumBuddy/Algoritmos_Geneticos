package Extras;
import java.util.ArrayList; 
 
 
/**
 * Provides basic statistical functionality such as calculating the mean 
 * and standard deviation. 
 */
public abstract class Estadisticas
{ 
 /**
  * Calculates the mean of an array of values 
  * @param values An array of values 
  * @return The mean of the values 
  */ 
 public static Double mean(ArrayList<Double> values) 
 { 
  Double sum = 0.0; 
   
  for (Double value : values) {
   sum += value;
  }

  return sum / values.size();
 } 
  
 /**
  * Calculates the standard deviation of an array of values 
  * @param values An array of values 
  * @return The standard deviation of the values 
  */ 
 public static Double std(ArrayList<Double> values) 
 { 
  Double sum = 0.0; 
  Double mean = 0.0; 
  Double variance = 0.0; 
   
  /* Calculate the mean, u */ 
  for (Double value : values) {
   sum += value;
  }
  mean = sum / values.size();
   
  /* Calculate the standard deviation */ 
  for (Double value : values) {
   variance += Math.pow((value - mean), 2.0);
  }
  return Math.sqrt(variance / values.size());
 } 
}