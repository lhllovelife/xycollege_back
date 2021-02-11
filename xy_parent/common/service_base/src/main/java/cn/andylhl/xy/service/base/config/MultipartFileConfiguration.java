package cn.andylhl.xy.service.base.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/***
 * @Title: MultipartFileConfiguration
 * @Description: 设置上传文件大小
 * @author: lhl
 * @date: 2021/2/11 15:24
 */

@Configuration
public class MultipartFileConfiguration {

    /**
     * 设置文件上传大小
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(5)); //MB
        //factory.setMaxFileSize(DataSize.ofKilobytes(80)); //KB
        //factory.setMaxFileSize(DataSize.ofGigabytes(80)); //Gb
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(50));
        return factory.createMultipartConfig();
    }

}
