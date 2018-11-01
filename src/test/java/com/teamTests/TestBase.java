package com.teamTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static WebDriver browser1;
    public static WebDriver browser2;
    static Helper h1;
    static Helper h2;

    @BeforeTest
    public static void openBrowser() {
        /*set up path to chromedriver*/
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        /*initialize 1st browser window*/
        browser1 = new ChromeDriver();

        /*set up options for 1st browser window*/
        browser1.manage().window().maximize();
        browser1.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        /*initialize 2nd browser window*/
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        browser2 = new ChromeDriver(options);

        /*set up options for 1st browser window*/
        browser2.manage().window().maximize();
        browser2.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        /*initialize helpers for browsers*/
        h1 = new Helper(browser1);
        h2 = new Helper(browser2);

    }

//    @AfterTest
    public void closeBrowser() {
        h1 = new Helper(browser1);
        browser1.quit();

        h2 = new Helper(browser2);
        browser2.quit();
    }
}
