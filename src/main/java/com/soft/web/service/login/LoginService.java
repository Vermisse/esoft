package com.soft.web.service.login;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.soft.web.dao.user.*;

@Service
public class LoginService {

	@Autowired
	private UserMapper mapper;

	public Map<String, Object> login(String user_name) {
		return mapper.queryUser(user_name);
	}
}