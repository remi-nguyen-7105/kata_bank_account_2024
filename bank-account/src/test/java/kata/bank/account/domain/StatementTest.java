package kata.bank.account.domain;

import kata.bank.account.utils.MoneyHelper;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatementTest {

    private Money amount(String value) {
        return Money.of(new BigDecimal(value), MoneyHelper.EUR_CURRENCY_CODE);
    }

    @Test
    public void whenCreate_ok() {
        new Statement();
    }

    @Test
    public void givenStatement_whenAddDeposit_thenOk() {
        Statement statement = new Statement();
        Deposit deposit = new Deposit("clientId", amount("1000.00"), "accountId");
        statement.add(deposit);
    }

    @Test
    public void givenArchivedStatement_whenAddDeposit_thenKo() {
        Statement statement = new Statement();
        statement.archive();
        Deposit deposit = new Deposit("clientId", amount("1000.00"), "accountId");

        assertThrows(IllegalStateException.class, () -> statement.add(deposit));
    }

    @Test
    public void givenStatement_whenBalance_calculationOk() {
        Statement statement = new Statement();
        var operation_1 = new Deposit("clientId", amount("1000.01"), "accountId");
        var operation_2 = new Deposit("clientId", amount("1.01"), "accountId");
        var operation_3 = new Deposit("clientId", amount("10.01"), "accountId");
        var operation_4 = new Deposit("clientId", amount("100.01"), "accountId");
        var operation_5 = new Withdrawal("clientId", amount("0.04"), "accountId");
        statement.add(operation_1);
        statement.add(operation_2);
        statement.add(operation_3);
        statement.add(operation_4);
        statement.add(operation_5);
        assertEquals(amount("1111.00"), statement.getBalance(), "inconsistent balance");
    }

    @Test
    public void givenStatement_whenAmount_calculationOk() {
        Statement statement = new Statement();
        var operation_1 = new Deposit("clientId", amount("1000.01"), "accountId");
        var operation_2 = new Deposit("clientId", amount("1.01"), "accountId");
        var operation_3 = new Deposit("clientId", amount("10.01"), "accountId");
        var operation_4 = new Deposit("clientId", amount("100.01"), "accountId");
        var operation_5 = new Withdrawal("clientId", amount("111.04"), "accountId");
        statement.add(operation_1);
        statement.add(operation_2);
        statement.add(operation_3);
        statement.add(operation_4);
        statement.add(operation_5);

        assertEquals(amount("1000"), statement.getAmount(), "inconsistent amount");
    }

    @Test
    public void givenStatementWithPrevious_whenAmount_calculationOk() {
        Statement statement_previous = new Statement();
        var operation_1 = new Deposit("clientId", amount("1000.01"), "accountId");
        var operation_2 = new Deposit("clientId", amount("1.01"), "accountId");
        var operation_3 = new Deposit("clientId", amount("10.01"), "accountId");
        var operation_4 = new Deposit("clientId", amount("100.01"), "accountId");
        var operation_5 = new Withdrawal("clientId", amount("111.04"), "accountId");
        statement_previous.add(operation_1);
        statement_previous.add(operation_2);
        statement_previous.add(operation_3);
        statement_previous.add(operation_4);
        statement_previous.add(operation_5);

        Statement statement = new Statement(statement_previous);
        var operation_6 = new Deposit("clientId", amount("100"), "accountId");
        var operation_7 = new Withdrawal("clientId", amount("200"), "accountId");
        statement.add(operation_6);
        statement.add(operation_7);

        assertEquals(amount("900.0"), statement.getAmount(), "inconsistent amount");
    }

}
