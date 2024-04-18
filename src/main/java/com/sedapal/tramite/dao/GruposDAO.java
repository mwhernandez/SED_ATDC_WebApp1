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



import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.StoredTipos;



public class GruposDAO implements IGruposDAO, Serializable{
	
	private JdbcTemplate jdbcTemplate;
    private StoredListaGrupos storedGrupos;
    private StoredNuevoGrupo storednuevoGrupos;
    private StoredActualizarGrupos storedactualizarGrupos;
    private StoredDeleteGrupos storeddeleteGrupos;
    private StoredFiltrosGrupos storedFiltrosGrupos;
    
    //private static Logger logger = Logger.getLogger("R1");
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List<GrupoBean> grupos() {
		Map output = storedGrupos.execute();
		List grupos = (ArrayList)output.get("grupos");
		return grupos;
	}
	
	
	public List<AreaBean> areas_seleccionadas(int codgrupo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO AREAS_SELECCIONADAS:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(				
				//"SELECT D.NCODAREA as codigo,upper(A.VDESCRIPCION) as nombre "+
				//"FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A "+
				//"WHERE  D.NANO="+anio+" AND D.NORIGEN="+origen+" AND D.VTIPODOC='"+tipodoc+"' AND D.NCORRELATIVO="+correlativo+" " +
				//"AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1 ORDER BY A.VABREVIATURA",
				"SELECT NCODDESTINATARIOS AS codigo, upper(VABREVIATURA)||' - '||upper(VDESCRIPCION) as nombre "+
				"FROM ATD_DESTINATARIOS WHERE NESTADO=1 AND NINDICADOR=1 AND NCODGRUPODESTINO="+codgrupo+" ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setNombre(rs.getString("nombre"));
						result.add(bean1);
					}
				});	
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO AREAS_SELECCIONADAS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO AREAS_SELECCIONADAS:" + nowDif);
		return result;
	}
	
	public List<AreaBean> areas(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO AREAS:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(
				//"SELECT A.NCODAREA as codigo, A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "+
				//"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.NCODAREA",
				//"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
				//"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.VABREVIATURA",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - '||upper(A.VDESCRIPCION) as nombre "+
				"FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P') ORDER BY A.VABREVIATURA",
				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));	
						bean1.setNombre(rs.getString("nombre"));											
						result.add(bean1);
					}
				});	
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO AREAS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO AREAS:" + nowDif);
		return result;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    public boolean updatedirigido(int codgrupo, int area, String responsable) {	
		  
		  String sql = "update ATD_DESTINATARIOS set NESTADO=2, VRESACT= '"+responsable+"' where NCODGRUPODESTINO= "+codgrupo+" AND NCODDESTINATARIOS = "+area+" ";		 
		  
		  int rs = jdbcTemplate.update(sql);
		  if (rs > 0)
		  return true;
		  return false;
	 }
	
	
	
	public void actualizarGruposSP(GrupoBean grupoBean) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();

		Map output = storedactualizarGrupos.execute(grupoBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_UPDATE_TIPOS_DOC Duracion:" + nowDif);
		
	}
	
	@Override
	public String nuevoGruposSP(GrupoBean grupoBean) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		

		Map output = storednuevoGrupos.execute(grupoBean);
		
		String out = (String) output.get("out");
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_NUEVO_TIPO_DOCUMENTO Duracion:" + nowDif);
		return out;
	}
	
	@Override
	public void eliminarGrupoSP(int codigo) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		//Map output = storeddeleteTipos.execute(codigo);
		Map output = storeddeleteGrupos.execute(codigo);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_DELETE_TIPO_DOCUMENTO Duracion:" + nowDif);
		
	}
	
	
	public List<GrupoBean> filtrosSP(String opcion, String detalle) {
			//Map output = storedFiltrosTipo.execute(opcion, detalle);
			Map output = storedFiltrosGrupos.execute(opcion, detalle);
			List grupos = (ArrayList)output.get("filtrosgrupos");
			return grupos;
	}
	
	
	
	public void setStoredGrupos(StoredListaGrupos storedGrupos) {
		this.storedGrupos = storedGrupos;
	}
		
	
	public void setStorednuevoGrupos(StoredNuevoGrupo storednuevoGrupos) {
		this.storednuevoGrupos = storednuevoGrupos;
	}
	public void setStoredactualizarGrupos(
			StoredActualizarGrupos storedactualizarGrupos) {
		this.storedactualizarGrupos = storedactualizarGrupos;
	}
	public void setStoreddeleteGrupos(StoredDeleteGrupos storeddeleteGrupos) {
		this.storeddeleteGrupos = storeddeleteGrupos;
	}
	public void setStoredFiltrosGrupos(StoredFiltrosGrupos storedFiltrosGrupos) {
		this.storedFiltrosGrupos = storedFiltrosGrupos;
	}
	
	
	
	
	
	
	
}
