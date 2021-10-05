package com.blz;

public class DBServiceException extends Throwable {
    DBServiceExceptionType exceptionType;

    public DBServiceException(String message, DBServiceExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }
}

enum DBServiceExceptionType {
    SQL_EXCEPTION , CLASSNOTFOUNDEXCEPTION
}
