package com.mimi.express.controller;


import com.mimi.core.common.R;
import com.mimi.core.common.annotation.SendMsgField;
import com.mimi.core.common.enumration.VariableTypeEnum;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.common.util.ClassUtil;
import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.BaseOrder;
import com.mimi.core.express.service.MsgVariableService;
import com.mimi.core.express.type.InnerVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
    @Transactional
    public R<Boolean> saveBatch(@RequestBody List<MsgVariable> msgVariables) {
        if(msgVariables!=null&&msgVariables.size()>0){
            String templateId = msgVariables.get(0).getTemplateId();
            msgVariableService.deleteByTemplateId(templateId);
            msgVariableService.saveBatch(msgVariables);
        }
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
            msgVariable.setType(VariableTypeEnum.INNER.getDescription());
            msgVariable.setTag(innerVariable.getName());
            msgVariable.setValue(innerVariable.getValue());
            result.add(msgVariable);
        }
        MsgVariable dateVariable = new MsgVariable();
        dateVariable.setType(VariableTypeEnum.DATE_PART.getDescription());
        dateVariable.setTag("时间段");
        dateVariable.setValue(VariableTypeEnum.DATE_PART.getDescription());
        result.add(dateVariable);

        result.addAll(getOrderVariable());


        return R.success(result);
    }

    private List<MsgVariable> orderVariable;

    private List<MsgVariable> getOrderVariable(){
        if(orderVariable==null){
            orderVariable = new ArrayList<>();
            loadOrderVar(BaseOrder.class);
            Set<Class<?>> subCLassList = ClassUtil.getSubClasses(BaseOrder.class);
            if(subCLassList!=null){
                for(Class clazz:subCLassList){
                    loadOrderVar(clazz);
                }
            }
        }
        return orderVariable;
    }

    private void loadOrderVar(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        if(fields!=null){
            for(Field field:fields){
                if(field.isAnnotationPresent(SendMsgField.class)){
                    SendMsgField sendMsgField = field.getAnnotation(SendMsgField.class);
                    long cnt = orderVariable.stream().filter(m->m.getTag().equals(sendMsgField.text())).count();
                    if(cnt==0){
                        MsgVariable msgVariable = new MsgVariable();
                        msgVariable.setType(VariableTypeEnum.DATE_PART.getDescription());
                        msgVariable.setTag(sendMsgField.text());
                        msgVariable.setValue(sendMsgField.value());
                        orderVariable.add(msgVariable);
                    }
                }
            }
        }
    }
}
