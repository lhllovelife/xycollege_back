package cn.andylhl.xy.service.trade.service;

import com.alipay.api.AlipayApiException;

/***
 * @Title: AlipayService
 * @Description:
 * @author: lhl
 * @date: 2021/3/7 13:18
 */

public interface AlipayService {

    /**
     * 调用统一下单支付接口
     * @param orderNo
     * @return
     */
    String tradePage(String orderNo) throws AlipayApiException;
}
