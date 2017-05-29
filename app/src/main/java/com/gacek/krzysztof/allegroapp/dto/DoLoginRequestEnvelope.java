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
public class DoLoginRequestEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "DoLoginRequest")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoLoginRequestBody doLoginRequestBody;
    }

    @Root
    @Order(elements={"userLogin", "userPassword", "countryCode", "webapiKey", "localVersion"})
    private static class DoLoginRequestBody {

        @Element(name = "userLogin")
        @Namespace(reference = "urn:SandboxWebApi")
        private String userLogin;
        @Element(name = "userPassword")
        @Namespace(reference = "urn:SandboxWebApi")
        private String userPassword;
        @Element(name = "countryCode")
        @Namespace(reference = "urn:SandboxWebApi")
        private int countryCode;
        @Element(name = "webapiKey")
        @Namespace(reference = "urn:SandboxWebApi")
        private String webApiKey;
        @Element(name = "localVersion")
        @Namespace(reference = "urn:SandboxWebApi")
        private long localVersion;

    }

    public DoLoginRequestEnvelope() {
        this.body = new Body();
        this.body.doLoginRequestBody = new DoLoginRequestBody();
    }

    public String getT1UserLogin() {
        return body.doLoginRequestBody.userLogin;
    }

    public String getT2UserPassword() {
        return body.doLoginRequestBody.userPassword;
    }

    public int getT3CountryCode() {
        return body.doLoginRequestBody.countryCode;
    }

    public String getT4webapiKey() {
        return body.doLoginRequestBody.webApiKey;
    }

    public long getT5localVersion() {
        return body.doLoginRequestBody.localVersion;
    }

    public void setT1UserLogin(String t1UserLogin) {
        this.body.doLoginRequestBody.userLogin = t1UserLogin;
    }

    public void setT2UserPassword(String t2UserPassword) {
        this.body.doLoginRequestBody.userPassword = t2UserPassword;
    }

    public void setT3CountryCode(int t3CountryCode) {
        this.body.doLoginRequestBody.countryCode = t3CountryCode;
    }

    public void setT4webapiKey(String t4webapiKey) {
        this.body.doLoginRequestBody.webApiKey = t4webapiKey;
    }

    public void setT5localVersion(long t5localVersion) {
        this.body.doLoginRequestBody.localVersion = t5localVersion;
    }

}
