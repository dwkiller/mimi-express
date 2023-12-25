package com.mimi.express.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mimi.common.util.UserInfoUtil;
import com.mimi.express.entity.config.SysDict;
import com.mimi.express.entity.config.SysDictItem;
import com.mimi.express.mapper.SysDictItemMapper;
import com.mimi.express.service.SysDictItemService;
import com.mimi.express.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典项
 *
 * @author lengleng
 * @date 2019/03/19
 */
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService{

    private final SysDictService dictService;

    @Autowired
    private UserInfoUtil userInfoUtil;

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     * @return
     */
    @Override
    @Transactional
    public void removeDictItem(String id) {
        // 根据ID查询字典ID
        SysDictItem dictItem = this.getById(id);
        this.removeById(id);
    }

    /**
     * 更新字典项
     *
     * @param item 字典项
     * @return
     */
    @Override
    @Transactional
    public void updateDictItem(SysDictItem item) {
        // 查询字典
        this.updateById(item);
    }

    /**
     * 根据type查字典项列表
     *
     * @param type
     * @return
     */
    @Override
    public List<SysDictItem> getListByType(String type) {
        return list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getType, type));
    }

    /**
     * 新增字典项数据
     * 同一个type下value、label值唯一
     * * @param sysDictItem
     *
     * @return
     */
    @Override
    @Transactional
    public Boolean saveItem(SysDictItem sysDictItem) {
        //设置排序值
        return this.save(sysDictItem);
    }

    @Override
    public List<SysDictItem> getAllDictItem() {
        List<SysDictItem> dictItems = null;
        String schoolId = userInfoUtil.getSchoolId();
        if(StringUtils.isBlank(schoolId))
        {
            dictItems = this.list();
            List<String> typeList = dictItems.stream().map(SysDictItem::getType).collect(Collectors.toList());
            List<SysDict> sysDictList = dictService.getByTypes(typeList);
            for (SysDictItem dictItem : dictItems) {
                for (SysDict sysDict : sysDictList) {
                    if (dictItem.getType().equals(sysDict.getType())) {
                        dictItem.setType(sysDict.getType());
                        dictItem.setTypeName(sysDict.getTypeName());
                    }
                }
            }
        }else{
            dictItems = baseMapper.getAllItem(schoolId);
        }
        return dictItems;
    }
}
