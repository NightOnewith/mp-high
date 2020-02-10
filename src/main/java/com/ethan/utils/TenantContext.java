package com.ethan.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 2020/2/9.
 */
public class TenantContext {

    private static final Map<String, Object> contextMap = new HashMap<>();

    public void putTokenTenantIdPair(String token, Long tenantId) {
        contextMap.put(token, tenantId);
    }

    public Long getTenantIdWithToken(String token) {
        return (Long) contextMap.get(token);
    }
}
