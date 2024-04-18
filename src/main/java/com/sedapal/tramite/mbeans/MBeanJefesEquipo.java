package com.sedapal.tramite.mbeans;

import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.beans.admin.JefeEquipoBean;
import com.sedapal.tramite.dao.JefesEquipoDAO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

public class MBeanJefesEquipo implements IMBeanJefesEquipo, Serializable {

    private JefesEquipoDAO jefesEquipoDAO;
    private int codAreaUsuResp;
    private String nomAreaUsuResp;
    private String usuResponsable;
    //Atributos de las paginas
    private Boolean verAlerta = false;
    private String msgAlerta;
    private Boolean verAlertaConfirm = false;
    private String msgAlertaConfirm;
    private Boolean valorConfirm = false;
    private String accionConfirm;
    //Mantenimiento   
    private String descArea;
    //Atributos de la clase
    private String codArea;
    private String apePaterno;
    private String apeMaterno;
    private String nombres;
    private String codFicha;
    
    

    public MBeanJefesEquipo() {
        System.out.println("Contructor MBeanJefesEquipo");
    }

    @PostConstruct
    public void postConstruct() {
        obtenerUsuario();
        reiniciarAtributos();
    }

    /**
     * ***********************************************
     */
    /**
     * ******** Metodos de Uso de la Pagina *********
     */
    /**
     * ***********************************************
     */
    private void reiniciarAtributos() {
        JefeEquipoBean beanJE = new JefeEquipoBean();
        beanJE = getJefesEquipoDAO().getJefeEquipoArea(getCodArea());

        this.setCodFicha(beanJE.getCodFicha());
        this.setApePaterno(beanJE.getApePaterno());
        this.setApeMaterno(beanJE.getApeMaterno());
        this.setNombres(beanJE.getNombres());
    }

    private void obtenerUsuario() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario beanUsuario = (Usuario) session.getAttribute("sUsuario");

        setUsuResponsable(beanUsuario.getLogin());
        setCodAreaUsuResp(beanUsuario.getCodarea());
        setNomAreaUsuResp(beanUsuario.getNomequipo());
        setCodArea(String.valueOf(beanUsuario.getCodarea()));
        setDescArea(beanUsuario.getNomequipo());
    }

    public void processActualizar(ActionEvent actionEvent) {
        showAlertConfirm("Esta seguro que desea actualizar el registro?", "updJE");
    }

    private void processConfirmActualizar() {
        Map resultMap = new HashMap();
        resultMap = getJefesEquipoDAO().updateJefeEquipoArea(getCodArea(), getCodFicha(), getApePaterno(), getApeMaterno(), getNombres(), getUsuResponsable());

        String tipMsgSalida = (String) resultMap.get("tipMsgSalida");
        String msgSalida = (String) resultMap.get("msgSalida");

        showAlert(msgSalida);

        if (tipMsgSalida.equals("OK")) {
            showAlert(msgSalida);
        } else {
            reiniciarAtributos();
            showAlert(msgSalida);
        }
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
        if (getAccionConfirm().equals("updJE")) {
            processConfirmActualizar();
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

    /**
     * *************************************
     */
    /**
     * ******** Metodos de Acceso *********
     */
    /**
     * *************************************
     */
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
     * @return the apePaterno
     */
    public String getApePaterno() {
        return apePaterno;
    }

    /**
     * @param apePaterno the apePaterno to set
     */
    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    /**
     * @return the apeMaterno
     */
    public String getApeMaterno() {
        return apeMaterno;
    }

    /**
     * @param apeMaterno the apeMaterno to set
     */
    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the codFicha
     */
    public String getCodFicha() {
        return codFicha;
    }

    /**
     * @param codFicha the codFicha to set
     */
    public void setCodFicha(String codFicha) {
        this.codFicha = codFicha;
    }

    /**
     * @return the jefesEquipoDAO
     */
    public JefesEquipoDAO getJefesEquipoDAO() {
        return jefesEquipoDAO;
    }

    /**
     * @param jefesEquipoDAO the jefesEquipoDAO to set
     */
    public void setJefesEquipoDAO(JefesEquipoDAO jefesEquipoDAO) {
        this.jefesEquipoDAO = jefesEquipoDAO;
    }
}
