package com.dudu.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dudu.usercenter.common.ErrorCode;
import com.dudu.usercenter.exception.BusinessException;
import com.dudu.usercenter.model.domain.User;
import com.dudu.usercenter.service.UserService;
import com.dudu.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dudu.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务
* @author duxingyu
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-04-25 23:13:45
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值 混淆密码
     */
    private static final String SALT = "dudu";


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if(userPassword.length() < 8 || checkPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }

        //账户不包含特殊字符
        String pattern = "^[a-zA-Z0-9_]+$"; // 匹配非字母或数字的字符
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(userAccount);
        if (!matcher.find()) {     //不匹配
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号含非法字符");
        }

        //密码应该和校验密码相同
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码输入不同");
        }

        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "插入数据失败");
        }
        return user.getId();


    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "账号或密码为空");
        }
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度过短");
        }
        if(userPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度过短");
        }

        //账户不包含特殊字符
        String pattern = "^[a-zA-Z0-9_]+$"; // 匹配非字母或数字的字符
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(userAccount);
        if (!matcher.find()) {     //不匹配
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号含非法字符");
        }

        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if(user == null){
            log.info("User login failed, userAccount cannnot match userPassword.");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码错误");
        }

        //3.用户脱敏
        User safetyUser = getSafetyUser(user);

        //4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);  //把key，value存到session


        return safetyUser;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */

    @Override
    public User getSafetyUser(User originUser){
        if(originUser==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());

        return safetyUser;
    }

    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

}




