package kata.bank.account.domain;

import kata.bank.account.exception.InsufficientFundException;
import kata.bank.account.exception.UnAutorizedOperationException;
import kata.bank.account.utils.MoneyHelper;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Statement {

    private final List<Operation> operations = new ArrayList<>();

    private final Account account;
    private final Statement previous;
    private LocalDateTime archiveDate;

    public Statement(Account account) {
        Objects.requireNonNull(account);
        this.account = account;
        this.previous = null;
    }

    public Statement(Statement previous) {
        Objects.requireNonNull(previous);
        this.account = previous.getAccount();
        this.previous = previous;
    }

    public String toString() {
        String template = "date :  %s  balance :  %s amount : %s";
        return String.format(template, getDate(), getBalance(), getAmount());
    }

    public Account getAccount() {
        return account;
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
        checkOperation(operation);
        operations.add(operation);
    }

    public void checkOperation(Operation operation) {
        // may need refactoring to service layer
        switch (operation) {
            case Deposit deposit -> checkDeposit(deposit);
            case Withdrawal withdrawal -> checkWithdrawal(withdrawal);
        }
    }

    private void checkClient(String clientId) {
        // may need refactoring to service layer
        if (!account.getClientId().equals(clientId)) {
            throw new UnAutorizedOperationException(clientId);
        }
    }

    private void checkAccount(String accountId) {
        // may need refactoring to service layer
        if (!account.getAccountId().equals(accountId)) {
            throw new UnAutorizedOperationException(accountId);
        }
    }

    private void checkDeposit(Deposit deposit) {
        // may need refactoring to service layer
        checkClient(deposit.clientId());
        checkAccount(deposit.accountId());
    }

    private void checkWithdrawal(Withdrawal withdrawal) {
        // may need refactoring to service layer
        checkClient(withdrawal.clientId());
        checkAccount(withdrawal.accountId());
        if (this.getAmount().isLessThan(withdrawal.amount())) {
            throw new InsufficientFundException(withdrawal.amount().toString());
        }
    }
}
