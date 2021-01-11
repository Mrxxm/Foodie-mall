package com.kenrou.controller.center;

import com.kenrou.pojo.Users;
import com.kenrou.service.center.CenterUserService;
import com.kenrou.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户中心", tags = {"用户中心相关接口"})
@RestController
@RequestMapping("center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("userInfo")
    public IMOOCJSONResult userInfo(@RequestParam String userId) {
        Users user = centerUserService.queryUserInfo(userId);

        return IMOOCJSONResult.ok(user);
    }

}
