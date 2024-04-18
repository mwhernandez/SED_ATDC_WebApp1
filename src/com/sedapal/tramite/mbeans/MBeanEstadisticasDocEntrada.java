package com.sedapal.tramite.mbeans;

import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.beans.estadisticas.EstadisticaDocEntrandaBean;
import com.sedapal.tramite.servicios.estadisticas.EstadisticasService;

import java.awt.event.FocusEvent;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public class MBeanEstadisticasDocEntrada implements IMBeanEntrantes, Serializable {

    @Autowired
    private EstadisticasService estadisticasService;
    //Atributos de la pagina
    private List listadoEstadisticaDocEntrantes;
    private Date fechaIni = new Date();
    private Date fechaFin = new Date();    
    private byte[] imagenEstadistica;
    private Boolean verResultadoBusqueda;
    private boolean ver = false;
    private String error;

    public MBeanEstadisticasDocEntrada(){
        System.out.println("Contructor MBeanEstadisticasDocEntrada");
    }

    /**************************************************/
    /********** Metodos de Uso de la Pagina  **********/
    /**************************************************/
    public void processEjecutarEstadistica(ActionEvent actionEvent){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario beanUsuario = (Usuario) session.getAttribute("sUsuario");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        int codArea = beanUsuario.getNcodarea();
        
        String strFechaIni = sdf.format(this.getFechaIni());
        String strFechaFin = sdf.format(this.getFechaFin());
        System.out.println("FECHA DEL ESTADISTICOS");
        System.out.println(codArea);
        System.out.println(strFechaIni);
        System.out.println(strFechaIni);
        
        this.setImagenEstadistica(estadisticasService.getImgEstadisticaDocsEntrantesByArea(codArea, strFechaIni, strFechaFin));
        this.setListadoEstadisticaDocEntrantes(estadisticasService.getListadoEstadisticaDocsEntrantesByArea(codArea, strFechaIni, strFechaFin));
        
        
        
        if(this.getListadoEstadisticaDocEntrantes().size()==0){
        	//this.setError("No se encontraron registros");
        	this.error = "No se encontraron registros de acuerdo al rango de fechas";
        	this.setVerResultadoBusqueda((Boolean) false);
        	this.ver = true;
        }else{
        	this.setVerResultadoBusqueda((Boolean) true);	
        }
        
    }
    
    public void cerrar(ActionEvent event) {
		this.ver = false;
	}

    /****************************************/
    /********** Metodos de Acceso  **********/
    /****************************************/

    /**
     * @return the listadoEstadisticaDocEntrantes
     */
    public List getListadoEstadisticaDocEntrantes() {
        return listadoEstadisticaDocEntrantes;
    }

    /**
     * @param listadoEstadisticaDocEntrantes the listadoEstadisticaDocEntrantes to set
     */
    public void setListadoEstadisticaDocEntrantes(List listadoEstadisticaDocEntrantes) {
        this.listadoEstadisticaDocEntrantes = listadoEstadisticaDocEntrantes;
    }

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
     * @return the imagenEstadistica
     */
    public byte[] getImagenEstadistica() {
        return imagenEstadistica;
    }

    /**
     * @param imagenEstadistica the imagenEstadistica to set
     */
    public void setImagenEstadistica(byte[] imagenEstadistica) {
        this.imagenEstadistica = imagenEstadistica;
    }

    /**
     * @return the verResultadoBusqueda
     */
    public Boolean getVerResultadoBusqueda() {
        return verResultadoBusqueda;
    }

    /**
     * @param verResultadoBusqueda the verResultadoBusqueda to set
     */
    public void setVerResultadoBusqueda(Boolean verResultadoBusqueda) {
        this.verResultadoBusqueda = verResultadoBusqueda;
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

	
    
    
}