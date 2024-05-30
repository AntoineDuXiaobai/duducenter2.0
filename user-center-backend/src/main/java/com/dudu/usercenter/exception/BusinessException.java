package com.dudu.usercenter.exception;

import com.dudu.usercenter.common.ErrorCode;

/**
 * 自定义异常类
 * @author dudu
 */
public class BusinessException extends RuntimeException{
    private final int code;  //不需要set， 所以是final
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message); // 把msg传递给父类
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
