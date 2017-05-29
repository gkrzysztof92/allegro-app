package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;


@Root(name = "SOAP-ENV:Envelope")
@NamespaceList({
        @Namespace(prefix = "SOAP-ENV", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
public class DoGetMySellItemsResponseEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "doGetMySellItemsResponse")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoGetMySellItemsResponse doGetMySellItemsResponse;
    }

    @Root
    private static class DoGetMySellItemsResponse {

        @Element @Namespace(reference = "urn:SandboxWebApi")
        private int sellItemsCounter;
        @ElementList @Namespace(reference = "urn:SandboxWebApi")
        private ArrayList<SellItem> sellItemsList;

    }

    public DoGetMySellItemsResponseEnvelope() {
        body = new Body();
        body.doGetMySellItemsResponse = new DoGetMySellItemsResponse();
    }

    public int getSellItemsCounter() {
        return body.doGetMySellItemsResponse.sellItemsCounter;
    }

    public void setSellItemsCounter(int sellItemsCounter) {
        this.body.doGetMySellItemsResponse.sellItemsCounter = sellItemsCounter;
    }

    public ArrayList<SellItem> getSellItemsList() {
        return body.doGetMySellItemsResponse.sellItemsList;
    }

    public void setSellItemsList(ArrayList<SellItem> sellItemsList) {
        this.body.doGetMySellItemsResponse.sellItemsList = sellItemsList;
    }
}
