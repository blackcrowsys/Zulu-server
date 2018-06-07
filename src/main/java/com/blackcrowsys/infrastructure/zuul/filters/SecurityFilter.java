package com.blackcrowsys.infrastructure.zuul.filters;

import com.blackcrowsys.infrastructure.zuul.auth.AuthService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class SecurityFilter extends ZuulFilter {

    private static final String AUTH = "Authorization";
    private static final String AUTH_URL = "/auth/authenticate";

    @Autowired
    private AuthService authService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("Header " + AUTH + ":" + request.getHeader(AUTH));
        log.info("URL: " + request.getRequestURI());
        log.info("Method: " + request.getMethod());
        if (!request.getRequestURI().equals(AUTH_URL)) {
            if (!authService.isAuthenticated(request.getHeader(AUTH))) {
                try {
                    ctx.getResponse().sendRedirect(AUTH_URL);
                } catch (IOException e) {
                    log.error("Error prefilter: " + e.getMessage());
                }
            }
        }
        return null;
    }
}
