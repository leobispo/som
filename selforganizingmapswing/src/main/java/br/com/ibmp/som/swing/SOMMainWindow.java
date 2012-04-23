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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.ibmp.som.exception.SOMException;
import br.com.ibmp.som.matrix.SampleVectorFile;
import br.com.ibmp.som.swing.component.SOMArrestedPanel;
import br.com.ibmp.som.swing.component.SOMControlPanelTable;
import br.com.ibmp.som.swing.component.SOMFileFilter;
import br.com.ibmp.som.swing.component.listener.SOMClickTableListener;
import br.com.ibmp.som.swing.components.external.MDIDesktopPane;
import br.com.ibmp.som.swing.components.external.WindowMenu;
import br.com.ibmp.som.swing.internalframe.SOMClusterView;
import br.com.ibmp.som.swing.internalframe.SOMSampleView;
import br.com.ibmp.som.swing.vo.SOMClusterVO;
import br.com.ibmp.som.swing.vo.SOMLearningVO;

public final class SOMMainWindow extends JFrame {
 
  private static final long serialVersionUID = -6600855528744374682L;
  
  public static final int   DEFAULT_ITERATION = 10;
  public static final Color DESKTOP_COLOR     = new Color(46, 97, 116);

  public final static String MENU_FILE        = "main.menu.file";
  public final static String MENU_OPEN_SAMPLE = "main.menu.file.open_sample";
  public final static String MENU_WINDOW      = "main.menu.window";
  public final static String MENU_HELP        = "main.menu.help";
  public final static String MENU_EXIT        = "main.menu.file.exit";
  public final static String MENU_ABOUT       = "main.menu.help.about";
  public final static String TABLE_CLUSTER    = "main.tables.cluster_table";
  public final static String TABLE_SAMPLE     = "main.tables.sample_table";
  public final static String TABLE_LEARN      = "main.tables.learn_table";
  public final static String LEARN_CONTROL    = "main.control.learn_control";
  public final static String ERROR_FILE_OPEN  = "main.openfile.error";
  public final static String ERROR_ALERT      = "main.error";
  
  private final static String RESOURCE_NAME   = "/resource.properties";
  public final static String APP_NAME         = "main.app_name";
  
  private static final String ICON_IMAGE      = "/images/iconpic.gif";
  private List<SampleVectorFile> sampleList;
  private List<SOMLearningVO>   learningList;
  private List<SOMClusterVO>    clusterList;
  
  private ResourceBundle   resourceBundle;
  private MDIDesktopPane   desktop;
  private SOMAboutDialog   aboutDialog;
  private SOMArrestedPanel arrestedPanel;
  
  private SOMControlPanelTable clusterTable;
  private SOMControlPanelTable sampleTable;
  private SOMControlPanelTable learningTable;
   
  public SOMMainWindow() {
    SOMSplashScreen splash;
    int xl, yl, xs, ys; 
    Dimension size; 
    File resourceFile;
    
    resourceFile = new File(RESOURCE_NAME);
    
    if (!resourceFile.exists()) {
      System.err.println("[ERROR] Resource file does not exists!");
      System.exit(1);
    }
    
    try {
      resourceBundle = new PropertyResourceBundle(
        new FileInputStream(resourceFile));
    } 
    catch (Exception e) {
      System.err.println("[ERROR] Resource cannot be opened!");
      e.printStackTrace();
      System.exit(1);
    }
    
    sampleList   = new ArrayList<SampleVectorFile>();
    learningList = new ArrayList<SOMLearningVO>();
    clusterList  = new ArrayList<SOMClusterVO>();
    
    splash = new SOMSplashScreen();
    setTitle(resourceBundle.getString(APP_NAME));
    
    aboutDialog = new SOMAboutDialog(resourceBundle);

    size = this.getToolkit().getScreenSize(); 

    addWindowListener(new WindowAdapter() {
    	
      public void windowClosing(WindowEvent e) {
        dispose(); 
        System.exit(0); 
      } 
    }); 

    setIconImage((new ImageIcon(ICON_IMAGE)).getImage());

    xs = 850;
    ys = 655;
    xl = (size.width/2) - (xs/2); 
    yl = (size.height/2) - (ys/2); 

    setBounds(xl, yl, xs, ys);

    loadMDIPanel();
    loadArrestedPanel();
    loadMainMenu();
    
    setVisible(true);
    
    try {
      Thread.sleep(1000);
    } 
    catch (InterruptedException e) {
      System.err.println("[ERROR] Problems with thread synchornization!");
      e.printStackTrace();
      System.exit(1);
    }

    splash.setVisible(false);
  }
	
  private final void loadArrestedPanel() {
    arrestedPanel = new SOMArrestedPanel();
    getContentPane().add(arrestedPanel, BorderLayout.WEST);
    
    clusterTable  = new SOMControlPanelTable();
    sampleTable   = new SOMControlPanelTable();
    learningTable = new SOMControlPanelTable();
    
    sampleTable.addClickListener(new SOMClickTableListener(){

      public void doubleClick(int row, int col, Point point) {
        desktop.add(new SOMSampleView(sampleList.get(row), 
          (String) sampleTable.getValueAt(row, col), 
          resourceBundle, learningTable, learningList));
      }

      public void rightClick(int row, int col, Point point) {}

      public void leftClick(int row, int col, Point point) {}
    });
    
    learningTable.addClickListener(new SOMClickTableListener() {
      public void leftClick(int row, int col, Point point) {}
      
      public void rightClick(int row, int col, Point point) {}
      
      public void doubleClick(int row, int col, Point point) {
        new SOMControlDialog(learningList.get(row), resourceBundle, clusterList, 
          clusterTable);
      }
    });

    clusterTable.addClickListener(new SOMClickTableListener() {
      public void leftClick(int row, int col, Point point) {}

      public void rightClick(int row, int col, Point point) {}

      public void doubleClick(int row, int col, Point point) {
        desktop.add(new SOMClusterView(clusterList.get(row), 
          resourceBundle));
      }
      
    });
    
    arrestedPanel.addComponent(resourceBundle.getString(TABLE_SAMPLE), 
      new JScrollPane(sampleTable));

    arrestedPanel.addComponent(resourceBundle.getString(TABLE_LEARN), 
      new JScrollPane(learningTable));      

    arrestedPanel.addComponent(resourceBundle.getString(TABLE_CLUSTER), 
      new JScrollPane(clusterTable));
  }
    
  private final void loadMDIPanel() {
    JPanel mainPanel;
   
    mainPanel = new JPanel(); 
    mainPanel.setLayout(new BorderLayout()); 

    desktop = new MDIDesktopPane(); 
    desktop.setBackground(DESKTOP_COLOR); 
    desktop.setVisible(true); 

    mainPanel.add(new JScrollPane(desktop), BorderLayout.CENTER);
    getContentPane().add(mainPanel, BorderLayout.CENTER); 
  }

  private final void loadMainMenu() {
    JMenu menu;
    JMenuBar menuBar;
    JMenuItem menuItem;
    
    menuBar = new JMenuBar();
    
    menu = new JMenu(resourceBundle.getString(MENU_FILE)); 

    menuItem = new JMenuItem(resourceBundle.getString(MENU_OPEN_SAMPLE)); 

    menuItem.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        JFileChooser chooser;
        String file;
        SampleVectorFile sample;

        chooser = new JFileChooser();
        chooser.setFileFilter(new SOMFileFilter());
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(SOMMainWindow.this) == JFileChooser.APPROVE_OPTION) {
          file = chooser.getSelectedFile().getAbsoluteFile().toString();
          
          try {
            sample = new SampleVectorFile(file);
            sampleList.add(sample);
            sampleTable.addRow(chooser.getSelectedFile().getAbsoluteFile().toString());
          }
          catch (SOMException e1) {
            JOptionPane.showMessageDialog(SOMMainWindow.this, 
              resourceBundle.getString(ERROR_FILE_OPEN),
              resourceBundle.getString(ERROR_ALERT), 
              JOptionPane.ERROR_MESSAGE);
            
            sample = null;
          }
        }
      } 
    }); 

    menu.add(menuItem);

/*    menuItem = new JMenuItem("New Learning"); 

    menuItem.addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent e) {
 
        int i;
        SOMLearningVO learningVO;
        
        String str[];
        
        str = new String[sampleTable.getRowCount() + 1];
        str[0] = "-- Select --";
        for (i = 0; i < sampleTable.getRowCount(); i++) {

          str[i+1] = (String) sampleTable.getValueAt(i, 0);
          
        }

        if ((learningVO = (new SOMNewLearningDialog(sampleList, str, 
          resourceBundle)).showDialog()) != null) {
          
          learningList.add(learningVO);
          learningTable.addRow(learningVO.getName());
          
        }
        
      }
    
    });

    menu.add(menuItem);

    menuItem = new JMenuItem("New Cluster"); 

    menuItem.addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent arg0) {

        new SOMNewClusterDialog();
        
      }});

    menu.add(menuItem);

    
    menu.addSeparator();
*/    
    menuItem = new JMenuItem(resourceBundle.getString(MENU_EXIT)); 

    menuItem.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        dispose(); 
        System.exit(0); 
      } 
    }); 

    menu.add(menuItem);
    menuBar.add(menu);

    menuBar.add(new WindowMenu(desktop, resourceBundle.getString(MENU_WINDOW), 
      arrestedPanel, resourceBundle));
    
    menu = new JMenu(resourceBundle.getString(MENU_HELP)); 

    menuItem = new JMenuItem(resourceBundle.getString(MENU_ABOUT)); 

    menuItem.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        aboutDialog.setVisible(true);
      } 
    }); 

    menu.add(menuItem);
    menuBar.add(menu);
    
    setJMenuBar(menuBar);
  }
  
  public static void main(String[] args) {
    new SOMMainWindow();
  }
}
