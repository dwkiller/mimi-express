package com.mimi.core.common.superpackage.intercept;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.superpackage.base.BaseEntity;
import com.mimi.core.common.superpackage.base.TenantEntity;
import com.mimi.core.common.superpackage.util.ObjectUtil;
import com.mimi.core.express.entity.order.HasExpressDelivery;
import com.mimi.core.express.entity.order.param.OrderParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author dw
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisQueryIntercept  implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();

        if(!"SELECT".equals(sqlCommandType)) {
            return invocation.proceed();
        }
        BoundSql boundSql = statementHandler.getBoundSql();

        Object paramObject = boundSql.getParameterObject();
        if(paramObject==null){
            return invocation.proceed();
        }

        if(!(paramObject instanceof OrderParam)){
            return invocation.proceed();
        }

        OrderParam orderParam = (OrderParam)paramObject;
        String newSql = boundSql.getSql();
        boolean isCount = newSql.toLowerCase().indexOf(" count(")>0;
        String tableName = null;
        if(orderParam.getBusinessData().getClass().isAnnotationPresent(TableName.class)){
            TableName tableNameAnno = orderParam.getBusinessData().getClass().getAnnotation(TableName.class);
            tableName = tableNameAnno.value();
        }else{
            return invocation.proceed();
        }

        newSql = newSql.replaceAll(" create_time"," "+tableName+".create_time");

        newSql = addTenantSql(newSql,tableName,orderParam.getBusinessData(),isCount);
        if(orderParam.getBusinessData() instanceof HasExpressDelivery){
            HasExpressDelivery hasExpressDelivery = (HasExpressDelivery)orderParam.getBusinessData();
            newSql = addExpressDeliverySql(newSql,tableName,hasExpressDelivery,isCount);
        }
        newSql = addEmployeeSql(newSql,tableName,orderParam.getBusinessData(),isCount);

        if(!isCount){
            if(orderParam.getPageNum()>0&&orderParam.getPageSize()>0){
                newSql = addPage(newSql,orderParam);
            }
        }

        if(!newSql.equals(boundSql.getSql())){
            ObjectUtil.setFieldValue(boundSql,"sql",newSql);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub

    }

    private String addPage(String sql,OrderParam orderParam){
        String startPosition=String.valueOf((orderParam.getPageNum()-1)*orderParam.getPageSize());
        String count=String.valueOf(orderParam.getPageSize());
        String newCondition = " limit "+startPosition+","+count;
        return sql + newCondition;
    }

    private String addEmployeeSql(String sql, String tableName, BaseEntity baseEntity, boolean isCount){
        String upperSql = sql.toUpperCase();
        StringBuffer newSqlBuffer = new StringBuffer(sql);
        String upperTableName = " "+tableName.toUpperCase()+" ";
        int insertRsLocat = upperSql.indexOf(" FROM ");
        int insertConditionLocat = upperSql.indexOf(upperTableName)+upperTableName.length();
        int orderByLocat = upperSql.indexOf(" ORDER BY ");

        boolean hasCondition = true;
        if(upperSql.indexOf("WHERE")<=0) {
            hasCondition = false;
        }

        String createBy = baseEntity.getCreateBy();
        if(!StringUtils.isEmpty(createBy)){
            StringBuffer newConditionBuffer = new StringBuffer();
            if(!hasCondition){
                newConditionBuffer.append(" WHERE ");
            }else{
                newConditionBuffer.append(" AND ");
            }
            newConditionBuffer.append(" EMPLOYEE.id = '"+createBy+"'");
            if(orderByLocat>-1){
                newSqlBuffer.insert(orderByLocat,newConditionBuffer);
            }else{
                newSqlBuffer.append(newConditionBuffer);
            }
        }

        newSqlBuffer.insert(insertConditionLocat," left join T_EMPLOYEE EMPLOYEE on "+tableName+".CREATE_BY=EMPLOYEE.id ");
        if(!isCount){
            newSqlBuffer.insert(insertRsLocat,",EMPLOYEE.REAL_NAME as CREATE_BY_NAME ");
        }

        return newSqlBuffer.toString();
    }

    private String addExpressDeliverySql(String sql,String tableName, HasExpressDelivery expressDelivery,boolean isCount){
        String upperSql = sql.toUpperCase();
        StringBuffer newSqlBuffer = new StringBuffer(sql);
        String upperTableName = " "+tableName.toUpperCase()+" ";
        int insertRsLocat = upperSql.indexOf(" FROM ");
        int insertConditionLocat = upperSql.indexOf(upperTableName)+upperTableName.length();
        int orderByLocat = upperSql.indexOf(" ORDER BY ");

        boolean hasCondition = true;
        if(upperSql.indexOf("WHERE")<=0) {
            hasCondition = false;
        }

        String expressDeliveryId = expressDelivery.getExpressDeliveryId();
        if(!StringUtils.isEmpty(expressDeliveryId)){
            StringBuffer newConditionBuffer = new StringBuffer();
            if(!hasCondition){
                newConditionBuffer.append(" WHERE ");
            }else{
                newConditionBuffer.append(" AND ");
            }
            newConditionBuffer.append(" ED.id = '"+expressDeliveryId+"'");
            if(orderByLocat>-1){
                newSqlBuffer.insert(orderByLocat,newConditionBuffer);
            }else{
                newSqlBuffer.append(newConditionBuffer);
            }
        }
        newSqlBuffer.insert(insertConditionLocat," left join T_EXPRESS_DELIVERY ED on "+tableName+".EXPRESS_DELIVERY_ID=ED.id ");
        if(!isCount){
            newSqlBuffer.insert(insertRsLocat,",ED.ADDRESS as EXPRESS_DELIVERY_NAME ");
        }

        return newSqlBuffer.toString();
    }

    private String addTenantSql(String sql,String tableName,TenantEntity tenantEntity,boolean isCount){
        String upperSql = sql.toUpperCase();
        StringBuffer newSqlBuffer = new StringBuffer(sql);
        String upperTableName = " "+tableName.toUpperCase()+" ";
        int insertRsLocat = upperSql.indexOf(" FROM ");
        int insertConditionLocat = upperSql.indexOf(upperTableName)+upperTableName.length();
        int orderByLocat = upperSql.indexOf(" ORDER BY ");

        boolean hasCondition = true;
        if(upperSql.indexOf("WHERE")<=0) {
            hasCondition = false;
        }

        String schoolId = tenantEntity.getSchoolId();
        if(!StringUtils.isEmpty(schoolId)){
            StringBuffer newConditionBuffer = new StringBuffer();
            if(!hasCondition){
                newConditionBuffer.append(" WHERE ");
            }else{
                newConditionBuffer.append(" AND ");
            }
            newConditionBuffer.append(" SCHOOL.id = '"+schoolId+"'");
            if(orderByLocat>-1){
                newSqlBuffer.insert(orderByLocat,newConditionBuffer);
            }else{
                newSqlBuffer.append(newConditionBuffer);
            }
        }

        newSqlBuffer.insert(insertConditionLocat," left join T_SCHOOL SCHOOL on "+tableName+".school_id=SCHOOL.id ");
        if(!isCount){
            newSqlBuffer.insert(insertRsLocat,",SCHOOL.NAME as SCHOOL_NAME");
        }
        return newSqlBuffer.toString();
    }

}
