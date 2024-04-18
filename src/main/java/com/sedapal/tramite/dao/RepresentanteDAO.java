package com.sedapal.tramite.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.TiposPersonasBean;
import com.sedapal.tramite.beans.UsersBean;
import com.sedapal.tramite.beans.Usuario;




public class RepresentanteDAO implements IRepresentanteDAO , Serializable{
	
	private JdbcTemplate jdbcTemplate;
    private StoredRepresentante storedrepresentante;
    private StoredDeleteRepresentante storeddeleterepresentante;
    private StoredActualizarRepresentante storedactualizarrepresentante;
    private StoredNuevoRepresentante storednuevorepresentante;
	private static Logger logger = Logger.getLogger("R1");
   
    
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

    @Override
	public List<RepresentanteBean> representanteSP(int codigo) {
    	Map output = storedrepresentante.execute(codigo);
		List representante = (ArrayList)output.get("representante");
		return representante;
	}
    
    public List<TiposPersonasBean> tipos_persona(){
    	
		final List<TiposPersonasBean> results = new ArrayList<TiposPersonasBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO AS CODIGO,VDESCRIPCION AS DESCRIPCION FROM TIPO WHERE VOBSERVACIONES='TIPO REPRESENTANTE' AND VESTADO='A' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposPersonasBean bean2 = new TiposPersonasBean();
						bean2.setCodigo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});		
		return results;	
	}	
    
    public List<TiposDocumentosBean> tipos_documento(){
    	
		final List<TiposDocumentosBean> results1 = new ArrayList<TiposDocumentosBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO AS CODIGO,VDESCRIPCION AS DESCRIPCION FROM TIPO WHERE VOBSERVACIONES='TIPO DOCUMENTO' AND VESTADO='A' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposDocumentosBean bean2 = new TiposDocumentosBean();
						bean2.setCodigo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results1.add(bean2);
					}
				});		
		return results1;	
	}	
    
    @Override
	public void actualizarRepresentanteSP(RepresentanteBean representanteBean) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedactualizarrepresentante.execute(representanteBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_UPDATE_REPRESENTANTE Duracion:" + nowDif);
	}
    
   
    
    @Override
	public String nuevoSP(RepresentanteBean representanteBean) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
    	Map output = storednuevorepresentante.execute(representanteBean);
    	String out = (String) output.get("out");
    	nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_NUEVO_REPRESENTANTE Duracion:" + nowDif);
		return out;
		
	}
    
    @Override
	public void eliminarSP(int codremite, int codrepresentante) {		
		Map output = storeddeleterepresentante.execute(codremite,codrepresentante);		
	}
    
    public void setStoredRemitente(StoredRemitente storedRemitente) {
		//this.storedRemitente = storedRemitente;
	}
    
    @Override
	public List<RepresentanteBean> representante() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStoredrepresentante(StoredRepresentante storedrepresentante) {
		this.storedrepresentante = storedrepresentante;
	}

	public void setStoredactualizarrepresentante(StoredActualizarRepresentante storedactualizarrepresentante) {
		this.storedactualizarrepresentante = storedactualizarrepresentante;
	}

	public void setStorednuevorepresentante(StoredNuevoRepresentante storednuevorepresentante) {
		this.storednuevorepresentante = storednuevorepresentante;
	}

	public void setStoreddeleterepresentante(StoredDeleteRepresentante storeddeleterepresentante) {
		this.storeddeleterepresentante = storeddeleterepresentante;
	}

	

	
   

    
	
}
