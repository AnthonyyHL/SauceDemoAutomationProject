package com.globant.pages;

import com.globant.utils.Exceptions.CheckoutStepTwoPageException;
import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutStepTwoPage extends BasePage {
    @FindBy(id="finish")
    private WebElement finishButton;

    @FindBy(id="cancel")
    private WebElement cancelButton;

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutCompletePage confirmInfo() throws CheckoutStepTwoPageException {
        try {
            waitToBeClickeable(finishButton);
        } catch (Exception e) {
            throw new CheckoutStepTwoPageException(String.format("Error in checkout step two page: %s", e.getMessage()));
        }
        finishButton.click();
        try {
            finishButton.click();
            throw new CheckoutStepTwoPageException("The checkout step two page couldn't change");
        } catch (NoSuchElementException e) {
            return new CheckoutCompletePage(super.getDriver());
        }
    }

    public HomePage goBackToHomePage() {
        waitToBeClickeable(cancelButton);
        cancelButton.click();
        return new HomePage(super.getDriver());
    }
}
