package com.self.learning.common.enums;

public enum  UserErrorEnums implements IErrorCode{
    EMPTY_PARAM("E_001","参数为空错误"),
    ERROR_PARAM("E_002","参数类型错误");

    private String errorCode;

    private String errorMsg;

    UserErrorEnums(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
