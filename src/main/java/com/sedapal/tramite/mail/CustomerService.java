package com.sedapal.tramite.mail;

import org.springframework.mail.MailException;

public interface CustomerService {
   public void enviarCorreo(String from, String to[], String subject, String mensaje) throws MailException;
}
