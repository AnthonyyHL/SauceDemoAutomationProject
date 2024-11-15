package com.globant.pages;

import com.globant.utils.Exceptions.CartPageException;
import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {
    @FindBy(id="checkout")
    private WebElement checkoutButton;

    @FindBy(id="continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(css="div.cart_list > .cart_item")
    private List<WebElement> cartProducts;

    private List<WebElement> removeCartProductButtons = cartProducts.stream().map(
                productContainer -> productContainer.findElement(By.className("cart_button"))
            ).collect(Collectors.toList());

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

    public void removeFromCart() throws CartPageException {
        for (WebElement removeCartProductButton : removeCartProductButtons) {
            try {
                waitToBeClickeable(removeCartProductButton);
                removeCartProductButton.click();
            } catch (Exception e) {
                throw new CartPageException(String.format("Error cleaning cart: %s", e.getMessage()));
            }
        }
    }

    public boolean isCartEmpty() {
        return cartProducts.isEmpty();
    }
}
