package cn.andylhl.xy.service.statistics.feign.fallback;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.statistics.feign.UcenterRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***
 * @Title: UcenterRemoteServiceFallback
 * @Description: 用户中心接口容错类
 * @author: lhl
 * @date: 2021/3/10 15:52
 */

@Slf4j
@Service
public class UcenterRemoteServiceFallback implements UcenterRemoteService {

    @Override
    public R countRegisterNum(String day) {
        log.error("降级处理");
        return R.ok().data("registerNum", 0);
    }
}
