package com.globant.pages;

import com.globant.utils.Exceptions.CartPageException;
import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {
    @FindBy(id="checkout")
    private WebElement checkoutButton;

    @FindBy(id="continue-shopping")
    private WebElement continueShoppingButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutStepOnePage goToCheckoutStepOne() throws CartPageException {
        try {
            waitToBeClickeable(checkoutButton);
        } catch (Exception e) {
            throw new CartPageException(String.format("Error in cart page: %s", e.getMessage()));
        }
        checkoutButton.click();
        try {
            checkoutButton.click();
            throw new CartPageException("The cart page couldn't change");
        } catch (NoSuchElementException e) {
            return new CheckoutStepOnePage(super.getDriver());
        }
    }

    public HomePage goBackToHomePage() {
        waitToBeClickeable(continueShoppingButton);
        continueShoppingButton.click();
        return new HomePage(super.getDriver());
    }
}
