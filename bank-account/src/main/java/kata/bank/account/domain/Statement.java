package kata.bank.account.domain;

import kata.bank.account.utils.MoneyHelper;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Statement {

    private final List<Operation> operations = new ArrayList<>();
    private final Statement previous;
    private LocalDateTime archiveDate;

    public Statement() {
        this(null);
    }

    public Statement(Statement previous) {
        this.previous = previous;
    }

    public LocalDateTime getDate() {
        return archiveDate != null ? archiveDate : LocalDateTime.now();
    }

    public Money getAmount() {
        return previous != null ? previous.getAmount().add(getBalance()) : getBalance();
    }

    public Money getBalance() {
        Money balance = Money.of(BigDecimal.ZERO, MoneyHelper.EUR_CURRENCY_CODE);
        for (Operation operation : operations) {
            switch (operation) {
                case Deposit deposit -> balance = balance.add(deposit.amount());
                case Withdrawal withdrawal -> balance = balance.add(withdrawal.amount().negate());
            }
        }
        return balance;
    }

    public boolean isArchived() {
        return archiveDate != null;
    }

    public void archive() {
        if (!isArchived()) {
            archiveDate = LocalDateTime.now();
        }
    }

    public void add(Operation operation) {
        if (isArchived()) {
            throw new IllegalStateException("Statement is archived");
        }
        //TODO adding additional context check here ?
        operations.add(operation);
    }
}
