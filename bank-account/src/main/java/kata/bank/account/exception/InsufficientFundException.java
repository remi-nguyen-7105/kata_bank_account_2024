package kata.bank.account.exception;

public class InsufficientFundException extends BankAccountException {
    public InsufficientFundException(String message) {
        super(message);
    }
}
