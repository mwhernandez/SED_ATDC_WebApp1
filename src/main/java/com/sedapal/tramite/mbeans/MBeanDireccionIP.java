package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.AccionDAO;
import com.sedapal.tramite.dao.UsuarioDAO;
import com.sedapal.tramite.servicios.IServiciosAccion;
import com.sedapal.tramite.servicios.IServiciosUsuarios;
import com.sedapal.tramite.servlets.ServletObtenerIP;

public class MBeanDireccionIP implements IMBeanAccion, Serializable {

    private List<AccionBean> accion;
    public IServiciosAccion serviciosAccion;    
    private boolean ver = false;
    private String error = "Se Grabó Satisfactoriamente";
    private String mensaje = "Se Grabó Satisfactoriamente";
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean verDetalles = false;    
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    private AccionDAO accionDAO;
    private IServiciosUsuarios serviciosUsuarios;
    private String ip_desktop;    
    private UsuarioDAO usuariosDAO;
    
  
    
    

    
    public void eventImpresion(ActionEvent event) {
    }

     

    public void eventNuevo(ActionEvent event) {
        this.limpiar();
        //combo.setLabel("ITEMS");
        this.verNuevo = true;
        this.verCatalogo = false;

    }
    
    public void Direccionip(ActionEvent event) throws UnknownHostException  {
    	
    	 HttpSession session = null;
    	 Usuario usuario = null;
    	 session = (HttpSession) FacesContext.getCurrentInstance()
         .getExternalContext().getSession(false);    	
    	usuario = (Usuario) session.getAttribute("sUsuario");
        
        String usuariologeo = usuario.getLogin();
    	int area = usuario.getCodarea();                 
    	int codsistema = 30;    	
    	String acceso = "A";
    	String ingreso = "S";
    	int correlativo = 1;
    	Date fecha = new Date();
    	ServletObtenerIP obtenerIP = new  ServletObtenerIP();
		ip_desktop = obtenerIP.direccionip;
		System.out.println("Pinto IP Terminal");
		System.out.println(ip_desktop);
        String dato = usuariosDAO.UpdateLogs(codsistema, usuariologeo, fecha, ip_desktop, area, acceso, ingreso, correlativo);
        //System.out.println("Pinto Datoossssssss");
        //System.out.println(dato);
        session.invalidate();
    	

    }

    
    public void cerrarDetalles(ActionEvent event) {
        this.verDetalles = false;

    }

    public List<AccionBean> getAccion() {
        return this.accion;
    }

    public void setAccion(List<AccionBean> accion) {
        this.accion = accion;
    }

    public void setServiciosAccion(IServiciosAccion serviciosAccion) {
        this.serviciosAccion = serviciosAccion;

    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }
    /**
     * ***********************************************
     */
    // list of selected employees
    protected ArrayList<AccionBean> selectedAccionees;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = true;
    protected boolean enhancedMultiple;

    public MBeanDireccionIP() {
        selectedAccionees = new ArrayList<AccionBean>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (AccionBean t : selectedAccionees) {
            System.out.println(t.getCodigo() + "   " + t.getDescripcion());
        }
    }

    @PostConstruct
    public void posterior() throws UnknownHostException {
        //this.accion = serviciosAccion.catalogo();
    	/*
    	HttpSession session = null;
   	 Usuario usuario = null;
   	 session = (HttpSession) FacesContext.getCurrentInstance()
        .getExternalContext().getSession(false);    	
   	usuario = (Usuario) session.getAttribute("sUsuario");
       
       String usuariologeo = usuario.getLogin();
   	int area = usuario.getCodarea();                 
   	int codsistema = 30;    	
   	String acceso = "S";
   	String ingreso = "I";
   	int correlativo = 1;
   	//codigo para obtener una direccion ip de la pc            	
   	InetAddress direccion = InetAddress.getLocalHost();
       String nombreDelHost = direccion.getHostName();//nombre host
       String IP_local = direccion.getHostAddress();//ip como String  
   	serviciosUsuarios.insertarLog(codsistema, usuariologeo, fecha, IP_local, area, acceso, ingreso, correlativo);
      // usuariosDAO.UpdateLogs(codsistema, usuariologeo, fecha, IP_local, area, acceso, ingreso, correlativo);
       * 
       */
       
    }

    /**
     * SelectionListener bound to the ice:rowSelector component. Called when a
     * row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedAccionees.clear();

        /*
         * If application developers rely on validation to control submission of
         * the form or use the result of the selection in cascading control set
         * up the may want to defer procession of the event to
         * INVOKE_APPLICATION stage by using this code fragment if
         * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
         * return; }
         */

        System.out.println("Tama:" + accion.size());
        // build the new selected list
        AccionBean acciones;
        for (int i = 0, max = accion.size(); i < max; i++) {
            acciones = (AccionBean) accion.get(i);
            if (acciones.isSelected()) {
                selectedAccionees.add(acciones);
            }
        }
        for (AccionBean t : selectedAccionees) {
            System.out.println(t.getCodigo() + "  " + t.getDescripcion());
        }
        /*
         * If application developers do not rely on validation and want to
         * bypass UPDATE_MODEL and INVOKE_APPLICATION stages, they may be able
         * to use the following statement:
         * FacesContext.getCurrentInstance().renderResponse(); to send
         * application to RENDER_RESPONSE phase shortening the app. cycle
         */
    }

    /**
     * Clear the selection list if going from multi select to single select.
     *
     * @param event jsf action event.
     */
    public void changeSelectionMode(ValueChangeEvent event) {

        String newValue = event.getNewValue() != null ? event.getNewValue()
                .toString() : null;
        multiple = false;
        enhancedMultiple = false;
        if ("Single".equals(newValue)) {
            selectedAccionees.clear();

            // build the new selected list
            AccionBean employee;
            for (int i = 0, max = accion.size(); i < max; i++) {
                employee = (AccionBean) accion.get(i);
                employee.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = true;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = true;
        }
    }

    public void eventCancelar(ActionEvent event) {
        this.verCatalogo = true;
        this.verNuevo = false;
        this.verActualizar = false;
    }

    public void limpiar() {
        
    }

    public void limpiarActualizar() {
       
    }

    public ArrayList getSelectedAccionees() {
        return selectedAccionees;
    }

    public void setSelectedAccionees(ArrayList selectedAccionees) {
        this.selectedAccionees = selectedAccionees;
    }

    public String getMultiRowSelect() {
        return multiRowSelect;
    }

    /**
     * Sets the selection more of the rowSelector.
     *
     * @param multiRowSelect true indicates multi-row select and false indicates
     * single row selection mode.
     */
    public void setMultiRowSelect(String multiRowSelect) {
        this.multiRowSelect = multiRowSelect;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isEnhancedMultiple() {
        return enhancedMultiple;
    }

    public void setEnhancedMultiple(boolean enhancedMultiple) {
        this.enhancedMultiple = enhancedMultiple;
    }

    

    public void setAccionDAO(AccionDAO accionDAO) {
        this.accionDAO = accionDAO;
    }

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isVerNuevo() {
        return verNuevo;
    }

    public void setVerNuevo(boolean verNuevo) {
        this.verNuevo = verNuevo;
    }

    public boolean isVerActualizar() {
        return verActualizar;
    }

    public void setVerActualizar(boolean verActualizar) {
        this.verActualizar = verActualizar;
    }

    public boolean isVerCatalogo() {
        return verCatalogo;
    }

    public void setVerCatalogo(boolean verCatalogo) {
        this.verCatalogo = verCatalogo;
    }



	public void setUsuariosDAO(UsuarioDAO usuariosDAO) {
		this.usuariosDAO = usuariosDAO;
	}



	public void setServiciosUsuarios(IServiciosUsuarios serviciosUsuarios) {
		this.serviciosUsuarios = serviciosUsuarios;
	}



	public String getIp_desktop() {
		return ip_desktop;
	}



	public void setIp_desktop(String ip_desktop) {
		this.ip_desktop = ip_desktop;
	}
    
    

    
}
