package com.qa.simple.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.qa.simple.pages.LoginPage;
import com.qa.simple.pages.SecurePage;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import static com.codeborne.selenide.Condition.text;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Слой бизнес-шагов для формы авторизации.
 */
public class LoginSteps {
    private final LoginPage loginPage = new LoginPage();
    private final SecurePage securePage = new SecurePage();

    /**
     * Открывает страницу входа по базовому URL из конфигурации Selenide.
     */
    @Step("Шаг: Открываем страницу входа")
    public void openLoginPage() {
        Selenide.open(Configuration.baseUrl);
    }

    /**
     * Выполняет вход с заданными учётными данными.
     * <p>
     * Этот метод отвечает только за действие «логин». Он НЕ ждёт перехода
     * и НЕ гасит алерты — это позволяет использовать его и в позитивных,
     * и в негативных тестах без лишних ожиданий.
     *
     * @param username логин пользователя
     * @param password пароль пользователя
     */
    @Step("Шаг: Выполняем вход с логином '{0}' и паролем '{1}'")
    public void performLogin(String username, String password) {
        loginPage.login(username, password);
    }

    /**
     * Ждёт, что текущий URL содержит указанный фрагмент.
     * <p>
     * Используется как базовый шаг ожидания. Не выполняет никаких дополнительных действий.
     *
     * @param fragment ожидаемый фрагмент URL (например, {@code "secure"})
     */
    @Step("Шаг: Ожидаем, что URL содержит фрагмент '{0}'")
    public void waitForUrlContains(String fragment) {
        new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(fragment));
    }

    /**
     * Комбинированный шаг для позитивных сценариев:
     * 1. Ждёт, пока URL будет содержать указанный фрагмент (обычно "secure").
     * 2. Если переход подтверждён, принудительно закрывает системный алерт Chrome
     *    «Смените пароль» нажатием клавиши ESC.
     * <p>
     * Вызывай этот метод ТОЛЬКО в позитивных тестах, где ожидается успешный переход
     * на защищённую страницу.
     */
    @Step("Шаг: Ждём перехода на страницу с фрагментом '{0}' и гасим алерт при необходимости")
    public void ensureSecurePageAndSuppressAlert() {
        if (utils.EnvUtils.isCi()) {
            return; // В CI алертов нет, Robot не нужен
        }
        try {
            System.out.println("[INFO] Попытка закрыть алерт утечки пароля (ESC)...");
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            System.out.println("[INFO] Алерт обработан.");
        } catch (Exception e) {
            System.out.println("[DEBUG] Robot недоступен: " + e.getMessage());
        }
    }

    /**
     * Проверяет, что flash-сообщение содержит ожидаемый текст.
     */
    @Step("Шаг: Проверяем, что flash-сообщение содержит '{0}'")
    public void verifyFlashMessageContains(String expectedText) {
        loginPage.getFlashMessage().shouldHave(text(expectedText));
    }

    /**
     * Проверяет, что на защищённой странице отображается кнопка Logout.
     */
    @Step("Шаг: Проверяем, что на странице secure отображается кнопка Logout")
    public void verifyLogoutButtonVisible() {
        assertTrue(securePage.isLogoutButtonVisible(),
                "Кнопка Logout должна быть видима после успешного входа");
    }

    /**
     * Выполняет выход из системы — нажимает кнопку Logout.
     */
    @Step("Шаг: Выполняем выход из системы")
    public void performLogout() {
        securePage.clickLogout();
    }

    /**
     * Оставляет поле логина пустым, вводит только пароль и нажимает Login.
     */
    @Step("Шаг: Вводим только пароль '{0}' (логин пустой)")
    public void loginWithEmptyUsername(String password) {
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    /**
     * Оставляет поле пароля пустым, вводит только логин и нажимает Login.
     */
    @Step("Шаг: Вводим только логин '{0}' (пароль пустой)")
    public void loginWithEmptyPassword(String username) {
        loginPage.enterUsername(username);
        loginPage.clickLoginButton();
    }

    /**
     * Нажимает кнопку входа с обоими пустыми полями.
     */
    @Step("Шаг: Нажимаем Login с пустыми полями")
    public void loginWithEmptyFields() {
        loginPage.clickLoginButton();
    }

    /**
     * Проверяет, что пользователь остался на странице входа.
     */
    @Step("Шаг: Проверяем, что остались на странице входа (URL содержит '{0}')")
    public void verifyStayOnLoginPage(String expectedFragment) {
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertTrue(currentUrl.contains(expectedFragment),
                "Ожидалось, что URL содержит '" + expectedFragment + "', но был: " + currentUrl);
    }

    /**
     * Проверяет видимость поля логина.
     */
    @Step("Шаг: Проверяем, что поле логина видно")
    public void verifyUsernameFieldVisible() {
        assertTrue(loginPage.isUsernameFieldVisible(),
                "Поле ввода логина должно быть видно");
    }

    /**
     * Проверяет видимость поля пароля.
     */
    @Step("Шаг: Проверяем, что поле пароля видно")
    public void verifyPasswordFieldVisible() {
        assertTrue(loginPage.isPasswordFieldVisible(),
                "Поле ввода пароля должно быть видно");
    }

    /**
     * Проверяет видимость кнопки входа.
     */
    @Step("Шаг: Проверяем, что кнопка входа видна")
    public void verifyLoginButtonVisible() {
        assertTrue(loginPage.isLoginButtonVisible(),
                "Кнопка входа должна быть видна");
    }

    /**
     * Проверяет, что поле пароля маскирует ввод.
     */
    @Step("Шаг: Проверяем, что поле пароля маскирует ввод (type=password)")
    public void verifyPasswordIsMasked() {
        assertTrue(loginPage.isPasswordMasked(),
                "Поле пароля должно иметь type='password'");
    }

    /**
     * Проверяет текст кнопки входа.
     */
    @Step("Шаг: Проверяем, что текст кнопки входа — '{0}'")
    public void verifyLoginButtonText(String expectedText) {
        loginPage.getLoginButton().shouldHave(text(expectedText));
    }

    /**
     * Проверяет заголовок страницы.
     */
    @Step("Шаг: Проверяем, что заголовок страницы — '{0}'")
    public void verifyHeading(String expectedHeading) {
        loginPage.getHeading().shouldHave(text(expectedHeading));
    }
}
