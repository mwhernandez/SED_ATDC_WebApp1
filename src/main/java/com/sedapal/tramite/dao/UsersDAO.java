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
import com.sedapal.tramite.beans.PerfilBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.UsersBean;
import com.sedapal.tramite.beans.Usuario;



public class UsersDAO implements IUsersDAO , Serializable{
	
	private JdbcTemplate jdbcTemplate;
    private StoredUsers storedUsers;
    private StoredFiltros storedFiltros;
    private StoredActualizarUsers storedactualizarUsers;
    private StoredDeleteUsers storeddeleteUsers;
    private StoredNuevoUsers storednuevousers;
    private StoredBusquedaUser storedbusquedauser;
	private static Logger logger = Logger.getLogger("R1");
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
    
	//@Override
	public List<UsersBean> usersSP() {
		Map output = storedUsers.execute();
		List users = (ArrayList)output.get("users");
		return users;
	}
	
	public List<PerfilBean> perfiles() {
		final List<PerfilBean> results = new ArrayList<PerfilBean>();
		jdbcTemplate.query(				
				"SELECT NCODPERFIL CODIGO,VDESCRIPCION NOMBRE FROM PERFIL_SISTEMA WHERE VESTADO='A' AND NCODSISTEMA = 30 ORDER BY CODIGO",				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						PerfilBean bean = new PerfilBean();
						bean.setCodigo(rs.getInt("codigo"));
						bean.setNombre(rs.getString("nombre"));					
                        results.add(bean);
					}
				});
		return results;
	}	
	
	public List<AreaBean> areas(){
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(
				//"SELECT NCODAREA as codigo,VDESCRIPCION as nombre FROM AREA WHERE NESTADO=0 AND CTIPAREA='E' ORDER BY CODIGO",
				//"SELECT NCODAREA as codigo,NCODAREA||'-'||UPPER(VDESCRIPCION) as nombre FROM AREA WHERE NESTADO=0 AND CTIPAREA='E' ORDER BY NCODAREA",
				//"SELECT A.NCODAREA as codigo, A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "+
				//"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.NCODAREA",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
				"FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P','M') ORDER BY A.VABREVIATURA",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setNombre(rs.getString("nombre"));
						result.add(bean1);
					}
				});
		return result;
		
	}
	
	
	
	public void setStoredUsers(StoredUsers storedUsers) {
		this.storedUsers = storedUsers;
	}

	//@Override
	public List<UsersBean> users() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarUsersSP(UsersBean usersBean) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storedactualizarUsers.execute(usersBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_UPDATE_USERS Duracion:" + nowDif);
		
	}
	
	public List<UsersBean> filtrosSP(String fecha1, String fecha2, String perfil) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storedFiltros.execute(fecha1, fecha2, perfil);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_FILTRO_USUARIO Duracion:" + nowDif);
		
		List users = (ArrayList)output.get("filtros");
		return users;
	}
	
	public List<UsersBean> busquedauserSP(String opcion, String detalle) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storedbusquedauser.execute(opcion, detalle);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_BUSQUEDA_USER Duracion:" + nowDif);
		
		List users = (ArrayList)output.get("busquedauser");
		return users;
	}

	@Override
	public void eliminarSP(String codigo) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storeddeleteUsers.execute(codigo);		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_DELETE_USERS Duracion:" + nowDif);
		
		
	}

	@Override
	public void nuevoSP(UsersBean usersBean) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storednuevousers.execute(usersBean);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_NUEVO_USERS Duracion:" + nowDif);
		
		
		
	}

	public void setStoredFiltros(StoredFiltros storedFiltros) {
		this.storedFiltros = storedFiltros;
	}
	
	
	
    public void setStoredbusquedauser(StoredBusquedaUser storedbusquedauser) {
		this.storedbusquedauser = storedbusquedauser;
	}

	public void setStoredActualizarUsers(StoredActualizarUsers storedactualizarUsers) {
		this.storedactualizarUsers = storedactualizarUsers;
	}

    public void setStoredDeleteUsers(StoredDeleteUsers storeddeleteUsers) {
		this.storeddeleteUsers = storeddeleteUsers;
	}
	
    public void setStoredNuevo(StoredNuevoUsers storednuevousers) {
		this.storednuevousers = storednuevousers;
	}
    
    
	
}
