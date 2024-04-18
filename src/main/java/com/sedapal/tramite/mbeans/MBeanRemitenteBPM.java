package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.ext.RowSelectorEvent;




import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.RemitenteBPMDAO;
import com.sedapal.tramite.servicios.IServiciosRemitenteBPM;

public class MBeanRemitenteBPM implements IMBeanRemitenteBPM, Serializable {

    private List<RemitenteBPM> remitentebpm;
    public IServiciosRemitenteBPM serviciosremitentebpm;
    private List items;
    private String itemSeleccionado;
    private boolean ver = false;
    private String Mensaje = "";
    private String error = "Se Grabó Satisfactoriamente";
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean verDetalles = false;
    // para el formulario nuevo
    private String tipoDocumentoN;
	private String nroDocumentoN;
	private String nombreN;
	private String userCreaN;
	private String userActN;
	private String correoN;
	private String telefonoN;
	private String direccionN;
	private int indCasillaN;
    // para el formulario Actualizar
	private long correlativoA;
	private String tipoDocumentoA;
	private String nroDocumentoA;
	private String nombreA;
	private String userCreaA;
	private String userActA;
	private String correoA;
	private String telefonoA;
	private String direccionA;
	private int indCasillaA;
	private String responsableA;
	private String descripcion_tipoA;
    //binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    //variables para el filtro
    private String opcion;
    private String detalle;
    private RemitenteBPMDAO remitentebpmDAO;

    

    public void eventActualizar (ActionEvent evt) {
        
        if (this.selectedEmployees.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;
            HttpSession session = null;
            // /acediendo a sesion http
            session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            // ///guardando en sesion un objeto
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            this.responsableA = usuario.getLogin();

            for (RemitenteBPM t : selectedEmployees) {
            	this.correlativoA = t.getNcorrelativo();               
                this.tipoDocumentoA = t.getTipoDocumento();
                this.nroDocumentoA = t.getNroDocumento();
                this.nombreA = t.getNombre();
                this.userCreaA = t.getUserCrea();
                this.userActA = t.getUserAct();
                this.correoA = t.getCorreo();
                this.telefonoA = t.getTelefono();
                this.direccionA = t.getDireccion();
                this.indCasillaA = t.getIndCasilla();
                this.descripcion_tipoA = t.getDescripciontipo();
            }
            selectedEmployees.clear();
            this.remitentebpm = serviciosremitentebpm.catalogo();// actualiza el reporte
            this.Mensaje = "Se Tipo Actualizó Correctamente";
        } else {
            this.error = "Debe seleccionar solo un registro";
            this.ver = true;

        }
    }
    
    
    /*
    public void eventActualizarremitentebpm(ActionEvent event) {
   	 try {
	        // llama DAO/Stored Para actualizar producto
	        this.verActualizar = false;
	        RemitenteBPM remitentebpmBean = new RemitenteBPM();
	        Usuario usuarioBean = new Usuario();
	        // le paso como parametro al stored
	        remitentebpmBean.setNroDocumento(this.nroDocumentoA);
	        remitentebpmBean.setDireccion(this.direccionA);       
	        remitentebpmBean.setNombre(this.nombreA);
	        remitentebpmBean.setNroDocumento(this.nroDocumentoA);
	        remitentebpmBean.setTelefono(this.telefonoA);
	        remitentebpmBean.setCorreo(this.correoA);
	        
		    //serviciosremitentebpm.actualizarTipos(remitenteBPMBean);
		       	        
		       
		    this.remitentebpm = serviciosremitentebpm.catalogo();
		    this.Mensaje = "Se Realizaron los Cambios Correctamente";
		    this.verCatalogo = true;
		    this.verActualizar = false;
		    this.ver = true;
		        
	        
   	 	} catch (Exception e) {
	            logger.error("Fallo el registro", e);
	            this.Mensaje = "Error,Comunicarse con el ETIC";
	            this.ver = true;
	            this.verCatalogo = true;
	        }
       
       
   }
   */

    public void remitentebpm(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
        this.verActualizar = false;
        RemitenteBPM remitentebpmBean = new RemitenteBPM();
        Usuario usuarioBean = new Usuario();
        // le paso como parametro al stored
        remitentebpmBean.setNcorrelativo(this.correlativoA);
        remitentebpmBean.setNroDocumento(this.nroDocumentoA);
        remitentebpmBean.setDireccion(this.direccionA);       
        remitentebpmBean.setNombre(this.nombreA);
        remitentebpmBean.setNroDocumento(this.nroDocumentoA);
        remitentebpmBean.setTelefono(this.telefonoA);
        remitentebpmBean.setCorreo(this.correoA);
        remitentebpmBean.setDescripciontipo(this.descripcion_tipoA);
        //serviciosremitentebpm.actualizarremitentebpm(remitentebpmBean);
        this.remitentebpm = serviciosremitentebpm.catalogo();// actualiza el
        // reporte
        this.Mensaje = "Se Realizaron los Cambios Correctamente";
        this.verCatalogo = true;
        this.verActualizar = false;
    }

    

    public void eventRefrescar(ActionEvent event) {
        this.verCatalogo = true;
        this.remitentebpm = serviciosremitentebpm.catalogo();
        this.detalle = "";

    }

    

    public void cerrarDetalles(ActionEvent event) {
        this.verDetalles = false;

    }

   

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }
    /**
     * ***********************************************
     */
    // list of selected employees
    protected ArrayList<RemitenteBPM> selectedEmployees;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanRemitenteBPM() {
        selectedEmployees = new ArrayList<RemitenteBPM>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (RemitenteBPM t : selectedEmployees) {
            System.out.println(t.getNroDocumento() + "   " + t.getNombre());
        }
    }

    @PostConstruct
    public void posterior() {
        this.remitentebpm = serviciosremitentebpm.catalogo();
        System.out.println("Post Construct, cargo catalogo...size=" + this.remitentebpm.size());
        System.out.println("Contructor MBeanremitentebpm....");
        //List<remitentebpm> remitentebpm = productoDAO.remitentebpm();
        //List itemsremitentebpm = Utils.getremitentebpm(remitentebpm);
        //this.items = itemsremitentebpm;
        //System.out.println("Se cargo los combos....");
        //for (remitentebpm p : remitentebpm)
        //	System.out.println(p.getTipo() + "   " + p.getDescripcion());
    }

    /**
     * SelectionListener bound to the ice:rowSelector component. Called when a
     * row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedEmployees.clear();

        /*
         * If application developers rely on validation to control submission of
         * the form or use the result of the selection in cascading control set
         * up the may want to defer procession of the event to
         * INVOKE_APPLICATION stage by using this code fragment if
         * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
         * return; }
         */

        System.out.println("Tama:" + remitentebpm.size());
        // build the new selected list
        RemitenteBPM employee;
        for (int i = 0, max = remitentebpm.size(); i < max; i++) {
            employee = (RemitenteBPM) remitentebpm.get(i);
            if (employee.isSelected()) {
                selectedEmployees.add(employee);
            }
        }
        for (RemitenteBPM t : selectedEmployees) {
            System.out.println(t.getNroDocumento() + "  " + t.getNombre());
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
            selectedEmployees.clear();

            // build the new selected list
            RemitenteBPM employee;
            for (int i = 0, max = remitentebpm.size(); i < max; i++) {
                employee = (RemitenteBPM) remitentebpm.get(i);
                employee.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = true;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = true;
        }
    }

    public void eventFiltros(ActionEvent event) {
        //this.ver = true;	
        System.out.println("Filtrando..");
        this.remitentebpm = remitentebpmDAO.filtrosSP(opcion, detalle);

        System.out.println(opcion + "  " + detalle);
    }

    public void eventCancelar(ActionEvent event) {
        this.verCatalogo = true;
        this.verNuevo = false;
        this.verActualizar = false;
    }

    
    

    public ArrayList getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(ArrayList selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
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

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public String getItemSeleccionado() {
        return itemSeleccionado;
    }

    public void setItemSeleccionado(String itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
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

    

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

	public List<RemitenteBPM> getRemitentebpm() {
		return remitentebpm;
	}

	public void setRemitentebpm(List<RemitenteBPM> remitentebpm) {
		this.remitentebpm = remitentebpm;
	}

	public boolean isVerDetalles() {
		return verDetalles;
	}

	public void setVerDetalles(boolean verDetalles) {
		this.verDetalles = verDetalles;
	}

	public String getTipoDocumentoN() {
		return tipoDocumentoN;
	}

	public void setTipoDocumentoN(String tipoDocumentoN) {
		this.tipoDocumentoN = tipoDocumentoN;
	}

	public String getNroDocumentoN() {
		return nroDocumentoN;
	}

	public void setNroDocumentoN(String nroDocumentoN) {
		this.nroDocumentoN = nroDocumentoN;
	}

	public String getNombreN() {
		return nombreN;
	}

	public void setNombreN(String nombreN) {
		this.nombreN = nombreN;
	}

	public String getUserCreaN() {
		return userCreaN;
	}

	public void setUserCreaN(String userCreaN) {
		this.userCreaN = userCreaN;
	}

	public String getUserActN() {
		return userActN;
	}

	public void setUserActN(String userActN) {
		this.userActN = userActN;
	}

	public String getCorreoN() {
		return correoN;
	}

	public void setCorreoN(String correoN) {
		this.correoN = correoN;
	}

	public String getTelefonoN() {
		return telefonoN;
	}

	public void setTelefonoN(String telefonoN) {
		this.telefonoN = telefonoN;
	}

	public String getDireccionN() {
		return direccionN;
	}

	public void setDireccionN(String direccionN) {
		this.direccionN = direccionN;
	}

	public int getIndCasillaN() {
		return indCasillaN;
	}

	public void setIndCasillaN(int indCasillaN) {
		this.indCasillaN = indCasillaN;
	}

	public String getTipoDocumentoA() {
		return tipoDocumentoA;
	}

	public void setTipoDocumentoA(String tipoDocumentoA) {
		this.tipoDocumentoA = tipoDocumentoA;
	}

	public String getNroDocumentoA() {
		return nroDocumentoA;
	}

	public void setNroDocumentoA(String nroDocumentoA) {
		this.nroDocumentoA = nroDocumentoA;
	}

	public String getNombreA() {
		return nombreA;
	}

	public void setNombreA(String nombreA) {
		this.nombreA = nombreA;
	}

	public String getUserCreaA() {
		return userCreaA;
	}

	public void setUserCreaA(String userCreaA) {
		this.userCreaA = userCreaA;
	}

	public String getUserActA() {
		return userActA;
	}

	public void setUserActA(String userActA) {
		this.userActA = userActA;
	}

	public String getCorreoA() {
		return correoA;
	}

	public void setCorreoA(String correoA) {
		this.correoA = correoA;
	}

	public String getTelefonoA() {
		return telefonoA;
	}

	public void setTelefonoA(String telefonoA) {
		this.telefonoA = telefonoA;
	}

	public String getDireccionA() {
		return direccionA;
	}

	public void setDireccionA(String direccionA) {
		this.direccionA = direccionA;
	}

	public int getIndCasillaA() {
		return indCasillaA;
	}

	public void setIndCasillaA(int indCasillaA) {
		this.indCasillaA = indCasillaA;
	}

	public IServiciosRemitenteBPM getServiciosremitentebpm() {
		return serviciosremitentebpm;
	}

	public void setServiciosremitentebpm(
			IServiciosRemitenteBPM serviciosremitentebpm) {
		this.serviciosremitentebpm = serviciosremitentebpm;
	}

	public String getResponsableA() {
		return responsableA;
	}

	public void setResponsableA(String responsableA) {
		this.responsableA = responsableA;
	}

	public void setRemitentebpmDAO(RemitenteBPMDAO remitentebpmDAO) {
		this.remitentebpmDAO = remitentebpmDAO;
	}


	public long getCorrelativoA() {
		return correlativoA;
	}


	public void setCorrelativoA(long correlativoA) {
		this.correlativoA = correlativoA;
	}


	public String getDescripcion_tipoA() {
		return descripcion_tipoA;
	}


	public void setDescripcion_tipoA(String descripcion_tipoA) {
		this.descripcion_tipoA = descripcion_tipoA;
	}

	
	
	
    
    
    
}
