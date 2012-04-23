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
package br.com.ibmp.som.swing.internalframe;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;

import br.com.ibmp.som.swing.SOMAboutDialog;

import br.com.ibmp.som.swing.component.SOMElementPanel;

import br.com.ibmp.som.swing.vo.SOMClusterVO;

/**
 * This class will show the U-Matrix.
 *  
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class SOMClusterView extends JInternalFrame {

  /** Frame constants. */
  private static final long serialVersionUID = -1996119597639054165L;

  private static final String TITLE = "clusterview.title";
  
  /** The current elementPanel. */
  private SOMElementPanel elementPanel;
  
  /**
   * Constructor.
   * 
   * @param clusterVO      - VO that contains informations about this cluster.
   * @param resourceBundle - Bundle that contains the language informations.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMClusterView(SOMClusterVO clusterVO, ResourceBundle resourceBundle) {
    
    super(resourceBundle.getString(TITLE) + " - " + clusterVO.getClusterName() +
      ":" + clusterVO.getIteration() , true, true, true, true);

    JPanel toolBar;
    JButton exit;
    
    exit = new JButton(resourceBundle.getString(SOMAboutDialog.ABOUT_CLOSE));

    exit.addActionListener(new ActionListener() {

    public void actionPerformed(ActionEvent e) {

        dispose();
        
      }
        
    });
    
    toolBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    toolBar.add(exit);

    elementPanel = new SOMElementPanel();
    add(elementPanel, BorderLayout.PAGE_START);
    add(toolBar, BorderLayout.PAGE_END);
    add(new JScrollPane(clusterVO.getMainPanel(elementPanel)), BorderLayout.CENTER);
    setSize(300, 300);
    
    setVisible(true);
    
  }
  
}
