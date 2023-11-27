import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SeleniumTests {
    public String baseUrl = "http://ekart.com";
    String driverPath = "/Users/sreenidhikr/Development/chromedriver-mac-arm64/chromedriver";
    public WebDriver driver ;

    @BeforeMethod
    public void beforeTest() {

    }
    @AfterMethod
    public void afterTest() {
        driver.quit();
        System.out.println("after test");
    }

    @Test(priority = 1)
    public void bypassPasswordLength() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                driverPath);
        driver= new ChromeDriver();
        driver.get(baseUrl);


        JavascriptExecutor js = (JavascriptExecutor) driver;
        //Removing minLength attribute
        js.executeScript("document.getElementById('password').removeAttribute('minLength')");

        driver.findElement(By.id("registerButton")).click();
        driver.findElement(By.id("username")).sendKeys("username");
        //PASSWORD less than required length
        driver.findElement(By.id("password")).sendKeys("pass");
        driver.findElement(By.id("submitButton")).click();
        //RESULT : Server gives an error stating password length should be atleast 8 digits
        Thread.sleep(5000);
        System.out.println("Done");
    }
    @Test(priority = 2)
    public void bypassDataType() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                driverPath);
        driver= new ChromeDriver();
        driver.get(baseUrl);
        driver.findElement(By.id("registerButton")).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement checkbox = driver.findElement(By.id("age"));
        js.executeScript("arguments[0].setAttribute('type', 'text')", checkbox);


        driver.findElement(By.id("username")).sendKeys("username1");
        driver.findElement(By.id("password")).sendKeys("123456789");
        //MODIFY numeric data type to String
        driver.findElement(By.id("age")).sendKeys("twenty");
        //RESULT server gives an error
        driver.findElement(By.id("submitButton")).click();


        Thread.sleep(5000);
        System.out.println("Done");
    }

    @Test(priority = 3)
    public void bypassNormalWorkFlow() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        //ACCESS CART WITHOUT LOGIN
        driver.get(baseUrl+"/user-cart");
        //RESULT  REDIRECT TO LOGIN
        Thread.sleep(5000);
    }

    @Test(priority = 4)
    public void bypassRole()throws InterruptedException{
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        //NORMAL USER ACCESS SELLER SITE
        driver.get(baseUrl);
        //LOGIN
        driver.findElement(By.id("username")).sendKeys("username");
        driver.findElement(By.id("password")).sendKeys("123456789");
        driver.findElement(By.id("submitButton")).click();
        Thread.sleep(2000);

        driver.get(baseUrl+"/seller-home");

        //LOGOUT
//        driver.findElement(By.id("profile")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.id("logoutButton")).click();

        //RESULT  REDIRECT TO USER HOME PAGE
        Thread.sleep(5000);
    }



}
