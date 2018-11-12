package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.time.Duration;

public class TestSteps {

    private WebDriver browser;
    private Helper h;

    @FindBy(css = "a#create_link")
    WebElement createIssueButton;

    public TestSteps(WebDriver browser) {
        this.browser = browser;
        this.h = new Helper(browser);
    }

    void connectToWorkSpace(String workSpace) throws Exception {
        browser.get(TestData.protocol + TestData.siteLink);
        browser.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        h.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }

    void login (String username, String password) throws Exception{
        browser.get(TestData.protocol + TestData.workSpaceName + "." + TestData.siteLink);
        h.findAndFill(By.cssSelector("#email"), username);
        h.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    void sendMessage(String toUser, String text) {
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
                .until(browser2 -> browser.findElement(By.xpath("//span[text()='" + fromUser + "']")))
                .click();

        /*this option may also be used*/
//        browser2.findElement(By.xpath("//span[text()='"+ TestData.userName_1 +"']")).click();
        Assert.assertTrue(browser.findElement(By.cssSelector("button[id='im_title']")).getText().contains(fromUser));

        String selector = "//span[@class='c-message__body' and text() = '" + message + "']";
        return browser.findElement(By.xpath(selector)).getText();
    }

    void signOut() {
        browser.findElement(By.cssSelector("#team_menu")).click();
        browser.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();
    }
}
