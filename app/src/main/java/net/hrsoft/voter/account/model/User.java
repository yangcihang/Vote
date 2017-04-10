/*
 * Copyright (c) 2017. www.hrsoft.net  Inc. All rights reserved.
 */

package net.hrsoft.voter.account.model;


import net.hrsoft.voter.common.BaseModel;

/**
 * 用户信息
 *
 * @author yuanzeng
 * @since 17/1/24 下午8:07
 */

public class User extends BaseModel {
    private long id;
    private String mobile;
    private boolean freeze;
    private String nickname;
    private String avatar;
    private String sign;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
