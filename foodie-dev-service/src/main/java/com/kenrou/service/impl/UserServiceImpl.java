package com.kenrou.service.impl;

import com.kenrou.mapper.UsersMapper;
import com.kenrou.pojo.Users;
import com.kenrou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service // 容器加载Service
public class UserServiceImpl implements UserService {

    @Autowired // 注入UsersMapper
    private UsersMapper usersMapper;

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
