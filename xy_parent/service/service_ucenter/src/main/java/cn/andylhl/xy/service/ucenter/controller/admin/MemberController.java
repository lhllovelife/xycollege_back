package cn.andylhl.xy.service.ucenter.controller.admin;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @Title: MemberController
 * @Description: 会员管理
 * @author: lhl
 * @date: 2021/3/10 14:49
 */

@Api(tags = "会员管理")
@Slf4j
@RestController
@RequestMapping("/admin/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("根据日期统计注册人数")
    @GetMapping("/count-register-num/{day}")
    public R countRegisterNum(
            @ApiParam(value = "日期", example = "2021-03-10") @PathVariable(value = "day", required = true) String day) {
        log.info("进入service_ucenter, 根据日期统计注册人数");

        Integer num = memberService.countRegisterNum(day);

        return R.ok().data("registerNum", num);

    }

}
