package com.tiansen.ordermanager.mybatis.fill;

import com.tiansen.ordermanager.mybatis.entity.SysUser;
import org.apache.shiro.SecurityUtils;

import java.lang.reflect.Field;
import java.util.Date;

public class CreateFieldFill {
    private static final String CREATEID="creatorId";
    private static final String CREATEDATE="createDate";

    public static void fill(Object obj) {
        fill(obj,CREATEID,CREATEDATE);
    }
    public static void fill(Object obj, String creatorPropertyName,String datePropertyName){
        try {
            Class c = obj.getClass();
            SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();;
            Field fcreator = c.getDeclaredField(creatorPropertyName);
            Field fdate = c.getDeclaredField(datePropertyName);
            fcreator.setAccessible(true);
            fdate.setAccessible(true);
            fcreator.set(obj, sysUser.getId());
            fdate.set(obj, new Date(System.currentTimeMillis()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
