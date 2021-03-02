package cn.andylhl.xy.service.sms.service;

/***
 * @Title: SmsService
 * @Description: 消息业务接口
 * @author: lhl
 * @date: 2021/3/2 10:18
 */
public interface SmsService {

    /**
     * 邮箱形式发送验证码
     * @param email
     * @param checkCode
     */
    void sendEmail(String email,  String checkCode);

}
