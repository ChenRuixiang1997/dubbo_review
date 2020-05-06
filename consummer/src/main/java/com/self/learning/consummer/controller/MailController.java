package com.self.learning.consummer.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1518:44
 * @Description TODO
 */
@RestController
@RequestMapping(value = "/mail")
public class MailController {

    @Autowired
    JavaMailSender jms;

    /**
     * 带附件发送，可多个附件 图片，doc都可以发送。
     * ADMIN
     * 2018年10月13日 下午12:31:13
     */
    @ApiOperation(value = "发送邮件")
    @PostMapping(value = "/sendFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName",value = "路径数组",required = true,paramType = "query ",dataType = "String[]"),
            @ApiImplicitParam(name = "receiver",value = "邮件接收人",required = true,paramType = "query ",dataType = "String")
    })
    public String sendAttachmentsMail( String[] fileName, String receiver) {
        String [] fileArray=fileName;
        MimeMessage message=jms.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom("631671241@qq.com");
            helper.setTo(receiver);
            helper.setSubject("陈瑞祥");
            helper.setText("带邮件的附件");
            //验证文件数据是否为空
            if(null != fileArray){
                FileSystemResource file=null;
                for (int i = 0; i < fileArray.length; i++) {
                    //添加附件
                    file=new FileSystemResource(fileArray[i]);
                    helper.addAttachment(fileArray[i].substring(fileArray[i].lastIndexOf("\\")), file);
                }
            }
            jms.send(message);
            return "带附件的邮件发送成功";
        }catch (Exception e){
            e.printStackTrace();
            return "发送带附件的邮件失败";
        }
    }
}
