package com.sedapal.tramite.servicios.util;

import java.io.File;

/**
 *
 * @author Jose Luis Huaman Villar
 */
public interface MailService {
    public void enviarMail(String textoMensaje);
    public void enviarMail(String textoMensaje, File... attachments);
    public Boolean enviarMail(String txtMensaje, String asunto, String correoDest1, String correoDest2);
    public Boolean enviarMail(String txtMensaje, String asunto, String correoDest1, String correoDest2, File... attachments);
}
