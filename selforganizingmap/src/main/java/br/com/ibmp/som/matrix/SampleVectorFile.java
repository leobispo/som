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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.StringTokenizer;

import br.com.ibmp.som.exception.SOMException;
import br.com.ibmp.som.matrix.vo.SOMElementVO;

/**
 * Class that contains the sample vector structure.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class SampleVectorFile implements SampleVectorInterface {

  /** Matrix Header. */
  private List<String> header;
  
  /** Array that contains the sample vector. */
  private List<SOMElementVO> vector; 
  
  /** Random instance. */
  private Random randomize;
  
  /** Sample name. */
  private String name;
  
  /**
   * Constructor.
   * 
   * @param file - File that contains the sample vector.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException 
   * 
   */
  public SampleVectorFile(String file) throws SOMException {
    if (file == null)
      throw new SOMException("File is not valid");
    
    this.name = file;
    vector    = new ArrayList<SOMElementVO>(100);
    header    = new ArrayList<String>(100);
    randomize = new Random();
    
    openFile(file);
  }

  /**
   * Assessor for opening a file that contains the sample vector.
   * 
   * @param fileName - File to be opened.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException 
   * 
   */
  protected final void openFile(String fileName) 
    throws SOMException {
    File file;	    
    String line;
    
    InputStream is;
    BufferedReader reader;
    
    file = new File(fileName);
    
    if (!file.exists())
      throw new SOMException("File does not exists");

    try {
      is = new FileInputStream(file);
    } 
    catch (FileNotFoundException e) {
      throw new SOMException("File not found", e);    
    }
    
    reader = new BufferedReader(new InputStreamReader(is));
    
    try {
      parseHeader(reader.readLine());

      while ((line = reader.readLine()) != null)
        vector.add(parseLine(line));
    } 
    catch (IOException e) {
      throw new SOMException("Problems with reader", e);
    }
  }

  /**
   * Assessor to normalize empty columns.
   * 
   * @param line - Line to be normalized.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  private final String normalizeLine(String line) {	  
    while (line.contains("\t\t"))
      line = line.replaceAll("\t\t", "\t \t");

    return line;
  }
  
  /** Parse the header line.
   * 
   * @param line - Sample unparsed line.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   *
   * @throws SOMException
   * 
   */
  protected final void parseHeader(String line) throws SOMException {
    StringTokenizer tokenizer;
	    
    line = normalizeLine(line);
    
    tokenizer = new StringTokenizer(line, "\t");

    while (tokenizer.hasMoreTokens())
      header.add(tokenizer.nextToken());	    
  }
  
  /**
   * Parse the vector sample.
   * 
   * @param line - Sample unparsed line.
   * 
   * @return List of parsed line.
   *
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   * @throws SOMException
   * 
   */
  protected final SOMElementVO parseLine(String line) throws SOMException {
    String token;
    Double value;
    SOMElementVO element;
    StringTokenizer tokenizer;
    
    line = normalizeLine(line);
    
    tokenizer = new StringTokenizer(line, "\t");

    if (!tokenizer.hasMoreTokens())
      throw new SOMException("Problems with the Sample file");
    
    element = new SOMElementVO();
    
    element.setName(tokenizer.nextToken());
    
    if (!tokenizer.hasMoreTokens())
      throw new SOMException("Problems with the Sample file");
    
    element.setDescription(tokenizer.nextToken());
    
    while (tokenizer.hasMoreTokens()) {
      if ((token = tokenizer.nextToken()).equals(" "))
        value = null;
      else
        value = new Double(token);
      
      element.addValue(value);
    }
    
    while (element.getNumberOfValues() < getColSize())
      element.addValue(null);
    
    return element;
  }

  /**
   * Assessor for returning the sample header.
   * 
   * @return List of sample header.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public List<String> getHeader() {
    return header;
  }
  
  /**
   * Assessor for returning the sample name.
   * 
   * @return Sample name.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public String getName() {
    return name;
  }
  
  /**
   * Assessor for returning the Sample vector line passed with parameter.
   * 
   * @param idx - Sample line to be returned.
   * @return The Matrix element.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMElementVO getElement(final int idx) {
    return vector.get(idx);
  }

  /**
   * Assessor for returning a randomized weight.
   * 
   * @return Randomized weight.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMElementVO randomizeWeight() {	  
	  int i;
	  int row;
    SOMElementVO weight;
    Double value;
    
    value = null;
    
    weight = new SOMElementVO();
    
    for (i = 0; i < getColSize(); i++) {
      while (value == null) {
        row = randomize.nextInt(getRowSize());
        value = getElement(row).getValue(i);
      }

      weight.addValue(value);
    }
    
    return weight;
  }
  
  /**
   * Assessor for returning a randomized sample.
   * 
   * @return Randomized sample.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMElementVO randomizeSample() {
    int row;
    
    row = randomize.nextInt(getRowSize());
    
    return getElement(row);
  }

  
  /**
   * Assessor for returning the row size.
   * 
   * @return Row size.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getRowSize() {
    return vector.size();
  }
  
  /**
   * Assessor for returning the column size.
   * 
   * @return Column size.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public int getColSize() {
    return (header.size() - 2);
  }
}
