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
            waitToBeClickeable(hamburgerButton);
        } catch (Exception e) {
            throw new HomePageException(String.format("Error in home page: %s", e.getMessage()));
        }
        hamburgerButton.click();
    }

    public void addToCart(int numberOfProducts) throws HomePageException {
        int nMax = Math.min(numberOfProducts, addToCartButtons.size());
        for(int n = 0; n <= nMax; n++) {
            try {
                waitToBeClickeable(addToCartButtons.get(n));
            } catch (Exception e) {
                throw new HomePageException(String.format("Error in home page: %s", e.getMessage()));
            }
            addToCartButtons.get(n).click();
        }
    }

    public CartPage openCart() throws HomePageException {
        try {
            waitToBeClickeable(cartButton);
        } catch (Exception e) {
            throw new HomePageException(String.format("Error in home page: %s", e.getMessage()));
        }
        cartButton.click();
        return new CartPage(super.getDriver());
    }

    public LoginPage logout() throws HomePageException {
        clickHamburgerButton();
        wait(3);
        logoutButton.click();
        try {
            logoutButton.click();
            throw new HomePageException("The home page couldn't logout");
        } catch (NoSuchElementException e) {
            return new LoginPage(super.getDriver());
        }
    }
}
