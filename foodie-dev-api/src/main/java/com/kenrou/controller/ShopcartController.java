package com.kenrou.controller;

import com.kenrou.pojo.bo.ShopcartBO;
import com.kenrou.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车", tags = {"购物车相关接口"})
@RestController
@RequestMapping("shopcart")
public class ShopcartController {

    @ApiOperation(value = "同步购物车到后端", notes = "同步购物车到后端", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isBlank(userId)) {
            IMOOCJSONResult.errorMsg("");
        }

        System.out.println(shopcartBO);

        // TODO 前端用户在登录情况下，添加商品到购物车，会同时在后端同步购物车到redis

        return IMOOCJSONResult.ok();
    }
}
