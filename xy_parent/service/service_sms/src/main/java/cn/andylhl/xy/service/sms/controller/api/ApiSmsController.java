package cn.andylhl.xy.service.sms.controller.api;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.common.base.util.FormUtils;
import cn.andylhl.xy.common.base.util.RandomUtils;
import cn.andylhl.xy.service.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/***
 * @Title: ApiSmsController
 * @Description: 消息管理控制器
 * @author: lhl
 * @date: 2021/3/2 10:20
 */

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "短信管理")
@RequestMapping("/api/sms")
public class ApiSmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("邮件发送")
    @GetMapping("/send/{email}")
    public R getCode(@ApiParam(value = "邮件地址", required = true) @PathVariable("email") String email) {
        log.info("进入service_sms, 邮件发送");

        // 校验邮箱格式是否合法
        if (StringUtils.isEmpty(email) ||  !FormUtils.isEmail(email)) {
            log.error("请输入正确的邮箱地址");
            throw new XyCollegeException(ResultCodeEnum.EMAIL_FORMAT_ERROR);
        }

        // 生成验证码
        String checkCode = RandomUtils.getFourBitRandom();

        // 发送验证码（通过邮件形式发送）
        try {
            smsService.sendEmail(email, checkCode);
        } catch (Exception e) {
            throw new XyCollegeException(ResultCodeEnum.EMAIL_SEND_ERROR);
        }

        // 将验证码存入redis缓存
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 设置超时时间为5分钟
        valueOperations.set(email, checkCode, 5, TimeUnit.MINUTES);

        return R.ok().message("验证码发送成功");
    }

}
