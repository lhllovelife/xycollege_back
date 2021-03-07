package cn.andylhl.xy.service.trade.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * @Title: AliPaySDKConfiguration
 * @Description: 阿里云配置类
 * @author: lhl
 * @date: 2021/3/6 23:14
 */

@Configuration
public class AlipaySDKConfiguration {

    @Autowired
    private AlipayProperties aliPayProperties;

    /**
     * 将client对象交由spring容器管理
     * @return
     */
    @Bean
    public AlipayClient alipayClient() {

        return new DefaultAlipayClient(
                aliPayProperties.getServerUrl(),
                aliPayProperties.getAppId(),
                aliPayProperties.getPrivateKey(),
                aliPayProperties.getFormat(),
                aliPayProperties.getCharset(),
                aliPayProperties.getAlipayPublicKey(),
                aliPayProperties.getSignType()
        );
    }

}
