package com.qa.simple.tests;

import com.qa.simple.steps.LoginSteps;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {

    private final LoginSteps loginSteps = new LoginSteps();

    @Test
    void testSuccessfulLogin() {
        loginSteps.openLoginPage();
        loginSteps.performLogin("tomsmith", "SuperSecretPassword!");
        loginSteps.waitForUrlContains("secure");
    }
}