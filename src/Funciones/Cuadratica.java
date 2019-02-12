package Funciones;

import Extras.Vector;
import com.google.common.collect.Range;

public class Cuadratica implements Funcion_Rendimiento {
    private static final Range<Double> BOUNDS = Range.closed(-5.12, 5.12);


    private static final String NAME = "Cuadratica";


    private static Integer NFC = 0;

    @Override
    public Range<Double> getBounds() {
        return BOUNDS;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Integer getNFC() {
        return NFC;
    }

    @Override
    public void resetNFC() {
        NFC = 0;
    }

    @Override
    public Double evaluate(Vector vector) {
        Double fitness = 0.0;
        Double parameter = 0.0;

        for (int i = 0; i < vector.size(); ++i) {
            parameter = vector.get(i);
            fitness += (i + 1) * Math.pow(parameter, 2.0);
        }

        ++NFC;
        return fitness;
    }
}