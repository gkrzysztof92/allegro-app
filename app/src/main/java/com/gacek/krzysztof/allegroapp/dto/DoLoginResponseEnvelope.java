package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@Root(name = "SOAP-ENV:Envelope")
@NamespaceList({
        @Namespace(prefix = "SOAP-ENV", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
public class DoLoginResponseEnvelope {


    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    public static class Body {

        @Element(name = "doLoginResponse")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoLoginResponseBody doLoginResponseBody;
    }

    @Root
    public static class DoLoginResponseBody {

        @Element(name = "sessionHandlePart")
        @Namespace(reference = "urn:SandboxWebApi")
        private String sessionHandlePart;
        @Element(name = "userId")
        @Namespace(reference = "urn:SandboxWebApi")
        private Integer userId;
        @Element(name = "serverTime")
        @Namespace(reference = "urn:SandboxWebApi")
        private Long serverTime;

    }

    public DoLoginResponseEnvelope() {
        this.body = new Body();
        this.body.doLoginResponseBody = new DoLoginResponseBody();
    }

    public String getSessionHandlePart() {
        return this.body.doLoginResponseBody.sessionHandlePart;
    }

    public void setSessionHandlePart(String sessionHandlePart) {
        this.body.doLoginResponseBody.sessionHandlePart = sessionHandlePart;
    }

    public Integer getUserId() {
        return this.body.doLoginResponseBody.userId;
    }

    public void setUserId(Integer userId) {
        this.body.doLoginResponseBody.userId = userId;
    }

    public Long getServerTime() {
        return this.body.doLoginResponseBody.serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.body.doLoginResponseBody.serverTime = serverTime;
    }

}