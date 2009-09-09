/*
 * MyNode.java
 *
 * Created on May 28, 2007, 6:29:54 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.gui.scheme;

/**
 *
 * @author gw152771
 */
public class MyNode {
    
    private int id;
    private String name;
    private String category;
    
    public MyNode(String name, String category, int id) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
   
    public String getName() {
        return name;
    }

    public String getCategory() {
        return name;
    }

    public int getId() {
        return id;
    }

    
}