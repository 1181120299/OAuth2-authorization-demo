package com.jack.defaultclientfirst.Controller;

import com.jack.clientauthority.annotation.NeedPermission;
import com.jack.utils.web.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "设备管理相关接口")
@RequestMapping("/device")
public class DeviceController {

    @NeedPermission("查看设备列表")
    @GetMapping("/page")
    public R page() {
        return R.ok();
    }

    @NeedPermission("删除设备")
    @GetMapping("/delete")
    public R delete(String id) {
        return R.ok("删除成功");
    }
}
