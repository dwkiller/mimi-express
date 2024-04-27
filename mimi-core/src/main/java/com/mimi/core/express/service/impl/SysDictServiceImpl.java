package com.mimi.core.express.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.config.SysDict;
import com.mimi.core.express.entity.config.SysDictItem;
import com.mimi.core.express.mapper.SysDictItemMapper;
import com.mimi.core.express.mapper.SysDictMapper;
import com.mimi.core.express.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典表
 *
 * @author lengleng
 * @date 2019/03/19
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends TenantServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private final SysDictItemMapper dictItemMapper;


    /**
     * 根据ID 删除字典
     *
     * @param id 字典ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeDict(String id) {
        super.removeById(id);
        dictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, id));
    }

    /**
     * 更新字典
     *
     * @param dict 字典
     * @return
     */
    @Override
    public void updateDict(SysDict dict) {
        this.updateById(dict);
    }

    @Override
    public List<SysDict> getByTypes(List<String> types) {
        LambdaQueryWrapper<SysDict> sysDictLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictLambdaQueryWrapper.in(SysDict::getType,types);
        return this.list(sysDictLambdaQueryWrapper);
    }

    @Override
    public SysDict getByType(String type){
        LambdaQueryWrapper<SysDict> sysDictLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictLambdaQueryWrapper.eq(SysDict::getType,type);
        return this.getOne(sysDictLambdaQueryWrapper);
    }

    /**
     * 新增字典
     *
     * @param dict
     * @return
     */
    @Override
    public Boolean saveDict(SysDict dict) {
//        if (isExistByCode(dict.getId(), dict.getType())) {
//            throw new RuntimeException("字典类型数据唯一，不能重复");
//        }
        this.save(dict);
        return Boolean.TRUE;
    }

    /**
     * 根据类型查字典信息
     *
     * @param id
     * @param type
     * @return
     */
    private Boolean isExistByCode(String id, String type) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<SysDict>()
                .apply(" upper(type) = upper({0}) ", type)
                .ne(StrUtil.isNotEmpty(id), SysDict::getId, id);
        return ObjectUtil.isNotNull(this.getOne(queryWrapper));
    }


}
