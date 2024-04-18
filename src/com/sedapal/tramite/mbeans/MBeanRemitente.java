package com.sedapal.tramite.mbeans;

import java.io.Serializable;
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


import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.RemitenteDAO;
import com.sedapal.tramite.dao.RepresentanteDAO;
import com.sedapal.tramite.servicios.IServiciosRemitente;
import com.sedapal.tramite.servicios.IServiciosRepresentante;

public class MBeanRemitente implements IMBeanRemitente, Serializable {

    private List<RemitenteBean> remitente;
    public IServiciosRemitente serviciosRemitente;
    private List items;
    private String itemSeleccionado;
    private boolean ver = false;
    private String error = "Se Grabó Satisfactoriamente";
    private String mensaje = "";
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean verDetalles = false;
    // para el formulario nuevo
    private String codigoN;
    private String descripcionN;
    private String representanteN;
    private Integer areaN;
    private String estadoN;
    private Date fechaN;
    private String responsableN;
    private String respactualN;
    // para el formulario Actualizar
    private String codigoA;
    private String descripcionA;
    private String representanteA;
    private Integer areaA;
    private String estadoA;
    private Date fechaA;
    private String responsableA;
    private String respactualA;
    //binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    // para el filtro 
    private String opcion;
    private String detalle;
    ///Llaves
    private int codigo;
    private RemitenteDAO remitenteDAO;
    private MBeanRepresentantante mBeanRepresentantante;
    public IServiciosRepresentante serviciosRepresentante;
    private RepresentanteDAO representanteDAO;

    public void eventEliminar(ActionEvent event) {

        if (this.selectedRemitentees.size() < 1) {
            this.error = "Debe seleccionar por lo menos un Remitente";
            this.ver = true;
        } else {
            // llamamos a servicios o dao pasandole los datos a eliminar
            int codigo = 0;
            for (RemitenteBean a : selectedRemitentees) {
                codigo = a.getCodigo();
                // le paso como parametro al stored
                serviciosRemitente.eliminarRemitente(codigo);
            }
            this.mensaje = "Registro Eliminado!";
            selectedRemitentees.clear();
            this.remitente = serviciosRemitente.catalogo();// actualiza el
            // reporte
            this.ver = true;
        }

    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        if (this.selectedRemitentees.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;

            for (RemitenteBean t : selectedRemitentees) {

                this.codigoA = String.valueOf(t.getCodigo());
                this.descripcionA = t.getDescripcion();
                this.areaA = t.getArea();
                this.estadoA = t.getEstado();
                this.fechaA = t.getFecha();

                //this.responsableA = t.getResponsable();
                //this.respactualA = t.getResponActual();

            }
            selectedRemitentees.clear();
            this.remitente = serviciosRemitente.catalogo();// actualiza el reporte
        } else {
            this.error = "Debe seleccionar solo un registro";
            this.ver = true;

        }
    }

    public void eventRefrescar(ActionEvent event) {
        this.verCatalogo = true;
        this.remitente = serviciosRemitente.catalogo();
        this.detalle = "";
        //this.verNuevo = false;
        //this.verActualizar = false;
    }

    public void eventActualizarRemitente(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
        this.verActualizar = false;
        RemitenteBean remitenteBean = new RemitenteBean();
        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        this.areaA = usuario.getCodarea();
        this.responsableA = usuario.getLogin();
        // le paso como parametro al stored
        remitenteBean.setCodigo(Integer.parseInt(this.codigoA));
        remitenteBean.setDescripcion(this.descripcionA);
        remitenteBean.setEstado(this.estadoA);
        remitenteBean.setFecha(this.fechaA);
        remitenteBean.setResponActual(this.responsableA);
        remitenteBean.setArea(this.areaA);
        serviciosRemitente.actualizarRemitente(remitenteBean);
        this.remitente = serviciosRemitente.catalogo();// actualiza el
        // reporte
        this.verCatalogo = true;
        this.verActualizar = false;
    }

    public void eventNuevo(ActionEvent event) {
        this.limpiar();
        //combo.setLabel("ITEMS");
        this.verNuevo = true;
        this.verCatalogo = false;

    }

    public void eventRegistrarRemitente(ActionEvent event) {
        try {
            this.verNuevo = false;
            ///acediendo a sesion http
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            /////guardando en sesion un objeto
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            this.areaN = usuario.getCodarea();
            this.responsableN = usuario.getLogin();
            RemitenteBean remitenteBean = new RemitenteBean();
            remitenteBean.setDescripcion(this.descripcionN);
            remitenteBean.setEstado(this.estadoN);
            remitenteBean.setFecha(this.fechaN);
            remitenteBean.setResponsable(this.responsableN);
            remitenteBean.setResponActual(this.responsableN);
            remitenteBean.setArea(this.areaN);
            //System.out.println(this.descripcionN);
            //System.out.println(this.estadoN);
            String out = serviciosRemitente.nuevoRemitente(remitenteBean);
            out = out.trim();
            System.out.println("OUT STORED!!!:" + out);
            if (out.equals("0")) {
                this.remitente = serviciosRemitente.catalogo();// actualiza el
                // 	reporte
                this.mensaje = "Se grabó Satisfactoriamente";
                this.ver = true;
                this.verCatalogo = true;
                this.verActualizar = false;
            } else {
                this.mensaje = "Ya existe el Remitente en la Base de Datos, No se grabó";
                this.ver = true;
                this.verCatalogo = true;

            }
            //this.verNuevo = true;
        } catch (Exception e) {
            logger.error("Fallo el registro", e);
            this.mensaje = "Transaccion No valida.";
            this.ver = true;
        }

    }

    public void cerrarDetalles(ActionEvent event) {
        this.verDetalles = false;

    }

    public List<RemitenteBean> getRemitente() {
        return this.remitente;
    }

    public void setRemitente(List<RemitenteBean> remitente) {
        this.remitente = remitente;
    }

    public void setServiciosRemitente(IServiciosRemitente serviciosRemitente) {
        this.serviciosRemitente = serviciosRemitente;

    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }

    public void eventFiltros(ActionEvent event) {
        //this.ver = true;	
        System.out.println("Filtrando..");
        this.remitente = remitenteDAO.filtrosSP(opcion, detalle);
        System.out.println(opcion + "  " + detalle);
    }
    /**
     * ***********************************************
     */
    // list of selected employees
    protected ArrayList<RemitenteBean> selectedRemitentees;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanRemitente() {
        selectedRemitentees = new ArrayList<RemitenteBean>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (RemitenteBean t : selectedRemitentees) {
            System.out.println(t.getCodigo() + "   " + t.getDescripcion());
        }
    }

    @PostConstruct
    public void posterior() {
        this.remitente = serviciosRemitente.catalogo();
        //this.accion = serviciosAccion.catalogo();
        //System.out.println("Post Construct, cargo catalogo...size="+this.accion.size());
        //System.out.println("Contructor MBeanAccion....");
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
        selectedRemitentees.clear();

        /*
         * If application developers rely on validation to control submission of
         * the form or use the result of the selection in cascading control set
         * up the may want to defer procession of the event to
         * INVOKE_APPLICATION stage by using this code fragment if
         * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
         * return; }
         */

        System.out.println("Tama:" + remitente.size());
        // build the new selected list
        RemitenteBean remitentes;
        for (int i = 0, max = remitente.size(); i < max; i++) {
            remitentes = (RemitenteBean) remitente.get(i);
            if (remitentes.isSelected()) {
                selectedRemitentees.add(remitentes);
            }
        }
        for (RemitenteBean t : selectedRemitentees) {
            System.out.println(t.getCodigo() + "  " + t.getDescripcion());
            this.codigo = t.getCodigo();
            LlavesBean beans = new LlavesBean();
            beans.setCodigo(this.codigo);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("sLlaves", beans);
            List<RepresentanteBean> catalogo = null;
            //Eli Comenta este codigo
            catalogo = this.serviciosRepresentante.catalogo(this.codigo);
            this.mBeanRepresentantante.setRepresentante(catalogo);

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
            selectedRemitentees.clear();

            // build the new selected list
            RemitenteBean employee;
            for (int i = 0, max = remitente.size(); i < max; i++) {
                employee = (RemitenteBean) remitente.get(i);
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
        this.codigoN = "";
        //this.descripcionN = "";
        //this.areaN=0;
        //this.estadoN = "";
        this.fechaN = new Date();
        this.descripcionN = "";
        //this.responsableN="";
        //this.respactualN="";
    }

    public void limpiarActualizar() {
        //this.codigoA="";		
        //this.descripcionA = "";
        //this.areaA=0;
        //this.estadoA = "";
        this.fechaA = new Date();
        //this.responsableA="";
        //this.respactualA="";
    }

    public ArrayList getSelectedResponsablees() {
        return selectedRemitentees;
    }

    public void setSelectedResponsablees(ArrayList selectedRemitentees) {
        this.selectedRemitentees = selectedRemitentees;
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

    public void setRemitenteDAO(RemitenteDAO remitenteDAO) {
        this.remitenteDAO = remitenteDAO;
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

    public Integer getAreaN() {
        return areaN;
    }

    public void setAreaN(Integer areaN) {
        this.areaN = areaN;
    }

    public Date getFechaN() {
        return fechaN;
    }

    public void setFechaN(Date fechaN) {
        this.fechaN = fechaN;
    }

    public String getResponActualN() {
        return respactualN;
    }

    public void setResponActualN(String respactualN) {
        this.respactualN = respactualN;
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

    public Integer getAreaA() {
        return areaA;
    }

    public void setAreaA(Integer areaA) {
        this.areaA = areaA;
    }

    public Date getFechaA() {
        return fechaA;
    }

    public void setFechaA(Date fechaA) {
        this.fechaA = fechaA;
    }

    public String getResponActualA() {
        return respactualA;
    }

    public void setResponActualA(String respactualA) {
        this.respactualA = respactualA;
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

    public String getRepresentanteN() {
        return representanteN;
    }

    public void setRepresentanteN(String representanteN) {
        this.representanteN = representanteN;
    }

    public String getRepresentanteA() {
        return representanteA;
    }

    public void setRepresentanteA(String representanteA) {
        this.representanteA = representanteA;
    }

    public void setmBeanRepresentantante(MBeanRepresentantante mBeanRepresentantante) {
        this.mBeanRepresentantante = mBeanRepresentantante;
    }

    public void setServiciosRepresentante(IServiciosRepresentante serviciosRepresentante) {
        this.serviciosRepresentante = serviciosRepresentante;
    }

    public void setRepresentanteDAO(RepresentanteDAO representanteDAO) {
        this.representanteDAO = representanteDAO;
    }
}
