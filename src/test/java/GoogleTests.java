import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.DriverManager;


import java.util.List;

public class GoogleTests {

    @AfterSuite
    public void killDriver() {
        DriverManager.killDriver();
    }

    @DataProvider(name = "platforms")

    public static Object[] browsers() {
        return new Object[] { "local","chrome","firefox","internet-explorer","edge","chrome-android","safari-mac","safari-ios","local"};
    }

    @Test(description = "lets set selenium up again",dataProvider = "platforms")
    public void getToGoogle(String browser) {
        //selectors
        String searchBoxSuggestions = "form ul li";
        //String searchBoxSuggestions = "li[role='presentation'] div.suggestions-inner-container";
//        String resultLinkXpath = "//div[@class='r']/a[contains(@href, 'hotelbeds.com')]";
//        String resultLinkXpath = "//a[contains(@class, 'C8nzq') and contains(@href, 'hotelbeds.com')]";
       // String resultLinkXpath = "//a/div/span/span[contains(text(),'hotelbeds.com')]"; //only valid results, not links
//        String resultLinkXpath = "//a[contains(@href,'www.hotelbeds.com')]//span[contains(text(),'otelbeds')]"; //getting this to work across countries, devices with one selector is not servicable, but i got it to work.
        String resultLinkXpath = "//a[contains(@href,'www.hotelbeds.com')]//*[self::cite or self:: span and contains(text(),'hotelbeds.com')]";

        System.out.println("test type: " + browser);
        WebDriver driver = DriverManager.getDriver(browser);

        driver.get("http://www.google.com/xhtml");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("hotelbeds");
        WebDriverWait waiter = new WebDriverWait(driver,10);
        waiter.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(searchBoxSuggestions)));

        List<WebElement> suggestions = driver.findElements(By.cssSelector(searchBoxSuggestions));
        suggestions.get(0).click();
        List<WebElement> validlinks = driver.findElements(By.xpath(resultLinkXpath));


        /**
         * not required for the test, therefore I have used a loop just to see that everything in O-K
         */
        System.out.println("valid links : " + validlinks.size());
        for(WebElement link : validlinks) {
            System.out.println("-----");
            System.out.println(link.getText());
        }
        Assert.assertTrue(validlinks.size() >= 5);

    }




}
