package kata.bank.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Statement {
    private final List<Operation> operations = new ArrayList<>();
    private Statement previous;
    private LocalDateTime archiveDate;

    public LocalDateTime getDate() {
        return archiveDate != null ? archiveDate : LocalDateTime.now();
    }

    public BigDecimal getAmount() {
        return BigDecimal.ZERO;
    }

    public BigDecimal getBalance() {
        return BigDecimal.ZERO;
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
        //TODO adding additionnal context check here ?
        operations.add(operation);
    }
}
