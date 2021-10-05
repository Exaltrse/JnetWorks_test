package com.ushkov.exceptions;

public class IllegalRequestParamException extends RuntimeException {

    public IllegalRequestParamException(String message){
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
