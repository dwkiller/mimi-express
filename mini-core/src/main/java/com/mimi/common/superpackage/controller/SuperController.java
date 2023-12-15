package com.mimi.common.superpackage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.common.R;
import com.mimi.common.superpackage.param.Filter;
import com.mimi.common.superpackage.param.ListParam;
import com.mimi.common.superpackage.param.PageParam;
import com.mimi.common.superpackage.service.ISuperService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


public class SuperController<M extends ISuperService<T>, T> {

    @Autowired
    protected M superService;


    @Operation(summary = "查询全部实体")
    @GetMapping("")
    public R<List<T>> list() throws Exception {
        ListParam listParam = new ListParam();
        return R.success(superService.getByParam(listParam));
    }

    @Operation(summary = "查询分页查询实体")
    @PostMapping("/page")
    public R<IPage<T>> page(@RequestBody PageParam pageParam) throws Exception {
        return R.success(superService.pageByParam(pageParam));
    }

    @Operation(summary = "根据id查询实体")
    @GetMapping("/{id}")
    public R<T> getById(@PathVariable String id) {
        return R.success(superService.getById(id));
    }

    @Operation(summary = "根据条件查询")
    @PostMapping("/param")
    public R<List<T>> getByParam(@RequestBody ListParam listParam) throws Exception {
        return R.success(superService.getByParam(listParam));
    }

    @Operation(summary = "根据id修改实体")
    @PutMapping("")
    public R<Boolean> updateById(@RequestBody @Valid T t) {
        return R.success(superService.updateById(t));
    }

    @Operation(summary = "新增实体")
    @PostMapping("")
    public R<Boolean> save(@RequestBody @Valid  T t) {
        return R.success(superService.save(t));
    }

    @Operation(summary = "删除实体")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.success(superService.removeById(id));
    }

    @Operation(summary = "带条件删除")
    @DeleteMapping("/param")
    public R deleteByParam(@RequestBody Filter filter) throws Exception {
        superService.deleteByParam(filter);
        return R.success(true);
    }
}
