package com.teamTests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

public class TestBase {
    public static WebDriver browser;
    static Helper h;


    @BeforeTest

    public static void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        h = new Helper(browser);

        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

    }

    @AfterTest
    public void closeBrowser() {
        h = new Helper(browser);
        browser.quit();
    }
}
