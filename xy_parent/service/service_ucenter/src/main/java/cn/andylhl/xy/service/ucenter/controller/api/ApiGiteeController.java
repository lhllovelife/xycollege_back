package cn.andylhl.xy.service.ucenter.controller.api;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.common.base.util.HttpClientUtils;
import cn.andylhl.xy.common.base.util.JwtInfo;
import cn.andylhl.xy.common.base.util.JwtUtils;
import cn.andylhl.xy.service.ucenter.entity.Member;
import cn.andylhl.xy.service.ucenter.service.MemberService;
import cn.andylhl.xy.service.ucenter.util.GiteeProperties;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/***
 * @Title: ApiGiteeController
 * @Description: 整合第三方登录 Gitee
 * @author: lhl
 * @date: 2021/3/2 21:27
 */

//@CrossOrigin
@Api(tags = "第三方登录（Gitee）")
@Slf4j
@Controller
@RequestMapping("/api/ucenter/gitee")
public class ApiGiteeController {

    @Autowired
    private GiteeProperties giteeProperties;

    @Autowired
    private MemberService memberService;

    /**
     * 将用户引导到码云三方认证页面上
     * @return
     */
    @GetMapping("/login")
    public String toGiteeAuthPage() {

        String baseUrl = "https://gitee.com/oauth/authorize?"
                + "client_id=%s"
                + "&redirect_uri=%s"
                + "&response_type=code";

        String url = String.format(baseUrl, giteeProperties.getClientId(), giteeProperties.getRedirectUri());
        log.info("url: " + url);

        return "redirect:" + url;
    }

    /**
     * 码云认证服务器通过回调地址{redirect_uri}将用户授权码 传递给 应用服务器
     * @param code
     */
    @GetMapping("/callback")
    public String callback(String code, String error, String error_description) {
        log.info("callback被调用");
        log.info("code: " + code);

        // step0: 判断回调是否有误
        if (!StringUtils.isEmpty(error)) {
            log.error(error + " " + error_description);
            throw new XyCollegeException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        if (StringUtils.isEmpty(code)) {
            log.error("非法回调请求");
            throw new XyCollegeException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        // step1: 向码云认证服务器发送post请求传入 用户授权码 以及 回调地址
        // 携带授权临时票据code和 appid appidsecret请求access_token
        String accessTokenUrl = "https://gitee.com/oauth/token";
        // 准备请求参数
        Map<String, String> accessTokenParam = new HashMap<>();
        accessTokenParam.put("grant_type", "authorization_code");
        accessTokenParam.put("code", code);
        accessTokenParam.put("client_id", giteeProperties.getClientId());
        accessTokenParam.put("redirect_uri", giteeProperties.getRedirectUri());
        accessTokenParam.put("client_secret", giteeProperties.getClientSecret());

        // 创建请求对象
        HttpClientUtils client = new HttpClientUtils(accessTokenUrl, accessTokenParam);

        // 发送请求获取access_token
        String result = "";
        try {
            // 发送post请求
            client.post();
            result = client.getContent();
            log.info("result: " + result);
        } catch (Exception e) {
            log.error("获取access_token失败");
            throw new XyCollegeException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        // 分析响应结果
        Gson gson = new Gson();
        HashMap<String, Object> resultMap = gson.fromJson(result, HashMap.class);
        if (resultMap.get("error") != null) {
            log.error("获取access_token失败：error: {}, error_description: {}", resultMap.get("error"), resultMap.get("invalid_grant"));
            throw new XyCollegeException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        // 获取access_token成功，从响应结果中取值出access_token
        String accessToken = (String) resultMap.get("access_token");


        // step2: 应用通过access_token访问Gitee服务器，获取用户数据
        String baseUserInfoUrl = "https://gitee.com/api/v5/user";
        // 准备请求参数
        Map<String, String> userInfoParam = new HashMap<>();
        userInfoParam.put("access_token", accessToken);
        // 创建请求对象
        client = new HttpClientUtils(baseUserInfoUrl, userInfoParam);

        String userInfoResult = "";
        try {
            client.get();
            userInfoResult = client.getContent();

        } catch (Exception e) {
            // 请求出现异常
            log.error("获取用户信息失败");
            throw new XyCollegeException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
        // 处理响应结果
        HashMap<String, Object> userInfoResultMap = gson.fromJson(userInfoResult, HashMap.class);
        if (userInfoResultMap.get("message") != null) {
            log.error("获取用户信息失败: {}", userInfoResultMap.get("message"));
            throw new XyCollegeException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }

        // 成功获取到用户信息
        String openid = ((Double) userInfoResultMap.get("id")).toString();

        // step3: 根据openid判断该用户是否已经被注册
        Member member = memberService.getByOpenId(openid);
        // 如果不存在，则进行用户注册
        if (member == null) {

            // 从获取到的用户信息中取出可用的信息

            // 头像地址
            String avatar = (String) userInfoResultMap.get("avatar_url");
            // 昵称
            String nickname = (String) userInfoResultMap.get("name");

            // 创建member对象
            member = new Member();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(avatar);

            // 注册用户（默认创建用户 第一次授权时）
            memberService.save(member);
        }

        // 用户登录，颁发JwtToken
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(member.getId());
        jwtInfo.setNickname(member.getNickname());
        jwtInfo.setAvatar(member.getAvatar());
        // 生成JwtToken令牌
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 1800);
        log.info("jwtToken: "  + jwtToken);
        // 携带token跳转到首页
        return "redirect:http://localhost:2001/?token=" + jwtToken;
    }

}

