package cn.andylhl.xy.service.vod.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 * @Title: VodProperties
 * @Description: 视频点播服务相关配置
 * @author: lhl
 * @date: 2021/2/17 17:31
 */

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.vod")
public class VodProperties {

    private String keyId;

    private String keySecret;
    // 你配置的转码模板组id
    private String templateGroupId;
    // 你配置的工作流id
    private String workflowId;
}