package com.jack.resourceserver.dto;

import java.util.Date;

import lombok.*;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户信息（定义业务系统需要的用户信息）Dto
 * 
 * @author chenjiabao
 * @email 1181120299@qq.com
 * @date 2023-04-22 15:40:13
 */
@Data
public class UserDto {
	
	/**
	* 用户名
	*/
	@Length(max = 50, message = "用户名最多50个字符")
	private String username;

	/**
	* 性别。1：男，0：女
	*/
	@Range(min = 0, max = 1, message = "性别。1：男，0：女超出大小限制[-128, 127]")
	private Integer gender;

	/**
	* 爱好
	*/
	@Length(max = 500, message = "爱好最多500个字符")
	private String hobby;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	private String password;

	/**
	 * 是否启用。true: 启用
	 */
	private Boolean enabled;

	/**
	 * 手机号，不允许重复
	 */
	@Length(max = 50, message = "爱好最多50个字符")
	private String phone;
}
