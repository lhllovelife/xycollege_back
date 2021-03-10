package cn.andylhl.xy.service.trade.controller.admin;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.trade.entity.Order;
import cn.andylhl.xy.service.trade.service.OrderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * @Title: OrderController
 * @Description: 订单管理
 * @author: lhl
 * @date: 2021/3/10 22:51
 */

@Api(tags = "订单管理")
@Slf4j
@RestController
@RequestMapping("/admin/trade/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("分页查询订单信息")
    @GetMapping("/list/{page}/{limit}")
    public R pageList(
            @ApiParam("页码") @PathVariable("page") Long page,
            @ApiParam("每页显示记录条数") @PathVariable("limit") Long limit) {

        log.info("进入service_trade, 分页查询订单信息");

        Page<Order> pageModel = orderService.selectPage(page, limit);

        // 总记录条数
        long total = pageModel.getTotal();
        // 结果集合
        List<Order> orderList = pageModel.getRecords();

        return R.ok().data("total", total).data("rows", orderList);
    }


}
