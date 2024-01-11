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

    @Operation(summary = "带条件删除")
    @DeleteMapping("/param")
    public R deleteByParam(@RequestBody Filter filter) throws Exception {
        superService.deleteByParam(filter);
        return R.success(true);
    }
}
