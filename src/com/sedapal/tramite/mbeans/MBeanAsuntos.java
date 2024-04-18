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




import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.AsuntoDAO;
import com.sedapal.tramite.dao.TiposDAO;
import com.sedapal.tramite.servicios.IServiciosAsunto;
import com.sedapal.tramite.servicios.IServiciosTipos;
import com.sedapal.tramite.servicios.ServiciosAsunto;

public class MBeanAsuntos implements IMBeanTipos, Serializable {

    private List<TiposBean> asunto;
    public IServiciosAsunto servicioasunto;
    private List items;
    private String itemSeleccionado;
    private boolean ver = false;
    private String mensaje;
    private String error = "Se Grabó Satisfactoriamente";
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean verDetalles = false;
    // para el formulario nuevo
    private String codigoN;
    private String descripcionN;
    private Date fechaN;
    private String responsableN;
    private String estadoN;    
    // para el formulario Actualizar
    private String codigoA;
    private String descripcionA;
    private Date fechaA;
    private String responsableA;
    private String estadoA;    
    //binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    //variables para el filtro
    private String opcion;
    private String detalle;
    private AsuntoDAO asuntoDAO;

    public void eventEliminar(ActionEvent event) {

        if (this.selectedEmployees.size() == 1) {        	
        	 // llamamos a servicios o dao pasandole los datos a eliminar
            String codigo;
            for (TiposBean t : selectedEmployees) {
                codigo = t.getCodigo();                
                servicioasunto.eliminarAsunto(codigo);
            }
            this.mensaje = "El registro de asunto ha sido eliminados!";
            selectedEmployees.clear();            
            this.asunto = servicioasunto.catalogo();
            this.ver = true;            
            
        } else {           
        	this.mensaje = "Debe seleccionar por lo menos un registro";
            this.ver = true;
        }

    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
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

            for (TiposBean t : selectedEmployees) {
                this.codigoA = String.valueOf(t.getCodigo());
                this.descripcionA = t.getDescripcion();
                this.fechaA = t.getFecha();
                this.estadoA = t.getEstado();                
            }
            selectedEmployees.clear();            
            this.asunto = servicioasunto.catalogo();
            this.mensaje = "Se Tipo Actualizó Correctamente";
        } else {
            this.error = "Debe seleccionar solo un registro";
            this.ver = true;

        }
    }

    public void eventActualizarTipos(ActionEvent event) {
    	 try {
	        // llama DAO/Stored Para actualizar producto
	        this.verActualizar = false;
	        TiposBean tiposBean = new TiposBean();
	        Usuario usuarioBean = new Usuario();
	        // le paso como parametro al stored
	        tiposBean.setTipo(this.codigoA);
	        tiposBean.setDescripcion(this.descripcionA);       
	        tiposBean.setResponsable(this.responsableA);
	        tiposBean.setEstado(this.estadoA);     
	        
		        Map outMap = servicioasunto.actualizarAsunto(tiposBean);
		        String out = (String) outMap.get("out");
		        
		        if (out.equals("0")) {
		        	this.asunto = servicioasunto.catalogo();
		            this.mensaje = "Se Realizaron los Cambios Correctamente";
		            this.verCatalogo = true;
		            this.verActualizar = false;
		            this.ver = true;
		        } else {
		        	this.mensaje = "No se actualizo en la Base de Datos, No se Grabó";
		            this.ver = true;
		            this.verCatalogo = true;
		        }
	        
    	 	} catch (Exception e) {
	            logger.error("Fallo el registro", e);
	            this.mensaje = "Error,Comunicarse con el ETIC";
	            this.ver = true;
	        }
        
        
    }
    
    public void eventRegistrarTipos(ActionEvent event) {
        try {
            HttpSession session = null;
            // /acediendo a sesion http
            session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            // ///guardando en sesion un objeto
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            this.responsableN = usuario.getLogin();
            this.verNuevo = false;
            TiposBean tiposBean = new TiposBean();
            tiposBean.setDescripcion(this.descripcionN);            
            tiposBean.setEstado(this.estadoN);
            tiposBean.setResponsable(this.responsableN);
            
            Map outMap = servicioasunto.nuevoAsunto(tiposBean);
            
            String out = (String) outMap.get("out");
            System.out.println("OUT STORED!!!:" + out);
            if (out.equals("0")) {
            	this.asunto = servicioasunto.catalogo();			
                this.ver = true;
                this.verCatalogo = true;
                this.verActualizar = false;
                this.mensaje = "Se Realizaron los Cambios Correctamente";
            } else {
                this.mensaje = "Ya existe el Tipo de Documento en la Base de Datos, No se Grabó";
                this.ver = true;
                this.verCatalogo = true;

            }
        } catch (Exception e) {
            logger.error("Fallo el registro", e);
            this.mensaje = "Error,Comunicarse con el ETIC";
            this.ver = true;
        }

    }

    public void eventNuevo(ActionEvent event) {
        this.limpiar();
        //combo.setLabel("ITEMS");
        this.verNuevo = true;
        this.verCatalogo = false;

    }

    public void eventRefrescar(ActionEvent event) {
        this.verCatalogo = true;
        this.asunto = servicioasunto.catalogo();
        this.detalle = "";

    }

    

    public void cerrarDetalles(ActionEvent event) {
        this.verDetalles = false;

    }

    

    public List<TiposBean> getAsunto() {
		return asunto;
	}

	public void setAsunto(List<TiposBean> asunto) {
		this.asunto = asunto;
	}

	public void setServicioAsunto(IServiciosAsunto servicioAsunto) {
		this.servicioasunto = servicioAsunto;
	}

	public void cerrar(ActionEvent event) {
        this.ver = false;
    }
    /**
     * ***********************************************
     */
    // list of selected employees
    protected ArrayList<TiposBean> selectedEmployees;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanAsuntos() {
        selectedEmployees = new ArrayList<TiposBean>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (TiposBean t : selectedEmployees) {
            System.out.println(t.getCodigo() + "   " + t.getDescripcion());
        }
    }

    @PostConstruct
    public void posterior() {
        
        this.asunto = servicioasunto.catalogo();
        System.out.println("Post Construct, cargo catalogo...size=" + this.asunto.size());
        System.out.println("Contructor MBeanTipos....");
        //List<Tipos> tipos = productoDAO.tipos();
        //List itemsTipos = Utils.getTipos(tipos);
        //this.items = itemsTipos;
        //System.out.println("Se cargo los combos....");
        //for (Tipos p : tipos)
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

        System.out.println("Tama:" + asunto.size());
        // build the new selected list
        TiposBean employee;
        for (int i = 0, max = asunto.size(); i < max; i++) {
            employee = (TiposBean) asunto.get(i);
            if (employee.isSelected()) {
                selectedEmployees.add(employee);
            }
        }
        for (TiposBean t : selectedEmployees) {
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
            selectedEmployees.clear();

            // build the new selected list
            TiposBean employee;
            for (int i = 0, max = asunto.size(); i < max; i++) {
                employee = (TiposBean) asunto.get(i);
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
        if (this.detalle.equals("") || this.opcion.equals("")) {
            this.mensaje = "Debe de Ingresar una opcion de busqueda";
            this.ver = true;
        } else {
        	this.asunto = asuntoDAO.filtrosSP(opcion, detalle);
        	System.out.println(opcion + "  " + detalle);
        }
    }

    public void eventCancelar(ActionEvent event) {
        this.verCatalogo = true;
        this.verNuevo = false;
        this.verActualizar = false;
    }

    public void limpiar() {
        this.codigoN = "";
        this.descripcionN = "";
        this.fechaN = new Date();
        this.responsableN = "";
        this.estadoN = "";
       
    }

    public void limpiarActualizar() {
        this.codigoA = "";
        this.descripcionA = "";
        this.fechaA = new Date();
        this.responsableA = "";
        this.estadoA = "";
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

   
    
    public void setAsuntoDAO(AsuntoDAO asuntoDAO) {
		this.asuntoDAO = asuntoDAO;
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

    public String getCodigoN() {
        return codigoN;
    }

    public void setCodigoN(String codigoN) {
        this.codigoN = codigoN;
    }

    public String getDescripcionN() {
        return descripcionN;
    }

    public void setDescripcionN(String descripcionN) {
        this.descripcionN = descripcionN;
    }

    public Date getFechaN() {
        return fechaN;
    }

    public void setFechaN(Date fechaN) {
        this.fechaN = fechaN;
    }

    public String getResponsableN() {
        return responsableN;
    }

    public void setResponsableN(String responsableN) {
        this.responsableN = responsableN;
    }

    public String getEstadoN() {
        return estadoN;
    }

    public void setEstadoN(String estadoN) {
        this.estadoN = estadoN;
    }

    public String getCodigoA() {
        return codigoA;
    }

    public void setCodigoA(String codigoA) {
        this.codigoA = codigoA;
    }

    public String getDescripcionA() {
        return descripcionA;
    }

    public void setDescripcionA(String descripcionA) {
        this.descripcionA = descripcionA;
    }

    public Date getFechaA() {
        return fechaA;
    }

    public void setFechaA(Date fechaA) {
        this.fechaA = fechaA;
    }

    public String getResponsableA() {
        return responsableA;
    }

    public void setResponsableA(String responsableA) {
        this.responsableA = responsableA;
    }

    public String getEstadoA() {
        return estadoA;
    }

    public void setEstadoA(String estadoA) {
        this.estadoA = estadoA;
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
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

   

    
}
