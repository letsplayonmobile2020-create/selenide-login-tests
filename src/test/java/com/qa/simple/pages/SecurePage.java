package com.qa.simple.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.byText;

/**
 * Page Object защищённой страницы {@code /secure}.
 * <p>
 * Открывается после успешной авторизации. Содержит кнопку выхода
 * и flash-сообщение с подтверждением входа.
 *
 */
public class SecurePage {

    /**
     * Возвращает SelenideElement flash-сообщения на защищённой странице.
     * <p>
     * Локатор: {@code #flash}. Содержит текст «You logged into a secure area».
     *
     * @return элемент flash-сообщения
     */
    private SelenideElement flashMessage() {
        return $("#flash");
    }

    /**
     * Возвращает SelenideElement кнопки выхода (Logout).
     * <p>
     * Локатор: по тексту {@code "Logout"}.
     *
     * @return элемент кнопки выхода
     */
    private SelenideElement logoutButton() {
        return $(byText("Logout"));
    }

    /**
     * Возвращает SelenideElement заголовка защищённой страницы (тег {@code h2}).
     *
     * @return элемент заголовка
     */
    private SelenideElement pageHeading() {
        return $("h2");
    }

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
     * Возвращает элемент кнопки выхода для проверок в Steps.
     *
     * @return SelenideElement кнопки Logout
     */
    public SelenideElement getLogoutButton() {
        return logoutButton();
    }

    /**
     * Нажимает кнопку выхода и инициирует возврат на страницу входа.
     */
    public void clickLogout() {
        logoutButton().click();
    }

    /**
     * Проверяет, что кнопка выхода отображается на защищённой странице.
     *
     * @return {@code true}, если кнопка Logout видна
     */
    public boolean isLogoutButtonVisible() {
        return logoutButton().isDisplayed();
    }
}
