package com.self.learning.common.exception;

import com.self.learning.common.enums.IErrorCode;

import java.io.Serializable;

public class UserBussinessException extends RuntimeException implements Serializable {
    private String code;

    private String errorCode;

    private String errorMessage;

    private IErrorCode iErrorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public IErrorCode getiErrorCode() {
        return iErrorCode;
    }

    public void setiErrorCode(IErrorCode iErrorCode) {
        this.iErrorCode = iErrorCode;
    }

    public UserBussinessException(IErrorCode iErrorCode) {
        super(iErrorCode.getMessage());
        this.errorCode= iErrorCode.getErrorCode();
    }

    public UserBussinessException(String code,String errorMessage) {
        super(errorMessage);
        this.errorCode= code;
    }
}
