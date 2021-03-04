package cn.andylhl.xy.service.trade.service;

import cn.andylhl.xy.service.trade.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
