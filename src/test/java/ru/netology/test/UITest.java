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
    public void shouldCheckIfSuccessWithValidCardInformationPaymentByDebitCard() throws SQLException {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByDebitCard();
        val validCardInformation = DataHelper.getValidCardInformation();
        fillingInCardData.fillCardInformationForSelectedWay(validCardInformation);
        fillingInCardData.checkIfPaymentSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByDebitCard = SqlUtils.getStatusForPaymentByDebitCard(paymentId);
        val paymentAmount = SqlUtils.getPaymentAmount(paymentId);
        assertEquals("APPROVED", statusForPaymentByDebitCard);
        assertEquals("4500000", paymentAmount);

    }

    @Test
    public void shouldCheckIfSuccessWithValidCardInformationPaymentByCreditCard() throws SQLException {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val validCardInformation = DataHelper.getValidCardInformation();
        fillingInCardData.fillCardInformationForSelectedWay(validCardInformation);
        fillingInCardData.checkIfPaymentSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByCreditCard = SqlUtils.getStatusForPaymentByCreditCard(paymentId);
        assertEquals("APPROVED", statusForPaymentByCreditCard);
    }

    @Test
    public void shouldCheckIfNotSuccessWithInvalidCardInformationPaymentByDebitCard() throws SQLException {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByDebitCard();
        val invalidCardInformation = DataHelper.getInvalidCardInformation();
        fillingInCardData.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData.checkIfPaymentNotSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByDebitCard = SqlUtils.getStatusForPaymentByDebitCard(paymentId);
        assertThat(statusForPaymentByDebitCard, equalTo("DECLINED"));
    }

    @Test
    public void shouldCheckIfNotSuccessWithInvalidCardInformationPaymentByCreditCard() throws SQLException {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val validCardInformation = DataHelper.getInvalidCardInformation();
        fillingInCardData.fillCardInformationForSelectedWay(validCardInformation);
        fillingInCardData.checkIfPaymentNotSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByCreditCard = SqlUtils.getStatusForPaymentByCreditCard(paymentId);
        assertThat(statusForPaymentByCreditCard, equalTo("DECLINED"));
    }

    @Test
    public void shouldCheckIfNotSuccessWithWrongCardNumber() {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongLongCardNumber();
        fillingInCardData.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData.checkIfWrongFormatOfField();
        val fillingInCardData2 = tripProposalPage.selectBuyByDebitCard();
        fillingInCardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData2.checkIfWrongFormatOfField();
    }

    @Test
    public void shouldCheckIfNotSuccessWithShortestCardNumber() {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithShortestCardNumber();
        fillingInCardData.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData.checkIfWrongFormatOfField();
        val fillingInCardData2 = tripProposalPage.selectBuyByDebitCard();
        fillingInCardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData2.checkIfWrongFormatOfField();
    }

    @Test
    public void shouldCheckIfNotSuccessWithWrongMonth() {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongMonth();
        fillingInCardData.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData.checkIfWrongFormatOfField();
        val fillingInCardData2 = tripProposalPage.selectBuyByDebitCard();
        fillingInCardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData2.checkIfWrongFormatOfField();
    }

    @Test
    public void shouldCheckIfNotSuccessWithWrongYear() {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongYear();
        fillingInCardData.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData.checkIfWrongFormatOfField();
        val fillingInCardData2 = tripProposalPage.selectBuyByDebitCard();
        fillingInCardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData2.checkIfWrongFormatOfField();
    }

    @Test
    public void shouldCheckIfNotSuccessWithWrongYearFromOneNumber() {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongYearWithOneNumber();
        fillingInCardData.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData.checkIfWrongFormatOfField();
        val fillingInCardData2 = tripProposalPage.selectBuyByDebitCard();
        fillingInCardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData2.checkIfWrongFormatOfField();
    }

    @Test
    public void shouldCheckIfNotSuccessWithWrongCVC() {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongCvc();
        fillingInCardData.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData.checkIfWrongFormatOfField();
        val fillingInCardData2 = tripProposalPage.selectBuyByDebitCard();
        fillingInCardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData2.checkIfWrongFormatOfField();
    }

    @Test
    public void shouldCheckIfNotSuccessWithWrongName() {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongHolderName();
        fillingInCardData.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData.checkIfWrongFormatOfField();
        val fillingInCardData2 = tripProposalPage.selectBuyByDebitCard();
        fillingInCardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData2.checkIfWrongFormatOfField();
    }

    @Test
    public void shouldCheckIfNotSuccessWithoutName() {
        val tripProposalPage = new TripPage();
        val fillingInCardData = tripProposalPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithoutName();
        fillingInCardData.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData.checkIfWrongFormatOfField();
        val fillingInCardData2 = tripProposalPage.selectBuyByDebitCard();
        fillingInCardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        fillingInCardData2.checkIfWrongFormatOfField();
    }
}
