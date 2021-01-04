package com.kenrou.controller;

import com.kenrou.pojo.UserAddress;
import com.kenrou.pojo.bo.ShopcartBO;
import com.kenrou.service.AddressService;
import com.kenrou.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "收货地址", tags = {"收货地址相关接口"})
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1.查询用户的所有收货地址列表
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     */

    @ApiOperation(value = "收货地址列表", notes = "收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public IMOOCJSONResult add(
            @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            IMOOCJSONResult.errorMsg("");
        }

        List<UserAddress> userAddressList = addressService.queryAll(userId);

        return IMOOCJSONResult.ok(userAddressList);
    }
}
