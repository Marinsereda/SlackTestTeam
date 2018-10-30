package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SlackTest extends TestBase {

    static void connectToWorkSpace (String workSpace) throws Exception {
        browser1.get(TestData.protocol + TestData.siteLink);
        browser1.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        h.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }

    @Test
    static void connectToWorkSpaceSuccess()throws Exception{
        connectToWorkSpace(TestData.workSpace);
        Assert.assertTrue(browser1.getCurrentUrl().contains(TestData.protocol + TestData.workSpace + "." + TestData.siteLink));
    }

    static void login (String username, String password) throws Exception{
        browser1.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink);
        h.findAndFill(By.cssSelector("#email"), username);
        h.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true)
    static void loginSuccess() throws Exception{
        login(TestData.login_1,TestData.password_1);
        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size()>0);
    }

//    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        login("bad@login.com","badPass");
        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

//    @Test (dependsOnMethods = "loginSuccess")
    public static void testSlackbot(){
        browser1.findElement(By.xpath("//span[text()='slackbot']")).click();
        h.findAndFill(By.cssSelector(".msg_input"), TestData.messageToBot + "\n");

        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

    static void sendMessage(String name, String text) {
        browser1.findElement(By.xpath("//span[text()='"+ name +"']")).click();
        Assert.assertTrue(browser1.findElement(By.cssSelector("button[id='im_title']")).getText().contains(name));

        Actions actions = new Actions(browser1);
        actions.moveToElement(browser1.findElement(By.id("msg_input"))).click();
        actions.sendKeys(text + "\n").build().perform();
    }

    @Test (dependsOnMethods = "loginSuccess")
    static void sendMessageToUser() {
        browser1.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink + "messages/");
        sendMessage(TestData.userName_2, TestData.messageText);

        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageText + "']";
        Assert.assertEquals(browser1.findElements(By.xpath(selector)).size(),1);
    }

    /*static void messageBot() {
        browser1.findElement(By.xpath("//span[text()='slackbot']")).click();
        Assert.assertTrue(browser1.findElement(By.cssSelector("button[id='im_title']")).getText().contains("slackbot"));

        Actions actions = new Actions(browser1);
        actions.moveToElement(browser1.findElement(By.id("msg_input"))).click();
        actions.sendKeys(TestData.messageToBot + "\n").build().perform();
        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageToBot + "']";
        Assert.assertEquals(browser1.findElements(By.xpath(selector)).size(),1);
    }*/

//    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
    public static void testSignOut(){
        browser1.findElement(By.cssSelector("#team_menu")).click();
        browser1.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();

        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

}

