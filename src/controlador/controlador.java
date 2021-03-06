package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.Modelo;
import vista.*;

/**
 *
 * @author Esau Perez y Víctor Cárdenas
 */
public class controlador implements ActionListener, MouseListener {

    // Intanciamos todas las vistas
    home vistaHome;

    //instanciamos el modelo
    Modelo mo = new Modelo();

    /**
     * Constructor de la clase
     *
     * @param vistaHome es la vista del Home
     */
    public controlador(home vistaHome) {
        this.vistaHome = vistaHome;

    }

    /**
     * Inicia el skin y las diferentes variables que se utilizan
     */
    public void iniciar() throws SQLException {
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vistaHome);
            vistaHome.setVisible(true);

        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        // Botones del Menú Home
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaHome.BtnAniadeProyecto.addActionListener(this);
        this.vistaHome.BtnActualizaProyecto.addActionListener(this);
        this.vistaHome.BtnBorraProyecto.addActionListener(this);
        this.vistaHome.BtnLimpiarTxtProyecto.addActionListener(this);

        this.vistaHome.BtnAniadePieza.addActionListener(this);
        this.vistaHome.BtnActualizaPieza.addActionListener(this);
        this.vistaHome.BtnBorraPieza.addActionListener(this);
        this.vistaHome.BtnLimpiaTxtPieza.addActionListener(this);
        this.vistaHome.BtnCargarPiezasDelProyecto.addActionListener(this);

        this.vistaHome.btnSalir.addActionListener(this);

        this.vistaHome.TableProyectos.addMouseListener(this);
        this.vistaHome.TableProyectos.setModel(mo.getTablaProyectos());
        this.vistaHome.TableProyectos.setDefaultRenderer(Object.class, new MiRender());
        this.vistaHome.TableProyectos.getTableHeader().setReorderingAllowed(false);
        ((DefaultTableCellRenderer) this.vistaHome.TableProyectos.getDefaultRenderer(String.c‌lass)).setHorizontal‌Alignment(JLabel.CENTER);//centramos el texto dentro de las celdas de la tabla
        this.vistaHome.TableProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//le damos la propiedad a la tabla para que solo se pueda seleccionar un registro
                
        this.vistaHome.TablePiezas.addMouseListener(this);
        this.vistaHome.TablePiezas.setModel(mo.getTablaPiezas());
        this.vistaHome.TablePiezas.setDefaultRenderer(Object.class, new MiRender());
        this.vistaHome.TablePiezas.getTableHeader().setReorderingAllowed(false);
        ((DefaultTableCellRenderer) this.vistaHome.TablePiezas.getDefaultRenderer(String.c‌lass)).setHorizontal‌Alignment(JLabel.CENTER);//centramos el texto dentro de las celdas de la tabla
        this.vistaHome.TablePiezas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//le damos la propiedad a la tabla para que solo se pueda seleccionar un registro

    }

    //Control de eventos de los controles que tienen definido un "ActionCommand"
    @Override
    public void actionPerformed(ActionEvent e) {

        /*----------------------------------------------------------------------------*/
        //Proyectos
        //ADD_PROYECTO
        if (e.getSource() == this.vistaHome.BtnAniadeProyecto) {

            if (mo.InsertaProyecto(this.vistaHome.TxtCodigoProy.getText(), this.vistaHome.TxtNombreProy.getText(), this.vistaHome.TxtCiudadProy.getText()) == true) {
                JOptionPane.showMessageDialog(this.vistaHome, "Exito: Proyecto añadido con éxito.");
                this.refrescar();
            } else {
                JOptionPane.showMessageDialog(this.vistaHome, "Error: algo salió mal");
            }
        }
        //UPDATE_PROYECTO
        if (e.getSource() == this.vistaHome.BtnActualizaProyecto) {
            if (mo.ActualizaProyecto(this.vistaHome.TxtCodigoProy.getText(), this.vistaHome.TxtNombreProy.getText(), this.vistaHome.TxtCiudadProy.getText()) == true) {
                JOptionPane.showMessageDialog(this.vistaHome, "Exito: Proyecto modificado con éxito.");
                this.refrescar();
            } else {
                JOptionPane.showMessageDialog(this.vistaHome, "Error: algo salió mal");
            }
        }
        //DELETE_PROYECTO
        if (e.getSource() == this.vistaHome.BtnBorraProyecto) {
            if (mo.BorraProyecto(this.vistaHome.TxtCodigoProy.getText()) == true) {
                JOptionPane.showMessageDialog(this.vistaHome, "Exito: Proyecto borrado con éxito.");
                this.refrescar();
            } else {
                JOptionPane.showMessageDialog(this.vistaHome, "Error: algo salió mal");
            }
        }
        //LIMPIAR DATOS PROYECTOS
        if (e.getSource() == this.vistaHome.BtnLimpiarTxtProyecto) {
            this.vistaHome.TxtCodigoProy.setText("");
            this.vistaHome.TxtNombreProy.setText("");
            this.vistaHome.TxtCiudadProy.setText("");
            this.refrescar();
        }
        /*----------------------------------------------------------------------------*/
        //Piezas
        if (e.getSource() == this.vistaHome.BtnAniadePieza) {
            String codP, nomP, descP, codPr, nomPr, apePr, direcPr, codProy;
            double precP, cantP;
            codProy = this.vistaHome.TxtCodigoProy.getText();
            codP = this.vistaHome.TxtCodigoPieza.getText();
            nomP = this.vistaHome.TxtNombrePieza.getText();
            descP = this.vistaHome.TxtDescripcionPi.getText();
            precP = Double.parseDouble(String.valueOf(this.vistaHome.SpinnerPrecioPi.getValue()));
            cantP = Double.parseDouble(String.valueOf(this.vistaHome.SpinnerCantidadPi.getValue()));
            codPr = this.vistaHome.TxtCodProveedor.getText();
            nomPr = this.vistaHome.TxtNombreProveedor.getText();
            apePr = this.vistaHome.TxtApellidosProveedor.getText();
            direcPr = this.vistaHome.TxtDirecProveedor.getText();

            if (this.vistaHome.TxtCodigoProy.getText().equals("")) {
                if (mo.InsertaPieza(codP, nomP, precP, descP, cantP, codPr, nomPr, apePr, direcPr)) {
                    JOptionPane.showMessageDialog(this.vistaHome, "Exito: Pieza añadida con éxito.");
                    this.refrescar();
                } else {
                    JOptionPane.showMessageDialog(this.vistaHome, "Error: algo salió mal");
                }

            } else if (!this.vistaHome.TxtCodigoProy.getText().equals("")) {

                if (mo.InsertaPiezaProyecto(codP, codProy) == true) {
                    JOptionPane.showMessageDialog(this.vistaHome, "Exito: Pieza del proyeto: " + codProy + "añadida con éxito.");
                    this.vistaHome.TablePiezas.setModel(mo.getTablaPiezasRelacionadas(this.vistaHome.TxtCodigoProy.getText()));
                } else {
                    JOptionPane.showMessageDialog(this.vistaHome, "Error: algo salió mal");
                }
            }

        }
        //ACTUALIZAR PIEZA de TABLADEPIEZAS DE LA TABLA_PIEZAS DE PROYECTOS
        if (e.getSource() == this.vistaHome.BtnActualizaPieza) {
            String codProy, codP, nomP, descP, codPr, nomPr, apePr, direcPr;
            double precP, cantP;
            codProy = this.vistaHome.TxtCodigoProy.getText();
            codP = this.vistaHome.TxtCodigoPieza.getText();
            nomP = this.vistaHome.TxtNombrePieza.getText();
            descP = this.vistaHome.TxtDescripcionPi.getText();
            precP = Double.parseDouble(String.valueOf(this.vistaHome.SpinnerPrecioPi.getValue()));
            cantP = Double.parseDouble(String.valueOf(this.vistaHome.SpinnerCantidadPi.getValue()));
            nomPr = this.vistaHome.TxtNombreProveedor.getText();
            apePr = this.vistaHome.TxtApellidosProveedor.getText();
            direcPr = this.vistaHome.TxtDirecProveedor.getText();

            if (this.vistaHome.TxtCodigoProy.getText().equals("")) {

                if (mo.ActualizaPieza(codP, nomP, precP, descP, cantP, nomPr, apePr, direcPr) == true) {
                    JOptionPane.showMessageDialog(this.vistaHome, "Exito: Pieza actualizada con éxito.");
                    this.refrescar();
                } else {
                    JOptionPane.showMessageDialog(this.vistaHome, "Error: algo salió mal");
                }

            } else if (!this.vistaHome.TxtCodigoProy.getText().equals("")) {

                if (mo.ActualizarPiezaProyecto(codProy, nomP, precP, descP, cantP, nomPr, apePr, direcPr, codP)) {
                    JOptionPane.showMessageDialog(this.vistaHome, "Exito: Pieza del proyeto: " + codProy + " actualizada con éxito.");
                    this.vistaHome.TablePiezas.setModel(mo.getTablaPiezasRelacionadas(this.vistaHome.TxtCodigoProy.getText()));
                } else {
                    JOptionPane.showMessageDialog(this.vistaHome, "Error: algo salió mal");
                }

            }

        }
        //DELETE PIEZAS DE TABLADEPIEZAS O DE UN PROYECTO
        if (e.getSource() == this.vistaHome.BtnBorraPieza) {
            String codP, codProy;
            codP = this.vistaHome.TxtCodigoPieza.getText();
            codProy = this.vistaHome.TxtCodigoProy.getText();

            if (this.vistaHome.TxtCodigoProy.getText().equals("")) {
                if (mo.BorrarPieza(codP) == true) {
                    JOptionPane.showMessageDialog(this.vistaHome, "Exito: Pieza borrada con éxito.");
                    this.refrescar();
                } else {
                    JOptionPane.showMessageDialog(this.vistaHome, "Error: algo salió mal al borrar la pieza");
                }
            } else if (!this.vistaHome.TxtCodigoProy.getText().equals("")) {
                if (mo.BorrarPiezaProyecto(codP, codProy) == true) {
                    JOptionPane.showMessageDialog(this.vistaHome, "Exito: Pieza borrada del proyeto: " + codProy + " con éxito.");
                    this.vistaHome.TablePiezas.setModel(mo.getTablaPiezasRelacionadas(this.vistaHome.TxtCodigoProy.getText()));
                } else {
                    JOptionPane.showMessageDialog(this.vistaHome, "Error: algo salió mal");
                }
            }

        }

        //CARGAR PIEZAS DE UN PROYECTO
        if (e.getSource() == this.vistaHome.BtnCargarPiezasDelProyecto) {

            if (this.vistaHome.TxtCodigoProy.getText().equals("")) {
                JOptionPane.showMessageDialog(this.vistaHome, "Debe seleccionar un proyecto para poder cargar su tabla de piezas");
            } else {
                this.vistaHome.TablePiezas.setModel(mo.getTablaPiezasRelacionadas(this.vistaHome.TxtCodigoProy.getText()));
            }
        }

        //LIMPIAR CUADROS DE TEXTOS DE PROYECTOS
        if (e.getSource() == this.vistaHome.BtnLimpiaTxtPieza) {
            this.vistaHome.TxtCodigoPieza.setText("");
            this.vistaHome.TxtNombrePieza.setText("");
            this.vistaHome.TxtDescripcionPi.setText("");
            this.vistaHome.SpinnerCantidadPi.setValue(0);
            this.vistaHome.SpinnerPrecioPi.setValue(0);
            this.vistaHome.TxtCodProveedor.setText("");
            this.vistaHome.TxtNombreProveedor.setText("");
            this.vistaHome.TxtApellidosProveedor.setText("");
            this.vistaHome.TxtDirecProveedor.setText("");
            this.refrescar();
        }

        //Botón de Salir
        if (e.getSource() == this.vistaHome.btnSalir) {
            Object[] opciones = {"Aceptar", "Cancelar"}; //Es un array para guardar las dos opciones
            int eleccion = JOptionPane.showOptionDialog(vistaHome, "¿Deseas salir de la aplicación?", "Mensaje de Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
            if (eleccion == JOptionPane.YES_OPTION) {
                System.exit(0); //Se cierra la aplicación.
            } else {
            }  //Sino, no se hace nada. 
        }

    }

    public void mouseClicked(MouseEvent e) {

        if (e.getButton() == 1 && e.getSource() == this.vistaHome.TableProyectos)//boton izquierdo
        {
            int fila = this.vistaHome.TableProyectos.rowAtPoint(e.getPoint());
            if (fila > -1) {
                //this.vistaHome.TablePiezas.setModel(mo.getTablaPiezasRelacionadas(String.valueOf(this.vistaHome.TableProyectos.getValueAt(fila, 0))));
                this.vistaHome.TxtCodigoProy.setText(String.valueOf(this.vistaHome.TableProyectos.getValueAt(fila, 0)));
                this.vistaHome.TxtNombreProy.setText(String.valueOf(this.vistaHome.TableProyectos.getValueAt(fila, 1)));
                this.vistaHome.TxtCiudadProy.setText(String.valueOf(this.vistaHome.TableProyectos.getValueAt(fila, 2)));
            }
        } else if (e.getButton() == 1 && e.getSource() == this.vistaHome.TablePiezas) {
            int fila = this.vistaHome.TablePiezas.rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.vistaHome.TxtCodigoPieza.setText(String.valueOf(this.vistaHome.TablePiezas.getValueAt(fila, 0)));
                this.vistaHome.TxtNombrePieza.setText(String.valueOf(this.vistaHome.TablePiezas.getValueAt(fila, 1)));
                this.vistaHome.SpinnerPrecioPi.setValue(Double.parseDouble(String.valueOf(this.vistaHome.TablePiezas.getValueAt(fila, 2))));
                this.vistaHome.TxtDescripcionPi.setText(String.valueOf(this.vistaHome.TablePiezas.getValueAt(fila, 3)));
                this.vistaHome.SpinnerCantidadPi.setValue(Integer.parseInt(String.valueOf(this.vistaHome.TablePiezas.getValueAt(fila, 4))));
                this.vistaHome.TxtCodProveedor.setText(String.valueOf(this.vistaHome.TablePiezas.getValueAt(fila, 5)));
                this.vistaHome.TxtNombreProveedor.setText(String.valueOf(this.vistaHome.TablePiezas.getValueAt(fila, 6)));
                this.vistaHome.TxtApellidosProveedor.setText(String.valueOf(this.vistaHome.TablePiezas.getValueAt(fila, 7)));
                this.vistaHome.TxtDirecProveedor.setText(String.valueOf(this.vistaHome.TablePiezas.getValueAt(fila, 8)));

            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void refrescar() {
        this.vistaHome.TableProyectos.setModel(mo.getTablaProyectos());
        this.vistaHome.TablePiezas.setModel(mo.getTablaPiezas());
    }

}
