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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

import br.com.ibmp.som.swing.SOMMainWindow;

/**
 * Control panel for SOM application.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class SOMArrestedPanel extends JPanel {

  private static final long serialVersionUID = 3068785052373166527L;
  
  /** Control Panel constants. */
  private static final String CLOSE_ACTIVE   = "/images/close_act.gif";
  private static final String CLOSE_INACTIVE = "/images/close_inact.gif";
  
  /** Arrow cursor. */
  private Cursor currCursor;
  
  /** Tell if the border is dragged. */
  private boolean resizeEvent;
  
  /** Main panel that contains all tables. */
  private JPanel mainPanel;
  
  /** Active X for close button. */
  private ImageIcon closeAct;
  
  /** Inactive X for close button. */
  private ImageIcon closeInact;
  
  /** Close button instance. */
  private JLabel closeButton;

  /**
   * Constructor.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   *
   */
  public SOMArrestedPanel() {
    resizeEvent = false;
    currCursor = getCursor();
 
    setLayout(new BorderLayout()); 
    setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); 
    mainPanel = new JPanel();

    addClose();
    
    add(mainPanel, BorderLayout.CENTER);
    
    setPreferredSize(new Dimension(230,400));
    
    addMouseMotionListener(new MouseMotionAdapter() {

      public void mouseMoved(MouseEvent e) {
    	  
        if (e.getX() >= (SOMArrestedPanel.this.getWidth() - 3) &&
          e.getX() <= (SOMArrestedPanel.this.getWidth())) {
        	
          SOMArrestedPanel.this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
          
        }
        else if (!resizeEvent)
          SOMArrestedPanel.this.setCursor(SOMArrestedPanel.this.currCursor);
       
      }
      
      public void mouseDragged(MouseEvent e) {
        
          if (resizeEvent && e.getX() > 20) {

        	  SOMArrestedPanel.this.setPreferredSize(new Dimension(e.getX() + 3,400));
        	  SOMArrestedPanel.this.revalidate();

          }

      }

    });
    
    addMouseListener(new MouseListener() {

    public void mouseClicked(MouseEvent e) {}

		public void mousePressed(MouseEvent e) {

      if (e.getX() >= (SOMArrestedPanel.this.getWidth() - 3) &&
        e.getX() <= (SOMArrestedPanel.this.getWidth()))
        resizeEvent = true;

    }

    public void mouseReleased(MouseEvent e) {

      resizeEvent = false;
        	
    }

		public void mouseEntered(MouseEvent arg0) {}

		public void mouseExited(MouseEvent arg0) {

      if (!resizeEvent)
        SOMArrestedPanel.this.setCursor(SOMArrestedPanel.this.currCursor);
	        	  
		}
    	
    });

    setVisible(true);
	  
  }
  
  /**
   * Assessor to add a new component in main panel.
   * 
   * @param value     - Label to be shonw at top of component.
   * @param component - Component to be added.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void addComponent(String value, Component component) {

    JPanel panel;
    JLabel label;
    
    label = new JLabel(value);
    label.setForeground(Color.WHITE);
    
    panel = new JPanel(new BorderLayout());
    panel.setBackground(SOMMainWindow.DESKTOP_COLOR);
    panel.add(label, BorderLayout.NORTH);
    panel.add(component, BorderLayout.CENTER);
    mainPanel.add(panel);
 
    mainPanel.setLayout(new GridLayout(mainPanel.getComponentCount(), 1));

  }

  /**
   * Assessor to add the cluster controllers in main panel.
   * 
   * @param value     - Label to be shonw at top of component.
   * @param component - Component to be added.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void addControllers(String value, JPanel component) {

    JPanel panel;
    JLabel label;
	    
    label = new JLabel(value);
    label.setForeground(Color.WHITE);
	    
    panel = new JPanel(new BorderLayout());
    panel.setBackground(SOMMainWindow.DESKTOP_COLOR);
    panel.add(label, BorderLayout.NORTH);
    panel.add(component, BorderLayout.CENTER);
    
    panel.setBorder(BorderFactory.createLineBorder(SOMMainWindow.DESKTOP_COLOR));
    add(panel, BorderLayout.SOUTH);  
	  
  }
  
  /**
   * Assessor to add the close button at top of control panel.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   *
   */
  private final void addClose() {
  
    JPanel closePanel;
    
    closeAct   = new ImageIcon(CLOSE_ACTIVE);
    closeInact = new ImageIcon(CLOSE_INACTIVE);

    closePanel  = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    closeButton = new JLabel(closeInact);
    
    closeButton.addMouseListener(new MouseListener() {

      public void mouseClicked(MouseEvent e) {

        SOMArrestedPanel.this.setVisible(false);
	
      }

      public void mousePressed(MouseEvent e) {}

      public void mouseReleased(MouseEvent e) {}

      public void mouseEntered(MouseEvent e) {}

      public void mouseExited(MouseEvent e) {
    
        closeButton.setIcon(closeInact);
      
      }
    	
    });

    closeButton.addMouseMotionListener(new MouseMotionAdapter() {

      public void mouseMoved(MouseEvent e) {
      
        closeButton.setIcon(closeAct);
      
      }
      
    });

    closePanel.add(closeButton);
    add(closePanel, BorderLayout.NORTH);
    
  }
  
}
