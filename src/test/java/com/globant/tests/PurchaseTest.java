package com.globant.tests;

import com.globant.data.Data;
import com.globant.pages.*;
import com.globant.utils.Exceptions.*;
import com.globant.utils.baseTest.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.atomic.AtomicReference;

@Epic("Shopping Flow")
public class PurchaseTest extends BaseTest {
    @Test(dataProvider = "data-provider", dataProviderClass = Data.class)
    @Description("Verify successful product purchase flow from login to completion")
    @Severity(SeverityLevel.CRITICAL)
    public void purchaseTest(String username, String password) throws HomePageException {
        SoftAssert softAssert = new SoftAssert();

        LoginPage loginPage = Allure.step("1. Navigate to Login Page", this::loadFirstPage);
        HomePage homePage = null;
        CartPage cartPage = null;
        CheckoutStepOnePage checkoutStepOnePage = null;
        CheckoutStepTwoPage checkoutStepTwoPage = null;
        CheckoutCompletePage checkoutCompletePage;
        try {
            LoginPage finalLoginPage = loginPage;
            homePage = Allure.step("2. Login with credentials", () -> finalLoginPage.login(username, password));

            int numberOfProducts = 1;
            HomePage finalHomePage = homePage;
            Allure.step(String.format("3. Add %s products to cart", numberOfProducts), () -> finalHomePage.addToCart(numberOfProducts));

            cartPage = Allure.step("4. Navigate to Cart Page", homePage::openCart);

            AtomicReference<CartPage> finalCartPage = new AtomicReference<>(cartPage);
            AtomicReference<HomePage> secondFinalHomePage = new AtomicReference<>(homePage);
            HomePage finalHomePage1 = homePage;
            checkoutStepOnePage = Allure.step("5. Proceed to Checkout", () -> {
                try {
                    return finalCartPage.get().goToCheckoutStepOne();
                } catch (CartPageException e) {
                    secondFinalHomePage.set(finalCartPage.get().goBackToHomePage());
                    softAssert.fail("Failure for user " + username + " - " + e.getMessage());
                    finalHomePage1.logout();
                }
                return null;
            });

            CheckoutStepOnePage finalCheckoutStepOnePage = checkoutStepOnePage;
            AtomicReference<CartPage> finalCartPage1 = new AtomicReference<>(cartPage);
            AtomicReference<HomePage> homePage1 = new AtomicReference<>(homePage);
            checkoutStepTwoPage = Allure.step("6. Fill customer information", () -> {
                try {
                    return finalCheckoutStepOnePage.setPurchaseInfo();
                } catch (CheckoutStepOnePageException e) {
                    finalCartPage1.set(finalCheckoutStepOnePage.goBackToCartPage());
                    homePage1.set(finalCartPage1.get().goBackToHomePage());
                    homePage1.get().logout();
                    softAssert.fail("Failure for user " + username + " - " + e.getMessage());
                }
                return null;
            });

            CheckoutStepTwoPage finalCheckoutStepTwoPage = checkoutStepTwoPage;
            AtomicReference<HomePage> homePage2 = new AtomicReference<>(homePage);
            checkoutCompletePage = Allure.step("7. Review and confirm order", () -> {
                try {
                    return finalCheckoutStepTwoPage.confirmInfo();
                } catch (CheckoutStepTwoPageException e) {
                    homePage2.set(finalCheckoutStepTwoPage.goBackToHomePage());
                    homePage2.get().logout();
                    softAssert.fail("Failure for user " + username + " - " + e.getMessage());
                }
                return null;
            });

            Allure.step("8. Verify successful purchase message", () -> softAssert.assertTrue(checkoutCompletePage.isOrderCreatedSuccessfully("Thank you for your order!"),
                    "Purchase failed for user " + username));

            homePage = checkoutCompletePage.goBackToHomePage();
            loginPage = homePage.logout();
        } catch (LoginPageException e) {
            softAssert.fail("Failure for user " + username + " - " + e.getMessage());
        } catch (HomePageException e) {
            loginPage = homePage.logout();
            softAssert.fail("Failure for user " + username + " - " + e.getMessage());
        } catch (Exception e) {
            softAssert.fail("Unexpected failure for user " + username + " - " + e.getMessage());
        }

        softAssert.assertAll();
    }
}
