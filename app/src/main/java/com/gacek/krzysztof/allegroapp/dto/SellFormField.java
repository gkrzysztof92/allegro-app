package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@NamespaceList({
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
@Root(name = "item", strict = false)
@Namespace(reference = "urn:SandboxWebApi")
public class SellFormField {

    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int sellFormId;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormTitle;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int sellFormCat;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int sellFormType;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int sellFormResType;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormDefValue;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormOpt;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormPos;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormLength;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String sellMinValue;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String sellMaxValue;
    @Element(required=false) @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormDesc;
    @Element(required=false) @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormOptsValues;
    @Element(required=false) @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormParamId;
    @Element(required=false) @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormParamValues;
    @Element(required=false) @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormParentId;
    @Element(required=false) @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormParentValue;
    @Element(required=false) @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormUnit;
    @Element(required=false) @Namespace(reference = "urn:SandboxWebApi")
    private String sellFormOptions;

    public SellFormField() {
    }

    public SellFormField(int sellFormId, String sellFormTitle, int sellFormCat, int sellFormType, int sellFormResType, String sellFormDefValue, String sellFormOpt, String sellFormPos, String sellFormLength, String sellMinValue, String sellMaxValue, String sellFormDesc, String sellFormOptsValues, String sellFormParamId, String sellFormParamValues, String sellFormParentId, String sellFormParentValue, String sellFormUnit, String sellFormOptions) {
        this.sellFormId = sellFormId;
        this.sellFormTitle = sellFormTitle;
        this.sellFormCat = sellFormCat;
        this.sellFormType = sellFormType;
        this.sellFormResType = sellFormResType;
        this.sellFormDefValue = sellFormDefValue;
        this.sellFormOpt = sellFormOpt;
        this.sellFormPos = sellFormPos;
        this.sellFormLength = sellFormLength;
        this.sellMinValue = sellMinValue;
        this.sellMaxValue = sellMaxValue;
        this.sellFormDesc = sellFormDesc;
        this.sellFormOptsValues = sellFormOptsValues;
        this.sellFormParamId = sellFormParamId;
        this.sellFormParamValues = sellFormParamValues;
        this.sellFormParentId = sellFormParentId;
        this.sellFormParentValue = sellFormParentValue;
        this.sellFormUnit = sellFormUnit;
        this.sellFormOptions = sellFormOptions;
    }

    public int getSellFormId() {
        return sellFormId;
    }

    public void setSellFormId(int sellFormId) {
        this.sellFormId = sellFormId;
    }

    public String getSellFormTitle() {
        return sellFormTitle;
    }

    public void setSellFormTitle(String sellFormTitle) {
        this.sellFormTitle = sellFormTitle;
    }

    public int getSellFormCat() {
        return sellFormCat;
    }

    public void setSellFormCat(int sellFormCat) {
        this.sellFormCat = sellFormCat;
    }

    public int getSellFormType() {
        return sellFormType;
    }

    public void setSellFormType(int sellFormType) {
        this.sellFormType = sellFormType;
    }

    public int getSellFormResType() {
        return sellFormResType;
    }

    public void setSellFormResType(int sellFormResType) {
        this.sellFormResType = sellFormResType;
    }

    public String getSellFormDefValue() {
        return sellFormDefValue;
    }

    public void setSellFormDefValue(String sellFormDefValue) {
        this.sellFormDefValue = sellFormDefValue;
    }

    public String getSellFormOpt() {
        return sellFormOpt;
    }

    public void setSellFormOpt(String sellFormOpt) {
        this.sellFormOpt = sellFormOpt;
    }

    public String getSellFormPos() {
        return sellFormPos;
    }

    public void setSellFormPos(String sellFormPos) {
        this.sellFormPos = sellFormPos;
    }

    public String getSellFormLength() {
        return sellFormLength;
    }

    public void setSellFormLength(String sellFormLength) {
        this.sellFormLength = sellFormLength;
    }

    public String getSellMinValue() {
        return sellMinValue;
    }

    public void setSellMinValue(String sellMinValue) {
        this.sellMinValue = sellMinValue;
    }

    public String getSellMaxValue() {
        return sellMaxValue;
    }

    public void setSellMaxValue(String sellMaxValue) {
        this.sellMaxValue = sellMaxValue;
    }

    public String getSellFormDesc() {
        return sellFormDesc;
    }

    public void setSellFormDesc(String sellFormDesc) {
        this.sellFormDesc = sellFormDesc;
    }

    public String getSellFormOptsValues() {
        return sellFormOptsValues;
    }

    public void setSellFormOptsValues(String sellFormOptsValues) {
        this.sellFormOptsValues = sellFormOptsValues;
    }

//    public String getSellFormFieldDesc() {
//        return sellFormFieldDesc;
//    }
//
//    public void setSellFormFieldDesc(String sellFormFieldDesc) {
//        this.sellFormFieldDesc = sellFormFieldDesc;
//    }

    public String getSellFormParamId() {
        return sellFormParamId;
    }

    public void setSellFormParamId(String sellFormParamId) {
        this.sellFormParamId = sellFormParamId;
    }

    public String getSellFormParamValues() {
        return sellFormParamValues;
    }

    public void setSellFormParamValues(String sellFormParamValues) {
        this.sellFormParamValues = sellFormParamValues;
    }

    public String getSellFormParentId() {
        return sellFormParentId;
    }

    public void setSellFormParentId(String sellFormParentId) {
        this.sellFormParentId = sellFormParentId;
    }

    public String getSellFormParentValue() {
        return sellFormParentValue;
    }

    public void setSellFormParentValue(String sellFormParentValue) {
        this.sellFormParentValue = sellFormParentValue;
    }

    public String getSellFormUnit() {
        return sellFormUnit;
    }

    public void setSellFormUnit(String sellFormUnit) {
        this.sellFormUnit = sellFormUnit;
    }

    public String getSellFormOptions() {
        return sellFormOptions;
    }

    public void setSellFormOptions(String sellFormOptions) {
        this.sellFormOptions = sellFormOptions;
    }

    @Override
    public String toString() {
        return "SellFormField{" +
                "sellFormId=" + sellFormId +
                ", sellFormTitle='" + sellFormTitle + '\'' +
                ", sellFormCat=" + sellFormCat +
                ", sellFormType=" + sellFormType +
                ", sellFormResType=" + sellFormResType +
                ", sellFormDefValue='" + sellFormDefValue + '\'' +
                ", sellFormOpt='" + sellFormOpt + '\'' +
                ", sellFormPos='" + sellFormPos + '\'' +
                ", sellFormLength='" + sellFormLength + '\'' +
                ", sellMinValue='" + sellMinValue + '\'' +
                ", sellMaxValue='" + sellMaxValue + '\'' +
                ", sellFormDesc='" + sellFormDesc + '\'' +
                ", sellFormOptsValues='" + sellFormOptsValues + '\'' +
                ", sellFormParamId='" + sellFormParamId + '\'' +
                ", sellFormParamValues='" + sellFormParamValues + '\'' +
                ", sellFormParentId='" + sellFormParentId + '\'' +
                ", sellFormParentValue='" + sellFormParentValue + '\'' +
                ", sellFormUnit='" + sellFormUnit + '\'' +
                ", sellFormOptions='" + sellFormOptions + '\'' +
                '}';
    }
}
