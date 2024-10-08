package com.dudu.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author dudu
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -2727012276756491953L;
    private String userAccount;
    private String userPassword;

}