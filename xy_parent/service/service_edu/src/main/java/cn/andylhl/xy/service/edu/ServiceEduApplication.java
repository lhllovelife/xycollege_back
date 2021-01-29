package cn.andylhl.xy.service.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/***
 * @Title: ServiceEduApplication
 * @Description: 讲师管理模块启动类
 * @author: lhl
 * @date: 2021/1/29 22:12
 */

@ComponentScan("cn.andylhl.xy.service")
@SpringBootApplication
public class ServiceEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEduApplication.class);
    }
}
