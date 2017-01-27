package modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Esau Perez y Víctor Cárdenas
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

    /**
     *
     * @return DEFAULTTABLEMODEL
     * @throws SQLException
     */
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
     * @return DEFAULTTABLEMODEL
     */
    public DefaultTableModel getTablaPiezas() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        Statement s;
        ResultSet rs = null;

        try {
            s = this.getConexion().createStatement();
            rs = s.executeQuery("Select PI.CODIGOPI, PI.NOMBRE, PI.PRECIO, PI.DESCRIPCION, PI.CANTIDAD, PI.PROVEEDOR.CODIGOPO, PI.PROVEEDOR.NOMBRE, PI.PROVEEDOR.APELLIDOS, PI.PROVEEDOR.DIREC From TABLADEPIEZAS PI");
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
     * @return DEFAULTTABLEMODEL
     */
    public DefaultTableModel getTablaPiezasRelacionadas(String aux) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        Statement s;
        ResultSet rs = null;

        try {
            s = this.getConexion().createStatement();
            rs = s.executeQuery("Select PI.CODIGOPI, PI.NOMBRE, PI.PRECIO, PI.DESCRIPCION, PI.CANTIDAD, PI.PROVEEDOR.CODIGOPO, PI.PROVEEDOR.NOMBRE, PI.PROVEEDOR.APELLIDOS, PI.PROVEEDOR.DIREC From TABLA_PROYECTOS P, Table(P.PIEZASPROYECTO) PI WHERE P.CODIGOPR='" + aux + "'");
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
     * METODO USADO PARA INSERTAR PROYECTOS NUEVOS
     *
     * @param codProy CODIGO PROYECTO
     * @param nomProy NOMBRE PROYECTO
     * @param ciudadProy CIUDAD PROYECTO
     * @return BOOLEAN
     */
    public boolean InsertaProyecto(String codProy, String nomProy, String ciudadProy) {
        try {
            //Llamada a la funcion
            String sql = "{call ADD_PROYECTO (?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, codProy);
            cStmt.setString(2, nomProy);
            cStmt.setString(3, ciudadProy);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * METOD PARA ACTUALIZAR PROYECTOS EXISTENTES
     *
     * @param codProy
     * @param nombProy
     * @param ciudadProy
     * @return BOOLEAN
     */
    public boolean ActualizaProyecto(String codProy, String nombProy, String ciudadProy) {
        try {
            //Llamada a la funcion
            String sql = "{call UPDATE_PROYECTO (?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, codProy);
            cStmt.setString(2, nombProy);
            cStmt.setString(3, ciudadProy);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * METODO PARA ELIMINAR UN PROYECTO EXISTENTE
     *
     * @param codProy
     * @return BOOLEAN
     */
    public boolean BorraProyecto(String codProy) {
        try {
            //Llamada a la funcion
            String sql = "{call DELETE_PROYECTO (?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, codProy);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * METODO QUE USAMOS PARA INSERTAR UNA NUEVA PIEZA
     *
     * @param codP CODIGO PIEZA
     * @param nomP NOMBRE PIEZA
     * @param precP PRECIO PIEZA
     * @param descP DESCRIPCION PIEZA
     * @param cantP CANTIDAD PIEZA
     * @param codPr CODIGO PROVEEDOR
     * @param nomPr NOMBRE PROVEEDOR
     * @param apePr APELLIDOS PROVEEDOR
     * @param direcPr DIRECCION PROVEEDOR
     * @return BOOLEAN
     */
    public boolean InsertaPieza(String codP, String nomP, double precP, String descP, double cantP, String codPr, String nomPr, String apePr, String direcPr) {

        try {
            //Llamada a la funcion
            String sql = "{call ADD_PIEZA (?,?,?,?,?,?,?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, codP);
            cStmt.setString(2, nomP);
            cStmt.setDouble(3, precP);
            cStmt.setString(4, descP);
            cStmt.setDouble(5, cantP);
            cStmt.setString(6, codPr);
            cStmt.setString(7, nomPr);
            cStmt.setString(8, apePr);
            cStmt.setString(9, direcPr);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * METODO PARA INSERTAR UNA PIEZA EN UN PROYECTO
     *
     * @param codPi CODIGO PIEZA
     * @param codProy CODIGO PROYECTO
     * @return BOOLEAN
     */
    public boolean InsertaPiezaProyecto(String codPi, String codProy) {
        try {
            //Llamada a la funcion
            String sql = "{call ADD_PIEZA_PROYECTO (?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, codPi);
            cStmt.setString(2, codProy);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * METODO PARA ACTUALIZAR UNA NUEVA PIEZA
     *
     * @param codP CODIGO PIEZA
     * @param nomP NOMBRE PIEZA
     * @param descP DESCRIPCION PIEZA
     * @param precP PRECIO PIEZA
     * @param cantP CANTIDAD PIEZA
     * @param nomPr NOMBRE PROVEEDOR
     * @param apePr APELLIDOS PROVEEDOR
     * @param direcPr DIRECCION PROVEEDOR
     * @param codPro CODIGO PROYECTO
     * @return BOOLEAN
     */
    public boolean ActualizarPiezaProyecto(String codPro, String nomP, double precP, String descP, double cantP, String nomPr, String apePr, String direcPr, String codP) {
        Statement s;

        try {
            //Llamada a la funcion
            String sql = "{call UPDATE_PIEZA_PROYECTO (?,?,?,?,?,?,?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, codPro);
            cStmt.setString(2, nomP);
            cStmt.setDouble(3, precP);
            cStmt.setString(4, descP);
            cStmt.setDouble(5, cantP);
            cStmt.setString(6, nomPr);
            cStmt.setString(7, apePr);
            cStmt.setString(8, direcPr);
            cStmt.setString(9, codP);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar un registro de la tabla TABLADEPIEZA
     *
     * @param codpieza Cód. de la pieza
     * @param nomPieza nombre de la pieza
     * @param precioPieza precio de la pieza en double
     * @param descriPieza descripción de la pieza
     * @param cantdPieza cantidad de piezas tipo double
     * @param nombreProvee nombre del proveedor
     * @param apellProvee apellidos del proveedor
     * @param direcProveedor dirección del proveedor
     * @return boolean en función de si la operación se realizó con o sin
     * errores.
     */
    public boolean ActualizaPieza(String codpieza, String nomPieza, double precioPieza, String descriPieza, double cantdPieza, String nombreProvee, String apellProvee, String direcProveedor) {
        try {
            //Llamada a la funcion
            String sql = "{call UPDATE_PIEZA (?,?,?,?,?,?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, codpieza);
            cStmt.setString(2, nomPieza);
            cStmt.setDouble(3, precioPieza);
            cStmt.setString(4, descriPieza);
            cStmt.setDouble(5, cantdPieza);
            cStmt.setString(6, nombreProvee);
            cStmt.setString(7, apellProvee);
            cStmt.setString(8, direcProveedor);
            //ejecuto la funcion
            cStmt.execute();
            this.getConexion().commit();
            return true;
        } catch (SQLException e) {
            e.getMessage();
            return false;
        }
    }

    /**
     * METODO PARA BORRAR UNA PIEZA ASOCIADA A UN PROYECTO
     *
     * @param codP CODIGO DE PIEZA
     * @param codProy CODIGO DE PROYECTO
     * @return BOOLEAN
     */
    public boolean BorrarPiezaProyecto(String codP, String codProy) {
        try {
            //Llamada a la funcion
            String sql = "{call DELETE_PIEZA_PROYECTO (?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, codProy);
            cStmt.setString(2, codP);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean BorrarPieza(String codPieza) {
        try {
            //Llamada a la funcion
            String sql = "{call DELETE_PIEZA (?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, codPieza);
            //ejecuto la funcion
            cStmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
