package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static WebDriver browser;

    Helper(WebDriver currentBrowser) {
        browser = currentBrowser;
    }

    public static WebElement findAndFill(By selector, String value) {
        WebElement element = browser.findElement(selector);
        element.sendKeys(value);
        return element;
    }

    public static String timeStamp() {
        return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
    }

    public void adminUserCreate(){
        browser.findElement(By.cssSelector("#system-admin-menu")).click();
        browser.findElement(By.cssSelector("#admin_users_menu")).click();
        browser.findElement(By.cssSelector("input#login_1-form-authenticatePassword")).sendKeys("forautotests\n");
        browser.findElement(By.cssSelector("#create_user")).click();

        browser.findElement(By.cssSelector("#user-create-submit")).click();

    }

}

