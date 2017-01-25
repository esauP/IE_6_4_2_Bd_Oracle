package controlador;

import vista.*;

/**
 * Programa para gestionar talleres.
 *
 * @author Victor
 * @version 1.0
 */
public class main {

    public static void main(String[] args) {

        home vistaHome = new home();
        new controlador(vistaHome).iniciar();

    }
}
