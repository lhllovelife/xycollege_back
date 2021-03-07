package cn.andylhl.xy.service.trade.service.impl;

import cn.andylhl.xy.service.trade.entity.Order;
import cn.andylhl.xy.service.trade.service.AlipayService;
import cn.andylhl.xy.service.trade.service.OrderService;
import cn.andylhl.xy.service.trade.util.AlipayProperties;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/***
 * @Title: AlipayServiceImpl
 * @Description: 支付业务实现类
 * @author: lhl
 * @date: 2021/3/7 13:18
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayClient client;

    @Autowired
    private AlipayProperties aliPayProperties;

    @Autowired
    private OrderService orderService;

    /**
     * 调用统一下单支付接口, 返回支付页面
     * @param orderNo
     * @return
     */
    @Override
    public String tradePage(String orderNo) throws AlipayApiException {

        // step0: 根据订单号获取订单信息
        Order order = orderService.getOrderByOrderNo(orderNo);

        String out_trade_no = order.getOrderNo(); // 商户订单号
        String total_amount = order.getTotalFee().divide(new BigDecimal(100)).toString(); // 订单总金额，单位为元
        String subject = order.getCourseTitle() + " - " + order.getTeacherName(); // 订单标题
        
        // 准备参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("out_trade_no", out_trade_no);
        paramMap.put("total_amount", total_amount);
        paramMap.put("subject", subject);
        paramMap.put("product_code", "FAST_INSTANT_TRADE_PAY"); // 销售产品码

        // 将参数转换为json字符串
        Gson gson = new Gson();
        String paramString = gson.toJson(paramMap);

        // step1: 获得初始化的AlipayClient
        // step2: 创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        // 在公共参数中设置回跳和通知地址
         alipayRequest.setReturnUrl(aliPayProperties.getReturnUrl());
        // 支付成功的回调地址
        alipayRequest.setNotifyUrl(aliPayProperties.getNotifyUrl());

        //填充业务参数
        //  alipayRequest.setBizContent(paramString);
        alipayRequest.setBizContent(paramString);
        // step4:调用SDK生成表单
        AlipayTradePagePayResponse response = client.pageExecute(alipayRequest);

        return response.getBody();
    }
}
