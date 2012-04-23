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
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SpinnerModel;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultComboBoxModel;
import br.com.ibmp.som.SelfOrganizingMap;

import br.com.ibmp.som.exception.SOMException;

import br.com.ibmp.som.matrix.SampleVectorFile;

import br.com.ibmp.som.swing.components.external.AbsoluteLayout;

import br.com.ibmp.som.swing.vo.SOMLearningVO;

/**
 * This class will show the learning creation dialo.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
 * @version 1.0
 *
 */
public final class SOMNewLearningDialog extends JDialog {  

  /** Class constants. */
  private static final String NAME_ERROR      = "learning.name.error";
  private static final String SAMPLE_ERROR    = "learning.sample.error";
  private static final String LEARNING_TITLE  = "learning.title";
  private static final String SAVE_BUTTON     = "button.save";
  private static final String CANCEL_BUTTON   = "button.cancel";
  private static final String NAME_LABEL      = "label.name";
  private static final String SAMPLE_LABEL    = "label.sample";
  private static final String DISTANCE_LABEL  = "label.distance_method";
  private static final String NEIGHBORS_LABEL = "label.neighbors_method";
  private static final String EUCLIDEAN_LABEL = "label.euclidean_method";
  private static final String GAUSSIAN_LABEL  = "label.gaussian_method";
  private static final String WIDTH_LABEL     = "label.width";
  private static final String HEIGHT_LABEL    = "label.height";
  
  private static final long serialVersionUID = -3575562545127225731L;

  /** Contain all sample lists that should be used for learning creation. */
  private List<SampleVectorFile> sampleList;
  
  /** Indicate if the sample to be used was passed with parameter. */
  private boolean uniqueSample;
  
  /** Combobox that will show all samples to generate the learning. */
  private JComboBox cmbSample;
  
  /** Learning name. */
  private JTextField txtName;
  
  /** Bundle that contains language informations. */
  private ResourceBundle resourceBundle;
  
  /** Contains the width information. */
  private JSpinner spnWidth;
  
  /** Contains the height information. */
  private JSpinner spnHeight;
 
  /** VO that contains information about the created learning. */
  private SOMLearningVO learningVO;
  
  /**
   * Constructor.
   * @param sample         - Unique application sample.
   * @param sampleName     - Sample name.
   * @param resourceBundle - Bundle that contains language informations.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public SOMNewLearningDialog(SampleVectorFile sample, 
    String sampleName, ResourceBundle resourceBundle) {
    super(new JFrame(), resourceBundle.getString(LEARNING_TITLE), 
      true);

    String samples[];
    
    sampleList = new ArrayList<SampleVectorFile>(1);
    sampleList.add(sample);
    
    samples = new String[1];
    samples[0] = sampleName;
    
    this.resourceBundle = resourceBundle;
    uniqueSample        = true;

    startGui(samples);    
  }
  
  /**
   * Constructor.
   * 
   * @param sampleList     - List of possible samples to be choosed.
   * @param samples        - Sample names.
   * @param resourceBundle - Bundle that contains language informations.
   */
  public SOMNewLearningDialog(List<SampleVectorFile> sampleList, 
    String samples[], ResourceBundle resourceBundle) {
    super(new JFrame(), resourceBundle.getString(LEARNING_TITLE), 
      true);

    this.sampleList     = sampleList;
    this.resourceBundle = resourceBundle;
    uniqueSample        = false;
    startGui(samples);
  }
  
  /**
   * Assessor for show the dialog and create the new learning VO.
   * 
   * @return The learning VO, if everything is ok or null.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira
   * 
   */
  public final SOMLearningVO showDialog() {
    setVisible(true);

    return learningVO;
  }

  /** 
   * Assessor for GUI creation (Sorry about the method size. But this method
   * will just create the Dialog components).
   * 
   * @param samples - Sample names.
   * 
   * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
   *  
   */
  private final void startGui(String samples[]) {
    int xl, yl, xs, ys;
    Dimension size;
    JRadioButton radio;
    JPanel controlPanel, mainPanel;
    JButton cancelButton, saveButton;
    JLabel label;
    
    SpinnerModel  spinnerModel;
    ComboBoxModel sampleModel;
    
    learningVO = null;
    
    sampleModel  = new DefaultComboBoxModel(samples);
    
    controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    saveButton   = new JButton(resourceBundle.getString(SAVE_BUTTON));
    cancelButton = new JButton(resourceBundle.getString(CANCEL_BUTTON));
    
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        dispose();
      }
    });
    
    saveButton.addActionListener(new ActionListener() {
      SelfOrganizingMap som;
      
      public void actionPerformed(ActionEvent arg0) {
        if (txtName.getText().equals("")) {
          
          JOptionPane.showMessageDialog(SOMNewLearningDialog.this, 
              SOMNewLearningDialog.this.resourceBundle.getString(NAME_ERROR),
              SOMNewLearningDialog.this.resourceBundle.getString(SOMMainWindow.ERROR_ALERT),
              JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (!uniqueSample) {
          if (cmbSample.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(SOMNewLearningDialog.this, 
                SOMNewLearningDialog.this.resourceBundle.getString(SAMPLE_ERROR),
                SOMNewLearningDialog.this.resourceBundle.getString(SOMMainWindow.ERROR_ALERT), 
                JOptionPane.ERROR_MESSAGE);
            return;
          }
          else {
            try {
              som = new SelfOrganizingMap(SOMMainWindow.DEFAULT_ITERATION, sampleList.get(
                cmbSample.getSelectedIndex() - 1));
            } 
            catch (SOMException e) {
              System.err.println("[ERROR] Problems when I try to create a SOM!");
              e.printStackTrace();
              System.exit(1);
            }
          }
        }
        else {
          try {
            som = new SelfOrganizingMap(SOMMainWindow.DEFAULT_ITERATION, sampleList.get(0));
          }
          catch (SOMException e) {
            System.err.println("[ERROR] Problems when I try to create a SOM!");
            e.printStackTrace();
            System.exit(1);
          }
        }
        
        learningVO = new SOMLearningVO(((SpinnerNumberModel)spnWidth.getModel()).getNumber().intValue(), 
          ((SpinnerNumberModel)spnHeight.getModel()).getNumber().intValue(),
          txtName.getText(), (String) cmbSample.getSelectedItem(), som);
        
        dispose();
      }
    });
    
    controlPanel.add(saveButton);
    controlPanel.add(cancelButton);
    
    getContentPane().add(controlPanel, BorderLayout.SOUTH);
    
    mainPanel = new JPanel(new AbsoluteLayout());
    
    label = new JLabel(new ImageIcon("/images/learning_process.gif"));    
    label.setBounds(7, 7, 154, 243);
    label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));   
    mainPanel.add(label);    

    label = new JLabel(resourceBundle.getString(NAME_LABEL));
    label.setBounds(175, 20, 35, 21);
    mainPanel.add(label);

    label = new JLabel(resourceBundle.getString(SAMPLE_LABEL));
    label.setBounds(175, 60, 35, 21);
    mainPanel.add(label);

    label = new JLabel(resourceBundle.getString(WIDTH_LABEL));
    label.setBounds(175, 100, 35, 21);
    mainPanel.add(label);

    label = new JLabel(resourceBundle.getString(HEIGHT_LABEL));
    label.setBounds(378, 100, 35, 21);
    mainPanel.add(label);
    
    label = new JLabel();
    label.setBounds(175, 140, 370, 100);
    label.setBorder(BorderFactory.createTitledBorder(
      resourceBundle.getString(DISTANCE_LABEL)));
    label.setPreferredSize(new Dimension(370,100));
 
    radio = new JRadioButton();    
    radio.setText(resourceBundle.getString(EUCLIDEAN_LABEL));
    radio.setBounds(14, 29, 300, 21);
    radio.setSelected(true);

    label.add(radio);
    mainPanel.add(label);
    
    label = new JLabel();
    label.setBounds(175, 270, 370, 100);
    label.setBorder(BorderFactory.createTitledBorder(
      resourceBundle.getString(NEIGHBORS_LABEL)));
    label.setPreferredSize(new Dimension(370,100));
    radio = new JRadioButton();    
    radio.setText(resourceBundle.getString(GAUSSIAN_LABEL));
    radio.setBounds(14, 29, 300, 21);
    radio.setSelected(true);

    label.add(radio);
    mainPanel.add(label);
 
    txtName = new JTextField();
    txtName.setBounds(245, 20, 300, 21);
    txtName.setPreferredSize(new Dimension(300, 21));
    mainPanel.add(txtName);
    
    cmbSample = new JComboBox();
    cmbSample.setModel(sampleModel);
    cmbSample.setBounds(245, 60, 300, 21);
    cmbSample.setPreferredSize(new Dimension(300, 21));
    
    if (uniqueSample)
      cmbSample.setEnabled(false);
    
    mainPanel.add(cmbSample);
    
    spinnerModel = new SpinnerNumberModel(5, 1, 50, 1);
    spnWidth = new JSpinner(spinnerModel);
    spnWidth.setBounds(245, 100, 80, 21);
    spnWidth.setPreferredSize(new Dimension(80, 21));
    mainPanel.add(spnWidth);

    spinnerModel = new SpinnerNumberModel(5, 1, 50, 1);
    spnHeight = new JSpinner(spinnerModel);
    spnHeight.setBounds(465, 100, 80, 21);
    spnHeight.setPreferredSize(new Dimension(80, 21));
    mainPanel.add(spnHeight);
    
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
}
