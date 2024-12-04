package com.globant.tests;

import com.globant.data.Data;
import com.globant.pages.*;
import com.globant.utils.Exceptions.*;
import com.globant.utils.baseTest.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Shopping Cart")
public class RemoveFromCartTest extends BaseTest {

    @Description("Verify successful removal of items from shopping cart")
    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "data-provider", dataProviderClass = Data.class)
    public void removeFromCartTest(String username, String password) throws HomePageException {
        SoftAssert softAssert = new SoftAssert();

        LoginPage loginPage;
        HomePage homePage = null;
        CartPage cartPage = null;
        try {
            loginPage = Allure.step("1. Navigate to Login Page", this::loadFirstPage);

            LoginPage finalLoginPage = loginPage;
            homePage = Allure.step("2. Login with credentials", () -> finalLoginPage.login(username, password));

            int numberOfProducts = 3;
            HomePage finalHomePage = homePage;
            Allure.step(String.format("3. Add %s products to cart", numberOfProducts), () -> finalHomePage.addToCart(3));

            cartPage = Allure.step("4. Navigate to Cart Page", homePage::openCart);

            Allure.step("5. Remove items from cart", cartPage::removeFromCart);

            CartPage finalCartPage = cartPage;
            Allure.step("6. Verify cart is successfully cleared", () -> softAssert.assertTrue(finalCartPage.isCartEmpty(),
                        "Checkout failed for user " + username));

            homePage = cartPage.goBackToHomePage();
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
