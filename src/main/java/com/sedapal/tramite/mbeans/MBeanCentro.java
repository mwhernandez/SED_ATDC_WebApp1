package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.sedapal.tramite.beans.CentroBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.dao.CentroDAO;
import com.sedapal.tramite.servicios.IServiciosCentro;


public class MBeanCentro implements IMBeanCentro, Serializable{
	
	private List<CentroBean> centro;
	public IServiciosCentro serviciosCentro;
	private List items;
	private String itemSeleccionado;
	private boolean ver = false;
	private String error = "";
	private boolean verNuevo = false;
	private boolean verCatalogo = true;
	private boolean verActualizar = false;
	private boolean verDetalles = false;
	// para el formulario nuevo
	private int codigo;
	private String nombre;
    private String direccion;
    private String abreviatura;   
    private String estado;
 // para el formulario nuevo
	private int codigoA;
	private String nombreA;
    private String direccionA;
    private String abreviaturaA;   
    private String estadoA;
	//binding al combo
	private HtmlSelectOneMenu combo;
	private String desc;	
	private Logger logger = Logger.getLogger("R1");

	private CentroDAO centroDAO;
	
	public void eventActualizar(ActionEvent evt) {
		this.limpiarActualizar();
		if (this.selectedCentross.size() == 1) {
			this.verActualizar = true;
			this.verCatalogo = false;

			for (CentroBean c : selectedCentross) {
				
				this.codigoA = c.getCodigo();
				this.nombreA = c.getNombre();
				this.abreviaturaA = c.getAbreviatura();
				this.direccionA=c.getDireccion();
				this.estadoA = c.getEstado();				
				
			}
			selectedCentross.clear();
			this.centro = serviciosCentro.catalogo();// actualiza el reporte
		} else {
			this.error = "Debe seleccionar solo un registro";
			this.ver = true;

		}
	}
	
	public void eventCancelar(ActionEvent event) {
		this.verCatalogo =true;
		this.verNuevo = false;
		this.verActualizar = false;
	}

	
	public void cerrarDetalles(ActionEvent event) {
		 this.verDetalles=false;
		
	}

	public List<CentroBean> getCentro() {
		return this.centro;
	}

	public void setCentro(List<CentroBean> centro) {
		this.centro = centro;
	}

	public void setServiciosCentro(IServiciosCentro serviciosCentro) {
		this.serviciosCentro = serviciosCentro;

	}

	public void cerrar(ActionEvent event) {
		this.ver = false;
	}

	

	/**************************************************/

	// list of selected employees
	protected ArrayList<CentroBean> selectedCentross;

	// flat to indicate multiselect row enabled.
	protected String multiRowSelect = "Multiple";
	protected boolean multiple = true;
	protected boolean enhancedMultiple;

	public MBeanCentro() {
		selectedCentross = new ArrayList<CentroBean>();

	}

	public void eventoNuevo(ActionEvent evnt) {
		for (CentroBean t : selectedCentross) {
			System.out.println(t.getCodigo() + "   " + t.getNombre());
		}
	}

	@PostConstruct
	public void posterior() {
		this.centro = serviciosCentro.catalogo();
		//System.out.println("Post Construct, cargo catalogo...size="+this.centro.size());
		//System.out.println("Contructor MBeanCentros....");
		//List<Tipos> tipos = productoDAO.tipos();
		//List itemsTipos = Utils.getTipos(tipos);
		//this.items = itemsTipos;
		//System.out.println("Se cargo los combos....");
		//for (Tipos p : tipos)
		//	System.out.println(p.getTipo() + "   " + p.getDescripcion());
	}

	/**
	 * SelectionListener bound to the ice:rowSelector component. Called when a
	 * row is selected in the UI.
	 * 
	 * @param event
	 *            from the ice:rowSelector component
	 */
	public void rowSelectionListener(RowSelectorEvent event) {
		// clear our list, so that we can build a new one
		selectedCentross.clear();

		/*
		 * If application developers rely on validation to control submission of
		 * the form or use the result of the selection in cascading control set
		 * up the may want to defer procession of the event to
		 * INVOKE_APPLICATION stage by using this code fragment if
		 * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
		 * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
		 * return; }
		 */

		System.out.println("Tama:" + centro.size());
		// build the new selected list
		CentroBean employee;
		for (int i = 0, max = centro.size(); i < max; i++) {
			employee = (CentroBean) centro.get(i);
			if (employee.isSelected()) {
				selectedCentross.add(employee);
			}
		}
		for (CentroBean t : selectedCentross) {
			System.out.println(t.getCodigo() + "  " + t.getNombre());
		}
		/*
		 * If application developers do not rely on validation and want to
		 * bypass UPDATE_MODEL and INVOKE_APPLICATION stages, they may be able
		 * to use the following statement:
		 * FacesContext.getCurrentInstance().renderResponse(); to send
		 * application to RENDER_RESPONSE phase shortening the app. cycle
		 */
	}

	/**
	 * Clear the selection list if going from multi select to single select.
	 * 
	 * @param event
	 *            jsf action event.
	 */
	public void changeSelectionMode(ValueChangeEvent event) {

		String newValue = event.getNewValue() != null ? event.getNewValue()
				.toString() : null;
		multiple = false;
		enhancedMultiple = false;
		if ("Single".equals(newValue)) {
			selectedCentross.clear();

			// build the new selected list
			CentroBean employee;
			for (int i = 0, max = centro.size(); i < max; i++) {
				employee = (CentroBean) centro.get(i);
				employee.setSelected(false);
			}
		} else if ("Multiple".equals(newValue)) {
			multiple = true;
		} else if ("Enhanced Multiple".equals(newValue)) {
			enhancedMultiple = true;
		}
	}
	public void limpiar()
	{
		this.codigo = 0;
		this.nombre="";
		this.direccion="";
		this.abreviatura="";
		this.estado="";
		
	}
	
	public void limpiarActualizar() {
		this.codigoA = 0;
		this.nombreA="";
		this.direccionA="";
		this.abreviaturaA="";
		this.estadoA="";
		
	}

	public ArrayList getselectedCentross() {
		return selectedCentross;
	}

	public void setselectedCentross(ArrayList selectedCentross) {
		this.selectedCentross = selectedCentross;
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

	public void setCentroDAO(CentroDAO centroDAO) {
		this.centroDAO = centroDAO;
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

	public String getNombreA() {
		return nombreA;
	}

	public void setNombreA(String nombreA) {
		this.nombreA = nombreA;
	}

	public String getDireccionA() {
		return direccionA;
	}

	public void setDireccionA(String direccionA) {
		this.direccionA = direccionA;
	}

	public String getAbreviaturaA() {
		return abreviaturaA;
	}

	public void setAbreviaturaA(String abreviaturaA) {
		this.abreviaturaA = abreviaturaA;
	}

	public String getEstadoA() {
		return estadoA;
	}

	public void setEstadoA(String estadoA) {
		this.estadoA = estadoA;
	}
	
	
	

}
