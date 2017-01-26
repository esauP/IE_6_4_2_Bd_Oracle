package modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Clase database que se encargará crear un objeto de conexción para las
 * llamadas a ORACLE
 *
 * @author Victor_ORACLE
 * @version 1.0
 */
public class database {
    private Connection conn = null;
//    

    /**
     * Constructor de clase
     */
    public database() {
        //Segunda parte
        try {
            //obtenemos el driver de para mysql
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //obtenemos la conexión
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","0045");
            System.out.println("conectazo");
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConexion() {
        return this.conn;
    }
    
    public static void main (String[] args){
    
        database d = new database ();
        d.getConexion();
        
    }
    
}