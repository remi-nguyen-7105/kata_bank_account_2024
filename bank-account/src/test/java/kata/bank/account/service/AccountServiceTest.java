package kata.bank.account.service;

import kata.bank.account.domain.Account;
import kata.bank.account.domain.Deposit;
import kata.bank.account.utils.MoneyHelper;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    Repository<String, Account> accountRepository;

    @Test
    public void givenExistingAccountId_whenPrintStatement_Ok() {
        Account account = new Account("accountId", "clientId");
        when(accountRepository.findById(account.getAccountId())).thenReturn(account);

        AccountService accountService = new AccountServiceImpl(accountRepository);
        String result = accountService.printStatement(account.getAccountId());
        assertTrue(result.endsWith("  balance :  EUR 0 amount : EUR 0"));
    }

    @Test
    public void givenExistingAccount_whenSavingMoney_Ok() {
        Account account = new Account("accountId", "clientId");
        BigDecimal value = new BigDecimal("1000.00");

        when(accountRepository.findById(account.getAccountId())).thenReturn(account);

        AccountService accountService = new AccountServiceImpl(accountRepository);
        accountService.saveMoney(account.getClientId(), value, account.getAccountId());

        assertEquals(Money.of(value, MoneyHelper.EUR_CURRENCY_CODE), account.getAmount());
    }

    private Money amount(String value) {
        return Money.of(new BigDecimal(value), MoneyHelper.EUR_CURRENCY_CODE);
    }

    @Test
    public void givenExistingAccount_whenRetrieveSaving_Ok() {
        Account account = new Account("accountId", "clientId");
        account.process(new Deposit(account.getClientId(), amount("1001.00"), account.getAccountId()));
        BigDecimal value = new BigDecimal("1000.00");

        when(accountRepository.findById(account.getAccountId())).thenReturn(account);

        AccountService accountService = new AccountServiceImpl(accountRepository);

        accountService.retrieveSaving(account.getClientId(), value, account.getAccountId());

        assertEquals(amount("1"), account.getAmount());
    }

    @Test
    public void givenExistingAccount_whenNextStatement_Ok() {
        Account account = new Account("accountId", "clientId");
        account.process(new Deposit(account.getClientId(), amount("1000.00"), account.getAccountId()));

        when(accountRepository.findById(account.getAccountId())).thenReturn(account);

        AccountService accountService = new AccountServiceImpl(accountRepository);

        accountService.nextStatement(account.getAccountId());

        assertEquals(amount("1000"), account.getAmount());
        assertEquals(amount("0"), account.getCurrentStatement().getBalance());
    }
}
