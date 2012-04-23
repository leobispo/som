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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.ibmp.som.distance.DistanceMethodInterface;
import br.com.ibmp.som.exception.SOMException;
import br.com.ibmp.som.matrix.vo.SOMElementVO;
import br.com.ibmp.som.matrix.vo.WeightElementVO;
import br.com.ibmp.som.neighbors.NeighborsMethodInterface;

/**
 * Class that contains the Weight elements matrix. This class
 * this class will do the learning process and retrieve the mounted
 * groups of some sample entry.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public class WeightMatrix {

  /** Matrix width. */
  private int width;
  
  /** Matrix height. */
  private int height;
  
  /** Sample vector that contains the learning elements. */
  private SampleVectorInterface sampleVector;
  
  /** Scale neighbors and learning method. */
  private NeighborsMethodInterface neighborsMethod;
  
  /** Distance calcule method. */
  private DistanceMethodInterface distanceMethod;
  
  /** The weight matrix. This matrix is inatingible by the user. */
  private WeightElementVO[][] matrix;
  
  /** Random instance. */
  private Random randomize;
  
  /**
   * Contructor.
   * 
   * @param width           - Matrix width.
   * @param height          - Matrix height.
   * @param sampleVector    - Sample vector that contains the learning elements.
   * @param neighborsMethod - Scale neighbors and learning method.
   * @param distanceMethod  - Distance calcule method.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public WeightMatrix(int width, int height, SampleVectorInterface sampleVector,
    NeighborsMethodInterface neighborsMethod, DistanceMethodInterface distanceMethod) 
    throws SOMException {
    int i, j;
    
    if (neighborsMethod == null)
      throw new SOMException("Not valid neighbors method");	
    
    this.width  = width;
    this.height = height;
    
    randomize = new Random();
    
    this.sampleVector    = sampleVector;
    this.neighborsMethod = neighborsMethod;
    this.distanceMethod  = distanceMethod;
    
    matrix = new WeightElementVO[width][height];
    
    for (i = 0; i < width; i++)
      for (j = 0; j < height; j++)
        matrix[i][j] = new WeightElementVO(i, j, sampleVector.randomizeWeight());
  }
  
  /**
   * Assessor to retrieve the matrix width.
   * 
   * @return The matrix width.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getWidth() {
    return width;
  }

  /**
   * Assessor to retrieve the matrix height.
   * 
   * @return The matrix height.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getHeight() {
    return height;
  }
  
  /**
   * Assessor for returning the best weight from some sample. If more then
   * one weight match then we return a random weight.
   * 
   * @param matrix - Weight matrix for returning the best weight.
   * @param sample - Sample element to be match.
   * 
   * @return Best matching weight.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  protected WeightElementVO getBestMatchingWeight(WeightElementVO[][] matrix, 
    SOMElementVO sample) throws SOMException {
    int i, j;
    double bestDistance, currDistance;

    List<WeightElementVO> matchList;
    
    bestDistance = Double.MAX_VALUE;
    
    matchList = null;
    
    for (i = 0; i < width; i++) {	
      for (j = 0; j < height; j++) {
        currDistance = distanceMethod.calculateDistance(sample, 
          matrix[i][j].getWeight());
        
        if (currDistance == bestDistance)
          matchList.add(matrix[i][j]);
        else if (currDistance < bestDistance) {
          bestDistance = currDistance;
          matchList = new ArrayList<WeightElementVO>();
          matchList.add(matrix[i][j]);
        }  
      }
    }
    
    return matchList.get(randomize.nextInt(matchList.size()));
  }

  /**
   * Assessor for returning the best weight from some sample. If more then
   * one weight match then we return a random weight.
   * 
   * @param matrix - Weight matrix for returning the best weight.
   * @param sample - Sample element to be match.
   * 
   * @return Best matching weight.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  protected WeightElementVO getBestUMatchingWeight(WeightElementVO[][] matrix, 
    SOMElementVO sample) throws SOMException {
    int i, j;
    double bestDistance, currDistance;

    List<WeightElementVO> matchList;
    
    bestDistance = Double.MAX_VALUE;
    
    matchList = null;
    
    for (i = 0; i < (width*2); i += 2) {
      for (j = 0; j < (height*2); j += 2) {
        currDistance = distanceMethod.calculateDistance(sample, 
          matrix[i][j].getWeight());
        
        if (currDistance == bestDistance)
          matchList.add(matrix[i][j]);
        else if (currDistance < bestDistance) {
          bestDistance = currDistance;
          matchList = new ArrayList<WeightElementVO>();
          matchList.add(matrix[i][j]);
        }  
      }
    }
    
    return matchList.get(randomize.nextInt(matchList.size()));
  }
  
  /**
   * Class interface to execute a step learn.
   * 
   * @param t - Time to be used in learning process.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public void executeStepLearn(double t) throws SOMException {
    SOMElementVO sample;
    WeightElementVO bestWeight;
    
    sample = sampleVector.randomizeSample();
    bestWeight = getBestMatchingWeight(matrix, sample);
    
    neighborsMethod.scaleNeighbors(width, height, matrix, 
      bestWeight, t, distanceMethod);
  }
  
  /**
   * Set the matrix distance between components.
   * 
   * @param groups - The U-Matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  protected void setUMatrixDistances(WeightElementVO[][] groups) {
    int i, j;
    
    /////////////////////////////////////////////////////////////////
    //TODO - Algorithm to set the distance between elements !!!
    //       I Have problems to calculate the distance, then I will 
    //       need some DANIELES help. Develop this with her.
    /////////////////////////////////////////////////////////////////
    
    for (i = 0; i < groups.length; i++) {  
      for (j = 0; j < groups[i].length; j++) {
        if (j%2 == 0)
          groups[i][j].setPercentageDistance(0);
        else
          groups[i][j].setPercentageDistance(100);
      }
    }
  }
  
  /**
   * Assessor to retrieve the U-Matrix generated by Self organizing method.
   * 
   * @param sampleVector - Samples to be put on weight matrix.
   * 
   * @return The U-Matrix matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   * @throws SOMException
   * 
   */
  public WeightElementVO[][] mountUMatrix(SampleVectorInterface sampleVector) 
    throws SOMException {
    int i, j, tmpI, tmpJ;
    SOMElementVO element;
    WeightElementVO group;
    WeightElementVO[][] groups;
    
    groups = new WeightElementVO[width*2][height*2];
  
    //////////////////////////////////////////////////////////////////////////////////////
    //FIXME - This Alorithm should be better. I do this just for cleaning compreention.
    //        Maybe I'll fix it latter.
    //////////////////////////////////////////////////////////////////////////////////////
    
    for (i = 0, tmpI = 0; tmpI < (width*2); i++, tmpI += 2) {
      for (j = 0, tmpJ = 0 ; tmpJ < (height*2); j++, tmpJ += 2) {
        groups[tmpI][tmpJ] = new WeightElementVO(tmpI, tmpJ, matrix[i][j].getWeight());
      }
    }

    for (i = 1; i < (width*2); i +=2 )
      for (j = 0; j < (height*2); j += 2)
        groups[i][j] = new WeightElementVO(i, j, null);
        
    for (i = 0; i < (width*2); i++) {
      for (j = 1; j < (height*2); j += 2)
        groups[i][j] = new WeightElementVO(i, j, null);
    }
    
    setUMatrixDistances(groups);
    
    for (i = 0; i < sampleVector.getRowSize(); i++) {
      element = sampleVector.getElement(i);
      group = getBestUMatchingWeight(groups, element);
      group.addOnGroup(element);     
    }

    return groups;
  }
  
  /**
   * Assessor to retrieve the groups generated by Self organizing method.
   * 
   * @param sampleVector - Samples to be put on weight matrix.
   * 
   * @return The weight element matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   * @throws SOMException
   * 
   */
  public WeightElementVO[][] mountGroups(SampleVectorInterface sampleVector) throws SOMException {
    int i, j;
    SOMElementVO element;
    WeightElementVO group;
    WeightElementVO[][] groups;
    
    groups = new WeightElementVO[width][height];
	    
    for (i = 0; i < width; i++)
      for (j = 0; j < height; j++)
        groups[i][j] = new WeightElementVO(i, j, matrix[i][j].getWeight());
    
    for (i = 0; i < sampleVector.getRowSize(); i++) {
      element = sampleVector.getElement(i);
      group = getBestMatchingWeight(groups, element);
      group.addOnGroup(element);     
    }

    return groups;
  }
}
