package cn.andylhl.xy.infrastructure.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/***
 * @Title: InfrastructureApiGatewayApplication
 * @Description: 网关启动类
 * @author: lhl
 * @date: 2021/3/9 15:03
 */

@EnableDiscoveryClient // 启动注册客户端
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 取消数据源的自动配置
public class InfrastructureApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfrastructureApiGatewayApplication.class);
    }

}
