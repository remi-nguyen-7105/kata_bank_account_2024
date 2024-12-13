package kata.bank.account.domain;

import java.util.Objects;

public class Account {

    private final String accountId;
    private String clientId;

    public Account(String accountId, String clientId) {
        Objects.requireNonNull(accountId);
        Objects.requireNonNull(clientId);
        this.accountId = accountId;
        this.clientId = clientId;
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
