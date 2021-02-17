package cn.andylhl.xy.service.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/***
 * @Title: ServiceVodApplication
 * @Description: 视频点播服务启动类
 * @author: lhl
 * @date: 2021/2/17 17:27
 */

@EnableDiscoveryClient // 启用注册中心客户端
@ComponentScan("cn.andylhl.xy.service") // 指定组件扫描路径
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 取消数据源的自动配置
public class ServiceVodApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class);
    }

}
