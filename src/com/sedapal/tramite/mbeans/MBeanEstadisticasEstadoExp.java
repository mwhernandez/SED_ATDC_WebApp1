package com.sedapal.tramite.mbeans;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.beans.estadisticas.EstadisticaDocEntrandaBean;
import com.sedapal.tramite.dao.ConsultaDAO;
import com.sedapal.tramite.dao.EstadisticasDAO;
import com.sedapal.tramite.servicios.estadisticas.EstadisticasService;
import com.sedapal.tramite.util.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

public class MBeanEstadisticasEstadoExp implements IMBeanEstadisticasEstadoExp, Serializable {

    @Autowired
    private EstadisticasService estadisticasService;
    //Atributos de la pagina
    private List listadoEstadisticaDocEntrantes;
    private List listadoResumenMateriaExpDoc;
    private List listadoEstadisticaEstExp;
    private Date fechaIni = new Date();
	private Date fechaFin = new Date();    
     
    private byte[] imagenEstadistica;
    private Boolean verResultadoBusqueda;
    private boolean ver = false;
    private boolean verEstadistica1 = false;
    private boolean verEstadistica2 = false;
    private boolean verDetalles = false;
    
    private String error= " ";
    
  //para el tipo de reporte
    private List itemsareas;    
    private String varea;
    private int ncodarea;
    
    private ConsultaDAO consultaDAO;
    private EstadisticasDAO estadisticasDAO;
    

    public MBeanEstadisticasEstadoExp(){
        System.out.println("Contructor MBeanEstadisticasDocEntrada");
    }
    
   
   
    @PostConstruct
    public void posterior() {
    	
    	List<AreaBean> areas = consultaDAO.areas(); 
   	 	List itemsareas = Utils.getAreas(areas);
        this.itemsareas = itemsareas; 
        
         
       
    }
   
    
    public void cerrarDetalles(ActionEvent event) {
		this.verDetalles = false;

	}
    

    /**************************************************/
    /********** Metodos de Uso de la Pagina  **********/
    /**************************************************/
    public void processEjecutarEstadistica(ActionEvent actionEvent){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario beanUsuario = (Usuario) session.getAttribute("sUsuario");

        System.out.println("Quiero ver el combo");
        System.out.println(varea);
        
        if (varea.equals("0")){        	
        	this.error = "Seleccionar una Gerencia";
    		this.ver= true;
		       
        } else {
        
        	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    		// int codArea = beanUsuario.getNcodarea();
    		String strFechaIni = sdf.format(this.getFechaIni());
    		String strFechaFin = sdf.format(this.getFechaFin());
        	
        	 System.out.println("paso 1");
        	 System.out.println(varea);
        	 String tiporeporte = "EST004";
        	 ncodarea = Integer.parseInt(this.varea);
        	 this.setImagenEstadistica(estadisticasService.getImgBarraEstadisticaEstado(strFechaIni, strFechaFin, ncodarea));
        	 this.setListadoEstadisticaEstExp(estadisticasService.getListadoEstadisticaEstadoExp(strFechaIni, strFechaFin, ncodarea));
        	 
        	 this.verEstadistica1 = true;
        	 this.verEstadistica2 = true;
        		
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
    
    
    
	public List getListadoEstadisticaEstExp() {
		return listadoEstadisticaEstExp;
	}



	public void setListadoEstadisticaEstExp(List listadoEstadisticaEstExp) {
		this.listadoEstadisticaEstExp = listadoEstadisticaEstExp;
	}



	public List getListadoResumenMateriaExpDoc() {
		return listadoResumenMateriaExpDoc;
	}

	public void setListadoResumenMateriaExpDoc(List listadoResumenMateriaExpDoc) {
		this.listadoResumenMateriaExpDoc = listadoResumenMateriaExpDoc;
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

		

	public List getItemsareas() {
		return itemsareas;
	}



	public void setItemsareas(List itemsareas) {
		this.itemsareas = itemsareas;
	}

	

	public void setConsultaDAO(ConsultaDAO consultaDAO) {
		this.consultaDAO = consultaDAO;
	}

	public boolean isVerDetalles() {
		return verDetalles;
	}

	public void setVerDetalles(boolean verDetalles) {
		this.verDetalles = verDetalles;
	}

	public boolean isVerEstadistica1() {
		return verEstadistica1;
	}

	public void setVerEstadistica1(boolean verEstadistica1) {
		this.verEstadistica1 = verEstadistica1;
	}

	public boolean isVerEstadistica2() {
		return verEstadistica2;
	}

	public void setVerEstadistica2(boolean verEstadistica2) {
		this.verEstadistica2 = verEstadistica2;
	}



	public Date getFechaIni() {
		return fechaIni;
	}



	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}



	public Date getFechaFin() {
		return fechaFin;
	}



	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}



	public String getVarea() {
		return varea;
	}



	public void setVarea(String varea) {
		this.varea = varea;
	}



	public int getNcodarea() {
		return ncodarea;
	}



	public void setNcodarea(int ncodarea) {
		this.ncodarea = ncodarea;
	}
	
	
    
    
}