package br.com.softplan.rinhabackendspring.exceptions;

public class CustomerNotFoundException extends RuntimeException{

    private int errorCode = -1;

    private String message;

    public CustomerNotFoundException(int errorCode, String message){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage(){ return message; }
}
