package com.kenrou.service.impl;

import com.kenrou.enums.Sex;
import com.kenrou.mapper.UsersMapper;
import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.UserBO;
import com.kenrou.service.UserService;
import com.kenrou.utils.DateUtil;
import com.kenrou.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service // 容器加载Service
public class UserServiceImpl implements UserService {

    @Autowired // 注入UsersMapper
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    // 用户默认头像
    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        // 切面日志测试
//        try {
//            Thread.sleep(3500);
//        } catch (Exception e) {
//        }

        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);

        Users result = usersMapper.selectOneByExample(userExample);

        return result == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {

        String userId = sid.nextShort();

        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        user.setNickname(userBO.getUsername());
        user.setFace(USER_FACE);
        user.setBirthday(DateUtil.stringToDate("1970-01-01"));
        user.setSex(Sex.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        usersMapper.insert(user);

        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {

        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        try {
            userCriteria.andEqualTo("password", MD5Utils.getMD5Str(password));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Users result = usersMapper.selectOneByExample(userExample);

        return result;
    }

}
