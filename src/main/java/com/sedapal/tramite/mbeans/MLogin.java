package com.sedapal.tramite.mbeans;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.icesoft.icefaces.tutorial.facelets.NavigationBean;
import com.icesoft.icefaces.tutorial.facelets.PageContentBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.MarquesitaBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.LogMailDAO;
import com.sedapal.tramite.dao.UsuarioDAO;
import com.sedapal.tramite.servicios.IServiciosUsuarios;
import com.sedapal.tramite.servicios.ServiciosUsuarios;
import com.sedapal.tramite.servicios.util.MailService;
import com.sedapal.tramite.servlets.ServletObtenerIP;


public class MLogin {

    private String usuario;
    private String clave;
    private String nomequipo;
    private String perfil;
    private String error;
    private String nombre;
    private String mensajeuno;
    private String mensajedos;
    private String mensajetres;
    private boolean ver = false;
    private boolean entrada = true;
    private boolean salida = true;
    private Logger logger = Logger.getLogger("R1");
    // inyectando servicios
    private IServiciosUsuarios serviciosUsuarios;
    /* Agregado el 16/11/2011 - Alfredo Panitz */
    //Para enviar correos
    @Autowired
    private MailService mailService;
    //Atributos para envio de correo de recuperar password
    private Boolean verLoginInicio = true;
    private Boolean verLoginRecuperarPass = false;
    private String codigoUsuario;
    private String msgResultado;
    private String tipMsgResultado;
    private Boolean verIniRecuperarPass;
    private Boolean verMsgResultadoOk;
    private Boolean verMsgResultadoErr;
    private int codigoarea;
    private int correlativo;
    private int ficha;
    private String ip_desktop;
    
    private UsuarioDAO usuarioDAO;
    private LogMailDAO logmailDAO;
    private Usuario usuarioBean;
    Calendar c = Calendar.getInstance();
    private String grupo;
    public MBeanEntrantes mBeanEntrantes;
    private NavigationBean navigation;
    
       

    public void showRecuperarPass() {
        this.setVerLoginInicio((Boolean) false);
        this.setVerLoginRecuperarPass((Boolean) true);
        this.setVerIniRecuperarPass((Boolean) true);
        this.setCodigoUsuario("");
    }
    
    
    
    
    
    

    public void enviarCorreoRecPass(ActionEvent event) {
        Map usuarioMap = new HashMap();
       
        usuarioMap = serviciosUsuarios.getUsuarioByCodUsu(this.getCodigoUsuario());

        this.setTipMsgResultado((String) usuarioMap.get("tipoMsgResult"));
        this.setMsgResultado((String) usuarioMap.get("msgResult"));

        if (this.getTipMsgResultado().equals("ERR")) {
            this.setVerIniRecuperarPass((Boolean) false);
            this.setVerMsgResultadoOk((Boolean) false);
            this.setVerMsgResultadoErr((Boolean) true);
        } else {
            this.setVerIniRecuperarPass((Boolean) false);
            this.setVerMsgResultadoOk((Boolean) true);
            this.setVerMsgResultadoErr((Boolean) false);

            Integer fichaUsu = (Integer) usuarioMap.get("fichaUsu");
            String nomUsu = (String) usuarioMap.get("descUsu");
            String passUsu = (String) usuarioMap.get("passUsu");
            Integer areaUsu = (Integer) usuarioMap.get("area");
            
            System.out.println("VER DATOS");
            System.out.println("Ficha " + fichaUsu);
            System.out.println("Nombre" + nomUsu);
            System.out.println("Password" + passUsu);
            
            String txtMensaje = "";
            String txtAsunto = "Recordatorio Usuario y Password - Tramite Documentario Corporativo";
            txtMensaje = txtMensaje + txtAsunto + "\n\n";
            txtMensaje = txtMensaje + "Estimada(o) " + nomUsu + ", le recordamos que sus datos de ingreso al aplicativo son los siguientes :\n";
            txtMensaje = txtMensaje + "Usuario  : " + this.getCodigoUsuario().toUpperCase() + "\n";
            txtMensaje = txtMensaje + "Password : " + passUsu + "\n\n";
            txtMensaje = txtMensaje + "Atentamente,\n\n";
            txtMensaje = txtMensaje + "Tramite Documentario Corporativo \n";
            txtMensaje = txtMensaje + "Por favor no responder este Email";
            
            

            Boolean envioCorreo = serviciosUsuarios.enviarEmailByFichaUsu(fichaUsu, txtMensaje, txtAsunto);
            
            
            if (envioCorreo) {
                mailService.enviarMail("El usuario " + nomUsu + " solicito recuperar su contraseña y se le envio el\n"
                        + "correo de forma satisfactoria");
            } else {
                mailService.enviarMail("El usuario " + nomUsu + " solicito recuperar su contraseña pero no cuenta\n"
                        + "con un correo registrado en la tabla de Trabajadores, por lo que no se le envio ningun correo.");
            }
            
            
        }
    }

    public void showVolverInicioRecuperarPass(ActionEvent event) {
        this.setVerIniRecuperarPass((Boolean) true);
        this.setVerMsgResultadoOk((Boolean) false);
        this.setVerMsgResultadoErr((Boolean) false);

        this.setMsgResultado("");
        this.setTipMsgResultado("");
    }

    public void showVolverInicioLogin(ActionEvent event) {
        showVolverInicioRecuperarPass(event);

        this.setVerLoginInicio((Boolean) true);
        this.setVerLoginRecuperarPass((Boolean) false);
    }
    //Metodo modificado para el envio de correos al entrar al sistema

    public String autenticar() throws UnknownHostException {
        String msgExterno, msgInterno = "";
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        InetAddress direccion = InetAddress.getLocalHost();
     
    	
        try {
        	//
        	this.mensajeuno = serviciosUsuarios.marquisita();        	
            // llamar a la capa de servicios para la autenticacion
            Usuario usu = serviciosUsuarios.autenticar(usuario.toUpperCase(), clave.toUpperCase());
            session.setAttribute("sUsuario", usu);
            nomequipo = usu.getNomequipo().toUpperCase();
            perfil = usu.getPerfil().toUpperCase();
            usuario = usu.getLogin().toUpperCase();
            nombre = usu.getNombre().toUpperCase();            
            ficha = usu.getFicha();
            if (usu != null) {
                int perfilUsu = usu.getNcodperfil();
                int conexion = usu.getConexion();
                String correo = usu.getCorreo();
                if (conexion == 0) {
                	
                	
                	                	
                    if (perfilUsu == 2 || perfilUsu == 4) { //Perfiles de Secretarias
                        //retorna TRUE si ya se encuentra un registros para esa ficha en el dia
                        //        FALSE en caso contrario
                        Boolean condMailDiario = serviciosUsuarios.revisarEnvioMailDiario(usu.getFicha());
                        
                        if (!condMailDiario) {
                            List<EntranteBean> listadoDocsPendientes = serviciosUsuarios.obtenerDocEntrUrgentesByCodArea(usu.getNcodarea());
                            int valor = listadoDocsPendientes.size();
                            System.out.println("Viendo los documentos");
                            System.out.println(valor);

                            String txtListado = "";
                            String txtMensaje = "";
                            String txtAsunto = "Documentos Entrantes Pendientes Urgentes proximos a la fecha de Plazo  - ATDC";
                            txtMensaje = txtMensaje + txtAsunto + "\n\n";
                            txtMensaje = txtMensaje + "Estimada(o) : " + usu.getNombre().toUpperCase() + "\n";
                            
                            if (valor > 0){
                            	
                            
	                            if (!listadoDocsPendientes.isEmpty()) {
	                                txtMensaje = txtMensaje + "Le recordamos que los documentos que seran listados a continuacion llegaran a su\n";
	                                txtMensaje = txtMensaje + "fecha de Plazo limite en las proximas 48 horas.\n\n";
	                                txtMensaje = txtMensaje + "Nro.Reg - Nro.Documento - Fecha Plazo - Remite - Asunto \n\n";
	                                for (int i = 0; i < listadoDocsPendientes.size(); i++) {
	                                    EntranteBean beanEntrante = (EntranteBean) listadoDocsPendientes.get(i);
	                                    txtListado = txtListado + beanEntrante.getNcorrelativo() + " - " + beanEntrante.getVnumdoc() + " - " + beanEntrante.getFechaplazo() +  " - " + beanEntrante.getVremitente() +  " - " + beanEntrante.getVasunto() +"\n";
	                                }
	                                txtMensaje = txtMensaje + txtListado + "\n";
	                                txtMensaje = txtMensaje + "Por favor, sirvase atenderlos segun vea conveniente.\n\n";
	                            }
	
	                            txtMensaje = txtMensaje + "Atentamente,\n\n";
	                            txtMensaje = txtMensaje + "Tramite Documentario Corporativo \n";
	                            txtMensaje = txtMensaje + "Por favor no responder este Email";
	
	                            //Boolean envioCorreo = serviciosUsuarios.enviarEmailByFichaUsu(usu.getFicha(), txtMensaje, txtAsunto);
	                            Boolean envioCorreo = serviciosUsuarios.enviarEmail(correo, txtMensaje, txtAsunto);
	                            /*
	                            if (envioCorreo) {
	                                mailService.enviarMail("El usuario " + usu.getNombre().toUpperCase() + " ingreso al aplicativo y se le envio el\n"
	                                        + "correo de forma satisfactoria");
	                            } else {
	                                mailService.enviarMail("El usuario " + usu.getNombre().toUpperCase() + " ingreso al aplicativo pero no cuenta\n"
	                                        + "con un correo registrado en la tabla de Trabajadores, por lo que no se le envio ningun correo.");
	                            }
	                            */
	                            System.out.println("Insertando el log de email");
	                            //serviciosUsuarios.insertarLogMailDiario(usu.getFicha(), usu.getFicha());
	                            Date date = new Date();
	                        	String annio = Integer.toString(c.get(Calendar.DAY_OF_YEAR));
	                        		                            
	                            Usuario usuarioBean = new Usuario();
	                            usuarioBean.setFicha(ficha);
	                            usuarioBean.setAnnio(annio);	                           
	                            usuarioBean.setLogin(usuario);
	                            //serviciosUsuarios.nuevologemail(usuarioBean);
	                            //
	                            
	                            logmailDAO.nuevologemail(usuarioBean);
	                            
                            }                           
                            
                        }
                        
                    }
                }
                               
                  
                
                ////////////- OBTENER DIRECCION IP -/////////////////
                
		        ServletObtenerIP obtenerIP = new  ServletObtenerIP();
			    ip_desktop = obtenerIP.direccionip;
				System.out.println("Pinto IP Terminal");
				System.out.println(ip_desktop);
				//////////////////////////////////////            	
            	            	
	            String nombreDelHost = direccion.getHostName();//nombre host
	            String IP_local = direccion.getHostAddress();//ip como String	          
	            ///serviciosUsuarios.insertarLog(codsistema, usuario, fecha, IP_local, area, acceso, ingreso, correlativo);
	            Usuario usuarioBean = new Usuario();
	            usuarioBean.setLogin(usuario);
	            usuarioBean.setDireccionip(IP_local);
	            usuarioBean.setCodarea(usu.getCodarea()); 
	            usuarioBean.setIp_desktop(ip_desktop);
	            
	            //fichainspeccionBean.setIp_desktop(this.ip_desktop);
	            
            	// CAMBIADO POR ELI DIAZ -- INSERTO UN LOG AHORA CON PROCEDIMIENTO ALMACENADO -- 07/11/2016
	            serviciosUsuarios.nuevoLogIngreso(usuarioBean);
	            System.out.println("Inserto un nuevo logs");
	            System.out.println(correlativo);
	            
	            
	            
                return "toMain";
            } else {            
                msgInterno = "Usuario y/o Password Incorrectos - ATDC";
                msgExterno = "Usuario y/o Password Incorrectos 1";
                msg.setDetail(msgExterno);
                fctx.addMessage(null, msg);                
                return null;
            }
        } catch (EmptyResultDataAccessException e) {       	 
        	        	
            msgInterno = "No hay Datos - ATDC" + "\n\n" + e;
            msgExterno = "Los Usuario y/o Password Incorrectos 2";
            msg.setDetail(msgExterno);
            fctx.addMessage(null, msg);
            return null;
        } catch (Exception e) {       
            msgInterno = "Problemas con Servicios - ATDC" + "\n\n" + e;
            msgExterno = "El Usuario y/o Password Incorrectos, de baja o trabajador de baja";
            //mailService.enviarMail(msgInterno);
            msg.setDetail(msgExterno);
            System.out.println(msgInterno);
            fctx.addMessage(null, msg);   
            return null;
        }
    }
    
   
     
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setServiciosUsuarios(IServiciosUsuarios serviciosUsuarios) {
        this.serviciosUsuarios = serviciosUsuarios;
    }

    public String getNomequipo() {
        return nomequipo;
    }

    public void setNomequipo(String nomequipo) {
        this.nomequipo = nomequipo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the verLoginInicio
     */
    public Boolean getVerLoginInicio() {
        return verLoginInicio;
    }

    /**
     * @param verLoginInicio the verLoginInicio to set
     */
    public void setVerLoginInicio(Boolean verLoginInicio) {
        this.verLoginInicio = verLoginInicio;
    }

    /**
     * @return the verLoginRecuperarPass
     */
    public Boolean getVerLoginRecuperarPass() {
        return verLoginRecuperarPass;
    }

    /**
     * @param verLoginRecuperarPass the verLoginRecuperarPass to set
     */
    public void setVerLoginRecuperarPass(Boolean verLoginRecuperarPass) {
        this.verLoginRecuperarPass = verLoginRecuperarPass;
    }

    /**
     * @return the msgResultado
     */
    public String getMsgResultado() {
        return msgResultado;
    }

    /**
     * @param msgResultado the msgResultado to set
     */
    public void setMsgResultado(String msgResultado) {
        this.msgResultado = msgResultado;
    }

    /**
     * @return the tipMsgResultado
     */
    public String getTipMsgResultado() {
        return tipMsgResultado;
    }

    /**
     * @param tipMsgResultado the tipMsgResultado to set
     */
    public void setTipMsgResultado(String tipMsgResultado) {
        this.tipMsgResultado = tipMsgResultado;
    }

    /**
     * @return the verMsgResultadoOk
     */
    public Boolean getVerMsgResultadoOk() {
        return verMsgResultadoOk;
    }

    /**
     * @param verMsgResultadoOk the verMsgResultadoOk to set
     */
    public void setVerMsgResultadoOk(Boolean verMsgResultadoOk) {
        this.verMsgResultadoOk = verMsgResultadoOk;
    }

    /**
     * @return the verMsgResultadoErr
     */
    public Boolean getVerMsgResultadoErr() {
        return verMsgResultadoErr;
    }

    /**
     * @param verMsgResultadoErr the verMsgResultadoErr to set
     */
    public void setVerMsgResultadoErr(Boolean verMsgResultadoErr) {
        this.verMsgResultadoErr = verMsgResultadoErr;
    }

    /**
     * @return the codigoUsuario
     */
    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     * @param codigoUsuario the codigoUsuario to set
     */
    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    /**
     * @return the verIniRecuperarPass
     */
    public Boolean getVerIniRecuperarPass() {
        return verIniRecuperarPass;
    }

    /**
     * @param verIniRecuperarPass the verIniRecuperarPass to set
     */
    public void setVerIniRecuperarPass(Boolean verIniRecuperarPass) {
        this.verIniRecuperarPass = verIniRecuperarPass;
    }

    public int getCodigoarea() {
        return codigoarea;
    }

    public void setCodigoarea(int codigoarea) {
        this.codigoarea = codigoarea;
    }

	public String getMensajeuno() {
		return mensajeuno;
	}

	public void setMensajeuno(String mensajeuno) {
		this.mensajeuno = mensajeuno;
	}

	public String getMensajedos() {
		return mensajedos;
	}

	public void setMensajedos(String mensajedos) {
		this.mensajedos = mensajedos;
	}

	public String getMensajetres() {
		return mensajetres;
	}

	public void setMensajetres(String mensajetres) {
		this.mensajetres = mensajetres;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void setLogmailDAO(LogMailDAO logmailDAO) {
		this.logmailDAO = logmailDAO;
	}

	public String getIp_desktop() {
		return ip_desktop;
	}

	public void setIp_desktop(String ip_desktop) {
		this.ip_desktop = ip_desktop;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public void setmBeanEntrantes(MBeanEntrantes mBeanEntrantes) {
		this.mBeanEntrantes = mBeanEntrantes;
	}

	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}

	public boolean isEntrada() {
		return entrada;
	}

	public void setEntrada(boolean entrada) {
		this.entrada = entrada;
	}

	public boolean isSalida() {
		return salida;
	}

	public void setSalida(boolean salida) {
		this.salida = salida;
	}

	
    
    
    
}
