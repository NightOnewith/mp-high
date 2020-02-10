package com.ethan.utils;

import com.ethan.entity.Result;

/**
 * 接口统一返回json格式数据util工具类
 *   {
 *      "code": 200,
 *      "msg": "成功",
 *      "data": { }
 *   }
 */
public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
