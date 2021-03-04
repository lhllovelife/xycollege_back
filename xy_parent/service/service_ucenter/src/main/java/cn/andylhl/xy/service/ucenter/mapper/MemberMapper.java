package cn.andylhl.xy.service.ucenter.mapper;

import cn.andylhl.xy.service.base.dto.MemberDTO;
import cn.andylhl.xy.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lhl
 * @since 2021-03-02
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 根据id获取订单中需要的会员信息
     * @param memberId
     * @return
     */
    MemberDTO selectMemberDTOByMemberId(String memberId);
}
