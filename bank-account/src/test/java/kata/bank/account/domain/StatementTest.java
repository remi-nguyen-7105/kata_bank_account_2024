package kata.bank.account.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatementTest {

    @Test
    public void whenCreate_ok() {
        new Statement();
    }

    @Test
    public void givenStatement_whenAddDeposit_thenOk() {
        Statement statement = new Statement();
        Deposit deposit = new Deposit("clientId", new BigDecimal("1000.00"), "accountId");
        statement.add(deposit);
    }

    @Test
    public void givenArchivedStatement_whenAddDeposit_thenKo() {
        Statement statement = new Statement();
        statement.archive();
        Deposit deposit = new Deposit("clientId", new BigDecimal("1000.00"), "accountId");

        assertThrows(IllegalStateException.class, () -> statement.add(deposit));
    }


}
