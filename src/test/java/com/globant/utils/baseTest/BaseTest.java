package com.globant.utils.baseTest;

import com.globant.pages.LoginPage;
import com.globant.utils.DriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {
    DriverManager driver;

    @BeforeClass(alwaysRun = true)
    @Parameters({"url"})
    public void setupDriver(String url) {
        String browser = System.getProperty("browserName");
        driver = new DriverManager(browser == null ? "Chrome" : browser);
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
