package com.sedapal.tramite.mbeans;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.LlavesBean;
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

public class MBeanEstadisticasGerenciales implements IMBeanEstadisticas,
		Serializable {

	@Autowired
	private EstadisticasService estadisticasService;
	// Atributos de la pagina
	private List listadoEstadisticaDocEntrantes;
	private List listadoResumenMateriaExpDoc;
	private List listadoResumenProcesoExpDoc;

	private Date fechaIni = new Date();
	private Date fechaFin = new Date();
	private byte[] imagenEstadistica;
	private Boolean verResultadoBusqueda;
	private boolean ver = false;
	private boolean verEstadistica1 = false;
	private boolean verEstadistica2 = false;
	private boolean verEstadistica3 = false;
	private boolean verDetalles = false;
	private boolean verRegistros = false;

	private String error = " ";
	private int ncodarea;
	private int ncodgerencia;

	// para el tipo de reporte
	private List itemstiporeporte;
	private List itemsgerencia;
	private String tiporeporte;
	private String codigogerencia;
	//
	private String llave_tiporeporte;

	private ConsultaDAO consultaDAO;
	//private EstadisticasDAO estadisticasDAO;

	public MBeanEstadisticasGerenciales() {
		System.out.println("Contructor MBeanEstadisticasDocEntrada");
	}

	public void buscar_reporte(ValueChangeEvent changeEven) {

		this.tiporeporte = (String) changeEven.getNewValue();
		llave_tiporeporte = this.tiporeporte;
		LlavesBean beans = new LlavesBean();        
        beans.setTipo_reporte(this.tiporeporte);
        
        HttpSession session = (HttpSession) FacesContext
        .getCurrentInstance().getExternalContext()
        .getSession(false);
        session.setAttribute("sLlaves", beans);
		
		

	}

	public String getLlave_tiporeporte() {
		return llave_tiporeporte;
	}

	public void setLlave_tiporeporte(String llaveTiporeporte) {
		llave_tiporeporte = llaveTiporeporte;
	}

	public void buscar_gerencia(ValueChangeEvent changeEven) {

		this.codigogerencia = (String) changeEven.getNewValue();
		this.ncodgerencia = Integer.parseInt(this.codigogerencia);
		

	}

	@PostConstruct
	public void posterior() {
		
		List<TiposBean> tipoConsultas = consultaDAO.consultaSP();
		List itemstiporeporte = Utils.getTipo(tipoConsultas);
		this.itemstiporeporte = itemstiporeporte;
		

		List<AreaBean> codigogerencia = consultaDAO.areas();
		List itemsgerencia = Utils.getAreas(codigogerencia);
		this.itemsgerencia = itemsgerencia;

	}

	public void cerrarDetalles(ActionEvent event) {
		this.verDetalles = false;

	}

	/**************************************************/
	/********** Metodos de Uso de la Pagina **********/
	/**************************************************/
	public void processEjecutarEstadistica(ActionEvent actionEvent) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Usuario beanUsuario = (Usuario) session.getAttribute("sUsuario");
		ncodarea = beanUsuario.getCodarea();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		// int codArea = beanUsuario.getNcodarea();
		String strFechaIni = sdf.format(this.getFechaIni());
		String strFechaFin = sdf.format(this.getFechaFin());
		System.out.println("FECHA DEL ESTADISTICOS");
		System.out.println(ncodgerencia);
		System.out.println(strFechaIni);
		System.out.println(strFechaFin);
		
		

		if (tiporeporte.equals("EST001")) {
			
			System.out.println("Estoy en reporte documentos por situacion 1");
			 this.setImagenEstadistica(estadisticasService.getImgEstadisticaDocsEntrantesByArea(ncodgerencia, strFechaIni, strFechaFin));
		     this.setListadoEstadisticaDocEntrantes(estadisticasService.getListadoEstadisticaDocsEntrantesByArea(ncodgerencia, strFechaIni, strFechaFin));
			 //this.setListadoEstadisticaDocEntrantes(estadisticasService.getListadoEstadisticaporsituacionByArea(ncodgerencia, strFechaIni, strFechaFin)); 

			if (this.getListadoEstadisticaDocEntrantes().size() == 0) {
				// this.setError("No se encontraron registros");
				this.error = "No se encontraron registros de acuerdo al rango de fechas";
				this.setVerResultadoBusqueda((Boolean) false);
				this.ver = true;
			} else {
				// this.setVerResultadoBusqueda((Boolean) true);
				this.verEstadistica1 = true;
				this.verEstadistica2 = false;
				this.verEstadistica3 = false;
			}
		} else if (tiporeporte.equals("EST002")) {
			System.out.println("Estoy en reporte de documentos entrada por gerencias");
			System.out.println("Quiero ver los datos del reporte material");
			System.out.println(strFechaIni);
			System.out.println(strFechaFin);
			System.out.println(tiporeporte);
			System.out.println(ncodgerencia);
			this.setImagenEstadistica(estadisticasService.getImgBarraEstadisticaDocsEntrantesByArea(strFechaIni,strFechaFin, tiporeporte,ncodgerencia));
			this.setListadoResumenMateriaExpDoc(estadisticasService.getListadoEstadisticaMateriasExpedientes(strFechaIni,strFechaFin, tiporeporte, ncodgerencia));
			int valor = getListadoResumenMateriaExpDoc().size();
			System.out.println("Quiero ver el valor");
			System.out.println(valor);

			this.verEstadistica1 = false;
			this.verEstadistica2 = true;
			this.verEstadistica3 = false;

		} else if (tiporeporte.equals("EST003")) {
			System.out.println("Estoy en reporte de documentos de salida por gerencias");
			System.out.println("Quiero el tipo reporte 3");
			System.out.println(tiporeporte);
			this.setImagenEstadistica(estadisticasService.getImgBarraEstadisticaDocsEntrantesByArea(strFechaIni,strFechaFin, tiporeporte, ncodgerencia));
			this.setListadoResumenMateriaExpDoc(estadisticasService.getListadoEstadisticaMateriasExpedientes(strFechaIni, strFechaFin, tiporeporte, ncodgerencia));

			this.verEstadistica1 = false;
			this.verEstadistica2 = true;
			this.verEstadistica3 = false;

		} else if (tiporeporte.equals("EST004")) {
			System.out.println("Estoy en reporte no implementado");
			System.out.println("Quiero el tipo reporte 4");
			System.out.println(tiporeporte);
			this.setImagenEstadistica(estadisticasService.getImgBarraEstadisticaDocsEntrantesByArea(strFechaIni, strFechaFin, tiporeporte, ncodgerencia));
			this.setListadoResumenMateriaExpDoc(estadisticasService.getListadoEstadisticaMateriasExpedientes(strFechaIni, strFechaFin, tiporeporte, ncodgerencia));			
			this.verEstadistica1 = false;
			this.verEstadistica2 = false;
			this.verEstadistica3 = true;

		} else if (tiporeporte.equals("EST005")) {
			System.out.println("Estoy en reporte por abogado");
			System.out.println("Quiero el tipo reporte 5");
			System.out.println(tiporeporte);
			//this.setImagenEstadistica(estadisticasService.getImgBarraEstadisticaDocsEntrantesByArea(strFechaIni, strFechaFin, tiporeporte, ncodarea));
			//this.setListadoResumenMateriaExpDoc(estadisticasService.getListadoEstadisticaMateriasExpedientes(strFechaIni, strFechaFin, tiporeporte, ncodarea));
			this.verEstadistica1 = false;
			this.verEstadistica2 = false;
			this.verEstadistica3 = true;

		} else {

			this.error = "Reporte No Implementado";
			this.ver = true;
			this.verEstadistica1 = false;
			this.verEstadistica2 = false;
			this.verEstadistica3 = false;
		}

	}

	public void cerrar(ActionEvent event) {
		this.ver = false;
	}

	/****************************************/
	/********** Metodos de Acceso **********/
	/****************************************/

	/**
	 * @return the listadoEstadisticaDocEntrantes
	 */
	public List getListadoEstadisticaDocEntrantes() {
		return listadoEstadisticaDocEntrantes;
	}

	/**
	 * @param listadoEstadisticaDocEntrantes
	 *            the listadoEstadisticaDocEntrantes to set
	 */
	public void setListadoEstadisticaDocEntrantes(List listadoEstadisticaDocEntrantes) {
		this.listadoEstadisticaDocEntrantes = listadoEstadisticaDocEntrantes;
	}

	public List getListadoResumenMateriaExpDoc() {
		return listadoResumenMateriaExpDoc;
	}

	public void setListadoResumenMateriaExpDoc(List listadoResumenMateriaExpDoc) {
		this.listadoResumenMateriaExpDoc = listadoResumenMateriaExpDoc;
	}

	public List getListadoResumenProcesoExpDoc() {
		return listadoResumenProcesoExpDoc;
	}

	public void setListadoResumenProcesoExpDoc(List listadoResumenProcesoExpDoc) {
		this.listadoResumenProcesoExpDoc = listadoResumenProcesoExpDoc;
	}

	/**
	 * @return the fechaIni
	 */
	public Date getFechaIni() {
		return fechaIni;
	}

	/**
	 * @param fechaIni
	 *            the fechaIni to set
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
	 * @param fechaFin
	 *            the fechaFin to set
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
	 * @param imagenEstadistica
	 *            the imagenEstadistica to set
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
	 * @param verResultadoBusqueda
	 *            the verResultadoBusqueda to set
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

	public List getItemstiporeporte() {
		return itemstiporeporte;
	}

	public void setItemstiporeporte(List itemstiporeporte) {
		this.itemstiporeporte = itemstiporeporte;
	}

	public String getTiporeporte() {
		return tiporeporte;
	}

	public void setTiporeporte(String tiporeporte) {
		this.tiporeporte = tiporeporte;
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

	public boolean isVerEstadistica3() {
		return verEstadistica3;
	}

	public void setVerEstadistica3(boolean verEstadistica3) {
		this.verEstadistica3 = verEstadistica3;
	}

	public int getNcodarea() {
		return ncodarea;
	}

	public void setNcodarea(int ncodarea) {
		this.ncodarea = ncodarea;
	}

	public void setEstadisticasService(EstadisticasService estadisticasService) {
		this.estadisticasService = estadisticasService;
	}

	

	public List getItemsgerencia() {
		return itemsgerencia;
	}

	public void setItemsgerencia(List itemsgerencia) {
		this.itemsgerencia = itemsgerencia;
	}

	public String getCodigogerencia() {
		return codigogerencia;
	}

	public void setCodigogerencia(String codigogerencia) {
		this.codigogerencia = codigogerencia;
	}

	public int getNcodgerencia() {
		return ncodgerencia;
	}

	public void setNcodgerencia(int ncodgerencia) {
		this.ncodgerencia = ncodgerencia;
	}

	public boolean isVerRegistros() {
		return verRegistros;
	}

	public void setVerRegistros(boolean verRegistros) {
		this.verRegistros = verRegistros;
	}

	
	
	
	
	

}