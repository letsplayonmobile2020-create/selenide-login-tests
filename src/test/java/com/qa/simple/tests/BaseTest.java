package com.qa.simple.tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import com.qa.simple.config.TestConfig;

public class BaseTest {

    @BeforeAll
    static void globalSetup() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.holdBrowserOpen = false;
        Configuration.timeout = 10000;
        Configuration.baseUrl = TestConfig.getBaseUrl();
    }
}