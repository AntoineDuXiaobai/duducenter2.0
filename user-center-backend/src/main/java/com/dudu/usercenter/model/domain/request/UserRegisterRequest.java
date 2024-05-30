package com.dudu.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author dudu
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 4943880412270094748L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;

}
