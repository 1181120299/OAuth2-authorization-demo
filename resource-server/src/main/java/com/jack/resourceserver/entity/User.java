package com.jack.resourceserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 用户信息（定义业务系统需要的用户信息）
 * <p></p>
 *
 * 自定义的用户信息，用户名（登录账号）不应该允许修改。
 * 在新增、删除用户时，需要发布RabbitMq消息，auth server会消费消息进行账号激活。
 *
 * <p></p>
 * @author chenjiabao
 * @email 1181120299@qq.com
 * @date 2023-04-22 15:40:13
 */
@Data
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class User {
	
	/**
	* 用户名
	*/
	@TableId(value = "username", type = IdType.ASSIGN_UUID)
	private String username;

	/**
	* 性别。1：男，0：女
	*/
	private Integer gender;

	/**
	* 爱好
	*/
	private String hobby;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 是否启用。true: 启用
	 */
	private Boolean enabled;

	/**
	 * 手机号，不允许重复
	 */
	private String phone;
}
