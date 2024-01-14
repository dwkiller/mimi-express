package com.mimi.core.common.superpackage.intercept;

import com.baomidou.mybatisplus.annotation.TableName;
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

        if(orderParam.getBusinessData() instanceof TenantEntity){
            newSql = addTenantSql(newSql,tableName,(TenantEntity)orderParam.getBusinessData(),isCount);
        }

        if(!isCount){
            if(orderParam.getBusinessData() instanceof HasExpressDelivery){
                newSql = addExpressDeliverySql(newSql,tableName,(HasExpressDelivery)orderParam.getBusinessData());
            }
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
        String pageSql=sql+ " limit ";
        pageSql+= startPosition;
        pageSql+=",";
        pageSql+=count;
        return pageSql;
    }

    private String addExpressDeliverySql(String sql,String tableName, HasExpressDelivery expressDelivery){
        String upperSql = sql.toUpperCase();
        StringBuffer newSqlBuffer = new StringBuffer(sql);

        int insertRsLocat = upperSql.indexOf(" FROM ");
        int insertTableLocat = upperSql.indexOf(" FROM ")+" FROM ".length();
        boolean hasCondition = true;
        if(upperSql.indexOf("WHERE")<=0) {
            hasCondition = false;
        }
        if(!hasCondition){
            newSqlBuffer.append(" WHERE ");
        }else{
            newSqlBuffer.append(" AND ");
        }
        newSqlBuffer.append(tableName+".EXPRESS_DELIVERY_ID = ED.ID");
        newSqlBuffer.insert(insertTableLocat,"T_EXPRESS_DELIVERY ED,");
        newSqlBuffer.insert(insertRsLocat,",ED.NAME as EXPRESS_DELIVERY_NAME");
        return newSqlBuffer.toString();
    }

    private String addTenantSql(String sql,String tableName,TenantEntity tenantEntity,boolean isCount){
        String upperSql = sql.toUpperCase();
        StringBuffer newSqlBuffer = new StringBuffer(sql);

        int insertRsLocat = upperSql.indexOf(" FROM ");
        int insertTableLocat = upperSql.indexOf(" FROM ")+" FROM ".length();

        boolean hasCondition = true;
        if(upperSql.indexOf("WHERE")<=0) {
            hasCondition = false;
        }

        String schoolId = tenantEntity.getSchoolId();
        if(!hasCondition){
            newSqlBuffer.append(" WHERE ");
        }else{
            newSqlBuffer.append(" AND ");
        }
        if(!isCount){
            String condition=StringUtils.isEmpty(schoolId)?"":" AND SCHOOL.ID = '"+schoolId+"'";
            newSqlBuffer.append(tableName+".SCHOOL_ID = SCHOOL.ID"+condition);
            newSqlBuffer.insert(insertTableLocat,"T_SCHOOL SCHOOL,");
            newSqlBuffer.insert(insertRsLocat,",SCHOOL.NAME as SCHOOL_NAME");
        }else{
            if(!StringUtils.isEmpty(schoolId)){
                newSqlBuffer.append(tableName+".SCHOOL_ID = '"+schoolId+"'");
            }
        }
        return newSqlBuffer.toString();
    }

}
