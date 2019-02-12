package Controlador;

import Extras.Grafica_de_Rendimiento;
import Extras.Operaciones_del_Vector;
import Extras.Vector;
import Funciones.Cuadratica;
import Funciones.Funcion_Rendimiento;
import Funciones.Rastrigin;
import com.google.common.collect.Range;
import com.google.common.primitives.Doubles;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;


public class Evolucion_MAIN {
    private static ArrayList<Funcion_Rendimiento> benchmarkFunctions;
    private static Funcion_Rendimiento funcionRendimiento;
    private static ArrayList<Vector> population;
    private static ArrayList<Double> solutions;
    private static Random random = new Random();
    private static LinkedHashMap<Integer, Double> lowestFit;

    private static int prevAmount = 0;
    private static Double bestValue = Double.MAX_VALUE;


    /*
     Inicializa la población con parámetros aleatorios dentro de los límites.
    */
    public static void initPopulation(Range<Double> bounds) {
        population = new ArrayList<Vector>(Variables_de_Control.TAMAÑO_DE_POBLACION);
        Vector newVector;

        while (population.size() < Variables_de_Control.TAMAÑO_DE_POBLACION) {
            newVector = new Vector(random, bounds);
            newVector.setFitness(funcionRendimiento.evaluate(newVector));
            population.add(newVector);

            if (population.get(population.size() - 1).getFitness() < lowestFit.get(prevAmount)) {
                prevAmount = funcionRendimiento.getNFC();
                lowestFit.put(prevAmount, population.get(population.size() - 1).getFitness());

            }
        }
    }

    /*
    Crea un entero aleatorio entre 0 y el tamaño de la población.
    */
    public static int getRandomIndex() {
        return random.nextInt(Variables_de_Control.TAMAÑO_DE_POBLACION - 1);
    }

    public static void main(String[] args) {
        Variables_de_Control.Seleccion();
        solutions = new ArrayList<Double>(Variables_de_Control.EVOLUCIONES);


        /* Una matriz de las funciones de referencia para evaluar */
        benchmarkFunctions = new ArrayList<Funcion_Rendimiento>();
        benchmarkFunctions.add(new Cuadratica());
        benchmarkFunctions.add(new Rastrigin());

        /* Aplicar el algoritmo de evolución diferencial a cada función de referencia.         */
        for (Funcion_Rendimiento benchmarkFunction : benchmarkFunctions) {
            /* Establecer la función de aptitud para la función de referencia actual             */
            funcionRendimiento = benchmarkFunction;

            /* Ejecutar el algoritmo de evolución varias veces por función */
            for (int runs = 0; runs < Variables_de_Control.EVOLUCIONES; ++runs) {
                int a;
                int b;
                int c;
                boolean validVector = false;
                Vector noisyVector = null;


                /* Restablecer la matriz de los mejores valores encontrados*/
                prevAmount = 0;
                lowestFit = new LinkedHashMap<Integer, Double>();
                lowestFit.put(prevAmount, Double.MAX_VALUE);

                initPopulation(funcionRendimiento.getBounds());

                /* Restablecer la función de fitness NFC cada vez   */
                funcionRendimiento.resetNFC();

                while (funcionRendimiento.getNFC() < Variables_de_Control.LLAMADAS_A_FUNCION_GENERACIONES) {
                    for (int i = 0; i < Variables_de_Control.TAMAÑO_DE_POBLACION; i++) {
                        /* Selecciona 3 Padres Mi != a != b != c*/
                        while (!validVector) {
                            do {
                                a = getRandomIndex();
                            } while (a == i);

                            do {
                                b = getRandomIndex();
                            } while (b == i || b == a);

                            do {
                                c = getRandomIndex();
                            } while (c == i || c == a || c == b);

                            //Coger vectores inválidos
                            try {
                                validVector = true;
                                noisyVector = Operaciones_del_Vector.mutation(population.get(c),
                                        population.get(b), population.get(a));
                            } catch (IllegalArgumentException e) {
                                validVector = false;
                            }
                        }

                        validVector = false;

                        Vector trialVector = Operaciones_del_Vector.crossover(population.get(i), noisyVector, random);

                        trialVector.setFitness(funcionRendimiento.evaluate(trialVector));

                        population.set(i, Operaciones_del_Vector.selection(population.get(i), trialVector));

                        /* Obtén el mejor valor físico encontrado hasta ahora */
                        if (population.get(i).getFitness() < lowestFit.get(prevAmount)) {
                            prevAmount = funcionRendimiento.getNFC();
                            bestValue = population.get(i).getFitness();
                            lowestFit.put(prevAmount, bestValue);
                        }
                    }
                }

                /*Guarde el mejor valor encontrado para toda la ejecución del algoritmo */
                solutions.add(bestValue);
            }


            /* Mostrar la media y la desviación estándar*/
            JOptionPane.showMessageDialog(null, "Resultados para : " + funcionRendimiento.getName());
            DescriptiveStatistics stats = new DescriptiveStatistics(Doubles.toArray(solutions));
            JOptionPane.showMessageDialog(null, "El Valor mas Optimo es : " + stats.getMean());
            /*  JOptionPane.showMessageDialog(null, "Desviacion Estandar --> " + stats.getStandardDeviation());*/

            /* Establezca el último valor (NFC) al mejor valor encontrado*/
            lowestFit.put(Variables_de_Control.LLAMADAS_A_FUNCION_GENERACIONES, bestValue);

            /* Trazar el mejor valor encontrado vs. NFC*/
            Grafica_de_Rendimiento.plot(lowestFit, funcionRendimiento.getName());

            /* Restablecer los resultados para la próxima función de referencia para evaluar*/
            solutions.clear();
            lowestFit.clear();
            bestValue = Double.MAX_VALUE;
        }
    }
}