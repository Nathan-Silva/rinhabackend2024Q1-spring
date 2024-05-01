package br.com.softplan.rinhabackendspring.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HttpErrorHandlingResponse {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> customerNotFoundExceptionHandling(CustomerNotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> insufficientBalanceExceptionHandling(InsufficientBalanceException e) {
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
    }

    @ExceptionHandler(PayloadValidateException.class)
    public ResponseEntity<String> payloadValidateException(PayloadValidateException e) {
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
    }
}
