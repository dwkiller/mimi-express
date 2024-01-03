package com.mimi.express.controller;


import com.mimi.common.R;
import com.mimi.common.superpackage.controller.SuperController;
import com.mimi.common.superpackage.param.Filter;
import com.mimi.common.superpackage.param.ListParam;
import com.mimi.common.superpackage.param.Rule;
import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.config.SysDict;
import com.mimi.express.service.MsgVariableService;
import com.mimi.express.type.InnerVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * 消息模板变量管理
 * </p>
 *
 * @author 消息模板变量管理
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "消息模板变量管理")
@RestController
@RequestMapping("/msgVariable")
public class MsgVariableController extends SuperController<MsgVariableService, MsgVariable> {

    @Autowired
    private MsgVariableService msgVariableService;


    @Operation(summary = "批量添加消息变量")
    @PostMapping("/saveBatch")
    public R<Boolean> saveBatch(@RequestBody List<MsgVariable> msgVariables) {
        msgVariableService.saveBatch(msgVariables);
        return R.success();
    }

    @Operation(summary = "批量更新消息变量")
    @PostMapping("/updateBatch")
    public R<Boolean> updateBatch(@RequestBody List<MsgVariable> msgVariables) {
        msgVariableService.updateBatchById(msgVariables);
        return R.success();
    }

    /**
     * 获取内置变量."
     *
     * @return 字典信息
     */
    @Operation(summary = "通过ID查询字典信息")
    @GetMapping("/getInnerVariable")
    public R<List<MsgVariable>> getInnerVariable() throws Exception {
        List<MsgVariable> result = new ArrayList<>();
        for(InnerVariable innerVariable:InnerVariable.values()){
            MsgVariable msgVariable = new MsgVariable();
            msgVariable.setType("INNER");
            msgVariable.setTag(innerVariable.getName());
            msgVariable.setValue(innerVariable.getValue());
            result.add(msgVariable);
        }
        return R.success(result);
    }
}
