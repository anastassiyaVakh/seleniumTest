package com.romanceabroad.ui;

import com.romanceabroad.ui.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

public class BaseUI {
    WebDriver driver;
    WebDriverWait wait;
    String mainUrl = "https://romanceabroad.com/";
    MainPage mainPage;
    MediaPage mediaPage;
    SearchPage searchPage;
    TourToUkrainePage tourToUkrainePage;
    GiftsPage giftsPage;
    ContactUsPage contactUsPage;
    BlogPage blogPage;
    SoftAssert softAssert = new SoftAssert ();



    @BeforeMethod (groups = {"user", "admin", "ie"}, alwaysRun = true)
    @Parameters("browser")
    public void setup (@Optional("chrome") String browser, Method method) throws Exception {

        //Check if parameter passed from TestNG is 'firefox'
        if (browser.equalsIgnoreCase("firefox")) {
            //Create firefox instance
            System.setProperty("webdriver.gecko.driver", "geckodriver");
            driver = new FirefoxDriver();
        }

        // Check if parameter passed 'chrome'
        else if (browser.equalsIgnoreCase("chrome")) {

            //Set path to chromedriver.exe
            System.setProperty("webdriver.chrome.driver", "chromedriver");
            //Create chrome instance
            driver = new ChromeDriver();
            driver.get("chrome://settings/clearBrowserData");
        }else if (browser.equalsIgnoreCase("IE")) {

            System.setProperty("webdriver.ie.driver", "IEDriverServer");
            driver = new InternetExplorerDriver();
            driver.manage().deleteAllCookies();

        }else {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
            driver = new ChromeDriver();
            driver.get("chrome://settings/clearBrowserData");
        }

        wait = new WebDriverWait(driver, 20);
        mainPage = new MainPage(driver, wait);
        mediaPage = new MediaPage (driver, wait);
        searchPage = new SearchPage (driver, wait);
        tourToUkrainePage = new TourToUkrainePage (driver,wait);
        giftsPage = new GiftsPage (driver,wait);
        contactUsPage = new ContactUsPage (driver,wait);
        blogPage = new BlogPage (driver,wait);
        driver.manage().window().maximize();
        driver.get(Data.mainUrl);
    }

    @AfterMethod
    public void afterActions() {
        driver.quit();
    }

}


