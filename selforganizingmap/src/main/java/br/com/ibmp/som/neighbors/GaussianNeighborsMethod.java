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
import br.com.ibmp.som.matrix.vo.SOMElementVO;
import br.com.ibmp.som.matrix.vo.WeightElementVO;

/**
 * Gaussian neighbors method for scale neighbors and to do the learning
 * method.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class GaussianNeighborsMethod implements NeighborsMethodInterface {

  /** Default radius. */
  private final static int RADIUS = 60;
 
  /** Radius to be used with gaussian method. */
  int radius;
  
  /**
   * Constructor.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   *
   */
  public GaussianNeighborsMethod() {
    this(RADIUS); 
  }
  
  /**
   * Constructor.
   * 
   * @param radius - Radius to be used with gaussian method.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public GaussianNeighborsMethod(int radius) {
    this.radius = radius;
  }
  
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
    throws SOMException {
    int i, j;
    int x, y;
	
    double r, nullDecimal;
    SOMElementVO outer;
    SOMElementVO center;
	
    WeightElementVO newWeight;
    
    double t1, distanceNormalize, distance;
    
    r = Math.round((double) (radius * (1.0f - t))/2.0d);
    
    nullDecimal = 0.0d;
    
    outer  = new SOMElementVO();
    center = new SOMElementVO();
    
    outer.addMultiValue(r, r, nullDecimal);
    
    center.addMultiValue(nullDecimal, nullDecimal, nullDecimal);
    
    distanceNormalize = distanceMethod.calculateDistance(center, outer);
    
    for (i = (int) -r; i < r; i++) {
      for (j = (int) -r; j < r; j++) {
        if ((i + weight.getXPosition()) >= 0 && 
            (i + weight.getXPosition()) < width &&
            (j + weight.getYPosition()) >= 0 && 
            (j + weight.getYPosition()) < height) {
          outer  = new SOMElementVO();
          outer.addMultiValue(new Double(i), new Double(j), nullDecimal);
          distance = distanceMethod.calculateDistance(outer, center);
          distance /= distanceNormalize;
          
          t1 = Math.exp(-1.0d * (Math.pow(distance, 2.0d)) / 0.15d);
          t1 /= (t * 4.0d + 1.0d);
          
          x = weight.getXPosition() + i;
          y = weight.getYPosition() + j;
          
          newWeight = weight.multiply(t1).add(matrix[x][y].multiply((1.0f - t)));
          
          newWeight.setXPosition(x);
          newWeight.setYPosition(y);
          matrix[x][y] = newWeight;
        }
      }
    } 
  }
}
