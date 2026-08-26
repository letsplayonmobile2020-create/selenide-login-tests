package com.qa.simple.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement usernameField() {
        return $("[name='username']");
    }

    private SelenideElement passwordField() {
        return $("[name='password']");
    }

    private SelenideElement loginButton() {
        return $("button[type='submit']");
    }

    public void login(String username, String password) {
        usernameField().setValue(username);
        passwordField().setValue(password);
        loginButton().click();
    }
}