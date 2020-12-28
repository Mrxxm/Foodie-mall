package com.kenrou.controller;

import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.UserBO;
import com.kenrou.service.UserService;
import com.kenrou.utils.IMOOCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {

        // 1.判断入参是否为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        // 2.查找username
        boolean result = userService.queryUsernameIsExist(username);

        if (result) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        // 3.请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO) {

        String username        = userBO.getUsername();
        String password        = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        // 1.判断用户名密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                    StringUtils.isBlank(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        // 2.查询用户名是否存在
        boolean result = userService.queryUsernameIsExist(username);
        if (result) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        // 3.判断密码长度，不能少于6位
        if (StringUtils.length(password) < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能少于6位");
        }
        // 4.判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("两次输入密码不一致");
        }
        // 5.实现注册
        Users user = userService.createUser(userBO);

        // 6.请求成功
        return IMOOCJSONResult.ok(user);
    }

}
