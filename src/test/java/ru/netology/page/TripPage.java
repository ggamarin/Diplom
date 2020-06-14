package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TripPage {
    private SelenideElement buttonBuyByDebit = $(byText("Купить"));
    private SelenideElement buttonBuyCredit = $(byText("Купить в кредит"));
    private SelenideElement paymentBySelectedWayHeader = $("#root > div > h3");


    public CardData selectBuyByDebitCard() {
        buttonBuyByDebit.click();
        paymentBySelectedWayHeader.shouldHave(Condition.text("Оплата по карте"));
        return new CardData();
    }

    public CardData selectBuyByCreditCard() {
        buttonBuyCredit.click();
        paymentBySelectedWayHeader.shouldHave(Condition.text("Кредит по данным карты"));
        return new CardData();
    }
}
