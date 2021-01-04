package com.kenrou.service;

import com.kenrou.pojo.UserAddress;

import java.util.List;

public interface AddressService {

    /**
     * 查询用户收货地址列表
     */
    public List<UserAddress> queryAll(String userId);
}
