package com.hgsoft.zengzhiyingyong.security.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义带验证码的token
 */
public class LoginToken extends UsernamePasswordToken {

    public String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LoginToken(String username, String password, String code) {
        super(username, password);
        this.code = code;
//      super.setPassword(passWord != null ? passWord.toCharArray() : null);
//      super.setUsername(username);
    }

    public LoginToken(final String username, final String passWord, final String code, final boolean rememberMe) {
        this.code = code;
        super.setPassword(passWord != null ? passWord.toCharArray() : null);
        super.setUsername(username);
        super.setRememberMe(rememberMe);
    }
}
