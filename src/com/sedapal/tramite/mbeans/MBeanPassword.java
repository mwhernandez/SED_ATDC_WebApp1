package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import com.sedapal.tramite.beans.PasswordBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.PasswordDAO;

import com.sedapal.tramite.servicios.IServiciosPassword;

public class MBeanPassword implements IMBeanPassword, Serializable {

    private List<PasswordBean> password;
    public IServiciosPassword serviciosPassword;
    // para el formulario Actualizar 
    private String usuarioA;
    private String passwordactualA;
    private String passwordnuevoA;
    private String passwordconfirmaA;
    private int fichaA;
    private int areaA;
    private List items;
    private boolean verNuevo = false;
    //private boolean verCatalogo = true;
    private boolean verActualizar = true;
    private boolean ver = false;
    private String error = "";
    private int ncodareaA;
    private Date date1 = new Date();
    private Date date2 = new Date();
    private String ncodarea;
    private PasswordDAO passwordDAO;

    public void setServiciosPassword(IServiciosPassword serviciosPassword) {
        this.serviciosPassword = serviciosPassword;
    }
    // list of selected profiles
    protected ArrayList<PasswordBean> selectedPasswordd;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = true;
    protected boolean enhancedMultiple;
    private String itemSeleccionado;

    public MBeanPassword() {
        selectedPasswordd = new ArrayList<PasswordBean>();
        System.out.println("Contructor MBeanProfiles....");
    }

    public void eventCancelar(ActionEvent event) {
        //this.verCatalogo =true;
        //this.verNuevo = false;
        this.verActualizar = true;
    }

    public void cerrar(ActionEvent event) {
        this.ver = false;
    }

    public void limpiarActualizar() {
        //this.codigoA = 0;
        //this.centroA = "";
        //this.nombreA = "";
        //this.abreviaturaA = "";
        //this.tipoA = "";		
        //this.estadoA = "";
    }

    @PostConstruct
    public void posterior() {
        //this.resumen = serviciosResumen.catalogo();
        System.out.println("Post Construct");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        //guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        this.usuarioA = usuario.getLogin();
        this.fichaA = usuario.getFicha();


    }

    public void eventActualizarPassword(ActionEvent event) {
        // llama DAO/Stored Para actualizar producto
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);;
        this.verActualizar = true;
        PasswordBean passwordBean = new PasswordBean();
        ///acediendo a sesion http
        //session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /////guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        this.usuarioA = usuario.getLogin();
        this.fichaA = usuario.getFicha();
        String passwordactual = usuario.getPassword();
        String nuevopassword = this.getPasswordnuevoA();
        String confirmapassword = this.getPasswordconfirmaA();
        //(this.date1.after(this.date2))
        if (passwordactual.equals(this.passwordactualA)) {
            if (nuevopassword.equals(confirmapassword)) {
                passwordDAO.updatePassword(this.usuarioA, this.fichaA, confirmapassword);
                this.error = "Se grabó Satisfactoriamente";
                this.ver = true;
            } else {
                this.error = "El Password Nuevo no coinciden con el Password de Confirmación";
                this.ver = true;
            }
        } else {
            this.error = "El Password Actual no coinciden con el Password de la Base de Datos ";
            this.ver = true;
        }


        //secuencialDAO.updatecorrelativo(secuencial,tipodoc);
        //respactualA = usuario.getLogin();
        //feriadoBean.setNcodigo(this.ncodigoA);
        //feriadoBean.setDferiado(this.dferiadoA);
        //feriadoBean.setVdescripcion(this.vdescripcionA);
        //feriadoBean.setVtipoferiado(this.vtipoferiadoA);
        //feriadoBean.setVestado(this.vestadoA);
        //feriadoBean.setRespactual(this.respactualA);
        //serviciosFeriado.actualizarFeriado(feriadoBean);		
        //this.feriado = serviciosFeriado.catalogo();// actualiza el
        // reporte
        //this.verCatalogo = true;
        //this.verActualizar = true;
    }

    public ArrayList<PasswordBean> getSelectedResumenn() {
        return selectedPasswordd;
    }

    public void setSelectedResumenn(ArrayList<PasswordBean> selectedResumenn) {
        this.selectedPasswordd = selectedResumenn;
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

    public void setPasswordDAO(PasswordDAO passwordDAO) {
        this.passwordDAO = passwordDAO;
    }

    public String getPasswordactualA() {
        return passwordactualA;
    }

    public void setPasswordactualA(String passwordactualA) {
        this.passwordactualA = passwordactualA;
    }

    public String getPasswordnuevoA() {
        return passwordnuevoA;
    }

    public void setPasswordnuevoA(String passwordnuevoA) {
        this.passwordnuevoA = passwordnuevoA;
    }

    public String getPasswordconfirmaA() {
        return passwordconfirmaA;
    }

    public void setPasswordconfirmaA(String passwordconfirmaA) {
        this.passwordconfirmaA = passwordconfirmaA;
    }

    public String getUsuarioA() {
        return usuarioA;
    }

    public void setUsuarioA(String usuarioA) {
        this.usuarioA = usuarioA;
    }

    public int getFichaA() {
        return fichaA;
    }

    public void setFichaA(int fichaA) {
        this.fichaA = fichaA;
    }

    public int getAreaA() {
        return areaA;
    }

    public void setAreaA(int areaA) {
        this.areaA = areaA;
    }
}