package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.dao.AccionDAO;
import com.sedapal.tramite.servicios.IServiciosAccion;

public class MBeanAccion implements IMBeanAccion, Serializable {

    private List<AccionBean> accion;
    public IServiciosAccion serviciosAccion;
    private List items;
    private String itemSeleccionado;
    private boolean ver = false;
    private String error = "Se Grabó Satisfactoriamente";
    private String mensaje = "Se Grabó Satisfactoriamente";
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
    private AccionDAO accionDAO;

    public void eventEliminar(ActionEvent event) {

        if (this.selectedAccionees.size() < 1) {
            this.error = "Debe seleccionar por lo menos una Acción";
            this.ver = true;
        } else {
            // llamamos a servicios o dao pasandole los datos a eliminar
            int codigo;
            for (AccionBean a : selectedAccionees) {
                codigo = a.getCodigo();
                // le paso como parametro al stored
                //serviciosAccion.eliminarAccion(codigo);
            }
            this.mensaje = "Registro eliminado!";
            selectedAccionees.clear();
            this.accion = serviciosAccion.catalogo();// actualiza el
            // reporte 
            this.ver = true;
        }

    }

    public void eventImpresion(ActionEvent event) {
    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        if (this.selectedAccionees.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;

            for (AccionBean t : selectedAccionees) {
                this.codigoA = String.valueOf(t.getCodigo());
                this.descripcionA = t.getDescripcion().toUpperCase();
                this.fechaA = t.getFecha();
                this.responsableA = t.getResponsable();
                this.estadoA = t.getEstado();
            }
            selectedAccionees.clear();
            this.accion = serviciosAccion.catalogo();// actualiza el
            // reporte
        } else {
            this.error = "Debe seleccionar solo un registro";
            this.ver = true;

        }
    }

    public void eventActualizarAccion(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
        this.verActualizar = false;
        AccionBean accionBean = new AccionBean();
        // le paso como parametro al stored
        //accionBean.setCodigo(this.codigoA);
        accionBean.setDescripcion(this.descripcionA.toUpperCase());
        accionBean.setFecha(this.fechaA);
        accionBean.setEstado(this.estadoA);
        //tiposBean.setResponsable(this.responsableA);		
        serviciosAccion.actualizarAccion(accionBean);
        this.accion = serviciosAccion.catalogo();// actualiza el
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

    public void eventRegistrarAccion(ActionEvent event) {
        try {
            this.verNuevo = false;
            AccionBean accionBean = new AccionBean();
            accionBean.setDescripcion(this.descripcionN);
            accionBean.setFecha(this.fechaN);
            accionBean.setEstado(this.estadoN);
            System.out.println(this.descripcionN);
            System.out.println(this.estadoN);
            serviciosAccion.nuevoAccion(accionBean);
            this.accion = serviciosAccion.catalogo();// actualiza el
            // reporte
            this.mensaje = "Transaccion ok.";
            this.ver = true;
            this.verCatalogo = true;
            this.verActualizar = false;
        } catch (Exception e) {
            logger.error("Fallo el registro", e);
            this.error = "Transaccion No valida.";
            this.ver = true;
        }

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

    public MBeanAccion() {
        selectedAccionees = new ArrayList<AccionBean>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (AccionBean t : selectedAccionees) {
            System.out.println(t.getCodigo() + "   " + t.getDescripcion());
        }
    }

    @PostConstruct
    public void posterior() {
        this.accion = serviciosAccion.catalogo();
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
}
