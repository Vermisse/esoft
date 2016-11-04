package com.soft.filter;

import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;

import org.springframework.data.redis.core.*;
import org.springframework.web.servlet.handler.*;

import com.alibaba.fastjson.*;
import com.soft.util.*;

/**
 * 登录权限过滤器
 */
public class SecurityFilter extends HandlerInterceptorAdapter {

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String auth = null;
		String url = request.getServletPath().toString();
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals("auth")){
					auth = cookie.getValue(); //读取凭证
					break;
				}
		
		if (auth == null || !Redis.checkAuth(redisTemplate, auth)) { //如果凭证为空直接拦截
			if(!url.equals("/index.html") && !url.equals("/login.html")) //只放行登录页和登录操作
				response.sendRedirect(request.getContextPath() + "/index.html");
			return super.preHandle(request, response, handler);
		}
		
		Map<String, String> user = Redis.getMap(redisTemplate, auth);

		//走到这里基本上不会出现user为空
		System.out.println("用户凭证:" + auth);
		System.out.println("用户请求:" + request.getHeader("user-agent"));
		System.out.println("登录绑定:" + user.get("user-agent"));
		
		//绑定user-agent与请求user-agent不相同说明该请求有问题，直接重定向到首页
		if (!request.getHeader("user-agent").equals(user.get("user-agent"))) {
			Redis.delete(redisTemplate, auth);
			response.sendRedirect(request.getContextPath() + "/index.html");
			return super.preHandle(request, response, handler);
		}
		
		//如果已经是登录状态拦截登录操作
		if (url.equals("/login.html")) {
			response.sendRedirect(request.getContextPath() + "/index.html");
			return super.preHandle(request, response, handler);
		}
		
		//每次请求为凭证续期，续期长度为90天，意思是90天不访问就自动清除该凭证
		Redis.delay(redisTemplate, auth);
		
		request.setAttribute("user", user);
		request.setAttribute("auth", auth);
		request.setAttribute("path", request.getContextPath());
		
		//这里需要修改，权限直接缓存，不在用户下级，没必要重复缓存
		request.setAttribute("menu", JSONArray.parseArray(user.get("menu"), Map.class));
		return super.preHandle(request, response, handler);
	}
}