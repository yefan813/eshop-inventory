package com.yefan.inventor.controller;

import com.yefan.inventor.model.User;
import com.yefan.inventor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户Controller控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/getUserInfo")
	@ResponseBody
	public User getUserInfo() {
		User user = userService.findUserInfo();
		return user;
	}
	
	@RequestMapping("/getCachedUserInfo")
	@ResponseBody
	public User getCachedUserInfo() {
		User user = userService.getCachedUserInfo();
		return user;
	}
	
}
