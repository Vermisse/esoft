package com.soft.filter;

import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;

import org.springframework.data.redis.core.*;
import org.springframework.web.servlet.handler.*;

public class SecurityFilter extends HandlerInterceptorAdapter {

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.getRequestURL().toString();
		System.out.println(request.getHeader("user-agent"));
		System.out.println(redisTemplate);
		return super.preHandle(request, response, handler);
	}
}