package com.teamTests;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SlackTest2 extends TestBase {
    static boolean result;
    static void connectToWorkSpace(String workSpace) throws Exception {
        browser2.get(com.teamTests.TestData.protocol + com.teamTests.TestData.siteLink);
        browser2.findElement(By.cssSelector("a[href='https://slack.com/signin']")).click();
        h2.findAndFill(By.cssSelector("#domain"), workSpace + "\n");
    }


    @Test
    static void connectToWorkSpaceSuccess() throws Exception {
        connectToWorkSpace(com.teamTests.TestData.workSpace);
        Assert.assertTrue(browser2.getCurrentUrl().contains(com.teamTests.TestData.protocol + com.teamTests.TestData.workSpace
                + "." + com.teamTests.TestData.siteLink));
    }

    static void login(String username, String password) throws Exception {
        browser2.get(com.teamTests.TestData.protocol + com.teamTests.TestData.workSpace + "." + com.teamTests.TestData.siteLink);
        h2.findAndFill(By.cssSelector("#email"), username);
        h2.findAndFill(By.cssSelector("#password"), password + "\n");
    }

    @Test(dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true, groups = "login")
    static void loginSuccess() throws Exception {
        login(com.teamTests.TestData.login_2, com.teamTests.TestData.password_2);
        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size() > 0);
    }

    //    @Test (dependsOnMethods = "connectToWorkSpaceSuccess", alwaysRun = true , priority = -1)
    static void loginFail() throws Exception{
        login("bad@login.com","badPass");
        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user_name")).size()<1);
    }

      //  @Test (dependsOnMethods = "loginSuccess")
    public static void testSlackbot() throws Exception {

        browser2.findElement(By.xpath("//span[text()='slackbot']")).click();
            Thread.sleep(3000);

            h2.findAndFill(By.cssSelector(".msg_input"), TestData.messageToBot + "\n");

        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
    }

    @Test (dependsOnMethods = "loginSuccess")
    static void messageBot() {
        browser2.findElement(By.xpath("//span[text()='slackbot']")).click();
        Assert.assertTrue(browser2.findElement(By.cssSelector("button[id='im_title']")).getText().contains("slackbot"));

        Actions actions = new Actions(browser2);
        actions.moveToElement(browser2.findElement(By.id("msg_input"))).click();
        actions.sendKeys(TestData.messageToBot + "\n").build().perform();
        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageToBot + "']";
        Assert.assertEquals(browser2.findElements(By.xpath(selector)).size(),1);
    }

    //    @Test (dependsOnMethods = "loginSuccess" , priority = 20)
//    public static void testSignOut(){
//        browser2.findElement(By.cssSelector("#team_menu")).click();
//        browser2.findElement(By.cssSelector("#menu_items_scroller li#logout.logout_url")).click();
//
//        Assert.assertTrue(browser2.findElements(By.cssSelector("#team_menu_user")).size()>0);
//    }
//    @Test(dependsOnMethods = "loginSuccess")
//@Test
//    static void getMessage() throws Exception {
//        loginSuccess();
//        Thread.sleep(3000);
//
//        new FluentWait<>(browser2)
//                .withTimeout(Duration.ofSeconds(5))
//                .pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class)
//                .until(browser2 -> browser2.findElement(By.xpath("//span[text()='" + TestData.userName_1 + "']")))
//                .click();
//
//        Assert.assertTrue(listMessage());
//
////        Assert.assertTrue(browser2.findElement(By.cssSelector("button[id='im_title']")).getText().contains(TestData.userName_1));
//
////        String selector = "//span[@class='c-message__body' and text() = '" + TestData.messageText + "']";
////        System.out.println(selector);
////        Assert.assertTrue(browser2.findElements(By.xpath(selector)).size()>0);
//
////        List<WebElement>messageBodyLines = browser2.findElements(By.cssSelector(".c-message__body"));
////        Assert.assertTrue(messageBodyLines.contains(com.teamTests2.TestData.messageText));
////        проверка проходит, если есть конкретный текст сообщения, без генерирования даты
//    }


    @Test (dependsOnMethods = "loginSuccess")
    static void getMessage1() throws Exception {
//        loginSuccess();
        Thread.sleep(3000);

        new FluentWait<>(browser2)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class)
                .until(browser2 -> browser2.findElement(By.xpath("//span[text()='" + TestData.userName_1 + "']")))
                .click();
        Thread.sleep(3000);

        Assert.assertTrue(checkLines());
    }
    public static boolean checkLines () throws InterruptedException {

        List<WebElement> messageLines = browser2.findElements(By.cssSelector("span.c-message__body"));
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


    }

