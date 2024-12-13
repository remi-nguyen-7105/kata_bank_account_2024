package kata.bank.account.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientTest {

    @Test
    public void whenCreate_ok() {
        String clientId = "clientId";
        new Client(clientId);
    }

    @Test
    public void whenCreate_clientIdMustNotBeNull() {
        assertThrows(NullPointerException.class, () -> new Client(null));
    }
}
