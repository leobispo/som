package br.com.ibmp.som.swing.components.external;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ResourceBundle;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/** 
 * <p><b>Title:</b> SortHeaderRenderer.java</p> 
 * <p><b>Description:</b> 
 * Menu component that handles the functionality expected of a standard 
 * "Windows" menu for MDI applications. 
 * <p> 
 * 
 * ----------------------------------------------------------------------------- 
 * 
 * Portions created by Gerald Nunn are Copyright &copy; 
 * Gerald Nunn, originally made available at 
 * http://www.javaworld.com/javaworld/jw-05-2001/jw-0525-mdi.html</p> 
 * 
 * ----------------------------------------------------------------------------- 
 * 
 * @author Gerald Nunn, cwilper@cs.cornell.edu 
 * @version $Id: WindowMenu.java,v 1.6 2005/04/21 13:51:17 rlw Exp $ 
 */ 
public final class WindowMenu extends JMenu { 

  private static final long serialVersionUID = -9174790243804541821L;
  
  private final String MENU_CASCADE    = "main.menu.cascade";
  private final String MENU_TILE       = "main.menu.tile";
  private final String MENU_MINALL     = "main.menu.min_all";
  private final String MENU_CONTROL    = "main.menu.control";
  private final String MENU_RESTOREALL = "main.menu.restore_all";

  private JPanel control;
  
  private MDIDesktopPane desktop; 
  private JMenuItem cascade; 
  private JMenuItem tile; 
  private JMenuItem minAll; 
  private JMenuItem restoreAll; 
  private JMenuItem controlPanel;

  public WindowMenu(MDIDesktopPane desktop, String name, JPanel control, 
    ResourceBundle resourceBundle) { 

    super(name); 
    
    this.control = control;
    
    controlPanel = new JMenuItem(resourceBundle.getString(MENU_CONTROL));
    cascade      = new JMenuItem(resourceBundle.getString(MENU_CASCADE)); 
    tile         = new JMenuItem(resourceBundle.getString(MENU_TILE)); 
    minAll       = new JMenuItem(resourceBundle.getString(MENU_MINALL)); 
    restoreAll   = new JMenuItem(resourceBundle.getString(MENU_RESTOREALL));
    controlPanel = new JMenuItem(resourceBundle.getString(MENU_CONTROL));
    	
    this.desktop = desktop; 
  
    controlPanel.addActionListener(new ActionListener() { 

        public void actionPerformed(ActionEvent ae) { 

          WindowMenu.this.control.setVisible(true);
          
        } 

      }); 

    cascade.addActionListener(new ActionListener() { 

      public void actionPerformed(ActionEvent ae) { 

        WindowMenu.this.desktop.cascadeFrames(); 
      } 

    }); 
    
    tile.addActionListener(new ActionListener() { 

      public void actionPerformed(ActionEvent ae) { 

        WindowMenu.this.desktop.tileFrames(); 

      } 

    }); 

    minAll.addActionListener(new ActionListener() { 

      public void actionPerformed(ActionEvent ae) { 

        WindowMenu.this.desktop.minimizeFrames(); 

      } 

    }); 

    restoreAll.addActionListener(new ActionListener() { 

      public void actionPerformed(ActionEvent ae) { 

        WindowMenu.this.desktop.restoreFrames(); 

      } 

    }); 
    
    addMenuListener(new MenuListener() { 

      public void menuCanceled (MenuEvent e) {} 

      public void menuDeselected (MenuEvent e) { 

        removeAll(); 

      } 

      public void menuSelected (MenuEvent e) { 

        buildChildMenus(); 
    
      } 

    }); 

  } 

  private final void buildChildMenus() { 

    int i; 
    JInternalFrame[] allFrames;
    ChildMenuItem menu;
    
    allFrames = desktop.getAllFrames(); 

    add(controlPanel);
    add(cascade); 
    add(tile); 
    add(minAll); 
    add(restoreAll); 

    if (allFrames.length > 0) 
      addSeparator(); 

    cascade.setEnabled(allFrames.length > 0); 
    tile.setEnabled(allFrames.length > 0); 
    minAll.setEnabled(desktop.deIconifiedFrames() > 0); 
    restoreAll.setEnabled(desktop.iconifiedFrames()>0); 

 
    for (i = 0; i < allFrames.length; i++) { 

      menu = new ChildMenuItem(allFrames[i]); 
      menu.setState(i == 0); 

      menu.addActionListener(new ActionListener() { 

        public void actionPerformed(ActionEvent ae) { 

          JInternalFrame frame = ((ChildMenuItem) ae.getSource()).getFrame(); 
          frame.moveToFront(); 

          try { 

            frame.setSelected(true); 

          } 
          catch (PropertyVetoException e) {} 

        } 

      }); 

      menu.setIcon(allFrames[i].getFrameIcon()); 
      add(menu); 

    } 

  } 

  class ChildMenuItem extends JCheckBoxMenuItem { 

    private static final long serialVersionUID = 1678574821224411966L;

    private JInternalFrame frame; 

    public ChildMenuItem(JInternalFrame frame) { 

      super(frame.getTitle()); 
      this.frame=frame; 

    } 

    public JInternalFrame getFrame() { 

      return frame; 

    } 

  } 

} 