package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public WebDriver browser;

    Helper(WebDriver currentBrowser) {
        browser = currentBrowser;
    }

    public WebElement findAndFill(By selector, String value) {
        WebElement element = browser.findElement(selector);
        element.sendKeys(value);
        return element;
    }

    public static String timeStamp() {
        return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
    }


}

