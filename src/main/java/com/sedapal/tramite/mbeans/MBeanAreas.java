package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.icesoft.faces.component.ext.RowSelectorEvent;

import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.dao.AreaDAO;
import com.sedapal.tramite.servicios.IServiciosArea;


public class MBeanAreas implements IMBeanAreas, Serializable{
  
	private List<AreaBean> areas;
    public IServiciosArea serviciosArea;	
	private Integer codigo;
	private String centro;
    private String nombre;
    private String abreviatura;
    private String tipo;
    private String estado; 
 // para el formulario Actualizar
    private Integer codigoA;
	private String centroA;
    private String nombreA;
    private String abreviaturaA;
    private String tipoA;
    private String estadoA;
	
	private List items;
	private boolean verNuevo = false;
	private boolean verCatalogo = true;
	private boolean verActualizar = false;
	private boolean ver = false;
	private String error = "";
	
	private AreaDAO areaDAO;
	
	public void eventActualizar(ActionEvent evt) {
		this.limpiarActualizar();
		if (this.selectedAreass.size() == 1) {
			this.verActualizar = true;
			this.verCatalogo = false;

			for (AreaBean t : selectedAreass) {
				this.codigoA = t.getCodigo();
				this.centroA = t.getCentro();
				this.nombreA = t.getNombre();
				this.abreviaturaA = t.getAbreviatura();
				this.tipoA = t.getTipo();
				this.estadoA = t.getEstado();				
			}
			selectedAreass.clear();
			this.areas = serviciosArea.catalogo();// actualiza el
			// reporte
		} else {
			this.error = "Debe seleccionar solo un producto";
			this.ver = true;

		}
	}
    

	public List<AreaBean> getAreas() {
		return this.areas;
	}

	public void setAreas(List<AreaBean> areas) {
		this.areas = areas;
	}

	public void setServiciosArea(IServiciosArea serviciosArea) {
		this.serviciosArea = serviciosArea;
	}
   
    // list of selected profiles
    protected ArrayList<AreaBean> selectedAreass;

    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple=true;
    protected boolean enhancedMultiple;
	private String itemSeleccionado;
    
    public MBeanAreas() {
    	selectedAreass = new ArrayList<AreaBean>();    	
    	System.out.println("Contructor MBeanProfiles....");
	}
    
    @PostConstruct
    public void posterior()
    {
    	this.areas = serviciosArea.catalogo();
    	System.out.println("Post Construct");
    }

    /**
     * SelectionListener bound to the ice:rowSelector component.  Called
     * when a row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
    	selectedAreass.clear();
        
        /* If application developers rely on validation to control submission of the form or use the result of
           the selection in cascading control set up the may want to defer procession of the event to
           INVOKE_APPLICATION stage by using this code fragment
            if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
               event.setPhaseId( PhaseId.INVOKE_APPLICATION );
               event.queue();
               return;
            }

         */
              
        System.out.println("Tama:" + areas.size());
        // build the new selected list
        AreaBean areaa;
        for(int i = 0, max =areas.size(); i < max; i++){
        	areaa = (AreaBean)areas.get(i);
            if (areaa.isSelected()) {
            	selectedAreass.add(areaa);
            }
        }
        for(AreaBean p:selectedAreass)
        {
        	System.out.println(p.getCodigo() +"  " + p.getNombre());
        }
        /* If application developers do not rely on validation and want to bypass UPDATE_MODEL and
           INVOKE_APPLICATION stages, they may be able to use the following statement:
           FacesContext.getCurrentInstance().renderResponse();
           to send application to RENDER_RESPONSE phase shortening the app. cycle
         */
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
        if ("Single".equals(newValue)){
        	selectedAreass.clear();

            // build the new selected list
            AreaBean areaa;
            for(int i = 0, max = areas.size(); i < max; i++){
            	areaa = (AreaBean)areas.get(i);
            	areaa.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)){
            multiple = true;
        } else if ("Enhanced Multiple".equals(newValue)){
            enhancedMultiple = true;
        }
    }
    
    public void eventCancelar(ActionEvent event) {
		this.verCatalogo =true;
		this.verNuevo = false;
		this.verActualizar = false;
	}
    
    public void limpiarActualizar() {    	
		this.codigoA = 0;
		this.centroA = "";
		this.nombreA = "";
		this.abreviaturaA = "";
		this.tipoA = "";		
		this.estadoA = "";
	}

    public ArrayList getSelectedAreass() {
        return selectedAreass;
    }

    public void setSelectedAreass(ArrayList selectedAreass) {
        this.selectedAreass = selectedAreass;
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

	

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public String getItemSeleccionado() {
		return itemSeleccionado;
	}

	public void setItemSeleccionado(String itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
	}

	public void setAreaDAO(AreaDAO areaDAO) {
		this.areaDAO = areaDAO;
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
	public Integer getCodigoA() {
		return codigoA;
	}
	public void setCodigoA(Integer codigoA) {
		this.codigoA = codigoA;
	}
	public String getCentroA(){
		return centroA;
	}
	public void setCentroA(String centroA) {
		this.centroA = centroA;
	}
	public String getNombreA(){
		return nombreA;
	}
	public void setNombreA(String nombreA) {
		this.nombreA = nombreA;
	}	
	public String getAbreviaturaA(){
		return abreviaturaA;
	}
	public void setAbreviaturaA(String abreviaturaA) {
		this.abreviaturaA = abreviaturaA;
	}
	public String getTipoA(){
		return tipoA;
	}
	public void setTipoA(String tipoA) {
		this.tipoA = tipoA;
	}
	
	public String getEstadoA(){
		return estadoA;
	}
	public void setEstadoA(String estadoA) {
		this.estadoA = estadoA;
	}
	
}