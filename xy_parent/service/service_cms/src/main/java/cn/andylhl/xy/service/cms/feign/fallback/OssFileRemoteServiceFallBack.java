package cn.andylhl.xy.service.cms.feign.fallback;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.cms.feign.OssFileRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***
 * @Title: OssFileRemoteServiceFallBack
 * @Description: oss服务容错类
 * @author: lhl
 * @date: 2021/2/24 23:26
 */

@Slf4j
@Service
public class OssFileRemoteServiceFallBack implements OssFileRemoteService {

    @Override
    public R removeFile(String url) {
        log.info("service-cms ossfile 降级处理");
        return R.error();
    }
}
