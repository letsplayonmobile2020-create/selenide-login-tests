package com.qa.simple.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.qa.simple.pages.LoginPage;
import io.qameta.allure.Step; // 1. Добавляем этот импорт
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginSteps {
    private final LoginPage loginPage = new LoginPage();

    @Step("Шаг: Открываем страницу входа по адресу {0}")
    public void openLoginPage() {
        Selenide.open(Configuration.baseUrl);
    }

    @Step("Шаг: Выполняем вход с логином '{0}' и паролем '{1}'")
    public void performLogin(String username, String password) {
       loginPage.login(username, password);
    }

    @Step("Шаг: Ожидаем, что URL содержит фрагмент '{0}'")
    public void waitForUrlContains(String fragment) {
        new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(fragment));
    }
}
