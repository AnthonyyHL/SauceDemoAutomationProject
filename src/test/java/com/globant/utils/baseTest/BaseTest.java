package com.globant.utils.baseTest;

import com.globant.pages.LoginPage;
import com.globant.utils.DriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {
    DriverManager driver;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser", "url"})
    public void setupDriver(String browser, String url) {
        driver = new DriverManager(browser);
        driver.getDriver().manage().window().maximize();
        navigateTo(url);
    }

    public void navigateTo(String url) {
        driver.getDriver().get(url);
    }

    public LoginPage loadFirstPage() {
        return new LoginPage(driver.getDriver());
    }

    @AfterClass
    public void closeDriver() {
        driver.getDriver().quit();
    }
}
