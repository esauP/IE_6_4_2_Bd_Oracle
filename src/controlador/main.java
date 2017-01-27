package controlador;

import java.sql.SQLException;
import vista.*;

/**
 *
 * @author Esau Perez y Víctor Cárdenas
 */
public class main {

    public static void main(String[] args) throws SQLException {

        home vistaHome = new home();
        new controlador(vistaHome).iniciar();

    }
}
