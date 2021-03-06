package com.ethan.controller;

import com.ethan.dao.ProfileMapper;
import com.ethan.entity.Profile;
import com.ethan.entity.Result;
import com.ethan.utils.ResultUtil;
import com.ethan.utils.TableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ASUS on 2020/2/9.
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private TableName myTableName;

    @RequestMapping("/findAll")
    public Result findAll() {
        List<Profile> profiles = profileMapper.selectList(null);

        return ResultUtil.success(profiles);
    }

    @RequestMapping("/deleteAll")
    public Result deleteAll() {
        myTableName.setName("_2020");

        int rows = profileMapper.deleteAll();

        return ResultUtil.success(rows);
    }
}
