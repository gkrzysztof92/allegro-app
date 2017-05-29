package com.gacek.krzysztof.allegroapp.model;

import java.util.Arrays;


public class ItemThumbnail {

    private int itemId;
    private String itemName;
    private byte[] photo;
    private boolean isSelected = false;

    public ItemThumbnail() {
    }

    public ItemThumbnail(int itemId, String itemName, byte[] photo) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.photo = photo;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "ItemThumbnail{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
