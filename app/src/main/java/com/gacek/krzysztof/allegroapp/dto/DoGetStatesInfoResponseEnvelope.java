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
public class DoGetStatesInfoResponseEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "doGetStatesInfoResponse")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoGetStatesInfoResponse doGetStatesInfoResponse;
    }

    @Root
    public static class DoGetStatesInfoResponse {

        @ElementList @Namespace(reference = "urn:SandboxWebApi")
        ArrayList<State> statesInfoArray;

    }

    public DoGetStatesInfoResponseEnvelope() {
        body = new Body();
        body.doGetStatesInfoResponse = new DoGetStatesInfoResponse();
    }

    public ArrayList<State> getStatesInfoArray() {
        return body.doGetStatesInfoResponse.statesInfoArray;
    }

    public void setStatesInfoArray(ArrayList<State> statesInfoArray) {
        this.body.doGetStatesInfoResponse.statesInfoArray = statesInfoArray;
    }

}
