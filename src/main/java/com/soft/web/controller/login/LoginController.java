package com.soft.web.controller.login;

import java.text.*;
import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.soft.util.*;
import com.soft.web.service.login.*;

@Controller
public class LoginController {

	@Autowired
	private LoginService service;

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redis;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

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

		String auth = null;
		do {
			auth = Text.randomText(18);
		} while (Redis.checkAuth(redis, auth));
		
		Cookie cookie = new Cookie("auth", auth);
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
		
		model.addAttribute("message", Redis.add(redis, auth, new HashMap<String, String>() {
			{
				put("user-agent", request.getHeader("user-agent"));
				put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
				map.forEach((k, v) -> put(k, String.valueOf(v)));
			}
		}) ? "登录成功！" : "登录失败！");
		return "index";
	}
}