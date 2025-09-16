package org.example;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    protected static WebDriver driver; // поле класса
    private static String baseAddress = "http://217.74.37.176/?route=account/register&language=ru-ru";
    static String email = "pipipivnov@gmail.com";

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.edge.driver",
                "C:\\Users\\user\\Desktop\\testJava\\AutoTestingPractice1\\src\\test\\resources\\msedgedriver.exe");

        driver = new EdgeDriver(); // присваиваем полю класса
        driver.get(baseAddress);
    }

    @After
    public void checkPage(){
        //проверяем, не обновилась ли страница после теста, если обновилась - меняем email, очищаем данные и возвращаемся на страницу регистрации для последующих тестов
        if(!baseAddress.equals(driver.getCurrentUrl())) {
            driver.manage().deleteAllCookies();
            driver.navigate().to(baseAddress);
            email = email+"1";
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

