package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SlackTest extends TestBase {

    @BeforeClass
    private static void init() {
        steps = PageFactory.initElements(browser, TestSteps.class);
    }

    static TestSteps steps;

    static By selectorForLogin = By.cssSelector("#team_menu_user_name");
    /*@FindBy(css = "#team_menu_user_name")
    static WebElement selectorForLogin;*/
    static By selectorForLogOut = By.cssSelector("#team_menu_user");
    /*@FindBy(css="#team_menu_user")
    static WebElement selectorForLogOut;*/
    static By selectorForMessage = By.xpath("//span[@class='c-message__body' and text() = '" + TestData.messageText + "']");
    /*@FindBy ("//span[@class='c-message__body' and text() = '" + TestData.messageText + "']")
    WebElement selectorForMessage;*/


    @Test
    static void connectToWorkSpaceSuccess()throws Exception{
        steps.connectToWorkSpace(TestData.workSpaceName);
        Assert.assertTrue(browser.getCurrentUrl().contentEquals(TestData.workSpaceUrl));
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        steps.login("bad@login.com","badPass");
        Assert.assertTrue(browser.findElements(selectorForLogin).size()<1);
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true)
    static void loginSuccess() throws Exception{
        steps.login(TestData.login_1,TestData.password_1);
        Assert.assertTrue(browser.findElements(selectorForLogin).size()>0);
    }

    @Test (dependsOnMethods = "loginSuccess")
    static void sendMessageToUser() {
        steps.sendMessage(TestData.userName_2, TestData.messageText);
        Assert.assertEquals(browser.findElements(selectorForMessage).size(),1);
    }

    @Test(dependsOnMethods = "sendMessageToUser")
    static void getMessageFromUser() {
        String messageFromUser = steps.getMessage(TestData.userName_1, TestData.messageText);
        Assert.assertEquals(messageFromUser, TestData.messageText);
    }


    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
    public static void testSignOut(){
        steps.signOut();
        Assert.assertTrue(browser.findElements(selectorForLogOut).size()>0);
    }

}



/* stuff for user #2*/
//    @Test
//    static void connectToWorkSpaceSuccess2()throws Exception{
//        connectToWorkSpace2(TestData.workSpaceName);
//        Assert.assertTrue(browser2.getCurrentUrl().contains(TestData.protocol + TestData.workSpaceName + "." + TestData.siteLink));
//    }


//    static void login2 (String username, String password) throws Exception{
//        browser2.get(TestData.protocol + TestData.workSpaceName + "." + TestData.siteLink);
//        h2.findAndFill(By.cssSelector("#email"), username);
//        h2.findAndFill(By.cssSelector("#password"), password + "\n");

//    }

//    @Test (dependsOnMethods = "connectToWorkSpaceSuccess2", alwaysRun = true)
//    static void loginSuccess2() throws Exception{
//        login2(TestData.login_2,TestData.password_2);
//        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size()>0);

//    }
