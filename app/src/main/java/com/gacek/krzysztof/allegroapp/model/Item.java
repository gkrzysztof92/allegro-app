package com.gacek.krzysztof.allegroapp.model;


public class Item {

    private int id;
    private String name;
    private int categoryId;
    private String categoryPath;
    private String description;
    private int quantity;
    private String quantityType;
    private int quantityTypeId;
    private String creationDate;

    public Item() {
    }

    public Item(int id, String name, int categoryId, String categoryPath, String description,
                int quantity, String quantityType, int quantityTypeId, String creationDate) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryPath = categoryPath;
        this.description = description;
        this.quantity = quantity;
        this.quantityType = quantityType;
        this.quantityTypeId = quantityTypeId;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }

    public int getQuantityTypeId() {
        return quantityTypeId;
    }

    public void setQuantityTypeId(int quantityTypeId) {
        this.quantityTypeId = quantityTypeId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", categoryPath='" + categoryPath + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", quantityType='" + quantityType + '\'' +
                ", quantityTypeId=" + quantityTypeId +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
