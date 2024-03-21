package com.mimi.core.express.service.impl.order;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import com.mimi.core.express.entity.order.OrderOut;
import com.mimi.core.express.mapper.order.OrderOutMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class OrderOutService extends BaseOrderService<OrderOutMapper, OrderOut> {
    @Override
    public String type() {
        return "出库单";
    }

    @Transactional
    public boolean saveBatch(Collection<OrderOut> entityList,String rootPath) throws Exception {
        List<OrderOut> orderOutList = new ArrayList<>();
        for(OrderOut order : entityList){
            if(findByOrderNum(order.getOrderNum())!=null){
                continue;
            }
            if(!StringUtils.isEmpty(order.getFileMd5())){
                String filePath = rootPath+File.separator+order.getSchoolId();
                FileUtil.mkdir(filePath);
                setFile(order);
                Base64.decodeToFile(order.getFileMd5(),new File(filePath+File.separator+order.getFile()));
            }
            order.setFileMd5(null);
            orderOutList.add(order);
        }
        return super.saveBatch(orderOutList);
    }

    private void setFile(OrderOut order){
        if(StringUtils.isEmpty(order.getFileMd5())){
            return;
        }
        String md5Str = order.getFileMd5();
        if(md5Str.indexOf(",")>0){
            String[] ss =  md5Str.split(",");
            String s1=ss[0]; //data:image/jpeg;base64
            if(s1.indexOf(";")>0&&s1.indexOf("/")>0){
                String[] ss1 = s1.split(";")[0].split("/");
                order.setFile(UUID.randomUUID().toString().replaceAll("-","")+"."+ss1[ss1.length-1]);
            }else{
                order.setFile(UUID.randomUUID().toString().replaceAll("-","")+".jpg");
            }
            order.setFileMd5(ss[1]);
        }else{
            order.setFile(UUID.randomUUID().toString().replaceAll("-","")+".jpg");
        }
    }

    public static void main(String[] args){

        String s1="data:image/jpeg;base64";
        if(s1.indexOf(";")>0&&s1.indexOf("/")>0){
            String[] ss1 = s1.split(";")[0].split("/");
            System.out.println(ss1[ss1.length-1]);
        }

//        String s = Base64.encode(new File("d:/a.jpg"));
//        System.out.println(s);
//        Base64.decodeToFile("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",new File("d:/a2.jpg"));
    }

}
