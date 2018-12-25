package com.tiansen.ordermanager.mybatis.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 继承自BaseTypeHandler<Object[]> 使用时传入的参数一定要是Object[]，例如 int[]是 Object, 不是Object[]，所以传入int[] 会报错的
public class MybatisArrayHandler extends BaseTypeHandler<Object[]> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object[] t, JdbcType jdbcType) throws SQLException {
        try {
            preparedStatement.setString(i, JSON.toJSONString(t));
        } catch (Exception var6) {
            throw new TypeException("Error setting non null for parameter #" + i + " with JdbcType " + jdbcType + " . Try setting a different JdbcType for this parameter or a different configuration property. Cause: " + var6, var6);
        }
    }

    @Override
    public Object[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String result=resultSet.getString(s);
        return parse(result);
    }

    @Override
    public Object[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String result=resultSet.getString(i);
        return parse(result);
    }

    @Override
    public Object[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String result=callableStatement.getString(i);
        return parse(result);
    }
    private Object[] parse(String json) {
        if(json == null || json.length() == 0) {
            return null;
        }
        return JSON.parseObject(json,new TypeReference<Object[]>(){});
    }

}