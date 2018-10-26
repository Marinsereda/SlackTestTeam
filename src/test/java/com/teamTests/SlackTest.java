package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SlackTest extends TestBase {
    static void connectToWorkSpace (String workSpace) throws Exception {
        browser.get(TestData.siteLink);
        h.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }

    @Test (priority = -1)
    static void connectToWorkSpaceSuccess()throws Exception{
        connectToWorkSpace(TestData.workSpace);
        Assert.assertEquals(browser.getCurrentUrl(),TestData.workSpaceUrl);
    }

    static void login (String username, String password) throws Exception{
        h.findAndFill(By.cssSelector("#email"), username);
        h.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    @Test (priority = 0)
    static void loginSuccess() throws Exception{
        login(TestData.login,TestData.password);
        browser.findElement(By.cssSelector("#team_menu_user_name")).click();
        Assert.assertTrue(browser.findElement(By.cssSelector("#logout")).isDisplayed());
    }
}
