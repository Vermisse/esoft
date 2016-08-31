package com.soft.web.dao.user;

import java.util.*;

import org.apache.ibatis.annotations.*;

public interface UserMapper {

	public Map<String, Object> queryUser(@Param("user_name") String user_name);
}