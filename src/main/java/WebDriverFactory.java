import io.github.bonigarcia.wdm.WebDriverManager;
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

}
