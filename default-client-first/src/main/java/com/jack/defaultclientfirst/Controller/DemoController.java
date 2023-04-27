package com.jack.defaultclientfirst.Controller;

import com.jack.clientauthority.annotation.CustomUserType;
import com.jack.clientauthority.annotation.NeedPermission;
import com.jack.clientauthority.utils.UserHelper;
import com.jack.clientauthority.utils.WebClientHelper;
import com.jack.defaultclientfirst.entity.User;
import com.jack.utils.web.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @NeedPermission("打招呼")
    @GetMapping("/helloJack")
    @ResponseBody
    public R helloJack() {
        User userinfo = UserHelper.getUserinfo(User.class);
        return R.ok("good good study, day day up.").setData(userinfo);
    }

    @GetMapping("/requestOtherClient")
    public String requestOtherClient(Model model) {
        List<String> usernameList = Collections.emptyList();

        String uri = "http://localhost:9001/user/usernameList";     // Fake uri, you can provide a client who`s name is client-second
        R resp = WebClientHelper.get(uri, R.class);
        if (resp.getCode() == R.getCodeOk()) {
            // do something
            usernameList = resp.getDataList(String.class);
        }

        model.addAttribute("messages", usernameList);
        return "homePage";
    }

    @NeedPermission("查看设备清单")
    @GetMapping("/foo")
    public String foo(Model model) {
        model.addAttribute("messages", List.of("3台电子秤", "1台桌面称"));
        return "homePage";
    }

    /**
     * 示例获取用户信息
     * <P></P>
     *
     * 请不要使用内置的账号：jack。内置的账号只适合用于登录系统，进管理页面创建client应用、创建用户。
     * @return  用户信息
     */
    @GetMapping("/getUserinfo")
    public String getUserinfo(Model model) {
        CustomUserType user = UserHelper.getUserinfo();
        model.addAttribute("messages", List.of(user));
        return "homePage";
    }

    @Value("${fakeUri:http://192.168.1.101:9001}")
    private String jumpUri;

    /**
     * 单点登录实现的是同域下的单点登录。假如你是通过192.168.1.101访问的A应用。
     * 那么跳转到B应用的时候，只有通过192.168.1.101访问B应用，才能保持登录状态。
     */
    @GetMapping("/jumpToOtherClient")
    public void jumpToOtherClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, jumpUri);
    }
}
