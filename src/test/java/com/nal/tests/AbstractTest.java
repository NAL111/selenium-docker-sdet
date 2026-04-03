package com.nal.tests;

import com.google.common.util.concurrent.Uninterruptibles;
import com.nal.listener.TestListener;
import com.nal.pages.vendorportal.DashboardPage;
import com.nal.pages.vendorportal.LoginPage;
import com.nal.util.Config;
import com.nal.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
//import org.testng.annotations.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Listeners;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;

/**
 * Created by N on 2/13/2026
 **/

@Listeners({TestListener.class})
public abstract class AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(AbstractTest.class);

    protected WebDriver driver;

    @BeforeSuite
    public void setupConfig() {
        Config.initialize();
    }

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

//    @BeforeTest
//    public void setDriver() throws MalformedURLException {
//        if (Boolean.getBoolean("selenium.grid.enabled")) {
//            this.driver = getRemoteDriver();
//        } else {
//            this.driver = getLocalBraveDriver();
//            this.driver.manage().window().maximize();
//        }
//    }

    @BeforeTest
    public void setDriver(ITestContext testContext) throws MalformedURLException {

//        if (Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED))) {
//            this.driver = getRemoteDriver();
//        } else {
//            this.driver = getLocalBraveDriver();
//            this.driver.manage().window().maximize();
//        }

        this.driver = Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)) ?
                getRemoteDriver() : getLocalBraveDriver(); this.driver.manage().window().maximize();
        testContext.setAttribute(Constants.DRIVER, this.driver);

//        this.driver = Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)) ?
//                getRemoteDriver() : getLocalFirefoxDriver(); this.driver.manage().window().maximize();
//        testContext.setAttribute(Constants.DRIVER, this.driver);

//        if (Boolean.getBoolean("selenium.grid.enabled")) {
//            this.driver = getRemoteDriver();
//        } else {
//            this.driver = getLocalBraveDriver();
//            this.driver.manage().window().maximize();
//        }
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

        Capabilities capabilities = new ChromeOptions();
//        if (Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))) {
        if (Config.get(Constants.BROWSER).equalsIgnoreCase(Constants.FIREFOX)) {
            capabilities = new FirefoxOptions();
//        } else if (Constants.EDGE.equalsIgnoreCase(Config.get(Constants.BROWSER))) {
        } else if (Config.get(Constants.BROWSER).equalsIgnoreCase(Constants.EDGE)) {
            capabilities = new EdgeOptions();
        }

//        Capabilities capabilities;
//        if (System.getProperty("browser").equalsIgnoreCase("chrome")) {
//            capabilities = new ChromeOptions();
//        } else if (System.getProperty("browser").equalsIgnoreCase("firefox")){
//            capabilities = new FirefoxOptions();
//        } else{
//            capabilities = new EdgeOptions();
//        }

        String urlFormat = Config.get(Constants.GRID_URL_FORMAT);
        String hubHost = Config.get(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat, hubHost);
        log.info("Grid url: {}", url);

//        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        return new RemoteWebDriver(new URL(url), capabilities);
    }

    private WebDriver getRemoteDriverFromParams(String browser) throws MalformedURLException {

        // Enable Selenium Grid and Disable System property variables for browsers in pom.xml
        // Based on browser params defined for each test in testng.xml

        Capabilities capabilities;
        if (browser.equalsIgnoreCase("chrome")) {
            capabilities = new ChromeOptions();
        } else if (browser.equalsIgnoreCase("firefox")){
            capabilities = new FirefoxOptions();
        } else if (browser.equalsIgnoreCase("edge")) {
            capabilities = new EdgeOptions();
        } else {
            capabilities = null;
        }
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), Objects.requireNonNull(capabilities));
    }

    private WebDriver getLocalChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private WebDriver getLocalBraveDriver() {
//        WebDriverManager.chromedriver().driverVersion("144.0.7559.133").setup();
        WebDriverManager.chromedriver().driverVersion("146.0.7680.164").setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        return new ChromeDriver(options);
    }

    private WebDriver getLocalFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    @AfterTest
    public void quitDriver() {
        this.driver.quit();
    }

//    @AfterMethod
//    public void sleep() {
//        // All test methods will wait for 5 secs if we want to observe tests running in grid browsers
//        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
//    }
}
