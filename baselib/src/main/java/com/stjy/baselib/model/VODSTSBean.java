package com.stjy.baselib.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author daifalin
 * @date 2019/3/29 2:48 PM
 * @ClassName VODSTSBean
 * @Description
 */
public class VODSTSBean {

    @SerializedName("accessKeyId")
    private String accessKeyId;
    @SerializedName("accessKeySecret")
    private String accessKeySecret;
    @SerializedName("securityToken")
    private String securityToken;
    @SerializedName("expiration")
    private String expiration;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
