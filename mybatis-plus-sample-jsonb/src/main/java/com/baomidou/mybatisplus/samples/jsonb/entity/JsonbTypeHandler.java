package com.baomidou.mybatisplus.samples.jsonb.entity;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@MappedTypes({Object.class})
//  FastjsonTypeHandler 同理继承使用
// 更多使用查看文档 https://baomidou.com/guides/type-handler/#%E8%87%AA%E5%AE%9A%E4%B9%89%E7%B1%BB%E5%9E%8B%E5%A4%84%E7%90%86%E5%99%A8
public class JsonbTypeHandler extends JacksonTypeHandler {

    public JsonbTypeHandler(Class<?> type) {
        super(type);
    }

    // 自3.5.6版本开始支持泛型,需要加上此构造.
    public JsonbTypeHandler(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (ps != null) {
            PGobject jsonObject = new PGobject();
            jsonObject.setType("jsonb");
            jsonObject.setValue(toJson(parameter));
            ps.setObject(i, jsonObject);
        }
    }
}
