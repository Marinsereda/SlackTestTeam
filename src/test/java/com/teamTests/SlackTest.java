package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SlackTest extends TestBase {

    static void connectToWorkSpace (String workSpace) throws Exception {
        browser.get(TestData.protocol + TestData.siteLink);
        browser.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        h.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }

    @Test
    static void connectToWorkSpaceSuccess()throws Exception{
        connectToWorkSpace(TestData.workSpace);
        Assert.assertTrue(browser.getCurrentUrl().contains(TestData.protocol + TestData.workSpace + "." + TestData.siteLink));
    }

    static void login (String username, String password) throws Exception{
        browser.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink);
        h.findAndFill(By.cssSelector("#email"), username);
        h.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true)
    static void loginSuccess() throws Exception{
        login(TestData.login,TestData.password);
        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user_name")).size()>0);
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        login("bad@login.com","badPass");
        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

//    @Test
    public static void testSignOut(){
//        login;
        browser.findElement(By.cssSelector("#team_menu")).click();
        browser.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();

        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user")).size()>0);

    }

//    @Test
    public static void testSlackbot(){
//        login;
        browser.findElement(By.cssSelector(".p-channel_sidebar__channel--im-slackbot")).click();
        h.findAndFill(By.cssSelector(".msg_input"), TestData.message + "\n");

        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user")).size()>0);

    }


}

