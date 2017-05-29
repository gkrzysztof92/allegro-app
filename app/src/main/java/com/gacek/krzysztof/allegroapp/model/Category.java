package com.gacek.krzysztof.allegroapp.model;


public class Category {

    private int id;
    private String categoryName;
    private int categoryParent;

    public Category() {
    }

    public Category(int id, String categoryName, int categoryParent) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryParent = categoryParent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryParent() {
        return categoryParent;
    }

    public void setCategoryParent(int categoryParent) {
        this.categoryParent = categoryParent;
    }


}
