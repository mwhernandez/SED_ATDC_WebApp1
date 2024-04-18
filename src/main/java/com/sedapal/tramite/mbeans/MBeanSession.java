package com.sedapal.tramite.mbeans;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

public class MBeanSession {

    public void eventSession(ActionEvent event) {
        ///acediendo a sesion http
        //HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        // Get the existing session. 
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();

        }


    }
}
