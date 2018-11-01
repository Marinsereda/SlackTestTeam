package com.teamTests;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SlackTest extends TestBase {

    static void connectToWorkSpace (String workSpace) throws Exception {
        browser1.get(TestData.protocol + TestData.siteLink);
        browser1.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        h1.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }
    static void connectToWorkSpace2 (String workSpace) throws Exception {
        browser2.get(TestData.protocol + TestData.siteLink);
        browser2.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        h2.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }

    @Test
    static void connectToWorkSpaceSuccess()throws Exception{
        connectToWorkSpace(TestData.workSpace);
        Assert.assertTrue(browser1.getCurrentUrl().contains(TestData.protocol + TestData.workSpace + "." + TestData.siteLink));
    }

    @Test
    static void connectToWorkSpaceSuccess2()throws Exception{
        connectToWorkSpace2(TestData.workSpace);
        Assert.assertTrue(browser2.getCurrentUrl().contains(TestData.protocol + TestData.workSpace + "." + TestData.siteLink));
    }

    static void login (String username, String password) throws Exception{
        browser1.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink);
        h1.findAndFill(By.cssSelector("#email"), username);
        h1.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    static void login2 (String username, String password) throws Exception{
        browser2.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink);
        h2.findAndFill(By.cssSelector("#email"), username);
        h2.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true)
    static void loginSuccess() throws Exception{
        login(TestData.login_1,TestData.password_1);
        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size()>0);
    }

    @Test (dependsOnMethods = "connectToWorkSpaceSuccess2", alwaysRun = true)
    static void loginSuccess2() throws Exception{
        login2(TestData.login_2,TestData.password_2);
        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size()>0);
    }

//    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        login("bad@login.com","badPass");
        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

//    @Test (dependsOnMethods = "loginSuccess")
    public static void testSlackbot(){
        browser1.findElement(By.xpath("//span[text()='slackbot']")).click();
        h1.findAndFill(By.cssSelector(".msg_input"), TestData.messageToBot + "\n");

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

        /*output message in console*/
        System.out.println(TestData.userName_1 + " has sent message to " + TestData.userName_2 + ": " + browser1.findElement(By.xpath(selector)).getText());
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

    @Test(dependsOnMethods = "sendMessageToUser")
    static void getMessage() {
//        browser2.get(TestData.protocol + TestData.workSpace + "." + TestData.siteLink + "messages/");

        new FluentWait<>(browser2)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class)
                .until(browser2 -> browser2.findElement(By.xpath("//span[text()='" + TestData.userName_1 + "']")))
                .click();

        /*this option may also be used*/
//        browser2.findElement(By.xpath("//span[text()='"+ TestData.userName_1 +"']")).click();
        Assert.assertTrue(browser2.findElement(By.cssSelector("button[id='im_title']")).getText().contains(TestData.userName_1));

        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageText + "']";
        Assert.assertEquals(browser2.findElements(By.xpath(selector)).size(),1);

        /*output message in console*/
        System.out.println(TestData.userName_2 + " has received message from " + TestData.userName_1 + ": " + browser2.findElement(By.xpath(selector)).getText());
//
//        List<WebElement>messageBodyLines = browser2.findElements(By.cssSelector(".c-message__body"));
//        Assert.assertTrue(messageBodyLines.contains(com.teamTests2.TestData.messageText));
//        проверка проходит, если есть конкретный текст сообщения, без генерирования даты
    }



//    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
    public static void testSignOut(){
        browser1.findElement(By.cssSelector("#team_menu")).click();
        browser1.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();

        Assert.assertTrue(browser1.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

}

