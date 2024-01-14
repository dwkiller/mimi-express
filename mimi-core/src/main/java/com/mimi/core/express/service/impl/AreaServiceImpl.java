package com.mimi.core.express.service.impl;

import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.config.Area;
import com.mimi.core.express.mapper.AreaMapper;
import com.mimi.core.express.service.AreaService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class AreaServiceImpl extends TenantServiceImpl<AreaMapper, Area> implements AreaService {

}
