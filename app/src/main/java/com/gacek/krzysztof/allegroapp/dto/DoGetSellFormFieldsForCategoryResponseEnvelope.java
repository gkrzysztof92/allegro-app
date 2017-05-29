package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "SOAP-ENV:Envelope")
@NamespaceList({
        @Namespace(prefix = "SOAP-ENV", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
public class DoGetSellFormFieldsForCategoryResponseEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Path("doGetSellFormFieldsForCategoryResponse")
        @Element(name = "sellFormFieldsForCategory")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoGetSellFormFieldsForCategoryResponse responseData;
    }

    @Root(strict=false)
    private static class DoGetSellFormFieldsForCategoryResponse {

        @ElementList(inline = false, name = "sellFormFieldsList")
        @Namespace(reference = "urn:SandboxWebApi")
        private List<SellFormField> sellFormsFields;

    }

    public DoGetSellFormFieldsForCategoryResponseEnvelope() {
        this.body = new Body();
        this.body.responseData = new DoGetSellFormFieldsForCategoryResponse();
    }

    public List<SellFormField> getSellFormsFields() {
        return body.responseData.sellFormsFields;
    }

    public void setSellFormsFields(List<SellFormField> sellFormsFields) {
        this.body.responseData.sellFormsFields = sellFormsFields;
    }

}
