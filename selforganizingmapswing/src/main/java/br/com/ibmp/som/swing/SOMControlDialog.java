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
import java.awt.GridLayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.JProgressBar;
import javax.swing.SpinnerNumberModel;

import br.com.ibmp.som.exception.SOMException;

import br.com.ibmp.som.listener.SelfOrganizingMapListener;

import br.com.ibmp.som.matrix.WeightMatrix;

import br.com.ibmp.som.swing.component.SOMControlPanelTable;

import br.com.ibmp.som.swing.vo.SOMClusterVO;
import br.com.ibmp.som.swing.vo.SOMLearningVO;

/**
 * This class will show the learning control dialog.
 * 
 * @author Leonardo Bispo de Oliveira and Daniele Sunaga de Oliveira.
 * @version 1.0
 *
 */
public class SOMControlDialog extends JDialog
  implements SelfOrganizingMapListener {

  /** Dialog constants. */
  private static final long serialVersionUID = 5975247642391975552L;
  
  private JButton btnPlay;
  private JButton btnStop;
  private JButton btnPause;
  private JButton btnFf;
  private JButton btnClose;
  private JButton btnCluster;
  
  private WeightMatrix currentMatrix;

  private List<SOMClusterVO> clusterList;
  private SOMControlPanelTable clusterTable; 
  
  private JProgressBar progress;
  
  private JSpinner spnIter;
  private SOMLearningVO learningVO;
  
  private ResourceBundle resourceBundle;
  
  public SOMControlDialog(SOMLearningVO learningVO,
    ResourceBundle resourceBundle, List<SOMClusterVO> clusterList,
    SOMControlPanelTable clusterTable) {
    super(new JFrame(), "Learning Control panel", true);

    int xl, yl, xs, ys;
    Dimension size;
   
    JLabel label;

    JPanel toolBar;
    JPanel controlPanel;
    JPanel buttonsPanel;
    JPanel iterationPanel;
    SpinnerModel spinnerModel;
  
    this.learningVO     = learningVO;
    this.clusterList    = clusterList;
    this.clusterTable   = clusterTable;
     this.resourceBundle = resourceBundle;
    
    toolBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    btnClose   = new JButton("Close");
    btnCluster = new JButton("New Cluster");
    
    btnCluster.setEnabled(false);
    
    btnClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        SOMControlDialog.this.learningVO.getSom().removeListener(SOMControlDialog.this);
        dispose();
      }
    });
    
    btnCluster.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        SOMClusterVO clusterVO;
        
        if ((clusterVO = (new SOMNewClusterDialog(SOMControlDialog.this.resourceBundle, 
          SOMControlDialog.this.learningVO, currentMatrix)).showDialog()) != null) {
          SOMControlDialog.this.clusterTable.addRow(clusterVO.getClusterName());
          SOMControlDialog.this.clusterList.add(clusterVO);
          clusterVO.setIteration(progress.getValue());
        }
      }
    });
    
    toolBar.add(btnCluster);
    toolBar.add(btnClose);
    
    getContentPane().add(toolBar, BorderLayout.SOUTH);
    
    learningVO.getSom().addListener(this);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE );
    
    getContentPane().add(toolBar, BorderLayout.SOUTH);
    
    controlPanel = new JPanel(new GridLayout(3, 1));
    
    iterationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    label = new JLabel("Iterations:");
    iterationPanel.add(label);
    spinnerModel = new SpinnerNumberModel(20, 20, 5000, 1);
    spnIter = new JSpinner(spinnerModel);
    spnIter.setPreferredSize(new Dimension(80, 21));
    iterationPanel.add(spnIter);

    controlPanel.add(iterationPanel);

    progress = new JProgressBar();
    progress.setValue(0);
    progress.setStringPainted(true);
    
    controlPanel.add(progress);
    
    buttonsPanel = new JPanel(new GridLayout(1, 4));
    
    btnPlay = new JButton("Play");
    buttonsPanel.add(btnPlay);
    btnFf = new JButton("Step");
    buttonsPanel.add(btnFf);
    btnPause = new JButton("Pause");
    buttonsPanel.add(btnPause);
    btnStop = new JButton("Stop");
    buttonsPanel.add(btnStop);
    
    btnStop.setEnabled(false);
    btnPause.setEnabled(false);
    
    btnPlay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int iteration;

        currentMatrix = null;
        btnStop.setEnabled(true);
        btnPause.setEnabled(true);
        btnFf.setEnabled(false);
        btnPlay.setEnabled(false);
        spnIter.setEnabled(false);
        btnClose.setEnabled(false);
        btnCluster.setEnabled(false);

        iteration = ((SpinnerNumberModel)spnIter.getModel()).getNumber().intValue(); 

        if (progress.getValue() == 0) {
          progress.setMaximum(iteration);
          (new SOMControlThread(SOMControlDialog.this.learningVO,
            iteration)).start();
        }
        else
          (new SOMControlThread(SOMControlDialog.this.learningVO)).start();
      }
    });
    
    btnStop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          SOMControlDialog.this.learningVO.getSom().stopLearn();
        } 
        catch (SOMException e1) {
          System.out.println("[ERROR] Problems with learning process");
          e1.printStackTrace();
          System.exit(1);
        }
      }
    });
    
    btnPause.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          SOMControlDialog.this.learningVO.getSom().pauseLearn();
          btnStop.setEnabled(false);
          btnPause.setEnabled(false);
          btnFf.setEnabled(true);
          btnPlay.setEnabled(true);
          btnClose.setEnabled(true);
          btnCluster.setEnabled(true);
        } 
        catch (SOMException e1) {
          System.out.println("[ERROR] Problems with learning process");
          e1.printStackTrace();
          System.exit(1);
        }        
      }
    });
    
    btnFf.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int iteration;
        
        try {
          if (progress.getValue() != 0) {
            SOMControlDialog.this.learningVO.getSom().stepLearn();
          }
          else {
            iteration = ((SpinnerNumberModel)spnIter.getModel()).getNumber().intValue(); 
            progress.setMaximum(iteration);
            SOMControlDialog.this.learningVO.getSom()
              .setIterationNumber(iteration);

            SOMControlDialog.this.learningVO.getSom().stepLearn(
              SOMControlDialog.this.learningVO.getWidth(),
              SOMControlDialog.this.learningVO.getHeight());
          }
        }
        catch (SOMException e1) {
          System.out.println("[ERROR] Problems with learning process");
          e1.printStackTrace();
          System.exit(1);

        }
        
        btnCluster.setEnabled(true); 
      }
    });
  
    controlPanel.add(buttonsPanel);
    
    getContentPane().add(controlPanel, BorderLayout.CENTER);

    pack();
    
    xs = getWidth(); 
    ys = getHeight();
    size = getToolkit().getScreenSize(); 

    xl = (size.width/2) - (xs/2); 
    yl = (size.height/2) - (ys/2); 

    setBounds(xl, yl, xs, ys);

    setResizable(false);
      
    setAlwaysOnTop(true);
    setVisible(true);
  }

  public void end(WeightMatrix matrix, int iteration) {
    btnStop.setEnabled(false);
    btnPause.setEnabled(false);
    btnFf.setEnabled(true);
    btnPlay.setEnabled(true);
    spnIter.setEnabled(true);
    btnClose.setEnabled(true);
    btnCluster.setEnabled(true);
    
    currentMatrix = matrix; 
  }

  public void stepEnd(WeightMatrix matrix, int iteration) {
    currentMatrix = matrix;
    progress.setValue(iteration);
  }
}

class SOMControlThread extends Thread {

  private SOMLearningVO learningVO;
  private int iteration;
  
  public SOMControlThread(SOMLearningVO learningVO, int iteration) {
    this.learningVO = learningVO;
    this.iteration  = iteration;
  }
  
  public SOMControlThread(SOMLearningVO learningVO) {
    this.learningVO = learningVO;
    this.iteration  = -1;
  }
  
  public void run() {
    try {
      if (iteration != -1) {
        learningVO.getSom().setIterationNumber(iteration);
      
        learningVO.getSom().startLearn(learningVO.getWidth(),
          learningVO.getHeight());
      }
      else
        learningVO.getSom().resumeLearn();
    } 
    catch (SOMException e1) {
      System.err.println("[ERROR] Problems with Learning process");
      e1.printStackTrace();
      System.exit(1);
    }
  }  
}
