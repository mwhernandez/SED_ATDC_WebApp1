package com.sedapal.tramite.mbeans;

import java.util.List;

import javax.faces.component.html.HtmlInputText;
import javax.faces.event.ActionEvent;

public class MPais {

    private boolean verDatos = false;
    private boolean verPanelColapsible = true;
    private boolean verPopup = false;
    private HtmlInputText inputPhone;

    public void eventDesactivarPhone(ActionEvent event) {
        inputPhone.setDisabled(true);

    }

    public HtmlInputText getInputPhone() {
        return inputPhone;
    }

    public void setInputPhone(HtmlInputText inputPhone) {
        this.inputPhone = inputPhone;
    }

    public void eventImprimir(ActionEvent event) {
        this.verPopup = true;
    }

    public void evento(ActionEvent event) {
        this.verPopup = false;
    }

    public List getUsuarios() {
        return null;
    }

    public boolean isVerPopup() {
        return verPopup;
    }

    public void setVerPopup(boolean verPopup) {
        this.verPopup = verPopup;
    }

    public boolean isVerDatos() {
        return verDatos;
    }

    public void setVerDatos(boolean verDatos) {
        this.verDatos = verDatos;
    }

    public boolean isVerPanelColapsible() {
        return verPanelColapsible;
    }

    public void setVerPanelColapsible(boolean verPanelColapsible) {
        this.verPanelColapsible = verPanelColapsible;
    }
}