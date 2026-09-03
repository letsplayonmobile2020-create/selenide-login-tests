package com.qa.simple.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object страницы авторизации {@code /login}.
 * <p>
 * Инкапсулирует все локаторы и базовые действия с элементами формы входа.
 * Локаторы спрятаны в приватные методы, что позволяет менять их в одном месте
 * без затрагивания Steps и Tests.
 *
 */
public class LoginPage {

    /**
     * Возвращает SelenideElement поля ввода логина.
     * <p>
     * Локатор: {@code [name='username']}.
     *
     * @return элемент поля логина
     */
    private SelenideElement usernameField() {
        return $("[name='username']");
    }

    /**
     * Возвращает SelenideElement поля ввода пароля.
     * <p>
     * Локатор: {@code [name='password']}.
     *
     * @return элемент поля пароля
     */
    private SelenideElement passwordField() {
        return $("[name='password']");
    }

    /**
     * Возвращает SelenideElement кнопки отправки формы.
     * <p>
     * Локатор: {@code button[type='submit']}.
     *
     * @return элемент кнопки входа
     */
    private SelenideElement loginButton() {
        return $("button[type='submit']");
    }

    /**
     * Возвращает SelenideElement flash-сообщения (успех/ошибка).
     * <p>
     * Локатор: {@code #flash}. Используется для проверки текста
     * как при успешном входе, так и при ошибках валидации.
     *
     * @return элемент flash-сообщения
     */
    private SelenideElement flashMessage() {
        return $("#flash");
    }

    /**
     * Возвращает SelenideElement заголовка страницы (тег {@code h2}).
     *
     * @return элемент заголовка
     */
    private SelenideElement pageHeading() {
        return $("h2");
    }

    /**
     * Возвращает SelenideElement подзаголовка страницы (тег {@code h4}).
     *
     * @return элемент подзаголовка
     */
    private SelenideElement subHeading() {
        return $("h4");
    }

    // === Действия ===

    /**
     * Выполняет полный сценарий входа: заполняет оба поля и нажимает кнопку.
     *
     * @param username логин пользователя
     * @param password пароль пользователя
     */
    public void login(String username, String password) {
        usernameField().setValue(username);
        passwordField().setValue(password);
        loginButton().click();
    }

    /**
     * Вводит значение в поле логина, не отправляя форму.
     *
     * @param username логин пользователя
     */
    public void enterUsername(String username) {
        usernameField().setValue(username);
    }

    /**
     * Вводит значение в поле пароля, не отправляя форму.
     *
     * @param password пароль пользователя
     */
    public void enterPassword(String password) {
        passwordField().setValue(password);
    }

    /**
     * Нажимает кнопку входа без заполнения полей.
     * Используется в негативных тестах с пустыми полями.
     */
    public void clickLoginButton() {
        loginButton().click();
    }

    // === Геттеры для ассертов в Steps ===

    /**
     * Возвращает элемент flash-сообщения для проверок в Steps.
     *
     * @return SelenideElement flash-сообщения
     */
    public SelenideElement getFlashMessage() {
        return flashMessage();
    }

    /**
     * Возвращает элемент заголовка страницы для проверок в Steps.
     *
     * @return SelenideElement заголовка
     */
    public SelenideElement getHeading() {
        return pageHeading();
    }

    /**
     * Возвращает элемент кнопки входа для проверок в Steps.
     *
     * @return SelenideElement кнопки входа
     */
    public SelenideElement getLoginButton() {
        return loginButton();
    }

    /**
     * Возвращает элемент поля логина для проверок в Steps.
     *
     * @return SelenideElement поля логина
     */
    public SelenideElement getUsernameField() {
        return usernameField();
    }

    /**
     * Возвращает элемент поля пароля для проверок в Steps.
     *
     * @return SelenideElement поля пароля
     */
    public SelenideElement getPasswordField() {
        return passwordField();
    }

    // === Булевые проверки ===

    /**
     * Проверяет, что поле логина отображается на странице.
     *
     * @return {@code true}, если поле логина видно
     */
    public boolean isUsernameFieldVisible() {
        return usernameField().isDisplayed();
    }

    /**
     * Проверяет, что поле пароля отображается на странице.
     *
     * @return {@code true}, если поле пароля видно
     */
    public boolean isPasswordFieldVisible() {
        return passwordField().isDisplayed();
    }

    /**
     * Проверяет, что кнопка входа отображается на странице.
     *
     * @return {@code true}, если кнопка входа видна
     */
    public boolean isLoginButtonVisible() {
        return loginButton().isDisplayed();
    }

    /**
     * Проверяет, что поле пароля маскирует ввод.
     * Проверяется атрибут {@code type="password"}.
     *
     * @return {@code true}, если ввод маскируется
     */
    public boolean isPasswordMasked() {
        return "password".equals(passwordField().getAttribute("type"));
    }
}
