package com.gacek.krzysztof.allegroapp.model;

import java.util.Arrays;

public class Photo {

    private int id;
    private int itemId;
    private byte[] photoData;
    private String primaryImage;

    public Photo() {
    }

    public Photo(int id, int itemId, byte[] photoData, String primaryImage) {
        this.id = id;
        this.itemId = itemId;
        this.photoData = photoData;
        this.primaryImage = primaryImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", photoData=" + Arrays.toString(photoData) +
                ", primaryImage='" + primaryImage + '\'' +
                '}';
    }
}
