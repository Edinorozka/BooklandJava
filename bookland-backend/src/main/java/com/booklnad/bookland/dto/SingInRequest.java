package com.booklnad.bookland.dto;

public class SingInRequest {
    private String login;
    private String password;

    public SingInRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public SingInRequest() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
