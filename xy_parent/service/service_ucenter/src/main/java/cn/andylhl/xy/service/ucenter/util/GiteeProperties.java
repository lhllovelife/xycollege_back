package cn.andylhl.xy.service.ucenter.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/***
 * @Title: GiteeProperties
 * @Description: Gitee配置信息
 * @author: lhl
 * @date: 2021/3/2 21:22
 */
@Data
@Component
@ConfigurationProperties(prefix = "gitee")
public class GiteeProperties {

    // 应用id
    private String clientId;

    // 应用秘钥
    private String clientSecret;

    // 用户同意授权后的回调地址
    private String redirectUri;

}
