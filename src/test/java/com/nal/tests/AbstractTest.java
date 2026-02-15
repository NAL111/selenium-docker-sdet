package com.nal.tests;

import com.nal.pages.vendorportal.DashboardPage;
import com.nal.pages.vendorportal.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by N on 2/13/2026
 **/

public abstract class AbstractTest {

    protected WebDriver driver;

//    @BeforeTest
//    public void setDriver() {
//        WebDriverManager.chromedriver().driverVersion("144.0.7559.133").setup();
//        ChromeOptions options = new ChromeOptions();
//        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
//        this.driver = new ChromeDriver(options);
//        this.driver.manage().window().maximize();
//    }

//    @BeforeTest
//    public void setDriver() {
//        WebDriverManager.chromedriver().setup();
//        this.driver = new ChromeDriver();
//        this.driver.manage().window().maximize();
//    }

    @BeforeTest
    public void setDriver() throws MalformedURLException {
        if (Boolean.getBoolean("selenium.grid.enabled")) {
            this.driver = getRemoteDriver();
        } else {
            this.driver = getLocalBraveDriver();
            this.driver.manage().window().maximize();
        }
    }

//    @BeforeTest
//    @Parameters({"browser"})
//    public void setDriver(String browser) throws MalformedURLException {
//        if (Boolean.getBoolean("selenium.grid.enabled")) {
//            this.driver = getRemoteDriverFromParams(browser);
//        } else {
//            this.driver = getLocalBraveDriver();
//            this.driver.manage().window().maximize();
//        }
//    }

    private WebDriver getRemoteDriver() throws MalformedURLException {

        //Selenium hub path to send our requests: http://localhost:4444/wd/hub

        Capabilities capabilities;
        if (System.getProperty("browser").equalsIgnoreCase("chrome")) {
            capabilities = new ChromeOptions();
        } else {
            capabilities = new FirefoxOptions();
        }
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    }

    private WebDriver getRemoteDriverFromParams(String browser) throws MalformedURLException {

        // Enable Selenium Grid and Disable System property variables for browsers in pom.xml

        Capabilities capabilities;
        if (browser.equalsIgnoreCase("chrome")) {
            capabilities = new ChromeOptions();
        } else {
            capabilities = new FirefoxOptions();
        }
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    }

    private WebDriver getLocalChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private WebDriver getLocalBraveDriver() {
        WebDriverManager.chromedriver().driverVersion("144.0.7559.133").setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        return new ChromeDriver(options);
    }

    @AfterTest
    public void quitDriver() {
        this.driver.quit();
    }
}
