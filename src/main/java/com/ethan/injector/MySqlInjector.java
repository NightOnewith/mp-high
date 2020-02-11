package com.ethan.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import com.ethan.method.DeleteAllMethod;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 创建注入器
 */
@Component
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        //  加载DefaultSqlInjector类提供的所有方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        //  再加入我们自定义的方法
        methodList.add(new DeleteAllMethod());
        //  注入一个InsertBatchSomeColumn方法：在批量插入的时候不插入逻辑删除字段deleted值
        //  注意：在加入多租户配置的时候会出现报错提示租户字段manager_id在insert语句中设置了2次，此时可以在SQL注入器中手动排除一次manager_id
        //methodList.add(new InsertBatchSomeColumn(t ->!t.isLogicDelete()&&!t.getColumn().equals("manager_id")&&!t.getColumn().equals("version")));
        methodList.add(new InsertBatchSomeColumn(t ->!t.isLogicDelete()&&!t.getColumn().equals("version")));
        //  注入一个逻辑删除方法：在通过id进行逻辑删除的时候自动填充一些字段值，例如操作人、操作时间等。
        methodList.add(new LogicDeleteByIdWithFill());
        //  注入一个更新固定字段方法：可以设置哪些字段需要更新 哪些字段不需要更新，在需要更新的字段在实体中没有设置值时 它会设置为null（逻辑删除字段方法中已经排除）
        methodList.add(new AlwaysUpdateSomeColumnById(t ->!t.getColumn().equals("name")));

        return methodList;
    }
}
