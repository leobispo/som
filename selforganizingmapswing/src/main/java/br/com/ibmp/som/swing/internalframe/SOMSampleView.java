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

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;

import javax.swing.table.DefaultTableModel;

import br.com.ibmp.som.matrix.SampleVectorFile;
import br.com.ibmp.som.matrix.SampleVectorInterface;

import br.com.ibmp.som.matrix.vo.SOMElementVO;

import br.com.ibmp.som.swing.SOMAboutDialog;
import br.com.ibmp.som.swing.SOMNewLearningDialog;
import br.com.ibmp.som.swing.component.SOMTableModel;
import br.com.ibmp.som.swing.component.SOMControlPanelTable;

import br.com.ibmp.som.swing.vo.SOMLearningVO;

/**
 * Class for viewing the sample vector.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public class SOMSampleView extends JInternalFrame {

  /** Frame constants. */
  private static final String TITLE        = "sampleview.title";
  private static final String NEW_LEARNING = "label.new_learning";
  
  private static final long serialVersionUID = -1021319612745093426L;
  
  /** Current sample name. */
  private String sampleName;
  
  /** Current sample vector. */
  private SampleVectorInterface sampleVector;
  
  /** Bundle that contains language informations. */
  private ResourceBundle resourceBundle;
  
  /** Main learning table to be appended. */
  private SOMControlPanelTable learningTable;
  
  /** Main learning list to be appended. */
  private List<SOMLearningVO> learningList;
  
  /**
   * Contructor.
   * 
   * @param sampleVector    - Current sample vector. 
   * @param sampleName      - Current sample name.
   * @param resourceBundle  - Bundle that contains language informations.
   * @param learningTable   - Main learning table to be appended.
   * @param learningList    -  Main learning list to be appended.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMSampleView(SampleVectorInterface sampleVector, String sampleName, 
    ResourceBundle resourceBundle, SOMControlPanelTable learningTable,
    List<SOMLearningVO> learningList) {
    super(resourceBundle.getString(TITLE), true, true, true, true);
	
    JTable table;
    JButton exit;
    JPanel toolBar;
    JPanel panelTable;
    JButton newLearning;
    
    this.sampleVector   = sampleVector;
    this.resourceBundle = resourceBundle;
    this.sampleName     = sampleName;
    
    this.learningTable  = learningTable;
    this.learningList   = learningList;
    
    newLearning = new JButton(resourceBundle
      .getString(NEW_LEARNING));
    
    newLearning.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SOMLearningVO learningVO;
        SOMNewLearningDialog learningDialog;
        
        learningDialog = new SOMNewLearningDialog((SampleVectorFile)
          SOMSampleView.this.sampleVector, SOMSampleView.this.sampleName, 
          SOMSampleView.this.resourceBundle);
        
        if ((learningVO = learningDialog.showDialog()) != null) {
          SOMSampleView.this.learningList.add(learningVO);
          SOMSampleView.this.learningTable.addRow(learningVO.getName());
        }
      }
    });
    
    exit = new JButton(resourceBundle.getString(SOMAboutDialog.ABOUT_CLOSE));

    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    toolBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    toolBar.add(newLearning);
    toolBar.add(exit);
    
    panelTable = new JPanel(new BorderLayout());
    table = new JTable(new SOMTableModel());
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    loadHeader(table, sampleVector);
    loadRecords(table, sampleVector);

    panelTable.add(table.getTableHeader(), BorderLayout.NORTH);
    panelTable.add(table, BorderLayout.CENTER);    
    
    add(toolBar, BorderLayout.PAGE_END);
    add(new JScrollPane(panelTable), BorderLayout.CENTER);
    setSize(300, 300);
    
    setVisible(true);
  }
  
  /**
   * Assessor for loading the table header.
   * 
   * @param table        - Current table.
   * @param sampleVector - Sample that contains all table information.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  private final void loadHeader(JTable table, SampleVectorInterface sampleVector) {
    List<String> sampleHeader;
    
    sampleHeader = sampleVector.getHeader();
    ((DefaultTableModel)table.getModel()).setColumnIdentifiers(sampleHeader.toArray());
  }
  
  /**
   * Assessor for loading the table records.
   * 
   * @param table        - Current table.
   * @param sampleVector - Sample that contains all table information.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  private final void loadRecords(JTable table, SampleVectorInterface sampleVector) {
    int i, j, j1;
    SOMElementVO element;
    Object[] row;
    
    row = null;
    for (i = 0; i < sampleVector.getRowSize(); i++) {
      row = new Object[sampleVector.getColSize() + 2];
      element = sampleVector.getElement(i);
      row[0] = element.getName();
      row[1] = element.getDescription();
      
      for (j = 0, j1 = 2; j < element.getNumberOfValues(); j++, j1++)
        row[j1] = (element.getValue(j) == null)?"":element.getValue(j).toString();

      ((DefaultTableModel)table.getModel()).addRow(row);
    }
  }
}
