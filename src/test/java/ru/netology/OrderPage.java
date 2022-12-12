package ru.netology;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private WebDriver webdriver;
    private WebElement name;
    private WebElement phone;
    private WebElement checkBox;
    private WebElement submit;
    private WebElement responseText;
    private WebElement responseH2Text;

    public OrderPage(@NotNull WebDriver driver) {
        webdriver = driver;
        name = webdriver.findElement(By.cssSelector("[data-test-id=name] input"));
        phone = webdriver.findElement(By.cssSelector("[data-test-id=phone] input"));
        checkBox = webdriver.findElement(By.cssSelector("[data-test-id=agreement] span:first-child"));
        submit = webdriver.findElement(By.cssSelector("button"));
    }

    public void fillName(@NotNull String name) {
        this.name.clear();
        this.name.sendKeys(name);
    }

    public String getSubNameText() {
        WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(3));
        String selectorStr = "[data-test-id=name] .input__sub";
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selectorStr)));
        } catch (TimeoutException e) {
            return null;
        }
        return webdriver.findElement(By.cssSelector(selectorStr)).getText();
    }

    public void fillPhone(@NotNull String phone) {
        this.phone.clear();
        this.phone.sendKeys(phone);
    }

    public String getSubPhoneText() {
        WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(3));
        String selectorStr = "[data-test-id=phone] .input__sub";
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selectorStr)));
        } catch (TimeoutException e) {
            return null;
        }
        return webdriver.findElement(By.cssSelector(selectorStr)).getText();
    }

    public void clickAgreement() {
        this.checkBox.click();
    }

    public boolean isCheckboxInvalidClass() {
        WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(3));
        String selectorStr = "[data-test-id=agreement]";
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selectorStr)));
        } catch (TimeoutException e) {
            return false;
        }
        String classStr = webdriver.findElement(By.cssSelector(selectorStr)).getAttribute("class");
        return classStr.contains("input_invalid");
    }

    public void clickContinue() {
        this.submit.click();
    }

    public String getH2Text() {
        WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(3));
        String selectorStr = "#root h2";
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selectorStr)));
        } catch (TimeoutException e) {
            return null;
        }
        return webdriver.findElement(By.cssSelector(selectorStr)).getText();
    }

    public String getResponseText() {
        WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(3));
        String selectorStr = "[data-test-id=order-success]";
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selectorStr)));
        } catch (TimeoutException e) {
            return null;
        }
        return webdriver.findElement(By.cssSelector(selectorStr)).getText();
    }
}
