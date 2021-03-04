package cn.andylhl.xy.service.trade.controller.api;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.util.JwtInfo;
import cn.andylhl.xy.common.base.util.JwtUtils;
import cn.andylhl.xy.service.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-03-04
 */
@CrossOrigin
@Api(tags = "网站订单")
@Slf4j
@RestController
@RequestMapping("/api/trade/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * auth 代表：需要登录才能访问该接口
     */
    @ApiOperation(value = "新增订单")
    @PostMapping("/auth/save/{courseId}")
    public R register(
            @ApiParam(value = "课程id", required = true) @PathVariable("courseId") String courseId,
            HttpServletRequest request) {

        log.info("进入service_trade, 新增订单");

        // 获取token中的信息
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        // 新增订单
        String orderId = orderService.saveOrder(courseId, jwtInfo.getId());

        return R.ok().data("orderId", orderId);
    }
}

