package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@Root(name = "soapenv:Envelope")
@NamespaceList({
        @Namespace(prefix = "soapenv", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace(prefix = "urn", reference = "urn:SandboxWebApi")
})
public class DoGetMySellItemsRequestEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "DoGetMySellItemsRequest")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoGetMySellItemsRequest doGetMySellItemsRequest;
    }

    @Root
    private static class DoGetMySellItemsRequest {

        @Element @Namespace(reference = "urn:SandboxWebApi")
        private String sessionId;

    }

    public DoGetMySellItemsRequestEnvelope() {
        body = new Body();
        body.doGetMySellItemsRequest = new DoGetMySellItemsRequest();
    }

    public String getSessionId() {
        return body.doGetMySellItemsRequest.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.body.doGetMySellItemsRequest.sessionId = sessionId;
    }

}
