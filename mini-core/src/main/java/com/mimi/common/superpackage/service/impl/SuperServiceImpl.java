package com.mimi.common.superpackage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mimi.common.superpackage.mapper.SuperMapper;
import com.mimi.common.superpackage.param.Filter;
import com.mimi.common.superpackage.param.ListParam;
import com.mimi.common.superpackage.param.PageParam;
import com.mimi.common.superpackage.service.ISuperService;
import com.mimi.common.superpackage.util.WrapperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * service实现父类
 *
 * @author pig
 * @date 2019/1/10
 * <p>
 */
public abstract class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements ISuperService<T> {

    @Autowired
    protected M superMapper;

    /**
     * 带参数的分页查询
     *
     * @param pageParam
     * @return
     */
    @Override
    public IPage<T> pageByParam(PageParam pageParam) throws Exception {
        // 构造分页参数
        long pageNum = null == pageParam.getPageNum() ? 1 : pageParam.getPageNum();
        long pageSize = null == pageParam.getPageSize() ? 10 : pageParam.getPageSize();
        // 查询
        IPage<T> iPage = superMapper.selectPage(new Page<>(pageNum, pageSize), this.buildQueryWrapper(pageParam.getFilter(), pageParam.getSorts()));
        return iPage;
    }

    /**
     * 带条件删除
     *
     * @param filter
     */
    @Override
    public void deleteByParam(Filter filter) throws Exception {
        if (filter == null || CollectionUtils.isEmpty(filter.getRules())) {
            return;
        }
        this.remove(this.buildQueryWrapper(filter));
    }

    /**
     * 根据条件查询
     *
     * @param listParam
     * @return
     */
    @Override
    public List<T> getByParam(ListParam listParam) throws Exception {
        return this.list(buildQueryWrapper(listParam.getFilter(), listParam.getSorts()));
    }

    @Override
    public boolean save(@Valid T t) {
        return super.save(t);
    }

    @Override
    public boolean updateById(@Valid T t) {
        return super.updateById(t);
    }

    /**
     * 插入数据，如果中已经存在相同的记录，则忽略当前新数据
     *
     * @param entity 实体类
     * @return 影响条数
     */
    @Override
    public int insertIgnore(T entity) {
        return superMapper.insertIgnore(entity);
    }

    /**
     * 批量插入数据，如果中已经存在相同的记录，则忽略当前新数据
     *
     * @param entityList 实体类列表
     * @return 影响条数
     */
    @Override
    public int insertIgnoreBatch(List<T> entityList) {
        return superMapper.insertIgnoreBatch(entityList);
    }

    /**
     * 替换数据
     * replace into表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
     *
     * @param entity 实体类
     * @return 影响条数
     */
    @Override
    public int replace(T entity) {
        return superMapper.replace(entity);
    }

    /**
     * 批量替换数据
     * replace into表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
     *
     * @param entityList 实体类列表
     * @return 影响条数
     */
    @Override
    public int replaceBatch(List<T> entityList) {
        return superMapper.replaceBatch(entityList);
    }

    /**
     * 构造查询条件
     *
     * @param filter
     * @return
     */
    protected QueryWrapper<T> buildQueryWrapper(Filter filter) throws Exception {
        return buildQueryWrapper(filter, null);
    }

    /**
     * 构造查询条件
     *
     * @param filter
     * @param sorts
     * @return
     */
    protected QueryWrapper<T> buildQueryWrapper(Filter filter, LinkedHashMap<String, String> sorts) throws Exception {
        // 获取泛型的类型
        Class<T> entityClass = null;
        try {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        // 构建查询条件
        WrapperBuilder wrapperBuilder = new WrapperBuilder<T>(entityClass);
        QueryWrapper<T> wrapper = wrapperBuilder.buildByParam(filter, sorts);
        return wrapper;
    }
}
