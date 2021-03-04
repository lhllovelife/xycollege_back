package cn.andylhl.xy.service.trade.service.impl;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.service.base.dto.CourseDTO;
import cn.andylhl.xy.service.base.dto.MemberDTO;
import cn.andylhl.xy.service.trade.entity.Order;
import cn.andylhl.xy.service.trade.feign.EduCourseRemoteService;
import cn.andylhl.xy.service.trade.feign.UcenterMemberRemoteService;
import cn.andylhl.xy.service.trade.mapper.OrderMapper;
import cn.andylhl.xy.service.trade.service.OrderService;
import cn.andylhl.xy.service.trade.util.OrderNoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
}
