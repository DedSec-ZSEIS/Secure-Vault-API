package net.ckmk.api.service;

public interface MailService {

    void sendMessage(String to, String subject, String text);
}
