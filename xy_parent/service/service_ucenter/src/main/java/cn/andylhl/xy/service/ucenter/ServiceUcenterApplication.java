package cn.andylhl.xy.service.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/***
 * @Title: ServiceUcenterApplication
 * @Description: 用户中心启动类
 * @author: lhl
 * @date: 2021/3/2 11:45
 */

@EnableDiscoveryClient //启用注册中心
@ComponentScan("cn.andylhl.xy.service")
@SpringBootApplication
public class ServiceUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceUcenterApplication.class);
    }

}
