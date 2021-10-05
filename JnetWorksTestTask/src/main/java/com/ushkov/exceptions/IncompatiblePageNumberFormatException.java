package com.ushkov.exceptions;

public class IncompatiblePageNumberFormatException extends RuntimeException{
    public static final String ZEROPAGENUMBER = "There is a page with number 0 in request. Page number must not be 0.";
    public static final String NEGATIVEPAGENUMBER = "There is a negative page number in request. Page number must be positive.";
    public static final String NOTDIGITPAGENUMBER = "There is non digit symbol in request. Request must contain only digits and comma as separator.";

    public IncompatiblePageNumberFormatException(String message){
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
