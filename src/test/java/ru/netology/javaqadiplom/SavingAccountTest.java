package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SavingAccountTest {

    @Test
    public void shouldAddLessThanMaxBalance() {
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                5
        );

        account.add(3_000);

        Assertions.assertEquals(2_000 + 3_000, account.getBalance());
    }
    @Test
    public void shouldPayWhenBalanceBecomesEqualToMinBalance() {
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                5
        );

        // Оплата 1000 рублей должна пройти успешно,
        // так как баланс станет равным minBalance (1000)
        boolean result = account.pay(1_000);

        Assertions.assertTrue(result, "Оплата должна пройти успешно когда баланс становится равным minBalance");
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotPayWhenBalanceBecomesLessThanMinBalance() {
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                5
        );

        // Оплата 1001 рублей должна быть отклонена,
        // так как баланс станет меньше minBalance (999 < 1000)
        boolean result = account.pay(1_001);

        Assertions.assertFalse(result, "Оплата должна быть отклонена когда баланс становится меньше minBalance");
        Assertions.assertEquals(2_000, account.getBalance());
    }

}
