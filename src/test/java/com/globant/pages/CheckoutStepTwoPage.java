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

    /**
     * Confirms the information and finishes checkout,
     * then redirects user from {@link CheckoutStepTwoPage} to {@link CheckoutCompletePage}.
     * @return {@link CheckoutCompletePage} instance
     * @throws {@link CheckoutStepTwoPageException} If an event does not occur as expected or user can not be redirected to {@link CheckoutCompletePage}
     */
    public CheckoutCompletePage confirmInfo() throws CheckoutStepTwoPageException {
        try {
            waitToBeClickable(finishButton);
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

    /**
     * Redirects user from {@link CheckoutStepTwoPage} to {@link HomePage}
     * @return {@link HomePage} instance
     */
    public HomePage goBackToHomePage() {
        waitToBeClickable(cancelButton);
        cancelButton.click();
        return new HomePage(super.getDriver());
    }
}
