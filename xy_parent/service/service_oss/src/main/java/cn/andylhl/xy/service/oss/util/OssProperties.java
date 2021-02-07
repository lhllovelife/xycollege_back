package cn.andylhl.xy.service.oss.util;

import com.sun.xml.internal.ws.server.ServerRtException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 * @Title: OssProperties
 * @Description: 阿里云对象存储服务配置信息
 * @author: lhl
 * @date: 2021/2/6 18:01
 */

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    private String keyId;
    private String keySecret;
    private String bucketName;
    private String endpoint;

}
