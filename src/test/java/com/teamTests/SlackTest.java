package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

public class SlackTest extends TestBase {}

//    SlackMethods SM1;
//
//
//
//    @Test
//     void connectToWorkSpaceSuccess() throws Exception {
//        SM1.connectToWorkSpace(com.teamTests.TestData.workSpace);
//        Assert.assertTrue(browser1.getCurrentUrl().contains(com.teamTests.TestData.protocol + com.teamTests.TestData.workSpace
//                + "." + com.teamTests.TestData.siteLink));
//    }
//
//    @Test(dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true, groups = "login")
//     void loginSuccess() throws Exception{
//        SM1.login(com.teamTests.TestData.login_1, com.teamTests.TestData.password_1);
//        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size() > 0);
//    }
//
////    static void login (String username, String password) throws Exception{
////        browser1.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink);
////        h.findAndFill(By.cssSelector("#email"), username);
////        h.findAndFill(By.cssSelector("#password"), password + "\n");
////    }
////
////    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true, groups = "login")
////    static void loginSuccess() throws Exception{
////        login(TestData.login_1,TestData.password_1);
////        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size()>0);
////    }
////
////  @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
////    static void loginFail() throws Exception{
////        login("bad@login.com","badPass");
////        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
////    }
////    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
////    static void loginFail() throws Exception{
////        SlackMethods.login("bad@login.com","badPass", browser1);
////        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
////    }
////
////    @Test (dependsOnMethods = "loginSuccess")
////    public static void testSlackBot() throws Exception {
////
////        SlackMethods.slackBot(browser1);
////
////        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user")).size()>0);
////    }
//
////    static void sendMessage(String name, String text) {
////        browser1.findElement(By.xpath("//span[text()='"+ name +"']")).click();
////        Assert.assertTrue(browser1.findElement(By.cssSelector("button[id='im_title']")).getText().contains(name));
////
////        Actions actions = new Actions(browser1);
////        actions.moveToElement(browser1.findElement(By.id("msg_input"))).click();
////        actions.sendKeys(text + "\n").build().perform();
////    }
//
//
//    @Test (dependsOnMethods = "loginSuccess", groups = "testMessagePath")
//    static void sendMessageToUser() {
//        browser1.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink + "messages/");
//        SlackMethods.sendMessage(TestData.userName_2, TestData.messageText);
//
//        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageText + "']";
//        Assert.assertEquals(browser1.findElements(By.xpath(selector)).size(),1);
//
//    }
//
//    /*static void messageBot() {
//        browser1.findElement(By.xpath("//span[text()='slackbot']")).click();
//        Assert.assertTrue(browser1.findElement(By.cssSelector("button[id='im_title']")).getText().contains("slackbot"));
//
//        Actions actions = new Actions(browser1);
//        actions.moveToElement(browser1.findElement(By.id("msg_input"))).click();
//        actions.sendKeys(TestData.messageToBot + "\n").build().perform();
//        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageToBot + "']";
//        Assert.assertEquals(browser1.findElements(By.xpath(selector)).size(),1);
//    }*/
//
//    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
//    public static void testSignOut(){
//
//        SlackMethods.signOut();
//
//        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user")).size()>0);
//    }
//
//}
//
