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

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * File chooser filter to accept just .som extensions.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public class SOMFileFilter extends FileFilter {

  /** 
   * Accept method overriding.
   * 
   * @param f - Selected file on file chooser.
   * 
   * @return True if is a directory or if has the .som extension.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public boolean accept(File f) {

    String extension; 

    if (f.isDirectory())
      return true;

    if ((extension = getExtension(f)) != null && extension.equals("som"))
      return true;

    return false;
    
  }

  /**
   * Assessor for returning the filter description.
   * 
   * @return Filter description.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public String getDescription() {

    return "SOM Files (*.som)";

  }
  
  /**
   * Assessor for returning the file extension.
   * 
   * @param f - File to be parsed to get the extension.
   * 
   * @return The file extension.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  private final String getExtension(File f) {
    	
    int i;
    String ext, s; 
      
    ext = null;
    s = f.getName();

    i = s.lastIndexOf('.');

    if (i > 0 &&  i < s.length() - 1)
      ext = s.substring(i+1).toLowerCase();

    return ext;

  }

}
