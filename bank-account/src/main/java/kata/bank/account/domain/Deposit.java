package kata.bank.account.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record Deposit(String clientId, BigDecimal amount, String accountId) implements Operation {

    public Deposit {
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(accountId);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }
}
