package com.ethan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.dao.UserMapper;
import com.ethan.entity.Result;
import com.ethan.entity.User;
import com.ethan.serive.UserService;
import com.ethan.utils.ResultUtil;
import com.ethan.utils.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * Created by ASUS on 2020/2/9.
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private TenantContext tenantContext;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    private static Random random = new Random();

    @RequestMapping("/login/{id}")
    public Result login(@PathVariable Long id) {

        //直接根据用户id号查询出用户信息
        User user = userMapper.selectById(id);
        //System.out.println(user.toString());
        //生成一个10000以内的随机数，作为用户登录后返回给用户的token
        String token = String.valueOf(random.nextInt(10000));

        //将token、tenantId键值对放入tenantContext
        tenantContext.putTokenTenantIdPair(token,user.getManagerId());

        return ResultUtil.success("login success, token is " + token + ", tenant id is " + user.getManagerId());
    }

    @RequestMapping("/add/{token}")
    public Result insertUser() {
        User user = new User();
        user.setName("王七");
        user.setAge(28);
        user.setEmail("wangqi@user.com");
        int row = userMapper.insert(user);

        List<User> userList = userService.list();

        return ResultUtil.success(userList);
    }

    @RequestMapping("/delete/{token}")
    public Result deleteById() {
        userMapper.selectById(1224581991769374722L);
        int row = userMapper.deleteById(1224581991769374722L);
        userMapper.selectById(1224581991769374722L);

        return ResultUtil.success("影响行数：" + row);
    }

    @RequestMapping("/findAll/{token}")
    public Result selectUser() {
        List<User> users = userMapper.selectList(null);

        users.forEach(System.out::println);
        return ResultUtil.success(users);
    }

    @RequestMapping("/selectUser2")
    public Result selectUser2() {
        List<User> users = userMapper.selectList(null);

        users.forEach(System.out::println);
        return ResultUtil.success(users);
    }

    @RequestMapping("/mySelectUsers/{token}")
    public Result mySelectUsers() {
        // 直接查询
        /*List<User> users = userMapper.mySelectUsers(new QueryWrapper<User>().lambda());
        users.forEach(System.out::println);*/

        // 手动排除deleted字段，这里演示一种排除方式，还可以在自定义sql中排除
        List<User> userList = userMapper.mySelectUsers(new QueryWrapper<User>().lambda().eq(User::getDeleted, 0));
        userList.forEach(System.out::println);

        return ResultUtil.success(userList);
    }

    @RequestMapping("/update/{token}")
    public Result updateById() {
        User user = new User();
        user.setId(1225261847062532097L);
        user.setAge(28);
        int row = userMapper.updateById(user);

        User selectById = userMapper.selectById(1225261847062532097L);

        return ResultUtil.success(selectById);
    }

    //  测试乐观锁
    @RequestMapping("/updateById2")
    public Result updateById2() {
        Integer version = userMapper.selectById(1225261847062532097L).getVersion();

        User user = new User();
        user.setId(1225261847062532097L);
        user.setAge(29);
        user.setVersion(version);
        int row = userMapper.updateById(user);

        User selectById = userMapper.selectById(1225261847062532097L);

        return ResultUtil.success(selectById);
    }
}
