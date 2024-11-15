package com.globant.tests;

import com.globant.data.Data;
import com.globant.pages.*;
import com.globant.utils.Exceptions.*;
import com.globant.utils.baseTest.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class RemoveFromCartTest extends BaseTest {
    @Test(dataProvider = "data-provider", dataProviderClass = Data.class)
    public void removeFromCartTest(String[] credentials) throws HomePageException {
        SoftAssert softAssert = new SoftAssert();

        LoginPage loginPage;
        HomePage homePage = null;
        CartPage cartPage = null;
        try {
            loginPage = loadFirstPage();
            homePage = loginPage.login(credentials[0], credentials[1]);
            homePage.addToCart(3);
            cartPage = homePage.openCart();
            cartPage.removeFromCart();

            softAssert.assertTrue(cartPage.isCartEmpty(),
                    "Checkout failed for user " + credentials[0]);

            homePage = cartPage.goBackToHomePage();
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
        } catch (Exception e) {
            softAssert.fail("Unexpected failure for user " + credentials[0] + " - " + e.getMessage());
        }

        softAssert.assertAll();
    }
}
