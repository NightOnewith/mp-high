package com.ethan.interceptors;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.ethan.utils.MyTenantHandler;
import com.ethan.utils.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ASUS on 2020/2/9.
 */
@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Autowired
    private TenantContext tenantContext;

    @Autowired
    private PaginationInterceptor pi;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("进入拦截器~~");
        String path=httpServletRequest.getRequestURI();
        System.out.println(path);
        String token = path.substring(path.lastIndexOf("/") + 1);
        System.out.println(token);
        if (isTokenValid(token)) {
            prepareTenantContext(token);
            return true;
        }

        return false;
    }

    private void prepareTenantContext(String token) {
        TenantSqlParser tenantSqlParser = (TenantSqlParser) pi.getSqlParserList().get(0);
        MyTenantHandler myTenantHandler = (MyTenantHandler) tenantSqlParser.getTenantHandler();
        myTenantHandler.setTenantId(tenantContext.getTenantIdWithToken(token));
        System.out.println(token);
    }

    private boolean isTokenValid(String token) {
        System.out.println("isTokenValid " + token);
        return null != tenantContext.getTenantIdWithToken(token);
    }
}
