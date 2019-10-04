package com.core.common;

public class CoreException extends Exception {
    private String errorCode;
    private String errorMessage;

    public CoreException(Throwable ex) {
        super(ex);
    }

    public CoreException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
