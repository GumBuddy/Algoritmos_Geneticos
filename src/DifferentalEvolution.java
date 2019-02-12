import com.google.common.collect.Range;
import com.google.common.primitives.Doubles;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;


public class DifferentalEvolution
{
    private static ArrayList<FitnessFunction> benchmarkFunctions;
    private static FitnessFunction fitnessFunction;
    private static ArrayList<Vector> population;
    private static ArrayList<Double> solutions;
    private static Random random = new Random();
    private static LinkedHashMap<Integer, Double> lowestFit;

    private static int prevAmount = 0;
    private static Double bestValue = Double.MAX_VALUE;

    /**
     * Initializes the population with random parameters within the bounds.
     *
     * @param bounds The defined bounds of the fitnessFunction.
     */
    public static void initPopulation(Range<Double> bounds)
    {
        population = new ArrayList<Vector>(ControlVariables.POPULATION_SIZE);
        Vector newVector;

        while(population.size() < ControlVariables.POPULATION_SIZE)
        {
            newVector = new Vector(random, bounds);
            newVector.setFitness(fitnessFunction.evaluate(newVector));
            population.add(newVector);

            if(population.get(population.size()-1).getFitness() < lowestFit.get(prevAmount))
            {
                prevAmount = fitnessFunction.getNFC();
                lowestFit.put(prevAmount, population.get(population.size()-1).getFitness());

            }
        }
    }

    /**
     * Creates a random integer between 0 and the population size.
     * @return The random integer.
     */
    public static int getRandomIndex()
    {
        return random.nextInt(ControlVariables.POPULATION_SIZE-1);
    }

    public static void main (String[] args)
    {
        solutions = new ArrayList<Double>(ControlVariables.RUNS_PER_FUNCTION);

        /* An array of the benchmark functions to evalute */
        benchmarkFunctions = new ArrayList<FitnessFunction>();
//        benchmarkFunctions.add(new DeJong());
        benchmarkFunctions.add(new HyperEllipsoid());
     //   benchmarkFunctions.add(new Schwefel());
     //   benchmarkFunctions.add(new RosenbrocksValley());
        benchmarkFunctions.add(new Rastrigin());

        /* Apply the differential evolution algorithm to each benchmark function */
        for (FitnessFunction benchmarkFunction : benchmarkFunctions)
        {
            /* Set the fitness function for the current benchmark function */
            fitnessFunction = benchmarkFunction;

            /* Execute the differential evolution algorithm a number of times per function */
            for (int runs = 0; runs < ControlVariables.RUNS_PER_FUNCTION; ++runs)
            {
                int a;
                int b;
                int c;
                boolean validVector = false;
                Vector noisyVector = null;


                /* Reset the array of the best values found */
                prevAmount = 0;
                lowestFit = new LinkedHashMap<Integer, Double>();
                lowestFit.put(prevAmount, Double.MAX_VALUE);

                initPopulation(fitnessFunction.getBounds());

                /* Reset the fitness function NFC each time */
                fitnessFunction.resetNFC();

                while(fitnessFunction.getNFC() < ControlVariables.MAX_FUNCTION_CALLS)
                {
                    for (int i = 0; i < ControlVariables.POPULATION_SIZE; i++)
                    {
                        // Select 3 Mutually Exclusive Parents i != a != b != c
                        while(!validVector)
                        {
                            do
                            {
                                a = getRandomIndex();
                            } while(a == i);

                            do
                            {
                                b = getRandomIndex();
                            } while(b == i || b == a);

                            do
                            {
                                c = getRandomIndex();
                            } while(c == i || c == a || c == b);

                            // Catch invalid vectors
                            try
                            {
                                validVector = true;
                                noisyVector = VectorOperations.mutation(population.get(c),
                                        population.get(b), population.get(a));
                            }
                            catch (IllegalArgumentException e)
                            {
                                validVector = false;
                            }
                        }

                        validVector = false;

                        Vector trialVector = VectorOperations.crossover(population.get(i), noisyVector, random);

                        trialVector.setFitness(fitnessFunction.evaluate(trialVector));

                        population.set(i, VectorOperations.selection(population.get(i), trialVector));

                        /* Get the best fitness value found so far */
                        if(population.get(i).getFitness() < lowestFit.get(prevAmount))
                        {
                            prevAmount = fitnessFunction.getNFC();
                            bestValue =  population.get(i).getFitness();
                            lowestFit.put(prevAmount, bestValue);
                        }
                    }
                }

                /* save the best value found for the entire DE algorithm run */
                solutions.add(bestValue);
            }


            /* Display the mean and standard deviation */
            System.out.println("\nResults for " + fitnessFunction.getName());
            DescriptiveStatistics stats = new DescriptiveStatistics(Doubles.toArray(solutions));
            System.out.println("AVERAGE BEST FITNESS: " + stats.getMean());
            System.out.println("STANDARD DEVIATION:   " + stats.getStandardDeviation());

            /* Set the last value (NFC) to the best value found */
            lowestFit.put(ControlVariables.MAX_FUNCTION_CALLS, bestValue);

            /* Plot the best value found vs. NFC */
            PerformanceGraph.plot(lowestFit, fitnessFunction.getName());

            /* Reset the results for the next benchmark function to be evaluated */
            solutions.clear();
            lowestFit.clear();
            bestValue = Double.MAX_VALUE;
        }
    }
}