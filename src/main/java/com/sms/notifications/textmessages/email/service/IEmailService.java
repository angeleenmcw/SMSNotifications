package com.sms.notifications.textmessages.email.service;

import com.sms.notifications.textmessages.email.model.Mail;
import freemarker.template.TemplateException;
import org.springframework.mail.MailException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IEmailService {

    void sendSimpleEmail(Mail mail) throws MailException;

    void sendEmail(Mail mail) throws MailException, MessagingException, IOException, TemplateException;

}
