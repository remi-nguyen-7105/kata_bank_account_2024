package kata.bank.account.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WithdrawalTest {

    @Test
    public void whenCreate_ok() {
        String clientId = "clientId";
        BigDecimal amount = new BigDecimal("1000.00");
        String accountId = "accountId";

        new Withdrawal(clientId, amount, accountId);
    }

    @Test
    public void whenCreate_clientIdMustNotBeNull() {
        BigDecimal amount = new BigDecimal("1000.00");
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
        BigDecimal amount = new BigDecimal("1000.00");

        assertThrows(NullPointerException.class, () -> new Withdrawal(clientId, amount, null));
    }

    @Test
    public void whenCreate_amountMustNotPositive() {
        String clientId = "clientId";
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal amount = new BigDecimal("-1000.00");
        String accountId = "accountId";

        assertThrows(IllegalArgumentException.class, () -> new Withdrawal(clientId, zero, accountId));
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal(clientId, amount, accountId));
    }
}
