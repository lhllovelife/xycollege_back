package cn.andylhl.xy.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

/***
 * @Title: LoginVO
 * @Description: 登录表单VO
 * @author: lhl
 * @date: 2021/3/2 16:33
 */
@Data
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String password;

}
