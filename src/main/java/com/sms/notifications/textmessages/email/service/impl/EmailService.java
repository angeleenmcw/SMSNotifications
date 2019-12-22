package com.sms.notifications.textmessages.email.service.impl;

import com.sms.notifications.textmessages.email.model.Mail;
import com.sms.notifications.textmessages.email.service.IEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailService implements IEmailService {

    @Value("${spring.mail.sender}")
    private String sender;

    private JavaMailSender javaMailSender;

    private Configuration freemarkerConfig;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, Configuration freemarkerConfig) {
        this.javaMailSender = javaMailSender;
        this.freemarkerConfig = freemarkerConfig;
        this.freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/");
    }

    @Override
    public void sendSimpleEmail(Mail mail) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getTo());
        message.setFrom(this.sender);
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        javaMailSender.send(message);
    }

    @Override
    public void sendEmail(Mail mail) throws MailException, MessagingException, IOException, TemplateException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        Template t = freemarkerConfig.getTemplate(mail.getTemplate());
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getArgs());

        helper.setTo(mail.getTo());
        helper.setFrom(this.sender, "BeanstalkNotification");
        helper.setSubject(mail.getSubject());
        message.setText(content, "utf-8", "html");

        for (String attachment : mail.getAttachments()) {
            FileSystemResource file = new FileSystemResource(attachment);
            helper.addAttachment(file.getFilename(), file);
        }

        javaMailSender.send(message);
    }

}
