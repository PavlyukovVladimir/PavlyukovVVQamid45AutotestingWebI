package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class CardOrderTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "http://0.0.0.0:9999";

    private OrderPage orderPage;

    @BeforeAll
    public static void setupAll(){
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        // Ждалка
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // пройдем по адресу на форму
        driver.get(baseUrl);
//        driver.manage().window().fullscreen();
        // ждем пока текущий адрес в браузере не станет baseUrl
//        System.out.println(driver.getCurrentUrl());
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/"));
        // создадим страницу для манипуляций с формой
        orderPage = new OrderPage(driver);
    }

    @AfterEach
    public void teardown(){
        driver.quit();
        driver = null;
    }

    @Test
    public void happySendFormTest(){
        orderPage.fillName("Василий");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = orderPage.getResponseText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void happySendFormDashTest(){
        orderPage.fillName("Василий-Мазилий-Бразилий");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = orderPage.getResponseText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void happySendFormWithSpacesTest(){
        orderPage.fillName("Василий Мазилий Бразилий");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = orderPage.getResponseText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void happySendFormWithYoLargeTest(){
        orderPage.fillName("Ёжик");
        orderPage.fillPhone("+31111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = orderPage.getResponseText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void happySendFormWithYoSmallTest(){
        orderPage.fillName("Мистер ё");
        orderPage.fillPhone("+31111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = orderPage.getResponseText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void happySendFormPhoneAndNameWithStartEndSpacesTest(){
        orderPage.fillName("    Мусик-Пусик  ");
        orderPage.fillPhone("   +71111111111    ");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = orderPage.getResponseText().trim();
        assertEquals(expected, actual);
    }

    // <editor-fold desc="Name">
    @Test
    public void negativeNameNotCyrillicTest(){
        orderPage.fillName("Vasya");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativeNameSpaceOnlyTest(){
        orderPage.fillName("       ");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Поле обязательно для заполнения";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativeNameEmptyTest(){
        orderPage.fillName("");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Поле обязательно для заполнения";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativeNameOneDashOnlyTest(){
        orderPage.fillName("-");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativeNameDashFirstTest(){
        orderPage.fillName("-Маня");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativeNameDashLastTest(){
        orderPage.fillName("Маня-");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativeNameWithDigitTest(){
        orderPage.fillName("Петр 1");
        orderPage.fillPhone("+71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }
    // </editor-fold>

    // <editor-fold desc="Phone">
    @Test
    public void negativePhoneWithoutFirstPlusTest(){
        orderPage.fillName("Мишель Гарсия Идальго-Бахмутова");
        orderPage.fillPhone("71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativePhoneWithDoubleFirstPlusTest(){
        orderPage.fillName("Мишель Гарсия Идальго-Бахмутова");
        orderPage.fillPhone("++71111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativePhoneEmptyTest(){
        orderPage.fillName("Мишель Гарсия Идальго-Бахмутова");
        orderPage.fillPhone("");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Поле обязательно для заполнения";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativePhoneWithSpacesInsideTest(){
        orderPage.fillName("Мишель Гарсия Идальго-Бахмутова");
        orderPage.fillPhone("+7 1111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativePhoneShortTest(){
        orderPage.fillName("Софья Ковалевская");
        orderPage.fillPhone("+7111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativePhoneLongTest(){
        orderPage.fillName("Софья Ковалевская");
        orderPage.fillPhone("+711111111111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void negativePhoneWithLetterTest(){
        orderPage.fillName("Софья Ковалевская");
        orderPage.fillPhone("+711111ф1111");
        orderPage.clickAgreement();
        orderPage.clickContinue();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = orderPage.getSubTextOfInvalid().trim();
        assertEquals(expected, actual);
    }
    // </editor-fold>

    // <editor-fold desc="Flag">
    @Test
    public void negativeFlagTest(){
        orderPage.fillName("Александр Панкратов-черный");
        orderPage.fillPhone("+91111111111");
        orderPage.clickContinue();
        assertTrue(orderPage.isCheckboxInvalidClass());
    }
    // </editor-fold>
}
