package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulOrderWithBelgorodAndEvgeniy() {
        $("[data-test-id='city'] input").setValue("Белгород");

        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);

        $("[data-test-id='name'] input").setValue("Евгений Степанович");

        $("[data-test-id='phone'] input").setValue("+89092064650");

        $("[data-test-id='agreement'] .checkbox__box").click();

        $$(".button").find(Condition.visible).click();

        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}