package cn.andylhl.xy.service.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/***
 * @Title: ServiceSmsApplication
 * @Description: 短信微服务启动类
 * @author: lhl
 * @date: 2021/3/2 9:23
 */

@EnableDiscoveryClient //启用注册中心
@ComponentScan("cn.andylhl.xy.service")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 取消数据源的自动配置
public class ServiceSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class);
    }

}
