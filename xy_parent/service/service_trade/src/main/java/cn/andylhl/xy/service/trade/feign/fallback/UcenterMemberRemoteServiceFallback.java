package cn.andylhl.xy.service.trade.feign.fallback;

import cn.andylhl.xy.service.base.dto.MemberDTO;
import cn.andylhl.xy.service.trade.feign.UcenterMemberRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***
 * @Title: UcenterMemberRemoteServiceFallback
 * @Description:
 * @author: lhl
 * @date: 2021/3/4 11:25
 */

@Slf4j
@Service
public class UcenterMemberRemoteServiceFallback implements UcenterMemberRemoteService {

    @Override
    public MemberDTO getMemberDTO(String memberId) {
        log.error("降级处理");
        return null;
    }
}
