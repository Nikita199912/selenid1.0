package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldCardDeliveryTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Белгород");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(6, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Евгений Степанович");
        $("[data-test-id=phone] input").setValue("+79092064650");
        $("[data-test-id=agreement]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(30));
        $("button").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(30));
        $(withText("Встреча успешно забронирована")).shouldBe(Condition.visible, Duration.ofSeconds(30));
        $("[data-test-id=notification]").shouldHave(Condition.text("Успешно!\n" +
                "Встреча успешно забронирована на " + generateDate(6, "dd.MM.yyyy"))).shouldBe(visible);
    }
}