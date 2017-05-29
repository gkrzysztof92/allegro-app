package com.gacek.krzysztof.allegroapp.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;


@NamespaceList({
        @Namespace(prefix = "ns1", reference = "urn:SandboxWebApi")
})
@Root(name = "item")
@Namespace(reference = "urn:SandboxWebApi")
@Order(elements = {"stateId", "stateName"})
public class State {

    @Element @Namespace(reference = "urn:SandboxWebApi")
    private int stateId;
    @Element @Namespace(reference = "urn:SandboxWebApi")
    private String stateName;

    public State() {
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}
