package picker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DatePickerMap extends base.ElementMap{
    public DatePickerMap(WebDriver driver) {
        super(driver);
    }

    String days = "assumed Known";
    public List<WebElement> days_elems() {
        return this.getDriver().findElements(By.cssSelector(days));
    }

    String active_day = "assumed Known";
    public WebElement active_day_elem() {
        return this.getDriver().findElement(By.cssSelector(active_day));
    }

    String next_month_btn = "assumed Known";
    public WebElement next_month_btn_elem() {
        return this.getDriver().findElement(By.cssSelector(next_month_btn));
    }

}
