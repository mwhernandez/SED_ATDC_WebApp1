package com.sedapal.tramite.mbeans;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.beans.admin.SecuencialAdmBean;
import com.sedapal.tramite.dao.AreaDAO;
import com.sedapal.tramite.dao.SecuencialDAO;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

public class MBeanSecuenciales implements IMBeanSecuenciales, Serializable {

    private Date fechaActual = new Date();
    private SimpleDateFormat formatoAnnio = new SimpleDateFormat("yyyy");
    private SecuencialDAO secuencialDAO;
    private int codAreaUsuResp;
    private String nomAreaUsuResp;
    //Atributos de las paginas
    private Boolean verListado = true;
    private Boolean verRegistro = false;
    private Boolean verActualizar = false;
    private Boolean verAlerta = false;
    private String msgAlerta;
    private Boolean verAlertaConfirm = false;
    private String msgAlertaConfirm;
    private Boolean valorConfirm = false;
    private Boolean secuencialValidado = false;
    private String accionConfirm;
    //Listado
    private List listadoAnnios;
    private List<SecuencialAdmBean> listadoSecuenciales;
    private String optFiltro;
    private String txtFiltro;
    private SecuencialAdmBean selectedBean;
    //Mantenimientos    
    private String descArea;
    //Atributos de la clase
    private String codArea;
    private String annio = formatoAnnio.format(fechaActual);
    private String codTipDoc;
    private String descTipDoc;
    private String valCombo;
    private String valSecuencial;
    private String estado;
    private String descEstado;
    private String usuResponsable;
    //Atributos para la Busqueda del Area
    private Boolean verPopupBusArea;
    private String valRadioBusArea;
    private String valBusquedaBusArea;
    private List<AreaBean> listadoArea;
    private AreaDAO areaDAO;
    //Atributos para la Busqueda del Tipos de Documento
    private Boolean verPopupBusDoc;
    private String valRadioBusDoc;
    private String valBusquedaBusDoc;
    private List<TiposDocumentosBean> listadoDocumentos;

    public MBeanSecuenciales() {
        System.out.println("Contructor MBeanSecuenciales");
    }

    @PostConstruct
    public void postConstruct() {
        showListadoSecuenciales(null);
    }

    /**
     * ***********************************************
     */
    /**
     * ******** Metodos de Uso de la Pagina  *********
     */
    /**
     * ***********************************************
     */
    private void reiniciarAtributos() {
        this.setAnnio(getFormatoAnnio().format(getFechaActual()));
        this.setCodArea("");
        this.setDescArea("");
        this.setCodTipDoc("");
        this.setDescTipDoc("");
        this.setValCombo("");
        this.setValSecuencial("");
        this.setEstado("");
        this.setDescEstado("");
        this.setUsuResponsable("");
        this.setCodAreaUsuResp(0);
        for (int i = 0; i < getListadoSecuenciales().size(); i++) {
            SecuencialAdmBean beanSec = (SecuencialAdmBean) getListadoSecuenciales().get(i);
            beanSec.setSelected(false);
            getListadoSecuenciales().set(i, beanSec);
        }
    }

    private void mostrarElemento(String nomElem) {
        this.setVerListado(false);
        this.setVerRegistro(false);
        this.setVerActualizar(false);

        if (nomElem.equals("list")) {
            this.setVerListado(true);
        } else if (nomElem.equals("reg")) {
            this.setVerRegistro(true);
        } else if (nomElem.equals("upd")) {
            this.setVerActualizar(true);
        }
    }

    private void obtenerUsuario() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario beanUsuario = (Usuario) session.getAttribute("sUsuario");

        setUsuResponsable(beanUsuario.getLogin());
        setCodAreaUsuResp(beanUsuario.getCodarea());
        setNomAreaUsuResp(beanUsuario.getNomequipo());
    }

    private void obtenerListadoSecuenciales() {
        setAnnio(getFormatoAnnio().format(getFechaActual()));
        setOptFiltro("codArea");
        setTxtFiltro(String.valueOf(getCodAreaUsuResp()));
        List listaAux = getSecuencialDAO().getListadoSecuenciales(getAnnio(), getOptFiltro(), getTxtFiltro());
        this.setListadoSecuenciales(listaAux);
    }

    public void showListadoSecuenciales(ActionEvent actionEvent) {
        obtenerUsuario();
        /*
         int annioIni = 2011;
         int annioAct = Integer.parseInt(getAnnio());

         List listAnnios = new ArrayList();

         for (int i = annioIni; i < annioAct + 1; i++) {
         SelectItem selItemAnnio = new SelectItem(i, String.valueOf(i));
         listAnnios.add(selItemAnnio);
         }
         this.setListadoAnnios(listAnnios);
         */
        obtenerListadoSecuenciales();
        mostrarElemento("list");
    }
    /*
     public void filtrarListadoSecuenciales(ActionEvent actionEvent) {
     obtenerListadoSecuenciales();
     }
     */

    public void reiniciarListadoSecuenciales(ActionEvent actionEvent) {
        obtenerUsuario();
        /*
         setOptFiltro(null);
         setTxtFiltro(null);

         int annioIni = 2011;
         int annioAct = Integer.parseInt(getAnnio());
         List listAnnios = new ArrayList();

         for (int i = annioIni; i < annioAct + 1; i++) {
         SelectItem selItemAnnio = new SelectItem(i, String.valueOf(i));
         listAnnios.add(selItemAnnio);
         }
         this.setListadoAnnios(listAnnios);
         */
        obtenerListadoSecuenciales();
    }

    public void rowSelectionListener(RowSelectorEvent event) {
        setSelectedBean(new SecuencialAdmBean());

        for (int i = 0; i < getListadoSecuenciales().size(); i++) {
            SecuencialAdmBean beanSec = (SecuencialAdmBean) getListadoSecuenciales().get(i);
            if (beanSec.getSelected() != null) {
                if (beanSec.getSelected()) {
                    setSelectedBean(beanSec);
                }
            }
        }

        this.setAnnio(getSelectedBean().getAnnio());
        this.setCodArea(getSelectedBean().getCodArea());
        this.setDescArea(getSelectedBean().getDescArea());
        this.setCodTipDoc(getSelectedBean().getCodTipoDocumento());
        this.setDescTipDoc(getSelectedBean().getDescTipoDocumento());
        this.setValCombo(getSelectedBean().getOrdenCombo());
        this.setValSecuencial(getSelectedBean().getValorSecuencial());
        this.setEstado(getSelectedBean().getEstado());
        this.setDescEstado(getSelectedBean().getDescEstado());
        this.setUsuResponsable(getSelectedBean().getUsuResponsable());
    }

    public void showRegistrar(ActionEvent actionEvent) {
        reiniciarAtributos();
        obtenerUsuario();
        this.setCodArea(String.valueOf(getCodAreaUsuResp()));
        this.setDescArea(getNomAreaUsuResp());
        this.setValSecuencial("0");
        mostrarElemento("reg");
    }

    public void processRegistrar(ActionEvent actionEvent) {
        showAlertConfirm("Esta seguro que desea registrar el registro?", "insSec");
    }

    private void processConfirmRegistrar() {
        Map resultMap = new HashMap();

        resultMap = getSecuencialDAO().insertSecuencial(getCodArea(), getAnnio(), getCodTipDoc(), getUsuResponsable());

        String tipMsgSalida = (String) resultMap.get("tipMsgSalida");
        String msgSalida = (String) resultMap.get("msgSalida");

        if (tipMsgSalida.equals("OK")) {
            obtenerListadoSecuenciales();
            mostrarElemento("list");
            showAlert(msgSalida);
        } else {
            showAlert(msgSalida);
        }
    }

    public void showActualizar(ActionEvent actionEvent) {
        setAnnio(getFormatoAnnio().format(getFechaActual()));
        obtenerUsuario();
        this.setSecuencialValidado(false);
        mostrarElemento("upd");
    }

    public void invalidarSecuencial(ValueChangeEvent changeEvent) {
        setValSecuencial((String) changeEvent.getNewValue());
        setSecuencialValidado(false);
    }

    public void processValidarSecuencial(ActionEvent actionEvent) {
        Map resultMap = new HashMap();
        resultMap = getSecuencialDAO().validarSecuencial(getValSecuencial(), getCodArea(), getAnnio(), getCodTipDoc());

        String tipMsgSalida = (String) resultMap.get("tipMsgSalida");
        String msgSalida = (String) resultMap.get("msgSalida");

        if (tipMsgSalida.equals("OK")) {
            setSecuencialValidado(true);
            showAlert(msgSalida);
        } else {
            setSecuencialValidado(false);
            showAlert(msgSalida);
        }
    }

    public void processActualizar(ActionEvent actionEvent) {
        obtenerUsuario();
        showAlertConfirm("Esta seguro que desea actualizar el registro?", "updSec");
    }

    private void processConfirmActualizar() {
        Map resultMap = new HashMap();
        if (getSecuencialValidado()) {
            resultMap = getSecuencialDAO().updateSecuencial(getCodArea(), getAnnio(), getCodTipDoc(), getValSecuencial(), getEstado());

            String tipMsgSalida = (String) resultMap.get("tipMsgSalida");
            String msgSalida = (String) resultMap.get("msgSalida");

            if (tipMsgSalida.equals("OK")) {
                obtenerListadoSecuenciales();
                mostrarElemento("list");
                showAlert(msgSalida);
            } else {
                showAlert(msgSalida);
            }
        } else {
            showAlert("Por favor, Ud. debe validar el numero del secuencial antes de Actualizar el registro.");
        }
    }

    public void processEliminar(ActionEvent actionEvent) {
        obtenerUsuario();
        if (getSelectedBean() == null) {
            showAlert("Por favor, seleccione un registro a eliminar");
        } else {
            showAlertConfirm("Esta Ud. seguro que desea eliminar el registro seleccionado? Esta accion no puede deshacerse!", "delSec");
        }
    }

    private void processConfirmEliminar() {
        Map resultMap = new HashMap();
        resultMap = getSecuencialDAO().deleteSecuencial(getCodArea(), getAnnio(), getCodTipDoc());

        String tipMsgSalida = (String) resultMap.get("tipMsgSalida");
        String msgSalida = (String) resultMap.get("msgSalida");

        if (tipMsgSalida.equals("OK")) {
            obtenerListadoSecuenciales();
            showAlert(msgSalida);
        } else {
            showAlert(msgSalida);
        }
    }

    public void processRegresar(ActionEvent actionEvent) {
        mostrarElemento("list");
        reiniciarAtributos();
        obtenerUsuario();
    }

    private void showAlertConfirm(String msgAlerta, String accion) {
        this.setVerAlertaConfirm(true);
        this.setMsgAlertaConfirm(msgAlerta);
        this.setAccionConfirm(accion);
        this.setValorConfirm(false);
    }

    public void processConfirmOK(ActionEvent actionEvent) {
        this.setValorConfirm(true);
        this.setVerAlertaConfirm(false);
        this.setMsgAlertaConfirm("");
        if (getAccionConfirm().equals("insSec")) {
            processConfirmRegistrar();
        } else if (getAccionConfirm().equals("updSec")) {
            processConfirmActualizar();
        } else if (getAccionConfirm().equals("delSec")) {
            processConfirmEliminar();
        }
        this.setAccionConfirm("");
    }

    public void processConfirmNO(ActionEvent actionEvent) {
        this.setValorConfirm(false);
        this.setVerAlertaConfirm(false);
        this.setMsgAlertaConfirm("");
        this.setAccionConfirm("");
    }

    private void showAlert(String msgAlerta) {
        this.setVerAlerta(true);
        this.setMsgAlerta(msgAlerta);
    }

    public void processCerrarAlerta(ActionEvent actionEvent) {
        this.setVerAlerta(false);
        this.setMsgAlerta("");
    }

    public void processShowBusArea(ActionEvent actionEvent) {
        this.setVerPopupBusArea((Boolean) true);
        this.setValRadioBusArea("descArea");
        this.setValBusquedaBusArea("");
        this.setListadoArea(new ArrayList());
    }

    public void processBusquedaBusArea(ActionEvent actionEvent) {
        List listadoItems = new ArrayList();
        listadoItems = getAreaDAO().busquedaArea(getValRadioBusArea(), getValBusquedaBusArea());
        this.setListadoArea(listadoItems);
    }

    public void processSeleccBusArea(ActionEvent actionEvent) {
        AreaBean beanArea = new AreaBean();
        for (int i = 0; i < getListadoArea().size(); i++) {
            beanArea = (AreaBean) getListadoArea().get(i);
            if (beanArea.isSelected()) {
                break;
            }
        }
        if (beanArea != null) {
            this.setCodArea(String.valueOf(beanArea.getCodigo()));
            this.setDescArea(beanArea.getNombre());

            this.setVerPopupBusArea((Boolean) false);
        }
    }

    public void processCerrarBusArea(ActionEvent actionEvent) {
        this.setVerPopupBusArea((Boolean) false);
    }

    public void processShowBusDoc(ActionEvent actionEvent) {
        if (getCodArea().isEmpty()) {
            showAlert("Por favor, sirvase seleccionar un area previamente.");
        } else {
            List listadoItems = new ArrayList();
            listadoItems = getSecuencialDAO().getListadoTipoDocumentos(getCodArea(), "disponible", "", "");
            this.setListadoDocumentos(listadoItems);

            this.setVerPopupBusDoc((Boolean) true);
            this.setValRadioBusDoc("descDoc");
            this.setValBusquedaBusDoc("");

        }
    }

    public void processResetBusquedaBusDoc(ActionEvent actionEvent) {
        List listadoItems = new ArrayList();
        listadoItems = getSecuencialDAO().getListadoTipoDocumentos(getCodArea(), "disponible", "", "");

        this.setListadoDocumentos(listadoItems);
        this.setValRadioBusDoc("descDoc");
        this.setValBusquedaBusDoc("");
    }

    public void processBusquedaBusDoc(ActionEvent actionEvent) {
        List listadoItems = new ArrayList();

        //disponible : listado de documentos que no estan en uso por el area
        //   enUso   : listado de documentos que si estan en uso por el area
        //  *null*   : todos los documentos
        listadoItems = getSecuencialDAO().getListadoTipoDocumentos(getCodArea(), "disponible", getValRadioBusDoc(), getValBusquedaBusDoc());
        this.setListadoDocumentos(listadoItems);
    }

    public void processSeleccBusDoc(ActionEvent actionEvent) {
        TiposDocumentosBean beanTDoc = new TiposDocumentosBean();
        for (int i = 0; i < getListadoDocumentos().size(); i++) {
            beanTDoc = (TiposDocumentosBean) getListadoDocumentos().get(i);
            if (beanTDoc.isSelected()) {
                break;
            }
        }
        if (beanTDoc != null) {
            this.setCodTipDoc(String.valueOf(beanTDoc.getCodigo()));
            this.setDescTipDoc(beanTDoc.getDescripcion());

            this.setVerPopupBusDoc((Boolean) false);

        }
    }

    public void processCerrarBusDoc(ActionEvent actionEvent) {
        this.setVerPopupBusDoc((Boolean) false);
    }

    /**
     * *************************************
     */
    /**
     * ******** Metodos de Acceso  *********
     */
    /**
     * *************************************
     */
    /**
     * @return the fechaActual
     */
    public Date getFechaActual() {
        return fechaActual;
    }

    /**
     * @param fechaActual the fechaActual to set
     */
    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    /**
     * @return the formatoAnnio
     */
    public SimpleDateFormat getFormatoAnnio() {
        return formatoAnnio;
    }

    /**
     * @param formatoAnnio the formatoAnnio to set
     */
    public void setFormatoAnnio(SimpleDateFormat formatoAnnio) {
        this.formatoAnnio = formatoAnnio;
    }

    /**
     * @return the secuencialDAO
     */
    public SecuencialDAO getSecuencialDAO() {
        return secuencialDAO;
    }

    /**
     * @param secuencialDAO the secuencialDAO to set
     */
    public void setSecuencialDAO(SecuencialDAO secuencialDAO) {
        this.secuencialDAO = secuencialDAO;
    }

    /**
     * @return the verListado
     */
    public Boolean getVerListado() {
        return verListado;
    }

    /**
     * @param verListado the verListado to set
     */
    public void setVerListado(Boolean verListado) {
        this.verListado = verListado;
    }

    /**
     * @return the verRegistro
     */
    public Boolean getVerRegistro() {
        return verRegistro;
    }

    /**
     * @param verRegistro the verRegistro to set
     */
    public void setVerRegistro(Boolean verRegistro) {
        this.verRegistro = verRegistro;
    }

    /**
     * @return the verActualizar
     */
    public Boolean getVerActualizar() {
        return verActualizar;
    }

    /**
     * @param verActualizar the verActualizar to set
     */
    public void setVerActualizar(Boolean verActualizar) {
        this.verActualizar = verActualizar;
    }

    /**
     * @return the verAlerta
     */
    public Boolean getVerAlerta() {
        return verAlerta;
    }

    /**
     * @param verAlerta the verAlerta to set
     */
    public void setVerAlerta(Boolean verAlerta) {
        this.verAlerta = verAlerta;
    }

    /**
     * @return the msgAlerta
     */
    public String getMsgAlerta() {
        return msgAlerta;
    }

    /**
     * @param msgAlerta the msgAlerta to set
     */
    public void setMsgAlerta(String msgAlerta) {
        this.msgAlerta = msgAlerta;
    }

    /**
     * @return the listadoAnnios
     */
    public List getListadoAnnios() {
        return listadoAnnios;
    }

    /**
     * @param listadoAnnios the listadoAnnios to set
     */
    public void setListadoAnnios(List listadoAnnios) {
        this.listadoAnnios = listadoAnnios;
    }

    /**
     * @return the listadoSecuenciales
     */
    public List<SecuencialAdmBean> getListadoSecuenciales() {
        return listadoSecuenciales;
    }

    /**
     * @param listadoSecuenciales the listadoSecuenciales to set
     */
    public void setListadoSecuenciales(List<SecuencialAdmBean> listadoSecuenciales) {
        this.listadoSecuenciales = listadoSecuenciales;
    }

    /**
     * @return the optFiltro
     */
    public String getOptFiltro() {
        return optFiltro;
    }

    /**
     * @param optFiltro the optFiltro to set
     */
    public void setOptFiltro(String optFiltro) {
        this.optFiltro = optFiltro;
    }

    /**
     * @return the txtFiltro
     */
    public String getTxtFiltro() {
        return txtFiltro;
    }

    /**
     * @param txtFiltro the txtFiltro to set
     */
    public void setTxtFiltro(String txtFiltro) {
        this.txtFiltro = txtFiltro;
    }

    /**
     * @return the selectedBean
     */
    public SecuencialAdmBean getSelectedBean() {
        return selectedBean;
    }

    /**
     * @param selectedBean the selectedBean to set
     */
    public void setSelectedBean(SecuencialAdmBean selectedBean) {
        this.selectedBean = selectedBean;
    }

    /**
     * @return the listadoDocumentos
     */
    public List getListadoDocumentos() {
        return listadoDocumentos;
    }

    /**
     * @param listadoDocumentos the listadoDocumentos to set
     */
    public void setListadoDocumentos(List listadoDocumentos) {
        this.listadoDocumentos = listadoDocumentos;
    }

    /**
     * @return the descArea
     */
    public String getDescArea() {
        return descArea;
    }

    /**
     * @param descArea the descArea to set
     */
    public void setDescArea(String descArea) {
        this.descArea = descArea;
    }

    /**
     * @return the codArea
     */
    public String getCodArea() {
        return codArea;
    }

    /**
     * @param codArea the codArea to set
     */
    public void setCodArea(String codArea) {
        this.codArea = codArea;
    }

    /**
     * @return the annio
     */
    public String getAnnio() {
        return annio;
    }

    /**
     * @param annio the annio to set
     */
    public void setAnnio(String annio) {
        this.annio = annio;
    }

    /**
     * @return the codTipDoc
     */
    public String getCodTipDoc() {
        return codTipDoc;
    }

    /**
     * @param codTipDoc the codTipDoc to set
     */
    public void setCodTipDoc(String codTipDoc) {
        this.codTipDoc = codTipDoc;
    }

    /**
     * @return the descTipDoc
     */
    public String getDescTipDoc() {
        return descTipDoc;
    }

    /**
     * @param descTipDoc the descTipDoc to set
     */
    public void setDescTipDoc(String descTipDoc) {
        this.descTipDoc = descTipDoc;
    }

    /**
     * @return the valCombo
     */
    public String getValCombo() {
        return valCombo;
    }

    /**
     * @param valCombo the valCombo to set
     */
    public void setValCombo(String valCombo) {
        this.valCombo = valCombo;
    }

    /**
     * @return the valSecuencial
     */
    public String getValSecuencial() {
        return valSecuencial;
    }

    /**
     * @param valSecuencial the valSecuencial to set
     */
    public void setValSecuencial(String valSecuencial) {
        this.valSecuencial = valSecuencial;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the usuResponsable
     */
    public String getUsuResponsable() {
        return usuResponsable;
    }

    /**
     * @param usuResponsable the usuResponsable to set
     */
    public void setUsuResponsable(String usuResponsable) {
        this.usuResponsable = usuResponsable;
    }

    /**
     * @return the verPopupBusArea
     */
    public Boolean getVerPopupBusArea() {
        return verPopupBusArea;
    }

    /**
     * @param verPopupBusArea the verPopupBusArea to set
     */
    public void setVerPopupBusArea(Boolean verPopupBusArea) {
        this.verPopupBusArea = verPopupBusArea;
    }

    /**
     * @return the valRadioBusArea
     */
    public String getValRadioBusArea() {
        return valRadioBusArea;
    }

    /**
     * @param valRadioBusArea the valRadioBusArea to set
     */
    public void setValRadioBusArea(String valRadioBusArea) {
        this.valRadioBusArea = valRadioBusArea;
    }

    /**
     * @return the valBusquedaBusArea
     */
    public String getValBusquedaBusArea() {
        return valBusquedaBusArea;
    }

    /**
     * @param valBusquedaBusArea the valBusquedaBusArea to set
     */
    public void setValBusquedaBusArea(String valBusquedaBusArea) {
        this.valBusquedaBusArea = valBusquedaBusArea;
    }

    /**
     * @return the listadoArea
     */
    public List<AreaBean> getListadoArea() {
        return listadoArea;
    }

    /**
     * @param listadoArea the listadoArea to set
     */
    public void setListadoArea(List<AreaBean> listadoArea) {
        this.listadoArea = listadoArea;
    }

    /**
     * @return the areaDAO
     */
    public AreaDAO getAreaDAO() {
        return areaDAO;
    }

    /**
     * @param areaDAO the areaDAO to set
     */
    public void setAreaDAO(AreaDAO areaDAO) {
        this.areaDAO = areaDAO;
    }

    /**
     * @return the verAlertaConfirm
     */
    public Boolean getVerAlertaConfirm() {
        return verAlertaConfirm;
    }

    /**
     * @param verAlertaConfirm the verAlertaConfirm to set
     */
    public void setVerAlertaConfirm(Boolean verAlertaConfirm) {
        this.verAlertaConfirm = verAlertaConfirm;
    }

    /**
     * @return the msgAlertaConfirm
     */
    public String getMsgAlertaConfirm() {
        return msgAlertaConfirm;
    }

    /**
     * @param msgAlertaConfirm the msgAlertaConfirm to set
     */
    public void setMsgAlertaConfirm(String msgAlertaConfirm) {
        this.msgAlertaConfirm = msgAlertaConfirm;
    }

    /**
     * @return the valorConfirm
     */
    public Boolean getValorConfirm() {
        return valorConfirm;
    }

    /**
     * @param valorConfirm the valorConfirm to set
     */
    public void setValorConfirm(Boolean valorConfirm) {
        this.valorConfirm = valorConfirm;
    }

    /**
     * @return the secuencialValidado
     */
    public Boolean getSecuencialValidado() {
        return secuencialValidado;
    }

    /**
     * @param secuencialValidado the secuencialValidado to set
     */
    public void setSecuencialValidado(Boolean secuencialValidado) {
        this.secuencialValidado = secuencialValidado;
    }

    /**
     * @return the descEstado
     */
    public String getDescEstado() {
        return descEstado;
    }

    /**
     * @param descEstado the descEstado to set
     */
    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    /**
     * @return the accionConfirm
     */
    public String getAccionConfirm() {
        return accionConfirm;
    }

    /**
     * @param accionConfirm the accionConfirm to set
     */
    public void setAccionConfirm(String accionConfirm) {
        this.accionConfirm = accionConfirm;
    }

    /**
     * @return the verPopupBusDoc
     */
    public Boolean getVerPopupBusDoc() {
        return verPopupBusDoc;
    }

    /**
     * @param verPopupBusDoc the verPopupBusDoc to set
     */
    public void setVerPopupBusDoc(Boolean verPopupBusDoc) {
        this.verPopupBusDoc = verPopupBusDoc;
    }

    /**
     * @return the valRadioBusDoc
     */
    public String getValRadioBusDoc() {
        return valRadioBusDoc;
    }

    /**
     * @param valRadioBusDoc the valRadioBusDoc to set
     */
    public void setValRadioBusDoc(String valRadioBusDoc) {
        this.valRadioBusDoc = valRadioBusDoc;
    }

    /**
     * @return the valBusquedaBusDoc
     */
    public String getValBusquedaBusDoc() {
        return valBusquedaBusDoc;
    }

    /**
     * @param valBusquedaBusDoc the valBusquedaBusDoc to set
     */
    public void setValBusquedaBusDoc(String valBusquedaBusDoc) {
        this.valBusquedaBusDoc = valBusquedaBusDoc;
    }

    /**
     * @return the codAreaUsuResp
     */
    public int getCodAreaUsuResp() {
        return codAreaUsuResp;
    }

    /**
     * @param codAreaUsuResp the codAreaUsuResp to set
     */
    public void setCodAreaUsuResp(int codAreaUsuResp) {
        this.codAreaUsuResp = codAreaUsuResp;
    }

    /**
     * @return the nomAreaUsuResp
     */
    public String getNomAreaUsuResp() {
        return nomAreaUsuResp;
    }

    /**
     * @param nomAreaUsuResp the nomAreaUsuResp to set
     */
    public void setNomAreaUsuResp(String nomAreaUsuResp) {
        this.nomAreaUsuResp = nomAreaUsuResp;
    }
}
