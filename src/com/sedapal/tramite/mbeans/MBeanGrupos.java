package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.j2solutionsit.fwk.patterns.jsf.bean.BaseSortableList;




import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.GruposDAO;
import com.sedapal.tramite.dao.TiposDAO;
import com.sedapal.tramite.servicios.IServiciosGrupos;
import com.sedapal.tramite.servicios.IServiciosTipos;
import com.sedapal.tramite.util.Utils;

public class MBeanGrupos extends BaseSortableList implements 
		IMBeanGrupos, Serializable {

    private List<GrupoBean> grupos;
    public IServiciosGrupos serviciosGrupos;
    private List items;
    private String itemSeleccionado;
    private boolean ver = false;
    private String Mensaje = "";
    private String error = "Se Grabó Satisfactoriamente";
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean verDetalles = false;
    // para el formulario nuevo
    private String codigoN;
    private String descripcionN;
    private Date fechaN;
    private String responsableN;
    private String estadoN;
    private String abreviaturaN;
    private String dirigidoN;
    // para el formulario Actualizar
    private int codigoA;
    private String descripcionA;
    private Date fechaA;
    private String responsableA;
    private String estadoA;
    private String abreviaturaA;
    //binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    //variables para el filtro
    private String opcion;
    private String detalle;
    private GruposDAO grupoDAO;
    /*para la lista de los grupos*/
    private String dirigidoA = "NADA";
    private String itemselectA[] = new String[1];
    private LinkedHashMap<String, String> items7a = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, String> items7b = new LinkedHashMap<String, String>();
	private String[] items7aSelected;
	private String[] items7bSelected;
	private String itemselect[] = new String[1];
	private String valores = "";
	String suma_areas;
	private String dirigidoAA;
	// area seleccionadas
	private int areas_actuales;

    public void eventEliminar(ActionEvent event) {

        if (this.selectedEmployees.size() < 1) {
            this.error = "Debe seleccionar por lo menos un Tipo";
            this.ver = true;
        } else {
            // llamamos a servicios o dao pasandole los datos a eliminar
            int codigo;
            for (GrupoBean t : selectedEmployees) {
                codigo = t.getCodigo();
                // le paso como parametro al stored
                serviciosGrupos.eliminarGrupos(codigo);
            }
            this.Mensaje = "El Tipo de Documento Eliminados!";
            selectedEmployees.clear();
            this.grupos = serviciosGrupos.catalogo();
            // reporte
            this.ver = true;
        }

    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        if (this.selectedEmployees.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;
            HttpSession session = null;
            // /acediendo a sesion http
            session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            // ///guardando en sesion un objeto
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            this.responsableA = usuario.getLogin();

            for (GrupoBean t : selectedEmployees) {
                this.codigoA = t.getCodigo();
                this.descripcionA = t.getDescripcion();                
                this.estadoA = t.getEstado();
                this.abreviaturaA = t.getAbreviatura();
                
                List<AreaBean> areas = grupoDAO.areas_seleccionadas(this.codigoA);
                
                this.items7b = Utils.getAreasLink(areas);
    			for (AreaBean q : areas) {
    				this.items7a.remove(q.getNombre());
    			}
            }
            
			this.areas_actuales = this.items7b.size();
            selectedEmployees.clear();
            //this.tipos = serviciosTipos.catalogo();// actualiza el reporte
            this.grupos = serviciosGrupos.catalogo();
            this.Mensaje = "Se Tipo Actualizó Correctamente";
        } else {
            this.error = "Debe seleccionar solo un registro";
            this.ver = true;

        }
    }
    
    public void pasaDerechaA(ActionEvent actionEvent) {
		itemselectA[0] = this.dirigidoA;
		Utils.pasaDerecha(this.items7a, this.items7b, this.itemselectA);
	}
    
    public void pasaDerecha(ActionEvent actionEvent) {
		itemselect[0] = this.dirigidoN;
		Utils.pasaDerecha(this.items7a, this.items7b, this.itemselect);
	}
    
    public void pasaIzquierda(ActionEvent actionEvent) { // this.items7aSelected
    	 Usuario usuario = null;
    	 HttpSession session = null;
    	 session = (HttpSession) FacesContext.getCurrentInstance()
         .getExternalContext().getSession(false);
         usuario = (Usuario) session.getAttribute("sUsuario");
         this.responsableA = usuario.getLogin();
		for (int i = 0; i < this.items7bSelected.length; i++) {
			System.out.println(this.items7bSelected[i]);
			//System.out.println(this.correlativollave);
			int area = Integer.parseInt(this.items7bSelected[i]);
			//System.out.println("QUIERO VER EL AREA");
			//System.out.println(area);
			// long ncodderivado=Long.parseLong(this.items7bSelected[i]);
			// this.anollave=p.getNano();
			// this.origenllave=p.getNorigen();
			// this.tipodocllave=p.getVtipodoc();
			// secuencialDAO.updatecorrelativo(secuencial,tipodoc);
			grupoDAO.updatedirigido(this.codigoA, area, this.responsableA );

		}

		Utils.pasaIzquierda(items7a, items7b, items7bSelected);
	}
    
    

    public void eventActualizarGrupo(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
    	try {
	        this.verActualizar = false;
	        
	        Set set = this.items7b.entrySet();
			Iterator i = set.iterator();
			String valor;

			int cont = 0;
			// captura areas seleccionadas modificadas o igual
			this.dirigidoAA = "";
			while (i.hasNext()) {
				cont++;
				Map.Entry val = (Map.Entry) (i.next());
				valor = val.getValue().toString();
				valores = valores + valor;
				this.dirigidoAA = valores;

			}
	        
	        
	        GrupoBean gruposBean = new GrupoBean();
	        Usuario usuarioBean = new Usuario();
	        
	    	
	        // le paso como parametro al stored	        
	        gruposBean.setCodigo(this.codigoA);
	        gruposBean.setDescripcion(this.descripcionA);
	        gruposBean.setEstado(this.estadoA);
	        gruposBean.setAbreviatura(this.abreviaturaA);
	        gruposBean.setRespcrea(this.responsableA);
	        gruposBean.setVdirigido(this.dirigidoAA);	        
	        serviciosGrupos.actualizarGrupos(gruposBean);
	        //this.grupos = serviciosTipos.catalogo();// actualiza el
	        this.grupos = serviciosGrupos.catalogo();
	        // reporte
	        this.Mensaje = "Se Realizaron los Cambios Correctamente";
	        this.verCatalogo = true;
	        this.verActualizar = false;
	        
    	} catch (Exception exception) {
				logger.error("[MBeanEntrantesMesa.eventActualizarEntrante]",
						exception);
				this.error = "Error Interno, comuniquese con el Administrador";
				this.ver = true;
				exception.printStackTrace();
				this.verActualizar = true;
    	} finally {

			//this.valores = "";
			this.verDetalles = false;
			// ind_adjuntar=0;
			// if(formulario)
			// session.setAttribute("file", null);

		}
    }

    public void eventNuevo(ActionEvent event) {
        this.limpiar();
        //combo.setLabel("ITEMS");
        this.verNuevo = true;
        this.verCatalogo = false;

    }

    public void eventRefrescar(ActionEvent event) {
        this.verCatalogo = true;
        //this.tipos = serviciosTipos.catalogo();
        this.grupos = serviciosGrupos.catalogo();
        this.detalle = "";

    }

    public void eventRegistrarTipos(ActionEvent event) {
    	HttpSession session = null;
        try {
            // /acediendo a sesion http
            session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            String valida = this.validarFormularioNuevo();
            if (valida == null) {
			            // ///guardando en sesion un objeto
			            Usuario usuario = null;
			            usuario = (Usuario) session.getAttribute("sUsuario");
			            this.responsableN = usuario.getLogin();
			            this.verNuevo = false;
			            
			            Set set = this.items7b.entrySet();
						Iterator i = set.iterator();
						String valor = null;
						suma_areas = "";
						while (i.hasNext()) {
							Map.Entry val = (Map.Entry) (i.next());
							System.out.println("value Hash !! : " + val.getValue());
							valor = (String) val.getValue();
							suma_areas += valor;
			
						}
						GrupoBean gruposBean = new GrupoBean();
				        gruposBean.setDescripcion(this.descripcionN);
				        gruposBean.setAbreviatura(this.abreviaturaN);
				        gruposBean.setEstado(this.estadoN);
				        gruposBean.setRespcrea(this.responsableN);
						gruposBean.setVdirigido(suma_areas);
			            //System.out.println(this.descripcionN);
			            //System.out.println(this.estadoN);
			            //String out = serviciosTipos.nuevoTipos(tiposBean);
			            String out = serviciosGrupos.nuevoGrupos(gruposBean);
			            
			            out = out.trim();
			            if (out.equals("0")) {
			            System.out.println("OUT STORED!!!:" + out);
					    //this.tipos = serviciosTipos.catalogo();// actualiza el
			            this.grupos = serviciosGrupos.catalogo();
			            // reporte			
			            this.ver = true;
			            this.verCatalogo = true;
			            this.verActualizar = false;
			            this.Mensaje = "Se Realizaron los Cambios Correctamente";
			            	
			            }
			            
			           
			       
			        }
			        
			        else {
						//formulario = false;
						this.Mensaje = valida;						
						this.ver = true;
					}

				} catch (Exception e) {
					logger.error("Fallo el registro", e);
					this.Mensaje = "Transaccion No valida.";
					this.ver = true;
					this.verCatalogo = true;
					this.verActualizar = false;
				} finally {
					
					
				}

    }

    public void cerrarDetalles(ActionEvent event) {
        this.verDetalles = false;

    }

    

    public List<GrupoBean> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoBean> grupos) {
		this.grupos = grupos;
	}

	

    public void setServiciosGrupos(IServiciosGrupos serviciosGrupos) {
		this.serviciosGrupos = serviciosGrupos;
	}

	public void cerrar(ActionEvent event) {
        this.ver = false;
    }
    /**
     * ***********************************************
     */
    // list of selected employees
    protected ArrayList<GrupoBean> selectedEmployees;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanGrupos() {
        selectedEmployees = new ArrayList<GrupoBean>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (GrupoBean t : selectedEmployees) {
            System.out.println(t.getCodigo() + "   " + t.getAbreviatura());
        }
    }

    @PostConstruct
    public void posterior() {
        //this.grupos = serviciosTipos.catalogo();
    	this.grupos = serviciosGrupos.catalogo();
        System.out.println("Post Construct, cargo catalogo...size=" + this.grupos.size());
        System.out.println("Contructor MBeanTipos....");
        
        List<AreaBean> area_derivados = grupoDAO.areas();
		List itemsderivados = Utils.getAreas(area_derivados);
		// this.items7 = itemsderivados;
		// for (AreaBean a: area_derivados);
		// CF V01.00
		this.items7a = Utils.getAreasLink(area_derivados);
		
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
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedEmployees.clear();

        /*
         * If application developers rely on validation to control submission of
         * the form or use the result of the selection in cascading control set
         * up the may want to defer procession of the event to
         * INVOKE_APPLICATION stage by using this code fragment if
         * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
         * return; }
         */

        System.out.println("Tama:" + grupos.size());
        // build the new selected list
        GrupoBean employee;
        for (int i = 0, max = grupos.size(); i < max; i++) {
            employee = (GrupoBean) grupos.get(i);
            if (employee.isSelected()) {
                selectedEmployees.add(employee);
            }
        }
        for (GrupoBean t : selectedEmployees) {
            System.out.println(t.getCodigo() + "  " + t.getAbreviatura());
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
     * @param event jsf action event.
     */
    public void changeSelectionMode(ValueChangeEvent event) {

        String newValue = event.getNewValue() != null ? event.getNewValue()
                .toString() : null;
        multiple = false;
        enhancedMultiple = false;
        if ("Single".equals(newValue)) {
            selectedEmployees.clear();

            // build the new selected list
            GrupoBean employee;
            for (int i = 0, max = grupos.size(); i < max; i++) {
                employee = (GrupoBean) grupos.get(i);
                employee.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = true;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = true;
        }
    }

    public void eventFiltros(ActionEvent event) {
        //this.ver = true;	
        System.out.println("Filtrando..");
        this.grupos = grupoDAO.filtrosSP(opcion, detalle);

        System.out.println(opcion + "  " + detalle);
    }

    public void eventCancelar(ActionEvent event) {
        this.verCatalogo = true;
        this.verNuevo = false;
        this.verActualizar = false;
    }
    
    public String validarFormulario() {
		String mensaje = "Falta ingresar: ";
		boolean ok = true;
				
		if (this.items7b.size() == 0) {
			if (ok == false) {
				mensaje += ",Dirigido";
			} else {
				mensaje += "Dirigido";
			}
			ok = false;
		}
		
		
		if (ok) {
			mensaje = null;
		}
		return mensaje;
	}
    
    public String validarFormularioNuevo() {
		String mensaje = "Falta ingresar: ";
		boolean ok = true;
		
		if (this.descripcionN.equals("")) {
			if (ok == false) {
				mensaje += ",Descripcion";
			} else {
				mensaje += " Descripcion";
			}
			ok = false;
		}
		
		if (this.abreviaturaN.equals("")) {
			if (ok == false) {
				mensaje += ",Abreviatura";
			} else {
				mensaje += " Abreviatura";
			}
			ok = false;
		}
		
			
		if (this.items7b.size() == 0) {
			if (ok == false) {
				mensaje += ",Integrante Grupo";
			} else {
				mensaje += " Integrante Grupo";
			}
			ok = false;
		}
		
		
		if (ok) {
			mensaje = null;
		}
		return mensaje;
	}

    public void limpiar() {
        this.codigoN = "";
        this.descripcionN = "";
        this.fechaN = new Date();
        this.responsableN = "";
        this.estadoN = "";
        this.abreviaturaN = "";
        this.items7b.clear();
        this.items7b.clear();
		
		List<AreaBean> area_derivados = grupoDAO.areas();
		List itemsderivados = Utils.getAreas(area_derivados);
		this.items7a = Utils.getAreasLink(area_derivados);
        //this.items7a = Utils.getAreasLink(area_derivados);
        //abreviaturaA
    }

    public void limpiarActualizar() {
        //this.codigoA = "";
        this.descripcionA = "";
        this.fechaA = new Date();
        this.responsableA = "";
        this.estadoA = "";
    }

    public ArrayList getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(ArrayList selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
    }

    public String getMultiRowSelect() {
        return multiRowSelect;
    }

    /**
     * Sets the selection more of the rowSelector.
     *
     * @param multiRowSelect true indicates multi-row select and false indicates
     * single row selection mode.
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

    

    public void setGrupoDAO(GruposDAO grupoDAO) {
		this.grupoDAO = grupoDAO;
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

    public boolean isVerActualizar() {
        return verActualizar;
    }

    public void setVerActualizar(boolean verActualizar) {
        this.verActualizar = verActualizar;
    }

    public boolean isVerCatalogo() {
        return verCatalogo;
    }

    public void setVerCatalogo(boolean verCatalogo) {
        this.verCatalogo = verCatalogo;
    }

    public String getCodigoN() {
        return codigoN;
    }

    public void setCodigoN(String codigoN) {
        this.codigoN = codigoN;
    }

    public String getDescripcionN() {
        return descripcionN;
    }

    public void setDescripcionN(String descripcionN) {
        this.descripcionN = descripcionN;
    }

    public Date getFechaN() {
        return fechaN;
    }

    public void setFechaN(Date fechaN) {
        this.fechaN = fechaN;
    }

    public String getResponsableN() {
        return responsableN;
    }

    public void setResponsableN(String responsableN) {
        this.responsableN = responsableN;
    }

    public String getEstadoN() {
        return estadoN;
    }

    public void setEstadoN(String estadoN) {
        this.estadoN = estadoN;
    }

    

    public int getCodigoA() {
		return codigoA;
	}

	public void setCodigoA(int codigoA) {
		this.codigoA = codigoA;
	}

	public String getDescripcionA() {
        return descripcionA;
    }

    public void setDescripcionA(String descripcionA) {
        this.descripcionA = descripcionA;
    }

    public Date getFechaA() {
        return fechaA;
    }

    public void setFechaA(Date fechaA) {
        this.fechaA = fechaA;
    }

    public String getResponsableA() {
        return responsableA;
    }

    public void setResponsableA(String responsableA) {
        this.responsableA = responsableA;
    }

    public String getEstadoA() {
        return estadoA;
    }

    public void setEstadoA(String estadoA) {
        this.estadoA = estadoA;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getAbreviaturaN() {
        return abreviaturaN;
    }

    public void setAbreviaturaN(String abreviaturaN) {
        this.abreviaturaN = abreviaturaN;
    }

    public String getAbreviaturaA() {
        return abreviaturaA;
    }

    public void setAbreviaturaA(String abreviaturaA) {
        this.abreviaturaA = abreviaturaA;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

	public String getDirigidoA() {
		return dirigidoA;
	}

	public void setDirigidoA(String dirigidoA) {
		this.dirigidoA = dirigidoA;
	}

	public LinkedHashMap<String, String> getItems7a() {
		return items7a;
	}

	public void setItems7a(LinkedHashMap<String, String> items7a) {
		this.items7a = items7a;
	}

	public LinkedHashMap<String, String> getItems7b() {
		return items7b;
	}

	public void setItems7b(LinkedHashMap<String, String> items7b) {
		this.items7b = items7b;
	}

	public String[] getItems7aSelected() {
		return items7aSelected;
	}

	public void setItems7aSelected(String[] items7aSelected) {
		this.items7aSelected = items7aSelected;
	}

	public String[] getItems7bSelected() {
		return items7bSelected;
	}

	public void setItems7bSelected(String[] items7bSelected) {
		this.items7bSelected = items7bSelected;
	}

	public String getDirigidoN() {
		return dirigidoN;
	}

	public void setDirigidoN(String dirigidoN) {
		this.dirigidoN = dirigidoN;
	}

	public String getDirigidoAA() {
		return dirigidoAA;
	}

	public void setDirigidoAA(String dirigidoAA) {
		this.dirigidoAA = dirigidoAA;
	}
	
	
    
    
}
