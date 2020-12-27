package com.kenrou.service;

import com.kenrou.pojo.Users;

public interface UserService {
    public Users getUser(String id);

    public void saveUser();

    public void updateUser(String id);

    public void deleteUser(String id);
}
