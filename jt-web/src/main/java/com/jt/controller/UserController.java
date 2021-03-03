package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

/**
 * UserController设计页面跳转
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Reference(check = false)
	private DubboUserService dubboUserService;

	@Autowired
	private JedisCluster jedisCluster;

	private String TICKET = "JT_TICKET";

	@RequestMapping("/{moduleName}")
	public String module(@PathVariable String moduleName) {
		return moduleName;
	}

	/**
	 * {password:_password,username:_username,phone:_phone},
	 * 
	 * @return
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		dubboUserService.saveUser(user);
		return SysResult.success();

	}

	/**
	 * 关于cookie的使用 cookie.setPath("/"); 默认 全部的请求都可见该cookie信息 www.jt.com
	 * cookie.setPath("/aa"); 只有在/aa路径下的url才能访问该cookie www.jt.com/aa/a.html
	 * 
	 * 关于Cookie的使用时间 cookie.setMaxAge(>0); cookie的存活时间 cookie.setMaxAge(0); 删除cookie
	 * cookie.setMaxAge(-1); 关闭会话之后删除cookie
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user, HttpServletResponse response) {
		// 1.校验数据是否正确.获取密钥
		String ticket = dubboUserService.findUserByUP(user);
		if (StringUtils.isEmpty(ticket)) {
			return SysResult.fail();
		}
		// 2.如果程序执行到这里.表示密钥有值.写入cookie
		Cookie cookie = new Cookie(TICKET, ticket);
		cookie.setMaxAge(7 * 24 * 3600);
		cookie.setPath("/");
		// 设定cookie的共享!!!!! sso
		cookie.setDomain("jt.com");

		// 将cookie写入客户端
		response.addCookie(cookie);
		return SysResult.success();
	}

	/**
	 * 实现用户登出操作. 0.获取cookie数据 1.删除redis key~~~ticket 2.删除cookie
	 * 
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String ticket = null;
		// 判断cookie是有效的.
		if (cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (TICKET.equals(cookie.getName())) {
					ticket = cookie.getValue();
					break;
				}
			}
		}

		if (!StringUtils.isEmpty(ticket)) {
			// 如果数据不为null,则删除数据
			jedisCluster.del(ticket);
			Cookie cookie = new Cookie(TICKET, "");
			cookie.setMaxAge(0); // 立即删除
			cookie.setPath("/");
			cookie.setDomain("jt.com");
			response.addCookie(cookie);
		}

		// 重定向到系统首页
		return "redirect:/";
	}

}
