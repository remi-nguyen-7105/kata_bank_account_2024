package kata.bank.account.domain;

import org.javamoney.moneta.Money;

import java.util.Objects;

public class Account {
    private final String accountId;
    private Statement currentStatement = new Statement(this);
    private String clientId;

    public Account(String accountId, String clientId) {
        Objects.requireNonNull(accountId);
        Objects.requireNonNull(clientId);
        this.accountId = accountId;
        this.clientId = clientId;
    }

    public void process(Operation operation) {
        currentStatement.add(operation);
    }

    public String getAccountId() {
        return accountId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        Objects.requireNonNull(clientId);
        this.clientId = clientId;
    }

    public Statement getCurrentStatement() {
        return currentStatement;
    }

    public void nextStatement() {
        currentStatement = new Statement(currentStatement);
    }

    public String printCurrentStatement() {
        return currentStatement.toString();
    }

    public Money getAmount() {
        return getCurrentStatement().getAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
