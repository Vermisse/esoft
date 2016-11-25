package com.soft.web.dao.user;

import java.util.*;

import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import com.soft.util.*;

public class UserMapperTest extends BaseTest {
	
	@Autowired
	private UserMapper mapper;

	@Test
	public void testQueryUser() {
		Map<String, Object> map = mapper.queryUser("admin");
		map.forEach((k, v) -> System.out.println(k + ":" + v));
	}
}