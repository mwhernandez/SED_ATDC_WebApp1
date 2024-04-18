package com.sedapal.tramite.mbeans;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.j2solutionsit.fwk.patterns.jsf.bean.BaseSortableList;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoDerivado;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.ReporteGerencialBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.TipoConsulta;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.beans.consultas.AtencionDocSalidaBean;
import com.sedapal.tramite.dao.EntranteDAO;
import com.sedapal.tramite.dao.ReporteGerenciaDAO;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.nova.util.RecursoReporte;
import com.sedapal.tramite.nova.util.RecursoReporteNuevo;
import com.sedapal.tramite.util.Utils;

public class MBeanGerencial extends BaseSortableList implements Serializable {

    private String tipoConsulta="GER000";
    private int ficha = 0;
    private String area="100";
    private int narea;
    private boolean disArea = true;
    private boolean disEstado = true;
    private boolean disTipo = true;
    private boolean disFecha = true;
    private boolean disFicha = true;
    private boolean disBoton = true;
    private boolean disPrint = true;    
    protected boolean multiple = false;
    
    // Eli DIaz 18/03/2015
    private int narea_logo;
    private Date fechaInicial = new Date();
    private Date fechaFinal = new Date();
    ///
    private String inicio;
    private String fin;
    private String codigogerencia;
    
    /////
    private List items1;
    private List estados;
    private List tipos;
    private String estadoSeleccionado = "ESTA000";
    private String tipoSeleccionado = "ATD0000";
    
    private String opcion = "OPCION00";
    private LinkedHashMap<String, String> items7a = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> tiposCombo = new LinkedHashMap<String, String>();
       
    private ReporteGerenciaDAO reportegerenciaDAO;   
    private List<ReporteGerencialBean> gerencial;
    
    protected boolean enhancedMultiple;
    private boolean verRegistros = true; 
    
    // binding al combo
    private HtmlSelectOneMenu combo;
    private String itemSeleccionado;    
    private boolean verResultados = false;
    private String error;
    private boolean ver = false;
    private static Logger logger = Logger.getLogger("R1");
    
    private RecursoReport reporte; // inyectado
    private RecursoReport recursoReport;// para displayar    
    private RecursoReporte reportes; // inyectado    
    private RecursoReporte recursoReporte;// para displayar    
    private RecursoReporteNuevo reporteNuevo;
    private RecursoReporteNuevo recursoReporteNuevo;
    
    private Map<String, Object> parametros = new HashMap<String, Object>();
    private ExternalContext ec = null;
    private Map<String, Object> parametros2 = new HashMap<String, Object>();
    private ExternalContext ec2 = null;
    private Map<String, Object> parametros3 = new HashMap<String, Object>();
    private ExternalContext ec3 = null;
    
    //Agregado por Alfredo Panitz - 13/01/2012
    //Atributos para el reporte de atencion de documentos de salida
    private Boolean verAtencionDocs;
    private List<AtencionDocSalidaBean> listadoResultados;
    private List itemsgerencia;
    private int ncodgerencia;
    private int ncodareas;

    @PostConstruct
    public void carga() {
    	
        List<AreaBean> area_derivados = reportegerenciaDAO.areas();
        this.items7a = Utils.getAreasLink(area_derivados);
        
        
        List<AreaBean> areas = reportegerenciaDAO.areas();
        List itemsAreas = Utils.getAreas(areas);
        this.items1 = itemsAreas;
        
        
        List<TipoConsulta> tipoConsultas = reportegerenciaDAO.getTiposConsulta();        
        this.tiposCombo = Utils.getTiposConsulta(tipoConsultas);
        
        List<EstadosBean> estado = reportegerenciaDAO.estados();
        List itemsEstado = Utils.getEstado(estado);
        this.estados = itemsEstado;
        
        
        List<TiposBean> tipos_documento = reportegerenciaDAO.tipos_salida();
        List itemsTipo = Utils.getTipos(tipos_documento);
        this.tipos = itemsTipo;
        
        List<AreaBean> codigogerencia = reportegerenciaDAO.gerencias();
		List itemsgerencia = Utils.getAreas(codigogerencia);
		this.itemsgerencia = itemsgerencia;        
        
    }
    
    public void eventBuscaReportegerenciaplazogdi(ActionEvent actionEvent){
    	
    	 //System.out.println("Estoy en evento eventBuscaReportegerenciaplazo ....");
    	 //this.error = "Estoy en evento eventBuscaReportegerenciaplazo";
         //this.ver = true;
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
         System.out.println("Ver el codigo de area");
         System.out.println(this.ncodareas);
         
         if (this.ncodareas==100) {
             this.error = "Debe de seleccione un Area";
             this.ver = true;                        
             this.disFicha = false;
             this.disTipo = true;
         } else {                        
         	System.out.println("Estoy En el reporte de GDI");  
         	System.out.println("Area" + this.ncodareas);
         	System.out.println("Fecha Inicial" + sdf.format(fechaInicial));
         	System.out.println("Fecha Final" + sdf.format(fechaFinal));
         	this.inicio = sdf.format(this.fechaInicial);
            this.fin = sdf.format(this.fechaFinal);                                              
            //this.setVerResultados(true);
            //this.setVerAtencionDocs(false);                        
            this.gerencial = reportegerenciaDAO.BusquedaGerenciaPorGDI(this.ncodareas, sdf.format(fechaInicial), sdf.format(fechaFinal));
             
            
         }
    	
    }
    
    public void eventBusca(ActionEvent actionEvent) {
        System.out.println("Event Busca....");
        try {
            System.out.println("buscando");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            HttpSession session = (HttpSession) FacesContext
                    .getCurrentInstance().getExternalContext().getSession(true);
            Usuario usu = (Usuario) session.getAttribute("sUsuario");
            narea_logo = usu.getCodarea();
            System.out.println("VER TIPO DE REPORTE");
            System.out.println("TIPO REP:" + this.tipoConsulta);
            
            if (tipoConsulta.equals("GER001")) {
            	System.out.println("VER ESTADO");
                System.out.println("ESTADO:" + this.estadoSeleccionado);
                
                	if (this.area.equals("100")) {
                        this.error = "Debe de seleccione un Area";
                        this.ver = true;                        
                        this.disFicha = false;
                        this.disTipo = true;
                    } else {                        
                    	System.out.println("Estoy Aquiiiiiii en Equipo");  
                    	System.out.println("Area" + this.area);
                    	System.out.println("Fecha Inicial" + sdf.format(fechaInicial));
                    	System.out.println("Fecha Final" + sdf.format(fechaFinal));
                    	this.inicio = sdf.format(this.fechaInicial);
                        this.fin = sdf.format(this.fechaFinal);                                              
                        this.setVerResultados(true);
                        this.setVerAtencionDocs(false);                        
                        this.gerencial = reportegerenciaDAO.BusquedaGerenciaPorEquipo(Integer.parseInt(this.area), sdf.format(fechaInicial), sdf.format(fechaFinal));
                       
                    }
                
               
            } else if (tipoConsulta.equals("GER002")) {
                    if (this.area.equals("100")) {
                        this.error = "Debe de seleccione un Area";
                        this.ver = true;                        
                        this.disFicha = false;
                        this.disTipo = true;
                    } else if (this.ficha==0) {
                        this.error = "Debe digitar Número de Ficha";
                        this.ver = true;                        
                        this.disFicha = false;
                        this.disTipo = true;
                    }   else {                        
                    	System.out.println("Estoy Aquiiiiiii");  
                    	System.out.println("Area" + this.narea);
                    	System.out.println("Fecha Inicial" + sdf.format(fechaInicial));
                    	System.out.println("Fecha Final" + sdf.format(fechaFinal));                    	
                    	System.out.println("Ficha" + this.ficha); 
                    	this.inicio = sdf.format(this.fechaInicial);
                        this.fin = sdf.format(this.fechaFinal);                                              
                        this.setVerResultados(true);
                        this.setVerAtencionDocs(false);                        
                        this.gerencial = reportegerenciaDAO.BusquedaGerenciaPorTrabajador(Integer.parseInt(this.area), sdf.format(fechaInicial), sdf.format(fechaFinal), this.ficha);
                       
                    }
                } else  if (getTipoConsulta().equals("GER005") || getTipoConsulta().equals("GER002") || getTipoConsulta().equals("GER008")) {
                    if (this.area.equals("100")) {
                        this.error = "Seleccione Area.";
                        this.ver = true;
                        this.disPrint = false;
                    } else {
                        this.disPrint = true;
                        this.setVerResultados(true);

                        sdf = new SimpleDateFormat("dd-MM-yyyy");
                        

                        this.setVerResultados(false);
                        this.setVerAtencionDocs(true);
                    }
                }
            

        } catch (Exception exception) {
            this.error = "Error Interno, consulte con el administrador";
            this.ver = true;
            logger.error("MConsultas.eventBusca", exception);
            exception.printStackTrace();
        }

    }
    
    
    public void eventBuscaReportePlazo(ActionEvent actionEvent) {
        System.out.println("Estoy en evento eventBuscaReportePlazo....");
        try {
            System.out.println("buscando");            
            HttpSession session = (HttpSession) FacesContext
                    .getCurrentInstance().getExternalContext().getSession(true);
            Usuario usu = (Usuario) session.getAttribute("sUsuario");
            narea_logo = usu.getCodarea();
         
        } catch (Exception exception) {
            this.error = "Error Interno, consulte con el administrador";
            this.ver = true;
            logger.error("MConsultas.eventBusca", exception);
            exception.printStackTrace();
        }

    }

    
    public void eventLimpiar(ActionEvent event)throws IOException, ServletException {
		
		this.verRegistros = false;
		this.gerencial = null;
		
		
    }
    
    public void changeSelectionMode(ValueChangeEvent event) {

        String newValue = event.getNewValue() != null ? event.getNewValue()
                .toString() : null;
        multiple = false;
        enhancedMultiple = false;
        if ("Single".equals(newValue)) {
            //selected.clear();

            // build the new selected list
            ReporteGerencialBean documentosss;
            for (int i = 0, max = gerencial.size(); i < max; i++) {
                documentosss = (ReporteGerencialBean) gerencial.get(i);
                documentosss.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = false;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = false;
        }
    }

    public String descripcionCombo(LinkedHashMap<String, String> linkMap, String pCodigo) {
        String result = null;
        Set set = linkMap.entrySet();
        Iterator i = set.iterator();
        String valor = null, label = null;
        while (i.hasNext()) {
            Map.Entry val = (Map.Entry) (i.next());
            valor = (String) val.getValue();
            if (valor.equals(pCodigo)) {
                result = (String) val.getKey();
                break;
            }
        }
        return result;
    }

    public String descripcionCombo2(List lista, String pCodigo) {
        String result = null;
        SelectItem item = null;
        for (int i = 0; i < lista.size(); i++) {
            item = (SelectItem) lista.get(i);
            if (item.getValue().toString().equals(pCodigo)) {
                result = item.getLabel();
            }
        }
        return result;
    }

    public void cerrar(ActionEvent actionEvent) {
        this.ver = false;
    }

    
    public void eventSelectConsulta(ValueChangeEvent event) {
        String opc = (String) event.getNewValue();
        if (opc.equals("GER000")) {
            this.disArea = true;
            this.disFecha = true;
            this.disEstado = true;
            this.disTipo = false;
            this.disBoton = true;
            this.disFicha = true;
            this.disPrint = false;
        } else if (opc.equals("GER001")) {
            this.disFicha = false;
            this.disArea = false;
            this.disFecha = false;
            this.disEstado = true;
            this.disTipo = true;
            this.disBoton = false;
            this.disFicha = true;
            this.disPrint = false;            
        } else if (opc.equals("GER002")) {
            this.disArea = false;
            this.disFecha = false;
            this.disEstado = true;
            this.disTipo = true;
            this.disBoton = false;
            this.disFicha = false;  
            this.disPrint = true;
         
        } else if (opc.equals("GER003")) {
            this.disArea = false;
            this.disFecha = false;
            this.disEstado = true;
            this.disTipo = true;
            this.disBoton = false;
            this.disFicha = true;            
           
        } else if (opc.equals("GER004")) {
            this.disFecha = false;
            this.disEstado = false;
            this.disTipo = true;
            this.disBoton = false;
            this.disFicha = false;
            this.disArea = false;           
            
        }

        this.opcion = opc;

    }
	
      
 // para impresion
    //    
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
    	System.out.println("Tipo de Consulta");
    	System.out.println(this.tipoConsulta);
        
    	if (tipoConsulta.equals("GER000")) {        
            
    		System.out.println("ESTOY REPORTE ATENCION DE DOCUMENTOS POR TRABAJADOR !!!!");        	
        	parametros3.put("P_AREA", area);
            parametros3.put("P_FECHA_FINAL", String.valueOf(sdf.format(this.fechaFinal)));
            parametros3.put("P_FECHA_INICIAL", String.valueOf(sdf.format(this.fechaInicial)));
            parametros3.put("P_FICHA", String.valueOf(this.ficha));            
            parametros3.put("P_NOMBRE_AREA", nombre_equipo);            
            System.out.println("AREA :"+ area);   
            System.out.println("FECHA FINAL :" +  this.fin);
            System.out.println("FECHA INICIAL :"+ this.inicio);            
            System.out.println("FICHA :" + String.valueOf(this.ficha));
            System.out.println("NOMBRE AREA :" + nombre_equipo);  
            
            reportes.asignar("reporteEntr.pdf", ec3, parametros3, "reportes/rpte_gerencial_trabajador.jasper");//REPORTE EN BLANCO POR NO TENER DATOS
            recursoReporte = reportes;
            
    	}  	else if (tipoConsulta.equals("GER001")) {
        	
        	System.out.println("ESTOY REPORTE ATENCION DE DOCUMENTOS POR EQUIPO !!!!");        	
        	parametros3.put("P_AREA", area);
        	parametros3.put("P_FECHA_FINAL", String.valueOf(sdf.format(this.fechaFinal)));
        	parametros3.put("P_FECHA_INICIAL", String.valueOf(sdf.format(this.fechaInicial)));                                                
            parametros3.put("P_NOMBRE_AREA", nombre_equipo);            
            System.out.println("AREA :"+ area);
            System.out.println("FECHA FINAL :" +  this.fin);
            System.out.println("FECHA INICIAL :"+ this.inicio);                       
            System.out.println("NOMBRE AREA :" + nombre_equipo);
            reportes.asignar("reporteEquipo.pdf", ec3, parametros3, "reportes/rpte_gerencial_equipos.jasper");// se le puede adicionar
            recursoReporte = reportes;
        
           
        }  	else if (tipoConsulta.equals("GER002")) {
        	
        	System.out.println("ESTOY REPORTE ATENCION DE DOCUMENTOS POR TRABAJADOR !!!!");        	
        	parametros3.put("P_AREA", area);
            parametros3.put("P_FECHA_FINAL", String.valueOf(sdf.format(this.fechaFinal)));
            parametros3.put("P_FECHA_INICIAL", String.valueOf(sdf.format(this.fechaInicial)));
            parametros3.put("P_FICHA", String.valueOf(this.ficha));            
            parametros3.put("P_NOMBRE_AREA", nombre_equipo);            
            System.out.println("AREA :"+ area);   
            System.out.println("FECHA FINAL :" +  this.fin);
            System.out.println("FECHA INICIAL :"+ this.inicio);            
            System.out.println("FICHA :" + String.valueOf(this.ficha));
            System.out.println("NOMBRE AREA :" + nombre_equipo);  
            
            reportes.asignar("reporteEntr.pdf", ec3, parametros3, "reportes/rpte_gerencial_trabajador.jasper");// se le puede adicionar
            recursoReporte = reportes;
        	
        }
       
        else {

            reportes.asignar("ticket.pdf", ec3, parametros3,"reportes/blanco.jasper");// REPORTE EN BLANCO POR NO TENER DATOS
            recursoReporte = reportes;

        }
       
        return recursoReporte;        

    }
    
    
    public RecursoReporteNuevo getRecursoReporteNuevo() {
        HttpSession session = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ec2 = FacesContext.getCurrentInstance().getExternalContext();
        /*
        ReporteGerencialBean p = null;
        for (ReporteGerencialBean q : this.gerencial) {
            if (q.isSelected()) {
                p = q;
            }
        }
        */
        
        session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        String area_logeado = String.valueOf(usuario.getNomequipo());
        session.getAttribute("sUsuario");

        /*
		if (p != null) {
		*/
           
            parametros2.put("P_AREA", String.valueOf(ncodareas));
            parametros2.put("P_INICIO", String.valueOf(sdf.format(this.fechaInicial)));
            parametros2.put("P_FIN", String.valueOf(sdf.format(this.fechaFinal)));            
            parametros2.put("P_AREA_ORIGEN", area_logeado);
            
            //System.out.println("Parametros : ");
            System.out.println("P_AREA        : " + String.valueOf(ncodareas));            
            System.out.println("P_INICIO" + String.valueOf(sdf.format(this.fechaInicial)));
            System.out.println("P_FIN" + String.valueOf(sdf.format(this.fechaFinal)));
            System.out.println("P_AREA_ORIGEN : " + area_logeado);
            
            reporteNuevo.asignar("reporteEnvio.pdf", ec2, parametros2, "reportes/reportedocentradagdi.jasper");
            recursoReporteNuevo = reporteNuevo;
            
         /*

        } else {
            reportes.asignar("ticket.pdf", ec2, parametros2, "reportes/blanco.jasper");// se le puede adicionar
            recursoReporteNuevo = reporteNuevo;

        }
        */

        return recursoReporteNuevo;

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

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }
    
    public RecursoReport getReporte() {
        return reporte;
    }

    public void setReporte(RecursoReport reporte) {
        this.reporte = reporte;
    }

    public int getFicha() {
        return ficha;
    }

    public void setFicha(int ficha) {
        this.ficha = ficha;
    }

    public boolean isDisArea() {
        return disArea;
    }

    public void setDisArea(boolean disArea) {
        this.disArea = disArea;
    }

    public boolean isDisEstado() {
        return disEstado;
    }

    public void setDisEstado(boolean disEstado) {
        this.disEstado = disEstado;
    }
    
    

    public boolean isDisTipo() {
		return disTipo;
	}

	public void setDisTipo(boolean disTipo) {
		this.disTipo = disTipo;
	}

	public boolean isDisFecha() {
        return disFecha;
    }

    public void setDisFecha(boolean disFecha) {
        this.disFecha = disFecha;
    }

    public boolean isDisFicha() {
        return disFicha;
    }

    public void setDisFicha(boolean disFicha) {
        this.disFicha = disFicha;
    }

    public boolean isDisBoton() {
        return disBoton;
    }

    public void setDisBoton(boolean disBoton) {
        this.disBoton = disBoton;
    }

    public LinkedHashMap<String, String> getItems7a() {
        return items7a;
    }

    public void setItems7a(LinkedHashMap<String, String> items7a) {
        this.items7a = items7a;
    }

   

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public List getEstados() {
        return estados;
    }

    public void setEstados(List estados) {
        this.estados = estados;
    }
    
    

    public List getTipos() {
		return tipos;
	}

	public void setTipos(List tipos) {
		this.tipos = tipos;
	}

	public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }
    
    

    

	

	public boolean isVerResultados() {
        return verResultados;
    }

    public void setVerResultados(boolean verResultados) {
        this.verResultados = verResultados;
    }
    

    public List<ReporteGerencialBean> getGerencial() {
		return gerencial;
	}

	public void setGerencial(List<ReporteGerencialBean> gerencial) {
		this.gerencial = gerencial;
	}

	public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }

    public boolean isDisPrint() {
        return disPrint;
    }

    public void setDisPrint(boolean disPrint) {
        this.disPrint = disPrint;
    }

    public LinkedHashMap<String, String> getTiposCombo() {
        return tiposCombo;
    }

    public void setTiposCombo(LinkedHashMap<String, String> tiposCombo) {
        this.tiposCombo = tiposCombo;
    }

    /**
     * @return the verAtencionDocs
     */
    public Boolean getVerAtencionDocs() {
        return verAtencionDocs;
    }

    /**
     * @param verAtencionDocs the verAtencionDocs to set
     */
    public void setVerAtencionDocs(Boolean verAtencionDocs) {
        this.verAtencionDocs = verAtencionDocs;
    }

    /**
     * @return the listadoResultados
     */
    public List<AtencionDocSalidaBean> getListadoResultados() {
        return listadoResultados;
    }

    /**
     * @param listadoResultados the listadoResultados to set
     */
    public void setListadoResultados(List<AtencionDocSalidaBean> listadoResultados) {
        this.listadoResultados = listadoResultados;
    }

    

	public HtmlSelectOneMenu getCombo() {
		return combo;
	}

	public void setCombo(HtmlSelectOneMenu combo) {
		this.combo = combo;
	}

	public String getItemSeleccionado() {
		return itemSeleccionado;
	}

	public void setItemSeleccionado(String itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
	}

	

	public ReporteGerenciaDAO getReportegerenciaDAO() {
		return reportegerenciaDAO;
	}

	public int getNarea_logo() {
		return narea_logo;
	}

	public void setNarea_logo(int nareaLogo) {
		narea_logo = nareaLogo;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public boolean isVerRegistros() {
		return verRegistros;
	}

	public void setVerRegistros(boolean verRegistros) {
		this.verRegistros = verRegistros;
	}

	public void setReportegerenciaDAO(ReporteGerenciaDAO reportegerenciaDAO) {
		this.reportegerenciaDAO = reportegerenciaDAO;
	}

	public boolean isEnhancedMultiple() {
		return enhancedMultiple;
	}

	public void setEnhancedMultiple(boolean enhancedMultiple) {
		this.enhancedMultiple = enhancedMultiple;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public int getNarea() {
		return narea;
	}

	public void setNarea(int narea) {
		this.narea = narea;
	}

	public String getTipoSeleccionado() {
		return tipoSeleccionado;
	}

	public void setTipoSeleccionado(String tipoSeleccionado) {
		this.tipoSeleccionado = tipoSeleccionado;
	}

	public void setRecursoReport(RecursoReport recursoReport) {
		this.recursoReport = recursoReport;
	}

	public void setReportes(RecursoReporte reportes) {
		this.reportes = reportes;
	}

	public ExternalContext getEc() {
		return ec;
	}

	public void setEc(ExternalContext ec) {
		this.ec = ec;
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

	public RecursoReporte getReportes() {
		return reportes;
	}

	

	public void setRecursoReporte(RecursoReporte recursoReporte) {
		this.recursoReporte = recursoReporte;
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

	public String getCodigogerencia() {
		return codigogerencia;
	}

	public void setCodigogerencia(String codigogerencia) {
		this.codigogerencia = codigogerencia;
	}

	public List getItemsgerencia() {
		return itemsgerencia;
	}

	public void setItemsgerencia(List itemsgerencia) {
		this.itemsgerencia = itemsgerencia;
	}

	public int getNcodgerencia() {
		return ncodgerencia;
	}

	public void setNcodgerencia(int ncodgerencia) {
		this.ncodgerencia = ncodgerencia;
	}

	public List getItems1() {
		return items1;
	}

	public void setItems1(List items1) {
		this.items1 = items1;
	}

	public int getNcodareas() {
		return ncodareas;
	}

	public void setNcodareas(int ncodareas) {
		this.ncodareas = ncodareas;
	}

	    
    
    
}