package com.teamTests2;

import com.teamTests.TestData;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SlackTest2 extends TestBase {
    static void connectToWorkSpace (String workSpace) throws Exception {
        browser2.get(com.teamTests2.TestData.protocol + com.teamTests2.TestData.siteLink);
        browser2.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        h.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }

    @Test
    static void connectToWorkSpaceSuccess()throws Exception{
        connectToWorkSpace(com.teamTests2.TestData.workSpace);
        Assert.assertTrue(browser2.getCurrentUrl().contains(com.teamTests2.TestData.protocol + com.teamTests2.TestData.workSpace
                + "." + com.teamTests2.TestData.siteLink));
    }

    static void login (String username, String password) throws Exception{
        browser2.get(com.teamTests2.TestData.protocol + com.teamTests2.TestData.workSpace + "." + com.teamTests2.TestData.siteLink);
        h.findAndFill(By.cssSelector("#email"), username);
        h.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true)
    static void loginSuccess() throws Exception{
        login(com.teamTests2.TestData.login_2, com.teamTests2.TestData.password_2);
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
        h.findAndFill(By.cssSelector(".msg_input"), com.teamTests2.TestData.messageToBot + "\n");

        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

    @Test (dependsOnMethods = "loginSuccess")
    static void messageBot() {
        browser2.findElement(By.xpath("//span[text()='slackbot']")).click();
        Assert.assertTrue(browser2.findElement(By.cssSelector("button[id='im_title']")).getText().contains("slackbot"));

        Actions actions = new Actions(browser2);
        actions.moveToElement(browser2.findElement(By.id("msg_input"))).click();
        actions.sendKeys(com.teamTests2.TestData.messageToBot + "\n").build().perform();
        String selector = "//span[@class='c-message__body' and text() = '" + com.teamTests2.TestData.messageToBot + "']";
        Assert.assertEquals(browser2.findElements(By.xpath(selector)).size(),1);
    }

    //    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
    public static void testSignOut(){
        browser2.findElement(By.cssSelector("#team_menu")).click();
        browser2.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();

        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }


}

