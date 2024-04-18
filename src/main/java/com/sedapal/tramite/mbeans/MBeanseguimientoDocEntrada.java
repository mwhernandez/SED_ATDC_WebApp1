package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.icesoft.icefaces.tutorial.facelets.NavigationBean;
import com.j2solutionsit.fwk.patterns.jsf.bean.BaseSortableList;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.Seguir;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.EntranteGerenciasDAO;
import com.sedapal.tramite.dao.EntranteGrupoDAO;
import com.sedapal.tramite.dao.SecuencialDAO;
import com.sedapal.tramite.dao.SeguimientoEntranteDAO;
import com.sedapal.tramite.mail.CustomerService;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.nova.util.RecursoReporte;
import com.sedapal.tramite.servicios.IServiciosEntranteGerencias;
import com.sedapal.tramite.servicios.IServiciosEntranteGrupo;
import com.sedapal.tramite.servicios.IServiciosSeguimientoEntrante;
import com.sedapal.tramite.servicios.IServiciosSelection;
import com.sedapal.tramite.tree.TreeController;
import com.sedapal.tramite.util.Utils;

import edu.emory.mathcs.backport.java.util.Collections;

public class MBeanseguimientoDocEntrada extends BaseSortableList implements IMBeanseguimientoDocEntrada, Serializable {

    private boolean verAlertaConfirmacionBorrar;
    private String msg;
    private boolean editarEmpresa = true;
    private MCarga mCarga;
    private NavigationBean navigation;
    private CustomerService customerService;
    private SecuencialDAO secuencialDAO;
    // reporte
    // para el reporte
    private RecursoReport reporte; // inyectado
    private RecursoReport recursoReport;// para displayar
    private RecursoReporte reportes; // inyectado
    private RecursoReporte recursoReporte;// para displayar
    private Map<String, Object> parametros = new HashMap<String, Object>();
    private ExternalContext ec = null;
    private Map<String, Object> parametros2 = new HashMap<String, Object>();
    private ExternalContext ec2 = null;
    Calendar c = Calendar.getInstance();
    // inject
    private TreeController treeController;
    private List<EntranteBean> grupos;    
    private List<EntranteBean> entrantes;
    private List<AreaBean> areas;
    private List<SeguimientoEntranteBean> seguimiento;
    private List<SelectionBean> selection;
    public IServiciosEntranteGrupo serviciosEntranteGrupo;
    public IServiciosEntranteGerencias serviciosEntranteGerencias;
    public IServiciosSelection serviciosselection;
    private List items1;
    private List items2;
    private List items3;
    private List items4;
    private List items5;
    private List items6;
    private List items7;
    private List items9;
    // control de seleccion multiple
    private LinkedHashMap<String, String> items7a = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> items7b = new LinkedHashMap<String, String>();
    private String[] items7aSelected;
    private String[] items7bSelected;
    private List itemsseleccionado;
    private Date fechaIni = new Date();
    private Date fechaFin = new Date();
    private String itemSeleccionado;
    private String item2Seleccionado;
    private String error = "Se Grabó Satisfactoriamente";
    
    private boolean ver = false;
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean cerrarVentanta = false;
    private boolean activo = true;
    

    private boolean verPDF = false;
    private String rutaPDFPopup;
    
    //private boolean disPrint = false;
    // binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    private boolean verDetalles = false;
    private boolean verCatalogoEntrada = false;
    private HtmlInputText botonCodigo;
    // entidades externas
    private String empresa;
    private String login;
    private String date1;
    private String date2;
    private int area_remite;
    String area;
    String area_deriva;
    String codigo_representante;
    String origen;
    int area_derivador;
    int areas_remite;
    int origenes;
    String tipodoc;
    String file2;
    String ruta;
    int ind_adjuntar = 0;
    private String opcion;
    private String detalle;
    private int dirigido;
    String area_dirigido = "";
    String caracter = "-";
    String accion = "";
    String suma_areas;
    int cont = 0;
    int cont2 = 0;
    String nombre_archivo;
    String ubicacion;
    // items seleccionados de las acciones deo doc
    private List<String> selectedItems;// NUEVO
    private List<String> selectedItems1;
    // /Llaves
    private int anollave;
    private int origenllave;
    private String tipodocllave;
    private long correlativollave;
    private String numdocllave;
    private Date fechaplazollave;
    private int diasplazollave;
    private Date fecharecepcionllave;
    private String prioridadllave;
    private String textoA;
    //
    String valores = "";
    int areas_actuales;
    private MBeanSegDocuEntrates mBeanSeguimiento;
    public IServiciosSeguimientoEntrante serviciosseguimientoentrante;
    private EntranteGrupoDAO entranteGrupoDAO;    
    private SeguimientoEntranteDAO seguimientodocumentoDAO;
    private LinkedHashMap<String, Object> observaciones0;
    private List<AreaBean> areasOrigen = new ArrayList<AreaBean>();
    private List<Seguir> seleccionados = Collections.synchronizedList(new ArrayList<Seguir>());
    private List<AreaBean> areasseleccionados = Collections.synchronizedList(new ArrayList<AreaBean>());
    private String estadoSeleccionado = "TO";
    private boolean disBotonGrabar = false;
    private boolean disBotonAgregar = false;
    private boolean disBotonQuitar = false;
    //para quitar areas
    public String quitarareas = "";
    ///Autocompletar Eli Diaz Horna 26/03/2012
    private List<SelectItem> posiblespersonas;
    private String personaSeleccionada;
    private String labelPersona;
    private long ficha = 0;

    public void eventLimpiar(ActionEvent actionEvent) {
        this.items3.clear();
        this.personaSeleccionada = "";
    }

    public void eventEntrada(ActionEvent evt) {
        this.verCatalogoEntrada = true;
        // for (EntranteBean p : selectedEntrantes) {
        // }
    }

    public void cerrarCatalogoEntrada(ActionEvent event) {
        this.verCatalogoEntrada = false;
    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }

    public void setEntrantes(List<EntranteBean> grupos) {
        this.grupos = grupos;
    }

    public List<SelectionBean> getSelection() {
        return selection;
    }

    public void setSelection(List<SelectionBean> selection) {
        this.selection = selection;
    }

    public void setServiciosEntranteGrupo(IServiciosEntranteGrupo serviciosEntranteGrupo) {
        this.serviciosEntranteGrupo = serviciosEntranteGrupo;
    }
    
    

    public void setServiciosEntranteGerencias(IServiciosEntranteGerencias serviciosEntranteGerencias) {
		this.serviciosEntranteGerencias = serviciosEntranteGerencias;
	}

	/**
     * ***********************************************
     */
    public void eventFiltros(ActionEvent event) {
    }

    public void eventRefrescar() {
        String area = this.area;
        selectedItems1.clear();
        this.grupos = entranteGrupoDAO.entrantesSP(area);        
        this.setSortColumnName("ncorrelativo");
        this.setAscending(false);
    }

    public void eventRefrescarCombo(ActionEvent evt) {

        List<RemitenteBean> remitentes = entranteGrupoDAO.remitentes();
        List itemsRemitentes = Utils.getRemitentes(remitentes);
        this.items1 = itemsRemitentes;

    }

    /**
     * ***********************************************
     */
    /**
     * ***********************************************
     */
    public void eventBusquedas(ActionEvent event) {
    	Usuario usuario = null;
        HttpSession session = null;
        session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        usuario = (Usuario) session.getAttribute("sUsuario");
        area = String.valueOf(usuario.getCodarea());

        if (this.detalle.equals("")) {
            this.error = "Debe de Ingresar una opcion de busqueda";
            this.ver = true;
        } else {
        	//this.seguimiento = seguimientodocumentoDAO.BusquedaSP(this.anio, this.origenes, this.correlativos,area, opcion, detalle);
        }


    }

    
    /* Evento visualizar PDF*/
    /*
    public void evenVerPDF(ActionEvent evt) {
         SeguimientoEntranteBean v = null;

         for (SeguimientoEntranteBean r : this.seguimiento) {
             if (r.isSelected()) {
                 v = r;
             }
         }
         
         if (v != null){

            this.rutaPDFPopup = v.getUbicacion_seguimiento(); 
            this.verPDF = true;
        } else {
            this.error = "Debe seleccionar un registro para visualizar el PDF";
            this.ver = true;
            this.verPDF = false;
        }
        
    }
    
    */
    public void evenVerPDF(ActionEvent evt) {
    	if (this.selectedEntrantes.size() == 1) {
    		for (EntranteBean d : selectedEntrantes) {
            this.rutaPDFPopup  = d.getUbicacion_entrada();
    		}
            this.verPDF = true;
        } else {
            this.error = "Debe seleccionar un registro para visualizar el PDF";
            this.ver = true;
            this.verPDF = false;
        }
        
    }
    
    
    
    public void eventRefrescar(ActionEvent evt) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        date1 = sdf.format(this.getFechaIni());
        date2 = sdf.format(this.getFechaFin());
        String estado = "ESTA000";
        // se puede validar fechas
        //if (this.date1.after(this.date2)) {
        if (this.date1.equals(this.date2)) {
            this.error = "Fecha Inicial debe ser menor que la fecha Final";
            this.ver = true;

        } else {
            // System.out.println("Filtrando..");
            // Falta Programar por Eli Diaz
            this.grupos = entranteGrupoDAO.ConsultaDireccionSP(this.date1, this.date2, estado);

        }
    }

    public void eventTipoDoc1(ValueChangeEvent changeEvent) {
        String itemSeleccionado = (String) changeEvent.getNewValue();
        // System.out.println("Hola mmm");
        // System.out.println(itemSeleccionado);
        if (itemSeleccionado.equals("INTERNO")) {
            activo = true;
            // /botonCodigo.setDisabled(false);
        } else {
            activo = false;
            // /botonCodigo.setDisabled(true);
        }
    }
    // list of selected employees
    protected ArrayList<EntranteBean> selectedEntrantes;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanseguimientoDocEntrada() {
        selectedEntrantes = new ArrayList<EntranteBean>();
        // System.out.println("Contructor MBeanProfiles....");
    }

    public void eventoNuevo(ActionEvent evnt) {
        for (EntranteBean p : selectedEntrantes) {
            // System.out.println(p.getArea()+ "   " + p.getNano());
        }
    }

    @PostConstruct
    public void posterior() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        int dirigido = usuario.getCodarea();
        String ncodarea = String.valueOf(usuario.getCodarea());
        area = String.valueOf(usuario.getCodarea());
        // vareaN= usuario.getNomequipo().toUpperCase();

        int area_logon = usuario.getNcodarea();
        // ndirigidoN=this.dirigido;
        // ndirigidoN=313;
        // ndirigidoN=this.dirigido;
        // this.nremitenteN = dirigido;
        // this.ndirigidoN= dirigido;
        // this.vremitenteN = ncodarea;
        // ver eli
        // this.dirigidoN = ncodarea;
        // System.out.println(usuario.getLogin().toUpperCase());
        // System.out.println(usuario.getLogin().toUpperCase());
        //this.grupos = serviciosEntranteGrupo.catalogo(ncodarea);

        //List<AreaBean> areas = entranteGrupoDAO.areas();
        //List itemsAreas = Utils.getAreas(areas);
        //this.items1 = itemsAreas;

        List<TiposBean> tipos = entranteGrupoDAO.tipos();
        List itemstipos = Utils.getTipos(tipos);
        this.items2 = itemstipos;



        List<RemitenteBean> remitentes = entranteGrupoDAO.remitentes();
        List itemsRemitentes = Utils.getRemitentes(remitentes);
        this.items4 = itemsRemitentes;

        List<EstadosBean> estado = entranteGrupoDAO.estados();
        List itemsEstado = Utils.getEstado(estado);
        this.items7 = itemsEstado;

        // String tipo=entranteGrupoDAO.e
        // List itemsAccion = Utils.getAccion(accion);
        // this.items5 = itemsAccion;
        // for (AccionBean a: accion);

        List<TrabajadorBean> trabajador_derivador = entranteGrupoDAO
                .trabajador_derivador(dirigido);
        List itemsTrabajador_derivador = Utils
                .getTrabajador_derivador(trabajador_derivador);
        this.items6 = itemsTrabajador_derivador;

        List<AreaBean> area_derivados = entranteGrupoDAO.areas();
        List itemsderivados = Utils.getAreas(area_derivados);
        this.items9 = itemsderivados;
        // CF V01.00
        //this.items7a = Utils.getAreasLink(area_derivados);
        List<OrigenBean> origen = entranteGrupoDAO.origen();
        List itemsOrigen = Utils.getOrigen(origen);
        this.items5 = itemsOrigen;

    }

    /**
     * SelectionListener bound to the ice:rowSelector component. Called when a
     * row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedEntrantes.clear();

        /*
         * If application developers rely on validation to control submission of
         * the form or use the result of the selection in cascading control set
         * up the may want to defer procession of the event to
         * INVOKE_APPLICATION stage by using this code fragment if
         * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
         * return; }
         */

        // System.out.println("Tama:" + entrantes.size());
        // build the new selected list
        EntranteBean employee;
        for (int i = 0, max = grupos.size(); i < max; i++) {
            employee = (EntranteBean) grupos.get(i);
            if (employee.isSelected()) {
                selectedEntrantes.add(employee);
            }
        }
        for (EntranteBean p : selectedEntrantes) {
            // System.out.println("VER EL DETALLE");
            System.out.println(p.getNano() + " " + p.getNorigen() + " " + p.getDfecdoc());
            // +p.getVtipodoc()+ " "
            // +p.getNcorrelativo()+" "+p.getDfecplazo()+" "+p.getNdiasplazo());
            this.mCarga.setNombrePdf(p.getVubiarchivo());
            this.anollave = p.getNano();
            this.origenllave = p.getNorigen();
            this.tipodocllave = p.getVtipodoc();
            this.correlativollave = p.getNcorrelativo();
            this.numdocllave = p.getVnumdoc();
            this.fechaplazollave = p.getDfecplazo();
            this.diasplazollave = p.getNdiasplazo();
            this.fecharecepcionllave = p.getDfecdoc();
            
            LlavesBean beans = new LlavesBean();
            beans.setAno(this.anollave);
            beans.setOrigen(this.origenllave);
            beans.setTipodoc(this.tipodocllave);
            beans.setCorrelativo(this.correlativollave);
            beans.setNumdoc(this.numdocllave);
            beans.setFechaplazo(this.fechaplazollave);
            beans.setDiasplazo(this.diasplazollave);
            beans.setFecharecepcion(this.fecharecepcionllave);
            HttpSession session = (HttpSession) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getSession(false);
            session.setAttribute("sLlaves", beans);
            List<SeguimientoEntranteBean> catalogo = null;
            catalogo = this.serviciosseguimientoentrante.catalogo(
                    this.anollave, this.origenllave, this.tipodocllave,
                    this.correlativollave, area);
            this.mBeanSeguimiento.setSeguimiento(catalogo);

            this.dirigido = p.getNdirigido();
            // this.dirigidoA = String.valueOf(p.getNdirigido());
            // this.dirigidoA = String.valueOf(p.getNremitente());

            this.treeController.inicializarArbol(String.valueOf(p
                    .getNcorrelativo()), String.valueOf(p.getNano()));
            // this.vestadoA = p.getNestado();
            ruta = p.getVubiarchivo();
            ind_adjuntar = p.getNindicador();

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
            selectedEntrantes.clear();

            // build the new selected list
            EntranteBean employee;
            for (int i = 0, max = grupos.size(); i < max; i++) {
                employee = (EntranteBean) grupos.get(i);
                employee.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = false;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = false;
        }
    }

    public ArrayList getSelectedEntrantes() {
        return selectedEntrantes;
    }

    public void setSelectedEntrantes(ArrayList<EntranteBean> selectedEntrantes) {
        this.selectedEntrantes = selectedEntrantes;
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

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List getItems1() {
        return items1;
    }

    public void setItems1(List items1) {
        this.items1 = items1;
    }

    public List getItems2() {
        return items2;
    }

    public void setItems2(List items2) {
        this.items2 = items2;
    }

    public List getItems3() {
        return items3;
    }

    public List getItems7() {
        return items7;
    }

    public void setItems7(List items7) {
        this.items7 = items7;
    }

    public void setItems3(List items3) {
        this.items3 = items3;
    }

    public List getItems4() {
        return items4;
    }

    public void setItems4(List items4) {
        this.items4 = items4;
    }

    public List getItems5() {
        return items5;
    }

    public List getItems6() {
        return items6;
    }

    public void setItems6(List items6) {
        this.items6 = items6;
    }

    public void setItems5(List items5) {
        this.items5 = items5;
    }

    public List getItems9() {
        return items9;
    }

    public void setItems9(List items9) {
        this.items9 = items9;
    }

    public String getItemSeleccionado() {
        return itemSeleccionado;
    }

    public void setItemSeleccionado(String itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }

    public void setEntranteGrupoDAO(EntranteGrupoDAO entranteGrupoDAO) {
        this.entranteGrupoDAO = entranteGrupoDAO;
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

    public boolean isCerrarVentanta() {
        return cerrarVentanta;
    }

    public void setCerrarVentanta(boolean cerrarVentanta) {
        this.cerrarVentanta = cerrarVentanta;
    }

    public boolean isVerCatalogoEntrada() {
        return verCatalogoEntrada;
    }

    public void setVerCatalogoEntrada(boolean verCatalogoEntrada) {
        this.verCatalogoEntrada = verCatalogoEntrada;
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

    public HtmlSelectOneMenu getCombo() {
        return combo;
    }

    public void setCombo(HtmlSelectOneMenu combo) {
        this.combo = combo;
    }

    public boolean isVerDetalles() {
        return verDetalles;
    }

    public void setVerDetalles(boolean verDetalles) {
        this.verDetalles = verDetalles;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getItem2Seleccionado() {
        return item2Seleccionado;
    }

    public void setItem2Seleccionado(String item2Seleccionado) {
        this.item2Seleccionado = item2Seleccionado;
    }

    public HtmlInputText getBotonCodigo() {
        return botonCodigo;
    }

    public void setBotonCodigo(HtmlInputText botonCodigo) {
        this.botonCodigo = botonCodigo;
    }

    public int getDirigido() {
        return dirigido;
    }

    public void setDirigido(int dirigido) {
        this.dirigido = dirigido;
    }

    public int getArea_derivador() {
        return area_derivador;
    }

    public void setArea_derivador(int areaDerivador) {
        area_derivador = areaDerivador;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List getItemsseleccionado() {
        return itemsseleccionado;
    }

    public void setItemsseleccionado(List itemsseleccionado) {
        this.itemsseleccionado = itemsseleccionado;
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<String> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public int getAnollave() {
        return anollave;
    }

    public void setAnollave(int anollave) {
        this.anollave = anollave;
    }

    public int getOrigenllave() {
        return origenllave;
    }

    public void setOrigenllave(int origenllave) {
        this.origenllave = origenllave;
    }

    public String getTipodocllave() {
        return tipodocllave;
    }

    public void setTipodocllave(String tipodocllave) {
        this.tipodocllave = tipodocllave;
    }

    public void setmBeanSeguimiento(MBeanSegDocuEntrates mBeanSeguimiento) {
        this.mBeanSeguimiento = mBeanSeguimiento;
    }

    public void setServiciosseguimientoentrante(
            IServiciosSeguimientoEntrante serviciosseguimientoentrante) {
        this.serviciosseguimientoentrante = serviciosseguimientoentrante;
    }

    public String getNumdocllave() {
        return numdocllave;
    }

    public void setNumdocllave(String numdocllave) {
        this.numdocllave = numdocllave;
    }

    public LinkedHashMap<String, String> getItems7a() {
        return items7a;
    }

    public void setItems7a(LinkedHashMap<String, String> items7a) {
        this.items7a = items7a;
    }

    public LinkedHashMap<String, String> getItems7b() {
        return items7b;
    }

    public void setItems7b(LinkedHashMap<String, String> items7b) {
        this.items7b = items7b;
    }

    public String[] getItems7aSelected() {
        return items7aSelected;
    }

    public void setItems7aSelected(String[] items7aSelected) {
        this.items7aSelected = items7aSelected;
    }

    public String[] getItems7bSelected() {
        return items7bSelected;
    }

    public void setItems7bSelected(String[] items7bSelected) {
        this.items7bSelected = items7bSelected;
    }

    public LinkedHashMap<String, Object> getObservaciones0() {
        return observaciones0;
    }

    public void setObservaciones(LinkedHashMap<String, Object> observaciones0) {
        this.observaciones0 = observaciones0;
    }

    public void setmCarga(MCarga mCarga) {
        this.mCarga = mCarga;
    }

    public Date getFechaplazollave() {
        return fechaplazollave;
    }

    public void setFechaplazollave(Date fechaplazollave) {
        this.fechaplazollave = fechaplazollave;
    }

    public int getDiasplazollave() {
        return diasplazollave;
    }

    public void setDiasplazollave(int diasplazollave) {
        this.diasplazollave = diasplazollave;
    }

    public List<String> getSelectedItems1() {
        return selectedItems1;
    }

    public void setSelectedItems1(List<String> selectedItems1) {
        this.selectedItems1 = selectedItems1;
    }

    public RecursoReport getReporte() {
        return reporte;
    }

    public void setReporte(RecursoReport reporte) {
        this.reporte = reporte;
    }

    public RecursoReporte getReportes() {
        return reportes;
    }

    public void setReportes(RecursoReporte reportes) {
        this.reportes = reportes;
    }

    public void setRecursoReport(RecursoReport recursoReport) {
        this.recursoReport = recursoReport;
    }

    public void setRecursoReportes(RecursoReporte recursoReporte) {
        this.recursoReporte = recursoReporte;
    }

    public ExternalContext getEc() {
        return ec;
    }

    public void setEc(ExternalContext ec) {
        this.ec = ec;
    }

    public void setTreeController(TreeController treeController) {
        this.treeController = treeController;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setSecuencialDAO(SecuencialDAO secuencialDAO) {
        this.secuencialDAO = secuencialDAO;
    }

    public void setNavigation(NavigationBean navigation) {
        this.navigation = navigation;
    }

    public boolean isEditarEmpresa() {
        return editarEmpresa;
    }

    public void setEditarEmpresa(boolean editarEmpresa) {
        this.editarEmpresa = editarEmpresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public long getCorrelativollave() {
        return correlativollave;
    }

    public void setCorrelativollave(long correlativollave) {
        this.correlativollave = correlativollave;
    }

    public String getTextoA() {
        return textoA;
    }

    public void setTextoA(String textoA) {
        this.textoA = textoA;
    }

    public boolean isDisBotonGrabar() {
        return disBotonGrabar;
    }

    public void setDisBotonGrabar(boolean disBotonGrabar) {
        this.disBotonGrabar = disBotonGrabar;
    }

    public boolean isVerAlertaConfirmacionBorrar() {
        return verAlertaConfirmacionBorrar;
    }

    public void setVerAlertaConfirmacionBorrar(boolean verAlertaConfirmacionBorrar) {
        this.verAlertaConfirmacionBorrar = verAlertaConfirmacionBorrar;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getFecharecepcionllave() {
        return fecharecepcionllave;
    }

    public void setFecharecepcionllave(Date fecharecepcionllave) {
        this.fecharecepcionllave = fecharecepcionllave;
    }

    public List<EntranteBean> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<EntranteBean> grupos) {
        this.grupos = grupos;
    }

    public List<Seguir> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<Seguir> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public List<AreaBean> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaBean> areas) {
        this.areas = areas;
    }

    public List<AreaBean> getAreasseleccionados() {
        return areasseleccionados;
    }

    public void setAreasseleccionados(List<AreaBean> areasseleccionados) {
        this.areasseleccionados = areasseleccionados;
    }

    public boolean isDisBotonAgregar() {
        return disBotonAgregar;
    }

    public void setDisBotonAgregar(boolean disBotonAgregar) {
        this.disBotonAgregar = disBotonAgregar;
    }

    public boolean isDisBotonQuitar() {
        return disBotonQuitar;
    }

    public void setDisBotonQuitar(boolean disBotonQuitar) {
        this.disBotonQuitar = disBotonQuitar;
    }

    public List<SelectItem> getPosiblespersonas() {
        return posiblespersonas;
    }

    public void setPosiblespersonas(List<SelectItem> posiblespersonas) {
        this.posiblespersonas = posiblespersonas;
    }

    public String getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(String personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public String getLabelPersona() {
        return labelPersona;
    }

    public void setLabelPersona(String labelPersona) {
        this.labelPersona = labelPersona;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public List<EntranteBean> getEntrantes() {
        return entrantes;
    }

	public String getPrioridadllave() {
		return prioridadllave;
	}

	public void setPrioridadllave(String prioridadllave) {
		this.prioridadllave = prioridadllave;
	}

	public boolean isVerPDF() {
		return verPDF;
	}

	public void setVerPDF(boolean verPDF) {
		this.verPDF = verPDF;
	}

	public String getRutaPDFPopup() {
		return rutaPDFPopup;
	}

	public void setRutaPDFPopup(String rutaPDFPopup) {
		this.rutaPDFPopup = rutaPDFPopup;
	}
    
}
