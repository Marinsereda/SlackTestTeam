package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.teamTests.Helper;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SlackMethods {
    public static WebDriver browser;
    static boolean result;


    static void connectToWorkSpace(String workSpace, WebDriver browser) throws Exception {
        browser.get(com.teamTests.TestData.protocol + com.teamTests.TestData.siteLink);
        browser.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        Helper.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }

    static void login(String username, String password, WebDriver browser) throws Exception {
        browser.get(com.teamTests.TestData.protocol + com.teamTests.TestData.workSpace + "." + com.teamTests.TestData.siteLink);
        Helper.findAndFill(By.cssSelector("#email"), username);
        Helper.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    static void slackBot(WebDriver browser) throws InterruptedException {
        browser.findElement(By.xpath("//span[text()='slackbot']")).click();
        Thread.sleep(3000);

        Helper.findAndFill(By.cssSelector(".msg_input"), TestData.messageToBot + "\n");

    }

    public static boolean checkLines (WebDriver browser) throws InterruptedException {

        List<WebElement> messageLines = browser.findElements(By.cssSelector("span.c-message__body"));
        for (WebElement line: messageLines){
            if (line.getText().equals(TestData.messageText)){
                result= true;
                break;
            }
            else
                result=false;
        }
        return result;
    }

    public static void signOut(WebDriver browser){
        browser.findElement(By.cssSelector("#team_menu")).click();
        browser.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();
    }
}
