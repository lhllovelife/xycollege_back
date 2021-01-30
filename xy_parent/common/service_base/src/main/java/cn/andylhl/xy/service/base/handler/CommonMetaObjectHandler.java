package cn.andylhl.xy.service.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/***
 * @Title: CommonMetaObjectHandler
 * @Description: 配置自动填充
 * @author: lhl
 * @date: 2021/1/30 20:45
 */
@Slf4j
@Component // 注入ioc容器
public class CommonMetaObjectHandler implements MetaObjectHandler {

    /*
       执行insert语句时该处被调用执行
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    /*
        执行update语句时该处被调用执行
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}