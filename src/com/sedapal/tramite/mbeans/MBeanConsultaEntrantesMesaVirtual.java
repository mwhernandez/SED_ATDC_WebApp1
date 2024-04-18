package com.sedapal.tramite.mbeans;

import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

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
import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.icefaces.tutorial.facelets.NavigationBean;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.EntranteMesaVirtualBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.ParametroCorreoMesaVirtualBean;
import com.sedapal.tramite.beans.PlantillaCorreoMesaVirtualBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.EntranteMesaDAO;
import com.sedapal.tramite.dao.EntranteMesaVirtualDAO;
import com.sedapal.tramite.dao.SecuencialDAO;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.nova.util.RecursoReporte;
import com.sedapal.tramite.nova.util.RecursoReporteNuevo;
import com.sedapal.tramite.servicios.IServiciosEntranteMesa;
import com.sedapal.tramite.servicios.IServiciosEntranteMesaVirtual;
import com.sedapal.tramite.servicios.IServiciosSelection;
import com.sedapal.tramite.servicios.IServiciosUsuarios;
import com.sedapal.tramite.servicios.util.MailService;
import com.sedapal.tramite.tree.TreeControllerMesa;
import com.sedapal.tramite.util.Utils;










import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;








/* office 365 */
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
/* office 365 */

public class MBeanConsultaEntrantesMesaVirtual extends BaseSortableList implements IMBeanEntrantesMesaVirtual, Serializable {


	private boolean verAlertaConfirmacionBorrar;
	private String msg;
	private boolean editarEmpresa = true;
	private MCarga mCarga;
	private NavigationBean navigation;	
	private List<EntranteMesaVirtualBean> entrantesmesavirtual;
	private EntranteMesaVirtualDAO entranteMesaVirtualDAO;;
	public IServiciosEntranteMesaVirtual serviciosEntranteMesaVirtual;	
	private SecuencialDAO secuencialDAO;
	private List<SelectionBean> selection;
	// para el reporte	
	private RecursoReporteNuevo reporteNuevo;
	private RecursoReporteNuevo recursoReporteNuevo;
	private RecursoReporte reportes; // inyectado    
    private RecursoReporte recursoReporte;// para displayar
	
	private ExternalContext ec2 = null;
	private Map<String, Object> parametros2 = new HashMap<String, Object>();
	private Map<String, Object> parametros3 = new HashMap<String, Object>();
    private ExternalContext ec3 = null;
    
	Calendar c = Calendar.getInstance();
	
	private Date fechaInicial = new Date();
    private Date fechaFinal = new Date();
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
	private List items11;
	private List items12;
	private List items13;
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
	private int ncodmesapartes;
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
	/*Fecha recepci�n para correo*/
	private Date dfecdocCorreo;
	/*Remitente para correo*/
	private String vremitenteCorreo;
	/*Subtitulo correo*/
	private String vsubtituloCorreo;
	/*�rea dirigido correo*/
	private String vareadirigidoCorreo;
	/*Plantilla correo*/
	private String plantillaCorreo;
	/*Host correo*/
	private String vhostcorreo;
	/*Correo 365*/
	private String vcorreo365;
	/*Password 365*/
	private String vpassword365;
	private String correlativo;
	/*Dirigido correo*/
	private String dirigidoCorreo;
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
	private String correoA;
	private int ntelefonoA;
	private String direccionA;
	private String vtipoingresoA;
	private String vmotivoingresoA;
	//private String vnumerodnirucA;
	private String vreferenciadireccionA;
	/////
	private boolean ver = false;
	private boolean verNuevo = false;
	private boolean verCatalogo = true;
	private boolean disPrint = true;
	private boolean verActualizar = false;
	private boolean cerrarVentanta = false;
	private boolean activo = true;
	
	private boolean verPDF = false;
    private String rutaPDFPopup;
    private String correo = "Correo :";
    private String Direccion = "Direccion :";
    private String telefono = "Telefono :";
	
	
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
   
    

	

	// Autocompletar ------Inserta Eli Diaz 26/03/2012
	

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
			// Se a�ade a la propiedad posiblepersonas lo que devuelve
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
				List<RemitenteBean> remitente = entranteMesaVirtualDAO
						.remitentesA(area_remite);
				List itemsAreas = Utils.getRemitentes(remitente);
				this.items1 = itemsAreas;

				List<RepresentanteBean> remite = entranteMesaVirtualDAO
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
			ListaPersona = entranteMesaVirtualDAO.persona(cadenaDigitada);
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
			ListaPersona = entranteMesaVirtualDAO.personaExterna(cadenaDigitada);
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
			EntranteMesaVirtualBean p = null;
			for (EntranteMesaVirtualBean q : this.entrantesmesavirtual) {
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
				origen = String.valueOf(p.getNorigen());
				tipodoc = p.getVtipodoc();
				ncodarea = String.valueOf(p.getNcodarea());
				codigo = String.valueOf(p.getNcorrelativo());

				
				//serviciosEntranteMesaVirtual.eliminarEntrantemesavirtual(anno, origen,	codigo, login);

				this.error = "Registro Eliminado!";

				//selectedEntrantesMesaVirtual.clear();
				this.entrantesmesavirtual = serviciosEntranteMesaVirtual.catalogo(ncodarea);// actualiza
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
	
	/*
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
	
	*/
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
		EntranteMesaVirtualBean bean = null;
		for (EntranteMesaVirtualBean p : this.entrantesmesavirtual) {
			if (p.isSelected()) {
				bean = p;
			}
		}
		if (event.getNewTabIndex() == 1 || event.getNewTabIndex() == 2
				&& bean != null) {
			// clear our list, so that we can build a new one
			selectedEntrantesMesaVirtual.clear();

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
			EntranteMesaVirtualBean employee;
			for (int i = 0, max = entrantesmesavirtual.size(); i < max; i++) {
				employee = (EntranteMesaVirtualBean) entrantesmesavirtual.get(i);
				if (employee.isSelected()) {
					selectedEntrantesMesaVirtual.add(employee);
				}
			}
			for (EntranteMesaVirtualBean p : selectedEntrantesMesaVirtual) {
				// System.out.println("VER EL DETALLE");
				System.out.println(p.getFicha_dirigido() + " "
						+ p.getNdirigido() + " " + p.getVubiarchivo());
				this.mCarga.setNombrePdf(p.getVubiarchivo());
				this.anollave = p.getNano();
				this.origenllave = p.getNorigen();
				this.tipodocllave = p.getVtipodoc();
				this.correlativollave = p.getNcorrelativo();
				this.numdocllave = p.getVnumdoc();
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
		EntranteMesaVirtualBean p = null;
		for (EntranteMesaVirtualBean q : this.entrantesmesavirtual) {
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
			this.dfecdocCorreo = p.getDfecregistro();
			this.vremitenteCorreo = p.getVdirigido();
			this.vsubtituloCorreo = p.getArea();
			this.vareadirigidoCorreo = p.getArea_dirigido_correo();
			this.dfecregistroA = p.getDfecregistro();
			this.vasuntoA = p.getVasunto();
			//this.vremitenteA = p.getVdirigido();
			this.vremitenteA = String.valueOf(p.getNdirigido());
			this.ficha_dirigidoA = p.getNdirigido();
			this.vreferenciaA = p.getVreferencia();
			this.vobservacionA = p.getVobservacion();
			// this.vaccionA = p.getVaccion();
			this.vprioridadA = p.getVprioridad();
			this.vubiarchivoA = p.getVubiarchivo();
			this.dfecplazoA = p.getDfecdoc();
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
			this.direccionA = p.getDireccion();
			this.correoA = p.getCorreo();
			this.ntelefonoA = p.getNtelefono();
			this.nfolioA = p.getNfolio();
			this.vtipoingresoA = p.getVtipoingreso();
			this.vmotivoingresoA = p.getVmotivoingreso();
			this.vreferenciadireccionA = p.getVreferenciadireccion();			
			
			this.ndiasplazoA = p.getNdiasplazo();
			dato_remitente = this.ndirigidoA;
			this.textoA = "El Registro tiene " + this.nindicadorA
					+ " Archivo Adjunto";
			// /
			List<RepresentanteBean> representante = entranteMesaVirtualDAO.representante(dato_remitente);
			List itemsRepresentante = Utils.getRepresentante(representante);
			this.items3 = itemsRepresentante;
			
			this.anollave = this.nanoA;
			this.origenllave = this.norigenA;
			this.tipodocllave = this.vtipodocA;
			this.correlativollave = this.ncorrelativoA;
			
			

			List<AreaBean> areas = entranteMesaVirtualDAO.areas_seleccionadas(
					this.anollave, this.origenllave, this.tipodocllave,
					this.correlativollave);

			
			this.items7b = Utils.getAreasLink(areas);
			for (AreaBean q : areas) {
				this.items7a.remove(q.getNombre());
			}
			this.areas_actuales = this.items7b.size();
			System.out.println("Num Areas Actuales:" + areas_actuales);
			
						
			

			List<RemitenteBean> remitentes = entranteMesaVirtualDAO.remitentesA(this.ndirigidoA);
			List itemsderivados = Utils.getRemitentes(remitentes);
			this.items1 = itemsderivados;

			List<RepresentanteBean> representantes = entranteMesaVirtualDAO.representante(this.ndirigidoA);
			List itemsrepresentante = Utils.getRepresentante(representantes);
			this.items3 = itemsrepresentante;
			
			List<AreaBean> area_derivados = entranteMesaVirtualDAO.areas();
			List itemsderivadoss = Utils.getAreas(area_derivados);
			this.items7a = Utils.getAreasLink(area_derivados);

			// }

			//selectedEntrantesMesaVirtual.clear();
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
			this.entrantesmesavirtual = serviciosEntranteMesaVirtual.catalogo(ncodarea);// actualiza
			// el
			// reporte

		} else {
			this.error = "Debe seleccionar solo un registro para modificar";
			this.ver = true;
		}
	}

	public void eventOpenpdf(ActionEvent evt) throws URISyntaxException {

		if (this.selectedEntrantesMesaVirtual.size() < 1) {
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
			
		this.entrantesmesavirtual.clear();		
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
		//session.setAttribute("tipodoc", vtipodocN);
		session.setAttribute("indicador", 5);
		ind_adjuntar = 1;
		this.verDetalles = true;

		selectedEntrantesMesaVirtual.clear();
		// this.productos = serviciosProducto.catalogo();// actualiza el
		// reporte

	}
	
	
    /* Evento visualizar PDF*/
    public void evenVerPDF(ActionEvent evt) {
    	EntranteMesaVirtualBean p = null;
		for (EntranteMesaVirtualBean q : this.entrantesmesavirtual) {
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

    public void cancelarDocs(ActionEvent actionEvent) {
		this.verPDF = false;
        this.verdocs = false;
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
		session.setAttribute("indicador", 5);
		ind_adjuntar = 1;
		this.verDetalles = true;
		for (EntranteMesaVirtualBean d : selectedEntrantesMesaVirtual) {
		}
		selectedEntrantesMesaVirtual.clear();
		// this.productos = serviciosProducto.catalogo();// actualiza el
		// reporte

	}

	
	
	public void setEntrantesmesavirtual(List<EntranteMesaVirtualBean> entrantesmesavirtual) {
		this.entrantesmesavirtual = entrantesmesavirtual;
	}
	
	/// para mover un archivo pdf///
	/*
	public void moverArchivo(String virtual, String mesa){
	    Path origenPath = FileSystems.getDefault().getPath(virtual);
	    Path destinoPath = FileSystems.getDefault().getPath(mesa);


	    try {
	        Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException e) {
	        System.err.println(e);
	    }
	}
	*/
	//////////


	public void eventEntrada(ActionEvent evt) {
		this.verCatalogoEntrada = true;
		// for (EntranteBean p : selectedEntrantesMesa) {
		// }
	}
	/*
	public void moverArchivo(String origen, String destino){
	    Path origenPath = FileSystems.getDefault().getPath(origen);
	    Path destinoPath = FileSystems.getDefault().getPath(destino);


	    try {
	        Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException e) {
	        System.err.println(e);
	    }
	}
	*/
	/*
	public boolean copyFile(String fromFile, String toFile) {
        File origin = new File(fromFile);
        File destination = new File(toFile);
        if (origin.exists()) {
            try {
                InputStream in = new FileInputStream(origin);
                OutputStream out = new FileOutputStream(destination);
                // We use a buffer for the copy (Usamos un buffer para la copia).
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
    */
    
	
	
	
	
	

	public void eventActualizarEntrante(ActionEvent event) {
		HttpSession session = null;
		boolean formulario = true;
		try {
			// llama DAO/Stored Para actualizar producto
			this.verActualizar = true;
			EntranteMesaVirtualBean entrantemesavirtualBean = new EntranteMesaVirtualBean();
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
			this.dfecdocCorreo = new Date();
			
			if (valida == null) {
				// controlando el adjuntar
				/*
				if (ind_adjuntar == 0) {
					this.vubiarchivoA = "NADA";
				} else {
					file = (String) session.getAttribute("file");
					if (file != null) {
						nombre_archivo = file;
						List<ServidorBean> bean = this.entranteMesaVirtualDAO.servidor();
						for (ServidorBean e : bean) {
							ubicacion = e.getDescripcion();
						}
						Date date = new Date();
						String annio = Integer.toString(c.get(Calendar.YEAR));
						nombre_archivo = annio + nombre_archivo;
						this.vubiarchivoA = nombre_archivo;
					}
				}
				*/
				
				if (ind_adjuntar == 0) {
					this.vubiarchivoA = "NADA";
				} else {
					//InputFile inputFile = (InputFile) event.getSource();
					//FileInfo fileInfo = inputFile.getFileInfo();
			        // System.out.println(fileInfo.getPhysicalPath()+"\\"+fileInfo.getFileName());
			        //System.out.println("SIZE FILE!!!:" + fileInfo.getSize());
			        //String nombreFile = fileInfo.getFileName();
					// ELI CAMBIA DE LOGICA YA QUE LOS SERVIDORES ESTAN MONTADOS 10/07/2020/
					
			        ResourceBundle bundle = ResourceBundle.getBundle("com.sedapal.tramite.files.parametros");	
			        String rutaorigen = bundle.getString("path.virtual");
					String rutadestino =  bundle.getString("path.mesa");
					
					String rutaorigenfinal  = rutaorigen + "/" +this.vubiarchivoA;
					String rutadestinofinal = rutadestino + "/" + this.vubiarchivoA;
					
					/*
					String servidororigen="";
					String servidordestino="";
					List<ServidorBean> bean = entranteMesaVirtualDAO.servidororigen();
                    for (ServidorBean e : bean) {
                    	servidororigen = e.getDescripcion();
                    }
					
					List<ServidorBean> bean1 = entranteMesaVirtualDAO.servidordestino();
                    for (ServidorBean e : bean1) {
                    	servidordestino = e.getDescripcion();
                    }
			        
					String rutaorigenfinal  = servidororigen + this.vubiarchivoA;
					String rutadestinofinal = servidordestino + this.vubiarchivoA;
					
					System.out.println("================");
					System.out.println("QUIERO VER LA RUTA ORIGEN Y DESTINO");
					System.out.println("origen = " + rutaorigenfinal);
					System.out.println("destino = " + rutadestinofinal);
					System.out.println("================");
					*/
					FileCopy filecopy = new FileCopy(rutaorigenfinal, rutadestinofinal);
					
					
					//moverArchivo(rutaorigenfinal, rutadestinofinal);
					//JavaIOUtils javaIOUtils = new JavaIOUtils();
					/*
					boolean result = copyFile(rutaorigenfinal, rutadestinofinal);
					System.out.println("VIENDO EL RESULTADO FINAL");
					System.out.println(result);
					System.out.println("================");
					*/
					/*
					JavaIOUtils javaIOUtils = new JavaIOUtils();
					 */
			        String fromFile = rutaorigenfinal;
			        String toFile = rutadestinofinal;
			        
			      
			        
			        /* ESTO SI FUNCIONA OK EDH
			        javaIOUtils javaIOUtils = new javaIOUtils();
			        boolean result = javaIOUtils.copyFile(fromFile, toFile);
			        System.out.println(result?
			            "Success! File moved (�xito! Fichero movido)"
			                : "Error! Failed to move the file (Error! No se ha podido mover el fichero)");
			                */
			                
			        javaIOUtils javaIOUtils = new javaIOUtils();
			        boolean result = javaIOUtils.moveFile(fromFile, toFile);
			        System.out.println(result?
			            "Success! File moved (éxito! Fichero movido)"
			                : "Error! Failed to move the file (Error! No se ha podido mover el fichero)"); 
			    
			        
			    
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
				
				String texto1 = this.correo + this.correoA;
				String texto2 = this.telefono + this.ntelefonoA;
				String texto3 = this.Direccion + this.direccionA;
				
				this.vobservacionA= texto1 + "\n" +texto2+ "\n"+ texto3 +"\n" ;
				// le paso como parametro al stored
				entrantemesavirtualBean.setNano(this.nanoA);
				entrantemesavirtualBean.setNorigen(Integer.parseInt(this.origenA));
				entrantemesavirtualBean.setVtipodoc(this.vtipodocA);
				entrantemesavirtualBean.setNcorrelativo(this.ncorrelativoA);
				entrantemesavirtualBean.setVnumdoc(this.vnumdocA);
				entrantemesavirtualBean.setFicha_dirigido(this.ficha_dirigidoA);
				entrantemesavirtualBean.setFicha_derivado(this.ficha_derivadoA);
				entrantemesavirtualBean.setVasunto(this.vasuntoA);
				entrantemesavirtualBean.setVreferencia(this.vreferenciaA);
				entrantemesavirtualBean.setVobservacion(this.vobservacionA);
				entrantemesavirtualBean.setVaccion(this.vaccionA);
				entrantemesavirtualBean.setVprioridad(this.vprioridadA);
				entrantemesavirtualBean.setVresact(this.vresactA);
				entrantemesavirtualBean.setVubiarchivo(this.vubiarchivoA);
				entrantemesavirtualBean.setDfecdoc(this.dfecdocA);
				entrantemesavirtualBean.setNdiasplazo(this.ndiasplazoA);
				entrantemesavirtualBean.setVestado(this.vestadoA);
				entrantemesavirtualBean.setNremitente(Integer.parseInt(this.vremitenteA));
				entrantemesavirtualBean.setVdirigido(this.dirigidoAA);
				entrantemesavirtualBean.setNcodarea(this.ncodareaA);
				entrantemesavirtualBean.setDfecregistro(this.dfecregistroA);
				entrantemesavirtualBean.setNfolio(this.nfolioA);
				entrantemesavirtualBean.setVtipoingreso(this.vtipoingresoA);
				entrantemesavirtualBean.setVmotivoingreso(this.vmotivoingresoA);
				entrantemesavirtualBean.setVreferenciadireccion(this.vreferenciadireccionA);
				
				
				// serviciosEntranteMesa.actualizarEntrantemesa(entrantemesaBean);
				//Map outMap = serviciosEntranteMesaVirtual.actualizarEntrantemesavirtual(entrantemesavirtualBean);
				Map outMap = serviciosEntranteMesaVirtual.actualizarEntrantemesavirtual(entrantemesavirtualBean);
				String out = (String) outMap.get("out");
				correlativo = (String) outMap.get("outcorrelativo");
				// TODO : descomentar el metodo de abajo cuando se implemente
				// una salida al SP
				// String out =
				// serviciosEntranteMesa.nuevoEntrantemesa(entrantemesaBean);
				// out = out.trim();
				// String out = "0"; // codigo temporal, borrar esta linea luego
				System.out.println("OUT STORED!!!:" + out);

				if (out.equals("0")) {

					this.error = "Se actualizaron los campos correctamente";
					this.entrantesmesavirtual = serviciosEntranteMesaVirtual
							.catalogo(ncodarea);// actualiza el reporte
					
					
					String obs = "Correo:ralvi@canvia.com Telefono:0 Direccion:null";
					/*Enviar correo office 365*/
                    this.enviarCorreo(obs, entrantemesavirtualBean);
                    
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
	
	
	// Funci�n enviar correo con office 365
	public void enviarCorreo (String obs , EntranteMesaVirtualBean entrantemesavirtualBean ) throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("com.sedapal.tramite.files.parametros");
		String[] one = obs.split(" ");
	    String[] list = one[0].split(":");
	    String email = list[1];
	    
        
        List<ParametroCorreoMesaVirtualBean> beanCorreo = this.entranteMesaVirtualDAO.parametroCorreo();
        for (ParametroCorreoMesaVirtualBean e : beanCorreo) {
      	      vhostcorreo = e.getVhost();
			  vcorreo365 = e.getVcorreo();
			  vpassword365 = e.getVpassword();
            // parametros
        }
        
	    ExchangeService exchangeService = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		WebCredentials credenciales = new WebCredentials(vcorreo365,vpassword365);
		exchangeService.setUseDefaultCredentials(false);
		exchangeService.setCredentials(credenciales);
		exchangeService.setUrl(new URL(vhostcorreo).toURI());
		EmailMessage emailMessage = new EmailMessage(exchangeService);
		EmailAddress from = new EmailAddress(vcorreo365);
		emailMessage.setFrom(from);
		// ENVIO EN PRODUCCI�N
		EmailAddress to = new EmailAddress(this.correoA);
		//EmailAddress to = new EmailAddress(email);
		EmailAddress copia = new EmailAddress(vcorreo365);
		emailMessage.getToRecipients().add(to);
		emailMessage.getCcRecipients().add(copia);
		String asunto = "Cargo de recepción - Mesa de Partes";
		emailMessage.setSubject(asunto);
		
		  List<PlantillaCorreoMesaVirtualBean> beanPlantilla = this.entranteMesaVirtualDAO.plantillaCorreo();
          for (PlantillaCorreoMesaVirtualBean e : beanPlantilla) {
             plantillaCorreo = e.getNvalorclob();
             System.out.println(plantillaCorreo);
              // plantilla = "CLOB"; modificado en
              // parametros
          }

		String html = plantillaCorreo;
		System.out.println(plantillaCorreo);
		System.out.println(this.vsubtituloCorreo);
		html  = html.replace("{{subtitulo}}", this.vsubtituloCorreo +"");
		//html  = html.replace("{{registro}}", entrantemesavirtualBean.getNcorrelativo()+"");
		html  = html.replace("{{registro}}", correlativo+"");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		html  = html.replace("{{fecha}}", df.format(this.dfecdocCorreo) +"");
		html  = html.replace("{{remitente}}", this.vremitenteCorreo+"");
		html  = html.replace("{{documento}}", entrantemesavirtualBean.getVnumdoc()+"");
		html  = html.replace("{{asunto}}", entrantemesavirtualBean.getVasunto()+"");
		html  = html.replace("{{dirigido}}", this.vareadirigidoCorreo==null?this.dirigidoCorreo:this.vareadirigidoCorreo +"");
		
		
		MessageBody messageBody = new MessageBody(html);
		emailMessage.setBody(messageBody);
		System.out.println(df.format(this.dfecdocCorreo));
		System.out.println(html);
		try {
			emailMessage.send();
			
		} catch (Exception e) {
			System.out.println("error" + e);
			this.error = "No se ha realizado el envio del correo";
			this.ver = true;
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
		///this.nindicadorN = 1;
		this.nindicadorA = 1;
		//this.textoA = "El Registro tiene " + this.nindicadorN Archivo Adjunto";
	}

	public void eventNuevo(ActionEvent event) {
		this.verAutocompletar = true;
		this.verRemitente = false;
		//this.limpiarNuevo();
		// combo.setLabel("ITEMS");
		this.verNuevo = true;
		this.verCatalogo = false;
	}

	private String itemselect[] = new String[1];

	public void pasaDerecha(ActionEvent actionEvent) {
		//itemselect[0] = this.dirigidoN;
		Utils.pasaDerecha(this.items7a, this.items7b, this.itemselect);
	}

	// Eli comenta este codigo 22/06/2016
	private String itemselectA[] = new String[1];

	public void pasaDerechaA(ActionEvent actionEvent) {
		List<AreaBean> listaArea; 
		itemselectA[0] = this.dirigidoA;
		// Dirigido seleccionado correo
		listaArea = entranteMesaVirtualDAO.areas();
		for (int i = 0; i < listaArea.size(); i++) {
			if(listaArea.get(i).getCodigo() == Integer.parseInt(itemselectA[0]) ){
				this.dirigidoCorreo = listaArea.get(i).getNombre();
			}
		}
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
			entranteMesaVirtualDAO.updatedirigido(this.correlativollave, area, this.anollave);

		}

		Utils.pasaIzquierda(items7a, items7b, items7bSelected);
	}

	public void captura(ValueChangeEvent changeEvent) {
		System.out.println("Captura");
		area = (String) changeEvent.getNewValue();
		System.out.println(area);
		int area_remite = Integer.parseInt(area);
		if (area_remite > 600) {
			List<RepresentanteBean> remite = entranteMesaVirtualDAO
					.representante(area_remite);
			List itemsrepresentante = Utils.getRepresentante(remite);
			this.items3 = itemsrepresentante;
			// for (RepresentanteBean r: remite);

		} else {
			System.out.println(area);
			entranteMesaVirtualDAO.trabajador(Integer.parseInt(area));
			List<TrabajadorBean> trabajador = entranteMesaVirtualDAO
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

	

	public void captura_derivador(ValueChangeEvent changeEvent) {
		area_deriva = (String) changeEvent.getNewValue();
		// System.out.println(area_deriva);
		// entranteDAO.trabajador_derivador(Integer.parseInt(area_deriva));
		List<TrabajadorBean> trabajador_derivador = entranteMesaVirtualDAO
				.trabajador_derivador(Integer.parseInt(area_deriva));
		List itemsTrabajador = Utils.getTrabajador(trabajador_derivador);
		this.items6 = itemsTrabajador;
		for (TrabajadorBean t : trabajador_derivador)
			// System.out.println(t.getArea())
			;
	}

	
	public void actualizar(ValueChangeEvent changeEvent) {

		int area = this.getNdirigidoA();
		entranteMesaVirtualDAO.trabajador(area);
		List<TrabajadorBean> trabajador = entranteMesaVirtualDAO.trabajador(area);
		List itemsTrabajador = Utils.getTrabajador(trabajador);
		this.items1 = itemsTrabajador;
		for (TrabajadorBean t : trabajador)
			;
	}

	public void actualizar_ficha(ValueChangeEvent changeEvent) {
		String area_derivada = String.valueOf(this.nremitenteA);
		entranteMesaVirtualDAO.trabajador_derivador(Integer.parseInt(area_derivada));
		List<TrabajadorBean> trabajador_derivador = entranteMesaVirtualDAO
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

			List<AreaBean> area = entranteMesaVirtualDAO.areas();
			List itemsArea = Utils.getAreas(area);
			this.items1 = itemsArea;
			for (AreaBean a : area)
				;

		} else {

			List<RemitenteBean> remitente = entranteMesaVirtualDAO.remitentes();
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
		//areaseleccionadaN = area_dirigido;
		//this.ncodareaN = Integer.parseInt(area_deriva);
		// this.dirigidoN=area_dirigido;

	}

	public void eventDeseleccionarTrabajador(ActionEvent event) {
		// Falta codificar el sacar registros
		// area_dirigido = area_deriva + caracter - area_dirigido;
		// String dato = area_dirigido.split(area_dirigido).toString();
		// System.out.println(dato);
	}

	public void eventRegistrarEntrante(ActionEvent event) {}

	public List<SelectionBean> getSelection() {
		return selection;
	}

	public void setSelection(List<SelectionBean> selection) {
		this.selection = selection;
	}

	

	public void setServiciosselection(IServiciosSelection serviciosselection) {
		this.serviciosselection = serviciosselection;
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
		if (this.date1.after(this.date2) || this.date1.equals(date2) ){
			this.error = "Fecha Inicial debe ser menor que la fecha final";
			this.ver = true;

		} else {
			System.out.println("Filtrando..");
			// Falta Programar por Eli Diaz
			// this.entrantesmesa =
			// entranteMesaDAO.filtrosSP(date1,date2,this.getItem2Seleccionado(),
			// ncodarea);
			this.entrantesmesavirtual = entranteMesaVirtualDAO.filtrosSP(date1, date2, ncodarea);

		}
		// System.out.println(this.entrantes.size());
		// System.out.println(date1 + "  " + date2 + "  "
		// + this.getItem2Seleccionado());
	}

	public void eventRefrescar(ActionEvent evt) {
		// this.seguimiento = seguimientodocumentoDAO.seguimientoSP();
		String area = this.area;
		this.entrantesmesavirtual = entranteMesaVirtualDAO.entrantesSP(area);
		this.detalle = "";
		this.setSortColumnName("nano");
		this.setAscending(false);

	}

	// A CF 16-06-2011

	public void eventRefrescar() {
		String area = this.area;
		selectedItems1.clear();
		this.entrantesmesavirtual = entranteMesaVirtualDAO.entrantesSP(area);
		this.setSortColumnName("nano");
		this.setAscending(false);
	}

	public void eventRefrescarCombo(ActionEvent evt) {

		List<RemitenteBean> remitentes = entranteMesaVirtualDAO.remitentes();
		List itemsRemitentes = Utils.getRemitentes(remitentes);
		this.items1 = itemsRemitentes;

	}

	/**
	 * ***********************************************
	 */
	private boolean disBotonGrabar = false;
	/*
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
				
				String out = entranteMesaVirtualDAO.actualizaCombos(this.empresa,
						String.valueOf(usuario.getCodarea()), usuario
								.getLogin());
				out = out.trim();
				System.out.println("OUT STORED!!!:" + out);
				if (out.equals("0")) {
					this.editarEmpresa = true;

					List<RemitenteBean> remitentes = entranteMesaVirtualDAO
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
						List<RepresentanteBean> remite = entranteMesaVirtualDAO
								.representante(area_remite);
						List itemsrepresentante = Utils
								.getRepresentante(remite);
						this.items3 = itemsrepresentante;
						// for (RepresentanteBean r: remite);

					} else {
						// System.out.println(area);
						entranteMesaVirtualDAO.trabajador(Integer.parseInt(area));
						List<TrabajadorBean> trabajador = entranteMesaVirtualDAO
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
	*/
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
			this.entrantesmesavirtual = entranteMesaVirtualDAO.BusquedaSP(area, opcion, detalle);
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
	protected ArrayList<EntranteMesaVirtualBean> selectedEntrantesMesaVirtual;
	// flat to indicate multiselect row enabled.
	protected String multiRowSelect = "Multiple";
	protected boolean multiple = false;
	protected boolean enhancedMultiple;

	

	public void eventoNuevo(ActionEvent evnt) {
		for (EntranteMesaVirtualBean p : selectedEntrantesMesaVirtual) {
			System.out.println(p.getArea() + "   " + p.getNano());
		}
	}
	
	public void eventbuscar(ActionEvent event) {
        
        //boolean formulario = true;
       

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        String validaopcion = this.validarOpcion();

        if (validaopcion.equals("ok")) {
        	
        	//formulario = true;
	        this.inicio = sdf.format(this.fechaInicial);
	        this.fin = sdf.format(this.fechaFinal);
	        
	        // se puede validar fechas
	        if (this.date1.after(this.date2)) {
	            this.error = "Fecha 1 debe ser menor que la fecha 2";
	            this.ver = true;        
	        } else {
	            this.entrantesmesavirtual = entranteMesaVirtualDAO.buscarSP(inicio, fin, String.valueOf(this.ncodmesapartes));
	            int dato = this.entrantesmesavirtual.size();
	            System.out.println("dato  " + dato);
	        
	        }
	        
        } else {
        	//formulario = false;
            this.error = validaopcion;
            this.ver = true;
        }
        
    }
	
	
	public String validarOpcion() {
        String msg = "Falta Seleccionar: ";
        boolean ok = true;
        
        if (this.ncodmesapartes==0) {
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
		
		login = usuario.getLogin();
		
		//this.entrantesmesavirtual = serviciosEntranteMesaVirtual.catalogo(ncodarea);

	
		List<TiposBean> tipos = entranteMesaVirtualDAO.tipos();
		List itemstipos = Utils.getTipos(tipos);
		this.items2 = itemstipos;
		for (TiposBean t : tipos);

		
		List<RepresentanteBean> representante = entranteMesaVirtualDAO.representante(dato_remitente);
		List itemsRepresentante = Utils.getRepresentante(representante);
		this.items3 = itemsRepresentante;
		// for (RepresentanteBean t: representante);

		List<EstadosBean> estado = entranteMesaVirtualDAO.estados();
		List itemsEstado = Utils.getEstado(estado);
		this.items7 = itemsEstado;
		// for (AccionBean a: accion);

		 List<AnioBean> anio = entranteMesaVirtualDAO.anio();
	     List itemsAnio = Utils.getAnio(anio);
	     this.items10 = itemsAnio;

		List<AreaBean> area_derivados = entranteMesaVirtualDAO.areas();
		List itemsderivados = Utils.getAreas(area_derivados);		
		this.items7a = Utils.getAreasLink(area_derivados);
		
		List<ServidorBean> bean = this.entranteMesaVirtualDAO.servidor();
        for (ServidorBean e : bean) {
            ubicacion = e.getDescripcion();
        }
        
        List<TiposBean> tipo_motivo = entranteMesaVirtualDAO.tipo_motivo();
		List itemstipomotivo = Utils.getMotivos(tipo_motivo);
		this.items11 = itemstipomotivo;
		
		/*
		List<TiposBean> motivo = entranteMesaVirtualDAO.motivo();
		List itemsmotivo = Utils.getMotivos(motivo);
		this.items12 = itemsmotivo;
		*/
		
		
		List<AreaBean> area_mesa = entranteMesaVirtualDAO.mesa();
		List itemsmesa = Utils.getAreas(area_mesa);		
		this.items13 = itemsmesa;
		/*
		for (AreaBean e : area_mesa) {
			ncodmesapartes = e.getCodigo();
        }
        */
		
		

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
	public List<EntranteMesaVirtualBean> getEntrantesmesa() {
		if ("nano".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesavirtual);
		}
		if ("ncorrelativo".equals(this.getSortColumnName())) {
		  this.sort(this.entrantesmesavirtual);
		 }
		if ("vnumdoc".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesavirtual);
		}
		if ("vdirigido".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesavirtual);
		}
		if ("vasunto".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesavirtual);
		}
		if ("area".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesavirtual);
		}

		if ("dfecregistro".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesavirtual);
		}
		if ("vestado".equals(this.getSortColumnName())) {
			this.sort(this.entrantesmesavirtual);
		}
		return this.entrantesmesavirtual;
		// return entrantesmesa;
	}

	

	public void setServiciosEntranteMesaVirtual(
			IServiciosEntranteMesaVirtual serviciosEntranteMesaVirtual) {
		this.serviciosEntranteMesaVirtual = serviciosEntranteMesaVirtual;
	}

	public void setSelectedEntrantesMesaVirtual(
			ArrayList<EntranteMesaVirtualBean> selectedEntrantesMesaVirtual) {
		this.selectedEntrantesMesaVirtual = selectedEntrantesMesaVirtual;
	}

	public void rowSelectionListener(RowSelectorEvent event) {
		// clear our list, so that we can build a new one
		selectedEntrantesMesaVirtual.clear();

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
		EntranteMesaVirtualBean employee;
		for (int i = 0, max = entrantesmesavirtual.size(); i < max; i++) {
			employee = (EntranteMesaVirtualBean) entrantesmesavirtual.get(i);
			if (employee.isSelected()) {
				selectedEntrantesMesaVirtual.add(employee);
			}
		}
		for (EntranteMesaVirtualBean p : selectedEntrantesMesaVirtual) {
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
			selectedEntrantesMesaVirtual.clear();

			// build the new selected list
			EntranteMesaVirtualBean employee;
			for (int i = 0, max = entrantesmesavirtual.size(); i < max; i++) {
				employee = (EntranteMesaVirtualBean) entrantesmesavirtual.get(i);
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
		//selectedItems1.clear();
		// ***** editar ...
		this.editarEmpresa = true;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("file", null);
		//this.selectedItems1.clear();
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

	/*
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
		
		
		
		

		if (ok) {
			mensaje = null;
		}
		return mensaje;
	}
	*/

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
		
		if (this.nfolioA <= 0) {
			if (ok == false) {
				msg += ", N° Folio";
			} else {
				msg += " N° Folio";
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
		if (this.vprioridadA.equals("SELECCIONAR")) {
			if (ok == false) {
				msg += ",Prioridad";
			} else {
				msg += ",Prioridad";
			}
			ok = false;
		}
		
		
		
		if (this.vestadoA.equals("ESTA000")) {
			if (ok == false) {
				msg += ",Cambiar el Estado del Documento a Pendiente";
			} else {
				msg += ",Cambiar el Estado del Documento a Pendiente";
			}
			ok = false;
		}
		
		
		

		if (ok) {
			msg = null;
		}
		return msg;
	}
	
	public RecursoReporteNuevo getRecursoReporteNuevo() {
        HttpSession session = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ec2 = FacesContext.getCurrentInstance().getExternalContext();
        
        
        session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_logeado = String.valueOf(usuario.getNomequipo());
        session.getAttribute("sUsuario");

                  
            parametros2.put("P_AREA", String.valueOf(ncodmesapartes));
            parametros2.put("P_INICIO", String.valueOf(sdf.format(this.fechaInicial)));
            parametros2.put("P_FIN", String.valueOf(sdf.format(this.fechaFinal)));            
            
            
            //System.out.println("Parametros : ");
            System.out.println("P_AREA        : " + String.valueOf(ncodmesapartes));            
            System.out.println("P_INICIO" + String.valueOf(sdf.format(this.fechaInicial)));
            System.out.println("P_FIN" + String.valueOf(sdf.format(this.fechaFinal)));
            
           
            reporteNuevo.asignar("reporteEnvio.pdf", ec2, parametros2, "reportes/reportedocentradagdi.jasper");
            recursoReporteNuevo = reporteNuevo;
          
        

        return recursoReporteNuevo;

    }
	
	public RecursoReporte getRecursoReporte(){
    	HttpSession session = null;    	
    	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	ec3 = FacesContext.getCurrentInstance().getExternalContext();
    	 
    	session = (HttpSession) FacesContext
        .getCurrentInstance().getExternalContext().getSession(false);
    	Usuario usu = null;
    	usu = (Usuario) session.getAttribute("sUsuario");
    	String nombre_equipo = usu.getNomequipo();
    	session.getAttribute("sUsuario");
    	//System.out.println("Tipo de Consulta");
    	//System.out.println(this.tipoConsulta);
    	
    	
    	System.out.println("REPORTE TIPO DE DOCUMENTO INGRESADOS MESA DE PARTES VIRTUAL !!!!");        	
    	parametros3.put("P_AREA", String.valueOf(ncodmesapartes));
    	parametros3.put("P_FECHA_INICIAL", String.valueOf(sdf.format(this.fechaInicial)));
    	parametros3.put("P_FECHA_FINAL", String.valueOf(sdf.format(this.fechaFinal)));  
    	parametros3.put("P_NOMBRE_AREA", String.valueOf(nombre_equipo));  
    	
        System.out.println("AREA :"+ ncodmesapartes); 
        System.out.println("FECHA INICIAL :"+ this.fechaInicial);
        System.out.println("FECHA FINAL :" +  this.fechaFinal);        
        System.out.println("NOMBRE AREA :" + nombre_equipo); 
            
        reportes.asignar("reporteEntr.pdf", ec3, parametros3, "reportes/rpt_tipo_doc_mesa_virtual.jasper");//REPORTE EN BLANCO POR NO TENER DATOS
        recursoReporte = reportes;
               
        return recursoReporte;        

    }
	

	

	public ArrayList<EntranteMesaVirtualBean> getSelectedEntrantesMesaVirtual() {
		return selectedEntrantesMesaVirtual;
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

	
	public String getArea_origenA() {
		return area_origenA;
	}

	public void setArea_origenA(String areaOrigenA) {
		area_origenA = areaOrigenA;
	}

	

	public String getCentroA() {
		return centroA;
	}

	public void setCentroA(String centroA) {
		this.centroA = centroA;
	}

	

	public int getNcodarea_origenA() {
		return ncodarea_origenA;
	}

	public void setNcodarea_origenA(int ncodareaOrigenA) {
		ncodarea_origenA = ncodareaOrigenA;
	}

	

	public int getDirigido() {
		return dirigido;
	}

	public void setDirigido(int dirigido) {
		this.dirigido = dirigido;
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

	

	public int getNrepresentanteA() {
		return nrepresentanteA;
	}

	public void setNrepresentanteA(int nrepresentanteA) {
		this.nrepresentanteA = nrepresentanteA;
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

	

	public int getNaccionA() {
		return naccionA;
	}

	public void setNaccionA(int naccionA) {
		this.naccionA = naccionA;
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

	public IServiciosUsuarios getServiciosUsuarios() {
		return serviciosUsuarios;
	}

	public void setServiciosUsuarios(IServiciosUsuarios serviciosUsuarios) {
		this.serviciosUsuarios = serviciosUsuarios;
	}

	public EntranteMesaVirtualDAO getEntranteMesaVirtualDAO() {
		return entranteMesaVirtualDAO;
	}

	public void setEntranteMesaVirtualDAO(
			EntranteMesaVirtualDAO entranteMesaVirtualDAO) {
		this.entranteMesaVirtualDAO = entranteMesaVirtualDAO;
	}

	public List<EntranteMesaVirtualBean> getEntrantesmesavirtual() {
		return entrantesmesavirtual;
	}

	public String getCorreoA() {
		return correoA;
	}

	public void setCorreoA(String correoA) {
		this.correoA = correoA;
	}

	public int getNtelefonoA() {
		return ntelefonoA;
	}

	public void setNtelefonoA(int ntelefonoA) {
		this.ntelefonoA = ntelefonoA;
	}

	public String getDireccionA() {
		return direccionA;
	}

	public void setDireccionA(String direccionA) {
		this.direccionA = direccionA;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getDfecdocCorreo() {
		return dfecdocCorreo;
	}

	public void setDfecdocCorreo(Date dfecdocCorreo) {
		this.dfecdocCorreo = dfecdocCorreo;
	}

	public String getVremitenteCorreo() {
		return vremitenteCorreo;
	}

	public void setVremitenteCorreo(String vremitenteCorreo) {
		this.vremitenteCorreo = vremitenteCorreo;
	}

	public String getVsubtituloCorreo() {
		return vsubtituloCorreo;
	}

	public void setVsubtituloCorreo(String vsubtituloCorreo) {
		this.vsubtituloCorreo = vsubtituloCorreo;
	}

	public String getVareadirigidoCorreo() {
		return vareadirigidoCorreo;
	}

	public void setVareadirigidoCorreo(String vareadirigidoCorreo) {
		this.vareadirigidoCorreo = vareadirigidoCorreo;
	}

	public String getPlantillaCorreo() {
		return plantillaCorreo;
	}

	public void setPlantillaCorreo(String plantillaCorreo) {
		this.plantillaCorreo = plantillaCorreo;
	}

	public String getVhostcorreo() {
		return vhostcorreo;
	}

	public void setVhostcorreo(String vhostcorreo) {
		this.vhostcorreo = vhostcorreo;
	}

	public String getVcorreo365() {
		return vcorreo365;
	}

	public void setVcorreo365(String vcorreo365) {
		this.vcorreo365 = vcorreo365;
	}

	public String getVpassword365() {
		return vpassword365;
	}

	public void setVpassword365(String vpassword365) {
		this.vpassword365 = vpassword365;
	}

	public String getDirigidoCorreo() {
		return dirigidoCorreo;
	}

	public void setDirigidoCorreo(String dirigidoCorreo) {
		this.dirigidoCorreo = dirigidoCorreo;
	}

	public String getVtipoingresoA() {
		return vtipoingresoA;
	}

	public void setVtipoingresoA(String vtipoingresoA) {
		this.vtipoingresoA = vtipoingresoA;
	}

	public String getVmotivoingresoA() {
		return vmotivoingresoA;
	}

	public void setVmotivoingresoA(String vmotivoingresoA) {
		this.vmotivoingresoA = vmotivoingresoA;
	}

	public List getItems11() {
		return items11;
	}

	public void setItems11(List items11) {
		this.items11 = items11;
	}

	public List getItems12() {
		return items12;
	}

	public void setItems12(List items12) {
		this.items12 = items12;
	}

	public String getVreferenciadireccionA() {
		return vreferenciadireccionA;
	}

	public void setVreferenciadireccionA(String vreferenciadireccionA) {
		this.vreferenciadireccionA = vreferenciadireccionA;
	}

	public boolean isDisPrint() {
		return disPrint;
	}

	public void setDisPrint(boolean disPrint) {
		this.disPrint = disPrint;
	}

	public RecursoReporteNuevo getReporteNuevo() {
		return reporteNuevo;
	}

	public void setReporteNuevo(RecursoReporteNuevo reporteNuevo) {
		this.reporteNuevo = reporteNuevo;
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

	public void setRecursoReporteNuevo(RecursoReporteNuevo recursoReporteNuevo) {
		this.recursoReporteNuevo = recursoReporteNuevo;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public List getItems13() {
		return items13;
	}

	public void setItems13(List items13) {
		this.items13 = items13;
	}

	public int getNcodmesapartes() {
		return ncodmesapartes;
	}

	public void setNcodmesapartes(int ncodmesapartes) {
		this.ncodmesapartes = ncodmesapartes;
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

	public RecursoReporte getReportes() {
		return reportes;
	}

	public void setReportes(RecursoReporte reportes) {
		this.reportes = reportes;
	}

	public void setRecursoReporte(RecursoReporte recursoReporte) {
		this.recursoReporte = recursoReporte;
	}

		
	
	
	


}
