package com.jack.resourceserver.annotation;

import com.jack.utils.web.R;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 定义别的模块需要的用户信息接口
 */
public interface UserDataProvider {

    /**
     * 根据用户名查询用户信息。精确查询。
     * @param username  用户名
     * @return  其中data为用户信息
     */
    @Nonnull
    @GetMapping("/findByUsername")
    @ResponseBody
    R findByUsername(@RequestParam String username);

    /**
     * 获取所有的用户名
     * @return  其中data为用户名的集合
     */
    @Nonnull
    @GetMapping("/usernameList")
    @ResponseBody
    R usernameList();
}
