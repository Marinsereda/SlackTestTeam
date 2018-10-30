package com.teamTests2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver browser2;
    static Helper h;

    @BeforeTest
    public static void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        browser2 = new ChromeDriver(options);

        h = new Helper(browser2);


        browser2.manage().window().maximize();
        browser2.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }

//    @AfterTest
    public void closeBrowser() {
        h = new Helper(browser2);
        browser2.quit();
    }
}
