package com.sedapal.tramite.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.MarquesitaBean;
import com.sedapal.tramite.beans.UsersBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.IEntranteDAO;
import com.sedapal.tramite.dao.ILogMailDAO;
import com.sedapal.tramite.dao.IUsuariosDAO;
import com.sedapal.tramite.servicios.util.MailService;

public class ServiciosUsuarios implements IServiciosUsuarios {

	
    private IUsuariosDAO usuariosDAO;
    /* Agregado el 16/11/2011 - Alfredo Panitz */
    private ILogMailDAO logMailDAO;
    private IEntranteDAO entranteDAO;
    //Para enviar correos
    @Autowired
    private MailService mailService;

    @Override
    public Usuario autenticar(String login, String clave) {
        Usuario usuario = getUsuariosDAO().autenticar(login, clave);
        return usuario;
    }
    
    
       
   
    
    @Override
	public void nuevoLogIngreso(Usuario usuarioBean) {
		usuariosDAO.nuevoLogingreso(usuarioBean);		
	}
    
    
    

    public void setUsuariosDAO(IUsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
    }
    
    ///Agrega este codigo Eli Diaz Horna - 15/10/2014
        
    @Override
	public String insertarLog(int codsistema, String usuario, Date fecha,String ip, int area, String acceso, String ingreso, int correlativo) {	
    	String log = usuariosDAO.UpdateLogs(codsistema, usuario, fecha, ip, area, acceso, ingreso, correlativo);
		return log;
	}
    
    @Override
	public String nuevologemail(Usuario usuarioBean) {
		
		return logMailDAO.nuevologemail(usuarioBean);
	}

       

    /* Agregado el 16/11/2011 - Alfredo Panitz */
    @Override
    public Boolean enviarEmailByFichaUsu(int codFichaUsu, String mensaje, String asunto) {
        Boolean result = false;
        HttpSession session = null;
        Usuario usuario = null;
        
        session = (HttpSession) FacesContext.getCurrentInstance()
        .getExternalContext().getSession(false);
        
        usuario = (Usuario) session.getAttribute("sUsuario");
        
        
        String emailUsu = getUsuariosDAO().getEmailByFichaUsu(codFichaUsu);
        //String emailJefe = getUsuariosDAO().getEmailJefe(codarea);
        System.out.println("Correo de Secretaria");
        System.out.println(emailUsu);
        //System.out.println("Correo de Jefe de Equipo");
        //System.out.println(emailJefe);

        if (!emailUsu.isEmpty()) {
            mailService.enviarMail(mensaje, asunto, emailUsu, null);
            result = true;
        }
        return result;
    }
    
    @Override
    public Boolean enviarEmail(String correo_electronico, String mensaje, String asunto) {
        Boolean result = false;
        //String emailUsu = getUsuariosDAO().getEmailByFichaUsu(codFichaUsu);

        //if (!emailUsu.isEmpty()) {
            mailService.enviarMail(mensaje, asunto, correo_electronico, null);
            result = true;
        //}
        return result;
    }
    
    @Override
	public String marquisita() {
		String resultStr = getUsuariosDAO().marquesita();
		return resultStr;
	}
    
    

    @Override
    public String getEmailByFichaUsu(int codFichaUsu) {
        String resultStr = getUsuariosDAO().getEmailByFichaUsu(codFichaUsu);
        return resultStr;
    }

    @Override
    public List<Usuario> getSecretariasByCodArea(int codArea) {
        List resultList = new ArrayList();
        resultList = getUsuariosDAO().getSecretariasByCodArea(codArea);
        return resultList;
    }

    @Override
    public Map getUsuarioByCodUsu(String codUsu) {
    	//, int codarea
        Usuario beanUsu = new Usuario();
        String tipoMsgResult, msgResult, nomUsu, passUsu = "";
        Integer fichaUsu = 0;
        Integer areaUsu = 0;
        Map resultMap = new HashMap();

        beanUsu = getUsuariosDAO().getUsuarioByCodUsu(codUsu);

        if (beanUsu == null) {
            tipoMsgResult = "ERR";
            msgResult = "No se pudo enviar su contraseña a su Email";
            nomUsu = "";
            fichaUsu = 0;
            passUsu = "";
            areaUsu = 0;
        } else {
            tipoMsgResult = "OK";
            msgResult = "Su contraseï¿½a ha sido enviada a su Email";
            nomUsu = beanUsu.getNombre();
            fichaUsu = beanUsu.getFicha();
            passUsu = beanUsu.getPassword();
            areaUsu = beanUsu.getCodarea();
        }

        resultMap.put("tipoMsgResult", tipoMsgResult);
        resultMap.put("msgResult", msgResult);
        resultMap.put("descUsu", nomUsu);
        resultMap.put("fichaUsu", fichaUsu);
        resultMap.put("passUsu", passUsu);
        resultMap.put("passUsu", passUsu);
        resultMap.put("areaUsu", areaUsu);

        return resultMap;
    }

    @Override
    public Boolean revisarEnvioMailDiario(int codFicha) {
        String fechaHoy = "to_char(sysdate,'yyyyMMdd')";
        return getLogMailDAO().getLogMailbyFichaAndFecha(codFicha, fechaHoy);
    }

    @Override
    public List<EntranteBean> obtenerDocEntrUrgentesByCodArea(int codArea) {
        List<EntranteBean> resultList = new ArrayList();
        resultList = getEntranteDAO().getListadoDocEntrUrgentes(codArea);
        return resultList;
    }

    public Boolean insertarLogMailDiario(int codFicha, int codResponsable) {
        Boolean result = getLogMailDAO().insertLogMail(codFicha, codResponsable);
        return result;
    }
    
    
    
    

   
    public ILogMailDAO getLogMailDAO() {
        return logMailDAO;
    }

    /**
     * @param logMailDAO the logMailDAO to set
     */
    public void setLogMailDAO(ILogMailDAO logMailDAO) {
        this.logMailDAO = logMailDAO;
    }

    /**
     * @return the entranteDAO
     */
    public IEntranteDAO getEntranteDAO() {
        return entranteDAO;
    }

    /**
     * @param entranteDAO the entranteDAO to set
     */
    public void setEntranteDAO(IEntranteDAO entranteDAO) {
        this.entranteDAO = entranteDAO;
    }

    public IUsuariosDAO getUsuariosDAO() {
        return usuariosDAO;
    }






	
	

	

	

	

	

	

	
}
