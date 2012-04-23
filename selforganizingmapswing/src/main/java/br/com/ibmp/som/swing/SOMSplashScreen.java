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

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.ImageIcon;

/**
 * Splash screen for SOM applicate.
 * 
 * @author Leonardo  Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 * 
 */
public class SOMSplashScreen extends JWindow {

  private static final long serialVersionUID = -714934454488997052L;
  
  /** Splash screen constants. */
  private static final String SPLASH_IMAGE = "/images/splashpic.gif";

  /**
   * Constructor.
   *
   * @author Leonardo  Bispo de Oliveira and Daniele Sunaga de Oliveira
   *
   */
  public SOMSplashScreen() {
    int xl, yl, xs, ys;
    Dimension size;
    JLabel splashPic;
    
    splashPic = new JLabel(new ImageIcon(SPLASH_IMAGE));

    getContentPane().add(splashPic); 
    pack(); 

    xs = getWidth(); 
    ys = getHeight();
    size = getToolkit().getScreenSize(); 

    xl = (size.width/2) - (xs/2); 
    yl = (size.height/2) - (ys/2); 

    setAlwaysOnTop(true);
    setBounds(xl, yl, xs, ys); 
    setVisible(true); 
  }
}
