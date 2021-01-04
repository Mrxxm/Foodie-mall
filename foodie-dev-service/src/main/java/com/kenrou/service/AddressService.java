package com.kenrou.service;

import com.kenrou.pojo.UserAddress;
import com.kenrou.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    /**
     * 查询用户收货地址列表
     */
    public List<UserAddress> queryAll(String userId);

    /**
     * 添加地址
     */
    public void addNewUserAddress(AddressBO addressBO);

    /**
     * 更新地址
     */
    public void updateUserAddress(AddressBO addressBO);
}
