package cloud.autotests.tests;

import cloud.autotests.helpers.DriverUtils;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;


public class VividAuthTest extends TestBase {
    Faker faker = new Faker();
    String email = faker.internet().emailAddress();

    @Test
    @DisplayName("Page title should have header text")
    void titleTest() {
        step("Open url 'https://vivid.money/en-eu/prime/'", () ->
                open("https://vivid.money/en-eu/prime/"));

        step("Page title should have text 'Vivid Prime plan with Premium Features | Vivid Europe'", () -> {
            String expectedTitle = "Vivid Prime plan with Premium Features | Vivid Europe";
            String actualTitle = title();

            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Test
    @DisplayName("Success open free account with use the email")
    void successOpenAccountWithUseEmail() {
        step("Open https://vivid.money/en-eu/", () -> {
            open("https://vivid.money/en-eu/");
        });

        step("Tap on the button \"Open free account\"", () -> {
            $(byText("Open free account")).click();
        });

        step("The invite form header should have a text \"Open your free account now\"", () -> {
            $(".inviteForm__title__YudQx > div").shouldHave(text("Open your free account now"));
        });

        step("The invite form subtitle should have a text \"Enter your phone number or e-mail and we'll send you a link to download the Vivid app\"", () -> {
            $(".inviteForm__subtitle__QmS8b > div").shouldHave(text("Enter your phone number or e-mail and we'll send you a link to download the Vivid app"));
        });

        step("Switch tab phone to email", () -> {
            $(".tabs__tab__RgT_f:nth-child(2)").shouldBe(visible).click();
        });

        step("Input email", () -> {
            executeJavaScript("document.querySelector('[name=EMAIL]').style.opacity = 1");
            $(By.cssSelector("[name='EMAIL']")).sendKeys(email);
        });

        step("Tap the \"Invite me!\" button", () -> {
            $(byText("Invite me!")).click();
        });

        step("The Congrats displayed", () -> {
            $("[data-test-id=invite-success-message]").shouldHave(text("Congrats \uD83C\uDF89 "));
        });
    }

    @Test
    @DisplayName("Success open free account with use the phone number")
    void successOpenAccountWithUsePhoneNumber() {
        step("Open https://vivid.money/en-eu/", () -> {
            open("https://vivid.money/en-eu/");
        });

        step("Tap on the button \"Open free account\"", () -> {
            $(byText("Open free account")).click();
        });

        step("The invite form subtitle should have a text \"Enter your phone number or e-mail and we'll send you a link to download the Vivid app\"", () -> {
            $(".inviteForm__subtitle__QmS8b > div").shouldHave(text("Enter your phone number or e-mail and we'll send you a link to download the Vivid app"));
        });

        step("Input phone number", () -> {
            executeJavaScript("document.querySelector('[name=PHONE]').style.opacity = 1");
            $(By.cssSelector("[name='PHONE']")).sendKeys("+34681993330");
        });

        step("Tap the \"Invite me!\" button", () -> {
            $(byText("Invite me!")).click();
        });

        step("The Congrats displayed", () -> {
            $("[data-test-id=invite-success-message]").shouldHave(text("Congrats \uD83C\uDF89 "));
        });
    }

    @Test
    @DisplayName("Page console log should not have errors")
    void consoleShouldNotHaveErrorsTest() {
        step("Open url 'https://vivid.money/en-eu/prime/'", () ->
            open("https://vivid.money/en-eu/prime/"));

        step("Console logs should not contain text 'SEVERE'", () -> {
            String consoleLogs = DriverUtils.getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }
}