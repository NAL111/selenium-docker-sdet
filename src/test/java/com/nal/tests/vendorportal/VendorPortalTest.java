package com.nal.tests.vendorportal;

import com.nal.pages.vendorportal.DashboardPage;
import com.nal.pages.vendorportal.LoginPage;
import com.nal.tests.AbstractTest;
import com.nal.tests.vendorportal.model.VendorPortalTestData;
import com.nal.util.JsonUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Created by N on 2/12/2026
 **/

public class VendorPortalTest extends AbstractTest {

//    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VendorPortalTestData testData;

    @BeforeTest
    @Parameters("testDataPath")
//    public void setDriver() {
    public void setPageObjects(String testDataPath) {
//        WebDriverManager.chromedriver().driverVersion("144.0.7559.133").setup();
//        ChromeOptions options = new ChromeOptions();
//        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
//        this.driver = new ChromeDriver(options);
//        this.driver.manage().window().maximize();
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.testData = JsonUtil.getTestData(testDataPath, VendorPortalTestData.class);
    }

    @Test
    public void loginTest() {
//        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo("https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/vendor-app/index.html");
        Assert.assertTrue(loginPage.isAt());
//        loginPage.login("sam", "sam");
        loginPage.login(testData.username(), testData.password());
    }

    @Test(dependsOnMethods = "loginTest")
    public void dashboardTest() {
//        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isAt());

//        Assert.assertEquals(dashboardPage.getMonthlyEarning(), "$40,000");
        Assert.assertEquals(dashboardPage.getMonthlyEarning(), testData.monthlyEarnings());
//        Assert.assertEquals(dashboardPage.getAnnualEarning(), "$215,000");
        Assert.assertEquals(dashboardPage.getAnnualEarning(), testData.annualEarning());
//        Assert.assertEquals(dashboardPage.getProfitMargin(), "50%");
        Assert.assertEquals(dashboardPage.getProfitMargin(), testData.profitMargin());
//        Assert.assertEquals(dashboardPage.getAvailableInventory(), "18");
        Assert.assertEquals(dashboardPage.getAvailableInventory(), testData.availableInventory());

//        dashboardPage.searchOrderHistoryBy("adams");
        dashboardPage.searchOrderHistoryBy(testData.searchKeyword());
//        Assert.assertEquals(dashboardPage.getSearchResultsCount(), 8);
        Assert.assertEquals(dashboardPage.getSearchResultsCount(), testData.searchResultsCount());

//        dashboardPage.logout();
    }

    @Test(dependsOnMethods = "dashboardTest")
    public void logoutTest() {
        dashboardPage.logout();
        Assert.assertTrue(loginPage.isAt());
    }

//    @AfterTest
//    public void quitDriver() {
//        this.driver.quit();
//    }

}
