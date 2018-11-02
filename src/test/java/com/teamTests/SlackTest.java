package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SlackTest extends TestBase {

    @Test
    static void connectToWorkSpaceSuccess()throws Exception{
        connectToWorkSpace(TestData.workSpace);
        Assert.assertTrue(browser1.getCurrentUrl().contains(TestData.protocol + TestData.workSpace + "." + TestData.siteLink));
    }

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

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true)
    static void loginSuccess() throws Exception{
        login(TestData.login_1,TestData.password_1);
        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size()>0);
    }

//    @Test (dependsOnMethods = "connectToWorkSpaceSuccess2", alwaysRun = true)
//    static void loginSuccess2() throws Exception{
//        login2(TestData.login_2,TestData.password_2);
//        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size()>0);
//    }

//    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        login("bad@login.com","badPass");
        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

    @Test (dependsOnMethods = "loginSuccess")
    static void sendMessageToUser() {
        browser1.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink + "messages/");
        sendMessage(TestData.userName_2, TestData.messageText);

        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageText + "']";
        Assert.assertEquals(browser1.findElements(By.xpath(selector)).size(),1);

        /*output message in console*/
        System.out.println(TestData.userName_1 + " has sent message to " + TestData.userName_2 + ": " + browser1.findElement(By.xpath(selector)).getText());
    }

    @Test(dependsOnMethods = "sendMessageToUser")
    static void getMessage() {
//        browser2.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink + "messages/");

        new FluentWait<>(browser2)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class)
                .until(browser2 -> browser2.findElement(By.xpath("//span[text()='" + TestData.userName_1 + "']")))
                .click();

        /*this option may also be used*/
//        browser2.findElement(By.xpath("//span[text()='"+ TestData.userName_1 +"']")).click();
        Assert.assertTrue(browser2.findElement(By.cssSelector("button[id='im_title']")).getText().contains(TestData.userName_1));

        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageText + "']";
        Assert.assertEquals(browser2.findElements(By.xpath(selector)).size(),1);

        /*output message in console*/
        System.out.println(TestData.userName_2 + " has received message from " + TestData.userName_1 + ": " + browser2.findElement(By.xpath(selector)).getText());
    }



//    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
    public static void testSignOut(){
        signOut();
        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

}

