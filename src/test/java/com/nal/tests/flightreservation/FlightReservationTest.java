package com.nal.tests.flightreservation;

import com.nal.pages.flightreservation.*;
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
 * Created by N on 2/5/2026
 **/

public class FlightReservationTest {

    private WebDriver driver;
    private String numOfPassengers;
    private String expectedPrice;

    @BeforeTest
    @Parameters({"numOfPassengers", "expectedPrice"})
    public void setDriver(String numOfPassengers, String expectedPrice) {
        this.numOfPassengers = numOfPassengers; // bug if commented out
        this.expectedPrice = expectedPrice;
        WebDriverManager.chromedriver().driverVersion("144.0.7559.133").setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        this.driver = new ChromeDriver(options);
        this.driver.manage().window().maximize();
    }

//    @BeforeTest
//    public void setDriver() {
//        WebDriverManager.chromedriver().driverVersion("144.0.7559.133").setup();
//        ChromeOptions options = new ChromeOptions();
//        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
//        this.driver = new ChromeDriver(options);
//        this.driver.manage().window().maximize();
//    }

    @Test
    public void userRegistrationTest() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.goTo("https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/reservation-app/index.html");
        Assert.assertTrue(registrationPage.isAt());

        registrationPage.enterUserDetails("selenium", "docker");
        registrationPage.enterUserCredentials("selenium@docker.com", "docker");
        registrationPage.enterUserAddress("123 some street", "state", "11123");
        registrationPage.register();
    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void registrationConfirmationTest() {
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        Assert.assertTrue(registrationConfirmationPage.isAt());
        registrationConfirmationPage.goToFlightsSearch();
    }

    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightsSearchTest() {
        FlightsSearchPage flightsSearchPage = new FlightsSearchPage(driver);
        Assert.assertTrue(flightsSearchPage.isAt());
        flightsSearchPage.selectPassengers(numOfPassengers);
//        flightsSearchPage.selectPassengers("1");
        flightsSearchPage.searchFlight();
    }

    @Test(dependsOnMethods = "flightsSearchTest")
    public void flightsSelectionTest() {
        FlightsSelectionPage flightsSelectionPage = new FlightsSelectionPage(driver);
        Assert.assertTrue(flightsSelectionPage.isAt());
        flightsSelectionPage.selectFlights();
        flightsSelectionPage.confirmFlights();
    }

    @Test(dependsOnMethods = "flightsSelectionTest")
    public void flightReservationConfirmationTest() {
        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
        Assert.assertTrue(flightConfirmationPage.isAt());
        Assert.assertEquals(flightConfirmationPage.getPrice(), expectedPrice);
//        Assert.assertEquals(flightConfirmationPage.getPrice(), "$584 USD");
    }

    @AfterTest
    public void quitDriver() {
        this.driver.quit();
    }

}
