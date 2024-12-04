package com.globant.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import resources.ConfigCapabilities;

public class DriverManager extends ConfigCapabilities {
    private final WebDriver driver;

    public DriverManager(String browserType) {
        super();
        String browser = browserType.toLowerCase();
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.merge(super.loadCapabilities());
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.merge(super.loadCapabilities());
                driver = new FirefoxDriver(options);
                break;
            default:
                throw new IllegalArgumentException("Browser type not supported: " + browser);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
