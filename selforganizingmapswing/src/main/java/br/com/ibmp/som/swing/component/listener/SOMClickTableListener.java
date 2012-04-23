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
package br.com.ibmp.som.swing.component.listener;

import java.awt.Point;

/**
 * SOM click listener interface.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public interface SOMClickTableListener {

  /**
   * Emit a left click.
   * 
   * @param row   - Clicked row.
   * @param col   - Clicked col.
   * @param point - Point where the mouse was clicked.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void leftClick(int row, int col, Point point);
  
  /**
   * Emit a right click.
   * 
   * @param row   - Clicked row.
   * @param col   - Clicked col.
   * @param point - Point where the mouse was clicked.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void rightClick(int row, int col, Point point);
  
  /**
   * Emit a double click.
   * 
   * @param row   - Clicked row.
   * @param col   - Clicked col.
   * @param point - Point where the mouse was clicked.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void doubleClick(int row, int col, Point point);
	
}
