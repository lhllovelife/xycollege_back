package cn.andylhl.xy.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

/***
 * @Title: RegisterVO
 * @Description: 注册VO对象
 * @author: lhl
 * @date: 2021/3/2 12:52
 */

@Data
public class RegisterVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 昵称
    private String nickname;

    // 邮箱地址
    private String email;

    // 密码
    private String password;

    // 验证码
    private String code;

}
