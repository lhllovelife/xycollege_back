package cn.andylhl.xy.service.base.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/***
 * @Title: MybatisPlusConfig
 * @Description: mybatis-plus配置类，所有service模块公共使用
 * @author: lhl
 * @date: 2021/1/29 22:07
 */
@EnableTransactionManagement // 开启事务支持
@MapperScan(basePackages = "cn.andylhl.xy.service.*.mapper") // 不同的service模块，包名有不同，所以用*通配符
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
