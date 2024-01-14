package com.mimi.core.common.superpackage.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.mimi.core.common.superpackage.param.Filter;
import com.mimi.core.common.superpackage.param.Rule;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Wrapper构建器
 *
 * @author pig
 */
public class WrapperBuilder<T> {

    /**
     * 表字段与实例字段的映射
     */
    public Map<String, String> fieldMap;
    private T t;

    public WrapperBuilder(Class<T> tClass) {
        fieldMap = new HashMap<>();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(tClass);
        // 插入id字段
        fieldMap.put(tableInfo.getKeyProperty(), tableInfo.getKeyColumn());
        for (TableFieldInfo tableFieldInfo : tableInfo.getFieldList()) {
            fieldMap.put(tableFieldInfo.getProperty(), tableFieldInfo.getColumn());
        }
    }

    /**
     * 构建Wrapper
     *
     * @return
     */
    public QueryWrapper<T> buildByParam(Filter filter) throws Exception {
        return buildByParam(filter, (LinkedHashMap<String, String>) null);
    }

    /**
     * 构建Wrapper
     *
     * @return
     */
    public QueryWrapper<T> buildByParam(LinkedHashMap<String, String> sorts) throws Exception {
        return buildByParam(null, sorts);
    }


    /**
     * 构建Wrapper
     *
     * @param filter
     * @return
     */
    public QueryWrapper<T> buildByParam(Filter filter, LinkedHashMap<String, String> sorts) throws Exception {
        QueryWrapper<T> wrapper = new QueryWrapper<T>();
        // 构造查询条件
        if (null != filter) {
            wrapper = this.buildByParam(filter, wrapper);
        }
        // 构造排序条件
        if (null != sorts) {
            for (String key : sorts.keySet()) {
                if (SqlKeyword.DESC.getSqlSegment().toUpperCase().equals(sorts.get(key)) || SqlKeyword.DESC.getSqlSegment().toLowerCase().equals(sorts.get(key))) {
                    wrapper.orderByDesc(this.getColumn(key));
                } else {
                    wrapper.orderByAsc(this.getColumn(key));
                }
            }
        }
        return wrapper;
    }

    /**
     * 递归构建Wrapper
     *
     * @param filter
     * @return
     */
    private QueryWrapper<T> buildByParam(Filter filter, QueryWrapper<T> wrapper) throws Exception {
        if (filter == null || CollectionUtils.isEmpty(filter.getRules())) {
            return wrapper;
        }
        List<Rule> rules = filter.getRules();
        String ruleOp = filter.getGroupOp();
        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            if (Filter.OP_AND.equals(ruleOp)) {
                buildWrapperByRule(rule, wrapper);
            } else {
                buildWrapperByRule(rule, wrapper).or();
            }
        }
        List<Filter> groups = filter.getGroups();
        if (!CollectionUtils.isEmpty(groups)) {
            if (Filter.OP_AND.equals(ruleOp)) {
                wrapper.and(wp -> groups.forEach(obj -> {
					try {
						buildByParam(obj, wp);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}));
            } else {
                wrapper.or(wp -> groups.forEach(obj -> {
					try {
						buildByParam(obj, wp);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}));
            }
        }
        return wrapper;
    }

    /**
     * 构建Wrapper
     *
     * @param rule
     * @param wrapper
     * @return
     */
    private QueryWrapper<T> buildWrapperByRule(Rule rule, QueryWrapper<T> wrapper) throws Exception {
        String column = this.getColumn(rule.getField());
        switch (rule.getOp()) {
            case Rule.OP_EQUAL:
                if (rule.getCondition() != null) {
                    wrapper.eq(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.eq(column, rule.getData());
                }
                break;
            case Rule.OP_NOT_EQUAL:
                if (rule.getCondition() != null) {
                    wrapper.ne(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.ne(column, rule.getData());
                }
                break;
            case Rule.OP_LESS_THAN:
                if (rule.getCondition() != null) {
                    wrapper.lt(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.lt(column, rule.getData());
                }
                break;
            case Rule.OP_LESS_EQUAL:
                if (rule.getCondition() != null) {
                    wrapper.le(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.le(column, rule.getData());
                }
                break;
            case Rule.OP_GREATER_THAN:
                if (rule.getCondition() != null) {
                    wrapper.gt(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.gt(column, rule.getData());
                }
                break;
            case Rule.OP_GREATER_EQUAL:
                if (rule.getCondition() != null) {
                    wrapper.ge(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.ge(column, rule.getData());
                }
                break;
            case Rule.OP_IN:
                if (rule.getData() instanceof Collection<?>) {
                    if (rule.getCondition() != null) {
                        wrapper.in(rule.getCondition(), column, rule.getData());
                    } else {
                        wrapper.in(column, rule.getData());
                    }
                }
                break;
            case Rule.OP_NOT_IN:
                if (rule.getData() instanceof List<?>) {
                    if (rule.getCondition() != null) {
                        wrapper.notIn(rule.getCondition(), column, rule.getData());
                    } else {
                        wrapper.notIn(column, rule.getData());
                    }
                }
                break;
            case Rule.OP_LIKE:
                if (rule.getCondition() != null) {
                    wrapper.like(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.like(column, rule.getData());
                }
                break;
            case Rule.OP_NOT_LIKE:
                if (rule.getCondition() != null) {
                    wrapper.notLike(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.notLike(column, rule.getData());
                }
                break;
            case Rule.OP_IS_NOT_NULL:
                if (rule.getCondition() != null) {
                    wrapper.isNotNull(rule.getCondition(),column);
                } else {
                    wrapper.isNotNull(column);
                }
                break;
            case Rule.OP_IS_NULL:
                if (rule.getCondition() != null) {
                    wrapper.isNull(rule.getCondition(),column);
                } else {
                    wrapper.isNull(column);
                }
                break;
            case Rule.OP_LEFT_LIKE:
                if (rule.getCondition() != null) {
                    wrapper.likeLeft(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.likeLeft(column, rule.getData());
                }
                break;
            case Rule.OP_RIGHT_LIKE:
                if (rule.getCondition() != null) {
                    wrapper.likeRight(rule.getCondition(), column, rule.getData());
                } else {
                    wrapper.likeRight(column, rule.getData());
                }
                break;
            default:

        }
        return wrapper;
    }

    /**
     * 获取字段名
     *
     * @param field
     * @return
     */
    private String getColumn(String field) throws Exception {
        String column = fieldMap.get(field);
        if (StrUtil.isEmpty(column)) {
            throw new Exception("获取字段失败： " + field);
        }
        return column;
    }

    /**
     * 根据属性，获取get方法 的值
     *
     * @param ob   对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    private Object getValueFromObject(Object ob, String name) {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                try {
                    return m[i].invoke(ob);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }
}
