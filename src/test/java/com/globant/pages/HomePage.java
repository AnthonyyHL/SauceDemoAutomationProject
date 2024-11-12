package com.globant.pages;

import com.globant.utils.Exceptions.HomePageException;
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

    public void clickHamburgerButton() throws HomePageException {
        try {
            waitToBeClickeable(hamburgerButton);
        } catch (Exception e) {
            throw new HomePageException(String.format("Error in home page: %s", e.getMessage()));
        }
        hamburgerButton.click();
    }

    public LoginPage logout() throws HomePageException {
        clickHamburgerButton();
        wait(3);
        logoutButton.click();
        return new LoginPage(super.getDriver());
    }
}
