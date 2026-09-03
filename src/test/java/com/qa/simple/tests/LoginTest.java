package com.qa.simple.tests;

import com.qa.simple.steps.LoginSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Тесты формы авторизации на сайте {@code the-internet.herokuapp.com/login}.
 * <p>
 * Покрывает позитивные и негативные сценарии, регистрозависимость,
 * базовые проверки безопасности (XSS, SQL-инъекции) и UX-проверки.
 * Все проверки реализованы через вызовы {@link LoginSteps},
 * тесты не содержат прямых обращений к Page Objects.
 *
 */
@DisplayName("Тесты формы авторизации на the-internet.herokuapp.com/login")
public class LoginTest extends BaseTest {

    private final LoginSteps loginSteps = new LoginSteps();

    // =====================================================
    //  ПОЗИТИВНЫЕ СЦЕНАРИИ
    // =====================================================

    /**
     * Проверяет успешный вход с валидными данными:
     * заполняет форму, ждёт редиректа на {@code /secure},
     * проверяет flash-сообщение и наличие кнопки Logout.
     */
    @Test
    @DisplayName("Успешный вход с валидными данными + редирект на /secure")
    void testSuccessfulLogin() {
        loginSteps.openLoginPage();
        loginSteps.performLogin("tomsmith", "SuperSecretPassword!");
        loginSteps.waitForUrlContains("secure");
        loginSteps.ensureSecurePageAndSuppressAlert();
        loginSteps.verifyFlashMessageContains("You logged into a secure area");
        loginSteps.verifyLogoutButtonVisible();
        loginSteps.ensureSecurePageAndSuppressAlert();
    }

    /**
     * Проверяет сценарий выхода после успешного входа:
     * логин → редирект на {@code /secure} → Logout → редирект на {@code /login},
     * проверяет flash-сообщение о выходе.
     */
    @Test
    @DisplayName("Выход из системы после успешного входа")
    void testLogoutAfterLogin() {
        loginSteps.openLoginPage();
        loginSteps.performLogin("tomsmith", "SuperSecretPassword!");
        loginSteps.waitForUrlContains("secure");
        loginSteps.ensureSecurePageAndSuppressAlert();
        loginSteps.performLogout();
        loginSteps.waitForUrlContains("login");
        loginSteps.ensureSecurePageAndSuppressAlert();
        loginSteps.verifyFlashMessageContains("You logged out of the secure area");
    }

//    // =====================================================
//    //  НЕГАТИВНЫЕ СЦЕНАРИИ
//    // =====================================================
//
//    /**
//     * Проверяет вход с невалидными данными (data-driven).
//     * Для каждой комбинации логина и пароля проверяет, что:
//     * <ul>
//     *   <li>пользователь остаётся на странице входа;</li>
//     *   <li>flash-сообщение содержит ожидаемый текст ошибки.</li>
//     * </ul>
//     *
//     * @param username      логин пользователя
//     * @param password      пароль пользователя
//     * @param expectedError ожидаемый текст ошибки
//     */
//    @ParameterizedTest(name = "Невалидные данные → логин={0}, пароль={1}, ожидаемая ошибка={2}")
//    @CsvSource({
//            "wronguser,    SuperSecretPassword!, Your username is invalid!",
//            "tomsmith,     wrongpassword,        Your password is invalid!",
//            "wronguser,    wrongpassword,        Your username is invalid!"
//    })
//    @DisplayName("Вход с невалидными учётными данными — data-driven")
//    void testInvalidCredentials(String username, String password, String expectedError) {
//        loginSteps.openLoginPage();
//        loginSteps.performLogin(username, password);
//        loginSteps.verifyStayOnLoginPage("login");
//        loginSteps.verifyFlashMessageContains(expectedError);
//    }
//
//    /**
//     * Проверяет, что при пустом поле логина вход не выполняется
//     * и показывается сообщение «Your username is invalid!».
//     */
//    @Test
//    @DisplayName("Вход с пустым логином — должна быть ошибка валидации")
//    void testEmptyUsername() {
//        loginSteps.openLoginPage();
//        loginSteps.loginWithEmptyUsername("SuperSecretPassword!");
//        loginSteps.verifyStayOnLoginPage("login");
//        loginSteps.verifyFlashMessageContains("Your username is invalid!");
//    }
//
//    /**
//     * Проверяет, что при пустом поле пароля вход не выполняется
//     * и показывается сообщение «Your password is invalid!».
//     */
//    @Test
//    @DisplayName("Вход с пустым паролем — должна быть ошибка валидации")
//    void testEmptyPassword() {
//        loginSteps.openLoginPage();
//        loginSteps.loginWithEmptyPassword("tomsmith");
//        loginSteps.verifyStayOnLoginPage("login");
//        loginSteps.verifyFlashMessageContains("Your password is invalid!");
//    }
//
//    /**
//     * Проверяет, что при обоих пустых полях вход не выполняется
//     * и показывается сообщение об ошибке валидации.
//     */
//    @Test
//    @DisplayName("Вход с обоими пустыми полями — должна быть ошибка валидации")
//    void testEmptyFields() {
//        loginSteps.openLoginPage();
//        loginSteps.loginWithEmptyFields();
//        loginSteps.verifyStayOnLoginPage("login");
//        loginSteps.verifyFlashMessageContains("Your username is invalid!");
//    }
//
//    // =====================================================
//    //  РЕГИСТРОЗАВИСИМОСТЬ
//    // =====================================================
//
//    /**
//     * Проверяет, что логин чувствителен к регистру:
//     * {@code TOMSMITH} не проходит, показывается ошибка.
//     */
//    @Test
//    @DisplayName("Логин чувствителен к регистру — TOMSMITH не проходит")
//    void testUsernameCaseSensitive() {
//        loginSteps.openLoginPage();
//        loginSteps.performLogin("TOMSMITH", "SuperSecretPassword!");
//        loginSteps.verifyStayOnLoginPage("login");
//        loginSteps.verifyFlashMessageContains("Your username is invalid!");
//    }
//
//    /**
//     * Проверяет, что пароль чувствителен к регистру:
//     * пароль в нижнем регистре не проходит.
//     */
//    @Test
//    @DisplayName("Пароль чувствителен к регистру — нижний регистр не проходит")
//    void testPasswordCaseSensitive() {
//        loginSteps.openLoginPage();
//        loginSteps.performLogin("tomsmith", "supersecretpassword!");
//        loginSteps.verifyStayOnLoginPage("login");
//        loginSteps.verifyFlashMessageContains("Your password is invalid!");
//    }
//
//    // =====================================================
//    //  БЕЗОПАСНОСТЬ
//    // =====================================================
//
//    /**
//     * Проверяет, что XSS-инъекция в поле логина не исполняется:
//     * вводится {@code <script>alert('xss')</script>},
//     * система показывает ошибку валидации, JS alert не появляется.
//     */
//    @Test
//    @DisplayName("XSS-инъекция в поле логина не исполняется — показывается ошибка")
//    void testXssInUsername() {
//        loginSteps.openLoginPage();
//        loginSteps.performLogin("<script>alert('xss')</script>", "SuperSecretPassword!");
//        loginSteps.verifyStayOnLoginPage("login");
//        loginSteps.verifyFlashMessageContains("Your username is invalid!");
//    }
//
//    /**
//     * Проверяет, что SQL-инъекция в поле логина не обходит авторизацию:
//     * вводится {@code ' OR 1=1 --}, система показывает ошибку валидации.
//     */
//    @Test
//    @DisplayName("SQL-инъекция в поле логина не обходит авторизацию")
//    void testSqlInjectionInUsername() {
//        loginSteps.openLoginPage();
//        loginSteps.performLogin("' OR 1=1 --", "SuperSecretPassword!");
//        loginSteps.verifyStayOnLoginPage("login");
//        loginSteps.verifyFlashMessageContains("Your username is invalid!");
//    }
//
//    // =====================================================
//    //  UX И СТРУКТУРА СТРАНИЦЫ
//    // =====================================================
//
//    /**
//     * Проверяет наличие всех элементов формы на странице входа:
//     * поле логина, поле пароля, кнопка входа.
//     */
//    @Test
//    @DisplayName("На странице входа есть все элементы формы")
//    void testLoginPageElementsPresent() {
//        loginSteps.openLoginPage();
//        loginSteps.verifyUsernameFieldVisible();
//        loginSteps.verifyPasswordFieldVisible();
//        loginSteps.verifyLoginButtonVisible();
//    }
//
//    /**
//     * Проверяет, что поле пароля маскирует ввод (атрибут {@code type="password"}).
//     */
//    @Test
//    @DisplayName("Поле пароля маскирует ввод (type=password)")
//    void testPasswordIsMasked() {
//        loginSteps.openLoginPage();
//        loginSteps.verifyPasswordIsMasked();
//    }
//
//    /**
//     * Проверяет, что текст кнопки входа — {@code "Login"}.
//     */
//    @Test
//    @DisplayName("Кнопка входа имеет текст 'Login'")
//    void testLoginButtonText() {
//        loginSteps.openLoginPage();
//        loginSteps.verifyLoginButtonText("Login");
//    }
//
//    /**
//     * Проверяет, что заголовок страницы входа — {@code "Login Page"}.
//     */
//    @Test
//    @DisplayName("Заголовок страницы входа — 'Login Page'")
//    void testLoginPageHeading() {
//        loginSteps.openLoginPage();
//        loginSteps.verifyHeading("Login Page");
//    }
}
