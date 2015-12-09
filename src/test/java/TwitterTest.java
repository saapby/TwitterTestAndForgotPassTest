import helpers.DataProviders;
import helpers.Helper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SignupPage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static helpers.DriverSingleton.getDriver;
import static helpers.DriverSingleton.quit;

/**
 * Created by Admin on 06.11.2015.
 */
public class TwitterTest {

    private SignupPage signupPage;

    private final static String BASE_URL = "https://twitter.com/signup";
//    private final static String BASE_URL = "https://twitter.com/signup?lang=en";
    private final String USER_NAME = "user name";
//    private final String USER_EMAIL = "user@name";
//    private final String PASSWORD = "pass";


    @BeforeMethod (alwaysRun = true)
    public void setup() {
        getDriver().get(BASE_URL);
        signupPage = new SignupPage();
    }

    @AfterMethod
    public void tearDown() {
        quit();
    }

    @Test
    public void signupTwitterTest() throws IOException {
        getDriver().findElement(By.className("caret")).click();
        getDriver().findElement(By.cssSelector("a[href='?lang=en']")).click();
        String name = "";
        String email = "invalidemail";
        String pass = "PassWord525dSDwdq";
        signupPage.enterFields(name, email, pass);
        List<WebElement> validation = signupPage.getValidMess();
        Assert.assertEquals(validation.size(), 1);
        Assert.assertTrue(getDriver().findElement(signupPage.active_email_valid_mess).isDisplayed());
        WebElement valid =getDriver().findElement(signupPage.active_email_valid_mess);
        Helper.saveScreenshot("F:\\it-academy-selenium\\lesson2015.11.13\\screen.png");
        Assert.assertTrue(valid.isDisplayed());
        Assert.assertEquals(valid.getText(), "Please enter a valid email.");

//        Assert.assertTrue(validation.get(0).findElement(By.xpath(".//parent::div[@data-fieldname='email']")).isDisplayed());

    }

    @Test (dataProvider = "registrationDate", dataProviderClass = DataProviders.class)
    public void twitterTest(String user, String email, String pass, String user_valid, String email_valid, String pass_valid) throws InterruptedException {
        signupPage.fillForm(user, email, pass);
        Thread.sleep(1000);
        int validation = 0;

        if (user_valid.length() != 0) {
            validation++;
            WebElement userValidElem =getDriver().findElement(signupPage.active_user_valid_mess);
            Assert.assertTrue(userValidElem.isDisplayed());
            Assert.assertEquals(userValidElem.getText(), user_valid);
        }

        if (email_valid.length() > 2) {
            validation++;
            WebElement emailValidElem =getDriver().findElement(signupPage.active_email_valid_mess);
            Assert.assertTrue(emailValidElem.isDisplayed());
            Assert.assertEquals(emailValidElem.getText(), email_valid);
        }

        if (pass_valid.length() > 2) {
            validation++;
            WebElement passValidMess =getDriver().findElement(signupPage.active_pass_valid_mess);
            Assert.assertTrue(passValidMess.isDisplayed());
            Assert.assertEquals(passValidMess.getText(), pass_valid);
        }

        Assert.assertEquals(SignupPage.getValidMess().size(), validation);
    }

    @Test
    public void signupTwitterScreenshotTest() throws IOException {
        String name = "";
        String email = "invalidemail";
        String pass = "PassWord525dSDwdq";
        signupPage.enterFields(name, email, pass);
        List<WebElement> validation = signupPage.getValidMess();
        Assert.assertEquals(validation.size(), 1);
        Assert.assertTrue(getDriver().findElement(signupPage.active_email_valid_mess).isDisplayed());
        WebElement valid =getDriver().findElement(signupPage.active_email_valid_mess);
        Helper.saveScreenshot("H:\\it-academy-selenium\\lesson2015.11.13\\screen.png");
        Helper.saveScreenshot(signupPage.active_email_valid_mess, "H:\\it-academy-selenium\\lesson2015.11.13\\element.png");
        Assert.assertTrue(valid.isDisplayed());
        Assert.assertEquals(valid.getText(), "Введите действительный адрес электронной почты");

//        Assert.assertTrue(validation.get(0).findElement(By.xpath(".//parent::div[@data-fieldname='email']")).isDisplayed());
    }

//    @Test
//    public void loginTest() {
//        getDriver().findElement(By.id("username")).sendKeys("tomsmith");
//        getDriver().findElement(By.id("password")).sendKeys("SuperSecretPassword!");
//        getDriver().findElement(By.cssSelector("button[type='submit']")).click();
//        Assert.assertTrue(getDriver().findElement(By.cssSelector("#flash.success")).isDisplayed());
//        Assert.assertTrue(getDriver().findElement(By.cssSelector("a[href='/logout']")).isDisplayed());
//    }
}
