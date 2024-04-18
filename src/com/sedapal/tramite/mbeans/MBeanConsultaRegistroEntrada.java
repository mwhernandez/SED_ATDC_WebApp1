package com.sedapal.tramite.mbeans;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.ConsultaRegistroEntradaDAO;
import com.sedapal.tramite.dao.EntranteDAO;
import com.sedapal.tramite.dao.SeguimientoEntranteDAO;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.servicios.IServiciosEntrante;
import com.sedapal.tramite.servicios.IServiciosSeguimientoEntrante;
import com.sedapal.tramite.servicios.estadisticas.EstadisticasService;
import com.sedapal.tramite.util.Utils;

import java.awt.event.FocusEvent;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public class MBeanConsultaRegistroEntrada implements IMBeanEntrantes, Serializable {

    @Autowired
    private EstadisticasService estadisticasService;
    //Atributos de la pagina
    private List listadoConsultaDocEntrantes;
    private List items1;
    private List items2;
    private List itemsannio;
    
    private List<EntranteBean> consulta;
    private List<SeguimientoEntranteBean> seguimiento;
    
    
    private Date fechaIni = new Date();
    private Date fechaFin = new Date();    
    private byte[] imagenEstadistica;
    private Boolean verResultadoBusqueda;
    private String registro;
    private String anodocumento;
    private int ncodarea;
    private long correlativo;
    private String vcodarea;
    private String vcodtipo;    
    public IServiciosEntrante serviciosEntrante;
    //private EntranteDAO entranteDAO;
    private ConsultaRegistroEntradaDAO consultaregistroentradaDAO;
    
    private String error;    
    private boolean ver = false;
    private boolean verEstado = true;    
    
    private boolean verCatalogo = true;
    private boolean verSeguimiento = true;    
    private String itemSeleccionado;
    
    private MBeanSegDocuEntrates mBeanSeguimiento;
    
    
 

    public MBeanConsultaRegistroEntrada(){
        System.out.println("Contructor MBeanEstadisticasDocEntrada");
    }
    
    
    
        
    public void eventBusquedaRegistro(ActionEvent event) {
		Usuario usuario=null;
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance()
							.getExternalContext().getSession(false);
			usuario = (Usuario) session.getAttribute("sUsuario");
		System.out.println("Estoy en el Boton de Buscar Registro");
		System.out.println(this.anodocumento);
		String valida = null;
		valida = this.validarFormulario();		
		this.vcodarea = String.valueOf(usuario.getNcodarea());
		if (valida.equals("ok")) {
			EntranteBean consulta = new EntranteBean();			
			String  dato = this.Lpad(this.registro, "0")+"-"+this.anodocumento+"-"+this.vcodarea+"-"+this.vcodtipo;
			System.out.println("Ver datos");
			System.out.println(dato);
			this.consulta = consultaregistroentradaDAO.BusquedaRegistroSP(dato);	
			this.seguimiento = consultaregistroentradaDAO.BusquedaSeguimientoReg(dato);
						
			this.verCatalogo = true;
			this.verSeguimiento = true;
			this.verEstado = true;
			//this.listadoConsultaDocEntrantes = this.consulta;			
			System.out.println("dos");
			
		} else {            
            this.error = valida;
            this.ver = true;
        }
				
			
	}
    
    public String validarFormulario() {
    	String mensaje = "Falta ingresar: ";
        boolean ok = true;
        if (this.registro.equals("")) {
            ok = false;
            mensaje += "Registro ";
        }
        /*
        if (this.vcodarea.equals("100")) {
            ok = false;
            mensaje += ",Area ";
        }
        */
        
        if (this.anodocumento.equals("0")) {
            ok = false;
            mensaje += ",Año Documento ";
        }
        
        
        if (this.vcodtipo.equals("SELECCIONAR")) {
            ok = false;
            mensaje += ",Tipo Documento ";
        }
        
        if (ok) {
            mensaje = "ok";
        }
        return mensaje;
    	
    }
    
    public String Lpad(String contenido, String caracter) {
    	String valor=contenido.toString();
    	int i=0;
    	for(i=contenido.length()+1;i<=7;i++)
        {
    		valor=caracter+valor;
        }
    	
    	return valor;
        
    }

    
    public void cerrar(ActionEvent event) {
		this.ver = false;
	}
    
    @PostConstruct
    public void posterior() {
    	/*
    	List<AreaBean> areas = consultaregistroentradaDAO.areas();
        List itemsAreas = Utils.getAreas(areas);
        this.items1 = itemsAreas;
        */
        
        List<TiposDocumentosBean> tipodoc = consultaregistroentradaDAO.tipo_doc();
        List itemsTipos = Utils.getTipos_Documentos(tipodoc);
        this.items2 = itemsTipos;
        
        List<AnioBean> anio = consultaregistroentradaDAO.anio();
        List itemsAnio = Utils.getAnio(anio);
        this.itemsannio = itemsAnio;
        
    	
    }
    
    public void eventLimpiar(ActionEvent event) {
		this.itemSeleccionado = "";
		//this.items1.clear();
		List<AreaBean> areas = consultaregistroentradaDAO.areas();
        List itemsAreas = Utils.getAreas(areas);
        this.items1 = itemsAreas;
        /////
        this.items2.clear();
        List<TiposDocumentosBean> tipodoc = consultaregistroentradaDAO.tipo_doc();
        List itemsTipos = Utils.getTipos_Documentos(tipodoc);
        this.items2 = itemsTipos;
		//answer = "";
		this.verCatalogo = false;
		this.verSeguimiento = false;
		this.consulta = null;
		this.anodocumento="";
		this.registro= "";
		this.verEstado = false;
		//this.mensaje="";
    }
    /**************************************************/

	// list of selected employees
	protected ArrayList<EntranteBean> selectedDocEntrada;
	// flat to indicate multiselect row enabled.
	protected String multiRowSelect = "Multiple";
	protected boolean multiple = false;
	protected boolean enhancedMultiple;
   
    
    public void MBeanEntrantes() {
		
    	selectedDocEntrada = new ArrayList<EntranteBean>();
        

	}
    
    
    public String getMultiRowSelect() {
		return multiRowSelect;
	}


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


	public void rowSelectionListener(RowSelectorEvent event) {
    	selectedDocEntrada.clear();
    	EntranteBean ConDocEntr;
    	// Eli Comenta este codigo
    	
    	for (int i = 0, max = consulta.size(); i < max; i++) {
    		ConDocEntr = (EntranteBean) consulta.get(i);
			if (ConDocEntr.isSelected()) {
				selectedDocEntrada.add(ConDocEntr);
			}
		}
		for (EntranteBean t : selectedDocEntrada) {
			//System.out.println(t.getCodigo() + "  " + t.getDescripcion());
			//this.registro=t.getNcorrelativo();	
			//LlavesBean beans = new LlavesBean();
			//beans.setCodigo(this.codigo);
			//HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			//session.setAttribute("sLlaves", beans);

		}
    	
    	
    	
    }
	
	
    /**
     * @return the listadoEstadisticaDocEntrantes
     */
    

    /**
     * @param listadoEstadisticaDocEntrantes the listadoEstadisticaDocEntrantes to set
     */
    
    
    

	public List getListadoConsultaDocEntrantes() {
		return listadoConsultaDocEntrantes;
	}

	public void setSelectedDocEntrada(ArrayList<EntranteBean> selectedDocEntrada) {
		this.selectedDocEntrada = selectedDocEntrada;
	}


	public void setListadoConsultaDocEntrantes(List listadoConsultaDocEntrantes) {
		this.listadoConsultaDocEntrantes = listadoConsultaDocEntrantes;
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



	public EstadisticasService getEstadisticasService() {
		return estadisticasService;
	}



	public void setEstadisticasService(EstadisticasService estadisticasService) {
		this.estadisticasService = estadisticasService;
	}



	public String getRegistro() {
		return registro;
	}



	public void setRegistro(String registro) {
		this.registro = registro;
	}



	public String getAnodocumento() {
		return anodocumento;
	}



	public void setAnodocumento(String anodocumento) {
		this.anodocumento = anodocumento;
	}


	

	public void setServiciosEntrante(IServiciosEntrante serviciosEntrante) {
		this.serviciosEntrante = serviciosEntrante;
	}


	public boolean isVer() {
		return ver;
	}


	public void setVer(boolean ver) {
		this.ver = ver;
	}



	public boolean isVerSeguimiento() {
		return verSeguimiento;
	}


	public void setVerSeguimiento(boolean verSeguimiento) {
		this.verSeguimiento = verSeguimiento;
	}


	public boolean isVerCatalogo() {
		return verCatalogo;
	}


	public void setVerCatalogo(boolean verCatalogo) {
		this.verCatalogo = verCatalogo;
	}


	

	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public boolean isVerEstado() {
		return verEstado;
	}


	public void setVerEstado(boolean verEstado) {
		this.verEstado = verEstado;
	}


	

	public MBeanSegDocuEntrates getmBeanSeguimiento() {
		return mBeanSeguimiento;
	}


	public void setmBeanSeguimiento(MBeanSegDocuEntrates mBeanSeguimiento) {
		this.mBeanSeguimiento = mBeanSeguimiento;
	}


	public List getItems1() {
		return items1;
	}


	public void setItems1(List items1) {
		this.items1 = items1;
	}


	public int getNcodarea() {
		return ncodarea;
	}


	public void setNcodarea(int ncodarea) {
		this.ncodarea = ncodarea;
	}


	public String getVcodarea() {
		return vcodarea;
	}


	public void setVcodarea(String vcodarea) {
		this.vcodarea = vcodarea;
	}


	public List getItems2() {
		return items2;
	}


	public void setItems2(List items2) {
		this.items2 = items2;
	}


	public String getVcodtipo() {
		return vcodtipo;
	}


	public void setVcodtipo(String vcodtipo) {
		this.vcodtipo = vcodtipo;
	}



	public List<EntranteBean> getConsulta() {
		return consulta;
	}



	public void setConsulta(List<EntranteBean> consulta) {
		this.consulta = consulta;
	}

	


	public List<SeguimientoEntranteBean> getSeguimiento() {
		return seguimiento;
	}



	public void setSeguimiento(List<SeguimientoEntranteBean> seguimiento) {
		this.seguimiento = seguimiento;
	}



	public String getItemSeleccionado() {
		return itemSeleccionado;
	}



	public void setItemSeleccionado(String itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
	}



	public void setConsultaregistroentradaDAO(
			ConsultaRegistroEntradaDAO consultaregistroentradaDAO) {
		this.consultaregistroentradaDAO = consultaregistroentradaDAO;
	}




	public long getCorrelativo() {
		return correlativo;
	}




	public void setCorrelativo(long correlativo) {
		this.correlativo = correlativo;
	}




	public List getItemsannio() {
		return itemsannio;
	}




	public void setItemsannio(List itemsannio) {
		this.itemsannio = itemsannio;
	}



	
	
    
    
}