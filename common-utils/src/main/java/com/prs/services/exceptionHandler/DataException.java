package com.prs.services.exceptionHandler;

import lombok.Getter;

@Getter
public class DataException extends RuntimeException {
    private String message;
    public DataException(String s) {
        super(s);
        this.message=s;
    }
}
