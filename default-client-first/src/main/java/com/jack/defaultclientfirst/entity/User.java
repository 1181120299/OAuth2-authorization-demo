package com.jack.defaultclientfirst.entity;

import com.jack.clientauthority.annotation.CustomUserType;
import lombok.*;
import org.springframework.stereotype.Component;

/**
 * 用户信息（定义业务系统需要的用户信息）
 * 
 * @author chenjiabao
 * @email 1181120299@qq.com
 * @date 2023-04-15 11:12:17
 */
@Data
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User implements CustomUserType {
	
	/**
	* 用户名
	*/
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
	 * 手机号，不允许重复
	 */
	private String phone;
}
