package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@NamespaceList({
        @Namespace(prefix = "urn", reference = "urn:SandboxWebApi")
})
@Root(name = "item", strict = false)
@Namespace(reference = "urn:SandboxWebApi")
public class AuctionAttributeField {

    @Element @Namespace(reference = "urn:SandboxWebApi")
    private Integer fid;
    @Element(required = false) @Namespace(reference = "urn:SandboxWebApi")
    private String fvalueString;
    @Element(required = false) @Namespace(reference = "urn:SandboxWebApi")
    private Integer fvalueInt;
    @Element(required = false) @Namespace(reference = "urn:SandboxWebApi")
    private Float fvalueFloat;
    @Element(required = false) @Namespace(reference = "urn:SandboxWebApi")
    private String fvalueImage;
    @Element(required = false) @Namespace(reference = "urn:SandboxWebApi")
    private String fvalueDatetime;
    @Element(required = false) @Namespace(reference = "urn:SandboxWebApi")
    private String fvalueDate;

    public AuctionAttributeField() {
    }

    public AuctionAttributeField(int fid, String fvalueString, int fvalueInt, float fvalueFloat,
                                 String fvalueImage, String fvalueDatetime, String fvalueDate) {
        this.fid = fid;
        this.fvalueString = fvalueString;
        this.fvalueInt = fvalueInt;
        this.fvalueFloat = fvalueFloat;
        this.fvalueImage = fvalueImage;
        this.fvalueDatetime = fvalueDatetime;
        this.fvalueDate = fvalueDate;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFvalueString() {
        return fvalueString;
    }

    public void setFvalueString(String fvalueString) {
        this.fvalueString = fvalueString;
    }

    public int getFvalueInt() {
        return fvalueInt;
    }

    public void setFvalueInt(int fvalueInt) {
        this.fvalueInt = fvalueInt;
    }

    public float getFvalueFloat() {
        return fvalueFloat;
    }

    public void setFvalueFloat(float fvalueFloat) {
        this.fvalueFloat = fvalueFloat;
    }

    public String getFvalueImage() {
        return fvalueImage;
    }

    public void setFvalueImage(String fvalueImage) {
        this.fvalueImage = fvalueImage;
    }

    public String getFvalueDatetime() {
        return fvalueDatetime;
    }

    public void setFvalueDatetime(String fvalueDatetime) {
        this.fvalueDatetime = fvalueDatetime;
    }

    public String getFvalueDate() {
        return fvalueDate;
    }

    public void setFvalueDate(String fvalueDate) {
        this.fvalueDate = fvalueDate;
    }

}
