package com.mimi.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mimi.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/12/7 15:01
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        //三个参数：字段名，字段值，元对象参数
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);

        this.setFieldValByName("createBy", LoginUtil.getUserId(),metaObject);
        this.setFieldValByName("updateBY", LoginUtil.getUserId(),metaObject);
    }
    //修改时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}

