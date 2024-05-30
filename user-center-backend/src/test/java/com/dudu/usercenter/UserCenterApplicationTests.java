package com.dudu.usercenter;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.dudu.usercenter.mapper.UserMapper;
import com.mysql.cj.protocol.Message;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {


	@Test
	void testDigest() throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] bytes = md5.digest("abcd".getBytes());
		String s = new String(bytes);
		System.out.println(s);
	}
}