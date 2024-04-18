package com.sedapal.tramite.mbeans;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.icesoft.icefaces.tutorial.facelets.NavigationBean;
import com.icesoft.icefaces.tutorial.facelets.PageContentBean;
import com.sedapal.tramite.nova.util.RecursoReport;

public class PaisesImpl implements IPaises {

    private NavigationBean navigation;
    private RecursoReport reporte;
    private RecursoReport recursoReport;
    private Map<String, Object> parametros = new HashMap<String, Object>();
    ExternalContext ec = null;
    private String empresa;

    public void setNavigation(NavigationBean navigation) {
        this.navigation = navigation;
    }

    @PostConstruct
    public void init() {
        ec = FacesContext.getCurrentInstance().getExternalContext();
        reporte.asignar("ticket.pdf", ec, parametros, "report3.jasper");//se le puede adicionar parametros...
        recursoReport = reporte;
    }

    public void verArgentina(ActionEvent event) {

        PageContentBean pageContentBean = new PageContentBean();
        pageContentBean.setMenuContentInclusionFile("./content/SA/argentina.jspx");
        pageContentBean.setMenuContentTitle("Argentina");
        navigation.setSelectedPanel(pageContentBean);

    }

    public RecursoReport getRecursoReport() {
        //parametros.put("P_AREA", "313");
        //parametros.put("P_LIMITE", "10000");
        parametros.put("P_TITULO", this.empresa);
        //parametros.put("P_LIMITE", this.empresa);
        reporte.asignar("ticket.pdf", ec, parametros, "report3.japer");//se le puede adicionar parametros...
        recursoReport = reporte;
        return recursoReport;
    }

    public void setRecursoReport(RecursoReport recursoReport) {
        this.recursoReport = recursoReport;
    }

    public void setReporte(RecursoReport reporte) {
        this.reporte = reporte;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
