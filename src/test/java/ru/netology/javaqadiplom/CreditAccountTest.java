package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditAccountTest {

    // БАГ #1: Некорректная логика в методе pay()
    @Test
    public void shouldPayCorrectlyWithinCreditLimit() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        // Платеж 1200: 1000 - 1200 = -200 (в пределах лимита -500)
        boolean result = account.pay(1200);

        Assertions.assertTrue(result);
        Assertions.assertEquals(-200, account.getBalance());
    }

    // БАГ #2: Некорректный метод add() (замена баланса вместо добавления)
    @Test
    public void shouldAddAmountToBalance() {
        CreditAccount account = new CreditAccount(1000, 500, 15);

        boolean result = account.add(500);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1500, account.getBalance()); // 1000 + 500 = 1500
    }

    // БАГ #3: Неполная валидация в конструкторе
    // Проверяем, что конструктор НЕ бросает исключение для отрицательного баланса в пределах лимита
    @Test
    public void shouldAllowNegativeInitialBalanceWithinLimit() {
        // Должен разрешить отрицательный баланс в пределах кредитного лимита
        CreditAccount account = new CreditAccount(-300, 500, 15);
        Assertions.assertEquals(-300, account.getBalance());
    }

    // БАГ #3: Проверяем, что конструктор бросает исключение при превышении кредитного лимита
    @Test
    public void shouldThrowExceptionWhenInitialBalanceExceedsCreditLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(-600, 500, 15); // -600 < -500 (превышает лимит)
        });
    }

    // БАГ #4: Некорректный расчет yearChange()
    @Test
    public void shouldCalculateYearChangeCorrectly() {
        CreditAccount account = new CreditAccount(0, 500, 15);
        account.pay(200); // баланс становится -200

        int result = account.yearChange();

        Assertions.assertEquals(-30, result); // 15% от -200 = -30
    }

    @Test
    public void shouldReturnZeroYearChangeForPositiveBalance() {
        CreditAccount account = new CreditAccount(200, 500, 15);

        int result = account.yearChange();

        Assertions.assertEquals(0, result);
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
