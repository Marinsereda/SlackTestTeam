package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.teamTests.Helper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SlackMethods {
   private WebDriver browser;
   private Helper h;

    SlackMethods (WebDriver browser) {
        this.browser = browser;
        System.out.println(browser);
        this.h= new Helper (browser);
    }

    static boolean result;


     void connectToWorkSpace(String workSpace) throws InterruptedException {
         browser.get(com.teamTests.TestData.protocol + com.teamTests.TestData.siteLink);
//         browser.get(com.teamTests.TestData.protocol);
        browser.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
         h.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }
     void login(String username, String password) throws Exception {
        browser.get(com.teamTests.TestData.protocol + com.teamTests.TestData.workSpace + "." + com.teamTests.TestData.siteLink);
        Thread.sleep(3000);
         h.findAndFill(By.cssSelector("#email"), username);
         h.findAndFill(By.cssSelector("#password"), password + "\n");
    }

     void slackBot() throws InterruptedException {
        browser.findElement(By.xpath("//span[text()='slackbot']")).click();
        Thread.sleep(3000);

        h.findAndFill(By.cssSelector(".msg_input"), TestData.messageToBot + "\n");

    }
    void sendMessage(String name, String text) {
        browser.findElement(By.xpath("//span[text()='"+ name +"']")).click();
        Assert.assertTrue(browser.findElement(By.cssSelector("button[id='im_title']")).getText().contains(name));

        Actions actions = new Actions(browser);
        actions.moveToElement(browser.findElement(By.id("msg_input"))).click();
        actions.sendKeys(text + "\n").build().perform();
    }

    boolean checkLines() {

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

    public  void signOut(){
        browser.findElement(By.cssSelector("#team_menu")).click();
        browser.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();
    }
}
