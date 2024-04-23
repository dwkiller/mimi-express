package com.mimi.core.express.service.impl;

import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.config.Area;
import com.mimi.core.express.entity.config.Building;
import com.mimi.core.express.entity.config.SysDict;
import com.mimi.core.express.entity.config.SysDictItem;
import com.mimi.core.express.mapper.BuildingMapper;
import com.mimi.core.express.service.BuildingService;
import com.mimi.core.express.service.SysDictItemService;
import com.mimi.core.express.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class BuildingServiceImpl extends TenantServiceImpl<BuildingMapper, Building> implements BuildingService {

    @Autowired
    private AreaServiceImpl areaService;

    @Autowired
    private SysDictItemService sysDictItemService;

    @Autowired
    private SysDictService sysDictService;

    @Override
    @Transactional
    public boolean updateById(Building building){
        Building oldBuilding = super.getById(building.getId());
        boolean b = super.updateById(building);
        if(b&&!oldBuilding.getName().equals(building.getName())){
            Area area = areaService.getById(building.getAreaId());
            SysDict sysDict = getDict();
            List<SysDictItem> dictItemList = sysDictItemService.getListByDictAndLabel(
                    sysDict.getId(),area.getName()+"-"+oldBuilding.getName());
            if(dictItemList!=null&&dictItemList.size()>0){
                for(SysDictItem sysDictItem : dictItemList){
                    sysDictItem.setLabel(area.getName()+"-"+building.getName());
                    sysDictItemService.updateById(sysDictItem);
                }
            }
        }
        return b;
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id){
        Building oldBuilding = super.getById(id);
        boolean b = super.removeById(id);
        if(b){
            Area area = areaService.getById(oldBuilding.getAreaId());
            SysDict sysDict = getDict();
            sysDictItemService.removeByByDictAndLabel(sysDict.getId(),area.getName()+"-"+oldBuilding.getName());
        }
        return b;
    }

    @Override
    @Transactional
    public boolean save(@Valid Building building){
        boolean b = super.save(building);
        if(b){
            Area area = areaService.getById(building.getAreaId());
            SysDict sysDict = getDict();
            createDictItem(sysDict.getId(),area.getName()+"-"+building.getName());
        }
        return b;
    }

    private SysDict getDict(){
        SysDict sysDict = sysDictService.getByType("userAddress");
        if(sysDict==null){
            sysDict = new SysDict();
            sysDict.setSchoolId(userInfoUtil.getSchoolId());
            sysDict.setSysDict((short) 1);
            sysDict.setType("userAddress");
            sysDict.setTypeName("用户地址");
            sysDictService.save(sysDict);
        }
        return sysDict;
    }

    private SysDictItem createDictItem(String dictId,String label){
        SysDictItem sysDictItem = new SysDictItem();
        sysDictItem.setDictId(dictId);
        sysDictItem.setLabel(label);
        sysDictItemService.save(sysDictItem);
        return sysDictItem;
    }
}
