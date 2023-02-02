package br.com.itau.avaliationtest.financialtransfer.adapters.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AccountDuplicityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountDuplicityException(String message) {
        super(message);
    }

    public AccountDuplicityException(String message, Throwable cause) {
        super(message, cause);
    }
}
