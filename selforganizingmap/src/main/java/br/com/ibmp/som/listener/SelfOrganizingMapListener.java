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
package br.com.ibmp.som.listener;

import br.com.ibmp.som.matrix.WeightMatrix;

/**
 * Listener implementation for self organizing map
 * communication with other classes.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public interface SelfOrganizingMapListener {

  /**
   * Emit the end event.
   * 
   * @param matrix    - Current weight matrix.
   * @param iteration - Current iteration.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void end(WeightMatrix matrix, int iteration);
 
  /**
   * Emit the step end event.
   * 
   * @param matrix    - Current weight matrix.
   * @param iteration - Current iteration.
   *
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void stepEnd(WeightMatrix matrix, int iteration); 
}
