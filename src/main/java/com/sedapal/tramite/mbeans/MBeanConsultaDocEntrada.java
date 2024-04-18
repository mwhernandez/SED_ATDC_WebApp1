package com.sedapal.tramite.mbeans;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.itextpdf.text.pdf.TextField;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.EntranteDAO;
import com.sedapal.tramite.dao.SeguimientoEntranteDAO;
import com.sedapal.tramite.nova.util.RecursoReport;
import com.sedapal.tramite.servicios.IServiciosEntrante;
import com.sedapal.tramite.servicios.IServiciosSeguimientoEntrante;
import com.sedapal.tramite.servicios.estadisticas.EstadisticasService;
import com.sedapal.tramite.util.Utils;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;

public class MBeanConsultaDocEntrada implements IMBeanEntrantes, Serializable {

    @Autowired
    private EstadisticasService estadisticasService;
    //Atributos de la pagina
    private List listadoConsultaDocEntrantes;
    private List items1;
    private List items2;
    private List itemsannio;
    
    //mcarga//
    private MCarga mCarga;
    Calendar c = Calendar.getInstance();
    
    //variables///
    private boolean verDetalles = false;
    private boolean disBotonGrabar = false;
    private boolean disbotonregistro = false;
    private boolean validacionNlote = false;
    int ind_adjuntar = 0;
    
    private List<EntranteBean> consulta;
    private List<SeguimientoEntranteBean> seguimiento;
    
    
    private Date fechaIni = new Date();
    private Date fechaFin = new Date();    
    private byte[] imagenEstadistica;
    private Boolean verResultadoBusqueda;
    private String registro = "";
    private String anodocumento;
    private int ncodarea;
    private String vcodarea;
    private String vcodtipo;    
    public IServiciosEntrante serviciosEntrante;
    private EntranteDAO entranteDAO;
    private String error;    
    private boolean ver = false;
    private boolean verEstado = true;    
    
    private boolean verCatalogo = true;
    private boolean verSeguimiento = true;    
    private String itemSeleccionado;
    
    /************************/
    /*inicio variables para grabar*/
    private String vrescreN;
	private String vresactN;
	private String vasuntoN;
	private String vreferenciaN;
	private String vobservacionN;
	private String nombre_archivo;
	private String ubicacion;
	private String vubiarchivoN;
	private String vubiarchivoA;
	


	private String nloteN;
	private String validacion;
	/*fin variables para grabar*/
    
    
    private MBeanSegDocuEntrates mBeanSeguimiento;
    
    
 

    public MBeanConsultaDocEntrada(){
        System.out.println("Contructor MBeanEstadisticasDocEntrada");
    }
    
    public void eventDetalles(ActionEvent evt) {
        // this.limpiarDetalles();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
                
        
        session.setAttribute("indicador", 4);
        ind_adjuntar = 1;
        this.verDetalles = true;

        //selectedDocEntrada.clear();
        

    }
    
    /*
    
    /*@Override*/
    /*
    public void focusGained(FocusEvent e) {
    	System.out.println("voy ingresar al metodo");
    	System.out.println(e.getSource());
    	
        if(e.getSource().equals(null)){
        	
        	registro = "<--Escribe Numero Registro-->";
        }
        /*
        if(e.getSource() == campo2){
            mensaje2.setText("<--Escribe tu Apellido.");
        }
        
       
    }
     */
    
    

    
    public void eventLimpiar(ActionEvent event)throws IOException, ServletException {
		itemSeleccionado = "";
		//answer = "";
		this.verCatalogo = false;
		this.verSeguimiento = false;
		this.consulta = null;
		this.anodocumento="";
		this.registro= "";
		this.verEstado = false;
		//this.mensaje="";
		//this.focusGained(null);
		disbotonregistro=false;
		
		
    }

    
    public void eventActualizar(ActionEvent evt){
    	
    	String file = null;
    	HttpSession session = null;
    	String nombre_archivo;
    	String ubicacion;
    
	    if (ind_adjuntar == 0) {
	                    //this.vubiarchivoA = "NADA";
	                } else {
	                    file = (String) session.getAttribute("file");
	                    if (file != null) {
	                        nombre_archivo = file;
	                        List<ServidorBean> bean = this.entranteDAO.servidor();
	                        for (ServidorBean e : bean) {
	                            ubicacion = e.getDescripcion();
	                            // ubicacion = "http://1.1.194.53/entrada/"; modificado
	                            // en parametros
	                        }
	                        Date date = new Date();
	                        String annio = Integer.toString(c.get(Calendar.YEAR));
	                        nombre_archivo = annio + nombre_archivo;	                        
	                        // nuevo parametro para archivos PDF 21/11/2019
	                        // eli comenta 06/05/2021
	                        //this.vubiarchivoA = nombre_archivo;
	                    }
	
	                }
    }
    
    public void cerrarDetalles(ActionEvent event) {
    	//this.verPDF = false;
        this.verDetalles = false;
        //this.nindicadorN = 1;
        //this.nindicadorA = 1;
        //this.textoA = "El Registro tiene " + this.nindicadorN + " Archivo Adjunto";
    }
    
    public void busqueda(ActionEvent event){
    
    	this.consulta = entranteDAO.ConsultaSP(registro, anodocumento);
    }
    
    
    public void eventBusquedas(ActionEvent event) {
		Usuario usuario=null;
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance()
							.getExternalContext().getSession(false);
			usuario = (Usuario) session.getAttribute("sUsuario");
		System.out.println("Estoy en el Boton de Buscar");
		//
		//if (this.registro.equals("") && this.anodocumento.equals("0")) {
		if (this.registro.equals("") || this.anodocumento.equals("0")) {
			this.error = "Debe de Ingresar los datos completos";			
			this.verSeguimiento = false;
			this.verEstado = false;			
			System.out.println("uno");
			
		} else {
			this.consulta = entranteDAO.ConsultaSP(registro, anodocumento);
			for (EntranteBean p : consulta) {
				this.nloteN = p.getVlote();
				this.vubiarchivoA = p.getVubiarchivo();
			}
			this.seguimiento = entranteDAO.ConsultaSeguimiento(registro, anodocumento);			
			this.verCatalogo = true;
			this.verSeguimiento = true;
			this.verEstado = true;
			//this.listadoConsultaDocEntrantes = this.consulta;			
			System.out.println("dos");
			
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
    
    public void eventRegistrarEntrante(ActionEvent event) {
		HttpSession session = null;
		boolean formulario = true;
		this.disBotonGrabar = false;
		try {
			// /acediendo a sesion http
			session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
			System.out.println("Registando!!!");
			formulario = true;
				
				
			String valida = this.validarFormulario();
			System.out.println("valida!!!=" + valida);

					if (valida.equals("ok")) {
			
			
			
					Usuario usuario = null;
					String file = null;
					usuario = (Usuario) session.getAttribute("sUsuario");
					// this.ncodareaN=usuario.getCodarea();
					//this.vrescreN = usuario.getLogin();
					this.vresactN = usuario.getLogin();
					
					String ncodarea = String.valueOf(usuario.getCodarea());
	
					file = (String) session.getAttribute("file");
					
					if (this.vubiarchivoA.equals("NADA")){
						
						if (file != null) {
							nombre_archivo = file;
							List<ServidorBean> bean = this.entranteDAO.servidor();
							for (ServidorBean e : bean) {
								ubicacion = e.getDescripcion();
								// ubicacion = "http://1.1.194.53/mesa/";
							}
							Date date = new Date();
							String annio = Integer.toString(c.get(Calendar.YEAR));
							nombre_archivo = annio + nombre_archivo;
							this.vubiarchivoN = nombre_archivo;
						} else {
							this.vubiarchivoN = "NADA";
						}
						
					}
					
					else {
					
						this.vubiarchivoN = this.vubiarchivoA;
		
					}
	
					EntranteMesaBean entrantemesaBean = new EntranteMesaBean();						
					entrantemesaBean.setNano(Integer.valueOf(this.anodocumento));
					entrantemesaBean.setNcorrelativo(Integer.valueOf(this.registro));				
					entrantemesaBean.setVubiarchivo(this.vubiarchivoN);
					entrantemesaBean.setVlote(this.nloteN);
					entrantemesaBean.setVresact(this.vresactN);				
					entrantemesaBean.setVasunto(this.vasuntoN);
					entrantemesaBean.setVreferencia(this.vreferenciaN);
					entrantemesaBean.setVobservacion(this.vobservacionN);
					
					
					System.out.println("quiero los datos registro linea digitalizacion");
					System.out.println(this.anodocumento);
					System.out.println(this.registro);
					System.out.println(this.vubiarchivoN);
					System.out.println(this.vresactN);
					
					
					
					String out = entranteDAO.actualizarLineaDigitalizacion(this.anodocumento, this.registro, this.vubiarchivoN, this.nloteN, this.vresactN);
					out = out.trim();
					
	                System.out.println("OUT STORED!!!:" + out);
	                if (out.equals("0")) {
					
					this.error = "Se Grabó Satisfactoriamente";
					//this.selectedItems1.clear();				
					//this.accion = "";
					ind_adjuntar = 0;
					this.ver = true;				
					this.verCatalogo = false;
					this.verSeguimiento = false;
					this.consulta = null;
					this.anodocumento="";
					this.registro= "";
					this.verEstado = false;
					//this.verActualizar = false;
					// ***** editar ...
					//this.editarEmpresa = true;
	               
	                } 
	                
					} else {
	    				formulario = false;
	    				this.error = valida;
	    				this.ver = true;
	    			}
		

			} catch (Exception e) {
						//logger.error("Fallo el registro", e);
						this.error = "Transaccion No valida.";
						this.ver = true;
						//this.verCatalogo = true;
						//this.verActualizar = false;
			} finally {
						//nombre_archivo = "";
						//this.vubiarchivoN = "";
						if (formulario) {
							session.setAttribute("file", null);
						}
						//ubicacion = "";
						//this.selectedItems1.clear();
						//this.accion = "";
			}
		
			
		
			
	}
    
    
    public String validarFormulario() {
		String mensaje = "Falta ingresar: ";
		boolean ok = true;
		
		if (this.nloteN.equals("0")) {

			mensaje += "Número Lote, ";
			this.validacionNlote = false;
			ok = false;
		} else {

			for (int x = 0; x <= nloteN.trim().length(); x++) {
				System.out.println("Quiero ver el input");
				System.out.println(nloteN);
				if (Comunes.isNumeric(nloteN)) {
					this.validacion = "numero";
					this.validacionNlote = true;
					System.out.println(" Lote ES un numero");
					System.out.println(this.validacionNlote);

				} else {

					this.validacion = "letras";
					this.validacionNlote = false;
					System.out.println(" El lote NO es un numero");
					System.out.println(this.validacionNlote);

				}
			}

			if (this.validacionNlote == false) {
				mensaje += "Solo número en Lote (1-9), ";
				ok = false;
			}

		}
		
		if (ok) {
			mensaje = "ok";
		}
		return mensaje;
		
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


	public List<SeguimientoEntranteBean> getSeguimiento() {
		return seguimiento;
	}


	public void setSeguimiento(List<SeguimientoEntranteBean> seguimiento) {
		this.seguimiento = seguimiento;
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


	public List getItemsannio() {
		return itemsannio;
	}


	public void setItemsannio(List itemsannio) {
		this.itemsannio = itemsannio;
	}

	public boolean isVerDetalles() {
		return verDetalles;
	}

	public void setVerDetalles(boolean verDetalles) {
		this.verDetalles = verDetalles;
	}

	public void setmCarga(MCarga mCarga) {
		this.mCarga = mCarga;
	}

	public boolean isDisBotonGrabar() {
		return disBotonGrabar;
	}

	public void setDisBotonGrabar(boolean disBotonGrabar) {
		this.disBotonGrabar = disBotonGrabar;
	}

	public String getVrescreN() {
		return vrescreN;
	}

	public void setVrescreN(String vrescreN) {
		this.vrescreN = vrescreN;
	}

	public String getVresactN() {
		return vresactN;
	}

	public void setVresactN(String vresactN) {
		this.vresactN = vresactN;
	}

	public String getVasuntoN() {
		return vasuntoN;
	}

	public void setVasuntoN(String vasuntoN) {
		this.vasuntoN = vasuntoN;
	}

	public String getVreferenciaN() {
		return vreferenciaN;
	}

	public void setVreferenciaN(String vreferenciaN) {
		this.vreferenciaN = vreferenciaN;
	}

	public String getVobservacionN() {
		return vobservacionN;
	}

	public void setVobservacionN(String vobservacionN) {
		this.vobservacionN = vobservacionN;
	}

	public String getVubiarchivoN() {
		return vubiarchivoN;
	}

	public void setVubiarchivoN(String vubiarchivoN) {
		this.vubiarchivoN = vubiarchivoN;
	}

	public boolean isDisbotonregistro() {
		return disbotonregistro;
	}

	public void setDisbotonregistro(boolean disbotonregistro) {
		this.disbotonregistro = disbotonregistro;
	}

		

	public String getNloteN() {
		return nloteN;
	}

	public void setNloteN(String nloteN) {
		this.nloteN = nloteN;
	}

	public boolean isValidacionNlote() {
		return validacionNlote;
	}

	public void setValidacionNlote(boolean validacionNlote) {
		this.validacionNlote = validacionNlote;
	}

	public String getValidacion() {
		return validacion;
	}

	public void setValidacion(String validacion) {
		this.validacion = validacion;
	}

	
	public String getVubiarchivoA() {
		return vubiarchivoA;
	}

	public void setVubiarchivoA(String vubiarchivoA) {
		this.vubiarchivoA = vubiarchivoA;
	}

		
	
    
	
    
    
}