package net.hrsoft.voter.account.model;

import net.hrsoft.voter.common.BaseModel;

/**
 * @author YangCihang.
 * @since 2017/4/13 0013.
 */

public class LoginRequest extends BaseModel {
    private String mobile;
    private String password;
    public LoginRequest(String mobile,String password) {
        this.mobile=mobile;
        this.password=password;
    }
    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
