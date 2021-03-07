package cn.andylhl.xy.service.trade.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 * @Title: AliPayProperties
 * @Description: 支付配置信息
 * @author: lhl
 * @date: 2021/3/6 23:08
 */

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {

    // 支付宝网关(向支付宝发起请求的网关)
    private String serverUrl;

    // 应用id
    private String appId;

    // 应用私钥
    private String privateKey;

    // 格式
    private String format;

    // 编码
    private String charset;

    // 支付宝公钥
    private String alipayPublicKey;

    // 密钥类型
    private String signType;

    // 回调地址
    private String notifyUrl;

    private String returnUrl;
}
