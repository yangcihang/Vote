package net.hrsoft.voter.account.model;

import net.hrsoft.voter.common.BaseModel;

/**
 * Created by abtion on 17/4/14.
 */

public class LoginResponse extends BaseModel {
    private User user;
    private Token token;

    public LoginResponse(User user, Token token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
