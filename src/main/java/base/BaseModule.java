package base;

import org.openqa.selenium.WebDriver;

/**
 * I like to work with modules for composite components
 *
 */
public abstract class BaseModule {

    public WebDriver driver;
    ElementMap map;
    public BaseModule(WebDriver driver) {
        this.driver = driver;

    }

    public abstract ElementMap getMap();


}
