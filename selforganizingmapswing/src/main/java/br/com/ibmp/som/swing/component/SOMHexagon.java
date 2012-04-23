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
package br.com.ibmp.som.swing.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

import br.com.ibmp.som.exception.SOMException;

import br.com.ibmp.som.matrix.vo.WeightElementVO;

import br.com.ibmp.som.swing.component.listener.SOMHexagonListener;

/**
 * SOM Hexagon component that will be used on U-Matrix.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class SOMHexagon extends JPanel {

  /** Panel constants. */
  private static final long serialVersionUID = -4824200387142978258L;
   
  /** polygon that contains the hexagon format. */
  private Polygon hexagon;

  /** Weight element from this cluster. */
  private WeightElementVO elementVO;
  
  /** Listeners waiting for a mouse emotion listener. */
  private List<MouseMotionListener> listeners;
  
  /** Listeners waiting for an hexagon listener. */
  private List<SOMHexagonListener> SOMlistener;
 
  /** The hexagon color. */
  private Color color;
  
  /**
   * Constructor. 
   * 
   * @param distancePercent - Percentage distance between two or more components.
   * @param size            - Hexagon size.
   * @param elementVO       - Weight element from this cluster.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public SOMHexagon(int distancePercent, int size, WeightElementVO elementVO) 
    throws SOMException {
    
    this(distancePercent, size);
    
    this.elementVO = elementVO;
    
  }

  /**
   * Constructor. 
   * 
   * @param distancePercent - Percentage distance between two or more components.
   * @param size            - Hexagon size.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  public SOMHexagon(int distancePercent, int size) throws SOMException {
    
    int x;
    
    if (distancePercent > 100 || distancePercent < 0)
      throw new SOMException("Invalid distance");
    
    x = (255 * distancePercent)/100;

    int[] xpoints = {0, 0, size/2, size, size, size/2};
    int[] ypoints = {size/4, size/2 + size/4, size, size/2 + size/4, size/4, 0};
    
    elementVO = null;
    
    hexagon = new Polygon(xpoints, ypoints, 6);
    
    color = new Color(x, x, x);
    
    setPreferredSize(new Dimension(size, size));
    setSize(size, size);
    
    listeners   = new ArrayList<MouseMotionListener>();
    SOMlistener = new ArrayList<SOMHexagonListener>();
    
    super.addMouseListener(new MouseListener() {

      public void mouseClicked(MouseEvent e) {
        
        if (hexagon.contains(e.getPoint())) {
          
          for (SOMHexagonListener listener : SOMlistener)
            listener.mouseEvent(elementVO);
            
        }
        
      }

      public void mousePressed(MouseEvent e) {}

      public void mouseReleased(MouseEvent e) {}

      public void mouseEntered(MouseEvent e) {}

      public void mouseExited(MouseEvent e) {}
      
    });
    
    super.addMouseMotionListener(new MouseMotionListener() {

      public void mouseDragged(MouseEvent e) {
        
        if (hexagon.contains(e.getPoint())) {
          
          for (MouseMotionListener listener : listeners)
            listener.mouseDragged(e);
            
        }
      
      }
      
      public void mouseMoved(MouseEvent e) {
        
        if (hexagon.contains(e.getPoint())) {
          
          if (elementVO != null) {
           
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
          }
          else {
            
            for (MouseMotionListener listener : listeners)
              listener.mouseMoved(e);
          
          }
            
        }
        
      }
    
    });
  
  }

  /**
   * Assessor for adding a new mouse listener.
   * 
   * @param listener - Listener that will be called wen a mouse event occurr.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void addMouseListener(SOMHexagonListener listener) {
  
    SOMlistener.add(listener);
    
  }

  /**
   * Assessor for adding a new mouse motion listener.
   * 
   * @param listener - Listener that will be called wen a mouse motion event occurr.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void addMouseMotionListener(MouseMotionListener listener) {
    
    listeners.add(listener);
    
  }
  
  /**
   * Painting method.
   * 
   * @param g - The graphic painter.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void paintComponent(Graphics g) {

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(color);
    g2d.fill(hexagon);
    g2d.setColor(Color.RED);
    
    if (elementVO != null)
      g2d.fillOval(getWidth()/2 - 5, getHeight()/2 - 5, 10, 10);
    
    g2d.setColor(Color.BLUE);
    g2d.draw(hexagon);
  
  }

}
