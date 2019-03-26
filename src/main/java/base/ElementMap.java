package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * this class stores locators and helper methods for locators
 */
public abstract class ElementMap {
    WebDriver driver;
    public ElementMap(WebDriver driver) {
        this.driver = driver;
    }



    protected WebDriver getDriver() {
        return this.driver;
    }
}
