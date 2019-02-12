package Extras;

import Controlador.Variables_de_Control;
import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.Random;
 
 
public class Vector 
{ 
 private ArrayList<Double> input; 
 private Double fitness;
    private Range<Double> bounds;

    public Vector(Random random, Range<Double> bounds) {
        this.input = new ArrayList<Double>(Variables_de_Control.DIMENSIONES);
   
  // Set to infinity 
        this.fitness = Double.MAX_VALUE;

        this.bounds = bounds;

        while (input.size() < Variables_de_Control.DIMENSIONES)
  { 
   this.input.add( new Double(((this.bounds.upperEndpoint() - this.bounds.lowerEndpoint()) *
           random.nextDouble()) + this.bounds.lowerEndpoint()));
  }
    }

    public Vector(Range<Double> bounds) {
        this.input = new ArrayList<Double>(Variables_de_Control.DIMENSIONES);
   
  // Set to infinity 
        this.fitness = Double.MAX_VALUE;

        this.bounds = bounds;


    }

    public Double getFitness() {
        return this.fitness;
    }

    public void setFitness(Double fitness) throws IllegalArgumentException {
        this.fitness = fitness;
    }

    public ArrayList<Double> get() throws IndexOutOfBoundsException {
        return this.input;
    }

    public Double get(int index) throws IndexOutOfBoundsException {
        return this.input.get(index);
    }

    public void setParameter(int index, Double parameter) throws IllegalArgumentException {
        this.input.set(index, parameter);
    }

    public int size() {
        return this.input.size();
    }

    public Range<Double> getBounds() {
        return bounds;
    }

    public void addParameter(Double parameter) throws IllegalArgumentException
 { 
  this.input.add(parameter); 
 } 
}