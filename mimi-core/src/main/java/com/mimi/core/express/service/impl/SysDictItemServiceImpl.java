package com.mimi.core.express.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mimi.core.common.util.UserInfoUtil;
import com.mimi.core.express.entity.config.SysDict;
import com.mimi.core.express.entity.config.SysDictItem;
import com.mimi.core.express.mapper.SysDictItemMapper;
import com.mimi.core.express.service.SysDictItemService;
import com.mimi.core.express.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
     * @param dictId
     * @return
     */
    @Override
    public List<SysDictItem> getListByDictId(String dictId) {
        return list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getDictId, dictId));
    }

    @Override
    public List<SysDictItem> getListByDictAndStartLabel(String dictId, String startLabel) {
        return list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getDictId, dictId)
        .likeLeft(SysDictItem::getLabel,startLabel));
    }

    @Override
    public void removeByByDictAndStartLabel(String dictId, String startLabel) {
        remove(Wrappers.<SysDictItem>update().lambda().eq(SysDictItem::getDictId, dictId)
                .likeLeft(SysDictItem::getLabel,startLabel));
    }

    @Override
    public List<SysDictItem> getListByDictAndLabel(String dictId, String label) {
        return list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getDictId, dictId)
                .eq(SysDictItem::getLabel,label));
    }

    @Override
    public void removeByByDictAndLabel(String dictId, String label) {
        remove(Wrappers.<SysDictItem>update().lambda().eq(SysDictItem::getDictId, dictId)
                .eq(SysDictItem::getLabel,label));
    }

    @Override
    public List<SysDictItem> getListByType(String type) {
        SysDict sysDict = dictService.getByType(type);
        if(sysDict==null){
            return null;
        }
        return getListByDictId(sysDict.getId());
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
            List<SysDict> sysDictList = dictService.list();
            for(SysDictItem sysDictItem:dictItems){
                Optional<SysDict> optional = sysDictList.stream().filter(
                        d->d.getId().equals(sysDictItem.getDictId())).findFirst();
                if(optional.isPresent()){
                    sysDictItem.setType(optional.get().getType());
                    sysDictItem.setTypeName(optional.get().getTypeName());
                }
            }
        }else{
            dictItems = baseMapper.getAllItem(schoolId);
        }
        return dictItems;
    }
}
