package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;


@Root(name = "soapenv:Envelope")
@NamespaceList({
        @Namespace(prefix = "soapenv", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace(prefix = "urn", reference = "urn:SandboxWebApi")
})
public class DoGetStatesInfoRequestEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "DoGetStatesInfoRequest")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoGetStatesInfoRequest doGetStatesInfoRequest;
    }

    @Root
    @Order(elements = {"countryCode", "webapiKey"})
    private static class DoGetStatesInfoRequest {

        @Element @Namespace(reference = "urn:SandboxWebApi")
        private int countryCode;
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private String webapiKey;

    }

    public DoGetStatesInfoRequestEnvelope() {
        body = new Body();
        body.doGetStatesInfoRequest = new DoGetStatesInfoRequest();
    }

    public int getCountryCode() {
        return body.doGetStatesInfoRequest.countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.body.doGetStatesInfoRequest.countryCode = countryCode;
    }

    public String getWebapiKey() {
        return body.doGetStatesInfoRequest.webapiKey;
    }

    public void setWebapiKey(String webapiKey) {
        this.body.doGetStatesInfoRequest.webapiKey = webapiKey;
    }

}
