package net.hrsoft.voter.account.model;

import net.hrsoft.voter.common.BaseModel;

import retrofit2.http.POST;

/**
 * Created by abtion on 17/4/14.
 */

public class LoginReuqest extends BaseModel {
    private String mobile;
    private String password;

    public LoginReuqest(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
