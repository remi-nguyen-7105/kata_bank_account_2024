package kata.bank.account.domain;

import org.javamoney.moneta.Money;

import java.util.Objects;

public record Deposit(String clientId, Money amount, String accountId) implements Operation {

    public Deposit {
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(accountId);
        if (amount.isNegativeOrZero()) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }
}
