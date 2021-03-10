package cn.andylhl.xy.service.ucenter.controller.api;


import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.common.base.util.JwtInfo;
import cn.andylhl.xy.common.base.util.JwtUtils;
import cn.andylhl.xy.service.base.dto.MemberDTO;
import cn.andylhl.xy.service.ucenter.entity.vo.LoginVO;
import cn.andylhl.xy.service.ucenter.entity.vo.RegisterVO;
import cn.andylhl.xy.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-03-02
 */

//@CrossOrigin
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
        log.info("进入service_ucenter, 会员注册");

        memberService.register(registerVO);
        return R.ok().message("注册成功");
    }

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(@ApiParam(value = "登录对象", required = true)
                   @RequestBody LoginVO loginVO) {
        log.info("进入service_ucenter, 会员登录");

        String token = memberService.login(loginVO);

        return R.ok().message("登录成功").data("token", token);
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("/get-login-info")
    public R getLogioIngo(HttpServletRequest request) {
        log.info("进入service_ucenter, 根据token获取登录信息");

        try {
            JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
            return R.ok().data("userInfo", jwtInfo);
        } catch (Exception e) {
            // 抛出获取信息失败异常
            throw new XyCollegeException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
    }

    /**
     * inner 供微服务进行调用
     */
    @ApiOperation("根据id获取订单中需要的会员信息")
    @GetMapping("/inner/get-member-dto/{memberId}")
    public MemberDTO getMemberDTO(
            @ApiParam(value = "会员id", required = true)
            @PathVariable("memberId") String memberId) {
        log.info("进入service_ucenter, 根据id获取订单中需要的会员信息");

        MemberDTO memberDTO = memberService.getMemberDTO(memberId);

        return memberDTO;

    }

}

