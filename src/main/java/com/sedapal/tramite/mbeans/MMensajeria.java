package com.sedapal.tramite.mbeans;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.icesoft.faces.async.render.IntervalRenderer;
import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.context.DisposableBean;
import com.icesoft.faces.webapp.xmlhttp.FatalRenderingException;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;
import com.icesoft.icefaces.tutorial.facelets.NavigationBean;
import com.icesoft.icefaces.tutorial.facelets.PageContentBean;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.DocumentoSalidaDAO;
import com.sedapal.tramite.dao.EntranteDAO;
import com.sedapal.tramite.dao.SeguimientoEntranteDAO;
import com.sedapal.tramite.servicios.IServiciosUsuarios;

public class MMensajeria implements Renderable, DisposableBean, Serializable {

    private static final long serialVersionUID = 1L;
    private String imagen;
    public String mensaje;
    public boolean verPopup = false;
    private int count = 0;
    //inject
    private NavigationBean navigation;
    private EntranteDAO entranteDAO;
    private String grupo;
    Logger logger = Logger.getLogger("R1");
    ResourceBundle bundle = ResourceBundle.getBundle("com.sedapal.tramite.files.parametros");
    //minutos
    private final long renderInterval = Integer.parseInt(bundle.getString("refresh")) * 1000 * 60;
    /**
     * The state associated with the current user that can be used for
     * server-initiated render calls.
     */
    private PersistentFacesState state;
    /**
     * A named render group that can be shared by all TimeZoneBeans for
     * server-initiated render calls. Setting the interval determines the
     * frequency of the render call.
     */
    private IntervalRenderer clock;
    public RenderManager renderManager;
    public MBeanEntrantes mBeanEntrantes;
    long nowIn = 0, nowOut = 0, nowDif = 0;
    private IServiciosUsuarios serviciosUsuarios;
    
    private Date fecha = new Date();
    
    // sed-req-00037
    private SeguimientoEntranteDAO seguimientoEntranteDAO;
    private DocumentoSalidaDAO documentoSalidaDAO;
    private List<EntranteBean> documentos;
    private boolean verDocumentos;

    public void setRenderManager(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    public MMensajeria() {
        init();
    }

    /**
     * Initializes this MonitorSwitchBean's properties.
     */
    @PostConstruct
    private void init() {

        state = PersistentFacesState.getInstance();
        nowIn = System.currentTimeMillis();
    }

    @PostConstruct
    public void initClock() {
        clock = renderManager.getIntervalRenderer("clock");
        clock.setInterval(renderInterval);
        clock.add(this);
        clock.requestRender();
    }
    
    public void eventAcuse(EntranteBean event) {
    	DocumentoSalidaBean doc = this.seguimientoEntranteDAO.getDocSalByNumDoc(event.getVnumdoc(), false);
    	if (doc == null || doc.getCodigo() == 0) {
			doc = this.seguimientoEntranteDAO.getDocSalByRegistroEntrada(event.getNcorrelativo() + "-" + event.getNano(), event.getVnumdoc(), false);
		}
    	
    	if (doc != null && doc.getCodigo() != 0) {
    		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Usuario usuario = null;
            usuario = (Usuario) session.getAttribute("sUsuario");
            
    		System.out.println("Acuse documento... " + doc.getCodigo() +  " - " + doc.getOrigen() + " - " + doc.getAno() + " - " + doc.getTipodoc() + " - " + doc.getArea() + 
    		 " - " + usuario.getLogin());
        	
    		this.documentoSalidaDAO.guardarAcuseRecibo(doc.getCodigo(), doc.getAno(), String.valueOf(doc.getOrigen()), doc.getTipodoc(), 
    				String.valueOf(doc.getArea()), usuario.getLogin(), true, 0, 0, 0);
    		event.setAcuse(true);
    	}
    }
    
    public void eventVerEntrantesV2(ActionEvent event) {
    	this.verDocumentos = true;
    }

    public void eventVerEntrantes(ActionEvent event) {

        this.verPopup = false;
        //refrescando...
        mBeanEntrantes.eventRefrescar();
        PageContentBean pageContentBean = new PageContentBean();
        System.out.println("Grupo");
        System.out.println(grupo);
        if (grupo.equals("15")) {
            pageContentBean.setMenuContentInclusionFile("./content/DE/doc_entrante_grupo.jspx");
        }
        if (grupo.equals("6")) {
            pageContentBean.setMenuContentInclusionFile("./content/DE/doc_entrante_grupo.jspx");
        }
        if (grupo.equals("10")) {
            pageContentBean.setMenuContentInclusionFile("./content/DE/doc_entrante_gerencias.jspx");
        }
        if (grupo.equals("2") || grupo.equals("1")) {
            pageContentBean.setMenuContentInclusionFile("./content/DE/doc_entrante.jspx");
        }
        if (grupo.equals("8")) {
            pageContentBean.setMenuContentInclusionFile("./content/DE/doc_derivado.jspx");
        }
        if (grupo.equals("7")) {
            pageContentBean.setMenuContentInclusionFile("./content/DE/doc_derivado.jspx");
        }

        if (grupo.equals("3")) {
            pageContentBean.setMenuContentInclusionFile("./content/DE/doc_entrante_consulta.jspx");
        }
        
        //pageContentBean.setMenuContentTitle("Ingreso Doc. Entrantes");
        navigation.setSelectedPanel(pageContentBean);

    }

    public void eventCancelar(ActionEvent event) throws UnknownHostException {    		
        this.verPopup = false;
        
    }

    /**
     * Gets RenderManager
     *
     * @return RenderManager null
     */
    public RenderManager getRenderManager() {
        return null;
    }

    //
    // Renderable interface
    //
    /**
     * Gets the current instance of PersistentFacesState
     *
     * @return PersistentFacesState state
     */
    public PersistentFacesState getState() {
        return state;
    }

    /**
     * Callback to inform us that there was an Exception while rendering
     *
     * @param renderingException
     */
    public void renderingException(RenderingException renderingException) {
        if (renderingException instanceof FatalRenderingException) {
            performCleanup();
        }
    }

    protected boolean performCleanup() {
        System.out.println("PERFORM CLEANUP!!!");
        try {
            if (clock != null) {
                clock.remove(this);
                if (clock.isEmpty()) {
                    clock.dispose();
                }
                clock = null;
            }
            return true;
        } catch (Exception failedCleanup) {
            failedCleanup.printStackTrace();
        }
        return false;
    }

    public void dispose() throws Exception {
        performCleanup();

    }

    public String getMensaje() {
        nowOut = System.currentTimeMillis();
        nowDif = nowOut - nowIn;
        System.out.println("Count:" + count);
        System.out.println("secs:" + nowDif);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        if (count == 0) {
            logger.debug("[MMensajeria]Llamando primera vez a dao Mensajes");
            grupo = this.entranteDAO.confirmaGrupo();
            String date = sdf.format(new Date());
            String num = this.entranteDAO.entrantesNuevos(grupo, date);
            logger.debug("[MMensajeria]Mensajes encontrados:" + num);
            if (Integer.parseInt(num) > 0) {
            	documentos = this.entranteDAO.obtenerEntrantesNuevos(grupo, date, Integer.parseInt(sdfYear.format(new Date())));
                logger.debug("[MMensajeria]Mensaje Mostrando");
                this.mensaje = "Usted tiene (" + num + ") nuevos documentos";
                this.verPopup = true;
            }
            nowIn = System.currentTimeMillis();
        }
        if (nowDif > renderInterval - 3000)//margen de error
        {
            System.out.println("Llamando a dao");
            grupo = this.entranteDAO.confirmaGrupo();
            String num = this.entranteDAO.entrantesNuevos(grupo, sdf.format(new Date()));
            if (Integer.parseInt(num) > 0) {
            	this.mensaje = "Usted tiene (" + num + ") nuevos mensajes";
                this.verPopup = true;
            }
            count = 1;
            nowIn = System.currentTimeMillis();
        }
        count++;
        return mensaje;
    }
    
    

    public boolean isVerPopup() {
        return verPopup;
    }

    public void setVerPopup(boolean verPopup) {
        this.verPopup = verPopup;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setNavigation(NavigationBean navigation) {
        this.navigation = navigation;
    }

    public void setmBeanEntrantes(MBeanEntrantes mBeanEntrantes) {
        this.mBeanEntrantes = mBeanEntrantes;
    }

    public void setEntranteDAO(EntranteDAO entranteDAO) {
        this.entranteDAO = entranteDAO;
    }

	public List<EntranteBean> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<EntranteBean> documentos) {
		this.documentos = documentos;
	}

	public boolean isVerDocumentos() {
		return verDocumentos;
	}

	public void setSeguimientoEntranteDAO(SeguimientoEntranteDAO seguimientoEntranteDAO) {
		this.seguimientoEntranteDAO = seguimientoEntranteDAO;
	}

	public void setDocumentoSalidaDAO(DocumentoSalidaDAO documentoSalidaDAO) {
		this.documentoSalidaDAO = documentoSalidaDAO;
	}
    
}
