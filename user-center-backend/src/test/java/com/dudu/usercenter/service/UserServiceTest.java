package com.dudu.usercenter.service;

import com.dudu.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 *
 * @author dudu
 */

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    @Resource
    private  UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("testdudu");
        user.setUserAccount("123");
        user.setAvatarUrl("https://www.google.com/imgres?q=naruto&imgurl=https%3A%2F%2Fcdn2.unrealengine.com%2F18br-headband-cosmetics-naruto-toast-400x400-5228564ee685.jpg&imgrefurl=https%3A%2F%2Fwww.fortnite.com%2Fnews%2Fnaruto-x-fortnite-is-finally-here%3Flang%3Dfr&docid=bV07y1irC_gExM&tbnid=Eb4-EJ-R485S8M&vet=12ahUKEwiomaDIqN6FAxWiVqQEHU1_AvwQM3oECDMQAA..i&w=400&h=400&hcb=2&ved=2ahUKEwiomaDIqN6FAxWiVqQEHU1_AvwQM3oECDMQAA");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);

    }
    @Test
    void userRegister() {
        String userAccount = "dudu";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "du";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "dudu";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "du du";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "dudu";
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "1234";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount = "mydudu2";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result > 0);







    }
}