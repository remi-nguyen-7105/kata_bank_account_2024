package kata.bank.account.domain;

import java.util.Objects;

public class Account {

    private final String accountId;

    public Account(String accountId) {
        Objects.requireNonNull(accountId);
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
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
