package com.sedapal.tramite.servicios.util;

import com.sedapal.tramite.util.MailConstante;
import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author jhuamanv
 */
@Service
public class MailServiceImpl implements MailService {
    private static final Logger logger = Logger.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    @Autowired
    private MailConstante mailConstante;

    public void enviarMail(String textoMensaje) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        logger.info("Agregando componentes para el simple mail...");

        msg.setText(textoMensaje);
        msg.setTo(mailConstante.aSendTo());
        msg.setCc(mailConstante.aSendCC());        
        msg.setBcc(mailConstante.aSendBCC());

        try{
            this.mailSender.send(msg);
        }catch(MailException ex){
            logger.debug("Error al enviar mail: " + ex.getMessage());
            return;
        }
        	logger.info("Envio de mail exitoso!!");
    }

    public void enviarMail(String textoMensaje, File... attachments) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        logger.info("Agregando componentes para el mime mail...");
        try{
            MimeMessage loMimeMsg = this.mailSender.createMimeMessage();
            MimeMessageHelper loMsgHlpr = new MimeMessageHelper(loMimeMsg,true);
            loMsgHlpr.setFrom(msg.getFrom());
            loMsgHlpr.setSubject(msg.getSubject());
            loMsgHlpr.setTo(mailConstante.aSendTo());
            loMsgHlpr.setBcc(mailConstante.aSendCC());
            loMsgHlpr.setText(textoMensaje);
            /*Eli Descomenta*/
            //loMsgHlpr.setBcc(mailConstante.aSendBCC());
            //loMsgHlpr.setBcc("");
            

            // adjuntando los ficheros
            if (attachments != null) {
                for (int i = 0; i < attachments.length; i++) {
                    if(attachments[i]!=null){
                        FileSystemResource file = new FileSystemResource(attachments[i]);
                        loMsgHlpr.addAttachment(attachments[i].getName(), file);
                        if (logger.isDebugEnabled()) {
                            logger.debug("File '" + file + "' attached.");
                        }
                    }
                }
            }

            this.mailSender.send(loMimeMsg);
        } catch (MessagingException mex) {
            logger.error("Error de MessagingException: " + mex.getMessage());
            return;
        }catch(MailException ex){
            logger.error("Error de MailException: " + ex.getMessage());
            return;
        }
        logger.info("Envio de mail exitoso!!");
    }

    public Boolean enviarMail(String txtMensaje, String asunto, String correoDest1, String correoDest2) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        logger.info("Agregando componentes para el simple mail...");
        Boolean result = false;

        msg.setText(txtMensaje);
        msg.setSubject(asunto);
        msg.setTo(correoDest1);
        msg.setCc(mailConstante.aSendCC());
        msg.setBcc(mailConstante.aSendBCC());
        //msg.setCc(mailConstante.aSendCC());
        /*Eli Descomenta*/
        //msg.setBcc(mailConstante.aSendBCC());
        /*Eli Comenta 11/05/2012*/
        //msg.setBcc("");
        /*Eli Comenta 11/05/2012*/
        
        if(correoDest2!=null){
            msg.setCc(correoDest2);
        }       
        msg.setBcc(mailConstante.aSendBCC());
       
        try{
            this.mailSender.send(msg);
            result = true;
        }catch(MailException ex){
            logger.debug("Error al enviar mail: " + ex.getMessage());
            result = false;
        }
        logger.info("Envio de mail exitoso!!");
        return result;
    }

    public Boolean enviarMail(String txtMensaje, String asunto, String correoDest1, String correoDest2, File... attachments) {
        Boolean result = false;

        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        logger.info("Agregando componentes para el mime mail...");
        try{
            MimeMessage loMimeMsg = this.mailSender.createMimeMessage();
            MimeMessageHelper loMsgHlpr = new MimeMessageHelper(loMimeMsg,true);
            loMsgHlpr.setFrom(msg.getFrom());
            loMsgHlpr.setSubject(asunto);
            if(correoDest2!=null){
                loMsgHlpr.setCc(correoDest2);
            }
           
            loMsgHlpr.setBcc(mailConstante.aSendBCC());
            loMsgHlpr.setText(txtMensaje);

            // adjuntando los ficheros
            if (attachments != null) {
                for (int i = 0; i < attachments.length; i++) {
                    FileSystemResource file = new FileSystemResource(attachments[i]);
                    loMsgHlpr.addAttachment(attachments[i].getName(), file);
                    if (logger.isDebugEnabled()) {
                        logger.debug("File '" + file + "' attached.");
                    }
                }
            }

            this.mailSender.send(loMimeMsg);
            result = true;
        } catch (MessagingException mex) {
            logger.error("Error de MessagingException: " + mex.getMessage());
            result = false;
        }catch(MailException ex){
            logger.error("Error de MailException: " + ex.getMessage());
            result = false;
        }
        logger.info("Envio de mail exitoso!!");
        return result;
    }
}
