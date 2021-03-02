package cn.andylhl.xy.service.sms.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 * @Title: MailProperties
 * @Description: 邮件配置信息
 * @author: lhl
 * @date: 2021/3/2 10:04
 */

@Data
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    private String username;

    private String password;
}
