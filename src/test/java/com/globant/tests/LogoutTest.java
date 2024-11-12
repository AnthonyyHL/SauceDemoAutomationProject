package com.globant.tests;

import com.globant.data.Data;
import com.globant.pages.HomePage;
import com.globant.pages.LoginPage;
import com.globant.utils.baseTest.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LogoutTest extends BaseTest {
    @Test(dataProvider = "data-provider", dataProviderClass = Data.class, alwaysRun = true)
    public void logoutTest(String[] credentials) {
        SoftAssert softAssert = new SoftAssert();
        try {
            LoginPage loginPage = loadFirstPage();
            HomePage homePage = loginPage.login(credentials[0], credentials[1]);
            loginPage = homePage.logout();

            softAssert.assertTrue(loginPage.isSuccessfullyLoggedOut("Login"),
                    "Logout failed for user " + credentials[0]);
        } catch (Exception e) {
            softAssert.fail("Unexpected failure for user " + credentials[0] + " - " + e.getMessage());
        }
        softAssert.assertAll();
    }
}
