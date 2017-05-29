package com.gacek.krzysztof.allegroapp.model;


public class AccountData {

    private String email;
    private String webApiKey;
    private Integer localeVersion;
    private String password;

    public AccountData() {
    }

    public AccountData(String email, String webApiKey, Integer localeVersion, String password) {
        this.email = email;
        this.webApiKey = webApiKey;
        this.localeVersion = localeVersion;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebApiKey() {
        return webApiKey;
    }

    public void setWebApiKey(String webApiKey) {
        this.webApiKey = webApiKey;
    }

    public Integer getLocaleVersion() {
        return localeVersion;
    }

    public void setLocaleVersion(Integer localeVersion) {
        this.localeVersion = localeVersion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountData{" +
                "email='" + email + '\'' +
                ", webApiKey='" + webApiKey + '\'' +
                ", localeVersion=" + localeVersion +
                ", password='" + password + '\'' +
                '}';
    }

}
