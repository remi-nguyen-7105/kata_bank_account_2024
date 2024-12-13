package kata.bank.account.service;

import kata.bank.account.domain.Deposit;
import kata.bank.account.domain.Withdrawal;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {
    @Override
    public void saveMoney(String clientId, BigDecimal amount, String accountId) {
        Deposit deposit = new Deposit(clientId, amount, accountId);
        //TODO
    }

    @Override
    public void retrieveSaving(String clientId, BigDecimal amount, String accountId) {
        Withdrawal withdrawal = new Withdrawal(clientId, amount, accountId);
        //TODO
    }

    @Override
    public void printStatement(String accountId) {
        //TODO
    }
}
