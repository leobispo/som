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
package br.com.ibmp.som.swing.vo;

import br.com.ibmp.som.SelfOrganizingMap;

/**
 * Class that contains the swing learning information.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class SOMLearningVO {

  /** Weight matrix width. */
  private int width;
  
  /** Weight matrix height. */
  private int height;
  
  /** The Learning Name. */
  private String name;
  
  /** The sample name for this learning process. */
  private String sampleName;
  
  /** Self Organizing map instance. */
  private SelfOrganizingMap som;
  
  /**
   * Constructor.
   * 
   * @param width      - Weight matrix width.
   * @param height     - Weight matrix height.
   * @param name       - The Learning Name.
   * @param sampleName - The sample name for this learning process.
   * @param som        - Self Organizing map instance.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMLearningVO(int width, int height, String name, 
    String sampleName, SelfOrganizingMap som) {
    this.width      = width;
    this.height     = height;
    this.name       = name;
    this.sampleName = sampleName;
    this.som        = som;
  }
   
  /**
   * Assessor for returning the weight matrix width.
   * 
   * @return Weight matrix width.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getWidth() {
    return width;
  }

  /**
   * Assessor for returning the weight matrix height.
   * 
   * @return Weight matrix height.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getHeight() {
    return height;
  }
 
  /**
   * Assessor for returning the Learning Name.
   * 
   * @return The Learning Name.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public String getName() {
    return name;
  }

  /**
   * Assessor for returning the sample name for this learning process.
   * 
   * @return The sample name for this learning process.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public String getSampleName() {
    return sampleName;
  }

  /**
   * Assessor for returning the Self Organizing Map instance.
   * 
   * @return The Self Organizing Map instance.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SelfOrganizingMap getSom() {
    return som;
  }
  
}
