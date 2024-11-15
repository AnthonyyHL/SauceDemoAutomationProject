package com.globant.tests;

import com.globant.data.Data;
import com.globant.pages.*;
import com.globant.utils.Exceptions.*;
import com.globant.utils.baseTest.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PurchaseTest extends BaseTest {
    @Test(dataProvider = "data-provider", dataProviderClass = Data.class)
    public void purchaseTest(String[] credentials) throws HomePageException {
        SoftAssert softAssert = new SoftAssert();

        LoginPage loginPage;
        HomePage homePage = null;
        CartPage cartPage = null;
        CheckoutStepOnePage checkoutStepOnePage = null;
        CheckoutStepTwoPage checkoutStepTwoPage = null;
        CheckoutCompletePage checkoutCompletePage;
        try {
            loginPage = loadFirstPage();
            homePage = loginPage.login(credentials[0], credentials[1]);
            homePage.addToCart(1);
            cartPage = homePage.openCart();
            checkoutStepOnePage = cartPage.goToCheckoutStepOne();
            checkoutStepTwoPage = checkoutStepOnePage.setPurchaseInfo();
            checkoutCompletePage = checkoutStepTwoPage.confirmInfo();

            softAssert.assertTrue(checkoutCompletePage.isOrderCreatedSuccessfully("Thank you for your order!"),
                    "Purchase failed for user " + credentials[0]);

            homePage = checkoutCompletePage.goBackToHomePage();
            loginPage = homePage.logout();
        } catch (LoginPageException e) {
            softAssert.fail("Failure for user " + credentials[0] + " - " + e.getMessage());
        } catch (HomePageException e) {
            loginPage = homePage.logout();
            softAssert.fail("Failure for user " + credentials[0] + " - " + e.getMessage());
        } catch (CartPageException e) {
            homePage = cartPage.goBackToHomePage();
            loginPage = homePage.logout();
            softAssert.fail("Failure for user " + credentials[0] + " - " + e.getMessage());
        } catch (CheckoutStepOnePageException e) {
            cartPage = checkoutStepOnePage.goBackToCartPage();
            homePage = cartPage.goBackToHomePage();
            loginPage = homePage.logout();
            softAssert.fail("Failure for user " + credentials[0] + " - " + e.getMessage());
        } catch (CheckoutStepTwoPageException e) {
            homePage = checkoutStepTwoPage.goBackToHomePage();
            loginPage = homePage.logout();
            softAssert.fail("Failure for user " + credentials[0] + " - " + e.getMessage());
        } catch (Exception e) {
            softAssert.fail("Unexpected failure for user " + credentials[0] + " - " + e.getMessage());
        }

        softAssert.assertAll();
    }
}
