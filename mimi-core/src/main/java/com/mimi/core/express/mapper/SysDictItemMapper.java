package com.mimi.core.express.mapper;

import com.mimi.core.common.superpackage.mapper.SuperMapper;
import com.mimi.core.express.entity.config.SysDictItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典项
 *
 * @author lengleng
 * @date 2019/03/19
 */
@Mapper
public interface SysDictItemMapper extends SuperMapper<SysDictItem> {

    @Select("select if(o_max > c_num,(o_max+1),c_num+1) as sort_order from (select max(sort_order) o_max, count(*) c_num from sys_dict_item b where type = #{type} and del_flag = '0')a")
    int getNextSortOrder(@Param("type") String type);

    /**
     * 删除所有数据字典项
     * @return
     */
    @Delete(" delete from sys_dict_item ")
    int deleteAllDicItem();

    @Select("select sdi.*,sd.type_name from sys_dict_item sdi,sys_dict sd where sdi.type=sd.type and sd.school_id=#{schoolId}")
    List<SysDictItem> getAllItem(@Param("schoolId") String schoolId);

}
