package cn.andylhl.xy.service.base.dto;

import lombok.Data;

import java.io.Serializable;

/***
 * @Title: MemberDTO
 * @Description: 会员信息DTO(服务之间数据传输)
 * @author: lhl
 * @date: 2021/3/4 9:57
 */

@Data
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;//会员id

    private String email;//邮箱地址

    private String nickname;//昵称

}
