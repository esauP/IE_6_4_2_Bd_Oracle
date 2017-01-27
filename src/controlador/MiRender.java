/*
 * Copyright (C) 2016 vcardenas
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package controlador;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Esau Perez y Víctor Cárdenas
 */
public class MiRender extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row%2==0) {

            c.setBackground(new Color(240, 240, 240)); //205
            c.setForeground(Color.BLACK);

        } else {
            c.setBackground(new Color(195, 195, 195));
            c.setForeground(Color.BLACK);
        }
        return c;
    }
}
