import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

import static helpers.DriverSingleton.getDriver;
import static helpers.DriverSingleton.quit;
import static helpers.Helper.isElementPresent;

public class ForgotPassword {
    private final static String BASE_URL = "https://the-internet.herokuapp.com";
    private final static String BASE_URL_FOR_EMAIL = "https://www.guerrillamail.com/";


    @BeforeMethod(alwaysRun = true)
    public void setup() {
        getDriver().get(BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        quit();
    }

    @Test
    public void forgotPassword() throws InterruptedException {
        String emailName = "brutto";
//        String emailName = "bertronium1986";
        String emailAfterDog = "guerrillamail.de";
        String emailDog = "@";
        getDriver().get(BASE_URL_FOR_EMAIL);
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(getDriver(), 12);
//        wait.until(ExpectedConditions.);
//        System.out.println(getDriver().findElement(By.id("inbox-id")).getText().toString());
        if (isElementPresent(By.cssSelector("input[value='']"))) {
            getDriver().findElement(By.cssSelector("input[value='']")).sendKeys(emailName);
//            System.out.println("test pustoe pole vvoda, i v nego vveli znachenie");
        } else {
            getDriver().findElement(By.id("forget_button")).click();
            getDriver().findElement(By.cssSelector("input[value='']")).sendKeys(emailName);
//            System.out.println("test ni to ni drugoe, i v nego vveli znachenie");
        }
        Select selectBox = new Select(getDriver().findElement(By.id("gm-host-select")));
        selectBox.selectByValue(emailAfterDog);
        getDriver().findElement(By.cssSelector(".save.button.small")).click();

        getDriver().get(BASE_URL);
        getDriver().findElement(By.linkText("Forgot Password")).click();
        getDriver().findElement(By.id("email")).sendKeys(emailName + emailDog + emailAfterDog);
        getDriver().findElement(By.id("form_submit")).click();

        getDriver().get(BASE_URL_FOR_EMAIL);
        Thread.sleep(15000);
        List<WebElement> emailList = getDriver().findElements(By.cssSelector("#email_list .td2"));
        Assert.assertTrue(emailList.get(0).getText().contains("no-reply@the-internet.herokuapp.com"));
        emailList.get(0).click();
//        System.out.println(getDriver().findElement(By.cssSelector(".email .email_subject")).getText().toString());
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".email .email_subject")).getText(), "Forgot Password from the-internet");
//        getDriver().findElement(By.xpath("//a[text()='http://the-internet.herokuapp.com/login']"));


    }
}
