package kata.bank.account.domain;

import kata.bank.account.utils.MoneyHelper;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WithdrawalTest {

    private Money amount1000() {
        return amount("1000.00");
    }

    private Money amount(String value) {
        return Money.of(new BigDecimal(value), MoneyHelper.EUR_CURRENCY_CODE);
    }

    private Money amount(BigDecimal value) {
        return Money.of(value, MoneyHelper.EUR_CURRENCY_CODE);
    }

    @Test
    public void whenCreate_ok() {
        String clientId = "clientId";
        Money amount = amount1000();
        String accountId = "accountId";

        new Withdrawal(clientId, amount, accountId);
    }

    @Test
    public void whenCreate_clientIdMustNotBeNull() {
        Money amount = amount1000();
        String accountId = "accountId";

        assertThrows(NullPointerException.class, () -> new Withdrawal(null, amount, accountId));
    }

    @Test
    public void whenCreate_amountMustNotBeNull() {
        String clientId = "clientId";
        String accountId = "accountId";

        assertThrows(NullPointerException.class, () -> new Withdrawal(clientId, null, accountId));
    }

    @Test
    public void whenCreate_accountIdMustNotBeNull() {
        String clientId = "clientId";
        Money amount = amount1000();

        assertThrows(NullPointerException.class, () -> new Withdrawal(clientId, amount, null));
    }

    @Test
    public void whenCreate_amountMustNotPositive() {
        String clientId = "clientId";
        Money zero = amount(BigDecimal.ZERO);
        Money amount = amount("-1000.00");
        String accountId = "accountId";

        assertThrows(IllegalArgumentException.class, () -> new Withdrawal(clientId, zero, accountId));
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal(clientId, amount, accountId));
    }
}
