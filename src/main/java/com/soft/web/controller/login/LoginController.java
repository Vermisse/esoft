package com.soft.web.controller.login;

import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.*;
import com.soft.util.*;
import com.soft.web.service.login.*;

/**
 * 登录Controller
 * 
 * @author vermisse
 */
@Controller
public class LoginController {
	/**
	 * 登录Service
	 */
	@Autowired
	private LoginService service;
	
	/**
	 * Redis模板
	 */
	@Resource(name = "redisTemplate")
	private RedisTemplate<String, String> redis;
	
	/**
	 * 进入首页
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		if (request.getAttribute("user") == null)
			return "index"; //未登录
		return "main"; //已登录
	}
	
	/**
	 * 登录
	 * @param 账号
	 * @param 密码
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(String user_name, String password, Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = service.login(user_name);
		
		model.addAttribute("user_name", user_name);
		if (map == null) {
			model.addAttribute("message", "帐号不存在！");
			return "index";
		}
		if (!map.get("password").equals(password)) {
			model.addAttribute("message", "密码不正确！");
			return "index";
		}
		if (!map.get("status").equals(1)) {
			model.addAttribute("message", "该用户已禁用！");
			return "index";
		}

		String auth;
		do {
			auth = Text.randomText(18);
		} while (Redis.checkAuth(redis, auth));
		
		Cookie cookie = new Cookie("auth", auth);
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
		
		boolean status = Redis.save(redis, auth, new HashMap<String, String>() {
			{
				put("user-agent", request.getHeader("user-agent"));
				map.forEach((k, v) -> put(k, String.valueOf(v)));
				
				//这里需要修改，菜单单独缓存，没有必要放在用户下级
				List<Map<String, String>> menu = service.queryMenu(get("group_id"));
				put("menu", JSON.toJSONString(menu));
			}
		});
		if (status) {
			model.addAttribute("user_name", null);
			return "redirect:index.html";
		} else {
			model.addAttribute("message", "登录失败！");
			return "index";
		}
	}
	
	/**
	 * 注销
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request){
		String auth = (String)request.getAttribute("auth");
		Redis.delete(redis, auth);
		return "redirect:index.html";
	}
}