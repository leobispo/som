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
package br.com.ibmp.som.matrix.vo;

import java.util.List;
import java.util.ArrayList;

/**
 * Class that contains the cluster element information.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public class WeightElementVO {

  /** X position in the matrix. */
  private int x;
  
  /** Y position in the matrix. */
  private int y;
  
  /** Distance between two Elements. */
  private int distance;
  
  /** Weight vector. */
  private SOMElementVO weight;
  
  /** Cluster elements. */
  private List<SOMElementVO> group;
  
  /**
   * Constructor.
   * 
   * @param x      - X position in the matrix.
   * @param y      - Y position in the matrix.
   * @param weight - Weight vector.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public WeightElementVO(int x, int y, SOMElementVO weight) {
    setNewWeight(x, y, weight);
    
    group = null;
  }

  /**
   * Return the group of this cluster.
   * 
   * @return The group of this cluster.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira 
   * 
   */
  public List<SOMElementVO> getGroup() {
    return group;
  }
  
  /**
   * Insert a new element on this cluster.
   * 
   * @param element - Element to be inserted on the cluster.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void addOnGroup(SOMElementVO element) { 
    if (group == null)
      group = new ArrayList<SOMElementVO>(100);
    
    group.add(element);
  }
 
  /**
   * Assessor for retrieve if this cluster has elements.
   * 
   * @return True if have one or more elements, otherwise false.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public boolean hasMembers() {
    if (group == null)
      return false;

    return ! group.isEmpty();  
  }
  
  /**
   * Assessor for set a new weight to this cluster.
   * 
   * @param x      - X position in the matrix.
   * @param y      - Y position in the matrix.
   * @param weight - Weight vector.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void setNewWeight(int x, int y, SOMElementVO weight) { 
    this.weight = weight;
  }
  
  /**
   * Return a new weight element multipling each weight elements by 
   * multiplier.
   * 
   * @param multiplier - Value to be multiply each weight elements.
   * 
   * @return A new Weight element.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public WeightElementVO multiply(double multiplier) {
    int i;
    SOMElementVO sample;
        
    sample = new SOMElementVO();
    
    for (i = 0; i < weight.getNumberOfValues(); i++) {
      if (weight.getValue(i) == null)
        sample.addValue(null);
      else
        sample.addValue(weight.getValue(i) * multiplier);
    }
    
    return new WeightElementVO(x, y, sample);
  }

  /**
   * Return a new weight element adding each weight elements by 
   * adder.
   * 
   * @param adder - Weight element to be added with this element.
   * 
   * @return A new Weight element.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public WeightElementVO add(WeightElementVO adder) {
    int i;
    SOMElementVO sample;
	    
    sample = new SOMElementVO();
	    
    for (i = 0; i < weight.getNumberOfValues(); i++) {
      if (weight.getValue(i) != null && adder.getWeight().getValue(i) != null)
        sample.addValue(weight.getValue(i) + adder.getWeight().getValue(i));
      else
        sample.addValue(null);
    }
    
    return new WeightElementVO(x, y, sample);
  }
  
  /**
   * Assessor to retrieve the weight element.
   * 
   * @return Weight element.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public SOMElementVO getWeight() {
    return weight;
  }
  
  /**
   * Return the X position of weight matrix.
   * 
   * @return the X position of weight matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public int getXPosition() {
    return x;
  }

  /**
   * Return the Y position of weight matrix.
   * 
   * @return the Y position of weight matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public int getYPosition() {
    return y;
  }
  
  /**
   * Set the X position of weight matrix.
   * 
   * @param x - X position in the matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void setXPosition(int x) {
    this.x = x;
  }

  /**
   * Set the Y position of weight matrix.
   * 
   * @param y - Y position in the matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void setYPosition(int y) {
    this.y = y;
  }
  
  /**
   * Assessor to set the distance between two or more elements.
   * 
   * @param distance - Percentage distance between elements.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void setPercentageDistance(int distance) {
    this.distance = distance; 
  }

  /**
   * Assessor to retrieve the distance between two or more elements.
   * 
   * @return Percentage distance between elements.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getPercentageDistance() {
    return distance;
  }
}
