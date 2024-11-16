package com.globant.tests;

import com.globant.data.Data;
import com.globant.pages.*;
import com.globant.utils.Exceptions.*;
import com.globant.utils.baseTest.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PurchaseTest extends BaseTest {
    @Test(dataProvider = "data-provider", dataProviderClass = Data.class)
    public void purchaseTest(String username, String password) throws HomePageException {
        SoftAssert softAssert = new SoftAssert();

        LoginPage loginPage = loadFirstPage();
        HomePage homePage = null;
        CartPage cartPage = null;
        CheckoutStepOnePage checkoutStepOnePage = null;
        CheckoutStepTwoPage checkoutStepTwoPage = null;
        CheckoutCompletePage checkoutCompletePage;
        try {
            homePage = loginPage.login(username, password);
            homePage.addToCart(1);
            cartPage = homePage.openCart();
            checkoutStepOnePage = cartPage.goToCheckoutStepOne();
            checkoutStepTwoPage = checkoutStepOnePage.setPurchaseInfo();
            checkoutCompletePage = checkoutStepTwoPage.confirmInfo();

            softAssert.assertTrue(checkoutCompletePage.isOrderCreatedSuccessfully("Thank you for your order!"),
                    "Purchase failed for user " + username);

            homePage = checkoutCompletePage.goBackToHomePage();
            loginPage = homePage.logout();
        } catch (LoginPageException e) {
            softAssert.fail("Failure for user " + username + " - " + e.getMessage());
        } catch (HomePageException e) {
            loginPage = homePage.logout();
            softAssert.fail("Failure for user " + username + " - " + e.getMessage());
        } catch (CartPageException e) {
            homePage = cartPage.goBackToHomePage();
            loginPage = homePage.logout();
            softAssert.fail("Failure for user " + username + " - " + e.getMessage());
        } catch (CheckoutStepOnePageException e) {
            cartPage = checkoutStepOnePage.goBackToCartPage();
            homePage = cartPage.goBackToHomePage();
            loginPage = homePage.logout();
            softAssert.fail("Failure for user " + username + " - " + e.getMessage());
        } catch (CheckoutStepTwoPageException e) {
            homePage = checkoutStepTwoPage.goBackToHomePage();
            loginPage = homePage.logout();
            softAssert.fail("Failure for user " + username + " - " + e.getMessage());
        } catch (Exception e) {
            softAssert.fail("Unexpected failure for user " + username + " - " + e.getMessage());
        }

        softAssert.assertAll();
    }
}
