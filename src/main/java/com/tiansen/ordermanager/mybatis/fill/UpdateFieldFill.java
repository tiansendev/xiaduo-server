package com.tiansen.ordermanager.mybatis.fill;

import com.tiansen.ordermanager.mybatis.entity.SysUser;
import org.apache.shiro.SecurityUtils;

import java.lang.reflect.Field;

public class UpdateFieldFill {
    private static final String MANAGERID="managerId";
    public static void fill(Object obj){
       fill(obj,MANAGERID);
    }
    public static void fill(Object obj, String updatePropertyName){
       try {
           Class c = obj.getClass();
           SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
           Field fupdate = c.getDeclaredField(updatePropertyName);
           fupdate.setAccessible(true);
           fupdate.set(obj, sysUser.getId());
       }catch (Exception ex){
           ex.printStackTrace();
       }
    }
}
