package com.mimi.core.common.util;

import com.mimi.core.express.entity.order.BaseOrder;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassUtil {

    public static Set<Class<?>> getSubClasses(Class superClass) {
        Reflections reflections = new Reflections("com.mimi");
        //获取继承了ISuperClass的所有类
        return reflections.getSubTypesOf(superClass);
    }


    public static void main(String[] args){
        Set<Class<?>> subList = getSubClasses(BaseOrder.class);
        System.out.println(subList);
    }
}
