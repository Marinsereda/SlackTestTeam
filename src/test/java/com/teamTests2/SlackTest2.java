package com.teamTests2;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SlackTest2 extends TestBase {

    static void connectToWorkSpace (String workSpace) throws Exception {
        browser2.get(TestData.protocol + TestData.siteLink);
        browser2.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        h.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }

    @Test
    static void connectToWorkSpaceSuccess()throws Exception{
        connectToWorkSpace(TestData.workSpace);
        Assert.assertTrue(browser2.getCurrentUrl().contains(TestData.protocol + TestData.workSpace + "." + TestData.siteLink));
    }

    static void login (String username, String password) throws Exception{
        browser2.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink);
        h.findAndFill(By.cssSelector("#email"), username);
        h.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true)
    static void loginSuccess() throws Exception{
        login(TestData.login, TestData.password);
        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size()>0);
    }

//    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        login("bad@login.com","badPass");
        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

//    @Test (dependsOnMethods = "loginSuccess")
    public static void testSlackbot(){
        browser2.findElement(By.xpath("//span[text()='slackbot']")).click();
        h.findAndFill(By.cssSelector(".msg_input"), TestData.messageToBot + "\n");

        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

    @Test (dependsOnMethods = "loginSuccess")
    static void messageBot() { //needs fix for assertion
        browser2.findElement(By.xpath("//span[text()='slackbot']")).click();
        Assert.assertTrue(browser2.findElement(By.cssSelector("button[id='im_title']")).getText().contains("slackbot"));

        Actions actions = new Actions(browser2);
        actions.moveToElement(browser2.findElement(By.id("msg_input"))).click();
        actions.sendKeys(TestData.messageToBot + "\n").build().perform();
//        browser.findElements(By.cssSelector(".c-message__body"));
//        Assert.assertTrue(browser.findElement(By.cssSelector("#messages_container")).getText().contains(TestData.messageToBot));
//
// browser.findElements(By.cssSelector("#channel_header_info")).getAttribute("title").equals("active");
//        Assert.assertTrue(browser.findElement(By.cssSelector("#messages_container")).getText().contains(TestData.messageToBot));
    }

//    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
//    public static void testSignOut(){
//        browser2.findElement(By.cssSelector("#team_menu")).click();
//        browser2.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();
//
//        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
//    }

//    @Test
//    public static void testCreateChannel() throws Exception {
//        loginSuccess();
//        Thread.sleep(2000);
//
//        browser.findElement(By.cssSelector(".c-button-unstyled[aria-label='Create a channel']")).click();
//        h.findAndFill(By.cssSelector("input#channel_create_title"), TestData.nameNewChannel);
//        browser.findElement(By.cssSelector("#save_channel")).click();
//        Thread.sleep(1000);
////        h.findAndFill(By.cssSelector("#channel_purpose_input"), TestData.purposeNewChannel).submit();
//
//        String nameCh= browser.findElement(By.cssSelector(".channel_title #channel_name_container")).getText().toLowerCase().replaceAll(" ","");
//        System.out.println(nameCh);
//
//        Assert.assertTrue(browser.findElement(By.cssSelector(".channel_title #channel_name_container")).getText().contains(TestData.nameNewChannel));
//
//    }



}

