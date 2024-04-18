package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.sedapal.tramite.beans.PerfilBean;
import com.sedapal.tramite.dao.PerfilDAO;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.servicios.IServiciosPerfil;

public class MBeanPerfiles implements IMBeanPerfiles, Serializable {

    private List<PerfilBean> perfiles;
    public IServiciosPerfil serviciosPerfil;
    private int codigo;
    private String nombre;
    private List items;
    private boolean ver = false;
    private String error = "Se Grabó Satisfactoriamente";
    private String mensaje = "Se Grabó Satisfactoriamente";
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean verDetalles = false;
    // para el formulario nuevo
    private String codigoN;
    private String nombreN;
    private String estadoN;
    // para el formulario Actualizar
    private String codigoA;
    private String nombreA;
    private String estadoA;
    private Logger logger = Logger.getLogger("R1");
    // para el reporte	
    private RecursoReport reporte;
    private RecursoReport recursoReport;
    private Map<String, Object> parametros = new HashMap<String, Object>();
    ExternalContext ec = null;
    private String empresa = "Sedapal";
    private PerfilDAO perfilDAO;
    private DataSource dataSource;

    public void eventEliminar(ActionEvent event) {

        if (this.selectedProfiles.size() < 1) {
            this.error = "Debe seleccionar por lo menos un Remitente";
            this.ver = true;
        } else {
            // llamamos a servicios o dao pasandole los datos a eliminar
            int codigo = 0;
            for (PerfilBean a : selectedProfiles) {
                codigo = a.getCodigo();
                // le paso como parametro al stored
                serviciosPerfil.eliminarPerfil(codigo);
            }
            this.mensaje = "Registro Eliminado!";
            selectedProfiles.clear();
            this.perfiles = serviciosPerfil.catalogo();// actualiza el
            // reporte
            this.ver = true;
        }

    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        if (this.selectedProfiles.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;

            for (PerfilBean t : selectedProfiles) {

                this.codigoA = String.valueOf(t.getCodigo());
                this.nombreA = t.getNombre();
                this.estadoA = t.getEstado();
                //this.fechaA = t.getFecha();
                //this.responsableA = t.getResponsable();
                //this.respactualA = t.getResponActual();
                System.out.println(t.getCodigo());

            }
            selectedProfiles.clear();
            this.perfiles = serviciosPerfil.catalogo();// actualiza el reporte
        } else {
            this.error = "Debe seleccionar solo un perfil";
            this.ver = true;

        }
    }

    public void eventActualizarPerfil(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
        this.verActualizar = false;
        PerfilBean perfilBean = new PerfilBean();
        // le paso como parametro al stored
        //remitenteBean.setCodigo(this.codigoA);
        perfilBean.setCodigo(Integer.parseInt(this.codigoA));
        perfilBean.setNombre(this.nombreA);
        perfilBean.setEstado(this.estadoA);
        serviciosPerfil.actualizarPerfil(perfilBean);
        this.perfiles = serviciosPerfil.catalogo();// actualiza el
        // reporte
        this.verCatalogo = true;
        this.verActualizar = false;
    }

    public void eventRegistrarPerfiles(ActionEvent event) {
        try {
            this.verNuevo = false;
            PerfilBean perfilBean = new PerfilBean();
            perfilBean.setNombre(this.nombreN);
            perfilBean.setEstado(this.estadoN);
            serviciosPerfil.nuevoPerfil(perfilBean);
            this.perfiles = serviciosPerfil.catalogo();// actualiza el
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

    public void eventNuevo(ActionEvent event) {
        this.limpiar();
        this.verNuevo = true;
        this.verCatalogo = false;

    }

    public List<PerfilBean> getPerfiles() {
        return this.perfiles;
    }

    public void setPerfiles(List<PerfilBean> perfiles) {
        this.perfiles = perfiles;
    }

    public void setServiciosPerfil(IServiciosPerfil serviciosPerfil) {
        this.serviciosPerfil = serviciosPerfil;
    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }
    // list of selected profiles
    protected ArrayList<PerfilBean> selectedProfiles;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = true;
    protected boolean enhancedMultiple;
    private String itemSeleccionado;

    public MBeanPerfiles() {
        selectedProfiles = new ArrayList<PerfilBean>();
        System.out.println("Contructor MBeanProfiles....");
    }

    @PostConstruct
    public void posterior() {
        this.perfiles = serviciosPerfil.catalogo();
        System.out.println("Post Construct");
    }

    /**
     * SelectionListener bound to the ice:rowSelector component. Called when a
     * row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedProfiles.clear();

        /* If application developers rely on validation to control submission of the form or use the result of
         the selection in cascading control set up the may want to defer procession of the event to
         INVOKE_APPLICATION stage by using this code fragment
         if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         event.setPhaseId( PhaseId.INVOKE_APPLICATION );
         event.queue();
         return;
         }

         */

        System.out.println("Tama:" + perfiles.size());
        // build the new selected list
        PerfilBean profilee;
        for (int i = 0, max = perfiles.size(); i < max; i++) {
            profilee = (PerfilBean) perfiles.get(i);
            if (profilee.isSelected()) {
                selectedProfiles.add(profilee);
            }
        }
        for (PerfilBean p : selectedProfiles) {
            System.out.println(p.getCodigo() + "  " + p.getNombre());
        }
        /* If application developers do not rely on validation and want to bypass UPDATE_MODEL and
         INVOKE_APPLICATION stages, they may be able to use the following statement:
         FacesContext.getCurrentInstance().renderResponse();
         to send application to RENDER_RESPONSE phase shortening the app. cycle
         */
    }

    /**
     * Clear the selection list if going from multi select to single select.
     *
     * @param event jsf action event.
     */
    public void changeSelectionMode(ValueChangeEvent event) {

        String newValue = event.getNewValue() != null ? event.getNewValue().toString() : null;
        multiple = false;
        enhancedMultiple = false;
        if ("Single".equals(newValue)) {
            selectedProfiles.clear();

            // build the new selected list
            PerfilBean profilee;
            for (int i = 0, max = perfiles.size(); i < max; i++) {
                profilee = (PerfilBean) perfiles.get(i);
                profilee.setSelected(false);
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

    public void limpiarActualizar() {
        this.codigoA = "";
        this.nombreA = "";
    }

    public void limpiar() {
        this.codigoN = "";
        this.nombreN = "";

    }

    public ArrayList getSelectedProfiles() {
        return selectedProfiles;
    }

    public void setSelectedProfiles(ArrayList selectedProfiles) {
        this.selectedProfiles = selectedProfiles;
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

    public void setPerfilDAO(PerfilDAO perfilDAO) {
        this.perfilDAO = perfilDAO;
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

    public boolean isVerCatalogo() {
        return verCatalogo;
    }

    public void setVerCatalogo(boolean verCatalogo) {
        this.verCatalogo = verCatalogo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isVerActualizar() {
        return verActualizar;
    }

    public void setVerActualizar(boolean verActualizar) {
        this.verActualizar = verActualizar;
    }

    public String getCodigoA() {
        return codigoA;
    }

    public void setCodigoA(String codigoA) {
        this.codigoA = codigoA;
    }

    public String getNombreA() {
        return nombreA;
    }

    public void setNombreA(String nombreA) {
        this.nombreA = nombreA;
    }

    public String getEstadoA() {
        return estadoA;
    }

    public void setEstadoA(String estadoA) {
        this.estadoA = estadoA;
    }

    public String getCodigoN() {
        return codigoN;
    }

    public void setCodigoN(String codigoN) {
        this.codigoN = codigoN;
    }

    public String getNombreN() {
        return nombreN;
    }

    public void setNombreN(String nombreN) {
        this.nombreN = nombreN;
    }

    public String getEstadoN() {
        return estadoN;
    }

    public void setEstadoN(String estadoN) {
        this.estadoN = estadoN;
    }

    @PostConstruct
    public void init() {
        ec = FacesContext.getCurrentInstance().getExternalContext();
        reporte.asignar("ticket.pdf", ec, parametros, "reportes/perfiles.jasper");//se le puede adicionar parametros...
        //reporte.asignar("ticket.pdf", ec, parametros);//se le puede adicionar parametros...
        recursoReport = reporte;
    }

    public RecursoReport getRecursoReport() {
        //parametros.put("P_AREA", "313");		
        parametros.put("P_TITULO", this.empresa);
        //parametros.put("P_LIMITE", this.empresa);
        reporte.asignar("ticket.pdf", ec, parametros, "reportes/perfiles.jasper");//se le puede adicionar parametros...
        //reporte.asignar("ticket.pdf", ec, parametros);//se le puede adicionar parametros...

        recursoReport = reporte;
        return recursoReport;
    }

    public void setRecursoReport(RecursoReport recursoReport) {
        this.recursoReport = recursoReport;
    }

    public void setReporte(RecursoReport reporte) {
        this.reporte = reporte;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getCodigo() {
        return codigo;
    }
}