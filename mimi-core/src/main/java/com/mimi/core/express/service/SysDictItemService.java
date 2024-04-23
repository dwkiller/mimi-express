/*
 * Copyright (c) 2020 cloud4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mimi.core.express.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mimi.core.express.entity.config.SysDictItem;

import java.util.List;

/**
 * 字典项
 *
 * @author lengleng
 * @date 2019/03/19
 */
public interface SysDictItemService extends IService<SysDictItem> {

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     * @return
     */
    void removeDictItem(String id);

    /**
     * 更新字典项
     *
     * @param item 字典项
     * @return
     */
    void updateDictItem(SysDictItem item);

    /**
     * 根据字典ID查字典项列表
     *
     * @param dictId
     * @return
     */
    List<SysDictItem> getListByDictId(String dictId);

    List<SysDictItem> getListByDictAndStartLabel(String dictId,String startLabel);

    void removeByByDictAndStartLabel(String dictId,String startLabel);

    List<SysDictItem> getListByDictAndLabel(String dictId,String label);

    void removeByByDictAndLabel(String dictId,String label);

    List<SysDictItem> getListByType(String type);
    /**
     * 新增字段项
     *
     * @param sysDictItem
     * @return
     */
    Boolean saveItem(SysDictItem sysDictItem);

    /**
     * 查询所有字典项的接口
     * @return
     */
    List<SysDictItem> getAllDictItem();
}
