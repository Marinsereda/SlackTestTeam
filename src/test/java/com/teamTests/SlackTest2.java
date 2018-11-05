package com.teamTests;


import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SlackTest2 extends TestBase {


    @Test
    static void connectToWorkSpaceSuccess() throws Exception {
        SlackMethods.connectToWorkSpace(com.teamTests.TestData.workSpace, browser2);
        Assert.assertTrue(browser2.getCurrentUrl().contains(com.teamTests.TestData.protocol + com.teamTests.TestData.workSpace
                + "." + com.teamTests.TestData.siteLink));
    }


    @Test(dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true, groups = "login")
    static void loginSuccess() throws Exception {
        SlackMethods.login(com.teamTests.TestData.login_2, com.teamTests.TestData.password_2,browser2);
        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size() > 0);
    }

       @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        SlackMethods.login("bad@login.com","badPass", browser2);
        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

    @Test (dependsOnMethods = "loginSuccess")
    public static void testSlackBot() throws Exception {

//        browser2.findElement(By.xpath("//span[text()='slackbot']")).click();
//            Thread.sleep(3000);
//
//            h2.findAndFill(By.cssSelector(".msg_input"), TestData.messageToBot + "\n");

        SlackMethods.slackBot(browser2);

        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

    @Test (dependsOnMethods = "loginSuccess")
    public static void testMessageBot() {
        browser2.findElement(By.xpath("//span[text()='slackbot']")).click();
        Assert.assertTrue(browser2.findElement(By.cssSelector("button[id='im_title']")).getText().contains("slackbot"));

        Actions actions = new Actions(browser2);
        actions.moveToElement(browser2.findElement(By.id("msg_input"))).click();
        actions.sendKeys(TestData.messageToBot + "\n").build().perform();
        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageToBot + "']";
        Assert.assertEquals(browser2.findElements(By.xpath(selector)).size(),1);
    }


    @Test (dependsOnMethods = "loginSuccess")
    static void getMessage1() throws Exception {

        new FluentWait<>(browser2)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class)
                .until(browser2 -> browser2.findElement(By.xpath("//span[text()='" + TestData.userName_1 + "']")))
                .click();
        Thread.sleep(3000);

        Assert.assertTrue(SlackMethods.checkLines(browser2));
    }

        @Test (dependsOnMethods = "loginSuccess" , priority = 20)
    public static void testSignOut(){

            SlackMethods.signOut(browser2);

        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }


    }

