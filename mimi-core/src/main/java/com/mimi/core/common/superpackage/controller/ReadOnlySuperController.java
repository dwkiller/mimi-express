package com.mimi.core.common.superpackage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.param.ListParam;
import com.mimi.core.common.superpackage.param.PageParam;
import com.mimi.core.common.superpackage.service.ISuperService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class ReadOnlySuperController<M extends ISuperService<T>, T> {

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

}
