package kata.bank.account.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    @Test
    public void whenCreate_ok() {
        String accountId = "accountId";
        String clientId = "clientId";
        new Account(accountId, clientId);
    }

    @Test
    public void whenCreate_accountIdMustNotBeNull() {
        assertThrows(NullPointerException.class, () -> new Account(null, "clientId"));
    }

    @Test
    public void whenCreate_clientIdMustNotBeNull() {
        assertThrows(NullPointerException.class, () -> new Account("accountId", null));
    }
}
