package com.sedapal.tramite.mbeans;

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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoDerivado;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.TipoConsulta;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.beans.consultas.AtencionDocSalidaBean;
import com.sedapal.tramite.dao.EntranteDAO;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.util.Utils;

public class MConsultas implements Serializable {

    private String tipoConsulta;
    private int ficha = 0;
    private boolean disArea = true;
    private boolean disEstado = true;
    private boolean disTipo = true;
    private boolean disFecha = true;
    private boolean disFicha = true;
    private boolean disBoton = true;
    private boolean disPrint = false;
    private Date fechaInicial = new Date();
    private Date fechaFinal = new Date();
    private List estados;
    private List tipos;
    private List trabajador;
    private String estadoSeleccionado = "ESTA000";
    private String tipoSeleccionado = "ATD0000";
    
    private String opcion = "OPCION00";
    private LinkedHashMap<String, String> items7a = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> tiposCombo = new LinkedHashMap<String, String>();
    private EntranteDAO entranteDAO;
    private String area;
    private List<DocumentoDerivado> derivados;
    private boolean verColumna = false;
    private boolean verColumnaObservacion = false;
    private boolean verColumnaAdjunto = false;
    private boolean verColumnaFechaAten = false;
    private boolean verColumnaDias = false;
    private boolean verColumnaFechaReg = false;
    private boolean verColumnaRegEntr = false;
    private boolean verColumnaUsuario = false;
    private boolean disderivados = false;
    private boolean dislista = false;
    private boolean verColumnaFechaDocumento = false;
    
    // binding al combo
    private HtmlSelectOneMenu combo;
    private String itemSeleccionado;
    private String nombre_trabajador;
    
    private boolean verResultados = false;
    private String error;
    private boolean ver = false;
    private static Logger logger = Logger.getLogger("R1");
    private RecursoReport reporte; // inyectado
    private RecursoReport recursoReport;// para displayar
    private Map<String, Object> parametros = new HashMap<String, Object>();
    private ExternalContext ec = null;
    //Agregado por Alfredo Panitz - 13/01/2012
    //Atributos para el reporte de atencion de documentos de salida
    private Boolean verAtencionDocs;
    private List<AtencionDocSalidaBean> listadoResultados;

    @PostConstruct
    public void carga() {
        List<AreaBean> area_derivados = getEntranteDAO().areas();
        this.items7a = Utils.getAreasLink(area_derivados);
        
        List<TipoConsulta> tipoConsultas = getEntranteDAO().getTiposConsulta();        
        this.tiposCombo = Utils.getTiposConsulta(tipoConsultas);
        List<EstadosBean> estado = getEntranteDAO().estados();
        List itemsEstado = Utils.getEstado(estado);
        this.estados = itemsEstado;
        
        
        List<TiposBean> tipos_documento = getEntranteDAO().tipos_salida();
        List itemsTipo = Utils.getTipos(tipos_documento);
        this.tipos = itemsTipo;
        
        
        // Reporte
        HttpSession session = (HttpSession) FacesContext
                .getCurrentInstance().getExternalContext().getSession(true);
        Usuario usu = (Usuario) session.getAttribute("sUsuario");
        parametros.put("P_AREA", usu.getNcodarea());
        reporte.asignar("reporteDerivados.pdf", ec, parametros, "reportes/derivadosArea.jasper");
        recursoReport = reporte;
        this.disFicha = false;
        this.disArea = false;
        this.disFecha = false;
        this.disEstado = false;
        this.disBoton = false;
        this.disTipo = false;
        //this.disFicha = true;
        this.verColumna = false;
        this.verColumnaObservacion = false;
        this.verColumnaAdjunto = false;
        this.verColumnaFechaAten = false;
        this.verColumnaDias = false;
        this.verColumnaFechaReg = false;
        this.verColumnaFechaDocumento = false;
        this.verColumnaRegEntr = false;
        this.verColumnaUsuario = false;
        this.disderivados = false;
        this.dislista = false;
        
        this.setVerAtencionDocs(false);
        this.setVerResultados(false);
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
        if (opc.equals("CONS000")) {
            this.disArea = true;
            this.disFecha = true;
            this.disEstado = true;
            this.disTipo = false;
            this.disBoton = true;
            this.disFicha = true;
            this.disPrint = false;
        } else if (opc.equals("CONS001")) {
            this.disFicha = false;
            this.disArea = false;
            this.disFecha = false;
            this.disEstado = false;
            this.disTipo = true;
            this.disBoton = false;
            this.disFicha = true;
            this.verColumna = true;
            this.disderivados = true;
            this.dislista = false;
            this.disPrint = false;
            //limpia la grilla desde la seleccion de consulta
            this.setVerResultados(false);
            this.setVerAtencionDocs(false);
        } else if (opc.equals("CONS002")) {
            this.disArea = false;
            this.disFecha = false;
            this.disEstado = true;
            this.disTipo = false;
            this.disBoton = false;
            this.disFicha = true;
            this.verColumna = false;
            this.verColumnaFechaAten = false;
            this.verColumnaDias = false;
            this.verColumnaFechaReg = false;
            this.verColumnaFechaDocumento = false;
            this.verColumnaRegEntr = false;
            this.verColumnaUsuario = true;
            this.dislista = true;
            this.disderivados = false;
            this.disPrint = false;
            //limpia la grilla desde la seleccion de consulta
            this.setVerResultados(false);
            this.setVerAtencionDocs(false);
        } else if (opc.equals("CONS003")) {
            this.disArea = false;
            this.disFecha = false;
            this.disEstado = true;
            this.disTipo = true;
            this.disBoton = false;
            this.disFicha = true;
            this.verColumna = false;
            this.disderivados = true;
            this.dislista = false;
            //limpia la grilla desde la seleccion de consulta
            this.setVerResultados(false);
            this.setVerAtencionDocs(false);
        } else if (opc.equals("CONS004")) {
            this.disFecha = false;
            this.disEstado = false;
            this.disTipo = true;
            this.disBoton = false;
            this.disFicha = false;
            this.disArea = false;
            this.verColumna = true;
            this.disderivados = true;
            this.dislista = false;
            //limpia la grilla desde la seleccion de consulta
            this.setVerResultados(false);
            this.setVerAtencionDocs(false);
        } else if (opc.equals("CONS005")) {
            this.disArea = false;
            this.disFecha = false;
            this.disBoton = false;
            this.disEstado = true;
            this.disTipo = true;
            this.disFicha = true;
            this.verColumna = false;
            this.dislista = true;
            this.disderivados = false;            
            this.verColumnaRegEntr= true;
            this.verColumnaFechaReg = true;
            this.verColumnaFechaAten = true;
            this.verColumnaDias=true;
            this.verColumnaFechaDocumento = false;            
            //limpia la grilla desde la seleccion de consulta
            this.setVerResultados(false);
            this.setVerAtencionDocs(false);
        } else if (opc.equals("CONS006")) {
            this.disArea = false;
            this.disFecha = false;
            this.disBoton = false;
            this.disEstado = true;
            this.disTipo = true;
            this.disFicha = true;
            this.verColumna = true;
            this.verColumnaObservacion = true;
            this.verColumna = true;    
            this.disderivados = true;
            this.dislista = false;
            //limpia la grilla desde la seleccion de consulta
            this.setVerResultados(false);
            this.setVerAtencionDocs(false);
        }  else if (opc.equals("CONS007")) {
            this.disArea = false;
            this.disFecha = false;
            this.disBoton = false;
            this.disEstado = false;
            this.disTipo = true;
            this.disFicha = false;
            this.verColumna = true;
            this.verColumnaObservacion = false;
            this.verColumnaAdjunto = false;
            this.disderivados = true;
            this.dislista = false;
            //limpia la grilla desde la seleccion de consulta
            this.setVerResultados(false);
            this.setVerAtencionDocs(false);
        }	else if (opc.equals("CONS008")) {
            this.disArea = false;
            this.disFecha = false;
            this.disBoton = false;
            this.disEstado = true;
            this.disTipo = true;
            this.disFicha = false;
            this.verColumna = true;
            this.verColumnaObservacion = false;
            this.verColumnaAdjunto = false;
            this.disderivados = true;
            this.dislista = false;
            //limpia la grilla desde la seleccion de consulta
            this.setVerResultados(false);
            this.setVerAtencionDocs(false);
        }

        this.opcion = opc;

    }

    public void eventBusca(ActionEvent actionEvent) {
        System.out.println("Event Busca....");
        try {
            System.out.println("buscando");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
            HttpSession session = (HttpSession) FacesContext
                    .getCurrentInstance().getExternalContext().getSession(true);
            Usuario usu = (Usuario) session.getAttribute("sUsuario");
            if (tipoConsulta.equals("CONS004")) {
                System.out.println("AREA:" + this.area);                
                if (this.ficha <= 0) {
                    this.error = "Ingrese Número de Ficha";
                    this.ver = true;
                    this.disPrint = false;
                } else {
                    this.derivados = this.entranteDAO.derivadosSP(this.opcion, this.area,
                            sdf.format(fechaInicial), sdf.format(fechaFinal),
                            estadoSeleccionado, String.valueOf(ficha), String.valueOf(usu.getNcodarea()));                    
                    this.disPrint = true;
                    this.setVerResultados(true);
                    this.setVerAtencionDocs(false);
                }
            } else if (tipoConsulta.equals("CONS001")  || tipoConsulta.equals("CONS003") || tipoConsulta.equals("CONS006") ) {
                    if (this.area.equals("100")) {
                        this.error = "Seleccione Area.";
                        this.ver = true;
                        this.disPrint = false;
                    } else {                        
                    	
                        this.derivados = this.entranteDAO.derivadosSP(this.opcion, this.area,
                                sdf.format(fechaInicial), sdf.format(fechaFinal),
                                estadoSeleccionado, String.valueOf(ficha), String.valueOf(usu.getNcodarea()));
                       
                        this.disPrint = false;
                        this.setVerResultados(true);
                        this.setVerAtencionDocs(false);
                    }
                    
            	} else if (tipoConsulta.equals("CONS007")) {
                    
	            	if (this.area.equals("100")) {
	                    this.error = "Seleccione Area.";
	                    this.ver = true;
	                    this.disPrint = true;
	                } else {                        
	                	
	                    this.derivados = this.entranteDAO.derivadosSP(this.opcion, this.area,
	                            sdf.format(fechaInicial), sdf.format(fechaFinal),
	                            estadoSeleccionado, String.valueOf(ficha), String.valueOf(usu.getNcodarea()));
	                    
	                    
	                   
	                    this.disPrint = true;
	                    this.setVerResultados(true);
	                    this.setVerAtencionDocs(false);
	                }
	            	
	            	
            	} else if (tipoConsulta.equals("CONS008")) {
                
            	if (this.area.equals("100")) {
                    this.error = "Seleccione Area.";
                    this.ver = true;
                    this.disPrint = true;
                } else {   
                	
                	System.out.println("Estoy en la consulta 8");
                	System.out.println(tipoConsulta);
                	System.out.println("Envio Parametros");
                	System.out.println(this.opcion);
                	System.out.println(this.area);
                	System.out.println(fechaInicial);
                	System.out.println(fechaFinal);
                	System.out.println(ficha);
                	System.out.println(usu.getNcodarea());
                	
                	this.derivados = this.entranteDAO.derivadosSP(this.opcion, this.area,
                             sdf.format(fechaInicial), sdf.format(fechaFinal),
                             estadoSeleccionado, String.valueOf(ficha), String.valueOf(usu.getNcodarea()));
                	
                	List<TrabajadorBean> bean = entranteDAO.trabajadorunico(ficha);                	
                	 for (TrabajadorBean t : bean) {
                		 this.nombre_trabajador = t.getNombre_completo();
                     }
                	
                	                 	
                	
                   
                    
                    
                   
                    this.disPrint = true;
                    this.setVerResultados(true);
                    this.setVerAtencionDocs(false);
                }
                    
                  
                } else  if (getTipoConsulta().equals("CONS005")) {
                    if (this.area.equals("100")) {
                        this.error = "Seleccione Area.";
                        this.ver = true;
                        this.disPrint = false;
                    } else {
                        this.disPrint = true;
                        this.setVerResultados(true);

                        sdf = new SimpleDateFormat("dd-MM-yyyy");
                        this.setListadoResultados(getEntranteDAO().listadoAtencionDocssalida(getTipoConsulta(), getArea(), 
                        		sdf.format(getFechaInicial()), sdf.format(getFechaFinal()), tipoSeleccionado ));

                        this.setVerResultados(false);
                        this.setVerAtencionDocs(true);
                    }
                
                }   else  if (getTipoConsulta().equals("CONS002")) {
                	
                	if (this.area.equals("100")) {
                        this.error = "Seleccione Area.";
                        this.ver = true;
                        this.disPrint = false;
                    } else {
                        this.disPrint = false;
                        this.setVerResultados(true);

                        sdf = new SimpleDateFormat("dd-MM-yyyy");
                        this.setListadoResultados(getEntranteDAO().listadoAtencionDocssalida(getTipoConsulta(), getArea(), 
                        		sdf.format(getFechaInicial()), sdf.format(getFechaFinal()), tipoSeleccionado ));

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

    public RecursoReport getReporte() {
        return reporte;
    }

    public void setReporte(RecursoReport reporte) {
        this.reporte = reporte;
    }

    public RecursoReport getRecursoReport() {
        ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Usuario usu = (Usuario) session.getAttribute("sUsuario");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        if (tipoConsulta.equals("CONS000")) {
            parametros.put("P_AREA", usu.getNcodarea());
            reporte.asignar("reporteDerivados.pdf", ec, parametros,
                    "reportes/derivadosArea.jasper");
        } else {
            if (tipoConsulta.equals("CONS004")) {
                if (this.estadoSeleccionado.equals("ESTA000"))//todos				
                {
                    parametros.put("P_AREA", this.area);
                    parametros.put("P_FECHAINICIO", sdf.format(this.fechaInicial));
                    parametros.put("P_FECHAFIN", sdf.format(this.fechaFinal));
                    parametros.put("P_FICHA", String.valueOf(this.ficha));
                    parametros.put("P_AREA_DESC", usu.getNomequipo());
                    parametros.put("P_ESTADO_DESC", this.descripcionCombo2(this.estados, this.estadoSeleccionado));
                    reporte.asignar("reporteDerivados.pdf", ec, parametros,
                            "reportes/derivadosAreaFechaEstadoFichaTodos.jasper");
                } else {
                    logger.debug("Al reporte jasper:");
                    logger.debug(this.area);
                    logger.debug(sdf.format(this.fechaInicial));
                    logger.debug(sdf.format(this.fechaFinal));
                    logger.debug(this.estadoSeleccionado);
                    logger.debug(String.valueOf(this.ficha));
                    logger.debug(usu.getNomequipo());
                    logger.debug(this.descripcionCombo2(this.estados, this.estadoSeleccionado));

                    parametros.put("P_AREA", this.area);
                    parametros.put("P_FECHAINICIO", sdf.format(this.fechaInicial));
                    parametros.put("P_FECHAFIN", sdf.format(this.fechaFinal));
                    parametros.put("P_ESTADO", this.estadoSeleccionado);
                    parametros.put("P_FICHA", String.valueOf(this.ficha));
                    parametros.put("P_AREA_DESC", usu.getNomequipo());
                    parametros.put("P_ESTADO_DESC", this.descripcionCombo2(this.estados, this.estadoSeleccionado));
                    reporte.asignar("reporteDerivados.pdf", ec, parametros,
                            "reportes/derivadosAreaFechaEstadoFicha.jasper");
                }
            } else if (tipoConsulta.equals("CONS001") || tipoConsulta.equals("CONS002") || tipoConsulta.equals("CONS003")) {
                if (this.opcion.equals("CONS001")) {
                    if (this.estadoSeleccionado.equals("ESTA000"))//todos
                    {
                        parametros.put("P_AREA", area);
                        parametros.put("P_FECHAINICIO", sdf.format(this.fechaInicial));
                        parametros.put("P_FECHAFIN", sdf.format(this.fechaFinal));
                        parametros.put("P_AREA_DESC", this.descripcionCombo(items7a, this.area));
                        parametros.put("P_ESTADO_DESC", this.descripcionCombo2(this.estados, this.estadoSeleccionado));
                        parametros.put("P_AREA_LOGIN", String.valueOf(usu.getNcodarea()));
                        reporte.asignar("reporteDerivados.pdf", ec, parametros,
                                "reportes/derivadosAreaFechaEstadoTodos.jasper");
                    } else {
                        parametros.put("P_AREA", area);
                        parametros.put("P_FECHAINICIO", sdf.format(this.fechaInicial));
                        parametros.put("P_FECHAFIN", sdf.format(this.fechaFinal));
                        parametros.put("P_ESTADO", this.estadoSeleccionado);
                        parametros.put("P_AREA_DESC", this.descripcionCombo(items7a, this.area));
                        parametros.put("P_ESTADO_DESC", this.descripcionCombo2(this.estados, this.estadoSeleccionado));
                        parametros.put("P_AREA_LOGIN", String.valueOf(usu.getNcodarea()));
                        reporte.asignar("reporteDerivados.pdf", ec, parametros,
                                "reportes/derivadosAreaFechaEstado.jasper");
                    }
                } else if (this.opcion.equals("CONS002")) {
                    parametros.put("P_AREA", area);
                    parametros.put("P_AREA_DESC", this.descripcionCombo(items7a, this.area));
                    reporte.asignar("reporteDerivados.pdf", ec, parametros,
                            "reportes/derivadosArea.jasper");
                } else if (this.opcion.equals("CONS003")) {
                    parametros.put("P_AREA", area);
                    parametros.put("P_FECHAINICIO", sdf.format(this.fechaInicial));
                    parametros.put("P_FECHAFIN", sdf.format(this.fechaFinal));
                    parametros.put("P_AREA_DESC", this.descripcionCombo(items7a, this.area));
                    reporte.asignar("reporteDerivados.pdf", ec, parametros,
                            "reportes/derivadosAreaFecha.jasper");
                }
            } else if (getTipoConsulta().equals("CONS005")) {
                parametros.put("P_AREA", area);
                parametros.put("P_FECHAINICIO", sdf.format(this.fechaInicial));
                parametros.put("P_FECHAFIN", sdf.format(this.fechaFinal));
                parametros.put("P_AREA_DESC", this.descripcionCombo(items7a, this.area));
                reporte.asignar("rptAtencionDocsSalida.pdf", ec, parametros, "reportes/rptAtencionDocsSalida.jasper");
                
            } else if (getTipoConsulta().equals("CONS007")) {
            	 parametros.put("P_AREA", this.area);
                 parametros.put("P_FECHAINICIO", sdf.format(this.fechaInicial));
                 parametros.put("P_FECHAFIN", sdf.format(this.fechaFinal));                 
                 parametros.put("P_FICHA", String.valueOf(this.ficha));
                 parametros.put("P_AREA_DESC", this.descripcionCombo(items7a, this.area));
                 reporte.asignar("rptAtencionDocsSalida.pdf", ec, parametros, "reportes/rptatenciondocAreaFechaEstadoFicha.jasper");                 																		
                 
            } else if (tipoConsulta.equals("CONS008")) {
            	
            	List<TrabajadorBean> bean = entranteDAO.trabajadorunico(ficha);                	
           	 	for (TrabajadorBean t : bean) {
           		 this.nombre_trabajador = t.getNombre_completo();
                }
           	 
           	    parametros.put("P_AREA", this.area);
                parametros.put("P_FECHAINICIO", sdf.format(this.fechaInicial));
                parametros.put("P_FECHAFIN", sdf.format(this.fechaFinal));                 
                parametros.put("P_FICHA", String.valueOf(this.ficha));
                parametros.put("P_AREA_DESC", this.descripcionCombo(items7a, this.area));
                parametros.put("P_NOMBRE", nombre_trabajador);
                reporte.asignar("rptAtencionDocsSalida.pdf", ec, parametros, "reportes/rpt_cargodocTrabajador.jasper");

            	
            }
        }
        recursoReport = reporte;
        return recursoReport;

    }

    public void setRecursoReport(RecursoReport recursoReport) {
        this.recursoReport = recursoReport;
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

    public void setEntranteDAO(EntranteDAO entranteDAO) {
        this.entranteDAO = entranteDAO;
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
    
    

    public String getTipoSeleccionado() {
		return tipoSeleccionado;
	}

	public void setTipoSeleccionado(String tipoSeleccionado) {
		this.tipoSeleccionado = tipoSeleccionado;
	}

	public boolean isVerColumna() {
        return verColumna;
    }

    public void setVerColumna(boolean verColumna) {
        this.verColumna = verColumna;
    }
    
    

    public boolean isVerColumnaObservacion() {
		return verColumnaObservacion;
	}

	public void setVerColumnaObservacion(boolean verColumnaObservacion) {
		this.verColumnaObservacion = verColumnaObservacion;
	}
	
	

	public boolean isVerColumnaAdjunto() {
		return verColumnaAdjunto;
	}

	public void setVerColumnaAdjunto(boolean verColumnaAdjunto) {
		this.verColumnaAdjunto = verColumnaAdjunto;
	}

	public boolean isVerResultados() {
        return verResultados;
    }

    public void setVerResultados(boolean verResultados) {
        this.verResultados = verResultados;
    }
    
    

    public boolean isVerColumnaFechaAten() {
		return verColumnaFechaAten;
	}

	public void setVerColumnaFechaAten(boolean verColumnaFechaAten) {
		this.verColumnaFechaAten = verColumnaFechaAten;
	}

	public boolean isVerColumnaDias() {
		return verColumnaDias;
	}

	public void setVerColumnaDias(boolean verColumnaDias) {
		this.verColumnaDias = verColumnaDias;
	}

	public List<DocumentoDerivado> getDerivados() {
        return derivados;
    }

    public void setDerivados(List<DocumentoDerivado> derivados) {
        this.derivados = derivados;
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

    /**
     * @return the entranteDAO
     */
    public EntranteDAO getEntranteDAO() {
        return entranteDAO;
    }

	public boolean isVerColumnaFechaReg() {
		return verColumnaFechaReg;
	}

	public void setVerColumnaFechaReg(boolean verColumnaFechaReg) {
		this.verColumnaFechaReg = verColumnaFechaReg;
	}
	
	

	public boolean isVerColumnaFechaDocumento() {
		return verColumnaFechaDocumento;
	}

	public void setVerColumnaFechaDocumento(boolean verColumnaFechaDocumento) {
		this.verColumnaFechaDocumento = verColumnaFechaDocumento;
	}

	public boolean isVerColumnaRegEntr() {
		return verColumnaRegEntr;
	}

	public void setVerColumnaRegEntr(boolean verColumnaRegEntr) {
		this.verColumnaRegEntr = verColumnaRegEntr;
	}

	public boolean isVerColumnaUsuario() {
		return verColumnaUsuario;
	}

	public void setVerColumnaUsuario(boolean verColumnaUsuario) {
		this.verColumnaUsuario = verColumnaUsuario;
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

	public boolean isDisderivados() {
		return disderivados;
	}

	public void setDisderivados(boolean disderivados) {
		this.disderivados = disderivados;
	}

	public boolean isDislista() {
		return dislista;
	}

	public void setDislista(boolean dislista) {
		this.dislista = dislista;
	}

	public List getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(List trabajador) {
		this.trabajador = trabajador;
	}

	public String getNombre_trabajador() {
		return nombre_trabajador;
	}

	public void setNombre_trabajador(String nombre_trabajador) {
		this.nombre_trabajador = nombre_trabajador;
	}
    
    
    
    
}