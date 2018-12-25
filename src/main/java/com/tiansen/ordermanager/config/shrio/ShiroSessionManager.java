package com.tiansen.ordermanager.config.shrio;

import com.tiansen.ordermanager.config.SpringBootBean;
import com.tiansen.ordermanager.config.cache.EhcacheService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class ShiroSessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "Authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    private EhcacheService ehcacheService;

    public ShiroSessionManager(){
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        ehcacheService = (EhcacheService) SpringBootBean.getBean("ehcacheService");
        String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        String id = ehcacheService.get(token, String.class);
        if (!StringUtils.isBlank(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }
        return super.getSessionId(request, response);
    }
}
