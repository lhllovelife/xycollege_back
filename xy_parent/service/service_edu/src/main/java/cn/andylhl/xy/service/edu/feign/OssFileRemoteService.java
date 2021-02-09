package cn.andylhl.xy.service.edu.feign;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.feign.fallback.OssFileRemoteServiceFallBack;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/***
 * @Title: OssFileRemoteService
 * @Description: oos远程服务
 * @author: lhl
 * @date: 2021/2/8 16:35
 */
// 1. 指定对应的微服务的名称
// 2. 指定容错类
@Service
@FeignClient(value = "service-oss", fallback = OssFileRemoteServiceFallBack.class)
public interface OssFileRemoteService {

    @GetMapping("/admin/oss/file/test")
    R test();

    /**
     * 删除文件
     * @return
     * @param url
     */
    @DeleteMapping("/admin/oss/file/remove")
    R removeFile(@RequestBody String url);
}
