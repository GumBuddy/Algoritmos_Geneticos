package Funciones;

import Extras.Vector;
import com.google.common.collect.Range;

public interface Funcion_Rendimiento {
    Range<Double> getBounds();

    String getName();

    Integer getNFC();

    void resetNFC();

    Double evaluate(Vector vector);
}