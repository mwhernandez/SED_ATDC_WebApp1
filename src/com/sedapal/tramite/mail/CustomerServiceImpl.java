package com.sedapal.tramite.mail;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class CustomerServiceImpl implements CustomerService {
	
	private MailSender mailSender;
	private SimpleMailMessage templateMessage;
	private static Logger logger = Logger.getLogger("R1");

	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void enviarCorreo(String from, String to[], String subject, String mensaje) throws MailException{
		 // Create a thread safe "copy" of the template message and customize it
		logger.debug("Procesando desde Template Spring....");
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);   
        msg.setSubject(subject);        
        msg.setFrom(from);
        msg.setTo(to);
        msg.setText(mensaje);
        this.mailSender.send(msg);
        logger.debug("Email enviado Ok");
        
        }

}
