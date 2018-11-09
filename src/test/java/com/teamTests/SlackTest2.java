package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class SlackTest2 extends TestBase {
    public WebDriver browser;
    SlackMethods SM2;


    @BeforeClass
    public void init(){
        openBrowser();
        browser = browser2;
        SM2 = new SlackMethods(browser);
    }// передает значение браузера в обекты браузера данного класса - при статик браузерах из TestBase передает значение null

    @Test
     void connectToWorkSpaceSuccess() throws Exception {

        SM2.connectToWorkSpace(com.teamTests.TestData.workSpace );
        System.out.println(browser);

        System.out.println(browser.getCurrentUrl());

        Assert.assertTrue(browser.getCurrentUrl().contains(com.teamTests.TestData.protocol + com.teamTests.TestData.workSpace
                + "." + com.teamTests.TestData.siteLink));

//        Assert.assertTrue(browser.getCurrentUrl().contains(com.teamTests.TestData.protocol));

    }


    @Test(dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true, groups = "login")
     void loginSuccess() throws Exception {
        SM2.login(com.teamTests.TestData.login_2, com.teamTests.TestData.password_2);
        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user_name")).size() > 0);
    }

       @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
     void loginFail() throws Exception{
           SM2.login("bad@login.com","badPass");
        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

    @Test (dependsOnMethods = "loginSuccess")
    public void testSlackBot() throws Exception {

//        browser2.findElement(By.xpath("//span[text()='slackbot']")).click();
//            Thread.sleep(3000);
//
//            h2.findAndFill(By.cssSelector(".msg_input"), TestData.messageToBot + "\n");

      //  SlackMethods.slackBot(browser2);

        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

    @Test (dependsOnMethods = "loginSuccess")
    public void testMessageBot() {
        browser2.findElement(By.xpath("//span[text()='slackbot']")).click();
        Assert.assertTrue(browser2.findElement(By.cssSelector("button[id='im_title']")).getText().contains("slackbot"));

        Actions actions = new Actions(browser2);
        actions.moveToElement(browser2.findElement(By.id("msg_input"))).click();
        actions.sendKeys(TestData.messageToBot + "\n").build().perform();
        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageToBot + "']";
        Assert.assertEquals(browser2.findElements(By.xpath(selector)).size(),1);
    }


    @Test (dependsOnMethods = "loginSuccess")
     void getMessage1() throws Exception {

        new FluentWait<>(browser2)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class)
                .until(browser2 -> browser2.findElement(By.xpath("//span[text()='" + TestData.userName_1 + "']")))
                .click();
        Thread.sleep(3000);

        Assert.assertTrue(SM2.checkLines());
    }

        @Test (dependsOnMethods = "loginSuccess" , priority = 20)
    public void testSignOut(){

            SM2.signOut();

        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

    }

