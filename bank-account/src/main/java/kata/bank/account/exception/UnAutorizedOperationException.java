package kata.bank.account.exception;

public class UnAutorizedOperationException extends BankAccountException {

    public UnAutorizedOperationException(String message) {
        super(message);
    }
}
