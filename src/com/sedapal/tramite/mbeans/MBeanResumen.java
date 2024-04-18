package com.sedapal.tramite.mbeans;

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

import com.icesoft.faces.component.ext.RowSelectorEvent;

import com.sedapal.tramite.beans.ResumenBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.ResumenDAO;
import com.sedapal.tramite.servicios.IServiciosResumen;

public class MBeanResumen implements IMBeanResumen, Serializable {

    private List<ResumenBean> resumen;
    public IServiciosResumen serviciosResumen;
    private List items;
    private boolean verNuevo = false;
    private boolean verCatalogo = true;
    private boolean verActualizar = false;
    private boolean ver = false;
    private String error = "";
    private int ncodareaA;
    private Date date1 = new Date();
    private Date date2 = new Date();
    private String ncodarea;
    private ResumenDAO resumenDAO;

    public List<ResumenBean> getResumen() {
        return resumen;
    }

    public void setResumen(List<ResumenBean> resumen) {
        this.resumen = resumen;
    }

    public void setServiciosResumen(IServiciosResumen serviciosResumen) {
        this.serviciosResumen = serviciosResumen;
    }
    // list of selected profiles
    protected ArrayList<ResumenBean> selectedResumenn;
    // flat to indicate multiselect row enabled.
    protected String multiRowSelect = "Multiple";
    protected boolean multiple = true;
    protected boolean enhancedMultiple;
    private String itemSeleccionado;

    public MBeanResumen() {
        selectedResumenn = new ArrayList<ResumenBean>();
        System.out.println("Contructor MBeanProfiles....");
    }

    @PostConstruct
    public void posterior() {
        //this.resumen = serviciosResumen.catalogo();
        System.out.println("Post Construct");
    }

    /**
     * SelectionListener bound to the ice:rowSelector component. Called when a
     * row is selected in the UI.
     *
     * @param event from the ice:rowSelector component
     */
    public void rowSelectionListener(RowSelectorEvent event) {
        // clear our list, so that we can build a new one
        selectedResumenn.clear();

        /* If application developers rely on validation to control submission of the form or use the result of
         the selection in cascading control set up the may want to defer procession of the event to
         INVOKE_APPLICATION stage by using this code fragment
         if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
         event.setPhaseId( PhaseId.INVOKE_APPLICATION );
         event.queue();
         return;
         }

         */

        System.out.println("Tama:" + resumen.size());
        // build the new selected list
        ResumenBean resumenn;
        for (int i = 0, max = resumen.size(); i < max; i++) {
            resumenn = (ResumenBean) resumen.get(i);
            if (resumenn.isSelected()) {
                selectedResumenn.add(resumenn);
            }
        }
        for (ResumenBean p : selectedResumenn) {
            System.out.println(p.getNcodarea() + "  " + p.getEstado() + " " + p.getResumen());
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
        if ("Single".equals(newValue)) {
            selectedResumenn.clear();

            // build the new selected list
            ResumenBean resumenn;
            for (int i = 0, max = resumen.size(); i < max; i++) {
                resumenn = (ResumenBean) resumen.get(i);
                resumenn.setSelected(false);
            }
        } else if ("Multiple".equals(newValue)) {
            multiple = true;
        } else if ("Enhanced Multiple".equals(newValue)) {
            enhancedMultiple = true;
        }
    }

    /**
     * ***********************************************
     */
    public void eventFiltros(ActionEvent event) {
        // this.ver = true;
        // usamos para darle el fomato adecuado para pasarle al stored de oracle
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // ///guardando en sesion un objeto
        Usuario usuario = null;
        usuario = (Usuario) session.getAttribute("sUsuario");
        //this.ncodareaA = usuario.getCodarea();		
        ncodarea = String.valueOf(usuario.getCodarea());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        //to_date(to_char(a.DFECCRE, 'YYYY-MM-DD hh24:mi')
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        String date1 = sdf.format(this.getDate1());
        String date2 = sdf.format(this.getDate2());
        //'YYYY-MM-DD hh24:mi'
        //String date1 = to_char(this.getDate1(), 'YYYY-MM-DD hh24:mi');
        //String date2 = to_char(this.getDate2(), 'YYYY-MM-DD hh24:mi');
        // se puede validar fechas
        if (this.date1.after(this.date2)) {
            this.error = "Fecha 1 debe ser menor que la fecha 2";
            this.ver = true;
            //this.disPrint = false;
        } else {
            // System.out.println("Filtrando..");
            // Falta Programar por Eli Diaz
            this.resumen = resumenDAO.resumenSP(date1, date2, ncodarea);
            //this.disPrint = true;

        }
        // System.out.println(this.entrantes.size());
        // System.out.println(date1 + "  " + date2 + "  "
        // + this.getItem2Seleccionado());
    }

    public void eventCancelar(ActionEvent event) {
        this.verCatalogo = true;
        this.verNuevo = false;
        this.verActualizar = false;
    }

    public void limpiarActualizar() {
        //this.codigoA = 0;
        //this.centroA = "";
        //this.nombreA = "";
        //this.abreviaturaA = "";
        //this.tipoA = "";		
        //this.estadoA = "";
    }

    public ArrayList<ResumenBean> getSelectedResumenn() {
        return selectedResumenn;
    }

    public void setSelectedResumenn(ArrayList<ResumenBean> selectedResumenn) {
        this.selectedResumenn = selectedResumenn;
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

    public void setResumenDAO(ResumenDAO resumenDAO) {
        this.resumenDAO = resumenDAO;
    }

    public int getNcodareaA() {
        return ncodareaA;
    }

    public void setNcodareaA(int ncodareaA) {
        this.ncodareaA = ncodareaA;
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
}