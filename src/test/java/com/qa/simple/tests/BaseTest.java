package com.qa.simple.tests;

import com.codeborne.selenide.Configuration;
import com.qa.simple.config.TestConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Базовый класс для всех UI-тестов проекта.
 * Решение проблемы с окном "Смените пароль":
 * 1. Настройки Chrome (профили, флаги) - чтобы окно не появлялось.
 * 2. Robot.keyPress(VK_ESCAPE) - чтобы закрыть окно, если оно все же появилось.
 */
public class BaseTest {

    @BeforeAll
    static void globalSetup() {
        // Базовые настройки Selenide
        Configuration.timeout = 10000;
        Configuration.baseUrl = TestConfig.getBaseUrl();
        Configuration.reportsFolder = "target/allure-results";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        ChromeOptions options = new ChromeOptions();
        Configuration.browserCapabilities = options;
        // Подключаем Allure
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterAll
    static void globalTeardown() {
        SelenideLogger.removeListener("AllureSelenide");
    }
}
