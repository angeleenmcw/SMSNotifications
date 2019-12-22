package com.sms.notifications.textmessages.email.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mail {

    private String from;

    private String to;

    private String subject;
    private String content;

    private String template;
    private List<String> attachments;
    private Map<String, Object> args;

    public Mail() {}

    public Mail( String to, String subject, String content, String template, List<String> attachments, Map<String, Object> args) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.template = template;
        this.attachments = attachments;
        this.args = args;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void addAttachment(String attachment) {
        List<String> attachments = this.getAttachments();
        if (attachments.contains(attachment)) return;
        attachments.add(attachment);
        this.setAttachments(attachments);
    }

    public void removeAttachment(String attachment) {
        this.getAttachments().remove(attachment);
    }

    public List<String> getAttachments() {
        return this.attachments != null ? this.attachments : new ArrayList<>();
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
