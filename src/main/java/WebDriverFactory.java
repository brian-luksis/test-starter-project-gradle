import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {

    static {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    public static WebDriver chrome(DesiredCapabilities caps) {
        return new ChromeDriver(new ChromeOptions().merge(caps));
    }

    public static WebDriver chrome() {
        return chrome(new DesiredCapabilities());
    }

    public static WebDriver firefox(DesiredCapabilities caps) {
        return new FirefoxDriver(new FirefoxOptions().merge(caps));
    }

    public static WebDriver firefox() {
        return chrome(new DesiredCapabilities());
    }

    public static IOSDriver ios(Capabilities caps) {
        return new IOSDriver(caps);
    }

    public static IOSDriver ios() {
        return new IOSDriver(new DesiredCapabilities());
    }

    public static AndroidDriver android(Capabilities caps) {
        return new AndroidDriver(caps);
    }

    public static AndroidDriver android() {
        return new AndroidDriver(new DesiredCapabilities());
    }

}
