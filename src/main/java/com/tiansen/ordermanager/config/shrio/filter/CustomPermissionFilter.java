package com.tiansen.ordermanager.config.shrio.filter;

import com.tiansen.ordermanager.common.code.ServiceResultCode;
import com.tiansen.ordermanager.common.util.HttpExpResponseHelper;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CustomPermissionFilter extends PermissionsAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        HttpServletRequest req=(HttpServletRequest)request;
        String url=req.getRequestURI();
        String method=req.getMethod().toLowerCase();
        boolean isPermitted=false;
//        if (subject.isPermitted(method+url)) {
//            isPermitted = true;
//        }
        // TODO: 18-11-21 测试用
        if (true/*subject.isAuthenticated()*/)
            isPermitted = true;
        return isPermitted;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            HttpExpResponseHelper.responseNoPermission(response, ServiceResultCode.TOKEN_EXPIRE.getCode(), null);
        } else {
            HttpExpResponseHelper.responseNoPermission(response, ServiceResultCode.PERMISSION_NO_ENOUGH.getCode(), null);
        }
        return false;
    }

}
