package com.qa.simple.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.qa.simple.pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginSteps {
    private final LoginPage loginPage = new LoginPage();

    public void openLoginPage() {
        Selenide.open(Configuration.baseUrl);
    }

    public void performLogin(String username, String password) {
        loginPage.login(username, password);
    }

    public void waitForUrlContains(String fragment) {
        new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(fragment));
    }
}