package com.soft.web.dao.menu;

import java.util.*;

import org.apache.ibatis.annotations.*;

public interface MenuMapper {

	public List<Map<String, String>> queryMenu(@Param("group_id") String group_id);
}