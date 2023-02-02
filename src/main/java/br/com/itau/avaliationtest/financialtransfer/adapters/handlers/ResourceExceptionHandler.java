package br.com.itau.avaliationtest.financialtransfer.adapters.handlers;

import br.com.itau.avaliationtest.financialtransfer.adapters.exceptions.AccountDuplicityException;
import br.com.itau.avaliationtest.financialtransfer.adapters.exceptions.AccountNotExistException;
import br.com.itau.avaliationtest.financialtransfer.adapters.exceptions.BadRequestException;
import br.com.itau.avaliationtest.financialtransfer.adapters.exceptions.TransferBlockedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Handler responsável por capturar e tratar/formatar exceptions dos fluxos partindo da camada controladora
 */
@ControllerAdvice
@Slf4j
public class ResourceExceptionHandler {

    private static final String ERROR_PREFIX = "Class Error >> {} || Cause Error >> {} || Message error >> {}";
    private static final String ACCOUNT_NOT_EXIST_EXCEPTION = "AccountNotExistException";
    private static final String TRANSFER_BLOCKED_EXCEPTION = "TransferBlockedException";
    private static final String ILLEGAL_ARGUMENT_EXCEPTION = "IllegalArgumentException";
    private static final String ACCOUNT_DUPLICITY_EXCEPTION = "AccountDuplicityException";
    private static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "MethodArgumentNotValidException";
    private static final String BAD_REQUEST_EXCEPTION = "BadRequestException";


    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> errorException(Exception e, HttpServletRequest request) {
        printErrorLog(e);
        StandardError err = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Erro interno no servidor")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler({IllegalArgumentException.class, BadRequestException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<StandardError> badRequestGeneralException(Exception e, HttpServletRequest request) {
        printErrorLog(e);
        StandardError err = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler({AccountNotExistException.class})
    public ResponseEntity<StandardError> notFoundGeneralException(Exception e, HttpServletRequest request) {
        printErrorLog(e);
        StandardError err = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({TransferBlockedException.class})
    public ResponseEntity<StandardError> forbiddenGeneralException(Exception e, HttpServletRequest request) {
        printErrorLog(e);
        StandardError err = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler({AccountDuplicityException.class})
    public ResponseEntity<StandardError> conflictGeneralException(Exception e, HttpServletRequest request) {
        printErrorLog(e);
        StandardError err = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    /**
     * Metodo de tratamento de erros para print de log
     *
     * @param e exception em questao como fonte de info para tratamento
     */
    private void printErrorLog(Exception e) {
        switch (e.getClass().getSimpleName()) {
            case ACCOUNT_NOT_EXIST_EXCEPTION:
            case TRANSFER_BLOCKED_EXCEPTION:
            case BAD_REQUEST_EXCEPTION:
            case ACCOUNT_DUPLICITY_EXCEPTION:
            case METHOD_ARGUMENT_NOT_VALID_EXCEPTION:
            case ILLEGAL_ARGUMENT_EXCEPTION:
                log.error(ERROR_PREFIX, e.getClass().getSimpleName(), e.getCause(), e.getMessage());
                break;
            default:
                log.error(ERROR_PREFIX, e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()), "");
        }
    }

}
