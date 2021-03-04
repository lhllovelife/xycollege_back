package cn.andylhl.xy.service.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/***
 * @Title: ServiceTradeApplication
 * @Description: 交易中心模块启动类
 * @author: lhl
 * @date: 2021/3/4 9:17
 */

@EnableFeignClients // 启用Feign客户端
@EnableDiscoveryClient //启用注册中心
@ComponentScan("cn.andylhl.xy.service")
@SpringBootApplication
public class ServiceTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTradeApplication.class);
    }

}
