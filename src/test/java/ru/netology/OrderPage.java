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
    private String nameSelector = "[data-test-id=name] input";
    private String phoneSelector = "[data-test-id=phone] input";
    private String checkBoxSelector = "[data-test-id=agreement] .checkbox__box";
    private String agreementLabelSelector = "[data-test-id=agreement]";
    private String submitSelector = "button";
    private String messageOfInvalidSelector = ".input_invalid .input__sub";
    private String responseMessageSelector = "[data-test-id=order-success]";

    private WebElement css(@NotNull String selectorStr){
        int timeout = 3;
        WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(timeout));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selectorStr)));
        } catch (TimeoutException e) {
            throw new AssertionError("The element was not found using the selector \"" +
                    selectorStr + "\" within " + timeout + " seconds");
        }
        return webdriver.findElement(By.cssSelector(selectorStr));
    }
    public OrderPage(@NotNull WebDriver driver) {
        webdriver = driver;
    }

    public void fillName(@NotNull String name) {
        WebElement nameInput = css(nameSelector);
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public String getSubTextOfInvalid() {
        return css(messageOfInvalidSelector).getText();
    }

    public void fillPhone(@NotNull String phone) {
        WebElement phoneInput = css(phoneSelector);
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void clickAgreement() {
        css(checkBoxSelector).click();
    }

    public boolean isCheckboxInvalidClass() {
        String classStr = css(agreementLabelSelector).getAttribute("class");
        return classStr.contains("input_invalid");
    }

    public void clickContinue() {
        css(submitSelector).click();
    }

    public String getResponseText() {
        return css(responseMessageSelector).getText();
    }
}
