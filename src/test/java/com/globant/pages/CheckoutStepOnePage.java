package com.globant.pages;

import com.globant.utils.Exceptions.CheckoutStepOnePageException;
import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class CheckoutStepOnePage extends BasePage {
    @FindBy(id="first-name")
    private WebElement firstNameInput;

    @FindBy(id="last-name")
    private WebElement lastNameInput;

    @FindBy(id="postal-code")
    private WebElement postalCodeInput;

    @FindBy(id="continue")
    private WebElement continueButton;

    @FindBy(id="cancel")
    private WebElement cancelButton;

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Sets First Name, Last Name and Postal Code for {@link CheckoutStepOnePage}.
     *
     */
    public void setPersonalInfo(String firstname, String lastname, String postalCode) {

        waitToBeVisible(firstNameInput);
        firstNameInput.sendKeys(firstname);

        waitToBeVisible(lastNameInput);
        lastNameInput.sendKeys(lastname);

        waitToBeVisible(postalCodeInput);
        postalCodeInput.sendKeys(postalCode);
    }

    /**
     * Calls {@code setPersonalInfo()} and submit the information
     * @return {@link CheckoutStepTwoPage} instance
     * @throws CheckoutStepOnePageException If an event does not occur as expected or user can not be redirected to {@link CheckoutStepTwoPage}
     */
    public CheckoutStepTwoPage setPurchaseInfo() throws CheckoutStepOnePageException {
        setPersonalInfo("testFirstname", "testLastname", "0000");

        try {
            waitToBeClickable(continueButton);
        } catch (Exception e) {
            throw new CheckoutStepOnePageException(String.format("Error in checkout step one page: %s", e.getMessage()));
        }
        continueButton.click();
        try {
            continueButton.click();
            throw new CheckoutStepOnePageException("The checkout step one page couldn't change");
        } catch (NoSuchElementException e) {
            return new CheckoutStepTwoPage(super.getDriver());
        }
    }

    /**
     * Redirects user from {@link CheckoutStepOnePage} to {@link CartPage}
     * @return {@link CartPage} instance
     */
    public CartPage goBackToCartPage() {
        waitToBeClickable(cancelButton);
        cancelButton.click();
        return new CartPage(super.getDriver());
    }
}
