package cn.andylhl.xy.service.ucenter.service;

import cn.andylhl.xy.service.ucenter.entity.Member;
import cn.andylhl.xy.service.ucenter.entity.vo.LoginVO;
import cn.andylhl.xy.service.ucenter.entity.vo.RegisterVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-03-02
 */
public interface MemberService extends IService<Member> {

    /**
     * 会员注册
     * @param registerVO
     */
    void register(RegisterVO registerVO);

    /**
     * 会员登录
     * @param loginVO
     * @return
     */
    String login(LoginVO loginVO);
}
