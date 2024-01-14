package com.mimi.core.express.service.impl;

import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.config.Building;
import com.mimi.core.express.mapper.BuildingMapper;
import com.mimi.core.express.service.BuildingService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class BuildingServiceImpl extends TenantServiceImpl<BuildingMapper, Building> implements BuildingService {

}
