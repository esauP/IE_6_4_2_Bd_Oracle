package modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 * Clase modeloCliente que contiene los métodos con sus algoritmos o llamadas a
 * rutinas plsql
 *
 * @author Víctor. Proyecto taller.
 * @version 1.0
 */
public class Modelo extends database {

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEjemplo;

    /**
     * Constructor vacío de la clase
     */
    public Modelo() {
    }

    /**
     *
     * @param nombre nombre del cliente
     * @return devuelve si está o no relleno el campo del nombre del cliente
     */
    public boolean validarCliente(String nombre) {
        boolean exito = false;
        if ("".equals(nombre)) {
            exito = false;
        } else {
            exito = true;
        }
        return exito;
    }

    /**
     * Método para validar que se ha introducido texto en el campo
     *
     * @param telefono del cliente
     * @return devuelve si está o no relleno el campo de telefono del cliente
     */
    public boolean validarTelefono(String telefono) {
        boolean exito = false;
        if ("".equals(telefono)) {
            exito = false;
        } else {
            exito = true;
        }
        return exito;
    }

    /**
     * Obtiene registros de la tabla cliente y los devuelve en un
     * DefaultTableMode
     *
     * @return devuelve el modelo de tabla
     */
    public DefaultTableModel getTablaProyectos() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        Statement s;
        ResultSet rs = null;

        try {
            s = this.getConexion().createStatement();
            rs = s.executeQuery("SELECT P.CODIGOPR, P.NOMBRE, P.CIUDAD FROM TABLA_PROYECTOS P");
            ResultSetMetaData rsMd = rs.getMetaData();
            //La cantidad de columnas que tiene la consulta
            int cantidadColumnas = rsMd.getColumnCount();
            //Establecer como cabezeras el nombre de las colimnas
            for (int i = 1; i <= cantidadColumnas; i++) {
                tablemodel.addColumn(rsMd.getColumnLabel(i));
            }
            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                tablemodel.addRow(fila);
            }
            rs.close();
            //this.getConexion().close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return tablemodel;
    }

    public DefaultTableModel getTablaPiezas2() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] columnas = {"Código Proyecto", "Nombre", "Ciudad"};
        DefaultTableModel tabla = new DefaultTableModel();

        int i = 0;
        String selectSQL = "SELECT count(*) as c FROM TABLA_PROYECTOS";

        ps = this.getConexion().prepareStatement(selectSQL);
        rs = ps.executeQuery(selectSQL);
        rs.next();
        int filas = rs.getInt("c");
        System.out.println(filas);
        rs.close();
        ps.close();
        //LE FIJAMOS EL NUMERO DE FILAS OBTENIDAS
        Object[][] datos = new String[filas][4];

        //PROCEDEMOS A CONSTRUIR Y EJECUTAR LA CONSULTA QUE RELLENARA LA TABLA
        selectSQL = "SELECT P.CODIGOPR, P.NOMBRE, P.CIUDAD FROM TABLA_PROYECTOS P";
        System.out.println(selectSQL);
        ps = this.getConexion().prepareStatement(selectSQL);
        rs = ps.executeQuery(selectSQL);

        while (rs.next()) {

            datos[i][0] = rs.getString("CODIGOPR");
            datos[i][1] = rs.getString("NOMBRE");
            datos[i][2] = rs.getString("CIUDAD");

            i++;

        }

        tabla.setDataVector(datos, columnas);
        rs.close();
        ps.close();
        this.getConexion().close();
        return tabla;
    }

    /**
     * Carga de la tabla Piezas
     *
     * @return
     */
    public DefaultTableModel getTablaPiezas() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        Statement s;
        ResultSet rs = null;

        try {
            s = this.getConexion().createStatement();
            rs = s.executeQuery("Select PI.CODIGOPI, PI.NOMBRE, PI.PRECIO, PI.DESCRIPCION, PI.CANTIDAD, PI.PROVEEDOR.CODIGOPO, PI.PROVEEDOR.NOMBRE From TABLA_PROYECTOS P, Table(P.PIEZASPROYECTO) PI");
            ResultSetMetaData rsMd = rs.getMetaData();
            //La cantidad de columnas que tiene la consulta
            int cantidadColumnas = rsMd.getColumnCount();
            //Establecer como cabezeras el nombre de las colimnas
            for (int i = 1; i <= cantidadColumnas; i++) {
                tablemodel.addColumn(rsMd.getColumnLabel(i));
            }
            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                tablemodel.addRow(fila);
            }
            rs.close();
            // this.getConexion().close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return tablemodel;
    }

    /**
     * Método para obtener piezas relacionadas dada un codigo de proyecto
     *
     * @param aux
     * @return
     */
    public DefaultTableModel getTablaPiezasRelacionadas(String aux) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        Statement s;
        ResultSet rs = null;

        try {
            s = this.getConexion().createStatement();
            rs = s.executeQuery("Select PI.CODIGOPI, PI.NOMBRE, PI.PRECIO, PI.DESCRIPCION, PI.CANTIDAD, PI.PROVEEDOR.CODIGOPO, PI.PROVEEDOR.NOMBRE From TABLA_PROYECTOS P, Table(P.PIEZASPROYECTO) PI WHERE P.CODIGOPR='" + aux + "'");
            ResultSetMetaData rsMd = rs.getMetaData();
            //La cantidad de columnas que tiene la consulta
            int cantidadColumnas = rsMd.getColumnCount();
            //Establecer como cabezeras el nombre de las colimnas
            for (int i = 1; i <= cantidadColumnas; i++) {
                tablemodel.addColumn(rsMd.getColumnLabel(i));
            }
            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                tablemodel.addRow(fila);
            }
            rs.close();
            // this.getConexion().close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return tablemodel;
    }

    public boolean InsertaProyecto(String text, String text0, String text1) {
        try {
            //Llamada a la funcion
            String sql = "{call ADD_PROYECTO (?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, text);
            cStmt.setString(2, text0);
            cStmt.setString(3, text1);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean ActualizaProyecto(String text, String text0, String text1) {
          try {
            //Llamada a la funcion
            String sql = "{call UPDATE_PROYECTO (?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, text);
            cStmt.setString(2, text0);
            cStmt.setString(3, text1);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean BorraProyecto(String text) {
        try {
            //Llamada a la funcion
            String sql = "{call DELETE_PROYECTO (?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, text);
            //ejecuto la funcion
            cStmt.execute();          
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean InsertaPieza(String codP, String nomP, String descP, String precP, String cantP, String codPr, String nomPr, String apePr, String direcPr) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean ActualizaPieza(String codP, String nomP, String descP, String precP, String cantP, String codPr, String nomPr, String apePr, String direcPr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean BorrarPieza(String codP, String codProy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
