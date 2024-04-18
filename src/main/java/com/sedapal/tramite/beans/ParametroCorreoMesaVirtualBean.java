package com.sedapal.tramite.beans;

import java.io.Serializable;

public class ParametroCorreoMesaVirtualBean implements Serializable{
	private String vhost;
	private String vcorreo;
	private String vpassword;
	
	public String getVhost() {
		return vhost;
	}
	public void setVhost(String vhost) {
		this.vhost = vhost;
	}
	public String getVcorreo() {
		return vcorreo;
	}
	public void setVcorreo(String vcorreo) {
		this.vcorreo = vcorreo;
	}
	public String getVpassword() {
		return vpassword;
	}
	public void setVpassword(String vpassword) {
		this.vpassword = vpassword;
	}

}
