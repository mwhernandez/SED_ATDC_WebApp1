package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.SeguimientoBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.ISeguimientoEntranteDAO;

public class SeguimientoEntranteDAO implements ISeguimientoEntranteDAO{
	
	private JdbcTemplate jdbcTemplate;
	private StoredSeguimientoEntrate Storedseguimientoentrante;	
	private StoredActualizarSeguimiento Storedactualizarseguimiento;
	private StoredBusquedaSegDocEntrantes Storedbusquedasegdocentrantes;
	private StoredFiltrosSegEnt storedfiltrossegent;
	private StoredBuscarSeguimientoEntraten Storedbuscarseguimientoentranten;
	private StoredNuevoSeguimiento storednuevoseguimiento;
	private StoredEliminarSeguimiento storedeliminarseguimiento;
	private StoredDerivado storedDerivado;
	
	//private static Logger logger = Logger.getLogger("R1");
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public List<SeguimientoEntranteBean> seguimientoSP(int anio, int origenes, String tipodocumentos, long correlativos, String area) {
		///acediendo a sesion http
		//, String derivado
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		//String area;
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		area=String.valueOf(usuario.getCodarea());
		//derivado=String.valueOf(usuario.getFicha());
		Map output = Storedseguimientoentrante.execute(anio, origenes, tipodocumentos, correlativos, area);
		//derivado
		List seguimiento = (ArrayList)output.get("seguimiento");
		return seguimiento;
	}
	

	public List<TiposBean> tipos(){
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SEGUIMIENTODAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		String sql="SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' " +
				"AND VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VCODTIPO";
		//logger.debug("[SeguimientoDAO.areas]["+usuario+"]"+sql);
		Date inicio = new Date();
		//logger.debug("[EntranteDAO.areas]["+usuario+"]Inicio sql:" + inicio);
		jdbcTemplate.query(sql,new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setTipo(rs.getString("codigo"));
						bean.setDescripcion(rs.getString("descripcion"));
						results.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
        //logger.debug("[SEGUIMIENTODAO.TIPOS]["+usuario+"]"+"Final SEGUIMIENTODAO AREA:" + nowOut);
        nowDif = nowOut - nowIn;
        //logger.debug("[SEGUIMIENTODAO.TIPOS]["+usuario+"]"+"Duracion SEGUIMIENTODAO AREA:" + nowDif);
		return results;
		
	}
	
	public List<TrabajadorBean> trabajador(int area){
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SEGUIMIENTODAO TRABAJADOR:" + nowIn);
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();
		
		/*		Eli Comenta para no duplicar el Jefe de Equipo
		String sql="select ficha, nombre from (select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','|| VNOMBRES AS nombre " + 
					"from trabajador where vcodestado IN ('EIRC01','EIRC03') and ncodarea= "+area+" " +
					"union select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','|| VNOMBRES AS nombre "+
					"from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= "+area+") order by nombre";
					*/
		
		String sql="select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','|| VNOMBRES AS nombre " + 
		"from trabajador where vcodestado IN ('EIRC01') and IND_EMAIL IN (0,1) and ncodarea= "+area+" " +
		"order by nombre";
		
				//logger.debug("[SEGUIMIENTODAO.TRABAJADOR]["+usuario+"]"+sql);
				Date inicio = new Date();
				//logger.debug("[SEGUIMIENTODAO.TRABAJADOR]["+usuario+"]Inicio sql:" + inicio);
				jdbcTemplate.query(sql,new RowCallbackHandler() {				
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean2 = new TrabajadorBean();
						bean2.setFicha(rs.getLong("ficha"));
						bean2.setNombre_completo(rs.getString("nombre"));						
						results1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		//logger.debug("[SEGUIMIENTODAO.TRABAJADOR]["+usuario+"]"+"Final SEGUIMIENTODAO TRABAJADOR:" + nowOut);
		nowDif = nowOut - nowIn;
		//logger.debug("[SEGUIMIENTODAO.TRABAJADOR]["+usuario+"]"+"Duracion SEGUIMIENTODAO TRABAJADOR:" + nowDif);		
		return results1;
		
	}
	
	
	public List<TrabajadorBean> trabajador_nuevo(int area){
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SEGUIMIENTODAO TRABAJADOR:" + nowIn);
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();
		
		/*		Eli Comenta para no duplicar el Jefe de Equipo
		String sql="select ficha, nombre from (select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','|| VNOMBRES AS nombre " + 
					"from trabajador where vcodestado IN ('EIRC01','EIRC03') and ncodarea= "+area+" " +
					"union select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','|| VNOMBRES AS nombre "+
					"from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= "+area+") order by nombre";
					*/
		
		String sql="select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','|| VNOMBRES AS nombre " + 
		"from trabajador where vcodestado IN ('EIRC01') and IND_EMAIL in (0,1) and ncodarea= "+area+" " +
		"order by nombre";
		
				//logger.debug("[SEGUIMIENTODAO.TRABAJADOR]["+usuario+"]"+sql);
				Date inicio = new Date();
				//logger.debug("[SEGUIMIENTODAO.TRABAJADOR]["+usuario+"]Inicio sql:" + inicio);
				jdbcTemplate.query(sql,new RowCallbackHandler() {				
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean2 = new TrabajadorBean();
						bean2.setFicha(rs.getLong("ficha"));
						bean2.setNombre_completo(rs.getString("nombre"));						
						results1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		//logger.debug("[SEGUIMIENTODAO.TRABAJADOR]["+usuario+"]"+"Final SEGUIMIENTODAO TRABAJADOR:" + nowOut);
		nowDif = nowOut - nowIn;
		//logger.debug("[SEGUIMIENTODAO.TRABAJADOR]["+usuario+"]"+"Duracion SEGUIMIENTODAO TRABAJADOR:" + nowDif);		
		return results1;
		
	}
	
	
	public List<AreaBean> area_derivado(){		
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        
		final List<AreaBean> result4 = new ArrayList<AreaBean>();		
		String sql="SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA) as nombre "+
				"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E','G', 'D','P') ORDER BY A.VABREVIATURA";
				
				Date inicio = new Date();
				jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean4 = new AreaBean();
						bean4.setCodigo(rs.getInt("codigo"));
						bean4.setNombre(rs.getString("nombre"));
						result4.add(bean4);
					}
				});		
		return result4;
	}
	
	public List<AreaBean> area_remitente(){		
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");       
        
		final List<AreaBean> result5 = new ArrayList<AreaBean>();
		String sql="SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA) as nombre "+
				"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E','G', 'D','P') ORDER BY A.VABREVIATURA";
		
		/*
		String sql ="SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+ 
		"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P') AND NESTADO IN (0,4) "+		
		"UNION "+
		"SELECT G.NCODGRUPODESTINO as codigo, upper(G.VDESCRIPCION) as nombre "+ 
		"FROM ATD_GRUPO_DESTINATARIOS G WHERE G.NESTADO=1 AND G.NINDICADOR=1 ";
		*/

				Date inicio = new Date();

				jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean5 = new AreaBean();
						bean5.setCodigo(rs.getInt("codigo"));
						bean5.setNombre(rs.getString("nombre"));
						result5.add(bean5);
					}
				});		
		
		return result5;
	}
	
	public List<RemitenteBean> remitentesA(int codigo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO REMITENTE:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(				
				//"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				//"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY VDESCRIPCION",
				"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 AND NCODREMITENTE="+codigo+" ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO AREA_SELECCIONADAS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO AREA_SELECCIONADAS:" + nowDif);
		return result1;
	}  
	
	public List<TrabajadorBean> trabajador_remitente(int area){
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SEGUIMIENTODAO TRABAJADOR REMITENTE:" + nowIn);
		final List<TrabajadorBean> results6 = new ArrayList<TrabajadorBean>();
		
		//String sql="select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','||VNOMBRES AS nombre " +
		//		"from trabajador where vcodestado='EIRC01' and ncodarea= "+area+"order by VAPEPATERNO";
		String sql="select ficha, nombre from (select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','|| VNOMBRES AS nombre " + 
		"from trabajador where vcodestado IN ('EIRC01') and ind_email in (0,1) and ncodarea= "+area+" union select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','|| VNOMBRES AS nombre "+
		"from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= "+area+") order by nombre";
		
		
				//logger.debug("[SEGUIMIENTODAO.TRABAJADOR_REMITENTE]["+usuario+"]"+sql);
				Date inicio = new Date();
				//logger.debug("[SEGUIMIENTODAO.TRABAJADOR_REMITENTE]["+usuario+"]Inicio sql:" + inicio);
				jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean6 = new TrabajadorBean();
						bean6.setFicha(rs.getLong("ficha"));
						bean6.setNombre_completo(rs.getString("nombre"));						
						results6.add(bean6);
					}
				});
		nowOut = System.currentTimeMillis();
		//logger.debug("[SEGUIMIENTODAO.TRABAJADOR_REMITENTE]["+usuario+"]"+"Final SEGUIMIENTODAO TRABAJADOR REMITENTE:" + nowOut);
		nowDif = nowOut - nowIn;
		//logger.debug("[SEGUIMIENTODAO.TRABAJADOR_REMITENTE]["+usuario+"]"+"Duracion SEGUIMIENTODAO TRABAJADOR REMITENTE:" + nowDif);
		return results6;
		
	}
	
	public List<RemitenteBean> remitentes(){
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SEGUIMIENTODAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result7 = new ArrayList<RemitenteBean>();
		
		String sql="SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY CODIGO";
				//logger.debug("[SEGUIMIENTODAO.REMITENTES]["+usuario+"]"+sql);
				Date inicio = new Date();
				//logger.debug("[SEGUIMIENTODAO.REMITENTES]["+usuario+"]Inicio sql:" + inicio);
				jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean7 = new RemitenteBean();
						bean7.setCodigo(rs.getInt("codigo"));
						bean7.setDescripcion(rs.getString("nombre"));
						result7.add(bean7);
					}
				});
		nowOut = System.currentTimeMillis();
		//logger.debug("[SEGUIMIENTODAO.REMITENTES]["+usuario+"]"+"Final SEGUIMIENTODAO REMITENTES:" + nowOut);
		nowDif = nowOut - nowIn;
		//logger.debug("[SEGUIMIENTODAO.REMITENTES]["+usuario+"]"+"Duracion SEGUIMIENTODAO REMITENTES:" + nowDif);
		return result7;
	}    
	
	public List<OrigenBean> origen(){
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SEGUIMIENTODAO ORIGEN:" + nowIn);
		final List<OrigenBean> results8 = new ArrayList<OrigenBean>();
		
		String sql="SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' AND VOBSERVACIONES='ORIGEN ATD' ORDER BY VCODTIPO";
				//logger.debug("[SEGUIMIENTODAO.ORIGEN]["+usuario+"]"+sql);
				Date inicio = new Date();
				//logger.debug("[SEGUIMIENTODAO.ORIGEN]["+usuario+"]Inicio sql:" + inicio);
				jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						OrigenBean bean8 = new OrigenBean();
						bean8.setCodigo(rs.getString("codigo"));
						bean8.setDescripcion(rs.getString("descripcion"));
						results8.add(bean8);
					}
				});
		nowOut = System.currentTimeMillis();
		//logger.debug("[SEGUIMIENTODAO.ORIGEN]["+usuario+"]"+"Final SEGUIMIENTODAO ORIGEN :" + nowOut);
		nowDif = nowOut - nowIn;
		//logger.debug("[SEGUIMIENTODAO.ORIGEN]["+usuario+"]"+"Duracion SEGUIMIENTODAO ORIGEN :" + nowDif);
		return results8;
		
	}
	
	public List<ServidorBean> servidorsegumiento(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO SERVIDOR:" + nowIn);
		final List<ServidorBean> results3 = new ArrayList<ServidorBean>();
		jdbcTemplate.query(
				"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=8",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ServidorBean bean4 = new ServidorBean();						
						bean4.setDescripcion(rs.getString("descripcion"));
						results3.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO SERVIDOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO SERVIDOR:" + nowDif);
		return results3;	
	}
	
	public List<ServidorBean> servidorsalida(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO SERVIDOR:" + nowIn);
		final List<ServidorBean> results9 = new ArrayList<ServidorBean>();
		jdbcTemplate.query(
				//"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=2",
				"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=6",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ServidorBean bean9 = new ServidorBean();						
						bean9.setDescripcion(rs.getString("descripcion"));
						results9.add(bean9);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO SERVIDOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO SERVIDOR:" + nowDif);
		return results9;	
	}	
	
	public List<RepresentanteBean> representantes(int representante){
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SEGUIMIENTODAO REPRESENTANTES:" + nowIn);
		final List<RepresentanteBean> result9 = new ArrayList<RepresentanteBean>();
		String sql="SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS nombre FROM ATD_REPRESENTANTE " + 
				"WHERE VESTADO = 'A' AND NINDICADOR = 1 AND NCODREMITENTE= "+representante+" ORDER BY CODIGO";
				//logger.debug("[SEGUIMIENTODAO.REPRESENTANTES]["+usuario+"]"+sql);
				Date inicio = new Date();
				//logger.debug("[SEGUIMIENTODAO.REPRESENTANTES]["+usuario+"]Inicio sql:" + inicio);
				jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RepresentanteBean bean9 = new RepresentanteBean();
						bean9.setCodrepresentante(rs.getInt("codigo"));
						bean9.setVnombre(rs.getString("nombre"));
						result9.add(bean9);
					}
				});
		nowOut = System.currentTimeMillis();
		//logger.debug("[SEGUIMIENTODAO.REPRESENTANTES]["+usuario+"]"+"Final SEGUIMIENTODAO REPRESENTANTES :" + nowOut);
		nowDif = nowOut - nowIn;
		//logger.debug("[SEGUIMIENTODAO.REPRESENTANTES]["+usuario+"]"+"Duracion SEGUIMIENTODAO REPRESENTANTES :" + nowDif);
		return result9;
	}  
	
	public List<ServidorBean> servidor(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO SERVIDOR:" + nowIn);
		final List<ServidorBean> results3 = new ArrayList<ServidorBean>();
		jdbcTemplate.query(
				//"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=4",
				"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=8",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ServidorBean bean4 = new ServidorBean();						
						bean4.setDescripcion(rs.getString("descripcion"));
						results3.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO SERVIDOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO SERVIDOR:" + nowDif);
		return results3;	
	}	
	
	public List<JefeBean> max_derivado(long ncorrelativo, int area){
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SEGUIMIENTODAO MAXIMO DERIVADO:" + nowIn);
        
		final List<JefeBean> result5 = new ArrayList<JefeBean>();
		String sql="SELECT NDERIVADO AS ficha FROM ATD_DOC_ENTR_SEG WHERE  NCORRELATIVO= "+ncorrelativo+" AND " +
				"NCODSEG IN (SELECT MAX(NCODSEG) FROM ATD_DOC_ENTR_SEG WHERE NCORRELATIVO= "+ncorrelativo+" AND VESTADO<>'ESTA004' AND NAREADERIVADO= "+area+")";
		
				//logger.debug("[SEGUIMIENTODAO.MAXIMO DERIVADO]["+usuario+"]"+sql);
				Date inicio = new Date();
				//logger.debug("[SEGUIMIENTODAO.MAXIMO DERIVADO]["+usuario+"]Inicio sql:" + inicio);
				jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						JefeBean bean6 = new JefeBean();						
						bean6.setFicha(rs.getInt("ficha"));
						result5.add(bean6);
					}
				});
		nowOut = System.currentTimeMillis();
		//logger.debug("[SEGUIMIENTODAO.AREA REMITENTE]["+usuario+"]"+"Final SEGUIMIENTODAO AREA REMITENTE:" + nowOut);
		nowDif = nowOut - nowIn;
		//logger.debug("[SEGUIMIENTODAO.AREA REMITENTE]["+usuario+"]"+"Duracion SEGUIMIENTODAO AREA REMITENTE:" + nowDif);
		return result5;
	}
	
	public List<JefeBean> jefe(int area){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO JEFE:" + nowIn);
		final List<JefeBean> results9 = new ArrayList<JefeBean>();
		jdbcTemplate.query(								
				"select nficha as ficha from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= "+area+" ",				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						JefeBean bean = new JefeBean();
						bean.setFicha(rs.getInt("ficha"));						
						results9.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO JEFE:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO JEFE:" + nowDif);
		return results9;	
	}	
	
	public List<LlavesBean> verifica_dirigido(int anio, int origen, String tipodoc, long correlativo, int area){		
        
		final List<LlavesBean> result8 = new ArrayList<LlavesBean>();
		String sql="select count(ncorrelativo) as valor from atd_doc_entr_dirigido where nano= "+anio+" and " +
		"norigen= "+origen+" and vtipodoc= '"+tipodoc+"' and ncorrelativo= "+correlativo+" and ncodarea= "+area+" ";
		
				jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						LlavesBean bean6 = new LlavesBean();
						bean6.setVerifica(rs.getInt("valor"));
						result8.add(bean6);
					}
				});
		
		return result8;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
	public void InsertDerivado(int anio, int origen, String tipodoc, long correlativo, int area, long ficha) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
    	Date fechacre = new Date(); 
    	Date fechaact = new Date(); 
    	String respcre = usuario.getLogin(); 
    	String respact = usuario.getLogin();
    	String area_origen = String.valueOf(usuario.getNcodarea());
    	int estado=1;
    	String sql = "insert into atd_doc_entr_dirigido (nano ,norigen, vtipodoc, ncorrelativo, ncodarea, dfeccre, dfecact, vrescre, vresact , nficha_dirigido, nestado, ncodarea_origen)"
    		+ " values(?,?,?,?,?,?,?,?,?,?,?,?)";
    	 Object[] parametros = new Object[] { anio, origen, tipodoc, correlativo, area, fechacre, fechaact, respcre, respact, ficha, estado, area_origen};
		 int rs = jdbcTemplate.update(sql, parametros);
		 
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
	public boolean UpdateDerivado(long correlativo, int area, long ficha) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
    	Date fechacre = new Date();    	
    	String respact = usuario.getLogin(); 
    	int area_origen = usuario.getCodarea();
		String sql = "update atd_doc_entr_dirigido set nestado=1, nficha_dirigido="+ficha+", vresact='"+respact+"', ncodarea_origen="+area_origen+" where ncorrelativo= " +correlativo+" and  ncodarea="+area+"";		 
		  
		  int rs = jdbcTemplate.update(sql);
		  if (rs > 0)
		  return true;
		  return false;
	 }
	
	@Override
	public void storedDerivado(int anio, int origen, String tipodoc, long correlativo, int area, String login, long ficha) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedDerivado.execute(anio, origen, tipodoc, correlativo, area, login, ficha);
		//int anio, int origen, String tipodoc, long correlativo, int area, String login, long ficha
		// TODO Auto-generated method stub
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_NUEVO_REMITENTE_DOC Duracion:" + nowDif);
		
	}
	
	public void setStoredseguimientoentrante(StoredSeguimientoEntrate Storedseguimientoentrante) {
		this.Storedseguimientoentrante = Storedseguimientoentrante;
		
		}


	@Override
	public String actualizarSeguimientoSP(SeguimientoEntranteBean seguimientoBean) {
		// TODO Auto-generated method stub		
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = Storedactualizarseguimiento.execute(seguimientoBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_UPDATE_SEGUIMIENTO Duracion:" + nowDif);
		String out = (String) output.get("out");
		return out;
	}
	
	
	
	@Override
	public String nuevoSPSeguimiento(SeguimientoEntranteBean seguimientoBean) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storednuevoseguimiento.execute(seguimientoBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_NUEVO_SEGUIMIENTO Duracion:" + nowDif);
		String out = (String) output.get("out");
		return out;
			
	}
	
	
	@Override
	public String EliminarSeguimiento(String correlativo, String codseg, String login, String area) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storedeliminarseguimiento.execute(correlativo, codseg, login, area);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_DELETE_SEGUIMIENTO Duracion:" + nowDif);
		String out = (String) output.get("out");
		return out;
	}
	
	public List<SeguimientoEntranteBean> BusquedaSP(int ano, int origen, long correlativos, String area, String opcion, String detalle) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = Storedbusquedasegdocentrantes.execute(ano, origen, correlativos, area, opcion, detalle);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_BUSQUEDA_SEG_DOC_ENT Duracion:" + nowDif);
		
		List seguimiento = (ArrayList)output.get("busquedas_seguimiento");
		return seguimiento;
	}
	
	public List<SeguimientoEntranteBean> BuscarSP(String ano, String origen, String tipo, String correlativo, String area) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = Storedbuscarseguimientoentranten.execute(ano, origen, tipo, correlativo, area);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_BUSCAR_DOC_SEG_ALL Duracion:" + nowDif);
		
		
		List buscar = (ArrayList)output.get("buscar_seguimiento");
		return buscar;
	}
	
	public void setStoredActualizarSeguimiento(StoredActualizarSeguimiento Storedactualizarseguimiento) {
		this.Storedactualizarseguimiento = Storedactualizarseguimiento;
	}

	public void setStoredBusqueda(StoredBusquedaSegDocEntrantes Storedbusquedasegdocentrantes) {
		this.Storedbusquedasegdocentrantes = Storedbusquedasegdocentrantes;
	}
	
	public void setStoredBuscar(StoredBuscarSeguimientoEntraten Storedbuscarseguimientoentranten) {
		this.Storedbuscarseguimientoentranten = Storedbuscarseguimientoentranten;
	}
	
	
	
	public void setStorednuevoseguimiento(StoredNuevoSeguimiento storednuevoseguimiento) {
		this.storednuevoseguimiento = storednuevoseguimiento;
	}
	

	public void setStoredeliminarseguimiento(StoredEliminarSeguimiento storedeliminarseguimiento) {
		this.storedeliminarseguimiento = storedeliminarseguimiento;
	}


	@Override
	public List seguimientoSP() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<SeguimientoEntranteBean> filtrosSP(String fecha1, String fecha2, String tipos, String area) {
		Map output = storedfiltrossegent.execute(fecha1, fecha2, tipos, area);
		List seguimientos_filtros = (ArrayList)output.get("filtros_seguimiento");
		return seguimientos_filtros;
	}
	
	
	public void setStoredfiltrossegent(StoredFiltrosSegEnt storedfiltrossegent) {
		this.storedfiltrossegent = storedfiltrossegent;
	}


	public StoredDerivado getStoredDerivado() {
		return storedDerivado;
	}


	public void setStoredDerivado(StoredDerivado storedDerivado) {
		this.storedDerivado = storedDerivado;
	}


	


	
	
		
	
}
