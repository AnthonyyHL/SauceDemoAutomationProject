package com.globant.pages;

import com.globant.utils.Exceptions.CheckoutStepOnePageException;
import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    @FindBy(css=".checkout_buttons > #cancel")
    private WebElement cancelButton;

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    public void setPersonalInfo(String firstname, String lastname, String postalCode) {

        waitToBeVisible(firstNameInput);
        firstNameInput.sendKeys(firstname);

        waitToBeVisible(lastNameInput);
        lastNameInput.sendKeys(lastname);

        waitToBeVisible(postalCodeInput);
        postalCodeInput.sendKeys(postalCode);
    }

    public CheckoutStepTwoPage setPurchaseInfo() throws CheckoutStepOnePageException {
        setPersonalInfo("testFirstname", "testLastname", "0000");
        try {
            waitToBeClickeable(continueButton);
        } catch (Exception e) {
            throw new CheckoutStepOnePageException(String.format("Error in checkout step one page: %s", e.getMessage()));
        }
        continueButton.click();
        return new CheckoutStepTwoPage(super.getDriver());
    }

    public HomePage goBackToHomePage() {
        waitToBeClickeable(cancelButton);
        cancelButton.click();
        return new HomePage(super.getDriver());
    }
}
