package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlUtils;
import ru.netology.page.TripPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UITest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        String appUrl = System.getProperty("app.url");
        open(appUrl);
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSuccessWithValidDebitCard() throws SQLException {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByDebitCard();
        val validCardInformation = DataHelper.getValidCardInformation();
        CardData.fillCardInformationForSelectedWay(validCardInformation);
        CardData.checkIfPaymentSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByDebitCard = SqlUtils.getStatusForPaymentByDebitCard(paymentId);
        val paymentAmount = SqlUtils.getPaymentAmount(paymentId);
        assertEquals("APPROVED", statusForPaymentByDebitCard);
        assertEquals("4500000", paymentAmount);

    }

    @Test
    void shouldSuccessWithValidCreditCard() throws SQLException {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val validCardInformation = DataHelper.getValidCardInformation();
        CardData.fillCardInformationForSelectedWay(validCardInformation);
        CardData.checkIfPaymentSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByCreditCard = SqlUtils.getStatusForPaymentByCreditCard(paymentId);
        assertEquals("APPROVED", statusForPaymentByCreditCard);
    }

    @Test
    void shouldNotSuccessWithInvalidDebitCard() throws SQLException {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByDebitCard();
        val invalidCardInformation = DataHelper.getInvalidCardInformation();
        CardData.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData.checkIfPaymentNotSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByDebitCard = SqlUtils.getStatusForPaymentByDebitCard(paymentId);
        assertThat(statusForPaymentByDebitCard, equalTo("DECLINED"));
    }

    @Test
    void shouldNotSuccessWithInvalidCreditCard() throws SQLException {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val validCardInformation = DataHelper.getInvalidCardInformation();
        CardData.fillCardInformationForSelectedWay(validCardInformation);
        CardData.checkIfPaymentNotSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByCreditCard = SqlUtils.getStatusForPaymentByCreditCard(paymentId);
        assertThat(statusForPaymentByCreditCard, equalTo("DECLINED"));
    }

    @Test
    void shouldNotSuccessWithWrongCardNumber() {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongLongCardNumber();
        CardData.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData.checkIfWrongFormatOfField();
        val CardData2 = tripPage.selectBuyByDebitCard();
        CardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithShortestCardNumber() {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithShortestCardNumber();
        CardData.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData.checkIfWrongFormatOfField();
        val CardData2 = tripPage.selectBuyByDebitCard();
        CardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongMonth() {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongMonth();
        CardData.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData.checkIfWrongFormatOfField();
        val CardData2 = tripPage.selectBuyByDebitCard();
        CardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongYear() {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongYear();
        CardData.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData.checkIfWrongFormatOfField();
        val CardData2 = tripPage.selectBuyByDebitCard();
        CardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongYearFromOneNumber() {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongYearWithOneNumber();
        CardData.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData.checkIfWrongFormatOfField();
        val CardData2 = tripPage.selectBuyByDebitCard();
        CardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongCVC() {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongCvc();
        CardData.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData.checkIfWrongFormatOfField();
        val CardData2 = tripPage.selectBuyByDebitCard();
        CardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongName() {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongHolderName();
        CardData.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData.checkIfWrongFormatOfField();
        val CardData2 = tripPage.selectBuyByDebitCard();
        CardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithoutName() {
        val tripPage = new TripPage();
        val CardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithoutName();
        CardData.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData.checkIfWrongFormatOfField();
        val CardData2 = tripPage.selectBuyByDebitCard();
        CardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        CardData2.checkIfWrongFormatOfField();
    }
}
