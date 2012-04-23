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
package br.com.ibmp.som.distance;

import br.com.ibmp.som.exception.SOMException;
import br.com.ibmp.som.matrix.vo.SOMElementVO;

/**
 * Euclidean method for calculate the distance.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class EuclideanDistanceMethod implements DistanceMethodInterface {

  /**
   * Assessor for calculating the distance between two elements.
   * 
   * @param e1 - First element to be calculated.
   * @param e2 - Second element to be calculated.
   * 
   * @return Distance between two elements.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public double calculateDistance(SOMElementVO e1, SOMElementVO e2)
    throws SOMException {
    int i;
    double x;
    
	  if (e1.getNumberOfValues() != e2.getNumberOfValues())
      throw new SOMException("Problems with elements size: " + e1.getNumberOfValues() +
        "," + e2.getNumberOfValues());
	
	  x = 0.0d;
	  for (i = 0; i < e1.getNumberOfValues(); i++) {
      if (e1.getValue(i) != null && e2.getValue(i) != null)
        x += Math.pow(e1.getValue(i).doubleValue() - e2.getValue(i).doubleValue(), 2);
	  }
	
    return Math.sqrt(x);
  }
}