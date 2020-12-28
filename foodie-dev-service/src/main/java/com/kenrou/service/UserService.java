package com.kenrou.service;

import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.UserBO;

public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 用户注册
     * @param userBO
     */
    public Users createUser(UserBO userBO);

    public Users getUser(String id);

    public void saveUser();

    public void updateUser(String id);

    public void deleteUser(String id);
}
