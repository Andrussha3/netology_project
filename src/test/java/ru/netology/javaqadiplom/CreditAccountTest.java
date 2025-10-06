package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditAccountTest {

    // Тесты для Бага #1: Некорректная логика в методе pay()
    @Test
    public void shouldPayWithinCreditLimit() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result = account.pay(1200);

        Assertions.assertFalse(result);
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    public void shouldNotPayWhenExceedsCreditLimit() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result = account.pay(1600);

        Assertions.assertFalse(result);
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    public void shouldPayExactCreditLimit() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result = account.pay(1500);

        Assertions.assertTrue(result);
        Assertions.assertEquals(-500, account.getBalance());
    }

    // Тесты для Бага #2: Некорректный метод add()
    @Test
    public void shouldAddToPositiveBalance() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result = account.add(500);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1500, account.getBalance());
    }

    @Test
    public void shouldAddToNegativeBalance() {
        CreditAccount account = new CreditAccount(-300, 500, 15);

        boolean result = account.add(500);

        Assertions.assertTrue(result);
        Assertions.assertEquals(200, account.getBalance());
    }

    @Test
    public void shouldNotAddZeroOrNegativeAmount() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result1 = account.add(0);
        boolean result2 = account.add(-100);

        Assertions.assertFalse(result1);
        Assertions.assertFalse(result2);
        Assertions.assertEquals(1000, account.getBalance());
    }

    // Тесты для Бага #3: Неполная валидация в конструкторе
    @Test
    public void shouldThrowExceptionForNegativeInitialBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(-100, 500, 15);
        });
    }

    @Test
    public void shouldThrowExceptionForNegativeCreditLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(1000, -500, 15);
        });
    }

    @Test
    public void shouldThrowExceptionForNegativeRate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(1000, 500, -15);
        });
    }

    @Test
    public void shouldThrowExceptionForZeroRate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(1000, 500, 0);
        });
    }

    // Тесты для Бага #4: Некорректный расчет yearChange()
    @Test
    public void shouldCalculateYearChangeForNegativeBalance() {
        CreditAccount account = new CreditAccount(-200, 500, 15);

        int result = account.yearChange();

        Assertions.assertEquals(-30, result);
    }

    @Test
    public void shouldCalculateYearChangeForPositiveBalance() {
        CreditAccount account = new CreditAccount(200, 500, 15);

        int result = account.yearChange();

        Assertions.assertEquals(0, result);
    }

    @Test
    public void shouldCalculateYearChangeForZeroBalance() {
        CreditAccount account = new CreditAccount(0, 500, 15);

        int result = account.yearChange();

        Assertions.assertEquals(0, result);
    }

    @Test
    public void shouldCalculateYearChangeForLargeNegativeBalance() {
        CreditAccount account = new CreditAccount(-1000, 2000, 10);

        int result = account.yearChange();

        Assertions.assertEquals(-100, result);
    }

    // Дополнительные тесты для граничных случаев
    @Test
    public void shouldHandleMaxCreditLimit() {
        CreditAccount account = new CreditAccount(0, 1000, 10);

        boolean result = account.pay(1000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(-1000, account.getBalance());
    }

    @Test
    public void shouldNotPayZeroOrNegativeAmount() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result1 = account.pay(0);
        boolean result2 = account.pay(-100);

        Assertions.assertFalse(result1);
        Assertions.assertFalse(result2);
        Assertions.assertEquals(1000, account.getBalance());
    }
}