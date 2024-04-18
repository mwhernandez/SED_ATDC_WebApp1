package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoDerivado;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TipoConsulta;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;

public class EntranteGerenciasDAO implements IEntranteGerenciasDAO{

	private JdbcTemplate jdbcTemplate;
    private StoredEntrantesGerencias storedEntrantesGerencias;   
    private StoredActualizarEnt storedActualizarEnt;
    private StoredEliminarEnt storedEliminarEnt;
    private StoredFiltrosEntGerencias storedfiltrosentgerencias;    
    private StoredNuevoDocumentoEntrada storednuevodocumentoentrada;
    private StoredDocumentosDerivados storedDocumentosDerivados;
    private StoredBusquedaEntrantesGerencias storedbusquedaentranteGerencias;
    private StoredCombo storedCombo;
    private StoredConsultaSegDocEntrantes StoredConsultasegdocentrantes;
    private StoredRemitentesSP storedremitentessp;
    
    
    //private static Logger logger = Logger.getLogger("R1");
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<RemitenteBean> personaExterna(String cadenaDigitada) {
    	
    	Map output = storedremitentessp.execute(cadenaDigitada);
		List remitentesactivos = (ArrayList)output.get("remitentesactivos");
		System.out.println("Estamos Aqui Eli- Remitentes");
		System.out.println(remitentesactivos);
		return remitentesactivos;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    public boolean updatevistaPDF(long correlativo,int nano) {		  
		  String sql = "update atd_doc_entr set nrecepcion=3, dfecrecepcion=sysdate where ncorrelativo= " +correlativo+" and nano="+nano+" ";		 
		  
		  int rs = jdbcTemplate.update(sql);
		  if (rs > 0)
		  return true;
		  return false;
	 }
	
	
	public void setStoredremitentessp(StoredRemitentesSP storedremitentessp) {
		this.storedremitentessp = storedremitentessp;
	}

	@Override
	public List<EntranteBean> entrantesSP(String ncodarea) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		//ncodarea=String.valueOf(usuario.getCodarea());
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		//Map output = storedEntrantesGrupo.execute(ncodarea);
		Map output = storedEntrantesGerencias.execute(ncodarea);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_DOC_ENTRAN_ALL Duracion:" + nowDif);
		List gerencias = (ArrayList)output.get("gerencias");
		return gerencias;
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
		"FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P') ORDER BY A.VABREVIATURA";
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
    
    public List<AreaBean> areasA(int codigo){
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
		"FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P') AND A.NCODAREA="+codigo+" ";
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
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<OrigenBean> origen(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO ORIGEN:" + nowIn);
		final List<OrigenBean> results8 = new ArrayList<OrigenBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' AND VOBSERVACIONES='ORIGEN ATD' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						OrigenBean bean8 = new OrigenBean();
						bean8.setCodigo(rs.getString("codigo"));
						bean8.setDescripcion(rs.getString("descripcion"));
						results8.add(bean8);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO ORIGEN:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO ORIGEN:" + nowDif);
		return results8;
		
	}
    
    public List<RemitenteBean> remitentes(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO REMITENTE:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(				
				"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY VDESCRIPCION",
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
    
    public List<TiposBean> tipos(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	int area;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		area= usuario.getCodarea();
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
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
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO TIPOS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO TIPOS:" + nowDif);
		return results;	
	}	
    
    
    
    public List<EntranteBean> entrada(int area){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO ENTRADA EMAIL:" + nowIn);
		final List<EntranteBean> results5 = new ArrayList<EntranteBean>();
		jdbcTemplate.query(				
				"SELECT (CASE VESTADO WHEN  'ESTA001' THEN  'PENDIENTE' WHEN  'ESTA002' THEN  'DERIVADO' WHEN  'ESTA003' THEN  'ATENDIDO' "+
				"WHEN  'ESTA004' THEN  'ELIMINADO'     END)  AS estado, COUNT(NCORRELATIVO) as cantidad "+
				"FROM ATD_DOC_ENTR WHERE NCODAREA="+area+" GROUP BY NCODAREA, VESTADO ",				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EntranteBean bean5 = new EntranteBean();						
						bean5.setVestado(rs.getString("estado"));
						bean5.setCantidad(rs.getInt("cantidad"));
						results5.add(bean5);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO ENTRADA CORREO:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRADA CORREO:" + nowDif);
		return results5;	
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
    
    public List<LlavesBean> codigo() {
		final List<LlavesBean> results = new ArrayList<LlavesBean>();
		jdbcTemplate.query(
				"select max(ncodremitente)+ 1 as codigo from atd_remitente",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						LlavesBean bean = new LlavesBean();
						bean.setCodigo(rs.getInt("codigo"));                        
                        results.add(bean);
					}
				});
		return results;
	}
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    public void UpdateEmpresa(int codigo, String empresa, int area, String estado, int indicador, String login) {
    	    	
    	String sql = "insert into atd_remitente (ncodremitente, vdescripcion, ncodarea, vestado, nindicador, vrescre)"
    		+ " values(?,?,?,?,?,?)";
    	 Object[] parametros = new Object[] { codigo, empresa, area, estado, indicador, login};
		 int rs = jdbcTemplate.update(sql, parametros);
		 
	}
    
    public void grabarCombo(int codigoRemitente, String empresa, int dirigido,String estado, int indicadorEmpresa, String login)
    {
    	
    }
    
    public List<LlavesBean> codigo_representante(int codempresa) {
		final List<LlavesBean> results = new ArrayList<LlavesBean>();
		jdbcTemplate.query(				
				"select count(ncodrepresentante)+ 1 as codigo from atd_representante where ncodremitente= "+codempresa+"",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						LlavesBean bean = new LlavesBean();
						bean.setCodigo_representante(rs.getInt("codigo"));                        
                        results.add(bean);
					}
				});
		return results;
	}
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    public void UpdateRepresentante(int codigo, int codrepresentante, String nombre, String estado, int indicador, String login) {
    	
    	String sql = "insert into atd_representante (ncodremitente, ncodrepresentante, vnombre, vestado, nindicador, vrescre)"
    		+ " values(?,?,?,?,?,?)";
    	 Object[] parametros = new Object[] { codigo, codrepresentante, nombre, estado, indicador, login};
		 int rs1 = jdbcTemplate.update(sql, parametros);
		 
	}
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    public boolean updatedirigido(long correlativo, int area) {		  
		  String sql = "update atd_doc_entr_dirigido set nestado=2 where ncorrelativo= " +correlativo+" and  ncodarea="+area+"";
		  System.out.println("Parametros:"+correlativo+"   "+ area);
		  System.out.println("Update dirigdo:"+ sql);
		  int rs = jdbcTemplate.update(sql);
		  if (rs > 0)
		  return true;
		  return false;
	 }
    
    
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    public boolean updatedirigidoCancel(long correlativo, int area) {		  
		  String sql = "update atd_doc_entr_dirigido set nestado=1 where ncorrelativo= " +correlativo+" and  ncodarea="+area+"";
		  System.out.println("Parametros:"+correlativo+"   "+ area);
		  System.out.println("Update dirigido cancel:"+ sql);
		  int rs = jdbcTemplate.update(sql);
		  if (rs > 0)
		  return true;
		  return false;
	 }
    
	@Override
	public void eliminarSPEnt(String anno, String origen, String tipodoc, String codigo, String login) {
		Map output = storedEliminarEnt.execute(anno, origen, tipodoc, codigo, login);	
	}
    
    public List<TrabajadorBean> trabajador(int area){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO TRABAJADOR:" + nowIn);
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();		
		jdbcTemplate.query(
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "+
				"from trabajador where vcodestado in ('EIRC01','EIRC03') and vcodtipo in ('TIPT001','TIPT005') and ncodarea= "+area+"order by VAPEPATERNO",
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
    
    public List<TrabajadorBean> trabajador_derivador(int area){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO TRABAJADOR_DERIVADOR:" + nowIn);
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
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO TRABAJADOR_DERIVADOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO TRABAJADOR_DERIVADOR:" + nowDif);
		return results3;
	}
    
    public List<RepresentanteBean> representante(int codigo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO REPRESENTANTE :" + nowIn);
		final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();		
		jdbcTemplate.query(
				"SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "+
				"FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE="+codigo+"ORDER BY VNOMBRE",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RepresentanteBean bean4 = new RepresentanteBean();
						bean4.setCodrepresentante(rs.getInt("codigo"));
						bean4.setVnombre(rs.getString("representante"));						
						results5.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO REPRESENTANTE:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO REPRESENTANTE:" + nowDif);
		return results5;
	}
    
    public List<RemitenteBean> persona(String cadenaDigitada){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(				
				"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 AND UPPER(VDESCRIPCION) LIKE UPPER('"+cadenaDigitada+"%')",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO REMITENTES:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO REMITENTES:" + nowDif);		
		return result1;
	}    
    
    public List<RemitenteBean> personaIntena(String cadenaDigitada){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(				
				//"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				//"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 AND UPPER(VDESCRIPCION) LIKE UPPER('"+cadenaDigitada+"%')",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
				"FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P') AND UPPER(A.VABREVIATURA) LIKE UPPER('"+cadenaDigitada+"%')",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO REMITENTES:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO REMITENTES:" + nowDif);		
		return result1;
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
    
    @Override
	public String actualizaCombos(String nombreEmpresa, String codArea,
			String login) {
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
    	Map output = storedCombo.execute(nombreEmpresa,codArea, login);
    	String out = (String) output.get("out");	
    	nowOut = System.currentTimeMillis();
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion S_GUIA.ATDC_NUEVO_REMITENTE_DOC:" + nowDif);
	    return out;
    	
	}
    
	
	public void setStoredEntrantesGerencias(StoredEntrantesGerencias storedEntrantesGerencias) {
		this.storedEntrantesGerencias = storedEntrantesGerencias;
	}

	public void actualizarSPEnt(EntranteBean entranteBean) {
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
		Map output = storedActualizarEnt.execute(entranteBean);		
		nowOut = System.currentTimeMillis();
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion S_GUIA.ATDC_UPDATE_DOC_ENTR:" + nowDif);
	}
	
	@Override
	public void nuevoSPEnt(EntranteBean entranteBean) {		
		Map output = storednuevodocumentoentrada.execute(entranteBean);
			
	}
	
	public List<EntranteBean> BusquedaSP(String area, String opcion, String detalle) {
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
		Map output = storedbusquedaentranteGerencias.execute(area, opcion, detalle);
		nowOut = System.currentTimeMillis();
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOC_ENT:" + nowDif);
		List busqueda = (ArrayList)output.get("busquedas_entrantes_gerentes");
		return busqueda;
	}
	
	/*
	@Override
	public List<EntranteBean> grupos() {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	@Override
	public List<EntranteBean> gerencias() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public List<EntranteBean> filtrosSP(String fecha1, String fecha2, String tipos, String area) {
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
		//Map output = storedfiltrosentgrupo.execute(fecha1, fecha2, tipos, area);
		Map output = storedfiltrosentgerencias.execute(fecha1, fecha2, tipos, area);
		nowOut = System.currentTimeMillis();
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion Stored S_GUIA.ATDC_FILTRO_ENTRANTE:" + nowDif);
		List documentos = (ArrayList)output.get("filtros");
		return documentos;
	}
	
	
	public List<EntranteBean> ConsultaDireccionSP(String fechaIni, String fechaFin, String tipos) {
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();       
		Map output = StoredConsultasegdocentrantes.execute(fechaIni, fechaFin, tipos);
		nowOut = System.currentTimeMillis();
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOC_ENT:" + nowDif);
		List consulta = (ArrayList)output.get("consulta");
		System.out.println("vuelvo de SP");
		System.out.println(consulta);
		return consulta;
	}
	
	public List<DocumentoDerivado> derivadosSP(String tipoConsulta, String area, String fechaInicial, String fechaFinal, String estado, String ficha, String areaLogin)
    {
        Map map = this.storedDocumentosDerivados.execute(tipoConsulta, area,
                fechaInicial, fechaFinal, estado, ficha, areaLogin);
        List  derivados = (ArrayList) map.get("derivados");
        return derivados;
    }
	
	public List<TipoConsulta> getTiposConsulta()
	{
		String sql= "SELECT VCODTIPO,VDESCRIPCION FROM TIPO WHERE VOBSERVACIONES='CONSULTA ATD' AND VESTADO='A' ORDER BY VCODTIPO";
		System.out.println(sql);
		final List<TipoConsulta> result = new ArrayList<TipoConsulta>();
		jdbcTemplate.query(sql,				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TipoConsulta consulta = new TipoConsulta();
						consulta.setTipo(rs.getString("VCODTIPO"));
						consulta.setDescripcion(rs.getString("VDESCRIPCION"));
						result.add(consulta);
					}
				});
		return result;
		
	}
	/*
	public List<DocumentoDerivado> obtenerDerivados(String tipoConsulta, String area, String fechaInicial, String fechaFinal, String estado, String ficha)
	{
		HttpSession session = (HttpSession) FacesContext
		.getCurrentInstance().getExternalContext().getSession(true);
		Usuario usu = (Usuario) session.getAttribute("sUsuario");		
		String sql1 = "SELECT A.NCORRELATIVO AS CORRELATIVO,	A.VNUMDOC AS NUMERODOCUMENTO,A.DFECDOC AS FECHADOC,	A.VASUNTO AS ASUNTO,"
			+ "B.VOBSERVACION AS OBSERVACION,B.DFECDERIVACION AS FECHADERIVACION,B.VUBIARCHIVO AS UBICACION,B.VOBSERVACION AS NOMBRE "
			+ "FROM ATD_DOC_ENTR A,	ATD_DOC_ENTR_SEG B	WHERE A.NANO = B.NANO AND A.NORIGEN = B.NORIGEN AND	A.VTIPODOC = B.VTIPODOC "
			+ " AND	A.NCORRELATIVO = B.NCORRELATIVO AND	B.NAREADERIVADO = "+area+" AND B.DFECDERIVACION >= '"+ fechaInicial+"' AND "
			+ "	B.DFECDERIVACION <= '"+ fechaFinal +"' AND B.VESTADO <> 'ESTA004' AND B.VESTADO ='"+estado+"'";
		String sql2 = "SELECT A.NCORRELATIVO AS CORRELATIVO, A.VNUMDOC AS NUMERODOCUMENTO, A.DFECDOC AS FECHADOC, A.VASUNTO AS ASUNTO,"
			+ "B.VOBSERVACION AS OBSERVACION, B.DFECDERIVACION AS FECHADERIVACION,B.VUBIARCHIVO AS UBICACION,B.VOBSERVACION AS NOMBRE "
			+ "FROM ATD_DOC_ENTR A, ATD_DOC_ENTR_SEG B	WHERE A.NANO = B.NANO AND A.NORIGEN = B.NORIGEN AND	A.VTIPODOC = B.VTIPODOC AND "
			+ "A.NCORRELATIVO = B.NCORRELATIVO AND	B.NAREADERIVADO ="+area+" AND B.VESTADO <> 'ESTA004'";
		String sql3 ="SELECT A.NCORRELATIVO AS CORRELATIVO,	A.VNUMDOC AS NUMERODOCUMENTO, A.DFECDOC AS FECHADOC, A.VASUNTO AS ASUNTO,"
			+ "B.VOBSERVACION AS OBSERVACION,B.DFECDERIVACION AS FECHADERIVACION,B.VUBIARCHIVO AS UBICACION,B.VOBSERVACION AS NOMBRE "
			+ "FROM ATD_DOC_ENTR A, ATD_DOC_ENTR_SEG B	WHERE A.NANO = B.NANO AND A.NORIGEN = B.NORIGEN AND	A.VTIPODOC = B.VTIPODOC AND "
			+ "A.NCORRELATIVO = B.NCORRELATIVO AND B.NAREADERIVADO ="+area+" AND B.DFECDERIVACION >= '"+ fechaInicial+"' AND "
			+ "B.DFECDERIVACION <= '"+ fechaFinal +"' AND B.VESTADO <> 'ESTA004'";
		String sql4 = "SELECT DISTINCT A.NCORRELATIVO AS CORRELATIVO, A.VNUMDOC AS NUMERODOCUMENTO, A.DFECDOC AS FECHADOC,"
			 + "A.VASUNTO AS ASUNTO, B.VOBSERVACION AS OBSERVACION, B.DFECDERIVACION AS FECHADERIVACION, B.VUBIARCHIVO AS UBICACION,"
			 + "C.VAPEPATERNO||' '||C.VAPEMATERNO||', '||C.VNOMBRES AS NOMBRE FROM ATD_DOC_ENTR A, ATD_DOC_ENTR_SEG B, TRABAJADOR C "
			 +"	WHERE A.NANO = B.NANO AND A.NORIGEN = B.NORIGEN AND	A.VTIPODOC = B.VTIPODOC AND	A.NCORRELATIVO = B.NCORRELATIVO AND "
			 +"	B.NAREAREMITENTE="+ usu.getNcodarea()+" AND B.NDERIVADO = C.NFICHA AND B.DFECDERIVACION >= '"+ fechaInicial+"' AND	"
			 + " B.DFECDERIVACION <= '"+ fechaFinal +"' AND B.VESTADO <> 'ESTA004' AND B.VESTADO ='"+estado+"' AND	B.NDERIVADO ="+ ficha;
		String sql = null;
		
		if(tipoConsulta.equals("OPCION1"))
		{
			sql = sql1;
		}
		else
			if(tipoConsulta.equals("OPCION2"))
				 sql=sql2;
			else
				if(tipoConsulta.equals("OPCION3"))
					 sql=sql3;
				else
					if(tipoConsulta.equals("OPCION4"))
					   sql = sql4;
		System.out.println(sql);
		final List<DocumentoDerivado> result = new ArrayList<DocumentoDerivado>();
		jdbcTemplate.query(sql,				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						DocumentoDerivado derivado = new DocumentoDerivado();
						derivado.setAsunto(rs.getString("ASUNTO"));
						derivado.setCorrelativo(Integer.parseInt(rs.getString("CORRELATIVO")));
						derivado.setFechaDoc(rs.getDate("FECHADOC"));
						derivado.setNombre(rs.getString("NOMBRE"));
						derivado.setNumeroDocumento(rs.getString("NUMERODOCUMENTO"));
						derivado.setObservacion(rs.getString("OBSERVACION"));
						derivado.setUbicacionArchivo(rs.getString("UBICACION"));
						derivado.setFechaDerivacion(rs.getDate("FECHADERIVACION"));					
						result.add(derivado);
					}
				});
		return result;
		
	}
	*/
	public void setStoredNuevoEnt(StoredNuevoDocumentoEntrada storednuevodocumentoentrada) {		
		this.storednuevodocumentoentrada =storednuevodocumentoentrada;
	}

	public void setStoredActualizarEnt(StoredActualizarEnt storedActualizarEnt) {
		this.storedActualizarEnt = storedActualizarEnt;
	}
	
	

	


	public void setStoredfiltrosentgerencias(StoredFiltrosEntGerencias storedfiltrosentgerencias) {
		this.storedfiltrosentgerencias = storedfiltrosentgerencias;
	}

	public void setStoredEliminarEnt(StoredEliminarEnt storedEliminarEnt) {
		this.storedEliminarEnt = storedEliminarEnt;
	}

	
	

	

	public void setStoredbusquedaentranteGerencias(
			StoredBusquedaEntrantesGerencias storedbusquedaentranteGerencias) {
		this.storedbusquedaentranteGerencias = storedbusquedaentranteGerencias;
	}

	public StoredCombo getStoredCombo() {
		return storedCombo;
	}

	public void setStoredCombo(StoredCombo storedCombo) {
		this.storedCombo = storedCombo;
	}
	
	public void setStoredDocumentosDerivados(
			StoredDocumentosDerivados storedDocumentosDerivados) {
		this.storedDocumentosDerivados = storedDocumentosDerivados;
	}



	public void setStorednuevodocumentoentrada(
			StoredNuevoDocumentoEntrada storednuevodocumentoentrada) {
		this.storednuevodocumentoentrada = storednuevodocumentoentrada;
	}
	
	public void setStoredConsultasegdocentrantes(
			StoredConsultaSegDocEntrantes storedConsultasegdocentrantes) {
		StoredConsultasegdocentrantes = storedConsultasegdocentrantes;
	}

	
	
}
