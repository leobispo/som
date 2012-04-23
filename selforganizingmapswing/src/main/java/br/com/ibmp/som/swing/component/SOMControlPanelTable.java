/* Copyright (C) 2006 Leonardo Bispo de Oliveira and 
 *                    Daniele Sunaga de Oliveira
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package br.com.ibmp.som.swing.component;

import java.awt.Point;

import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.FocusListener;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.ibmp.som.swing.component.listener.SOMClickTableListener;

/**
 * Changed table to support some SOM features, like Double click, Right click
 * and left click.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public class SOMControlPanelTable extends JTable {

  /** Possible mouse envent button type. */
  private enum ButtonType {LEFT, RIGHT, DOUBLE};
  
  private static final long serialVersionUID = 6075152109393549042L;
  
  /** Listeners waiting for a mouse event. */
  private List<SOMClickTableListener> clickListeners;
  
  /** 
   * Constructor.
   * 
   * Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public SOMControlPanelTable() {
    super(new SOMTableModel());

    clickListeners = null;

    setTableHeader(null);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  
    addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {}

      public void focusLost(FocusEvent e) {
        SOMControlPanelTable.this.clearSelection();
      }
    });
    
    addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        int selectedRow;
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
          emitClick(ButtonType.DOUBLE, getSelectedRow(), getSelectedColumn(), e.getPoint());
        else if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
          emitClick(ButtonType.LEFT, getSelectedRow(), getSelectedColumn(), e.getPoint());
        else if (e.getButton() == MouseEvent.BUTTON3) {
          if ((selectedRow = SOMControlPanelTable.this.rowAtPoint(e.getPoint())) >= 0) {
            SOMControlPanelTable.this.setRowSelectionInterval(selectedRow, selectedRow);
            emitClick(ButtonType.RIGHT, getSelectedRow(), 0, e.getPoint());
          }
        }
      }
    });
  }

  /**
   * Assessor to emit a mouse event.
   * 
   * @param type   - Shoud be DOUBLE, LEFT or RIGHT.
   * @param row    - The clicked row.
   * @param col    - The clicked col.
   * @param point  - Point where the mouse was clicked.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  private final void emitClick(ButtonType type, int row, int col, Point point) {
    if (clickListeners != null) {
      for (SOMClickTableListener listener : clickListeners) {
        if (type == ButtonType.DOUBLE)
          listener.doubleClick(row, col, point);
        else if (type == ButtonType.LEFT)
            listener.leftClick(row, col, point);
        else if (type == ButtonType.RIGHT)
            listener.rightClick(row, col, point);
      }
    }
  }

  /**
   * Assessor for adding a new listener.
   * 
   * @param listener - Waiter for som mouse listener.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void addClickListener(SOMClickTableListener listener) {
    if(clickListeners == null)
      clickListeners = new ArrayList<SOMClickTableListener>();
    
    clickListeners.add(listener);
  }
  
  /**
   * Assessor to add a new row in the table.
   * 
   * @param label - Table value.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void addRow(String label) {
    ((SOMTableModel)getModel()).addRow(label);
  }
  
  /**
   * Assessor to remove a especific row from the table.
   * 
   * @param row - Row to be removed.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void removeRow(int row) {
    ((SOMTableModel)getModel()).removeRow(row);
  }
}
