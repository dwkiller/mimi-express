package com.mimi.core.express.service.impl;

import com.mimi.core.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.core.express.entity.config.School;
import com.mimi.core.express.entity.config.SysDict;
import com.mimi.core.express.entity.receive.Insurance;
import com.mimi.core.express.entity.receive.Pricing;
import com.mimi.core.express.mapper.SchoolMapper;
import com.mimi.core.express.service.InsuranceService;
import com.mimi.core.express.service.PricingService;
import com.mimi.core.express.service.SchoolService;
import com.mimi.core.express.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


/**
 * @Description 学校管理
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class SchoolServiceImpl extends SuperServiceImpl<SchoolMapper, School> implements SchoolService {

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private InsuranceService insuranceService;

    @Override
    public boolean save(@Valid School school) {
        boolean rs = super.save(school);
        createDict(school,"source","来源");
        createDict(school,"questionDesc","问题描述");
        createDict(school,"faildQuestion","失败原因");
        createDict(school,"complaintContent","投诉内容");
        createDict(school,"expressCompany","快递公司");
        createDict(school,"dooToDoorPickup","上门取件时间");
        createPricing(school,1,new BigDecimal(2),new BigDecimal(0),"小于1个鞋盒");
        createPricing(school,3,new BigDecimal(4),new BigDecimal(0),"小于2个鞋盒");
        createPricing(school,6,new BigDecimal(6),new BigDecimal(0),"2-4个鞋盒");
        createPricing(school,10,new BigDecimal(10),new BigDecimal(0),"20寸行李箱");
        createPricing(school,11,new BigDecimal(20),new BigDecimal(0),"大于20寸行李箱");
        createInsurance(school,new BigDecimal(1),"投保");
        createInsurance(school,new BigDecimal(1),"价值100~200元");
        return rs;
    }

    private void createInsurance(School school,BigDecimal price,String note){
        Insurance insurance = new Insurance();
        insurance.setSchoolId(school.getId());
        insurance.setPrice(price);
        insurance.setNote(note);
        insuranceService.save(insurance);
    }

    private void createPricing(School school, double weight, BigDecimal price, BigDecimal discount, String note){
        Pricing pricing = new Pricing();
        pricing.setSchoolId(school.getId());
        pricing.setWeight(weight);
        pricing.setPrice(price);
        pricing.setNote(note);
        pricing.setDiscount(discount);
        pricingService.save(pricing);
    }

    private void createDict(School school,String type,String typeName){
        SysDict sysDict = new SysDict();
        sysDict.setSchoolId(school.getId());
        sysDict.setSysDict((short) 1);
        sysDict.setType(type);
        sysDict.setTypeName(typeName);
        sysDictService.save(sysDict);
    }

    @Override
    public School findByPos(double longitude, double latitude) {
        List<School> schools = list();
        double minDistance = 99999999;
        School result = null;

        for(School school:schools) {
            if(school.getLongitude()==null||school.getLatitude()==null){
                continue;
            }
            double x = Math.abs(school.getLongitude()-longitude)*1000000;
            double y = Math.abs(school.getLatitude()-latitude)*1000000;
            double z = Math.sqrt(x*x+y*y);
            if(z<minDistance){
                minDistance = z;
                result = school;
            }
        }
        return result;
    }
}
