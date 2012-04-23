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

import javax.swing.table.DefaultTableModel;

/**
 * Implementation of table model to SOM control panel table.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public class SOMTableModel extends DefaultTableModel {

  private static final long serialVersionUID = 3376566138571702166L;

  /**
   * Constructor.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   *
   */
  public SOMTableModel() {

    super(0,1);
	  
  }
  
  /**
   * No one cell are editable.
   * 
   * @param row - Row cell.
   * @param col - Col cell.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public boolean isCellEditable(int row, int col) { 

    return false; 

  }
  
  /**
   * Assessor for adding a new row in table.
   * 
   * @param label - Row value.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Ol
   *    */
  public void addRow(String label) {

    Object value[] = {label};

    addRow(value);
	  
  }

}
