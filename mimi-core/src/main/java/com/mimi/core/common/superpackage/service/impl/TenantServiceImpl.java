package com.mimi.core.common.superpackage.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.core.common.superpackage.base.TenantEntity;
import com.mimi.core.common.superpackage.mapper.SuperMapper;
import com.mimi.core.common.superpackage.param.Filter;
import com.mimi.core.common.superpackage.param.ListParam;
import com.mimi.core.common.superpackage.param.PageParam;
import com.mimi.core.common.superpackage.param.Rule;
import com.mimi.core.common.util.UserInfoUtil;
import com.mimi.core.express.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
public abstract class TenantServiceImpl<M extends SuperMapper<T>, T extends TenantEntity> extends SuperServiceImpl<M, T>{

    @Autowired
    protected UserInfoUtil userInfoUtil;

    @Override
    public T getOne(Wrapper<T> wrapper){
        addSchool(wrapper);
        return super.getOne(wrapper);
    }

    @Override
    public List<T> list(){
        Wrapper<T> wrapper = addSchool();
        return super.list(wrapper);
    }

    @Override
    public List<T> list(Wrapper<T> wrapper){
        addSchool(wrapper);
        return super.list(wrapper);
    }

    @Override
    public int count(){
        Wrapper<T> wrapper = addSchool();
        return super.count(wrapper);
    }

    @Override
    public int count(Wrapper<T> wrapper){
        addSchool(wrapper);
        return super.count(wrapper);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList){
        if(entityList!=null){
            for(T t: entityList){
                addSchool(t);
                if(t.getCreateTime()==null){
                    t.setCreateTime(new Date());
                }
            }
        }
        return super.saveBatch(entityList);
    }

    @Override
    public IPage<T> pageByParam(PageParam pageParam) throws Exception {
        pageParam.setFilter(addSchool(pageParam.getFilter()));
        return super.pageByParam(pageParam);
    }

    @Override
    public List<T> getByParam(ListParam listParam) throws Exception {
        listParam.setFilter(addSchool(listParam.getFilter()));
        return super.getByParam(listParam);
    }

    @Override
    @Transactional
    public boolean save(@Valid T t) {
        addSchool(t);
        t.setCreateTime(new Date());
        return super.save(t);
    }

    @Override
    @Transactional
    public boolean updateById(@Valid T t) {
        addSchool(t);
        return super.updateById(t);
    }

    @Override
    @Transactional
    public int insertIgnore(T entity) {
        addSchool(entity);
        return superMapper.insertIgnore(entity);
    }

    @Override
    @Transactional
    public int insertIgnoreBatch(List<T> entityList) {
        if(entityList!=null){
            for(T t:entityList){
                addSchool(t);
            }
        }
        return superMapper.insertIgnoreBatch(entityList);
    }

    @Override
    @Transactional
    public int replace(T entity) {
        addSchool(entity);
        return superMapper.replace(entity);
    }

    @Override
    @Transactional
    public int replaceBatch(List<T> entityList) {
        if(entityList!=null){
            for(T t:entityList){
                addSchool(t);
            }
        }
        return superMapper.replaceBatch(entityList);
    }

    private Wrapper<T> addSchool(Wrapper<T> wrapper){
        String schoolId = userInfoUtil.getSchoolId();
        if(!StringUtils.isEmpty(schoolId)) {
            if (wrapper instanceof LambdaQueryWrapper) {
                LambdaQueryWrapper<T> lambdaQueryWrapper = (LambdaQueryWrapper) wrapper;
                lambdaQueryWrapper.eq(T::getSchoolId, schoolId);
            } else if (wrapper instanceof QueryWrapper) {
                QueryWrapper<T> queryWrapper = (QueryWrapper) wrapper;
                queryWrapper.eq("school_id", schoolId);
            }
        }
        return wrapper;
    }

    private Wrapper<T> addSchool(){
        String schoolId = userInfoUtil.getSchoolId();
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper();
        if(!StringUtils.isEmpty(schoolId)) {
            wrapper.eq(T::getSchoolId, schoolId);
        }
        return wrapper;
    }

    private Filter addSchool(Filter filter) {
        String schoolId = userInfoUtil.getSchoolId();
        log.info(this.getClass().getName()+" : schoolId :"+schoolId);
        if(!StringUtils.isEmpty(schoolId)){
            Rule rule = new Rule();
            rule.setField("schoolId");
            rule.setOp("eq");
            rule.setData(schoolId);
            if(filter==null){
                filter=new Filter();
            }
            filter.addRule(rule);
        }
        return filter;
    }

    private void addSchool(T t){
        if(StringUtils.isEmpty(t.getSchoolId())){
            String schoolId = userInfoUtil.getSchoolId();
            if(!StringUtils.isEmpty(schoolId)){
                t.setSchoolId(schoolId);
            }
        }
    }
}
