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

package com.mimi.express.mapper;

import com.mimi.common.superpackage.mapper.SuperMapper;
import com.mimi.express.entity.config.SysDict;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author lengleng
 * @since 2017-11-19
 */
@Mapper
public interface SysDictMapper extends SuperMapper<SysDict> {
    /**
     * 删除所有数据字典
     * @return
     */
    @Delete(" delete from sys_dict ")
    int deleteAllDict();

}
