/*
 * Copyright (c) 2017. www.hrsoft.net  Inc. All rights reserved.
 */

package net.hrsoft.voter.account.model;


import net.hrsoft.voter.common.BaseModel;

/**
 * Token 信息
 *
 * @author yuanzeng
 * @since 17/1/24 下午8:08
 */

public class Token extends BaseModel {
    private String token;
    private long createAt;
    private long expiresAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }
}
