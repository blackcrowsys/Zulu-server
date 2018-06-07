package com.blackcrowsys.infrastructure.zuul.auth;

import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HazelcastAuthService implements AuthService {

    private static final String AUTH_MAP = "authMap";

    @Autowired
    private HazelcastInstance instance;

    @Override
    public boolean isAuthenticated(String header) {
        if (StringUtils.isEmpty(header))
            return false;
        Map<String, Object> authMap = instance.getMap(AUTH_MAP);
        return authMap.containsKey(header);
    }
}
