package cn.andylhl.xy.service.ucenter.service.impl;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.common.base.util.FormUtils;
import cn.andylhl.xy.common.base.util.JwtInfo;
import cn.andylhl.xy.common.base.util.JwtUtils;
import cn.andylhl.xy.service.base.dto.MemberDTO;
import cn.andylhl.xy.service.ucenter.entity.Member;
import cn.andylhl.xy.service.ucenter.entity.vo.LoginVO;
import cn.andylhl.xy.service.ucenter.entity.vo.RegisterVO;
import cn.andylhl.xy.service.ucenter.mapper.MemberMapper;
import cn.andylhl.xy.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lhl
 * @since 2021-03-02
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 会员注册
     *
     * @param registerVO
     */
    @Override
    public void register(RegisterVO registerVO) {

        // 参数获取
        String nickname = registerVO.getNickname();
        String email = registerVO.getEmail();
        String code = registerVO.getCode();
        String password = registerVO.getPassword();

        // 校验参数（参数不合法时，抛出参数错误异常）
        if (StringUtils.isEmpty(email)
                || !FormUtils.isEmail(email)
                || StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(password)) {

            throw new XyCollegeException(ResultCodeEnum.PARAM_ERROR);
        }

        // 验证码校验
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String codeFormRedis = (String)valueOperations.get(email);
        if (!code.equals(codeFormRedis)) {
            // 抛出验证码错误异常
            throw new XyCollegeException(ResultCodeEnum.CODE_ERROR);
        }
        // 验证码一致，则删除redis缓存中保存的验证码
        redisTemplate.delete(email);

        // 判断账号是否已被注册
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        Member member = baseMapper.selectOne(queryWrapper);
        if (member != null) {
            // 抛出邮箱已被注册异常
            throw new XyCollegeException(ResultCodeEnum.REGISTER_EMAIL_ERROR);
        }

        // 执行注册，新增一条用户信息

        // 密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordAfterEncoder = passwordEncoder.encode(password);

        // 创建会员对象
        member = new Member();
        member.setEmail(email);
        member.setNickname(nickname);
        member.setPassword(passwordAfterEncoder);
        // 设置默认头像
        member.setAvatar("https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg?imageView2/1/w/80/h/80");
        member.setDisabled(false);
        baseMapper.insert(member);
    }

    /**
     * 会员登录
     * @param loginVO
     * @return
     */
    @Override
    public String login(LoginVO loginVO) {

        // 参数获取
        String email = loginVO.getEmail();
        String password = loginVO.getPassword();

        // 参数校验
        if (StringUtils.isEmpty(email)
                || !FormUtils.isEmail(email)
                || StringUtils.isEmpty(password)) {
            // 抛出参数错误异常
            throw new XyCollegeException(ResultCodeEnum.PARAM_ERROR);
        }

        // 校验邮箱（根据邮箱地址判断该邮箱的用户是否存在）
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        Member member = baseMapper.selectOne(queryWrapper);
        if (member == null) {
            // 抛出登录邮箱地址不正确异常
            throw new XyCollegeException(ResultCodeEnum.LOGIN_EMAIL_ERROR);
        }

        // 校验密码是否正确
        String passwordFromDB = member.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMatch = passwordEncoder.matches(password, passwordFromDB);
        if (!isMatch) {
            // 抛出密码错误异常
            throw new XyCollegeException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        // 检查该用户是否已被禁用
        // true代表禁用
        // false代表未禁用
        Boolean disabled = member.getDisabled();
        if (disabled) {
            // 抛出用户已被禁用异常
            throw new XyCollegeException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        // 准备jwtinfo
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(member.getId());
        jwtInfo.setNickname(member.getNickname());
        jwtInfo.setAvatar(member.getAvatar());

        // 生成token令牌(过期时间为秒)
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 1800);

        return jwtToken;
    }

    /**
     * 根据openid获取会员对象
     * @param openid
     * @return
     */
    @Override
    public Member getByOpenId(String openid) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据id获取订单中需要的会员信息
     * @param memberId
     * @return
     */
    @Override
    public MemberDTO getMemberDTO(String memberId) {

        MemberDTO memberDTO = baseMapper.selectMemberDTOByMemberId(memberId);

        return memberDTO;
    }

    /**
     * 根据日期统计注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegisterNum(String day) {

        return baseMapper.selectRegisterNumByDay(day);
    }

    /**
     * 分页展示用户信息
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<Member> selectPage(Long page, Long limit) {

        // 封装分页对象
        Page<Member> pageParam = new Page<>(page, limit);
        // 准备查询参数（按照用户注册时间倒序）
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        return baseMapper.selectPage(pageParam, queryWrapper);
    }
}
