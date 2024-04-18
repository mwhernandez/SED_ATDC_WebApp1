package com.sedapal.tramite.mbeans;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.icesoft.icefaces.tutorial.facelets.NavigationBean;
import com.icesoft.icefaces.tutorial.facelets.PageContentBean;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.Usuario;
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

public class MBeanBusquedaSeguimientoDocEntrada implements IMBeanEntrantes, Serializable {

    @Autowired
    private EstadisticasService estadisticasService;
    //Atributos de la pagina
    private List listadoConsultaDocEntrantes;
    private List items1;
    private List items2;
    private List itemsannio;
    
    private NavigationBean navigation;    
    private List<EntranteBean> consulta;
    
    
    private Date fechaIni = new Date();
    private Date fechaFin = new Date();    
    private byte[] imagenEstadistica;
    private Boolean verResultadoBusqueda;
    private String numdocumento;
    private String anodocumento;
    private int ncodarea;
    private int nperfil;
    private String vcodarea;
    private String vcodtipo;
    private String vareas;
    
    
    private String error;    
    private boolean ver = false;
    private boolean verEstado = true;    
    
    private boolean verCatalogo = true;
    private boolean verSeguimiento = true;    
    private String itemSeleccionado;
    
 // /Llaves
    private int anollave;
    private int origenllave;
    private String tipodocllave;
    private long correlativollave;
    
    //inyectamos Documentos de Entrada 	
	private MBeanEntrantes mBeanEntrantes;
	public IServiciosEntrante serviciosEntrante;
	private EntranteDAO entranteDAO;
    
   
    
    
 

    public MBeanBusquedaSeguimientoDocEntrada(){
        System.out.println("Contructor MBeanEstadisticasDocEntrada");
    }
    
    
    public void eventBusquedas(ActionEvent event) {
		Usuario usuario=null;
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance()
							.getExternalContext().getSession(false);
			usuario = (Usuario) session.getAttribute("sUsuario");
			nperfil = usuario.getNcodperfil();
		System.out.println("Estoy en el Boton de Buscar");
		//
		//if (this.registro.equals("") && this.anodocumento.equals("0")) {
		if (this.numdocumento.equals("") || this.anodocumento.equals("0")) {
			this.error = "Debe de Ingresar los datos completos";
			this.ver = true;
			this.verSeguimiento = false;
			this.verEstado = false;			
			System.out.println("uno");
			
		} else {
			
			
				this.consulta = entranteDAO.BusquedaSeguimientoSP(anodocumento, vareas, vcodtipo, numdocumento);
						
			this.verCatalogo = true;
			this.verSeguimiento = true;
			this.verEstado = true;			
			
			
		}
		
			
	}
    
    public void eventVerDocumento(ActionEvent event) {		
		this.ver = false;
		EntranteBean p = null;
		for (EntranteBean q : this.consulta) {
            if (q.isSelected()) {
                p = q;
            }
        }

        if (p != null) {
        	
        	System.out.println("Quiero lo que paso");
        	System.out.println(String.valueOf(p.getNcodarea()));
        	System.out.println(String.valueOf(p.getNano()));
    		System.out.println(String.valueOf(p.getNcorrelativo()));
    		System.out.println("FIN Quiero lo que paso");
    		mBeanEntrantes.eventBusquedaSeguimientoDocumento(String.valueOf(p.getNcodarea()), String.valueOf(p.getNano()), String.valueOf(p.getNcorrelativo()));
            
            PageContentBean pageContentBean = new PageContentBean();
            pageContentBean.setMenuContentInclusionFile("./content/DE/doc_entrante.jspx");
            navigation.setSelectedPanel(pageContentBean);
        	
        } else {
        	this.error = "Debe seleccionar solo un registro para modificar";
            this.ver = true;
        }
        
          
	}
    
    
    public void cerrar(ActionEvent event) {
    	this.ver = false;
             
    	
		
	}
    
    @PostConstruct
    public void posterior() {
    	
    	List<AreaBean> areas = entranteDAO.areas();
        List itemsAreas = Utils.getAreas(areas);
        this.items1 = itemsAreas;
        
        List<TiposDocumentosBean> tipodoc = entranteDAO.tipo_doc();
        List itemsTipos = Utils.getTipos_Documentos(tipodoc);
        this.items2 = itemsTipos;
        
        List<AnioBean> anio = entranteDAO.anio();
        List itemsAnio = Utils.getAnio(anio);
        this.itemsannio = itemsAnio;
        
    }
    
    public void rowSelectionListener(RowSelectorEvent event) {
    	selectedDocEntrada.clear();
    	
    	
    	EntranteBean employee;
        for (int i = 0, max = consulta.size(); i < max; i++) {
            employee = (EntranteBean) consulta.get(i);
            if (employee.isSelected()) {            	
            	selectedDocEntrada.add(employee);
            }
        }
        
		for (EntranteBean t : selectedDocEntrada) {
			System.out.println("Estoy en la seleccion del Registro");
			System.out.println(t.getNano() + "  " + t.getNorigen() + " " +t.getVtipodoc() + " " +t.getNcorrelativo());
			
			this.anollave = t.getNcodarea();			
            this.origenllave = t.getNorigen();
            this.tipodocllave = t.getVtipodoc();
            this.correlativollave = t.getNcorrelativo();
            			

		}
    	
    }
    
       
    
    public void eventLimpiar(ActionEvent event)throws IOException, ServletException {
		itemSeleccionado = "";
		//answer = "";
		this.verCatalogo = false;
		this.verSeguimiento = false;
		this.consulta = null;
		this.anodocumento="";
		this.numdocumento= "";
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



	



	public String getNumdocumento() {
		return numdocumento;
	}


	public void setNumdocumento(String numdocumento) {
		this.numdocumento = numdocumento;
	}


	public String getAnodocumento() {
		return anodocumento;
	}



	public void setAnodocumento(String anodocumento) {
		this.anodocumento = anodocumento;
	}


	public void setEntranteDAO(EntranteDAO entranteDAO) {
		this.entranteDAO = entranteDAO;
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


	


	public void setConsulta(List<EntranteBean> consulta) {
		this.consulta = consulta;
	}


	public List<EntranteBean> getConsultas() {
		return consulta;
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


	public List<EntranteBean> getConsulta() {
		return consulta;
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


	public String getVareas() {
		return vareas;
	}


	public void setVareas(String vareas) {
		this.vareas = vareas;
	}


	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}


	public int getAnollave() {
		return anollave;
	}


	public void setAnollave(int anollave) {
		this.anollave = anollave;
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


	public String getItemSeleccionado() {
		return itemSeleccionado;
	}


	public void setItemSeleccionado(String itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
	}


	public MBeanEntrantes getmBeanEntrantes() {
		return mBeanEntrantes;
	}


	public void setmBeanEntrantes(MBeanEntrantes mBeanEntrantes) {
		this.mBeanEntrantes = mBeanEntrantes;
	}


	public List getItemsannio() {
		return itemsannio;
	}


	public void setItemsannio(List itemsannio) {
		this.itemsannio = itemsannio;
	}


	
	
	
    
	
    
    
}