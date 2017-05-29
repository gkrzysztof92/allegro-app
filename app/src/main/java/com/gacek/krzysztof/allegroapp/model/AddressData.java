package com.gacek.krzysztof.allegroapp.model;


public class AddressData {

    private String city;
    private String postCode;
    private Integer state;

    public AddressData() {
    }

    public AddressData(String city, String postCode, Integer state) {
        this.city = city;
        this.postCode = postCode;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AddressData{" +
                "city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", state=" + state +
                '}';
    }

}
