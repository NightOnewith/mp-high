package com.ethan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by ASUS on 2020/2/9.
 */
@Data
@TableName("profile")
public class Profile {

    private Long id;

    private Long manager_id;

    private String title;

    private String content;
}
