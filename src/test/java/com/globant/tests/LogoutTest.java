package com.globant.tests;

import com.globant.data.Data;
import com.globant.pages.HomePage;
import com.globant.pages.LoginPage;
import com.globant.utils.baseTest.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LogoutTest extends BaseTest {
    @Test(dataProvider = "data-provider", dataProviderClass = Data.class, alwaysRun = true)
    public void logoutTest(String username, String password) {
        SoftAssert softAssert = new SoftAssert();
        try {
            LoginPage loginPage = loadFirstPage();
            HomePage homePage = loginPage.login(username, password);
            loginPage = homePage.logout();

            softAssert.assertTrue(loginPage.isSuccessfullyLoggedOut("Login"),
                    "Logout failed for user " + username);
        } catch (Exception e) {
            softAssert.fail("Unexpected failure for user " + username + " - " + e.getMessage());
        }
        softAssert.assertAll();
    }
}
