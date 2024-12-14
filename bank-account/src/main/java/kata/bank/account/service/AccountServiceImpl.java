package kata.bank.account.service;

import kata.bank.account.domain.Account;
import kata.bank.account.domain.Deposit;
import kata.bank.account.domain.Withdrawal;
import kata.bank.account.utils.MoneyHelper;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountServiceImpl implements AccountService {

    private final Repository<String, Account> accountRepository;

    public AccountServiceImpl(Repository<String, Account> accountRepository) {
        Objects.requireNonNull(accountRepository);
        this.accountRepository = accountRepository;
    }

    @Override
    public void saveMoney(String clientId, BigDecimal amount, String accountId) {
        Money moneyAmount = Money.of(amount, MoneyHelper.EUR_CURRENCY_CODE);
        Deposit deposit = new Deposit(clientId, moneyAmount, accountId);
        Account account = accountRepository.findById(accountId);
        account.process(deposit);
    }

    @Override
    public void retrieveSaving(String clientId, BigDecimal amount, String accountId) {
        Money moneyAmount = Money.of(amount, MoneyHelper.EUR_CURRENCY_CODE);
        Withdrawal withdrawal = new Withdrawal(clientId, moneyAmount, accountId);
        Account account = accountRepository.findById(accountId);
        account.process(withdrawal);
    }

    @Override
    public String printStatement(String accountId) {
        Account account = accountRepository.findById(accountId);
        return account.printCurrentStatement();
    }

    @Override
    public void nextStatement(String accountId) {
        Account account = accountRepository.findById(accountId);
        account.nextStatement();
    }
}
