package com.mimi.express.service.impl;

import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.config.Area;
import com.mimi.express.mapper.AreaMapper;
import com.mimi.express.service.AreaService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class AreaServiceImpl extends TenantServiceImpl<AreaMapper, Area> implements AreaService {

}
