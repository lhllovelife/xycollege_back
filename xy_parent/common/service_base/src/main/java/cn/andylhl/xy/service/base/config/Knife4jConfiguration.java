package cn.andylhl.xy.service.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/***
 * @Title: Knife4jConfiguration
 * @Description: knife配置类
 * @author: lhl
 * @date: 2021/1/29 23:27
 */


@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    /**
     * 网站部分api
     * @return
     */
    @Bean
    public Docket webApiConfig() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(webApiInfo())
                //分组名称
                .groupName("webApi")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("cn.andylhl.xy.service"))
                .paths(PathSelectors.ant("/api/**"))
                .build();
        return docket;
    }


    /**
     * 后台管理系统api
     * @return
     */
    @Bean
    public Docket adminApiConfig() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(webApiInfo())
                //分组名称
                .groupName("adminApi")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("cn.andylhl.xy.service"))
                //只显示admin路径下的页面
                .paths(PathSelectors.ant("/admin/**"))
                .build();
        return docket;
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("网站-API文档")
                .description("本文档描述了网站微服务接口定义")
                .termsOfServiceUrl("https://andylhl.cn/")
                .contact("2432707158@qq.com")
                .version("1.0")
                .build();
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理系统-API文档")
                .description("本文档描述了网站微服务接口定义")
                .termsOfServiceUrl("https://andylhl.cn/")
                .contact("2432707158@qq.com")
                .version("1.0")
                .build();
    }
}
