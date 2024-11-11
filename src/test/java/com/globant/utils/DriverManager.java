package com.globant.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private WebDriver driver;

    public DriverManager(String browser) {
        switch (browser) {
            case "chrome":
                driver = WebDriverManager
                        .chromedriver()
                        .create();
                break;
            case "firefox":
                driver = WebDriverManager
                        .firefoxdriver()
                        .create();
                break;
            default:
                throw new IllegalArgumentException("Browser type not supported: " + browser);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
