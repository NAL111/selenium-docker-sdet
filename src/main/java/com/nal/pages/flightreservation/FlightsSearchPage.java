package com.nal.pages.flightreservation;

import com.nal.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class FlightsSearchPage extends AbstractPage {

    public FlightsSearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "passengers")
    private WebElement passengers;

    @FindBy(id = "search-flights")
    private WebElement searchFlightsButton;

    @Override
    public boolean isAt() {
        this.wait.until(ExpectedConditions.visibilityOf(this.passengers));
        return this.passengers.isDisplayed();
    }

    public void selectPassengers(String numOfPassengers) {
        Select passengers = new Select(this.passengers);
        passengers.selectByValue(numOfPassengers);
    }

    public void searchFlight() {
        this.searchFlightsButton.click();
    }
}
