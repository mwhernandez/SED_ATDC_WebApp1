package com.sedapal.tramite.util;

import java.util.StringTokenizer;

/**
 *
 * @author Jose Luis Huaman Villar
 */

public class MailConstante {
    private String sendTo;
    private String sendCC;
    private String sendBCC;
    private String token;

    public MailConstante() {
    }

    public MailConstante(String sendTo, String sendCC, String sendBCC, String token) {
        this.sendTo = sendTo;
        this.sendCC = sendCC;
        this.sendBCC = sendBCC;
        this.token = token;
    }

    public String getSendBCC() {
        return sendBCC;
    }

    public void setSendBCC(String sendBCC) {
        this.sendBCC = sendBCC;
    }

    public String getSendCC() {
        return sendCC;
    }

    public void setSendCC(String sendCC) {
        this.sendCC = sendCC;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String[] aSendTo(){
        return getMails(sendTo,token);
    }

    public String[] aSendCC(){
        return getMails(sendCC,token);
    }

    public String[] aSendBCC(){
        return getMails(sendBCC,token);
    }

    private static String[] getMails(String cadena, String token) {
        StringTokenizer tokenizer = new StringTokenizer(cadena, token);
        String[] mails = new String[tokenizer.countTokens()];
        for (int i = 0; tokenizer.hasMoreTokens(); i++) mails[i] = tokenizer.nextToken();
        return mails;
    }
}
