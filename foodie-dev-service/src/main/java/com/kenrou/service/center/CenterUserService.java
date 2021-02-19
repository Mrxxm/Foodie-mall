package com.kenrou.service.center;

import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.center.CenterUserBO;

public interface CenterUserService {

    /**
     * 查询用户
     *
     * @param userId
     */
    public Users queryUserInfo(String userId);

    /**
     * 更新用户信息
     */
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 更新用户头像
     */
    public Users updateUserFace(String userId, String faceUrl);
}
