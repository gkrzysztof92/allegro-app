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
public class DoGetCatsDataRequestEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "DoGetCatsDataRequest")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoGetCatsDataRequest doGetCatsDataRequest;
    }

    @Root()
    @Order(elements = {"countryId", "localVersion", "webapiKey" })
    private static class DoGetCatsDataRequest {
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private int countryId ;
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private int localVersion;
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private String webapiKey;

    }

    public DoGetCatsDataRequestEnvelope() {
        this.body = new Body();
        this.body.doGetCatsDataRequest = new DoGetCatsDataRequest();
    }

    public int getCountryId() {
        return body.doGetCatsDataRequest.countryId;
    }

    public void setCountryId(int countryId) {
        body.doGetCatsDataRequest.countryId = countryId;
    }

    public int getLocalVersion() {
        return body.doGetCatsDataRequest.localVersion;
    }

    public void setLocalVersion(int localVersion) {
        body.doGetCatsDataRequest.localVersion = localVersion;
    }

    public String getWebapiKey() {
        return body.doGetCatsDataRequest.webapiKey;
    }

    public void setWebapiKey(String webapiKey) {
        body.doGetCatsDataRequest.webapiKey = webapiKey;
    }

}
