package com.tiansen.ordermanager.common.util;

import com.alibaba.fastjson.JSON;
import com.tiansen.ordermanager.common.model.ServiceResult;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpExpResponseHelper {
    /**
     * 返回Response异常信息
     */
    public static void responseNoPermission(ServletResponse resp, String code, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
            if (msg != null)
                writer.write(JSON.toJSONString(ServiceResult.failure(code, msg)));
            else {
                writer.write(JSON.toJSONString(ServiceResult.failure(code)));
            }
            writer.flush();
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }
}
