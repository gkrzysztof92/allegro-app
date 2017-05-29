package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;


@Root(name = "soapenv:Envelope")
@NamespaceList({
        @Namespace(prefix = "soapenv", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace(prefix = "urn", reference = "urn:SandboxWebApi")
})
public class DoNewAuctionExtRequestEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "DoNewAuctionExtRequest")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoNewAuctionExtRequest requestData;
    }

    @Root
    @Order(elements = {"sessionHandle", "fields"})
    private static class DoNewAuctionExtRequest {

        @Element(name = "sessionHandle")
        @Namespace(reference = "urn:SandboxWebApi")
        private String sessionHandle;
        @ElementList
        //@Path("fields")
        @Namespace(reference = "urn:SandboxWebApi")
        private ArrayList<AuctionAttributeField> fields;

    }

    public DoNewAuctionExtRequestEnvelope() {
        this.body = new Body();
        this.body.requestData = new DoNewAuctionExtRequest();
    }

    public String getSessionHandle() {
        return body.requestData.sessionHandle;
    }

    public void setSessionHandle(String sessionHandle) {
        this.body.requestData.sessionHandle = sessionHandle;
    }

    public ArrayList<AuctionAttributeField> getFields() {
        return body.requestData.fields;
    }

    public void setFields(ArrayList<AuctionAttributeField> fields) {
        this.body.requestData.fields = fields;
    }

}
