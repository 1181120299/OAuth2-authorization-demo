package com.jack.resourceserver.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Spring Security 需要的用户信息
 * <p></p>
 *
 * 实现{@link Serializable}接口是因为要发布RabbitMq消息，传递用户数据。
 * <p></p>
 *
 * <b>但是，不推荐消息直接发送对象的序列化数据。强烈建议将对象转成JSON字符串，提高系统兼容性，方便别人解析。</b>
 *
 * <p></p>
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
public class SpringSecurityUser implements Serializable {
	
	/**
	* 用户名
	*/
	private String username;

	/**
	* 密码
	*/
	private String password;

	/**
	* 是否启用。true: 启用
	*/
	private Boolean enabled;

}
