package com.securetemplate.config;


public interface SimpleResponse {
    SimpleResponse error(String errorMessage);
    SimpleResponse finish();
    void setPayload(Object payload);
    void setSecurityToken(String token);
    String getSecurityToken();
    SimpleResponse ok();
}
