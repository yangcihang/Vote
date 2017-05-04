package net.hrsoft.voter.home.model;

import net.hrsoft.voter.common.BaseModel;

/**
 * @author YangCihang.
 * @since 2017/4/23 0023.
 */

public class ModifyPasswordRequest extends BaseModel {
    private String oldPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    private String newPassword;
}
