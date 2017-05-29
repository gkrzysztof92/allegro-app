package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@NamespaceList({
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
@Root(name = "item", strict = false)
@Namespace(reference = "urn:SandboxWebApi")
@Order(elements = {"itemId", "itemTitle", "itemPrice", "itemStartQuantity", "itemSoldQuantity",
         "itemQuantityType", "itemStartTime", "itemEndTime", "itemEndTimeLeft"}
)
public class SellItem {

    @Element
    @Namespace(reference = "urn:SandboxWebApi")
    private long itemId;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String itemTitle;
    @ElementList @Namespace(reference = "urn:SandboxWebApi")
    private ArrayList<ItemPrice> itemPrice;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int itemStartQuantity;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int itemSoldQuantity;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int itemQuantityType;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private long itemStartTime;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private long itemEndTime;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String itemEndTimeLeft;

    public SellItem() {
    }

    public SellItem(long itemId, String itemTitle, ArrayList<ItemPrice> itemPrice, int itemStartQuantity,
                    int itemSoldQuantity, int itemQuantityType, long itemStartTime,
                    long itemEndTime, String itemEndTimeLeft) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemPrice = itemPrice;
        this.itemStartQuantity = itemStartQuantity;
        this.itemSoldQuantity = itemSoldQuantity;
        this.itemQuantityType = itemQuantityType;
        this.itemStartTime = itemStartTime;
        this.itemEndTime = itemEndTime;
        this.itemEndTimeLeft = itemEndTimeLeft;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public ArrayList<ItemPrice> getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(ArrayList<ItemPrice> itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemStartQuantity() {
        return itemStartQuantity;
    }

    public void setItemStartQuantity(int itemStartQuantity) {
        this.itemStartQuantity = itemStartQuantity;
    }

    public int getPrivaitemSoldQuantity() {
        return itemSoldQuantity;
    }

    public void setPrivaitemSoldQuantity(int privaitemSoldQuantity) {
        this.itemSoldQuantity = privaitemSoldQuantity;
    }

    public int getItemQuantityType() {
        return itemQuantityType;
    }

    public void setItemQuantityType(int itemQuantityType) {
        this.itemQuantityType = itemQuantityType;
    }

    public long getItemStartTime() {
        return itemStartTime;
    }

    public void setItemStartTime(long itemStartTime) {
        this.itemStartTime = itemStartTime;
    }

    public long getItemEndTime() {
        return itemEndTime;
    }

    public void setItemEndTime(long itemEndTime) {
        this.itemEndTime = itemEndTime;
    }

    public String getItemEndTimeLeft() {
        return itemEndTimeLeft;
    }

    public void setItemEndTimeLeft(String itemEndTimeLeft) {
        this.itemEndTimeLeft = itemEndTimeLeft;
    }

    @Override
    public String toString() {
        return "SellItem{" +
                "itemId=" + itemId +
                ", itemTitle='" + itemTitle + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemStartQuantity=" + itemStartQuantity +
                ", itemSoldQuantity=" + itemSoldQuantity +
                ", itemQuantityType=" + itemQuantityType +
                ", itemStartTime=" + itemStartTime +
                ", itemEndTime=" + itemEndTime +
                ", itemEndTimeLeft='" + itemEndTimeLeft + '\'' +
                '}';
    }
}
