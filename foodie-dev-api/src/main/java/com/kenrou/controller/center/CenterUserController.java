package com.kenrou.controller.center;

import com.kenrou.controller.BaseController;
import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.center.CenterUserBO;
import com.kenrou.service.center.CenterUserService;
import com.kenrou.utils.CookieUtils;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "我的信息", tags = {"我的信息"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;


    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(@RequestParam String userId,
                                  @RequestBody CenterUserBO centerUserBO,
                                  HttpServletRequest request, HttpServletResponse response) {
        Users user = centerUserService.updateUserInfo(userId, centerUserBO);

        Users handleUser = setNullProperty(user);

        // 设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(handleUser), true);

        return IMOOCJSONResult.ok();
    }
}
