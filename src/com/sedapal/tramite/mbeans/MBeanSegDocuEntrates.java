package com.sedapal.tramite.mbeans;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.Seguir;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.SecuencialDAO;
import com.sedapal.tramite.dao.SeguimientoEntranteDAO;
import com.sedapal.tramite.servicios.IServiciosSeguimientoEntrante;
import com.sedapal.tramite.servicios.IServiciosUsuarios;
import com.sedapal.tramite.servicios.util.MailService;
import com.sedapal.tramite.util.Utils;

public class MBeanSegDocuEntrates implements IMBeanSegDocuEntrantes, Serializable {
	
	//Nombre : CAPA DE NEGOCIO : SEGUIMIENTO DE DOCUMENTO
	//Autor  : ELI DIAZ HORNA
	//Fecha Creacion: 30/11/2009
	//Fecha Actualiza: 15/06/2015
	
	private int nindicadorC;
    private boolean verAlertaConfirmacionBorrar;
    private String msg;
    private MCarga mCarga;
    private List<SeguimientoEntranteBean> seguimiento;
    private List<SeguimientoEntranteBean> seguimiento_total;
    private List<SeguimientoEntranteBean> busca_seguimiento;
    public IServiciosSeguimientoEntrante serviciosseguimientoDocuEntrante;
    private SecuencialDAO secuencialDAO;
    Calendar c = Calendar.getInstance();
    private List items1;
    private List items2;
    private List items3;
    private List items4;
    private List items5;
    private List items6;
    private List items7;
    private Date date1 = new Date();
    private Date date2 = new Date();
    private String itemSeleccionado;
    //private String item1Seleccionado;
    //private String item2Seleccionado;
    //private String item3Seleccionado;
    private String error = "Se grabó Satisfactoriamente";    	
    // para el formulario Actualizar
    private int anoA;
    private String origenA;
    private int norigenA;
    private String tipodocA;
    private long correlativoA;
    private String numdocA;
    private String asuntoA;
    private long ficha_dirigidoA;
    private long derivadoA;
    private Date fecderivadoA;
    private String observacionA;
    private Date fecrecepcionA;
    private Date fecplazoA;
    private String estadoA;
    private Date feccreA;
    private Date fecactA;
    private String rescreA;
    private String resactA;
    private String nombre_derivadoA;
    private String accionA;
    private String comentarioA;
    private int seguimientoA;
    private String vestadoA;
    private String vaccionA;
    private int areaderivadoA;
    private String codareaderivadoA;
    private int arearemitenteA;
    private String codarearemitenteA;
    private int ficharemitenteA;
    private String nombre_remitenteA;
    private int indicadorA;
    private String ubicaarchivoA;
    private String ubicaarchivoorigenA;
    private int indicaarchivoA;
    private Date fechaplazoA;
    private int diasplazoA;
    private int nrecepcionA;
    private int nplazoA;
    private String vprioridadA;
    //para formulario nuevo
    private int anoN;
    private String origenN;
    private int norigenN;
    private String tipodocN;
    private long correlativoN;
    private String numdocN;
    private String asuntoN;
    private long ficha_dirigidoN;
    private long derivadoN;
    private Date fecderivadoN;
    private String observacionN;
    private Date fecrecepcionN;
    private Date fecplazoN;
    private String estadoN;
    private Date feccreN;
    private Date fecactN;
    private String rescreN;
    private String resactN;
    private String nombre_derivadoN;
    private String accionN;
    private String comentarioN;
    
    private int seguimientoN;
    private String vestadoN;
    private String vaccionN;
    private int areaderivadoN;
    private String codareaderivadoN;
    private int arearemitenteN;
    private String codarearemitenteN;
    private int ficharemitenteN;
    private String nombre_remitenteN;
    private int indicadorN;
    private String ubicaarchivoN;
    private int indicaarchivoN;
    private Date fechaplazoN;
    private int diasplazoN;
    private int nrecepcionN;
    private int nplazoN;
    private String vprioridadN;
    //para formulario copia
    private String ubicaarchivoC = "NADA";
    private String comentarioC;
    private String ubicaposicion;
    // Detalle
    private long seguimiento_docD;
    private String numero_docD;
    //
    private boolean verNuevo = false;
    private boolean verNuevoCopia = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean ver = false;
    private boolean verDetalles = false;
    private boolean verDetalles1 = false;
    private boolean verRecepcion = false;
    private boolean verPlazo = false;
    
    private boolean verPDF = false;
    private String rutaPDFPopup;
    
    //binding al combo
    private HtmlSelectOneMenu combo;
    private HtmlSelectOneMenu combo1;
    private String desc;
    private Logger logger = Logger.getLogger("R1");    
    private boolean verCatalogoEntrada = false;
    String ruta;
    int ind_adjuntar = 0;
    private String opcion;
    private String detalle;
    private String detallecriterio;
    private Date fecha_recepcion;
    private int codarea;
    int area_remitente;
    //String area;
    String area_remite;
    String area_derivado;
    String origen;
    //
    String nombre_archivo;
    String ubicacion;
    String accion = "";
    ///Valores de Beans Llaves
    int anio;
    int origenes;
    String tipodocumentos;
    long correlativos;
    String numdoc;
    String asunto_documento;
    Date fechaplazodoc;
    Date fechadoc;
    int diasplazo;
    private int verifica;
    private String area;
    private String vprioridad;
   
    //////
    //items seleccionados de las acciones deo doc
    private List<String> selectedItems2;
    private SeguimientoEntranteDAO seguimientodocumentoDAO;
    public IServiciosSeguimientoEntrante serviciosseguimientoentrante;
    private LinkedHashMap<String, Object> observaciones2;
    private List<Seguir> seleccionados = Collections.synchronizedList(new ArrayList<Seguir>());
    private MBeanEntrantes mBeanEntrantes;
    private MBeanEntrantesGrupo mBeanEntrantesGrupo;
    private MBeanEntrantesGerencias mBeanEntrantesGerencias;
    //Validacion del primer seguimiento - evitar edicion
    private Boolean segInicial = true;
    
    
    //Agregado el 29/11/2019 - Envio de correos - Eli Diaz
    @Autowired
    private IServiciosUsuarios serviciosUsuarios;
    @Autowired
    private MailService mailService;
    
    //variable direccion archivo pdf    
    private String ubicacion_seguimiento;
    private String ls_ubicacion_seguimiento;
    private String ubicacion_salida;
    private int nindicador;
    private int nindicador_pdf;
    private String primeraficha;
    private String primeranombre;
    private String link_acceso;
   

    @PostConstruct
    public void carga() {
        selectedItems2 = new ArrayList<String>();
        observaciones2 = new LinkedHashMap<String, Object>();
        observaciones2.put("01.-Por Disposición", "11");
        observaciones2.put("02.-Coordinar Con", "12");
        observaciones2.put("03.-Acción Necesaria", "13");
        observaciones2.put("04.-Adjuntar Antecedentes", "14");
        observaciones2.put("05.-Revisar/Informar", "15");
        observaciones2.put("06.-Preparar Respuesta", "16");
        observaciones2.put("07.-Conocimientos y Fines", "17");
        observaciones2.put("08.-Su Aprobación", "18");
        observaciones2.put("09.-Tener Pendiente", "19");
        observaciones2.put("10.-Resolver", "20");
        observaciones2.put("11.-Archivar", "21");
        observaciones2.put("12.-Para Directorio", "22");
        observaciones2.put("13.-Para  Trámite", "23");
        observaciones2.put("14.-Contestar Directamente", "24");
        observaciones2.put("15.-Otros", "25");

    }

    public void pasarItems(ActionEvent actionEvent) {

        Seguir seguir = new Seguir();
        seguir.setCodArea(this.codareaderivadoN);
        seguir.setFicha(String.valueOf(this.ficha_dirigidoN));
        SelectItem item = null;
        for (int i = 0; i < this.items3.size(); i++) {
            item = (SelectItem) this.items3.get(i);
            String area = item.getValue().toString();
            if (area.equals(this.codareaderivadoN)) {
                seguir.setArea(item.getLabel());
                break;
            }
        }
        for (int i = 0; i < this.items2.size(); i++) {
            item = (SelectItem) this.items2.get(i);
            if (item.getValue().equals(this.ficha_dirigidoN)) {
                seguir.setNombreTrabajador(item.getLabel());
                break;
            }
        }
        boolean ok = false;
        ok = this.buscar(seguir, this.seleccionados);
        if (!ok) {
            this.seleccionados.add(seguir);
        }

    }

    public void quitarItems(ActionEvent actionEvent) {
        Seguir seguir = null;
        for (Seguir p : this.seleccionados) {
            if (p.isSelected()) {
                seguir = p;
            }
        }
        if (seguir == null) {
            this.error = "Seleccion item.";
            this.ver = true;
        } else {
            this.seleccionados.remove(seguir);
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

    public void confirmaBorrado(ActionEvent actionEvent) {
        try {
            //se copia exactamente igual de eventEliminar
            SeguimientoEntranteBean p = null;
            for (SeguimientoEntranteBean q : this.seguimiento) {
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
                String corrrelativo = "";
                String codseg = "";
                anio = p.getAno();
                int origenes = p.getNorigen();
                String area = String.valueOf(usuario.getCodarea());
                corrrelativo = String.valueOf(p.getCorrelativo());
                String areaderivado = String.valueOf(p.getAreaderivado());
                codseg = String.valueOf(p.getSeguimiento());
                String vtipodoc = p.getTipodoc();
                // le paso como parametro al stored
                String out = serviciosseguimientoDocuEntrante.EliminarEntrante(corrrelativo, codseg, login, areaderivado);
                out = out.trim();
                System.out.println("OUT STORED!!!:" + out);
                if (out.equals("0")) {
                    this.error = "Registro Eliminado!";
                    //selectedEntrantes.clear();
                    this.seguimiento = serviciosseguimientoDocuEntrante.catalogo(anio, origenes, vtipodoc, Long.parseLong(corrrelativo), area);
                    this.ver = true;
                    this.verAlertaConfirmacionBorrar = false;
                } else {
                    this.error = "Registro no es posible Eliminar ";
                    this.ver = true;
                    this.verAlertaConfirmacionBorrar = false;
                }

                // el
                // reporte

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[MbeanSegDocuEntrantes.confirmaBorrado]", e);
            this.error = "Error Interno";
            this.ver = true;
            this.verAlertaConfirmacionBorrar = false;

        }



    }

    public void cancelaBorrado(ActionEvent actionEvent) {
        this.verAlertaConfirmacionBorrar = false;
    }

    public void eventEliminar(ActionEvent event) {
        //se adiciona estas dos lineas
        this.msg = "Realmente desea eliminarlo?";
        this.verAlertaConfirmacionBorrar = true;
        System.out.println("Eli...Estoy aqui");
        /*

         if (this.selectedSeguimientoss.size() < 1) {
         this.error = "Debe seleccionar por lo menos un Documento";
         this.ver = true;
         } else {
         ///acediendo a sesion http
         HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
         /////guardando en sesion un objeto
         String login="";
         Usuario usuario = null;
         usuario=(Usuario)session.getAttribute("sUsuario");
         login=usuario.getLogin();
         String area = String.valueOf(usuario.getNcodarea());
         String derivado = String.valueOf(usuario.getFicha());
         // llamamos a servicios o dao pasandole los datos a eliminar
         String codigo="";
         String anno="";
         String origen="";
         String tipodoc="";
			
         for (SeguimientoEntranteBean s : selectedSeguimientoss) {
         //codigo = String.valueOf(d.getCodigo());
         //anno = String.valueOf(d.getAno());
         //origen = d.getOrigenes();
         //tipodoc = d.getNtipodoc();
         //area = String.valueOf(d.getArea());				
         // le paso como parametro al stored
         //serviciosseguimientoDocuEntrante.eliminarSeguimientoDocumento(codigo, anno, origen, tipodoc, area, login);
         }
         this.error = "Registro Eliminado!";
         selectedSeguimientoss.clear();
         //this.seguimiento = serviciosseguimientoDocuEntrante.catalogo(anio, origenes, tipodocumentos, correlativos);// actualiza el
         // reporte
         this.ver = true;
         }
         */

    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        LlavesBean beans = null;
        beans = (LlavesBean) session.getAttribute("sLlaves");
        vprioridad = beans.getPrioridad();
        
        if (this.selectedSeguimientoss.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;

            for (SeguimientoEntranteBean s : selectedSeguimientoss) {

                this.anoA = s.getAno();
                //this.origenA = s.getOrigen();	
                this.norigenA = s.getNorigen();
                this.tipodocA = s.getTipodoc();
                this.correlativoA = s.getCorrelativo();
                this.numdocA = s.getNumdoc();
                this.fechaplazoA = beans.getFechaplazo();
                this.diasplazoA = beans.getDiasplazo();
                this.asuntoA = s.getAsunto();
                this.derivadoA = s.getDerivado();
                this.fecderivadoA = s.getFecderivado();
                this.observacionA = s.getObservacion();
                this.fecrecepcionA = s.getFecrecepcion();
                //this.fecrecepcionA = beans.getFecharecepcion();
                this.fecplazoA = s.getFecplazo();
                this.estadoA = s.getEstado();
                this.feccreA = s.getFeccre();
                this.fecactA = s.getFecact();
                this.rescreA = s.getRescre();
                this.resactA = s.getResact();
                this.nombre_derivadoA = s.getNombre_derivado();
                this.accionA = s.getAccion();
                this.comentarioA = s.getComentario();
                this.seguimientoA = s.getSeguimiento();
                this.vestadoA = s.getVestado();
                this.estadoA = s.getEstado();
                this.fecderivadoA = s.getFecderivado();
                //this.vaccionA=s.getVaccion();
                //this.accionA=s.getAccion();
                this.areaderivadoA = s.getAreaderivado();
                this.codareaderivadoA = String.valueOf(s.getAreaderivado());
                this.arearemitenteA = s.getArearemitente();
                this.codarearemitenteA = String.valueOf(s.getArearemitente());
                
                System.out.println("Eli quiero ver el remitente");
                System.out.println(this.codarearemitenteA);
                this.ficharemitenteA = s.getFicharemitente();
                System.out.println("Eli quiero ver el ficha remitente");
                System.out.println(this.ficharemitenteA);
                this.ficha_dirigidoA = s.getDerivado();
                this.indicadorA = s.getIndicador();
                this.nrecepcionA = s.getNrecepcion();
                this.nplazoA = s.getNplazo();
                this.vprioridadA = vprioridad;
                this.ubicaarchivoA = s.getUbicaarchivo();
                this.ubicaarchivoorigenA = this.ubicaarchivoA;
                
                fecha_recepcion = this.fecrecepcionA;
                int origenes = this.norigenA;
                int indicador = this.indicadorA;
                System.out.println("Eli quiero ver el indicador mier");
                System.out.println(this.indicadorA);
                
                /// check recepcion del documento 15/06/2015
                /*
                if (this.nrecepcionA==0){
                	this.verRecepcion = false;
                } else {
                	this.verRecepcion = true;
                }
                */
                /// check con plazo del documento 30/06/2015
                /*
                if (this.nplazoA==0){
                	this.verPlazo = false;
                } else {
                	this.verPlazo = true;
                }
                */
                
                /////llena las acciones
                String codigo_accion = this.accionA;
                String tempo = null;
                int i = 0, j = 0;
                while (i < codigo_accion.length()) {
                    tempo = codigo_accion.substring(i, i + 2);
                    //asigna al arreglo
                    selectedItems2.add(tempo);
                    j++;
                    i += 2;
                }
                this.accionA = tempo;
                ////////				
                List<OrigenBean> origen = seguimientodocumentoDAO.origen();
                List itemsOrigen = Utils.getOrigen(origen);
                this.items7 = itemsOrigen;
                for (OrigenBean a : origen);
                //System.out.println(this.seguimiento);
                if (origenes == 1) {
                    List<AreaBean> areas = seguimientodocumentoDAO.area_remitente();
                    List itemsAreas = Utils.getAreas(areas);
                    this.items4 = itemsAreas;
                    for (AreaBean a : areas);

                    //ficha_dirigidoA
                    List<TrabajadorBean> trabajador = seguimientodocumentoDAO.trabajador(Integer.parseInt(this.codareaderivadoA));
                    List itemsTrabajador = Utils.getTrabajador_derivador(trabajador);
                    this.items2 = itemsTrabajador;
                    for (TrabajadorBean t : trabajador);

                    //ficha
                    List<TrabajadorBean> trabajadores = seguimientodocumentoDAO.trabajador_remitente(arearemitenteA);
                    List itemsremitente = Utils.getTrabajador_derivador(trabajadores);
                    this.items5 = itemsremitente;
                    for (TrabajadorBean t : trabajadores);
                } else {
                   // if (indicador == 1) {
                	  if (s.getArearemitente() > 600){
                        //List<RemitenteBean> remitentes = seguimientodocumentoDAO.remitentes();
                    	List<RemitenteBean> remitentes = seguimientodocumentoDAO.remitentesA(arearemitenteA);
                        List itemsderivados = Utils.getRemitentes(remitentes);
                        this.items4 = itemsderivados;
                        for (RemitenteBean a : remitentes);

                        ///Codigo Eli Diaz para el combo trabajador remitente
                        List<RepresentanteBean> representante = seguimientodocumentoDAO.representantes(arearemitenteA);
                        List itemsrepresentante = Utils.getRepresentante(representante);
                        this.items5 = itemsrepresentante;
                        for (RepresentanteBean t : representante);
                    } else {
                        List<AreaBean> areas = seguimientodocumentoDAO.area_remitente();
                        List itemsAreas = Utils.getAreas(areas);
                        this.items4 = itemsAreas;
                        for (AreaBean a : areas);

                        List<TrabajadorBean> trabajador = seguimientodocumentoDAO.trabajador_remitente(arearemitenteA);
                        List itemsremitente = Utils.getTrabajador_derivador(trabajador);
                        this.items5 = itemsremitente;
                        for (TrabajadorBean t : trabajador);

                        ///Codigo Eli Diaz para el combo trabajador derivados
                        List<TrabajadorBean> trabajador_derivados = seguimientodocumentoDAO.trabajador(Integer.parseInt(area_derivado));
                        List itemsTrabajador_derivado = Utils.getTrabajador(trabajador_derivados);
                        this.items2 = itemsTrabajador_derivado;
                        for (TrabajadorBean t : trabajador_derivados);

                    }


                }




            }
            //selectedSeguimientoss.clear();
            this.error = "Se Grabó Satisfactoriamente";
            ///acediendo a sesion http
            //HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            /////guardando en sesion un objeto
            String area = "";
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            area = String.valueOf(usuario.getCodarea());
            //this.seguimiento = serviciosDocumentoSalida.catalogo(area);// actualiza el
            // reporte
        } else {
            this.error = "Debe seleccionar solo un documento";
            this.ver = true;

        }
    }

    public void eventDetalles(ActionEvent evt) {
        //this.limpiarDetalles();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /*List<SecuencialBean> bean = this.secuencialDAO.correlativo(this.tipodocN);
         SecuencialBean secuencialBean = bean.get(0);
         int contador = secuencialBean.getCorrelativo();
         contador++;
         String secuencia = String.valueOf(contador);
         session.setAttribute("nombrePdf", secuencia);	*/
        session.setAttribute("tipodoc", this.tipodocN);
        session.setAttribute("indicador", 3);
        ind_adjuntar = 1;
        this.verDetalles = true;

        selectedSeguimientoss.clear();
        // this.productos = serviciosProducto.catalogo();// actualiza el
        // reporte

    }
    
    /* Evento visualizar PDF*/
    public void evenVerPDF(ActionEvent evt) {
    	 System.out.println(this.selectedSeguimientoss.size());
         if (this.selectedSeguimientoss.size() == 1){
        	 for (SeguimientoEntranteBean s : selectedSeguimientoss) {
        		 this.rutaPDFPopup = s.getUbicacion_seguimiento(); 
        	 }
            this.verPDF = true;
            System.out.println("llegue Ralvi"+this.verPDF);
        } else {
            this.error = "Debe seleccionar un registro para visualizar el PDF";
            this.ver = true;
            this.verPDF = false;
        }
        
    }

    public void eventDetallesA(ActionEvent evt) {
        //this.limpiarDetalles();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /*List<SecuencialBean> bean = this.secuencialDAO.correlativo(this.tipodocA);
         SecuencialBean secuencialBean = bean.get(0);
         int contador = secuencialBean.getCorrelativo();
         contador++;
         String secuencia = String.valueOf(contador);
         session.setAttribute("nombrePdf", secuencia);	*/
        session.setAttribute("tipodoc", this.tipodocA);
        session.setAttribute("indicador", 3);
        ind_adjuntar = 1;
        this.verDetalles = true;
        selectedSeguimientoss.clear();
        // this.productos = serviciosProducto.catalogo();// actualiza el
        // reporte

    }

    public void openpdf(ActionEvent evt) {

        if (this.selectedSeguimientoss.size() < 1) {
            this.error = "Debe seleccionar por lo menos un Documento";
            this.ver = true;
        } else {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ruta);
                System.out.println("Imprime");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void actualizar_remite(ValueChangeEvent changeEvent) {
        area_remite = (String) changeEvent.getNewValue();
        System.out.println("Pinta Eli este codigo");
        System.out.println(area_remite);
        int ficha = 0;
        if (!changeEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            changeEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            changeEvent.queue();
            return;
        }

        List<JefeBean> bean2 = this.seguimientodocumentoDAO.jefe(Integer.parseInt(area_remite));
        for (JefeBean e : bean2) {
            ficha = e.getFicha();
        }
        seguimientodocumentoDAO.trabajador_remitente(Integer.parseInt(area_remite));
        List<TrabajadorBean> trabajador = seguimientodocumentoDAO.trabajador_remitente(Integer.parseInt(area_remite));
        List itemsremitente = Utils.getTrabajador_derivador(trabajador);
        this.ficharemitenteN = ficha;
        this.items5 = itemsremitente;
        //for (TrabajadorBean t: trabajador);

    }

    public void actualizar_derivado(ValueChangeEvent changeEvent) {
        area_derivado = (String) changeEvent.getNewValue();
        int ficha = 0;
        if (!changeEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            changeEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            changeEvent.queue();
            return;
        }

        List<JefeBean> bean = this.seguimientodocumentoDAO.jefe(Integer.parseInt(area_derivado));
        for (JefeBean e : bean) {
            ficha = e.getFicha();
        }
        seguimientodocumentoDAO.trabajador(Integer.parseInt(area_derivado));
        List<TrabajadorBean> trabajador = seguimientodocumentoDAO.trabajador(Integer.parseInt(area_derivado));
        List itemsTrabajador = Utils.getTrabajador_derivador(trabajador);
        this.ficha_dirigidoN = ficha;
        //this.items3 = itemsTrabajador;
        this.items2 = itemsTrabajador;
    }

    public void Origen(ValueChangeEvent changeEv) {
        origen = (String) changeEv.getNewValue();
        int origenes = Integer.parseInt(origen);
        if (origenes == 1) {
            List<AreaBean> area = seguimientodocumentoDAO.area_remitente();
            List itemsArea = Utils.getAreas(area);
            this.items4 = itemsArea;
            for (AreaBean a : area);

        } else {
            List<RemitenteBean> remitente = seguimientodocumentoDAO.remitentes();
            List itemsRemitente = Utils.getRemitentes(remitente);
            this.items4 = itemsRemitente;
            for (RemitenteBean r : remitente);
        }
    }
    
   

    public void eventEntrada(ActionEvent evt) {
        this.verCatalogoEntrada = true;
        //for (EntranteBean p : selectedEntrantes) {		
        //}

    }

    public void captura_accion(ValueChangeEvent changeEvent) {
        selectedItems2 = new ArrayList<String>();
        String acciones = this.vaccionA;
        System.out.println(acciones);

        for (String p : this.selectedItems2) {
            selectedItems2.add(p);
        }

    }

    public void eventActualizarSeguimiento(ActionEvent event) {
        HttpSession session = null;        
        boolean formulario = true;
        try {
        	 String valida = null;
             valida = this.validarFormularioA();             
             if (valida.equals("ok")) {
            	 
             
            // llama DAO/Stored Para actualizar producto
            this.verActualizar = false;

            ///acediendo a sesion http
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            /////guardando en sesion un objeto
            Usuario usuario = null;
            String file = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            //this.areaA=usuario.getCodarea();			
            this.resactA = usuario.getLogin();
            String area = String.valueOf(usuario.getCodarea());
            String derivado = String.valueOf(usuario.getFicha());
            int perfil = usuario.getNcodperfil();
            // controlando el adjuntar			
            if (ind_adjuntar == 0) {
                this.ubicaarchivoA = "NADA";
            } else {
                file = (String) session.getAttribute("file");
                if (file != null) {
                    nombre_archivo = file;
                    List<ServidorBean> bean = this.seguimientodocumentoDAO.servidor();
                    for (ServidorBean e : bean) {
                        ubicacion = e.getDescripcion();
                        //ubicacion = "http://1.1.194.53/seguimiento/";
                    }
                    String annio = Integer.toString(c.get(Calendar.YEAR));
                    nombre_archivo = annio + nombre_archivo;
                    //this.ubicaarchivoA = ubicacion + nombre_archivo;
                    this.ubicaarchivoA = nombre_archivo;
                }
                //this.ubicararchivoA=String.valueOf(file.getPath());
            }
            
            if (this.ubicaarchivoorigenA.equals(this.ubicaarchivoA)){
            	this.nindicador=this.indicadorA;
            } else {
            	this.nindicador=1;
            }
            
            //cambio para el check de recepcion del documento
            /*
            if (this.verRecepcion == true) {
	          	this.nrecepcionA=3;
	         } else if (this.verRecepcion = false){
	          	this.nrecepcionA=0;
	         }
	        */
            //////////
            
            //cambio para el check de plazo del documento
            /*
            if (this.verPlazo == true) {
	          	this.nplazoA=1;
	         } else if (this.verPlazo = false){
	          	this.nplazoA=0;
	         }
	         */
            //////////

            String accion = "";
            for (String p : this.selectedItems2) {
                accion += p;

            }
            System.out.println("accion:" + accion);
            this.accionA = accion;
            SeguimientoEntranteBean seguimientoBean = new SeguimientoEntranteBean();
            // le paso como parametro al stored	
            seguimientoBean.setAno(this.anoA);
            seguimientoBean.setNorigen(this.norigenA);
            seguimientoBean.setTipodoc(this.tipodocA);
            seguimientoBean.setVestado(this.vestadoA);
            seguimientoBean.setEstado(this.estadoA);
            seguimientoBean.setCorrelativo(this.correlativoA);
            seguimientoBean.setSeguimiento(this.seguimientoA);
            seguimientoBean.setDerivado(this.ficha_dirigidoA);
            seguimientoBean.setFecderivado(this.fecderivadoA);
            seguimientoBean.setObservacion(this.observacionA);
            seguimientoBean.setComentario(this.comentarioA);
            seguimientoBean.setFecplazo(this.fecplazoA);
            seguimientoBean.setResact(this.resactA);
            seguimientoBean.setFecact(this.fecactA);
            seguimientoBean.setFecrecepcion(this.fecrecepcionA);
            seguimientoBean.setVaccion(this.accionA);
            seguimientoBean.setFicharemitente(this.ficharemitenteA);            
            seguimientoBean.setAreaderivado(Integer.parseInt(this.codareaderivadoA));
            seguimientoBean.setArearemitente(Integer.parseInt(this.codarearemitenteA));
            seguimientoBean.setUbicaarchivo(this.ubicaarchivoA);
            seguimientoBean.setNrecepcion(this.nrecepcionA);
            seguimientoBean.setNplazo(this.nplazoA);
            seguimientoBean.setVprioridad(this.vprioridadA);
            seguimientoBean.setIndicador(this.nindicador);
            String out = serviciosseguimientoDocuEntrante.ActualizarSeguimiento(seguimientoBean);
            out = out.trim();
            System.out.println("OUT STORED!!!:" + out);
            if (out.equals("0")) {
                this.error = "Se actualizaron los campos correctamente";
                //AGREGA ELI DIAZ
                LlavesBean beans = null;
                beans = (LlavesBean) session.getAttribute("sLlaves");
                this.anio = beans.getAno();
                this.origenes = beans.getOrigen();
                this.tipodocumentos = beans.getTipodoc();
                this.correlativos = beans.getCorrelativo();
                this.numdoc = beans.getNumdoc();
                this.asunto_documento = beans.getAsunto_documento();
                this.link_acceso = beans.getUbicacion();
                this.fechaplazodoc = beans.getFechaplazo();
                this.diasplazo = beans.getDiasplazo();
                //this.seguimiento = this.serviciosseguimientoentrante.catalogo(this.anollave, this.origenllave, this.tipodocllave, this.correlativollave, area);
                //this.seguimiento = serviciosseguimientoDocuEntrante.catalogo(anio, origenes, tipodocumentos, correlativos, area);			
                this.seguimiento = serviciosseguimientoDocuEntrante.catalogo(this.anoA, this.norigenA, this.tipodocA, this.correlativoA, area);
                //this.mensaje = "Transaccion ok.";
                this.verPlazo=false;
                this.verRecepcion=false;
                ind_adjuntar = 0;
                this.ubicaarchivoA = "NADA";
                
                //this.mBeanEntrantes.eventRefrescar();
                if (perfil == 6) {
                    this.mBeanEntrantesGrupo.eventRefrescar();
                } else if (perfil == 10)
                {
                	this.mBeanEntrantesGerencias.eventRefrescar();
                } else
                {
                    this.mBeanEntrantes.eventRefrescar();
                }
                //serviciosDocumentoSalida.ActualizarDocumentosSalida(documentosalidaBean);			
                //this.documento = serviciosDocumentoSalida.catalogo(area);			
                // reporte
                this.verCatalogo = true;
                this.verActualizar = false;
                this.ver = true;
                
            } else {
                this.error = "Ya existe el Remitente en la Base de Datos, Por favor seleccionar de la lista remitente";
                this.ver = true;
                //disBotonGrabar = false;
                //this.verCatalogo = true;
                //formulario = false;
            	}
            } else {
                formulario = false;
                this.error = valida;
                this.ver = true;                
                
            }
        } catch (Exception e) {
            logger.error("[MBeanSegDocuEntrantes.eventActualizarSeguimiento]", e);
            this.error = "Error Interno, comuniquese con el Administrador";
            this.ver = true;
            e.printStackTrace();
            this.verActualizar = true;
        } finally {
        	if (formulario) {
            session.setAttribute("file", null);
        	}
        }
    }

    public void eventRegistrarSeguimiento(ActionEvent event) {
        HttpSession session = null;
        boolean formulario = true;
        
        try {
       
            String valida = null;
            valida = this.validarFormulario();
            if (valida.equals("ok")) {
                formulario = true;
                for (String p : this.selectedItems2) {
                    //System.out.println("ITEM:"+ p);
                    accion = p + accion;
                    //System.out.println("completo:"+ accion);
                    this.vaccionN = accion;
                }
                this.verActualizar = false;
                this.verNuevo = false;
                this.verNuevoCopia = false;
                ///acediendo a sesion http
                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                /////guardando en sesion un objeto
                Usuario usuario = null;

                //File file = null;
                String file = null;
                usuario = (Usuario) session.getAttribute("sUsuario");
                this.resactN = usuario.getLogin();
                int perfil = usuario.getNcodperfil();
                String area = String.valueOf(usuario.getCodarea());
                // controlando el adjuntar
                //file = (File) session.getAttribute("file");
                file = (String) session.getAttribute("file");
                if (file != null) {
                    nombre_archivo = file;
                    List<ServidorBean> bean = this.seguimientodocumentoDAO.servidor();
                    for (ServidorBean e : bean) {
                        ubicacion = e.getDescripcion();
                        //ubicacion = "http://1.1.194.53/seguimiento/";
                    }
                    String annio = Integer.toString(c.get(Calendar.YEAR));
                    nombre_archivo = annio + nombre_archivo;
                    //this.ubicaarchivoN = ubicacion + nombre_archivo;
                    this.ubicaarchivoN = nombre_archivo;
                } else {
                    this.ubicaarchivoN = "NADA";
                }
                
                if (this.ubicaarchivoN=="NADA"){
                	this.nindicador_pdf=0;
                } else {
                	this.nindicador_pdf=1;
                }
              
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                
                SeguimientoEntranteBean seguimientoBean = new SeguimientoEntranteBean();              
                seguimientoBean.setAreas(this.generaAreas());
                seguimientoBean.setFichas(this.generaFichas());
                seguimientoBean.setAno(this.anoN);
                seguimientoBean.setNorigen(this.norigenN);
                seguimientoBean.setTipodoc(this.tipodocN);
                seguimientoBean.setVestado(this.vestadoN);
                seguimientoBean.setCorrelativo(this.correlativoN);
                seguimientoBean.setFecderivado(this.fecderivadoN);
                seguimientoBean.setAccion(this.vaccionN);
                seguimientoBean.setObservacion(this.observacionN);
                seguimientoBean.setComentario(this.comentarioN);
                seguimientoBean.setFecplazo(this.fecplazoN);
                seguimientoBean.setResact(this.resactN);
                seguimientoBean.setFecact(this.fecactN);
                seguimientoBean.setFecrecepcion(this.fecrecepcionN);
                //seguimientoBean.setVaccion(this.vaccionA);
                //seguimientoBean.setAreaderivado(this.areaderivadoA);			
                //seguimientoBean.setArearemitente(this.areaderivadoA);
                seguimientoBean.setFicharemitente(this.ficharemitenteN);
                seguimientoBean.setArearemitente(Integer.parseInt(this.codarearemitenteN));
                seguimientoBean.setUbicaarchivo(this.ubicaarchivoN);
                seguimientoBean.setNrecepcion(this.nrecepcionA);
                seguimientoBean.setNplazo(this.nplazoN);
                seguimientoBean.setVprioridad(this.vprioridadN);                
                seguimientoBean.setIndicador(nindicador_pdf);
                
                
                
                String out = serviciosseguimientoDocuEntrante.nuevoSeguimiento(seguimientoBean);
                out = out.trim();
                System.out.println("OUT STORED!!!:" + out);
                if (out.equals("0")) {                    
                	                	
                    LlavesBean beans = null;
                    beans = (LlavesBean) session.getAttribute("sLlaves");
                    this.anio = beans.getAno();
                    this.origenes = beans.getOrigen();
                    this.tipodocumentos = beans.getTipodoc();
                    this.correlativos = beans.getCorrelativo();
                    this.numdoc = beans.getNumdoc();
                    this.asunto_documento = beans.getAsunto_documento();
                    this.link_acceso = beans.getUbicacion();
                    this.fechaplazodoc = beans.getFechaplazo();
                    this.diasplazo = beans.getDiasplazo();
                    //this.link_acceso = beans.getUbicacion();
                    this.error = "Se Grabó Satisfactoriamente";
                    System.out.println("Pinto las variales del Email seguimiento 111");
	                System.out.println(this.primeraficha);
                    this.ver = true;
                    this.seguimiento = serviciosseguimientoDocuEntrante.catalogo(anio, origenes, tipodocumentos, correlativos, area);                  
                    selectedItems2.clear();
                    accion = "";                   
                    this.verCatalogo = true;
                    this.verActualizar = false;
                    this.seleccionados.clear();
                    this.ficharemitenteN = 0;
                    this.codarearemitenteN = "";
                    ind_adjuntar = 0;
                    this.nindicador_pdf =0;
                    this.ubicaarchivoN = "NADA";
                    
                                        
                    if (perfil == 6) {
                        this.mBeanEntrantesGrupo.eventRefrescar();
                    } else if (perfil == 10)
                    {
                    	this.mBeanEntrantesGerencias.eventRefrescar();
                    } else
                    {
                        this.mBeanEntrantes.eventRefrescar();
                    }
                    
                    

                } else {
                    this.error = "Ya existe el Remitente en la Base de Datos, Por favor seleccionar de la lista remitente";
                    this.ver = true;
                } 
                
                //ENVIO DE CORREO ELI DIAZ HORNA 29/11/2019
               
                if (this.nplazoN == 1){
            		
            		String txtMensaje = "";
					String txtAsunto  = "";
            		
            		txtAsunto = "Documento con Plazo, Registro Nro: " + this.correlativoN + " Sistema Tramite Documentario";
	                txtMensaje = txtMensaje + txtAsunto + "\n\n";
	                txtMensaje = txtAsunto + "\n\n";
	                txtMensaje = txtMensaje + "Estimada(o) " + this.primeranombre + " :\n";
	                txtMensaje = txtMensaje + "Le comunicamos que un documento con plazo de atención ha llegado a su bandeja de entrada en el Sistema Tramite Documentario." + "\n\n";
	                txtMensaje = txtMensaje + "Numero Registro : " + this.correlativoN + "\n\n";	                
	                txtMensaje = txtMensaje + "Numero Documento : " + this.numdoc + "\n\n";
	                txtMensaje = txtMensaje + "Asunto : " 	+ this.asunto_documento + "\n\n";
	                txtMensaje = txtMensaje + "Fecha Plazo : " 	+ sdf.format(this.fecplazoN) + "\n\n";	               
	                txtMensaje = txtMensaje + "Comentario : " + this.comentarioN  + "\n\n";
	                txtMensaje = txtMensaje + "Visualice el documento en el siguiente link  : " + this.link_acceso  + "\n\n";
	                txtMensaje = txtMensaje + "Favor de atenderlo a la brevedad posible.\n\n";
	                txtMensaje = txtMensaje + "Atentamente,\n\n";
	                txtMensaje = txtMensaje + "Sistema Tramite Documentario \n";
	                txtMensaje = txtMensaje + "Por favor no responder este Email";
	                
	                	                	                	                
	                System.out.println("Pinto las variales del Email seguimiento");
	                System.out.println(this.primeraficha);
	                System.out.println(txtMensaje);
	                System.out.println(txtAsunto);
	                
	                Boolean envioCorreo = serviciosUsuarios.enviarEmailByFichaUsu(Integer.parseInt(this.primeraficha),txtMensaje, txtAsunto);
	               	                					
	                
	                if (envioCorreo) {
	                    mailService
	                            .enviarMail("El area del usuario "
	                            + String.valueOf(this.primeraficha)	                            
	                            + " recibio un documento\n"
	                            + "de importancia URGENTE y este ha sido notificado");
	                } else {
	                    mailService
	                            .enviarMail("El area del usuario "
	                            + String.valueOf(this.primeraficha)
	                            + " recibio un documento\n"
	                            + "de importancia URGENTE y pero este, al no contar con un correo registrado en la tabla de Trabajadores, no pudo ser notificado.");
	                }
	               
	                
            		
            	}
                
                
	            } else {
	                formulario = false;
	                this.error = valida;
	                this.ver = true;
	            }
            	          
          
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Fallo el registro despues email", e);
            //this.error = "Transaccion No valida. despues email";
            this.error = "Se Grabó Satisfactoriamente";
            this.ver = true;
            this.verCatalogo = true;
            this.verActualizar = false;
        } finally {
            if (formulario) {
                session.setAttribute("file", null);
            }
        }
		
    }

    public void eventCopiarSeguimiento(ActionEvent event) {
        HttpSession session = null;
        boolean formulario = true;
        try {
            String valida = null;
            valida = this.validarFormulario();
            if (valida.equals("ok")) {
                formulario = true;
                for (String p : this.selectedItems2) {
                    //System.out.println("ITEM:"+ p);
                    accion = p + accion;
                    //System.out.println("completo:"+ accion);
                    this.vaccionN = accion;
                }
                this.verActualizar = false;
                this.verNuevo = false;
                this.verNuevoCopia = false;
                ///acediendo a sesion http
                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                /////guardando en sesion un objeto
                Usuario usuario = null;

                //File file = null;
                String file = null;
                usuario = (Usuario) session.getAttribute("sUsuario");
                this.resactN = usuario.getLogin();
                int perfil = usuario.getNcodperfil();
                String area = String.valueOf(usuario.getCodarea());
                /*
                if (this.verPlazo = true){
                	this.nplazoN=1;
                } else {
                	this.nplazoN=0;
                }	
                */	
                
                System.out.println("=============================");
                System.out.println("Quiero ver el nombre de archivo que copio");
                System.out.println(this.ubicaarchivoC);
                System.out.println(this.ubicaposicion);                
                System.out.println("=============================");
                
                //Buscar una cadena dentro de otro
                /*
                String cadenaDondeBuscar = this.observacionN;
                String loQueQuieroBuscar = "000";
                String[] palabras = loQueQuieroBuscar.split("\\W+");
                for (String palabra : palabras) {
                    if (cadenaDondeBuscar.contains(palabra)) {
                        System.out.println("Encontrado");
                        //aquí tu lógica en caso que se haya encontrado...
                        this.nindicador_pdf=2;
                    } else {
                    	this.nindicador_pdf=1;
                    }
                }
                */
                
              
                System.out.println("quiero ver el indicador de copia");
                System.out.println(this.nindicadorC);
                System.out.println("quiero ver el indicador de copia ccccc");
                System.out.println(this.ubicaposicion);
                
                //this.nindicador_pdf=nindicadorC;
                
                System.out.println("quiero ver el indicador de copia");
                System.out.println(this.nindicadorC);
                
                if (this.ubicaarchivoC=="NADA"){
                	this.nindicador_pdf=0;
                } 
                else if(nindicadorC==2)
                {
                	this.nindicador_pdf=2;
                } else {
                	this.nindicador_pdf=1;
                }
                
                
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                
                SeguimientoEntranteBean seguimientoBean = new SeguimientoEntranteBean();
                //Se adiciona varias areas y fichas concatenadas
                //ADD CF
                seguimientoBean.setAreas(this.generaAreas());
                seguimientoBean.setFichas(this.generaFichas());
                seguimientoBean.setAno(this.anoN);
                seguimientoBean.setNorigen(this.norigenN);
                seguimientoBean.setTipodoc(this.tipodocN);
                seguimientoBean.setVestado(this.vestadoN);
                seguimientoBean.setCorrelativo(this.correlativoN);
                seguimientoBean.setFecderivado(this.fecderivadoN);
                seguimientoBean.setAccion(this.vaccionN);
                seguimientoBean.setObservacion(this.observacionN);
                seguimientoBean.setComentario(this.comentarioC);
                seguimientoBean.setFecplazo(this.fecplazoN);
                seguimientoBean.setResact(this.resactN);
                seguimientoBean.setFecact(this.fecactN);
                seguimientoBean.setFecrecepcion(this.fecrecepcionN);
                //seguimientoBean.setVaccion(this.vaccionA);
                //seguimientoBean.setAreaderivado(this.areaderivadoA);			
                //seguimientoBean.setArearemitente(this.areaderivadoA);
                seguimientoBean.setFicharemitente(this.ficharemitenteN);
                seguimientoBean.setArearemitente(Integer.parseInt(this.codarearemitenteN));
                seguimientoBean.setUbicaarchivo(this.ubicaarchivoC);
                seguimientoBean.setNrecepcion(this.nrecepcionA);
                seguimientoBean.setNplazo(this.nplazoN);
                seguimientoBean.setVprioridad(this.vprioridadN);
                //seguimientoBean.setIndicador(this.nindicador_pdf);
                seguimientoBean.setIndicador(this.nindicadorC);
                
                serviciosseguimientoDocuEntrante.nuevoSeguimiento(seguimientoBean);

                //OJO se esta pasando un parï¿½metro mï¿½s areasFichas			
			/*for(Seguir p:this.seleccionados)
                 {
                 //Ahora son varias Areas !!!
                 seguimientoBean.setAreaderivado(Integer.parseInt(p.getCodArea()));
                 //Ahora son varias fichas !!
                 seguimientoBean.setDerivado(Long.parseLong(p.getFicha()));
                 List<LlavesBean> bean2 =this.seguimientodocumentoDAO.verifica_dirigido(this.anoN, this.norigenN, this.tipodocN, this.correlativoN, Integer.parseInt(p.getCodArea()));				
                 for (LlavesBean e : bean2) {
                 verifica = e.getVerifica();			
                 }
                 //Aqui se debe considerar insertar mï¿½s de un derivado o actualizarlo, ahora son varios areas y fichas
                 if (verifica==0){
                 if (Integer.parseInt(p.getCodArea())!=Integer.parseInt(this.codarearemitenteN)){					
                 seguimientodocumentoDAO.InsertDerivado(this.anoN, this.norigenN, this.tipodocN, this.correlativoN, Integer.parseInt(p.getCodArea()),Long.parseLong(p.getFicha()));						
                 }
                 } else
                 {
                 if (Integer.parseInt(p.getCodArea())!=Integer.parseInt(this.codarearemitenteN)){
                 seguimientodocumentoDAO.UpdateDerivado(this.correlativoN, Integer.parseInt(p.getCodArea()), Long.parseLong(p.getFicha()));
                 }
                 }
                 //Aqui se debe considerar mï¿½s de un derivado nuevo
                 //seguimientodocumentoDAO.storedDerivado(this.anoN, this.norigenN, this.tipodocN, this.correlativoN, Integer.parseInt(this.codareaderivadoN), this.resactN, this.ficha_dirigidoN);
                 serviciosseguimientoDocuEntrante.nuevoSeguimiento(seguimientoBean);
                 }*/
                //comenta Eli 01/02/2011
                //System.out.println(this.anio);
                //System.out.println(this.origenes);
                //System.out.println(this.tipodocumentos);
                //System.out.println(this.correlativos);
                //revisar eli
                //this.seguimiento = serviciosseguimientoDocuEntrante.catalogo(this.norigenN, this.origenes, this.tipodocN, this.correlativoN, area);	
                LlavesBean beans = null;
                beans = (LlavesBean) session.getAttribute("sLlaves");
                this.anio = beans.getAno();
                this.origenes = beans.getOrigen();
                this.tipodocumentos = beans.getTipodoc();
                this.correlativos = beans.getCorrelativo();
                this.numdoc = beans.getNumdoc();
                this.asunto_documento = beans.getAsunto_documento();
                this.link_acceso = beans.getUbicacion();
                this.fechaplazodoc = beans.getFechaplazo();
                this.diasplazo = beans.getDiasplazo();
                this.seguimiento = serviciosseguimientoDocuEntrante.catalogo(anio, origenes, tipodocumentos, correlativos, area);

                this.error = "Se Grabó Satisfactoriamente";

                selectedItems2.clear();
                accion = "";
                this.ver = true;
                this.verCatalogo = true;
                this.verActualizar = false;
                this.seleccionados.clear();
                this.ficharemitenteN = 0;
                this.codarearemitenteN = "";
                this.nindicador_pdf = 0;
                this.ubicaarchivoC = "NADA";
                
                if (perfil == 6) {
                    this.mBeanEntrantesGrupo.eventRefrescar();
                } else if (perfil == 10)
                {
                	this.mBeanEntrantesGerencias.eventRefrescar();
                } else
                {
                    this.mBeanEntrantes.eventRefrescar();
                }

              //ENVIO DE CORREO ELI DIAZ HORNA 29/11/2019
                
                System.out.println("Pinto las variable Envio de Email");
                System.out.println(this.nplazoN);
                System.out.println(this.nindicador_pdf);
                
                if (this.nplazoN == 1){

            		
            		String txtMensaje = "";
					String txtAsunto  = "";
            		
            		txtAsunto = "Documento con Plazo - Sistema Tramite Documentario";
	                txtMensaje = txtMensaje + txtAsunto + "\n\n";
	                txtMensaje = txtAsunto + "\n\n";
	                txtMensaje = txtMensaje + "Estimada(o) " + this.primeranombre + " :\n";
	                txtMensaje = txtMensaje + "Le comunicamos que un documento con plazo de atención ha llegado a su bandeja de entrada en el Sistema Tramite Documentario." + "\n\n";
	                txtMensaje = txtMensaje + "Numero Registro : " + this.correlativoN + "\n\n";	                
	                txtMensaje = txtMensaje + "Numero Documento : " + this.numdoc + "\n\n";
	                txtMensaje = txtMensaje + "Asunto : " 	+ this.asunto_documento + "\n\n";
	                txtMensaje = txtMensaje + "Fecha Plazo : " 	+ sdf.format(this.fecplazoN) + "\n\n";	               
	                txtMensaje = txtMensaje + "Comentario : " + this.comentarioN  + "\n\n";
	                txtMensaje = txtMensaje + "Visualice el documento en el siguiente link  : " + this.link_acceso  + "\n\n";
	                txtMensaje = txtMensaje + "Favor de atenderlo a la brevedad posible.\n\n";
	                txtMensaje = txtMensaje + "Atentamente,\n\n";
	                txtMensaje = txtMensaje + "Sistema Tramite Documentario \n";
	                txtMensaje = txtMensaje + "Por favor no responder este Email";
	                
	                	                	                	                
	                System.out.println("Pinto las variales del Email");
	                System.out.println(this.primeraficha);
	                System.out.println(txtMensaje);
	                System.out.println(txtAsunto);
	                
	                Boolean envioCorreo = serviciosUsuarios.enviarEmailByFichaUsu(Integer.parseInt(this.primeraficha),txtMensaje, txtAsunto);
	               	                					
	                
	                if (envioCorreo) {
	                    mailService
	                            .enviarMail("El area del usuario "
	                            + String.valueOf(this.primeraficha)	                            
	                            + " recibio un documento\n"
	                            + "de importancia URGENTE y este ha sido notificado");
	                } else {
	                    mailService
	                            .enviarMail("El area del usuario "
	                            + String.valueOf(this.primeraficha)
	                            + " recibio un documento\n"
	                            + "de importancia URGENTE y pero este, al no contar con un correo registrado en la tabla de Trabajadores, no pudo ser notificado.");
	                }
	               
	                
            		
            	
                }

            } else {
                formulario = false;
                this.error = valida;
                this.ver = true;
                
                
            }
        } catch (Exception e) {
        	 e.printStackTrace();
             logger.error("Fallo el registro despues email", e);
             //this.error = "Transaccion No valida. despues email";
             this.error = "Se Grabó Satisfactoriamente";
             this.ver = true;
             this.verCatalogo = true;
             this.verActualizar = false;
        } finally {
            if (formulario) {
                session.setAttribute("file", null);
            }
        }


    }

    public String generaAreas() {
        String seguir = "";
        for (Seguir p : this.seleccionados) {
            seguir += p.getCodArea();
        }
        return seguir;
    }

    public String generaFichas() {
        String seguir = "";
        for (Seguir p : this.seleccionados) {
            seguir += p.getFicha();
            primeraficha = p.getFicha();
            primeranombre = p.getNombreTrabajador();
        }
        return seguir;
    }

    public void setserviciosseguimientoDocuEntrante(IServiciosSeguimientoEntrante serviciosseguimientoDocuEntrante) {
        this.serviciosseguimientoDocuEntrante = serviciosseguimientoDocuEntrante;
    }

    public void cerrarDetalles(ActionEvent event) {
    	this.verPDF = false;
    	this.verDetalles = false;
    }

    public void cerrarDetalles1(ActionEvent event) {
        this.verDetalles1 = false;
    }

    public void cerrarCatalogoEntrada(ActionEvent event) {
        this.verCatalogoEntrada = false;

    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }

    public void eventNuevo(ActionEvent event) {
        this.limpiar();
        this.verNuevo = true;
        this.verNuevoCopia = false;
        this.verCatalogo = false;
    }

    public void eventNuevoCopia(ActionEvent event) {
        SeguimientoEntranteBean v = null;

        for (SeguimientoEntranteBean r : this.seguimiento) {
            if (r.isSelected()) {
                v = r;
            }
        }
        if (v != null) {
            this.limpiar();
            this.verNuevo = false;
            this.verNuevoCopia = true;
            this.verCatalogo = false;
        } else {
            this.error = "Debe seleccionar solo un registro para copiar";
            this.ver = true;
        }
    }

    public void eventFiltros(ActionEvent event) {
        // this.ver = true;
        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        //this.areaA=usuario.getCodarea();			
        this.resactA = usuario.getLogin();
        String area = String.valueOf(usuario.getCodarea());
        //fechas

        // usamos para darle el fomato adecuado para pasarle al stored de oracle
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        String date1 = sdf.format(this.getDate1());
        String date2 = sdf.format(this.getDate2());
        // se puede validar fechas
        if (this.date1.after(this.date2)) {
            this.error = "Fecha 1 debe ser menor que la fecha 2";
            this.ver = true;
        } else {
            System.out.println("Filtrando..");
            this.seguimiento = seguimientodocumentoDAO.filtrosSP(date1, date2, getItemSeleccionado(), area);

        }

    }

    public void eventRefrescar(ActionEvent evt) {    	  
    	 ///acediendo a sesion http
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    	 LlavesBean beans = null;
         beans = (LlavesBean) session.getAttribute("sLlaves");
         this.anio = beans.getAno();
         this.origenes = beans.getOrigen();
         this.tipodocumentos = beans.getTipodoc();
         this.correlativos = beans.getCorrelativo();
         this.numdoc = beans.getNumdoc();
         this.asunto_documento = beans.getAsunto_documento();
         this.link_acceso = beans.getUbicacion();
         this.fechaplazodoc = beans.getFechaplazo();
         this.diasplazo = beans.getDiasplazo();
         this.fechadoc = beans.getFecharecepcion();
         this.seguimiento = serviciosseguimientoDocuEntrante.catalogo(this.anio, this.origenes, this.tipodocumentos, correlativos, area);
         this.detalle="";
          
    }

    public void eventBusquedas(ActionEvent event) {
        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area = String.valueOf(usuario.getCodarea());
        this.codareaderivadoA = area;
        
        LlavesBean beans = null;
        beans = (LlavesBean) session.getAttribute("sLlaves");
        this.anio = beans.getAno();
        this.origenes = beans.getOrigen();
        this.tipodocumentos = beans.getTipodoc();
        this.correlativos = beans.getCorrelativo();
        this.numdoc = beans.getNumdoc();
        this.asunto_documento = beans.getAsunto_documento();
        this.link_acceso = beans.getUbicacion();
        this.fechaplazodoc = beans.getFechaplazo();
        this.diasplazo = beans.getDiasplazo();
        this.fechadoc = beans.getFecharecepcion();
        
        System.out.println("Pinto las variables de busqueda");        
        System.out.println(this.correlativos);
        System.out.println(this.anio);
        System.out.println(this.origenes);       

       
        if (this.detalle.equals("")) {
            this.error = "Debe de Agregar un Detalle";
            this.ver = true;
        } else {
            System.out.println("buscando..");       
            this.seguimiento = seguimientodocumentoDAO.BusquedaSP(this.anio, this.origenes, this.correlativos, area, opcion, detalle);
        }
    }
    // list of selected profiles
    protected ArrayList<SeguimientoEntranteBean> selectedSeguimientoss;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanSegDocuEntrates() {
        selectedSeguimientoss = new ArrayList<SeguimientoEntranteBean>();
        System.out.println("Contructor MBeanProfiles....");
    }

    public void eventoNuevo(ActionEvent evnt) {
        for (SeguimientoEntranteBean p : selectedSeguimientoss) {
            System.out.println(p.getNumdoc() + "   " + p.getCorrelativo());
        }
    }
    // list of selected employees
    protected ArrayList<EntranteBean> selectedEntrantes;

    @PostConstruct
    public void posterior() {

        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute("sLlaves") != null) {

            /////guardando en sesion un objeto
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            codarea = usuario.getCodarea();
            area = String.valueOf(usuario.getCodarea());
            String derivado = String.valueOf(usuario.getFicha());


            LlavesBean beans = null;
            beans = (LlavesBean) session.getAttribute("sLlaves");
            this.anio = beans.getAno();
            this.origenes = beans.getOrigen();
            this.tipodocumentos = beans.getTipodoc();
            this.correlativos = beans.getCorrelativo();
            this.numdoc = beans.getNumdoc();
            this.asunto_documento = beans.getAsunto_documento();
            this.link_acceso = beans.getUbicacion();
            this.fechaplazodoc = beans.getFechaplazo();
            this.diasplazo = beans.getDiasplazo();
            this.fechadoc = beans.getFecharecepcion();
            this.vprioridad = beans.getPrioridad();
            this.asunto_documento = beans.getAsunto_documento();
            this.ls_ubicacion_seguimiento = "";
            
            /*
            System.out.println("---------------------");
            System.out.println("QUIERO VER EL ASUNTO DEL DOCUMENTO SEGUMIENTO");
            System.out.println(beans.getAsunto_documento());
            System.out.println(beans.getPrioridad());
            System.out.println(beans.getNumdoc());
            System.out.println("---------------------");
            
            System.out.println("Quiero ver la prioridad Eli....");
            System.out.println(this.vprioridad);
            System.out.println("Quiero ver el nro registro");
            System.out.println(this.correlativos);
            System.out.println("Quiero ver el asunto");
            System.out.println(this.asunto_documento);
            */
            
            
            //System.out.println("Pinto las variables");
            //System.out.println(this.anio);
            ////System.out.println(this.origenes);
            //System.out.println(this.tipodocumentos);
            //System.out.println(this.correlativos);
            //System.out.println(area);

            //System.out.println("Fecha Plazo:" + this.fechaplazodoc);
            //System.out.println("Dias Plazo:" + this.diasplazo);
            this.seguimiento = serviciosseguimientoDocuEntrante.catalogo(anio, origenes, tipodocumentos, correlativos, area);
            System.out.println("Eli Estoy en el seguimiento");
            System.out.println(this.seguimiento.size());


            ///Codigo Eli Diaz para el combo tipo
            List<TiposBean> tipos = seguimientodocumentoDAO.tipos();
            List itemstipos = Utils.getTipos(tipos);
            this.items1 = itemstipos;

            ///Codigo Eli Diaz para el combo trabajador
            //List<TrabajadorBean> trabajador = seguimientodocumentoDAO.trabajador(codarea);
            //List itemsTrabajador = Utils.getTrabajador(trabajador);			
            //this.items2 = itemsTrabajador;	



            ///Codigo Eli Diaz para el combo para area derivado
            List<AreaBean> area_derivado = seguimientodocumentoDAO.area_derivado();
            List itemsareaderivado = Utils.getAreas(area_derivado);
            this.items3 = itemsareaderivado;

            ///Codigo Eli Diaz para el combo para area remitente
            List<AreaBean> area_remitente = seguimientodocumentoDAO.area_remitente();
            List itemsarearemitente = Utils.getAreas(area_remitente);
            this.items4 = itemsarearemitente;

            //List<AccionBean> accion = seguimientodocumentoDAO.accion();
            //List itemsAccion = Utils.getAccion(accion);			
            //this.items6 = itemsAccion;	
            //for (AccionBean a: accion);

            List<TrabajadorBean> trabajador_remitente = seguimientodocumentoDAO.trabajador_remitente(codarea);
            List itemsremitente = Utils.getTrabajador_derivador(trabajador_remitente);
            this.items5 = itemsremitente;

            List<OrigenBean> origen = seguimientodocumentoDAO.origen();
            List itemsOrigen = Utils.getOrigen(origen);
            this.items7 = itemsOrigen;
            
            List<ServidorBean> beanseg = this.seguimientodocumentoDAO.servidorsegumiento();
            for (ServidorBean e : beanseg) {
            	ubicacion_seguimiento = e.getDescripcion();
            }
            
            List<ServidorBean> beansalida = this.seguimientodocumentoDAO.servidorsalida();
            for (ServidorBean e : beansalida) {
            	ubicacion_salida = e.getDescripcion();
            }
            
        }


    }

    /**
     * ***********************************************
     */
    public void eventBuscarSeguimiento(ActionEvent event) {
        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area = String.valueOf(usuario.getCodarea());

        //System.out.println(Global.ano);
        //System.out.println(origenes);
        //System.out.println(tipodoc);
        //System.out.println(correlativo);
        //System.out.println(area);

        //this.entrantes = entranteDAO.filtrosSP(date1,date2,this.getItem2Seleccionado(), ncodarea);
        //this.seguimiento = seguimientodocumentoDAO.BuscarSP(ano, origenes, tipodoc, correlativo, area);
        //this.seguimiento = seguimientodocumentoDAO.BuscarSP(String.valueOf(2011),String.valueOf(2), String.valueOf("ATD0003"), String.valueOf(9), area);


    }

    /**
     * ***********************************************
     */
    /**
     * SelectionListener bound to the ice:rowSelector component. Called when a
     * row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedSeguimientoss.clear();
        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        /* If application developers rely on validation to control submission of the form or use the result of
         the selection in cascading control set up the may want to defer procession of the event to
         INVOKE_APPLICATION stage by using this code fragment
         if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         event.setPhaseId( PhaseId.INVOKE_APPLICATION );
         event.queue();
         return;
         }

         */

        System.out.println("Tama:" + seguimiento.size());
        // build the new selected list
        SeguimientoEntranteBean seguimientoss;

        int ubicacionSegInicial = seguimiento.size() - 1;

        for (int i = 0, max = seguimiento.size(); i < max; i++) {
            seguimientoss = (SeguimientoEntranteBean) seguimiento.get(i);
            if (seguimientoss.isSelected()) {
                selectedSeguimientoss.add(seguimientoss);
                if (i == ubicacionSegInicial) {
                    this.segInicial = false;
                } else {
                    this.segInicial = true;
                }
            }
        }



        for (SeguimientoEntranteBean d : selectedSeguimientoss) {
            System.out.println(d.getAno() + "  " + d.getTipodoc() + " " + d.getCorrelativo() + " " + d.getNumdoc() + " " + d.getSeguimiento()+ " " + d.getUbicaarchivo() + " " + d.getUbicacion_seguimiento());
            this.mCarga.setNombrePdf(d.getUbicaarchivo());
            this.origenA = String.valueOf(d.getNorigen());
            this.fecha_recepcion = d.getFecrecepcion();
            this.rescreA = d.getRescre();
            this.feccreA = d.getFeccre();
            this.norigenA = d.getNorigen();
            this.nrecepcionA = d.getNrecepcion();
            this.nplazoA = d.getNplazo();
            this.ubicaarchivoC = d.getUbicaarchivo();
            this.ubicaposicion = d.getUbicacion_seguimiento();
            this.nindicadorC = d.getIndicador();
           
             //this.vprioridadA = this.vprioridad;            
            /// check recepcion del documento 15/06/2015
            if (this.nrecepcionA==0){
            	this.verRecepcion = false;
            } else {
            	this.verRecepcion = true;
            }
            /// check con plazo del documento 30/06/2015
            if (this.nplazoA==0){
            	this.verPlazo = false;
            } else {
            	this.verPlazo = true;
            }
            
            area_remitente = d.getArearemitente();
            area_derivado = String.valueOf(d.getAreaderivado());
            //
            ruta = d.getUbicaarchivo();
            ind_adjuntar = d.getIndicaarchivo();
            this.ubicaarchivoA = d.getUbicaarchivo();
            this.ubicaarchivoC = d.getUbicaarchivo();
            this.comentarioC = d.getComentario();
            this.nindicador = d.getIndicador();
            
            //inserta codigo para ver el archivo adjunto 19noviembre2019-EDH
            if (this.nindicador==2) {
            	ls_ubicacion_seguimiento = ubicacion_salida + d.getUbicaarchivo();                        
                SeguimientoEntranteBean beanseguimiento = new SeguimientoEntranteBean();
                beanseguimiento.setUbicacion_seguimiento(ls_ubicacion_seguimiento);
            	
            } else {
            	ls_ubicacion_seguimiento = ubicacion_seguimiento + d.getUbicaarchivo();                        
                SeguimientoEntranteBean beanseguimiento = new SeguimientoEntranteBean();
                beanseguimiento.setUbicacion_seguimiento(ls_ubicacion_seguimiento);
            	
            }
            

            

        }
        ///Codigo Eli Diaz para el combo trabajador remitente
        List<TrabajadorBean> trabajador_remitente = seguimientodocumentoDAO.trabajador_remitente(area_remitente);
        List itemsTrabajador_remitente = Utils.getTrabajador(trabajador_remitente);
        this.items5 = itemsTrabajador_remitente;
        for (TrabajadorBean t : trabajador_remitente);

        ///Codigo Eli Diaz para el combo trabajador derivados
        List<TrabajadorBean> trabajador_derivados = seguimientodocumentoDAO.trabajador(Integer.parseInt(area_derivado));
        List itemsTrabajador_derivado = Utils.getTrabajador(trabajador_derivados);
        this.items2 = itemsTrabajador_derivado;
        for (TrabajadorBean t : trabajador_derivados);
        
        





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
            selectedSeguimientoss.clear();

            // build the new selected list
            SeguimientoEntranteBean seguimientoss;
            for (int i = 0, max = seguimiento.size(); i < max; i++) {
                seguimientoss = (SeguimientoEntranteBean) seguimiento.get(i);
                seguimientoss.setSelected(false);
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
        this.verNuevoCopia = false;
        this.verActualizar = false;
        selectedItems2.clear();
        HttpSession session = (HttpSession) FacesContext
                .getCurrentInstance().getExternalContext()
                .getSession(false);
        session.setAttribute("file", null);
        this.seleccionados.clear();
        //items2.clear();
    }

    public void limpiarActualizar() {
        //this.codigoA = 0;
        this.anoA = 0;
        //this.origenA = 0;		
        this.tipodocA = "";
        //this.codigoA= 0;
        //this.numerodocA="";
        //this.ndirigidoA=0;
        //this.asuntoA="";
        //this.observacionA="";
        //this.estadoA="";


    }

    public void limpiar() {

        ///acediendo a sesion http
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        //File file = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area = String.valueOf(usuario.getCodarea());
        codarea = usuario.getCodarea();
        this.codarearemitenteN = area;
        this.codareaderivadoN = area;
        ///
        LlavesBean beans = null;
        beans = (LlavesBean) session.getAttribute("sLlaves");
        this.anoN = beans.getAno();
        this.norigenN = beans.getOrigen();
        this.tipodocN = beans.getTipodoc();
        this.correlativoN = beans.getCorrelativo();
        this.numdocN = beans.getNumdoc();
        this.fechaplazoN = beans.getFechaplazo();
        this.diasplazoN = beans.getDiasplazo();
        
        long correlativo = beans.getCorrelativo();
        List<JefeBean> bean2 = this.seguimientodocumentoDAO.max_derivado(correlativo, codarea);
        for (JefeBean e : bean2) {
            this.ficharemitenteN = e.getFicha();
        }
        long ficha = 0;
        int ficha_remite = 0;
        List<JefeBean> bean3 = this.seguimientodocumentoDAO.jefe(codarea);
        for (JefeBean e : bean3) {
            ficha = e.getFicha();
            ficha_remite = e.getFicha();
        }
        this.rescreN = usuario.getLogin();
        this.resactN = usuario.getLogin();
        this.ficha_dirigidoN = 0;
        this.comentarioN = "";
        this.observacionN = "";
        this.fecactN = new Date();
        this.feccreN = new Date();
        this.fecderivadoN = new Date();
        this.fecplazoN = new Date();
        this.fecrecepcionN = new Date();
        this.ind_adjuntar = 0;
        this.indicaarchivoN = 0;
        List<AreaBean> areas = seguimientodocumentoDAO.area_remitente();
        List itemsAreas = Utils.getAreas(areas);
        this.items4 = itemsAreas;
        for (AreaBean a : areas);
        ///Codigo Eli Diaz para el combo trabajador
        List<TrabajadorBean> trabajador = seguimientodocumentoDAO.trabajador_nuevo(codarea);
        List itemsTrabajador = Utils.getTrabajador(trabajador);
        this.ficha_dirigidoN = ficha;
        this.items2 = itemsTrabajador;
        for (TrabajadorBean t : trabajador);
        ///Codigo Eli Diaz para el combo trabajador
        List<TrabajadorBean> trabajador_remitente = seguimientodocumentoDAO.trabajador_remitente(codarea);
        List itemsremitente = Utils.getTrabajador_derivador(trabajador_remitente);
        //this.ficharemitenteN=ficha_remite;
        this.items5 = itemsremitente;
        for (TrabajadorBean t : trabajador_remitente);
        selectedItems2.clear();
        
        List<AreaBean> areas_remite = seguimientodocumentoDAO.area_remitente();
        List itemsAreas_remite = Utils.getAreas(areas);
        this.items3 = itemsAreas_remite;
        for (AreaBean a : areas);
        this.seleccionados.clear();
        this.vestadoN = "ESTA002";
        this.verPlazo = false;
        this.vprioridadN ="";
        this.nplazoN=2;
        this.ubicaarchivoN ="NADA";
        

    }

    public String validarFormulario() {
        String mensaje = "Falta ingresar: ";
        boolean ok = true;
        
        if (this.selectedItems2.size() == 0) {
            if (ok == false) {
                mensaje += ",Acciones";
            } else {
                mensaje += "Acciones";
            }
            ok = false;
        }
        
 
        
        if (this.codareaderivadoN.equals("100")) {
            if (ok == false) {
            	mensaje += ",Area Derivado";
            } else {
            	mensaje += "Area Derivado";
            }
            ok = false;
        }
        
        if (this.items2.size()==0) {
            if (ok == false) {
            	mensaje += ",Trabajador Derivado";
            } else {
            	mensaje += "Trabajador Derivado";
            }
            ok = false;
        }
        
        
        if (this.seleccionados.size() == 0) {
            if (ok == false) {
                mensaje += ",Trabajador Derivado";
            } else {
                mensaje += "Trabajador Derivado";
            }
            ok = false;
        }
        if (this.vprioridadN.equals("SELECCIONAR")) {
            if (ok == false) {
            	mensaje += ",Prioridad";
            } else {
            	mensaje += "Prioridad";
            }
            ok = false;
        }
        
        if (this.nplazoN==2) {
            if (ok == false) {
            	mensaje += ",Plazo";
            } else {
            	mensaje += "Plazo";
            }
            ok = false;
        }
        
        if (this.selectedItems2.size() == 0) {
            if (ok == false) {
            	mensaje += ",Acciones";
            } else {
            	mensaje += "Acciones";
            }
            ok = false;
        }
        
        
        /*
        if (this.nplazoN==1){
        	 if (this.comentarioN.trim().length() == 0) {
                 if (ok == false) {
                 	mensaje += ",Comentario";
                 } else {
                 	mensaje += "Comentario";
                 }
                 ok = false;
             }        	
        }
        */
       
        
        if (this.nplazoN==1) {    
        	
        	System.out.println("Quiero ver las fechas");
        	System.out.println(this.fecplazoN);
        	System.out.println(this.fecrecepcionN);
        	 
	        if (this.fecrecepcionN.after(this.fecplazoN)) {
	        	//msg += ",Fec Plazo Menor a Fec Recep";
	        		        	
	        	if (ok == false)
	        		mensaje += ",Fec Plazo Menor a Fec Derivado";
	        	else
	        		mensaje += "Fec Plazo Menor a Fec Derivado";
	        	ok = false;
	        	
	        } 
	        
	        if (this.fecrecepcionN.equals(this.fecplazoN)) {
	        	
	        	if (ok == false)
	        		mensaje += ",Fec Plazo Igual a Fec Derivado";
	        	else
	        		mensaje += "Fec Plazo  Igual a Fec Derivado";
	        	ok = false;
	        	
	        }
	        
	        }
        
        
        if (ok) {
            mensaje = "ok";
        }
        return mensaje;
    }
    
    public String validarFormularioA() {
        String mensaje = "Falta ingresar: ";
        boolean ok = true;
        
        
        
        if (this.codareaderivadoA.equals("100")) {
            if (ok == false) {
            	mensaje += ",Area Derivado";
            } else {
            	mensaje += "Area Derivado";
            }
            ok = false;
        }
        
        
        if (this.items2.size()==0) {
            if (ok == false) {
            	mensaje += ",Trabajador Derivado";
            } else {
            	mensaje += "Trabajador Derivado";
            }
            ok = false;
        }
        
        if (this.selectedItems2.size() == 0) {
            if (ok == false) {
            	mensaje += ",Acciones";
            } else {
            	mensaje += "Acciones";
            }
            ok = false;
        }
        
        
        
        if (this.vprioridadA.equals("SELECCIONAR")) {
            if (ok == false) {
            	mensaje += ",Prioridad";
            } else {
            	mensaje += "Prioridad";
            }
            ok = false;
        }
        
        if (ok) {
            mensaje = "ok";
        }
        return mensaje;
    }

    public ArrayList<SeguimientoEntranteBean> getSelectedSeguimientoss() {
        return selectedSeguimientoss;
    }

    public void setSelectedDocumentoss(ArrayList<SeguimientoEntranteBean> selectedSeguimientoss) {
        this.selectedSeguimientoss = selectedSeguimientoss;
    }

    public String getMultiRowSelect() {
        return multiRowSelect;
    }

    public SeguimientoEntranteDAO getSeguimientodocumentoDAO() {
        return seguimientodocumentoDAO;
    }

    public void setSeguimientodocumentoDAO(SeguimientoEntranteDAO seguimientodocumentoDAO) {
        this.seguimientodocumentoDAO = seguimientodocumentoDAO;
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

    public IServiciosSeguimientoEntrante getServiciosseguimientoDocuEntrante() {
        return serviciosseguimientoDocuEntrante;
    }

    public void setServiciosseguimientoDocuEntrante(
            IServiciosSeguimientoEntrante serviciosseguimientoDocuEntrante) {
        this.serviciosseguimientoDocuEntrante = serviciosseguimientoDocuEntrante;
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

    public List getItems7() {
        return items7;
    }

    public void setItems7(List items7) {
        this.items7 = items7;
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

    public boolean isVerNuevo() {
        return verNuevo;
    }

    public void setVerNuevo(boolean verNuevo) {
        this.verNuevo = verNuevo;
    }

    public boolean isVerNuevoCopia() {
        return verNuevoCopia;
    }

    public void setVerNuevoCopia(boolean verNuevoCopia) {
        this.verNuevoCopia = verNuevoCopia;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public List<SeguimientoEntranteBean> getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(List<SeguimientoEntranteBean> seguimiento) {
        this.seguimiento = seguimiento;
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

    public int getAnoA() {
        return anoA;
    }

    public void setAnoA(int anoA) {
        this.anoA = anoA;
    }

    public String getTipodocA() {
        return tipodocA;
    }

    public void setTipodocA(String tipodocA) {
        this.tipodocA = tipodocA;
    }

    public long getCorrelativoA() {
        return correlativoA;
    }

    public void setCorrelativoA(long correlativoA) {
        this.correlativoA = correlativoA;
    }

    public String getNumdocA() {
        return numdocA;
    }

    public void setNumdocA(String numdocA) {
        this.numdocA = numdocA;
    }

    public String getAsuntoA() {
        return asuntoA;
    }

    public void setAsuntoA(String asuntoA) {
        this.asuntoA = asuntoA;
    }

    public long getFicha_dirigidoA() {
        return ficha_dirigidoA;
    }

    public void setFicha_dirigidoA(long fichaDirigidoA) {
        ficha_dirigidoA = fichaDirigidoA;
    }

    public long getDerivadoA() {
        return derivadoA;
    }

    public void setDerivadoA(long derivadoA) {
        this.derivadoA = derivadoA;
    }

    public Date getFecderivadoA() {
        return fecderivadoA;
    }

    public void setFecderivadoA(Date fecderivadoA) {
        this.fecderivadoA = fecderivadoA;
    }

    public String getObservacionA() {
        return observacionA;
    }

    public void setObservacionA(String observacionA) {
        this.observacionA = observacionA;
    }

    public Date getFecrecepcionA() {
        return fecrecepcionA;
    }

    public void setFecrecepcionA(Date fecrecepcionA) {
        this.fecrecepcionA = fecrecepcionA;
    }

    public Date getFecplazoA() {
        return fecplazoA;
    }

    public void setFecplazoA(Date fecplazoA) {
        this.fecplazoA = fecplazoA;
    }

    public String getEstadoA() {
        return estadoA;
    }

    public void setEstadoA(String estadoA) {
        this.estadoA = estadoA;
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

    public String getRescreA() {
        return rescreA;
    }

    public void setRescreA(String rescreA) {
        this.rescreA = rescreA;
    }

    public String getResactA() {
        return resactA;
    }

    public void setResactA(String resactA) {
        this.resactA = resactA;
    }

    public String getNombre_derivadoA() {
        return nombre_derivadoA;
    }

    public void setNombre_derivadoA(String nombreDerivadoA) {
        nombre_derivadoA = nombreDerivadoA;
    }

    public String getAccionA() {
        return accionA;
    }

    public void setAccionA(String accionA) {
        this.accionA = accionA;
    }

    public String getComentarioA() {
        return comentarioA;
    }

    public void setComentarioA(String comentarioA) {
        this.comentarioA = comentarioA;
    }

    public int getAnoN() {
        return anoN;
    }

    public void setAnoN(int anoN) {
        this.anoN = anoN;
    }

    public String getTipodocN() {
        return tipodocN;
    }

    public void setTipodocN(String tipodocN) {
        this.tipodocN = tipodocN;
    }

    public long getCorrelativoN() {
        return correlativoN;
    }

    public void setCorrelativoN(long correlativoN) {
        this.correlativoN = correlativoN;
    }

    public String getNumdocN() {
        return numdocN;
    }

    public void setNumdocN(String numdocN) {
        this.numdocN = numdocN;
    }

    public String getAsuntoN() {
        return asuntoN;
    }

    public void setAsuntoN(String asuntoN) {
        this.asuntoN = asuntoN;
    }

    public long getFicha_dirigidoN() {
        return ficha_dirigidoN;
    }

    public void setFicha_dirigidoN(long fichaDirigidoN) {
        ficha_dirigidoN = fichaDirigidoN;
    }

    public long getDerivadoN() {
        return derivadoN;
    }

    public void setDerivadoN(long derivadoN) {
        this.derivadoN = derivadoN;
    }

    public Date getFecderivadoN() {
        return fecderivadoN;
    }

    public void setFecderivadoN(Date fecderivadoN) {
        this.fecderivadoN = fecderivadoN;
    }

    public String getObservacionN() {
        return observacionN;
    }

    public void setObservacionN(String observacionN) {
        this.observacionN = observacionN;
    }

    public Date getFecrecepcionN() {
        return fecrecepcionN;
    }

    public void setFecrecepcionN(Date fecrecepcionN) {
        this.fecrecepcionN = fecrecepcionN;
    }

    public Date getFecplazoN() {
        return fecplazoN;
    }

    public void setFecplazoN(Date fecplazoN) {
        this.fecplazoN = fecplazoN;
    }

    public String getEstadoN() {
        return estadoN;
    }

    public void setEstadoN(String estadoN) {
        this.estadoN = estadoN;
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

    public String getRescreN() {
        return rescreN;
    }

    public void setRescreN(String rescreN) {
        this.rescreN = rescreN;
    }

    public String getResactN() {
        return resactN;
    }

    public void setResactN(String resactN) {
        this.resactN = resactN;
    }

    public String getNombre_derivadoN() {
        return nombre_derivadoN;
    }

    public void setNombre_derivadoN(String nombreDerivadoN) {
        nombre_derivadoN = nombreDerivadoN;
    }

    public String getAccionN() {
        return accionN;
    }

    public void setAccionN(String accionN) {
        this.accionN = accionN;
    }

    public String getComentarioN() {
        return comentarioN;
    }

    public void setComentarioN(String comentarioN) {
        this.comentarioN = comentarioN;
    }

    public String getVestadoA() {
        return vestadoA;
    }

    public void setVestadoA(String vestadoA) {
        this.vestadoA = vestadoA;
    }

    public String getVestadoN() {
        return vestadoN;
    }

    public void setVestadoN(String vestadoN) {
        this.vestadoN = vestadoN;
    }

    public String getOrigenA() {
        return origenA;
    }

    public void setOrigenA(String origenA) {
        this.origenA = origenA;
    }

    public String getOrigenN() {
        return origenN;
    }

    public void setOrigenN(String origenN) {
        this.origenN = origenN;
    }

    public Date getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(Date fechaRecepcion) {
        fecha_recepcion = fechaRecepcion;
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

    public int getSeguimientoA() {
        return seguimientoA;
    }

    public void setSeguimientoA(int seguimientoA) {
        this.seguimientoA = seguimientoA;
    }

    public int getSeguimientoN() {
        return seguimientoN;
    }

    public void setSeguimientoN(int seguimientoN) {
        this.seguimientoN = seguimientoN;
    }

    public int getAreaderivadoA() {
        return areaderivadoA;
    }

    public void setAreaderivadoA(int areaderivadoA) {
        this.areaderivadoA = areaderivadoA;
    }

    public int getArearemitenteA() {
        return arearemitenteA;
    }

    public void setArearemitenteA(int arearemitenteA) {
        this.arearemitenteA = arearemitenteA;
    }

    public int getFicharemitenteA() {
        return ficharemitenteA;
    }

    public void setFicharemitenteA(int ficharemitenteA) {
        this.ficharemitenteA = ficharemitenteA;
    }

    public int getAreaderivadoN() {
        return areaderivadoN;
    }

    public void setAreaderivadoN(int areaderivadoN) {
        this.areaderivadoN = areaderivadoN;
    }

    public int getArearemitenteN() {
        return arearemitenteN;
    }

    public void setArearemitenteN(int arearemitenteN) {
        this.arearemitenteN = arearemitenteN;
    }

    public int getFicharemitenteN() {
        return ficharemitenteN;
    }

    public void setFicharemitenteN(int ficharemitenteN) {
        this.ficharemitenteN = ficharemitenteN;
    }

    public String getNombre_remitenteA() {
        return nombre_remitenteA;
    }

    public void setNombre_remitenteA(String nombreRemitenteA) {
        nombre_remitenteA = nombreRemitenteA;
    }

    public String getNombre_remitenteN() {
        return nombre_remitenteN;
    }

    public void setNombre_remitenteN(String nombreRemitenteN) {
        nombre_remitenteN = nombreRemitenteN;
    }

    public String getCodareaderivadoA() {
        return codareaderivadoA;
    }

    public void setCodareaderivadoA(String codareaderivadoA) {
        this.codareaderivadoA = codareaderivadoA;
    }

    public String getCodareaderivadoN() {
        return codareaderivadoN;
    }

    public void setCodareaderivadoN(String codareaderivadoN) {
        this.codareaderivadoN = codareaderivadoN;
    }

    public String getCodarearemitenteA() {
        return codarearemitenteA;
    }

    public void setCodarearemitenteA(String codarearemitenteA) {
        this.codarearemitenteA = codarearemitenteA;
    }

    public String getCodarearemitenteN() {
        return codarearemitenteN;
    }

    public void setCodarearemitenteN(String codarearemitenteN) {
        this.codarearemitenteN = codarearemitenteN;
    }

    public boolean isVerDetalles() {
        return verDetalles;
    }

    public boolean isVerDetalles1() {
        return verDetalles1;
    }

    public void setVerDetalles(boolean verDetalles) {
        this.verDetalles = verDetalles;
    }

    public void setVerDetalles1(boolean verDetalles1) {
        this.verDetalles1 = verDetalles1;
    }

    public long getSeguimiento_docD() {
        return seguimiento_docD;
    }

    public void setSeguimiento_docD(long seguimientoDocD) {
        seguimiento_docD = seguimientoDocD;
    }

    public String getNumero_docD() {
        return numero_docD;
    }

    public void setNumero_docD(String numeroDocD) {
        numero_docD = numeroDocD;
    }

    public List<SeguimientoEntranteBean> getSeguimiento_total() {
        return seguimiento_total;
    }

    public void setSeguimiento_total(List<SeguimientoEntranteBean> seguimientoTotal) {
        seguimiento_total = seguimientoTotal;
    }

    public int getNorigenA() {
        return norigenA;
    }

    public void setNorigenA(int norigenA) {
        this.norigenA = norigenA;
    }

    public List<SeguimientoEntranteBean> getBusca_seguimiento() {
        return busca_seguimiento;
    }

    public void setBusca_seguimiento(List<SeguimientoEntranteBean> buscaSeguimiento) {
        busca_seguimiento = buscaSeguimiento;
    }

    public int getNorigenN() {
        return norigenN;
    }

    public void setNorigenN(int norigenN) {
        this.norigenN = norigenN;
    }

    public int getIndicadorA() {
        return indicadorA;
    }

    public void setIndicadorA(int indicadorA) {
        this.indicadorA = indicadorA;
    }

    public int getIndicadorN() {
        return indicadorN;
    }

    public void setIndicadorN(int indicadorN) {
        this.indicadorN = indicadorN;
    }

    public String getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public String getUbicaarchivoA() {
        return ubicaarchivoA;
    }

    public void setUbicaarchivoA(String ubicaarchivoA) {
        this.ubicaarchivoA = ubicaarchivoA;
    }

    public int getIndicaarchivoA() {
        return indicaarchivoA;
    }

    public void setIndicaarchivoA(int indicaarchivoA) {
        this.indicaarchivoA = indicaarchivoA;
    }

    public String getUbicaarchivoN() {
        return ubicaarchivoN;
    }

    public void setUbicaarchivoN(String ubicaarchivoN) {
        this.ubicaarchivoN = ubicaarchivoN;
    }

    public int getIndicaarchivoN() {
        return indicaarchivoN;
    }

    public void setIndicaarchivoN(int indicaarchivoN) {
        this.indicaarchivoN = indicaarchivoN;
    }

    public void setmCarga(MCarga mCarga) {
        this.mCarga = mCarga;
    }

    public List<String> getSelectedItems2() {
        return selectedItems2;
    }

    public void setSelectedItems2(List<String> selectedItems2) {
        this.selectedItems2 = selectedItems2;
    }

    public LinkedHashMap<String, Object> getObservaciones2() {
        return observaciones2;
    }

    public int getDiasplazoA() {
        return diasplazoA;
    }

    public void setDiasplazoA(int diasplazoA) {
        this.diasplazoA = diasplazoA;
    }

    public Date getFechaplazoA() {
        return fechaplazoA;
    }

    public void setFechaplazoA(Date fechaplazoA) {
        this.fechaplazoA = fechaplazoA;
    }

    public Date getFechaplazoN() {
        return fechaplazoN;
    }

    public void setFechaplazoN(Date fechaplazoN) {
        this.fechaplazoN = fechaplazoN;
    }

    public int getDiasplazoN() {
        return diasplazoN;
    }

    public void setDiasplazoN(int diasplazoN) {
        this.diasplazoN = diasplazoN;
    }

    public void setSecuencialDAO(SecuencialDAO secuencialDAO) {
        this.secuencialDAO = secuencialDAO;
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

    public List<Seguir> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<Seguir> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public void setmBeanEntrantes(MBeanEntrantes mBeanEntrantes) {
        this.mBeanEntrantes = mBeanEntrantes;
    }

    public void setmBeanEntrantesGrupo(MBeanEntrantesGrupo mBeanEntrantesGrupo) {
        this.mBeanEntrantesGrupo = mBeanEntrantesGrupo;
    }
    
    public void setmBeanEntrantesGerencias(MBeanEntrantesGerencias mBeanEntrantesGerencias) {
		this.mBeanEntrantesGerencias = mBeanEntrantesGerencias;
	}

	public String getUbicaarchivoC() {
        return ubicaarchivoC;
    }

    public void setUbicaarchivoC(String ubicaarchivoC) {
        this.ubicaarchivoC = ubicaarchivoC;
    }

    public String getComentarioC() {
        return comentarioC;
    }

    public void setComentarioC(String comentarioC) {
        this.comentarioC = comentarioC;
    }

    public Boolean getSegInicial() {
        return segInicial;
    }

    public void setSegInicial(Boolean segInicial) {
        this.segInicial = segInicial;
    }

	
    
	public int getNrecepcionA() {
		return nrecepcionA;
	}

	public void setNrecepcionA(int nrecepcionA) {
		this.nrecepcionA = nrecepcionA;
	}

	public int getNrecepcionN() {
		return nrecepcionN;
	}

	public void setNrecepcionN(int nrecepcionN) {
		this.nrecepcionN = nrecepcionN;
	}

	public boolean isVerRecepcion() {
		return verRecepcion;
	}

	public void setVerRecepcion(boolean verRecepcion) {
		this.verRecepcion = verRecepcion;
	}

	public int getNplazoA() {
		return nplazoA;
	}

	public void setNplazoA(int nplazoA) {
		this.nplazoA = nplazoA;
	}

	public int getNplazoN() {
		return nplazoN;
	}

	public void setNplazoN(int nplazoN) {
		this.nplazoN = nplazoN;
	}

	public boolean isVerPlazo() {
		return verPlazo;
	}

	public void setVerPlazo(boolean verPlazo) {
		this.verPlazo = verPlazo;
	}

	public String getVprioridadA() {
		return vprioridadA;
	}

	public void setVprioridadA(String vprioridadA) {
		this.vprioridadA = vprioridadA;
	}

	public String getVprioridadN() {
		return vprioridadN;
	}

	public void setVprioridadN(String vprioridadN) {
		this.vprioridadN = vprioridadN;
	}

	public String getVprioridad() {
		return vprioridad;
	}

	public void setVprioridad(String vprioridad) {
		this.vprioridad = vprioridad;
	}

	

	public String getLs_ubicacion_seguimiento() {
		return ls_ubicacion_seguimiento;
	}

	public void setLs_ubicacion_seguimiento(String ls_ubicacion_seguimiento) {
		this.ls_ubicacion_seguimiento = ls_ubicacion_seguimiento;
	}

	public String getUbicacion_seguimiento() {
		return ubicacion_seguimiento;
	}

	public void setUbicacion_seguimiento(String ubicacion_seguimiento) {
		this.ubicacion_seguimiento = ubicacion_seguimiento;
	}

	public String getUbicacion_salida() {
		return ubicacion_salida;
	}

	public void setUbicacion_salida(String ubicacion_salida) {
		this.ubicacion_salida = ubicacion_salida;
	}

	public int getNindicador() {
		return nindicador;
	}

	public void setNindicador(int nindicador) {
		this.nindicador = nindicador;
	}

	public String getPrimeraficha() {
		return primeraficha;
	}

	public void setPrimeraficha(String primeraficha) {
		this.primeraficha = primeraficha;
	}

	public String getPrimeranombre() {
		return primeranombre;
	}

	public void setPrimeranombre(String primeranombre) {
		this.primeranombre = primeranombre;
	}

	public String getAsunto_documento() {
		return asunto_documento;
	}

	public void setAsunto_documento(String asunto_documento) {
		this.asunto_documento = asunto_documento;
	}

	public int getNindicador_pdf() {
		return nindicador_pdf;
	}

	public void setNindicador_pdf(int nindicador_pdf) {
		this.nindicador_pdf = nindicador_pdf;
	}

	public String getUbicaposicion() {
		return ubicaposicion;
	}

	public void setUbicaposicion(String ubicaposicion) {
		this.ubicaposicion = ubicaposicion;
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

	public String getUbicaarchivoorigenA() {
		return ubicaarchivoorigenA;
	}

	public void setUbicaarchivoorigenA(String ubicaarchivoorigenA) {
		this.ubicaarchivoorigenA = ubicaarchivoorigenA;
	}

	public int getNindicadorC() {
		return nindicadorC;
	}

	public void setNindicadorC(int nindicadorC) {
		this.nindicadorC = nindicadorC;
	}

	public String getLink_acceso() {
		return link_acceso;
	}

	public void setLink_acceso(String link_acceso) {
		this.link_acceso = link_acceso;
	}

	
	
	
	
	
    
}