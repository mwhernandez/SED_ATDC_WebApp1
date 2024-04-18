package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.dao.TrabajadorDAO;

import com.sedapal.tramite.servicios.IServiciosTrabajador;

public class MBeanTrabajador implements IMBeanTrabajador, Serializable {

    private List<TrabajadorBean> trabajador;
    public IServiciosTrabajador serviciosTrabajador;
    private List items;
    private String itemSeleccionado;
    private boolean ver = false;
    private String error = "";
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean verDetalles = false;
    // para el formulario nuevo
    private int codigo;
    private int nficha;
    private String nombre;
    private String apellidopaterno;
    private String apellidomaterno;
    private String nombre_completo;
    private int anexo;
    private int onomastico;
    private int dia;
    private String mes;
    private String cargo;
    private String correo;
    private int area;
    private String equipo;
    private int ncodlocal;
    private String local;
    private String tipo;
    // para el formulario nuevo
    private int codigoA;
    private long fichaA;
    private String nombreA;
    private String apellidopaternoA;
    private String apellidomaternoA;
    private String nombre_completoA;
    private int anexoA;
    private int onomasticoA;
    private int diaA;
    private String mesA;
    private String cargoA;
    private String correoA;
    private int areaA;
    private String equipoA;
    private int ncodlocalA;
    private String localA;
    private String tipoA;
    // para el filtro 
    private String opcion;
    private String detalle;
    //binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    private TrabajadorDAO trabajadorDAO;

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        if (this.selectedTrabajadorr.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;

            for (TrabajadorBean t : selectedTrabajadorr) {

                this.codigoA = t.getCodigo();
                this.nombreA = t.getNombre();
                this.apellidopaternoA = t.getApellidopaterno();
                this.apellidomaternoA = t.getApellidomaterno();
                this.onomasticoA = t.getOnomastico();
                this.mesA = t.getMes();
                this.fichaA = t.getFicha();
                this.cargoA = t.getCargo();
                this.equipoA = t.getEquipo();
                this.anexoA = t.getAnexo();
                this.tipoA = t.getTipo();
                this.correoA = t.getCorreo();
                this.localA = t.getLocal();
                //this.abreviaturaA = c.getAbreviatura();
                //this.direccionA=c.getDireccion();
                //this.estadoA = c.getEstado();				

            }
            selectedTrabajadorr.clear();
            this.trabajador = serviciosTrabajador.catalogo();// actualiza el reporte
        } else {
            this.error = "Debe seleccionar solo un registro";
            this.ver = true;

        }
    }

    public void eventCancelar(ActionEvent event) {
        this.verCatalogo = true;
        this.verNuevo = false;
        this.verActualizar = false;
    }
    
    public void eventRefrescar(ActionEvent event) {
        this.verCatalogo = true;
        this.trabajador = serviciosTrabajador.catalogo();        
        this.detalle = "";

    }

    public void cerrarDetalles(ActionEvent event) {
        this.verDetalles = false;

    }

    public List<TrabajadorBean> getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(List<TrabajadorBean> trabajador) {
        this.trabajador = trabajador;
    }

    public void setServiciosTrabajador(IServiciosTrabajador serviciosTrabajador) {
        this.serviciosTrabajador = serviciosTrabajador;

    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }

    public void eventFiltros(ActionEvent event) {
        //this.ver = true;	
        System.out.println("Filtrando..");
        this.trabajador = trabajadorDAO.filtrosSP(opcion, detalle);
        System.out.println(opcion + "  " + detalle);
    }
    /**
     * ***********************************************
     */
    // list of selected employees
    protected ArrayList<TrabajadorBean> selectedTrabajadorr;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = true;
    protected boolean enhancedMultiple;

    public MBeanTrabajador() {
        selectedTrabajadorr = new ArrayList<TrabajadorBean>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (TrabajadorBean t : selectedTrabajadorr) {
            System.out.println(t.getCodigo() + "   " + t.getNombre());
        }
    }

    @PostConstruct
    public void posterior() {
        this.trabajador = serviciosTrabajador.catalogo();
        //System.out.println("Post Construct, cargo catalogo...size="+this.centro.size());
        //System.out.println("Contructor MBeanCentros....");
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
        selectedTrabajadorr.clear();

        /*
         * If application developers rely on validation to control submission of
         * the form or use the result of the selection in cascading control set
         * up the may want to defer procession of the event to
         * INVOKE_APPLICATION stage by using this code fragment if
         * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
         * return; }
         */

        System.out.println("Tama:" + trabajador.size());
        // build the new selected list
        TrabajadorBean employee;
        for (int i = 0, max = trabajador.size(); i < max; i++) {
            employee = (TrabajadorBean) trabajador.get(i);
            if (employee.isSelected()) {
                selectedTrabajadorr.add(employee);
            }
        }
        for (TrabajadorBean t : selectedTrabajadorr) {
            System.out.println(t.getCodigo() + "  " + t.getNombre());
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
            selectedTrabajadorr.clear();

            // build the new selected list
            TrabajadorBean employee;
            for (int i = 0, max = trabajador.size(); i < max; i++) {
                employee = (TrabajadorBean) trabajador.get(i);
                employee.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = true;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = true;
        }
    }

    public void limpiar() {
        this.codigo = 0;
        this.nombre = "";


    }

    public void limpiarActualizar() {
        this.codigoA = 0;
        this.nombreA = "";


    }

    public ArrayList getselectedTrabajadorr() {
        return selectedTrabajadorr;
    }

    public void setselectedTrabajadorr(ArrayList selectedTrabajadorr) {
        this.selectedTrabajadorr = selectedTrabajadorr;
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

    public void setTrabajadorDAO(TrabajadorDAO trabajadorDAO) {
        this.trabajadorDAO = trabajadorDAO;
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

    public boolean isVerActualizar() {
        return verActualizar;
    }

    public void setVerActualizar(boolean verActualizar) {
        this.verActualizar = verActualizar;
    }

    public int getCodigoA() {
        return codigoA;
    }

    public void setCodigoA(int codigoA) {
        this.codigoA = codigoA;
    }

    public long getFichaA() {
        return fichaA;
    }

    public void setFichaA(long fichaA) {
        this.fichaA = fichaA;
    }

    public String getNombreA() {
        return nombreA;
    }

    public void setNombreA(String nombreA) {
        this.nombreA = nombreA;
    }

    public String getApellidopaternoA() {
        return apellidopaternoA;
    }

    public void setApellidopaternoA(String apellidopaternoA) {
        this.apellidopaternoA = apellidopaternoA;
    }

    public String getApellidomaternoA() {
        return apellidomaternoA;
    }

    public void setApellidomaternoA(String apellidomaternoA) {
        this.apellidomaternoA = apellidomaternoA;
    }

    public String getNombre_completoA() {
        return nombre_completoA;
    }

    public void setNombre_completoA(String nombreCompletoA) {
        nombre_completoA = nombreCompletoA;
    }

    public int getAnexoA() {
        return anexoA;
    }

    public void setAnexoA(int anexoA) {
        this.anexoA = anexoA;
    }

    public int getOnomasticoA() {
        return onomasticoA;
    }

    public void setOnomasticoA(int onomasticoA) {
        this.onomasticoA = onomasticoA;
    }

    public int getDiaA() {
        return diaA;
    }

    public void setDiaA(int diaA) {
        this.diaA = diaA;
    }

    public String getMesA() {
        return mesA;
    }

    public void setMesA(String mesA) {
        this.mesA = mesA;
    }

    public String getCargoA() {
        return cargoA;
    }

    public void setCargoA(String cargoA) {
        this.cargoA = cargoA;
    }

    public String getCorreoA() {
        return correoA;
    }

    public void setCorreoA(String correoA) {
        this.correoA = correoA;
    }

    public int getAreaA() {
        return areaA;
    }

    public void setAreaA(int areaA) {
        this.areaA = areaA;
    }

    public String getEquipoA() {
        return equipoA;
    }

    public void setEquipoA(String equipoA) {
        this.equipoA = equipoA;
    }

    public int getNcodlocalA() {
        return ncodlocalA;
    }

    public void setNcodlocalA(int ncodlocalA) {
        this.ncodlocalA = ncodlocalA;
    }

    public String getLocalA() {
        return localA;
    }

    public void setLocalA(String localA) {
        this.localA = localA;
    }

    public String getTipoA() {
        return tipoA;
    }

    public void setTipoA(String tipoA) {
        this.tipoA = tipoA;
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
}
