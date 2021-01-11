package com.kenrou.service.impl.center;

import com.kenrou.mapper.UsersMapper;
import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.UserInfoBO;
import com.kenrou.service.center.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service // 容器加载Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(UserInfoBO userInfoBO) {
        Users user = new Users();
        user.setId(userInfoBO.getId());
        user.setNickname(userInfoBO.getNickname());
        user.setRealname(userInfoBO.getRealname());
        user.setBirthday(userInfoBO.getBirthday());
        user.setMobile(userInfoBO.getMobile());
        user.setEmail(userInfoBO.getEmail());
        user.setFace(userInfoBO.getFace());
        user.setSex(userInfoBO.getSex());
        user.setUpdatedTime(new Date());

        usersMapper.updateByPrimaryKeySelective(user);
    }
}
