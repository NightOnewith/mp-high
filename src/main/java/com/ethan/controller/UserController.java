package com.ethan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.dao.UserMapper;
import com.ethan.entity.Result;
import com.ethan.entity.User;
import com.ethan.serive.UserService;
import com.ethan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ASUS on 2020/2/4.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/insertUser")
    public Result insertUser() {
        User user = new User();
        user.setName("王六");
        user.setAge(27);
        user.setEmail("wangliu@user.com");
        //user.setManagerId(1224581991756791809L);  //  配置了多租户就不用我们在插入的时候手写租户id
        int row = userMapper.insert(user);

        List<User> userList = userService.list();

        return ResultUtil.success(userList);
    }

    @RequestMapping("/deleteById")
    public Result deleteById() {
        userMapper.selectById(1224581991769374722L);
        int row = userMapper.deleteById(1224581991769374722L);
        userMapper.selectById(1224581991769374722L);

        return ResultUtil.success("影响行数：" + row);
    }

    @RequestMapping("/selectUser")
    public Result selectUser() {
        List<User> users = userMapper.selectList(null);

        users.forEach(System.out::println);
        return ResultUtil.success(users);
    }

    @RequestMapping("/mySelectUsers")
    public Result mySelectUsers() {
        // 直接查询
        List<User> users = userMapper.mySelectUsers(new QueryWrapper<User>().lambda());
        users.forEach(System.out::println);

        // 手动排除deleted字段，这里演示一种排除方式，还可以在自定义sql中排除
        List<User> userList = userMapper.mySelectUsers(new QueryWrapper<User>().lambda().eq(User::getDeleted, 0));
        userList.forEach(System.out::println);

        return ResultUtil.success(userList);
    }

    @RequestMapping("/updateById")
    public Result updateById() {
        User user = new User();
        user.setId(1225261847062532097L);
        user.setAge(26);
        int row = userMapper.updateById(user);

        User selectById = userMapper.selectById(1225261847062532097L);

        return ResultUtil.success(selectById);
    }

    //  测试乐观锁
    @RequestMapping("/updateById2")
    public Result updateById2() {
        Long id = 1226387761511112706L;
        Integer version = userMapper.selectById(id).getVersion();

        User user = new User();
        user.setId(id);
        user.setAge(28);
        user.setVersion(version);
        int row = userMapper.updateById(user);

        User selectById = userMapper.selectById(id);

        return ResultUtil.success(selectById);
    }
    //  测试乐观锁2-Wrapper复用
    @RequestMapping("/updateById3")
    public Result updateById3() {
        Long id = 1226387761511112706L;
        User user = new User();
        user.setAge(30);
        user.setVersion(userMapper.selectById(id).getVersion());

        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        queryWrapper.eq(User::getDeleted, 0);

        // 手动排除deleted字段，这里演示一种排除方式，还可以在自定义sql中排除
        int row = userMapper.update(user, queryWrapper);

        User user2 = new User();
        user2.setAge(31);
        user2.setVersion(userMapper.selectById(id).getVersion());

        queryWrapper.eq(User::getName, "王六");
        int row2 = userMapper.update(user, queryWrapper);

        return ResultUtil.success(row);
    }

}
