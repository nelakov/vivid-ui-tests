package vivid.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class VividAuthNegativeTests extends TestBase {

    static Stream<Arguments> dataForNegativeAuthTests() {
        return Stream.of(
                Arguments.of(" ", "Required"),
                Arguments.of("+", "Please provide a valid phone number"),
                Arguments.of("393509577691", "Please provide a valid phone number"),
                Arguments.of("++39 3509577691", "Please provide a valid phone number"),
                Arguments.of("+++39 3509577691", "Please provide a valid phone number")
        );
    }

    @MethodSource(value = "dataForNegativeAuthTests")
    @ParameterizedTest(name = "Validation the Phone input field {0}")
    @DisplayName("Validation the Phone input field")
    void authNegativeTest(String wrongPhone, String errorMessege) {
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
            $(By.cssSelector("[name='PHONE']")).sendKeys(wrongPhone);
        });

        step("Tap the \"Invite me!\" button", () -> {
            $(byText("Invite me!")).click();
        });

        step("The Error text displayed", () -> {
            $(byText(errorMessege)).shouldHave(text(errorMessege));
        });
    }
}
