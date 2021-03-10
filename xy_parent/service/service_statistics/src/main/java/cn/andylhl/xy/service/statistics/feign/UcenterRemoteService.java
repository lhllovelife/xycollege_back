package cn.andylhl.xy.service.statistics.feign;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.statistics.feign.fallback.UcenterRemoteServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/***
 * @Title: UcenterRemoteService
 * @Description: 用户中心远程接口
 * @author: lhl
 * @date: 2021/3/10 15:51
 */

// 1. 指定对应的微服务的名称
// 2. 指定容错类
@Service
@FeignClient(value = "service-ucenter", fallback = UcenterRemoteServiceFallback.class)
public interface UcenterRemoteService {

    /**
     * 根据日期统计注册人数
     * @param day
     * @return
     */
    @GetMapping("/admin/ucenter/member/count-register-num/{day}")
    R countRegisterNum(@PathVariable(value = "day") String day);

}
