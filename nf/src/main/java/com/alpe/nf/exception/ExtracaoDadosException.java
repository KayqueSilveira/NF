package com.alpe.nf.exception;

public class ExtracaoDadosException extends RuntimeException {

    public ExtracaoDadosException(String message) {
        super(message);
    }

    public ExtracaoDadosException(String message, Throwable cause) {
        super(message, cause);
    }
}
