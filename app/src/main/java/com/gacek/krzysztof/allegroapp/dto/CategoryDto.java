package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@NamespaceList({
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
@Root(name = "item")
@Namespace(reference = "urn:SandboxWebApi")
public class CategoryDto {

    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int catId;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String catName;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int catParent;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int catPosition;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int catIsProductCatalogueEnabled;

    public CategoryDto() {
    }

    public CategoryDto(int catId, String catName, int catParent, int catPosition, int catIsProductCatalogueEnabled) {
        this.catId = catId;
        this.catName = catName;
        this.catParent = catParent;
        this.catPosition = catPosition;
        this.catIsProductCatalogueEnabled = catIsProductCatalogueEnabled;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatParent() {
        return catParent;
    }

    public void setCatParent(int catParent) {
        this.catParent = catParent;
    }

    public int getCatPosition() {
        return catPosition;
    }

    public void setCatPosition(int catPosition) {
        this.catPosition = catPosition;
    }

    public int getCatIsProductCatalogueEnabled() {
        return catIsProductCatalogueEnabled;
    }

    public void setCatIsProductCatalogueEnabled(int catIsProductCatalogueEnabled) {
        this.catIsProductCatalogueEnabled = catIsProductCatalogueEnabled;
    }
}
