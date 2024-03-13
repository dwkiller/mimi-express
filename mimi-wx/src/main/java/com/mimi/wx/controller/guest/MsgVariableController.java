package com.mimi.wx.controller.guest;


import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.service.MsgVariableService;
import com.mimi.core.express.type.InnerVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/guest/msgVariable")
public class MsgVariableController extends ReadOnlySuperController<MsgVariableService, MsgVariable> {

    @Autowired
    private MsgVariableService msgVariableService;

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
