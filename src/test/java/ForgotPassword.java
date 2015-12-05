import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SignupPage;

import java.util.ArrayList;
import java.util.List;

import static helpers.DriverSingleton.getDriver;
import static helpers.DriverSingleton.quit;

/**
 * Created by Admin on 13.11.2015.
 */
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
    public void forgtPassord() {
        getDriver().findElement(By.linkText("Forgot Password"));
        getDriver().findElement(By.id("email")).sendKeys("28qbtp+5vdtzq6piizk@sharklasers.com");
        getDriver().findElement(By.id("form_submit")).click();
        getDriver().get("BASE_URL_FOR_EMAIL");

        List<String> handles = new ArrayList<String>(getDriver().getWindowHandles());
        getDriver().switchTo().window(handles.get(1));

        getDriver().findElement(By.cssSelector("#inbox-id>input")).sendKeys("saap.by");
        getDriver().findElement(By.cssSelector("#inbox-id > .save.button.small")).click();
        getDriver().findElement(By.cssSelector("#email_list .td2"));
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#email_list .td2")).getText(), "no-reply@the-internet.herokuapp.com ");
        getDriver().findElement(By.cssSelector("#email_list .td2")).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector("email_subject")), "Forgot Password from the-internet");
        getDriver().findElement(By.xpath("//a[text()='http://the-internet.herokuapp.com/login']"));


    }
}
