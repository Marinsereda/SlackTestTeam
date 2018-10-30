package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
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

//    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        login("bad@login.com","badPass");
        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

//    @Test (dependsOnMethods = "loginSuccess")
    public static void testSlackbot(){
        browser.findElement(By.xpath("//span[text()='slackbot']")).click();
        h.findAndFill(By.cssSelector(".msg_input"), TestData.messageToBot + "\n");

        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

    @Test (dependsOnMethods = "loginSuccess")
    static void messageBot() { //needs fix for assertion
        browser.findElement(By.xpath("//span[text()='slackbot']")).click();
        Assert.assertTrue(browser.findElement(By.cssSelector("button[id='im_title']")).getText().contains("slackbot"));

        Actions actions = new Actions(browser);
        actions.moveToElement(browser.findElement(By.id("msg_input"))).click();
        actions.sendKeys(TestData.messageToBot + "\n").build().perform();
//        browser.findElements(By.cssSelector(".c-message__body"));
//        Assert.assertTrue(browser.findElement(By.cssSelector("#messages_container")).getText().contains(TestData.messageToBot));
    }

    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
    public static void testSignOut(){
        browser.findElement(By.cssSelector("#team_menu")).click();
        browser.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();

        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

}

