package com.sedapal.tramite.mbeans;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.print.Doc;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.icesoft.faces.component.ext.KeyEvent;
import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.j2solutionsit.fwk.patterns.jsf.bean.BaseSortableList;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.SecretariaBean;
import com.sedapal.tramite.beans.Seguir;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.DocumentoSalidaDAO;
import com.sedapal.tramite.dao.EntranteDAO;
import com.sedapal.tramite.dao.SecuencialDAO;
import com.sedapal.tramite.nova.util.UsuarioExternoResponse;
import com.sedapal.tramite.nova.util.CasillaElectronicaResponse;
import com.sedapal.tramite.nova.util.DocumentoDTO;
import com.sedapal.tramite.nova.util.Exp_docs;
import com.sedapal.tramite.nova.util.FirmaDigitalRequest;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.nova.util.RecursoReporteNuevo;
import com.sedapal.tramite.servicios.IServiciosAPI;
import com.sedapal.tramite.servicios.IServiciosDocumentoSalida;
import com.sedapal.tramite.servicios.IServiciosUsuarios;
import com.sedapal.tramite.servicios.ServiciosAPI;
import com.sedapal.tramite.servicios.util.MailService;
import com.sedapal.tramite.util.Utils;
import com.sedapal.tramite.mbeans.FileCopy;
import edu.emory.mathcs.backport.java.util.Collections;

public class MBeanDocumentosSalientes extends BaseSortableList implements IMBeanDocumentosSalientes, Serializable {
	
    private boolean verAlertaConfirmacionBorrar;
    private boolean verAlertaConfirmacionGrabar;
    private String msg; 
    private String msgnuevo;
    private boolean editarEmpresa = true;
    private int area_remite;
    private boolean disEstado = true;
    private boolean disnotificador = true;
    private boolean verEliminar = true;
    private boolean verReport1 = false;
    private boolean verReport2 = false;
    Calendar c = Calendar.getInstance();
    private MCarga mCarga;
    // para el reporte
    private RecursoReport reporte; // inyectado
    private RecursoReport recursoReport;// para displayar
    
    private RecursoReporteNuevo reporteNuevo;
    private RecursoReporteNuevo recursoReporteNuevo;
    
    
    private Map<String, Object> parametros = new HashMap<String, Object>();
    private ExternalContext ec = null;
    
    private Map<String, Object> parametros2 = new HashMap<String, Object>();
    private ExternalContext ec2 = null;
    
    //private DocumentoSalidaDAO documentoSalidaDAO;
    private SecuencialDAO secuencialDAO;
    //
    private List<DocumentoSalidaBean> documento;
    private List<AreaBean> areas;
    public IServiciosDocumentoSalida serviciosDocumentoSalida;
    private List items1;
    private List items2;
    private List items3;
    private List itemsanno;
    // popup
    private List<EntranteBean> entradasalida;
    // cambio de Eli Diaz 27/11/2013
    // para armar los grupos
    List<RemitenteBean> ListaPersona = new ArrayList<RemitenteBean>();
    private List<Seguir> listaderivaciones = new ArrayList<Seguir>();
    List<GrupoBean> listagrupo = new ArrayList<GrupoBean>();
    
    private int indicador;
    private int codigo;
    private String cadenaDigitadaFinal;
    //fin de codigo grupo
    private List<EntranteBean> items4;   
    private boolean radioseguimiento = true;
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
    private Date date1 = new Date();
    private Date date2 = new Date();
    private String itemSeleccionado;
    private String item2Seleccionado;
    private String item3Seleccionado;
    private String itemareaSeleccionado;
    private String error;
    // para el formulario Actualizar
    private int anoA;
    private int origenA;
    private String origenesA;
    private String tipodocA;
    private String ntipodocA;
    private int codigoA;
    private String numerodocA;
    private int ndirigidoA;
    private String asuntoA;
    private String observacionA;
    private String comentarioA;
    private String estadoA;
    private String nestadoA;
    private int areaA;
    private String nom_areaA;
    private Date fechaA;
    private String trabajadorA;
    private String dirigidoA;
    private String dirigidoAA;
    private String referenciaA;
    private String doc_entradaA;
    private long ficha_dirigidoA;
    private long ficha_remiteA;
    private String vrescreA;
    private String vresactA;
    private Date dfeccreA = new Date();
    private Date dfecactA = new Date();
    private String prioridadA;
    private String ubicararchivoA;
    private int remitenteA;
    private Date dfecplazoA;
    private String vdirigidosA;
    private int diasA;
    private String vaccionA;
    private String aresseleccionasA;
    private int indicaadjuntoA;
    private String anodocumentoentrA;
    private String ndirigidoAA;
    private String nombreremitenteA;
    private String vcodigo_verificadorA;
    private int nficha_jefe_equipoA;
    private String notificadorA;
    private String ls_anexosA;
    // para formulario nuevo
    private int codigoN;
    private String anoN;
    private String numerodocN;
    private int ndirigidoN;
    private Date fechaN = new Date();
    private String origenesN;
    private int origenN;
    private String tipodocN;
    private String ntipodocN;
    private String asuntoN;
    private String estadoN;
    private String nestadoN;
    private int areaN;
    private String trabajadorN;
    private String observacionN;
    private String comentarioN;
    private String vremitenteN;
    private String dirigidoN;
    private String referenciaN;
    private String nom_areaN;
    private String doc_entradaN;
    private long ficha_dirigidoN;
    private long ficha_remiteN;
    private String vrescreN;
    private String vresactN;
    private Date dfeccreN = new Date();
    private Date dfecactN = new Date();
    private String prioridadN;
    private int ncorr_entrN;
    private String ubicararchivoN;
    private int remitenteN;
    private Date dfecplazoN;
    private String areaseleccionadaN;
    private String areadeseleccionadaN;
    private String vdirigidosN;
    private int diasN;
    private String vaccionN;
    private int nindicadorN;
    private int indicaadjuntoN;
    private String anodocumentoentrN;
    private String vtipo="";
    private String nombreremitenteN;
    private String notificadorN;
    private String ls_anexosN;
    //
    private boolean ver = false;
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private List<String> selectedItems1;
    
    private boolean verPDF = false;
    private String rutaPDFPopup;
    
    
    // binding al combo
    private HtmlSelectOneMenu combo;
    private HtmlSelectOneMenu combo1;
    private HtmlSelectOneMenu comboarea;
    
    private String desc;
    //private Logger logger = Logger.getLogger("R1");
    private boolean verDetalles = false;
    private boolean verCatalogoEntrada = false;
    private String area;
    private String area_origen;
    private String tipodoc;
    private String file;
    private String ruta;
    private int ind_adjuntar = 0;
    private String opcion;
    private String detalle;
    private String opcion_entrada;
    private String detalle_entrada;
    private String detallecriterio;
    private int retornar;
    private int cont = 0;
    private String nombre_archivo;
    private String ubicacion;
    private String area_deriva;
    private String area_dirigido = "";
    private String suma_areas;
    private String accion = "";
    // area seleccionadas
    private int areas_actuales;
    private String valores = "";
    private String origen;
    private int origenes;
    // entidades externas
    private String empresa;
    private String cadenaDigitada;
    // sacar area de la lista
    private String quitar_areas = "";
    private String textoA;
    // para busquerda en el popub
    private String opcionentrada;
    private String detalleentrada;
    private DocumentoSalidaDAO documentoSalidaDAO;
    private LinkedHashMap<String, Object> observaciones1;
    private List<AreaBean> areasOrigen = new ArrayList<AreaBean>();
    // para quitar areas
    public String quitarareas = "";
    public String quitarfichas = "";
    private List<Seguir> seleccionados = new ArrayList<Seguir>();
    /*
    private List<Seguir> seleccionados = Collections
    .synchronizedList(new ArrayList<Seguir>());
    */
    private List<AreaBean> areasseleccionados = Collections
      .synchronizedList(new ArrayList<AreaBean>());
    private String estadoSeleccionado = "TO";
    private String opcion_seguimiento = "NADA";
    // Agregado el 16/11/2011 - Envio de correos - Alfredo Panitz
    @Autowired
    private IServiciosUsuarios serviciosUsuarios;
    @Autowired
    private MailService mailService;
    // inject Y ADD CF AGO 2011
    private EntranteDAO entranteDAO;
    private List<SelectItem> itemsPersonalRepresentantes;
    private List<SelectItem> itemsPersonalRepresentantesA;
    private String derivadoN;
    private String derivadoA;
    private String derivadoAA;
    private List itemsTrabajadores;
    ///Autocompletar Eli Diaz Horna 26/03/2012
    private List<SelectItem> posiblespersonas;
    private String personaSeleccionada;
    private String labelPersona;
    private long ficha = 0;
    ///Autocompletar Eli Diaz Horna 26/03/2012
    private List<SelectItem> posiblespersonasA;
    private String personaSeleccionadaA;
    private String labelPersonaA;
    private long fichaA = 0;
    //para el reporte
    private String tipodocarea;
    //valida el orige del doc 
    private int ls_origen_actual;
    String valida3 = null;
    
    //para los asuntos estadares
	private List<TiposBean> itemsAsuntos;
	private List<TiposBean> asuntos;
	private String tipoopcion="NADA";
    private boolean verdocs;
    private boolean disverestado = true;
    private boolean disverremite = true;
    private String opcion_asunto;
    private String detalle_asunto;
    
    //variable direccion archivo pdf
    private String ls_ubicacion;
    private String link_acceso;
    private int notificacion;
    private int li_ficha_jefe;
    private int li_ficha_secretaria;
    private String vficha_secretaria;
    private String vnombre_secretaria;
    private String correo;
    private String vestado_secretaria;
    private int li_cantidad_secretaria;
    
    //variables nuevas anexos 31.05.2023
    private String ls_anexos;
    private int ind_anexos;
    
    
    //SED-FON-161 INI
    private List itemsTipoDoc; // tipos de documento para busqueda
    private List<TiposDocumentosBean> listTipoDoc;
    private String remitente_conceptoBuscar;
    private String remitente_elementoBuscar;
    private String remitente_tipoDoc;
    private String remitente_nroDoc;
    private String remitente_nombre;
    private String origenTexto;
    private String remitenteSeleccionado;
    private IServiciosAPI serviciosAPI;
    private Boolean mostrarTipoBusqueda;
    private Boolean deshabilitaBusqueda;
    private Boolean nuevoDirigido;
    private String numeroDocumentoTO; 
    
    private Boolean documentoNumero=true;
    private Boolean documentoCaracter=false;
   
    //SED-REQ-00001
    private Boolean verNotificadorSI=true;
    
    
	public Boolean getVerNotificadorSI() {
		return verNotificadorSI;
	}

	public void setVerNotificadorSI(Boolean verNotificadorSI) {
		this.verNotificadorSI = verNotificadorSI;
	}

	public Boolean getDocumentoNumero() {
		return documentoNumero;
	}

	public void setDocumentoNumero(Boolean documentoNumero) {
		this.documentoNumero = documentoNumero;
	}

	public Boolean getDocumentoCaracter() {
		return documentoCaracter;
	}

	public void setDocumentoCaracter(Boolean documentoCaracter) {
		this.documentoCaracter = documentoCaracter;
	}

	public String getNumeroDocumentoTO() {
		return numeroDocumentoTO;
	}

	public void setNumeroDocumentoTO(String numeroDocumentoTO) {
		this.numeroDocumentoTO = numeroDocumentoTO;
	}

	public Boolean getNuevoDirigido() {
		return nuevoDirigido;
	}

	public void setNuevoDirigido(Boolean nuevoDirigido) {
		this.nuevoDirigido = nuevoDirigido;
	}

	public Boolean getDeshabilitaBusqueda() {
		return deshabilitaBusqueda;
	}

	public void setDeshabilitaBusqueda(Boolean deshabilitaBusqueda) {
		this.deshabilitaBusqueda = deshabilitaBusqueda;
	}

	public Boolean getMostrarTipoBusqueda() {
		return mostrarTipoBusqueda;
	}

	public void setMostrarTipoBusqueda(Boolean mostrarTipoBusqueda) {
		this.mostrarTipoBusqueda = mostrarTipoBusqueda;
	}

	public IServiciosAPI getServiciosAPI() {
		return serviciosAPI;
	}

	public void setServiciosAPI(IServiciosAPI serviciosAPI) {
		this.serviciosAPI = serviciosAPI;
	}

	public String getRemitenteSeleccionado() {
		return remitenteSeleccionado;
	}

	public void setRemitenteSeleccionado(String remitenteSeleccionado) {
		this.remitenteSeleccionado = remitenteSeleccionado;
	}

	public String getOrigenTexto() {
		return origenTexto;
	}

	public void setOrigenTexto(String origenTexto) {
		this.origenTexto = origenTexto;
	}
	private List<RemitenteBPM> listaRemitentesBPM;
    
    

	public List<RemitenteBPM> getListaRemitentesBPM() {
		return listaRemitentesBPM;
	}

	public void setListaRemitentesBPM(List<RemitenteBPM> listaRemitentesBPM) {
		this.listaRemitentesBPM = listaRemitentesBPM;
	}

	public List getItemsTipoDoc() {
		return itemsTipoDoc;
	}

	public void setItemsTipoDoc(List itemsTipoDoc) {
		this.itemsTipoDoc = itemsTipoDoc;
	}

	public String getRemitente_conceptoBuscar() {
		return remitente_conceptoBuscar;
	}

	public void setRemitente_conceptoBuscar(String remitente_conceptoBuscar) {
		this.remitente_conceptoBuscar = remitente_conceptoBuscar;
	}

	public String getRemitente_elementoBuscar() {
		return remitente_elementoBuscar;
	}

	public void setRemitente_elementoBuscar(String remitente_elementoBuscar) {
		this.remitente_elementoBuscar = remitente_elementoBuscar;
	}

	public String getRemitente_tipoDoc() {
		return remitente_tipoDoc;
	}

	public void setRemitente_tipoDoc(String remitente_tipoDoc) {
		this.remitente_tipoDoc = remitente_tipoDoc;
	}

	public String getRemitente_nroDoc() {
		return remitente_nroDoc;
	}

	public void setRemitente_nroDoc(String remitente_nroDoc) {
		this.remitente_nroDoc = remitente_nroDoc;
	}

	public String getRemitente_nombre() {
		return remitente_nombre;
	}

	public void setRemitente_nombre(String remitente_nombre) {
		this.remitente_nombre = remitente_nombre;
	}

	@PostConstruct
    public void carga() {
        selectedItems1 = new ArrayList<String>();
        observaciones1 = new LinkedHashMap<String, Object>();
        observaciones1.put("Por Disposición", "11");
        observaciones1.put("Coordinar Con", "12");
        observaciones1.put("Acciónn Necesaria", "13");
        observaciones1.put("Adjuntar Antecedentes", "14");
        observaciones1.put("Revisar/Informar", "15");
        observaciones1.put("Preparar Respuesta", "16");
        observaciones1.put("Conocimientos y Fines", "17");
        observaciones1.put("Su aprobación", "18");
        observaciones1.put("Tener Pendiente", "19");
        observaciones1.put("Resolver", "20");
        observaciones1.put("Archivar", "21");
        observaciones1.put("Para Directorio", "22");
        observaciones1.put("Para  trámite", "23");
        observaciones1.put("Contestar directamente", "24");
        observaciones1.put("Otros", "25");
        // Reporte
        reporte.asignar("ticket.pdf", ec, parametros, "reportes/salida.jasper");// se
        // le
        // puede
        // adicionar
        // parametros...
        // reporte.asignar("ticket.pdf", ec, parametros);//se le puede adicionar
        // parametros...
        recursoReport = reporte;
    }
	 public void buscarPersona(ValueChangeEvent changeEvent) {
		  if (changeEvent.getComponent() instanceof SelectInputText) {
			  SelectInputText autoComplete = (SelectInputText) changeEvent.getComponent();
			  String tipoBusqueda="";
			  String valorBusqueda;
			  List<RemitenteBPM> lista  = new ArrayList<RemitenteBPM>();
			  List<SelectItem> list = new ArrayList<SelectItem>();
			  tipoBusqueda = this.remitente_conceptoBuscar;
			  System.out.println(tipoBusqueda);
			  
			  valorBusqueda = (String) changeEvent.getNewValue();
			  if (tipoBusqueda.equals("DOC000A")) {  //buscar areas por nombre
		    	   lista = entranteDAO.consultaAreas(valorBusqueda);
		    	   ls_origen_actual=1;
		    	   this.origenesN = "1"; 
		       } else {
		    	   lista = entranteDAO.consultaRemitentesBPM(tipoBusqueda,valorBusqueda);   
		       }
			  if (lista.size()>0) {
			    	// inicializamos un bucle for para recorrer el objeto persona     
			           for (RemitenteBPM p : lista) {
			                   SelectItem item = new SelectItem(p.getNcorrelativo(), p.getNombre());
			                   list.add(item);
			           }
			           this.posiblespersonas = list;
			           if (autoComplete.getSelectedItem() != null) {
			        	   RemitenteBean personaSel = (RemitenteBean) autoComplete.getSelectedItem().getValue();
			           }
			}
		  }
	 }
	 private boolean validaNumeroDocumento(String nroDocumento) {
		  String tipo=this.remitente_conceptoBuscar;
		  boolean retorno=true;
		 if ((!tipo.equals("DOC0000")) &&  (!tipo.equals("DOC000A")))  { // si no es nombre ni area
			 if (nroDocumento.trim().length()<8) {
				 return false;
			 }
			 try {
				 Long documento = new Long(nroDocumento.trim());
				 return true;
				 
			 }catch(Exception e) {
				 System.out.println("Numero de documento no valido");
				 retorno=false;
			 }
		 }
		 return retorno;
	 }
	 public void buscar() {
			System.out.println("ok click");
			System.out.println(this.personaSeleccionada);
			this.nuevoDirigido=false;
			cadenaDigitada =  this.personaSeleccionada;
			this.posiblespersonas=null;
			if (!validaNumeroDocumento(cadenaDigitada)) {
				 this.error="Número de documento no válido";
				  this.ver=true;
				  this.personaSeleccionada="";
			} else {
			 this.posiblespersonas = buscaPersona(cadenaDigitada);
			 if ( this.posiblespersonas.size()==0) {
				 this.error="Usuario BPM no existe";
				  this.ver=true;
			 }   else {
	            if ((!this.remitente_conceptoBuscar.equals("DOC000A")) && (!this.remitente_conceptoBuscar.equals("DOC0000"))) {
	                RemitenteBean personaSel = (RemitenteBean) this.posiblespersonas.get(0).getValue();
	                
	                this.dirigidoN = Integer.toString(personaSel.getCodigo());
	                this.remitenteSeleccionado = Integer.toString(personaSel.getCodigo());
	                area_remite = personaSel.getCodigo();
	                if (origenes == 1) {
	                	ls_origen_actual=1;
	                    entranteDAO.trabajador(Integer.parseInt(area));
	                    List<TrabajadorBean> trabajador = documentoSalidaDAO.trabajador(area_remite);
	                    List itemsTrabajador = Utils.getTrabajador_derivador(trabajador); 
	                    List<JefeBean> bean = this.entranteDAO.jefe(area_remite);
	                    for (JefeBean e : bean) {
	                        ficha = e.getFicha();
	                    }
	                    this.itemsPersonalRepresentantes = itemsTrabajador;                    
	                    this.ficha_dirigidoN = ficha;
	                    this.derivadoN = String.valueOf(ficha);
	                    this.derivadoA = String.valueOf(ficha);

	                } else {
	                    List<RepresentanteBean> remite = documentoSalidaDAO.representante(area_remite);
	                    ls_origen_actual=2;
	                    List itemsrepresentante = Utils.getRepresentante(remite);
	                    this.itemsPersonalRepresentantes = itemsrepresentante;
	                }
	               // this.posiblespersonas = null;
	            }
			 }
			}
		}
	public void buscar(ActionEvent actionEvent) {
		this.buscar();
	}
    // Autocompletar ------Inserta Eli Diaz	 26/03/2012
    public void autocompletarPersona(ValueChangeEvent changeEvent) {
        //Se comprueba la instancia del objeto input
        if (changeEvent.getComponent() instanceof SelectInputText) {
        	this.nuevoDirigido=false;
            //se extrae la instancia del componente Selectinputtext
            SelectInputText autoComplete = (SelectInputText) changeEvent.getComponent();
            
            Map requestParemeterMap = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestParameterMap();
      

            //Atraves del evento se extrae lo que se digito en el SelectInputText
            cadenaDigitada = (String) changeEvent.getNewValue();
            //Se a�ade a la propiedad posiblepersonas lo que devuelve
            //el metodo buscaPersona
            //System.out.println("Imprime Eli DIaz");
            //System.out.println(cadenaDigitada);
            
            
            System.out.println(this.origenes);
            if ((this.remitente_conceptoBuscar.equals("DOC000A")) || (this.remitente_conceptoBuscar.equals("DOC0000")))  {
            	this.posiblespersonas = buscaPersona(cadenaDigitada);	
            
            


            //en el if se comprueba si existe alguna seleccion
            if (autoComplete.getSelectedItem() != null) {
                //Se extrae el objeto seleccionado
                RemitenteBean personaSel = (RemitenteBean) autoComplete.getSelectedItem().getValue();
                // a labelPersona la modificamos segun lo seleccionado
                //this.labelPersona = "La persona seleccionada es : " + personaSel.getApellido() + " " + personaSel.getNombre();        	
                //this.labelPersona = "La persona seleccionada es : " + personaSel.getDescripcion();
                this.dirigidoN = Integer.toString(personaSel.getCodigo());
                this.remitenteSeleccionado = Integer.toString(personaSel.getCodigo());
                area_remite = personaSel.getCodigo();
                //this.origenes= this.origenN;
                //System.out.println("origen eli");
                //System.out.println(this.origenN);
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
                    // System.out.println(area);
                    entranteDAO.trabajador(Integer.parseInt(area));
                    List<TrabajadorBean> trabajador = documentoSalidaDAO.trabajador(area_remite);
                    List itemsTrabajador = Utils.getTrabajador_derivador(trabajador); 
                    //this.items1 = itemsAreas;
                    // this.items6 = itemsTrabajador;
                    // for (TrabajadorBean t: trabajador)
                    // System.out.println(t.getArea());
                    List<JefeBean> bean = this.entranteDAO.jefe(area_remite);
                    for (JefeBean e : bean) {
                        ficha = e.getFicha();
                    }
                    this.itemsPersonalRepresentantes = itemsTrabajador;                    
                    this.ficha_dirigidoN = ficha;
                    this.derivadoN = String.valueOf(ficha);
                    this.derivadoA = String.valueOf(ficha);

                } else {
                    List<RepresentanteBean> remite = documentoSalidaDAO.representante(area_remite);
                	//List<RepresentanteBean> remite = documentoSalidaDAO.getRemitenteBPM(area_remite);
                	
                    List itemsrepresentante = Utils.getRepresentante(remite);
                    this.itemsPersonalRepresentantes = itemsrepresentante;
                    /*
                     List<RepresentanteBean> remite = entranteDAO.representante(area_remite);
                     List itemsrepresentante = Utils.getRepresentante(remite);
                     this.items6 = itemsrepresentante;
                     */
                }
            }
            }
        }
    }
    
 // Autocompletar cuando se modifica un documento de salida------Inserta Eli Diaz	 30/10/2012
    public void autocompletarPersonaA(ValueChangeEvent changeEvent) {
        //Se comprueba la instancia del objeto input
        if (changeEvent.getComponent() instanceof SelectInputText) {
            //se extrae la instancia del componente Selectinputtext
            SelectInputText autoComplete = (SelectInputText) changeEvent.getComponent();
            //Atraves del evento se extrae lo que se digito en el SelectInputText
            cadenaDigitada = (String) changeEvent.getNewValue();
            //Se a�ade a la propiedad posiblepersonas lo que devuelve
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

                //Eli Comenta este codigo 31/10/2012
                //this.dirigidoN = Integer.toString(personaSelA.getCodigo());
                this.derivadoA = Integer.toString(personaSelA.getCodigo());
                area_remite = personaSelA.getCodigo();                
                //System.out.println("Eli Busca Codigo");
                //System.out.println(this.derivadoA);
                //System.out.println(area_remite);
                //System.out.println(area);
                //System.out.println(origenes);
                this.derivadoAA=this.derivadoA;  
                this.vdirigidosA=personaSeleccionadaA;
                this.origenes= this.origenA;
                //this.remitenteA=this.derivadoA;
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
                    List<AreaBean> areas = documentoSalidaDAO.areas();
                    List itemsAreas = Utils.getAreas(areas);
                    this.items1 = itemsAreas;
                    
                    // System.out.println(area);   
                    //area=area_remite;
                    //entranteDAO.trabajador(Integer.parseInt(area));
                    entranteDAO.trabajador(area_remite);
                    List<TrabajadorBean> trabajador = documentoSalidaDAO.trabajador(area_remite);                                        
                    List itemsTrabajador = Utils.getTrabajador_derivador(trabajador);
                    System.out.println("Despues");
                    System.out.println(trabajador);
                    System.out.println(itemsTrabajador);
                    // this.items6 = itemsTrabajador;
                    // for (TrabajadorBean t: trabajador)
                    // System.out.println(t.getArea());
                    System.out.println(itemsTrabajador);
                    List<JefeBean> bean = this.entranteDAO.jefe(area_remite);
                    for (JefeBean e : bean) {
                        ficha = e.getFicha();
                    }
                    this.itemsPersonalRepresentantesA = itemsTrabajador;
                    //System.out.println(this.itemsPersonalRepresentantesA);
                    //this.ficha_dirigidoN = ficha;
                    this.ficha_dirigidoA = ficha;
                    this.derivadoN = String.valueOf(ficha);
                    this.derivadoA = String.valueOf(ficha);

                } else {
                	
                	if (!changeEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                        changeEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                        changeEvent.queue();
                        return;
                    }
                	
                    ///Eli Agrega este codigo 14/11/2012
                	List<RemitenteBean> remitente = documentoSalidaDAO.remitentes();
                    List itemsAreas = Utils.getRemitentes(remitente);
                    this.items1 = itemsAreas;
                    
                    List<RepresentanteBean> remite = documentoSalidaDAO.representante(area_remite);
                    List itemsrepresentanteA = Utils.getRepresentante(remite);
                    this.itemsPersonalRepresentantesA = itemsrepresentanteA;
                    
                }
            }
        }
    }

    private void capturaTipoIngresoNuevo() {
    	if (this.origenes ==1) { // interno
    		this.documentoNumero=false;
    		this.documentoCaracter=true;
    	} else { // externo
    		this.documentoNumero=false;
    		this.documentoCaracter=true;
    			String tipo = this.remitente_conceptoBuscar;
	    		 if ((!tipo.equals("DOC0000")) &&  (!tipo.equals("DOC000A")))  { 
	    			this.documentoNumero=true;
	    	    	this.documentoCaracter=false;
	    		 }
    	}
    }
    public List<SelectItem> buscaPersona(String cadenaDigitada) {
        //inicializamos un objeto del tipo SelectItem    
        List<SelectItem> list = new ArrayList<SelectItem>();
        // List<Persona> list = new ArrayList<Persona>();  
        //List<RemitenteBean> ListaPersona = new ArrayList<RemitenteBean>();
        //System.out.println("Imprime Eli DIaz Paso 222");
        //System.out.println(cadenaDigitada);  
        //origenes
        
        
        //tipo busqueda 161
        List<RemitenteBPM> lista  = new ArrayList<RemitenteBPM>();
        String tipo=this.remitente_conceptoBuscar;
        if (tipo.equals("DOC000A")) { //por area
        	 List<RemitenteBean> lp =  new ArrayList<RemitenteBean>();
        	lp = documentoSalidaDAO.personaIntena(cadenaDigitada);
        	 for (RemitenteBean e : lp) {
        		 RemitenteBPM rem = new RemitenteBPM();
        		 rem.setNombre(e.getDescripcion());
        		 rem.setNcorrelativo(new Long(e.getCodigo()));
        		 rem.setTipoDocumento("0");
        		 lista.add(rem);
        	 }
        
        	
 	   	} else {
 		   lista = entranteDAO.consultaRemitentesBPM(tipo,cadenaDigitada);      
 		   if (lista.size()==0) {
 			  if (!tipo.equals("DOC0000")) { //si no es por nombre, es por tipo de documento
 	 			 
 	 			 RemitenteBPM remitenteBPM = new RemitenteBPM();
 	 			remitenteBPM =  this.serviciosAPI.actualizarPorTipoDocumento(tipo, cadenaDigitada.trim(), this.listTipoDoc); 		
 	 			if (remitenteBPM != null) {
 	 				lista.add(remitenteBPM);
 	 			    List<RemitenteBean> remitente = documentoSalidaDAO.remitentes();
 	 	            List itemsAreas = Utils.getRemitentes(remitente);
 	 	            this.items1 = itemsAreas;
 	 			}
 	 		 
 	 		  }  
 			 ListaPersona = new ArrayList<RemitenteBean>();
 		   }
 	   	}
        if (lista.size()>0) {
        	
	    	// inicializamos un bucle for para recorrer el objeto persona     
	       
	           this.posiblespersonas = list;	          
	           this.listaRemitentesBPM = lista;
	           ListaPersona = new ArrayList<RemitenteBean>();
	           for (RemitenteBPM i : lista) {
	        	   RemitenteBean rem = new RemitenteBean();
	        	   rem.setCodigo((i.getNcorrelativo().intValue()));
	        	   rem.setDescripcion(i.getNombre());
	        	   ListaPersona.add(rem);	        	   
	           }
        }
        
        
//        if (origenes == 1) {
//            if (cadenaDigitada.length() >= 1) {
//                System.out.println("Interno");
//                ListaPersona = documentoSalidaDAO.personaIntena(cadenaDigitada);
//                for (RemitenteBean e : ListaPersona) {
//                    indicador = e.getIndicador();
//                    codigo = e.getCodigo();
//                }
//            }
//        } else {
//            if (cadenaDigitada.length() >= 2) {
//                System.out.println("Externo");
//                //ListaPersona = documentoSalidaDAO.persona(cadenaDigitada);
//                ListaPersona = documentoSalidaDAO.personaExterna(cadenaDigitada);
//            }
//        }
        
      
        
        
        // inicializamos un bucle for para recorrer el objeto persona     
        for (RemitenteBean p : ListaPersona) {
        	RemitenteBPM rem = new RemitenteBPM();
            //for (Persona p : Persona) {   
            //System.out.println(p.getCodigo());	
            //System.out.println(p.getDescripcion());
            // preparamos la cadena a comparar y la pasamos a minusculas    
            String cadenaPersona = (String.valueOf(p.getCodigo()) + "" + p.getDescripcion()).toLowerCase();
            cadenaDigitada = cadenaDigitada.toLowerCase();
            System.out.println("Ver Eli DIaz");
            System.out.println(cadenaDigitada);
            System.out.println(p.getCodigo());
            System.out.println(p.getDescripcion());
            System.out.println(p.getIndicador());
            indicador = p.getIndicador();
            //con el metodo indesof del estring verificamos si la   
            // cadenaDigitada esta contenida el la cadenaPersona, que con-    
            //tiene el apellido y nombre de la Persona    
         /*   if (cadenaPersona.indexOf(cadenaDigitada) >= 0) {
                SelectItem item = new SelectItem(p, p.getDescripcion());
                list.add(item);
            } */
            SelectItem item = new SelectItem(p, p.getDescripcion());
            list.add(item);
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
        if (this.origenA == 1) {
            if (cadenaDigitada.length() >= 1) {
                System.out.println("Interno");
                ListaPersona = documentoSalidaDAO.personaIntenaA(cadenaDigitada);
                    
            }
        } else {
            if (cadenaDigitada.length() >= 2) {
                System.out.println("Externo");
                //ListaPersona = documentoSalidaDAO.persona(cadenaDigitada);
                ListaPersona = documentoSalidaDAO.personaExterna(cadenaDigitada);
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
    
    public void eventtipoconsulta(ValueChangeEvent event){
    	
    	String opcion = (String) event.getNewValue();
    	
    	if (opcion.equals("CONS00")){  
    		System.out.println("Estoy" + opcion);
    		this.disverestado=true;
    		this.disverremite=true;    		
    		
    	} else if(opcion.equals("CONS01")) {
    		System.out.println("Estoy" + opcion);
    		this.disverestado=false;
    		this.disverremite=true;
    		this.verReport1 = true;
    		this.verReport2 = false;
    		
    	} else {
    		System.out.println("Estoy" + opcion);
    		this.disverestado=true;
    		this.disverremite=false;
    		this.verReport1 = false;
    		this.verReport2 = true;
    		
    	}
    	
    }
    

    public List<SelectItem> getItemsPersonalRepresentantes() {
        return itemsPersonalRepresentantes;
    }

    public void setItemsPersonalRepresentantes(
            List<SelectItem> itemsPersonalRepresentantes) {
        this.itemsPersonalRepresentantes = itemsPersonalRepresentantes;
    }

    public String getDerivadoN() {
        return derivadoN;
    }

    public void setDerivadoN(String derivadoN) {
        this.derivadoN = derivadoN;
    }

    public String getDerivadoA() {
        return derivadoA;
    }

    public void setDerivadoA(String derivadoA) {
        this.derivadoA = derivadoA;
    }

    public List getItemsTrabajadores() {
        return itemsTrabajadores;
    }

    public void setItemsTrabajadores(List itemsTrabajadores) {
        this.itemsTrabajadores = itemsTrabajadores;
    }

    public void setEntranteDAO(EntranteDAO entranteDAO) {
        this.entranteDAO = entranteDAO;
    }

    /**
     * ADD CF AGO 2011
     *
     * @param changeEvent
     */
    public void capturaPerfil(ValueChangeEvent changeEvent) {
        // /acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext
                .getCurrentInstance().getExternalContext()
                .getSession(false);
        // ///guardando en sesion un objeto
        int perfil = 0;
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        perfil = usuario.getNcodperfil();
        
        if (perfil == 7 || perfil == 8) {
            this.documento = serviciosDocumentoSalida.catalogo(area);            
            List<TiposBean> estadoderivador = documentoSalidaDAO.estadoderivador();
            List itemsMantenimiento = Utils.getTipos(estadoderivador);
            this.items9 = itemsMantenimiento;
            
            this.verEliminar=true;
                        

        } else if (perfil == 10) {
            this.documento = serviciosDocumentoSalida.catalogo(area);
            List<TiposBean> estadojefe = documentoSalidaDAO.estadojefe();
            List itemsMantenimiento = Utils.getTipos(estadojefe);
            this.items9 = itemsMantenimiento;
            
            this.verEliminar=false;

        }  else {
        	 this.documento = serviciosDocumentoSalida.catalogo(area);
             List<TiposBean> mantenimiento = documentoSalidaDAO.estadomantenimiento();
             List itemsMantenimiento = Utils.getTipos(mantenimiento);
             this.items9 = itemsMantenimiento;
        }

    }
    
    

    public void capturaPersonalRepresentantes(ValueChangeEvent changeEvent) {

        area = (String) changeEvent.getNewValue();
        area_remite = Integer.parseInt(area);
        System.out.println("Eli Estoy aqui");
        System.out.println(area_remite);

        if (area_remite == 100 || area_remite == 601) {
        } else if (area_remite > 600) {
            List<RepresentanteBean> remite = entranteDAO
                    .representante(area_remite);
            List itemsrepresentante = Utils.getRepresentante(remite);
            this.itemsPersonalRepresentantes = itemsrepresentante;
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
            this.itemsPersonalRepresentantes = itemsTrabajador;
            System.out.println(this.itemsPersonalRepresentantes);
            this.ficha_dirigidoN = ficha;
            this.derivadoN = String.valueOf(ficha);
            this.derivadoA = String.valueOf(ficha);

        }

    }

    public void pasarItems_anterior(ActionEvent actionEvent) {

        Seguir seguir = new Seguir();
        // llenar el objeto seguir....
        // seguir.setArea(this.area);
        // seguir.setFicha("1000");        
        
        // Eli Agrega este codigo 27/11/2013
        if (indicador ==0) {
        	String nombre = this.buscarNombre(this.derivadoN);
            seguir.setCodArea(nombre);        
            seguir.setEstado(this.estadoSeleccionado);// se muestra en la tabla
            seguir.setNombreTrabajador(this.dirigidoN);// se muestra en la tabla
            //System.out.println("pasarItems Nuevo");        
            //System.out.println(nombre);
            //System.out.println(this.estadoSeleccionado);
            //System.out.println(this.dirigidoN);
            // seguir.setArea(this.area_deriva);
            SelectItem item = null;
            System.out.println("Personal Representante");
            System.out.println(this.itemsPersonalRepresentantes.size());
            if (this.estadoSeleccionado != ""  && this.itemsPersonalRepresentantes.size()>0) {
                // adiconando el objeto en la tabla
                boolean ok = false;
                ok = this.buscar(seguir, this.seleccionados);
                if (!ok) {
                    for (int i = 0; i < this.items1.size(); i++) {
                    	//System.out.println("Eli pinta items1");
                    	//System.out.println(this.items1.size());
                        item = (SelectItem) this.items1.get(i);
                        String area = item.getValue().toString();
                        //System.out.println("Eli esta aqui");
                        System.out.println("Pinto la comparacion");
                        System.out.println(area);
                        System.out.println(this.dirigidoN);
                        if (area.equals(this.dirigidoN)) {
                            seguir.setArea(this.dirigidoN); // CF Equipo o Empresa  // break;
                            seguir.setNombreTrabajador(item.getLabel()); // para mostrar en la tabla
                            seguir.setFicha(this.derivadoN);// Codigo del trabajador
                            //System.out.println("pasarItems");
                            //System.out.println(this.dirigidoN);
                            //System.out.println(item.getLabel());
                            //System.out.println(this.derivadoN);
                            // o representante

                            if (Integer.parseInt(this.dirigidoN) > 601) {
                                ls_origen_actual = 2;
                            }

                            this.seleccionados.add(seguir);
                            this.personaSeleccionada = "";
                            this.itemsPersonalRepresentantes.clear();
                        }
                    }
                }
            } else {
                this.error = "Seleccione un tipo Destinatario o no tiene Representante !!";
                this.ver = true;

            }
        	
        } else {
        	
        	 
        	 
        	 List<GrupoBean> bean = this.documentoSalidaDAO.grupo(codigo);
        	 int dato = bean.size();
        	 SelectItem item = null;
        	 //System.out.println("Pinto Cantidad");
        	 //System.out.println(dato);
        	 boolean ok = false;
             ok = this.buscar(seguir, this.seleccionados);
             if (!ok) { 
            	 
             for (GrupoBean e : bean) {	 
                 int area = e.getCodigo();
                 //areas = String.valueOf(e.getCodigo());
                 List<JefeBean> jefe = this.documentoSalidaDAO.jefe_grupo(area);
                 for (JefeBean j : jefe){
                	 	String codigoarea = String.valueOf(j.getNcodarea());
                	// if (area == j.getNcodarea()){  
                	   for (int i = 0; i < this.items1.size(); i++) {                         	
                         item = (SelectItem) this.items1.get(i);
                         String varea = item.getValue().toString();
                         //System.out.println("Pinto la comparacion");
                 	 	 //System.out.println(varea);
                 	 	 //System.out.println(j.getNcodarea());   
                         seguir = new Seguir();
                         if (varea.equals(codigoarea)){                        	 
                        	 //System.out.println("Veo el nombresssssss");
                        	 //System.out.println(j.getNcodarea());
                        	 //System.out.println(j.getNombrearea());
                        	 //System.out.println(j.getFicha());
                        	 //System.out.println(j.getNombre());
                        	 //System.out.println(dato);
	                		 seguir.setArea(String.valueOf(j.getNcodarea()));
	                    	 seguir.setNombreTrabajador(j.getNombrearea());
	                    	 seguir.setFicha(String.valueOf(j.getFicha()));
	                    	 seguir.setCodArea(j.getNombre());
	                    	 seguir.setEstado(this.estadoSeleccionado); 
	                    	 this.seleccionados.add(seguir);
	                         this.personaSeleccionada = ""; 
                        	 }
                                                  	
                	 	}
                	   
                	 }
                
              }
             
            	               	 
             
             }
        	
        	
        }
        
    }
    
    
    public String buscarNombre(String codigo) {
        String cod = null, nom = null;
        for (SelectItem p : this.itemsPersonalRepresentantes) {
            cod = String.valueOf(p.getValue());
            if (codigo.equals(cod)) {
                nom = String.valueOf(p.getLabel());
                break;
            }

        }
        return nom;
    }
    
    public String buscarNombreA(String codigo) {
        String cod = null, nom = null;
        for (SelectItem p : this.itemsPersonalRepresentantesA) {
            cod = String.valueOf(p.getValue());
            if (codigo.equals(cod)) {
                nom = String.valueOf(p.getLabel());
                break;
            }

        }
        return nom;
    }

    
    public void pasarItemsA(ActionEvent actionEvent) {
    	
 	   Seguir seguir = new Seguir();    	   		 
 	  
	        AreaBean area = new AreaBean();
	        // llenar el objeto seguir....
	        String nombre = this.buscarNombreA(this.derivadoA);
	        //this.derivadoAA
	        //System.out.println("Pinta Nombre");
	        //System.out.println(nombre);
	        area.setAbreviatura(nombre);// solo p visualizar nombre
	        area.setTipo(this.estadoSeleccionado);
	        area.setNombre(this.dirigidoA);
	        area.setCodigo(Integer.parseInt(this.dirigidoA));
	        //System.out.println("Pinta Nombre Actualizar");
	        //System.out.println(nombre);
	        //System.out.println(this.estadoSeleccionado);
	        //System.out.println(this.dirigidoA);
	        //System.out.println(this.derivadoA);
	        // area.setCodigo(Integer.parseInt(this.vdirigidosA));
	        SelectItem item = null;
	        if (this.estadoSeleccionado != "") {
	            // adiconando el objeto en la tabla
	            boolean ok = false;
	            ok = this.buscarA(area, this.areasseleccionados); // cambiar
	            int valor = this.items1.size();
	            //System.out.println("pasarItemsA");
	            //System.out.println(valor);
	            // algoritmo
	            if (!ok) {            	
	                for (int i = 0; i < this.items1.size(); i++) {
	                    item = (SelectItem) this.items1.get(i);
	                    String areas = item.getValue().toString();
	                    if (areas.equals(this.derivadoAA)) {
	                        //area.setCentro(this.dirigidoA);// contiene a los codigos  eli comenta   
	                        area.setCentro(this.derivadoAA);//
	                        area.setNombre(item.getLabel());                        
	                        area.setCodigo(Integer.parseInt(this.derivadoAA));
	                        area.setFicha(Integer.parseInt(this.derivadoA));                        
	                        //System.out.println("Eli pinta las areas seleccionadas");
	                        //System.out.println(this.dirigidoA);
	                        //System.out.println(this.derivadoAA);
	                        //System.out.println(this.derivadoA);
	                        //System.out.println(area);                        
	                        this.areasseleccionados.add(area);
	                        this.personaSeleccionadaA = "";
	                        this.itemsPersonalRepresentantesA.clear();
	                        // seguir.setNombreTrabajador(item.getLabel());
	                        // this.seleccionados.add(seguir);
	                    }
	                }
	            }
	        } else {
	            this.error = "Seleccione un tipo Destinatario!!";
	            this.ver = true;
	
	        }
 	
 }
    

    public void quitarItems(ActionEvent actionEvent) {
        // quita elemento seleccionado de la tabla
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
        }

    }

    public void quitarItemsA(ActionEvent actionEvent) {
        // quita elemento seleccionado de la tabla
        AreaBean area = null;
        for (AreaBean p : this.areasseleccionados) {
            if (p.isSelected()) {
                area = p;
                quitarareas += String.valueOf(area.getCodigo()) + '-';
                quitarfichas += String.valueOf(area.getFicha()) + '-';
                // areasTemporal+=p;
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
            if (p.getCodArea().equals(seguir.getCodArea())
                    && p.getFicha().equals(seguir.getFicha())) {
                ok = true;
            }
        }
        return ok;
    }

    public boolean buscarA(AreaBean area, List<AreaBean> list) {
        boolean ok = false;
        for (AreaBean p : list) {
            if (p.getTipo().equals(area.getTipo())
                    && p.getNombre().equals(area.getNombre())) {
                ok = true;
            }
        }
        return ok;
    }

    public void confirmaBorrado(ActionEvent actionEvent) {
        DocumentoSalidaBean p = null;
        for (DocumentoSalidaBean q : this.documento) {
            if (q.isSelected()) {
                p = q;
            }
        }
        if (p == null) {
            this.error = "Debe seleccionar por lo menos un registro";
            this.ver = true;
        } else {
            // /acediendo a sesion http
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
            String area = "";
            for (DocumentoSalidaBean d : selectedDocumentoss) {
                codigo = String.valueOf(d.getCodigo());
                anno = String.valueOf(d.getAno());
                origen = d.getOrigenes();
                tipodoc = d.getNtipodoc();
                area = String.valueOf(d.getArea());
                // le paso como parametro al stored
                serviciosDocumentoSalida.eliminarDocumentoSalida(codigo, anno,
                        origen, tipodoc, area, login);
            }
            this.error = "Registro Eliminado!";
            selectedDocumentoss.clear();
            this.documento = serviciosDocumentoSalida.catalogo(area);// actualiza
            // el
            // reporte
            this.ver = true;
            this.verAlertaConfirmacionBorrar = false;
        }
    }

    public void cancelaBorrado(ActionEvent actionEvent) {    	
        this.verAlertaConfirmacionBorrar = false;
       
    }
    
    public void cancelaGrabar(ActionEvent actionEvent) {
        this.verAlertaConfirmacionGrabar = false;        
        //this.ver = true;
        //this.verCatalogo = false;
        //this.verActualizar = true;
    }
    
    public void eventAnexo(ActionEvent event) {
    	// falta desarrollar EDH 31.05.2023
    }

    public void eventEliminar(ActionEvent event) {

        // se adiciona estas dos lineas
        this.msg = "Realmente desea eliminarlo?";
        this.verAlertaConfirmacionBorrar = true;
        /*
         * if (this.selectedDocumentoss.size() < 1) { this.error =
         * "Debe seleccionar por lo menos un Documento"; this.ver = true; } else
         * { // /acediendo a sesion http HttpSession session = (HttpSession)
         * FacesContext .getCurrentInstance().getExternalContext()
         * .getSession(false); // ///guardando en sesion un objeto String login
         * = ""; Usuario usuario = null; usuario = (Usuario)
         * session.getAttribute("sUsuario"); login = usuario.getLogin(); //
         * llamamos a servicios o dao pasandole los datos a eliminar String
         * codigo = ""; String anno = ""; String origen = ""; String tipodoc =
         * ""; String area = ""; for (DocumentoSalidaBean d :
         * selectedDocumentoss) { codigo = String.valueOf(d.getCodigo()); anno =
         * String.valueOf(d.getAno()); origen = d.getOrigenes(); tipodoc =
         * d.getNtipodoc(); area = String.valueOf(d.getArea()); // le paso como
         * parametro al stored
         * serviciosDocumentoSalida.eliminarDocumentoSalida(codigo, anno,
         * origen, tipodoc, area, login); } this.error = "Registro Eliminado!";
         * selectedDocumentoss.clear(); this.documento =
         * serviciosDocumentoSalida.catalogo(area);// actualiza // el // reporte
         * this.ver = true; }
         */

    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        if (this.selectedDocumentoss.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;
            // /acediendo a sesion http
            HttpSession session = (HttpSession) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getSession(false);
            // ///guardando en sesion un objeto
            int perfil = 0;
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            perfil = usuario.getNcodperfil();
            
            if (perfil == 7 || perfil == 8) {
                this.documento = serviciosDocumentoSalida.catalogo(area);            
                List<TiposBean> mantenimiento = documentoSalidaDAO.estadoderivador();
                List itemsMantenimiento = Utils.getTipos(mantenimiento);
                this.items9 = itemsMantenimiento;
                
                //this.verEliminar=true;
                            

            } else if (perfil == 10) {
                this.documento = serviciosDocumentoSalida.catalogo(area);
                List<TiposBean> mantenimiento = documentoSalidaDAO.estadojefe();
                List itemsMantenimiento = Utils.getTipos(mantenimiento);
                this.items9 = itemsMantenimiento;
                
                //this.verEliminar=false;

            }  else {
            	 this.documento = serviciosDocumentoSalida.catalogo(area);
                 List<TiposBean> mantenimiento = documentoSalidaDAO.estadomantenimiento();
                 List itemsMantenimiento = Utils.getTipos(mantenimiento);
                 this.items9 = itemsMantenimiento;
            }
           


            for (DocumentoSalidaBean d : selectedDocumentoss) {
                this.codigoA = d.getCodigo();
                this.anoA = d.getAno();
                //this.anoA = Integer.parseInt(d.getAnio());                
                this.numerodocA = d.getNumerodoc();
                this.fechaA = d.getFecha();
                this.origenesA = d.getOrigenes();
                this.origenA = d.getOrigen();
                this.nom_areaA = d.getNom_area();
                this.tipodocA = d.getTipodoc();
                this.ntipodocA = d.getNtipodoc();
                this.asuntoA = d.getAsunto();
                this.estadoA = d.getEstado();
                this.trabajadorA = d.getTrabajador();
                this.observacionA = d.getObservacion();
                this.dirigidoA = String.valueOf(d.getNdirigido());
                this.vdirigidosA = d.getVdirigidos();
                this.referenciaA = d.getReferencia();
                //this.doc_entradaA = d.getDoc_entrada();                
                this.doc_entradaA = d.getDoc_entrada().substring(0, d.getDoc_entrada().indexOf("-"));
                System.out.println("QUIERO VER LA INFORMACION DE DOCUMENTO DE ENTRADA DIRIGIDO");
                System.out.println(this.doc_entradaA);
                System.out.println(this.dirigidoA);
                this.vdirigidosA = d.getVdirigidos();
                System.out.println("VIENDO DEL AREA DIRIGIDO ACTUAL");
                System.out.println(area_remite);
                
                this.areaA = d.getArea();
                this.prioridadA = d.getPrioridad();
                this.ndirigidoA = d.getNdirigido();
                this.nestadoA = d.getNestado();
                this.ficha_dirigidoA = d.getFicha_dirigido();
                this.nficha_jefe_equipoA = d.getNficha_jefe_equipo();
                this.ficha_remiteA = d.getFicha_remitente();
                this.ubicararchivoA = d.getUbiarchivo();
                this.dfeccreA = d.getDfeccre();
                this.dfecactA = d.getDfecact();
                this.vrescreA = d.getVrescre();
                this.vresactA = d.getVresact();
                this.remitenteA = d.getRemitente();
                this.dfecplazoA = d.getDfecplazo();
                this.diasA = d.getDias();
                this.vaccionA = d.getVaccion();
                this.vcodigo_verificadorA = d.getVcodigo_verificador();
                this.opcion_seguimiento = d.getOpcion_seguimiento();
                this.anodocumentoentrA = d.getDoc_entrada().substring(d.getDoc_entrada().indexOf("-") + 1);
                this.notificadorA = d.getVnotificador();
                //System.out.println("QUIERO VER EL NOTIFICADOR");
                //System.out.println(this.notificadorA);
                
                //|| this.notificadorA.equals("1"))
                if (this.notificadorA.equals("0"))  {
                	this.disnotificador = false;
                } else  {
                	this.disnotificador = true;
                }
                
                if (this.anodocumentoentrA.equals("")){
                	this.anodocumentoentrA="0";
                }
                
                
                
                
               


                //this.anodocumentoentrA = d.getVanoentrada().indexOf("-")+ d.getVanoentrada();
                
                if (this.opcion_seguimiento.equals("0")) {
                    this.opcion_seguimiento = "1";
                }
                
                // this.indicaadjuntoA = d.getIndicaadjunto();
                this.indicaadjuntoA = d.getIndicador();
                this.textoA = "El Registro tiene " + this.indicaadjuntoA
                        + " Archivo Adjunto";
                // int li_opcion= Integer.parseInt(this.opcion_seguimiento);

                // if (this.origenA == 1) {
                // List<AreaBean> areas = documentoSalidaDAO
                // .areas_seleccionadas(this.anoA, this.origenA,
                // this.ntipodocA, this.areaA, this.codigoA);

                // this.items7b = Utils.getAreasLink(areas);
                // for (AreaBean p : areas) {
                // this.items7a.remove(p.getNombre());
                // }
                // } else {
                // List<RemitenteBean> remitente = documentoSalidaDAO
                // .remitentes_seleccionadas(this.anoA, this.origenA,
                // this.ntipodocA, this.areaA, this.codigoA);
                // A�ade Eli Diaz
                // this.areas = areas;
                // this.areasseleccionados=areas;
                // this.items7b = Utils.getRemitenteLink(remitente);
                // for (RemitenteBean p: remitente)
                // {
                // this.items7a.remove(p.getNombre());
                // }
                // }
                // ***A�ADE EDH
                List<AreaBean> areas = documentoSalidaDAO.areas_seleccionadas(
                        this.anoA, this.origenA, this.ntipodocA, this.areaA,
                        this.codigoA);
                // Add CF 05/05/2011
                List<AreaBean> area_derivados = documentoSalidaDAO.areas();
                this.items7a = Utils.getAreasLink(area_derivados);
                // A�ade Eli Diaz
                this.areas = areas;
                this.areasseleccionados = areas;
                this.areas_actuales = this.items7b.size();

                /* ELI COMENTA ESTE CODGIO 30/10/2012*/
                /*
                if (this.origenA == 1)// interno
                {

                    System.out.println("Interno");
                    List<AreaBean> areasx = documentoSalidaDAO.areas();
                    List itemsAreas = Utils.getAreas(areasx);
                    // this.items7a = Utils.getAreasLink(areas);
                    this.items1 = itemsAreas;

                    List<TrabajadorBean> trabajador = documentoSalidaDAO
                            .trabajador(this.ndirigidoA);
                    System.out.println(this.ndirigidoA);
                    List itemsTrabajador = Utils.getTrabajador(trabajador);
                    // this.items3 = itemsTrabajador;

                    List<JefeBean> bean = this.entranteDAO.jefe(area_remite);
                    int ficha = 0;
                    for (JefeBean e : bean) {
                        ficha = e.getFicha();
                    }
                    
                    this.derivadoA = String.valueOf(ficha);

                    this.itemsPersonalRepresentantes = itemsTrabajador;

                } else {

                    List<RemitenteBean> remitente = documentoSalidaDAO
                            .remitentes();
                    List itemsAreas = Utils.getRemitentes(remitente);
                    // this.items7a = Utils.getRemitenteLink(remitente);
                    this.items1 = itemsAreas;

                    // ADD CF Ago 2011
                    // this.itemsPersonalRepresentantes = itemsTrabajador;
                    List<RepresentanteBean> representante = documentoSalidaDAO
                            .representante(this.ndirigidoA);
                    List itemsTrabajador = Utils
                            .getRepresentante(representante);
                    this.itemsPersonalRepresentantes = itemsTrabajador;

                }
                */

            }
            /*
             * String codigo_accion= this.vaccionA; String tempo = null; int
             * z=0, j= 0; while( z < codigo_accion.length()) { tempo=
             * codigo_accion.substring(z ,z+2); //asigna al arreglo
             * selectedItems1.add(tempo); j++; z+=2; }
             */
            selectedDocumentoss.clear();
            this.error = "Se grabó satisfactoriamente";
            // /acediendo a sesion http
            // HttpSession session = (HttpSession) FacesContext
            // 		.getCurrentInstance().getExternalContext()
            // 		.getSession(false);
            // ///guardando en sesion un objeto
            String area = "";
            // Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            area = String.valueOf(usuario.getCodarea());
            this.documento = serviciosDocumentoSalida.catalogo(area);// actualiza
            // el
            // reporte
        } else {
            this.error = "Debe seleccionar solo un documento";
            this.ver = true;

        }
        this.validaNotificacionEmail();
    }
    //SED-REQ-00001
    private void validaNotificacionEmail() {
    	System.out.println("origen seleccinado");
    	System.out.println(origenA);
    	
    	if (this.origenA==2) {
    		this.notificadorA = "2";
    		this.verNotificadorSI=true;
    	} else {
        	this.notificadorA = "0";
        	this.verNotificadorSI=false;
    	}
    }
    
    
    public void verRemitente(ActionEvent event) {

        // PageContentBean pageContentBean = new PageContentBean();
        // pageContentBean.setMenuContentInclusionFile("./content/MAN/remitente.jspx");
        // branchObject.setMenuContentInclusionFile("./content/MAN/remitente.jspx");
        // pageContentBean.setMenuContentTitle("Argentina");
        // navigation.setSelectedPanel(pageContentBean);
    	if (this.origenesN.equals("2")) {
    		 this.editarEmpresa = false;
    	        this.empresa = "";
    	} else {
    		 this.error = "Debe seleccionar origen externo";
             this.ver = true;
    	}
       

    }

    public void eventDetalles(ActionEvent evt) {
        // this.limpiarDetalles();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        /*
         * List<SecuencialBean> bean =
         * this.secuencialDAO.correlativo(this.tipodocN); SecuencialBean
         * secuencialBean = bean.get(0); int contador =
         * secuencialBean.getCorrelativo(); contador++; String secuencia =
         * String.valueOf(contador);
         */
        // String contador = this.secuencialDAO.actualizasecuencial();
        System.out.println("Secuencial Eli");
        // System.out.println(contador);
        // session.setAttribute("nombrePdf", contador);
        session.setAttribute("tipodoc", this.tipodocN);
        session.setAttribute("indicador", 2);
        ind_adjuntar = 1;
        this.verDetalles = true;
        // for (DocumentoSalidaBean d : selectedDocumentoss) {
        //	
        // }
        selectedDocumentoss.clear();
        // this.productos = serviciosProducto.catalogo();// actualiza el
        // reporte

    }
    
    /* Evento visualizar PDF*/
    public void evenVerPDF(ActionEvent evt) {
    	if (this.selectedDocumentoss.size() == 1) {
    		for (DocumentoSalidaBean d : selectedDocumentoss) {
            this.rutaPDFPopup  = d.getUbicacion_salida();
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
        System.out.println("Tipo doc:" + this.ntipodocA);
        
        session.setAttribute("tipodoc", this.ntipodocA);
        session.setAttribute("indicador", 2);
        ind_adjuntar = 1;
        this.verDetalles = true;
       
        selectedDocumentoss.clear();
       

    }

    public void eventEntrada(ActionEvent evt) {
        this.verCatalogoEntrada = true;
        // for (EntranteBean p : selectedEntrantes) {
        // }

    }

    public void eventSeleccionarTrabajador(ActionEvent event) {

        if (cont == 0) {
            area_dirigido = area + area_dirigido;
            cont = 1;
        } else {
            area_dirigido = area + area_dirigido;
        }
        // area_dirigido.split("-");
        // System.out.println(area_dirigido);
        // System.out.println(area_dirigido);
        areaseleccionadaN = area_dirigido;
        // this.dirigidoN=area_dirigido;

    }

    public void eventSeguimiento(ActionEvent event) {
        // this.limpiar();
        // this.verNuevo = true;
        // this.verCatalogo = false;
        // System.out.println("ESTOY AQUI");
    }
    public void eventActualizarDocumentosLocal(ActionEvent event) {
    	System.out.println("ok");
    	validaInfoBPM_Actualizar();
    }
    public void eventActualizarDocumentos(ActionEvent event) {
        HttpSession session = null;
        boolean formulario = true;
        boolean resultemial = false;
        try {
            // llama DAO/Stored Para actualizar producto
            this.verActualizar = false;
            DocumentoSalidaBean documentosalidaBean = new DocumentoSalidaBean();
            // /acediendo a sesion http
            session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            System.out.println("Eli quiere ver el seguimiento");
            System.out.println(this.opcion_seguimiento);
           	                
	            String valida2 = null;
	            valida2 = this.validaFormulario2();
	            if (valida2.equals("ok")) {
	                formulario = true;
	                // ///guardando en sesion un objeto
	                Usuario usuario = null;
	                String file = null;
	                usuario = (Usuario) session.getAttribute("sUsuario");
	                this.areaA = usuario.getCodarea();
	                this.vresactA = usuario.getLogin();
	                this.nombreremitenteA = usuario.getNomequipo();
	                String area = String.valueOf(usuario.getCodarea());
	                // controlando el adjuntar
	                if (ind_adjuntar == 0) {
	                    this.ubicararchivoA = "NADA";
	                    this.indicaadjuntoA=0;
	                } else {
	                    //if (this.indicaadjuntoA == 0) {
	                    file = (String) session.getAttribute("file");
	                    if (file != null) {
	                        nombre_archivo = file;
	                        List<ServidorBean> bean = this.documentoSalidaDAO.servidor();
	                        for (ServidorBean e : bean) {
	                            ubicacion = e.getDescripcion();
	                            // ubicacion = "http://1.1.194.53/salida/"; modificado en
	                            // parametros
	                        }
	                        Date date = new Date();
	                        String annio = Integer.toString(c.get(Calendar.YEAR));
	                        nombre_archivo = annio + nombre_archivo;
	                        //this.ubicararchivoA = ubicacion + nombre_archivo;
	                        // nuevo parametro para archivos PDF 21/11/2019
	                        this.ubicararchivoA = nombre_archivo;
	                        this.indicaadjuntoA=1;
	                        this.link_acceso= ubicacion +nombre_archivo;
	                    }
	
	                }
	               
	                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	                documentosalidaBean.setAno(this.anoA);                
	                documentosalidaBean.setOrigenes(this.origenesA);
	                documentosalidaBean.setNumerodoc(this.numerodocA);
	                documentosalidaBean.setNtipodoc(this.ntipodocA);
	                documentosalidaBean.setArea(this.areaA);
	                documentosalidaBean.setCodigo(this.codigoA);
	                documentosalidaBean.setAsunto(this.asuntoA);
	                documentosalidaBean.setReferencia(this.referenciaA);
	                documentosalidaBean.setObservacion(this.observacionA);
	                documentosalidaBean.setPrioridad(this.prioridadA);
	                documentosalidaBean.setNestado(this.nestadoA);
	                documentosalidaBean.setVresact(this.vresactA);
	                documentosalidaBean.setDoc_entrada(this.doc_entradaA);
	                documentosalidaBean.setFecha(this.fechaA);
	                documentosalidaBean.setNdirigido(this.ndirigidoA);
	                //documentosalidaBean.setNdirigido(Integer.parseInt(generaCodigos().substring(generaCodigos().indexOf("-"+1))));
	                documentosalidaBean.setVdirigidos(this.vdirigidosA);
	                documentosalidaBean.setFicha_dirigido(this.ficha_dirigidoA);
	                documentosalidaBean.setUbiarchivo(this.ubicararchivoA);
	                documentosalidaBean.setFicha_remitente(this.ficha_remiteA);
	                documentosalidaBean.setDfecplazo(this.getDfecplazoA());
	                documentosalidaBean.setTipodoc(this.tipodocA);
	                documentosalidaBean.setEstado(this.estadoA);
	                documentosalidaBean.setDirigido(this.dirigidoAA);
	                documentosalidaBean.setOrigen(this.origenA);
	                documentosalidaBean.setRemitente(this.remitenteA);
	                documentosalidaBean.setDias(this.diasA);
	                documentosalidaBean.setVaccion(this.vaccionA);
	                documentosalidaBean.setOpcion_seguimiento(this.opcion_seguimiento);
	                //documentosalidaBean.setAreaseleccionadas(this.aresseleccionasA);
	                documentosalidaBean.setAreaseleccionadas(this.generaAreasA());
	                
	                //documentosalidaBean.setIndica(this.generaIndicadorA());
	                documentosalidaBean.setIndicadores(this.generaIndicadorA());
	                documentosalidaBean.setQuitarareas(this.quitarareas);
	                documentosalidaBean.setQuitarfichas(this.quitarfichas);
	                documentosalidaBean.setVanoentrada(this.anodocumentoentrA);
	                documentosalidaBean.setNombreremitente(this.nombreremitenteA);
	                documentosalidaBean.setCodigos(this.generaCodigosA());
	                documentosalidaBean.setVnotificador(this.notificadorA);
	                System.out.println("Eli actualizar...Quiere ver el adjunto");
	                System.out.println(indicaadjuntoA);
	                System.out.println(this.nestadoA);
	                System.out.println(this.estadoA);
	                
	                if (this.getUbicararchivoA().equals("NADA")) {
	                	this.indicaadjuntoA =0;
	                }
	                
	                System.out.println("Viendo el indicador adjunto : " + this.indicaadjuntoA);
	                System.out.println("QUIERE VER LA PRIMER AREA DE CORREO CHECK SEGUMIENTO : " + this.dirigidoA);
	                
	                
	                if (opcion_seguimiento.equals("2")){
                    	
	                	
	                	if (this.nestadoA.equals("ESTA002") ||this.nestadoA.equals("ESTA003")){
	                		
	                		if ( this.indicaadjuntoA == 1){
	                		
	                			Map outMap = serviciosDocumentoSalida.ActualizarDocumentosSalida(documentosalidaBean);
			                	String out = (String) outMap.get("out");
			                	String correlativo = (String) outMap.get("outcorrelativo");
			                	out = out.trim();                
			                	System.out.println("OUT STORED!!!:" + out);			                	
		                
				                if (out.equals("0")) {
				                	
				                	validaInfoBPM_Actualizar();
					                List<SecretariaBean> bean = documentoSalidaDAO.secretaria(Integer.parseInt(this.dirigidoA));
					                this.li_cantidad_secretaria = bean.size();
					                System.out.println("Estoy viendo la cantidad de secretaria : " +li_cantidad_secretaria);
					                		
					                for (SecretariaBean e : bean) {
				                    	vficha_secretaria = String.valueOf(e.getFicha());
				                    	li_ficha_secretaria = e.getFicha();
				                    	vnombre_secretaria= e.getVnombre();
				                    	correo = e.getVcorreo();
				                    	vestado_secretaria = e.getVcodestado();				                    	
				                    }
				                    
				                   
				
				                    this.documento = serviciosDocumentoSalida.catalogo(area);			                   
				                    this.error = "Se actualizaron los campos correctamente";
				                    this.ver = true;
				                    this.verCatalogo = true;
				                    this.verActualizar = false;
				                    ind_adjuntar = 0;
				                    this.valores = "";
				                    this.quitarareas = "";
				                    this.quitarfichas = "";
				                    this.areas.clear();
				                    this.indicaadjuntoA=0;
				                   
				                    
				                  //INICIO ENVIO DE CORREO ELI DIAZ HORNA 21/09/2022
				                    System.out.println("QUIERO VER LOS DATOS DEL CORREO NOTIFICACION Y ORIGENES SEGUMIENTO = 2");
				                    System.out.println(this.notificadorA);
				                    System.out.println(this.origenesA);
				                    System.out.println(this.origenA);
				                    //INTERNO
				                    
				                   if (this.li_cantidad_secretaria != 0){
				                    
				                    if (this.notificadorA.equals("2") && this.origenesA.equals("INTERNO")){
				                		
				                		String txtMensaje = "";
				    					String txtAsunto  = "";
				                		
				    					txtAsunto = "Notificación de Documento Salida, Nro Doc.: " + this.numerodocA + " Tramite Documentario";
				    	                txtMensaje = txtMensaje + txtAsunto + "\n\n";
				    	                txtMensaje = txtAsunto + "\n\n";
				    	                txtMensaje = txtMensaje + "Estimada(o) Colaborador(a) :" + vnombre_secretaria + " \n";
				    	                txtMensaje = txtMensaje + "Le comunicamos que ha recibido un documento de caracter interno, que se encuentra en su bandeja de entrada opcion seguimiento en el Sistema Tramite Documentario." + "\n\n";				    	                	                
				    	                txtMensaje = txtMensaje + "Numero Documento : " + this.numerodocA + "\n\n";
				    	                txtMensaje = txtMensaje + "Numero Registro : " + this.doc_entradaA + "\n\n";
				    	                txtMensaje = txtMensaje + "Tipo Documento : " + this.tipodocA + "\n\n";				    	                
				    	                txtMensaje = txtMensaje + "Area Remitente : " + this.nombreremitenteA + "\n\n";
				    	                txtMensaje = txtMensaje + "Asunto : " 	+ this.asuntoA + "\n\n";
				    	                txtMensaje = txtMensaje + "Fecha Documento : " 	+ sdf.format(this.fechaA) + "\n\n";	               
				    	                txtMensaje = txtMensaje + "Referencia : " + this.referenciaA  + "\n\n";
				    	                txtMensaje = txtMensaje + "Visualice el documento en el siguiente link  : " + this.link_acceso  + "\n\n";
				    	                //txtMensaje = txtMensaje + "Favor de atenderlo a la brevedad posible.\n\n";
				    	                txtMensaje = txtMensaje + "Atentamente,\n\n";
				    	                txtMensaje = txtMensaje + "Sistema Tramite Documentario \n";
				    	                txtMensaje = txtMensaje + "Por favor no responder este Email";
				    	                
				    	                	                	                	                
				    	                System.out.println("Pinto las variales del Email modulo de salida 1");
				    	                System.out.println(this.ndirigidoA);
				    	                System.out.println(txtMensaje);
				    	                System.out.println(txtAsunto);
				    	                
				    	                /*
				    	                 * ELI COMENTA ENVIO DE CORREO ORIGINAL 18/OCT/2022
				    	                Boolean envioCorreo = serviciosUsuarios.enviarEmailByFichaUsu(li_ficha_secretaria,txtMensaje, txtAsunto);
				    	               	                					
				    	                
				    	                if (envioCorreo) {
				    	                    mailService
				    	                            .enviarMail("El area del usuario 1 "
				    	                            + vficha_secretaria
				    	                                                     
				    	                            + " recibio un documento\n"
				    	                            + "de importancia URGENTE y este ha sido notificado");
				    	                } else {
				    	                    mailService
				    	                            .enviarMail("El area del usuario 2 "
				    	                            + vficha_secretaria
				    	                            
				    	                            + " recibio un documento\n"
				    	                            + "de importancia URGENTE y pero este, al no contar con un correo registrado en la tabla de Trabajadores, no pudo ser notificado.");
				    	                }
				                    	*/
				    	               
				                    	mailService.enviarMail(txtMensaje, txtAsunto, correo, null);
				    	                resultemial = true;	                	                	                
				    	                /*
				    	                if (resultemial = true){
				    	                	
				    	                	this.error = "Se envio correctamente el email";
				    	        			this.ver = true;
				    	                	
				    	                } else {
				    	                	this.error = "No se ha realizado el envio del email";
				    	        			this.ver = true;
				    	                }
				    	                */
				                		
				                	} else {
				    	                formulario = false;
				    	                this.ver = true;
					                    this.verCatalogo = true;
					                    this.verActualizar = false;
				    	            }
				                    
				                }
				                
				                  //FIN ENVIO DE CORREO ELI DIAZ HORNA 21/09/2022
				                    
					             } else {
					                    this.error = "No Se grabó por inconsistencia de la información 1 !";
					                    System.out.println("No Se grabó por inconsistencia de la información 1");
					                    this.ver = true;
					                    this.verCatalogo = true;
					                    this.verActualizar = false;
					             } 
	                			
	                			
	                					
	                		
	                		} else {
	                			
	                			 formulario = false;
				                 this.error = "Recuerda que es obligatorio adjuntar un archivo, para poder cambiar de estado al documento de salida ! ";                
				                 this.ver = true;
				                 this.verActualizar = true;
				                 this.indicaadjuntoA=0;
	                			
	                		}
	                		
	                	} else if (this.nestadoA.equals("ESTA001") ){
	                		
						                		Map outMap = serviciosDocumentoSalida.ActualizarDocumentosSalida(documentosalidaBean);
							                	String out = (String) outMap.get("out");
							                	String correlativo = (String) outMap.get("outcorrelativo");
							                	out = out.trim();                
							                	System.out.println("OUT STORED!!!:" + out);			                	
						                
								                if (out.equals("0")) {
								                   
								                	
								                    this.documento = serviciosDocumentoSalida.catalogo(area);			                   
								                    this.error = "Se actualizaron los campos correctamente";
								                    this.ver = true;
								                    this.verCatalogo = true;
								                    this.verActualizar = false;
								                    ind_adjuntar = 0;
								                    this.valores = "";
								                    this.quitarareas = "";
								                    this.quitarfichas = "";
								                    this.areas.clear();
									                
								                } else {
									                    this.error = "No Se grabó por inconsistencia de la información 2 !";
									                    System.out.println("No Se grabó por inconsistencia de la información 2");
									                    this.ver = true;
									                    this.verCatalogo = true;
									                    this.verActualizar = false;
									            } 
	                		
	                	}
	                		
	                	                    	
		                
                    } else if(opcion_seguimiento.equals("1") || opcion_seguimiento.equals("3")) {
                    	
                    	if (this.nestadoA.equals("ESTA002") || this.nestadoA.equals("ESTA003")){
	                		
	                		if ( this.indicaadjuntoA == 1){
                    		
                    			                						
	                			        	Map outMap = serviciosDocumentoSalida.ActualizarDocumentosSalida(documentosalidaBean);
						                	String out = (String) outMap.get("out");
						                	String correlativo = (String) outMap.get("outcorrelativo");
						                	out = out.trim();                
						                	System.out.println("OUT STORED!!!:" + out);			                	
					                
							                if (out.equals("0")) {
							                	
							                	
							                	List<SecretariaBean> bean = documentoSalidaDAO.secretaria(Integer.parseInt(this.dirigidoA));
							                	this.li_cantidad_secretaria = bean.size();
								                System.out.println("Estoy viendo la cantidad de secretaria opcion 1 y 3 : " +li_cantidad_secretaria);
								                
							                	for (SecretariaBean e : bean) {
							                    	vficha_secretaria = String.valueOf(e.getFicha());
							                    	li_ficha_secretaria = e.getFicha();
							                    	vnombre_secretaria= e.getVnombre();
							                    	correo = e.getVcorreo();
							                    	vestado_secretaria = e.getVcodestado();
							                    	
							                    }
							                   
							
							                    this.documento = serviciosDocumentoSalida.catalogo(area);			                   
							                    this.error = "Se actualizaron los campos correctamente";
							                    this.ver = true;
							                    this.verCatalogo = true;
							                    this.verActualizar = false;
							                    ind_adjuntar = 0;
							                    this.valores = "";
							                    this.quitarareas = "";
							                    this.quitarfichas = "";
							                    this.areas.clear();
							                    this.indicaadjuntoA=0;
							                       
							                    
							                    
							                  //INICIO ENVIO DE CORREO ELI DIAZ HORNA 21/09/2022
							                    System.out.println("QUIERO VER LOS DATOS DEL CORREO NOTIFICACION Y ORIGENES SEGUIMIENTO 1 Y 3");
							                    System.out.println(this.notificadorA);
							                    System.out.println(this.origenesA);
							                    System.out.println(this.origenA);
							                    
							                    System.out.println("QUIERE VER LA PRIMER AREA DE CORREO CHECK ENTRADA: " + this.dirigidoA);
							                    
							                  if (this.li_cantidad_secretaria != 0){
							                    
							                    if (this.notificadorA.equals("2") && this.origenesA.equals("INTERNO")){
							                		
							                		String txtMensaje = "";
							    					String txtAsunto  = "";
							    																			
							                		
							                		txtAsunto = "Notificación de Documento Salida, Nro Doc.: " + this.numerodocA + " Tramite Documentario";
							    	                txtMensaje = txtMensaje + txtAsunto + "\n\n";
							    	                txtMensaje = txtAsunto + "\n\n";
							    	                txtMensaje = txtMensaje + "Estimada(o) Colaborador(a) :" + vnombre_secretaria + " \n";
							    	                txtMensaje = txtMensaje + "Le comunicamos que ha recibido un documento de caracter interno, que se encuentra en su bandeja de entrada en el Sistema Tramite Documentario." + "\n\n";				    	                	                
							    	                txtMensaje = txtMensaje + "Numero Documento : " + this.numerodocA + "\n\n";
							    	                //txtMensaje = txtMensaje + "Numero Registro : " + correlativo + "\n\n";	
							    	                txtMensaje = txtMensaje + "Tipo Documento : " + this.tipodocA + "\n\n";
							    	                txtMensaje = txtMensaje + "Area Remitente : " + this.nombreremitenteA + "\n\n";
							    	                txtMensaje = txtMensaje + "Asunto : " 	+ this.asuntoA + "\n\n";
							    	                txtMensaje = txtMensaje + "Fecha Documento : " 	+ sdf.format(this.fechaA) + "\n\n";	               
							    	                txtMensaje = txtMensaje + "Referencia : " + this.referenciaA  + "\n\n";
							    	                txtMensaje = txtMensaje + "Visualice el documento en el siguiente link  : " + this.link_acceso  + "\n\n";
							    	                //txtMensaje = txtMensaje + "Favor de atenderlo a la brevedad posible.\n\n";
							    	                txtMensaje = txtMensaje + "Atentamente,\n\n";
							    	                txtMensaje = txtMensaje + "Sistema Tramite Documentario \n";
							    	                txtMensaje = txtMensaje + "Por favor no responder este Email";
							    	                
							    	                	                	                	                
							    	                System.out.println("Pinto las variales del Email modulo de salida 2");
							    	                System.out.println(this.ndirigidoA);
							    	                System.out.println(txtMensaje);
							    	                System.out.println(txtAsunto);
							    	                
							    	                /*
							    	                 * ELI COMENTA ENVIO DE CORREO ORIGINAL 18/OCT/2022
							    	                Boolean envioCorreo = serviciosUsuarios.enviarEmailByFichaUsu(li_ficha_secretaria,txtMensaje, txtAsunto);
							    	               	                					
							    	                
							    	                if (envioCorreo) {
							    	                    mailService
							    	                            .enviarMail("El area del usuario 3 "
							    	                            + vficha_secretaria
							    	                                                        
							    	                            + " recibio un documento\n"
							    	                            + "de importancia URGENTE y este ha sido notificado");
							    	                } else {
							    	                    mailService
							    	                            .enviarMail("El area del usuario 4 "
							    	                            + vficha_secretaria
							    	                            
							    	                            + " recibio un documento\n"
							    	                            + "de importancia URGENTE y pero este, al no contar con un correo registrado en la tabla de Trabajadores, no pudo ser notificado.");
							    	                }
							    	                
							    	                */
							    	                
							    	                mailService.enviarMail(txtMensaje, txtAsunto, correo, null);
							    	                resultemial = true;	                	                	                
							    	                /*
							    	                if (resultemial = true){
							    	                	
							    	                	this.error = "Se envio correctamente el email";
							    	        			this.ver = true;
							    	                	
							    	                } else {
							    	                	this.error = "No se ha realizado el envio del email";
							    	        			this.ver = true;
							    	                }
							    	                */
							                    
								                } else {
								                    this.error = "Se actualizaron los campos correctamente, No se envio Notificación";
								                    this.ver = true;
								                    this.verCatalogo = true;
								                    this.verActualizar = false;
								                }
							                    
							                  }
							                    
							                  //FIN ENVIO DE CORREO ELI DIAZ HORNA 21/09/2022
							                    
								                } else {
								                    this.error = "No Se grab� por inconsistencia de la información !";
								                    System.out.println("No Se grab� por inconsistencia de la información ");
								                    this.ver = true;
								                    this.verCatalogo = true;
								                    this.verActualizar = false;
								                    this.indicaadjuntoA=0;
								                } 
							             
	                		} else {
	                			
	                		     formulario = false;
				                 this.error = "Recuerda que es obligatorio adjuntar un archivo, para poder cambiar de estado al documento de salida ! ";                
				                 this.ver = true;
				                 this.verActualizar = true;
				                 this.indicaadjuntoA=0;
	                			
	                		}
	                		
	                	} else if (this.nestadoA.equals("ESTA001") ){
	                		
						                		Map outMap = serviciosDocumentoSalida.ActualizarDocumentosSalida(documentosalidaBean);
							                	String out = (String) outMap.get("out");
							                	String correlativo = (String) outMap.get("outcorrelativo");
							                	out = out.trim();                
							                	System.out.println("OUT STORED!!!:" + out);			                	
						                
								                if (out.equals("0")) {
								                   
								
								                    this.documento = serviciosDocumentoSalida.catalogo(area);			                   
								                    this.error = "Se actualizaron los campos correctamente";
								                    this.ver = true;
								                    this.verCatalogo = true;
								                    this.verActualizar = false;
								                    ind_adjuntar = 0;
								                    this.valores = "";
								                    this.quitarareas = "";
								                    this.quitarfichas = "";
								                    this.areas.clear();
								                    								                    
	                		
								                } else {
									                    this.error = "No Se grabó por inconsistencia de la información !";
									                    System.out.println("No Se grabó por inconsistencia de la información 5");
									                    this.ver = true;
									                    this.verCatalogo = true;
									                    this.verActualizar = false;
									             } 
                    	
	                } 
	                
	            }   else {
	                formulario = false;
	                this.error = "Visualizar Msg - 1";                
	                this.ver = true;
	                this.verActualizar = true;
	             }  
	           }  else {
	                formulario = false;
	                this.error = valida2;                
	                this.ver = true;
	                this.verActualizar = true;
	             }
	          
            
        } catch (Exception exception) {
            exception.printStackTrace();
            //logger.error("Fallo el registro", exception);
            this.error = "Transaccion No valida.";
            //this.error = "Se Grab� Satisfactoriamente";
            this.ver = true;
            this.verActualizar = true;
            this.verAlertaConfirmacionGrabar = false;
            this.notificacion=0;
        } finally {
            if (formulario) {
                ind_adjuntar = 0;
            }
            session.setAttribute("file", null);
        }
      
    }
    
    public void eventActualizarDocumentosSinNroEntrada(ActionEvent event) {
    	
    	System.out.println("Eli Estoy en el nuevo evento");
    	System.out.println("eventActualizarDocumentosSinNroEntrada");
    	
    	HttpSession session = null;
    	  session = (HttpSession) FacesContext.getCurrentInstance()
          .getExternalContext().getSession(false);
    	  
    	boolean formulario = true;
    	
    	 try {
        
		    	DocumentoSalidaBean documentosalidaBean = new DocumentoSalidaBean();
		    	
		        formulario = true;
		        // ///guardando en sesion un objeto
		        Usuario usuario = null;
		        String file = null;
		        usuario = (Usuario) session.getAttribute("sUsuario");
		        this.areaA = usuario.getCodarea();
		        this.vresactA = usuario.getLogin();
		        this.nombreremitenteA = usuario.getNomequipo();
		        String area = String.valueOf(usuario.getCodarea());
		        // controlando el adjuntar
		        /*
		        if (ind_adjuntar == 0) {
		            this.ubicararchivoA = "NADA";
		        } else {
		            //if (this.indicaadjuntoA == 0) {
		            file = (String) session.getAttribute("file");
		            if (file != null) {
		                nombre_archivo = file;
		                List<ServidorBean> bean = this.documentoSalidaDAO.servidor();
		                for (ServidorBean e : bean) {
		                    ubicacion = e.getDescripcion();
		                    // ubicacion = "http://1.1.194.53/salida/"; modificado en
		                    // parametros
		                }
		                Date date = new Date();
		                String annio = Integer.toString(c.get(Calendar.YEAR));
		                nombre_archivo = annio + nombre_archivo;
		                this.ubicararchivoA = ubicacion + nombre_archivo;
		            }
		
		        }
		        */
		       
		        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        
		        documentosalidaBean.setAno(this.anoA);                
		        documentosalidaBean.setOrigenes(this.origenesA);
		        documentosalidaBean.setNumerodoc(this.numerodocA);
		        documentosalidaBean.setNtipodoc(this.ntipodocA);
		        documentosalidaBean.setArea(this.areaA);
		        documentosalidaBean.setCodigo(this.codigoA);
		        documentosalidaBean.setAsunto(this.asuntoA);
		        documentosalidaBean.setReferencia(this.referenciaA);
		        documentosalidaBean.setObservacion(this.observacionA);
		        documentosalidaBean.setPrioridad(this.prioridadA);
		        documentosalidaBean.setNestado(this.nestadoA);
		        documentosalidaBean.setVresact(this.vresactA);
		        documentosalidaBean.setDoc_entrada(this.doc_entradaA);
		        documentosalidaBean.setFecha(this.fechaA);
		        documentosalidaBean.setNdirigido(this.ndirigidoA);
		        //documentosalidaBean.setNdirigido(Integer.parseInt(generaCodigos().substring(generaCodigos().indexOf("-"+1))));
		        documentosalidaBean.setVdirigidos(this.vdirigidosA);
		        documentosalidaBean.setFicha_dirigido(this.ficha_dirigidoA);
		        documentosalidaBean.setUbiarchivo(this.ubicararchivoA);
		        documentosalidaBean.setFicha_remitente(this.ficha_remiteA);
		        documentosalidaBean.setDfecplazo(this.getDfecplazoA());
		        documentosalidaBean.setTipodoc(this.tipodocA);
		        documentosalidaBean.setEstado(this.estadoA);
		        documentosalidaBean.setDirigido(this.dirigidoAA);
		        documentosalidaBean.setOrigen(this.origenA);
		        documentosalidaBean.setRemitente(this.remitenteA);
		        documentosalidaBean.setDias(this.diasA);
		        documentosalidaBean.setVaccion(this.vaccionA);
		        documentosalidaBean.setOpcion_seguimiento(this.opcion_seguimiento);
		        //documentosalidaBean.setAreaseleccionadas(this.aresseleccionasA);
		        ///a�ade Eli Diaz Horna 
		        documentosalidaBean.setAreaseleccionadas(this.generaAreasA());
		        //documentosalidaBean.setIndica(this.generaIndicadorA());
		        documentosalidaBean.setIndicadores(this.generaIndicadorA());
		        documentosalidaBean.setQuitarareas(this.quitarareas);
		        documentosalidaBean.setQuitarfichas(this.quitarfichas);
		        documentosalidaBean.setVanoentrada(this.anodocumentoentrA);
		        documentosalidaBean.setNombreremitente(this.nombreremitenteA);
		        
		
		
		        // ADD CF Ago 2011
		        documentosalidaBean.setCodigos(this.generaCodigosA());
		        System.out.println("Trama Codigo de Areas Seleccionadas: "
		                + this.generaAreasA());
		        
		        Map outMap = serviciosDocumentoSalida.ActualizarDocumentosSalida(documentosalidaBean);
		        String out = (String) outMap.get("out");
		        String correlativo = (String) outMap.get("outcorrelativo");
		        out = out.trim();
		        System.out.println("OUT STORED!!!:" + out);
		        if (out.equals("0")) {
		            String estadoDocSal = this.getNestadoA();
		
		            if (estadoDocSal.equals("ESTA003") && (this.origenA == 1)) {
		
		                /*Agregado el 16/11/2011 - Alfredo Panitz */
		                //Enviando Correo de acuerdo a evaluacion del documento (Prioridad)
		                String prioridad = this.getPrioridadA();
		                if (prioridad.equals("URGENTE")) {
		                    String txtMensaje = "";
		                    String txtAsunto = "Nuevo Documento de Entrada de Prioridad URGENTE - Trámite Documentario Corporativo";
		                    txtMensaje = txtMensaje + txtAsunto + "\n\n";
		
		                    String strArrAreas = documentosalidaBean.getAreaseleccionadas();
		                    System.out.println("StrArray Areas : " + strArrAreas);
		                    strArrAreas = strArrAreas.replace("-", "");
		                    String strArrIndicadores = documentosalidaBean.getIndicadores();
		                    System.out.println("StrArray Indicadores : " + strArrIndicadores);
		                    strArrIndicadores = strArrIndicadores.replace("-", "");
		
		                    int cantAreas = strArrAreas.length() / 3;
		                    //int cantIndicadores = strArrIndicadores.length()/2;
		
		                    List listadoAux = new ArrayList();
		                    //Obtener la lista de Areas de acuerdo a si son AA o CC 
		                    for (int cont = 0; cont < cantAreas; cont++) {
		                        String areaAct = strArrAreas.substring(0, 3);
		                        strArrAreas = strArrAreas.substring(3);
		
		                        String indAct = strArrIndicadores.substring(0, 2);
		                        strArrIndicadores = strArrIndicadores.substring(2);
		
		                        if (indAct.equals("AA")) {
		                            listadoAux.add(areaAct);
		                        }
		                    }
		                    //Filtrando las areas repetidas 
		                    for (int cont = 0; cont < listadoAux.size(); cont++) {
		                        String areaIni = (String) listadoAux.get(cont);
		                        if (!areaIni.equals("")) {
		                            for (int j = cont + 1; j < listadoAux.size(); j++) {
		                                String areaEval = (String) listadoAux.get(j);
		                                if (!areaEval.equals("")) {
		                                    if (areaIni.equals(areaEval)) {
		                                        listadoAux.set(j, "");
		                                    }
		                                }
		                            }
		                        }
		                    }
		
		                    List listadoAreas = new ArrayList();
		                    //Creando un listado de areas limpio (sin espacios en blanco) final
		                    for (int cont = 0; cont < listadoAux.size(); cont++) {
		                        String itemAct = (String) listadoAux.get(cont);
		                        if (!itemAct.equals("")) {
		                            listadoAreas.add(itemAct);
		                        }
		                    }
		
		                    for (int cont = 0; cont < listadoAreas.size(); cont++) {
		                        System.out.println((String) listadoAreas.get(cont));
		                    }
		                    /*
		                    for (int cont = 0; cont < listadoAreas.size(); cont++) {
		                        List<Usuario> listadoSecretarias = serviciosUsuarios.getSecretariasByCodArea(Integer.parseInt((String) listadoAreas.get(cont)));
		                        for (int j = 0; j < listadoSecretarias.size(); j++) {
		                        	txtAsunto = "Documento con Plazo - Sistema Tramite Documentario";
		        	                txtMensaje = txtMensaje + txtAsunto + "\n\n";
		        	                txtMensaje = txtAsunto + "\n\n";
		        	                txtMensaje = txtMensaje + "Estimada(o) " + this.primeranombre + " :\n";
		        	                txtMensaje = txtMensaje + "Le comunicamos que un documento ha llegado a su bandeja de entrada en el Sistema Tramite Documentario." + "\n\n";
		        	                txtMensaje = txtMensaje + "Equipo Remitente : " + this.nombreremitenteA + "\n\n";	                
		        	                txtMensaje = txtMensaje + "Numero Documento : " + this.numerodocA + "\n\n";
		        	                txtMensaje = txtMensaje + "Asunto : " 	+ this.asuntoA + "\n\n";
		        	                txtMensaje = txtMensaje + "Fecha Documento : " 	+ sdf.format(this.fechaA) + "\n\n";	               
		        	                txtMensaje = txtMensaje + "Visualice el documento en el siguiente link  : " + this.link_acceso  + "\n\n";
		        	                txtMensaje = txtMensaje + "Atentamente,\n\n";
		        	                txtMensaje = txtMensaje + "Sistema Tramite Documentario \n";
		        	                txtMensaje = txtMensaje + "Por favor no responder este Email";
		        	                
		                            //listadoSecretarias.get(j).getCodarea(), 
		                            Boolean envioCorreo = serviciosUsuarios.enviarEmailByFichaUsu(listadoSecretarias.get(j).getFicha(), txtMensaje, txtAsunto);
		                            
		                            if (envioCorreo) {
		                                mailService.enviarMail("El area del usuario " + listadoSecretarias.get(j).getNombre().toUpperCase() + " recibio un documento\n"
		                                        + "de importancia URGENTE y este ha sido notificado");
		                            } else {
		                                mailService.enviarMail("El area del usuario " + listadoSecretarias.get(j).getNombre().toUpperCase() + " recibio un documento\n"
		                                        + "de importancia URGENTE y pero este, al no contar con un correo registrado en la tabla de Trabajadores, no pudo ser notificado.");
		                            }
		                            
		                        }
		                    }
		                    */
		                }
		            }
		
		
		            this.documento = serviciosDocumentoSalida.catalogo(area);
		            // e
		            // reporte
		            //this.error = "Se actualizaron los campos correctamente";
		            //this.error = "Se grab� Satisfactoriamente";	
		            this.error = "Se actualizaron los campos correctamente";
		            this.ver = true;
		            this.verCatalogo = true;
		            this.verActualizar = false;
		            this.verAlertaConfirmacionGrabar = false;
		            ind_adjuntar = 0;
		            this.valores = "";
		            this.quitarareas = "";
		            this.quitarfichas = "";
		            this.areas.clear();
		            
		        } else {
		            this.error = "No Se grabó por inconsistencia de la información";
		            System.out.println("Eli quiero ver el error 1");
		            this.ver = true;
		            this.verCatalogo = true;
		            this.verActualizar = false;
		            this.verAlertaConfirmacionGrabar = false;
		        }
		        
	    	 } catch (Exception exception) {
	             exception.printStackTrace();
	             //logger.error("Fallo el registro", exception);
	             this.error = "Transaccion No valida.";
	             System.out.println("Eli quiero ver el error 2");
	             this.ver = true;
	             this.verCatalogo = true;
		         this.verActualizar = false;
	             this.verAlertaConfirmacionGrabar = false;
	         } finally {
	             if (formulario) {
	                 ind_adjuntar = 0;
	             }
	             session.setAttribute("file", null);
	         }
    	
    }

    public String generaAreas() {
        String seguir = "";
        for (Seguir p : this.seleccionados) {
            // seguir += p.getCodArea() + '-';
        	//SED-FON-161 INI
            //seguir += p.getArea() + '-';
        	if ((p.getnIndRemitente()==2) ||  (p.getnIndRemitente()==3))  {
        		seguir += p.getArea() + '|' + String.valueOf(p.getnIndRemitente()) + '-';
        	} else {
        		seguir += p.getArea() + "|1-";
        	}
        }
        return seguir;
    }

    public String generaCodigos() {
        String seguir = "";
        for (Seguir p : this.seleccionados) {
            seguir += p.getFicha() + '-';

        }
        return seguir;
    }

    public String generaCodigosA() {
        String ficha = "";
        for (AreaBean p : this.areas) {
            // if (p.getCentro()!=null){
            // seguir += p.getCentro() + '-';
            ficha += String.valueOf(p.getFicha()) + '-';
            // }
        }
        return ficha;
    }
    

    public String generaAreasA() {
        String areas = "";

        for (AreaBean p : this.areas) {
            // if (p.getCentro()!=null){
            areas += String.valueOf(p.getCodigo()) + '-';
            // }
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

    public void cerrarDetalles(ActionEvent event) {
    	this.verPDF = false;
        this.verDetalles = false;
        this.indicaadjuntoN = 1;
        this.indicaadjuntoA = 1;
        this.textoA = "El Registro tiene " + this.indicaadjuntoN
                + " Archivo Adjunto";

    }

    public void cerrarCatalogoEntrada(ActionEvent event) {
        this.verCatalogoEntrada = false;

    }

    public void cerrar(ActionEvent event) {
    	this.verPDF = false;
        this.ver = false;
        eventRefrescar();
    }

    public void eventNuevo(ActionEvent event) {
        this.limpiar();
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
        itemselectA[0] = this.dirigidoA;
        Utils.pasaDerecha(this.items7a, this.items7b, this.itemselectA);
    }

    public void pasaIzquierda(ActionEvent actionEvent) {
        Utils.pasaIzquierda(items7a, items7b, items7bSelected);

        int area = 0;
        if (this.origenA == 1) {
            System.out.println("Interno");
            List<AreaBean> areas = documentoSalidaDAO.areas();
            List itemsAreas = Utils.getAreas(areas);
            this.items7a = Utils.getAreasLink(areas);
            for (int i = 0; i < this.items7bSelected.length; i++) {
                System.out.println(this.items7bSelected[i]);
                area = Integer.parseInt(this.items7bSelected[i]);

                // for(AreaBean p:this.areasOrigen)
                // {
                // if(area==p.getCodigo());
                // }

            }
            quitar_areas = quitar_areas + area;
            System.out.println(quitar_areas);
        } else {
            System.out.println("Externo");
            List<RemitenteBean> remitente = documentoSalidaDAO.remitentes();
            List itemsAreas = Utils.getRemitentes(remitente);
            this.items7a = Utils.getRemitenteLink(remitente);
        }
    }

    public void captura(ValueChangeEvent changeEvent) {
        area = (String) changeEvent.getNewValue();
        System.out.println(area);
        documentoSalidaDAO.trabajador(Integer.parseInt(area));
        List<TrabajadorBean> trabajador = documentoSalidaDAO.trabajador(Integer
                .parseInt(area));
        List itemsTrabajador = Utils.getTrabajador(trabajador);
        this.items3 = itemsTrabajador;
        // for (TrabajadorBean t: trabajador)
        // System.out.println(t.getArea());

    }

    public void captura_accion(ValueChangeEvent changeEvent) {
        selectedItems1 = new ArrayList<String>();
        String acciones = this.vaccionA;
        System.out.println(acciones);

        for (String p : this.selectedItems1) {
            selectedItems1.add(p);
        }

    }

    public void actualizar(ValueChangeEvent changeEvent) {
        String area = String.valueOf(this.ndirigidoA);
        System.out.println(area);
        documentoSalidaDAO.trabajador(Integer.parseInt(area));
        List<TrabajadorBean> trabajador = documentoSalidaDAO.trabajador(Integer
                .parseInt(area));
        List itemsTrabajador = Utils.getTrabajador(trabajador);
        this.items3 = itemsTrabajador;
        // for (TrabajadorBean t : trabajador)
        // ;

    }

   
    public void captura_origen(ValueChangeEvent changeEvent) {
        origen = (String) changeEvent.getNewValue();
        origenes = Integer.parseInt(origen);
        System.out.println("Imprime Origen");
        System.out.println(origen);
        if (origenes==1) {
            System.out.println("Interno");
            List<AreaBean> areas = documentoSalidaDAO.areas();
            List itemsAreas = Utils.getAreas(areas);
            // this.items7a = Utils.getAreasLink(areas);
            this.items1 = itemsAreas;
        } else {
            System.out.println("Externo");
            List<RemitenteBean> remitente = documentoSalidaDAO.remitentes();
            List itemsAreas = Utils.getRemitentes(remitente);
            // this.items7a = Utils.getRemitenteLink(remitente);
            this.items1 = itemsAreas;
        }
        /*
        if (origenes == 1) {
            System.out.println("Interno");
            List<AreaBean> areas = documentoSalidaDAO.areas();
            List itemsAreas = Utils.getAreas(areas);
            // this.items7a = Utils.getAreasLink(areas);
            this.items1 = itemsAreas;

        } else {
            System.out.println("Externo");
            List<RemitenteBean> remitente = documentoSalidaDAO.remitentes();
            List itemsAreas = Utils.getRemitentes(remitente);
            // this.items7a = Utils.getRemitenteLink(remitente);
            this.items1 = itemsAreas;
        }
        */
        this.listaConceptosBuscar();
        this.capturaTipoIngresoNuevo();
    }


    public void visualiza_origen(ValueChangeEvent changeEvent) {
        // String origen = this.origenA;
        origen = String.valueOf((Integer) changeEvent.getNewValue());
        origenes = Integer.parseInt(origen);
        // int origenes = this.origenA;
        System.out.println("Imprime Origen Actualizar");
        System.out.println(origen);

        if (origenes == 1) {
            System.out.println("Interno");
            List<AreaBean> areas = documentoSalidaDAO.areas();
            List itemsAreas = Utils.getAreas(areas);
            this.items1 = itemsAreas;
            // this.items7a = Utils.getAreasLink(areas);

        } else {

            System.out.println("Externo");
            List<RemitenteBean> remitente = documentoSalidaDAO.remitentes();
            List itemsAreas = Utils.getRemitentes(remitente);
            this.items1 = itemsAreas;
            // this.items7a = Utils.getRemitenteLink(remitente);

        }
        //SED-REQ-00001
        this.origenA=origenes;
        this.validaNotificacionEmail();
        
    }
    //SED-FON-161
 public void eventTipoConceptoBuscar(ValueChangeEvent event){
    	
    	String opcion = (String) event.getNewValue();
    	this.remitente_conceptoBuscar = opcion;
    	 this.capturaTipoIngresoNuevo();
    	
    
 }	
   
 	//SED-FON-161
 	public static void crearDirectorio(String ruta) throws IOException {
		File directorio = new File(ruta);
		if (!directorio.exists()) {
			if (!directorio.mkdirs()) {
				throw new IOException("Error al crear directorio");
			}
		}
	}
// 	 public void eventFirmaDigital(ActionEvent evt) {
//         this.limpiarActualizar();
//         Gson gson = new Gson();
//         if (this.selectedDocumentoss.size() == 1) {
//        	 FirmaDigitalRequest firma = new FirmaDigitalRequest();
//        	 firma.getArchivos().add("b0b76af2-c2e3-4ae0-9410-a2d0c0cfa039.pdf");
//        	 firma.setRutaDestino("/Firmasqas/salientes/");
//        	 firma.setRutaImagenes("/Firmasqas/imagenes/");
//        	 firma.setRutaOrigen("/Firmasqas/salientes/");
//        	 
//        	 String jsonFirmaRequest = gson.toJson(firma);
//        	 System.out.println(jsonFirmaRequest);
//         } else {
//             this.error = "Debe seleccionar un documento";
//             this.ver = true;
//
//         }
// 	 }    
 	 
 	//SED-FON-161
 	 
 	//SED-FON-161   
    public void eventValidar(ActionEvent event) {
    	  
    	  List<Seguir> personasBPM = new ArrayList<Seguir>();
    	  Integer correlativo;
    	  Integer anio;
    	  
    	  correlativo= Integer.parseInt(this.doc_entradaA);
    	  anio=Integer.parseInt(this.anodocumentoentrN);
    	  //   this.error = "No Se grab� por inconsistencia de la informaci�n";
          //this.ver = true;
    	  boolean valido=true;

    	//  this.serviciosAPI.consultarPorTipoDocumento("", "");

    	  	personasBPM = documentoSalidaDAO.personaBPM(correlativo, anio);
	    	if (personasBPM.size()>0) {
	    		
	    		for (int i=0; i<personasBPM.size(); i++) {
	    			for (int j=0; j<this.seleccionados.size(); j++ ) {
	    				if (Integer.parseInt(personasBPM.get(i).getArea())==Integer.parseInt(this.seleccionados.get(j).getArea())) {
	    					this.error="Usuario BPM ya registrado";
	      				  	this.ver=true;
	      				  	valido=false;
	    				}
	    			}
	    		}
	    		if (valido) {
		    		for (int i=0; i<personasBPM.size(); i++) {
		    			personasBPM.get(i).setFicha("0");    		    		 
		    			this.seleccionados.add(personasBPM.get(i));    		
		    			ls_origen_actual=2;
		    		}
	    		}
	    		
	    	
    	  }
    }
  
    private DocumentoDTO obtenerDocumento(Long correlativo) {
    	
    	RemitenteBPM remitente = new RemitenteBPM();
    	remitente = documentoSalidaDAO.consultaDocumentoBPM(correlativo);
    	if (remitente != null) {
    		DocumentoDTO documento = new DocumentoDTO(remitente.getTipoDocumento(), remitente.getNroDocumento());
    		return documento;
    	} else {
    		return null;
    	}
    	
    }
 	
 	//SED-FON-161
    private void validaInfoBPM_Actualizar(){
 
    				if (this.nestadoA.equals("ESTA003")) { // es atendido
    					if (this.origenA==2)  {	// externo    						
    						DocumentoDTO  documento = new DocumentoDTO();
    						documento = obtenerDocumento(new Long(this.areas.get(0).getCodigo()));
    						//	DocumentoDTO  documento = new DocumentoDTO( itemS.getVtipdoc(), itemS.getVnumdoc());
    						if (serviciosAPI.consultaCasilla(documento, this.listTipoDoc))	{ // tiene casilla electronica
    							ResourceBundle bundle = ResourceBundle.getBundle("com.sedapal.tramite.files.parametros");
    							String rutaCarpetas;
    							rutaCarpetas = bundle.getString("app.config.fileserver.bpm") ;
    							try {
									crearDirectorio(rutaCarpetas);
									HttpSession session = null;
								    session = (HttpSession) FacesContext.getCurrentInstance()
						                    .getExternalContext().getSession(false);
									String file = (String) session.getAttribute("file");
									 String annio = Integer.toString(c.get(Calendar.YEAR));
									 String rutaIni = bundle.getString("path.salida")+"/" + annio + file;
					                 String rutaSalida = rutaCarpetas+"/" + annio + file;
					                 String rutaToJson = bundle.getString("app.config.fileserver.bpm.json");
					                 System.out.println("ruta ini " + rutaIni);
					                 System.out.println("ruta salida "+ rutaSalida);
					                 System.out.println("ruta json "+ rutaToJson);
					                 FileCopy fc = new FileCopy(rutaIni,rutaSalida);
					                 
					                 CasillaElectronicaResponse ce = new CasillaElectronicaResponse();
					                 //AQUI SE SETEAN LOS VALORES QUE VIENE DE INDENOVA AL JSON EDH 22/05/2023
					                 
					                 ce.setNumDocInteresado(documento.getNumero_documento());					                 
					                 //ce.setIdexp(Integer.toString(this.codigoA));
					                 ce.setNumexpediente(this.numerodocA);
					                 ce.setObservaciones(this.asuntoA);
					                 //ce.setCodExpType(this.tipodocA);
					                 ce.setCodExpType("MPV");
					                 String nroRegistro="";
					                 if ((this.doc_entradaA != null) && (this.doc_entradaA!="")) {
					                	 nroRegistro+=this.doc_entradaA.trim().concat("-").concat(this.anodocumentoentrA);
					                 }
					                 ce.setNumreg(nroRegistro);
					                 
					                 List<Exp_docs> edDocs = new ArrayList<Exp_docs>();
					                 edDocs.add(new Exp_docs(rutaToJson, nombre_archivo)); 
					                // edDocs.add(new Exp_docs(rutaToJson, "2023117.pdf"));
					                 ce.setDocs(edDocs);
					                 this.serviciosAPI.enviarNotificacionBPM(ce); 
								} catch (IOException e) {
									e.printStackTrace();
								}
    						}
    	    				
    					}
    				}
    			//}
    		}
     
    
 	private void validaInfoBPM(){
 		if (this.seleccionados.size()>0) {
    		for (int i=0; i<this.seleccionados.size(); i++) {
    			Seguir itemS = this.seleccionados.get(i);
    			//if (itemS.getnIndRemitente()==2) { //es BPM
    				if (this.nestadoN.equals("ESTA003")) { // es atendido
    					if (this.origenesN.equals("2") && (itemS.getEstado().equals("AA"))) {	// externo
    						DocumentoDTO  documento = new DocumentoDTO( itemS.getVtipdoc(), itemS.getVnumdoc());
    						if (serviciosAPI.consultaCasilla(documento, this.listTipoDoc))	{ // tiene casilla electronica
    							ResourceBundle bundle = ResourceBundle.getBundle("com.sedapal.tramite.files.parametros");
    							String rutaCarpetas;
    							rutaCarpetas = bundle.getString("app.config.fileserver.bpm");
    							try {
									crearDirectorio(rutaCarpetas);
									HttpSession session = null;
								    session = (HttpSession) FacesContext.getCurrentInstance()
						                    .getExternalContext().getSession(false);
									String file = (String) session.getAttribute("file");
									 String annio = Integer.toString(c.get(Calendar.YEAR));
									 String rutaIni = bundle.getString("path.salida")+"/" + annio + file;
					                 String rutaSalida = rutaCarpetas+"/" + annio + file;
					                 String rutaToJson = bundle.getString("app.config.fileserver.bpm.json");
					                 FileCopy fc = new FileCopy(rutaIni,rutaSalida);
					                 
					                 CasillaElectronicaResponse ce = new CasillaElectronicaResponse();
					                 //AQUI SE SETEAN LOS VALORES QUE VIENE DE INDENOVA AL JSON EDH 22/05/2023
					                 ce.setEstado("ATENDIDO");
					                 ce.setNumDocInteresado(itemS.getVnumdoc());					                 
					                // ce.setIdexp("0");
					                 List<Exp_docs> edDocs = new ArrayList<Exp_docs>();
					                 edDocs.add(new Exp_docs(rutaToJson, annio+file));
					                 ce.setDocs(edDocs);
					                 this.serviciosAPI.enviarNotificacionBPM(ce); 
								} catch (IOException e) {
									e.printStackTrace();
								}
    						}
    	    				
    					}
    				}
    			//}
    		}
    	}
 	}
    public void eventRegistrarDocumentos(ActionEvent event) {
        HttpSession session = null;
        boolean formulario = true;
        this.disBotonGrabar = false;
        this.vtipo = this.generaIndicador();
        try {
            // /acediendo a sesion http
            session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            String valida = null;
            this.notificadorN="0";


            valida = this.validarFormulario();
            if (valida.equals("ok")) {
                formulario = true;
                this.verNuevo = false;
                // ///guardando en sesion un objeto
                Usuario usuario = null;
                String file = null;
                usuario = (Usuario) session.getAttribute("sUsuario");
                this.areaN = usuario.getCodarea();
                this.vrescreN = usuario.getLogin();
                this.vresactN = usuario.getLogin();
                String area = String.valueOf(usuario.getCodarea());
                // controlando el adjuntar
                file = (String) session.getAttribute("file");
                if (file != null) {
                    nombre_archivo = file;
                    List<ServidorBean> bean = this.documentoSalidaDAO.servidor();
                    for (ServidorBean e : bean) {
                        ubicacion = e.getDescripcion();
                        // ubicacion = "http://1.1.194.53/salida/"; modificado
                        // en parametros
                    }
                    Date date = new Date();
                    String annio = Integer.toString(c.get(Calendar.YEAR));
                    nombre_archivo = annio + nombre_archivo;
                    this.ubicararchivoN = nombre_archivo;
                } else {
                    this.ubicararchivoN = "NADA";
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
                for (String p : this.selectedItems1) {
                    // System.out.println("ITEM:"+ p);
                    accion = p + accion;
                    // System.out.println("completo:"+ accion);
                    this.vaccionN = accion;

                }
                this.vaccionN = "13";
                DocumentoSalidaBean documentosalidaBean = new DocumentoSalidaBean();
                // Se adiciona varias areas y fichas concatenadas
                // ADD EDH
                //Usuario usuario = null;
                //usuario = (Usuario) session.getAttribute("sUsuario");
                int remitente = usuario.getCodarea();
                int perfil = usuario.getNcodperfil();
                area = String.valueOf(usuario.getCodarea());
                
                if (perfil == 7 || perfil == 8) {
                    this.documento = serviciosDocumentoSalida.catalogo(area);            
                    List<TiposBean> mantenimiento = documentoSalidaDAO.estadoderivador();
                    List itemsMantenimiento = Utils.getTipos(mantenimiento);
                    this.items9 = itemsMantenimiento;
                    
                    this.verEliminar=true;
                                

                } else if (perfil == 10) {
                    this.documento = serviciosDocumentoSalida.catalogo(area);
                    List<TiposBean> mantenimiento = documentoSalidaDAO.estadojefe();
                    List itemsMantenimiento = Utils.getTipos(mantenimiento);
                    this.items9 = itemsMantenimiento;
                    
                    this.verEliminar=false;

                }  else {
                	 this.documento = serviciosDocumentoSalida.catalogo(area);
                     List<TiposBean> mantenimiento = documentoSalidaDAO.estadomantenimiento();
                     List itemsMantenimiento = Utils.getTipos(mantenimiento);
                     this.items9 = itemsMantenimiento;
                }

                documentosalidaBean.setAreas(this.generaAreas());
                System.out.println("Equipos o Empresas Seleccionadas:"
                        + this.generaAreas());
                documentosalidaBean.setIndicadores(this.generaIndicador());
                documentosalidaBean.setOrigenes(this.origenesN);
                documentosalidaBean.setTipodoc(this.tipodocN);
                documentosalidaBean.setArea(this.areaN);
                // documentosalidaBean.setDirigido(this.areaseleccionadaN);
                documentosalidaBean.setDirigido(suma_areas);
                // entranteBean.setVremitente(this.areaseleccionadaN);
                documentosalidaBean.setNdirigido(this.ndirigidoN);
                documentosalidaBean.setFecha(this.fechaN);
                documentosalidaBean.setAsunto(this.asuntoN);
                documentosalidaBean.setReferencia(this.referenciaN);
                documentosalidaBean.setObservacion(this.observacionN);
                documentosalidaBean.setPrioridad(this.prioridadN);
                //documentosalidaBean.setEstado(this.estadoN);
                documentosalidaBean.setNestado(this.nestadoN);               
                documentosalidaBean.setVrescre(this.vrescreN);
                documentosalidaBean.setVresact(this.vresactN);
                documentosalidaBean.setFicha_dirigido(this.ficha_dirigidoN);
                documentosalidaBean.setDoc_entrada(this.doc_entradaN);
                documentosalidaBean.setUbiarchivo(this.ubicararchivoN);
                documentosalidaBean.setRemitente(this.remitenteN);
                documentosalidaBean.setDfecplazo(this.dfecplazoN);
                documentosalidaBean.setNumerodoc(this.numerodocN);
                documentosalidaBean.setVaccion(this.vaccionN);
                documentosalidaBean.setDias(this.diasN);
                documentosalidaBean.setVanoentrada(this.anodocumentoentrN);
                documentosalidaBean.setCodigos(this.generaCodigos());
                documentosalidaBean.setNombreremitente(cadenaDigitada);
                documentosalidaBean.setVnotificador(notificadorN);
                System.out.println("Codigo seleccionados: "
                        + this.generaCodigos());
                
                if (this.getUbicararchivoN().equals("NADA")) {
                	this.indicaadjuntoN =0;
                }
                
                if (this.nestadoN.equals("ESTA001")) {
                	
                	    Map outMap = serviciosDocumentoSalida.NuevoDocumentoSalida(documentosalidaBean);       				
		                String out = (String) outMap.get("out");
		                String msgOper = (String) outMap.get("msgOper");		
		                out = out.trim();
		                String correlativo = (String) outMap.get("outcorrelativo");
		                System.out.println("OUT STORED!!!:" + out);
		                System.out.println("Resultado de la Operacion del SP :" + msgOper);		                	
            
	                if (out.equals("0")) {
	                	
	                	this.documento = serviciosDocumentoSalida.catalogo(area);
	                	this.documento = documentoSalidaDAO.documentoSP(area);
	                    this.error = "Se actualizaron los campos correctamente";
	                    eventRefrescar(event);                   
	                    this.ver = true;
	                    this.verCatalogo = true;
	                    this.verActualizar = false;
	                    ind_adjuntar = 0;
	                    this.valores = "";
	                    this.quitarareas = "";
	                    this.quitarfichas = "";
	                    this.areas.clear();
	                    
	                    
		                } else {
		                    this.error = "No Se grabó por inconsistencia de la información !";
		                    this.ver = true;
		                    this.verCatalogo = true;
		                    this.verActualizar = false;
		                } 
	               	                	
		            } else if (this.nestadoN.equals("ESTA002") || this.nestadoN.equals("ESTA003")) {
			                
		                	if (this.indicaadjuntoN==1) { 
		
				                Map outMap = serviciosDocumentoSalida.NuevoDocumentoSalida(documentosalidaBean);
				
				                String out = (String) outMap.get("out");
				                String msgOper = (String) outMap.get("msgOper");
				
				                out = out.trim();
				                String correlativo = (String) outMap.get("outcorrelativo");
				                System.out.println("OUT STORED!!!:" + out);
				                System.out.println("Resultado de la Operacion del SP :" + msgOper);
				                if (out.equals("0")) {
				
				                    String estadoDocSal = this.getEstadoN();
				
				                    
				                    if (estadoDocSal.equals("ESTA003") && (this.origenes == 1)) {
				
				                        /* Agregado el 16/11/2011 - Alfredo Panitz */
				                        // Enviando Correo de acuerdo a evaluacion del documento
				                        // (Prioridad)
				                    	
				                        String prioridad = this.getPrioridadN();
				                        /*
				                        if (prioridad.equals("URGENTE")) {
				                            String txtMensaje = "";
				                            String txtAsunto = "Documento de Salida de Prioridad URGENTE - Tr�mite Documentario Corporativo";
				                            txtMensaje = txtMensaje + txtAsunto + "\n\n";
				
				                            String strArrAreas = documentosalidaBean.getAreas();
				                            System.out.println("StrArray Areas : "
				                                    + strArrAreas);
				                            strArrAreas = strArrAreas.replace("-", "");
				                            String strArrIndicadores = documentosalidaBean
				                                    .getIndicadores();
				                            System.out.println("StrArray Indicadores : "
				                                    + strArrIndicadores);
				                            strArrIndicadores = strArrIndicadores.replace("-","");
				
				                            int cantAreas = strArrAreas.length() / 3;
				                           
				
				                            List listadoAux = new ArrayList();
				                           
				                            for (int cont = 0; cont < cantAreas; cont++) {
				                                String areaAct = strArrAreas.substring(0, 3);
				                                strArrAreas = strArrAreas.substring(3);
				
				                                String indAct = strArrIndicadores.substring(0,
				                                        2);
				                                strArrIndicadores = strArrIndicadores
				                                        .substring(2);
				
				                                if (indAct.equals("AA")) {
				                                    listadoAux.add(areaAct);
				                                }
				                            }
				                            // Filtrando las areas repetidas
				                            for (int cont = 0; cont < listadoAux.size(); cont++) {
				                                String areaIni = (String) listadoAux.get(cont);
				                                if (!areaIni.equals("")) {
				                                    for (int j = cont + 1; j < listadoAux
				                                            .size(); j++) {
				                                        String areaEval = (String) listadoAux
				                                                .get(j);
				                                        if (!areaEval.equals("")) {
				                                            if (areaIni.equals(areaEval)) {
				                                                listadoAux.set(j, "");
				                                            }
				                                        }
				                                    }
				                                }
				                            }
				
				                            List listadoAreas = new ArrayList();
				                           
				                            for (int cont = 0; cont < listadoAux.size(); cont++) {
				                                String itemAct = (String) listadoAux.get(cont);
				                                if (!itemAct.equals("")) {
				                                    listadoAreas.add(itemAct);
				                                }
				                            }
				
				                            for (int cont = 0; cont < listadoAreas.size(); cont++) {
				                                System.out.println((String) listadoAreas
				                                        .get(cont));
				                            }
				                            
				                            for (int cont = 0; cont < listadoAreas.size(); cont++) {
				                                List<Usuario> listadoSecretarias = serviciosUsuarios
				                                        .getSecretariasByCodArea(Integer
				                                        .parseInt((String) listadoAreas
				                                        .get(cont)));
				                                for (int j = 0; j < listadoSecretarias.size(); j++) {
				                                    txtMensaje = txtAsunto + "\n\n";
				                                    txtMensaje = txtMensaje
				                                            + "Estimada(o) "
				                                            + listadoSecretarias.get(j)
				                                            .getNombre().toUpperCase()
				                                            + " :\n";
				                                    txtMensaje = txtMensaje
				                                            + "Le recordamos que ha llegado a su Area un documento de importancia URGENTE.\n";
				                                    txtMensaje = txtMensaje
				                                            + "Correlativo : "
				                                            + correlativo + "\n";
				                                    txtMensaje = txtMensaje + "Asunto : "
				                                            + documentosalidaBean.getAsunto()
				                                            + "\n";
				                                    txtMensaje = txtMensaje + "Remitente : "
				                                    + documentosalidaBean.getNombreremitente()
				                                    + "\n";
				                                    txtMensaje = txtMensaje
				                                            + "Favor de atenderlo a la brevedad posible.\n\n";
				                                    txtMensaje = txtMensaje
				                                            + "Atentamente,\n\n";
				                                    txtMensaje = txtMensaje
				                                            + "Tramite Documentario Corporativo \n";
				                                    txtMensaje = txtMensaje
				                                            + "Por favor no responder este Email";
				
				                                    Boolean envioCorreo = serviciosUsuarios
				                                            .enviarEmailByFichaUsu(
				                                            listadoSecretarias.get(j).getFicha(),                                            
				                                            txtMensaje, txtAsunto);
				                                    
				                                }
				                            }
				                        }
				                        */
				                        
				                    }
				                    
				                    this.documento = serviciosDocumentoSalida.catalogo(area); // actualiza
				                    // el
				                    // reporte
				                    this.error = "Se grabó Satisfactoriamente";
				                    //this.eventRefrescar();
				                    ind_adjuntar = 0;
				                    this.ver = true;
				                    this.verCatalogo = true;
				                    this.verActualizar = false;
				                    this.editarEmpresa = true;
				                } else {
				                    this.error = "No Se grabó por inconsistencia de la información";
				                    this.ver = true;
				                    this.verCatalogo = true;
				                }
				                
			            } else {
		                    formulario = false;
		                    this.error = "Recuerda que es obligatorio adjuntar un archivo, para poder cambiar de estado al documento  ";                
		                    this.ver = true;
		                    this.verNuevo = true;
		                    
		                }
		               }
                this.validaInfoBPM(); //SED-FON-161
            }
            
            else {
                formulario = false;
                this.error = valida;
                this.ver = true;
            }
            
        } catch (Exception e) {
            //logger.error("Fallo el registro", e);
            //this.error = "Transaccion No valida.";
            //this.ver = true;
        } finally {
            if (formulario) {
                session.setAttribute("file", null);
            }
            ubicacion = "";
            nombre_archivo = "";
            this.ubicararchivoN = "";

        }

    }

    public List<DocumentoSalidaBean> getDocumentoSalida() {
        return this.documento;
    }

    public void setDocumentoSalida(List<DocumentoSalidaBean> documento) {
        this.documento = documento;
    }

    public void setServiciosDocumentoSalida(
            IServiciosDocumentoSalida serviciosDocumentoSalida) {
        this.serviciosDocumentoSalida = serviciosDocumentoSalida;
    }

    public void eventFiltros(ActionEvent event) {
        // this.ver = true;
        // /acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        this.areaA = usuario.getCodarea();
        this.vresactA = usuario.getLogin();
        String ncodarea = String.valueOf(usuario.getCodarea());
        this.areas_actuales = usuario.getCodarea();
        // fechas

        // usamos para darle el fomato adecuado para pasarle al stored de oracle
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        String validaopcion = this.validarOpcion();
        
        if (validaopcion.equals("ok")) {
        
        	 	String date1 = sdf.format(this.getDate1());
		        String date2 = sdf.format(this.getDate2());
		        // se puede validar fechas
		        if (this.date1.after(this.date2)) {
		            this.error = "Fecha inicio debe ser menor que la fecha final";
		            this.ver = true;
		
		        } else {
		            System.out.println("Filtrando..");
		            // Falta Programar por Eli Diaz
		            System.out.println("Envio las fechas");
		            System.out.println(date1);
		            System.out.println(date2);
		            this.vtipo = this.getItemSeleccionado();
		
		            this.documento = documentoSalidaDAO.filtrosSP(date1, date2, this.tipoopcion, ncodarea,
		            		this.vtipo, this.getItemareaSeleccionado());
		            System.out.println(vtipo);
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

    public void eventCriterios(ActionEvent event) {
        // this.ver = true;
        // /acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        this.areaA = usuario.getCodarea();
        this.vresactA = usuario.getLogin();
        String area = String.valueOf(usuario.getCodarea());
        // fechas
        // usamos para darle el fomato adecuado para pasarle al stored de oracle
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        String date1 = sdf.format(this.getDate1());
        String date2 = sdf.format(this.getDate2());
        // se puede validar fechas
        if (this.date1.after(this.date2)) {
            this.error = "Fecha Inicio debe ser menor que la fecha Final";
            this.ver = true;
        } else {
            System.out.println(date1);
            System.out.println(date2);
            System.out.println(this.getItemSeleccionado());
            System.out.println(this.getItem3Seleccionado());
            System.out.println(area);
            System.out.println(detallecriterio);
            // Falta Programar por Eli Diaz
            this.documento = documentoSalidaDAO.criteriosSP(date1, date2, this
                    .getItemSeleccionado(), this.getItem3Seleccionado(), area,
                    detallecriterio.toUpperCase());
            // System.out.println(date1+ "  " +date2+ "  "
            // +this.getItem2Seleccionado()+ "  " +this.getItem3Seleccionado()+
            // " " +area+ "  " + detallecriterio);
        }
        System.out.println(this.documento.size());
        System.out.println(date1 + "  " + date2 + "  "
                + this.getItem2Seleccionado());
    }

    public void eventRefrescar(ActionEvent evt) {
        // this.seguimiento = seguimientodocumentoDAO.seguimientoSP();
        String area = this.area;
        this.documento = documentoSalidaDAO.documentoSP(area);
        selectedItems1.clear();
        this.detalle = "";

    }

    //A CF 16-06-2011
    public void eventRefrescar() {
        String area = this.area;
        selectedItems1.clear();
        this.documento = documentoSalidaDAO.documentoSP(area);
        this.setSortColumnName("nano");
        this.setAscending(false);
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
        if (this.origenesN.equals("2")) {
        try {
            // desactiva boton
            disBotonGrabar = true;
            // /valida
            if (this.empresa.trim().length() > 0) {

                /*
                 * for (EntranteBean p : selectedEntrantes) { this.empresa =
                 * p.getEmpresa(); }
                 */
                String out = documentoSalidaDAO.actualizaCombos(this.empresa, String
                        .valueOf(usuario.getCodarea()), usuario.getLogin());
                out = out.trim();
                System.out.println("OUT STORED!!!:" + out);
                if (out.equals("0")) {
                    this.editarEmpresa = true;

//					SED-FON-161
//                    List<RemitenteBean> remitentes = documentoSalidaDAO
//                            .remitentes();
//                    List itemsRemitentes = Utils.getRemitentes(remitentes);
//                    // this.items7a = Utils.getRemitenteLink(remitentes);
//                    // preseleccionar el ingresado
//                    this.items1 = itemsRemitentes;
//                    for (RemitenteBean p : remitentes) {
//                        if (p.getDescripcion().equals(this.empresa)) {
//                            // this.vremitenteN = String.valueOf(p.getCodigo());
//                            this.dirigidoN = String.valueOf(p.getCodigo());
//                            this.personaSeleccionada = this.empresa;
//                            break;
//                        }
//                    }

                    // act el otro combo
                    // area = this.vremitenteN;
                    
                    
                    //SED-FON-161
                    this.nuevoDirigido=true;
                    RemitenteBean rem = new RemitenteBean();
                    rem=documentoSalidaDAO.getRemitenteByNombre(this.empresa);
                    this.items1.add(new SelectItem(rem.getCodigo(), rem.getDescripcion()));
                    this.dirigidoN = Integer.toString(rem.getCodigo());
                    this.personaSeleccionada = this.empresa;
                    
                    area = this.dirigidoN;
                    area_remite = Integer.parseInt(area);
                    if (area_remite == 100 || area_remite == 601) {
                    } else if (area_remite > 600) {
                        /**
                         * List<AreaBean> areas = documentoSalidaDAO.areas();
                         * List itemsAreas = Utils.getAreas(areas);
                         * //this.items7 = itemsAreas; this.items7a =
                         * Utils.getAreasLink(areas);
                         */
                        List<RepresentanteBean> remite = documentoSalidaDAO
                                .representanteInterno(area_remite);
                        List itemsrepresentante = Utils.getRepresentante(remite);
                        this.items3 = itemsrepresentante;
                        this.itemsPersonalRepresentantes = itemsrepresentante;
                        // for (RepresentanteBean r: remite);

                    } else {
                        // System.out.println(area);
                        documentoSalidaDAO.trabajador(Integer.parseInt(area));
                        List<TrabajadorBean> trabajador = documentoSalidaDAO
                                .trabajador(area_remite);
                        List itemsTrabajador = Utils
                                .getTrabajador_derivador(trabajador);
                        this.items3 = itemsTrabajador;
                        // this.items6 = itemsTrabajador;
                        // for (TrabajadorBean t: trabajador)
                        // System.out.println(t.getArea());
                    }
                } else {
                    this.error = "Ya existe el Remitente en la Base de Datos, Por favor seleccionar de la lista Equipo/Empresa";
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
        	System.out.println(exception.getMessage());
            this.error = "Error Interno, consulte con el administrador[203]";
            this.ver = true;
            //logger.error("MBeanEntrante.eventGrabarCombo][203]", exception);
            exception.printStackTrace();
            disBotonGrabar = false;
        } finally {
            // this.empresa="";
        }
        } else {
        	 this.error = "Debe seleccionar origen externo";
             this.ver = true;
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
            this.documento = documentoSalidaDAO.BusquedaSP(area, opcion, detalle);
        }
    }
    // list of selected profiles
    protected ArrayList<DocumentoSalidaBean> selectedDocumentoss;
    // protected ArrayList<EntranteBean> selectedEntrantes;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanDocumentosSalientes() {
        selectedDocumentoss = new ArrayList<DocumentoSalidaBean>();
        System.out.println("Contructor MBeanProfiles....");
    }

    public void eventoNuevo(ActionEvent evnt) {
        // for (DocumentoSalidaBean p : selectedDocumentoss) {
        // System.out.println(p.getArea() + "   " + p.getCodigo());
        // }
    }

    @PostConstruct
    public void posterior() {
        long nowIn = 0, nowOut = 0, nowDif = 0;
        nowIn = System.currentTimeMillis();
        // /acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        int remitente = usuario.getCodarea();
        int perfil = usuario.getNcodperfil();
        area = String.valueOf(usuario.getCodarea());
        
        
        this.vrescreN = usuario.getLogin();
        this.vresactN = usuario.getLogin();
       
        if (perfil == 7 || perfil == 8) {
            this.documento = serviciosDocumentoSalida.catalogo(area);            
            List<TiposBean> estadoderivador = documentoSalidaDAO.estadoderivador();
            List itemsMantenimiento = Utils.getTipos(estadoderivador);
            this.items9 = itemsMantenimiento;
            
            this.verEliminar=true;
                        

        } else if (perfil == 10) {
            this.documento = serviciosDocumentoSalida.catalogo(area);
            List<TiposBean> estadojefe = documentoSalidaDAO.estadojefe();
            List itemsMantenimiento = Utils.getTipos(estadojefe);
            this.items9 = itemsMantenimiento;
            
            this.verEliminar=false;

        }  else {
        	 this.documento = serviciosDocumentoSalida.catalogo(area);
             List<TiposBean> mantenimiento = documentoSalidaDAO.estadomantenimiento();
             List itemsMantenimiento = Utils.getTipos(mantenimiento);
             this.items9 = itemsMantenimiento;
        }
        

        List<TiposBean> tipoconsulta = documentoSalidaDAO.tipoconsulta();
        List itemtipoconsulta = Utils.getTipos(tipoconsulta);
        this.itemscombo = itemtipoconsulta;

        // /Codigo Eli Diaz para el combo tipo
        List<TiposBean> tipos = documentoSalidaDAO.tipos();
        List itemstipos = Utils.getTipos(tipos);
        this.items2 = itemstipos;


        // /Codigo Eli Diaz para el combo trabajador
        List<TrabajadorBean> trabajador = documentoSalidaDAO.trabajador(this.ndirigidoA);
        List itemsTrabajador = Utils.getTrabajador(trabajador);
        this.items3 = itemsTrabajador;


        // /Codigo agregar Eli Diaz para el combo a�o --22/07/2013
        List<AnioBean> anio = documentoSalidaDAO.anio();
        List itemsAnio = Utils.getAnio(anio);
        this.items10 = itemsAnio;
        
        List<AreaBean> areas = documentoSalidaDAO.areas();
        List itemsAreas = Utils.getAreas(areas);
        this.items11 = itemsAreas;


        // /Codigo Eli Diaz para el combo trabajador remitente
        List<TrabajadorBean> trabajador_remitente = documentoSalidaDAO.trabajador_remitente(remitente);
        List itemremitente = Utils.getTrabajador(trabajador_remitente);
        this.items6 = itemremitente;
    

        List<OrigenBean> origen = documentoSalidaDAO.origen();
        List itemsOrigen = Utils.getOrigen(origen);
        this.items7 = itemsOrigen;
    

        List<EstadosBean> estado = documentoSalidaDAO.estados();
        List itemsEstado = Utils.getEstado(estado);
        this.items8 = itemsEstado;
        
        List<ServidorBean> bean = this.documentoSalidaDAO.servidor();
        for (ServidorBean e : bean) {
            ubicacion = e.getDescripcion();
        }
        
       
        //SED-FON-161
        this.nuevoDirigido=false;
        this.mostrarTipoBusqueda = true;
        this.deshabilitaBusqueda = true;
        this.listTipoDoc = entranteDAO.getTiposDocumentos();
        TiposDocumentosBean tdnombre = new TiposDocumentosBean();
        tdnombre.setCodigo("DOC0000");
        tdnombre.setDescripcion("NOMBRE");
        this.listTipoDoc.add(tdnombre);
 


    }

    private void listaConceptosBuscar() {
    	System.out.println("origen seleccionado");
    	System.out.println(this.origenes);
    	if (this.origenes==2) {
    		 List itemsTD= Utils.getTipos_Documentos(this.listTipoDoc);
    		 this.itemsTipoDoc = itemsTD;
            this.remitente_conceptoBuscar = this.listTipoDoc.get(0).getCodigo();
            this.mostrarTipoBusqueda = true;
            this.deshabilitaBusqueda=false;

    	} else if (this.origenes==1) {
    		 this.itemsTipoDoc = new ArrayList<>();
    		 this.remitente_conceptoBuscar = "DOC000A";
    		  this.mostrarTipoBusqueda = false;
              this.deshabilitaBusqueda=false;

    	} else {
    		this.itemsTipoDoc = new ArrayList<>();
            this.deshabilitaBusqueda=true;

    	}
    	  
          
        
                 
    }
    /**
     * SelectionListener bound to the ice:rowSelector component. Called when a
     * row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedDocumentoss.clear();
         // System.out.println("Tama:" + documento.size());
        // build the new selected list
        DocumentoSalidaBean documentosss = null;
        for (int i = 0, max = documento.size(); i < max; i++) {        	
            documentosss = (DocumentoSalidaBean) documento.get(i);
            if (documentosss.isSelected()) {
                selectedDocumentoss.add(documentosss);                
            }
        }
        for (DocumentoSalidaBean d : selectedDocumentoss) {
            System.out.println(d.getAno() + "  " + d.getTipodoc() + " "  + d.getFicha_remitente() + " " + d.getOrigen());
            this.mCarga.setNombrePdf(d.getUbiarchivo());
            this.ndirigidoA = d.getNdirigido();
            this.ficha_dirigidoA = d.getFicha_dirigido();
            this.remitenteA = d.getRemitente();
            this.dfecplazoA = d.getDfecplazo();
            this.origenA = d.getOrigen();
            ruta = d.getUbiarchivo();
            ind_adjuntar = d.getIndicador();
            this.indicaadjuntoA = d.getIndicador();
            int origen = d.getOrigen();
            //inserta codigo para ver el archivo adjunto 19noviembre2019-EDH
            
            
            ls_ubicacion = ubicacion + d.getUbiarchivo();
            System.out.println("Visualizacion de archivo");
            System.out.println(ls_ubicacion); 
            DocumentoSalidaBean bean = new DocumentoSalidaBean();
            bean.setUbicacion_salida(ls_ubicacion);	
            
            
            /* Eli COMENTA ESTE CODIGO */
            /*
            if (origen == 1) {
                List<AreaBean> areas = documentoSalidaDAO.areas();
                List itemsAreas = Utils.getAreas(areas);
                this.items7a = Utils.getAreasLink(areas);
            } else {
                List<RemitenteBean> remitente = documentoSalidaDAO.remitentes();
                List itemsRemitente = Utils.getRemitentes(remitente);
                this.items7a = Utils.getRemitenteLink(remitente);
            }
            */
        }
        
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
            selectedDocumentoss.clear();

            // build the new selected list
            DocumentoSalidaBean documentosss;
            for (int i = 0, max = documento.size(); i < max; i++) {
                documentosss = (DocumentoSalidaBean) documento.get(i);
                documentosss.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = false;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = false;
        }
    }

    public void eventCancelar(ActionEvent event) {
        this.verCatalogo = true;
        this.disBotonGrabar = false;
        this.verNuevo = false;
        this.verActualizar = false;
        selectedItems1.clear();
        this.items7b.clear();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.setAttribute("file", null);
        this.editarEmpresa = true;
        // limpiar el autocompletar
        this.personaSeleccionada = "";
        this.personaSeleccionadaA = "";
        //this.itemsPersonalRepresentantes.clear();
        //this.itemsPersonalRepresentantesA.clear();
        
        
    }

    public void limpiarActualizar() {
        // /acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext
                .getCurrentInstance().getExternalContext()
                .getSession(false);
        // ///guardando en sesion un objeto
        int perfil = 0;
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        perfil = usuario.getNcodperfil();
        this.codigoA = 0;
        this.anoA = 0;
        this.origenA = 0;
        this.tipodocA = "";
        this.codigoA = 0;
        this.numerodocA = "";
        this.ndirigidoA = 0;
        this.asuntoA = "";
        this.observacionA = "";
        //this.estadoA = "";
        this.areaA = 0;
        this.numerodocA = "";
        this.remitenteA = 0;
        this.dirigidoA = "";
        this.ficha_dirigidoA = 0;
        if (perfil == 7)// interno
        {
            this.disEstado = true;
        } else {
            this.disEstado = false;
        }


    }

    public void limpiar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        int ficha = 0;
        int perfil = 0;
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        int area_dirigido = usuario.getCodarea();
        this.nom_areaN = usuario.getNomequipo();
        List<JefeBean> bean = this.documentoSalidaDAO.jefe(area_dirigido);
        for (JefeBean e : bean) {
            ficha = e.getFicha();
        }
        ls_origen_actual = 1;
        this.remitenteN = ficha;
        this.anodocumentoentrN = "0";
        this.codigoN = 0;
        this.anoN = "";
        this.origenesN = "";
        this.tipodocN = "INTERNO";
        this.codigoN = 0;
        this.numerodocN = "";
        this.asuntoN = "";
        this.observacionN = "";
        this.estadoN = "ESTA000";
        this.nestadoN = "ESTA000";
        this.referenciaN = "";
        this.dirigidoN = "";
        this.doc_entradaN = "";
        this.dfeccreN = new Date();
        this.dfecactN = new Date();
        this.dfecplazoN = new Date();
        this.fechaN = new Date();
        this.areaseleccionadaN = "";
        selectedItems1.clear();
        this.items7b.clear();
        this.diasN = 1;
        this.indicaadjuntoN = 0;
        this.indicaadjuntoA = 0;
        this.textoA = "El Registro tiene " + this.indicaadjuntoN
                + " Archivo Adjunto";
        this.seleccionados.clear();
        this.estadoSeleccionado = "";
        this.prioridadN = "MEDIA";
        this.seleccionados.clear();
        if (perfil == 7 || perfil == 8)// interno
        {
            this.disEstado = true;
        } else {
            this.disEstado = false;
        }
        this.personaSeleccionada = "";
        this.derivadoA = "";
        //this.itemsPersonalRepresentantes.clear();
        this.origenes=1;
        this.origenN=1;
        


    }

    public String validaFormulario2() {

        String mensaje = "Falta ingresar: ";
        boolean ok = true;
        //this.asuntoN.trim().length() == 0
        if (this.opcion_seguimiento.equals("2")) {
            if (this.doc_entradaA.equals("") || this.doc_entradaA.equals("0")) {
                ok = false;
                mensaje += "Numero del Documento Entrada ";
            }
            System.out.println(this.anodocumentoentrA);
            if (this.anodocumentoentrA.equals("0")) {
                ok = false;
                mensaje += " Año del Documento Entrada ";
            }
        }
        
        if (this.nestadoA.equals("ESTA003")){
	        if (notificadorA.equals("0")) {
	            ok = false;
	            mensaje += "Indicar si Desea Notificar via Email";
	        }
       
        }
       
        if (ok) {
            mensaje = "ok";
        }
        return mensaje;

    }
    
    public String validaFormulario5() {
    	
    	String mensaje = "Falta ingresar: ";
        boolean ok = true;
        
        if (notificacion==0) {
            ok = false;
            mensaje += "Indicar si Deseas Notificar via Email";
        }
        
        if (ok) {
        	msgnuevo = "ok";
        }
        return msgnuevo;
		
    }
   
    
    public String validaFormulario4() {
    	
        
        boolean ok = true;
        if (this.opcion_seguimiento.equals("1")) {
            if (this.doc_entradaA.equals("")) {
                ok = false;
                msgnuevo = "Recuerda que grabaras sin Número del Registro Entrada ";
                this.verAlertaConfirmacionGrabar = true;
                this.verActualizar=true;
                this.verCatalogo=false;
            }
           
        }
       
        if (ok) {
        	msgnuevo = "ok";
        }
        return msgnuevo;
		
    }

    public String validarFormulario() {
        String mensaje = "Falta ingresar: ";
        boolean ok = true;
        
        if (this.tipodocN.equals("ATD0000")) {
            ok = false;
            mensaje += "Tipo de Documento";
        }

        if (this.diasN <= 0) {
            if (ok == false) {
                mensaje += ",Dias";
            } else {
                mensaje += "Dias";
            }
            ok = false;
        }
        if (this.vtipo.equals("")) {
            if (ok == false) {
                mensaje += ",Destinatario";
            } else {
                mensaje += "Destinatario";
            }
            ok = false;
        }

        if (this.remitenteN == 100) {
            if (ok == false) {
                mensaje += ",Remitente";
            } else {
                mensaje += "Remitente";
            }
            ok = false;
        }
        if (nestadoN.equals("ESTA000")){
        	if (ok == false) {
                mensaje += ",Estado";
            } else {
                mensaje += "Estado";
            }
            ok = false;
        	
        }
        // if (this.fechaN.after(this.dfecplazoN)) {
        // if (ok == false)
        // mensaje += ",Fecha de Plazo Menor a Fecha Documento";
        // else
        // mensaje += " Fecha de Plazo Menor a Fecha Documento";
        // ok = false;
        // }
        if (this.asuntoN.trim().length() == 0) {
            if (ok == false) {
                mensaje += ",Asunto";
            } else {
                mensaje += "Asunto";
            }
            ok = false;
        }
        if (this.doc_entradaN.trim().length() > 0) {
            if (this.anodocumentoentrN.equals("0")) {
                ok = false;
                mensaje += "Año del Documento Entrada";
            }
        }

        //ls_origen_actual origenesN
       
        if (origenes != ls_origen_actual) {
            if (ok == false) {
                mensaje += ",Verificar Origen del Doc.";
            } else {
                mensaje += "Verificar Origen del Doc.";
            }
            ok = false;
        }
        
        //System.out.println("Valida Eli");
        //System.out.println(origenes);
        //System.out.println(ls_origen_actual);
        //System.out.println(this.tipodocN);
        //System.out.println(this.remitenteN);        
        //validacion anti brutas!!!!!
        /*
		Usar la sentencia compareTo para las comparaciones. 
	    cadena1.compareTo(cadena2)==0; //cadena1 igual a cadena2 
		cadena1.compareTo(cadena2)!=0; //cadena1 diferente a cadena2 
		cadena1.compareTo(cadena2)<0; //cadena1 menor que cadena2 
		cadena1.compareTo(cadena2)>0; //cadena1 mator que cadena2 

         */
        /*String tipodocumento = "ATD0003";
        /*
        if (!this.tipodocN.equals(tipodocumento)){
        	if (ls_origen_actual==2 && this.remitenteN >= 601) {        		 	 
        			 mensaje += "Verificar el tipo de documento";
        			 ok = false;
        	 }        	
        }
        */
                       

        if (ok) {
            mensaje = "ok";
        }
        return mensaje;
    }

    public void eventVerDocs(ActionEvent actionEvent) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_login = String.valueOf(usuario.getCodarea());
        this.verdocs = true;
        // /Codigo Eli Diaz para el combo documentos entrante
        // List<EntranteBean> entrante = documentoSalidaDAO.entrante(remitente);
        // List itemsEntrante = Utils.getEntrante(entrante);
        // this.items4 = entrante;
        // this.documento = serviciosDocumentoSalida.catalogo(area);
        //this.entradasalida = documentoSalidaDAO.documentoentradaSP(area_login);
        //List entrante = this.entradasalida;
        //this.items4 = entrante;
        this.asuntos = documentoSalidaDAO.asuntos_estandares();
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
	        this.asuntos = documentoSalidaDAO.busqueda_asuntos_estandar(opcion_asunto, detalle_asunto);
	        List entrante = this.asuntos;
	        this.itemsAsuntos = entrante;
		 }

    }
    
    public void RefrescarAsunto(ActionEvent actionEvent){
		
		 this.asuntos = documentoSalidaDAO.asuntos_estandares();
	     List entrante = this.asuntos;
	     this.itemsAsuntos = entrante;	     
	     this.detalle_asunto="";
		
	}

    public void buscarDocs(ActionEvent actionEvent) {
        // long ncorrelativoentrada=Long.parseLong(detalleentrada);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        // int area = usuario.getNcodarea();
        String area_login = String.valueOf(usuario.getCodarea());
        // this.items4 =
        // documentoSalidaDAO.entrante_buscar(area,ncorrelativoentrada);
        // opcionentrada
        this.entradasalida = documentoSalidaDAO.busquedadocumentoentradaSP(
                area_login, opcion_entrada, detalle_entrada);
        List entrante = this.entradasalida;
        this.items4 = entrante;

    }

    public void RefrescarDocs(ActionEvent actionEvent) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_login = String.valueOf(usuario.getCodarea());
        // this.verdocs = true;
        // /Codigo Eli Diaz para el combo documentos entrante
        // List<EntranteBean> entrante = documentoSalidaDAO.entrante(remitente);
        // List itemsEntrante = Utils.getEntrante(entrante);
        // this.items4 = entrante;
        // this.documento = serviciosDocumentoSalida.catalogo(area);
        this.entradasalida = documentoSalidaDAO.documentoentradaSP(area_login);
        List entrante = this.entradasalida;
        this.items4 = entrante;
        this.detalle_entrada = "";

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
            this.asuntoN = bean.getDescripcion();
            this.asuntoA = bean.getDescripcion();
            //this.doc_entradaN = String.valueOf(bean.getNcorrelativo());
            //this.doc_entradaA = String.valueOf(bean.getNcorrelativo());            
            //correlativo = bean.getNcorrelativo();
           
            this.verdocs = false;
         
        }

    }

    public void cancelarDocs(ActionEvent actionEvent) {
        this.verdocs = false;
    }

    public ArrayList<DocumentoSalidaBean> getSelectedDocumentoss() {
        return selectedDocumentoss;
    }

    public void setSelectedDocumentoss(
            ArrayList<DocumentoSalidaBean> selectedDocumentoss) {
        this.selectedDocumentoss = selectedDocumentoss;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public List getItems3() {
        return items3;
    }

    public void setItems3(List items3) {
        this.items3 = items3;
    }

    public void setItems2(List items2) {
        this.items2 = items2;
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

    public void setItems5(List items5) {
        this.items5 = items5;
    }

    public List getItems6() {
        return items6;
    }

    public void setItems6(List items6) {
        this.items6 = items6;
    }

    public String getItemSeleccionado() {
        return itemSeleccionado;
    }

    public void setItemSeleccionado(String itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }

    public String getItem3Seleccionado() {
        return item3Seleccionado;
    }

    public void setItem3Seleccionado(String item3Seleccionado) {
        this.item3Seleccionado = item3Seleccionado;
    }

    public void setDocumentoSalidaDAO(DocumentoSalidaDAO documentoSalidaDAO) {
        this.documentoSalidaDAO = documentoSalidaDAO;
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

    public int getAnoA() {
        return anoA;
    }

    public void setAnoA(int anoA) {
        this.anoA = anoA;
    }

    public int getOrigenA() {
        return origenA;
    }

    public void setOrigenA(int origenA) {
        this.origenA = origenA;
    }

    public String getTipodocA() {
        return tipodocA;
    }

    public void setTipodocA(String tipodocA) {
        this.tipodocA = tipodocA;
    }

    public int getCodigoA() {
        return codigoA;
    }

    public void setCodigoA(int codigoA) {
        this.codigoA = codigoA;
    }

    public String getNumerodocA() {
        return numerodocA;
    }

    public void setNumerodocA(String numerodocA) {
        this.numerodocA = numerodocA;
    }

    public int getNdirigidoA() {
        return ndirigidoA;
    }

    public void setNdirigidoA(int ndirigidoA) {
        this.ndirigidoA = ndirigidoA;
    }

    public String getAsuntoA() {
        return asuntoA;
    }

    public void setAsuntoA(String asuntoA) {
        this.asuntoA = asuntoA;
    }

    public String getObservacionA() {
        return observacionA;
    }

    public void setObservacionA(String observacionA) {
        this.observacionA = observacionA;
    }

    public String getEstadoA() {
        return estadoA;
    }

    public void setEstadoA(String estadoA) {
        this.estadoA = estadoA;
    }

    public int getAreaA() {
        return areaA;
    }

    public void setAreaA(int areaA) {
        this.areaA = areaA;
    }

    public Date getFechaA() {
        return fechaA;
    }

    public void setFechaA(Date fechaA) {
        this.fechaA = fechaA;
    }

    public String getOrigenesA() {
        return origenesA;
    }

    public void setOrigenesA(String origenesA) {
        this.origenesA = origenesA;
    }

    public String getTrabajadorA() {
        return trabajadorA;
    }

    public void setTrabajadorA(String trabajadorA) {
        this.trabajadorA = trabajadorA;
    }

    public String getDirigidoA() {
        return dirigidoA;
    }

    public void setDirigidoA(String dirigidoA) {
        this.dirigidoA = dirigidoA;
    }

    public String getReferenciaA() {
        return referenciaA;
    }

    public void setReferenciaA(String referenciaA) {
        this.referenciaA = referenciaA;
    }

    public int getCodigoN() {
        return codigoN;
    }

    public void setCodigoN(int codigoN) {
        this.codigoN = codigoN;
    }

    public String getAnoN() {
        return anoN;
    }

    public void setAnoN(String anoN) {
        this.anoN = anoN;
    }

    public String getNumerodocN() {
        return numerodocN;
    }

    public void setNumerodocN(String numerodocN) {
        this.numerodocN = numerodocN;
    }

    public Date getFechaN() {
        return fechaN;
    }

    public void setFechaN(Date fechaN) {
        this.fechaN = fechaN;
    }

    public String getOrigenesN() {
        return origenesN;
    }

    public void setOrigenesN(String origenesN) {
        this.origenesN = origenesN;
    }

    public String getTipodocN() {
        return tipodocN;
    }

    public void setTipodocN(String tipodocN) {
        this.tipodocN = tipodocN;
    }

    public String getAsuntoN() {
        return asuntoN;
    }

    public void setAsuntoN(String asuntoN) {
        this.asuntoN = asuntoN;
    }

    public String getEstadoN() {
        return estadoN;
    }

    public void setEstadoN(String estadoN) {
        this.estadoN = estadoN;
    }

    public String getTrabajadorN() {
        return trabajadorN;
    }

    public void setTrabajadorN(String trabajadorN) {
        this.trabajadorN = trabajadorN;
    }

    public String getObservacionN() {
        return observacionN;
    }

    public void setObservacionN(String observacionN) {
        this.observacionN = observacionN;
    }

    public String getDirigidoN() {
        return dirigidoN;
    }

    public void setDirigidoN(String dirigidoN) {
        this.dirigidoN = dirigidoN;
    }

    public String getReferenciaN() {
        return referenciaN;
    }

    public void setReferenciaN(String referenciaN) {
        this.referenciaN = referenciaN;
    }

    public String getNom_areaA() {
        return nom_areaA;
    }

    public void setNom_areaA(String nomAreaA) {
        nom_areaA = nomAreaA;
    }

    public String getNom_areaN() {
        return nom_areaN;
    }

    public void setNom_areaN(String nomAreaN) {
        nom_areaN = nomAreaN;
    }

    public String getDoc_entradaA() {
        return doc_entradaA;
    }

    public void setDoc_entradaA(String docEntradaA) {
        doc_entradaA = docEntradaA;
    }

    public String getDoc_entradaN() {
        return doc_entradaN;
    }

    public void setDoc_entradaN(String docEntradaN) {
        doc_entradaN = docEntradaN;
    }

    public HtmlSelectOneMenu getCombo() {
        return combo;
    }

    public void setCombo(HtmlSelectOneMenu combo) {
        this.combo = combo;
    }

    public HtmlSelectOneMenu getCombo1() {
        return combo1;
    }

    public void setCombo1(HtmlSelectOneMenu combo1) {
        this.combo1 = combo1;
    }

    public List<DocumentoSalidaBean> getDocumento() {
        if ("ano".equals(this.getSortColumnName())) {
            this.sort(this.documento);
        }
        if ("dirigido".equals(this.getSortColumnName())) {
            this.sort(this.documento);
        }
        if ("tipodoc".equals(this.getSortColumnName())) {
            this.sort(this.documento);
        }
        if ("asunto".equals(this.getSortColumnName())) {
            this.sort(this.documento);
        }
        if ("fecha".equals(this.getSortColumnName())) {
            this.sort(this.documento);
        }
        if ("dfecplazo".equals(this.getSortColumnName())) {
            this.sort(this.documento);
        }
        if ("estado".equals(this.getSortColumnName())) {
            this.sort(this.documento);
        }

        //String col = this.getSortColumnName();
        //
        //"asunto".equals(col)
        //if ("ano".equals(col) || "dirigido".equals(col) 
        //		|| "tipodoc".equals(col) || "fecha".equals(col)
        //		|| "dfecplazo".equals(col) || "estado".equals(col)) {
        //	this.sort(this.documento);
        //}
        return this.documento;
    }

    public void setDocumento(List<DocumentoSalidaBean> documento) {
        this.documento = documento;
    }

    public long getFicha_dirigidoA() {
        return ficha_dirigidoA;
    }

    public void setFicha_dirigidoA(long fichaDirigidoA) {
        ficha_dirigidoA = fichaDirigidoA;
    }

    public long getFicha_dirigidoN() {
        return ficha_dirigidoN;
    }

    public void setFicha_dirigidoN(long fichaDirigidoN) {
        ficha_dirigidoN = fichaDirigidoN;
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

    public int getAreaN() {
        return areaN;
    }

    public void setAreaN(int areaN) {
        this.areaN = areaN;
    }

    public String getPrioridadA() {
        return prioridadA;
    }

    public void setPrioridadA(String prioridadA) {
        this.prioridadA = prioridadA;
    }

    public String getPrioridadN() {
        return prioridadN;
    }

    public void setPrioridadN(String prioridadN) {
        this.prioridadN = prioridadN;
    }

    public int getNdirigidoN() {
        return ndirigidoN;
    }

    public void setNdirigidoN(int ndirigidoN) {
        this.ndirigidoN = ndirigidoN;
    }

    public String getNtipodocA() {
        return ntipodocA;
    }

    public void setNtipodocA(String ntipodocA) {
        this.ntipodocA = ntipodocA;
    }

    public String getNtipodocN() {
        return ntipodocN;
    }

    public void setNtipodocN(String ntipodocN) {
        this.ntipodocN = ntipodocN;
    }

    public String getNestadoA() {
        return nestadoA;
    }

    public void setNestadoA(String nestadoA) {
        this.nestadoA = nestadoA;
    }

    public String getNestadoN() {
        return nestadoN;
    }

    public void setNestadoN(String nestadoN) {
        this.nestadoN = nestadoN;
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

    public String getItem2Seleccionado() {
        return item2Seleccionado;
    }

    public void setItem2Seleccionado(String item2Seleccionado) {
        this.item2Seleccionado = item2Seleccionado;
    }

    public boolean isVerDetalles() {
        return verDetalles;
    }

    public void setVerDetalles(boolean verDetalles) {
        this.verDetalles = verDetalles;
    }

    public boolean isVerCatalogoEntrada() {
        return verCatalogoEntrada;
    }

    public void setVerCatalogoEntrada(boolean verCatalogoEntrada) {
        this.verCatalogoEntrada = verCatalogoEntrada;
    }

    public String getUbicararchivoA() {
        return ubicararchivoA;
    }

    public void setUbicararchivoA(String ubicararchivoA) {
        this.ubicararchivoA = ubicararchivoA;
    }

    public String getUbicararchivoN() {
        return ubicararchivoN;
    }

    public void setUbicararchivoN(String ubicararchivoN) {
        this.ubicararchivoN = ubicararchivoN;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public String getOpcion_seguimiento() {
        return opcion_seguimiento;
    }

    public void setOpcion_seguimiento(String opcionSeguimiento) {
        opcion_seguimiento = opcionSeguimiento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDetallecriterio() {
        return detallecriterio;
    }

    public void setDetallecriterio(String detallecriterio) {
        this.detallecriterio = detallecriterio;
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

    public String getComentarioA() {
        return comentarioA;
    }

    public void setComentarioA(String comentarioA) {
        this.comentarioA = comentarioA;
    }

    public String getComentarioN() {
        return comentarioN;
    }

    public void setComentarioN(String comentarioN) {
        this.comentarioN = comentarioN;
    }

    public Date getDfecplazoA() {
        return dfecplazoA;
    }

    public void setDfecplazoA(Date dfecplazoA) {
        this.dfecplazoA = dfecplazoA;
    }

    public Date getDfecplazoN() {
        return dfecplazoN;
    }

    public void setDfecplazoN(Date dfecplazoN) {
        this.dfecplazoN = dfecplazoN;
    }

    public int getRemitenteA() {
        return remitenteA;
    }

    public void setRemitenteA(int remitenteA) {
        this.remitenteA = remitenteA;
    }

    public int getRemitenteN() {
        return remitenteN;
    }

    public void setRemitenteN(int remitenteN) {
        this.remitenteN = remitenteN;
    }

    public long getFicha_remiteA() {
        return ficha_remiteA;
    }

    public void setFicha_remiteA(long fichaRemiteA) {
        ficha_remiteA = fichaRemiteA;
    }

    public long getFicha_remiteN() {
        return ficha_remiteN;
    }

    public void setFicha_remiteN(long fichaRemiteN) {
        ficha_remiteN = fichaRemiteN;
    }

    public String getAreaseleccionadaN() {
        return areaseleccionadaN;
    }

    public void setAreaseleccionadaN(String areaseleccionadaN) {
        this.areaseleccionadaN = areaseleccionadaN;
    }

    public String getAreadeseleccionadaN() {
        return areadeseleccionadaN;
    }

    public void setAreadeseleccionadaN(String areadeseleccionadaN) {
        this.areadeseleccionadaN = areadeseleccionadaN;
    }

    public String getVdirigidosA() {
        return vdirigidosA;
    }

    public void setVdirigidosA(String vdirigidosA) {
        this.vdirigidosA = vdirigidosA;
    }

    public String getVdirigidosN() {
        return vdirigidosN;
    }

    public void setVdirigidosN(String vdirigidosN) {
        this.vdirigidosN = vdirigidosN;
    }

    public int getDiasA() {
        return diasA;
    }

    public void setDiasA(int diasA) {
        this.diasA = diasA;
    }

    public int getDiasN() {
        return diasN;
    }

    public void setDiasN(int diasN) {
        this.diasN = diasN;
    }

    public void setmCarga(MCarga mCarga) {
        this.mCarga = mCarga;
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

    public String getVaccionA() {
        return vaccionA;
    }

    public void setVaccionA(String vaccionA) {
        this.vaccionA = vaccionA;
    }

    public String getVaccionN() {
        return vaccionN;
    }

    public void setVaccionN(String vaccionN) {
        this.vaccionN = vaccionN;
    }

    public LinkedHashMap<String, Object> getObservaciones1() {
        return observaciones1;
    }

    public List<String> getSelectedItems1() {
        return selectedItems1;
    }

    public void setSelectedItems1(List<String> selectedItems1) {
        this.selectedItems1 = selectedItems1;
    }

    public String getAresseleccionasA() {
        return aresseleccionasA;
    }

    public void setAresseleccionasA(String aresseleccionasA) {
        this.aresseleccionasA = aresseleccionasA;
    }

    public int getIndicaadjuntoA() {
        return indicaadjuntoA;
    }

    public void setIndicaadjuntoA(int indicaadjuntoA) {
        this.indicaadjuntoA = indicaadjuntoA;
    }

    public List getItems7() {
        return items7;
    }

    public void setItems7(List items7) {
        this.items7 = items7;
    }

    public RecursoReport getReporte() {
        return reporte;
    }

    public List getItems8() {
        return items8;
    }

    public void setItems8(List items8) {
        this.items8 = items8;
    }

    public void setReporte(RecursoReport reporte) {
        this.reporte = reporte;
    }

    // para impresion
    public RecursoReport getRecursoReport() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        //guardando en sesion un objeto
        Usuario usuario = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        usuario = (Usuario) session.getAttribute("sUsuario");
        int dirigido = usuario.getCodarea();
        String area_origen = String.valueOf(dirigido);
        //String annio = Integer.toString(c.get(this.getDate1().getYear()));
        String annio = Integer.toString(c.get(Calendar.YEAR));
        String area_logeado = String.valueOf(usuario.getNomequipo());
        //String tipodoc = "MEMORANDO";
        this.tipodocarea = this.getItemSeleccionado();
        
        //(this.estadoSeleccionado != "")
        if (this.tipodocarea != "ATD0000") {
            List<TiposBean> bean = documentoSalidaDAO.tipodoc(areas_actuales, this.tipodocarea);
            for (TiposBean e : bean) {
                tipodocarea = e.getDescripcion();
            }
            System.out.println("Eli Captura el tipo doc");
            System.out.println(tipodocarea);
            //this.tipodocarea="MEMORANDO";
        }


        ec = FacesContext.getCurrentInstance().getExternalContext();
        System.out.println("Parametros : ");
        System.out.println("P_AREA        : " + area);
        System.out.println("P_AREA_ORIGEN : " + area_logeado);
        System.out.println("P_INICIO : " + String.valueOf(sdf.format(this.getDate1())));
        System.out.println("P_FIN   : " + String.valueOf(sdf.format(this.getDate1())));
        System.out.println("P_TIPO  : " + this.getItemSeleccionado());
        System.out.println("P_TIPODOC   : " + tipodocarea);
        System.out.println("P_ANNO   : " + String.valueOf(annio));
        parametros.put("P_AREA", area);
        parametros.put("P_AREA_ORIGEN", area_logeado);
        parametros.put("P_INICIO", String.valueOf(sdf.format(this.getDate1())));
        parametros.put("P_FIN", String.valueOf(sdf.format(this.getDate2())));
        parametros.put("P_TIPO", this.getItemSeleccionado());
        parametros.put("P_TIPODOC", tipodocarea);
        parametros.put("P_ANNO", String.valueOf(annio));
        reporte.asignar("reportedespacho.pdf", ec, parametros,
                "reportes/reportedespacho.jasper");// se le puede adicionar parametros...
		/*Eli Cambia de Reporte de Documento de Salida
         reporte.asignar("reporteSalida.pdf", ec, parametros,
         "reportes/salida.jasper");// se le puede adicionar parametros...
         */
        recursoReport = reporte;
        return recursoReport;
        
        
        
    }
    
    
    public RecursoReporteNuevo getRecursoReporteNuevo() {
        HttpSession session = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
       
        session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_logeado = String.valueOf(usuario.getNomequipo());
        session.getAttribute("sUsuario");
        
            ec2 = FacesContext.getCurrentInstance().getExternalContext();
				
			System.out.println("Estoy" + opcion);
    		System.out.println("Parametros : ");
    	    System.out.println("P_OPCION : " + this.tipoopcion);
    	    System.out.println("===================");
    	    System.out.println("P_AREA        : " + area);
    	    System.out.println("P_FECHAINICIO : " + String.valueOf(sdf.format(this.getDate1())));
    	    System.out.println("P_FECHAFIN   : " + String.valueOf(sdf.format(this.getDate2())));        	    
    	    System.out.println("P_AREA_DESC : " + area_logeado);        	            
    	    System.out.println("P_REMITE : " + this.getItemareaSeleccionado());
            
            parametros2.put("P_AREA", area);
            parametros2.put("P_FECHAINICIO", String.valueOf(sdf.format(this.getDate1())));
            parametros2.put("P_FECHAFIN", String.valueOf(sdf.format(this.getDate2())));
            parametros2.put("P_AREA_DESC", area_logeado);            
            parametros2.put("P_REMITE", this.getItemareaSeleccionado());
           
            reporteNuevo.asignar("reportedespachoremite.pdf", ec2, parametros2, "reportes/rpt_despacho_por_remitente.jasper");           
            recursoReporteNuevo = reporteNuevo;
            return recursoReporteNuevo;

    }
	
    public ExternalContext getEc() {
        return ec;
    }

    public void setEc(ExternalContext ec) {
        this.ec = ec;
    }

    public void setRecursoReport(RecursoReport recursoReport) {
        this.recursoReport = recursoReport;
    }

    public boolean isVerdocs() {
        return verdocs;
    }

    public void setVerdocs(boolean verdocs) {
        this.verdocs = verdocs;
    }

    public boolean isRadioseguimiento() {
        return radioseguimiento;
    }

    public void setRadioseguimiento(boolean radioseguimiento) {
        this.radioseguimiento = radioseguimiento;
    }

    public void setSecuencialDAO(SecuencialDAO secuencialDAO) {
        this.secuencialDAO = secuencialDAO;
    }

    public boolean isVerAlertaConfirmacionBorrar() {
        return verAlertaConfirmacionBorrar;
    }

    public void setVerAlertaConfirmacionBorrar(
            boolean verAlertaConfirmacionBorrar) {
        this.verAlertaConfirmacionBorrar = verAlertaConfirmacionBorrar;
    }
    
    

    public boolean isVerAlertaConfirmacionGrabar() {
		return verAlertaConfirmacionGrabar;
	}

	public void setVerAlertaConfirmacionGrabar(boolean verAlertaConfirmacionGrabar) {
		this.verAlertaConfirmacionGrabar = verAlertaConfirmacionGrabar;
	}

	public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getVremitenteN() {
        return vremitenteN;
    }

    public void setVremitenteN(String vremitenteN) {
        this.vremitenteN = vremitenteN;
    }

    public boolean isEditarEmpresa() {
        return editarEmpresa;
    }

    public void setEditarEmpresa(boolean editarEmpresa) {
        this.editarEmpresa = editarEmpresa;
    }

    public boolean isDisBotonGrabar() {
        return disBotonGrabar;
    }

    public void setDisBotonGrabar(boolean disBotonGrabar) {
        this.disBotonGrabar = disBotonGrabar;
    }

    public String getTextoA() {
        return textoA;
    }

    public void setTextoA(String textoA) {
        this.textoA = textoA;
    }

    public int getNindicadorN() {
        return nindicadorN;
    }

    public void setNindicadorN(int nindicadorN) {
        this.nindicadorN = nindicadorN;
    }

    public int getIndicaadjuntoN() {
        return indicaadjuntoN;
    }

    public void setIndicaadjuntoN(int indicaadjuntoN) {
        this.indicaadjuntoN = indicaadjuntoN;
    }

    public String getOpcionentrada() {
        return opcionentrada;
    }

    public void setOpcionentrada(String opcionentrada) {
        this.opcionentrada = opcionentrada;
    }

    public String getDetalleentrada() {
        return detalleentrada;
    }

    public void setDetalleentrada(String detalleentrada) {
        this.detalleentrada = detalleentrada;
    }

    public List<EntranteBean> getEntradasalida() {
        return entradasalida;
    }

    public void setEntradasalida(List<EntranteBean> entradasalida) {
        this.entradasalida = entradasalida;
    }

    public List<AreaBean> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaBean> areas) {
        this.areas = areas;
    }

    public List<Seguir> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<Seguir> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public List<AreaBean> getAreasseleccionados() {
        return areasseleccionados;
    }

    public void setAreasseleccionados(List<AreaBean> areasseleccionados) {
        this.areasseleccionados = areasseleccionados;
    }

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public String getOpcion_entrada() {
        return opcion_entrada;
    }

    public void setOpcion_entrada(String opcionEntrada) {
        opcion_entrada = opcionEntrada;
    }

    public String getDetalle_entrada() {
        return detalle_entrada;
    }

    public void setDetalle_entrada(String detalleEntrada) {
        detalle_entrada = detalleEntrada;
    }

    public String getVtipo() {
        return vtipo;
    }

    public void setVtipo(String vtipo) {
        this.vtipo = vtipo;
    }

    public String getAnodocumentoentrA() {
        return anodocumentoentrA;
    }

    public void setAnodocumentoentrA(String anodocumentoentrA) {
        this.anodocumentoentrA = anodocumentoentrA;
    }

    public String getAnodocumentoentrN() {
        return anodocumentoentrN;
    }

    public void setAnodocumentoentrN(String anodocumentoentrN) {
        this.anodocumentoentrN = anodocumentoentrN;
    }

    public String getNdirigidoAA() {
        return ndirigidoAA;
    }

    public void setNdirigidoAA(String ndirigidoAA) {
        this.ndirigidoAA = ndirigidoAA;
    }

    public List getItems9() {
        return items9;
    }

    public void setItems9(List items9) {
        this.items9 = items9;
    }

    public boolean isDisEstado() {
        return disEstado;
    }

    public void setDisEstado(boolean disEstado) {
        this.disEstado = disEstado;
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

    public int getOrigenN() {
        return origenN;
    }

    public void setOrigenN(int origenN) {
        this.origenN = origenN;
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

	public List<SelectItem> getItemsPersonalRepresentantesA() {
		return itemsPersonalRepresentantesA;
	}

	public void setItemsPersonalRepresentantesA(
			List<SelectItem> itemsPersonalRepresentantesA) {
		this.itemsPersonalRepresentantesA = itemsPersonalRepresentantesA;
	}

	public String getDerivadoAA() {
		return derivadoAA;
	}

	public void setDerivadoAA(String derivadoAA) {
		this.derivadoAA = derivadoAA;
	}

	public List getItems10() {
		return items10;
	}

	public void setItems10(List items10) {
		this.items10 = items10;
	}

	public int getIndicador() {
		return indicador;
	}

	public void setIndicador(int indicador) {
		this.indicador = indicador;
	}

	public String getValida3() {
		return valida3;
	}

	public void setValida3(String valida3) {
		this.valida3 = valida3;
	}

	public String getMsgnuevo() {
		return msgnuevo;
	}

	public void setMsgnuevo(String msgnuevo) {
		this.msgnuevo = msgnuevo;
	}

	public String getNombreremitenteA() {
		return nombreremitenteA;
	}

	public void setNombreremitenteA(String nombreremitenteA) {
		this.nombreremitenteA = nombreremitenteA;
	}

	public String getNombreremitenteN() {
		return nombreremitenteN;
	}

	public void setNombreremitenteN(String nombreremitenteN) {
		this.nombreremitenteN = nombreremitenteN;
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

	public boolean isVerEliminar() {
		return verEliminar;
	}

	public void setVerEliminar(boolean verEliminar) {
		this.verEliminar = verEliminar;
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

	public List getItemscombo() {
		return itemscombo;
	}

	public void setItemscombo(List itemscombo) {
		this.itemscombo = itemscombo;
	}

	public HtmlSelectOneMenu getComboarea() {
		return comboarea;
	}

	public void setComboarea(HtmlSelectOneMenu comboarea) {
		this.comboarea = comboarea;
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

	public String getTipoopcion() {
		return tipoopcion;
	}

	public void setTipoopcion(String tipoopcion) {
		this.tipoopcion = tipoopcion;
	}

	public String getItemareaSeleccionado() {
		return itemareaSeleccionado;
	}

	public void setItemareaSeleccionado(String itemareaSeleccionado) {
		this.itemareaSeleccionado = itemareaSeleccionado;
	}

	public List getItems11() {
		return items11;
	}

	public void setItems11(List items11) {
		this.items11 = items11;
	}

	public boolean isVerReport1() {
		return verReport1;
	}

	public void setVerReport1(boolean verReport1) {
		this.verReport1 = verReport1;
	}

	public boolean isVerReport2() {
		return verReport2;
	}

	public void setVerReport2(boolean verReport2) {
		this.verReport2 = verReport2;
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

	public ExternalContext getEc2() {
		return ec2;
	}

	public void setEc2(ExternalContext ec2) {
		this.ec2 = ec2;
	}

	public List getItemsanno() {
		return itemsanno;
	}

	public void setItemsanno(List itemsanno) {
		this.itemsanno = itemsanno;
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

	public String getVcodigo_verificadorA() {
		return vcodigo_verificadorA;
	}

	public void setVcodigo_verificadorA(String vcodigo_verificadorA) {
		this.vcodigo_verificadorA = vcodigo_verificadorA;
	}

	public String getLink_acceso() {
		return link_acceso;
	}

	public void setLink_acceso(String link_acceso) {
		this.link_acceso = link_acceso;
	}

	public int getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(int notificacion) {
		this.notificacion = notificacion;
	}

	public int getNficha_jefe_equipoA() {
		return nficha_jefe_equipoA;
	}

	public void setNficha_jefe_equipoA(int nficha_jefe_equipoA) {
		this.nficha_jefe_equipoA = nficha_jefe_equipoA;
	}

	public String getNotificadorA() {
		return notificadorA;
	}

	public void setNotificadorA(String notificadorA) {
		this.notificadorA = notificadorA;
	}

	public boolean isDisnotificador() {
		return disnotificador;
	}

	public void setDisnotificador(boolean disnotificador) {
		this.disnotificador = disnotificador;
	}

	public String getNotificadorN() {
		return notificadorN;
	}

	public void setNotificadorN(String notificadorN) {
		this.notificadorN = notificadorN;
	}

	public int getLi_ficha_secretaria() {
		return li_ficha_secretaria;
	}

	public void setLi_ficha_secretaria(int li_ficha_secretaria) {
		this.li_ficha_secretaria = li_ficha_secretaria;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getVestado_secretaria() {
		return vestado_secretaria;
	}

	public void setVestado_secretaria(String vestado_secretaria) {
		this.vestado_secretaria = vestado_secretaria;
	}

	public int getLi_cantidad_secretaria() {
		return li_cantidad_secretaria;
	}

	public void setLi_cantidad_secretaria(int li_cantidad_secretaria) {
		this.li_cantidad_secretaria = li_cantidad_secretaria;
	}

		
//	 public void buscarRemitente(ActionEvent event) {
//
//	       String tipo;
//	       String valor;
//	       List<SelectItem> list = new ArrayList<SelectItem>();
//	       List<RemitenteBPM> lista  = new ArrayList<RemitenteBPM>();
//	       this.posiblespersonas = list;
//	       tipo=this.remitente_conceptoBuscar;
//	       valor=this.remitente_elementoBuscar;
//	       ls_origen_actual=2;
//	       if (tipo.equals("DOC000A")) {  //buscar areas por nombre
//	    	   lista = entranteDAO.consultaAreas(valor);
//	    	   ls_origen_actual=2;
//	       } else {
//	    	   lista = entranteDAO.consultaRemitentesBPM(tipo,valor);   
//	       }
//	       
//	       this.listaRemitentesBPM = lista;
//	       if (lista.size()>0) {
//	    	// inicializamos un bucle for para recorrer el objeto persona     
//	           for (RemitenteBPM p : lista) {
//	                   SelectItem item = new SelectItem(p.getNcorrelativo(), p.getNombre());
//	                   list.add(item);
//	           }
//	           this.posiblespersonas = list;
//	       } else {
//	    	   UsuarioExternoResponse respuesta =null;
//	    	   if (tipo.equals("DOC0000")) { //la busqueda es por nombre
//	    		 //  respuesta = this.consultaPorNombre(valor);
//	    		   respuesta = null;
//	    	   } else { // la busqueda es por tipo de documento
//	    		   if (!tipo.equals("DOC000A")) { 
//	    			   respuesta = this.consultaPorTipoDocumento(new DocumentoDTO(tipo, valor));   
//	    		   }	    		  
//	    	   }
//	    	   if (respuesta==null) { //no existe en BPM, se muestra mensaje
//	    			this.error="Usuario BPM no existe";
//  				  	this.ver=true;  
//	    	   } else { // existe en BPM, se adiciona
//	    		   RemitenteBPM nuevoRemitente = new RemitenteBPM();
//	    		   nuevoRemitente = registraRemitenteBPM(respuesta);
//	    		   ls_origen_actual=2;
//	    		   if (nuevoRemitente != null) {
//	    			   SelectItem item = new SelectItem(nuevoRemitente.getNcorrelativo(), nuevoRemitente.getNombre());
//	                   list.add(item);
//	                   this.listaRemitentesBPM = new ArrayList<RemitenteBPM>();
//	                   this.listaRemitentesBPM.add(nuevoRemitente);
//	                   this.posiblespersonas = list;
//	    		   }
//	    	   }
//	       }
//	       
//	       System.out.println(lista.size());
//	    }   
	 
	 private RemitenteBPM registraRemitenteBPM(UsuarioExternoResponse response) {
		 RemitenteBPM remitente = new RemitenteBPM();
		 remitente.setCorreo(response.getMail());
		 remitente.setDireccion(response.getDireccion());
		 remitente.setIndCasilla(Integer.parseInt(response.getCasilla()));
		 remitente.setNombre(response.getNombreCompleto());
		 remitente.setNroDocumento(response.getNumeroDocumento());
		 //remitente.setTelefono(response.getTelefono());
		 remitente.setTipoDocumento(response.getTipoDocumento());
		 Integer correlativo;
		 correlativo = entranteDAO.registraRemitentesBPM(remitente);
		 if (correlativo > 0) {
			 remitente.setNcorrelativo(new Long(correlativo));
			 return remitente;
		 }
		 return null;
	 }
	
	 
	 public void pasarItems(ActionEvent actionEvent) {

	        Seguir seguir = new Seguir();
	        if (indicador ==0) {
	        	String nombre = this.buscarNombre(this.derivadoN);
	        	
	        	RemitenteBPM itemBPM = new RemitenteBPM();
	        	Seguir bean2 = new Seguir();
	        	if (this.nuevoDirigido == false ) {
	        		 for (RemitenteBPM p : listaRemitentesBPM) {
						 if (p.getNcorrelativo().equals(new Long(this.remitenteSeleccionado))) {
							 itemBPM = p;
						 }
					 }
	        		 if (itemBPM != null) {				 
	 					bean2.setEstado(this.estadoSeleccionado);
	 					bean2.setArea(itemBPM.getNcorrelativo().toString());
	 					bean2.setCodArea(itemBPM.getNombre());
	 					bean2.setNombreTrabajador(itemBPM.getNombre());
	 					bean2.setnIndRemitente(itemBPM.getTipoDocumento() == "0" ? 3 : 2);
	 					bean2.setVnumdoc(itemBPM.getNroDocumento());
	 					bean2.setVtipdoc(itemBPM.getTipoDocumento());
	 					bean2.setCodArea(nombre);		
	 					bean2.setFicha(this.derivadoN);
	 					bean2.setEstado(this.estadoSeleccionado);
	 				 }
	        	} else {
	        		this.nuevoDirigido = false;
	        		bean2.setEstado(this.estadoSeleccionado);
 					bean2.setArea(this.derivadoN);
 					bean2.setCodArea(this.derivadoN);
 					bean2.setNombreTrabajador(nombre);
 					bean2.setnIndRemitente(1);
 					bean2.setVnumdoc(null);
 					bean2.setVtipdoc(null);
 					bean2.setCodArea(nombre);		
 					bean2.setFicha(this.derivadoN);
 					bean2.setEstado(this.estadoSeleccionado);
	        	}
	        	 
				
				 
				
	            seguir.setCodArea(nombre);        
	            seguir.setEstado(this.estadoSeleccionado);// se muestra en la tabla
	            seguir.setNombreTrabajador(this.dirigidoN);// se muestra en la tabla

	            SelectItem item = null;
	            System.out.println("Personal Representante");
	            System.out.println(this.itemsPersonalRepresentantes.size());
	            if (this.estadoSeleccionado != ""  && this.itemsPersonalRepresentantes.size()>0) {
	                // adiconando el objeto en la tabla
	                boolean ok = false;
	                ok = this.buscar(seguir, this.seleccionados);
	                if (!ok) {
	                    for (int i = 0; i < this.items1.size(); i++) {
	                    	//System.out.println("Eli pinta items1");
	                    	//System.out.println(this.items1.size());
	                        item = (SelectItem) this.items1.get(i);
	                        String area = item.getValue().toString();
	                        //System.out.println("Eli esta aqui");
	                        System.out.println("Pinto la comparacion");
	                        System.out.println(area);
	                        System.out.println(this.dirigidoN);
	                        if (area.equals(this.dirigidoN)) {
	                            seguir.setArea(this.dirigidoN); // CF Equipo o Empresa  // break;
	                            seguir.setNombreTrabajador(item.getLabel()); // para mostrar en la tabla
	                            seguir.setFicha(this.derivadoN);// Codigo del trabajador
	                            //System.out.println("pasarItems");
	                            //System.out.println(this.dirigidoN);
	                            //System.out.println(item.getLabel());
	                            //System.out.println(this.derivadoN);
	                            // o representante

	                            //SED-FON-161
	                            ls_origen_actual = this.origenes;
	                            if (Integer.parseInt(this.dirigidoN) > 601) {
	                                //ls_origen_actual = 2;
	                            }
	                            
	                            boolean valido=true;
	                            if (itemBPM != null) {	
	                            	for (int j=0; j<this.seleccionados.size(); j++ ) {
	                    				if (Integer.parseInt(bean2.getArea())==Integer.parseInt(this.seleccionados.get(j).getArea())) {
	                    					this.error="Area usuaria ya registrada";
	                      				  	this.ver=true;
	                      				  	valido=false;
	                    				}
	                    			}
	                            	if (valido) {
	                            		this.seleccionados.add(bean2);
	                            	}
	                            	  
	                            } else {
	                            	for (int j=0; j<this.seleccionados.size(); j++ ) {
	                    				if (Integer.parseInt(seguir.getArea())==Integer.parseInt(this.seleccionados.get(j).getArea())) {
	                    					
	                    					this.error="Usuario BPM ya registrado";
	                      				  	this.ver=true;
	                      				  	valido=false;
	                    				}
	                    			}
	                            	if (valido) {
	                            		this.seleccionados.add(seguir);
	                            	}
	                            }
	                          
	                            //this.seleccionados.add(seguir);
	                            this.personaSeleccionada = "";
	                            this.itemsPersonalRepresentantes.clear();
	                        }
	                    }
	                }
	            } else {
	                this.error = "Seleccione un tipo Destinatario o no tiene Representante !!";
	                this.ver = true;

	            }
	        	
	        } else {
	        	
	        	 
	        	 
	        	 List<GrupoBean> bean = this.documentoSalidaDAO.grupo(codigo);
	        	 int dato = bean.size();
	        	 SelectItem item = null;
	        	 //System.out.println("Pinto Cantidad");
	        	 //System.out.println(dato);
	        	 boolean ok = false;
	             ok = this.buscar(seguir, this.seleccionados);
	             if (!ok) { 
	            	 
	             for (GrupoBean e : bean) {	 
	                 int area = e.getCodigo();
	                 //areas = String.valueOf(e.getCodigo());
	                 List<JefeBean> jefe = this.documentoSalidaDAO.jefe_grupo(area);
	                 for (JefeBean j : jefe){
	                	 	String codigoarea = String.valueOf(j.getNcodarea());
	                	// if (area == j.getNcodarea()){  
	                	   for (int i = 0; i < this.items1.size(); i++) {                         	
	                         item = (SelectItem) this.items1.get(i);
	                         String varea = item.getValue().toString();
	                         //System.out.println("Pinto la comparacion");
	                 	 	 //System.out.println(varea);
	                 	 	 //System.out.println(j.getNcodarea());   
	                         seguir = new Seguir();
	                         if (varea.equals(codigoarea)){                        	 
	                        	 //System.out.println("Veo el nombresssssss");
	                        	 //System.out.println(j.getNcodarea());
	                        	 //System.out.println(j.getNombrearea());
	                        	 //System.out.println(j.getFicha());
	                        	 //System.out.println(j.getNombre());
	                        	 //System.out.println(dato);
		                		 seguir.setArea(String.valueOf(j.getNcodarea()));
		                    	 seguir.setNombreTrabajador(j.getNombrearea());
		                    	 seguir.setFicha(String.valueOf(j.getFicha()));
		                    	 seguir.setCodArea(j.getNombre());
		                    	 seguir.setEstado(this.estadoSeleccionado); 
		                    	 this.seleccionados.add(seguir);
		                         this.personaSeleccionada = ""; 
	                        	 }
	                                                  	
	                	 	}
	                	   
	                	 }
	                
	              }
	             
	            	               	 
	             
	             }
	        	
	        	
	        }
	        
	    }
	 public void pasarItems2(ActionEvent actionEvent) {
		 System.out.println(this.personaSeleccionada);
		 Long idSeleccionado = Long.parseLong(this.dirigidoN);
	    if (this.estadoSeleccionado != ""  && this.listaRemitentesBPM.size()>0) {
	    	 RemitenteBPM itemBPM = new RemitenteBPM();
			 for (RemitenteBPM p : listaRemitentesBPM) {
				 if (p.getNcorrelativo().equals(idSeleccionado)) {
					 itemBPM = p;
				 }
			 }
			 Seguir bean2 = new Seguir();
				bean2.setEstado(this.estadoSeleccionado);
				bean2.setArea(itemBPM.getTipoDocumento() == "0" ? itemBPM.getNroDocumento().toString() : itemBPM.getNcorrelativo().toString());
				bean2.setCodArea(itemBPM.getNombre());
				bean2.setNombreTrabajador(itemBPM.getNombre());
				bean2.setnIndRemitente(itemBPM.getTipoDocumento() == "0" ? 3 : 2);
				bean2.setVnumdoc(itemBPM.getNroDocumento());
				bean2.setVtipdoc(itemBPM.getTipoDocumento());
				bean2.setCodArea("");		
			boolean valido=true;	
				
				for (int j=0; j<this.seleccionados.size(); j++ ) {
    				if (Integer.parseInt(bean2.getArea())==Integer.parseInt(this.seleccionados.get(j).getArea())) {
    					this.error="Usuario BPM ya registrado";
      				  	this.ver=true;
      				  	valido=false;
    				}
    			}
				if (valido) {
					bean2.setFicha("0");
					this.seleccionados.add(bean2);	
				}
				
	    }
		
	 }

	public String getLs_anexosA() {
		return ls_anexosA;
	}

	public void setLs_anexosA(String ls_anexosA) {
		this.ls_anexosA = ls_anexosA;
	}

	public String getLs_anexosN() {
		return ls_anexosN;
	}

	public void setLs_anexosN(String ls_anexosN) {
		this.ls_anexosN = ls_anexosN;
	}
	 
	 
	 

}