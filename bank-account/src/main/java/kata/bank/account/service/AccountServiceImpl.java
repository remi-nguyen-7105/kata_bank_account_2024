package kata.bank.account.service;

import kata.bank.account.domain.Deposit;
import kata.bank.account.domain.Withdrawal;
import kata.bank.account.utils.MoneyHelper;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {
    @Override
    public void saveMoney(String clientId, BigDecimal amount, String accountId) {
        Money moneyAmount = Money.of(amount, MoneyHelper.EUR_CURRENCY_CODE);
        Deposit deposit = new Deposit(clientId, moneyAmount, accountId);
        //TODO
    }

    @Override
    public void retrieveSaving(String clientId, BigDecimal amount, String accountId) {
        Money moneyAmount = Money.of(amount, MoneyHelper.EUR_CURRENCY_CODE);
        Withdrawal withdrawal = new Withdrawal(clientId, moneyAmount, accountId);
        //TODO
    }

    @Override
    public void printStatement(String accountId) {
        //TODO
    }
}
