package com.teamTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static   WebDriver browser1;
    public static   WebDriver browser2;
//    public static WebDriver browser;
    static Helper h;
//    static Helper h2;




//    @BeforeTest
    public static void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser1 = new ChromeDriver();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        browser2 = new ChromeDriver(options);

  //      h = new Helper(WebDriver browser);
//        h2 = new Helper(browser2);

        browser1.manage().window().maximize();
        browser1.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        browser2.manage().window().maximize();
        browser2.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }


    @AfterTest
    public void closeBrowser() {
        browser1.quit();
        browser2.quit();
    }
}
