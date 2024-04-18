package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.PerfilBean;
import com.sedapal.tramite.beans.UsersBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.UsersDAO;
import com.sedapal.tramite.servicios.IServiciosUsers;
import com.sedapal.tramite.util.Utils;

public class MBeanUsers implements IMBeanUsers, Serializable {

    private List<UsersBean> users;
    public IServiciosUsers serviciosUsers;
    private Date date1 = new Date();
    private Date date2 = new Date();
    private List items;
    private List items1;
    private String itemSeleccionado;
    private String item1Seleccionado;
    private boolean ver = false;
    private String error = "";
    private String mensaje = "Se Grabó Satisfactoriamente";
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean verDetalles = false;
    // para el formulario nuevo
    private String loginN;
    private String fichaN;
    private String nombreN;
    private String estadoN;
    private String indicadorN;
    private String passwordN;
    private Date fechaN;
    private int zonaN;
    private int ncodareaN;
    private String nomequipoN;
    private String perfilN;
    private int nperfilN;
    private String sistemaN;
    private String resposableN;
    private int nconexionN;
    // para el formulario Actualizar
    private String loginA;
    private String fichaA;
    private String nombreA;
    private String estadoA;
    private String indicadorA;
    private String passwordA;
    private Date fechaA;
    private int zonaA;
    private int ncodareaA;
    private String nomequipoA;
    private String perfilA;
    private int nperfilA;
    private String sistemaA;
    private String resposableA;
    private int nconexionA;
    // para el formulario Detalle
    private String loginD;
    private String fichaD;
    private String nombreD;
    private String estadoD;
    private String indicadorD;
    private String passwordD;
    private Date fechaD;
    private int zonaD;
    private int ncodareaD;
    private String nomequipoD;
    private String perfilD;
    private String sistemaD;
    // binding al combo
    private String login2;
    private String ficha2;
    private String nombre2;
    private String estado2;
    private String indicador2;
    private String password2;
    private Date fecha2;
    private int zona2;
    private int ncodarea2;
    private String nomequipo2;
    private String perfil2;
    private String sistema2;
    //binding al combo
    private HtmlSelectOneMenu combo;
    private String desc;
    private Logger logger = Logger.getLogger("R1");
    // para la busqueda
    private String opcion;
    private String detalle;
    private UsersDAO usersDAO;

    public void eventEliminar(ActionEvent event) {

        if (this.selectedUserss.size() < 1) {
            this.error = "Debe seleccionar por lo menos un Usuario";
            this.ver = true;
        } else {
            // llamamos a servicios o dao pasandole los datos a eliminar
            String codigo;
            for (UsersBean u : selectedUserss) {
                codigo = u.getLogin();
                // le paso como parametro al stored
                serviciosUsers.eliminarUsers(codigo);
            }

            selectedUserss.clear();
            this.users = serviciosUsers.catalogo();// actualiza el
            // reporte
            this.error = "Registro Eliminado!";            
            this.ver = true;
        }

    }

    public void eventActualizar(ActionEvent evt) {
        this.limpiarActualizar();
        if (this.selectedUserss.size() == 1) {
            this.verActualizar = true;
            this.verCatalogo = false;

            for (UsersBean u : selectedUserss) {
                this.loginA = u.getLogin();
                this.nombreA = u.getNombre();
                this.fichaA = String.valueOf(u.getFicha());
                this.passwordA = u.getPassword();
                this.nomequipoA = u.getNomequipo();
                this.estadoA = u.getEstado();
                this.fechaA = u.getFecha();
                this.perfilA = u.getPerfil();
                this.ncodareaA = u.getCodarea();
                this.nperfilA = u.getNcodperfil();
                this.nconexionA = u.getNconexion();

            }
            selectedUserss.clear();
            this.mensaje = "Se Grabó Satisfactoriamente";
            this.users = serviciosUsers.catalogo();// actualiza el
            // reporte
        } else {
            this.error = "Debe Seleccionar un Usuario";
            this.ver = true;

        }
    }

    public void eventActualizarUsers(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
        this.verActualizar = false;
        try {
        	
        
	        UsersBean usersBean = new UsersBean();
	        ///acediendo a sesion http
	        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	        /////guardando en sesion un objeto
	        Usuario usuario = null;
	        usuario = (Usuario) session.getAttribute("sUsuario");
	        this.resposableA = usuario.getLogin();
	        // le paso como parametro al stored
	        usersBean.setLogin(this.loginA);
	        usersBean.setNombre(this.nombreA);
	        usersBean.setPassword(this.passwordA);
	        usersBean.setFicha(Integer.parseInt(this.fichaA));
	        usersBean.setEstado(this.estadoA);
	        usersBean.setFecha(this.fechaA);
	        usersBean.setNomequipo(this.nomequipoA);
	        usersBean.setPerfil(this.perfilA);
	        usersBean.setReponsable(this.resposableA);
	        usersBean.setCodarea(this.ncodareaA);
	        usersBean.setNcodperfil(this.nperfilA);
	        usersBean.setNconexion(this.nconexionA);
	        serviciosUsers.actualizarUser(usersBean);
	        this.users = serviciosUsers.catalogo();// actualiza el
	        // reporte
	        this.verCatalogo = true;
	        this.verActualizar = false;
	        this.error = "Se Grabó Satisfactoriamente";
	        this.ver = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Fallo el registro", e);
            this.error = "Transaccion No valida.";
            this.ver = true;
        }
    }

    public void eventNuevo(ActionEvent event) {
        this.limpiar();
        //combo.setLabel("ITEMS");
        //this.error = "No se Grabó el Registro";
        this.verNuevo = true;
        this.verCatalogo = false;
        //this.ver = true;

    }

    public void eventRefrescar(ActionEvent event) {
        this.users = serviciosUsers.catalogo();
        this.detalle = "";

    }

    public void eventBusquedas(ActionEvent event) {
        //this.ver = true;
        this.users = usersDAO.busquedauserSP(opcion, detalle);
        //this.remitente = remitenteDAO.filtrosSP(opcion, detalle);	

    }

    public void eventRegistrarUsers(ActionEvent event) {
        try {

            //String estado = bean.getLogin();
            this.verNuevo = false;
            UsersBean usersBean = new UsersBean();
            ///acediendo a sesion http
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            /////guardando en sesion un objeto
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            this.resposableN = usuario.getLogin();
            String valida = null;
            valida = this.validarFormulario();
	            if (valida.equals("ok")) {
	            usersBean.setLogin(this.loginN);
	            usersBean.setNombre(this.nombreN);
	            usersBean.setNomequipo(this.nomequipoN);
	            usersBean.setFicha(Integer.parseInt(this.fichaN));
	            usersBean.setPassword(this.passwordN);
	            usersBean.setEstado(this.estadoN);
	            usersBean.setFecha(this.fechaN);
	            usersBean.setPerfil(this.perfilN);
	            usersBean.setReponsable(this.resposableN);
	            usersBean.setNconexion(this.nconexionN);
	            serviciosUsers.nuevoUsers(usersBean);
	            this.users = serviciosUsers.catalogo();// actualiza el reporte			
	            //serviciosUsers.
	            this.error = "Se Grabó Satisfactoriamente";
	            this.ver = true;
	            this.verCatalogo = true;
	            this.verActualizar = false;
	            } else {
	                //formulario = false;
	                this.error = valida;
	                this.ver = true;
	            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Fallo el registro", e);
            this.error = "Transaccion No valida.";
            this.ver = true;
        }

    }
    
    public String validarFormulario() {
        String mensaje = "Falta ingresar: ";
        boolean ok = true;
        
       

       
        if (this.loginN.equals("")) {
            if (ok == false) {
                mensaje += ",Login";
            } else {
                mensaje += "Login";
            }
            ok = false;
        }
        
        if (this.estadoN.equals("SELECCIONAR")) {
            if (ok == false) {
                mensaje += ",Estado";
            } else {
                mensaje += "Estado";
            }
            ok = false;
        }
        
        
        if (this.nombreN.trim().length() == 0) {
            if (ok == false) {
                mensaje += ",Nombre";
            } else {
                mensaje += "Nombre";
            }
            ok = false;
        }
        
        if (this.passwordN.trim().length() == 0) {
            if (ok == false) {
                mensaje += ",Password";
            } else {
                mensaje += "Password";
            }
            ok = false;
        }
        

        
        
        
        if (this.nomequipoN.trim().length() == 0) {
            if (ok == false) {
                mensaje += ",Equipo";
            } else {
                mensaje += "Equipo";
            }
            ok = false;
        }
      
             
                             

        if (ok) {
            mensaje = "ok";
        }
        return mensaje;
    }


    public void cerrarDetalles(ActionEvent event) {
        this.verDetalles = false;
    }

    public List<UsersBean> getUsers() {
        return this.users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public void setServiciosUsers(IServiciosUsers serviciosUsers) {
        this.serviciosUsers = serviciosUsers;
    }

    public IServiciosUsers getServiciosUsers() {
        return serviciosUsers;
    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }

    public void eventFiltros(ActionEvent event) {
        // this.ver = true;
        // usamos para darle el fomato adecuado para pasarle al stored de oracle
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        String date1 = sdf.format(this.getDate1());
        String date2 = sdf.format(this.getDate2());
        // se puede validar fechas
        if (this.date1.after(this.date2)) {
            this.error = "Fecha 1 debe ser menor que la fecha 2";
            this.ver = true;
        } else {
            System.out.println("Filtrando..");
            this.users = usersDAO.filtrosSP(date1, date2, this.getItemSeleccionado());

        }
        System.out.println(this.users.size());
        System.out.println(date1 + "  " + date2 + "  "
                + this.getItemSeleccionado());
    }
    /**
     * ***********************************************
     */
    // list of selected userss
    protected ArrayList<UsersBean> selectedUserss;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = false;
    protected boolean enhancedMultiple;

    public MBeanUsers() {
        selectedUserss = new ArrayList<UsersBean>();

    }

    public void eventoNuevo(ActionEvent evnt) {
        for (UsersBean u : selectedUserss) {
            System.out.println(u.getLogin() + "   " + u.getNombre() + "  " + u.getPerfil());
        }
    }

    @PostConstruct
    public void posterior() {
        this.users = serviciosUsers.catalogo();
        //System.out.println("Perfiles de usuario...size=" + this.users.size());
        //System.out.println("Contructor MBeanProductos....");
        List<PerfilBean> perfiles = usersDAO.perfiles();
        List itemsTipos = Utils.getPerfiles(perfiles);
        this.items = itemsTipos;
        System.out.println("Perfil....size" + this.items.size());
        for (PerfilBean p : perfiles);
        //System.out.println(p.getCodigo() + "   " + p.getNombre())




        List<AreaBean> areas = usersDAO.areas();
        List itemsAreas = Utils.getAreas(areas);
        this.items1 = itemsAreas;
        for (AreaBean a : areas);
        //System.out.println(a.getCodigo() + "   " + a.getNombre())



        //Comenta - Agrega Eli Diaz
        //this.perfilA= perfiles.get(4).toString();
        //System.out.println("Se cargo los combos....");
        //System.out.println(items);
        //System.out.println(itemsTipos);
        //System.out.println(UsersDAO());
        //this.perfilA = perfiles.get(2).toString();
        //System.out.println(perfilA);	



    }

    public String getPerfilA() {
        return perfilA;
    }

    /**
     * SelectionListener bound to the ice:rowSelector component. Called when a
     * row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedUserss.clear();

        /*
         * If application developers rely on validation to control submission of
         * the form or use the result of the selection in cascading control set
         * up the may want to defer procession of the event to
         * INVOKE_APPLICATION stage by using this code fragment if
         * (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         * event.setPhaseId( PhaseId.INVOKE_APPLICATION ); event.queue();
         * return; }
         */

        System.out.println("Trama:" + users.size());
        // build the new selected list
        UsersBean usuarios;
        for (int i = 0, max = users.size(); i < max; i++) {
            usuarios = (UsersBean) users.get(i);
            if (usuarios.isSelected()) {
                selectedUserss.add(usuarios);
            }
        }
        for (UsersBean u : selectedUserss) {
            System.out.println(u.getLogin() + "  " + u.getNombre() + "  " + u.getPerfil() + "  " + u.getCodarea() + "  " + u.getNcodperfil());
            this.nomequipoA = u.getNomequipo();
            this.perfilA = u.getPerfil();
            this.nperfilA = u.getNcodperfil();
            this.ncodareaA = u.getCodarea();
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
            selectedUserss.clear();

            // build the new selected list
            UsersBean usuarios;
            for (int i = 0, max = users.size(); i < max; i++) {
                usuarios = (UsersBean) users.get(i);
                usuarios.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = true;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = true;
        }
    }

    public void eventCancelar(ActionEvent event) {
        this.verCatalogo = true;
        this.verNuevo = false;
        this.verActualizar = false;
    }

    public void limpiar() {
        this.loginN = "";
        this.fichaN = "";
        this.nombreN = "";
        this.estadoN = "";
        this.passwordN = "";
        //this.fechaA = new Date();	
        this.fechaN = new Date();
        this.nomequipoN = "";
        this.perfilN = "";
        
    }

    public void limpiarActualizar() {

        this.loginA = "";
        this.fichaA = "";
        this.nombreA = "";
        this.estadoA = "";
        this.passwordA = "";
        //this.fechaA = new Date();		
        this.nomequipoA = "";
        //this.perfilA = "";		
    }

    public void limpiarDetalles() {
        this.loginD = "";
        this.fichaD = "";
        this.nombreD = "";
        this.estadoD = "";
        this.passwordD = "";
        //this.fechaA = new Date();		
        this.nomequipoD = "";
        this.perfilD = "";
    }

    public boolean isVerActualizar() {
        return verActualizar;
    }

    public void setVerActualizar(boolean verActualizar) {
        this.verActualizar = verActualizar;
    }

    public HtmlSelectOneMenu getCombo() {
        return combo;
    }

    public void setCombo(HtmlSelectOneMenu combo) {
        this.combo = combo;
    }

    public ArrayList getSelectedUsers() {
        return selectedUserss;
    }

    public void setSelectedUsers(ArrayList selectedUserss) {
        this.selectedUserss = selectedUserss;
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

    public List getItems1() {
        return items1;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public void setItems1(List items1) {
        this.items1 = items1;
    }

    public String getItemSeleccionado() {
        return itemSeleccionado;
    }

    public String getItem1Seleccionado() {
        return item1Seleccionado;
    }

    public void setItemSeleccionado(String itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }

    public void setItem1Seleccionado(String item1Seleccionado) {
        this.item1Seleccionado = item1Seleccionado;
    }

    public void setUsersDAO(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
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

    public String getLoginN() {
        return loginN;
    }

    public void setLoginN(String loginN) {
        this.loginN = loginN;
    }

    public String getFichaN() {
        return fichaN;
    }

    public void setFichaN(String fichaN) {
        this.fichaN = fichaN;
    }

    public String getNombreN() {
        return nombreN;
    }

    public void setNombreN(String nombreN) {
        this.nombreN = nombreN;
    }

    public String getEstadoN() {
        return estadoN;
    }

    public void setEstadoN(String estadoN) {
        this.estadoN = estadoN;
    }

    public String getIndicadorN() {
        return indicadorN;
    }

    public void setIndicadorN(String indicadorN) {
        this.indicadorN = indicadorN;
    }

    public String getPasswordN() {
        return passwordN;
    }

    public void setPasswordN(String passwordN) {
        this.passwordN = passwordN;
    }

    public Date getFechaN() {
        return fechaN;
    }

    public void setFechaN(Date fechaN) {
        this.fechaN = fechaN;
    }

    public int getZonaN() {
        return zonaN;
    }

    public void setZonaN(int zonaN) {
        this.zonaN = zonaN;
    }

    public int getCodareaN() {
        return ncodareaN;
    }

    public void setCodareaN(int ncodareaN) {
        this.ncodareaN = ncodareaN;
    }

    public String getNomequipoN() {
        return nomequipoN;
    }

    public void setNomequipoN(String nomequipoN) {
        this.nomequipoN = nomequipoN;
    }

    public String getPerfilN() {
        return perfilN;
    }

    public void setPerfilN(String perfilN) {
        this.perfilN = perfilN;
    }

    public String getSistemaN() {
        return sistemaN;
    }

    public void setSistemaN(String sistemaN) {
        this.sistemaN = sistemaN;
    }

    ///
    /// para el formulario Atualizar
    ///
    public String getLoginA() {
        return loginA;
    }

    public void setLoginA(String loginA) {
        this.loginA = loginA;
    }

    public String getFichaA() {
        return fichaA;
    }

    public void setFichaA(String fichaA) {
        this.fichaA = fichaA;
    }

    public String getNombreA() {
        return nombreA;
    }

    public void setNombreA(String nombreA) {
        this.nombreA = nombreA;
    }

    public String getEstadoA() {
        return estadoA;
    }

    public void setEstadoA(String estadoA) {
        this.estadoA = estadoA;
    }

    public String getIndicadorA() {
        return indicadorA;
    }

    public void setIndicadorA(String indicadorA) {
        this.indicadorA = indicadorA;
    }

    public String getPasswordA() {
        return passwordA;
    }

    public void setPasswordA(String passwordA) {
        this.passwordA = passwordA;
    }

    public Date getFechaA() {
        return fechaA;
    }

    public void setFechaA(Date fechaA) {
        this.fechaA = fechaA;
    }

    public int getZonaA() {
        return zonaA;
    }

    public void setZonaA(int zonaA) {
        this.zonaA = zonaA;
    }

    public int getCodareaA() {
        return ncodareaA;
    }

    public void setCodareaA(int ncodareaA) {
        this.ncodareaA = ncodareaA;
    }

    public String getNomequipoA() {
        return nomequipoA;
    }

    public void setNomequipoA(String nomequipoA) {
        this.nomequipoA = nomequipoA;
    }

    public String getPefilA() {
        return perfilA;
    }

    public void setPerfilA(String perfilA) {
        this.perfilA = perfilA;
    }

    public String getSistemaA() {
        return sistemaA;
    }

    public void setSistemaA(String sistemaA) {
        this.sistemaA = sistemaA;
    }

    /// para el formulario Detalle
    ///
    public String getLoginD() {
        return loginD;
    }

    public void setLoginD(String loginD) {
        this.loginD = loginD;
    }

    public String getFichaD() {
        return fichaD;
    }

    public void setFichaD(String fichaD) {
        this.fichaD = fichaD;
    }

    public String getNombreD() {
        return nombreD;
    }

    public void setNombreD(String nombreD) {
        this.nombreD = nombreD;
    }

    public String getEstadoD() {
        return estadoD;
    }

    public void setEstadoD(String estadoD) {
        this.estadoD = estadoD;
    }

    public String getIndicadorD() {
        return indicadorD;
    }

    public void setIndicadorD(String indicadorD) {
        this.indicadorD = indicadorD;
    }

    public String getPasswordD() {
        return passwordD;
    }

    public void setPasswordD(String passwordD) {
        this.passwordD = passwordD;
    }

    public Date getFechaD() {
        return fechaD;
    }

    public void setFechaD(Date fechaD) {
        this.fechaD = fechaD;
    }

    public int getZonaD() {
        return zonaD;
    }

    public void setZonaD(int zonaD) {
        this.zonaD = zonaD;
    }

    public int getCodareaD() {
        return ncodareaD;
    }

    public void setCodareaD(int ncodareaD) {
        this.ncodareaD = ncodareaD;
    }

    public String getNomequipoD() {
        return nomequipoD;
    }

    public void setNomequipoD(String nomequipoD) {
        this.nomequipoD = nomequipoD;
    }

    public String getPerfilD() {
        return perfilD;
    }

    public void setPerfilD(String perfilD) {
        this.perfilD = perfilD;
    }

    public String getSistemaD() {
        return sistemaD;
    }

    public void setSistemaD(String sistemaD) {
        this.sistemaD = sistemaD;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public int getNcodareaA() {
        return ncodareaA;
    }

    public void setNcodareaA(int ncodareaA) {
        this.ncodareaA = ncodareaA;
    }

    public String getLogin2() {
        return login2;
    }

    public void setLogin2(String login2) {
        this.login2 = login2;
    }

    public String getFicha2() {
        return ficha2;
    }

    public void setFicha2(String ficha2) {
        this.ficha2 = ficha2;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getEstado2() {
        return estado2;
    }

    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }

    public String getIndicador2() {
        return indicador2;
    }

    public void setIndicador2(String indicador2) {
        this.indicador2 = indicador2;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public int getZona2() {
        return zona2;
    }

    public void setZona2(int zona2) {
        this.zona2 = zona2;
    }

    public int getNcodarea2() {
        return ncodarea2;
    }

    public void setNcodarea2(int ncodarea2) {
        this.ncodarea2 = ncodarea2;
    }

    public String getNomequipo2() {
        return nomequipo2;
    }

    public void setNomequipo2(String nomequipo2) {
        this.nomequipo2 = nomequipo2;
    }

    public String getPerfil2() {
        return perfil2;
    }

    public void setPerfil2(String perfil2) {
        this.perfil2 = perfil2;
    }

    public String getSistema2() {
        return sistema2;
    }

    public void setSistema2(String sistema2) {
        this.sistema2 = sistema2;
    }

    public String getResposableN() {
        return resposableN;
    }

    public void setResposableN(String resposableN) {
        this.resposableN = resposableN;
    }

    public String getResposableA() {
        return resposableA;
    }

    public void setResposableA(String resposableA) {
        this.resposableA = resposableA;
    }

    public int getNperfilN() {
        return nperfilN;
    }

    public void setNperfilN(int nperfilN) {
        this.nperfilN = nperfilN;
    }

    public int getNperfilA() {
        return nperfilA;
    }

    public void setNperfilA(int nperfilA) {
        this.nperfilA = nperfilA;
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

    public int getNconexionN() {
        return nconexionN;
    }

    public void setNconexionN(int nconexionN) {
        this.nconexionN = nconexionN;
    }

    public int getNconexionA() {
        return nconexionA;
    }

    public void setNconexionA(int nconexionA) {
        this.nconexionA = nconexionA;
    }
}
