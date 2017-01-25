package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Modelo;
import vista.*;

/**
 * Clase controlador que se encargará de oir los eventos ocurridos en las vistas
 * y llamar a los métodos y funciones contenidas en el Modelo
 *
 * @author Víctor. Proyecto taller.
 * @version 1.0
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
    public void iniciar() {
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
       
        this.vistaHome.btnSalir.addActionListener(this);

        this.vistaHome.TableProyectos.addMouseListener(this);
        this.vistaHome.TableProyectos.setModel(mo.getTablaProyectos());
        this.vistaHome.TablePiezas.addMouseListener(this);
    }

  
    //Control de eventos de los controles que tienen definido un "ActionCommand"
    public void actionPerformed(ActionEvent e) {

        /*----------------------------------------------------------------------------*/
        //Proyectos
        if(e.getSource() == this.vistaHome.BtnAniadeProyecto){
            
        }
        if (e.getSource() == this.vistaHome.BtnActualizaProyecto){
            
        }
        if (e.getSource() == this.vistaHome.BtnBorraProyecto){
            
        }
        if (e.getSource() == this.vistaHome.BtnLimpiarTxtProyecto){
            
        }
        /*----------------------------------------------------------------------------*/
        //Piezas
        if (e.getSource() == this.vistaHome.BtnAniadePieza){
            
        }
        if (e.getSource() == this.vistaHome.BtnActualizaPieza){
            
        }
        if (e.getSource() == this.vistaHome.BtnBorraPieza){
            
        }
        if (e.getSource() == this.vistaHome.BtnLimpiaTxtPieza){
            
        }
        /*----------------------------------------------------------------------------*/
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
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}

