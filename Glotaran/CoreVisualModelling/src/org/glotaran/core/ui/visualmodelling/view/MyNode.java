package org.glotaran.core.ui.visualmodelling.view;

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