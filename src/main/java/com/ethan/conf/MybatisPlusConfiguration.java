package com.ethan.conf;

/**
 * Created by ASUS on 2020/2/5.
 */

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.ethan.utils.TableName;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * MybatisPlus配置类
 */
@Configuration
//@MapperScan({"com.ethan.service", "com.ethan.dao"})
public class MybatisPlusConfiguration {

    @Autowired
    private TableName myTableName;

    private String tableName = "user";

    /**
     * 动态表名的实现也是基于分页器的
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        ArrayList<ISqlParser> sqlParserList = new ArrayList<>();    //  ISqlParser是sql解析器接口
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();   //  动态表名解析器
        Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<>();
        tableNameHandlerMap.put(tableName, new ITableNameHandler() {   //  选择替换user表
            @Override
            public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
                if (myTableName.getName() != null) {
                    return tableName + myTableName.getName();
                } else {
                    return tableName;
                }
            }
        });

        dynamicTableNameParser.setTableNameHandlerMap(tableNameHandlerMap);
        sqlParserList.add(dynamicTableNameParser);
        paginationInterceptor.setSqlParserList(sqlParserList);  //  配置到分页器中

        //  过滤指定sql
        /*paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
                // 过滤自定义查询，此时动态表名替换也不会对其生效
                if ("com.ethan.dao.UserMapper.mySelectUsers".equals(ms.getId())) {
                    return true;
                }
                return false;
            }
        });*/

        return paginationInterceptor;
    }

    /**
     * 多租户依赖于分页插件实现
     */
    /*@Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        ArrayList<ISqlParser> sqlParserList = new ArrayList<>();

        // SQL解析处理拦截：增加租户处理回调。
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new MyTenantHandler());
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }*/


    /**
     * 乐观锁插件
     */

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 1.多租户依赖于分页插件实现
     * 2.动态表名的实现也是基于分页器的
     *//*
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        ArrayList<ISqlParser> sqlParserList = new ArrayList<>();    //  ISqlParser是sql解析器接口
        TenantSqlParser tenantSqlParser = new TenantSqlParser();    //  TenantSqlParser是多租户解析器
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId(boolean where) {
                // 该 where 条件 3.2.0 版本开始添加的，用于区分是否为在 where 条件中使用
                // 如果是in/between之类的多个租户Id的情况，参考下方
                return new LongValue(1224581991756791809L);
            }

            @Override
            public String getTenantIdColumn() {
                return "manager_id";    //  设置表中对应的租户字段名
            }

            @Override
            public boolean doTableFilter(String tableName) {
                // 判断是否过滤表,即这些表不加租户信息约束 false为加，true为不加
                if ("user".equals(tableName)) {
                    return true;
                }
                return false;
            }
        });

        sqlParserList.add(tenantSqlParser);

        paginationInterceptor.setSqlParserList(sqlParserList);  //  配置到分页器中

        //  过滤指定sql，使其不执行多租户约束
        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
                // 过滤自定义查询此时无租户信息约束
                if ("com.ethan.dao.UserMapper.mySelectUsers".equals(ms.getId())) {
                    return true;
                }
                return false;
            }
        });

        return paginationInterceptor;
    }*/


    /**
     *  下面的方法为mp逻辑删除配置注册bean
     *  在使用3.1.1之前的版本时需要注册如下bean，之后的版本不需要
     *  本项目使用的是3.3.0版本，所以不注册该bean
     */
//    @Bean
//    public ISqlInjector sqlInjector () {
//        return new LogicSqllnjector();
//    }


}
