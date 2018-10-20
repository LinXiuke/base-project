package com.zero.project.biz.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2018/10/20
 */
@Service
public class EmailManager {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender jms;

    public void send() {
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom(from);
        //接收者
        mainMessage.setTo("xxx@qq.com");
        //发送的标题
        mainMessage.setSubject("test");
        //发送的内容
        mainMessage.setText("hello world");
        jms.send(mainMessage);
    }
}
