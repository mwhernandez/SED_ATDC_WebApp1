package com.sedapal.tramite.mbeans;

import java.awt.event.FocusEvent;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.icefaces.tutorial.facelets.NavigationBean;
import com.j2solutionsit.fwk.patterns.jsf.bean.BaseSortableList;
import com.sedapal.tramite.beans.Anexo;
import com.sedapal.tramite.beans.Area;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.SeguimientoBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.Seguir;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.DocumentoSalidaDAO;
import com.sedapal.tramite.dao.EntranteDAO;
import com.sedapal.tramite.dao.SecuencialDAO;
import com.sedapal.tramite.dao.SeguimientoEntranteDAO;
import com.sedapal.tramite.mail.CustomerService;
import com.sedapal.tramite.nova.util.AcuseReport;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.nova.util.RecursoReporteNuevo;
import com.sedapal.tramite.nova.util.RecursoReporte;
import com.sedapal.tramite.servicios.IServiciosEntrante;
import com.sedapal.tramite.servicios.IServiciosSeguimientoEntrante;
import com.sedapal.tramite.servicios.IServiciosSelection;
import com.sedapal.tramite.tree.TreeController;
import com.sedapal.tramite.util.Utils;


public class MBeanEntrantes extends BaseSortableList implements IMBeanEntrantes, Serializable {


	private boolean entrada = false;
    private boolean salida = false;
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
    private RecursoReporteNuevo reporteNuevo; // inyectado 
    private RecursoReporteNuevo recursoReporteNuevo; // para displayar
    private AcuseReport reporteAcuse;
    private AcuseReport acuseReciboReporte;
    
    private LinkedHashMap<String, String> tiposCombo = new LinkedHashMap<String, String>();
    
    private Map<String, Object> parametros = new HashMap<String, Object>();
    private ExternalContext ec = null;
    private Map<String, Object> parametros2 = new HashMap<String, Object>();
    private ExternalContext ec2 = null;
    private Map<String, Object> parametros3 = new HashMap<String, Object>();
    private ExternalContext ec3 = null;
    
    Calendar c = Calendar.getInstance();    
    // inject
    private TreeController treeController;
    private List<EntranteBean> entrantes;
    private List<AreaBean> areas;
    private List<SeguimientoEntranteBean> seguimiento;
    private List<SelectionBean> selection;
    public IServiciosEntrante serviciosEntrante;
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
    private List items10;
    private List items11;
    private List itemscombo;
    // control de seleccion multiple
    private LinkedHashMap<String, String> items7a = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> items7b = new LinkedHashMap<String, String>();
    private String[] items7aSelected;
    private String[] items7bSelected;
    private List itemsseleccionado;
    private Date date1 = new Date();
    private Date date2 = new Date();
    
    private int li_recepcion;
    private String inicio;
    private String fin;
    
    private String itemSeleccionado;
    private String item2Seleccionado;
    private String itemareaSeleccionado;
    private String error = "Se grabó Satisfactoriamente";
    // private String mensaje = "Transacción no Valida";
    // para el formulario nuevo
    private int nanoN;
    private String origenN;
    private String vtipodocN;
    private long ncorrelativoN;
    // private int ncorrelativoN;
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
    private String vtipoN;
    private String sistemaN="SEDAPAL";
    private int nfolioN;
    private String vtipoorigenN;
    private String vnumero_loteN;
	private String vnumero_discoN;
	private Date dfecdiscoN;
	private long nis_radN;
    // para Actualizar   
    private int nanoA;
    private int norigenA;
    private String origenA;
    private String vtipodocA;
    private long ncorrelativoA;
    // private int ncorrelativoA;
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
    private Date dfecrecepcionA;
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
    private String dirigidoAA;
    private String area_origenA;
    private String centroA;
    private int ncodarea_origenA;
    private long ficha_derivadoA;
    private int nrepresentanteA;
    private String areaseleccionadaA;
    private String vtipoA;
    private String sistemaA;
    private int nfolioA;
    private String vtipoorigenA;
    private String vnumero_loteA;
	private String vnumero_discoA;
	private Date dfecdiscoA;
	private String vnumdnirucA;
	private Date dfeclineaA;
	private long nis_radA;
    ////////
    private boolean ver = false;
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean cerrarVentanta = false;
    private boolean activo = true;
    private boolean verImpresion = false;
    //private boolean disPrint = false;
    // binding al combo
    private HtmlSelectOneMenu combo;
    private HtmlSelectOneMenu comboarea;
    
    private String desc;
    //private Logger logger = Logger.getLogger("R1");
    private boolean verDetalles = false;
    private boolean verCatalogoEntrada = false;
    private HtmlInputText botonCodigo;
    private boolean verPDF = false;
    private String rutaPDFPopup;
    
    
    // entidades externas
    private String empresa;
    private String login;
    private int area_remite;
    String area;
    String area_deriva;
    String codigo_representante;
    String origen;
    int origenes;
    int area_derivador;
    int areas_remite;
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
    String ubicacion_seguimiento;
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
    private String textoA;    
    private String prioridadllave;
    private String asuntollave;
    private String ubicacionllave;
    private int indremitentellave;
    
    //
    String valores = "";
    int areas_actuales;
    //para quitar areas
    public String quitarareas = "";
    private MBeanSegDocuEntrates mBeanSeguimiento;
    private MBeanDocumentosSalientes mBeandocumentosalida; 
    public IServiciosSeguimientoEntrante serviciosseguimientoentrante;
    private EntranteDAO entranteDAO;
    private SeguimientoEntranteDAO seguimientodocumentoDAO;
    private DocumentoSalidaDAO documentoSalidaDAO;
    private LinkedHashMap<String, Object> observaciones0;
    private List<AreaBean> areasOrigen = new ArrayList<AreaBean>();
    private List<Seguir> seleccionados = Collections.synchronizedList(new ArrayList<Seguir>());
    private List<AreaBean> areasseleccionados = Collections.synchronizedList(new ArrayList<AreaBean>());
    private String estadoSeleccionado = "TO";
    private boolean disBotonGrabar = false;
    private boolean disBotonAgregar = false;
    private boolean disBotonQuitar = false;
    ///Autocompletar Eli Diaz Horna 26/03/2012
    private List<SelectItem> posiblespersonas;
    private String personaSeleccionada;
    private String labelPersona;
    private long ficha = 0;
  ///AutocompletarA Eli Diaz Horna 26/03/2012
    private List<SelectItem> posiblespersonasA;
    private String personaSeleccionadaA;
    private String labelPersonaA;
    private long fichaA = 0;
    private String derivadoA;
    private String derivadoAA;
    private boolean disBoton = false;
    private boolean disBotonRemitente = true;
    
    //Alertas del Documentos con plazo 29/09/2016    
    private List<EntranteBean> listadocalertas;
    private boolean verdocs;
    
    
 // para los asuntos estadares
 	private List<TiposBean> itemsAsuntos;
 	private List<TiposBean> asuntos;
    private boolean verasuntosdocs;
    private String tipoopcion;
    private boolean disverestado = true;
    private boolean disverremite = true;
    private String opcion_asunto;
    private String detalle_asunto;
    
  //variable direccion archivo pdf
    private String ls_ubicacion;
  //SED-FON-161 INI
  	private Long numeroBPM;
  	private int nIndRemitente;
  	//agrega Eli Diaz 18/05/2023
  	private String vnumexp;


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
        observaciones0.put("08.-Su Aprobación", "18");
        observaciones0.put("09.-Tener Pendiente", "19");
        observaciones0.put("10.-Resolver", "20");
        observaciones0.put("11.-Archivar", "21");
        observaciones0.put("12.-Para Directorio", "22");
        observaciones0.put("13.-Para  trámite", "23");
        observaciones0.put("14.-Contestar directamente", "24");
        observaciones0.put("15.-Otros", "25");
        // ADD CF AGO 2011
        reporte.asignar("ticket.pdf", ec, parametros, "reportes/blanco.jasper");//
        reporteNuevo.asignar("ticket2.pdf", ec2, parametros2, "reportes/blanco.jasper");
        reporteAcuse.asignar("ticket.pdf", ec, parametros, "reportes/blanco.jasper");
      
        //reportes.asignar("ticket3.pdf", ec3, parametros3, "reportes/blanco.jasper");//		
        //reportenuevo.asignar("ticket3.pdf", ec3, parametros3, "reportes/blanco.jasper");//
        recursoReport = reporte;
        //recursoReporte = reportes;
        recursoReporteNuevo = reporteNuevo;
        //recursoReporteNuevo = reportenuevo;
        acuseReciboReporte = reporteAcuse;

    }
    
    public void eventVisualizar(EntranteBean event) {
    	DocumentoSalidaBean doc = this.seguimientodocumentoDAO.getDocSalByRegistroEntrada(event.getNcorrelativo() + "-" + event.getNano(), event.getVnumdoc(), false);
    	if (doc == null || doc.getCodigo() == 0) {
    		doc = this.seguimientodocumentoDAO.getDocSalByNumDoc(event.getVnumdoc(), false);
		}
    	
    	if (doc != null && doc.getCodigo() != 0) {
    		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            
    		System.out.println("Visualizando documento... " + doc.getCodigo() +  " - " + doc.getOrigen() + " - " + doc.getAno() + " - " + doc.getTipodoc() + " - " + doc.getArea() + 
    		 " - " + usuario.getLogin());
        	this.documentoSalidaDAO.guardarVisualizacion(doc.getCodigo(), doc.getAno(), String.valueOf(doc.getOrigen()), doc.getTipodoc(), String.valueOf(doc.getArea()), usuario.getLogin());
		}
    }
    
    public void eventAcuse(EntranteBean event, boolean recibido) {
    	DocumentoSalidaBean doc = this.seguimientodocumentoDAO.getDocSalByRegistroEntrada(event.getNcorrelativo() + "-" + event.getNano(), event.getVnumdoc(), false);
    	if (doc == null || doc.getCodigo() == 0) {
    		doc = this.seguimientodocumentoDAO.getDocSalByNumDoc(event.getVnumdoc(), false);
		}
    	
    	if (doc != null && doc.getCodigo() != 0) {
    		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            
    		System.out.println("Acuse documento... " + doc.getCodigo() +  " - " + doc.getOrigen() + " - " + doc.getAno() + " - " + doc.getTipodoc() + " - " + doc.getArea() + 
    		 " - " + usuario.getLogin());
        	
    		this.documentoSalidaDAO.guardarAcuseRecibo(doc.getCodigo(), doc.getAno(), String.valueOf(doc.getOrigen()), 
    				doc.getTipodoc(), String.valueOf(doc.getArea()), usuario.getLogin(), recibido, 
    				(int) event.getNcorrelativo(), event.getNano(), usuario.getCodarea());
    		event.setAcuse(false);
    		
    		if (!recibido) {
				eventRefrescar();
			}
    	}
    }
    
    public void eventVerDocs(ActionEvent actionEvent) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_login = String.valueOf(usuario.getCodarea());
        this.verasuntosdocs = true;
     
        this.asuntos = entranteDAO.asuntos_estandares();
        List entrante = this.asuntos;
        this.itemsAsuntos = entrante;
        this.opcion_asunto = "";
        this.detalle_asunto = "";
    }
    
    
    public void eventtipoconsulta(ValueChangeEvent event){
    	
    	String opcion = (String) event.getNewValue();
    	
    	if (opcion.equals("CONS00")){  
    		System.out.println("Estoy" + opcion);
    		this.disverestado = true;
    		this.disverremite = true;
    		//this.verImpresion = false;
    		
    	} else if(opcion.equals("CONS01")) {
    		System.out.println("Estoy" + opcion);
    		this.disverestado = false;
    		this.disverremite = true;
    		//this.verImpresion = true;
    		
    	} else {
    		System.out.println("Estoy" + opcion);
    		this.disverestado=true;
    		this.disverremite=false;
    		//this.verImpresion = false;
    		
    	}
    	
    }
    
    
    public void cerrarDocs(ActionEvent actionEvent) {
    	TiposBean bean = null;
        for (TiposBean p : this.itemsAsuntos) {
            if (p.isSelected()) {
                bean = p;
                
                break;
            }
        }
        
        if (bean != null) {
            long correlativo;
            this.vasuntoN = bean.getDescripcion();
            this.vasuntoA = bean.getDescripcion();
            //this.doc_entradaN = String.valueOf(bean.getNcorrelativo());
            //this.doc_entradaA = String.valueOf(bean.getNcorrelativo());            
            //correlativo = bean.getNcorrelativo();
           
            this.verasuntosdocs = false;
         
        }

    }
    
    public void buscarAsunto(ActionEvent actionEvent) {
    	if (this.detalle_asunto.equals("") || this.opcion_asunto.equals("")) {
			 this.error = "Debe de Ingresar una opcion de busqueda";
	         this.ver = true;
		 }	else {
	        this.asuntos = entranteDAO.busqueda_asuntos_estandar(opcion_asunto, detalle_asunto);
	        List entrante = this.asuntos;
	        this.itemsAsuntos = entrante;
		 }

    }
	
	public void RefrescarAsunto(ActionEvent actionEvent){
		
		 this.asuntos = entranteDAO.asuntos_estandares();
	     List entrante = this.asuntos;
	     this.itemsAsuntos = entrante;
	     this.detalle_asunto = "";
		
	}
    

    // Autocompletar ------Inserta Eli Diaz	 26/03/2012
    public void autocompletarPersona(ValueChangeEvent changeEvent) {
        //Se comprueba la instancia del objeto input
        if (changeEvent.getComponent() instanceof SelectInputText) {
            //se extrae la instancia del componente Selectinputtext
            SelectInputText autoComplete = (SelectInputText) changeEvent.getComponent();
            //Atraves del evento se extrae lo que se digito en el SelectInputText
            String cadenaDigitada = (String) changeEvent.getNewValue();
            //Se aáade a la propiedad posiblepersonas lo que devuelve
            //el metodo buscaPersona
            //System.out.println("Imprime Eli DIaz");
            //System.out.println(cadenaDigitada);
            this.posiblespersonas = buscaPersona(cadenaDigitada);


            //en el if se comprueba si existe alguna seleccion
            if (autoComplete.getSelectedItem() != null) {
                //Se extrae el objeto seleccionado
                RemitenteBean personaSel = (RemitenteBean) autoComplete.getSelectedItem().getValue();
                // a labelPersona la modificamos segun lo seleccionado
                //this.labelPersona = "La persona seleccionada es : " + personaSel.getApellido() + " " + personaSel.getNombre();        	
                //this.labelPersona = "La persona seleccionada es : " + personaSel.getDescripcion();
                this.vremitenteN = Integer.toString(personaSel.getCodigo());
                area_remite = personaSel.getCodigo();
                //vremitenteN
                //List<RemitenteBean> remitentes = entranteMesaDAO.remitentes();
                //List itemsRemitentes = Utils.getRemitentes(remitentes);
                //this.items1 = itemsRemitentes;
                if (origenes == 1) {
                    //AreaBean personaSel = (AreaBean) autoComplete.getSelectedItem().getValue();
                    //this.vremitenteN = Integer.toString(personaSel.getCodigo());
                    //area_remite = personaSel.getCodigo();
                    if (!changeEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                        changeEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                        changeEvent.queue();
                        return;
                    }

                    List<TrabajadorBean> trabajador = entranteDAO.trabajador(area_remite);
                    List itemsTrabajador = Utils.getTrabajador(trabajador);
                    //this.items3 = itemsTrabajador;

                    List<JefeBean> bean = entranteDAO.jefe(area_remite);
                    for (JefeBean e : bean) {
                        ficha = e.getFicha();
                    }
                    this.items3 = itemsTrabajador;
                    this.ficha_dirigidoN = ficha;
                    //System.out.println("Eli esta es la ficha?????");
                   //System.out.println(ficha);

                } else {
                    //RemitenteBean personaSel = (RemitenteBean) autoComplete.getSelectedItem().getValue();
                    ///this.vremitenteN = Integer.toString(personaSel.getCodigo());
                    //area_remite = personaSel.getCodigo();

                    List<RepresentanteBean> remite = entranteDAO.representante(area_remite);
                    List itemsrepresentante = Utils.getRepresentante(remite);
                    this.items3 = itemsrepresentante;
                }
            }
        }
    }
    
    // Autocompletar cuando se modifica un documento de entrada------Inserta Eli Diaz	 30/10/2012
    public void autocompletarPersonaA(ValueChangeEvent changeEvent) {
        //Se comprueba la instancia del objeto input
        if (changeEvent.getComponent() instanceof SelectInputText) {
            //se extrae la instancia del componente Selectinputtext
            SelectInputText autoComplete = (SelectInputText) changeEvent.getComponent();
            //Atraves del evento se extrae lo que se digito en el SelectInputText
            String cadenaDigitada = (String) changeEvent.getNewValue();
            //Se aáade a la propiedad posiblepersonas lo que devuelve
            //el metodo buscaPersona
            //System.out.println("Imprime Eli DIaz");
            //System.out.println(cadenaDigitada);
            this.posiblespersonasA = buscaPersonaA(cadenaDigitada);


            //en el if se comprueba si existe alguna seleccion
            if (autoComplete.getSelectedItem() != null) {
                //Se extrae el objeto seleccionado
                RemitenteBean personaSelA = (RemitenteBean) autoComplete.getSelectedItem().getValue();
                // a labelPersona la modificamos segun lo seleccionado
                //this.labelPersona = "La persona seleccionada es : " + personaSel.getApellido() + " " + personaSel.getNombre();        	
                //this.labelPersona = "La persona seleccionada es : " + personaSel.getDescripcion();
                this.vremitenteA = Integer.toString(personaSelA.getCodigo());                 
                area_remite = personaSelA.getCodigo();
                origenes= this.norigenA;
                //this.derivadoAA=this.vremitenteA;
                //System.out.println("Eli quiere ver el origem");
                //System.out.println(origenes);
                //System.out.println(this.norigenA);
                
                //vremitenteN
                //List<RemitenteBean> remitentes = entranteMesaDAO.remitentes();
                //List itemsRemitentes = Utils.getRemitentes(remitentes);
                //this.items1 = itemsRemitentes;
                if (origenes == 1) {                   
                    if (!changeEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                        changeEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                        changeEvent.queue();
                        return;
                    }
                    
                    ///Eli Agrega este codigo 14/11/2012
                    // ListaPersona = entranteDAO.personaIntena(cadenaDigitada);                    
                    //List<AreaBean> areas = entranteDAO.areas();
                    List<AreaBean> areas = entranteDAO.areasA(area_remite);
                    List itemsAreas = Utils.getAreas(areas);
                    this.items1 = itemsAreas;

                    List<TrabajadorBean> trabajador = entranteDAO.trabajador(area_remite);
                    List itemsTrabajador = Utils.getTrabajador(trabajador);
                    //this.items3 = itemsTrabajador;

                    List<JefeBean> bean = entranteDAO.jefe(area_remite);
                    for (JefeBean e : bean) {
                        ficha = e.getFicha();
                    }
                    this.items3 = itemsTrabajador;
                    this.ficha_dirigidoA = ficha;
                    this.vremitenteA=String.valueOf(ficha);
                    //System.out.println("Eli esta es la ficha?????");
                   //System.out.println(ficha);

                } else {
                	///Eli Agrega este codigo 14/11/2012
                	List<RemitenteBean> remitente = entranteDAO.remitentesA(area_remite);
                    List itemsAreas = Utils.getRemitentes(remitente);
                    this.items1 = itemsAreas;

                    List<RepresentanteBean> remite = entranteDAO.representante(area_remite);
                    List itemsrepresentante = Utils.getRepresentante(remite);
                    this.items3 = itemsrepresentante;
                }
            }
        }
    }
    
    public void eventAlerta(ActionEvent actionEvent) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_login = String.valueOf(usuario.getCodarea());
        this.verdocs = true;
        this.listadocalertas = entranteDAO.AlertasEntradaSP(area_login);
        
    }

    public void eventBusquedasAlertas(ActionEvent actionEvent) {
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        
        String area_login = String.valueOf(usuario.getCodarea());
        /*eli comenta 2016
        this.entradasalida = documentoSalidaDAO.busquedadocumentoentradaSP(
                area_login, opcion_entrada, detalle_entrada);
        List entrante = this.entradasalida;
        this.items4 = entrante;
        */

    }

    public List<SelectItem> buscaPersona(String cadenaDigitada) {
        //inicializamos un objeto del tipo SelectItem    
        List<SelectItem> list = new ArrayList<SelectItem>();
        // List<Persona> list = new ArrayList<Persona>();  
        List<RemitenteBean> ListaPersona = new ArrayList<RemitenteBean>();
        //System.out.println("Imprime Eli DIaz Paso 222");
        //System.out.println(cadenaDigitada);
        if (origenes == 1) {
            if (cadenaDigitada.length() >= 1) {
                //System.out.println("Interno");
                ListaPersona = entranteDAO.personaIntena(cadenaDigitada);
            }
        } else {
            if (cadenaDigitada.length() >= 4) {
                //System.out.println("Externo");
                //ListaPersona = entranteDAO.persona(cadenaDigitada);
            	ListaPersona = entranteDAO.personaExterna(cadenaDigitada);
            }
        }
        // inicializamos un bucle for para recorrer el objeto persona     
        for (RemitenteBean p : ListaPersona) {
            //for (Persona p : Persona) {   
            //System.out.println(p.getCodigo());	
            //System.out.println(p.getDescripcion());
            // preparamos la cadena a comparar y la pasamos a minusculas    
            String cadenaPersona = (String.valueOf(p.getCodigo()) + "" + p.getDescripcion()).toLowerCase();
            cadenaDigitada = cadenaDigitada.toLowerCase();
            //con el metodo indesof del estring verificamos si la   
            // cadenaDigitada esta contenida el la cadenaPersona, que con-    
            //tiene el apellido y nombre de la Persona    
            if (cadenaPersona.indexOf(cadenaDigitada) >= 0) {
                SelectItem item = new SelectItem(p, p.getDescripcion());
                list.add(item);
            }

        }
        //persona = ListaPersona;
        return list;


    }
    
    public List<SelectItem> buscaPersonaA(String cadenaDigitada) {
        //inicializamos un objeto del tipo SelectItem    
        List<SelectItem> list = new ArrayList<SelectItem>();
        // List<Persona> list = new ArrayList<Persona>();  
        List<RemitenteBean> ListaPersona = new ArrayList<RemitenteBean>();
        //System.out.println("Imprime Eli DIaz Paso 222");
        //System.out.println(cadenaDigitada);       
        if (this.norigenA == 1) {
            if (cadenaDigitada.length() >= 1) {
                //System.out.println("Interno");
                ListaPersona = entranteDAO.personaIntena(cadenaDigitada);
                
            }
        } else {
            if (cadenaDigitada.length() >= 2) {
                //System.out.println("Externo");
                ListaPersona = entranteDAO.personaExterna(cadenaDigitada);
                
            }
        }
        // inicializamos un bucle for para recorrer el objeto persona     
        for (RemitenteBean p : ListaPersona) {
            //for (Persona p : Persona) {   
            //System.out.println(p.getCodigo());	
            //System.out.println(p.getDescripcion());
            // preparamos la cadena a comparar y la pasamos a minusculas    
            String cadenaPersona = (String.valueOf(p.getCodigo()) + "" + p.getDescripcion()).toLowerCase();
            cadenaDigitada = cadenaDigitada.toLowerCase();
            //con el metodo indesof del estring verificamos si la   
            // cadenaDigitada esta contenida el la cadenaPersona, que con-    
            //tiene el apellido y nombre de la Persona    
            if (cadenaPersona.indexOf(cadenaDigitada) >= 0) {
                SelectItem item = new SelectItem(p, p.getDescripcion());
                list.add(item);
            }

        }
        //persona = ListaPersona;
        
        return list;
    }

    public void pasarItems(ActionEvent actionEvent) {

        Seguir seguir = new Seguir();
        //llenar el objeto seguir....
        //seguir.setArea(this.area);
        seguir.setCodArea(this.dirigidoN);
        seguir.setFicha("1000");
        seguir.setEstado(this.estadoSeleccionado);//se muestra en la tabla
        seguir.setNombreTrabajador(this.dirigidoN);//se muestra en la tabla
        //seguir.setArea(this.area_deriva);
        SelectItem item = null;
        if (this.estadoSeleccionado != "") {
            //adiconando el objeto en la tabla
            boolean ok = false;
            ok = this.buscar(seguir, this.seleccionados);
            if (!ok) {
                for (int i = 0; i < this.items1.size(); i++) {
                    item = (SelectItem) this.items1.get(i);
                    String area = item.getValue().toString();
                    if (area.equals(this.dirigidoN)) {
                        //seguir.setArea(item.getLabel());
                        //break;
                        seguir.setNombreTrabajador(item.getLabel());
                        this.seleccionados.add(seguir);
                    }
                }
            }
        } else {
            this.error = "Seleccione un tipo Destinatario!!";
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

    public void quitarItems(ActionEvent actionEvent) {
        //quita elemento seleccionado de la tabla
        Seguir seguir = null;
        for (Seguir p : this.seleccionados) {
            if (p.isSelected()) {
                seguir = p;
            }
        }
        if (seguir == null) {
            this.error = "Seleccione item.";
            this.ver = true;
        } else {
            this.seleccionados.remove(seguir);
            List<AreaBean> areas = entranteDAO.areas();
            List itemsAreas = Utils.getAreas(areas);
            this.items1 = itemsAreas;
        }


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

    public boolean buscar(Seguir seguir, List<Seguir> list) {
        boolean ok = false;
        for (Seguir p : list) {
            if (p.getCodArea().equals(seguir.getCodArea()) && p.getFicha().equals(seguir.getFicha())) {

                ok = true;

            }
        }
        return ok;
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

    public void confirmaBorrado(ActionEvent actionEvent) {
        //se copia exactamente igual de eventEliminar
        EntranteBean p = null;
        for (EntranteBean q : this.entrantes) {
            if (q.isSelected()) {
                p = q;
            }
        }
        if (p == null) {
            this.error = "Debe seleccionar por lo menos un registro";
            this.ver = true;
        } else {
            HttpSession session = (HttpSession) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getSession(false);
            // ///guardando en sesion un objeto
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
            anno = String.valueOf(p.getNano());
            origen = String.valueOf(p.getNorigen());
            tipodoc = p.getVtipodoc();
            ncodarea = String.valueOf(p.getNcodarea());
            codigo = String.valueOf(p.getNcorrelativo());
            // le paso como parametro al stored
            String out = serviciosEntrante.eliminarEntrante(anno, origen, tipodoc, codigo, login);
            out = out.trim();
            //System.out.println("OUT STORED!!!:" + out);
            if (out.equals("0")) {
                this.error = "Registro Eliminado!";
                selectedEntrantes.clear();
                this.entrantes = serviciosEntrante.catalogo(ncodarea);// actualiza
                verificarAnexos();
                //el
                // 	reporte
                this.ver = true;
                this.verAlertaConfirmacionBorrar = false;
            } else {
                this.error = "Registro no es posible Eliminar,Lo Debe Eliminar el Area que generó el registro!";
                this.ver = true;
                this.verAlertaConfirmacionBorrar = false;
            }

        }
    }

    public void cancelaBorrado(ActionEvent actionEvent) {
        this.verAlertaConfirmacionBorrar = false;
    }

    public void eventLimpiar(ActionEvent actionEvent) {
    	this.items1.clear();
        this.items3.clear();
        this.personaSeleccionada = "";
    }
    
    public void eventLimpiarA(ActionEvent actionEvent) {
    	this.disBoton=true;
    	this.disBotonRemitente=false;
        this.items3.clear();
        this.personaSeleccionada = "";
        this.personaSeleccionadaA ="";
    }

    public void eventEliminar(ActionEvent event) {
        //se adiciona estas dos lineas
        this.msg = "Realmente desea eliminarlo?";
        this.verAlertaConfirmacionBorrar = true;
        // se comenta todo esto ...
		/*EntranteBean p = null;
         for (EntranteBean q : this.entrantes) {
         if (q.isSelected())
         p = q;
         }
         if (p==null) {
         this.error = "Debe seleccionar por lo menos un registro";
         this.ver = true;
         } else {
         HttpSession session = (HttpSession) FacesContext
         .getCurrentInstance().getExternalContext()
         .getSession(false);
         // ///guardando en sesion un objeto
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
         anno = String.valueOf(p.getNano());
         origen = String.valueOf(p.getNorigen());
         tipodoc = p.getVtipodoc();
         ncodarea = String.valueOf(p.getNcodarea());
         codigo = String.valueOf(p.getNcorrelativo());
         // le paso como parametro al stored
         serviciosEntrante.eliminarEntrante(anno, origen, tipodoc,
         codigo, login);			
         this.error = "Registro Eliminado!";
         selectedEntrantes.clear();
         this.entrantes = serviciosEntrante.catalogo(ncodarea);// actualiza
         // el
         // reporte
         this.ver = true;
         }
         */
    }

    public void eventoTab(TabChangeEvent event) {
        EntranteBean bean = null;
        //System.out.println("Estoy aqui Eli Diaz");

        for (EntranteBean p : this.entrantes) {
            if (p.isSelected()) {
                bean = p;
            }
        }
        if (event.getNewTabIndex() == 1 || event.getNewTabIndex() == 2
                && bean != null) {
            // clear our list, so that we can build a new one
            selectedEntrantes.clear();

            /*
             * If application developers rely on validation to control
             * submission of the form or use the result of the selection in
             * cascading control set up the may want to defer procession of the
             * event to INVOKE_APPLICATION stage by using this code fragment if
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
                this.asuntollave = p.getVasunto();
                this.ubicacionllave = p.getUbicacion_entrada();
                this.indremitentellave = p.getnIndRemitente();
                
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
                beans.setAsunto_documento(this.asuntollave);
                beans.setUbicacion(this.ubicacionllave);
                beans.setIndremitente(this.indremitentellave);
                
                System.out.println("Pinto las llaves que paso");
                System.out.println(this.correlativollave);
                System.out.println(this.prioridadllave);
                System.out.println(this.indremitentellave);
                 
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
                this.treeController.inicializarArbol(String.valueOf(p
                        .getNcorrelativo()), String.valueOf(p.getNano()));
                // this.vestadoA = p.getNestado();
                ruta = p.getVubiarchivo();
                this.ind_adjuntar = p.getNindicador();
                this.vubiarchivoA = p.getVubiarchivo();
                //this.eventRefrescar();

            }
            /*
             * If application developers do not rely on validation and want to
             * bypass UPDATE_MODEL and INVOKE_APPLICATION stages, they may be
             * able to use the following statement:
             * FacesContext.getCurrentInstance().renderResponse(); to send
             * application to RENDER_RESPONSE phase shortening the app. cycle
             */

        }

        if (event.getNewTabIndex() != 3) {
            mBeanSeguimiento.resetAnexos();	
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
            this.asuntollave = p.getVasunto();
            this.ubicacionllave = p.getUbicacion_entrada();
            this.indremitentellave = p.getnIndRemitente();
            
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
            beans.setAsunto_documento(this.asuntollave);
            beans.setUbicacion(this.ubicacionllave);
            beans.setIndremitente(this.indremitentellave);
            //System.out.println(this.anollave);
            //System.out.println(this.origenllave);
            //System.out.println(this.tipodocllave);
            //System.out.println(this.correlativollave);
            //System.out.println(this.numdocllave);
            //System.out.println(this.fechaplazollave);
            //System.out.println(this.diasplazollave);
            //System.out.println(this.fecharecepcionllave);
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
            this.treeController.inicializarArbol(
                    String.valueOf(p.getNcorrelativo()),
                    String.valueOf(p.getNano()));
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

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        EntranteBean p = null;
        for (EntranteBean q : this.entrantes) {
            if (q.isSelected()) {
                p = q;
            }
        }

        if (p != null) {
            this.disBotonAgregar = true;
            this.disBotonQuitar = true;
            this.verActualizar = true;
            this.verCatalogo = false;
            this.nanoA = p.getNano();
            this.origenA = String.valueOf(p.getNorigen());
            this.norigenA = p.getNorigen();
            this.vtipodocA = p.getVtipodoc();
            tipodoc = p.getVtipodoc();
            this.ncorrelativoA = p.getNcorrelativo();
            this.vnumdocA = p.getVnumdoc();
            this.vmesaparteA = p.getVmesaparte();
            // System.out.println("Remitente!!!!!:"+p.getNcodarea());
            // Eli Comenta
            // this.vremitenteA = String.valueOf(p.getNcodarea());
            // this.dirigidoA = String.valueOf(p.getNdirigido());
            this.vremitenteA = String.valueOf(p.getNdirigido());
            this.dirigidoA = String.valueOf(p.getNcodarea());
            // this.areaseleccionadaA = p.getVremitente();
            // this.items7aSelected=p.getVremitente();
            this.ndirigidoA = p.getNdirigido();
            this.dfecdocA = p.getDfecdoc();
            this.dfecregistroA = p.getDfecregistro();
            this.vasuntoA = p.getVasunto();
            this.vreferenciaA = p.getVreferencia();
            this.vobservacionA = p.getVobservacion();
            this.vaccionA = p.getVaccion();
            this.vprioridadA = p.getVprioridad();
            this.vubiarchivoA = p.getVubiarchivo();
            this.dfecplazoA = p.getDfecplazo();
            this.ndiasplazoA = p.getNdiasplazo();
            this.vestadoA = p.getNestado();
            this.nindicadorA = p.getNindicador();
            this.ind_adjuntar = p.getNindicador();;
            this.dfeccreA = p.getDfeccre();
            this.dfecactA = p.getDfecact();
            this.vrescreA = p.getVrescre();
            this.vresactA = p.getVresact();
            this.ncodcentroA = p.getNcodcentro();
            this.ncodareaA = p.getNcodarea();
            this.vareaA = p.getArea();
            this.area_origenA = p.getArea_origen();
            this.sistemaA = p.getSistema();
            this.nfolioA = p.getNfolio();
            this.vnumero_loteA = p.getVlote();
			this.dfecdiscoA = p.getDfecdisco();
			this.vnumero_discoA = p.getVnumero_disco();
			this.vnumdnirucA = p.getVnumdniruc();
			this.dfeclineaA = p.getDfechalinea();
			//this.nis_radA = p.getNis_rad();
           
            //linea insertada por CF
            this.ncodarea_origenA = p.getNcodarea_origen();
            //System.out.println("Cod Area Orgine!!!!: " + this.ncodarea_origenA);
            this.centroA = p.getCentro();
            this.ficha_derivadoA = p.getFicha_derivado();
            this.ficha_derivadoA = p.getNremitente();
            this.ficha_dirigidoA = p.getFicha_dirigido();
            this.textoA = "El Registro tiene " + this.nindicadorA
                    + " Archivo Adjunto";
            this.anollave = this.nanoA;
            this.origenllave = this.norigenA;
            this.tipodocllave = this.vtipodocA;
            this.correlativollave = this.ncorrelativoA;
            
            List<OrigenBean> origen = entranteDAO.origenA();
            List itemsOrigen = Utils.getOrigen(origen);
            this.items11 = itemsOrigen;
            
            
            //this.prioridadllave = this.vprioridadA;
            // System.out.println("Ficha dirigido:"+ p.getFicha_dirigido());
            // this.items7b=
            // Utils.getAreasLink(entranteDAO.areas_seleccionadas(this.anollave,this.origenllave,this.tipodocllave,this.correlativollave));
            //
            List<AreaBean> areas = entranteDAO.areas_seleccionadas(
                    this.anollave, this.origenllave, this.tipodocllave,
                    this.correlativollave);
            //Add CF 05/05/2011
            //Eli comenta ya no se uso 20/11/2012
            /*
            List<AreaBean> area_derivados = entranteDAO.areas();
            this.items7a = Utils.getAreasLink(areas);
            


            areasOrigen = entranteDAO.areas_seleccionadasOrigen(
                    this.anollave, this.origenllave, this.correlativollave,
                    this.ncodarea_origenA);
             */
            //Aóade Eli Diaz
            this.areas = areas;
            this.areasseleccionados = areas;
            System.out.println("quiero ver el tipo_area Eli - 11.07.2023");
            System.out.println(this.vremitenteA);
            int tipo_area = Integer.parseInt(this.vremitenteA);
            this.items7b = Utils.getAreasLink(areas);

            //for (AreaBean q : areas) {
            //	this.items7a.remove(q.getNombre());
            //}
            //this.areas_actuales = this.items7b.size();
            //System.out.println("Areas Actuales!!!!:" + this.areas_actuales);
            // llena las acciones
            String codigo_accion = this.vaccionA;
            String tempo = null;
            int i = 0, j = 0;
            while (i < codigo_accion.length()) {
                tempo = codigo_accion.substring(i, i + 2);
                // asigna al arreglo
                selectedItems1.add(tempo);
                j++;
                i += 2;
            }
            this.vaccionA = tempo;
            // this.observaciones=Utils.getAreasLink(entranteDAO.areas_seleccionadas(this.anollave,this.origenllave,this.tipodocllave,this.correlativollave));
            if (this.norigenA == 1) {
                List<AreaBean> areass = entranteDAO.areas();
                List itemsAreas = Utils.getAreas(areass);
                this.items1 = itemsAreas;


                List<TrabajadorBean> trabajador = entranteDAO
                        .trabajador(this.ndirigidoA);
                List itemsTrabajador = Utils.getTrabajador(trabajador);
                this.items3 = itemsTrabajador;

            } else {
            	
            	if (tipo_area < 601) {
            	
            	//SED-FON-161 INI
//            	   List<AreaBean> areass = entranteDAO.areas();
//                 List itemsAreas = Utils.getAreas(areass);
//                 this.items1 = itemsAreas;
//                 
//                 List<TrabajadorBean> trabajador = entranteDAO
//                         .trabajador(this.ndirigidoA);
//                 List itemsTrabajador = Utils.getTrabajador(trabajador);
//                 this.items3 = itemsTrabajador;
            	if (p.getnIndRemitente() ==2) {
            		 List<RemitenteBean> remitentes = entranteDAO.remitentesBPM(this.ndirigidoA);
                     List itemsderivados = Utils.getRemitentes(remitentes);
                     this.items1 = itemsderivados;
                     List<RepresentanteBean> representante = entranteDAO.representanteBPM(this.ndirigidoA);
                     List itemsrepresentante = Utils.getRepresentante(representante);
                     this.items3 = itemsrepresentante;
            	} else {
            		 List<AreaBean> areass = entranteDAO.areas();
                     List itemsAreas = Utils.getAreas(areass);
                     this.items1 = itemsAreas;
                     
                     List<TrabajadorBean> trabajador = entranteDAO.trabajador(this.ndirigidoA);
                     List itemsTrabajador = Utils.getTrabajador(trabajador);
                     this.items3 = itemsTrabajador;
            	}
            	//SED-FON-161 FIN
            } else {

        		
                //List<RemitenteBean> remitentes = entranteDAO.remitentes();
                //List<RemitenteBean> remitentes = entranteDAO.remitentesSP();
                List<RemitenteBean> remitentes = entranteDAO.remitentesA(this.ndirigidoA);
                List itemsderivados = Utils.getRemitentes(remitentes);
                this.items1 = itemsderivados;
                System.out.println("quiero ver el remitente del bpm 11.07.2023");
                System.out.println(p.getnIndRemitente());
            	if (p.getnIndRemitente() ==2) {
            		List<RemitenteBean> remitentes2 = entranteDAO.remitentesBPM(this.ndirigidoA);
                    List itemsderivados2 = Utils.getRemitentes(remitentes2);
                    this.items1 = itemsderivados2;
            		 List<RepresentanteBean> representante = entranteDAO.representanteBPM(this.ndirigidoA);
                     List itemsrepresentante = Utils.getRepresentante(representante);
                     this.items3 = itemsrepresentante;
            	} else {
            		List<RepresentanteBean> representante = entranteDAO.representante(this.ndirigidoA);
                    List itemsrepresentante = Utils.getRepresentante(representante);
                    this.items3 = itemsrepresentante;
            	}
            	
             }
               
            }

            selectedEntrantes.clear();
            this.error = "Se grabó satisfactoriamente";
            // /acediendo a sesion http
            HttpSession session = (HttpSession) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getSession(false);
            // ///guardando en sesion un objeto
            String ncodarea = "";
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            area = String.valueOf(usuario.getCodarea());
            this.entrantes = serviciosEntrante.catalogo(ncodarea);// actualiza
            verificarAnexos();
            // el
            // reporte

        } else {
            this.error = "Debe seleccionar solo un registro para modificar";
            this.ver = true;
        }

    }

    public void verRemitente(ActionEvent event) {

        // PageContentBean pageContentBean = new PageContentBean();
        // pageContentBean.setMenuContentInclusionFile("./content/MAN/remitente.jspx");
        // branchObject.setMenuContentInclusionFile("./content/MAN/remitente.jspx");
        // pageContentBean.setMenuContentTitle("Argentina");
        // navigation.setSelectedPanel(pageContentBean);
        this.editarEmpresa = false;
        this.empresa = "";
        //this.disBotonGrabar=true;

    }

    public void eventDetalles(ActionEvent evt) {
        // this.limpiarDetalles();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
                
        session.setAttribute("tipodoc", vtipodocN);
        session.setAttribute("indicador", 1);
        ind_adjuntar = 1;
        this.verDetalles = true;

        selectedEntrantes.clear();
        

    }
     
    /* Evento visualizar PDF*/
    /*
    public void evenVerPDF(ActionEvent evt) {
    	 EntranteBean p = null;
    	 Boolean estadoActualizar = false;
         for (EntranteBean q : this.entrantes) {
             if (q.isSelected()) {
                 p = q;
             }
         }
         
         if (p != null){

            this.rutaPDFPopup = p.getUbicacion_entrada();            
            System.out.println("Quiero ver la recepcion");
            System.out.println(p.getNrecepcion());
            
            if (p.getNrecepcion() == 0) {
            	estadoActualizar = entranteDAO.updatevistaPDF(p.getNcorrelativo(), p.getNano());
            }
            
            if(estadoActualizar == true) {
            	//this.verPDF = true;
            	System.out.println("TODO SALIO BIEN");
            }else {
            	 this.error = "Debe seleccionar un registro para visualizar el PDF";
                 this.ver = true;
                 this.verPDF = false;
            }
              
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
    
    public void eventDetallesA(ActionEvent evt) {
        // this.limpiarDetalles();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // OJO vtipodocA jala MEMORANDUM mas no su codigo !!!
		/*List<SecuencialBean> bean = this.secuencialDAO
         .correlativo(this.vtipodocA);
         SecuencialBean secuencialBean = bean.get(0);
         int contador = secuencialBean.getCorrelativo();
         contador++;
         String secuencia = String.valueOf(contador);
         session.setAttribute("nombrePdf", secuencia);*/
        session.setAttribute("tipodoc", this.vtipodocA);
        session.setAttribute("indicador", 1);
        ind_adjuntar = 1;
        this.verDetalles = true;
        for (EntranteBean d : selectedEntrantes) {
        }
        selectedEntrantes.clear();
        // this.productos = serviciosProducto.catalogo();// actualiza el
        // reporte

    }

    public void eventEntrada(ActionEvent evt) {
        this.verCatalogoEntrada = true;
        // for (EntranteBean p : selectedEntrantes) {
        // }
    }

    public void eventActualizarEntrante(ActionEvent event) {
        HttpSession session = null;
        boolean formulario = true;
        try {
            // llama DAO/Stored Para actualizar producto
            EntranteBean entranteBean = new EntranteBean();
            // /acediendo a sesion http
            session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            // ///guardando en sesion un objeto
            Usuario usuario = null;
            // File file = null;
            String file = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            this.ncodareaA = usuario.getCodarea();
            this.vresactA = usuario.getLogin();
            String ncodarea = String.valueOf(usuario.getCodarea());
            String valida = this.validarFormularioActualizar();
            if (valida == null) {
                // controlando el adjuntar
                if (ind_adjuntar == 0) {
                    this.vubiarchivoA = "NADA";
                } else {
                    file = (String) session.getAttribute("file");
                    if (file != null) {
                        nombre_archivo = file;
                        List<ServidorBean> bean = this.entranteDAO.servidor();
                        for (ServidorBean e : bean) {
                            ubicacion = e.getDescripcion();
                            // ubicacion = "http://1.1.194.53/entrada/"; modificado
                            // en parametros
                        }
                        Date date = new Date();
                        String annio = Integer.toString(c.get(Calendar.YEAR));
                        nombre_archivo = annio + nombre_archivo;
                        //this.vubiarchivoA = ubicacion + nombre_archivo;
                        // this.vubiarchivoA=String.valueOf(file.getName());
                        // nuevo parametro para archivos PDF 21/11/2019
                        this.vubiarchivoA = nombre_archivo;
                    }

                }
                //Set set = this.items7b.entrySet();
                //Iterator i = set.iterator();
                //String valor;

                //int cont = 0;
                //System.out.println("Ahora viendo si habrna nuevas:"
                //		+ items7b.size());
                //System.out.println("Areas Actuales antes del hash:"
                //		+ this.areas_actuales);

                // captura areas seleccionadas modificadas o igual
                //this.dirigidoAA = "";
                //while (i.hasNext()) {
                //	cont++;
                //	Map.Entry val = (Map.Entry) (i.next());
                //	valor = val.getValue().toString();
                //	valores = valores + valor;
                //	this.dirigidoAA = valores;
                //}
                //acciones
                String accion = "";
                for (String p : this.selectedItems1) {
                    accion += p;

                }
                //System.out.println("Accion...Eli");
                //System.out.println("accion:" + accion);
                this.vaccionA = accion;

                // le paso como parametro al stored
                entranteBean.setNano(this.nanoA);
                entranteBean.setNorigen(this.norigenA);
                entranteBean.setVtipodoc(this.vtipodocA);
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
                //entranteBean.setVdirigido(this.dirigidoAA);
                entranteBean.setVremitente(this.vremitenteA);
                entranteBean.setNdirigido(this.ncodareaA);
                entranteBean.setDfecregistro(this.dfecregistroA);
                entranteBean.setVrescre(this.vrescreA);
                entranteBean.setSistema(this.sistemaA);
                entranteBean.setNfolio(this.nfolioA);
                entranteBean.setNis_rad(this.nis_radA);
                /// Eli Diaz
                entranteBean.setVdirigido(this.generaAreasA());
                entranteBean.setIndicador(this.generaIndicadorA());
                entranteBean.setQuitarareas(this.quitarareas);

                /*
                 //recuperando temporal
                 String areasTemporal = "";
                 for(Integer p:this.temporal)
                 {				
                 areasTemporal+=p;
                 }
                 System.out.println("Temporal!!!:" + areasTemporal);
                 ////
                 String out = serviciosEntrante.nuevoEntrante(entranteBean);
                 out = out.trim();
                 System.out.println("OUT STORED!!!:"+ out);
                 //
                 */
                String out = serviciosEntrante.actualizarEntrante(entranteBean);
                out = out.trim();
                //System.out.println("OUT STORED!!!:" + out);
                if (out.equals("0")) {
                    this.temporal.clear();
                    this.entrantes = serviciosEntrante.catalogo(ncodarea);// actualiza
                    verificarAnexos();
                    this.error = "Se actualizaron los campos correctamente";
                    nombre_archivo = "";
                    ubicacion = "";
                    this.vubiarchivoA = "";
                    this.quitarareas = "";
                    this.areas.clear();
                    session.setAttribute("file", null);
                    this.selectedItems1.clear();
                    this.ver = true;
                    this.verCatalogo = true;
                    this.verActualizar = false;
                    /*Eli Agrega este codigo*/
                    this.disBoton=false;
                	this.disBotonRemitente=true;
                    // el
                    // reporte
                } else {
                    session.setAttribute("file", null);
                    formulario = false;
                    this.error = valida;
                    this.ver = true;
                    this.verActualizar = true;
                }
            } else {
                formulario = false;
                this.error = valida;
                this.ver = true;
            }
        } catch (Exception exception) {
            //logger.error("[MBeanEntrantes.eventActualizarEntrante]", exception);
            this.error = "Error Interno, consulte con el Administrador";
            this.ver = true;
            this.verActualizar = true;

        } finally {
            this.dirigidoAA = "";
            this.valores = "";
            this.verDetalles = false;
            //ind_adjuntar = 0;
            //if(formulario)
            //session.setAttribute("file", null);
        }


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
        this.nindicadorN = 1;
        this.nindicadorA = 1;
        this.textoA = "El Registro tiene " + this.nindicadorN + " Archivo Adjunto";
    }

    public void eventNuevo(ActionEvent event) {
        this.limpiarNuevo();
        // combo.setLabel("ITEMS");
        this.verNuevo = true;
        this.verCatalogo = false;
    }
    private String itemselect[] = new String[1];

    public void pasaDerecha(ActionEvent actionEvent) {
        itemselect[0] = this.dirigidoN;
        Utils.pasaDerecha(this.items7a, this.items7b, this.itemselect);
    }
    private String itemselectA[] = new String[1];

    public void pasaDerechaA(ActionEvent actionEvent) {
        if (!this.dirigidoA.equals("601") && !this.dirigidoA.equals("100")) {
            itemselectA[0] = this.dirigidoA;
            Utils.pasaDerecha(this.items7a, this.items7b, this.itemselectA);
            int area = Integer.parseInt(this.dirigidoA);
            //System.out.println("Area:" + this.dirigidoA);
            if (temporal.contains(area)) {
                temporal.remove(new Integer(area));
                entranteDAO.updatedirigidoCancel(this.correlativollave, area);
            }
        } else {
            this.error = "Seleccione Area";
            this.ver = true;
        }

    }
    
    public void cancelarDocs(ActionEvent actionEvent) {
        this.verasuntosdocs = false;
    }
    
    
    public void cancelarPlazo(ActionEvent actionEvent) {
        this.verdocs = false;
    }
    
    
    
    private List<Integer> temporal = new ArrayList<Integer>();

    public void pasaIzquierda(ActionEvent actionEvent) { // this.items7aSelected
        // = this.item    	
        boolean areaOrigenEstado = false;
        for (int i = 0; i < this.items7bSelected.length; i++) {
            //System.out.println(this.items7bSelected[i]);
            //System.out.println(this.correlativollave);
            int area = Integer.parseInt(this.items7bSelected[i]);
            for (AreaBean p : this.areasOrigen) {
                if (area == p.getCodigo()) {
                    areaOrigenEstado = true;
                }
            }
            if (areaOrigenEstado) {
                areaOrigenEstado = true;
            } else if (!temporal.contains(area)) {
                temporal.add(area);
                entranteDAO.updatedirigido(this.correlativollave, area);
            }
        }

        if (areaOrigenEstado) {
            this.error = "Area Origen no se puede eliminar.";
            this.ver = true;
            this.temporal.clear();
        } else {
            Utils.pasaIzquierda(items7a, items7b, items7bSelected);
        }
    }

    public void captura(ValueChangeEvent changeEvent) {

        area = (String) changeEvent.getNewValue();
        area_remite = Integer.parseInt(area);


        if (area_remite == 100 || area_remite == 601) {
        } else if (area_remite > 600) {
            List<RepresentanteBean> remite = entranteDAO
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
            entranteDAO.trabajador(Integer.parseInt(area));
            List<TrabajadorBean> trabajador = entranteDAO
                    .trabajador(area_remite);
            List itemsTrabajador = Utils.getTrabajador_derivador(trabajador);
            // this.items6 = itemsTrabajador;
            // for (TrabajadorBean t: trabajador)
            // System.out.println(t.getArea());
            List<JefeBean> bean = this.entranteDAO.jefe(area_remite);
            for (JefeBean e : bean) {
                ficha = e.getFicha();
            }
            this.items3 = itemsTrabajador;
            this.ficha_dirigidoN = ficha;



        }

    }

    public void captura_derivador(ValueChangeEvent changeEvent) {
        area_deriva = (String) changeEvent.getNewValue();
        // System.out.println(area_deriva);
        // entranteDAO.trabajador_derivador(Integer.parseInt(area_deriva));
        List<TrabajadorBean> trabajador_derivador = entranteDAO
                .trabajador_derivador(Integer.parseInt(area_deriva));
        List itemsTrabajador = Utils.getTrabajador(trabajador_derivador);
        this.items6 = itemsTrabajador;
        // for (TrabajadorBean t: trabajador_derivador)
        // System.out.println(t.getArea());
    }

    public void captura_representante(ValueChangeEvent changeEvent) {

        codigo_representante = (String) changeEvent.getNewValue();
        // this.ndirigidoN=Integer.parseInt(area);
        // System.out.println(codigo_representante);
        entranteDAO.representante(Integer.parseInt(codigo_representante));
        List<RepresentanteBean> remitente = entranteDAO.representante(Integer
                .parseInt(codigo_representante));
        // List itemsrepresentante = Utils.getRepresentante(remitente);
        // this.items4 = itemsrepresentante;
        // this.items6 = itemsTrabajador;
        for (RepresentanteBean t : remitente) {
            nrepresentanteN = t.getVnombre();
        }
        // System.out.println(t.getDescripcion());
    }

    public void actualizar(ValueChangeEvent changeEvent) {
        // String area = String.valueOf(this.ndirigidoA);
        // String area = String.valueOf(this.vremitenteA);
        // String area = (String)changeEvent.getNewValue();
        // System.out.println(area);
        int area = this.getNdirigidoA();
        entranteDAO.trabajador(area);
        List<TrabajadorBean> trabajador = entranteDAO.trabajador(area);
        List itemsTrabajador = Utils.getTrabajador(trabajador);
        this.items1 = itemsTrabajador;
        // for (TrabajadorBean t: trabajador);
    }

    public void actualizar_ficha(ValueChangeEvent changeEvent) {
        String area_derivada = String.valueOf(this.nremitenteA);
        entranteDAO.trabajador_derivador(Integer.parseInt(area_derivada));
        List<TrabajadorBean> trabajador_derivador = entranteDAO
                .trabajador_derivador(Integer.parseInt(area_derivada));
        List itemsTrabajador = Utils.getTrabajador(trabajador_derivador);
        this.items6 = itemsTrabajador;
        // for (TrabajadorBean t: trabajador_derivador);

    }

    //public void Origen(ValueChangeEvent changeEv) {
    //	origen = (String) changeEv.getNewValue();
    // System.out.println(origen);
    //	int origenes = Integer.parseInt(origen);
    //	if (origenes == 1) {
    // System.out.println("estoy aqui");
    //		List<AreaBean> area = entranteDAO.areas();
    //		List itemsArea = Utils.getAreas(area);
    //		this.items1 = itemsArea;
    //		} else {
    // System.out.println(origen);
    //		List<RemitenteBean> remitente = entranteDAO.remitentes();
    //		List itemsRemitente = Utils.getRemitentes(remitente);
    //		this.items1 = itemsRemitente;
    //	}
    //}
    
    // Eli comenta este codgio por que ya no se utiliza 21/11/2012 
    
    public void captura_origen(ValueChangeEvent changeEvent) {
        origen = (String) changeEvent.getNewValue();
        origenes = Integer.parseInt(origen);
        if (origenes == 1) {
            //System.out.println("Interno");
            List<OrigenBean> origen = entranteDAO.origen();
            List itemsOrigen = Utils.getOrigen(origen);
            //this.items7a = Utils.getAreasLink(areas);
            this.items8 = itemsOrigen;

        } 
        /*
        else {

            //System.out.println("Externo");
            //List<RemitenteBean> remitente = entranteDAO.remitentes();        	
            //List itemsAreas = Utils.getRemitentes(remitente);
            //this.items9 = itemsAreas;

        }
        */
    }
    
    public void captura_origenA(ValueChangeEvent changeEvent) {
        origen = (String) changeEvent.getNewValue();
        origenes = Integer.parseInt(origen);
        ///if (origenes == 1) {
            //System.out.println("Interno");
            List<OrigenBean> origen = entranteDAO.origenA();
            List itemsOrigen = Utils.getOrigen(origen);
            //this.items7a = Utils.getAreasLink(areas);
            this.items8 = itemsOrigen;

       // } 
        
    }
    
    
    
    public void eventEnviarLog(ActionEvent event){
    	//System.out.println("Hola Eli Estoy aqui prueba viendo el pdf");
    	//System.out.println("Hola Eli Estoy aqui prueba viendo el pdf");
    	//System.out.println("Hola Eli Estoy aqui prueba viendo el pdf");
    	//System.out.println("Hola Eli Estoy aqui prueba viendo el pdf");
    	//System.out.println("Hola Eli Estoy aqui prueba viendo el pdf");
    
     	

    }
    

    public void captura_accion(ValueChangeEvent changeEvent) {
        selectedItems1 = new ArrayList<String>();
        String acciones = this.vaccionA;
        // System.out.println(acciones);

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
        // area_dirigido.split("-");
        // System.out.println(area_dirigido);
        // System.out.println(area_dirigido);
        areaseleccionadaN = area_dirigido;
        this.ncodareaN = Integer.parseInt(area_deriva);
        // this.dirigidoN=area_dirigido;

    }

    public void eventRegistrarEntrante(ActionEvent event) {
        HttpSession session = null;
        boolean formulario = true;
        this.disBotonGrabar = false;
        try {
            // /acediendo a sesion http
            session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            // System.out.println("REGISTRANDO!!!");
            String valida = null;
            valida = this.validarFormulario();
            if (valida.equals("ok")) {
                for (String p : this.selectedItems1) {
                    // System.out.println("ITEM:"+ p);
                    accion = p + accion;
                    // System.out.println("completo:"+ accion);
                    this.vaccionN = accion;

                }
                this.verNuevo = false;
                this.vtipoN = "AA";
                // ///guardando en sesion un objeto
                Usuario usuario = null;
                // File file = null;
                String file = null;
                usuario = (Usuario) session.getAttribute("sUsuario");
                // this.ncodareaN=usuario.getCodarea();
                this.vrescreN = usuario.getLogin();
                this.vresactN = usuario.getLogin();
                this.ncodarea_origenN = usuario.getCodarea();
                this.nremitenteN = Integer.parseInt(vremitenteN);
                String ncodarea = String.valueOf(usuario.getCodarea());
                // controlando el adjuntar

                file = (String) session.getAttribute("file");
                if (file != null) {
                    nombre_archivo = file;
                    List<ServidorBean> bean = this.entranteDAO.servidor();
                    for (ServidorBean e : bean) {
                        ubicacion = e.getDescripcion();
                        // ubicacion = "http://1.1.194.53/entrada/"; modificado
                        // en parametros
                    }
                    Date date = new Date();
                    String annio = Integer.toString(c.get(Calendar.YEAR));
                    nombre_archivo = annio + nombre_archivo;
                    //this.vubiarchivoN = ubicacion + nombre_archivo;
                    this.vubiarchivoN = nombre_archivo;
                } else {
                    this.vubiarchivoN = "NADA";
                }
                
                this.vprioridadN="MEDIA";

                //Set set = this.items7b.entrySet();
                //Iterator i = set.iterator();
                //String valor = null;
                //suma_areas = "";
                //while (i.hasNext()) {
                //	Map.Entry val = (Map.Entry) (i.next());
                // System.out.println("value Hash !! : " + val.getValue());
                //	valor = (String) val.getValue();
                //	suma_areas += valor;

                //}
                //logger.debug("Areas seleccionadas:" + suma_areas);
                // System.out.println("Areas seleccionadas:" + suma_areas);
                //suma_areas = suma_areas + this.ncodarea_origenN;

                EntranteBean entranteBean = new EntranteBean();

                //Se adiciona varias areas y fichas concatenadas
                //ADD EDH
                entranteBean.setAreas(this.generaAreas() + this.ncodarea_origenN);
                entranteBean.setIndicador(this.generaIndicador() + this.vtipoN);

                entranteBean.setOrigen(this.origenN);
                entranteBean.setVtipodoc(this.vtipodocN);
                entranteBean.setNcodarea(this.ncodarea_origenN);
                entranteBean.setNcodarea_origen(this.ncodarea_origenN);
                // Eli Comenta
                // entranteBean.setNremitente(this.nremitenteN);
                entranteBean.setDfecregistro(this.dfecregistroN);
                entranteBean.setVremitente(suma_areas);
                // entranteBean.setVremitente(this.vremitenteN);
                entranteBean.setVubiarchivo(this.vubiarchivoN);
                // entranteBean.setNdirigido(this.ndirigidoN);
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
                this.vnumero_loteN="0";
				this.vnumero_discoN=" ";               
                
                String out = serviciosEntrante.nuevoEntrante(entranteBean);
                out = out.trim();
                //System.out.println("OUT STORED!!!:" + out);
                if (out.equals("0")) {
                    
                    this.ver = true;
                    this.selectedItems1.clear();
                    this.accion = "";
                    suma_areas = "";
                    this.vubiarchivoN = "";
                    ind_adjuntar = 0;
                    this.ver = true;
                    this.verCatalogo = true;
                    this.verActualizar = false;
                    // ***** editar ...
                    this.editarEmpresa = true;
                    formulario = true;
                    this.entrantes = serviciosEntrante.catalogo(ncodarea);// actualiza el reporte
                    verificarAnexos();
                    this.error = "Se grabó Satisfactoriamente";
                    this.eventRefrescar();

                } else {
                    this.error = "No Se grabó por inconsistencia de la información";
                    this.ver = true;
                    this.verCatalogo = true;
                    formulario = false;
                }

            } else {
                formulario = false;
                this.error = valida;
                this.ver = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            //logger.error("Fallo el registro", e);
            this.error = "Transaccion No valida.";
            this.ver = true;
        } finally {
            suma_areas = "";
            this.vubiarchivoN = "";
            nombre_archivo = "";
            this.vubiarchivoN = "";
            if (formulario) {
                session.setAttribute("file", null);
            }
            ubicacion = "";
            this.selectedItems1.clear();
            this.accion = "";
        }
    }

    public String generaAreas() {
        String seguir = "";
        for (Seguir p : this.seleccionados) {
            seguir += p.getCodArea();
        }
        return seguir;
    }

    public String generaAreasA() {
        String areas = "";
        for (AreaBean p : this.areas) {
            areas += p.getCodigo();
        }
        return areas;
    }

    public String generaIndicador() {
        String seguir = "";
        for (Seguir p : this.seleccionados) {
            seguir += p.getEstado();
        }
        return seguir;
    }

    public String generaIndicadorA() {
        String areas = "";
        for (AreaBean p : this.areas) {
            areas += p.getTipo();
        }
        return areas;
    }

    public void eventEnviarCorreo(ActionEvent actionEvent) {
        String estado;
        String mensaje = "DOCUMENTOS";
        String rochoa = "                                                    .";
        String valor = "";
        int cantidad;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        int area_logon = usuario.getCodarea();
        String nombre_equipo = usuario.getNomequipo();
        List<EntranteBean> entrantes = entranteDAO.entrada(area_logon);
        for (EntranteBean e : entrantes) {
            estado = e.getVestado();
            cantidad = e.getCantidad();
            valor += (nombre_equipo + " " + estado + " " + cantidad + " "
                    + mensaje + " " + rochoa + "\n");
            // System.out.println(estado);
        }

        // System.out.println(valor);
        String to[] = {"ediazh@sedapal.com.pe"};
        customerService.enviarCorreo("ediazh@sedapal.com.pe", to,
                "Tramite Documentario : Documentos Pendientes", valor);
    }

    public List<EntranteBean> getEntrantes() {
        if ("nano".equals(this.getSortColumnName())) {
            this.sort(this.entrantes);
        }
        if ("vdirigido".equals(this.getSortColumnName())) {
            this.sort(this.entrantes);
        }
        if ("vasunto".equals(this.getSortColumnName())) {
            this.sort(this.entrantes);
        }
        if ("dfecdoc".equals(this.getSortColumnName())) {
            this.sort(this.entrantes);
        }
        //if ("ncorrelativo".equals(this.getSortColumnName())) {
        //	this.sort(this.entrantes);
        //}
        if ("tipodoc".equals(this.getSortColumnName())) {
            this.sort(this.entrantes);
        }
        if ("dfecregistro".equals(this.getSortColumnName())) {
            this.sort(this.entrantes);
        }
        if ("dfecplazo".equals(this.getSortColumnName())) {
            this.sort(this.entrantes);
        }
        if ("vnumdoc".equals(this.getSortColumnName())) {
            this.sort(this.entrantes);
        }
        if ("vestado".equals(this.getSortColumnName())) {
            this.sort(this.entrantes);
        }
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

    public void setServiciosEntrante(IServiciosEntrante serviciosEntrante) {
        this.serviciosEntrante = serviciosEntrante;
    }

    /**
     * ***********************************************
     */
    public void eventFiltros(ActionEvent event) {
        // this.ver = true;
        // usamos para darle el fomato adecuado para pasarle al stored de oracle
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        this.ncodareaA = usuario.getCodarea();
        this.vresactA = usuario.getLogin();
        String ncodarea = String.valueOf(usuario.getCodarea());
        boolean formulario = true;
       

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        String validaopcion = this.validarOpcion();

        if (validaopcion.equals("ok")) {
        	
        	formulario = true;
	        this.inicio = sdf.format(this.getDate1());
	        this.fin = sdf.format(this.getDate2());
	        
	        // se puede validar fechas
	        if (this.date1.after(this.date2)) {
	            this.error = "Fecha 1 debe ser menor que la fecha 2";
	            this.ver = true;        
	        } else {
	            this.entrantes = entranteDAO.filtrosSP(inicio, fin, this.tipoopcion, ncodarea, 
	            		this.item2Seleccionado, this.getItemareaSeleccionado());
	            int dato = this.entrantes.size();
	            verificarAnexos();
	        }
	        
        } else {
        	formulario = false;
            this.error = validaopcion;
            this.ver = true;
        }
        
    }

    public void eventRefrescar(ActionEvent evt) {

        String area = this.area;
        //System.out.println(area);
        //System.out.println("S_GUIA.ATDC_DOC_ENTRAN_ALL");
        this.entrantes = entranteDAO.entrantesSP(area);
        verificarAnexos();
        this.detalle = "";
        selectedItems1.clear();
        this.setSortColumnName("nano");
        this.setAscending(false);
        //this.disPrint = false;
    }
    //A CF 16-06-2011

    public void eventRefrescar() {
        String area = this.area;
        selectedItems1.clear();
        this.entrantes = entranteDAO.entrantesSP(area);
        verificarAnexos();
        this.setSortColumnName("nano");
        this.setAscending(false);
    }

    /*
    public void eventRefrescarCombo(ActionEvent evt) {

        List<RemitenteBean> remitentes = entranteDAO.remitentes();
        List itemsRemitentes = Utils.getRemitentes(remitentes);
        this.items1 = itemsRemitentes;

    }
    */

    /**
     * ***********************************************
     */
    public void eventGrabarCombo(ActionEvent evt) {
        int codigo_remitente = 0, codigo_representantes = 0, indicador_empresa = 1;
        String estado = "A";
        Usuario usuario = null;
        HttpSession session = null;
        session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        usuario = (Usuario) session.getAttribute("sUsuario");

        try {
            // desactiva boton
            disBotonGrabar = true;
            // /valida
            if (this.empresa.trim().length() > 0) {

                /*for (EntranteBean p : selectedEntrantes) {
                 this.empresa = p.getEmpresa();
                 }
                 */
                //System.out.println(this.empresa);
                //System.out.println(usuario.getCodarea());
                //System.out.println(usuario.getLogin());
                String out = entranteDAO.actualizaCombos(this.empresa, String.valueOf(usuario.getCodarea()), usuario.getLogin());
                out = out.trim();
                //System.out.println("OUT STORED!!!:" + out);
                if (out.equals("0")) {
                    this.editarEmpresa = true;

                    List<RemitenteBean> remitentes = entranteDAO.remitentes();
                    List itemsRemitentes = Utils.getRemitentes(remitentes);
                    //preseleccionar el ingresado
                    this.items9 = itemsRemitentes;
                    for (RemitenteBean p : remitentes) {
                        if (p.getDescripcion().equals(this.empresa)) {
                            this.vremitenteN = String.valueOf(p.getCodigo());
                            this.personaSeleccionada = this.empresa;
                            break;
                        }
                    }

                    //act el otro combo

                    area = this.vremitenteN;

                    area_remite = Integer.parseInt(area);
                    if (area_remite == 100 || area_remite == 601) {
                    } else if (area_remite > 600) {
                        List<RepresentanteBean> remite = entranteDAO
                                .representante(area_remite);
                        List itemsrepresentante = Utils.getRepresentante(remite);
                        this.items3 = itemsrepresentante;
                        // for (RepresentanteBean r: remite);

                    } else {
                        // System.out.println(area);
                        entranteDAO.trabajador(Integer.parseInt(area));
                        List<TrabajadorBean> trabajador = entranteDAO
                                .trabajador(area_remite);
                        List itemsTrabajador = Utils.getTrabajador_derivador(trabajador);
                        this.items3 = itemsTrabajador;
                        // this.items6 = itemsTrabajador;
                        // for (TrabajadorBean t: trabajador)
                        // System.out.println(t.getArea());
                    }
                } else {
                    this.error = "Ya existe el Remitente en la Base de Datos, Por favor seleccionar de la lista remitente";
                    this.ver = true;
                    disBotonGrabar = false;
                    //this.verCatalogo = true;
                    //formulario = false;
                }

                //

            } else {
                this.error = "Ingrese la empresa";
                this.ver = true;
                disBotonGrabar = false;
            }
        } catch (Exception exception) {
            this.error = "Error Interno, consulte con el administrador[203]";
            this.ver = true;
            //logger.error("MBeanEntrante.eventGrabarCombo][203]", exception);
            exception.printStackTrace();
            disBotonGrabar = false;
        } finally {
            // this.empresa="";
        }

    }

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

        if (this.detalle.equals("") || this.opcion.equals("")) {
            this.error = "Debe de Ingresar una opcion de busqueda";
            this.ver = true;
        } else {
            this.entrantes = entranteDAO.BusquedaSP(area, opcion, detalle);
           verificarAnexos();
        }
    }
    
    
    
    
    void eventBusquedaSeguimiento(String area, String opcion, String detalle){
    	
    	 this.entrantes = entranteDAO.BusquedaSP(area, opcion, detalle);
    	 verificarAnexos();
    }
    
    void eventBusquedaSeguimientoDocumento(String area, String anno, String correlativo){
    	
   	 this.entrantes = entranteDAO.BusquedaSeguimientoDocumentosSP(area, anno, correlativo);
   	verificarAnexos();
   	
   }

    public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

    public MBeanEntrantes() {
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
        vrescreN = usuario.getLogin().toUpperCase();
        vresactN = usuario.getLogin().toUpperCase();
        centroN = usuario.getCentro().toUpperCase();
        area_origenN = usuario.getNomequipo().toUpperCase();
        ncodcentroN = usuario.getNcodcentro();
        vareaN = usuario.getNomequipo();
        int area_logon = usuario.getNcodarea();
       
        this.entrantes = serviciosEntrante.catalogo(ncodarea);
        verificarAnexos();
       

        List<TiposBean> tipos = entranteDAO.tipos();
        List itemstipos = Utils.getTipos(tipos);
        this.items2 = itemstipos;
        
       

        List<EstadosBean> estado = entranteDAO.estados();
        List itemsEstado = Utils.getEstado(estado);
        this.items7 = itemsEstado;
        
        List<AreaBean> areas = entranteDAO.areas();
        List itemsAreas = Utils.getAreas(areas);
        this.items10 = itemsAreas;
        
        List<TiposBean> tipoconsulta = entranteDAO.tipoconsulta();
        List itemtipoconsulta = Utils.getTipos(tipoconsulta);
        this.itemscombo = itemtipoconsulta;

       

        List<AreaBean> area_derivados = entranteDAO.areas();
        List itemsderivados = Utils.getAreas(area_derivados);
        this.items9 = itemsderivados;
        // CF V01.00
        //this.items7a = Utils.getAreasLink(area_derivados); 
        

       

        
        
        List<ServidorBean> bean = this.entranteDAO.servidor();
        for (ServidorBean e : bean) {
            ubicacion = e.getDescripcion();
        }
        
        
      
        


    }
    
    public void verificarAnexos() {
    	if (!entrantes.isEmpty()) {
			for (EntranteBean ent : entrantes) {
				List<Anexo> anexos = obtenerAnexos((int)ent.getNcorrelativo(), "", ent.getVnumdoc(), ent.getNano());
				ent.setAnexo(!anexos.isEmpty());
				verificarAcuse(ent);
			}
		}
    }
    
    private void verificarAcuse(EntranteBean ent) {
    	DocumentoSalidaBean doc = this.seguimientodocumentoDAO.getDocSalByNumDoc(ent.getVnumdoc(), false);
    	if (doc == null || doc.getCodigo() == 0) {
			doc = this.seguimientodocumentoDAO.getDocSalByRegistroEntrada(ent.getNcorrelativo() + "-" + ent.getNano(), ent.getVnumdoc(), false);
		}
    	
    	if (doc != null && doc.getCodigo() != 0) {
    		ent.setCodDocSal(doc.getCodigo());
    		ent.setAreaDocSal(doc.getArea());
    		ent.setAnioDocSal(doc.getAno());
    		ent.setAcuse(this.documentoSalidaDAO.getAcuse(doc.getCodigo(), doc.getAno(), doc.getArea()));
    	} else {
    		ent.setAcuse(false);
    	}
    }
    
    private List<Anexo> obtenerAnexos(int correlativo, String numDocEntrada, String numDoc, int anio) {
    	return seguimientodocumentoDAO.getAnexos(correlativo, numDocEntrada, numDoc, anio);
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
        for (int i = 0, max = entrantes.size(); i < max; i++) {
            employee = (EntranteBean) entrantes.get(i);
            if (employee.isSelected()) {            	
                selectedEntrantes.add(employee);
            }
        }
        for (EntranteBean p : selectedEntrantes) {
        	System.out.println("Estoy en la seleccion del Registro");
            System.out.println(p.getNano() + " " + p.getNorigen() + " " + p.getDfecdoc() + " " + p.getVprioridad());
            this.li_recepcion = p.getNrecepcion();
            this.mCarga.setNombrePdf(p.getVubiarchivo());
            this.anollave = p.getNano();
            this.origenllave = p.getNorigen();
            this.tipodocllave = p.getVtipodoc();
            this.correlativollave = p.getNcorrelativo();
            this.numdocllave = p.getVnumdoc();
            this.fechaplazollave = p.getDfecplazo();
            this.diasplazollave = p.getNdiasplazo();
            this.fecharecepcionllave = p.getDfecdoc();
            this.asuntollave = p.getVasunto();
            this.prioridadllave = prioridadllave;
            this.ubicacionllave = p.getUbicacion_entrada();
            this.indremitentellave = p.getnIndRemitente();
            
            //System.out.println("---------------------");
            //System.out.println("QUIERO VER EL ASUNTO DEL DOCUMENTO DOCUMENTO ENTRADA");
            //System.out.println(p.getVasunto());
            //System.out.println(p.getNcorrelativo());
            //System.out.println(p.getVtipodoc());
            //System.out.println("---------------------");
            
            
           
           
            //inserta codigo para ver el archivo adjunto 19noviembre2019-EDH
            ls_ubicacion = ubicacion + p.getVubiarchivo();
            System.out.println("Visualizacion de archivo");
            System.out.println(ls_ubicacion);            
            EntranteBean bean = new EntranteBean();
            bean.setUbicacion_entrada(ls_ubicacion);
            
            System.out.println("Quiero ver la prioridad");
            System.out.println(this.prioridadllave);
            
            System.out.println("Quiero ver el asunto");
            System.out.println(this.asuntollave);
            
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
            beans.setAsunto_documento(this.asuntollave);
            beans.setUbicacion(this.ubicacionllave);
            beans.setIndremitente(this.indremitentellave);
            
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
            this.nremitenteA = p.getNremitente();
            this.ficha_dirigidoA = p.getFicha_dirigido();
            this.ficha_derivadoA = p.getFicha_derivado();
            this.treeController.inicializarArbol(String.valueOf(p
                    .getNcorrelativo()), String.valueOf(p.getNano()));
            ruta = p.getVubiarchivo();
            ind_adjuntar = p.getNindicador();
            this.vubiarchivoA = p.getVubiarchivo();
            ///insertar codigo para arhivo adjunto seguimiento
            //ls_ubicacion_seguimiento = ubicacion_seguimiento + p.getVubiarchivo();
            //System.out.println("Visualizacion de archivo");
            //System.out.println(ls_ubicacion_seguimiento);            
            //SeguimientoBean bean1 = new SeguimientoBean();
            //bean1.setUbicaarchivo(ls_ubicacion_seguimiento);
            	
            
            

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

        //*para la validacion */
    	/*
        if (this.items7b.size() == 0) {
            this.error = "Debe de seleccionar un dirigido";
            this.ver = true;
        } else {

            for (Integer p : this.temporal) {
                entranteDAO.updatedirigidoCancel(this.correlativollave, p);
            }
            */
            /*Eli Agrega este codigo*/
            this.disBoton=false;
        	this.disBotonRemitente=true;        	
            this.disBotonGrabar = false;
            this.verCatalogo = true;
            this.verNuevo = false;
            this.verActualizar = false;
            selectedItems1.clear();
            this.accion = "";
            this.vubiarchivoA = "NADA";
            this.temporal.clear();
            
            // ***** editar ...
            this.editarEmpresa = true;
            
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);                    
            session.setAttribute("file", null);
        
    }

    public void eventCancelarNuevo(ActionEvent event) {


        this.disBotonGrabar = false;
        this.verCatalogo = true;
        this.verNuevo = false;
        this.verActualizar = false;
        selectedItems1.clear();
        this.accion = "";
        this.vubiarchivoA = "NADA";
        // ***** editar ...
        this.editarEmpresa = true;
        /*
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.setAttribute("file", null);
        */



    }

    public void limpiarNuevo() {        
        this.nanoN = 0;
        this.origenN = "";
        this.vtipodocN = "ATD0001";
        this.ncorrelativoN = 0;
        this.vnumdocN = "";
        this.vmesaparteN = "";
        this.nremitenteN = 0;
        this.ficha_dirigidoN=0;
        this.vremitenteN = "";
        this.dirigidoN = "";
        this.dfecdocN = new Date();
        this.dfecregistroN = new Date();
        this.vasuntoN = "";
        this.vreferenciaN = "";
        this.vobservacionN = "";
        this.vaccionN = "";
        this.vprioridadN = "";
        // this.vubiarchivoN = "";
        this.dfecplazoN = new Date();
        this.ndiasplazoN = 1;
        this.vestadoN = "";
        // this.nindicadorN = 0;
        this.dfeccreN = new Date();
        this.dfecactN = new Date();
        //this.areaseleccionadaN = "";
        // this.vrescreN = "";
        // this.vresactN = "";
        // this.ncodcentroN = 0;
        // this.ncodareaN = 0;
        //this.ficha_dirigidoN = 0;
        //this.items7b.clear();
        
        this.accion = "";
        this.ind_adjuntar = 0;
        this.vubiarchivoN = "NADA";
       /*
        List<AreaBean> area_derivados = entranteDAO.areas();
        List itemsderivados = Utils.getAreas(area_derivados);
        this.items7a = Utils.getAreasLink(area_derivados);
        */
       
        this.empresa = "";
        //ficha_dirigidoN=0;		
        this.empresa = "";
        
        List<RepresentanteBean> representante = entranteDAO.representante(601);
        List itemsRepresentante = Utils.getRepresentante(representante);
        this.items3 = itemsRepresentante;
        
        this.nindicadorN = 0;
        this.nindicadorA = 0;
        this.textoA = "El Registro tiene " + this.nindicadorN + " Archivo Adjunto";       
        this.norigenA = 0;
        //this.disBotonGrabar=true;
        
        
        List<AreaBean> areas = entranteDAO.areas();
        List itemsAreas = Utils.getAreas(areas);
        this.items1 = itemsAreas;
        
        this.vprioridadN = "ALTA";
        this.personaSeleccionada = "";
        this.ficha_dirigidoN=0;
        //this.items3.clear();
        this.selectedItems1.clear();
        this.seleccionados.clear();
        this.nfolioN = 0;
        
        List<OrigenBean> origen = entranteDAO.origen();
        List itemsOrigen = Utils.getOrigen(origen);
        this.items8 = itemsOrigen;
        
    }

    public void verGrupo() {
        this.vremitenteN = "";
    }

    public void limpiarActualizar() {
        this.nanoA = 0;
        // this.norigenA = 0;
        // this.vtipodocA = "";
        this.ncorrelativoA = 0;
        this.vnumdocA = "";
        // this.vmesaparteA = "";
        // this.nremitenteA = 0;
        // this.vremitenteA = "";
        // this.ndirigidoA = 0;
        this.dfecdocA = new Date();
        this.dfecregistroA = new Date();
        // this.vasuntoA = "";
        // this.vreferenciaA = "";
        // this.vobservacionA = "";
        // this.vaccionA = "";
        // this.vprioridadA = "";
        // this.vubiarchivoA = "";
        this.dfecplazoA = new Date();
        this.ndiasplazoA = 0;
        // this.vestadoA = "";
        // this.nindicadorA = 0;
        this.dfeccreA = new Date();
        this.dfecactA = new Date();
        ind_adjuntar = 0;
        this.vubiarchivoA = "NADA";
        // this.vrescreA = "";
        // this.vresactA = "";
        // this.ncodcentroA = 0;
        // this.ncodareaA = 0;
        // this.ficha_dirigidoA = 0;
    }

    public String validarFormulario() {
        String msg = "Falta ingresar: ";
        boolean ok = true;
        if (this.origenN.equals("0")) {
            ok = false;
            msg += "Origen";
        }
        if (this.vtipodocN.equals("ATD0001")) {
            if (ok == false) {
                msg += ",Tipo de Documento";
            } else {
                msg += "Tipo de Documento";
            }
            ok = false;
        }
        if (this.vnumdocN.trim().length() == 0) {
            if (ok == false) {
                msg += ",Numero Documento";
            } else {
                msg += "Numero Documento";
            }
            ok = false;
        }
        //if (this.dfecplazoN.after(this.dfecregistroN)) {
        //	if (ok == false)
        //		msg += ",Fec Recep Menor a Fec Doc";
        //	else
        //		msg += "Fec Recep Menor a Fec Doc";
        //	ok = false;
        //} 
        if (this.ndiasplazoN <= 0) {
            if (ok == false) {
                msg += ",Dias";
            } else {
                msg += "Dias";
            }
            ok = false;
        }
        if (this.items3.size() == 0) {
			if (ok == false) {
				msg += ",Remitido Por";
			} else {
				msg += "Remitido Por";
			}
			ok = false;
		}
        //System.out.println("Origen:" + this.origenN);
        if (this.origenN.equals("1")) {
            if (this.vremitenteN.equals("100")) {
                if (ok == false) {
                    msg += ",Remitente";
                } else {
                    msg += "Remitente";
                }
                ok = false;
            }

        } else {

            if (this.vremitenteN.equals("601")) {
                if (ok == false) {
                    msg += ",Remitente";
                } else {
                    msg += "Remitente";
                }
                ok = false;
            }
        }
        
        

        if (this.vasuntoN.trim().length() == 0) {
            if (ok == false) {
                msg += ",Asunto";
            } else {
                msg += "Asunto";
            }
            ok = false;
        }
        
        if (this.vremitenteN.equals("")) {
                if (ok == false) {
                    msg += ",Remitente";
                } else {
                    msg += "Remitente";
                }
                ok = false;
         }
        
       
        
        
            
        if (this.selectedItems1.size() == 0) {
            if (ok == false) {
                msg += ",Acciones";
            } else {
                msg += "Acciones";
            }
            ok = false;
        }

        if (ok) {
            msg = "ok";
        }
        return msg;
    }
    
    
    public String validarOpcion() {
        String msg = "Falta Seleccionar: ";
        boolean ok = true;
        
        if (this.tipoopcion.equals("CONS00")) {
            if (ok == false) {
                msg += ",Tipo de Opción";
            } else {
                msg += "Tipo de Opción";
            }
            ok = false;
        }
       
       
        if (ok) {
            msg = "ok";
        }
        return msg;
    }

    public String validarFormularioActualizar() {
        String msg = "Falta ingresar: ";
        boolean ok = true;
        if (this.origenA.equals("0")) {
            ok = false;
            msg += "Origen";
        }
        if (this.vtipodocA.equals("ATD0001")) {
            if (ok == false) {
                msg += ",Tipo de Documento";
            } else {
                msg += "Tipo de Documento";
            }
            ok = false;
        }
        if (this.vnumdocA.trim().length() == 0) {
            if (ok == false) {
                msg += ",Numero Documento";
            } else {
                msg += "Numero Documento";
            }
            ok = false;
        }
        if (this.ndiasplazoA <= 0) {
            if (ok == false) {
                msg += ",Dias";
            } else {
                msg += "Dias";
            }
            ok = false;
        }
        if (this.items9.size() == 0) {
            if (ok == false) {
                msg += ",Destinatario";
            } else {
                msg += "Destinatario";
            }
            ok = false;
        }
        //System.out.println("Origen:" + this.origenA);
       
        if (this.origenA.equals("1")) {
            if (this.vremitenteA.equals("100")) {
                if (ok == false) {
                    msg += ",Remitente";
                } else {
                    msg += "Remitente";
                }
                ok = false;
            }

        } 
       
        else {

            if (this.vremitenteA.equals("601")) {
                if (ok == false) {
                    msg += ",Remitente";
                } else {
                    msg += "Remitente";
                }
                ok = false;
            }
        }
       

        if (this.vasuntoA.trim().length() == 0) {
            if (ok == false) {
                msg += ",Asunto";
            } else {
                msg += "Asunto";
            }
            ok = false;
        }
        if (this.selectedItems1.size() == 0) {
            if (ok == false) {
                msg += ",Acciones";
            } else {
                msg += "Acciones";
            }
            ok = false;
        }

        if (ok) {
            msg = null;
        }
        return msg;
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

    public List getItems8() {
        return items8;
    }

    public List getItems9() {
        return items9;
    }

    public void setItems9(List items9) {
        this.items9 = items9;
    }

    public void setItems8(List items8) {
        this.items8 = items8;
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

    public void setEntranteDAO(EntranteDAO entranteDAO) {
        this.entranteDAO = entranteDAO;
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

    // /////////////////////////////
    public int getNanoN() {
        return nanoN;
    }

    public void setNanoN(int nanoN) {
        this.nanoN = nanoN;
    }

    public String getVtipodocN() {
        return vtipodocN;
    }

    public void setVtipodocN(String vtipodocN) {
        this.vtipodocN = vtipodocN;
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

    public String getVtipodocA() {
        return vtipodocA;
    }

    public void setVtipodocA(String vtipodocA) {
        this.vtipodocA = vtipodocA;
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
    
	public AcuseReport getReporteAcuse() {
		return reporteAcuse;
	}

	public void setReporteAcuse(AcuseReport reporteAcuse) {
		this.reporteAcuse = reporteAcuse;
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
        
        //System.out.println("Verifico la varible");
		//System.out.println(p);

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
                List<TrabajadorBean> trabajador = entranteDAO.trabajadorSolo(p
                        .getFicha_dirigido());
                if (!trabajador.isEmpty()) {
                    nomb = trabajador.get(0).getNombre_completo();
                } else {
                    nomb = "";
                }

            } else {

                // obtengo nombre del representante
                List<RepresentanteBean> representante = entranteDAO
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
            //System.out.println("Se asigno la variable REPORTE - 1er reporte");


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

    // para impresion 2
    //private RecursoReporteNuevo reporteNuevo;
    //private RecursoReporteNuevo recursoNuevo;
    public RecursoReporte getRecursoReporte() {
		HttpSession session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ec2 = FacesContext.getCurrentInstance().getExternalContext();
		EntranteBean p = null;
		for (EntranteBean q : this.entrantes) {
			if (q.isSelected()) {
				p = q;
			}
		}

		System.out.println(p);
		// /acediendo a sesion http
		session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		String area_logeado = String.valueOf(usuario.getNomequipo());
		session.getAttribute("sUsuario");

		if (p != null) {

			// this.eventoTab();// ADD CF AGO 2011
			System.out.println("Fila Selecionada 22222!!!!");
			// this.anollave = p.getNano();
			// this.origenllave = p.getNorigen();
			// this.tipodocllave = p.getVtipodoc();
			// this.correlativollave = p.getNcorrelativo();
			// this.numdocllave = p.getVnumdoc();
			//parametros2.put("P_AREA", String.valueOf(p.getNcodarea()));
			parametros2.put("P_ANO", String.valueOf(p.getNano()));
			parametros2.put("P_CORRELATIVO", String.valueOf(p.getNcorrelativo()));
			parametros2.put("P_AREA_ORIGEN", area_logeado);			
			//parametros2.put("P_FECHA", sdf.format(p.getDfecdoc()));
			//parametros2.put("P_REFERENCIA", p.getVreferencia());
			//parametros2.put("P_REMITE", p.getVdirigido());
			//parametros2.put("P_VNUMDOC", p.getVnumdoc());
			//parametros2.put("P_ASUNTO", p.getVasunto());
			//System.out.println("P_AREA        : " + p.getNcodarea());
			System.out.println("Parametros : ");
			System.out.println("P_ANO  : " + String.valueOf(p.getNano()));
			System.out.println("P_CORRELATIVO : " + String.valueOf(p.getNcorrelativo()));
			System.out.println("P_AREA_ORIGEN : " + area_logeado);
								
			//System.out.println("P_FECHA   : " + sdf.format(p.getDfecdoc()));
			//System.out.println("P_REFERENCIA  : " + p.getVreferencia());
			//System.out.println("P_REMITE   : " + p.getVdirigido());
			//System.out.println("P_ASUNTO   : " + p.getVasunto());
			reportes.asignar("reporteEntrantes.pdf", ec2, parametros2,
					"reportes/rptahojacargo.jasper");// se le puede adicionar
			System.out.println("Se asigno la variable REPORTES - 2do reporte");
			// parametros...
			recursoReporte = reportes;

		} else {
			System.out.println("Blanco");
			reportes.asignar("ticket.pdf", ec2, parametros2,
					"reportes/blanco.jasper");// se le puede adicionar
			// parametros...
			recursoReporte = reportes;

		}

		return recursoReporte;

	}
    
    
    
    public RecursoReporteNuevo getRecursoReporteNuevo() {
        HttpSession session = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ec2 = FacesContext.getCurrentInstance().getExternalContext();
        EntranteBean p = null;
        for (EntranteBean q : this.entrantes) {
            if (q.isSelected()) {
                p = q;
            }
        }
        
        
        for (EntranteBean q : this.entrantes) {
            if (q.isSelected()) {
                p = q;
            }
        }
        
        
        
        //System.out.println(p);
        // /acediendo a sesion http
        session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_logeado = String.valueOf(usuario.getNomequipo());
        session.getAttribute("sUsuario");

        
		if (p != null) {

            this.eventoTab();// ADD CF AGO 2011
            //System.out.println("Fila Selecionada 22222!!!!");          
            parametros2.put("P_ANO", String.valueOf(this.anollave));
            parametros2.put("P_AREA", area);
            parametros2.put("P_AREA_ORIGEN", area_logeado);
            parametros2.put("P_CORRELATIVO", String.valueOf(this.correlativollave));
            parametros2.put("P_FECHA", sdf.format(p.getDfecdoc()));
            parametros2.put("P_REFERENCIA", p.getVreferencia());
            parametros2.put("P_REMITE", p.getVdirigido());
            parametros2.put("P_VNUMDOC", p.getVnumdoc());
            parametros2.put("P_ASUNTO", p.getVasunto());
           
            //System.out.println("Parametros : ");
            //System.out.println("P_ANO  : " + String.valueOf(this.anollave));
            //System.out.println("P_AREA        : " + area);
            //System.out.println("P_AREA_ORIGEN : " + p.getArea());
            //System.out.println("P_CORRELATIVO : " + String.valueOf(this.correlativollave));
            //System.out.println("P_FECHA   : " + sdf.format(p.getDfecdoc()));
            //System.out.println("P_REFERENCIA  : " + p.getVreferencia());
            //System.out.println("P_REMITE   : " + p.getVdirigido());
            //reportes.asignar("reporteEntrantes.pdf", ec2, parametros2, "reportes/hojaenvio.jasper");// se le puede adicionar

            reporteNuevo.asignar("reporteEnvio.pdf", ec2, parametros2, "reportes/hojaenvio.jasper");
            //System.out.println("Se asigno la variable REPORTES - 2do reporte");
            ///            
            // parametros...
            recursoReporteNuevo = reporteNuevo;

        } else {
            //System.out.println("Blanco");
            //this.error = "Debe seleccionar por lo menos un registro";
            //this.ver = true;
            reportes.asignar("ticket.pdf", ec2, parametros2, "reportes/blanco.jasper");// se le puede adicionar
            // parametros...
            recursoReporteNuevo = reporteNuevo;

        }

        return recursoReporteNuevo;

    }
    
    
    // para impresion 3
    //public RecursoReportNuevo getRecursoReportNuevo(){
    /*
    public RecursoReporte getRecursoReporte(){
        HttpSession session = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ec3 = FacesContext.getCurrentInstance().getExternalContext();
        EntranteBean p = null;
        for (EntranteBean q : this.entrantes) {            
                p = q;            
        }
        //acediendo a sesion http
        session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_logeado = String.valueOf(usuario.getNomequipo());
        area = String.valueOf(usuario.getCodarea());
        session.getAttribute("sUsuario");        
        this.estadoSeleccionado = this.getItem2Seleccionado();               
        String valor = this.getItem2Seleccionado();
        //inicio = this.getInicio();
        //fin = this.getFin();
       
        //if (p != null) {
        /* ver este reporte
        if (opcion.equals("CONS00")){
        	
        }
        */
    /*
        if (valor != "ESTA000") {
        	
        	 //this.eventoTab();// ADD CF AGO 2011
        	 //System.out.println("Envio la impresion !!!!");
        	 //System.out.println(area);
        	 //System.out.println(area_logeado);
        	 //System.out.println(this.inicio);
        	 //System.out.println(this.fin);
        	 //System.out.println(this.estadoSeleccionado);
             parametros3.put("P_AREA", area);
             parametros3.put("P_AREA_ORIGEN", area_logeado);            
             parametros3.put("P_FIN", this.fin);
             parametros3.put("P_INICIO", this.inicio);            
             parametros3.put("P_TIPO", this.estadoSeleccionado);
             
             reportes.asignar("reporteEntr.pdf", ec3, parametros3, "reportes/reportedocentrada.jasper");// se le puede adicionar
             //System.out.println("Se asigno la variable REPORTES - 3do reporte");
             //
             
             // parametros...
             //recursoReporteNuevo = reportenuevo;
             recursoReporte = reportes;
        	
        } else {
        	//System.out.println("Blanco");
            reportes.asignar("ticket.pdf", ec3, parametros3, "reportes/blanco.jasper");// se le puede adicionar           
            recursoReporte = reportes;
            

        }
        	       
        return recursoReporte;
        
       
        
       
       

    }
	*/
    
    public AcuseReport getAcuseReciboReporte() {
        ec = FacesContext.getCurrentInstance().getExternalContext();
        EntranteBean p = null;
        for (EntranteBean q : this.entrantes) {
            if (q.isSelected()) {
                p = q;
            }
        }

        if (p != null && !p.isAcuse()) {
            this.eventoTab();
            parametros.put("P_COD_SAL", String.valueOf(p.getCodDocSal()));
            parametros.put("P_AREA", String.valueOf(p.getAreaDocSal()));
            parametros.put("P_ANIO", String.valueOf(p.getAnioDocSal()));
            parametros.put("P_REG_ANIO", String.valueOf(p.getNano()));
            parametros.put("P_REG", String.valueOf(p.getNcorrelativo()));
            parametros.put("P_REG_AREA", String.valueOf(p.getNcodarea()));

            System.out.println("Generando reporte de Acuse - Entrada: " + parametros);
            
            reporteAcuse.asignar("acuse_recibo.pdf", ec, parametros, "reportes/acuseRecibo.jasper");

            acuseReciboReporte = reporteAcuse;
        } else {
            reporte.asignar("ticket.pdf", ec, parametros, "reportes/blanco.jasper");
            acuseReciboReporte = reporteAcuse;
        }

        return acuseReciboReporte;
    }

    public void setRecursoReport(RecursoReport recursoReport) {
        this.recursoReport = recursoReport;
    }	

	public void setAcuseReciboReporte(AcuseReport acuseReport) {
		this.acuseReciboReporte = acuseReport;
	}

	public void setRecursoReporteNuevo(RecursoReporteNuevo recursoReporteNuevo) {
		this.recursoReporteNuevo = recursoReporteNuevo;
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
    
    
    public ExternalContext getEc2() {
		return ec2;
	}

	public void setEc2(ExternalContext ec2) {
		this.ec2 = ec2;
	}

	public ExternalContext getEc3() {
		return ec3;
	}

	public void setEc3(ExternalContext ec3) {
		this.ec3 = ec3;
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

	public List<SelectItem> getPosiblespersonasA() {
		return posiblespersonasA;
	}

	public void setPosiblespersonasA(List<SelectItem> posiblespersonasA) {
		this.posiblespersonasA = posiblespersonasA;
	}

	public String getPersonaSeleccionadaA() {
		return personaSeleccionadaA;
	}

	public void setPersonaSeleccionadaA(String personaSeleccionadaA) {
		this.personaSeleccionadaA = personaSeleccionadaA;
	}

	public String getLabelPersonaA() {
		return labelPersonaA;
	}

	public void setLabelPersonaA(String labelPersonaA) {
		this.labelPersonaA = labelPersonaA;
	}

	public long getFichaA() {
		return fichaA;
	}

	public void setFichaA(long fichaA) {
		this.fichaA = fichaA;
	}

	public String getDerivadoA() {
		return derivadoA;
	}

	public void setDerivadoA(String derivadoA) {
		this.derivadoA = derivadoA;
	}

	public String getDerivadoAA() {
		return derivadoAA;
	}

	public void setDerivadoAA(String derivadoAA) {
		this.derivadoAA = derivadoAA;
	}

	public boolean isDisBoton() {
		return disBoton;
	}

	public void setDisBoton(boolean disBoton) {
		this.disBoton = disBoton;
	}

	public boolean isDisBotonRemitente() {
		return disBotonRemitente;
	}

	public void setDisBotonRemitente(boolean disBotonRemitente) {
		this.disBotonRemitente = disBotonRemitente;
	}

	public RecursoReporteNuevo getReporteNuevo() {
		return reporteNuevo;
	}

	public void setReporteNuevo(RecursoReporteNuevo reporteNuevo) {
		this.reporteNuevo = reporteNuevo;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	

	public List<EntranteBean> getListadocalertas() {
		return listadocalertas;
	}

	public void setListadocalertas(List<EntranteBean> listadocalertas) {
		this.listadocalertas = listadocalertas;
	}

	public boolean isVerdocs() {
		return verdocs;
	}

	public void setVerdocs(boolean verdocs) {
		this.verdocs = verdocs;
	}

	public String getPrioridadllave() {
		return prioridadllave;
	}

	public void setPrioridadllave(String prioridadllave) {
		this.prioridadllave = prioridadllave;
	}

	public boolean isVerasuntosdocs() {
		return verasuntosdocs;
	}

	public void setVerasuntosdocs(boolean verasuntosdocs) {
		this.verasuntosdocs = verasuntosdocs;
	}

	public List<TiposBean> getItemsAsuntos() {
		return itemsAsuntos;
	}

	public void setItemsAsuntos(List<TiposBean> itemsAsuntos) {
		this.itemsAsuntos = itemsAsuntos;
	}

	public List<TiposBean> getAsuntos() {
		return asuntos;
	}

	public void setAsuntos(List<TiposBean> asuntos) {
		this.asuntos = asuntos;
	}

	public List getItems10() {
		return items10;
	}

	public void setItems10(List items10) {
		this.items10 = items10;
	}

	public String getItemareaSeleccionado() {
		return itemareaSeleccionado;
	}

	public void setItemareaSeleccionado(String itemareaSeleccionado) {
		this.itemareaSeleccionado = itemareaSeleccionado;
	}

	public HtmlSelectOneMenu getComboarea() {
		return comboarea;
	}

	public void setComboarea(HtmlSelectOneMenu comboarea) {
		this.comboarea = comboarea;
	}

	public String getTipoopcion() {
		return tipoopcion;
	}

	public void setTipoopcion(String tipoopcion) {
		this.tipoopcion = tipoopcion;
	}

	public boolean isDisverestado() {
		return disverestado;
	}

	public void setDisverestado(boolean disverestado) {
		this.disverestado = disverestado;
	}

	public boolean isDisverremite() {
		return disverremite;
	}

	public void setDisverremite(boolean disverremite) {
		this.disverremite = disverremite;
	}

	public List getItemscombo() {
		return itemscombo;
	}

	public void setItemscombo(List itemscombo) {
		this.itemscombo = itemscombo;
	}

	public String getOpcion_asunto() {
		return opcion_asunto;
	}

	public void setOpcion_asunto(String opcion_asunto) {
		this.opcion_asunto = opcion_asunto;
	}

	public String getDetalle_asunto() {
		return detalle_asunto;
	}

	public void setDetalle_asunto(String detalle_asunto) {
		this.detalle_asunto = detalle_asunto;
	}

	public boolean isVerImpresion() {
		return verImpresion;
	}

	public void setVerImpresion(boolean verImpresion) {
		this.verImpresion = verImpresion;
	}

	public String getLs_ubicacion() {
		return ls_ubicacion;
	}

	public void setLs_ubicacion(String ls_ubicacion) {
		this.ls_ubicacion = ls_ubicacion;
	}

	public String getAsuntollave() {
		return asuntollave;
	}

	public void setAsuntollave(String asuntollave) {
		this.asuntollave = asuntollave;
	}

	public boolean isEntrada() {
		return entrada;
	}

	public void setEntrada(boolean entrada) {
		this.entrada = entrada;
	}

	public boolean isSalida() {
		return salida;
	}

	public void setSalida(boolean salida) {
		this.salida = salida;
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

	public String getVtipoN() {
		return vtipoN;
	}

	public void setVtipoN(String vtipoN) {
		this.vtipoN = vtipoN;
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

	

	public String getVtipoorigenN() {
		return vtipoorigenN;
	}

	public void setVtipoorigenN(String vtipoorigenN) {
		this.vtipoorigenN = vtipoorigenN;
	}

	public String getVtipoorigenA() {
		return vtipoorigenA;
	}

	public void setVtipoorigenA(String vtipoorigenA) {
		this.vtipoorigenA = vtipoorigenA;
	}

	public String getUbicacionllave() {
		return ubicacionllave;
	}

	public void setUbicacionllave(String ubicacionllave) {
		this.ubicacionllave = ubicacionllave;
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

	public Date getDfecdiscoN() {
		return dfecdiscoN;
	}

	public void setDfecdiscoN(Date dfecdiscoN) {
		this.dfecdiscoN = dfecdiscoN;
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

	public Date getDfecdiscoA() {
		return dfecdiscoA;
	}

	public void setDfecdiscoA(Date dfecdiscoA) {
		this.dfecdiscoA = dfecdiscoA;
	}

	public Long getNumeroBPM() {
		return numeroBPM;
	}

	public void setNumeroBPM(Long numeroBPM) {
		this.numeroBPM = numeroBPM;
	}

	public int getnIndRemitente() {
		return nIndRemitente;
	}

	public void setnIndRemitente(int nIndRemitente) {
		this.nIndRemitente = nIndRemitente;
	}

	public String getVnumexp() {
		return vnumexp;
	}

	public void setVnumexp(String vnumexp) {
		this.vnumexp = vnumexp;
	}

	public int getIndremitentellave() {
		return indremitentellave;
	}

	public void setIndremitentellave(int indremitentellave) {
		this.indremitentellave = indremitentellave;
	}

	public String getVnumdnirucA() {
		return vnumdnirucA;
	}

	public void setVnumdnirucA(String vnumdnirucA) {
		this.vnumdnirucA = vnumdnirucA;
	}

	public List getItems11() {
		return items11;
	}

	public void setItems11(List items11) {
		this.items11 = items11;
	}

	public Date getDfeclineaA() {
		return dfeclineaA;
	}

	public void setDfeclineaA(Date dfeclineaA) {
		this.dfeclineaA = dfeclineaA;
	}

	public void setmBeandocumentosalida(
			MBeanDocumentosSalientes mBeandocumentosalida) {
		this.mBeandocumentosalida = mBeandocumentosalida;
	}

	public long getNis_radN() {
		return nis_radN;
	}

	public void setNis_radN(long nis_radN) {
		this.nis_radN = nis_radN;
	}

	public long getNis_radA() {
		return nis_radA;
	}

	public void setNis_radA(long nis_radA) {
		this.nis_radA = nis_radA;
	}

	public void setSeguimientodocumentoDAO(SeguimientoEntranteDAO seguimientodocumentoDAO) {
		this.seguimientodocumentoDAO = seguimientodocumentoDAO;
	}

	public void setDocumentoSalidaDAO(DocumentoSalidaDAO documentoSalidaDAO) {
		this.documentoSalidaDAO = documentoSalidaDAO;
	}
}
