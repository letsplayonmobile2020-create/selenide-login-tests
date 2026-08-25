package com.qa.simple.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import com.qa.simple.config.TestConfig;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AllureJunit5.class)
public class BaseTest {

    @BeforeAll
    static void globalSetup() {
        Configuration.browser = "chrome";
        Configuration.holdBrowserOpen = false;
        Configuration.timeout = 10000;
        Configuration.baseUrl = TestConfig.getBaseUrl();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()); //Когда произойдет ошибка, сделай скриншот, сохрани его и скажи Allure, чтобы он прикрепил его к отчету
        WebDriverManager.chromedriver().setup();
    }
}