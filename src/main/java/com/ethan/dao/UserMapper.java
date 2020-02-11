package com.ethan.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ethan.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserMapper类继承我们定义的通用mapper
 * 如果有其他的xxxMapper也都是继承定义的通用mapper
 */
@Repository
public interface UserMapper extends MyBaseMapper<User> {

    //  自定义SQL语句接口
    @SqlParser(filter = true)   //  使用注解配置接口是否启动多租户，true为不启用，默认为false启用
    @Select("select * from user ${ew.customSqlSegment}")
    List<User> mySelectUsers(@Param(Constants.WRAPPER) Wrapper<User> wrapper);
}
