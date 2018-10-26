package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SlackTest extends TestBase{
    private static WebDriver browser;
    static Helper h;


    @Test
    public static void testSignOut(){
//        login;
        browser.findElement(By.cssSelector("#team_menu")).click();
        browser.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();

        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user")).size()>0);

    }
    @Test
    public static void testSlackbot(){
//        login;
        browser.findElement(By.cssSelector(".p-channel_sidebar__channel--im-slackbot")).click();
        h.findAndFill(By.cssSelector(".msg_input"), TestData.message + "\n");

        Assert.assertTrue(browser.findElements(By.cssSelector("#team_menu_user")).size()>0);

    }

}
