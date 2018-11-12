package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SlackTest extends TestBase {
    static TestSteps steps;
    @BeforeClass
    private static void init() {
        steps = PageFactory.initElements(browser, TestSteps.class);
    }

    @Test
    static void connectToWorkSpaceSuccess()throws Exception{
        steps.connectToWorkSpace(TestData.workSpace);
        Assert.assertTrue(browser.getCurrentUrl().contains(TestData.protocol + TestData.workSpace + "." + TestData.siteLink));
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        steps.login("bad@login.com","badPass");
        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true)
    static void loginSuccess() throws Exception{
        steps.login(TestData.login_1,TestData.password_1);
        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user_name")).size()>0);
    }

    @Test (dependsOnMethods = "loginSuccess")
    static void sendMessageToUser() {
        browser.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink + "messages/");
        steps.sendMessage(TestData.userName_2, TestData.messageText);

        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageText + "']";
        Assert.assertEquals(browser.findElements(By.xpath(selector)).size(),1);

        /*output message in console*/
        System.out.println(TestData.userName_1 + " has sent message to " + TestData.userName_2 + ": " + browser1.findElement(By.xpath(selector)).getText());
    }

    @Test(dependsOnMethods = "sendMessageToUser")
    static void getMessageFromUser() {
        String messageFromUser = steps.getMessage(TestData.userName_1, TestData.messageText);
        Assert.assertEquals(messageFromUser, TestData.messageText);
    }


    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
    public static void testSignOut(){
        steps.signOut();
        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

}



/* stuff for user #2*/
//    @Test
//    static void connectToWorkSpaceSuccess2()throws Exception{
//        connectToWorkSpace2(TestData.workSpace);
//        Assert.assertTrue(browser2.getCurrentUrl().contains(TestData.protocol + TestData.workSpace + "." + TestData.siteLink));
//    }


//    static void login2 (String username, String password) throws Exception{
//        browser2.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink);
//        h2.findAndFill(By.cssSelector("#email"), username);
//        h2.findAndFill(By.cssSelector("#password"), password + "\n");

//    }

//    @Test (dependsOnMethods = "connectToWorkSpaceSuccess2", alwaysRun = true)
//    static void loginSuccess2() throws Exception{
//        login2(TestData.login_2,TestData.password_2);
//        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size()>0);

//    }
