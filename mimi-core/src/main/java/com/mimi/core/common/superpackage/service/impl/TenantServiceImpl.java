package com.mimi.core.common.superpackage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.core.common.superpackage.base.TenantEntity;
import com.mimi.core.common.superpackage.mapper.SuperMapper;
import com.mimi.core.common.superpackage.param.Filter;
import com.mimi.core.common.superpackage.param.ListParam;
import com.mimi.core.common.superpackage.param.PageParam;
import com.mimi.core.common.superpackage.param.Rule;
import com.mimi.core.common.util.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Slf4j
public abstract class TenantServiceImpl<M extends SuperMapper<T>, T extends TenantEntity> extends SuperServiceImpl<M, T>{

    @Autowired
    protected UserInfoUtil userInfoUtil;

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

}
