package com.kenrou.pojo.bo.center;

import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel(value = "用户中心对象BO", description = "从客户端，由用户传入的数据封装在此entity中")
public class CenterUserBO {

//    @ApiModelProperty(value = "用户名", name = "username", example = "kenrou", required = true)
    private String username;
    private Date birthday;
    private String email;
    private String face;
    private String mobile;
    private String nickname;
    private String realname;
    private Integer sex;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
