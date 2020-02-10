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
 * Created by ASUS on 2020/2/4.
 */
@Repository
public interface UserMapper extends BaseMapper<User>{

    //  自定义SQL语句接口
    //@SqlParser(filter = false)   //  使用注解配置接口是否启动多租户，true为不启用，默认为false启用
    @Select("select * from user ${ew.customSqlSegment}")
    List<User> mySelectUsers(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

}
