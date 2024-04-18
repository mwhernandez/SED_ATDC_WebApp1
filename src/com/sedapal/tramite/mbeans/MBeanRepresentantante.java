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
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.TiposPersonasBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.RepresentanteDAO;
import com.sedapal.tramite.servicios.IServiciosRepresentante;
import com.sedapal.tramite.util.Utils;

public class MBeanRepresentantante implements IMBeanRepresentante, Serializable {

    private List<RepresentanteBean> representante;
    public IServiciosRepresentante serviciosrepresentante;
    private List items;
    private List items1;
    private String itemSeleccionado;
    private boolean ver = false;
    private String error = "Se Grabó Satisfactoriamente";
    private String mensaje = "";
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean verDetalles = false;
    // para el formulario nuevo
    private int codremitenteN;
    private int codrepresentanteN;
    private String vtiporepresentanteN;
    private String vtipodocumentoN;
    private String vnumerodocumentoN;
    private String vnombreN;
    private String vdireccionN;
    private String vcorreoN;
    private String vtelefonoN;
    private String vfaxN;
    private String vcelularN;
    private String vestadoN;
    private int indicadorN;
    private Date feccreN;
    private Date fecactN;
    private String vrescreN;
    private String vresactN;
    private String estadoN;
    // para el formulario Actualizar
    private int codremitenteA;
    private int codrepresentanteA;
    private String vtiporepresentanteA;
    private String vtipodocumentoA;
    private String vnumerodocumentoA;
    private String vnombreA;
    private String vdireccionA;
    private String vcorreoA;
    private String vtelefonoA;
    private String vfaxA;
    private String vcelularA;
    private String vestadoA;
    private int indicadorA;
    private Date feccreA;
    private Date fecactA;
    private String vrescreA;
    private String vresactA;
    private String estadoA;
    //binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    // para el filtro 
    private String opcion;
    private String detalle;
    private int codigo;
    private RepresentanteDAO representanteDAO;

    public void eventEliminar(ActionEvent event) {

        if (this.selectedRepresentantees.size() < 1) {
            this.error = "Debe seleccionar por lo menos un Remitente";
            this.ver = true;
        } else {
            // llamamos a servicios o dao pasandole los datos a eliminar
            int codremite = 0;
            int codrepresentante = 0;
            for (RepresentanteBean a : selectedRepresentantees) {
                codremite = a.getCodremitente();
                codrepresentante = a.getCodrepresentante();
                // le paso como parametro al stored
                // falta programar
                serviciosrepresentante.eliminarRepresentante(codremite, codrepresentante);
            }
            this.mensaje = "Registro Eliminado!";
            selectedRepresentantees.clear();
            this.representante = serviciosrepresentante.catalogo(codremite);// actualiza el
            // reporte
            this.ver = true;
        }

    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        if (this.selectedRepresentantees.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;

            for (RepresentanteBean t : selectedRepresentantees) {
                this.codremitenteA = t.getCodremitente();
                this.codrepresentanteA = t.getCodrepresentante();
                this.vtiporepresentanteA = t.getVtiporepresentante();
                this.vtipodocumentoA = t.getVtipodocumento();
                this.vnumerodocumentoA = t.getVnumerodocumento();
                this.vnombreA = t.getVnombre();
                this.vdireccionA = t.getVdireccion();
                this.vcorreoA = t.getVcorreo();
                this.vtelefonoA = t.getVtelefono();
                this.vfaxA = t.getVfax();
                this.vcelularA = t.getVcelular();
                //this.vestadoA = t.getVestado();
                this.vestadoA = t.getEstado();

            }
            selectedRepresentantees.clear();
            ///acediendo a sesion http
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            LlavesBean beans = null;
            beans = (LlavesBean) session.getAttribute("sLlaves");
            codigo = beans.getCodigo();
            this.representante = serviciosrepresentante.catalogo(codigo);// actualiza el reporte
        } else {
            this.error = "Debe seleccionar solo un registro";
            this.ver = true;

        }
    }

    public void eventRefrescar(ActionEvent event) {
        this.verCatalogo = true;
        //this.verNuevo = false;
        //this.verActualizar = false;
    }

    public void eventActualizarRepresentante(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
        this.verActualizar = false;
        RepresentanteBean RepresentanteBean = new RepresentanteBean();
        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        this.vresactA = usuario.getLogin();
        // le paso como parametro al stored
        RepresentanteBean.setCodremitente(this.codremitenteA);
        RepresentanteBean.setCodrepresentante(this.codrepresentanteA);
        RepresentanteBean.setVtiporepresentante(this.vtiporepresentanteA);
        RepresentanteBean.setVtipodocumento(this.vtipodocumentoA);
        RepresentanteBean.setVnumerodocumento(this.vnumerodocumentoA);
        RepresentanteBean.setVnombre(this.vnombreA);
        RepresentanteBean.setVdireccion(this.vdireccionA);
        RepresentanteBean.setVcorreo(this.vcorreoA);
        RepresentanteBean.setVtelefono(this.vtelefonoA);
        RepresentanteBean.setVfax(this.vfaxA);
        RepresentanteBean.setVcelular(this.vcelularA);
        RepresentanteBean.setVestado(this.vestadoA);
        RepresentanteBean.setVresact(this.vresactA);
        serviciosrepresentante.actualizarRepresentante(RepresentanteBean);
        this.representante = serviciosrepresentante.catalogo(codigo);// actualiza el
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

    public void eventRegistrarRepresentante(ActionEvent event) {
        try {
            this.verNuevo = false;
            ///acediendo a sesion http
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            /////guardando en sesion un objeto
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            this.vrescreN = usuario.getLogin();
            this.vresactN = usuario.getLogin();
            ///acediendo a sesion http
            //HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            LlavesBean beans = null;
            beans = (LlavesBean) session.getAttribute("sLlaves");
            codigo = beans.getCodigo();
            RepresentanteBean representanteBean = new RepresentanteBean();
            representanteBean.setCodremitente(this.codigo);
            representanteBean.setVtipodocumento(this.vtipodocumentoN);
            representanteBean.setVtiporepresentante(this.vtiporepresentanteN);
            representanteBean.setVnumerodocumento(this.vnumerodocumentoN);
            representanteBean.setVnombre(this.vnombreN);
            representanteBean.setVdireccion(this.vdireccionN);
            representanteBean.setVcorreo(this.vcorreoN);
            representanteBean.setVtelefono(this.vtelefonoN);
            representanteBean.setVfax(this.vfaxN);
            representanteBean.setVcelular(this.vcelularN);
            representanteBean.setVestado(this.vestadoN);
            representanteBean.setVrescre(this.vrescreN);
            String out = serviciosrepresentante.nuevoRemitente(representanteBean);
            out = out.trim();
            System.out.println("OUT STORED!!!:" + out);
            if (out.equals("0")) {
                this.representante = serviciosrepresentante.catalogo(codigo);
                //ServiciosRepresentante.nuevoRemitente(RepresentanteBean);
                ///this.representante = serviciosrepresentante.catalogo();// actualiza el
                // 	reporte
                this.mensaje = "Se Realizaron los Cambios Correctamente";
                this.ver = true;
                this.verCatalogo = true;
                this.verActualizar = false;
            } else {
                this.mensaje = "Ya existe el Remitente en la Base de Datos, No se grabó";
                this.ver = true;
                this.verCatalogo = true;

            }

        } catch (Exception e) {
            logger.error("Fallo el registro", e);
            this.mensaje = "Transaccion No valida.";
            this.ver = true;
        }

    }

    public void cerrarDetalles(ActionEvent event) {
        this.verDetalles = false;

    }

    public List<RepresentanteBean> getRepresentante() {
        return this.representante;
    }

    public void setRemitente(List<RepresentanteBean> representante) {
        this.representante = representante;
    }

    public void setRepresentante(List<RepresentanteBean> representante) {
        this.representante = representante;
    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }
    /**
     * ***********************************************
     */
    // list of selected employees
    protected ArrayList<RepresentanteBean> selectedRepresentantees;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = true;
    protected boolean enhancedMultiple;

    public MBeanRepresentantante() {
        selectedRepresentantees = new ArrayList<RepresentanteBean>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (RepresentanteBean t : selectedRepresentantees) {
            //System.out.println(t.getCodigo() + "   " + t.getDescripcion());
        }
    }

    @PostConstruct
    public void posterior() {
        //this.representante = serviciosrepresentante.catalogo(601);
        //this.accion = serviciosAccion.catalogo();
        //System.out.println("Post Construct, cargo catalogo...size="+this.accion.size());
        //System.out.println("Contructor MBeanAccion....");
        List<TiposPersonasBean> personas = representanteDAO.tipos_persona();
        List itemsPersonas = Utils.getTipos_Personas(personas);
        this.items = itemsPersonas;

        List<TiposDocumentosBean> documentos = representanteDAO.tipos_documento();
        List itemsdocumentos = Utils.getTipos_Documentos(documentos);
        this.items1 = itemsdocumentos;
    }

    /**
     * SelectionListener bound to the ice:rowSelector component. Called when a
     * row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedRepresentantees.clear();

        /*
         * If application developers rely on validation to control submission of
         * the form or use the result of the selection in cascading control set
         * up the may want to defer procession of the event to
         * INVOKE_APPLICATION stage by using this code fragment if
         * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
         * return; }
         */

        System.out.println("Tama:" + representante.size());
        // build the new selected list
        RepresentanteBean representantes;
        for (int i = 0, max = representante.size(); i < max; i++) {
            representantes = (RepresentanteBean) representante.get(i);
            if (representantes.isSelected()) {
                selectedRepresentantees.add(representantes);
            }
        }
        for (RepresentanteBean t : selectedRepresentantees) {
            //System.out.println(t.getCodigo() + "  " + t.getRepresentante());
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
            selectedRepresentantees.clear();

            // build the new selected list
            RepresentanteBean employee;
            for (int i = 0, max = representante.size(); i < max; i++) {
                employee = (RepresentanteBean) representante.get(i);
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
        this.codremitenteN = 0;
        this.codrepresentanteN = 0;
        this.vtiporepresentanteN = "";
        this.vtipodocumentoN = "";
        this.vnumerodocumentoN = "";
        this.vnombreN = "";
        this.vdireccionN = "";
        this.vcorreoN = "";
        this.vtelefonoN = "";
        this.vfaxN = "";
        this.vcelularN = "";
        this.vestadoN = "";

    }

    public void limpiarActualizar() {
        //this.codigoA="";		
        //this.descripcionA = "";
        //this.areaA=0;
        //this.estadoA = "";
        //this.responsableA="";
        //this.respactualA="";
    }

    public ArrayList<RepresentanteBean> getSelectedRepresentantees() {
        return selectedRepresentantees;
    }

    public void setSelectedRepresentantees(
            ArrayList<RepresentanteBean> selectedRepresentantees) {
        this.selectedRepresentantees = selectedRepresentantees;
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

    public String getItemSeleccionado() {
        return itemSeleccionado;
    }

    public void setItemSeleccionado(String itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }

    public void setRepresentanteDAO(RepresentanteDAO representanteDAO) {
        this.representanteDAO = representanteDAO;
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

    public boolean isverCatalogo() {
        return verCatalogo;
    }

    public void setverCatalogo(boolean verCatalogo) {
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

    public void setServiciosrepresentante(IServiciosRepresentante serviciosrepresentante) {
        this.serviciosrepresentante = serviciosrepresentante;
    }

    public int getCodremitenteN() {
        return codremitenteN;
    }

    public void setCodremitenteN(int codremitenteN) {
        this.codremitenteN = codremitenteN;
    }

    public int getCodrepresentanteN() {
        return codrepresentanteN;
    }

    public void setCodrepresentanteN(int codrepresentanteN) {
        this.codrepresentanteN = codrepresentanteN;
    }

    public String getVtiporepresentanteN() {
        return vtiporepresentanteN;
    }

    public void setVtiporepresentanteN(String vtiporepresentanteN) {
        this.vtiporepresentanteN = vtiporepresentanteN;
    }

    public String getVtipodocumentoN() {
        return vtipodocumentoN;
    }

    public void setVtipodocumentoN(String vtipodocumentoN) {
        this.vtipodocumentoN = vtipodocumentoN;
    }

    public String getVnumerodocumentoN() {
        return vnumerodocumentoN;
    }

    public void setVnumerodocumentoN(String vnumerodocumentoN) {
        this.vnumerodocumentoN = vnumerodocumentoN;
    }

    public String getVnombreN() {
        return vnombreN;
    }

    public void setVnombreN(String vnombreN) {
        this.vnombreN = vnombreN;
    }

    public String getVdireccionN() {
        return vdireccionN;
    }

    public void setVdireccionN(String vdireccionN) {
        this.vdireccionN = vdireccionN;
    }

    public String getVcorreoN() {
        return vcorreoN;
    }

    public void setVcorreoN(String vcorreoN) {
        this.vcorreoN = vcorreoN;
    }

    public String getVtelefonoN() {
        return vtelefonoN;
    }

    public void setVtelefonoN(String vtelefonoN) {
        this.vtelefonoN = vtelefonoN;
    }

    public String getVfaxN() {
        return vfaxN;
    }

    public void setVfaxN(String vfaxN) {
        this.vfaxN = vfaxN;
    }

    public String getVcelularN() {
        return vcelularN;
    }

    public void setVcelularN(String vcelularN) {
        this.vcelularN = vcelularN;
    }

    public String getVestadoN() {
        return vestadoN;
    }

    public void setVestadoN(String vestadoN) {
        this.vestadoN = vestadoN;
    }

    public int getIndicadorN() {
        return indicadorN;
    }

    public void setIndicadorN(int indicadorN) {
        this.indicadorN = indicadorN;
    }

    public Date getFeccreN() {
        return feccreN;
    }

    public void setFeccreN(Date feccreN) {
        this.feccreN = feccreN;
    }

    public Date getFecactN() {
        return fecactN;
    }

    public void setFecactN(Date fecactN) {
        this.fecactN = fecactN;
    }

    public String getVrescreN() {
        return vrescreN;
    }

    public void setVrescreN(String vrescreN) {
        this.vrescreN = vrescreN;
    }

    public String getVresactN() {
        return vresactN;
    }

    public void setVresactN(String vresactN) {
        this.vresactN = vresactN;
    }

    public int getCodremitenteA() {
        return codremitenteA;
    }

    public void setCodremitenteA(int codremitenteA) {
        this.codremitenteA = codremitenteA;
    }

    public int getCodrepresentanteA() {
        return codrepresentanteA;
    }

    public void setCodrepresentanteA(int codrepresentanteA) {
        this.codrepresentanteA = codrepresentanteA;
    }

    public String getVtiporepresentanteA() {
        return vtiporepresentanteA;
    }

    public void setVtiporepresentanteA(String vtiporepresentanteA) {
        this.vtiporepresentanteA = vtiporepresentanteA;
    }

    public String getVtipodocumentoA() {
        return vtipodocumentoA;
    }

    public void setVtipodocumentoA(String vtipodocumentoA) {
        this.vtipodocumentoA = vtipodocumentoA;
    }

    public String getVnumerodocumentoA() {
        return vnumerodocumentoA;
    }

    public void setVnumerodocumentoA(String vnumerodocumentoA) {
        this.vnumerodocumentoA = vnumerodocumentoA;
    }

    public String getVnombreA() {
        return vnombreA;
    }

    public void setVnombreA(String vnombreA) {
        this.vnombreA = vnombreA;
    }

    public String getVdireccionA() {
        return vdireccionA;
    }

    public void setVdireccionA(String vdireccionA) {
        this.vdireccionA = vdireccionA;
    }

    public String getVcorreoA() {
        return vcorreoA;
    }

    public void setVcorreoA(String vcorreoA) {
        this.vcorreoA = vcorreoA;
    }

    public String getVtelefonoA() {
        return vtelefonoA;
    }

    public void setVtelefonoA(String vtelefonoA) {
        this.vtelefonoA = vtelefonoA;
    }

    public String getVfaxA() {
        return vfaxA;
    }

    public void setVfaxA(String vfaxA) {
        this.vfaxA = vfaxA;
    }

    public String getVcelularA() {
        return vcelularA;
    }

    public void setVcelularA(String vcelularA) {
        this.vcelularA = vcelularA;
    }

    public String getVestadoA() {
        return vestadoA;
    }

    public void setVestadoA(String vestadoA) {
        this.vestadoA = vestadoA;
    }

    public int getIndicadorA() {
        return indicadorA;
    }

    public void setIndicadorA(int indicadorA) {
        this.indicadorA = indicadorA;
    }

    public Date getFeccreA() {
        return feccreA;
    }

    public void setFeccreA(Date feccreA) {
        this.feccreA = feccreA;
    }

    public Date getFecactA() {
        return fecactA;
    }

    public void setFecactA(Date fecactA) {
        this.fecactA = fecactA;
    }

    public String getVrescreA() {
        return vrescreA;
    }

    public void setVrescreA(String vrescreA) {
        this.vrescreA = vrescreA;
    }

    public String getVresactA() {
        return vresactA;
    }

    public void setVresactA(String vresactA) {
        this.vresactA = vresactA;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public List getItems1() {
        return items1;
    }

    public void setItems1(List items1) {
        this.items1 = items1;
    }

    public String getEstadoN() {
        return estadoN;
    }

    public void setEstadoN(String estadoN) {
        this.estadoN = estadoN;
    }

    public String getEstadoA() {
        return estadoA;
    }

    public void setEstadoA(String estadoA) {
        this.estadoA = estadoA;
    }
}
