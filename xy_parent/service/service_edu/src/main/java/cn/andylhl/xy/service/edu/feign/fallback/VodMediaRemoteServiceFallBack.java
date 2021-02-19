package cn.andylhl.xy.service.edu.feign.fallback;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.feign.VodMediaRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @Title: VodMediaRemoteServiceFallBack
 * @Description: 视频点播服务容错类（降级）
 * @author: lhl
 * @date: 2021/2/19 15:54
 */

@Slf4j
@Service
public class VodMediaRemoteServiceFallBack implements VodMediaRemoteService {

    @Override
    public R removeVideo(List<String> videoIdList) {
        log.info("service-vod removeVideo 降级处理");
        return R.error();
    }
}
