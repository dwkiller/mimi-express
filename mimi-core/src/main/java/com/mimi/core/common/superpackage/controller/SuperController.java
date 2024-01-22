package com.mimi.core.common.superpackage.controller;

import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.param.Filter;
import com.mimi.core.common.superpackage.service.ISuperService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


public abstract class SuperController<M extends ISuperService<T>, T> extends ReadOnlySuperController<M,T> {


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

}
