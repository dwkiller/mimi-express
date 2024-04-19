package com.mimi.express.controller;

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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        LinkedHashMap<String,String> sort = new LinkedHashMap();
        sort.put("sysDict","asc");
        listParam.setSorts(sort);
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
     * 添加字典
     *
     * @param sysDict 字典信息
     * @return success、false
     */
    @PostMapping
    @Operation(summary = "添加字典")
    public R<Boolean> save(@Valid @RequestBody SysDict sysDict) {
        return R.success(sysDictService.saveDict(sysDict));
    }

    /**
     * 删除字典
     *
     * @param id ID
     * @return R
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除字典")
    public R removeById(@PathVariable String id) {
        sysDictService.removeDict(id);
        return R.success("删除成功");
    }

    /**
     * 修改字典
     *
     * @param sysDict 字典信息
     * @return success/false
     */
    @PutMapping
    @Operation(summary = "修改字典")
    public R updateById(@Valid @RequestBody SysDict sysDict) {
        // TODO 新增和修改字典以及字典项的时候需要判断是否已存在
        sysDictService.updateDict(sysDict);
        return R.success("修改成功");
    }


    /**
     * 新增字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @PostMapping("/item")
    @Operation(summary = "新增字典项")
    public R<Boolean> save(@RequestBody SysDictItem sysDictItem) {
        return R.success(sysDictItemService.saveItem(sysDictItem));
    }

    /**
     * 修改字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @PutMapping("/item")
    @Operation(summary = "修改字典项")
    public R updateById(@RequestBody SysDictItem sysDictItem) {
        sysDictItemService.updateDictItem(sysDictItem);
        return R.success("修改成功");
    }


    /**
     * 通过id删除字典项
     *
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id删除字典项")
    @DeleteMapping("/item/{id}")
    public R removeDictItemById(@PathVariable String id) {
        sysDictItemService.removeDictItem(id);
        return R.success("删除成功");
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
