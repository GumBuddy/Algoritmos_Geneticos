import com.google.common.collect.Range; 
import java.lang.Math; 
 
 
/**
 * Calculates the fitness of Rastrigin's function. 
 */ 
public class Rastrigin implements FitnessFunction 
{ 
 /* The bounds of Rastrigin's Function */ 
 private static final Range<Double> BOUNDS = Range.closed(-5.12, 5.12); 
  
 /* The name of the fitness function */ 
 private static final String NAME = "Rastrigin's Function"; 
  
 /* The number of function calls, includes the sum of all function calls
  * for all instances of the object 
  */ 
 private static Integer NFC = 0; 
 
 
 /**
  * Returns the bounds for Rastrigin's Function, which is, [-5.12, 5.12]. 
  *  
  * @return Range The bounds of Rastrigin's Function 
  */ 
 @Override 
 public Range<Double> getBounds() 
 { 
  return BOUNDS; 
 } 
 
 /**
  * Returns the name of the fitness function, this is useful for plotting 
  * the fitness results and saving the image as the name of the fitness function. 
  */ 
 @Override 
 public String getName() 
 { 
  return NAME; 
 } 
  
 /**
  * Returns the number of function calls (NFC) this is a count of 
  * the number of times the fitness function has been called. 
  *  
  * @return The number of function calls NFC 
  */ 
 @Override 
 public Integer getNFC() 
 { 
  return NFC; 
 } 
  
 /**
  * Resets the NFC count, this is useful when running multiple new  
  * instances of the DE algorithm and you wish to reset the NFC. 
  */ 
 @Override 
 public void resetNFC() 
 { 
  NFC = 0; 
 } 
  
 /**
  * Evaluates the fitness of Rastrigin's Function benchmark function. 
  *  
  * @return The fitness value 
  */ 
 @Override 
 public Double evaluate(Vector vector) 
 { 
  Double fitness = 10.0 * vector.size(); 
   
  /* Compute the fitness function for Rastrigin's Function:
   *  
   * f(X) = 10n + sigma (Xi^2 - 10cos(2*pi*Xi)) 
   *  
   */ 
  for (Double parameter : vector.get()) 
  { 
   /* Calculate the fitness */ 
   fitness += Math.pow(parameter, 2.0) - 10.0 * Math.cos(2 * Math.PI * parameter); 
  } 
   
  ++NFC; 
  return fitness; 
 } 
}