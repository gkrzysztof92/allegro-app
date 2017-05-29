package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@NamespaceList({
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
@Root(name = "item")
@Namespace(reference = "urn:SandboxWebApi")
public class ItemPrice {

    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int priceType;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private float priceValue;

    public ItemPrice() {
    }

    public ItemPrice(int priceType, float priceValue) {
        this.priceType = priceType;
        this.priceValue = priceValue;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public float getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(float priceValue) {
        this.priceValue = priceValue;
    }
}
