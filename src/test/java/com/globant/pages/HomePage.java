package com.globant.pages;

import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(id="react-burger-menu-btn")
    private WebElement hamburgerButton;

    @FindBy(id="logout_sidebar_link")
    private WebElement logoutButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickHamburgerButton() {
        waitToBeClickeable(hamburgerButton);
        hamburgerButton.click();
    }

    public LoginPage logout() {
        clickHamburgerButton();
        wait(3);
        logoutButton.click();
        return new LoginPage(super.getDriver());
    }
}
