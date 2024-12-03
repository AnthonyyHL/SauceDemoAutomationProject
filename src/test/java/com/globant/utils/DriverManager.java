package com.globant.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private final WebDriver driver;

    public DriverManager(String browserType) {
        String browser = browserType.toLowerCase();
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
