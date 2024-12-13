package kata.bank.account.domain;

import kata.bank.account.utils.MoneyHelper;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepositTest {

    private Money amount(String value) {
        return Money.of(new BigDecimal(value), MoneyHelper.EUR_CURRENCY_CODE);
    }

    private Money amount(BigDecimal value) {
        return Money.of(value, MoneyHelper.EUR_CURRENCY_CODE);
    }

    @Test
    public void whenCreate_ok() {
        String clientId = "clientId";
        var amount = amount("1000.00");
        String accountId = "accountId";

        new Deposit(clientId, amount, accountId);
    }

    @Test
    public void whenCreate_clientIdMustNotBeNull() {
        var amount = amount("1000.00");
        String accountId = "accountId";

        assertThrows(NullPointerException.class, () -> new Deposit(null, amount, accountId));
    }

    @Test
    public void whenCreate_amountMustNotBeNull() {
        String clientId = "clientId";
        String accountId = "accountId";

        assertThrows(NullPointerException.class, () -> new Deposit(clientId, null, accountId));
    }

    @Test
    public void whenCreate_accountIdMustNotBeNull() {
        String clientId = "clientId";
        var amount = amount("1000.00");

        assertThrows(NullPointerException.class, () -> new Deposit(clientId, amount, null));
    }

    @Test
    public void whenCreate_amountMustNotPositive() {
        String clientId = "clientId";
        var zero = amount(BigDecimal.ZERO);
        var amount = amount("-1000.00");
        String accountId = "accountId";

        assertThrows(IllegalArgumentException.class, () -> new Deposit(clientId, zero, accountId));
        assertThrows(IllegalArgumentException.class, () -> new Deposit(clientId, amount, accountId));
    }
}
