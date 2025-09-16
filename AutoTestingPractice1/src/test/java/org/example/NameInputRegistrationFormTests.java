package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NameInputRegistrationFormTests extends BaseTest{

    String name;
    String inputNameXpath = "//input[@placeholder='Имя']";
    String urlBefore;

    //метод включения переключателей и нажатия на кнопку
    public void switchesAndButtonClick(WebDriver driver) {
        //находим объекты
        WebElement newsSwitch = driver.findElement(By.xpath("//input[@type='checkbox'  and @name='newsletter']"));
        WebElement agreeSwitch = driver.findElement(By.xpath("//input[@type='checkbox'  and @name='agree']"));
        WebElement buttonContinue = driver.findElement(By.xpath("//button[text()='Продолжить']"));

        //листаем страницу до объекта кнопки чтобы захватить и переключатели т.к. они выше кнопки
        scroll(buttonContinue);

        if (!newsSwitch.isSelected()) {
            newsSwitch.click();
        }
        if (!agreeSwitch.isSelected()) {
            agreeSwitch.click();
        }

        //сохраняем адрес, так как после нажатия кнопки может измениться, это нужно дя проверки
        urlBefore = driver.getCurrentUrl();
        buttonContinue.click();
    }

    //ввод информации в текстовые поля
    public void otherDataInput(WebDriver driver) {
        WebElement inputLastName = driver.findElement(By.xpath("//input[@placeholder='Фамилия']"));
        //очищаем на случай если предыдущий тест не прошёл, это быстрее чем обновить страницу так как полей не много
        inputLastName.clear();
        inputLastName.sendKeys("Пивнов");
        WebElement inputMail = driver.findElement(By.xpath("//input[@placeholder='E-Mail']"));
        inputMail.clear();
        inputMail.sendKeys(email);
        WebElement inputPassword = driver.findElement(By.xpath("//input[@placeholder='Пароль']"));
        inputPassword.clear();
        inputPassword.sendKeys("piv999");
    }

    //метод пролистывания страницы вниз
    public void scroll(WebElement element){

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegistrationWithoutInputName() {

        /*
        * находим и листаем до элемента имени
        * вводим данные
        * нажимаем на переключатели и кнопку продолжить
        * ожидаем, пытается ли страница обновиться
        *
        * проверяем изменение адресной строки
        * */
        WebElement inputName = driver.findElement(By.xpath(inputNameXpath));
        scroll(inputName);
        inputName.clear();
        otherDataInput(driver);
        switchesAndButtonClick(driver);


        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("URL изменился, регистрация, прошла", urlBefore, driver.getCurrentUrl());
}

    @Test
    public void testRegistrationWithSpaseSymb() {
        name = " ";
        WebElement inputName = driver.findElement(By.xpath(inputNameXpath));
        inputName.clear();
        inputName.sendKeys(name);
        otherDataInput(driver);
        switchesAndButtonClick(driver);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("URL изменился, регистрация, прошла", urlBefore, driver.getCurrentUrl());
    }

    @Test
    public void testRegistrationWithNameAndNumber() {
        name = "Андрей3526";
        WebElement inputName = driver.findElement(By.xpath(inputNameXpath));
        inputName.clear();
        inputName.sendKeys(name);
        otherDataInput(driver);
        switchesAndButtonClick(driver);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("URL изменился, регистрация, прошла", urlBefore, driver.getCurrentUrl());
    }
}
