package com.jack.resourceserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.clientauthority.annotation.RabbitOperation;
import com.jack.resourceserver.config.ResourceRabbitmqConstant;
import com.jack.resourceserver.entity.SpringSecurityUser;
import com.jack.resourceserver.entity.User;
import com.jack.resourceserver.mapper.UserMapper;
import com.jack.resourceserver.service.UserService;
import com.jack.utils.web.RRException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;

@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * auth server内置的账号，授权服务器不允许操作此账号。避免删除所有账号后，无法登录系统。
     */
    private static final String SUPER_ADMIN_USERNAME = "jack";

    /**
     * 新增用户
     * <p></p>
     *
     * 用户新增成功，需要发布消息通知auth server激活用户
     *
     * <p></p>
     * @param entity 实体对象
     * @return  true：新增成功
     * @throws com.jack.utils.web.RRException   如果用户名已存在或者为空
     */
    @Override
    public boolean save(User entity) {
        if (!StringUtils.hasText(entity.getUsername())) {
            throw new RRException("用户名不能为空");
        }

        // 检查用户名是否存在
        checkUsername(entity);

        // 密码加密处理
        entity.setPassword(encodePassword(entity.getPassword()));
        entity.setEnabled(true);
        int row = baseMapper.insert(entity);

        // 发布rabbitmq消息
        SpringSecurityUser springSecurityUser = SpringSecurityUser.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .enabled(true)
                .build();
        RabbitOperation<Object> rabbitOperation = RabbitOperation.builder()
                .op(RabbitOperation.OP.ADD)
                .data(springSecurityUser)
                .build();
        rabbitTemplate.convertAndSend(ResourceRabbitmqConstant.CUSTOM_USER.exchange,
                ResourceRabbitmqConstant.CUSTOM_USER.routeKey,
                JSON.toJSONString(rabbitOperation));

        return row > 0;
    }

    @Override
    public void checkUsername(User entity) {
        if (SUPER_ADMIN_USERNAME.equals(entity.getUsername())) {
            log.info("Not allow operate super admin account: {}", SUPER_ADMIN_USERNAME);
            throw new RRException("用户名已存在");
        }

        User existedUser = baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, entity.getUsername()));
        if (Objects.isNull(existedUser)) {
            return;
        }

        // 如果User表的主键不是username，还要比较主键
        //if (existedUser.getId().equals(entity.getId())) { return; }

        throw new RRException("用户名已存在");
    }

    @Override
    public boolean updateById(User entity) {
        if (StringUtils.hasText(entity.getPassword())) {
            entity.setPassword(encodePassword(entity.getPassword()));
        } else {
            // 前端可能穿空字符串
            entity.setPassword(null);
        }

        SpringSecurityUser springSecurityUser = new SpringSecurityUser();
        BeanUtils.copyProperties(entity, springSecurityUser);
        RabbitOperation<Object> rabbitOperation = RabbitOperation.builder()
                .op(RabbitOperation.OP.UPDATE)
                .data(springSecurityUser)
                .build();
        rabbitTemplate.convertAndSend(ResourceRabbitmqConstant.CUSTOM_USER.exchange,
                ResourceRabbitmqConstant.CUSTOM_USER.routeKey,
                JSON.toJSONString(rabbitOperation));

        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean result = super.removeById(id);
        if (!result) {
            return false;
        }

        // 通过Rabbimtmq发布消息：删除用户
        final String username = id.toString();
        RabbitOperation<Object> rabbitOperation = RabbitOperation.builder()
                .op(RabbitOperation.OP.DELETE)
                .data(username)
                .build();
        rabbitTemplate.convertAndSend(ResourceRabbitmqConstant.CUSTOM_USER.exchange,
                ResourceRabbitmqConstant.CUSTOM_USER.routeKey,
                JSON.toJSONString(rabbitOperation));

        return true;
    }

    /**
     * 密码加密处理
     * <p></p>
     *
     * Spring boot starter Security会将代码的加密交给代理类。
     * 当前版本(3.0.5)使用的加密算法是bcrypt。
     * <p></p>
     *
     * bcrypt这种加密算法的特点：
     * <li>salt会保存在密文中</li>
     * <li>密文必定是$2a$开头</li>
     *
     * <p></p>
     * @param password  密码
     * @return  加密后的密文
     */
    private String encodePassword(String password) {
        Assert.notNull(password, "password can not be null");
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(password);
    }
}

















