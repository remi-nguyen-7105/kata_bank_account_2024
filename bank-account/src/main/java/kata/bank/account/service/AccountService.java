package kata.bank.account.service;

import kata.bank.account.domain.Account;
import kata.bank.account.domain.Client;

import java.math.BigDecimal;

public interface AccountService {
    // US 1
    void saveMoney(Client client, BigDecimal amount, Account account);

    // US 2
    void retrieveSaving(Client client, BigDecimal amount, Account account);
    
    // requirement
    void printStatement(Account account);
}
