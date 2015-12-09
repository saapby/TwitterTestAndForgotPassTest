package helpers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static helpers.DriverSingleton.getDriver;

/**
 * Created by Admin on 06.11.2015.
 */
public class Helper {

    public static List<String> readAllLines(String resourcePath) throws IOException {
        return Files.readAllLines(new File(resourcePath).toPath(), Charset.defaultCharset());
    }

    public static void saveScreenshot(String path) {
        TakesScreenshot screenMaker = (TakesScreenshot)getDriver();
        File screen = screenMaker.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screen, new File(path));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveScreenshot(By element, String path) {
        TakesScreenshot screenMaker = (TakesScreenshot)getDriver().findElement(element);
        File screen = screenMaker.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screen, new File(path));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean isElementPresent(By locator) {
        try {
            getDriver().findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void enterEmail(String emailName, By forgetButton, By input, String emailAfterDog, By selectCombobox, By setButton) {
        getDriver().findElement(forgetButton).click();
        if (isElementPresent(input)) {
            getDriver().findElement(input).sendKeys(emailName);
//            System.out.println("test pustoe pole vvoda, i v nego vveli znachenie");
        } else {
            getDriver().findElement(forgetButton).click();
            getDriver().findElement(input).sendKeys(emailName);
//            System.out.println("test ni to ni drugoe, i v nego vveli znachenie");
        }

        Select selectBox = new Select(getDriver().findElement(selectCombobox));
        selectBox.selectByValue(emailAfterDog);
        getDriver().findElement(setButton).click();
    }
}
