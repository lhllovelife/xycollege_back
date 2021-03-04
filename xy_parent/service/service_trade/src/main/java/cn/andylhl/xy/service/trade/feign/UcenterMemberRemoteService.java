package cn.andylhl.xy.service.trade.feign;

import cn.andylhl.xy.service.base.dto.MemberDTO;
import cn.andylhl.xy.service.trade.feign.fallback.UcenterMemberRemoteServiceFallback;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/***
 * @Title: UcenterMemberRemoteService
 * @Description: ucenter模块提供的远程接口
 * @author: lhl
 * @date: 2021/3/4 11:13
 */

// 1. 指定对应的微服务的名称
// 2. 指定容错类
@Service
@FeignClient(value = "service-ucenter", fallback = UcenterMemberRemoteServiceFallback.class)
public interface UcenterMemberRemoteService {

    /**
     * 根据id获取订单中需要的会员信息
     * @param memberId
     * @return
     */
    @GetMapping("/api/ucenter/member/inner/get-member-dto/{memberId}")
    MemberDTO getMemberDTO(@PathVariable("memberId") String memberId);

}
