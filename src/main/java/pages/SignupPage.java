package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static helpers.DriverSingleton.getDriver;
import static helpers.Locators.get;

/**
 * Created by Admin on 06.11.2015.
 */
public class SignupPage {
    public final static By USER_NAME_FIELD = get("signup.username.field");
    public final static By USER_EMAIL_FIELD = get("signup.email.field");
    public final static By PASSWORD_FIELD = get("signup.password.field");
    public final static By SIGN_UP_BUTTON = get("signup.button");

    public final static By active_valid_mess = get("signup.activeValidMess");
    public final static By active_email_valid_mess = get("signup.emailValidMess");
    public final static By active_pass_valid_mess = get("signup.passValidMess");
    public final static By active_user_valid_mess = get("signup.userValidMess");


    public static void enterFields(String user, String email, String pass) {
        fillForm(user, email, pass);
        getDriver().findElement(SIGN_UP_BUTTON).click();
    }

    public static void fillForm(String user, String email, String pass) {
        getDriver().findElement(USER_NAME_FIELD).sendKeys(user);
        getDriver().findElement(USER_EMAIL_FIELD).sendKeys(email);
        getDriver().findElement(PASSWORD_FIELD).sendKeys(pass);
    }

    public static List<WebElement> getValidMess() {
        return getDriver().findElements(active_valid_mess);
    }
}
