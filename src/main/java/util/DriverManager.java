package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DriverManager {
    private static List<WebDriver> instances = null;
    private static Properties props = null;


    public static Properties getProps() {
        if (props != null) {
            return props;
        }else {
            props = new Properties();
            InputStream input = null;
            try {

                input = new FileInputStream("config.properties");

                // load a properties file
                props.load(input);
                return props;
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("[ERROR] no props file");
        return null;
    }

    /**
     * updates the desired capabilities object based on browser requirement and config.properties file
     * @param browsername browsername
     * @return
     */
    public static DesiredCapabilities setCapabilities(String browsername) {
        DesiredCapabilities caps = null;
        switch (browsername) {
            case("chrome"):
                caps = DesiredCapabilities.chrome();
                caps.setCapability("platform", "Windows 10");
                caps.setCapability("version", "72.0");
                return caps;
            case("firefox"):
                caps = DesiredCapabilities.firefox();
                caps.setCapability("platform", "Windows 10");
                caps.setCapability("version", "51.0");
                return caps;
            case("internet-explorer"):
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability("platform", "Windows 10");
                caps.setCapability("version", "11.285");
                return caps;
            case("edge"):
                caps = DesiredCapabilities.edge();
                caps.setCapability("platform", "Windows 10");
                caps.setCapability("version", "18.17763");
                return caps;
            case("chrome-android"):
                caps = DesiredCapabilities.android();
                caps.setCapability("appiumVersion", "1.9.1");
                caps.setCapability("deviceName","Android Emulator");
                caps.setCapability("deviceOrientation", "portrait");
                caps.setCapability("browserName", "Chrome");
                caps.setCapability("platformVersion", "8.0");
                caps.setCapability("platformName","Android");
                return caps;
            case("safari-mac"):
                caps = DesiredCapabilities.safari();
                caps.setCapability("platform", "macOS 10.14");
                caps.setCapability("version", "12.0");
                return caps;
            case("safari-ios"):
                caps = DesiredCapabilities.iphone();
                caps.setCapability("appiumVersion", "1.9.1");
                caps.setCapability("deviceName","iPhone X Simulator");
                caps.setCapability("deviceOrientation", "portrait");
                caps.setCapability("platformVersion","12.0");
                caps.setCapability("platformName", "iOS");
                caps.setCapability("browserName", "Safari");
                return caps;
            default:
                System.out.println("[error]invalid type");
                return null;
        }
    }

    public static WebDriver getDriver(String type) {
        if (instances == null) {
            instances = new ArrayList<WebDriver>();
        }
        WebDriver driver = null;
        props = getProps();
        String driverType = type;
        if (driverType == "local"){
            String path = props.getProperty("local.chrome.driver.path");
            System.setProperty("webdriver.chrome.driver",path);
            driver = new ChromeDriver();
            instances.add(driver);
        } else{
            DesiredCapabilities caps = setCapabilities(type);
            caps.setCapability("username",props.getProperty("saucelabs.user"));
            caps.setCapability("accessKey", props.getProperty("saucelabs.password"));
            caps.setCapability("name", props.getProperty("saucelabs.name"));
            try {
                driver = new RemoteWebDriver(new URL("http://ondemand.saucelabs.com:80/wd/hub"), caps);
                instances.add(driver);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return driver;
    }

    /**
     * clean up everything
     */
    public static void killDriver() {
        for (WebDriver instance : instances) {
            instance.close();
            instance.quit();
        }
    }
}
