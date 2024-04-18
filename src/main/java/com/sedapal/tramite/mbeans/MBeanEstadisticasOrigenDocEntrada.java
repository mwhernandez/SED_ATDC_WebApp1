package com.sedapal.tramite.mbeans;

import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.servicios.estadisticas.EstadisticasService;

import java.awt.event.FocusEvent;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public class MBeanEstadisticasOrigenDocEntrada implements IMBeanEntrantes, Serializable {

    //@Autowired
    //private EstadisticasService estadisticasService;
    //Atributos de la pagina
    private Date fechaIni = new Date();
    private Date fechaFin = new Date();
    private Boolean verBtnImpr = false;
    private Boolean verMensajeReporte = false;
    private String txtMensajeReporte;

    private RecursoReport reporteEstadisticos;

    private RecursoReport reporte; // inyectado
    //private RecursoReport recursoReport;// para displayar
    private Map<String, Object> parametros = new HashMap<String, Object>();
    private ExternalContext ec = null;

    private int codArea;
    private String fecIni;
    private String fecFin;
    private String nomArea;

    public MBeanEstadisticasOrigenDocEntrada() {
        System.out.println("Contructor MBeanEstadisticasOrigenDocEntrada");
    }

    /**************************************************/
    /********** Metodos de Uso de la Pagina  **********/
    /**************************************************/
    public void processObtenerRptEstadistica(ActionEvent actionEvent) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario beanUsuario = (Usuario) session.getAttribute("sUsuario");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        this.setCodArea(beanUsuario.getNcodarea());
        this.setNomArea(beanUsuario.getNomequipo());
        this.setFecIni(sdf.format(this.getFechaIni()));
        this.setFecFin(sdf.format(this.getFechaFin()));

        if(this.getFecIni().trim().equals("") || this.getFecFin().trim().equals("")){
            this.setVerMensajeReporte(true);
            this.setVerBtnImpr(false);
            this.setTxtMensajeReporte("Por favor, ingrese una fecha inicial y final validas");
        }else{
            this.setVerMensajeReporte(true);
            this.setVerBtnImpr(true);
            this.setTxtMensajeReporte("Su reporte esta listo. Por favor haga click en el boton Imprimir para visualizarlo.");
        }
    }

    /****************************************/
    /********** Metodos de Acceso  **********/
    /****************************************/
    /**
     * @return the fechaIni
     */
    public Date getFechaIni() {
        return fechaIni;
    }

    /**
     * @param fechaIni the fechaIni to set
     */
    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the reporteEstadisticos
     */
    public RecursoReport getReporteEstadisticos() {
        ec = FacesContext.getCurrentInstance().getExternalContext();

        parametros.put("P_AREA", String.valueOf(this.getCodArea()));
        parametros.put("P_FECHAINICIO", this.getFecIni());
        parametros.put("P_FECHAFIN", this.getFecFin());
        parametros.put("P_AREA_DESC", getNomArea());

        reporte.asignar("estadisticasDocEntr.pdf", ec, parametros,"reportes/rptEstadisticosDocsEntrada.jasper");

        reporteEstadisticos = reporte;
        return reporteEstadisticos;
    }

    /**
     * @param reporteEstadisticos the reporteEstadisticos to set
     */
    public void setReporteEstadisticos(RecursoReport reporteEstadisticos) {
        this.reporteEstadisticos = reporteEstadisticos;
    }

    /**
     * @return the verBtnImpr
     */
    public Boolean getVerBtnImpr() {
        return verBtnImpr;
    }

    /**
     * @param verBtnImpr the verBtnImpr to set
     */
    public void setVerBtnImpr(Boolean verBtnImpr) {
        this.verBtnImpr = verBtnImpr;
    }

    /**
     * @return the verMensajeReporte
     */
    public Boolean getVerMensajeReporte() {
        return verMensajeReporte;
    }

    /**
     * @param verMensajeReporte the verMensajeReporte to set
     */
    public void setVerMensajeReporte(Boolean verMensajeReporte) {
        this.verMensajeReporte = verMensajeReporte;
    }

    /**
     * @return the txtMensajeReporte
     */
    public String getTxtMensajeReporte() {
        return txtMensajeReporte;
    }

    /**
     * @param txtMensajeReporte the txtMensajeReporte to set
     */
    public void setTxtMensajeReporte(String txtMensajeReporte) {
        this.txtMensajeReporte = txtMensajeReporte;
    }

    /**
     * @return the codArea
     */
    public int getCodArea() {
        return codArea;
    }

    /**
     * @param codArea the codArea to set
     */
    public void setCodArea(int codArea) {
        this.codArea = codArea;
    }

    /**
     * @return the fecIni
     */
    public String getFecIni() {
        return fecIni;
    }

    /**
     * @param fecIni the fecIni to set
     */
    public void setFecIni(String fecIni) {
        this.fecIni = fecIni;
    }

    /**
     * @return the fecFin
     */
    public String getFecFin() {
        return fecFin;
    }

    /**
     * @param fecFin the fecFin to set
     */
    public void setFecFin(String fecFin) {
        this.fecFin = fecFin;
    }

    /**
     * @return the nomArea
     */
    public String getNomArea() {
        return nomArea;
    }

    /**
     * @param nomArea the nomArea to set
     */
    public void setNomArea(String nomArea) {
        this.nomArea = nomArea;
    }

    public RecursoReport getReporte() {
        return reporte;
    }

    public void setReporte(RecursoReport reporte) {
        this.reporte = reporte;
    }

	
}
