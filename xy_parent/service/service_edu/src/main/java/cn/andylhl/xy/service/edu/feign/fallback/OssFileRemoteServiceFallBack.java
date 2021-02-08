package cn.andylhl.xy.service.edu.feign.fallback;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.feign.OssFileRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***
 * @Title: OssFileRemoteServiceFallBack
 * @Description: 文件上传的容错类（降级）
 * @author: lhl
 * @date: 2021/2/8 20:51
 */
@Slf4j
@Service
public class OssFileRemoteServiceFallBack implements OssFileRemoteService {

    @Override
    public R test() {
        log.info("service-edu ossfile 降级处理");
        return R.error();
    }

    @Override
    public R removeFile(String url) {
        log.info("service-edu ossfile removeFile 降级处理");
        return R.error();
    }
}
