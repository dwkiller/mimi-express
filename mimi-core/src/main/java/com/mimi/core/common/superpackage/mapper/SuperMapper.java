package com.mimi.core.common.superpackage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * mapper 父类，注意这个类不要让 mp 扫描到！！
 *
 */
public interface SuperMapper<T> extends BaseMapper<T> {
    // 这里可以放一些公共的方法

    /**
     * 插入数据，如果中已经存在相同的记录，则忽略当前新数据
     *
     *
     * @param entity 实体类
     * @return 影响条数
     */
    int insertIgnore(T entity);

    /**
     * 批量插入数据，如果中已经存在相同的记录，则忽略当前新数据
     *
     *
     * @param entityList 实体类列表
     * @return 影响条数
     */
    int insertIgnoreBatch(List<T> entityList);

    /**
     * 替换数据
     * replace into表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
     *
     *
     * @param entity 实体类
     * @return 影响条数
     */
    int replace(T entity);
    /**
     * 批量替换数据
     * replace into表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
     *
     *
     * @param entityList 实体类列表
     * @return 影响条数
     */
    int replaceBatch(List<T> entityList);
}
