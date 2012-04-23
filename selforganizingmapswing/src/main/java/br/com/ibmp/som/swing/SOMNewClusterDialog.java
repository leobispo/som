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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import br.com.ibmp.som.exception.SOMException;
import br.com.ibmp.som.matrix.WeightMatrix;
import br.com.ibmp.som.matrix.vo.WeightElementVO;
import br.com.ibmp.som.swing.components.external.AbsoluteLayout;
import br.com.ibmp.som.swing.vo.SOMClusterVO;
import br.com.ibmp.som.swing.vo.SOMLearningVO;

/**
 * This Class will present the dialog for new cluster creation.
 *
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class SOMNewClusterDialog extends JDialog {  

  /** Dialog constanst. */
  private static final long serialVersionUID = 2535554265941253356L;
  
  private static final String CLUSTER_TITLE   = "label.new_cluster";
  private static final String CLUSTER_IMAGE   = "/images/cluster_process.gif";
  private static final String NAME_ERROR      = "cluster.name.error";
  private static final String SAVE_BUTTON     = "button.save";
  private static final String CANCEL_BUTTON   = "button.cancel";
  private static final String NAME_LABEL      = "label.name";
  private static final String SAMPLE_LABEL    = "label.sample";
  private static final String LEARNING_LABEL  = "label.learning";
  
  /** List of samples. */
  private JComboBox cmbSample;
  
  /** List of leanings. */
  private JComboBox cmbLearning;
  
  /** Cluster name. */
  private JTextField txtName;
  
  /** Contains information about the learning process used to create this Cluster. */
  private SOMLearningVO learningVO;
  
  /** This cluster information. */
  private SOMClusterVO clusterVO;
  
  /** The weight matrix for this cluster. */
  private WeightMatrix matrix;
  
  /** Bundle that contains the language informations. */
  private ResourceBundle resourceBundle;
  
  /** Say that we have just unique learning. Don't show the comboboxes. */
  boolean uniqueLearning;
  
  /**
   * Constructor. 
   * 
   * @param resourceBundle - Bundle that contains the language informations.
   * @param learningVO     - Contains information about the learning process 
   *                         used to create this Cluster.     
   * @param matrix         - The weight matrix for this cluster.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   * 
   */
  public SOMNewClusterDialog(ResourceBundle resourceBundle, SOMLearningVO learningVO,
    WeightMatrix matrix) {
    
    super(new JFrame(), resourceBundle.getString(CLUSTER_TITLE), true);

    String samples[], learnings[];
    
    samples = new String[1];
    samples[0] = learningVO.getSampleName();
    
    learnings = new String[1];
    learnings[0] = learningVO.getName();    

    
    this.resourceBundle = resourceBundle;
    this.learningVO     = learningVO;
    this.matrix         = matrix;
    
    uniqueLearning = true;
    
    startGui(samples, learnings);
    
  }
  
  /** 
   * Assessor for GUI creation (Sorry about the method size. But this method
   * will just create the Dialog components).
   * 
   * @param samples   - Sample names.
   * @param learnings - Learning names.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   *  
   */
  private final void startGui(String samples[], String learnings[]) {

    int xl, yl, xs, ys;
    Dimension size;
    JPanel controlPanel, mainPanel;
    JButton cancelButton, saveButton;
    JLabel label;
    
    ComboBoxModel sampleModel;
        
    clusterVO = null;
    
    controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    saveButton   = new JButton(resourceBundle.getString(SAVE_BUTTON));
    cancelButton = new JButton(resourceBundle.getString(CANCEL_BUTTON));
    
    cancelButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent arg0) {

        dispose();
        
      }
      
    });
    
    saveButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {

        WeightElementVO[][] weightVO;
        
        weightVO = null;
         
        if (txtName.getText().equals("")) {
          
          JOptionPane.showMessageDialog(SOMNewClusterDialog.this, 
              SOMNewClusterDialog.this.resourceBundle.getString(NAME_ERROR),
              SOMNewClusterDialog.this.resourceBundle.getString(SOMMainWindow.ERROR_ALERT),
              JOptionPane.ERROR_MESSAGE);
          return;
          
        }
        
        if (uniqueLearning) {
          
          try {
            
            weightVO = matrix.mountUMatrix(SOMNewClusterDialog.this
              .learningVO.getSom().getSample());
            
          } 
          catch (SOMException e1) {
            
            System.err.println("[ERROR] Error when I try to mount the groups.");
            e1.printStackTrace();
            System.exit(1);
            
          }
 
          clusterVO = new SOMClusterVO(txtName.getText(), (String) cmbLearning.getSelectedItem(),
            (String) cmbSample.getSelectedItem(), weightVO);
          
          clusterVO.setHeader(SOMNewClusterDialog.this.learningVO.getSom()
            .getSample().getHeader());
          
        }

        dispose();
      
      }
      
    });
    
    controlPanel.add(saveButton);
    controlPanel.add(cancelButton);
    
    getContentPane().add(controlPanel, BorderLayout.SOUTH);
    
    mainPanel = new JPanel(new AbsoluteLayout());
    
    label = new JLabel(new ImageIcon(CLUSTER_IMAGE));    
    label.setBounds(7, 7, 154, 243);
    label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));   
    mainPanel.add(label);    

    label = new JLabel(resourceBundle.getString(NAME_LABEL));
    label.setBounds(175, 20, 35, 21);
    mainPanel.add(label);

    label = new JLabel(resourceBundle.getString(SAMPLE_LABEL));
    label.setBounds(175, 60, 35, 21);
    mainPanel.add(label);

    label = new JLabel(resourceBundle.getString(LEARNING_LABEL));
    label.setBounds(175, 100, 35, 21);
    mainPanel.add(label);

    txtName = new JTextField();
    txtName.setBounds(245, 20, 300, 21);
    txtName.setPreferredSize( new Dimension(300, 21));
    mainPanel.add(txtName);
    
    sampleModel = new DefaultComboBoxModel(samples);
    cmbSample = new JComboBox();
    cmbSample.setModel(sampleModel);
    cmbSample.setBounds(245, 60, 300, 21);
    cmbSample.setPreferredSize(new Dimension(300, 21));
    mainPanel.add(cmbSample);

    sampleModel = new DefaultComboBoxModel(learnings);
    cmbLearning = new JComboBox();
    cmbLearning.setModel(sampleModel);
    cmbLearning.setBounds(245, 100, 300, 21);
    cmbLearning.setPreferredSize(new Dimension(300, 21));
    mainPanel.add(cmbLearning);

    if (uniqueLearning) {
      
      cmbSample.setEnabled(false);
      cmbLearning.setEnabled(false);
      
    }
    
    getContentPane().add(mainPanel, BorderLayout.CENTER);
    
    setSize(577, 443);
    setResizable(false);
    setAlwaysOnTop(true);
    setModal(true);

    xs = getWidth(); 
    ys = getHeight();
    size = getToolkit().getScreenSize(); 

    xl = (size.width/2) - (xs/2); 
    yl = (size.height/2) - (ys/2); 

    setBounds(xl, yl, xs, ys);
    
  }
  
  /**
   * Assessor for show the dialog and create the new cluster VO.
   * 
   * @return The cluster VO, if everything is ok or null.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public final SOMClusterVO showDialog() {
    
    setVisible(true);

    return clusterVO;
    
  }

}
