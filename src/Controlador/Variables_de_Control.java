package Controlador;

import javax.swing.*;

public abstract class Variables_de_Control {

    public static Integer DIMENSIONES = null;
    public static Integer TAMAÑO_DE_POBLACION = null;
    public static Double PROBABILIDAD_DE_MUTACION = null;
    public static Double PROBABILIDAD_DE_CRUZA = null;
    public static Integer LLAMADAS_A_FUNCION_GENERACIONES = null;
    public static Integer EVOLUCIONES = null;


    static void Seleccion() {
        String Desicion = JOptionPane.showInputDialog("Para ingresar valores personalizados ingresa 1  \n" +
                "Para ingresar valores por defecto ingresa 2  ");
        if (Desicion.equals("1")) {
            DIMENSIONES = Integer.parseInt(JOptionPane.showInputDialog("Cuantas dimensiones vas ingresar?"));
            TAMAÑO_DE_POBLACION = Integer.parseInt(JOptionPane.showInputDialog("Que tamaño de poblacion vas ingresar?"));
            PROBABILIDAD_DE_MUTACION = Double.valueOf(JOptionPane.showInputDialog("Que probabilidad de mutacion vas ingresar?"));
            PROBABILIDAD_DE_CRUZA = Double.valueOf(JOptionPane.showInputDialog("Que probabilidad de cruza vas ingresar?"));
            LLAMADAS_A_FUNCION_GENERACIONES = Integer.parseInt(JOptionPane.showInputDialog("Cuantas Generaciones Deseas ingresar?"));
            EVOLUCIONES = Integer.parseInt(JOptionPane.showInputDialog("Cuantas veces puede evolucionar un individuo?"));
        }
        if (Desicion.equals("2")) {
            DIMENSIONES = 30;
            TAMAÑO_DE_POBLACION = 2 * DIMENSIONES;
            PROBABILIDAD_DE_MUTACION = 0.5;
            PROBABILIDAD_DE_CRUZA = 0.9;
            LLAMADAS_A_FUNCION_GENERACIONES = 1000 * DIMENSIONES;
            EVOLUCIONES = 50;
        } else {
            JOptionPane.showMessageDialog(null, "Ingresa una opcion valida");
            Seleccion();
        }
    }
}