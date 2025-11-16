package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards = $$(".list__item");
    private final SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var last4 = cardInfo.getLastDigits();
        SelenideElement card = cards.findBy(text(last4));
        String text = card.getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        var last4 = cardInfo.getLastDigits();
        SelenideElement card = cards.findBy(text(last4));
        card.$("[data-test-id=action-deposit]").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        // Пример текста карты:
        // "**** **** **** 0001\nбаланс: 10000 р."
        int index = text.indexOf("баланс:");
        if (index == -1) {
            index = text.indexOf("Баланс:");
        }
        int start = index + "баланс:".length();
        int end = text.indexOf("р", start);
        String value = text.substring(start, end).replaceAll("\\s+", "");
        return Integer.parseInt(value);
    }
}
