package com.gacek.krzysztof.allegroapp.model;

import android.support.annotation.Nullable;

/**
 * Created by Krzysztof on 03/05/2017.
 */

public class ItemAttribute {

    private int id;
    private int itemId;
    private int attributeId;
    private String attributeName;
    private int attributeType;
    private String attributeValue;
    @Nullable private String attributeValueStr;

    public ItemAttribute() {
    }

    public ItemAttribute(int id, int itemId, int attributeId, String attributeName,
                         int attributeType, String attributeValue, String attributeValueStr) {
        this.id = id;
        this.itemId = itemId;
        this.attributeId = attributeId;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.attributeValue = attributeValue;
        this.attributeValueStr = attributeValueStr;
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

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(int attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Nullable
    public String getAttributeValueStr() {
        return attributeValueStr;
    }

    public void setAttributeValueStr(@Nullable String attributeValueStr) {
        this.attributeValueStr = attributeValueStr;
    }

    @Override
    public String toString() {
        return "ItemAttribute{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                ", attributeType=" + attributeType +
                ", attributeValue='" + attributeValue + '\'' +
                ", attributeValueStr='" + attributeValueStr + '\'' +
                '}';
    }
}
