package com.kenrou.controller;

import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.ShopcartBO;
import com.kenrou.pojo.bo.UserBO;
import com.kenrou.pojo.vo.UsersVO;
import com.kenrou.service.UserService;
import com.kenrou.utils.CookieUtils;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.JsonUtils;
import com.kenrou.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Api(value = "注册登录", tags = {"注册登录相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private RedisOperator redisOperator;

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

//        user = setNullProperty(user);

        // 6.设置cookie
//        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        // 用户的分布式会话
        // TODO 生成用户token，存入redis会话 - 已完成
        String token = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + user.getId(), token);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserUniqueToken(token);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);

        // TODO 同步购物车数据 - 已完成
        synchShopcartData(user.getId(), request, response);

        return IMOOCJSONResult.ok(usersVO);
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
//        user = setNullProperty(user);

        // 3.设置cookie
//        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        // 用户的分布式会话
        // TODO 生成用户token，存入redis会话 - 已完成
        String token = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + user.getId(), token);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserUniqueToken(token);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);

        // TODO 同步购物车数据 - 已完成
        synchShopcartData(user.getId(), request, response);

        return IMOOCJSONResult.ok(user);
    }

    @ApiOperation(value = "用户登出", notes = "用户登出", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {

        // jsp时代，需要清除用户会话数据session
        // 清除用户相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车 - 已完成
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
        // TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();
    }

    /**
     * 注册登录成功后，同步cookie和redis中的购物车数据
     */
    private void synchShopcartData(String userId, HttpServletRequest request, HttpServletResponse response) {
        // 1.redis中无数据 cookie中购物车为空 不做处理
        // 2.             cookie中的购物车不为空，此时直接放入redis中
        // 3.redis中有数据 cookie中购物车为空 直接同步
        // 4.             cookie中的购物车不为空, 如果cookie中的某个商品在redis中存在，则以cookie为主 cookie直接覆盖redis商品
        // 5.同步到redis中，覆盖本地cookie购物车数据，保证数据一致性

        String shopCartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        String shopCartJsonCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);
        List<ShopcartBO> shopCartListRedis = JsonUtils.jsonToList(shopCartJsonRedis, ShopcartBO.class);
        List<ShopcartBO> shopCartListCookie = JsonUtils.jsonToList(shopCartJsonCookie, ShopcartBO.class);

        if (StringUtils.isBlank(shopCartJsonRedis) && StringUtils.isNotBlank(shopCartJsonCookie)) {
            redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartListCookie));
        }

        if (StringUtils.isNotBlank(shopCartJsonRedis) && StringUtils.isBlank(shopCartJsonCookie)) {
            CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopCartListRedis), true);
        }

        // 定义一个待删除list
        List<ShopcartBO> pendingDeleteList = new ArrayList<>();
        // 以cookie数据为主
        if (StringUtils.isNotBlank(shopCartJsonRedis) && StringUtils.isNotBlank(shopCartJsonCookie)) {
            for (ShopcartBO redisCart: shopCartListRedis) {
                String redisSpecId = redisCart.getSpecId();
                for (ShopcartBO cookieCart: shopCartListCookie) {
                    String cookieSpecId = cookieCart.getSpecId();
                    if (redisSpecId.equals(cookieSpecId)) {
                        // cookie中数量覆盖redis中数量
                        redisCart.setBuyCounts(cookieCart.getBuyCounts());
                        // 把cookie数据放入待删除列表，用于删除与合并
                        pendingDeleteList.add(cookieCart);
                    }
                }
            }
            // 从现有cookie中删除对应覆盖过的商品数据
            shopCartListCookie.removeAll(pendingDeleteList);
            // 合并两个list
            shopCartListRedis.addAll(shopCartListCookie);

            redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartListRedis));
            CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopCartListRedis), true);
        }

    }

}
