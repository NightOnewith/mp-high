package com.ethan.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by ASUS on 2020/2/6.
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //  插入时自动填充createTime字段为当前时间（不需要在实体类中设置该字段值）
        //this.setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject); 3.3.0版本中已过时
        //this.fillStrategy(metaObject, "createTime", LocalDateTime.now()); 该方法也可以使用但在3.3.0中有bug，可以升级到`3.3.1.8-SNAPSHOT`
        // 看实体类中是否有这个属性，有的话就执行，没有就不执行
        boolean hasSetter = metaObject.hasSetter("createTime");
        if (hasSetter) {
            System.out.println("insertFill 进来了~");
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //  更新时自动填充updateTime字段为当前时间（不需要在实体类中设置该字段值）
        //this.setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject); 3.3.0版本中已过时
        //this.fillStrategy(metaObject, "updateTime", LocalDateTime.now()); 该方法也可以使用但在3.3.0中有bug，可以升级到`3.3.1.8-SNAPSHOT`
        boolean hasSetter = metaObject.hasSetter("updateTime");
        // 如果预先自己设置了值，则设置不使用MP的自动填充
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (hasSetter && updateTime == null) {
            System.out.println("updateFill 进来了~");
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        }
    }
}
