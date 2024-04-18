package com.sedapal.tramite.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;





import com.sedapal.tramite.beans.CorrelativosBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.StoredTipos;



public class CorrelativosDAO implements ICorrelativosDAO, Serializable{
	
	private JdbcTemplate jdbcTemplate;
    private StoredListaCorrelativos storedlistacorrelativos;
    //private StoredNuevoTipos storednuevoTipos;
    private StoredActualizarCorrelativo storedactualizarcorrelativos;
    //private StoredDeleteTipos storeddeleteTipos;
    //private StoredFiltrosTipos storedFiltrosTipo;
    private static Logger logger = Logger.getLogger("R1");
    Calendar c = Calendar.getInstance();
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List<CorrelativosBean> correlativosSP() {
		Date date = new Date();
        String annio = Integer.toString(c.get(Calendar.YEAR));
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		String ncodarea = String.valueOf(usuario.getCodarea());
		Map output = storedlistacorrelativos.execute(annio, ncodarea, ncodarea);
		
		List correlativos = (ArrayList)output.get("correlativos");
		return correlativos;
	}
	
	public void actualizarTipoSP(CorrelativosBean correlativoBean) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storedactualizarcorrelativos.execute(correlativoBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_UPDATE_TIPOS_DOC Duracion:" + nowDif);
		
	}
	/*
	public String nuevoSP(TiposBean tiposBean) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storednuevoTipos.execute(tiposBean);
		String out = (String) output.get("out");
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_NUEVO_TIPO_DOCUMENTO Duracion:" + nowDif);
		return out;
	}
	
	public void eliminarSP(String codigo) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storeddeleteTipos.execute(codigo);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_DELETE_TIPO_DOCUMENTO Duracion:" + nowDif);
		
	}
	
	
	 public List<TiposBean> filtrosSP(String opcion, String detalle) {
			Map output = storedFiltrosTipo.execute(opcion, detalle);
			List tipos = (ArrayList)output.get("filtrostipos");
			return tipos;
		}
		
	*/
	
	
	
	
	@Override
	public List<CorrelativosBean> correlativos() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStoredlistacorrelativos(
			StoredListaCorrelativos storedlistacorrelativos) {
		this.storedlistacorrelativos = storedlistacorrelativos;
	}
	public void setStoredactualizarcorrelativos(
			StoredActualizarCorrelativo storedactualizarcorrelativos) {
		this.storedactualizarcorrelativos = storedactualizarcorrelativos;
	}
	@Override
	public void actualizarCorrelativosSP(CorrelativosBean correlativosBean) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	/*
	public void setStoredNuevo(StoredNuevoTipos storednuevoTipos) {
		this.storednuevoTipos = storednuevoTipos;
	}
	
	public void setStoredDeleteTipos(StoredDeleteTipos storeddeleteTipos) {
		this.storeddeleteTipos = storeddeleteTipos;
	}
	
	public void setStoredFiltrosTipo(StoredFiltrosTipos storedFiltrosTipo) {
		this.storedFiltrosTipo = storedFiltrosTipo;
	}
	public void setStoreddeleteTipos(StoredDeleteTipos storeddeleteTipos) {
		this.storeddeleteTipos = storeddeleteTipos;
	}
	
	public void setStorednuevoTipos(StoredNuevoTipos storednuevoTipos) {
		this.storednuevoTipos = storednuevoTipos;
	}
	
	*/
	
}
