package com.jack.resourceserver.service;

import com.jack.resourceserver.entity.User;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户信息（定义业务系统需要的用户信息）
 * 
 * @author chenjiabao
 * @email 1181120299@qq.com
 * @date 2023-04-22 15:40:13
 */
public interface UserService extends IService<User> {

    /**
     * 检查用户名是否存在
     * @param entity 用户信息
     * @throws com.jack.utils.web.RRException   如果用户名已经存在
     */
    void checkUsername(User entity);
}
