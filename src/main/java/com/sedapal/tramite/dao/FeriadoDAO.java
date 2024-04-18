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

import com.sedapal.tramite.beans.FeriadosBean;
import com.sedapal.tramite.beans.PerfilBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;




public class FeriadoDAO implements IFeriadoDAO, Serializable{
	
	private JdbcTemplate jdbcTemplate;
    private StoredFeriado storedferiado;
    private StoredNuevoFeriado storednuevoferiado;
    private StoredActualizarFeriado storedactualizarferiado;
    private StoredDeleteFeriado Storeddeleteferiado;
    private StoredBusquedaFeriados storedBusquedaFeriados;
    private static Logger logger = Logger.getLogger("R1");
      
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

    @Override
	public List<FeriadosBean> feriadoSP() {
    	Map output = storedferiado.execute();
		List feriado = (ArrayList)output.get("feriado");
		return feriado;
	}
    
    @Override
	public void eliminarSP(int codigo) {
    	Map output = Storeddeleteferiado.execute(codigo);		
	}
    
    public List<FeriadosBean> filtrosSP(String opcion, String detalle) {
		Map output = storedBusquedaFeriados.execute(opcion, detalle);
		List feriado = (ArrayList)output.get("busquedaferiados");		
		return feriado;
	}
    
    public List<TiposBean> tipos() {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(				
				"select vcodtipo as codigo,vdescripcion as descripcion from tipo where vobservaciones='TIPO FERIADO' ORDER BY codigo",				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setTipo(rs.getString("codigo"));
						bean.setDescripcion(rs.getString("descripcion"));					
                        results.add(bean);
					}
				});
		return results;
	}	
   
    @Override
	public void nuevoSP(FeriadosBean feriadosBean) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storednuevoferiado.execute(feriadosBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_NUEVO_FERIADO Duracion:" + nowDif);
		
	}   
   
    @Override
	public void actualizarFeriadoSP(FeriadosBean feriadosBean) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedactualizarferiado.execute(feriadosBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_UPDATE_FERIADO Duracion:" + nowDif);
	}
    
    public void setStoredFeriado(StoredFeriado storedferiado) {
		this.storedferiado = storedferiado;
	}
    
	public void setStoredactualizarferiado(StoredActualizarFeriado storedactualizarferiado) {
		this.storedactualizarferiado = storedactualizarferiado;
	}

	public void setStorednuevoferiado(StoredNuevoFeriado storednuevoferiado) {
		this.storednuevoferiado = storednuevoferiado;
	}

	@Override
	public List<FeriadosBean> feriado() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStoredBusquedaFeriados(
			StoredBusquedaFeriados storedBusquedaFeriados) {
		this.storedBusquedaFeriados = storedBusquedaFeriados;
	}

	public void setStoreddeleteferiado(StoredDeleteFeriado storeddeleteferiado) {
		Storeddeleteferiado = storeddeleteferiado;
	}

	
	

	

	   
	
}
