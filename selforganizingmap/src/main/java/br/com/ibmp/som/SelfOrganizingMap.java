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
package br.com.ibmp.som;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import br.com.ibmp.som.distance.DistanceMethodInterface;
import br.com.ibmp.som.distance.EuclideanDistanceMethod;
import br.com.ibmp.som.exception.SOMException;
import br.com.ibmp.som.listener.SelfOrganizingMapListener;
import br.com.ibmp.som.matrix.SampleVectorInterface;
import br.com.ibmp.som.matrix.WeightMatrix;
import br.com.ibmp.som.matrix.vo.WeightElementVO;
import br.com.ibmp.som.neighbors.GaussianNeighborsMethod;
import br.com.ibmp.som.neighbors.NeighborsMethodInterface;

/**
 * Implementation of SOM Algorithm.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class SelfOrganizingMap {

  /** Listener array to emit the step end and end events. */
  private List<SelfOrganizingMapListener> listeners;
	
  /** Status from self organizing map algorithm. */
  private enum Status {START, STOP, PAUSE};

  /** Self organizing map status. Should be START, STOP or PAUSE. */
  private Status status;
  
  /** Mutex for synchornizing multiple threads. */
  private ReentrantLock mutex;

  /** Weight matrix that contains the result. */
  private WeightMatrix  weightMatrix;
  
  /** Contains the sample vector to be rearranged. */
  private SampleVectorInterface sampleVector;
    
  /** Time variable. */
  private double t;  
  
  /** Iteration counter. */
  private int iteration;
  
  /** Number of times to be iterate the algorithm. */
  private int iterationNumber;
  
  /** Time increment variable. */
  private double tPerIteration;
  
  /**
   * Constructor.
   * 
   * @param iterationNumber - Number of times to be iterate the algorithm.
   * @param sampleVector    - Contains the sample vector to be rearranged.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public SelfOrganizingMap(int iterationNumber, SampleVectorInterface sampleVector) 
    throws SOMException {
    if (sampleVector == null)
      throw new SOMException("Invalid sample vector");
    
    if (iterationNumber < 1)
      throw new SOMException("Invalid Iteration Number");
    
    setIterationNumber(iterationNumber);

    this.weightMatrix    = null;
    this.sampleVector    = sampleVector;

    mutex     = new ReentrantLock();
    listeners = new ArrayList<SelfOrganizingMapListener>();
 
    status    = Status.STOP;
    iteration = 0;
  }
  
  /**
   * Assessor to set the iteration number.
   * 
   * @param iterationNumber - Number of times to be iterate the algorithm.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public void setIterationNumber(int iterationNumber) 
    throws SOMException {
    if (status == Status.START)
      throw new SOMException("The process is already started"); 
 
    this.iterationNumber = iterationNumber;
    
    iteration     = 0;
    t             = 0.0d;
    tPerIteration = 1.0d/iterationNumber;
  }
  
  /**
   * Assessor to retrieve the iteration number.
   * 
   * @return Number of times to be iterate the algorithm.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getIterationNumber() {    
    return iterationNumber;	  
  }

  /**
   * Add a listener to receive step end and end events.
   * 
   * @param listener - Class to receive the events.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public final void addListener(SelfOrganizingMapListener listener) {
    listeners.add(listener);	  
  }

  /**
   * Remove a listener of this class.
   * 
   * @param listener - Class to be removed.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public final void removeListener(SelfOrganizingMapListener listener) {
    listeners.remove(listener);
  }
  
  /**
   * This method will start the process iteration.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  protected final void startLearn() throws SOMException {
    if (status == Status.START)
      throw new SOMException("The process is already started"); 
   
    mutex.lock();
    
    try {
      status = Status.START;
    }
    finally {
      mutex.unlock();
    }
    
    while (t <= 1.0d && status == Status.START)
      stepLearn();

    if (status != Status.PAUSE) {
      if (iteration != 0)
        emitEnd();
    }
  }

  /**
   * Assessor for emiting a step end event.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   *
   */
  private final void emitStepEnd() {
    for(SelfOrganizingMapListener listener : listeners)
      listener.stepEnd(weightMatrix, iteration);
  }

  /**
   * Assessor for emiting an end event.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * @throws SOMException 
   *
   */
  private final void emitEnd() throws SOMException {	
    for(SelfOrganizingMapListener listener: listeners)
      listener.end(weightMatrix, iteration);
    
    status = Status.STOP;
    setIterationNumber(this.iterationNumber);     
  }

  /**
   * Class interface to start the learning process of Self Organizing Map.
   * 
   * @param weightWidth  - Matrix weight width.
   * @param weightHeight - Matrix weight height.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final void startLearn(int weightWidth, int weightHeight) 
    throws SOMException {
    startLearn(weightWidth, weightHeight, new EuclideanDistanceMethod(), 
      new GaussianNeighborsMethod());
  }

  /**
   * Class interface to start the learning process of Self Organizing Map.
   * 
   * @param weightWidth     - Matrix weight width.
   * @param weightHeight    - Matrix weight height. 
   * @param distanceMethod  - Method to calculate the distance between
   *                          two elements.
   * @param neighborsMethod - Method to rearrange all neighbors and also
   *                          the learning method algorithm.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final void startLearn(int weightWidth, int weightHeight, 
    DistanceMethodInterface distanceMethod, NeighborsMethodInterface 
    neighborsMethod) throws SOMException {
    weightMatrix = new WeightMatrix(weightWidth, weightHeight, 
      sampleVector, neighborsMethod, distanceMethod);
    
    startLearn();		  
  }

  /**
   * Class interface to do a step learning process of Self Organizing Map.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final void stepLearn() throws SOMException {
    if (weightMatrix == null)
      throw new SOMException("Weight Matrix not initialized");
    
    mutex.lock();
    
    if (t > 1.0d) {
      if (iteration != 0)
        emitEnd();

      return;
    }

    try {
      weightMatrix.executeStepLearn(t);
      t += tPerIteration;
      iteration++;
      emitStepEnd();
    }
    finally {
      mutex.unlock();
    }
    
    if (t > 1.0) {
      if (iteration != 0)
        emitEnd();
    }
  }

  /**
   * Class interface to do a step learning process of Self Organizing Map.
   * 
   * @param weightWidth     - Matrix weight width.
   * @param weightHeight    - Matrix weight height. 
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final void stepLearn(int weightWidth, int weightHeight) 
    throws SOMException {
    stepLearn(weightWidth, weightHeight, new EuclideanDistanceMethod(), 
      new GaussianNeighborsMethod());
  }

  /**
   * Class interface to do a step learning process of Self Organizing Map.
   * 
   * @param weightWidth     - Matrix weight width.
   * @param weightHeight    - Matrix weight height. 
   * @param distanceMethod  - Method to calculate the distance between
   *                          two elements.
   * @param neighborsMethod - Method to rearrange all neighbors and also
   *                          the learning method algorithm.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final void stepLearn(int weightWidth, int weightHeight, 
    DistanceMethodInterface distanceMethod, NeighborsMethodInterface 
    neighborsMethod) throws SOMException {
    weightMatrix = new WeightMatrix(weightWidth, weightHeight, 
      sampleVector, neighborsMethod, distanceMethod);
    
    stepLearn();
  }
  
  /**
   * Class interface to resume the learning process of Self Organizing Map
   * when the process was paused.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final void resumeLearn() throws SOMException {
    startLearn();  
  }

  /**
   * Class interface to stop the learning process of Self Organizing Map.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final void stopLearn() throws SOMException {
    status = Status.STOP;
    
    try {    
      Thread.sleep(200);  
    } 
    catch (InterruptedException e) {
      throw new SOMException("Problems with thread", e);
    }
    mutex.lock();
    
    try {
      setIterationNumber(iterationNumber);
    }
    finally {
      mutex.unlock();
    }
  }

  /**
   * Class interface to stop the learning process of Self Organizing Map.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final void pauseLearn() throws SOMException {
    if (status == Status.STOP)
      throw new SOMException("The process is not started"); 

    status = Status.PAUSE;
  }

  /**
   * After the learning process we must mount the groups for each sample
   * element. 
   * 
   * @return Mounted groups.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final WeightElementVO[][] mountGroups() throws SOMException {
    return mountGroups(sampleVector);
  }
  
  /**
   * After the learning process we must mount the groups for each sample
   * element. 
   * 
   * @param sampleVector - Vector that contains all element to be separated
   *                       between the groups.
   *                       
   * @return Mounted groups.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public final WeightElementVO[][] mountGroups(SampleVectorInterface 
    sampleVector) throws SOMException {
    if (status == Status.START)
      throw new SOMException("The process is already started");
    
    if (weightMatrix == null)
      throw new SOMException("The process was not started");
    
    if (sampleVector == null)
      throw new SOMException("The sample vector must not be empty");
    
    return weightMatrix.mountGroups(sampleVector);
  }
  
  /**
   * Assessor for returning the current sample vector.
   * 
   * @return Current sample vector.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public final SampleVectorInterface getSample() {
    return sampleVector;
  }
}
