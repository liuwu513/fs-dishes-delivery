package com.fs.dishes.config.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * Created by liuwu on 2018/2/28 0028.
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
