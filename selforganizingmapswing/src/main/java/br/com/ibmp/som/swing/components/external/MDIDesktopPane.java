package br.com.ibmp.som.swing.components.external; 

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.beans.PropertyVetoException;

import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/** 
 * <p><b>Title:</b> SortHeaderRenderer.java</p> 
 * <p><b>Description:</b> 
 * An extension of WDesktopPane that supports often used MDI functionality. This 
 * class also handles setting scroll bars for when windows move too far to the left or 
 * bottom, providing the MDIDesktopPane is in a ScrollPane. 
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
 * @version $Id: MDIDesktopPane.java,v 1.9 2005/04/21 13:51:17 rlw Exp $ 
 */ 
public class MDIDesktopPane extends JDesktopPane { 

  private static final long serialVersionUID = 1L;

  private static int FRAME_OFFSET = 20; 

  private MDIDesktopManager manager; 

  public MDIDesktopPane() { 

    manager = new MDIDesktopManager(this); 
    setDesktopManager(manager); 

    setDragMode(JDesktopPane.LIVE_DRAG_MODE); 

  } 

  public void setBounds(int x, int y, int w, int h) { 

    super.setBounds(x,y,w,h); 
    checkDesktopSize(); 

  } 

  public Component add(JInternalFrame frame) { 
 
    Point p; 
    Component retval;
    JInternalFrame[] array;

    array = getAllFrames();

    retval = super.add(frame); 
    checkDesktopSize(); 

    if (array.length > 0) { 

      p = array[0].getLocation(); 
      p.x = p.x + FRAME_OFFSET; 
      p.y = p.y + FRAME_OFFSET; 

    } 
    else {
    	
      p = new Point(0, 0); 

    } 

    frame.setLocation(p.x, p.y); 
    moveToFront(frame); 

    frame.setVisible(true); 

    try { 

      frame.setSelected(true); 
    
    } 
    catch (PropertyVetoException e) { 
    
      frame.toBack(); 

    } 

    return retval; 

  } 

  public void remove(Component c) { 

    super.remove(c); 
    checkDesktopSize(); 

  } 

  public void cascadeFrames() { 

    int i;
    int x, y;
    int frameWidth, frameHeight;
    JInternalFrame allFrames[];
    
    x = y = 0;
    
    restoreFrames();
    allFrames = getAllFrames(); 

    manager.setNormalSize(); 
    frameHeight = (getBounds().height - 5) - allFrames.length * FRAME_OFFSET; 
    frameWidth = (getBounds().width - 5) - allFrames.length * FRAME_OFFSET; 

    for (i = allFrames.length - 1; i >= 0; i--) { 

      allFrames[i].setSize(frameWidth, frameHeight); 
      allFrames[i].setLocation(x, y); 
      x = x + FRAME_OFFSET; 
      y = y + FRAME_OFFSET; 

    } 

  } 

  public void tileFrames() { 
  
	int i;
    int y;
    int frameHeight;
    JInternalFrame allFrames[];
  
    y = 0;
    
    restoreFrames();
    allFrames = getAllFrames();
    manager.setNormalSize();
    
    frameHeight = getBounds().height/allFrames.length;
    
    for (i = 0; i < allFrames.length; i++) {
    
      allFrames[i].setSize(getBounds().width, frameHeight);
      allFrames[i].setLocation(0, y);
      y = y + frameHeight;
    	
    }
  
  } 

  public void minimizeFrames() {

    int i;
    JInternalFrame[] allFrames;
    
    allFrames = getAllFrames();
    
    for (i = 0; i < allFrames.length; i++) {
    	
      try {

        allFrames[i].setIcon(true);

      }
      catch (PropertyVetoException e) {}
 
    }
    
  } 

  public void restoreFrames() { 

    int i;
    JInternalFrame[] allFrames;
	    
    allFrames = getAllFrames();
	    
    for (i = 0; i < allFrames.length; i++) {
    	
      try {

        allFrames[i].setIcon(false);

      }
      catch (PropertyVetoException e) {}
 
    }
    
  } 

  public int deIconifiedFrames() { 

    int i, c; 
    JInternalFrame[] allFrames;
    
    allFrames = getAllFrames(); 

    c = 0;
    for (i = 0; i < allFrames.length; i++) {
    	
      if (!allFrames[i].isIcon()) c++; 

    } 

    return c; 

  } 

  public int iconifiedFrames() { 

    int i, c; 
    JInternalFrame[] allFrames;
    
    allFrames = getAllFrames(); 
    
    c = 0;

    for (i = 0; i < allFrames.length; i++) { 

      if (allFrames[i].isIcon()) c++; 

    } 

    return c; 
  
  } 

  public void setAllSize(Dimension d){ 

    setMinimumSize(d); 
    setMaximumSize(d); 
    setPreferredSize(d); 

  } 

  
  public void setAllSize(int width, int height) { 

    setAllSize(new Dimension(width,height)); 

  } 

  private void checkDesktopSize() { 

    if (getParent() != null && isVisible())
      manager.resizeDesktop(); 

  } 

} 

class MDIDesktopManager extends DefaultDesktopManager { 

  private static final long serialVersionUID = 1L;
  
  private MDIDesktopPane desktop; 

  public MDIDesktopManager(MDIDesktopPane desktop) { 

    this.desktop = desktop; 

  } 

  public void endResizingFrame(JComponent f) { 

    super.endResizingFrame(f); 
    resizeDesktop(); 

  } 

  public void endDraggingFrame(JComponent f) { 

    super.endDraggingFrame(f); 
    resizeDesktop(); 

  } 

  public void setNormalSize() { 

    int x, y;
    Dimension d;
    Insets scrollInsets;
    JScrollPane scrollPane;
    
    x = y = 0;
    
    scrollPane = getScrollPane(); 
    scrollInsets = getScrollPaneInsets(); 

    if (scrollPane != null) { 

      d = scrollPane.getVisibleRect().getSize(); 

      if (scrollPane.getBorder() != null) { 

        d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, 
          d.getHeight() - scrollInsets.top - scrollInsets.bottom); 

      } 

      d.setSize(d.getWidth() - 20, d.getHeight() - 20); 
      desktop.setAllSize(x, y); 
      scrollPane.invalidate(); 
      scrollPane.validate(); 

    } 

  } 

  private Insets getScrollPaneInsets() { 

    JScrollPane scrollPane;
    
    scrollPane = getScrollPane(); 

    if (scrollPane == null)
      return new Insets(0,0,0,0); 

    return getScrollPane().getBorder().getBorderInsets(scrollPane); 

  } 

  private JScrollPane getScrollPane() { 

	JViewport viewPort;
	
    if (desktop.getParent() instanceof JViewport) { 

      viewPort = (JViewport)desktop.getParent(); 

      if (viewPort.getParent() instanceof JScrollPane) 
        return (JScrollPane)viewPort.getParent(); 

    } 

    return null; 

  } 

  protected void resizeDesktop() {

    int i;
    int x, y;
    JInternalFrame allFrames[];
    JScrollPane scrollPane;
	Insets scrollInsets;
	
	Dimension d;

	x = y = 0;
	  
	scrollPane = getScrollPane();
	scrollInsets  = getScrollPaneInsets(); 
	  
    if (scrollPane != null) {
    	  
      allFrames = desktop.getAllFrames(); 

      for (i = 0; i < allFrames.length; i++) { 

        if (allFrames[i].getX() + allFrames[i].getWidth() > x) 
          x = allFrames[i].getX() + allFrames[i].getWidth(); 

        if (allFrames[i].getY() + allFrames[i].getHeight() > y) 
	      y = allFrames[i].getY() + allFrames[i].getHeight(); 

      } 

      d = scrollPane.getVisibleRect().getSize(); 

      if (scrollPane.getBorder() != null) { 

        d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, 
          d.getHeight() - scrollInsets.top - scrollInsets.bottom); 

      } 

      if (x <= d.getWidth()) 
        x = ((int)d.getWidth()) - 20; 

      if (y <= d.getHeight()) 
        y = ((int)d.getHeight()) - 20; 

      desktop.setAllSize(x,y); 
      scrollPane.invalidate(); 
      scrollPane.validate(); 

    }
    
  } 
 
}
