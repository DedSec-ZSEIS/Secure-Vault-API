package net.ckmk.api.service;

public interface MailService {

    boolean emailExists(String email);
    void sendMessage(String to, String subject, String text);
}
