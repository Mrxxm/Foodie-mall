package com.kenrou.service.impl.center;

import com.kenrou.mapper.UsersMapper;
import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.center.CenterUserBO;
import com.kenrou.service.center.CenterUserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service // 容器加载Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired // 注入UsersMapper
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    // 用户默认头像
    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users user  = usersMapper.selectByPrimaryKey(userId);
        user.setPassword("");

        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO userInfoBO) {
        Users user = new Users();
        user.setId(userId);
        user.setNickname(userInfoBO.getNickname());
        user.setRealname(userInfoBO.getRealname());
        user.setBirthday(userInfoBO.getBirthday());
        user.setMobile(userInfoBO.getMobile());
        user.setEmail(userInfoBO.getEmail());
        user.setFace(userInfoBO.getFace());
        user.setSex(userInfoBO.getSex());
        user.setUpdatedTime(new Date());

        usersMapper.updateByPrimaryKeySelective(user);

        return queryUserInfo(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users user = new Users();
        user.setId(userId);
        user.setFace(faceUrl);
        user.setUpdatedTime(new Date());

        usersMapper.updateByPrimaryKeySelective(user);

        return queryUserInfo(userId);
    }
}
