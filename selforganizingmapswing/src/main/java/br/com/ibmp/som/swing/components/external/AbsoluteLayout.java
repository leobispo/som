package br.com.ibmp.som.swing.components.external;

/*
 * @(#)AbsoluteLayout.java    2.3.5  18 February 2005
 *
 * Copyright 2005
 * College of Computer and Information Science
 * Northeastern University
 * Boston, MA  02115
 *
 * The Java Power Tools software may be used for educational
 * purposes as long as this copyright notice is retained intact
 * at the top of all source files.
 *
 * To discuss possible commercial use of this software, 
 * contact Richard Rasala at Northeastern University, 
 * College of Computer and Information Science,
 * 617-373-2462 or rasala@ccs.neu.edu.
 *
 * The Java Power Tools software has been designed and built
 * in collaboration with Viera Proulx and Jeff Raab.
 *
 * Should this software be modified, the words "Modified from 
 * Original" must be included as a comment below this notice.
 *
 * All publication rights are retained.  This software or its 
 * documentation may not be published in any media either
 * in whole or in part without explicit permission.
 *
 * This software was created with support from Northeastern 
 * University and from NSF grant DUE-9950829.
 */

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

/**
 * <p>Layout manager allowing child components of a container
 * to control their own locations and sizes.</p>
 *
 * <p>In version 2.3.5, a serious bug was fixed.  In the possess of
 * fixing the bug, the following policy about the determination of
 * <code>Component</code> size was implemented.<p>
 *
 * <p>If a <code>Component</code> is actually a <code>Container</code>
 * then its size is computed by calling <code>getPreferredSize</code>
 * and any existing size set by <code>setSize</code> is ignored.
 * This permits objects that extend <code>Container</code> to
 * effectively change their size by changing the return value of
 * <code>getPreferredSize</code> and this change is not affected
 * by an obsolete value set earlier by <code>setSize</code>.</p>
 *
 * <p>For a general <code>Component</code>, the size is set to be
 * the maximum of <code>getSize</code> and <code>getMinimumSize</code>.
 * This approach was a design choice based on the fact that for
 * many older Java <code>Component</code> objects people often
 * directly set the size and do not bother with the minimum size.
 * This means that the resulting size set in this layout manager
 * is at least a big as both the actual size set earlier and the
 * minimum size.</p>
 *
 * <p>The size policy is consistent with the effort in JPT to use
 * <code>getPreferredSize</code> (which is algorithmic) rather
 * than rely on size settings that may become obsolete for
 * dynamically changing components.</p> 
 *
 * @author  Jeff Raab
 * @author  Richard Rasala
 * @version 2.3.5
 * @since   1.1
 */
public class AbsoluteLayout implements LayoutManager2 {

    /** Child components of the parent container. */
    protected Vector components = new Vector();

    /**
     * <p>Adds the given component to this layout 
     * with the given name.</p>
     *
     * <p>Ignores a <code>null</code> component.</p>
     * 
     * <p>Ignores the name parameter.</p>
     * 
     * @param name the name for the component
     * @param component the component to be added
     * @see #addLayoutComponent(Component, Object)
     */
    public void addLayoutComponent(
        String name, 
        Component component) 
    {
        addLayoutComponent(component, null);
    } 

    /**
     * <p>Adds the given component to this layout 
     * with the given constraints.</p>
     *
     * <p>Ignores a <code>null</code> component.</p>
     * 
     * <p>Ignores the constraints parameter since the position
     * is determined by <code>getLocation</code> and the size is
     * computed by the policy described in the introduction to
     * the class.</p>
     * 
     * @param component the component to be added
     * @param constraints the constraints applied to the component
     * @see #addLayoutComponent(String, Component)
     */
    public void addLayoutComponent(
        Component component, 
        Object constraints) 
    {
        if (component == null)
            return;
        
        components.add(component);
    } 

    /**
     * <p>Removes the given component from this layout.</p>
     *
     * @param component the component to be removed
     */
    public void removeLayoutComponent(Component component) {
        if (component != null)
            components.remove(component);
    }

    /**
     * <p>Returns the minimum size required to layout the components
     * contained in the parent container.  This will be the same
     * as the preferred layout size.</p>
     *
     * @param parent the parent container
     * @see #preferredLayoutSize(Container)
     * @see #maximumLayoutSize(Container)
     */
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    } 

    /**
     * <p>Returns the preferred size required to layout the components
     * contained in the parent container.</p>
     *
     * <p>This method computes component size by the policy described
     * in the introduction to the class.</p> 
     *
     * @param parent the parent container
     * @see #minimumLayoutSize(Container)
     * @see #maximumLayoutSize(Container)
     */
    public Dimension preferredLayoutSize(Container parent) { 
        Rectangle limits = null;
        Rectangle bounds = null;
        Dimension dimension = new Dimension(0,0);
        
        for (int i = 0; i < components.size(); i++) {
            Component c = (Component)components.get(i);
            
            bounds = getPreferredBounds(c);
            
            if (limits == null)
                limits = bounds;
            else {
              
                limits = limits.union(bounds);
                dimension = new Dimension(c.getWidth()/2, c.getHeight()/2);
            
            }
        }
       
        if (limits == null)
            return new Dimension();
        
        Insets insets = parent.getInsets();
        
        if (insets == null)
            insets = new Insets(0, 0, 0, 0);
        
        int w = limits.width  + insets.left + insets.right + 
          (new Double(dimension.getWidth())).intValue();
        int h = limits.height + insets.top + insets.bottom + 
          (new Double(dimension.getHeight())).intValue();
        
        return new Dimension(w, h);
    } 

    /**
     * <p>Returns the maximum size required to layout the components
     * contained in the parent container.  This will be the same
     * as the preferred layout size.</p>
     *
     * @param parent the parent container
     * @see #minimumLayoutSize(Container)
     * @see #preferredLayoutSize(Container)
     */
    public Dimension maximumLayoutSize(Container parent) { 
        return preferredLayoutSize(parent);
    } 

    /**
     * <p>Lays out the container.</p>
     *
     * <p>This implementation ensures that components have a
     * size equal to their preferred size if that value
     * is available or at least as big as their minimum size
     * otherwise.</p>
     * 
     * <p>This counteracts a bug (feature?) of Java that some
     * components have an initial size of (0,0) even when
     * their preferred size is not zero.<p>
     * 
     * <p>The location (upper-left) of each component is left
     * as is.</p>
     *
     * @param parent the parent container
     */
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        
        if (insets == null)
            insets = new Insets(0, 0, 0, 0);
        
        int a = insets.left;
        int b = insets.top;
        
        for (int i = 0; i < components.size(); i++) {
            Component c = (Component)components.get(i);
            
            if (c == null)
                continue;
            
            Rectangle bounds = getPreferredBounds(c);
            
            bounds.x += a;
            bounds.y += b;
            
            c.setBounds(bounds);
        }
    } 

    /** 
     * Returns the layout alignment along the <I>y</I>-axis.
     *
     * @param target the target container
     * @see #getLayoutAlignmentX(Container)
     */
    public float getLayoutAlignmentY(Container target) { 
        return 0.5f; 
    } 

    /** 
     * Returns the layout alignment along the <I>x</I>-axis.
     *
     * @param target the target container
     * @see #getLayoutAlignmentY(Container)
     */
    public float getLayoutAlignmentX(Container target) {
        return 0.5f; 
    } 

    /**
     * This method does nothing, as this layout
     * does not cache information about its parent container.
     *
     * @param parent the parent container
     */
    public void invalidateLayout(Container parent) {
        parent.repaint();
    }
    
    /**
     * <p>If the component is a container then returns
     * bounds where the width and height are replaced
     * by the width and height of the preferred size.
     * The current size is ignored in this case.</p>
     * 
     * <p>Otherwise, returns the maximum of the current
     * size and the minimum size.</p>
     * 
     * <p>The location is set to the value returned by
     * the method <code>getLocation</code></p>.
     *
     * <p>This method is used to implement the size policy
     * described in the introduction to the class.</p>
     *
     * @param c the component whose preferred bounds are required
     */
    public static Rectangle getPreferredBounds(Component c) {
        if (c == null)
            return new Rectangle();
        
        Point corner = c.getLocation();
        Rectangle bounds = c.getBounds();
        
        bounds.x = corner.x;
        bounds.y = corner.y;
        
        Dimension d;
        Dimension e;
        
        if (c instanceof Container) {
            Container x = (Container) c;
            
            d = x.getPreferredSize();
            
            bounds.width  = d.width;
            bounds.height = d.height;
        }
        else {
            d = c.getMinimumSize();
            e = c.getSize();
            
            bounds.width  = Math.max(d.width,  e.width );
            bounds.height = Math.max(d.height, e.height);
        };
        
        return bounds;
    }
}

