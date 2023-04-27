package com.jack.resourceserver.mapper;

import com.jack.resourceserver.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息（定义业务系统需要的用户信息）
 * 
 * @author chenjiabao
 * @email 1181120299@qq.com
 * @date 2023-04-22 15:40:13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
	
}
