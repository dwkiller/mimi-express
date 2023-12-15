package com.mimi.common.superpackage.intercept;

import com.mimi.common.superpackage.base.TenantEntity;
import com.mimi.common.superpackage.util.ObjectUtil;
import com.mimi.express.entity.order.HasExpressDelivery;
import com.mimi.express.entity.order.param.BaseOrderParam;
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

        if(!(paramObject instanceof BaseOrderParam)){
            return invocation.proceed();
        }

        String newSql = boundSql.getSql();
        boolean isCount = newSql.indexOf(" count( ")>0;

        if(paramObject instanceof TenantEntity){
            newSql = addTenantSql(newSql,(TenantEntity)paramObject,isCount);
        }

        if(!isCount){
            if(paramObject instanceof HasExpressDelivery){
                newSql = addExpressDeliverySql(newSql,(HasExpressDelivery)paramObject);
            }
            BaseOrderParam baseOrderParam = (BaseOrderParam)paramObject;
            if(baseOrderParam.getPageNum()>0&&baseOrderParam.getPageSize()>0){
                newSql = addPage(newSql,baseOrderParam);
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

    private String addPage(String sql,BaseOrderParam baseOrderParam){
        String startPosition=String.valueOf((baseOrderParam.getPageNum()-1)*baseOrderParam.getPageSize());
        String count=String.valueOf(baseOrderParam.getPageSize());
        String pageSql=sql+ " limit ";
        pageSql+= startPosition;
        pageSql+=",";
        pageSql+=count;
        return pageSql;
    }

    private String addExpressDeliverySql(String sql, HasExpressDelivery expressDelivery){
        String upperSql = sql.toUpperCase();
        StringBuffer newSqlBuffer = new StringBuffer(sql);

        int insertRsLocat = upperSql.indexOf(" FROM ");
        int insertTableLocat = upperSql.indexOf(" FROM ")+" FROM ".length();
        String tableName ="";
        boolean hasCondition = true;
        if(upperSql.indexOf("WHERE")<=0) {
            tableName = upperSql.substring(insertTableLocat).trim();
            hasCondition = false;
        }else{
            tableName = upperSql.substring(insertTableLocat,upperSql.indexOf("WHERE")).trim();
        }
        if(hasCondition){
            newSqlBuffer.append(" WHERE ");
        }else{
            newSqlBuffer.append(" AND ");
        }
        newSqlBuffer.append(tableName+".EXPRESS_DELIVERY_ID = ED.ID");
        newSqlBuffer.insert(insertTableLocat,"T_EXPRESS_DELIVERY ED,");
        newSqlBuffer.insert(insertRsLocat,",ED.NAME as EXPRESS_DELIVERY_NAME");
        return newSqlBuffer.toString();
    }

    private String addTenantSql(String sql,TenantEntity tenantEntity,boolean isCount){
        String upperSql = sql.toUpperCase();
        StringBuffer newSqlBuffer = new StringBuffer(sql);

        int insertRsLocat = upperSql.indexOf(" FROM ");
        int insertTableLocat = upperSql.indexOf(" FROM ")+" FROM ".length();
        String tableName ="";

        boolean hasCondition = true;
        if(upperSql.indexOf("WHERE")<=0) {
            tableName = upperSql.substring(insertTableLocat).trim();
            hasCondition = false;
        }else{
            tableName = upperSql.substring(insertTableLocat,upperSql.indexOf("WHERE")).trim();
        }

        String schoolId = tenantEntity.getSchoolId();

        if(hasCondition){
            newSqlBuffer.append(" WHERE ");
        }else{
            newSqlBuffer.append(" AND ");
        }
        if(!isCount){
            String condition=StringUtils.isEmpty(schoolId)?"":" AND SCHOOL.ID = "+schoolId;
            newSqlBuffer.append(tableName+".SCHOOL_ID = SCHOOL.ID"+condition);
            newSqlBuffer.insert(insertTableLocat,"T_SCHOOL SCHOOL,");
            newSqlBuffer.insert(insertRsLocat,",SCHOOL.NAME as SCHOOL_NAME");
        }else{
            if(!StringUtils.isEmpty(schoolId)){
                newSqlBuffer.append(tableName+".SCHOOL_ID = "+schoolId);
            }
        }
        return newSqlBuffer.toString();
    }

}
