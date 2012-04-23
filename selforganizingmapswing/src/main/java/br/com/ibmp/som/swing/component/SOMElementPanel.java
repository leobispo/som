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

import java.awt.Dimension;
import java.awt.BorderLayout;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.ibmp.som.matrix.vo.SOMElementVO;
import br.com.ibmp.som.matrix.vo.WeightElementVO;

/**
 * Class that show informations about an especific cluster.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public class SOMElementPanel extends JPanel {

  /** Panel constants. */
  private static final long serialVersionUID = -8141125294424723253L;
  
  /** Scroll panel that contains the main table. */
  private JScrollPane scrollPane;
  
  /**
   * Contructor.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   *
   */
  public SOMElementPanel() {
    
    setLayout(new BorderLayout());
    scrollPane = null;
    setPreferredSize(new Dimension(230,300));

    setVisible(false);
    
  }

  /**
   * Assessor for changing the element from this element panel.
   * 
   * @param elementVO - Element to be shown.
   * @param header    - The header table.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public void changeElement(WeightElementVO elementVO, List<String> header) {
    
    int i;
    JTable table;
    Object row[];
    JPanel mainPanel;
    
    setVisible(false);
    
    if (scrollPane != null)
      remove(scrollPane);
    
    table = new JTable(new SOMTableModel());
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    ((DefaultTableModel)table.getModel()).setColumnIdentifiers(header.toArray());

    if (elementVO.hasMembers()) {
     
      for (SOMElementVO element : elementVO.getGroup()) {
        
        row = new Object[element.getNumberOfValues() + 2];
        row[0] = element.getName();
        row[1] = element.getDescription();
        for (i = 0; i < element.getNumberOfValues(); i++)
          row[i+2] = element.getValue(i);
        
        ((DefaultTableModel)table.getModel()).addRow(row);
        
      }
    
    }
    
    mainPanel = new JPanel(new BorderLayout());
    
    mainPanel.add(table, BorderLayout.CENTER);
    mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);

    mainPanel.setVisible(true);

    scrollPane = new JScrollPane(mainPanel);
    add(scrollPane, BorderLayout.CENTER);
    setVisible(true);
    
  }
  
}
