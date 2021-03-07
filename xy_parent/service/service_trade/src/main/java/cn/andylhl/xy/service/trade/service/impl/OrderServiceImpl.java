package cn.andylhl.xy.service.trade.service.impl;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.service.base.dto.CourseDTO;
import cn.andylhl.xy.service.base.dto.MemberDTO;
import cn.andylhl.xy.service.trade.entity.Order;
import cn.andylhl.xy.service.trade.entity.PayLog;
import cn.andylhl.xy.service.trade.feign.EduCourseRemoteService;
import cn.andylhl.xy.service.trade.feign.UcenterMemberRemoteService;
import cn.andylhl.xy.service.trade.mapper.OrderMapper;
import cn.andylhl.xy.service.trade.mapper.PayLogMapper;
import cn.andylhl.xy.service.trade.service.OrderService;
import cn.andylhl.xy.service.trade.util.OrderNoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import net.bytebuddy.asm.Advice;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private EduCourseRemoteService eduCourseRemoteService;

    @Autowired
    private UcenterMemberRemoteService ucenterMemberRemoteService;

    @Autowired
    private PayLogMapper payLogMapper;

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
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id", courseId)
                .eq("member_id", memberId);
        Order orderExist = baseMapper.selectOne(queryWrapper);
        if (orderExist != null) {
            // 订单不为空，说明该用户已创建该课程的订单, 直接返回订单的id
            return orderExist.getId();
        }


        // step2 调用edu远程服务接口，根据courseId获取课程相关信息
        /*
            course_id       课程id
            course_title    课程名称
            course_cover    课程封面
            teacher_name    讲师名称
            coursePrice     课程价格
         */
        CourseDTO courseDTO = eduCourseRemoteService.getCourseDTO(courseId);
        if (courseDTO == null) {
            throw new XyCollegeException(ResultCodeEnum.PARAM_ERROR); // 参数不正确，查询不到课程信息，或者服务降级处理
        }


        // step3 调用ucenter远程服务接口，根据memberId获取会员相关信息
        /*
            member_id  会员id
            nickname   会员昵称
            email      会员邮箱地址
         */
        MemberDTO memberDTO = ucenterMemberRemoteService.getMemberDTO(memberId);
        if (memberDTO == null) {
            throw new XyCollegeException(ResultCodeEnum.PARAM_ERROR); // 参数不正确，查询不到会员信息，或者服务降级处理
        }

        // step4 订单对象信息封装
        Order order = new Order();
        order.setOrderNo(OrderNoUtils.getOrderNo());

        order.setCourseId(courseId);
        order.setCourseTitle(courseDTO.getTitle());
        order.setCourseCover(courseDTO.getCover());
        order.setTeacherName(courseDTO.getTeacherName());
        order.setTotalFee(courseDTO.getPrice().multiply(new BigDecimal(100))); // 订单金额（分）

        order.setMemberId(memberId);
        order.setNickname(memberDTO.getNickname());
        order.setEmail(memberDTO.getEmail());

        // 设置订单状态 (未支付)
        order.setStatus(Order.ORDER_STATUS_NON_PAYMENT);
        // 设置支付方式 (支付宝支付)
        order.setPayType(Order.PAY_TYPE_ALIPAY);

        // 保存一个新的订单
        baseMapper.insert(order);

        return order.getId();
    }

    /**
     * 获取订单信息
     * @param orderId
     * @param memberId
     * @return
     */
    @Override
    public Order getOrdrtInfo(String orderId, String memberId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper();
        queryWrapper
                .eq("id", orderId)
                .eq("member_id",memberId);

        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 判断当前课程是否被购买
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public Boolean isBuyByCourseId(String courseId, String memberId) {

        // 方法一 ：

        /*
        // step1 查看用户是否有该课程的订单
        // 无订单，直接返回false
        // 有订单，进一步判断是否已支付
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id", courseId)
                .eq("member_id", memberId);
        Order order = baseMapper.selectOne(queryWrapper);
        if (order == null) {
            // 该用户没有该课程的订单，所以未购买
            return false;
        }

        // step2 判断订单支付状态
        Integer status = order.getStatus();

        if (status == Order.ORDER_STATUS_NON_PAYMENT) {
            // 未支付
            return false;
        }

        // 执行到该处说明已支付
        return true;
         */

        // 方法二：sql查询用户是否有该课程已支付的订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id", courseId)
                .eq("member_id", memberId)
                .eq("status", Order.ORDER_STATUS_PAYMENT_RECEIVED);
        Order order = baseMapper.selectOne(queryWrapper);

        return order == null ? false : true;
    }

    /**
     * 根据会员id查询自己的订单列表
     * @param memberId
     * @return
     */
    @Override
    public List<Order> getOrderList(String memberId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberId);

        List<Order> orderList = baseMapper.selectList(queryWrapper);

        return orderList;
    }

    /**
     * 根据订单id删除订单
     * @param orderId
     * @param memberId
     * @return
     */
    @Override
    public Boolean removeOrder(String orderId, String memberId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id", orderId)
                .eq("member_id", memberId);

        return this.remove(queryWrapper);
    }

    /**
     * 根据订单号获取订单信息
     * @param orderNo
     * @return
     */
    @Override
    public Order getOrderByOrderNo(String orderNo) {

        QueryWrapper<Order>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 更改购买订单状态, 记录支付日志
     * @param params
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(Map<String, String> params) {

        // 更改订单状态
        String orderNo = params.get("out_trade_no");
        Order order = this.getOrderByOrderNo(orderNo);
        order.setStatus(Order.ORDER_STATUS_PAYMENT_RECEIVED); // 修改状态未支付成功
        baseMapper.updateById(order);

        // 记录支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo); // 订单号
        payLog.setPayTime(new Date()); // 支付完成时间
        payLog.setTotalFee(new BigDecimal(Double.parseDouble(params.get("total_amount").trim())*100).longValue()); // 支付金额（分）
        payLog.setTransactionId(params.get("trade_no")); // 交易流水号
        payLog.setTradeState(params.get("trade_status")); // 交易状态
        payLog.setPayType(Order.PAY_TYPE_ALIPAY); // 支付类型（1：微信 2：支付宝）
        payLog.setAttr(new Gson().toJson(params));
        payLogMapper.insert(payLog);

        // TODO 更新课程销量
        eduCourseRemoteService.updateCourseBuyCount(order.getCourseId());

    }
}
