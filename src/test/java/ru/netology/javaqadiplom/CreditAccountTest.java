package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditAccountTest {

    // Тесты для конструктора
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

    @Test
    public void shouldThrowExceptionForNegativeInitialBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(-100, 500, 15);
        });
    }

    @Test
    public void shouldAllowZeroInitialBalance() {
        CreditAccount account = new CreditAccount(0, 500, 15);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void shouldAllowPositiveInitialBalance() {
        CreditAccount account = new CreditAccount(1000, 500, 15);
        Assertions.assertEquals(1000, account.getBalance());
    }

    // Тесты для метода pay()
    @Test
    public void shouldPayCorrectlyWithinCreditLimit() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result = account.pay(1200);

        Assertions.assertTrue(result);
        Assertions.assertEquals(-200, account.getBalance());
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

    @Test
    public void shouldNotPayZeroOrNegativeAmount() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result1 = account.pay(0);
        boolean result2 = account.pay(-100);

        Assertions.assertFalse(result1);
        Assertions.assertFalse(result2);
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    public void shouldPayFromNegativeBalanceWithinLimit() {
        CreditAccount account = new CreditAccount(1000, 500, 15);
        account.pay(1200); // баланс становится -200

        boolean result = account.pay(200);

        Assertions.assertTrue(result);
        Assertions.assertEquals(-400, account.getBalance());
    }

    @Test
    public void shouldNotPayFromNegativeBalanceExceedingLimit() {
        CreditAccount account = new CreditAccount(1000, 500, 15);
        account.pay(1400); // баланс становится -400

        boolean result = account.pay(200); // -400 - 200 = -600 (превышает лимит -500)

        Assertions.assertFalse(result);
        Assertions.assertEquals(-400, account.getBalance());
    }

    // Тесты для метода add()
    @Test
    public void shouldAddAmountToPositiveBalance() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result = account.add(500);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1500, account.getBalance());
    }

    @Test
    public void shouldAddAmountToNegativeBalance() {
        CreditAccount account = new CreditAccount(1000, 500, 15);
        account.pay(1200); // баланс становится -200

        boolean result = account.add(500);

        Assertions.assertTrue(result);
        Assertions.assertEquals(300, account.getBalance());
    }

    @Test
    public void shouldAddAmountToZeroBalance() {
        CreditAccount account = new CreditAccount(0, 500, 15);

        boolean result = account.add(500);

        Assertions.assertTrue(result);
        Assertions.assertEquals(500, account.getBalance());
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

    // Тесты для метода yearChange()
    @Test
    public void shouldCalculateYearChangeForNegativeBalance() {
        CreditAccount account = new CreditAccount(0, 500, 15);
        account.pay(200); // баланс становится -200

        int result = account.yearChange();

        Assertions.assertEquals(-30, result);
    }

    @Test
    public void shouldCalculateYearChangeForSmallNegativeBalance() {
        CreditAccount account = new CreditAccount(0, 500, 10);
        account.pay(50); // баланс становится -50

        int result = account.yearChange();

        Assertions.assertEquals(-5, result);
    }

    @Test
    public void shouldReturnZeroYearChangeForPositiveBalance() {
        CreditAccount account = new CreditAccount(200, 500, 15);

        int result = account.yearChange();

        Assertions.assertEquals(0, result);
    }

    @Test
    public void shouldReturnZeroYearChangeForZeroBalance() {
        CreditAccount account = new CreditAccount(0, 500, 15);

        int result = account.yearChange();

        Assertions.assertEquals(0, result);
    }

    @Test
    public void shouldCalculateYearChangeForLargeNegativeBalance() {
        CreditAccount account = new CreditAccount(0, 2000, 10);
        account.pay(1000); // баланс становится -1000

        int result = account.yearChange();

        Assertions.assertEquals(-100, result);
    }

    // Граничные тесты
    @Test
    public void testPayBoundaryCondition() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result = account.pay(1500);
        Assertions.assertTrue(result);
        Assertions.assertEquals(-500, account.getBalance());

        boolean result2 = account.pay(1);
        Assertions.assertFalse(result2);
        Assertions.assertEquals(-500, account.getBalance());
    }

    @Test
    public void shouldGetCreditLimit() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        int creditLimit = account.getCreditLimit();

        Assertions.assertEquals(500, creditLimit);
    }

    @Test
    public void shouldPayFromZeroBalanceWithinLimit() {
        CreditAccount account = new CreditAccount(0, 500, 15);

        boolean result = account.pay(300);

        Assertions.assertTrue(result);
        Assertions.assertEquals(-300, account.getBalance());
    }

    @Test
    public void shouldNotPayFromZeroBalanceExceedingLimit() {
        CreditAccount account = new CreditAccount(0, 500, 15);

        boolean result = account.pay(600);

        Assertions.assertFalse(result);
        Assertions.assertEquals(0, account.getBalance());
    }
}
