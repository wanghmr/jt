package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * param:用户需要校验的数据
	 * type: 校验的类型 1username 2 phone 3 email
	 */
	@Override
	public boolean checkUser(String param, Integer type) {
		String column = type==1?"username":(type==2?"phone":"email");
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq(column, param);
		int count = userMapper.selectCount(queryWrapper);
		//有数据 true  没有 false
		return count>0?true:false;
	}


}
