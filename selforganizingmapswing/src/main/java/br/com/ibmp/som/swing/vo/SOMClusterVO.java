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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.List;

import javax.swing.JPanel;

import br.com.ibmp.som.exception.SOMException;
import br.com.ibmp.som.matrix.vo.WeightElementVO;

import br.com.ibmp.som.swing.SOMMainWindow;
import br.com.ibmp.som.swing.component.SOMHexagon;
import br.com.ibmp.som.swing.component.SOMElementPanel;

import br.com.ibmp.som.swing.components.external.AbsoluteLayout;

import br.com.ibmp.som.swing.component.listener.SOMHexagonListener;

/**
 * Class that contains the swing learning information.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class SOMClusterVO {

  /** Number of iterations for this cluster creation. */
  private int iteration;
  
  /** The cluster name. */
  private String clusterName;
  
  /** The learning name used for this cluster creation. */
  private String learningName;
  
  /** Sample name used for this cluster creation. */
  private String sampleName;
  
  /** Header labels extracted from sample file. */
  private List<String> header;
  
  /** The main panel that contains all clusters and table. */
  private JPanel mainPanel;
  
  /** Show the element table panel. */
  private SOMElementPanel elementPanel;
  
  /** All clusters matrix. */
  private WeightElementVO[][] clusters;
  
  /**
   * Contructor.
   * 
   * @param clusterName  - The cluster name.
   * @param learningName - The learning name used for this cluster creation.
   * @param sampleName   - Sample name used for this cluster creation.
   * @param clusters     - All clusters matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public SOMClusterVO(String clusterName, String learningName, 
    String sampleName, WeightElementVO[][] clusters) {
    
    this.clusterName  = clusterName;
    this.learningName = learningName;
    this.sampleName   = sampleName;
    this.clusters     = clusters;
    
    try {
      
      loadUMatrix();
      
    } 
    catch (SOMException e) {

      System.err.println("[ERROR] Problems to create the U-Matrix");
      e.printStackTrace();
      System.exit(1);
      
    }
    
  }
  
  /**
   * Assessor for creating the U-Matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   */
  private final void loadUMatrix() throws SOMException {
    
    int i, j;
    int actualX, actualY;
    SOMHexagon hexagon;
    mainPanel = new JPanel(new AbsoluteLayout());
    mainPanel.setBackground(SOMMainWindow.DESKTOP_COLOR);
    
    actualY = 20;
    
    hexagon = null;
    for (i = 0; i < clusters.length; i++) {
      
      if (i%2 == 0)
        actualX = 20;
      else
        actualX = 20 + hexagon.getWidth()/2;
      
      for (j = 0; j < clusters[i].length; j++) {
        
        if (i%2 == 0 && j%2 == 0)
          hexagon = new SOMHexagon(clusters[i][j].getPercentageDistance(), 60,
            clusters[i][j]);
        else
          hexagon = new SOMHexagon(clusters[i][j].getPercentageDistance(), 60);
        
        hexagon.addMouseListener(new SOMHexagonListener() {

          public void mouseEvent(WeightElementVO elementVO) {

            if (elementVO != null)             
              changeEventPanel(elementVO);
            else
              SOMClusterVO.this.elementPanel.setVisible(false);

            
          }
          
        });
        
        hexagon.setLocation(actualX, actualY);
        mainPanel.add(hexagon);
        actualX += hexagon.getWidth();
      
      }
      
      actualY += hexagon.getHeight() - hexagon.getHeight()/4;
     
    }
    
  }
  
  /**
   * Assessor for change the event panel that show all elements from cluster.
   * 
   * @param elementVO - Cluster element.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void changeEventPanel(WeightElementVO elementVO) {
  
    SOMClusterVO.this.elementPanel.changeElement(elementVO, header);
  
  }
  
  /**
   * Assessor for appending the number of iteration for this cluster creation.
   * 
   * @param iteration - Number of iterations for this cluster creation.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void setIteration(int iteration) {
    
    this.iteration = iteration;
    
  }

  /**
   * Assessor for returning the number of iterations. 
   * 
   * @return Number of iterations for this cluster creation.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getIteration() {
    return iteration;
  }

  /**
   * Assessor for returning the cluster name.
   * 
   * @return The cluster Name.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public String getClusterName() {
    return clusterName;
  }

  /**
   * Assessor for returning the cluster matrix.
   * 
   * @return The cluster matrix.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public WeightElementVO[][] getClusters() {
    return clusters;
  }

  /**
   * Assessor for returning the learning name.
   * 
   * @return The learning name used for this cluster creation.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public String getLearningName() {
    return learningName;
  }

  /**
   * Assessor for retrieve the main panel that contains the table 
   * cluster.
   * 
   * @param elementPanel - The new elementPanel.
   * @return The main panel that contains the table cluster.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public JPanel getMainPanel(SOMElementPanel elementPanel) {
    
    mainPanel.addMouseListener(new MouseListener() {

      public void mouseClicked(MouseEvent e) {

        SOMClusterVO.this.elementPanel.setVisible(false);
        
      }

      public void mousePressed(MouseEvent e) {}

      public void mouseReleased(MouseEvent e) {}

      public void mouseEntered(MouseEvent e) {}

      public void mouseExited(MouseEvent e) {}
    
    });

    this.elementPanel = elementPanel;
    return mainPanel;
    
  }

  /**
   * Assessor for returning the sample name.
   * 
   * @return Sample name used for this cluster creation.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public String getSampleName() {
    
    return sampleName;
    
  }
  
  /**
   * Assessor for appending the table header.
   * 
   * @param header - Header labels extracted from sample file.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void setHeader(List<String> header) {
    
    this.header = header;
    
  }
  
  /**
   * Assessor for returning the table header.
   * 
   * @return Header labels extracted from sample file.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public List<String> getHeader() {
    
    return header;
    
  }
  
}
