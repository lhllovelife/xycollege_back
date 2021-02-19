package cn.andylhl.xy.service.edu.feign;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.feign.fallback.VodMediaRemoteServiceFallBack;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/***
 * @Title: VodMediaRemoteService
 * @Description: 视频点播远程服务
 * @author: lhl
 * @date: 2021/2/19 15:53
 */

// 1. 指定对应的微服务的名称
// 2. 指定容错类
@Service
@FeignClient(value = "service-vod", fallback = VodMediaRemoteServiceFallBack.class)
public interface VodMediaRemoteService {


    /**
     * 删除视频
     * @param videoIdList videoId集合
     * @return
     */
    @DeleteMapping("/admin/vod/media/remove")
    R removeVideo(@RequestBody List<String> videoIdList);

}
