package com.ethan.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by ASUS on 2020/2/4.
 */
@Data
@TableName("user")
public class User {

    //id
    private Long id;

    //姓名
    private String name;

    //年龄
    //@TableField(fill = FieldFill.UPDATE)
    private Integer age;

    //邮箱
    private String email;

    //上级主管id
    private Long managerId;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //更新时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    //版本默认为1
    @Version
    private Integer version;

    //逻辑删除标识（0.未删除,1.已删除)，默认为0）
    //@TableLogic(delval = "1",value = "0") delval为逻辑已删除、value为逻辑未删除
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

}
