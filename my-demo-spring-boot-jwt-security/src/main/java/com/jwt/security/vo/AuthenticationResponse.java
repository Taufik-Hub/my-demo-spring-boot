package com.jwt.security.vo;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
	private static final long serialVersionUID = -4167604405850911286L;
	private final String jwt;
    private long expirationTime;
    private String userName;

    public AuthenticationResponse(String jwt,long expirationTime,String userName) {
        this.jwt = jwt;
        this.expirationTime=expirationTime;
        this.userName=userName;
    }

    public String getJwt() {
        return jwt;
    }

	public long getExpirationTime() {
		return expirationTime;
	}

	public String getUserName() {
		return userName;
	}
	
}
