package kata.bank.account.service;

import java.io.Serializable;

public interface Repository<K extends Serializable, O> {
    O findById(K key);
}
