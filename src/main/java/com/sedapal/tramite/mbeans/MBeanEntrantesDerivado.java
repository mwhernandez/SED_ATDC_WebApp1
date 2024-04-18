package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.icesoft.icefaces.tutorial.facelets.NavigationBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.Seguir;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.EntranteDerivadoDAO;
import com.sedapal.tramite.dao.SecuencialDAO;
import com.sedapal.tramite.dao.SeguimientoEntranteDAO;
import com.sedapal.tramite.mail.CustomerService;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.servicios.IServiciosEntranteDerivado;
import com.sedapal.tramite.servicios.IServiciosSeguimientoEntrante;
import com.sedapal.tramite.servicios.IServiciosSelection;
import com.sedapal.tramite.tree.TreeController;
import com.sedapal.tramite.tree.TreeControllerDerivador;
import com.sedapal.tramite.util.Utils;

public class MBeanEntrantesDerivado implements IMBeanEntrantesDerivado, Serializable {

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
    private Map<String, Object> parametros = new HashMap<String, Object>();
    private ExternalContext ec = null;
    Calendar c = Calendar.getInstance();
    // inject
    private TreeController treeController;
    private TreeControllerDerivador treeControllerDerivador;
    private List<EntranteBean> entrantes;
    private List<SeguimientoEntranteBean> seguimiento;
    private List<SelectionBean> selection;
    public IServiciosEntranteDerivado serviciosEntranteDerivado;
    public IServiciosSelection serviciosselection;
    private List items1;
    private List items2;
    private List items3;
    private List items4;
    private List items5;
    private List items6;
    private List items7;
    private List items8;
    private List items9;
    //control de seleccion multiple
    private LinkedHashMap<String, String> items7a = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> items7b = new LinkedHashMap<String, String>();
    private String[] items7aSelected;
    private String[] items7bSelected;
    private List itemsseleccionado;
    private Date date1 = new Date();
    private Date date2 = new Date();
    private String itemSeleccionado;
    private String item2Seleccionado;
    private String error = "Se grabó Satisfactoriamente";
    private String mensaje = "Transacción no Valida";
    // para el formulario nuevo
    private int nanoN;
    private String origenN;
    private String tipodocN;
    private long ncorrelativoN;
    private String vnumdocN;
    private String vmesaparteN;
    private int nremitenteN;
    private String vremitenteN;
    private String dirigidoN;
    private Date dfecdocN = new Date();
    private Date dfecregistroN = new Date();
    private String vasuntoN;
    private String vreferenciaN;
    private String vobservacionN;
    private String vaccionN;
    private String vprioridadN;
    private String vubiarchivoN;
    private Date dfecplazoN = new Date();
    private int ndiasplazoN;
    private String vestadoN;
    private int nindicadorN;
    private Date dfeccreN = new Date();
    private Date dfecactN = new Date();
    private String vrescreN;
    private String vresactN;
    private int ncodcentroN;
    private int ncodareaN;
    private long ficha_dirigidoN;
    private String vareaN;
    private String area_origenN;
    private String centroN;
    private int ncodarea_origenN;
    private int ndirigidoN;
    private long ficha_derivadoN;
    private String nrepresentanteN;
    private String areaseleccionadaN;
    private String sistemaN="SEDAPAL";
    private int nfolioN;
    private String vnumero_loteN;
	private String vnumero_discoN;
	private Date dfecdiscoN;
    // para Actualizar
    private int nanoA;
    private int norigenA;
    private String origenA;
    private String tipodocA;
    private long ncorrelativoA;
    private String vnumdocA;
    private String vmesaparteA;
    private int nremitenteA;
    private String vremitenteA;
    private int ndirigidoA;
    private Date dfecdocA;
    private Date dfecregistroA;
    private String vasuntoA;
    private String vreferenciaA;
    private String vobservacionA;
    private String vaccionA;
    private String vprioridadA;
    private String vubiarchivoA;
    private Date dfecplazoA;
    private int ndiasplazoA;
    private String vestadoA;
    private int nindicadorA;
    private Date dfeccreA;
    private Date dfecactA;
    private String vrescreA;
    private String vresactA;
    private int ncodcentroA;
    private int ncodareaA;
    private long ficha_dirigidoA;
    private String vareaA;
    private String dirigidoA;
    private String area_origenA;
    private String centroA;
    private int ncodarea_origenA;
    private long ficha_derivadoA;
    private int nrepresentanteA;
    private String areaseleccionadaA;
    private String vtipoA;
    private String sistemaA;
    private int nfolioA;
    private String vnumero_loteA;
	private String vnumero_discoA;
	private Date dfecdiscoA;
    ///////////////////
    private boolean ver = false;
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean cerrarVentanta = false;
    private boolean activo = true;
    //binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    private boolean verDetalles = false;
    private boolean verCatalogoEntrada = false;
    private HtmlInputText botonCodigo;
    
    private boolean verPDF = false;
    private String rutaPDFPopup;
    
    // entidades externas
    private String empresa;
    private String login;
    private int area_remite;
    private String area;
    private int ficha;
    private String area_deriva;
    private String codigo_representante;
    private String origen;
    private int area_derivador;
    private String tipodoc;
    private String file2;
    private String ruta;
    private int ind_adjuntar = 0;
    private String opcion;
    private String detalle;
    private int dirigido;
    private String area_dirigido = "";
    private String caracter = "-";
    private String accion = "";
    private String suma_areas;
    private int cont = 0;
    private int cont2 = 0;
    private String nombre_archivo;
    private String ubicacion;
    //items seleccionados de las acciones deo doc
    private List<String> selectedItems;//NUEVO
    private List<String> selectedItems1;
    ///Llaves
    private int anollave;
    private int origenllave;
    private String tipodocllave;
    private long correlativollave;
    private String numdocllave;
    private Date fechaplazollave;
    private int diasplazollave;
    private Date fecharecepcionllave;
    private String textoA;
    private String prioridadllave;
    String valores = "";
    int areas_actuales;
    private MBeanSegDocuEntrates mBeanSeguimiento;
    public IServiciosSeguimientoEntrante serviciosseguimientoentrante;
    private EntranteDerivadoDAO entranteDerivadoDAO;
    private SeguimientoEntranteDAO seguimientodocumentoDAO;
    private LinkedHashMap<String, Object> observaciones0;
    private List<Seguir> seleccionados = Collections.synchronizedList(new ArrayList<Seguir>());
    private List<AreaBean> areasseleccionados = Collections.synchronizedList(new ArrayList<AreaBean>());
    private String estadoSeleccionado = "TO";
    //para quitar areas
    public String quitarareas = "";
    private List<AreaBean> areas;
    private List<AreaBean> areasOrigen = new ArrayList<AreaBean>();
    private boolean disBotonGrabar = false;

    @PostConstruct
    public void carga() {
        selectedItems1 = new ArrayList<String>();
        observaciones0 = new LinkedHashMap<String, Object>();
        observaciones0.put("01.-Por Disposición", "11");
        observaciones0.put("02.-Coordinar Con", "12");
        observaciones0.put("03.-Acción Necesaria", "13");
        observaciones0.put("04.-Adjuntar Antecedentes", "14");
        observaciones0.put("05.-Revisar/Informar", "15");
        observaciones0.put("06.-Preparar Respuesta", "16");
        observaciones0.put("07.-Conocimientos y Fines", "17");
        observaciones0.put("08.-Su aprobación", "18");
        observaciones0.put("09.-Tener Pendiente", "19");
        observaciones0.put("10.-Resolver", "20");
        observaciones0.put("11.-Archivar", "21");
        observaciones0.put("12.-Para Directorio", "22");
        observaciones0.put("13.-Para  trámite", "23");
        observaciones0.put("14.-Contestar directamente", "24");
        observaciones0.put("15.-Otros", "25");

        // Reporte
        reporte.asignar("ticket.pdf", ec, parametros, "reportes/blanco.jasper");//
        recursoReport = reporte;


    }

    public void eventoTab(TabChangeEvent event) {
        if (event.getNewTabIndex() == 1 || event.getNewTabIndex() == 2 && this.selectedEntrantes.size() == 1) {
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

            //System.out.println("Tama:" + entrantes.size());
            // build the new selected list
            EntranteBean employee;
            for (int i = 0, max = entrantes.size(); i < max; i++) {
                employee = (EntranteBean) entrantes.get(i);
                if (employee.isSelected()) {
                    selectedEntrantes.add(employee);
                }
            }
            for (EntranteBean p : selectedEntrantes) {
                //System.out.println("VER EL DETALLE");
                //System.out.println(p.getNano() + " " + p.getNorigen() + " " + p.getVtipodoc() + " " + p.getNcorrelativo() + " " + p.getDfecplazo() + " " + p.getNdiasplazo());
                this.mCarga.setNombrePdf(p.getVubiarchivo());
                this.anollave = p.getNano();
                this.origenllave = p.getNorigen();
                this.tipodocllave = p.getVtipodoc();
                this.correlativollave = p.getNcorrelativo();
                this.numdocllave = p.getVnumdoc();
                this.fechaplazollave = p.getDfecplazo();
                this.diasplazollave = p.getNdiasplazo();
                this.fecharecepcionllave = p.getDfecregistro();
                this.prioridadllave = p.getVprioridad();
                
                LlavesBean beans = new LlavesBean();
                beans.setAno(this.anollave);
                beans.setOrigen(this.origenllave);
                beans.setTipodoc(this.tipodocllave);
                beans.setCorrelativo(this.correlativollave);
                beans.setNumdoc(this.numdocllave);
                beans.setFechaplazo(this.fechaplazollave);
                beans.setDiasplazo(this.diasplazollave);
                beans.setFecharecepcion(this.fecharecepcionllave);
                beans.setPrioridad(this.prioridadllave);
                System.out.println("Pinto las llaves que paso");
                System.out.println(this.correlativollave);
                System.out.println(this.prioridadllave);
                
                
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("sLlaves", beans);
                List<SeguimientoEntranteBean> catalogo = null;
                catalogo = this.serviciosseguimientoentrante.catalogo(this.anollave, this.origenllave, this.tipodocllave, this.correlativollave, area);
                this.mBeanSeguimiento.setSeguimiento(catalogo);
                this.ndirigidoA = p.getNdirigido();
                this.dirigido = p.getNdirigido();
                //this.dirigidoA = String.valueOf(p.getNdirigido());
                //this.dirigidoA = String.valueOf(p.getNremitente());
                this.nremitenteA = p.getNremitente();
                this.ficha_dirigidoA = p.getFicha_dirigido();
                this.ficha_derivadoA = p.getFicha_derivado();
                this.treeControllerDerivador.inicializarArbol(String.valueOf(p.getNcorrelativo()), String.valueOf(p.getNano()));
                //this.vestadoA = p.getNestado();
                ruta = p.getVubiarchivo();
                ind_adjuntar = p.getNindicador();
                this.vubiarchivoA = p.getVubiarchivo();
            }
            /*
             * If application developers do not rely on validation and want to
             * bypass UPDATE_MODEL and INVOKE_APPLICATION stages, they may be able
             * to use the following statement:
             * FacesContext.getCurrentInstance().renderResponse(); to send
             * application to RENDER_RESPONSE phase shortening the app. cycle
             */
        }
    }

    // ADD CF AGO 2011 REPORT
    public void eventoTab() {
        EntranteBean bean = null;
        // System.out.println("Estoy aqui Eli Diaz");

        for (EntranteBean p : this.entrantes) {
            if (p.isSelected()) {
                bean = p;
            }
        }
        // if (event.getNewTabIndex() == 1 || event.getNewTabIndex() == 2
        // && bean != null) {
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
        for (int i = 0, max = entrantes.size(); i < max; i++) {
            employee = (EntranteBean) entrantes.get(i);
            if (employee.isSelected()) {
                selectedEntrantes.add(employee);
            }
        }
        for (EntranteBean p : selectedEntrantes) {
            // System.out.println("VER EL DETALLE");
            // System.out.println(p.getNano() + " " +p.getNorigen()+ " "
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
            this.fecharecepcionllave = p.getDfecregistro();
            this.prioridadllave = p.getVprioridad();
            
            LlavesBean beans = new LlavesBean();
            beans.setAno(this.anollave);
            beans.setOrigen(this.origenllave);
            beans.setTipodoc(this.tipodocllave);
            beans.setCorrelativo(this.correlativollave);
            beans.setNumdoc(this.numdocllave);
            beans.setFechaplazo(this.fechaplazollave);
            beans.setDiasplazo(this.diasplazollave);
            beans.setFecharecepcion(this.fecharecepcionllave);
            beans.setPrioridad(this.prioridadllave);
            
            HttpSession session = (HttpSession) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getSession(false);
            session.setAttribute("sLlaves", beans);
            List<SeguimientoEntranteBean> catalogo = null;
            catalogo = this.serviciosseguimientoentrante.catalogo(
                    this.anollave, this.origenllave, this.tipodocllave,
                    this.correlativollave, area);
            this.mBeanSeguimiento.setSeguimiento(catalogo);
            this.ndirigidoA = p.getNdirigido();
            this.dirigido = p.getNdirigido();
            // this.dirigidoA = String.valueOf(p.getNdirigido());
            // this.dirigidoA = String.valueOf(p.getNremitente());
            this.nremitenteA = p.getNremitente();
            this.ficha_dirigidoA = p.getFicha_dirigido();
            this.ficha_derivadoA = p.getFicha_derivado();
            //this.treeController.inicializarArbol(
            //		String.valueOf(p.getNcorrelativo()),
            //		String.valueOf(p.getNano()));
            // this.vestadoA = p.getNestado();
            ruta = p.getVubiarchivo();
            this.ind_adjuntar = p.getNindicador();
            this.vubiarchivoA = p.getVubiarchivo();
            // this.eventRefrescar();

        }
        /*
         * If application developers do not rely on validation and want to
         * bypass UPDATE_MODEL and INVOKE_APPLICATION stages, they may be able
         * to use the following statement:
         * FacesContext.getCurrentInstance().renderResponse(); to send
         * application to RENDER_RESPONSE phase shortening the app. cycle
         */

        // }
    }

    public void eventEliminar(ActionEvent event) {

        if (this.selectedEntrantes.size() < 1) {
            this.error = "Debe seleccionar por lo menos un registro";
            this.ver = true;
        } else {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            /////guardando en sesion un objeto
            String login = "";
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            login = usuario.getLogin();
            // llamamos a servicios o dao pasandole los datos a eliminar
            String codigo = "";
            String anno = "";
            String origen = "";
            String tipodoc = "";
            String ncodarea = "";

            for (EntranteBean p : selectedEntrantes) {
                anno = String.valueOf(p.getNano());
                origen = String.valueOf(p.getNorigen());
                tipodoc = p.getVtipodoc();
                ncodarea = String.valueOf(p.getNcodarea());
                codigo = String.valueOf(p.getNcorrelativo());

                // le paso como parametro al stored
                serviciosEntranteDerivado.eliminarEntrante(anno, origen, tipodoc, codigo, login);
            }
            this.error = "Registro Eliminado!";
            selectedEntrantes.clear();
            this.entrantes = serviciosEntranteDerivado.catalogo(ncodarea, ficha);
            this.ver = true;
        }
    }

    public void pasarItemsA(ActionEvent actionEvent) {
        AreaBean area = new AreaBean();
        //llenar el objeto seguir....		
        area.setTipo(this.estadoSeleccionado);
        area.setNombre(this.dirigidoA);
        area.setCodigo(Integer.parseInt(this.dirigidoA));
        SelectItem item = null;
        if (this.estadoSeleccionado != "") {
            //adiconando el objeto en la tabla
            boolean ok = false;
            ok = this.buscarA(area, this.areasseleccionados);	//cambiar algoritmo	
            if (!ok) {
                for (int i = 0; i < this.items9.size(); i++) {
                    item = (SelectItem) this.items9.get(i);
                    String areas = item.getValue().toString();
                    if (areas.equals(this.dirigidoA)) {
                        area.setNombre(item.getLabel());
                        area.setCodigo(Integer.parseInt(this.dirigidoA));
                        this.areasseleccionados.add(area);

                        //seguir.setNombreTrabajador(item.getLabel());
                        //	this.seleccionados.add(seguir);
                    }
                }
            }
        } else {
            this.error = "Seleccione un tipo Destinatario!!";
            this.ver = true;

        }
    }

    public boolean buscarA(AreaBean area, List<AreaBean> list) {
        boolean ok = false;
        for (AreaBean p : list) {
            if (p.getTipo().equals(area.getTipo()) && p.getNombre().equals(area.getNombre())) {
                ok = true;
            }
        }
        return ok;
    }

    public void quitarItemsA(ActionEvent actionEvent) {
        //quita elemento seleccionado de la tabla
        AreaBean area = null;
        for (AreaBean p : this.areasseleccionados) {
            if (p.isSelected()) {
                area = p;
                quitarareas += area.getCodigo();
                //areasTemporal+=p;
            }
        }
        if (area == null) {
            this.error = "Seleccione item.";
            this.ver = true;
        } else {
            this.areasseleccionados.remove(area);
        }

    }

    public void eventActualizar(ActionEvent event) {
        //this.limpiarActualizar();
              
    	
        EntranteBean p = null;
        for (EntranteBean q : this.entrantes) {
            if (q.isSelected()) {
                p = q;
            }
        }
        
        System.out.println("Estoy viendo si es nulo");
        System.out.println(this.entrantes.size());
        
        if (p != null) {
                
            this.verActualizar = true;
            this.verCatalogo = false;        
            this.nanoA = p.getNano();
            this.origenA = String.valueOf(p.getNorigen());
            this.norigenA = p.getNorigen();
            this.tipodocA = p.getVtipodoc();
            this.ncorrelativoA = p.getNcorrelativo();
            this.vnumdocA = p.getVnumdoc();
            this.vmesaparteA = p.getVmesaparte();
            //System.out.println("Remitente!!!!!:" + p.getNcodarea());
            this.vremitenteA = String.valueOf(p.getNcodarea());
            //System.out.println("Dirigido!!!!!:" + p.getNdirigido());
            this.dirigidoA = String.valueOf(p.getNdirigido());
            //this.areaseleccionadaA = p.getVremitente();
            //this.items7aSelected=p.getVremitente();
            this.ndirigidoA = p.getNdirigido();
            this.dfecdocA = p.getDfecdoc();
            this.dfecregistroA = p.getDfecregistro();
            this.vasuntoA = p.getVasunto();
            this.vreferenciaA = p.getVreferencia();
            this.vobservacionA = p.getVobservacion();
            this.vaccionA = p.getVaccion();
            
            System.out.println("quiero ver la prioridad");
            System.out.println(p.getVprioridad());            
            this.vprioridadA = p.getVprioridad();
            
            this.vubiarchivoA = p.getVubiarchivo();
            this.dfecplazoA = p.getDfecplazo();
            this.ndiasplazoA = p.getNdiasplazo();
            this.vestadoA = p.getNestado();
            this.nindicadorA = p.getNindicador();
            this.dfeccreA = p.getDfeccre();
            this.dfecactA = p.getDfecact();
            this.vrescreA = p.getVrescre();
            this.vresactA = p.getVresact();
            this.ncodcentroA = p.getNcodcentro();
            this.ncodareaA = p.getNcodarea();
            this.vareaA = p.getArea();
            this.area_origenA = p.getArea_origen();
            this.centroA = p.getCentro();
            this.ficha_derivadoA = p.getFicha_derivado();
            this.ficha_derivadoA = p.getNremitente();
            this.ficha_dirigidoA = p.getFicha_dirigido();
            //System.out.println("Ficha dirigido:" + p.getFicha_dirigido());
            //linea insertada por CF
            this.ncodarea_origenA = p.getNcodarea_origen();
            //System.out.println("Cod Area Orgine!!!!: " + this.ncodarea_origenA);
            this.anollave = this.nanoA;
            this.origenllave = this.norigenA;
            this.tipodocllave = this.tipodocA;
            this.correlativollave = this.ncorrelativoA;
            this.sistemaA = p.getSistema();
            this.nfolioA = p.getNfolio();
            this.vnumero_loteA = p.getVlote();
			this.dfecdiscoA = p.getDfecdisco();
			this.vnumero_discoA = p.getVnumero_disco();
            //
            List<AreaBean> areas = entranteDerivadoDAO.areas_seleccionadas(
                    this.anollave, this.origenllave, this.tipodocllave,
                    this.correlativollave);
            //Add CF 05/05/2011
            List<AreaBean> area_derivados = entranteDerivadoDAO.areas();
            this.items7a = Utils.getAreasLink(area_derivados);

            areasOrigen = entranteDerivadoDAO.areas_seleccionadasOrigen(
                    this.anollave, this.origenllave, this.correlativollave,
                    this.ncodarea_origenA);
            //int tipo_area=Integer.parseInt(this.vremitenteA);

            //Aóade Eli Diaz
            this.areas = areas;
            this.areasseleccionados = areas;
            //System.out.println("Areas Internas Eli");
            //System.out.println(this.vremitenteA);
            int tipo_area = Integer.parseInt(this.dirigidoA);
            this.items7b = Utils.getAreasLink(areas);
            //this.items7b= Utils.getAreasLink(entranteDerivadoDAO.areas_seleccionadas(this.anollave,this.origenllave,this.tipodocllave,this.correlativollave));
            //llena las acciones
            String codigo_accion = this.vaccionA;
            String tempo = null;
            int i = 0, j = 0;
            while (i < codigo_accion.length()) {
                tempo = codigo_accion.substring(i, i + 2);
                //asigna al arreglo
                selectedItems1.add(tempo);
                j++;
                i += 2;
            }
            //this.observaciones=Utils.getAreasLink(entranteDAO.areas_seleccionadas(this.anollave,this.origenllave,this.tipodocllave,this.correlativollave));			
            if (this.norigenA == 1) {
                List<AreaBean> areass = entranteDerivadoDAO.areas();
                List itemsAreas = Utils.getAreas(areass);
                this.items1 = itemsAreas;

                List<TrabajadorBean> trabajador = entranteDerivadoDAO.trabajador(Integer.parseInt(this.dirigidoA));
                List itemsTrabajador = Utils.getTrabajador(trabajador);
                this.items3 = itemsTrabajador;


            } else {
                if (tipo_area < 601) {
                    List<AreaBean> areass = entranteDerivadoDAO.areas();
                    List itemsAreas = Utils.getAreas(areass);
                    this.items1 = itemsAreas;

                    List<TrabajadorBean> trabajador = entranteDerivadoDAO.trabajador(Integer.parseInt(this.dirigidoA));
                    List itemsTrabajador = Utils.getTrabajador(trabajador);
                    this.items3 = itemsTrabajador;
                    
                    //System.out.println("Paso por Areas Internas");

                } else {
                    List<RemitenteBean> remitentes = entranteDerivadoDAO.remitentes();
                    List itemsderivados = Utils.getRemitentes(remitentes);
                    this.items1 = itemsderivados;


                    List<RepresentanteBean> representante = entranteDerivadoDAO.representante(Integer.parseInt(this.dirigidoA));
                    List itemsrepresentante = Utils.getRepresentante(representante);
                    this.items3 = itemsrepresentante;
                    
                    //System.out.println("Paso por Areas Externas");

                }


            }


            selectedEntrantes.clear();
            this.mensaje = "Se grabó satisfactoriamente";
            ///acediendo a sesion http
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            /////guardando en sesion un objeto
            String ncodarea = "";
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            area = String.valueOf(usuario.getCodarea());

            this.entrantes = serviciosEntranteDerivado.catalogo(ncodarea, ficha);

        
        } else {
            this.error = "Debe seleccionar un documento";
            this.ver = true;
        }
        

    }

    public void eventEntrada(ActionEvent evt) {
        this.verCatalogoEntrada = true;
        //for (EntranteBean p : selectedEntrantes) {		
        //}	
    }

    public void eventActualizarEntrante(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
        //this.verActualizar = false;
        EntranteBean entranteBean = new EntranteBean();
        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        //File file = null;
        String file = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        this.ncodareaA = usuario.getCodarea();
        this.vresactA = usuario.getLogin();
        String ncodarea = String.valueOf(usuario.getCodarea());
        ficha = usuario.getFicha();
        // controlando el adjuntar		
        if (ind_adjuntar == 0) {
            this.vubiarchivoA = "NADA";
        } else {
            //file = (File) session.getAttribute("file");
            file = (String) session.getAttribute("file");
            nombre_archivo = file;
            List<ServidorBean> bean = this.entranteDerivadoDAO.servidor();
            for (ServidorBean e : bean) {
                ubicacion = e.getDescripcion();
                //nombre_archivo = String.valueOf(file.getName());
                //ubicacion = "http://1.1.194.53/entrada/";
            }
            String annio = Integer.toString(c.get(Calendar.YEAR));
            nombre_archivo = annio + nombre_archivo;
            this.vubiarchivoA = ubicacion + nombre_archivo;
            //this.vubiarchivoA=String.valueOf(file.getName());
        }

        // le paso como parametro al stored
        entranteBean.setNano(this.nanoA);
        entranteBean.setNorigen(this.norigenA);
        entranteBean.setVtipodoc(this.tipodocA);
        entranteBean.setNcorrelativo(this.ncorrelativoA);
        entranteBean.setVnumdoc(this.vnumdocA);
        entranteBean.setNremitente(this.nremitenteA);
        entranteBean.setFicha_dirigido(this.ficha_dirigidoA);
        entranteBean.setFicha_derivado(this.ficha_derivadoA);
        entranteBean.setVasunto(this.vasuntoA);
        entranteBean.setVreferencia(this.vreferenciaA);
        entranteBean.setVobservacion(this.vobservacionA);
        entranteBean.setVaccion(this.vaccionA);
        entranteBean.setVprioridad(this.vprioridadA);
        entranteBean.setVresact(this.vresactA);
        entranteBean.setVubiarchivo(this.vubiarchivoA);
        entranteBean.setDfecdoc(this.dfecdocA);
        entranteBean.setNdiasplazo(this.ndiasplazoA);
        entranteBean.setVestado(this.vestadoA);
        entranteBean.setVremitente(this.vremitenteA);
        entranteBean.setNdirigido(this.ncodareaA);
        entranteBean.setDfecregistro(this.dfecregistroA);
        entranteBean.setSistema(this.sistemaA);
        entranteBean.setNfolio(this.nfolioA);
        ///aóade Eli Diaz
        entranteBean.setVdirigido(this.generaAreasA());
        entranteBean.setIndicador(this.generaIndicadorA());
        entranteBean.setQuitarareas(this.quitarareas);
        serviciosEntranteDerivado.actualizarEntrante(entranteBean);
        this.entrantes = serviciosEntranteDerivado.catalogo(ncodarea, ficha);// actualiza el reporte
        this.verCatalogo = true;
        this.verActualizar = false;
        //this.verDetalles = false;
        ind_adjuntar = 0;
    }

    /* Evento visualizar PDF*/
    public void evenVerPDF(ActionEvent evt) {
    	 EntranteBean p = null;
        for (EntranteBean q : this.entrantes) {
            if (q.isSelected()) {
                p = q;
            }
        }
        
        if (p != null) {
            this.rutaPDFPopup = p.getUbicacion_entrada();
            this.verPDF = true;
        } else {
            this.error = "Debe seleccionar un registro para visualizar el PDF";
            this.ver = true;
            this.verPDF = false;
        }
        
    }
    
    
    
    
    public String generaAreasA() {
        String areas = "";
        for (AreaBean p : this.areas) {
            areas += p.getCodigo();
        }
        return areas;
    }

    public String generaIndicadorA() {
        String areas = "";
        for (AreaBean p : this.areas) {
            areas += p.getTipo();
        }
        return areas;
    }

    public void cerrarCatalogoEntrada(ActionEvent event) {
        this.verCatalogoEntrada = false;
    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }

    public void cerrarDetalles(ActionEvent event) {
    	this.verPDF = false;
        this.verDetalles = false;
    }

    public void eventNuevo(ActionEvent event) {
        this.limpiarNuevo();
        //combo.setLabel("ITEMS");
        this.verNuevo = true;
        this.verCatalogo = false;
    }

    public void pasaDerecha(ActionEvent actionEvent) {
        Utils.pasaDerecha(this.items7a, this.items7b, this.items7aSelected);
    }

    public void pasaIzquierda(ActionEvent actionEvent) {
        Utils.pasaIzquierda(items7a, items7b, items7bSelected);
    }

    public void captura(ValueChangeEvent changeEvent) {

        long ficha = 0;
        area = (String) changeEvent.getNewValue();
        area_remite = Integer.parseInt(area);


        if (area_remite == 100 || area_remite == 601) {
        } else if (area_remite > 600) {
            List<RepresentanteBean> remite = entranteDerivadoDAO
                    .representante(area_remite);
            List itemsrepresentante = Utils.getRepresentante(remite);
            this.items3 = itemsrepresentante;
            // for (RepresentanteBean r: remite);

        } else {
            if (!changeEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                changeEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                changeEvent.queue();
                return;
            }
            // System.out.println(area);
            entranteDerivadoDAO.trabajador(Integer.parseInt(area));
            List<TrabajadorBean> trabajador = entranteDerivadoDAO
                    .trabajador(area_remite);
            List itemsTrabajador = Utils.getTrabajador_derivador(trabajador);
            // this.items6 = itemsTrabajador;
            // for (TrabajadorBean t: trabajador)
            // System.out.println(t.getArea());
            List<JefeBean> bean = this.entranteDerivadoDAO.jefe(area_remite);
            for (JefeBean e : bean) {
                ficha = e.getFicha();
            }
            this.items3 = itemsTrabajador;
            this.ficha_dirigidoN = ficha;

        }

    }

    public void captura_derivador(ValueChangeEvent changeEvent) {
        area_deriva = (String) changeEvent.getNewValue();
        //System.out.println(area_deriva);
        //entranteDAO.trabajador_derivador(Integer.parseInt(area_deriva));
        List<TrabajadorBean> trabajador_derivador = entranteDerivadoDAO.trabajador_derivador(Integer.parseInt(area_deriva));
        List itemsTrabajador = Utils.getTrabajador(trabajador_derivador);
        this.items6 = itemsTrabajador;
        for (TrabajadorBean t : trabajador_derivador) {
            //System.out.println(t.getArea());
        }
    }

    public void captura_representante(ValueChangeEvent changeEvent) {

        codigo_representante = (String) changeEvent.getNewValue();
        //this.ndirigidoN=Integer.parseInt(area);
        //System.out.println(codigo_representante);
        entranteDerivadoDAO.representante(Integer.parseInt(codigo_representante));
        List<RepresentanteBean> remitente = entranteDerivadoDAO.representante(Integer.parseInt(codigo_representante));
        //List itemsrepresentante = Utils.getRepresentante(remitente);			
        //this.items4 = itemsrepresentante;		
        //this.items6 = itemsTrabajador;		
        for (RepresentanteBean t : remitente) {
            nrepresentanteN = t.getVnombre();
        }
        //System.out.println(t.getDescripcion());		
    }

    public void actualizar(ValueChangeEvent changeEvent) {
        //String area = String.valueOf(this.ndirigidoA);
        //String area = String.valueOf(this.vremitenteA);
        //String area = (String)changeEvent.getNewValue();
        //System.out.println(area);
        int area = this.getNdirigidoA();
        entranteDerivadoDAO.trabajador(area);
        List<TrabajadorBean> trabajador = entranteDerivadoDAO.trabajador(area);
        List itemsTrabajador = Utils.getTrabajador(trabajador);
        this.items1 = itemsTrabajador;
        for (TrabajadorBean t : trabajador);
    }

    public void actualizar_ficha(ValueChangeEvent changeEvent) {
        String area_derivada = String.valueOf(this.nremitenteA);
        entranteDerivadoDAO.trabajador_derivador(Integer.parseInt(area_derivada));
        List<TrabajadorBean> trabajador_derivador = entranteDerivadoDAO.trabajador_derivador(Integer.parseInt(area_derivada));
        List itemsTrabajador = Utils.getTrabajador(trabajador_derivador);
        this.items6 = itemsTrabajador;
        for (TrabajadorBean t : trabajador_derivador);

    }

    public void Origen(ValueChangeEvent changeEv) {
        origen = (String) changeEv.getNewValue();
        //System.out.println(origen);
        int origenes = Integer.parseInt(origen);

        if (origenes == 1) {
            //System.out.println("estoy aqui");
            List<AreaBean> area = entranteDerivadoDAO.areas();
            List itemsArea = Utils.getAreas(area);
            this.items1 = itemsArea;

        } else {
            //System.out.println(origen);			
            List<RemitenteBean> remitente = entranteDerivadoDAO.remitentes();
            List itemsRemitente = Utils.getRemitentes(remitente);
            this.items1 = itemsRemitente;

        }



    }

    public void captura_accion(ValueChangeEvent changeEvent) {
        selectedItems1 = new ArrayList<String>();
        String acciones = this.vaccionA;
        //System.out.println(acciones);

        for (String p : this.selectedItems1) {
            selectedItems.add(p);
        }

    }

    public void eventSeleccionarTrabajador(ActionEvent event) {


        if (cont == 0) {
            area_dirigido = area_deriva + area_dirigido;
            cont = 1;
        } else {
            area_dirigido = area_deriva + caracter + area_dirigido;
        }
        //area_dirigido.split("-");
        //System.out.println(area_dirigido);
        //System.out.println(area_dirigido);	
        areaseleccionadaN = area_dirigido;
        this.ncodareaN = Integer.parseInt(area_deriva);
        //this.dirigidoN=area_dirigido;

    }

    public void eventDeseleccionarTrabajador(ActionEvent event) {
        // Falta codificar el sacar registros
        //area_dirigido = area_deriva + caracter - area_dirigido;
        //String dato = area_dirigido.split(area_dirigido).toString();
        //System.out.println(dato);
    }

    public void eventRegistrarEntrante(ActionEvent event) {
        try {
            //System.out.println("REGISTRANDO!!!");
            String valida = null;
            valida = this.validarFormulario();
            if (valida.equals("ok")) {
                for (String p : this.selectedItems1) {
                    //System.out.println("ITEM:"+ p);
                    accion = p + accion;
                    //System.out.println("completo:"+ accion);
                    this.vaccionN = accion;

                }
                this.verNuevo = false;
                ///acediendo a sesion http
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                /////guardando en sesion un objeto
                Usuario usuario = null;
                //File file = null;
                String file = null;
                usuario = (Usuario) session.getAttribute("sUsuario");
                //this.ncodareaN=usuario.getCodarea();
                this.vrescreN = usuario.getLogin();
                this.vresactN = usuario.getLogin();
                this.ncodarea_origenN = usuario.getCodarea();
                this.nremitenteN = Integer.parseInt(vremitenteN);
                String ncodarea = String.valueOf(usuario.getCodarea());
                ficha = usuario.getFicha();
                // controlando el adjuntar
                if (ind_adjuntar == 0) {
                    this.vubiarchivoN = "NADA";
                } else {
                    //file = (File) session.getAttribute("file");	
                    file = (String) session.getAttribute("file");
                    nombre_archivo = file;
                    List<ServidorBean> bean = this.entranteDerivadoDAO.servidor();
                    for (ServidorBean e : bean) {
                        ubicacion = e.getDescripcion();
                        //nombre_archivo = String.valueOf(file.getName());
                        //ubicacion = "http://1.1.194.53/entrada/";
                    }
                    String annio = Integer.toString(c.get(Calendar.YEAR));
                    nombre_archivo = annio + nombre_archivo;
                    this.vubiarchivoN = ubicacion + nombre_archivo;
                }

                Set set = this.items7b.entrySet();
                Iterator i = set.iterator();
                String valor = null;
                suma_areas = "";
                while (i.hasNext()) {
                    Map.Entry val = (Map.Entry) (i.next());
                    //System.out.println("value Hash !! : " + val.getValue());
                    valor = (String) val.getValue();
                    suma_areas += valor;

                }
                //logger.debug("Areas seleccionadas:" + suma_areas);
                //System.out.println("Areas seleccionadas:" + suma_areas);



                EntranteBean entranteBean = new EntranteBean();
                entranteBean.setOrigen(this.origenN);
                entranteBean.setVtipodoc(this.tipodocN);
                entranteBean.setNcodarea(this.ncodarea_origenN);
                entranteBean.setNcodarea_origen(this.ncodarea_origenN);
                //Eli Comenta
                //entranteBean.setNremitente(this.nremitenteN);	
                entranteBean.setDfecregistro(this.dfecregistroN);
                entranteBean.setVremitente(suma_areas);
                //entranteBean.setVremitente(this.vremitenteN);			
                entranteBean.setVubiarchivo(this.vubiarchivoN);
                //entranteBean.setNdirigido(this.ndirigidoN);
                entranteBean.setNdirigido(this.nremitenteN);
                entranteBean.setFicha_dirigido(this.ficha_dirigidoN);
                entranteBean.setVnumdoc(this.vnumdocN);
                entranteBean.setVasunto(this.vasuntoN);
                entranteBean.setVreferencia(this.vreferenciaN);
                entranteBean.setVobservacion(this.vobservacionN);
                entranteBean.setVaccion(this.vaccionN);
                entranteBean.setVprioridad(this.vprioridadN);
                entranteBean.setVestado(this.vestadoN);
                entranteBean.setDfecdoc(this.dfecdocN);
                entranteBean.setDfecplazo(this.dfecplazoN);
                entranteBean.setVrescre(this.vrescreN);
                entranteBean.setVresact(this.vresactN);
                entranteBean.setFicha_derivado(this.ficha_derivadoN);
                entranteBean.setNdiasplazo(this.ndiasplazoN);
                entranteBean.setNfolio(this.nfolioN);
                entranteBean.setSistema(this.sistemaN);
                serviciosEntranteDerivado.nuevoEntrante(entranteBean);
                this.entrantes = serviciosEntranteDerivado.catalogo(ncodarea, ficha);// actualiza el reporte
                this.error = "Transaccion ok.";
                ind_adjuntar = 0;
                this.ver = true;
                this.verCatalogo = true;
                this.verActualizar = false;
            } else {
                this.error = valida;
                this.ver = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            //logger.error("Fallo el registro", e);
            this.error = "Transaccion No valida.";
            this.ver = true;
            this.verCatalogo = true;
        }
    }

    public List<EntranteBean> getEntrantes() {
        return this.entrantes;
    }

    public void setEntrantes(List<EntranteBean> entrantes) {
        this.entrantes = entrantes;
    }

    public List<SelectionBean> getSelection() {
        return selection;
    }

    public void setSelection(List<SelectionBean> selection) {
        this.selection = selection;
    }

    public void setServiciosEntrante(IServiciosEntranteDerivado serviciosEntranteDerivado) {
        this.serviciosEntranteDerivado = serviciosEntranteDerivado;
    }

    /**
     * ***********************************************
     */
    public void eventFiltros(ActionEvent event) {
        // this.ver = true;
        // usamos para darle el fomato adecuado para pasarle al stored de oracle
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        this.ncodareaA = usuario.getCodarea();
        this.vresactA = usuario.getLogin();
        this.ficha = usuario.getFicha();
        String ncodarea = String.valueOf(usuario.getCodarea());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String date1 = sdf.format(this.getDate1());
        String date2 = sdf.format(this.getDate2());
        // se puede validar fechas
        if (this.date1.after(this.date2)) {
            this.error = "Fecha 1 debe ser menor que la fecha 2";
            this.ver = true;
        } else {
            System.out.println("Filtrando..");
            //Falta Programar por Eli Diaz
            this.entrantes = entranteDerivadoDAO.filtrosSP(date1, date2, this.getItem2Seleccionado(), ncodarea, this.ficha);

        }
        //System.out.println(this.entrantes.size());
        //System.out.println(date1 + "  " + date2 + "  "
        //	+ this.getItem2Seleccionado());
    }

    public void eventRefrescar(ActionEvent evt) {

        String area = this.area;
        this.entrantes = entranteDerivadoDAO.entrantesSP(area, ficha);
        this.detalle = "";
        selectedItems1.clear();

    }

    public void eventBusquedas(ActionEvent event) {
        Usuario usuario = null;

        HttpSession session = null;
        session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        usuario = (Usuario) session.getAttribute("sUsuario");
        area = String.valueOf(usuario.getCodarea());
        ficha = usuario.getFicha();

        if (this.detalle.equals("") || this.opcion.equals("")) {
            this.error = "Debe de Ingresar una opcion de busqueda";
            this.ver = true;
        } else {
            this.entrantes = entranteDerivadoDAO.BusquedaSP(area, ficha, opcion, detalle);
        }


    }
    /*
     public void eventBusquedas(ActionEvent event) {	
     ///acediendo a sesion http
     HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
     /////guardando en sesion un objeto
     Usuario usuario = null;
     usuario=(Usuario)session.getAttribute("sUsuario");
     //this.areaA=usuario.getCodarea();			
     //this.vresactA=usuario.getLogin();	
     String area=String.valueOf(usuario.getCodarea());
     ficha = usuario.getFicha();
     // usamos para darle el fomato adecuado para pasarle al stored de oracle
     SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
     String date1 = sdf.format(this.getDate1());		
     String date2 = sdf.format(this.getDate2());
     // se puede validar fechas
     if (this.date1.after(this.date2)) {
     this.error = "Fecha 1 debe ser menor que la fecha 2";
     this.ver = true;
     } else {
     System.out.println("buscando..");
     //Falta Programar por Eli Diaz
     this.entrantes = entranteDerivadoDAO.BusquedaSP(area, ficha, opcion, detalle);
     //this.documento = documentoSalidaDAO.BusquedaSP(date1, date2, this.getItem2Seleccionado(), area, opcion, detalle);	
     //System.out.println(date1+ "  " +date2+ "  " +this.getItemSeleccionado()+ "  " +area+ "  " +opcion + "  " + detalle);
     }
     }
     */

    public void eventTipoDoc1(ValueChangeEvent changeEvent) {
        String itemSeleccionado = (String) changeEvent.getNewValue();
        // System.out.println("Hola mmm");
        //System.out.println(itemSeleccionado);
        if (itemSeleccionado.equals("INTERNO")) {
            activo = true;
            ///botonCodigo.setDisabled(false);
        } else {
            activo = false;
            ///botonCodigo.setDisabled(true);
        }
    }
    // list of selected employees
    protected ArrayList<EntranteBean> selectedEntrantes;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanEntrantesDerivado() {
        selectedEntrantes = new ArrayList<EntranteBean>();
        //System.out.println("Contructor MBeanProfiles....");
    }

    public void eventoNuevo(ActionEvent evnt) {
        for (EntranteBean p : selectedEntrantes) {
            //System.out.println(p.getArea() + "   " + p.getNano());
        }
    }

    @PostConstruct
    public void posterior() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        int dirigido = usuario.getCodarea();
        String ncodarea = String.valueOf(usuario.getCodarea());
        area = String.valueOf(usuario.getCodarea());
        ficha = usuario.getFicha();
        //vareaN= usuario.getNomequipo().toUpperCase();
        vrescreN = usuario.getLogin().toUpperCase();
        vresactN = usuario.getLogin().toUpperCase();
        centroN = usuario.getCentro().toUpperCase();

        area_origenN = usuario.getNomequipo().toUpperCase();
        ncodcentroN = usuario.getNcodcentro();
        vareaN = usuario.getNomequipo();

        //ndirigidoN=this.dirigido;
        //ndirigidoN=313;
        //ndirigidoN=this.dirigido;
        //this.nremitenteN = dirigido;
        //this.ndirigidoN= dirigido;		
        //this.vremitenteN = ncodarea;
        //ver eli
        //this.dirigidoN = ncodarea;
        //System.out.println(usuario.getLogin().toUpperCase());
        //System.out.println(usuario.getLogin().toUpperCase());	
        this.entrantes = serviciosEntranteDerivado.catalogo(ncodarea, ficha);

        List<AreaBean> areas = entranteDerivadoDAO.areas();
        List itemsAreas = Utils.getAreas(areas);
        this.items1 = itemsAreas;


        List<TiposBean> tipos = entranteDerivadoDAO.tipos();
        List itemstipos = Utils.getTipos(tipos);
        this.items2 = itemstipos;


        List<TrabajadorBean> trabajador = entranteDerivadoDAO.trabajador(this.ndirigidoA);
        List itemsTrabajador = Utils.getTrabajador(trabajador);
        this.items3 = itemsTrabajador;


        List<RemitenteBean> remitentes = entranteDerivadoDAO.remitentes();
        List itemsRemitentes = Utils.getRemitentes(remitentes);
        this.items4 = itemsRemitentes;


        //List<AccionBean> accion = entranteDAO.accion();
        //List itemsAccion = Utils.getAccion(accion);			
        //this.items5 = itemsAccion;	
        //for (AccionBean a: accion);

        List<TrabajadorBean> trabajador_derivador = entranteDerivadoDAO.trabajador_derivador(dirigido);
        List itemsTrabajador_derivador = Utils.getTrabajador_derivador(trabajador_derivador);
        this.items6 = itemsTrabajador_derivador;


        //List<AreaBean> area_derivados = entranteDerivadoDAO.areas();
        //List itemsderivados = Utils.getAreas(area_derivados);			
        //this.items7 = itemsderivados;	
        //CF V01.00
        //this.items7a = Utils.getAreasLink(area_derivados);

        List<EstadosBean> estado = entranteDerivadoDAO.estados();
        List itemsEstado = Utils.getEstado(estado);
        this.items8 = itemsEstado;

        List<AreaBean> area_derivados = entranteDerivadoDAO.areas();
        List itemsderivados = Utils.getAreas(area_derivados);
        this.items9 = itemsderivados;


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

        //System.out.println("Tama:" + entrantes.size());
        // build the new selected list
        EntranteBean employee;
        for (int i = 0, max = entrantes.size(); i < max; i++) {
            employee = (EntranteBean) entrantes.get(i);
            if (employee.isSelected()) {
                selectedEntrantes.add(employee);
            }
        }
        for (EntranteBean p : selectedEntrantes) {
            //System.out.println("VER EL DETALLE");
            //System.out.println(p.getNano() + " " + p.getNorigen() + " " + p.getVtipodoc() + " " + p.getNcorrelativo() + " " + p.getDfecplazo() + " " + p.getNdiasplazo());
            this.mCarga.setNombrePdf(p.getVubiarchivo());
            this.anollave = p.getNano();
            this.origenllave = p.getNorigen();
            this.tipodocllave = p.getVtipodoc();
            this.correlativollave = p.getNcorrelativo();
            this.numdocllave = p.getVnumdoc();
            this.fechaplazollave = p.getDfecplazo();
            this.diasplazollave = p.getNdiasplazo();
            this.fecharecepcionllave = p.getDfecdoc();
            this.prioridadllave = prioridadllave;
            
            LlavesBean beans = new LlavesBean();
            beans.setAno(this.anollave);
            beans.setOrigen(this.origenllave);
            beans.setTipodoc(this.tipodocllave);
            beans.setCorrelativo(this.correlativollave);
            beans.setNumdoc(this.numdocllave);
            beans.setFechaplazo(this.fechaplazollave);
            beans.setDiasplazo(this.diasplazollave);
            beans.setFecharecepcion(this.fecharecepcionllave);
            beans.setPrioridad(this.prioridadllave);
            
            HttpSession session = (HttpSession) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getSession(false);
            session.setAttribute("sLlaves", beans);
            List<SeguimientoEntranteBean> catalogo = null;
            catalogo = this.serviciosseguimientoentrante.catalogo(
                    this.anollave, this.origenllave, this.tipodocllave,
                    this.correlativollave, area);
            this.mBeanSeguimiento.setSeguimiento(catalogo);
            this.ndirigidoA = p.getNdirigido();
            this.dirigido = p.getNdirigido();
            //this.dirigidoA = String.valueOf(p.getNdirigido());
            //this.dirigidoA = String.valueOf(p.getNremitente());
            this.nremitenteA = p.getNremitente();
            this.ficha_dirigidoA = p.getFicha_dirigido();
            this.ficha_derivadoA = p.getFicha_derivado();
            this.treeControllerDerivador.inicializarArbol(String.valueOf(p.getNcorrelativo()), String.valueOf(p.getNano()));
            //this.vestadoA = p.getNestado();
            ruta = p.getVubiarchivo();
            ind_adjuntar = p.getNindicador();
            this.vubiarchivoA = p.getVubiarchivo();
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
            for (int i = 0, max = entrantes.size(); i < max; i++) {
                employee = (EntranteBean) entrantes.get(i);
                employee.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = false;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = false;
        }
    }

    public void eventCancelar(ActionEvent event) {
        this.verCatalogo = true;
        this.verNuevo = false;
        this.verActualizar = false;
        selectedItems1.clear();
        this.vubiarchivoA = "NADA";
    }

    public void limpiarNuevo() {
        this.nanoN = 0;
        this.origenN = "";
        this.tipodocN = "ATD0001";
        this.ncorrelativoN = 0;
        this.vnumdocN = "";
        this.vmesaparteN = "";
        this.nremitenteN = 0;
        this.vremitenteN = "";
        this.dirigidoN = "";
        this.dfecdocN = new Date();
        this.dfecregistroN = new Date();
        this.vasuntoN = "";
        this.vreferenciaN = "";
        this.vobservacionN = "";
        this.vaccionN = "";
        this.vprioridadN = "";
        //this.vubiarchivoN = "";
        this.dfecplazoN = new Date();
        this.ndiasplazoN = 0;
        this.vestadoN = "";
        //this.nindicadorN = 0;
        this.dfeccreN = new Date();
        this.dfecactN = new Date();
        this.areaseleccionadaN = "";
        //this.vrescreN = "";
        //this.vresactN = "";
        //this.ncodcentroN = 0;
        //this.ncodareaN = 0;
        this.ficha_dirigidoN = 0;
        this.items7b.clear();
        selectedItems1.clear();
        this.vubiarchivoN = "NADA";
        this.nfolioN = 0;


    }

    public void verGrupo() {
        this.vremitenteN = "";
    }

    public void limpiarActualizar() {
        this.nanoA = 0;
        //this.norigenA = 0;
        //this.vtipodocA = "";
        this.ncorrelativoA = 0;
        this.vnumdocA = "";
        //this.vmesaparteA = "";
        //this.nremitenteA = 0;
        //this.vremitenteA = "";
        //this.ndirigidoA = 0;
        this.dfecdocA = new Date();
        this.dfecregistroA = new Date();
        //this.vasuntoA = "";
        //this.vreferenciaA = "";
        //this.vobservacionA = "";
        //this.vaccionA = "";
        //this.vprioridadA = "";
        //this.vubiarchivoA = "";
        this.dfecplazoA = new Date();
        this.ndiasplazoA = 0;
        //this.vestadoA = "";
        //this.nindicadorA = 0;
        this.dfeccreA = new Date();
        this.dfecactA = new Date();
        //this.vrescreA = "";
        //this.vresactA = "";
        //this.ncodcentroA = 0;
        //this.ncodareaA = 0;
        //this.ficha_dirigidoA = 0;
    }

    public String validarFormulario() {
        String mensaje = "Falta ingresar: ";
        boolean ok = true;
        if (this.origenN.equals("0")) {
            ok = false;
            mensaje += "Origen";
        }
        if (this.tipodocN.equals("ATD0001")) {
            if (ok == false) {
                mensaje += ",Tipo de Documento";
            } else {
                mensaje += "Tipo de Documento";
            }
            ok = false;
        }
        if (this.vnumdocN.trim().length() == 0) {
            if (ok == false) {
                mensaje += ",Numero Documento";
            } else {
                mensaje += "Numero Documento";
            }
            ok = false;
        }
        if (this.ndiasplazoN <= 0) {
            if (ok == false) {
                mensaje += ",Dias";
            } else {
                mensaje += "Dias";
            }
            ok = false;
        }

        if (this.vremitenteN.equals("100")) {
            if (ok == false) {
                mensaje += ",Remitente";
            } else {
                mensaje += "Remitente";
            }
            ok = false;
        }
        if (this.vasuntoN.trim().length() == 0) {
            if (ok == false) {
                mensaje += ",Asunto";
            } else {
                mensaje += "Asunto";
            }
            ok = false;
        }
        if (this.selectedItems1.size() == 0) {
            if (ok == false) {
                mensaje += ",Acciones";
            } else {
                mensaje += "Acciones";
            }
            ok = false;
        }

        if (ok) {
            mensaje = "ok";
        }
        return mensaje;
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

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
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

    public long getFicha_dirigidoN() {
        return ficha_dirigidoN;
    }

    public void setFicha_dirigidoN(long fichaDirigidoN) {
        ficha_dirigidoN = fichaDirigidoN;
    }

    public long getFicha_dirigidoA() {
        return ficha_dirigidoA;
    }

    public void setFicha_dirigidoA(long fichaDirigidoA) {
        ficha_dirigidoA = fichaDirigidoA;
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

    public String getOrigenN() {
        return origenN;
    }

    public void setOrigenN(String origenN) {
        this.origenN = origenN;
    }

    public String getDirigidoN() {
        return dirigidoN;
    }

    public void setDirigidoN(String dirigidoN) {
        this.dirigidoN = dirigidoN;
    }

    public String getOrigenA() {
        return origenA;
    }

    public void setOrigenA(String origenA) {
        this.origenA = origenA;
    }

    public String getItemSeleccionado() {
        return itemSeleccionado;
    }

    public void setItemSeleccionado(String itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }

    public void setEntranteDerivadoDAO(EntranteDerivadoDAO entranteDerivadoDAO) {
        this.entranteDerivadoDAO = entranteDerivadoDAO;
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

    ///////////////////////////////
    public int getNanoN() {
        return nanoN;
    }

    public void setNanoN(int nanoN) {
        this.nanoN = nanoN;
    }

    public String gettipodocN() {
        return tipodocN;
    }

    public void setVtipodocN(String tipodocN) {
        this.tipodocN = tipodocN;
    }

    public String getVnumdocN() {
        return vnumdocN;
    }

    public void setVnumdocN(String vnumdocN) {
        this.vnumdocN = vnumdocN;
    }

    public String getVmesaparteN() {
        return vmesaparteN;
    }

    public void setVmesaparteN(String vmesaparteN) {
        this.vmesaparteN = vmesaparteN;
    }

    public int getNremitenteN() {
        return nremitenteN;
    }

    public void setNremitenteN(int nremitenteN) {
        this.nremitenteN = nremitenteN;
    }

    public String getVremitenteN() {
        return vremitenteN;
    }

    public void setVremitenteN(String vremitenteN) {
        this.vremitenteN = vremitenteN;
    }

    public Date getDfecdocN() {
        return dfecdocN;
    }

    public void setDfecdocN(Date dfecdocN) {
        this.dfecdocN = dfecdocN;
    }

    public Date getDfecregistroN() {
        return dfecregistroN;
    }

    public void setDfecregistroN(Date dfecregistroN) {
        this.dfecregistroN = dfecregistroN;
    }

    public String getVasuntoN() {
        return vasuntoN;
    }

    public void setVasuntoN(String vasuntoN) {
        this.vasuntoN = vasuntoN;
    }

    public String getVreferenciaN() {
        return vreferenciaN;
    }

    public void setVreferenciaN(String vreferenciaN) {
        this.vreferenciaN = vreferenciaN;
    }

    public String getVobservacionN() {
        return vobservacionN;
    }

    public void setVobservacionN(String vobservacionN) {
        this.vobservacionN = vobservacionN;
    }

    public String getVaccionN() {
        return vaccionN;
    }

    public void setVaccionN(String vaccionN) {
        this.vaccionN = vaccionN;
    }

    public String getVprioridadN() {
        return vprioridadN;
    }

    public void setVprioridadN(String vprioridadN) {
        this.vprioridadN = vprioridadN;
    }

    public String getVubiarchivoN() {
        return vubiarchivoN;
    }

    public void setVubiarchivoN(String vubiarchivoN) {
        this.vubiarchivoN = vubiarchivoN;
    }

    public Date getDfecplazoN() {
        return dfecplazoN;
    }

    public void setDfecplazoN(Date dfecplazoN) {
        this.dfecplazoN = dfecplazoN;
    }

    public int getNdiasplazoN() {
        return ndiasplazoN;
    }

    public void setNdiasplazoN(int ndiasplazoN) {
        this.ndiasplazoN = ndiasplazoN;
    }

    public String getVestadoN() {
        return vestadoN;
    }

    public void setVestadoN(String vestadoN) {
        this.vestadoN = vestadoN;
    }

    public int getNindicadorN() {
        return nindicadorN;
    }

    public void setNindicadorN(int nindicadorN) {
        this.nindicadorN = nindicadorN;
    }

    public Date getDfeccreN() {
        return dfeccreN;
    }

    public void setDfeccreN(Date dfeccreN) {
        this.dfeccreN = dfeccreN;
    }

    public Date getDfecactN() {
        return dfecactN;
    }

    public void setDfecactN(Date dfecactN) {
        this.dfecactN = dfecactN;
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

    public int getNcodcentroN() {
        return ncodcentroN;
    }

    public void setNcodcentroN(int ncodcentroN) {
        this.ncodcentroN = ncodcentroN;
    }

    public int getNcodareaN() {
        return ncodareaN;
    }

    public void setNcodareaN(int ncodareaN) {
        this.ncodareaN = ncodareaN;
    }

    public int getNanoA() {
        return nanoA;
    }

    public void setNanoA(int nanoA) {
        this.nanoA = nanoA;
    }

    public int getNorigenA() {
        return norigenA;
    }

    public void setNorigenA(int norigenA) {
        this.norigenA = norigenA;
    }

    public String gettipodocA() {
        return tipodocA;
    }

    public void settipodocA(String tipodocA) {
        this.tipodocA = tipodocA;
    }

    public String getVnumdocA() {
        return vnumdocA;
    }

    public void setVnumdocA(String vnumdocA) {
        this.vnumdocA = vnumdocA;
    }

    public String getVmesaparteA() {
        return vmesaparteA;
    }

    public void setVmesaparteA(String vmesaparteA) {
        this.vmesaparteA = vmesaparteA;
    }

    public int getNremitenteA() {
        return nremitenteA;
    }

    public void setNremitenteA(int nremitenteA) {
        this.nremitenteA = nremitenteA;
    }

    public String getVremitenteA() {
        return vremitenteA;
    }

    public void setVremitenteA(String vremitenteA) {
        this.vremitenteA = vremitenteA;
    }

    public int getNdirigidoA() {
        return ndirigidoA;
    }

    public void setNdirigidoA(int ndirigidoA) {
        this.ndirigidoA = ndirigidoA;
    }

    public Date getDfecdocA() {
        return dfecdocA;
    }

    public void setDfecdocA(Date dfecdocA) {
        this.dfecdocA = dfecdocA;
    }

    public Date getDfecregistroA() {
        return dfecregistroA;
    }

    public void setDfecregistroA(Date dfecregistroA) {
        this.dfecregistroA = dfecregistroA;
    }

    public String getVasuntoA() {
        return vasuntoA;
    }

    public void setVasuntoA(String vasuntoA) {
        this.vasuntoA = vasuntoA;
    }

    public String getVreferenciaA() {
        return vreferenciaA;
    }

    public void setVreferenciaA(String vreferenciaA) {
        this.vreferenciaA = vreferenciaA;
    }

    public String getVobservacionA() {
        return vobservacionA;
    }

    public void setVobservacionA(String vobservacionA) {
        this.vobservacionA = vobservacionA;
    }

    public String getVaccionA() {
        return vaccionA;
    }

    public void setVaccionA(String vaccionA) {
        this.vaccionA = vaccionA;
    }

    public String getVprioridadA() {
        return vprioridadA;
    }

    public void setVprioridadA(String vprioridadA) {
        this.vprioridadA = vprioridadA;
    }

    public String getVubiarchivoA() {
        return vubiarchivoA;
    }

    public void setVubiarchivoA(String vubiarchivoA) {
        this.vubiarchivoA = vubiarchivoA;
    }

    public Date getDfecplazoA() {
        return dfecplazoA;
    }

    public void setDfecplazoA(Date dfecplazoA) {
        this.dfecplazoA = dfecplazoA;
    }

    public int getNdiasplazoA() {
        return ndiasplazoA;
    }

    public void setNdiasplazoA(int ndiasplazoA) {
        this.ndiasplazoA = ndiasplazoA;
    }

    public String getVestadoA() {
        return vestadoA;
    }

    public void setVestadoA(String vestadoA) {
        this.vestadoA = vestadoA;
    }

    public int getNindicadorA() {
        return nindicadorA;
    }

    public void setNindicadorA(int nindicadorA) {
        this.nindicadorA = nindicadorA;
    }

    public Date getDfeccreA() {
        return dfeccreA;
    }

    public void setDfeccreA(Date dfeccreA) {
        this.dfeccreA = dfeccreA;
    }

    public Date getDfecactA() {
        return dfecactA;
    }

    public void setDfecactA(Date dfecactA) {
        this.dfecactA = dfecactA;
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

    public int getNcodcentroA() {
        return ncodcentroA;
    }

    public void setNcodcentroA(int ncodcentroA) {
        this.ncodcentroA = ncodcentroA;
    }

    public int getNcodareaA() {
        return ncodareaA;
    }

    public void setNcodareaA(int ncodareaA) {
        this.ncodareaA = ncodareaA;
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

    public String getVareaN() {
        return vareaN;
    }

    public void setVareaN(String vareaN) {
        this.vareaN = vareaN;
    }

    public String getVareaA() {
        return vareaA;
    }

    public void setVareaA(String vareaA) {
        this.vareaA = vareaA;
    }

    public String getDirigidoA() {
        return dirigidoA;
    }

    public void setDirigidoA(String dirigidoA) {
        this.dirigidoA = dirigidoA;
    }

    public String getArea_origenN() {
        return area_origenN;
    }

    public void setArea_origenN(String areaOrigenN) {
        area_origenN = areaOrigenN;
    }

    public String getArea_origenA() {
        return area_origenA;
    }

    public void setArea_origenA(String areaOrigenA) {
        area_origenA = areaOrigenA;
    }

    public String getCentroN() {
        return centroN;
    }

    public void setCentroN(String centroN) {
        this.centroN = centroN;
    }

    public String getCentroA() {
        return centroA;
    }

    public void setCentroA(String centroA) {
        this.centroA = centroA;
    }

    public int getNcodarea_origenN() {
        return ncodarea_origenN;
    }

    public void setNcodarea_origenN(int ncodareaOrigenN) {
        ncodarea_origenN = ncodareaOrigenN;
    }

    public int getNcodarea_origenA() {
        return ncodarea_origenA;
    }

    public void setNcodarea_origenA(int ncodareaOrigenA) {
        ncodarea_origenA = ncodareaOrigenA;
    }

    public int getNdirigidoN() {
        return ndirigidoN;
    }

    public void setNdirigidoN(int ndirigidoN) {
        this.ndirigidoN = ndirigidoN;
    }

    public int getDirigido() {
        return dirigido;
    }

    public void setDirigido(int dirigido) {
        this.dirigido = dirigido;
    }

    public long getFicha_derivadoN() {
        return ficha_derivadoN;
    }

    public void setFicha_derivadoN(long fichaDerivadoN) {
        ficha_derivadoN = fichaDerivadoN;
    }

    public long getFicha_derivadoA() {
        return ficha_derivadoA;
    }

    public void setFicha_derivadoA(long fichaDerivadoA) {
        ficha_derivadoA = fichaDerivadoA;
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

    public String getNrepresentanteN() {
        return nrepresentanteN;
    }

    public void setNrepresentanteN(String nrepresentanteN) {
        this.nrepresentanteN = nrepresentanteN;
    }

    public int getNrepresentanteA() {
        return nrepresentanteA;
    }

    public void setNrepresentanteA(int nrepresentanteA) {
        this.nrepresentanteA = nrepresentanteA;
    }

    public String getAreaseleccionadaN() {
        return areaseleccionadaN;
    }

    public void setAreaseleccionadaN(String areaseleccionadaN) {
        this.areaseleccionadaN = areaseleccionadaN;
    }

    public List getItemsseleccionado() {
        return itemsseleccionado;
    }

    public void setItemsseleccionado(List itemsseleccionado) {
        this.itemsseleccionado = itemsseleccionado;
    }

    public String getAreaseleccionadaA() {
        return areaseleccionadaA;
    }

    public void setAreaseleccionadaA(String areaseleccionadaA) {
        this.areaseleccionadaA = areaseleccionadaA;
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

    public List getItems8() {
        return items8;
    }

    public void setItems8(List items8) {
        this.items8 = items8;
    }

    public TreeControllerDerivador getTreeControllerDerivador() {
        return treeControllerDerivador;
    }

    public void setTreeControllerDerivador(TreeControllerDerivador treeControllerDerivador) {
        this.treeControllerDerivador = treeControllerDerivador;
    }

    public List getItems9() {
        return items9;
    }

    public void setItems9(List items9) {
        this.items9 = items9;
    }

    public List<AreaBean> getAreasOrigen() {
        return areasOrigen;
    }

    public void setAreasOrigen(List<AreaBean> areasOrigen) {
        this.areasOrigen = areasOrigen;
    }

    public RecursoReport getReporte() {
        return reporte;
    }

    public void setReporte(RecursoReport reporte) {
        this.reporte = reporte;
    }

    // para impresion
    public RecursoReport getRecursoReport() {


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ec = FacesContext.getCurrentInstance().getExternalContext();
        EntranteBean p = null;
        for (EntranteBean q : this.entrantes) {
            if (q.isSelected()) {
                p = q;
            }
        }

        if (p != null) {
            this.eventoTab();// ADD CF AGO 2011
            //System.out.println("Fila Selecionada!!!!");
            parametros.put("P_AREA", area);
            parametros.put("P_ANIO_LLAVE", String.valueOf(this.anollave));
            parametros.put("P_ORIGEN_LLAVE", String.valueOf(this.origenllave));
            parametros.put("P_TIPOC_DOC_LLAVE", this.tipodocllave);
            parametros.put("P_NUMDOC_LLAVE", this.numdocllave);
            parametros.put("P_AREA_ORIGEN_A", p.getArea_origen());
            parametros.put("P_FECHA_DOC", sdf.format(p.getDfecdoc()));
            parametros.put("P_FECHA_RECEP", sdf.format(p.getDfeccre()));
            parametros.put("P_FECHA_PLAZO", sdf.format(p.getDfecplazo()));
            parametros.put("P_PRIORIDAD", p.getVprioridad());
            parametros.put("P_ESTADO", p.getVestado());
            parametros.put("P_REMITENTE", p.getVdirigido());
            String nombre = null;
            // System.out.println("Tipo Origen:"+p.getNorigen());
            // System.out.println("Cod Trabaj:"+ p.getFicha_dirigido());
            // System.out.println("Cod Remitente:" + p.getNdirigido());
            // System.out.println("Cod Representante:" + p.getNremitente());
            String nomb = null;
            if (p.getNorigen() == 1) {
                // obtengo nombre del trabajador
                List<TrabajadorBean> trabajador = entranteDerivadoDAO.trabajadorSolo(p
                        .getFicha_dirigido());
                if (!trabajador.isEmpty()) {
                    nomb = trabajador.get(0).getNombre_completo();
                } else {
                    nomb = "";
                }

            } else {

                // obtengo nombre del representante
                List<RepresentanteBean> representante = entranteDerivadoDAO
                        .representante(p.getNdirigido());
                if (!representante.isEmpty()) {
                    nomb = representante.get(0).getVnombre();
                } else {
                    nomb = "";
                }
            }

            parametros.put("P_REMITIDO", nomb);

            reporte.asignar("reporteEntrantes.pdf", ec, parametros,
                    "reportes/reportSeguimiento.jasper",
                    mBeanSeguimiento.getSeguimiento());// se le puede adicionar
            // parametros...
            recursoReport = reporte;
        } else {
            //System.out.println("Blanco");
            reporte.asignar("ticket.pdf", ec, parametros,
                    "reportes/blanco.jasper");// se le puede adicionar
            // parametros...
            recursoReport = reporte;

        }

        return recursoReport;

    }

    public void setRecursoReport(RecursoReport recursoReport) {
        this.recursoReport = recursoReport;
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

    public long getNcorrelativoN() {
        return ncorrelativoN;
    }

    public void setNcorrelativoN(long ncorrelativoN) {
        this.ncorrelativoN = ncorrelativoN;
    }

    public long getNcorrelativoA() {
        return ncorrelativoA;
    }

    public void setNcorrelativoA(long ncorrelativoA) {
        this.ncorrelativoA = ncorrelativoA;
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

    public String getVtipoA() {
        return vtipoA;
    }

    public void setVtipoA(String vtipoA) {
        this.vtipoA = vtipoA;
    }

	public String getPrioridadllave() {
		return prioridadllave;
	}

	public void setPrioridadllave(String prioridadllave) {
		this.prioridadllave = prioridadllave;
	}

	public String getSistemaN() {
		return sistemaN;
	}

	public void setSistemaN(String sistemaN) {
		this.sistemaN = sistemaN;
	}

	public int getNfolioN() {
		return nfolioN;
	}

	public void setNfolioN(int nfolioN) {
		this.nfolioN = nfolioN;
	}

	public String getSistemaA() {
		return sistemaA;
	}

	public void setSistemaA(String sistemaA) {
		this.sistemaA = sistemaA;
	}

	public int getNfolioA() {
		return nfolioA;
	}

	public void setNfolioA(int nfolioA) {
		this.nfolioA = nfolioA;
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

	public String getTipodocN() {
		return tipodocN;
	}

	public void setTipodocN(String tipodocN) {
		this.tipodocN = tipodocN;
	}

	public String getVnumero_loteN() {
		return vnumero_loteN;
	}

	public void setVnumero_loteN(String vnumero_loteN) {
		this.vnumero_loteN = vnumero_loteN;
	}

	public String getVnumero_discoN() {
		return vnumero_discoN;
	}

	public void setVnumero_discoN(String vnumero_discoN) {
		this.vnumero_discoN = vnumero_discoN;
	}

	public String getTipodocA() {
		return tipodocA;
	}

	public void setTipodocA(String tipodocA) {
		this.tipodocA = tipodocA;
	}

	public String getVnumero_loteA() {
		return vnumero_loteA;
	}

	public void setVnumero_loteA(String vnumero_loteA) {
		this.vnumero_loteA = vnumero_loteA;
	}

	public String getVnumero_discoA() {
		return vnumero_discoA;
	}

	public void setVnumero_discoA(String vnumero_discoA) {
		this.vnumero_discoA = vnumero_discoA;
	}

	public Date getDfecdiscoN() {
		return dfecdiscoN;
	}

	public void setDfecdiscoN(Date dfecdiscoN) {
		this.dfecdiscoN = dfecdiscoN;
	}

	public Date getDfecdiscoA() {
		return dfecdiscoA;
	}

	public void setDfecdiscoA(Date dfecdiscoA) {
		this.dfecdiscoA = dfecdiscoA;
	}
    
    
    
}
