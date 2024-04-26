package com.mimi.core.express.service.impl;

import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.config.Area;
import com.mimi.core.express.entity.config.Building;
import com.mimi.core.express.entity.config.SysDict;
import com.mimi.core.express.entity.config.SysDictItem;
import com.mimi.core.express.mapper.AreaMapper;
import com.mimi.core.express.service.AreaService;
import com.mimi.core.express.service.BuildingService;
import com.mimi.core.express.service.SysDictItemService;
import com.mimi.core.express.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class AreaServiceImpl extends TenantServiceImpl<AreaMapper, Area> implements AreaService {

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SysDictItemService sysDictItemService;

    @Override
    @Transactional
    public boolean updateById(Area area){
        boolean b = super.updateById(area);
        if(b){
            SysDict sysDict = getDict();
            List<SysDictItem> sysDictItemList = sysDictItemService
                    .getListByDictAndStartLabel(sysDict.getId(),area.getName());
            if(sysDictItemList!=null&&sysDictItemList.size()>0){
                for(SysDictItem sysDictItem:sysDictItemList){
                    String label = sysDictItem.getLabel();
                    if(label==null){
                        continue;
                    }
                    String areaLabe = label.split("-")[0];
                    label.replaceFirst(areaLabe+"-",area.getName());
                    sysDictItem.setLabel(label);
                    sysDictItemService.updateById(sysDictItem);
                }
            }
        }
        return b;
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        Area area = super.getById(id);
        boolean b = super.removeById(id);
        if(b){
            SysDict sysDict = getDict();
            sysDictItemService.removeByByDictAndStartLabel(sysDict.getId(),area.getName());
        }
        return b;
    }

    @Override
    @Transactional
    public boolean save(@Valid Area area) {
        boolean b = super.save(area);
        if(b){
            SysDict sysDict = getDict();
            createDictItem(sysDict.getId(),area.getName());
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
            sysDict.setTypeName("送货地点");
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
