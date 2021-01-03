package com.kenrou.controller;

import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.UserBO;
import com.kenrou.service.UserService;
import com.kenrou.utils.CookieUtils;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录", tags = {"注册登录相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
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

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {

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

        user = setNullProperty(user);

        // 6.设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        // 用户的分布式会话
        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

        return IMOOCJSONResult.ok(user);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {

        String username        = userBO.getUsername();
        String password        = userBO.getPassword();

        // 1.判断用户名密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        // 2.实现登录
        Users user = userService.queryUserForLogin(username, password);

        if (user == null) {
            return IMOOCJSONResult.errorMsg("密码错误");
        }
        user = setNullProperty(user);

        // 3.设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        // 用户的分布式会话
        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

        return IMOOCJSONResult.ok(user);
    }

    private Users setNullProperty(Users user) {

        user.setPassword(null);
        user.setRealname(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setBirthday(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);

        return user;
    }

    @ApiOperation(value = "用户登出", notes = "用户登出", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {

        // jsp时代，需要清除用户会话数据session
        // 清除用户相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();
    }

}
