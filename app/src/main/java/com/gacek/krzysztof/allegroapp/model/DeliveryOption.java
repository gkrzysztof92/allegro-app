package com.gacek.krzysztof.allegroapp.model;


public class DeliveryOption {

    private String name;
    private boolean isEnabled;
    private int firstItemPriceId;
    private float firstItemPriceValue;
    private int nextItemPriceId;
    private float nextItemPriceValue;
    private int qtyInPackageId;
    private int qtyInPackageValue;
    private String type;

    public DeliveryOption() {
    }

    public DeliveryOption(String name, boolean isEnabled, int firstItemPriceId,
                          float firstItemPriceValue, int nextItemPriceId, float nextItemPriceValue,
                          int qtyInPackageId, int qtyInPackageValue, String type) {
        this.name = name;
        this.isEnabled = isEnabled;
        this.firstItemPriceId = firstItemPriceId;
        this.firstItemPriceValue = firstItemPriceValue;
        this.nextItemPriceId = nextItemPriceId;
        this.nextItemPriceValue = nextItemPriceValue;
        this.qtyInPackageId = qtyInPackageId;
        this.qtyInPackageValue = qtyInPackageValue;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public float getFirstItemPriceValue() {
        return firstItemPriceValue;
    }

    public void setFirstItemPriceValue(float firstItemPriceValue) {
        this.firstItemPriceValue = firstItemPriceValue;
    }

    public float getNextItemPriceValue() {
        return nextItemPriceValue;
    }

    public void setNextItemPriceValue(float nextItemPriceValue) {
        this.nextItemPriceValue = nextItemPriceValue;
    }

    public int getQtyInPackageValue() {
        return qtyInPackageValue;
    }

    public void setQtyInPackageValue(int qtyInPackageValue) {
        this.qtyInPackageValue = qtyInPackageValue;
    }

    public int getFirstItemPriceId() {
        return firstItemPriceId;
    }

    public void setFirstItemPriceId(int firstItemPriceId) {
        this.firstItemPriceId = firstItemPriceId;
    }

    public int getNextItemPriceId() {
        return nextItemPriceId;
    }

    public void setNextItemPriceId(int nextItemPriceId) {
        this.nextItemPriceId = nextItemPriceId;
    }

    public int getQtyInPackageId() {
        return qtyInPackageId;
    }

    public void setQtyInPackageId(int qtyInPackageId) {
        this.qtyInPackageId = qtyInPackageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DeliveryOption{" +
                "name='" + name + '\'' +
                ", isEnabled=" + isEnabled +
                ", firstItemPriceId=" + firstItemPriceId +
                ", firstItemPriceValue=" + firstItemPriceValue +
                ", nextItemPriceId=" + nextItemPriceId +
                ", nextItemPriceValue=" + nextItemPriceValue +
                ", qtyInPackageId=" + qtyInPackageId +
                ", qtyInPackageValue=" + qtyInPackageValue +
                ", type='" + type + '\'' +
                '}';
    }
}
