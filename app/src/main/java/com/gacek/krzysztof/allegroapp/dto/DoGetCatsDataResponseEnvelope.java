package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "SOAP-ENV:Envelope")
@NamespaceList({
        @Namespace(prefix = "SOAP-ENV", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
public class DoGetCatsDataResponseEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "doGetCatsDataResponse")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoGetCatsDataResponseBody doGetCatsDataResponseBody;
    }

    @Root
    private static class DoGetCatsDataResponseBody {

        @ElementList(inline = false)
        @Namespace(reference = "urn:SandboxWebApi")
        private List<CategoryDto> catsList;
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private String verKey;
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private String verStr;

    }

    public DoGetCatsDataResponseEnvelope() {
        this.body = new Body();
        this.body.doGetCatsDataResponseBody = new DoGetCatsDataResponseBody();
        this.body.doGetCatsDataResponseBody.catsList = new ArrayList<>();
    }

    public List<CategoryDto> getCatsList() {
        return this.body.doGetCatsDataResponseBody.catsList;
    }

    public void setCatsList(List<CategoryDto> catsList) {
        this.body.doGetCatsDataResponseBody.catsList = catsList;
    }

    public String getVerStr() {
        return this.body.doGetCatsDataResponseBody.verStr;
    }

    public void setVerStr(String verStr) {
        this.body.doGetCatsDataResponseBody.verStr = verStr;
    }

    public String getVerKey() {
        return this.body.doGetCatsDataResponseBody.verKey;
    }

    public void setVerKey(String verKey) {
        this.body.doGetCatsDataResponseBody.verKey = verKey;
    }

}
