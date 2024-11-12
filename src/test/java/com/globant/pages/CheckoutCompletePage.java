package com.globant.pages;

import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {
    @FindBy(css="#checkout_complete_container > h2")
    private WebElement completeCheckoutLabel;

    @FindBy(id="back-to-products")
    private WebElement goBackToHomePage;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public boolean isOrderCreatedSuccessfully(String successText) {
        waitToBeVisible(completeCheckoutLabel);
        return completeCheckoutLabel.isDisplayed() &&
                completeCheckoutLabel.getText().toLowerCase().equals(successText.toLowerCase());
    }

    public HomePage goBackToHomePage() {
        waitToBeClickeable(goBackToHomePage);
        goBackToHomePage.click();
        return new HomePage(super.getDriver());
    }
}
