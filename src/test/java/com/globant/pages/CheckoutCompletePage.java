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

    /**
     * Verifies if the checkout is successfully by comparing Success Label text
     * @param successText Expected label text on Success Label
     * @return boolean True if texts match, false otherwise
     */
    public boolean isOrderCreatedSuccessfully(String successText) {
        waitToBeVisible(completeCheckoutLabel);
        return completeCheckoutLabel.isDisplayed() &&
                completeCheckoutLabel.getText().toLowerCase().equals(successText.toLowerCase());
    }

    /**
     * Redirects user from {@link CheckoutCompletePage} to {@link HomePage}
     * @return {@link HomePage} instance
     */
    public HomePage goBackToHomePage() {
        waitToBeClickable(goBackToHomePage);
        goBackToHomePage.click();
        return new HomePage(super.getDriver());
    }
}
