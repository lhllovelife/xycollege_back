package cn.andylhl.xy.service.trade.controller.api;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.util.JwtInfo;
import cn.andylhl.xy.common.base.util.JwtUtils;
import cn.andylhl.xy.service.trade.entity.Order;
import cn.andylhl.xy.service.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-03-04
 */
//@CrossOrigin
@Api(tags = "网站订单")
@Slf4j
@RestController
@RequestMapping("/api/trade/order")
public class ApiOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * auth 代表：需要登录才能访问该接口
     */
    @ApiOperation(value = "新增订单")
    @PostMapping("/auth/save/{courseId}")
    public R saveOrder(
            @ApiParam(value = "课程id", required = true) @PathVariable("courseId") String courseId,
            HttpServletRequest request) {

        log.info("进入service_trade, 新增订单");

        // 获取token中的信息
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        // 新增订单
        String orderId = orderService.saveOrder(courseId, jwtInfo.getId());

        return R.ok().data("orderId", orderId);
    }

    /**
     * 必须是自己的订单才能查看
     */
    @ApiOperation(value = "获取订单信息")
    @GetMapping("/auth/get/{orderId}")
    public R getOrderInfo(
            @ApiParam(value = "订单id", required = true) @PathVariable("orderId") String orderId,
            HttpServletRequest request) {

        log.info("进入service_trade, 获取订单信息");

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);

        Order order = orderService.getOrdrtInfo(orderId, jwtInfo.getId());

        return R.ok().data("order", order);
    }

    /**
     * 必须是自己的订单才能查看
     */
    @ApiOperation(value = "判断当前课程是否被购买")
    @GetMapping("/auth/is-buy/{courseId}")
    public R isBuyByCourseId (
            @ApiParam(value = "课程id", required = true) @PathVariable("courseId") String courseId,
            HttpServletRequest request) {

        log.info("进入service_trade, 判断当前课程是否被购买");

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);

        Boolean isBuy = orderService.isBuyByCourseId(courseId, jwtInfo.getId());

        return R.ok().data("isBuy", isBuy);

    }

    /**
     * 必须是自己的订单才能查看
     */
    @ApiOperation(value = "获取当前用户订单列表")
    @GetMapping("/auth/order/list")
    public R getOrderList(HttpServletRequest request) {

        log.info("进入service_trade, 获取当前用户订单列表");

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);

        // 根据会员id查询自己的订单列表
        List<Order> orderList = orderService.getOrderList(jwtInfo.getId());

        return R.ok().data("items", orderList);
    }

    /**
     * 必须是自己的订单才能查看
     */
    @ApiOperation(value = "根据订单id删除订单")
    @DeleteMapping("/auth/remove/{orderId}")
    public R removeOrder(
            @ApiParam(value = "orderId", required = true) @PathVariable("orderId") String orderId,
            HttpServletRequest request) {

        log.info("进入service_trade, 根据订单id删除订单");

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);

        Boolean result =  orderService.removeOrder(orderId, jwtInfo.getId());

        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.ok().message("数据不存在");
        }
    }
}

