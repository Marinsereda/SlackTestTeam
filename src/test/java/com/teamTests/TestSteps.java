package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.time.Duration;

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

    static void sendMessage(String toUser, String text) {
        browser1.findElement(By.xpath("//span[text()='"+ toUser +"']")).click();
        Assert.assertTrue(browser1.findElement(By.cssSelector("button[id='im_title']")).getText().contains(toUser));

        Actions actions = new Actions(browser1);
        actions.moveToElement(browser1.findElement(By.id("msg_input"))).click();
        actions.sendKeys(text + "\n").build().perform();
    }

    static String getMessage(String fromUser, String message) {

        new FluentWait<>(browser2)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class)
                .until(browser2 -> browser2.findElement(By.xpath("//span[text()='" + fromUser + "']")))
                .click();

        /*this option may also be used*/
//        browser2.findElement(By.xpath("//span[text()='"+ TestData.userName_1 +"']")).click();
        Assert.assertTrue(browser2.findElement(By.cssSelector("button[id='im_title']")).getText().contains(fromUser));

        String selector = "//span[@class='c-message__body' and text() = '" + message + "']";
        return browser2.findElement(By.xpath(selector)).getText();
    }

        static void signOut() {
        browser1.findElement(By.cssSelector("#team_menu")).click();
        browser1.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();
    }
}
