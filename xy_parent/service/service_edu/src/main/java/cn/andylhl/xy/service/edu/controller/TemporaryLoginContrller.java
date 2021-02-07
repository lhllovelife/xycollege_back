package cn.andylhl.xy.service.edu.controller;

import cn.andylhl.xy.common.base.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/***
 * @Title: TemporaryLoginContrller
 * @Description: 临时登录接口
 * @author: lhl
 * @date: 2021/2/3 20:06
 */

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/user")
public class TemporaryLoginContrller {

    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public R login() {
        log.info("临时登录接口");
        return R.ok().data("token", "admin");
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public R info() {
        return R.ok()
                .data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
    }

    @PostMapping("/logout")
    public R logout(){
        return R.ok();
    }


}
