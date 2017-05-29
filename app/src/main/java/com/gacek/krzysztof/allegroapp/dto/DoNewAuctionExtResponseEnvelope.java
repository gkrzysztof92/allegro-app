package com.gacek.krzysztof.allegroapp.dto;

import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;


@Root(name = "soapenv:Envelope")
@NamespaceList({
        @Namespace(prefix = "SOAP-ENV", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
public class DoNewAuctionExtResponseEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "doNewAuctionExtResponse")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoNewAuctionExtResponse responseData;

    }

    @Root
    @Order(elements = { "itemId", "itemInfo", "itemIsAllegroStandard"})
    private static class DoNewAuctionExtResponse {
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private Long itemId;
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private String itemInfo;
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private int itemIsAllegroStandard;

    }

    public Long getItemId() {
        return body.responseData.itemId;
    }

    public void setItemId(Long itemId) {
        this.body.responseData.itemId = itemId;
    }

    public String getItemInfo() {
        return body.responseData.itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.body.responseData.itemInfo = itemInfo;
    }

    public int getItemIsAllegroStandard() {
        return body.responseData.itemIsAllegroStandard;
    }

    public void setItemIsAllegroStandard(int itemIsAllegroStandard) {
        this.body.responseData.itemIsAllegroStandard = itemIsAllegroStandard;
    }

}
