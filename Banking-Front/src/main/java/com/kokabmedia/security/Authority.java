package com.kokabmedia.security;

import org.springframework.security.core.GrantedAuthority;

/*
 * This class handles grating authority as part of Spring Security.
 */
public class Authority implements GrantedAuthority{

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
