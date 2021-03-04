package cn.andylhl.xy.service.trade.service.impl;

import cn.andylhl.xy.service.trade.entity.Order;
import cn.andylhl.xy.service.trade.mapper.OrderMapper;
import cn.andylhl.xy.service.trade.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lhl
 * @since 2021-03-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    /**
     * 新增订单
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public String saveOrder(String courseId, String memberId) {

        // step1 根据courseId 和 memberId 查询该用户是否已经创建创建该课程的订单
        // 如果已经创建，则返回订单id即可
        // 如果未创建，则进行后续步骤


        // step2 调用edu远程服务接口，根据courseId获取课程相关信息
        /*
            course_id       课程id
            course_title    课程名称
            course_cover    课程封面
            teacher_name    讲师名称

            课程价格
         */

        // step3 调用ucenter远程服务接口，根据memberId获取会员相关信息
        /*
            member_id  会员id
            nickname   会员昵称
            email      会员邮箱地址
         */

        // step4 订单对象信息封装

        return null;
    }
}
