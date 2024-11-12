package com.globant.pages;

import com.globant.utils.Exceptions.CheckoutStepTwoPageException;
import com.globant.utils.basePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutStepTwoPage extends BasePage {
    @FindBy(id="finish")
    private WebElement finishButton;

    @FindBy(css=".cart_footer > #cancel")
    private WebElement cancelButton;

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutCompletePage confirmInfo() throws CheckoutStepTwoPageException {
        System.out.println(finishButton.getText());
        try {
            waitToBeClickeable(finishButton);
        } catch (Exception e) {
            throw new CheckoutStepTwoPageException(String.format("Error in checkout step two page: %s", e.getMessage()));
        }
        finishButton.click();
        return new CheckoutCompletePage(super.getDriver());
    }

    public HomePage goBackToHomePage() {
        waitToBeClickeable(cancelButton);
        cancelButton.click();
        return new HomePage(super.getDriver());
    }
}
