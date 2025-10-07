package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.nio.file.Files;

public class SavingAccountTest {
    SavingAccount save = new SavingAccount(2_000, 1_000, 10_000, 5);

    @BeforeEach
    public void setup() {
        save.balance = 2_000;
        save.minBalance = 1_000;
        save.maxBalance = 10_000;
        save.rate = 5;
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/shouldAddObjectSavingAccount.csv", numLinesToSkip = 1)
    public void shouldAddObjectSavingAccount(int initialBalance, int minBalance, int maxBalance, int rate, int expected) {
        SavingAccount saveAcc = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        Assertions.assertEquals(expected, saveAcc.getBalance());

    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/shouldNotAddObjectSavingAccountWithExeption.csv", numLinesToSkip = 1)
    public void shouldNotAddObjectSavingAccountWithExeption(int initialBalance, int minBalance, int maxBalance, int rate) {

        Assertions.assertThrows(IllegalArgumentException.class, () -> new SavingAccount(initialBalance, minBalance, maxBalance, rate));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/shouldAdd.csv", numLinesToSkip = 1)
    public void shouldAdd(int amount, int expected) {
        Assertions.assertTrue(save.add(amount));

        Assertions.assertEquals(expected, save.getBalance());
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/shouldNotAdd.csv", numLinesToSkip = 1)
    public void shouldNotAdd(int amount, int expected) {
        Assertions.assertFalse(save.add(amount));

        Assertions.assertEquals(expected, save.getBalance());
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/shouldPay.csv", numLinesToSkip = 1)
    public void shouldPay(int amount, int expected) {
        Assertions.assertTrue(save.pay(amount));

        Assertions.assertEquals(expected, save.getBalance());

    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/shouldNotPay.csv", numLinesToSkip = 1)
    public void shouldNotPay(int amount, int expected) {
        save.pay(amount);

        Assertions.assertEquals(expected, save.getBalance());
    }

    @Test
    public void shouldCalculateYearChange() {
        int expected = 100;

        Assertions.assertEquals(expected, save.yearChange());
    }
    @Test
    public void shouldGetMinBalance(){
        Assertions.assertEquals(1000,save.getMinBalance());
    }
    @Test
    public void shouldGetMaxBalance(){
        Assertions.assertEquals(10000,save.getMaxBalance());
    }


}
