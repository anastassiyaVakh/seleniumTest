package com.romanceabroad.ui;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;
import java.util.NoSuchElementException;

public class BaseActions {

    protected WebDriver driver;
    protected WebDriverWait wait;

    //constractor
    public BaseActions(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public void javaWait(int ms){
        try{
            Thread.sleep (ms);
        }catch (InterruptedException e){
            e.printStackTrace ();
        }
    }
    public void javaWaitSec(int sec){
        try{
            Thread.sleep (sec * 1000);
        }catch (InterruptedException e){
            e.printStackTrace ();
        }
    }

    public void ajaxSendKeys(WebElement element, String text){
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text +"')", element);
    }


    public static String generateNewNumber (String name, int length){
        return name + RandomStringUtils.random(length, "125466");
    }

    public static String generateRandomString(){
        return new BigInteger (120, new SecureRandom ()).toString(32);
    }

    public void checkLinksOnWebPage(String typeElement, String attribute){

        List<WebElement> links = driver.findElements(By.xpath(typeElement));

        System.out.println("I start taking  attributes on page");
        for (int i = 0; i < links.size(); i++) {
            WebElement ele = links.get(i);
            String url = ele.getAttribute(attribute);
            verifyLinkActive(url);
        }

        System.out.println("Total links are " + links.size());
    }
    // Method for link verification
    public void verifyLinkActive (String linkUrl){
        try {
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
            httpURLConnect.setConnectTimeout(3000);
            httpURLConnect.connect();
            if (httpURLConnect.getResponseCode() == 200){
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
            }
            else  if (httpURLConnect.getResponseCode() >=400 && httpURLConnect.getResponseCode()<= 504){
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - " + httpURLConnect.getResponseMessage());
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void scrollToBottomOfPage(){
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    public void clickUnselectedCheckbox(By checkbox){
        WebElement currentCheckbox = driver.findElement(checkbox);
        if (!currentCheckbox.isDisplayed()){
            ajaxClick(currentCheckbox);
        }
    }

    public void ajaxClick(WebElement element){ //java script executer
        ((JavascriptExecutor) driver).executeScript ("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void ajaxClick (By by){
        wait. until(ExpectedConditions.elementToBeClickable(by));
        ajaxClick(driver.findElement(by));
    }

    public void ajaxClick (By by, int index){
        wait.until(ExpectedConditions.elementToBeClickable(by));
        ajaxClick(driver.findElements(by).get(index));
    }

    public void performClick(By locator){
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        actions.click().build().perform();

    }

    public void performClick(By locator, int index){
        WebElement element = driver.findElements(locator).get(index);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        actions.click().build().perform();

    }

    public void performClick(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        actions.click().build().perform();

    }

    public void getNavigateToLinkPage(By locator){driver.findElement(locator).click();}



    public void getDropDownListByValue(WebElement element, String value){
        Select select = new Select(element);
        select.selectByValue(value);
    }
    public void getDropDownListByIndex(WebElement element, int index){
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    //Method for random choice from dropdown list
    public void selectItemDropDownRandomOption(By locator, String dropDownName){
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Select select = new Select(driver.findElement(locator));
            select.selectByIndex((int) (Math.random() * (select.getOptions().size() - 1)) + 1);
            System.out.println(dropDownName + ": " + select.getFirstSelectedOption().getText());
        } catch (NoSuchElementException e){

        }
    }

    public int getSizeDropDownList(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Select select = new Select(driver.findElement(locator));
            return select.getOptions().size();

        } catch (NoSuchElementException e) {
            System.out.println("getSizedropDownList error");

        }
        return 0;

    }
    public String getAnyTitle(){
        String title = driver.findElement (Locators.H1_TTILE).getText ();
        return title;
    }


    public void clickValueOfLists (By locator, String text){
        List<WebElement>elements = driver.findElements (locator);
        for (int i = 0; i <elements.size (); i ++){
            WebElement elementOfList = elements.get(i);
            String value = elementOfList.getText ();
            if (value.contains (text)){
                elementOfList.click ();

            }
        }

    }
}
