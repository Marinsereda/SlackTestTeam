package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.time.Duration;

import static org.openqa.selenium.By.xpath;

public class TestSteps {

    private WebDriver browser;
    private Helper h;

    @FindBy(css = "a[href='https://slack.com/signin']")
    WebElement buttonSignIn;
    @FindBy(css = "#domain")
    /*static*/ WebElement selectorDomain;
    @FindBy(css = "#email")
    WebElement fieldEmail;
    @FindBy(css = "#password")
    WebElement fieldPassword;
//    @FindBy(css = "#password")
//    WebElement fieldPassword;
//    button[id='im_title']";

    public TestSteps(WebDriver browser) {
        this.browser = browser;
        this.h = new Helper(browser);
    }

    void connectToWorkSpace(String workSpace) throws Exception {
        browser.get(TestData.protocol + TestData.siteLink);
        buttonSignIn.click();
        h.findAndFill(selectorDomain, workSpace + "\n");
    }

    void login (String username, String password) throws Exception{
        browser.get(TestData.workSpaceUrl);
        h.findAndFill(fieldEmail, username);
        h.findAndFill(fieldPassword, password + "\n");
    }

    void sendMessage(String toUser, String text) {
        browser.get(TestData.workSpaceUrl + "/messages/");
        browser.findElement(By.xpath("//span[text()='"+ toUser +"']")).click();
        Assert.assertTrue(browser.findElement(By.cssSelector("button[id='im_title']")).getText().contains(toUser));

        Actions actions = new Actions(browser);
        actions.moveToElement(browser.findElement(By.id("msg_input"))).click();
        actions.sendKeys(text + "\n").build().perform();
    }

    String getMessage(String fromUser, String message) {

        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class)
                .until(browser2 -> browser.findElement(xpath("//span[text()='" + fromUser + "']")))
                .click();

        /*this option may also be used*/
//        browser2.findElement(By.xpath("//span[text()='"+ TestData.userName_1 +"']")).click();
        Assert.assertTrue(browser.findElement(By.cssSelector("button[id='im_title']")).getText().contains(fromUser));

        String selector = "//span[@class='c-message__body' and text() = '" + message + "']";
        return browser.findElement(xpath(selector)).getText();
    }

    void signOut() {
        browser.findElement(By.cssSelector("#team_menu")).click();
        browser.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();
    }
}
