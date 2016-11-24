package com.soft.web.service.login;

import java.util.*;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test.xml")
public class LoginServiceTest {

	@Autowired
	private LoginService service;
	
	@Test
	public void testLogin() {
		Map<String, Object> map = service.login("admin");
		System.out.println(map.get("user_name"));
		System.out.println(map.get("password"));
	}

	@Test
	public void testQueryMenu() {
		//fail("Not yet implemented");
	}
}