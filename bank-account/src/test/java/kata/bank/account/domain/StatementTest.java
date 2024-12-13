package kata.bank.account.domain;

import kata.bank.account.exception.InsufficientFundException;
import kata.bank.account.exception.UnAutorizedOperationException;
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

    private Account anAccount() {
        return new Account("accountId", "clientId");
    }

    private Statement aStatement() {
        return new Statement(anAccount());
    }

    @Test
    public void whenCreate_ok() {
        aStatement();
    }

    @Test
    public void whenCreate_no_previous_ko() {
        assertThrows(NullPointerException.class, () -> new Statement((Statement) null));
    }

    @Test
    public void whenCreate_no_account_ko() {
        assertThrows(NullPointerException.class, () -> new Statement((Account) null));
    }

    @Test
    public void givenStatement_whenAddDeposit_thenOk() {
        Deposit deposit = new Deposit("clientId", amount("1000.00"), "accountId");
        aStatement().add(deposit);
    }

    @Test
    public void givenArchivedStatement_whenAddDeposit_thenKo() {
        Statement statement = aStatement();
        statement.archive();
        Deposit deposit = new Deposit("clientId", amount("1000.00"), "accountId");

        assertThrows(IllegalStateException.class, () -> statement.add(deposit));
    }

    @Test
    public void givenStatement_whenBalance_calculationOk() {
        Statement statement = aStatement();
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
        Statement statement = aStatement();
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
        Statement statement_previous = aStatement();
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

    @Test
    public void givenStatement_whenAddOperation_clientIdMustMatch() {
        Statement statement = aStatement();
        var deposit_ko = new Deposit("anotherClientId", amount("1000.01"), statement.getAccount().getAccountId());
        assertThrows(UnAutorizedOperationException.class, () -> statement.add(deposit_ko));

        var deposit_ok = new Deposit(aStatement().getAccount().getClientId(), amount("1000.01"), statement.getAccount().getAccountId());
        var withdrawal = new Withdrawal("anotherClientId", amount("1"), statement.getAccount().getAccountId());
        statement.add(deposit_ok);
        assertThrows(UnAutorizedOperationException.class, () -> statement.add(withdrawal));
    }

    @Test
    public void givenStatement_whenAddOperation_accountIdMustMatch() {
        Statement statement = aStatement();
        var deposit_ko = new Deposit(aStatement().getAccount().getClientId(), amount("1000.01"), "anotherAccountId");
        assertThrows(UnAutorizedOperationException.class, () -> statement.add(deposit_ko));

        var deposit_ok = new Deposit(aStatement().getAccount().getClientId(), amount("1000.01"), statement.getAccount().getAccountId());
        var withdrawal = new Withdrawal(aStatement().getAccount().getClientId(), amount("1"), "anotherAccountId");
        statement.add(deposit_ok);
        assertThrows(UnAutorizedOperationException.class, () -> statement.add(withdrawal));
    }

    @Test
    public void givenStatement_whenAddWithdrawal_fundMustBeSufficient() {
        Statement statement = aStatement();
        var deposit_ok = new Deposit(aStatement().getAccount().getClientId(), amount("1000.00"), statement.getAccount().getAccountId());
        var withdrawal_ko = new Withdrawal(aStatement().getAccount().getClientId(), amount("1000.01"), statement.getAccount().getAccountId());
        statement.add(deposit_ok);

        assertThrows(InsufficientFundException.class, () -> statement.add(withdrawal_ko));
    }
}
