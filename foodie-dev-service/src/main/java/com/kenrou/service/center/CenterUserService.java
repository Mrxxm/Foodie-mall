package com.kenrou.service.center;

import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.UserInfoBO;

public interface CenterUserService {

    /**
     * 查询用户
     *
     * @param userId
     */
    public Users queryUserInfo(String userId);


}
