package com.soft.web.service.login;

import java.util.*;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.soft.web.dao.menu.*;
import com.soft.web.dao.user.*;

@Service
public class LoginService {
	
	private static Logger logger = Logger.getLogger(LoginService.class);
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MenuMapper menuMapper;

	public Map<String, Object> login(String user_name) {
		logger.info("loginfo");
		return userMapper.queryUser(user_name);
	}

	public List<Map<String, String>> queryMenu(String group_id) {
		return menuMapper.queryMenu(group_id);
	}
}