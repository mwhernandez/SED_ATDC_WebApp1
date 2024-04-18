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


import com.sedapal.tramite.beans.FeriadosBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.FeriadoDAO;
import com.sedapal.tramite.servicios.IServiciosFeriado;
import com.sedapal.tramite.util.Utils;

public class MBeanFeriados implements IMBeanFeriados, Serializable {

    private List<FeriadosBean> feriado;
    public IServiciosFeriado serviciosFeriado;
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
    private int ncodigoN;
    private Date dferiadoN;
    private String vdescripcionN;
    private String vtipoferiadoN;
    private String vestadoN;
    private Date dfeccreaN;
    private Date dfecactualN;
    private String responsableN;
    private String respactualN;
    private String vcodestadoN;
    // para el formulario Actualizar
    private int ncodigoA;
    private Date dferiadoA;
    private String vdescripcionA;
    private String vtipoferiadoA;
    private String vestadoA;
    private String vcodestadoA;
    private Date dfeccreaA;
    private Date dfecactualA;
    private String responsableA;
    private String respactualA;
    //binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    // para el filtro 
    private String opcion;
    private String detalle;
    private FeriadoDAO feriadoDAO;

    public void eventEliminar(ActionEvent event) {

        if (this.selectedFeriadoss.size() < 1) {
            this.error = "Debe seleccionar por lo menos un Registro";
            this.ver = true;
        } else {
            // llamamos a servicios o dao pasandole los datos a eliminar
            int codigo = 0;
            for (FeriadosBean a : selectedFeriadoss) {
                codigo = a.getNcodigo();
                serviciosFeriado.eliminarFeriado(codigo);
            }
            this.mensaje = "Registro Eliminado!";
            selectedFeriadoss.clear();
            this.feriado = serviciosFeriado.catalogo();// actualiza el
            // reporte
            this.ver = true;
        }

    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        if (this.selectedFeriadoss.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;

            for (FeriadosBean t : selectedFeriadoss) {

                this.ncodigoA = t.getNcodigo();
                this.dferiadoA = t.getDferiado();
                this.vdescripcionA = t.getVdescripcion();
                this.vtipoferiadoA = t.getVtipoferiado();
                this.vestadoA = t.getVestado();
                this.dfecactualA = t.getDfecactual();
                this.dfeccreaA = t.getDfeccrea();
                this.respactualA = t.getRespactual();
                this.responsableA = t.getResponsable();


            }
            selectedFeriadoss.clear();
            this.feriado = serviciosFeriado.catalogo();// actualiza el reporte
        } else {
            this.error = "Debe seleccionar solo un registro";
            this.ver = true;

        }
    }

    public void eventRefrescar(ActionEvent event) {
        this.verCatalogo = true;
        this.feriado = serviciosFeriado.catalogo();
        this.detalle = "";

        //this.verNuevo = false;
        //this.verActualizar = false;
    }

    public void eventActualizarFeriado(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
        this.verActualizar = false;
        FeriadosBean feriadoBean = new FeriadosBean();
        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        respactualA = usuario.getLogin();
        feriadoBean.setNcodigo(this.ncodigoA);
        feriadoBean.setDferiado(this.dferiadoA);
        feriadoBean.setVdescripcion(this.vdescripcionA);
        feriadoBean.setVtipoferiado(this.vtipoferiadoA);
        feriadoBean.setVestado(this.vestadoA);
        feriadoBean.setRespactual(this.respactualA);
        serviciosFeriado.actualizarFeriado(feriadoBean);
        this.feriado = serviciosFeriado.catalogo();// actualiza el
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

    public void eventRegistrarFeriado(ActionEvent event) {
        try {
            this.verNuevo = false;
            ///acediendo a sesion http
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            /////guardando en sesion un objeto
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            this.respactualN = usuario.getLogin();
            FeriadosBean feriadoBean = new FeriadosBean();
            feriadoBean.setDferiado(this.dferiadoN);
            feriadoBean.setVdescripcion(this.vdescripcionN);
            feriadoBean.setVtipoferiado(this.vtipoferiadoN);
            feriadoBean.setVestado(this.vestadoN);
            feriadoBean.setRespactual(this.respactualN);
            serviciosFeriado.nuevoFeriado(feriadoBean);
            this.feriado = serviciosFeriado.catalogo();	// reporte			
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

    public List<FeriadosBean> getFeriado() {
        return this.feriado;
    }

    public void setFeriado(List<FeriadosBean> feriado) {
        this.feriado = feriado;
    }

    public void setServiciosFeriado(IServiciosFeriado serviciosFeriado) {
        this.serviciosFeriado = serviciosFeriado;

    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }

    public void eventFiltros(ActionEvent event) {
        //this.ver = true;	
        System.out.println("Filtrando..");
        if (this.detalle.equals("")) {
            this.error = "Debe de Ingresar una opcion de busqueda";
            this.ver = true;
        } else {
            System.out.println(opcion + "  " + detalle);
            this.feriado = feriadoDAO.filtrosSP(opcion, detalle);
        }



    }
    /**
     * ***********************************************
     */
    // list of selected employees
    protected ArrayList<FeriadosBean> selectedFeriadoss;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanFeriados() {
        selectedFeriadoss = new ArrayList<FeriadosBean>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (FeriadosBean t : selectedFeriadoss) {
            //System.out.println(t.getCodigo() + "   " + t.getDescripcion());
        }
    }

    @PostConstruct
    public void posterior() {
        this.feriado = serviciosFeriado.catalogo();
        //this.accion = serviciosAccion.catalogo();
        //System.out.println("Post Construct, cargo catalogo...size="+this.accion.size());
        //System.out.println("Contructor MBeanAccion....");
        List<TiposBean> tipos = feriadoDAO.tipos();
        List itemstipos = Utils.getTipos(tipos);
        this.items = itemstipos;

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
        selectedFeriadoss.clear();

        /*
         * If application developers rely on validation to control submission of
         * the form or use the result of the selection in cascading control set
         * up the may want to defer procession of the event to
         * INVOKE_APPLICATION stage by using this code fragment if
         * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
         * return; }
         */

        System.out.println("Tama:" + feriado.size());
        // build the new selected list
        FeriadosBean feriados;
        for (int i = 0, max = feriado.size(); i < max; i++) {
            feriados = (FeriadosBean) feriado.get(i);
            if (feriados.isSelected()) {
                selectedFeriadoss.add(feriados);
            }
        }
        for (FeriadosBean t : selectedFeriadoss) {
            System.out.println(t.getNcodigo() + "  " + t.getDferiado() + " " + t.getVestado());

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
            selectedFeriadoss.clear();

            // build the new selected list
            FeriadosBean employee;
            for (int i = 0, max = feriado.size(); i < max; i++) {
                employee = (FeriadosBean) feriado.get(i);
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
        this.dfeccreaN = new Date();
        //this.descripcionN = "";
        //this.areaN=0;
        //this.estadoN = "";
        //this.fechaN = new Date();		
        //this.responsableN="";
        //this.respactualN="";
    }

    public void limpiarActualizar() {
        //this.codigoA="";		
        //this.descripcionA = "";
        //this.areaA=0;
        //this.estadoA = "";
        //this.responsableA="";
        //this.respactualA="";
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

    public void setFeriadoDAO(FeriadoDAO feriadoDAO) {
        this.feriadoDAO = feriadoDAO;
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

    public int getNcodigoN() {
        return ncodigoN;
    }

    public void setNcodigoN(int ncodigoN) {
        this.ncodigoN = ncodigoN;
    }

    public Date getDferiadoN() {
        return dferiadoN;
    }

    public void setDferiadoN(Date dferiadoN) {
        this.dferiadoN = dferiadoN;
    }

    public String getVdescripcionN() {
        return vdescripcionN;
    }

    public void setVdescripcionN(String vdescripcionN) {
        this.vdescripcionN = vdescripcionN;
    }

    public String getVtipoferiadoN() {
        return vtipoferiadoN;
    }

    public void setVtipoferiadoN(String vtipoferiadoN) {
        this.vtipoferiadoN = vtipoferiadoN;
    }

    public String getVestadoN() {
        return vestadoN;
    }

    public void setVestadoN(String vestadoN) {
        this.vestadoN = vestadoN;
    }

    public Date getDfeccreaN() {
        return dfeccreaN;
    }

    public void setDfeccreaN(Date dfeccreaN) {
        this.dfeccreaN = dfeccreaN;
    }

    public Date getDfecactualN() {
        return dfecactualN;
    }

    public void setDfecactualN(Date dfecactualN) {
        this.dfecactualN = dfecactualN;
    }

    public String getResponsableN() {
        return responsableN;
    }

    public void setResponsableN(String responsableN) {
        this.responsableN = responsableN;
    }

    public String getRespactualN() {
        return respactualN;
    }

    public void setRespactualN(String respactualN) {
        this.respactualN = respactualN;
    }

    public int getNcodigoA() {
        return ncodigoA;
    }

    public void setNcodigoA(int ncodigoA) {
        this.ncodigoA = ncodigoA;
    }

    public Date getDferiadoA() {
        return dferiadoA;
    }

    public void setDferiadoA(Date dferiadoA) {
        this.dferiadoA = dferiadoA;
    }

    public String getVdescripcionA() {
        return vdescripcionA;
    }

    public void setVdescripcionA(String vdescripcionA) {
        this.vdescripcionA = vdescripcionA;
    }

    public String getVtipoferiadoA() {
        return vtipoferiadoA;
    }

    public void setVtipoferiadoA(String vtipoferiadoA) {
        this.vtipoferiadoA = vtipoferiadoA;
    }

    public String getVestadoA() {
        return vestadoA;
    }

    public void setVestadoA(String vestadoA) {
        this.vestadoA = vestadoA;
    }

    public Date getDfeccreaA() {
        return dfeccreaA;
    }

    public void setDfeccreaA(Date dfeccreaA) {
        this.dfeccreaA = dfeccreaA;
    }

    public Date getDfecactualA() {
        return dfecactualA;
    }

    public void setDfecactualA(Date dfecactualA) {
        this.dfecactualA = dfecactualA;
    }

    public String getResponsableA() {
        return responsableA;
    }

    public void setResponsableA(String responsableA) {
        this.responsableA = responsableA;
    }

    public String getRespactualA() {
        return respactualA;
    }

    public void setRespactualA(String respactualA) {
        this.respactualA = respactualA;
    }

    public String getVcodestadoN() {
        return vcodestadoN;
    }

    public void setVcodestadoN(String vcodestadoN) {
        this.vcodestadoN = vcodestadoN;
    }

    public String getVcodestadoA() {
        return vcodestadoA;
    }

    public void setVcodestadoA(String vcodestadoA) {
        this.vcodestadoA = vcodestadoA;
    }
}
