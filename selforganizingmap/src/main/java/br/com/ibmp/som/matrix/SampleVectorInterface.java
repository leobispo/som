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
package br.com.ibmp.som.matrix;

import java.util.List;

import br.com.ibmp.som.matrix.vo.SOMElementVO;

/**
 * Interface to implement the sample vector.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public interface SampleVectorInterface {

  /**
   * Assessor for returning the sample header.
   * 
   * @return List of sample header.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public List<String> getHeader();
  
  /**
   * Assessor for returning the sample name.
   * 
   * @return Sample name.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public String getName();
	  
  /**
   * Assessor for returning the Sample vector line passed with parameter.
   * 
   * @param idx - Sample line to be returned.
   *
   * @return The Matrix element.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMElementVO getElement(final int idx);
  
  /**
   * Assessor for returning a randomized weight.
   * 
   * @return Randomized weight.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMElementVO randomizeWeight();
  
  /**
   * Assessor for returning a randomized sample.
   * 
   * @return Randomized sample.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMElementVO randomizeSample();
	  
  /**
   * Assessor for returning the row size.
   * 
   * @return Row size.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getRowSize();
	  
  /**
   * Assessor for returning the column size.
   * 
   * @return Column size.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getColSize(); 
}
