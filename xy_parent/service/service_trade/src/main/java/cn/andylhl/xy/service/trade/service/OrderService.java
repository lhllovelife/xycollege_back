package cn.andylhl.xy.service.trade.service;

import cn.andylhl.xy.service.trade.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-03-04
 */
public interface OrderService extends IService<Order> {

    /**
     * 新增订单
     * @param courseId
     * @param memberId
     * @return
     */
    String saveOrder(String courseId, String memberId);

    /**
     * 获取订单信息
     * @param orderId
     * @param memberId
     * @return
     */
    Order getOrdrtInfo(String orderId, String memberId);

    /**
     * 判断当前课程是否被购买
     * @param courseId
     * @param memberId
     * @return
     */
    Boolean isBuyByCourseId(String courseId, String memberId);

    /**
     * 根据会员id查询自己的订单列表
     * @param memberId
     * @return
     */
    List<Order> getOrderList(String memberId);

    /**
     * 根据订单id删除订单
     * @param orderId
     * @return
     */
    Boolean removeOrder(String orderId, String memberId);

    /**
     * 根据订单号获取订单信息
     * @param orderNo
     * @return
     */
    Order getOrderByOrderNo(String orderNo);

    /**
     * 更改购买订单状态, 记录支付日志
     * @param params
     */
    void updateOrderStatus(Map<String, String> params);
}
