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
package br.com.ibmp.som.neighbors;

import br.com.ibmp.som.distance.DistanceMethodInterface;
import br.com.ibmp.som.exception.SOMException;
import br.com.ibmp.som.matrix.vo.WeightElementVO;

/**
 * Neighbors method interface for scale neighbors and to do the learning
 * method.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public interface NeighborsMethodInterface {

  /**
   * This method will scale the neighbors and do the
   * learning method.
   * 
   * @param width          - Matrix width.
   * @param height         - Matrix height.
   * @param matrix         - The weight matrix.
   * @param weight         - The selected weight.
   * @param t              - Time variable.
   * @param distanceMethod - Distance calcule method.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public void scaleNeighbors(int width, int height, WeightElementVO[][] matrix, 
    WeightElementVO weight, double t, DistanceMethodInterface distanceMethod) 
    throws SOMException;  
}
