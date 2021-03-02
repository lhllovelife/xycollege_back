package cn.andylhl.xy.service.ucenter.controller.api;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.ucenter.entity.vo.RegisterVO;
import cn.andylhl.xy.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-03-02
 */

@CrossOrigin
@Api(tags = "会员")
@Slf4j
@RestController
@RequestMapping("/api/ucenter/member")
public class ApiMemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员注册")
    @PostMapping("/register")
    public R register(
            @ApiParam(value = "注册对象", required = true)
            @RequestBody RegisterVO registerVO){
        log.info("进入service_edu, 会员注册");

        memberService.register(registerVO);

        return R.ok();
    }

}

