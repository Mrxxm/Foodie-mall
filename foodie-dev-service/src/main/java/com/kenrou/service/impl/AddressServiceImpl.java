package com.kenrou.service.impl;

import com.kenrou.enums.YesOrNo;
import com.kenrou.mapper.UserAddressMapper;
import com.kenrou.pojo.UserAddress;
import com.kenrou.pojo.bo.AddressBO;
import com.kenrou.service.AddressService;
import org.apache.catalina.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        return userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        // 1.判断当前用户是否存在地址，如果没有，则新增为 '默认地址'
        Integer isDefault = 0;
        List<UserAddress> userAddressList = this.queryAll(addressBO.getUserId());

        if (userAddressList == null || userAddressList.isEmpty()) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();

        // 2.保存地址到数据库
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, newAddress);

        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(newAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();

        UserAddress updateAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, updateAddress);

        updateAddress.setId(addressId);
        updateAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(updateAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);

        userAddressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void setDefaultAddress(String userId, String addressId) {

        // 1.查找默认地址，设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNo.YES.type);

        // 查询
        UserAddress queryResAddress = userAddressMapper.selectOne(queryAddress);
        if (queryResAddress != null) {
            queryResAddress.setIsDefault(YesOrNo.NO.type);
            // 更新
            userAddressMapper.updateByPrimaryKeySelective(queryResAddress);
        }

        // 2.根据地址id修改为默认的地址
        UserAddress userAddress = new UserAddress();

        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        userAddress.setIsDefault(YesOrNo.YES.type);

        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }
}
