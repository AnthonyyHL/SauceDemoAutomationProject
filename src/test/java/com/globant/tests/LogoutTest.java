package com.globant.tests;

import com.globant.data.Data;
import com.globant.pages.HomePage;
import com.globant.pages.LoginPage;
import com.globant.utils.baseTest.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Logout")
public class LogoutTest extends BaseTest {
    @Test(dataProvider = "data-provider", dataProviderClass = Data.class, alwaysRun = true)
    @Description("Verify user can successfully logout after login")
    @Severity(SeverityLevel.CRITICAL)
    public void logoutTest(String username, String password) {
        SoftAssert softAssert = new SoftAssert();
        try {
            LoginPage loginPage = Allure.step("1. Navigate to Login Page", this::loadFirstPage);

            LoginPage finalLoginPage = loginPage;
            HomePage homePage = Allure.step("2. Login with credentials", () -> finalLoginPage.login(username, password));

            loginPage = Allure.step("3. Perform logout", homePage::logout);

            LoginPage finalLoginPage1 = loginPage;
            Allure.step("4. Verify cart is successfully cleared", () -> softAssert.assertTrue(finalLoginPage1.isSuccessfullyLoggedOut("Login"),
                    "Logout failed for user " + username));
        } catch (Exception e) {
            softAssert.fail("Unexpected failure for user " + username + " - " + e.getMessage());
        }
        softAssert.assertAll();
    }
}
