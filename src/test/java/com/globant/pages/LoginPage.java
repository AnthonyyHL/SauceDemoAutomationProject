package com.globant.pages;

import com.globant.utils.Exceptions.LoginPageException;
import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(id="user-name")
    private WebElement usernameInput;

    @FindBy(id="password")
    private WebElement passwordInput;

    @FindBy(id="login-button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private void setUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    private void setPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /**
     * Performs login with provided user credentials, then opens {@link CartPage} after clicking on Cart Button.
     * @param username User username
     * @param password User password
     * @return {@link CartPage} instance
     * @throws LoginPageException If user can not be logged in due to an event that does not occur as expected
     */
    public HomePage login(String username, String password) throws LoginPageException {
        setUsername(username);
        setPassword(password);
        waitToBeClickable(loginButton);
        loginButton.click();
        try {
            loginButton.click();
            throw new LoginPageException("User couldn't login");
        } catch (NoSuchElementException e) {
            return new HomePage(super.getDriver());
        }

    }

    /**
     * Verifies if the current page is {@link LoginPage} by comparing Login Button text
     * @param buttonLabel Expected button label text on Login button
     * @return boolean True if texts match, false otherwise
     */
    public boolean isSuccessfullyLoggedOut(String buttonLabel) {
        waitToBeVisible(loginButton);
        return loginButton.isDisplayed() &&
                loginButton.getAccessibleName().toLowerCase().equals(buttonLabel.toLowerCase());
    }
}
