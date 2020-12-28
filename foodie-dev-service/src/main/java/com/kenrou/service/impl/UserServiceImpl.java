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

    @Transactional(propagation = Propagation.SUPPORTS) // 事务支持
    @Override
    public Users getUser(String id) {

        return usersMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser() {
        Users users = new Users();
        users.setId("xxx12345");
        users.setUsername("xxm");
        users.setNickname("啃肉");
        users.setPassword("34343434");
        users.setFace("face");
        Date date = new Date();
        users.setCreatedTime(date);
        users.setUpdatedTime(date);

        usersMapper.insert(users);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUser(String id) {
        Users users = new Users();

        users.setUsername("xxm_update");

        usersMapper.updateByPrimaryKey(users);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUser(String id) {
        Users users = new Users();
        users.setId(id);

        usersMapper.delete(users);
    }
}
