package br.com.softplan.rinhabackendspring.exceptions;

public class InsufficientBalanceException extends RuntimeException{

    private int errorCode = -1;

    private String message;

    public InsufficientBalanceException(int errorCode, String message){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage(){ return message; }
}
