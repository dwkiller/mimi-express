package com.mimi.express.controller.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.param.Filter;
import com.mimi.core.common.superpackage.param.PageParam;
import com.mimi.core.common.superpackage.param.Rule;
import com.mimi.core.express.entity.config.School;
import com.mimi.core.express.entity.order.OrderOut;
import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.service.SchoolService;
import com.mimi.core.express.service.impl.order.OrderOutService;
import com.mimi.express.controller.order.vo.AutoOutVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "投诉运单")
@RestController
@RequestMapping("/orderOut")
public class OrderOutController extends BaseOrderController<OrderOutService, OrderOut>{

    @Autowired
    private SchoolService schoolService;

    @Operation(summary = "批量生成运单")
    @PostMapping("/autoOut")
    public String autoOut(@RequestBody AutoOutVo autoOutVo){
        School school = schoolService.findByName(autoOutVo.getSchool());
        if(school==null){
            return null;
        }
        OrderParam<OrderOut> orderParam = new OrderParam();
        orderParam.setPageNum(Integer.parseInt(autoOutVo.getPageNo()));
        orderParam.setPageSize(Integer.parseInt(autoOutVo.getPageSize()));
        OrderOut businessParam = new OrderOut();
        businessParam.setSchoolId(school.getId());
        orderParam.setOrderBy("create_time");
        orderParam.setBusinessData(businessParam);
        orderParam.setToday("1");
        IPage<OrderOut> rsPage = superService.findPage(orderParam);
        List<OrderOut> rsList = rsPage.getRecords();
        if(rsList==null||rsList.size()==0){
            return null;
        }
        return StringUtils.join(rsList.stream().map(OrderOut::getOrderNum)
                .collect(Collectors.toList()),",");
    }

    @Operation(summary = "导出")
    @PostMapping("/export")
    public void export(@RequestBody OrderParam<OrderOut> orderParam, HttpServletResponse response){

        if(orderParam.getStartTime()==null||orderParam.getEndTime()==null){
            throw new RuntimeException("开始时间与结束时间必填!");
        }

        

        orderParam.setPageNum(1);
        orderParam.setPageSize(99999);
        IPage<OrderOut> rsPage = superService.findPage(orderParam);
        List<OrderOut> rsList = rsPage.getRecords();
        String content="";
        if(rsList!=null&&rsList.size()>0){
            content = rsList.stream().map(OrderOut::getOrderNum).collect(Collectors.joining(","));
        }

        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader("Content-Disposition", "attachment;filename=orderNum.txt");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
