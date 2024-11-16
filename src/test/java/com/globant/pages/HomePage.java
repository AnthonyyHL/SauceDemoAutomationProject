package com.globant.pages;

import com.globant.utils.Exceptions.HomePageException;
import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {
    @FindBy(id="react-burger-menu-btn")
    private WebElement hamburgerButton;

    @FindBy(id="logout_sidebar_link")
    private WebElement logoutButton;

    @FindBy(className="shopping_cart_link")
    private WebElement cartButton;

    @FindBy(css="div.inventory_item:nth-child(-n+3)")
    private List<WebElement> addToCartButtonsContainer;

    private List<WebElement> addToCartButtons = addToCartButtonsContainer.stream()
            .map(productButton -> productButton.findElement(By.cssSelector("div.pricebar > button")))
            .collect(Collectors.toList());

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickHamburgerButton() throws HomePageException {
        try {
            waitToBeClickable(hamburgerButton);
        } catch (Exception e) {
            throw new HomePageException(String.format("Error in home page: %s", e.getMessage()));
        }
        hamburgerButton.click();
    }

    /**
     * Iterates the number (established as a parameter) of products in {@link HomePage} and add it to {@link CartPage}
     * @throws HomePageException If an event does not occur as expected
     */
    public void addToCart(int numberOfProducts) throws HomePageException {
        int nMax = Math.min(numberOfProducts, addToCartButtons.size());
        for(int n = 0; n < nMax; n++) {
            try {
                waitToBeClickable(addToCartButtons.get(n));
            } catch (Exception e) {
                throw new HomePageException(String.format("Error in home page: %s", e.getMessage()));
            }
            addToCartButtons.get(n).click();
        }
    }

    /**
     * Opens {@link CartPage} after clicking on Cart Button
     * @return {@link CartPage} instance
     * @throws HomePageException If an event does not occur as expected
     */
    public CartPage openCart() throws HomePageException {
        try {
            waitToBeClickable(cartButton);
        } catch (Exception e) {
            throw new HomePageException(String.format("Error in home page: %s", e.getMessage()));
        }
        cartButton.click();
        return new CartPage(super.getDriver());
    }

    /**
     * Opens {@link LoginPage} after clicking on Logout Button in the navigation bar
     * @return {@link LoginPage} instance
     * @throws HomePageException If an event does not occur as expected or user can not be redirected to {@link LoginPage}
     */
    public LoginPage logout() throws HomePageException {
        clickHamburgerButton();
        waitToBeVisible(logoutButton);
        logoutButton.click();
        try {
            logoutButton.click();
            throw new HomePageException("User couldn't logout");
        } catch (NoSuchElementException e) {
            return new LoginPage(super.getDriver());
        }
    }
}
