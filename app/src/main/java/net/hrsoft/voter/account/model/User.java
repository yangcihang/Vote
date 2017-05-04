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
    private long userId;
    private String mobile;
    private String userName;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
