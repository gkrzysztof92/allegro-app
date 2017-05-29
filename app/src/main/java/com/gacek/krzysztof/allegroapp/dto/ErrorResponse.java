package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@Root(name = "SOAP-ENV:Envelope")
@NamespaceList({
        @Namespace(prefix = "SOAP-ENV", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class ErrorResponse {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    public static class Body {
        @Element(name = "Fault")
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
        private Fault fault;
    }

    @Root
    public static class Fault {
        @Element
        private String faultcode;
        @Element
        private String faultstring;
        @Element
        private String faultactor;

        public String getFaultcode() {
            return faultcode;
        }

        public String getFaultstring() {
            return faultstring;
        }

        public String getFaultactor() {
            return faultactor;
        }
    }

    public ErrorResponse() {
        this.body = new Body();
        this.body.fault = new Fault();
    }

    public Fault getFault() {
        return body.fault;
    }

}
