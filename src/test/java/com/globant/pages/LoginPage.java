package com.globant.pages;

import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    public void setUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public HomePage login(String username, String password) {
        setUsername(username);
        setPassword(password);
        waitToBeClickeable(loginButton);
        loginButton.click();
        return new HomePage(super.getDriver());
    }

    public boolean isSuccessfullyLoggedOut(String buttonLabel) {
        waitToBeVisible(loginButton);
        return loginButton.isDisplayed() &&
                loginButton.getAccessibleName().toLowerCase().equals(buttonLabel.toLowerCase());
    }
}
