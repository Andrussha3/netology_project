package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditAccountTest {

    // ===== ТЕСТЫ КОНСТРУКТОРА =====

    @Test
    public void shouldThrowExceptionWhenNegativeInitialBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(-100, 5_000, 15);
        }, "Должно выбрасываться исключение при отрицательном начальном балансе");
    }

    @Test
    public void shouldThrowExceptionWhenNegativeCreditLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(1_000, -5_000, 15);
        }, "Должно выбрасываться исключение при отрицательном кредитном лимите");
    }

    @Test
    public void shouldThrowExceptionWhenZeroRate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(1_000, 5_000, 0);
        }, "Должно выбрасываться исключение при нулевой ставке");
    }

    @Test
    public void shouldThrowExceptionWhenNegativeRate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(1_000, 5_000, -15);
        }, "Должно выбрасываться исключение при отрицательной ставке");
    }

    @Test
    public void shouldCreateAccountWithValidParameters() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        Assertions.assertEquals(1_000, account.getBalance());
        Assertions.assertEquals(5_000, account.getCreditLimit());
        Assertions.assertEquals(15, account.getRate());
    }

    // ===== ТЕСТЫ МЕТОДА pay() =====

    @Test
    public void shouldPayWhenBalanceStaysPositive() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.pay(500);

        Assertions.assertTrue(result);
        Assertions.assertEquals(500, account.getBalance());
    }

    @Test
    public void shouldPayWhenBalanceBecomesZero() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.pay(1_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void shouldPayWhenBalanceBecomesNegativeWithinLimit() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.pay(3_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(-2_000, account.getBalance());
    }

    @Test
    public void shouldPayWhenBalanceAtCreditLimit() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.pay(6_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(-5_000, account.getBalance());
    }

    @Test
    public void shouldNotPayWhenExceedsCreditLimit() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.pay(7_000);

        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotPayWhenAmountIsZero() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.pay(0);

        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotPayWhenAmountIsNegative() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.pay(-500);

        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    // ===== ТЕСТЫ МЕТОДА add() =====

    @Test
    public void shouldAddToPositiveBalance() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.add(500);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1_500, account.getBalance());
    }

    @Test
    public void shouldAddToNegativeBalance() {
        CreditAccount account = new CreditAccount(-2_000, 5_000, 15);

        boolean result = account.add(1_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(-1_000, account.getBalance());
    }

    @Test
    public void shouldAddToZeroBalance() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);

        boolean result = account.add(1_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotAddWhenAmountIsZero() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.add(0);

        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotAddWhenAmountIsNegative() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        boolean result = account.add(-500);

        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    // ===== ТЕСТЫ МЕТОДА yearChange() =====

    @Test
    public void shouldReturnZeroForPositiveBalance() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);

        int result = account.yearChange();

        Assertions.assertEquals(0, result);
    }

    @Test
    public void shouldReturnZeroForZeroBalance() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);

        int result = account.yearChange();

        Assertions.assertEquals(0, result);
    }

    @Test
    public void shouldCalculateYearChangeForNegativeBalance() {
        CreditAccount account = new CreditAccount(-200, 5_000, 15);

        int result = account.yearChange();

        Assertions.assertEquals(-30, result); // -200 * 15 / 100 = -30
    }

    @Test
    public void shouldCalculateYearChangeForLargeNegativeBalance() {
        CreditAccount account = new CreditAccount(-1_000, 5_000, 15);

        int result = account.yearChange();

        Assertions.assertEquals(-150, result); // -1000 * 15 / 100 = -150
    }

    @Test
    public void shouldCalculateYearChangeWithRounding() {
        CreditAccount account = new CreditAccount(-333, 5_000, 15);

        int result = account.yearChange();

        Assertions.assertEquals(-49, result); // -333 * 15 / 100 = -49.95 → -49
    }

    // ===== ИНТЕГРАЦИОННЫЕ ТЕСТЫ =====

    @Test
    public void shouldHandleMultipleOperations() {
        CreditAccount account = new CreditAccount(2_000, 5_000, 15);

        account.pay(1_000);  // баланс: 1000
        account.add(500);    // баланс: 1500
        account.pay(3_000);  // баланс: -1500
        account.add(1_000);  // баланс: -500

        Assertions.assertEquals(-500, account.getBalance());
    }

    @Test
    public void shouldNotChangeBalanceWhenPayFails() {
        CreditAccount account = new CreditAccount(1_000, 2_000, 15);

        // Попытка оплаты, превышающей кредитный лимит
        boolean result = account.pay(4_000);

        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }
}