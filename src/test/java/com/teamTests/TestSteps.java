package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class TestSteps extends TestBase {

    static void connectToWorkSpace (String workSpace) throws Exception {
        browser1.get(TestData.protocol + TestData.siteLink);
        browser1.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        h1.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }

    static void login (String username, String password) throws Exception{
        browser1.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink);
        h1.findAndFill(By.cssSelector("#email"), username);
        h1.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    static void sendMessage(String name, String text) {
        browser1.findElement(By.xpath("//span[text()='"+ name +"']")).click();
        Assert.assertTrue(browser1.findElement(By.cssSelector("button[id='im_title']")).getText().contains(name));

        Actions actions = new Actions(browser1);
        actions.moveToElement(browser1.findElement(By.id("msg_input"))).click();
        actions.sendKeys(text + "\n").build().perform();
    }

    static void signOut() {
        browser1.findElement(By.cssSelector("#team_menu")).click();
        browser1.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();
    }
}
