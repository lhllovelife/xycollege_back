package cn.andylhl.xy.service.trade.controller.api;

import cn.andylhl.xy.service.trade.entity.Order;
import cn.andylhl.xy.service.trade.entity.PayLog;
import cn.andylhl.xy.service.trade.service.AlipayService;
import cn.andylhl.xy.service.trade.service.OrderService;
import cn.andylhl.xy.service.trade.service.PayLogService;
import cn.andylhl.xy.service.trade.util.AlipayProperties;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/***
 * @Title: ApiAlipayController
 * @Description: 支付宝支付
 * @author: lhl
 * @date: 2021/3/7 12:41
 */

//@CrossOrigin
@Api(tags = "支付宝网站支付")
@Slf4j
@Controller
@RequestMapping("/api/trade/alipay")
public class ApiAlipayController {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private OrderService orderService;


    // 统一收单下单并支付页面接口
    @GetMapping("/trade-page/{orderNo}")
    public @ResponseBody
    String tradePage(@PathVariable("orderNo") String orderNo, HttpServletResponse response) throws AlipayApiException, IOException {
        log.info("进入service_trade, 调用统一下单支付接口");

        String result = alipayService.tradePage(orderNo);

        return result;
    }

    /**
     * 支付宝回调
     *
     * @param request
     */
    @PostMapping("/callback/notity")
    public void notify(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        log.info("进入service_trade, callback/notity被调用");

        // step1: 获取参数
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //step2: 调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getCharset(),
                alipayProperties.getSignType());

        log.info("验签：" + signVerified);
        if (signVerified) {
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            // step3: 判断交易是否成功
            if ("TRADE_SUCCESS".equals(trade_status)) {
                // step4: 判断金额是否一致
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
                // 订单金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

                Order order = orderService.getOrderByOrderNo(out_trade_no);
                if (order != null && total_amount.equals(order.getTotalFee().divide(new BigDecimal(100)).toString())) {

                    // 判断订单状态：保证接口调用的幂等性，如果订单状态已更新直接返回成功响应
                    // 幂等性：无论调用多少次结果都是一样的
                    if (order.getStatus() == Order.ORDER_STATUS_NON_PAYMENT) {
                        // 未支付
                        // 更改购买订单状态, 记录支付日志
                        orderService.updateOrderStatus(params);
                    }
                    log.info("支付成功，通知已处理");
                    return;
                }

            }

        }
        log.info("支付失败，通知已处理");
    }

    /**
     * 跳转
     */
    @GetMapping("/callback/return")
    public String callbackReturn(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {

        log.info("进入service_trade, callback/return被调用");
        // 参数获取
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        // 调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getCharset(),
                alipayProperties.getSignType());

        // 验签成功
        if (signVerified) {

            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            Order order = orderService.getOrderByOrderNo(out_trade_no);
            if (order != null) {
                // 跳转到课程页
                return "redirect:http://127.0.0.1:2001/course/" + order.getCourseId();
            }
        }

        return "";
    }

}