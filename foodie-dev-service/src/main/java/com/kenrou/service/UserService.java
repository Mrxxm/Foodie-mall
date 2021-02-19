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

    /**
     * 检索用户名和密码是否匹配，用于登录
     * @param username
     * @param password
     */
    public Users queryUserForLogin(String username, String password);

}
