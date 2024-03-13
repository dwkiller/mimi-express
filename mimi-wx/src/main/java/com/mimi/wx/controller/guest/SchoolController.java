package com.mimi.wx.controller.guest;

import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.express.entity.config.School;
import com.mimi.core.express.service.SchoolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "学校管理")
@RestController
@RequestMapping("/guest/school")
public class SchoolController extends ReadOnlySuperController<SchoolService, School> {

    @GetMapping("/getSchoolByPos")
    public R<String> getSchoolByPos(double longitude, double latitude){
        School school = superService.findByPos(longitude,latitude);
        if(school==null){
            return R.success();
        }
        return R.success(school.getId());
    }

    @Override
    @GetMapping("/list")
    public R<List<School>> list(){
        List<School> rs = superService.list();
        if(rs!=null){
            for(School school : rs){
                school.setAdminName(null);
                school.setAdminPassword(null);
            }
        }
        return R.success(rs);
    }
}
