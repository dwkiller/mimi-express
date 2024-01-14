package com.mimi.guest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.param.ListParam;
import com.mimi.core.express.entity.config.SysDict;
import com.mimi.core.express.entity.config.SysDictItem;
import com.mimi.core.express.service.SysDictItemService;
import com.mimi.core.express.service.SysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2019-03-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dict")
@Tag(name = "字典管理模块")
public class DictController {

    private final SysDictItemService sysDictItemService;

    private final SysDictService sysDictService;

    /**
     * 返回所有字典
     *
     * @return 字典信息
     */
    @Operation(summary = "返回所有字典")
    @GetMapping("/listDict")
    public R<List<SysDict>> listDict() throws Exception {
        ListParam listParam = new ListParam();
        return R.success(sysDictService.getByParam(listParam));
    }

    /**
     * 通过ID查询字典信息
     *
     * @param id ID
     * @return 字典信息
     */
    @Operation(summary = "通过ID查询字典信息")
    @GetMapping("/{id}")
    public R<SysDict> getById(@PathVariable String id) {
        return R.success(sysDictService.getById(id));
    }

    /**
     * 通过字典类型查找字典
     *
     * @param type 类型
     * @return 同类型字典
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "通过字典类型查找字典")
    public R<List<SysDictItem>> getDictByType(@PathVariable String type) {
        return R.success(sysDictItemService.getListByType(type));
    }

    /**
     * 分页查询
     *
     * @param page        分页对象
     * @param sysDictItem 字典项
     * @return
     */
    @Operation(summary = "分页查询字典项")
    @GetMapping("/item/page")
    public R<IPage<SysDictItem>> getSysDictItemPage(Page page, SysDictItem sysDictItem) {
        return R.success(sysDictItemService.page(page, Wrappers.query(sysDictItem)));
    }


    /**
     * 查询所有字典项
     *
     * @return
     */
    @Operation(summary = "分页查询字典项")
    @GetMapping("/allItem")
    public R<List<SysDictItem>> getAllDictItem() {
        return R.success(sysDictItemService.getAllDictItem());
    }


}
