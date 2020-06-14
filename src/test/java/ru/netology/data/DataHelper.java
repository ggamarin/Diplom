package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.util.Random;

public class DataHelper {
    static Faker faker = new Faker(new Locale("en"));
    static Random random = new Random();
    static int numberMonth = 1 + random.nextInt(9 - 1);
    static String wrongMonthNumber = Integer.toString(numberMonth);
    static int cvc = 100 + random.nextInt(999 - 100);
    static String correctCVC = Integer.toString(cvc);
    static int year = 26 + random.nextInt(99 - 26);
    static String wrongYear = Integer.toString(year);

    private DataHelper() {

    }

    public static CardInformation getValidCardInformation() {
        return new CardInformation("4444 4444 4444 4441", "22", "11", faker.name().fullName(), correctCVC);
    }

    public static CardInformation getInvalidCardInformation() {
        return new CardInformation("4444 4444 4444 4442", "22", "11", faker.name().fullName(), correctCVC);
    }

    public static CardInformation getCardInformationWithWrongLongCardNumber() {

        return new CardInformation("4444 4444 4444 444", "22", "11", faker.name().fullName(), correctCVC);
    }

    public static CardInformation getCardInformationWithShortestCardNumber() {

        return new CardInformation(wrongMonthNumber, "22", "11", faker.name().fullName(), correctCVC);
    }

    public static CardInformation getCardInformationWithWrongMonth() {
        return new CardInformation("4444 4444 4444 4441", "22", wrongMonthNumber, faker.name().fullName(), correctCVC);
    }

    public static CardInformation getCardInformationWithWrongYear() {
        return new CardInformation("4444 4444 4444 4441", wrongYear, "12", faker.name().fullName(), correctCVC);
    }

    public static CardInformation getCardInformationWithWrongYearWithOneNumber() {
        return new CardInformation("4444 4444 4444 4441", wrongMonthNumber, "12", faker.name().fullName(), correctCVC);
    }

    public static CardInformation getCardInformationWithWrongCvc() {
        return new CardInformation("4444 4444 4444 4441", "22", "11", faker.name().fullName(), wrongYear);
    }

    public static CardInformation getCardInformationWithWrongHolderName() {
        return new CardInformation("4444 4444 4444 4441", "22", "11", correctCVC, correctCVC);
    }

    public static CardInformation getCardInformationWithoutName() {
        return new CardInformation("4444 4444 4444 4441", "22", "11", " ", correctCVC);
    }

    @Value
    public static class CardInformation {
        private String number, year, month, holder, cvc;
    }
}
