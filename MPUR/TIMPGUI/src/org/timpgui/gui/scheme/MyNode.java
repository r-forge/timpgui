/*
 * MyNode.java
 *
 * Created on May 28, 2007, 6:29:54 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.gui.scheme;

import java.awt.Image;

/**
 *
 * @author gw152771
 */
public class MyNode {
    
    private Image image;
    private String name;
    
    public MyNode(String name, Image image) {
        this.image = image;
        this.name = name;
    }
    
    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}