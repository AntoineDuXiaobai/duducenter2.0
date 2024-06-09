package com.dudu.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dudu.usercenter.mapper")
//@ComponentScan({"com.dudu.usercenter.mapper", "com.dudu.usercenter.controller", "com.dudu.usercenter.service"})
public class UserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}

}
