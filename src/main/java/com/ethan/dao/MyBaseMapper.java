package com.ethan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 创建我们的通用mapper类，将自定义全局方法放到该类中，
 * MyBaseMapper<T>中就包含了MP提供的BaseMapper<T>类中所有方法以及我们自定义的方法，
 * 然后所有的mapper都继承该接口类
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 删除所有（自定义的全局方法）
     * @return 影响行数
     */
    int deleteAll();

    /**
     *  批量插入（排除deleted逻辑字段的插入）
     * @param list
     * @return
     */
    int insertBatchSomeColumn(List<T> list);

    /**
     *  逻辑删除并自动填充相关字段
     * @param entity
     * @return
     */
    int deleteByIdWithFill(T entity);

    /**
     *  根据id更新固定的某些字段
     * @param entity
     * @return
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY)T entity);
}
