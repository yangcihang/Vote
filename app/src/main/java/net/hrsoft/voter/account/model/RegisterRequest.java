package net.hrsoft.voter.account.model;

import net.hrsoft.voter.common.BaseModel;

/**
 * @author abtion.
 */

public class RegisterRequest extends BaseModel {
    private String mobile;
    private String name;
    private String password;

    public RegisterRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterRequest(String mobile, String name, String password) {


        this.mobile = mobile;
        this.name = name;
        this.password = password;
    }
}
