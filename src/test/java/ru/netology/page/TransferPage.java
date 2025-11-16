package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement fromField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");

    public DashboardPage makeValidTransfer(int amount, DataHelper.CardInfo fromCard) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        amountField.sendKeys(Keys.DELETE);
        amountField.setValue(String.valueOf(amount));

        fromField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        fromField.sendKeys(Keys.DELETE);
        fromField.setValue(fromCard.getNumber());

        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage cancel() {
        cancelButton.click();
        return new DashboardPage();
    }
}
