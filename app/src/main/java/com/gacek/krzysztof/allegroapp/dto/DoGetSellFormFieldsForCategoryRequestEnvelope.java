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
public class DoGetSellFormFieldsForCategoryRequestEnvelope {

    @Element(name = "Body")
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    @Root
    private static class Body {

        @Element(name = "DoGetSellFormFieldsForCategoryRequest")
        @Namespace(reference = "urn:SandboxWebApi")
        private DoGetSellFormFieldsForCategoryRequest requestData;
    }

    @Root
    @Order(elements = {"webapiKey", "countryId", "categoryId" })
    private static class DoGetSellFormFieldsForCategoryRequest {
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private String webapiKey;
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private int countryId;
        @Element @Namespace(reference = "urn:SandboxWebApi")
        private int categoryId;

    }

    public DoGetSellFormFieldsForCategoryRequestEnvelope() {
        this.body = new Body();
        this.body.requestData = new DoGetSellFormFieldsForCategoryRequest();
    }

    public String getWebapiKey() {
        return body.requestData.webapiKey;
    }

    public void setWebapiKey(String webapiKey) {
        this.body.requestData.webapiKey = webapiKey;
    }

    public int getCountryId() {
        return body.requestData.countryId;
    }

    public void setCountryId(int countryId) {
        this.body.requestData.countryId = countryId;
    }

    public int getCategoryId() {
        return body.requestData.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.body.requestData.categoryId = categoryId;
    }

}
