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
package com.mimi.express.service;

import com.mimi.common.superpackage.service.ISuperService;
import com.mimi.express.entity.config.SysDict;

import java.util.List;


/**
 * 字典表
 *
 * @author lengleng
 * @date 2019/03/19
 */
public interface SysDictService extends ISuperService<SysDict> {

    /**
     * 根据ID 删除字典
     *
     * @param id
     * @return
     */
    void removeDict(String id);

    /**
     * 新增字典
     *
     * @param sysDict 字典
     * @return
     */
    Boolean saveDict(SysDict sysDict);

    /**
     * 更新字典
     *
     * @param sysDict 字典
     * @return
     */
    void updateDict(SysDict sysDict);

    /**
     * 更新字典
     *
     * @param types 字典类型
     * @return
     */
    List<SysDict> getByTypes(List<String> types);

}
