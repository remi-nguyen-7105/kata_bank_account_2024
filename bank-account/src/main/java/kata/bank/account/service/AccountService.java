package kata.bank.account.service;

import java.math.BigDecimal;

public interface AccountService {
    // US 1
    void saveMoney(String clientId, BigDecimal amount, String accountId);

    // US 2
    void retrieveSaving(String clientId, BigDecimal amount, String accountId);

    // requirement
    String printStatement(String accountId);

    void nextStatement(String accountId);
}
