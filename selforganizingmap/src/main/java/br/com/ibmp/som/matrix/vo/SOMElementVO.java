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
 * Class that contains the sample element information.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public final class SOMElementVO {

  /** Element name. */
  private String name;
  
  /** Element description. */
  private String description;
  
  /** Element values. */
  private List<Double> elements;
  
  /**
   * Constructor.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public SOMElementVO() {
    this(null, null);
  }
  
  /**
   * Constructor.
   * 
   * @param name       - Element name
   * @param description - Element description
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public SOMElementVO(final String name, final String description) {
    elements = new ArrayList<Double>(20);
    
    setName(name);
    setDescription(description);
  }
  
  /**
   * Set the element name.
   * 
   * @param name - Element name
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void setName(final String name) {
    this.name = name;
  }
 
  /**
   * Get the element name.
   * 
   * @return Element name
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public String getName() {
    return name;
  }
  
  /**
   * Set the element description.
   *  
   * @param description - Element description
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get the element description.
   * 
   * @return Element description
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * Assessor for return the number of values for this element.
   * 
   * @return Number of values
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public int getNumberOfValues() {
    return elements.size();
  }
  /**
   * Assessor for returning a some element value.
   * 
   * @param idx - Element index. Null if the element does not exists
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public Double getValue(final int idx) {
    return elements.get(idx);
  }
  
  /**
   * Add a value in this element.
   * 
   * @param value - Element value
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void addValue(final Double value) {
    this.elements.add(value);
  }

  /**
   * Add a value in this element.
   * 
   * @param arguments - List of values
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void addMultiValue(final Double...arguments) {
    for (Double argument : arguments)
      this.elements.add(argument);
  }
}
