package com.sedapal.tramite.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.StoredTipos;



public class AsuntoDAO implements IAsuntoDAO, Serializable{
	
	private JdbcTemplate jdbcTemplate;
    private StoredListaAsunto storedlistaasunto;
    private StoredNuevoAsunto storednuevoasunto;
    private StoredActualizarAsunto storedactualizarasunto;
    private StoredDeleteAsunto storeddeleteasunto;
    private StoredFiltrosAsunto storedfiltrosasunto;
    private static Logger logger = Logger.getLogger("R1");
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public List<TiposBean> asunto() {
		Map output = storedlistaasunto.execute();
		List asunto = (ArrayList)output.get("asunto");
		return asunto;
	}
	
	
	@Override
	public Map actualizarTipoSP(TiposBean tiposBean) {		
		Map output = storedactualizarasunto.execute(tiposBean);
		return output;
		
	}
	
	@Override
	public Map nuevoSP(TiposBean tiposBean) {				
		Map output = storednuevoasunto.execute(tiposBean);				
		return output;
	}
	
	public void eliminarSP(String codigo) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		String login = usuario.getLogin();
				
		Map output = storeddeleteasunto.execute(codigo, login);
		
				
	}
	 public List<TiposBean> filtrosSP(String opcion, String detalle) {
			Map output = storedfiltrosasunto.execute(opcion, detalle);
			List tipos = (ArrayList)output.get("busqueda_asunto");
			return tipos;
		}
	
	
	
	
	public void setStoredlistaasunto(StoredListaAsunto storedlistaasunto) {
		this.storedlistaasunto = storedlistaasunto;
	}
	public void setStorednuevoasunto(StoredNuevoAsunto storednuevoasunto) {
		this.storednuevoasunto = storednuevoasunto;
	}
	public void setStoredactualizarasunto(
			StoredActualizarAsunto storedactualizarasunto) {
		this.storedactualizarasunto = storedactualizarasunto;
	}
	public void setStoreddeleteasunto(StoredDeleteAsunto storeddeleteasunto) {
		this.storeddeleteasunto = storeddeleteasunto;
	}
	public void setStoredfiltrosasunto(StoredFiltrosAsunto storedfiltrosasunto) {
		this.storedfiltrosasunto = storedfiltrosasunto;
	}
	
	
	
	
	
	
	
}
