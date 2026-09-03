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
        Configuration.screenshots = true;          // явно включаем скриншоты Selenide (по умолчанию true, но лучше зафиксировать)
        Configuration.savePageSource = true;      // сохраняем HTML страницы при падении (очень полезно для отладки форм)

        // Настройка ChromeOptions для CI (GitHub Actions)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        Configuration.browserCapabilities = options;

        // Подключаем Allure с явными настройками вложений
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)                 // скриншоты в Allure
                .savePageSource(true)             // HTML страницы в Allure
                .includeSelenideSteps(true)      // шаги Selenide в отчёте (shouldBe, click и т.п.)
        );
    }


    @AfterAll
    static void globalTeardown() {
        SelenideLogger.removeListener("AllureSelenide");
    }
}
