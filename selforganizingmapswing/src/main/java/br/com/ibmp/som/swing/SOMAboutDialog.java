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
package br.com.ibmp.som.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * About dialog for SOM application.
 * 
 * @author Leonardo  Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 * 
 */
public final class SOMAboutDialog extends JDialog {

  private static final long serialVersionUID = -4531026149974435572L;

  /** Dialog constants. */
  public static final String ABOUT_CLOSE = "help.about.close";
  private static final String ABOUT_IMAGE = "/images/aboutpic.gif";
  
  /**
   * Constructor.
   * 
   * @param resourceBundle - Bundle that contains language informations.
   * 
   * @author Leonardo  Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public SOMAboutDialog(ResourceBundle resourceBundle) {
    super(new JFrame(), resourceBundle.getString(SOMMainWindow.MENU_ABOUT), 
      true);

    setResizable(false);
    createImage();
    createCopyrightText(resourceBundle); 
    
    pack();

    setAlwaysOnTop(true);
  }
  
  /**
   * Overriding method to set visible the window. This method will center
   * the dialog on desktop.
   * 
   * @param visible - Window visibility.
   * 
   * @author Leonardo  Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public void setVisible(boolean visible) {
    int xl, yl, xs, ys;
    Dimension size;
    
    if (visible) {
      xs = getWidth(); 
      ys = getHeight();
      size = getToolkit().getScreenSize(); 

      xl = (size.width/2) - (xs/2); 
      yl = (size.height/2) - (ys/2); 

      setBounds(xl, yl, xs, ys);
    }
    
    super.setVisible(visible);
  }

  /**
   * Assessor to create the about image.
   * 
   * @author Leonardo  Bispo de Oliveira and Daniele Sunaga de Oliveira
   *
   */
  private final void createImage() {
    JLabel aboutPic;
	    
    aboutPic = new JLabel(new ImageIcon(ABOUT_IMAGE));

    getContentPane().add(aboutPic, BorderLayout.CENTER);
  }
  
  /**
   * Assessor for create the copyright information text.
   * 
   * @param resourceBundle - Bundle that contains language informations.
   * 
   * @author Leonardo  Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  private final void createCopyrightText(ResourceBundle resourceBundle) {
    JPanel infoPanel;
    JPanel buttonPanel;
    JButton closeButton;
    JLabel aboutText;

    infoPanel = new JPanel(); 

    infoPanel.setLayout(new BorderLayout()); 
    infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
 
    aboutText = new JLabel("<html>Copyright (C) 2006 <p>Leonardo Bispo de Oliveira and<p>" +
    		                   "Daniele Sunaga de Oliveira<p><p>" +
                           "This Application is free software; you can redistribute it and/or modify<p>" + 
                           "it under the terms of the GNU Lesser General Public License as<p>" + 
                           "published by the Free Software Foundation; either version 2 of<p>" + 
                           "the License, or (at your option) any later version.<p><p>" +
                           "This Application is distributed in the hope that it will be useful,<p>" +
                           "but WITHOUT ANY WARRANTY; without even the implied warranty of<p>" +
                           "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the<p>" +
                           "GNU Lesser General Public License for more details.<p><p>" +
                           "You should have received a copy of the GNU Lesser General Public<p>" +
                           "License along with this library; if not, write to the Free Software<p>" +
                           "Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.</html>"); 

    infoPanel.add(aboutText);
    
    closeButton = new JButton(resourceBundle.getString(ABOUT_CLOSE));

    closeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SOMAboutDialog.this.setVisible(false);
      }
    });
    
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
    buttonPanel.add(Box.createHorizontalGlue()); 
    buttonPanel.add(closeButton);
    
    infoPanel.add(buttonPanel, BorderLayout.SOUTH);
    getContentPane().add(infoPanel, BorderLayout.SOUTH);
  }
}
