package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = false;
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {

        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();

        int amount = 1000;

        int initialFirstBalance = dashboardPage.getCardBalance(firstCard);
        int initialSecondBalance = dashboardPage.getCardBalance(secondCard);

        var transferPage = dashboardPage.selectCardToTransfer(secondCard);
        dashboardPage = transferPage.makeValidTransfer(amount, firstCard);

        int finalFirstBalance = dashboardPage.getCardBalance(firstCard);
        int finalSecondBalance = dashboardPage.getCardBalance(secondCard);

        assertEquals(initialFirstBalance - amount, finalFirstBalance);
        assertEquals(initialSecondBalance + amount, finalSecondBalance);
    }
}