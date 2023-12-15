package com.mimi.common.superpackage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mimi.common.superpackage.param.Filter;
import com.mimi.common.superpackage.param.ListParam;
import com.mimi.common.superpackage.param.PageParam;

import java.util.List;

/**
 * service接口父类
 *
 * @author pig
 * <p>
 */
public interface ISuperService<T> extends IService<T> {

    IPage<T> pageByParam(PageParam pageParam) throws Exception;

    void deleteByParam(Filter filter) throws Exception;

    List<T> getByParam(ListParam listParam) throws Exception;

    @Override
    boolean save(T t);

    @Override
    boolean updateById(T t);
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
