package com.sedapal.tramite.mbeans;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.j2solutionsit.fwk.patterns.jsf.bean.BaseSortableList;
import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.icefaces.tutorial.facelets.NavigationBean;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.EntranteMesaDAO;
import com.sedapal.tramite.dao.SecuencialDAO;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.nova.util.RecursoReporte;
import com.sedapal.tramite.nova.util.RecursoReporteNuevo;
import com.sedapal.tramite.servicios.IServiciosEntranteMesa;
import com.sedapal.tramite.servicios.IServiciosSelection;
import com.sedapal.tramite.servicios.IServiciosUsuarios;
import com.sedapal.tramite.servicios.util.MailService;
import com.sedapal.tramite.tree.TreeControllerMesa;
import com.sedapal.tramite.util.Utils;

public class MBeanEntrantesMesa extends BaseSortableList implements
		IMBeanEntrantesMesa, Serializable {

	private boolean verAlertaConfirmacionBorrar;
	private String msg;
	private boolean editarEmpresa = true;
	private MCarga mCarga;
	private NavigationBean navigation;
	private TreeControllerMesa treeControllerMesa;
	private List<EntranteMesaBean> entrantesmesa;
	private List<SelectionBean> selection;
	public IServiciosEntranteMesa serviciosEntranteMesa;
	private SecuencialDAO secuencialDAO;
	
	// para el reporte
	private RecursoReport reporte; // inyectado
	private RecursoReport recursoReport;// para displayar	
	private RecursoReporte reportes; // inyectado
	private RecursoReporte recursoReporte;// para displayar
	private RecursoReporteNuevo reporteNuevo; // inyectado 
    private RecursoReporteNuevo recursoReporteNuevo; // para displayar
	
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private ExternalContext ec = null;
	private Map<String, Object> parametros2 = new HashMap<String, Object>();
	private ExternalContext ec2 = null;
	Calendar c = Calendar.getInstance();
	private Map<String, Object> parametros3 = new HashMap<String, Object>();
    private ExternalContext ec3 = null;
	// /
	public IServiciosSelection serviciosselection;
	private List items1;
	private List items2;
	private List items3;
	private List items4;
	private List items5;
	private List items6;
	private List items7;
	private List items10;
	// control de seleccion multiple
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
	
	// para el formulario nuevo
	private int nanoN;
	private String origenN;
	private String vtipodocN;
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
	private int naccionN;
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
	private String areadeseleccionadaN;
	private String vaccion_grupoaN;
	private String vaccion_grupobN;
	private String votrosN;
	private int diasN;
	private String anodocumentoentrN;
	private String sistemaN="SEDAPAL";
	private int nfolioN=0;
	private String vtipoorigenN;
	private String vnumero_loteN;
	private String vnumero_discoN;
	private Date dfecdiscoN;
	// para Actualizar
	private int nanoA;
	private int norigenA;
	private String origenA;
	private String vtipodocA;
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
	private String dirigidoA = "NADA";
	private String dirigidoAA;
	private String area_origenA;
	private String centroA;
	private int ncodarea_origenA;
	private long ficha_derivadoA;
	private int nrepresentanteA;
	private String areaseleccionadaA;
	private String representanteA;
	private int naccionA;
	private String vaccion_grupoaA;
	private String vaccion_grupobA;
	private String votrosA;
	private int diasA;
	private String anodocumentoentrA;
	private String sistemaA;
	private int nfolioA;
	private String vtipoorigenA;
	private String vnumero_loteA;
	private String vnumero_discoA;
	private Date dfecdiscoA;	
	/////
	private boolean ver = false;
	private boolean verNuevo = false;
	private boolean verCatalogo = true;
	private boolean verActualizar = false;
	private boolean cerrarVentanta = false;
	private boolean activo = true;
	
	private boolean verPDF = false;
    private String rutaPDFPopup;
	
	
	// private boolean disPrint = false;
	// binding al combo
	private HtmlSelectOneMenu combo;
	private String desc;
	private Logger logger = Logger.getLogger("R1");
	private boolean verDetalles = false;
	private boolean verCatalogoEntrada = false;
	private boolean verAutocompletar = true;
	private boolean verRemitente = false;
	private HtmlInputText botonCodigo;
	private String area;
	private String area_deriva;
	private String codigo_representante;
	private String origen;
	private int area_derivador;
	private int area_remite;
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
	String suma_areas;
	private int cont = 0;
	private int cont2 = 0;
	private String nombre_archivo;
	private String ubicacion;
	private String valores = "";
	private int dato_remitente;
	private String cadenaDigitada;
	// area seleccionadas
	private int areas_actuales;
	// items seleccionados de las acciones deo doc
	// private List<String> selectedItems;
	private List<String> selectedItems1;
	// /Llaves
	private int anollave;
	private int origenllave;
	private String tipodocllave;
	private long correlativollave;
	private String numdocllave;
	// entidades externas
	private String login;
	private String textoA;
	private String empresa;
	// filtro de impresion
	private String corrinicial = "0";
	private String corrfinal = "0";
	String inicio = "0";
	String fin = "0";
	private EntranteMesaDAO entranteMesaDAO;
	private LinkedHashMap<String, Object> observaciones1;
	// Agregado el 16/11/2011 - Envio de correos - Alfredo Panitz
	@Autowired
	private IServiciosUsuarios serviciosUsuarios;
	@Autowired
	private MailService mailService;
	// /Autocompletar Eli Diaz Horna 26/03/2012
	private List<SelectItem> posiblespersonas;
	private String personaSeleccionada;
	private String labelPersona;

	// /AutocompletarA Eli Diaz Horna 26/03/2012
	private List<SelectItem> posiblespersonasA;
	private String personaSeleccionadaA;
	private String labelPersonaA;
	private long fichaA = 0;
	private String derivadoA;
	private String derivadoAA;
	private boolean disBoton = false;
	private boolean disBotonRemitente = true;
	
	// para los asuntos estadares
	private List<TiposBean> itemsAsuntos;
	private List<TiposBean> asuntos;
    private boolean verdocs;
    private String opcion_asunto;
    private String detalle_asunto;
    
    // variables para enlazar documentos
    private String doc_entrada;
    private String anodocumentoentr;
    private String opcion_seguimiento = "NADA";
    
  //variable direccion archivo pdf
    private String ls_ubicacion;
   
    

	@PostConstruct
	public void carga() {
		selectedItems1 = new ArrayList<String>();
		observaciones1 = new LinkedHashMap<String, Object>();
		observaciones1.put("01.-Por Disposición", "11");
		observaciones1.put("02.-Coordinar Con", "12");
		observaciones1.put("03.-Acción Necesaria", "13");
		observaciones1.put("04.-Adjuntar Antecedentes", "14");
		observaciones1.put("05.-Revisar/Informar", "15");
		observaciones1.put("06.-Preparar Respuesta", "16");
		observaciones1.put("07.-Conocimientos y Fines", "17");
		observaciones1.put("08.-Su aprobación", "18");
		observaciones1.put("09.-Tener Pendiente", "19");
		observaciones1.put("10.-Resolver", "20");
		observaciones1.put("11.-Archivar", "21");
		observaciones1.put("12.-Para Directorio", "22");
		observaciones1.put("13.-Para  trámite", "23");
		observaciones1.put("14.-Contestar directamente", "24");
		observaciones1.put("15.-Otros", "25");
		// Reporte
		reporte.asignar("ticket.pdf", ec, parametros, "reportes/mesa.jasper");// se
		// le
		// puede
		// adicionar
		// mas
		// parametros...
		reportes.asignar("ticket2.pdf", ec2, parametros2,
				"reportes/blanco.jasper");//
		recursoReport = reporte;
		recursoReporte = reportes;
	}

	// Autocompletar ------Inserta Eli Diaz 26/03/2012
	public void autocompletarPersona(ValueChangeEvent event) {
		// Se comprueba la instancia del objeto input
		if (event.getComponent() instanceof SelectInputText) {
			// se extrae la instancia del componente Selectinputtext
			SelectInputText autoComplete = (SelectInputText) event
					.getComponent();
			// Atraves del evento se extrae lo que se digito en el
			// SelectInputText
			cadenaDigitada = (String) event.getNewValue();
			// Se aáade a la propiedad posiblepersonas lo que devuelve
			// el metodo buscaPersona
			// System.out.println("Imprime Eli DIaz");
			// System.out.println(cadenaDigitada);
			this.posiblespersonas = buscaPersona(cadenaDigitada);
			// en el if se comprueba si existe alguna seleccion
			if (autoComplete.getSelectedItem() != null) {
				// Se extrae el objeto seleccionado
				RemitenteBean personaSel = (RemitenteBean) autoComplete
						.getSelectedItem().getValue();
				// a labelPersona la modificamos segun lo seleccionado
				// this.labelPersona = "La persona seleccionada es : " +
				// personaSel.getApellido() + " " + personaSel.getNombre();
				// this.labelPersona = "La persona seleccionada es : " +
				// personaSel.getDescripcion();
				this.vremitenteN = Integer.toString(personaSel.getCodigo());
				area_remite = personaSel.getCodigo();
				// vremitenteN
				// List<RemitenteBean> remitentes =
				// entranteMesaDAO.remitentes();
				// List itemsRemitentes = Utils.getRemitentes(remitentes);
				// this.items1 = itemsRemitentes;

				List<RepresentanteBean> remite = entranteMesaDAO
						.representante(area_remite);
				List itemsrepresentante = Utils.getRepresentante(remite);
				this.items3 = itemsrepresentante;
			}
		}
	}

	// Autocompletar cuando se modifica un documento de entrada------Inserta Eli
	// Diaz 30/10/2012
	public void autocompletarPersonaA(ValueChangeEvent changeEvent) {
		// Se comprueba la instancia del objeto input
		if (changeEvent.getComponent() instanceof SelectInputText) {
			// se extrae la instancia del componente Selectinputtext
			SelectInputText autoComplete = (SelectInputText) changeEvent
					.getComponent();
			// Atraves del evento se extrae lo que se digito en el
			// SelectInputText
			cadenaDigitada = (String) changeEvent.getNewValue();
			// Se aáade a la propiedad posiblepersonas lo que devuelve
			// el metodo buscaPersona
			// System.out.println("Imprime Eli DIaz");
			// System.out.println(cadenaDigitada);
			this.posiblespersonasA = buscaPersonaA(cadenaDigitada);

			// en el if se comprueba si existe alguna seleccion
			if (autoComplete.getSelectedItem() != null) {
				// Se extrae el objeto seleccionado
				RemitenteBean personaSelA = (RemitenteBean) autoComplete
						.getSelectedItem().getValue();
				// a labelPersona la modificamos segun lo seleccionado
				// this.labelPersona = "La persona seleccionada es : " +
				// personaSel.getApellido() + " " + personaSel.getNombre();
				// this.labelPersona = "La persona seleccionada es : " +
				// personaSel.getDescripcion();
				this.vremitenteA = Integer.toString(personaSelA.getCodigo());
				area_remite = personaSelA.getCodigo();
				// this.derivadoAA=this.vremitenteA;
				System.out.println("Eli quiere ver el origem");
				System.out.println(this.norigenA);

				// vremitenteN
				// List<RemitenteBean> remitentes =
				// entranteMesaDAO.remitentes();
				// List itemsRemitentes = Utils.getRemitentes(remitentes);
				// this.items1 = itemsRemitentes;

				// /Eli Agrega este codigo 14/11/2012
				List<RemitenteBean> remitente = entranteMesaDAO
						.remitentesA(area_remite);
				List itemsAreas = Utils.getRemitentes(remitente);
				this.items1 = itemsAreas;

				List<RepresentanteBean> remite = entranteMesaDAO
						.representante(area_remite);
				List itemsrepresentante = Utils.getRepresentante(remite);
				this.items3 = itemsrepresentante;

			}
		}
	}

	public List<SelectItem> buscaPersona(String cadenaDigitada) {
		// inicializamos un objeto del tipo SelectItem
		List<SelectItem> list = new ArrayList<SelectItem>();
		// List<Persona> list = new ArrayList<Persona>();
		List<RemitenteBean> ListaPersona = new ArrayList<RemitenteBean>();
		// System.out.println("Imprime Eli DIaz Paso 222");
		// System.out.println(cadenaDigitada);
		if (cadenaDigitada.length() >= 4) {
			ListaPersona = entranteMesaDAO.persona(cadenaDigitada);
		}
		// inicializamos un bucle for para recorrer el objeto persona
		for (RemitenteBean p : ListaPersona) {
			// for (Persona p : Persona) {
			// System.out.println(p.getCodigo());
			// System.out.println(p.getDescripcion());
			// preparamos la cadena a comparar y la pasamos a minusculas
			String cadenaPersona = (String.valueOf(p.getCodigo()) + "" + p
					.getDescripcion()).toLowerCase();
			cadenaDigitada = cadenaDigitada.toLowerCase();
			// con el metodo indesof del estring verificamos si la
			// cadenaDigitada esta contenida el la cadenaPersona, que con-
			// tiene el apellido y nombre de la Persona
			if (cadenaPersona.indexOf(cadenaDigitada) >= 0) {
				SelectItem item = new SelectItem(p, p.getDescripcion());
				list.add(item);
			}

		}
		// persona = ListaPersona;
		return list;
	}

	public List<SelectItem> buscaPersonaA(String cadenaDigitada) {
		// inicializamos un objeto del tipo SelectItem
		List<SelectItem> list = new ArrayList<SelectItem>();
		// List<Persona> list = new ArrayList<Persona>();
		List<RemitenteBean> ListaPersona = new ArrayList<RemitenteBean>();
		// System.out.println("Imprime Eli DIaz Paso 222");
		// System.out.println(cadenaDigitada);
		if (cadenaDigitada.length() >= 4) {
			ListaPersona = entranteMesaDAO.personaExterna(cadenaDigitada);
		}
		// inicializamos un bucle for para recorrer el objeto persona
		for (RemitenteBean p : ListaPersona) {
			// for (Persona p : Persona) {
			// System.out.println(p.getCodigo());
			// System.out.println(p.getDescripcion());
			// preparamos la cadena a comparar y la pasamos a minusculas
			String cadenaPersona = (String.valueOf(p.getCodigo()) + "" + p
					.getDescripcion()).toLowerCase();
			cadenaDigitada = cadenaDigitada.toLowerCase();
			// con el metodo indesof del estring verificamos si la
			// cadenaDigitada esta contenida el la cadenaPersona, que con-
			// tiene el apellido y nombre de la Persona
			if (cadenaPersona.indexOf(cadenaDigitada) >= 0) {
				SelectItem item = new SelectItem(p, p.getDescripcion());
				list.add(item);
			}

		}
		// persona = ListaPersona;
		return list;
	}

	public void confirmaBorrado(ActionEvent actionEvent) {

		try {
			EntranteMesaBean p = null;
			for (EntranteMesaBean q : this.entrantesmesa) {
				if (q.isSelected()) {
					p = q;
				}
			}
			if (p == null) {
				this.error = "Debe seleccionar por lo menos un registro";
				this.ver = true;
			} else {

				HttpSession session = (HttpSession) FacesContext
						.getCurrentInstance().getExternalContext().getSession(
								false);
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
				origen = p.getOrigen();
				tipodoc = p.getVtipodoc();
				ncodarea = String.valueOf(p.getNcodarea());
				codigo = String.valueOf(p.getNcorrelativo());

				// le paso como parametro al stored
				serviciosEntranteMesa.eliminarEntrantemesa(anno, origen, tipodoc, codigo, login);

				this.error = "Registro Eliminado!";

				selectedEntrantesMesa.clear();
				this.entrantesmesa = serviciosEntranteMesa.catalogo(ncodarea);// actualiza
				// el
				// reporte
				this.ver = true;
				this.verAlertaConfirmacionBorrar = false;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cancelaBorrado(ActionEvent actionEvent) {
		this.verAlertaConfirmacionBorrar = false;
	}
	
	public void eventVerDocs(ActionEvent actionEvent) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_login = String.valueOf(usuario.getCodarea());
        this.verdocs = true;
        
        this.asuntos = entranteMesaDAO.asuntos_estandares();
        List entrante = this.asuntos;
        this.itemsAsuntos = entrante;
        this.opcion_asunto = "";
        this.detalle_asunto = "";
        
    }
	
	 
	    
	public void buscarAsunto(ActionEvent actionEvent) {  
		 if (this.detalle_asunto.equals("") || this.opcion_asunto.equals("")) {
			 this.error = "Debe de Ingresar una opcion de busqueda";
	         this.ver = true;
		 }	else {
	         this.asuntos = entranteMesaDAO.busqueda_asuntos_estandar(opcion_asunto, detalle_asunto);
	         List entrante = this.asuntos;
	         this.itemsAsuntos = entrante;
		 }

    }
	
	public void RefrescarAsunto(ActionEvent actionEvent){
		
		 this.asuntos = entranteMesaDAO.asuntos_estandares();
	     List entrante = this.asuntos;
	     this.itemsAsuntos = entrante;
	     this.detalle_asunto = "";
		
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
           
            this.verdocs = false;
            
        }

    }
	
	public void cancelarDocs(ActionEvent actionEvent) {
		this.verPDF = false;
        this.verdocs = false;
    }
	

	public void eventEliminar(ActionEvent event) {
		// se adiciona estas dos lineas
		this.msg = "Realmente desea eliminarlo?";
		this.verAlertaConfirmacionBorrar = true;

		/*
		 * EntranteMesaBean p = null; for (EntranteMesaBean q :
		 * this.entrantesmesa) { if (q.isSelected()) p = q; } if (p==null) {
		 * this.error = "Debe seleccionar por lo menos un registro"; this.ver =
		 * true; } else {
		 * 
		 * HttpSession session =
		 * (HttpSession)FacesContext.getCurrentInstance().getExternalContext
		 * ().getSession(false); /////guardando en sesion un objeto String
		 * login=""; Usuario usuario = null;
		 * usuario=(Usuario)session.getAttribute("sUsuario");
		 * login=usuario.getLogin(); // llamamos a servicios o dao pasandole los
		 * datos a eliminar String codigo=""; String anno=""; String origen="";
		 * String tipodoc=""; String ncodarea=""; anno =
		 * String.valueOf(p.getNano()); origen = p.getOrigen(); tipodoc =
		 * p.getVtipodoc(); ncodarea = String.valueOf(p.getNcodarea()); codigo =
		 * String.valueOf(p.getNcorrelativo());
		 * 
		 * // le paso como parametro al stored
		 * serviciosEntranteMesa.eliminarEntrantemesa(anno, origen, tipodoc,
		 * codigo, login);
		 * 
		 * 
		 * this.error = "Registro Eliminado!"; selectedEntrantesMesa.clear();
		 * this.entrantesmesa = serviciosEntranteMesa.catalogo(ncodarea);//
		 * actualiza el // reporte this.ver = true; }
		 */
	}
	
	
	public void eventoTab(TabChangeEvent event) {
		EntranteMesaBean bean = null;
		for (EntranteMesaBean p : this.entrantesmesa) {
			if (p.isSelected()) {
				bean = p;
			}
		}
		if (event.getNewTabIndex() == 1 || event.getNewTabIndex() == 2
				&& bean != null) {
			// clear our list, so that we can build a new one
			selectedEntrantesMesa.clear();

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
			EntranteMesaBean employee;
			for (int i = 0, max = entrantesmesa.size(); i < max; i++) {
				employee = (EntranteMesaBean) entrantesmesa.get(i);
				if (employee.isSelected()) {
					selectedEntrantesMesa.add(employee);
				}
			}
			for (EntranteMesaBean p : selectedEntrantesMesa) {
				// System.out.println("VER EL DETALLE");
				System.out.println(p.getFicha_dirigido() + " "
						+ p.getNdirigido() + " " + p.getVubiarchivo());
				this.mCarga.setNombrePdf(p.getVubiarchivo());
				this.anollave = p.getNano();
				this.origenllave = p.getNorigen();
				this.tipodocllave = p.getVtipodoc();
				this.correlativollave = p.getNcorrelativo();
				this.numdocllave = p.getVnumdoc();
				//
				this.treeControllerMesa.inicializarArbol(String.valueOf(p
						.getNcorrelativo()), String.valueOf(p.getNano()));
				//
				this.dato_remitente = p.getNdirigido();
				this.ndirigidoA = p.getNdirigido();
				this.dirigido = p.getNdirigido();
				this.vremitenteA = String.valueOf(p.getNdirigido());
				this.representanteA = String.valueOf(p.getNdirigido());
				this.vaccionA = p.getVaccion();
				cont2 = 0;
				// this.dirigidoA = String.valueOf(p.getNdirigido());
				// this.dirigidoA = String.valueOf(p.getNremitente());
				this.nremitenteA = p.getNremitente();
				this.ficha_dirigidoA = p.getFicha_dirigido();
				this.ficha_derivadoA = p.getFicha_derivado();

				// this.vestadoA = p.getNestado();
				ruta = p.getVubiarchivo();
				this.ind_adjuntar = p.getNindicador();
				this.vubiarchivoA = p.getVubiarchivo();

			}
			/*
			 * If application developers do not rely on validation and want to
			 * bypass UPDATE_MODEL and INVOKE_APPLICATION stages, they may be
			 * able to use the following statement:
			 * FacesContext.getCurrentInstance().renderResponse(); to send
			 * application to RENDER_RESPONSE phase shortening the app. cycle
			 */

		}
	}

	public void eventActualizar(ActionEvent evt) {
		this.limpiarActualizar();
		EntranteMesaBean p = null;
		for (EntranteMesaBean q : this.entrantesmesa) {
			if (q.isSelected()) {
				p = q;
			}
		}
		if (p != null) {
			this.verActualizar = true;
			this.verCatalogo = false;
			this.nanoA = p.getNano();
			this.origenA = String.valueOf(p.getNorigen());
			this.norigenA = p.getNorigen();
			this.vtipodocA = p.getVtipodoc();
			this.ncorrelativoA = p.getNcorrelativo();
			this.vnumdocA = p.getVnumdoc();
			this.vmesaparteA = p.getVmesaparte();
			this.areaseleccionadaA = p.getVremitente();
			this.ndirigidoA = p.getNdirigido();
			// this.dirigidoA = String.valueOf(p.getNcodarea());
			this.dfecdocA = p.getDfecdoc();
			this.dfecregistroA = p.getDfecregistro();
			this.vasuntoA = p.getVasunto();
			this.vreferenciaA = p.getVreferencia();
			this.vobservacionA = p.getVobservacion();
			// this.vaccionA = p.getVaccion();
			this.vprioridadA = p.getVprioridad();
			this.vubiarchivoA = p.getVubiarchivo();
			this.dfecplazoA = p.getDfecplazo();
			this.ndiasplazoA = p.getNdiasplazo();
			this.vestadoA = p.getNestado();
			this.nindicadorA = p.getNindicador();
			this.ind_adjuntar = p.getNindicador();
			this.dfeccreA = p.getDfeccre();
			this.dfecactA = p.getDfecact();
			this.vrescreA = p.getVrescre();
			this.vresactA = p.getVresact();
			this.ncodcentroA = p.getNcodcentro();
			this.vremitenteA = String.valueOf(p.getNdirigido());
			this.ficha_dirigidoA = p.getFicha_dirigido();
			// this.representanteA = String.valueOf(p.getFicha_dirigido());
			this.ncodareaA = p.getNcodarea();
			this.vareaA = p.getArea();
			this.area_origenA = p.getArea_origen();
			this.centroA = p.getCentro();
			this.ficha_derivadoA = p.getFicha_derivado();
			this.sistemaA = p.getSistema();
	        this.nfolioA = p.getNfolio();	       
	        this.vtipoorigenA = p.getVtipoorigen();
			// this.ficha_dirigidoA=p.getFicha_derivado();
			// this.ficha_derivadoA=p.getNremitente();
			this.ndiasplazoA = p.getNdiasplazo();
			// datos nuevos linea digitalizacion 08/09/202 EDH
			this.vnumero_loteA = p.getVlote();
			this.dfecdiscoA = p.getDfecdisco();
			this.vnumero_discoA = p.getVnumero_disco();
			
			dato_remitente = this.ndirigidoA;
			this.textoA = "El Registro tiene " + this.nindicadorA
					+ " Archivo Adjunto";
			// /
			List<RepresentanteBean> representante = entranteMesaDAO
					.representante(dato_remitente);
			List itemsRepresentante = Utils.getRepresentante(representante);
			this.items3 = itemsRepresentante;
			this.anollave = this.nanoA;
			this.origenllave = this.norigenA;
			this.tipodocllave = this.vtipodocA;
			this.correlativollave = this.ncorrelativoA;

			List<AreaBean> areas = entranteMesaDAO.areas_seleccionadas(
					this.anollave, this.origenllave, this.tipodocllave,
					this.correlativollave);

			
			this.items7b = Utils.getAreasLink(areas);
			for (AreaBean q : areas) {
				this.items7a.remove(q.getNombre());
			}
			this.areas_actuales = this.items7b.size();
			System.out.println("Num Areas Actuales:" + areas_actuales);
			
			
			
			// List<TrabajadorBean> trabajador =
			// entranteMesaDAO.trabajador(this.ndirigidoA);
			// List itemsTrabajador = Utils.getTrabajador(trabajador);
			// this.items3 = itemsTrabajador;
			// for (TrabajadorBean t: trabajador);

			// /
			// String codigo_accion= this.vaccionA;
			// String tempo = null;
			// int i=0, j= 0;
			// while( i < codigo_accion.length())
			// {
			// tempo= codigo_accion.substring(i ,i+2);
			// asigna al arreglo
			// selectedItems1.add(tempo);
			// j++;
			// i+=2;
			// }
			// this.observaciones=Utils.getAreasLink(entranteDAO.areas_seleccionadas(this.anollave,this.origenllave,this.tipodocllave,this.correlativollave));
			// if (this.norigenA == 1) {
			// List<AreaBean> areass = entranteMesaDAO.areas();
			// List itemsAreas = Utils.getAreas(areass);
			// this.items1 = itemsAreas;
			// for (AreaBean r: areass){
			// areas_remite=r.getCodigo();

			// List<TrabajadorBean> trabajador = entranteMesaDAO
			// .trabajador(this.ndirigidoA);
			// List itemsTrabajador = Utils.getTrabajador(trabajador);
			// this.items3 = itemsTrabajador;

			// } else {

			List<RemitenteBean> remitentes = entranteMesaDAO.remitentesA(this.ndirigidoA);
			List itemsderivados = Utils.getRemitentes(remitentes);
			this.items1 = itemsderivados;

			List<RepresentanteBean> representantes = entranteMesaDAO.representante(this.ndirigidoA);
			List itemsrepresentante = Utils.getRepresentante(representantes);
			this.items3 = itemsrepresentante;
			
			List<AreaBean> area_derivados = entranteMesaDAO.areas();
			List itemsderivadoss = Utils.getAreas(area_derivados);
			this.items7a = Utils.getAreasLink(area_derivados);

			// }

			selectedEntrantesMesa.clear();
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
			this.entrantesmesa = serviciosEntranteMesa.catalogo(ncodarea);// actualiza
			// el
			// reporte

		} else {
			this.error = "Debe seleccionar solo un registro para modificar";
			this.ver = true;
		}
	}

	public void eventOpenpdf(ActionEvent evt) throws URISyntaxException {

		if (this.selectedEntrantesMesa.size() < 1) {
			this.error = "Debe seleccionar por lo menos un Documento";
			this.ver = true;
		} else {
			// Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+ruta);
			// Path.context(vubiarchivoA);
			// Desktop.getDesktop().open(file);
			// System.out.println("Imprime");
			// path = new
			// File(getClass().getResource("/paqueteManuales/MenuPrincipal.pdf").toURI());
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	public void eventLimpiar(ActionEvent actionEvent) {
		this.items3.clear();
		this.personaSeleccionada = "";		
		this.posiblespersonas.clear();		
	}

	public void eventLimpiarA(ActionEvent actionEvent) {
		this.disBoton = true;
		this.disBotonRemitente = false;
		this.items3.clear();
		this.personaSeleccionada = "";
		this.personaSeleccionadaA = "";
	}

	public void verRemitente(ActionEvent event) {

		this.editarEmpresa = false;
		this.empresa = "";

	}

	public void eventDetalles(ActionEvent evt) {
		// this.limpiarDetalles();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		/*
		 * List<SecuencialBean> bean =
		 * this.secuencialDAO.correlativo(vtipodocN); SecuencialBean
		 * secuencialBean = bean.get(0); int contador =
		 * secuencialBean.getCorrelativo(); contador++; String secuencia =
		 * String.valueOf(contador); session.setAttribute("nombrePdf",
		 * secuencia);
		 */
		session.setAttribute("tipodoc", vtipodocN);
		session.setAttribute("indicador", 4);
		ind_adjuntar = 1;
		this.verDetalles = true;

		selectedEntrantesMesa.clear();
		// this.productos = serviciosProducto.catalogo();// actualiza el
		// reporte

	}
	
	
    /* Evento visualizar PDF*/
    public void evenVerPDF(ActionEvent evt) {
    	EntranteMesaBean p = null;
		for (EntranteMesaBean q : this.entrantesmesa) {
			if (q.isSelected()) {
				p = q;
			}
		}
         if (p != null){

            this.rutaPDFPopup = p.getUbicacion_mesa();
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
		/*
		 * List<SecuencialBean> bean =
		 * this.secuencialDAO.correlativo(vtipodocA); SecuencialBean
		 * secuencialBean = bean.get(0); int contador =
		 * secuencialBean.getCorrelativo(); contador++; String secuencia =
		 * String.valueOf(contador); session.setAttribute("nombrePdf",
		 * secuencia);
		 */
		session.setAttribute("tipodoc", vtipodocA);
		session.setAttribute("indicador", 4);
		ind_adjuntar = 1;
		this.verDetalles = true;
		for (EntranteMesaBean d : selectedEntrantesMesa) {
		}
		selectedEntrantesMesa.clear();
		// this.productos = serviciosProducto.catalogo();// actualiza el
		// reporte

	}

	public void setServiciosEntranteMesa(
			IServiciosEntranteMesa serviciosEntranteMesa) {
		this.serviciosEntranteMesa = serviciosEntranteMesa;
	}

	public void eventEntrada(ActionEvent evt) {
		this.verCatalogoEntrada = true;
		// for (EntranteBean p : selectedEntrantesMesa) {
		// }
	}

	public void eventActualizarEntrante(ActionEvent event) {
		HttpSession session = null;
		boolean formulario = true;
		try {
			// llama DAO/Stored Para actualizar producto
			this.verActualizar = true;
			EntranteMesaBean entrantemesaBean = new EntranteMesaBean();
			// /acediendo a sesion http
			session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
			// ///guardando en sesion un objeto
			Usuario usuario = null;
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
						List<ServidorBean> bean = this.entranteMesaDAO.servidor();
						for (ServidorBean e : bean) {
							ubicacion = e.getDescripcion();
						}
						Date date = new Date();
						String annio = Integer.toString(c.get(Calendar.YEAR));
						nombre_archivo = annio + nombre_archivo;
						this.vubiarchivoA = nombre_archivo;
					}
				}

				Set set = this.items7b.entrySet();
				Iterator i = set.iterator();
				String valor;

				int cont = 0;
				// captura areas seleccionadas modificadas o igual
				this.dirigidoAA = "";
				while (i.hasNext()) {
					cont++;
					Map.Entry val = (Map.Entry) (i.next());
					valor = val.getValue().toString();
					valores = valores + valor;
					this.dirigidoAA = valores;

				}
				if (this.dirigidoAA == null) {
					this.dirigidoAA = "NADA";
				}

				// le paso como parametro al stored
				entrantemesaBean.setNano(this.nanoA);
				entrantemesaBean.setNorigen(Integer.parseInt(this.origenA));
				entrantemesaBean.setVtipodoc(this.vtipodocA);
				entrantemesaBean.setNcorrelativo(this.ncorrelativoA);
				entrantemesaBean.setVnumdoc(this.vnumdocA);
				entrantemesaBean.setFicha_dirigido(this.ficha_dirigidoA);
				entrantemesaBean.setFicha_derivado(this.ficha_derivadoA);
				entrantemesaBean.setVasunto(this.vasuntoA);
				entrantemesaBean.setVreferencia(this.vreferenciaA);
				entrantemesaBean.setVobservacion(this.vobservacionA);
				entrantemesaBean.setVaccion(this.vaccionA);
				entrantemesaBean.setVprioridad(this.vprioridadA);
				entrantemesaBean.setVresact(this.vresactA);
				entrantemesaBean.setVubiarchivo(this.vubiarchivoA);
				entrantemesaBean.setDfecdoc(this.dfecdocA);
				entrantemesaBean.setNdiasplazo(this.ndiasplazoA);
				entrantemesaBean.setVestado(this.vestadoA);
				entrantemesaBean.setNremitente(Integer
						.parseInt(this.vremitenteA));
				entrantemesaBean.setVdirigido(this.dirigidoAA);
				entrantemesaBean.setNcodarea(this.ncodareaA);
				entrantemesaBean.setDfecregistro(this.dfecregistroA);
				entrantemesaBean.setSistema(this.sistemaA);
				entrantemesaBean.setNfolio(this.nfolioA);				
				// serviciosEntranteMesa.actualizarEntrantemesa(entrantemesaBean);
				Map outMap = serviciosEntranteMesa.actualizarEntrantemesa(entrantemesaBean);
				String out = (String) outMap.get("out");
				String correlativo = (String) outMap.get("outcorrelativo");
				// TODO : descomentar el metodo de abajo cuando se implemente
				// una salida al SP
				// String out =
				// serviciosEntranteMesa.nuevoEntrantemesa(entrantemesaBean);
				// out = out.trim();
				// String out = "0"; // codigo temporal, borrar esta linea luego
				System.out.println("OUT STORED!!!:" + out);

				if (out.equals("0")) {

					this.error = "Se actualizaron los campos correctamente";
					this.entrantesmesa = serviciosEntranteMesa
							.catalogo(ncodarea);// actualiza el reporte
					nombre_archivo = "";
					ubicacion = "";
					session.setAttribute("file", null);
					this.vubiarchivoA = "";
					this.ver = true;
					this.verCatalogo = true;
					this.verActualizar = false;
					this.disBoton = false;
					this.disBotonRemitente = true;
				}
			} else {
				// session.setAttribute("file", null);
				formulario = false;
				this.error = valida;
				this.ver = true;
				this.verActualizar = true;
			}

		} catch (Exception exception) {
			logger.error("[MBeanEntrantesMesa.eventActualizarEntrante]",
					exception);
			this.error = "Error Interno, comuniquese con el Administrador";
			this.ver = true;
			exception.printStackTrace();
			this.verActualizar = true;

		} finally {

			this.valores = "";
			this.verDetalles = false;
			// ind_adjuntar=0;
			// if(formulario)
			// session.setAttribute("file", null);

		}
	}

	public void cerrarCatalogoEntrada(ActionEvent event) {
		this.verCatalogoEntrada = false;
	}

	public void cerrar(ActionEvent event) {
		this.ver = false;
	}

	public void cerrarDetalles(ActionEvent event) {
		this.verDetalles = false;
		this.nindicadorN = 1;
		this.nindicadorA = 1;
		this.textoA = "El Registro tiene " + this.nindicadorN
				+ " Archivo Adjunto";
	}

	public void eventNuevo(ActionEvent event) {
		this.verAutocompletar = true;
		this.verRemitente = false;
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

	// Eli comenta este codigo 22/06/2016
	private String itemselectA[] = new String[1];

	public void pasaDerechaA(ActionEvent actionEvent) {
		itemselectA[0] = this.dirigidoA;		
		Utils.pasaDerecha(this.items7a, this.items7b, this.itemselectA);
	}

	public void pasaIzquierda(ActionEvent actionEvent) { // this.items7aSelected
		// = this.item
		for (int i = 0; i < this.items7bSelected.length; i++) {
			System.out.println(this.items7bSelected[i]);
			System.out.println(this.correlativollave);
			int area = Integer.parseInt(this.items7bSelected[i]);
			// long ncodderivado=Long.parseLong(this.items7bSelected[i]);
			// this.anollave=p.getNano();
			// this.origenllave=p.getNorigen();
			// this.tipodocllave=p.getVtipodoc();
			// secuencialDAO.updatecorrelativo(secuencial,tipodoc);
			entranteMesaDAO.updatedirigido(this.correlativollave, area, this.anollave);

		}

		Utils.pasaIzquierda(items7a, items7b, items7bSelected);
	}

	public void captura(ValueChangeEvent changeEvent) {
		System.out.println("Captura");
		area = (String) changeEvent.getNewValue();
		System.out.println(area);
		int area_remite = Integer.parseInt(area);
		if (area_remite > 600) {
			List<RepresentanteBean> remite = entranteMesaDAO
					.representante(area_remite);
			List itemsrepresentante = Utils.getRepresentante(remite);
			this.items3 = itemsrepresentante;
			// for (RepresentanteBean r: remite);

		} else {
			System.out.println(area);
			entranteMesaDAO.trabajador(Integer.parseInt(area));
			List<TrabajadorBean> trabajador = entranteMesaDAO
					.trabajador(Integer.parseInt(area));
			List itemsTrabajador = Utils.getTrabajador(trabajador);
			this.items3 = itemsTrabajador;
			// this.items6 = itemsTrabajador;
			// for (TrabajadorBean t: trabajador)
			// System.out.println(t.getArea());
		}

	}

	public void captura_accion(ValueChangeEvent changeEvent) {
		selectedItems1 = new ArrayList<String>();
		String acciones = this.vaccionA;
		System.out.println(acciones);

		for (String p : this.selectedItems1) {
			selectedItems1.add(p);
		}

	}

	/*
	 * public void captura_remite(ValueChangeEvent changeEvent) { // /ESTOY AQUI
	 * String dato = (String) changeEvent.getNewValue(); int area_remite =
	 * Integer.parseInt(dato); if (area_remite > 600) { List<RepresentanteBean>
	 * remite = entranteMesaDAO .representante(area_remite); List
	 * itemsrepresentante = Utils.getRepresentante(remite); this.items3 =
	 * itemsrepresentante; for (RepresentanteBean r : remite) ;
	 * 
	 * } else {
	 * 
	 * entranteMesaDAO.trabajador(area_remite); List<TrabajadorBean> trabajador
	 * = entranteMesaDAO .trabajador(area_remite); List itemsTrabajador =
	 * Utils.getTrabajador(trabajador); this.items3 = itemsTrabajador; for
	 * (TrabajadorBean t : trabajador) ; // System.out.println(t.getArea()
	 * 
	 * }
	 * 
	 * }
	 */

	public void captura_derivador(ValueChangeEvent changeEvent) {
		area_deriva = (String) changeEvent.getNewValue();
		// System.out.println(area_deriva);
		// entranteDAO.trabajador_derivador(Integer.parseInt(area_deriva));
		List<TrabajadorBean> trabajador_derivador = entranteMesaDAO
				.trabajador_derivador(Integer.parseInt(area_deriva));
		List itemsTrabajador = Utils.getTrabajador(trabajador_derivador);
		this.items6 = itemsTrabajador;
		for (TrabajadorBean t : trabajador_derivador)
			// System.out.println(t.getArea())
			;
	}

	public void captura_representante(ValueChangeEvent changeEvent) {

		codigo_representante = (String) changeEvent.getNewValue();

		entranteMesaDAO.representante(Integer.parseInt(codigo_representante));
		List<RepresentanteBean> remitente = entranteMesaDAO
				.representante(Integer.parseInt(codigo_representante));

		for (RepresentanteBean t : remitente) {
			nrepresentanteN = t.getVnombre();
		}
		// System.out.println(t.getDescripcion());
	}

	public void actualizar(ValueChangeEvent changeEvent) {

		int area = this.getNdirigidoA();
		entranteMesaDAO.trabajador(area);
		List<TrabajadorBean> trabajador = entranteMesaDAO.trabajador(area);
		List itemsTrabajador = Utils.getTrabajador(trabajador);
		this.items1 = itemsTrabajador;
		for (TrabajadorBean t : trabajador)
			;
	}

	public void actualizar_ficha(ValueChangeEvent changeEvent) {
		String area_derivada = String.valueOf(this.nremitenteA);
		entranteMesaDAO.trabajador_derivador(Integer.parseInt(area_derivada));
		List<TrabajadorBean> trabajador_derivador = entranteMesaDAO
				.trabajador_derivador(Integer.parseInt(area_derivada));
		List itemsTrabajador = Utils.getTrabajador(trabajador_derivador);
		this.items6 = itemsTrabajador;
		for (TrabajadorBean t : trabajador_derivador)
			;

	}

	public void Origen(ValueChangeEvent changeEv) {
		origen = (String) changeEv.getNewValue();

		int origenes = Integer.parseInt(origen);

		if (origenes == 1) {

			List<AreaBean> area = entranteMesaDAO.areas();
			List itemsArea = Utils.getAreas(area);
			this.items1 = itemsArea;
			for (AreaBean a : area)
				;

		} else {

			List<RemitenteBean> remitente = entranteMesaDAO.remitentes();
			List itemsRemitente = Utils.getRemitentes(remitente);
			this.items1 = itemsRemitente;
			for (RemitenteBean r : remitente)
				;
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

	public void eventDeseleccionarTrabajador(ActionEvent event) {
		// Falta codificar el sacar registros
		// area_dirigido = area_deriva + caracter - area_dirigido;
		// String dato = area_dirigido.split(area_dirigido).toString();
		// System.out.println(dato);
	}

	public void eventRegistrarEntrante(ActionEvent event) {
		HttpSession session = null;
		boolean formulario = true;
		this.disBotonGrabar = false;
		try {
			// /acediendo a sesion http
			session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
			System.out.println("Registando!!!");
			String valida = this.validarFormulario();
			System.out.println("valida!!!=" + valida);
			if (valida == null) {
				formulario = true;
				for (String p : this.selectedItems1) {
					// System.out.println("ITEM:"+ p);
					accion = p + accion;
					// System.out.println("completo:"+ accion);
					this.vaccionN = accion;

				}
				this.verNuevo = false;

				// ///guardando en sesion un objeto
				Usuario usuario = null;
				String file = null;
				usuario = (Usuario) session.getAttribute("sUsuario");
				// this.ncodareaN=usuario.getCodarea();
				this.vrescreN = usuario.getLogin();
				this.vresactN = usuario.getLogin();
				this.ncodarea_origenN = usuario.getCodarea();
				this.nremitenteN = Integer.parseInt(vremitenteN);
				 
				String ncodarea = String.valueOf(usuario.getCodarea());

				file = (String) session.getAttribute("file");
				if (file != null) {
					nombre_archivo = file;
					List<ServidorBean> bean = this.entranteMesaDAO.servidor();
					for (ServidorBean e : bean) {
						ubicacion = e.getDescripcion();
						// ubicacion = "http://1.1.194.53/mesa/";
					}
					Date date = new Date();
					String annio = Integer.toString(c.get(Calendar.YEAR));
					nombre_archivo = annio + nombre_archivo;
					this.vubiarchivoN = nombre_archivo;
				} else {
					this.vubiarchivoN = "NADA";
				}

				Set set = this.items7b.entrySet();
				Iterator i = set.iterator();
				String valor = null;
				suma_areas = "";
				while (i.hasNext()) {
					Map.Entry val = (Map.Entry) (i.next());
					System.out.println("value Hash !! : " + val.getValue());
					valor = (String) val.getValue();
					suma_areas += valor;

				}

				logger.debug("Areas seleccionadas:" + suma_areas);
				System.out.println("Areas seleccionadas:" + suma_areas);

				EntranteMesaBean entrantemesaBean = new EntranteMesaBean();
				entrantemesaBean.setOrigen(this.origenN);
				entrantemesaBean.setVtipodoc(this.vtipodocN);
				// entrantemesaBean.setNcodarea(this.ncodareaN);
				entrantemesaBean.setNcodarea(this.ncodarea_origenN);
				entrantemesaBean.setNcodarea_origen(this.ncodarea_origenN);
				// Eli Comenta
				// entrantemesaBean.setNremitente(this.nremitenteN);
				entrantemesaBean.setDfecregistro(this.dfecregistroN);
				entrantemesaBean.setVremitente(suma_areas);
				// entrantemesaBean.setVremitente(this.areaseleccionadaN);
				// entrantemesaBean.setVremitente(this.vremitenteN);
				entrantemesaBean.setVubiarchivo(this.vubiarchivoN);
				entrantemesaBean.setNdirigido(this.nremitenteN);
				entrantemesaBean.setFicha_dirigido(this.ficha_dirigidoN);
				entrantemesaBean.setVnumdoc(this.vnumdocN);
				entrantemesaBean.setVasunto(this.vasuntoN);
				entrantemesaBean.setVreferencia(this.vreferenciaN);
				entrantemesaBean.setVobservacion(this.vobservacionN);
				entrantemesaBean.setVaccion(this.vaccionN);
				entrantemesaBean.setVprioridad(this.vprioridadN);
				entrantemesaBean.setVestado(this.vestadoN);
				entrantemesaBean.setDfecdoc(this.dfecdocN);
				// entrantemesaBean.setDfecplazo(this.dfecplazoN);
				entrantemesaBean.setVrescre(this.vrescreN);
				entrantemesaBean.setVresact(this.vresactN);
				entrantemesaBean.setFicha_derivado(this.ficha_derivadoN);
				entrantemesaBean.setNdiasplazo(this.ndiasplazoN);
				entrantemesaBean.setNombreremitente(cadenaDigitada);
				// variables nuevas  09/03/2017
				//entrantemesaBean.setDoc_entrada(this.doc_entrada);
				//entrantemesaBean.setVanoentrada(this.anodocumentoentr);
				entrantemesaBean.setOpcion_seguimiento(this.opcion_seguimiento);
				entrantemesaBean.setNfolio(this.nfolioN);
				entrantemesaBean.setVtipoorigen("PRESENCIAL");				
				this.vnumero_loteN="0";
				this.vnumero_discoN =" ";
				
				if (this.opcion_seguimiento=="1"){
					entrantemesaBean.setDoc_entrada(this.doc_entrada);
					entrantemesaBean.setVanoentrada(this.anodocumentoentr);
				} else {
					entrantemesaBean.setDoc_entrada(this.doc_entrada);
					entrantemesaBean.setVanoentrada(this.anodocumentoentrN);
				}
				
				
				 
				
				Map outMap = serviciosEntranteMesa
						.nuevoEntrantemesa(entrantemesaBean);
				String out = (String) outMap.get("out");
				String correlativo = (String) outMap.get("outcorrelativo");
				// TODO : descomentar el metodo de abajo cuando se implemente
				// una salida al SP
				// String out =
				// serviciosEntranteMesa.nuevoEntrantemesa(entrantemesaBean);
				// out = out.trim();
				// String out = "0"; // codigo temporal, borrar esta linea luego
				System.out.println("OUT STORED!!!:" + out);

				if (out.equals("0")) {
					/* Agregado el 16/11/2011 - Alfredo Panitz */
					// Enviando Correo de acuerdo a evaluacion del documento
					// (Prioridad)
					String prioridad = entrantemesaBean.getVprioridad();
					if (prioridad.equals("URGENTE")) {
						String txtMensaje = "";
						String txtAsunto = "Nuevo Documento de Entrada de Prioridad URGENTE - Trámite Documentario Corporativo";
						txtMensaje = txtMensaje + txtAsunto + "\n\n";

						String strArrAreas = entrantemesaBean.getVremitente();
						System.out.println("StrArr Areas " + strArrAreas);
						strArrAreas = strArrAreas.replace("-", "");

						int cantAreas = strArrAreas.length() / 3;

						List listadoAux = new ArrayList();
						// Obtener la lista de Areas de acuerdo a si son AA o CC
						for (int cont = 0; cont < cantAreas; cont++) {
							String areaAct = strArrAreas.substring(0, 3);
							strArrAreas = strArrAreas.substring(3);

							listadoAux.add(areaAct);
						}
						// Filtrando las areas repetidas
						for (int cont = 0; cont < listadoAux.size(); cont++) {
							String areaIni = (String) listadoAux.get(cont);
							System.out.println("Area ini " + areaIni + " - "
									+ cont);
							if (!areaIni.equals("")) {
								for (int j = cont + 1; j < listadoAux.size(); j++) {
									String areaEval = (String) listadoAux
											.get(j);
									System.out.println("Area eval " + areaEval
											+ " - " + j);
									if (!areaEval.equals("")) {
										if (areaIni.equals(areaEval)) {
											listadoAux.set(j, "");
										}
									}
								}
							}
						}

						List listadoAreas = new ArrayList();
						// Creando un listado de areas limpio (sin espacios en
						// blanco) final
						for (int cont = 0; cont < listadoAux.size(); cont++) {
							String itemAct = (String) listadoAux.get(cont);
							if (!itemAct.equals("")) {
								listadoAreas.add(itemAct);
							}
						}

						for (int cont = 0; cont < listadoAreas.size(); cont++) {
							System.out.println((String) listadoAreas.get(cont));
						}

						for (int cont = 0; cont < listadoAreas.size(); cont++) {
							List<Usuario> listadoSecretarias = serviciosUsuarios
									.getSecretariasByCodArea(Integer
											.parseInt((String) listadoAreas
													.get(cont)));

							for (int j = 0; j < listadoSecretarias.size(); j++) {
								txtMensaje = txtAsunto + "\n\n";
								txtMensaje = txtMensaje + "Estimada(o) " + listadoSecretarias.get(j).getNombre().toUpperCase() + " :\n";
								txtMensaje = txtMensaje
										+ "Le recordamos que ha llegado a su Area un documento de importancia URGENTE.\n";
								txtMensaje = txtMensaje + "Correlativo : "
										+ correlativo + "\n";
								txtMensaje = txtMensaje + "Asunto : "
										+ entrantemesaBean.getVasunto()
										+ "\n";
								txtMensaje = txtMensaje + "Remitente : "
								+ entrantemesaBean.getNombreremitente()
								+ "\n";
								txtMensaje = txtMensaje
										+ "Favor de atenderlo a la brevedad posible.\n\n";
								txtMensaje = txtMensaje + "Atentamente,\n\n";
								txtMensaje = txtMensaje
										+ "Tramite Documentario Corporativo \n";
								txtMensaje = txtMensaje
										+ "Por favor no responder este Email";

								Boolean envioCorreo = serviciosUsuarios
										.enviarEmailByFichaUsu(
												listadoSecretarias.get(j)
														.getFicha(),												
												txtMensaje, txtAsunto);
								
								/*
								if (envioCorreo) {
									mailService
											.enviarMail("El area del usuario "
													+ listadoSecretarias.get(j)
															.getNombre()
															.toUpperCase()
													+ " recibio un documento\n"
													+ "de importancia URGENTE y este ha sido notificado");
								} 
								
								else {
									mailService
											.enviarMail("El area del usuario "
													+ listadoSecretarias.get(j)
															.getNombre()
															.toUpperCase()
													+ " recibio un documento\n"
													+ "de importancia URGENTE y pero este, al no contar con un correo registrado en la tabla de Trabajadores, no pudo ser notificado.");
								}
								*/
								
							}
						}
					}
				}

				this.entrantesmesa = serviciosEntranteMesa.catalogo(ncodarea);// actualiza
				// el
				// reporte
				this.error = "Se Grabó Satisfactoriamente";
				this.selectedItems1.clear();				
				this.accion = "";
				ind_adjuntar = 0;
				this.ver = true;
				this.verCatalogo = true;
				this.verActualizar = false;
				// ***** editar ...
				this.editarEmpresa = true;
				this.eventRefrescar();

			} else {
				formulario = false;
				this.error = valida;
				this.ver = true;
			}

		} catch (Exception e) {
			logger.error("Fallo el registro", e);
			this.error = "Transaccion No valida.";
			this.ver = true;
			this.verCatalogo = true;
			this.verActualizar = false;
			this.eventRefrescar();
		} finally {
			nombre_archivo = "";
			this.vubiarchivoN = "";
			if (formulario) {
				session.setAttribute("file", null);
			}
			ubicacion = "";
			this.selectedItems1.clear();
			this.accion = "";
			this.eventRefrescar();
		}
	}

	public List<SelectionBean> getSelection() {
		return selection;
	}

	public void setSelection(List<SelectionBean> selection) {
		this.selection = selection;
	}

	public void setserviciosEntranteMesa(
			IServiciosEntranteMesa serviciosEntranteMesa) {
		this.serviciosEntranteMesa = serviciosEntranteMesa;
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

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String date1 = sdf.format(this.getDate1());
		String date2 = sdf.format(this.getDate2());
		// se puede validar fechas
		if (this.date1.after(this.date2)) {
			this.error = "Fecha 1 debe ser menor que la fecha 2";
			this.ver = true;

		} else {
			System.out.println("Filtrando..");
			// Falta Programar por Eli Diaz
			// this.entrantesmesa =
			// entranteMesaDAO.filtrosSP(date1,date2,this.getItem2Seleccionado(),
			// ncodarea);
			this.entrantesmesa = entranteMesaDAO.filtrosSP(date1, date2, this
					.getItem2Seleccionado(), ncodarea);

		}
		// System.out.println(this.entrantes.size());
		// System.out.println(date1 + "  " + date2 + "  "
		// + this.getItem2Seleccionado());
	}

	public void eventRefrescar(ActionEvent evt) {
		// this.seguimiento = seguimientodocumentoDAO.seguimientoSP();
		String area = this.area;
		this.entrantesmesa = entranteMesaDAO.entrantesSP(area);
		this.detalle = "";
		this.setSortColumnName("nano");
		this.setAscending(false);

	}

	// A CF 16-06-2011

	public void eventRefrescar() {
		String area = this.area;
		selectedItems1.clear();
		this.entrantesmesa = entranteMesaDAO.entrantesSP(area);
		this.setSortColumnName("nano");
		this.setAscending(false);
	}

	public void eventRefrescarCombo(ActionEvent evt) {

		List<RemitenteBean> remitentes = entranteMesaDAO.remitentes();
		List itemsRemitentes = Utils.getRemitentes(remitentes);
		this.items1 = itemsRemitentes;

	}

	/**
	 * ***********************************************
	 */
	private boolean disBotonGrabar = false;

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
			if (this.empresa.trim().length() > 0) {
				/*
				 * for (EntranteMesaBean p : selectedEntrantesMesa) {
				 * this.empresa = p.getEmpresa(); }
				 */
				String out = entranteMesaDAO.actualizaCombos(this.empresa,
						String.valueOf(usuario.getCodarea()), usuario
								.getLogin());
				out = out.trim();
				System.out.println("OUT STORED!!!:" + out);
				if (out.equals("0")) {
					this.editarEmpresa = true;

					List<RemitenteBean> remitentes = entranteMesaDAO
							.remitentes();
					// List itemsRemitentes = Utils.getRemitentes(remitentes);
					// this.items1 = itemsRemitentes;
					for (RemitenteBean p : remitentes) {
						if (p.getDescripcion().equals(this.empresa)) {
							this.vremitenteN = String.valueOf(p.getCodigo());
							this.personaSeleccionada = this.empresa;
							break;
						}
					}
					// act el otro combo

					area = this.vremitenteN;
					area_remite = Integer.parseInt(area);
					if (area_remite == 100 || area_remite == 601) {
					} else if (area_remite > 600) {
						List<RepresentanteBean> remite = entranteMesaDAO
								.representante(area_remite);
						List itemsrepresentante = Utils
								.getRepresentante(remite);
						this.items3 = itemsrepresentante;
						// for (RepresentanteBean r: remite);

					} else {
						// System.out.println(area);
						entranteMesaDAO.trabajador(Integer.parseInt(area));
						List<TrabajadorBean> trabajador = entranteMesaDAO
								.trabajador(area_remite);
						List itemsTrabajador = Utils
								.getTrabajador_derivador(trabajador);
						this.items3 = itemsTrabajador;
						// this.items6 = itemsTrabajador;
						// for (TrabajadorBean t: trabajador)
						// System.out.println(t.getArea());
					}
				} else {
					this.error = "Ya existe el Remitente en la Base de Datos, Por favor seleccionar de la lista remitente";
					this.ver = true;
					disBotonGrabar = false;
					// this.verCatalogo = true;
					// formulario = false;
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
			logger.error("MBeanEntrante.eventGrabarCombo][203]", exception);
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
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		String area = String.valueOf(usuario.getCodarea());

		if (this.detalle.equals("")) {
			this.error = "Debe de Ingresar una opcion de busqueda";
			this.ver = true;
		} else {
			this.entrantesmesa = entranteMesaDAO.BusquedaSP(area, opcion,
					detalle);
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
	protected ArrayList<EntranteMesaBean> selectedEntrantesMesa;
	// flat to indicate multiselect row enabled.
	protected String multiRowSelect = "Multiple";
	protected boolean multiple = false;
	protected boolean enhancedMultiple;

	public MBeanEntrantesMesa() {
		selectedEntrantesMesa = new ArrayList<EntranteMesaBean>();
		System.out.println("Contructor MBeanProfiles....");
	}

	public void eventoNuevo(ActionEvent evnt) {
		for (EntranteMesaBean p : selectedEntrantesMesa) {
			System.out.println(p.getArea() + "   " + p.getNano());
		}
	}

	@PostConstruct
	public void posterior() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		// ///guardando en sesion un objeto
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		dirigido = usuario.getCodarea();
		String ncodarea = String.valueOf(usuario.getCodarea());
		// vareaN= usuario.getNomequipo().toUpperCase();
		vrescreN = usuario.getLogin().toUpperCase();
		vresactN = usuario.getLogin().toUpperCase();
		centroN = usuario.getCentro().toUpperCase();
		login = usuario.getLogin();
		area_origenN = usuario.getNomequipo().toUpperCase();
		ncodcentroN = usuario.getNcodcentro();
		vareaN = usuario.getNomequipo();		
		this.entrantesmesa = serviciosEntranteMesa.catalogo(ncodarea);

	
		List<TiposBean> tipos = entranteMesaDAO.tipos();
		List itemstipos = Utils.getTipos(tipos);
		this.items2 = itemstipos;
		for (TiposBean t : tipos);

		
		List<RepresentanteBean> representante = entranteMesaDAO.representante(dato_remitente);
		List itemsRepresentante = Utils.getRepresentante(representante);
		this.items3 = itemsRepresentante;
		// for (RepresentanteBean t: representante);

		List<EstadosBean> estado = entranteMesaDAO.estados();
		List itemsEstado = Utils.getEstado(estado);
		this.items7 = itemsEstado;
		// for (AccionBean a: accion);

		 List<AnioBean> anio = entranteMesaDAO.anio();
	     List itemsAnio = Utils.getAnio(anio);
	     this.items10 = itemsAnio;

		List<AreaBean> area_derivados = entranteMesaDAO.areas();
		List itemsderivados = Utils.getAreas(area_derivados);		
		this.items7a = Utils.getAreasLink(area_derivados);
		
		List<ServidorBean> bean = this.entranteMesaDAO.servidor();
        for (ServidorBean e : bean) {
            ubicacion = e.getDescripcion();
        }
		
		

	}

	/**
	 * SelectionListener bound to the ice:rowSelector component. Called when a
	 * row is selected in the UI.
	 * 
	 * @param event
	 *            from the ice:rowSelector component
	 */
	/**
	 * public List<EntranteMesaBean> getentrantesmesa() {
	 * 
	 * }
	 */
	public List<EntranteMesaBean> getEntrantesmesa() {
		if ("nano".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesa);
		}
		// if ("ncorrelativo".equals(this.getSortColumnName())) {
		// this.sort(this.entrantesmesa);
		// }
		if ("vnumdoc".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesa);
		}
		if ("vdirigido".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesa);
		}
		if ("vasunto".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesa);
		}
		if ("area".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesa);
		}

		if ("dfecregistro".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesa);
		}
		if ("vestado".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesa);
		}
		return this.entrantesmesa;
		// return entrantesmesa;
	}

	public void setEntrantesmesa(List<EntranteMesaBean> entrantesmesa) {
		this.entrantesmesa = entrantesmesa;
	}

	public void rowSelectionListener(RowSelectorEvent event) {
		// clear our list, so that we can build a new one
		selectedEntrantesMesa.clear();

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
		EntranteMesaBean employee;
		for (int i = 0, max = entrantesmesa.size(); i < max; i++) {
			employee = (EntranteMesaBean) entrantesmesa.get(i);
			if (employee.isSelected()) {
				selectedEntrantesMesa.add(employee);
			}
		}
		for (EntranteMesaBean p : selectedEntrantesMesa) {
			// System.out.println("VER EL DETALLE");
			System.out.println("Eli Selecciona los datos");
			System.out.println(p.getFicha_dirigido() + " " + p.getNdirigido()
					+ " " + p.getVubiarchivo() + " " + p.getNano() + ""
					+ p.getArea());
			this.mCarga.setNombrePdf(p.getVubiarchivo());
			this.anollave = p.getNano();
			this.origenllave = p.getNorigen();
			this.tipodocllave = p.getVtipodoc();
			this.correlativollave = p.getNcorrelativo();
			this.numdocllave = p.getVnumdoc();
			//
			this.treeControllerMesa.inicializarArbol(String.valueOf(p
					.getNcorrelativo()), String.valueOf(p.getNano()));
			//
			this.dato_remitente = p.getNdirigido();
			this.ndirigidoA = p.getNdirigido();
			this.dirigido = p.getNdirigido();
			this.vremitenteA = String.valueOf(p.getNdirigido());
			this.representanteA = String.valueOf(p.getNdirigido());
			this.vaccionA = p.getVaccion();
			cont2 = 0;
			this.nremitenteA = p.getNremitente();
			this.ficha_dirigidoA = p.getFicha_dirigido();
			this.ficha_derivadoA = p.getFicha_derivado();
			ruta = p.getVubiarchivo();
			ind_adjuntar = p.getNindicador();
			this.vubiarchivoA = p.getVubiarchivo();
			//inserta codigo para ver el archivo adjunto 19noviembre2019-EDH
            ls_ubicacion = ubicacion + p.getVubiarchivo();
            System.out.println("Visualizacion de archivo");
            System.out.println(ls_ubicacion);            
            EntranteMesaBean bean = new EntranteMesaBean();
            bean.setUbicacion_mesa(ls_ubicacion);


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
	 * @param event
	 *            jsf action event.
	 */
	public void changeSelectionMode(ValueChangeEvent event) {

		String newValue = event.getNewValue() != null ? event.getNewValue()
				.toString() : null;
		multiple = false;
		enhancedMultiple = false;
		if ("Single".equals(newValue)) {
			selectedEntrantesMesa.clear();

			// build the new selected list
			EntranteMesaBean employee;
			for (int i = 0, max = entrantesmesa.size(); i < max; i++) {
				employee = (EntranteMesaBean) entrantesmesa.get(i);
				employee.setSelected(false);
			}
		} else if ("Multiple".equals(newValue)) {
			multiple = false;
		} else if ("Enhanced Multiple".equals(newValue)) {
			enhancedMultiple = false;
		}
	}

	public void eventCancelar(ActionEvent event) {

		// *para la validacion */
		/*
		 * if (this.items7b.size() == 0) { this.error =
		 * "Debe de seleccionar un dirigido"; this.ver = true; } else {
		 */
		/* Eli Agrega este codigo */
		this.disBoton = false;
		this.disBotonRemitente = true;
		this.disBotonGrabar = false;
		this.verCatalogo = true;
		this.verNuevo = false;
		this.verActualizar = false;
		selectedItems1.clear();
		// ***** editar ...
		this.editarEmpresa = true;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("file", null);
		this.selectedItems1.clear();
		this.accion = "";

	}

	public void eventCancelarNuevo(ActionEvent event) {

		this.disBotonGrabar = false;
		this.verCatalogo = true;
		this.verNuevo = false;
		this.verActualizar = false;
		selectedItems1.clear();
		// ***** editar ...
		this.editarEmpresa = true;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("file", null);
		this.selectedItems1.clear();
		this.accion = "";

	}

	public void limpiarNuevo() {
		this.nanoN = 0;
		this.origenN = "";
		this.vtipodocN = "ATD0001";
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
		this.vprioridadN = "";
		// this.vubiarchivoN = "";
		this.dfecplazoN = new Date();
		this.ndiasplazoN = 1;
		this.vestadoN = "";
		// this.nindicadorN = 0;
		this.dfeccreN = new Date();
		this.dfecactN = new Date();
		this.areaseleccionadaN = "";
		area_dirigido = "";
		// this.vrescreN = "";
		// this.vresactN = "";
		// this.ncodcentroN = 0;
		// this.ncodareaN = 0;
		this.ficha_dirigidoN = 0;
		this.items7b.clear();
		this.selectedItems1.clear();
		this.accion = "";
		List<AreaBean> area_derivados = entranteMesaDAO.areas();
		List itemsderivados = Utils.getAreas(area_derivados);
		this.items7a = Utils.getAreasLink(area_derivados);
		inicio = "0";
		fin = "0";
		this.empresa = "";
		List<RepresentanteBean> representante = entranteMesaDAO
				.representante(601);
		List itemsRepresentante = Utils.getRepresentante(representante);
		this.items3 = itemsRepresentante;
		this.ficha_dirigidoN = 1;
		this.nindicadorN = 0;
		this.nindicadorA = 0;
		this.textoA = "El Registro tiene " + this.nindicadorN
				+ " Archivo Adjunto";
		this.vprioridadN = "MEDIA";
		this.personaSeleccionada = "";
		this.opcion_seguimiento="1";
		this.doc_entrada="";
	    this.anodocumentoentr="0";
	    this.nfolioN=0;
	    this.vtipoorigenN="PRESENCIAL";

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
		// this.vrescreA = "";
		// this.vresactA = "";
		// this.ncodcentroA = 0;
		// this.ncodareaA = 0;
		// this.ficha_dirigidoA = 0;
	}

	public String validarFormulario() {
		String mensaje = "Falta ingresar: ";
		boolean ok = true;
		if (this.origenN.equals("0")) {
			ok = false;
			mensaje += "Origen";
		}
		if (this.vtipodocN.equals("ATD0001")) {
			if (ok == false) {
				mensaje += ",Tipo de Documento";
			} else {
				mensaje += "Tipo de Documento";
			}
			ok = false;
		}
		
		
		if (this.vremitenteN.equals("")) {
			if (ok == false) {
				mensaje += ",Remitente";
			} else {
				mensaje += "Remitente";
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
		
		if (this.nfolioN <= 0) {
			if (ok == false) {
				mensaje += ",Numero Folio";
			} else {
				mensaje += "Numero Folio";
			}
			ok = false;
		}
		
		
		
		
		if (this.items7b.size() == 0) {
			if (ok == false) {
				mensaje += ",Dirigido";
			} else {
				mensaje += "Dirigido";
			}
			ok = false;
		}
		
		if (this.items3.size() == 0) {
			if (ok == false) {
				mensaje += ",Remitido Por";
			} else {
				mensaje += "Remitido Por";
			}
			ok = false;
		}
		
		
		if (this.vremitenteN.equals("601")) {
			if (ok == false) {
				mensaje += ",Remitente";
			} else {
				mensaje += "Remitente";
			}
			ok = false;
		}
		if(this.vasuntoN.trim().length()==0)
		 {
			if(ok==false)
				mensaje+=",Asunto";
			else
				mensaje+="Asunto";
			ok = false;
		 }
		
		if (this.opcion_seguimiento.equals("2")) {
			
			if (this.doc_entrada.equals("") || this.doc_entrada.equals("0")) {
                ok = false;
                mensaje += " ,Numero del Documento Entrada ";
            }
            System.out.println(this.anodocumentoentrN);
            if (this.anodocumentoentrN.equals("0")) {
                ok = false;
                mensaje += " ,Año del Documento Entrada ";
            }
			
			
		}
		

		if (ok) {
			mensaje = null;
		}
		return mensaje;
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
		
		
		
		if (this.items7b.size() == 0) {
			if (ok == false) {
				msg += ",Dirigido";
			} else {
				msg += "Dirigido";
			}
			ok = false;
		}
		if (this.vremitenteA.equals("601")) {
			if (ok == false) {
				msg += ",Remitente";
			} else {
				msg += "Remitente";
			}
			ok = false;
		}
		// if(this.vasuntoN.trim().length()==0)
		// {
		// if(ok==false)
		// mensaje+=",Asunto";
		// else
		// mensaje+="Asunto";
		// ok = false;
		// }

		if (ok) {
			msg = null;
		}
		return msg;
	}

	public ArrayList getselectedEntrantesMesa() {
		return selectedEntrantesMesa;
	}

	public void setselectedEntrantesMesa(
			ArrayList<EntranteMesaBean> selectedEntrantesMesa) {
		this.selectedEntrantesMesa = selectedEntrantesMesa;
	}

	public String getMultiRowSelect() {
		return multiRowSelect;
	}

	/**
	 * Sets the selection more of the rowSelector.
	 * 
	 * @param multiRowSelect
	 *            true indicates multi-row select and false indicates single row
	 *            selection mode.
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

	public void setEntranteMesaDAO(EntranteMesaDAO entranteMesaDAO) {
		this.entranteMesaDAO = entranteMesaDAO;
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

	public long getNcorrelativoN() {
		return ncorrelativoN;
	}

	public void setNcorrelativoN(long ncorrelativoN) {
		this.ncorrelativoN = ncorrelativoN;
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

	public long getNcorrelativoA() {
		return ncorrelativoA;
	}

	public void setNcorrelativoA(long ncorrelativoA) {
		this.ncorrelativoA = ncorrelativoA;
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

	public String getAreadeseleccionadaN() {
		return areadeseleccionadaN;
	}

	public void setAreadeseleccionadaN(String areadeseleccionadaN) {
		this.areadeseleccionadaN = areadeseleccionadaN;
	}

	public String getAreaseleccionadaA() {
		return areaseleccionadaA;
	}

	public void setAreaseleccionadaA(String areaseleccionadaA) {
		this.areaseleccionadaA = areaseleccionadaA;
	}

	public ArrayList<EntranteMesaBean> getSelectedEntrantesMesa() {
		return selectedEntrantesMesa;
	}

	public void setSelectedEntrantesMesa(
			ArrayList<EntranteMesaBean> selectedEntrantesMesa) {
		this.selectedEntrantesMesa = selectedEntrantesMesa;
	}

	public String getRepresentanteA() {
		return representanteA;
	}

	public void setRepresentanteA(String representanteA) {
		this.representanteA = representanteA;
	}

	public int getDato_remitente() {
		return dato_remitente;
	}

	public void setDato_remitente(int datoRemitente) {
		dato_remitente = datoRemitente;
	}

	public int getNaccionN() {
		return naccionN;
	}

	public void setNaccionN(int naccionN) {
		this.naccionN = naccionN;
	}

	public int getNaccionA() {
		return naccionA;
	}

	public void setNaccionA(int naccionA) {
		this.naccionA = naccionA;
	}

	public String getVaccionN() {
		return vaccionN;
	}

	public void setVaccionN(String vaccionN) {
		this.vaccionN = vaccionN;
	}

	public String getVaccion_grupoaN() {
		return vaccion_grupoaN;
	}

	public void setVaccion_grupoaN(String vaccionGrupoaN) {
		vaccion_grupoaN = vaccionGrupoaN;
	}

	public String getVaccion_grupobN() {
		return vaccion_grupobN;
	}

	public void setVaccion_grupobN(String vaccionGrupobN) {
		vaccion_grupobN = vaccionGrupobN;
	}

	public String getVaccion_grupoaA() {
		return vaccion_grupoaA;
	}

	public void setVaccion_grupoaA(String vaccionGrupoaA) {
		vaccion_grupoaA = vaccionGrupoaA;
	}

	public String getVaccion_grupobA() {
		return vaccion_grupobA;
	}

	public void setVaccion_grupobA(String vaccionGrupobA) {
		vaccion_grupobA = vaccionGrupobA;
	}

	public String getVotrosN() {
		return votrosN;
	}

	public void setVotrosN(String votrosN) {
		this.votrosN = votrosN;
	}

	public String getVotrosA() {
		return votrosA;
	}

	public void setVotrosA(String votrosA) {
		this.votrosA = votrosA;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public List<String> getSelectedItems1() {
		return selectedItems1;
	}

	public void setSelectedItems1(List<String> selectedItems1) {
		this.selectedItems1 = selectedItems1;
	}

	public int getDiasN() {
		return diasN;
	}

	public void setDiasN(int diasN) {
		this.diasN = diasN;
	}

	public int getDiasA() {
		return diasA;
	}

	public void setDiasA(int diasA) {
		this.diasA = diasA;
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

	public String getNumdocllave() {
		return numdocllave;
	}

	public void setNumdocllave(String numdocllave) {
		this.numdocllave = numdocllave;
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

	public long getCorrelativollave() {
		return correlativollave;
	}

	public void setCorrelativollave(long correlativollave) {
		this.correlativollave = correlativollave;
	}

	public LinkedHashMap<String, Object> getObservaciones1() {
		return observaciones1;
	}

	public void setObservaciones1(LinkedHashMap<String, Object> observaciones1) {
		this.observaciones1 = observaciones1;
	}

	public void setmCarga(MCarga mCarga) {
		this.mCarga = mCarga;
	}

	public TreeControllerMesa getTreeControllerMesa() {
		return treeControllerMesa;
	}

	public void setTreeControllerMesa(TreeControllerMesa treeControllerMesa) {
		this.treeControllerMesa = treeControllerMesa;
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

	public String getTextoA() {
		return textoA;
	}

	public void setTextoA(String textoA) {
		this.textoA = textoA;
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
	
	public RecursoReporteNuevo getRecursoReporteNuevo() {
		
		// if (this.corrinicial.trim().length()>1 &&
				// this.corrfinal.trim().length()>1){
				// if(this.vnumdocN.trim().length()==0)
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
						.getExternalContext().getSession(false);
				// ///guardando en sesion un objeto
				Usuario usuario = null;
				usuario = (Usuario) session.getAttribute("sUsuario");
				int dirigido = usuario.getCodarea();
				String area_origen = String.valueOf(dirigido);
				String annio = Integer.toString(c.get(Calendar.YEAR));
				ec = FacesContext.getCurrentInstance().getExternalContext();
				parametros.put("P_AREA", area_origen);
				parametros.put("P_INICIO", this.corrinicial);
				parametros.put("P_FIN", this.corrfinal);
				parametros.put("P_ANO", annio);
				// parametros.put("P_ESTADO", estado);
				reporteNuevo.asignar("reporteMesa.pdf", ec, parametros,
						"reportes/mesalinea.jasper");// se le puede adicionar parametros...
				recursoReporteNuevo = reporteNuevo;
				return recursoReporteNuevo;
				/*
				 * //} else //{ this.error="Ingrese datos de inicio y fin";
				 * this.ver=true; return null; }
				 */
		
	}
	// para impresion
	public RecursoReport getRecursoReport() {

		// if (this.corrinicial.trim().length()>1 &&
		// this.corrfinal.trim().length()>1){
		// if(this.vnumdocN.trim().length()==0)
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		// ///guardando en sesion un objeto
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		int dirigido = usuario.getCodarea();
		String area_origen = String.valueOf(dirigido);
		String annio = Integer.toString(c.get(Calendar.YEAR));
		ec = FacesContext.getCurrentInstance().getExternalContext();
		parametros.put("P_AREA", area_origen);
		parametros.put("P_INICIO", this.corrinicial);
		parametros.put("P_FIN", this.corrfinal);
		parametros.put("P_ANO", annio);
		// parametros.put("P_ESTADO", estado);
		reporte.asignar("reporteMesa.pdf", ec, parametros,
				"reportes/mesa.jasper");// se le puede adicionar parametros...
		recursoReport = reporte;
		return recursoReport;
		/*
		 * //} else //{ this.error="Ingrese datos de inicio y fin";
		 * this.ver=true; return null; }
		 */

	}

	// para impresion 2
	public RecursoReporte getRecursoReporte() {
		HttpSession session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ec2 = FacesContext.getCurrentInstance().getExternalContext();
		EntranteMesaBean p = null;
		for (EntranteMesaBean q : this.entrantesmesa) {
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

	

	public void setRecursoReportes(RecursoReporte recursoReporte) {
		this.recursoReporte = recursoReporte;
	}

	public ExternalContext getEc() {
		return ec;
	}

	public void setEc(ExternalContext ec) {
		this.ec = ec;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public boolean isDisBotonGrabar() {
		return disBotonGrabar;
	}

	public void setDisBotonGrabar(boolean disBotonGrabar) {
		this.disBotonGrabar = disBotonGrabar;
	}

	public String getCorrinicial() {
		return corrinicial;
	}

	public void setCorrinicial(String corrinicial) {
		this.corrinicial = corrinicial;
	}

	public String getCorrfinal() {
		return corrfinal;
	}

	public void setCorrfinal(String corrfinal) {
		this.corrfinal = corrfinal;
	}

	public boolean isVerAlertaConfirmacionBorrar() {
		return verAlertaConfirmacionBorrar;
	}

	public void setVerAlertaConfirmacionBorrar(
			boolean verAlertaConfirmacionBorrar) {
		this.verAlertaConfirmacionBorrar = verAlertaConfirmacionBorrar;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public boolean isVerAutocompletar() {
		return verAutocompletar;
	}

	public void setVerAutocompletar(boolean verAutocompletar) {
		this.verAutocompletar = verAutocompletar;
	}

	public boolean isVerRemitente() {
		return verRemitente;
	}

	public void setVerRemitente(boolean verRemitente) {
		this.verRemitente = verRemitente;
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

	public boolean isVerdocs() {
		return verdocs;
	}

	public void setVerdocs(boolean verdocs) {
		this.verdocs = verdocs;
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

	public String getAnodocumentoentr() {
		return anodocumentoentr;
	}

	public void setAnodocumentoentr(String anodocumentoentr) {
		this.anodocumentoentr = anodocumentoentr;
	}

	public String getOpcion_seguimiento() {
		return opcion_seguimiento;
	}

	public void setOpcion_seguimiento(String opcion_seguimiento) {
		this.opcion_seguimiento = opcion_seguimiento;
	}

	public String getDoc_entrada() {
		return doc_entrada;
	}

	public void setDoc_entrada(String doc_entrada) {
		this.doc_entrada = doc_entrada;
	}

	public List getItems10() {
		return items10;
	}

	public void setItems10(List items10) {
		this.items10 = items10;
	}

	public String getAnodocumentoentrN() {
		return anodocumentoentrN;
	}

	public void setAnodocumentoentrN(String anodocumentoentrN) {
		this.anodocumentoentrN = anodocumentoentrN;
	}

	public String getAnodocumentoentrA() {
		return anodocumentoentrA;
	}

	public void setAnodocumentoentrA(String anodocumentoentrA) {
		this.anodocumentoentrA = anodocumentoentrA;
	}

	public String getLs_ubicacion() {
		return ls_ubicacion;
	}

	public void setLs_ubicacion(String ls_ubicacion) {
		this.ls_ubicacion = ls_ubicacion;
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

	public RecursoReporteNuevo getReporteNuevo() {
		return reporteNuevo;
	}

	public void setReporteNuevo(RecursoReporteNuevo reporteNuevo) {
		this.reporteNuevo = reporteNuevo;
	}

	

	public void setRecursoReporteNuevo(RecursoReporteNuevo recursoReporteNuevo) {
		this.recursoReporteNuevo = recursoReporteNuevo;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public Map<String, Object> getParametros2() {
		return parametros2;
	}

	public void setParametros2(Map<String, Object> parametros2) {
		this.parametros2 = parametros2;
	}

	public ExternalContext getEc2() {
		return ec2;
	}

	public void setEc2(ExternalContext ec2) {
		this.ec2 = ec2;
	}

	public Map<String, Object> getParametros3() {
		return parametros3;
	}

	public void setParametros3(Map<String, Object> parametros3) {
		this.parametros3 = parametros3;
	}

	public ExternalContext getEc3() {
		return ec3;
	}

	public void setEc3(ExternalContext ec3) {
		this.ec3 = ec3;
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

	
	
	
	
	
	
	

}
