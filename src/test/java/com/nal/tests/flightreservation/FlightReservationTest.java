package com.nal.tests.flightreservation;

import com.nal.pages.flightreservation.*;
import com.nal.tests.AbstractTest;
import com.nal.tests.flightreservation.model.FlightReservationTestData;
import com.nal.util.Config;
import com.nal.util.Constants;
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
 * Created by N on 2/5/2026
 **/

public class FlightReservationTest extends AbstractTest {

//    private WebDriver driver;
//    private String numOfPassengers;
//    private String expectedPrice;
    private FlightReservationTestData testData;

    @BeforeTest
//    @Parameters({"numOfPassengers", "expectedPrice"})
    @Parameters("testDataPath")
//    public void setDriver(String numOfPassengers, String expectedPrice) {
//    public void setParameters(String numOfPassengers, String expectedPrice) {
    public void setParameters(String testDataPath) {
//        this.numOfPassengers = numOfPassengers; // bug if commented out
//        this.expectedPrice = expectedPrice;
        this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
//        WebDriverManager.chromedriver().driverVersion("144.0.7559.133").setup();
//        ChromeOptions options = new ChromeOptions();
//        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
//        this.driver = new ChromeDriver(options);
//        this.driver.manage().window().maximize();
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
//        registrationPage.goTo("https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/reservation-app/index.html");
        registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
        Assert.assertTrue(registrationPage.isAt());

//        registrationPage.enterUserDetails("selenium", "docker");
        registrationPage.enterUserDetails(testData.firstName(), testData.lastName());
//        registrationPage.enterUserCredentials("selenium@docker.com", "docker");
        registrationPage.enterUserCredentials(testData.email(), testData.password());
//        registrationPage.enterUserAddress("123 some street", "state", "11123");
        registrationPage.enterUserAddress(testData.street(), testData.city(), testData.zip());
        registrationPage.register();
    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void registrationConfirmationTest() {
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        Assert.assertTrue(registrationConfirmationPage.isAt());
        Assert.assertEquals(registrationConfirmationPage.getFirstName(), testData.firstName());
        registrationConfirmationPage.goToFlightsSearch();
    }

    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightsSearchTest() {
        FlightsSearchPage flightsSearchPage = new FlightsSearchPage(driver);
        Assert.assertTrue(flightsSearchPage.isAt());
//        flightsSearchPage.selectPassengers("1");
//        flightsSearchPage.selectPassengers(numOfPassengers);
        flightsSearchPage.selectPassengers(testData.passengersCount());
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
//        Assert.assertEquals(flightConfirmationPage.getPrice(), "$584 USD");
//        Assert.assertEquals(flightConfirmationPage.getPrice(), expectedPrice);
        Assert.assertEquals(flightConfirmationPage.getPrice(), testData.expectedPrice());
    }

//    @AfterTest
//    public void quitDriver() {
//        this.driver.quit();
//    }

}
