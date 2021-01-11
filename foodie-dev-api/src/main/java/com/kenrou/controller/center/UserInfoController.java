package com.kenrou.controller.center;

import com.kenrou.pojo.bo.UserInfoBO;
import com.kenrou.service.center.CenterUserService;
import com.kenrou.service.center.UserInfoService;
import com.kenrou.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "我的信息", tags = {"我的信息"})
@RestController
@RequestMapping("userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(@RequestBody UserInfoBO userInfoBO) {
        userInfoService.updateUserInfo(userInfoBO);

        return IMOOCJSONResult.ok();
    }
}
