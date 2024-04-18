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
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;

public class EntranteDerivadoDAO implements IEntranteDerivadoDAO{

	private JdbcTemplate jdbcTemplate;
    private StoredEntrantesDerivado storedEntrantesderivado;   
    private StoredActualizarEntDerivado storedActualizarEntderivado;
    private StoredEliminarEntDerivado storedEliminarEntderivado;
    private StoredFiltrosEntDerivado storedFiltrosEntderivado;    
    private StoredNuevoDocumentoEntradaDerivado storednuevodocumentoentradaderivado;
    private StoredBusquedaEntrantesDerivado storedbusquedaentrantederivado;
    //private static Logger logger = Logger.getLogger("R1");
   
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<EntranteBean> entrantesSP(String ncodarea, int ficha) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		ncodarea=String.valueOf(usuario.getCodarea());
		ficha=usuario.getFicha();
		Map output = storedEntrantesderivado.execute(ncodarea, ficha);    	
		List entrante = (ArrayList)output.get("entrantesderivado");
		return entrante;
	}

	public List<AreaBean> areas(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO AREA:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();
		
		//String sql="SELECT A.NCODAREA as codigo, A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "+
		//"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.NCODAREA";
		String sql="SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
		"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P') ORDER BY A.VABREVIATURA";
		//logger.debug("[EntranteDAO.areas]["+usuario+"]"+sql);
		Date inicio = new Date();
		//logger.debug("[EntranteDAO.areas]["+usuario+"]Inicio sql:" + inicio);
		jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setNombre(rs.getString("nombre"));
						result.add(bean1);
					}
				});
        nowOut = System.currentTimeMillis();
        //logger.debug("[EntranteDAO.areas]["+usuario+"]"+"Final ENTRANTEDAO AREA:" + nowOut);
        nowDif = nowOut - nowIn;
        //logger.debug("[EntranteDAO.areas]["+usuario+"]"+"Duracion ENTRANTEDAO AREA:" + nowDif);		
		return result;
	}
    
    public List<AreaBean> areas_seleccionadasOrigen(int anio, int origen, long correlativo, int nCodAreaOrigen){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        String sql = "SELECT NCODAREA as codigo FROM ATD_DOC_ENTR_DIRIGIDO WHERE NCORRELATIVO="+correlativo
        +" AND NCODAREA_ORIGEN="+nCodAreaOrigen+" AND NANO="+anio+" AND NORIGEN="+origen;       
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(
				//"SELECT D.NCODAREA as codigo,A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "+
				//"FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A "+
				//"WHERE  D.NANO="+anio+" AND D.NORIGEN="+origen+" AND D.VTIPODOC='"+tipodoc+"' AND D.NCORRELATIVO="+correlativo+" " +
				//		"AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1",						
				sql,
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));						
						result.add(bean1);
					}
				});	
		 nowOut = System.currentTimeMillis();
		 System.out.println("Areas Seleccionadas Origen:"+ sql);	    
	     nowDif = nowOut - nowIn;
	     //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO AREA_SELECCIONADAS:" + nowDif);
		return result;
	}
    
    public List<RemitenteBean> remitentes(){
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(				
				"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY CODIGO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		
		return result1;
	}    
    
    public List<TiposBean> tipos(){
		final List<TiposBean> results = new ArrayList<TiposBean>();
		int area;
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		area= usuario.getCodarea();
		jdbcTemplate.query(
				"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  " +
				"WHERE VESTADO='A' AND VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VDESCRIPCION",				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setTipo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});
		return results;	
	}	
    
    public List<AreaBean> areas_seleccionadas(int anio, int origen, String tipodoc, long correlativo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAODAO AREA_SELECCIONADAS:" + nowIn);
        //String sql = "SELECT D.NCODAREA as codigo,D.VTIPO||' - ' ||upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
        //"FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A "+
        //"WHERE  D.NANO="+anio+" AND D.NORIGEN="+origen+" AND D.VTIPODOC='"+tipodoc+"' AND D.NCORRELATIVO="+correlativo+" " +
        //"AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1 ORDER BY D.VTIPO";
        String sql = "SELECT D.NCODAREA as codigo,D.VTIPO as tipo,upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
        "FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A "+
        "WHERE  D.NANO="+anio+" AND D.NORIGEN="+origen+" AND D.VTIPODOC='"+tipodoc+"' AND D.NCORRELATIVO="+correlativo+" " +
        "AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1 ORDER BY D.VTIPO";
        
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(
				//"SELECT D.NCODAREA as codigo,A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "+
				//"FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A "+
				//"WHERE  D.NANO="+anio+" AND D.NORIGEN="+origen+" AND D.VTIPODOC='"+tipodoc+"' AND D.NCORRELATIVO="+correlativo+" " +
				//		"AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1",						
				sql,
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setTipo(rs.getString("tipo"));
						bean1.setNombre(rs.getString("nombre"));												
						result.add(bean1);
					}
				});	
		 nowOut = System.currentTimeMillis();
		 System.out.println("Areas Seleccionadas:"+ sql);
	     //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO AREA_SELECCIONADAS:" + nowOut);
	     nowDif = nowOut - nowIn;
	     //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO AREA_SELECCIONADAS:" + nowDif);
		return result;
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
				//"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=1",
				"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=5",
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
    
    public List<EstadosBean> estados(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO ESTADO :" + nowIn);
		final List<EstadosBean> results6 = new ArrayList<EstadosBean>();
		
		jdbcTemplate.query(
				
				"SELECT VCODTIPO AS codigo,VDESCRIPCION AS descripcion FROM TIPO WHERE VOBSERVACIONES='ESTADO ATD' AND VESTADO='A'",
				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EstadosBean bean6 = new EstadosBean();
						bean6.setCodigo(rs.getString("codigo"));
						bean6.setDescripcion(rs.getString("descripcion"));						
						results6.add(bean6);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO ESTADO:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO ESTADO:" + nowDif);
		return results6;
	}

	@Override
	public void eliminarSPEnt(String anno, String origen, String tipodoc, String codigo, String login) {
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
		Map output = storedEliminarEntderivado.execute(anno, origen, tipodoc, codigo, login);	
		nowOut = System.currentTimeMillis();
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion Stored S_GUIA.ATDC_DELETE_DOC_ENT:" + nowDif);
	}
    
    public List<TrabajadorBean> trabajador(int area){
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();
		
		jdbcTemplate.query(
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "+
				"from trabajador where vcodestado='EIRC01' and vcodtipo in ('TIPT001','TIPT005') and ncodarea= "+area+"order by VAPEPATERNO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean2 = new TrabajadorBean();
						bean2.setFicha(rs.getLong("ficha"));
						bean2.setNombre_completo(rs.getString("nombre"));						
						results1.add(bean2);
					}
				});
		return results1;
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
    
    public List<TrabajadorBean> trabajador_derivador(int area){
		final List<TrabajadorBean> results3 = new ArrayList<TrabajadorBean>();
		
		jdbcTemplate.query(
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "+
				"from trabajador where vcodestado='EIRC01' and vcodtipo in ('TIPT001','TIPT005') and ncodarea= "+area+"order by VAPEPATERNO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean3 = new TrabajadorBean();
						bean3.setFicha(rs.getLong("ficha"));
						bean3.setNombre_completo(rs.getString("nombre"));						
						results3.add(bean3);
					}
				});
		return results3;
	}
    
  //ADD CF AGO 2011 REPORT
	public List<TrabajadorBean> trabajadorSolo(long ficha){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO TRABAJADOR:" + nowIn);
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();
		String sql = "select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "+
				"from trabajador where vcodestado in ('EIRC01','EIRC03') and nficha="+ ficha;
		//logger.debug("ENTRANTEDAO TRABAJADOR (sql:"+ sql);
		jdbcTemplate.query(sql
				,
				//"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "+
				//"from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= "+area+"order by VAPEPATERNO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean2 = new TrabajadorBean();
						bean2.setFicha(rs.getLong("ficha"));
						bean2.setNombre_completo(rs.getString("nombre"));						
						results1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO TRABAJADOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO TRABAJADOR:" + nowDif);
		return results1;
	}
    
    public List<RepresentanteBean> representante(int codigo){
		final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();
		
		jdbcTemplate.query(
				
				//"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS representante "+
				//"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NCODREMITENTE="+codigo+"",
				"SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "+
				"FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE="+codigo+"",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RepresentanteBean bean4 = new RepresentanteBean();
						bean4.setCodrepresentante(rs.getInt("codigo"));
						bean4.setVnombre(rs.getString("representante"));						
						results5.add(bean4);
					}
				});
		return results5;
	}
    
	public void setStoredEntrantes(StoredEntrantesDerivado storedEntrantesderivado) {
		this.storedEntrantesderivado = storedEntrantesderivado;
	}
    
	public void actualizarSPEnt(EntranteBean entranteBean) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		Map output = storedActualizarEntderivado.execute(entranteBean);		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_UPDATE_DOC_ENTR Duracion:" + nowDif);
	}
	
	@Override
	public void nuevoSPEnt(EntranteBean entranteBean) {		
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		Map output = storednuevodocumentoentradaderivado.execute(entranteBean);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_NUEVO_DOCUMENTO_ENTRADA Duracion:" + nowDif);
			
	}
	/*
	public List<EntranteBean> BusquedaSP(String area, int ficha, String opcion, String detalle) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedbusquedaentrantederivado.execute(area, ficha, opcion, detalle);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_BUSQUEDA_DOC_DERIVADO Duracion:" + nowDif);
		
		List busqueda = (ArrayList)output.get("busquedas_entrantes");
		return busqueda;
	}
	*/
	public List<EntranteBean> BusquedaSP(String area, int ficha, String opcion, String detalle) {
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();       
		Map output = storedbusquedaentrantederivado.execute(area, ficha, opcion, detalle);
		nowOut = System.currentTimeMillis();
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOC_ENT:" + nowDif);
		List busqueda = (ArrayList)output.get("busquedas_entrantes");
		System.out.println("vuelvo de SP");
		System.out.println(busqueda);
		return busqueda;
	}
	@Override
	public List<EntranteBean> entrantes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List<EntranteBean> filtrosSP(String fecha1, String fecha2, String tipos, String area, int ficha) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedFiltrosEntderivado.execute(fecha1, fecha2, tipos, area, ficha);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_FILTRO_ENTRANTE Duracion:" + nowDif);
		
		List documentos = (ArrayList)output.get("filtros");
		return documentos;
	}
	
	
	public void setStoredNuevoEnt(StoredNuevoDocumentoEntradaDerivado storednuevodocumentoentradaderivado) {		
		this.storednuevodocumentoentradaderivado =storednuevodocumentoentradaderivado;
	}

	public void setStoredActualizarEnt(StoredActualizarEntDerivado storedActualizarEntderivado) {
		this.storedActualizarEntderivado = storedActualizarEntderivado;
	}
	
	public void setStoredFiltrosEnt(StoredFiltrosEntDerivado storedFiltrosEntderivado) {
		this.storedFiltrosEntderivado = storedFiltrosEntderivado;
	}

	public void setStoredEliminarEnt(StoredEliminarEntDerivado storedEliminarEntderivado) {
		this.storedEliminarEntderivado = storedEliminarEntderivado;
	}

	
	public void setStoredbusquedaentrante(StoredBusquedaEntrantesDerivado storedbusquedaentrantederivado) {
		this.storedbusquedaentrantederivado = storedbusquedaentrantederivado;
	}
	
	
}
