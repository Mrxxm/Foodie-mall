package com.kenrou.controller;

import com.kenrou.pojo.bo.ShopcartBO;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.JsonUtils;
import com.kenrou.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Api(value = "购物车", tags = {"购物车相关接口"})
@RestController
@RequestMapping("shopcart")
public class ShopcartController extends BaseController {

    @Autowired
    private RedisOperator redisOperator;

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

        // TODO 前端用户在登录情况下，添加商品到购物车，会同时在后端同步购物车到redis - 已完成
        // 需要判断当前购物车中包含已经存在的商品，如果存在则累加购买数量
        String shopCartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBO> shopCartList = null;
        if (StringUtils.isNotBlank(shopCartJson)) {
            shopCartList = JsonUtils.jsonToList(shopCartJson, ShopcartBO.class);
            boolean isHaving = false;
            for (ShopcartBO sc: shopCartList) {
                String temSpecId = sc.getSpecId();
                if (temSpecId.equals(shopcartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shopcartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shopCartList.add(shopcartBO);
            }
        } else {
            shopCartList = new ArrayList<>();
            shopCartList.add(shopcartBO);
        }
        // 覆盖现有redis中的购物车
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartList));

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "删除购物车商品", notes = "删除购物车商品", httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isBlank(userId)
        || StringUtils.isBlank(itemSpecId)) {
            IMOOCJSONResult.errorMsg("参数不能为空");
        }

        // TODO 用户在页面删除 购物车中的商品，如果用户已登录，需要同时在后端删除购物车商品到redis - 已完成
        String shopCartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBO> shopCartList = null;
        if (StringUtils.isNotBlank(shopCartJson)) {
            shopCartList = JsonUtils.jsonToList(shopCartJson, ShopcartBO.class);

            for (ShopcartBO sc: shopCartList) {
                String temSpecId = sc.getSpecId();
                if (temSpecId.equals(itemSpecId)) {
                    shopCartList.remove(sc);
                }
            }
        }
        // 覆盖现有redis中的购物车
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartList));

        return IMOOCJSONResult.ok();
    }
}
