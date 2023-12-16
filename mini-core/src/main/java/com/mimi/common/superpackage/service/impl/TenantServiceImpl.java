package com.mimi.common.superpackage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.common.superpackage.base.TenantEntity;
import com.mimi.common.superpackage.mapper.SuperMapper;
import com.mimi.common.superpackage.param.Filter;
import com.mimi.common.superpackage.param.ListParam;
import com.mimi.common.superpackage.param.PageParam;
import com.mimi.common.superpackage.param.Rule;
import com.mimi.common.util.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
    public boolean save(@Valid T t) {
        addSchool(t);
        return super.save(t);
    }

    @Override
    public boolean updateById(@Valid T t) {
        addSchool(t);
        return super.updateById(t);
    }

    @Override
    public int insertIgnore(T entity) {
        addSchool(entity);
        return superMapper.insertIgnore(entity);
    }

    @Override
    public int insertIgnoreBatch(List<T> entityList) {
        if(entityList!=null){
            for(T t:entityList){
                addSchool(t);
            }
        }
        return superMapper.insertIgnoreBatch(entityList);
    }

    @Override
    public int replace(T entity) {
        addSchool(entity);
        return superMapper.replace(entity);
    }

    @Override
    public int replaceBatch(List<T> entityList) {
        if(entityList!=null){
            for(T t:entityList){
                addSchool(t);
            }
        }
        return superMapper.replaceBatch(entityList);
    }

}
