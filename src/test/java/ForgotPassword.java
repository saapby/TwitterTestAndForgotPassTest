import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

import static helpers.DriverSingleton.getDriver;
import static helpers.DriverSingleton.quit;
import static helpers.Helper.enterEmail;

public class ForgotPassword {
    private final static String BASE_URL = "https://the-internet.herokuapp.com";
    private final static String BASE_URL_FOR_EMAIL = "https://www.guerrillamail.com/";
//    private final static String EMAIL_NAME = "brutto";
    private final static String EMAIL_NAME = "bertronium1986";
    private final static String EMAIL_AFTER_DOG = "guerrillamail.de";
    private final static String EMAIL_DOG = "@";
    private By forgetButton = By.id("forget_button");
    private By input = By.cssSelector("input[value='']");
    private By selectCombobox = By.id("gm-host-select");
    private By setButton = By.cssSelector(".save.button.small");
    private int userNumb;
    private int passNumb;


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

        getDriver().get(BASE_URL_FOR_EMAIL);

        enterEmail(EMAIL_NAME, forgetButton, input, EMAIL_AFTER_DOG, selectCombobox, setButton);

        getDriver().get(BASE_URL);
        getDriver().findElement(By.linkText("Forgot Password")).click();
        getDriver().findElement(By.id("email")).sendKeys(EMAIL_NAME + EMAIL_DOG + EMAIL_AFTER_DOG);
        getDriver().findElement(By.id("form_submit")).click();
        getDriver().get(BASE_URL_FOR_EMAIL);

        enterEmail(EMAIL_NAME, forgetButton, input, EMAIL_AFTER_DOG, selectCombobox, setButton);

        WebDriverWait wait = new WebDriverWait(getDriver(), 12);
        wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector("#email_list > tr:nth-child(1) > .td2"), "no-reply@the-internet.herokuapp.com"));

        List<WebElement> emailList = getDriver().findElements(By.cssSelector("#email_list > tr:nth-child(1) > .td2"));
        Assert.assertTrue(emailList.get(0).getText().contains("no-reply@the-internet.herokuapp.com"));
        emailList.get(0).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".email .email_subject")).getText(), "Forgot Password from the-internet");

        String emailBody = getDriver().findElement(By.cssSelector(".email_body")).getText();
        String[] emailBodyText = emailBody.split("\n");
        for (int i = 0; i < emailBodyText.length; i++) {
            if (emailBodyText[i].contains("username:")) {
                userNumb = i;
            }
            if (emailBodyText[i].contains("password:")) {
                passNumb = i;
            }
        }
        String[] user = emailBodyText[userNumb].split(" ");
        String[] pass = emailBodyText[passNumb].split(" ");

        getDriver().get(BASE_URL);
        getDriver().findElement(By.linkText("Form Authentication")).click();
        Assert.assertTrue(getDriver().findElement(By.cssSelector(".radius")).isDisplayed(), "button is invisible");

        getDriver().findElement(By.cssSelector("#username")).sendKeys(user[1]);
        getDriver().findElement(By.cssSelector("#password")).sendKeys(pass[1]);

        getDriver().findElement(By.cssSelector(".radius")).click();
        Assert.assertTrue(getDriver().findElement(By.cssSelector(".button.secondary.radius")).isDisplayed(), "something went wrong, button is invisible");
        Assert.assertEquals("https://the-internet.herokuapp.com/secure", getDriver().getCurrentUrl());
        Assert.assertTrue(getDriver().findElement(By.cssSelector("#flash")).getText().contains("You logged into a secure area!"), "text is not found, ou are not logged in");

    }


}
