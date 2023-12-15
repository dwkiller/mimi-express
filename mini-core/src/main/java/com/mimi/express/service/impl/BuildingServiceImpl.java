package com.mimi.express.service.impl;

import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.config.Building;
import com.mimi.express.mapper.BuildingMapper;
import com.mimi.express.service.BuildingService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class BuildingServiceImpl extends TenantServiceImpl<BuildingMapper, Building> implements BuildingService {

}
