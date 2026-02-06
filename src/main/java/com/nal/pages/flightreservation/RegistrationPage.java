package com.nal.pages.flightreservation;

import com.nal.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationPage extends AbstractPage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(name = "street")
    private WebElement streetAddress;

    @FindBy(name = "city")
    private WebElement city;

    @FindBy(name = "zip")
    private WebElement zipCode;

    @FindBy(id = "register-btn")
    private WebElement registerButton;

    @Override
    public boolean isAt() {
        this.wait.until(ExpectedConditions.visibilityOf(this.firstName));
        return this.firstName.isDisplayed();
    }

    public void goTo(String url) {
        this.driver.get(url);
    }

    public void enterUserDetails(String firstName, String lastName) {
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
    }

    public void enterUserCredentials(String email, String password) {
        this.email.sendKeys(email);
        this.password.sendKeys(password);
    }

    public void enterUserAddress(String streetAddress, String city, String zipCode) {
        this.streetAddress.sendKeys(streetAddress);
        this.city.sendKeys(city);
        this.zipCode.sendKeys(zipCode);
    }

    public void register() {
        this.registerButton.click();
    }
}
