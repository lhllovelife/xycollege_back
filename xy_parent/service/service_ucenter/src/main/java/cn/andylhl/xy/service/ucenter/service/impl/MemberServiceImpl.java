package cn.andylhl.xy.service.ucenter.service.impl;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.common.base.util.FormUtils;
import cn.andylhl.xy.service.ucenter.entity.Member;
import cn.andylhl.xy.service.ucenter.entity.vo.RegisterVO;
import cn.andylhl.xy.service.ucenter.mapper.MemberMapper;
import cn.andylhl.xy.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
}
