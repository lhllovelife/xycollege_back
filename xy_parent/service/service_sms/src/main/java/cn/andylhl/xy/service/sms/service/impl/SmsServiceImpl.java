package cn.andylhl.xy.service.sms.service.impl;

import cn.andylhl.xy.service.sms.service.SmsService;
import cn.andylhl.xy.service.sms.util.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/***
 * @Title: SmsService
 * @Description: 信息发送业务类
 * @author: lhl
 * @date: 2021/3/2 10:16
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private JavaMailSender jms;

    @Autowired
    private MailProperties mailProperties;

    /**
     * 将验证码以邮件形式发送到指定邮箱
     * @param email
     * @param checkCode
     */
    @Override
    public void sendEmail(String email, String checkCode) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(email); // 接收地址
        message.setSubject("馨月学院"); // 标题
        message.setText("【馨月学院】" + checkCode +  "（验证码），用于验证身份、修改密码等，请勿向他人泄露。"); // 内容
        jms.send(message);
    }

}
/*
【极客时间】186322（验证码），用于验证身份、修改密码等，请勿向他人泄露。
 */
