package com.yefan.inventor.service.impl;

import javax.annotation.Resource;

import com.yefan.inventor.dao.RedisDAO;
import com.yefan.inventor.mapper.UserMapper;
import com.yefan.inventor.model.User;
import com.yefan.inventor.service.UserService;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户Service实现类
 * @author Administrator
 *
 */
@Service("userService")  
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private RedisDAO redisDAO;
	
	@Override
	public User findUserInfo() {
		return userMapper.findUserInfo();
	}

	@Override
	public User getCachedUserInfo() {
		redisDAO.set("cached_user_lisi", "{\"name\": \"lisi\", \"age\":28}");
		
		String userJSON = redisDAO.get("cached_user_lisi");  
		JSONObject userJSONObject = JSONObject.parseObject(userJSON);
		
		User user = new User();
		user.setName(userJSONObject.getString("name"));   
		user.setAge(userJSONObject.getInteger("age"));  
		
		return user;
	}

}
