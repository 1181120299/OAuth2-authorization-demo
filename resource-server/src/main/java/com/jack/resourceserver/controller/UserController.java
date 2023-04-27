package com.jack.resourceserver.controller;

import com.jack.resourceserver.annotation.UserDataProvider;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;



import com.jack.resourceserver.entity.User;
import com.jack.resourceserver.dto.UserDto;
import com.jack.resourceserver.service.UserService;

import com.jack.utils.web.R;

import java.util.List;
import java.util.Objects;

/**
 * 用户信息（定义业务系统需要的用户信息）
 * 
 * @author chenjiabao
 * @email 1181120299@qq.com
 * @date 2023-04-22 15:40:13
 */
@Validated
@Controller
@RequestMapping("/user")
public class UserController implements UserDataProvider {

	@Resource
	private UserService userService;

	/**
	 * 信息
	 */
	/*@GetMapping("/info/{username}")
	public R info(@PathVariable("username") String username){
		User user = userService.getById(username);
		
		return R.ok().setData(user);
	}*/

	/**
	 * 保存
	 */
	@PostMapping("/save")
	@ResponseBody
	public R save(@RequestBody @Validated UserDto userDto){
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		userService.save(user);
		
		return R.ok();
	}

	/**
	 * 检查用户名是否存在
	 * @param username	用户名
	 * @return	retCode=2000 可以使用。2999已存在
	 */
	@GetMapping("/checkUsername")
	@ResponseBody
	public R checkUsername(@RequestParam String username) {
		userService.checkUsername(User.builder().username(username).build());
		return R.ok("可以使用");
	}

	@GetMapping("/toUpdate/{username}")
	public String toUpdate(@PathVariable("username") String username, Model model) {
		User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
		model.addAttribute("user", user);
		return "updateUser";
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ResponseBody
	public R update(@RequestBody User user){
		userService.updateById(user);
		
		return R.ok();
	}



	/**
	 * 删除
	 */
	@GetMapping("/delete")
	@ResponseBody
	public R delete(@NotNull(message = "username不能为空") String username){
		userService.removeById(username);
		
		return R.ok();
	}

	/**
	 * 列表
	 */
	@GetMapping("/page")
	public String page(@RequestParam(required = false, defaultValue = "1") Integer current,
				  @RequestParam(required = false, defaultValue = "10") Integer size,
				  Model model) {
		IPage<User> page = new Page<>(current, size);
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
				.orderByDesc(User::getUsername);
		page = userService.page(page, wrapper);
		model.addAttribute("page", page);

		return "index";
	}

	@GetMapping("/toSave")
	public String toSave() {
		return "createUser";
	}

	@Override
	public R findByUsername(String username) {
		User user = userService.getOne(new LambdaQueryWrapper<User>()
				.eq(User::getUsername, username));
		if (user != null) {
			user.setPassword(null);
		}

		return R.ok().setData(user);
	}

	@Override
	public R usernameList() {
		List<User> userList = userService.list(new LambdaQueryWrapper<User>()
				.select(User::getUsername));
		List<String> usernameList = userList.stream()
				.map(User::getUsername)
				.toList();
		return R.ok().setData(usernameList);
	}
}
