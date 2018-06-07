package com.blackcrowsys.infrastructure.zuul.auth;

public interface AuthService {
    boolean isAuthenticated(String header);
}
