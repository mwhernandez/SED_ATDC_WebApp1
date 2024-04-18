package com.sedapal.tramite.dao;

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

import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;


public class EntranteMesaLineaDAO implements IEntranteMesaLineaDAO{

	private JdbcTemplate jdbcTemplate;
    private StoredEntrantesMesaLinea storedentrantesmesalinea;   
    private StoredActualizarEntMesa storedactualizarentmesa;    
    private StoredFiltrosEntMesa storedfiltrosentmesa;    
    private StoredNuevoDocumentoEntradaMesa storednuevodocumentoentradamesa;
    private StoredBusquedaEntrantesMesaLinea storedbusquedaentrantemesalinea;
    private StoredEliminarEntMesa storedEliminarEntMesa;
    private StoredCombo storedCombo;
    private StoredRemitentesSP storedremitentessp;
    private StoredListaAsuntoPopup storedlistaasuntopopub;
    private StoredBusquedaAsuntos storedbusquedaasuntos;
    
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
    
    
	
	public void setStoredremitentessp(StoredRemitentesSP storedremitentessp) {
		this.storedremitentessp = storedremitentessp;
	}

	@Override
	public List<EntranteMesaBean> entrantesSP(String ncodarea) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		ncodarea=String.valueOf(usuario.getCodarea());
		Map output = storedentrantesmesalinea.execute(ncodarea);    	
		List entrantes_mesa = (ArrayList)output.get("entrantesmesa");
		return entrantes_mesa;
	}
	
	/*
	public List<TiposBean> asuntos_estandares(){    	
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"SELECT NCOD_PARAMETRO AS CODIGO,VOBSERVACIONES AS ASUNTO FROM ATD_PARAMETROS WHERE VDESCRIPCION='ASUNTO ESTANDAR' AND NVALOR=538 AND NINDICADOR=1 ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setCodigo(String.valueOf((rs.getInt("CODIGO"))));
						bean2.setDescripcion(rs.getString("ASUNTO"));
						results.add(bean2);
					}
				});		
		return results;		
	}
	*/
	
	public List<TiposBean> asuntos_estandares(){
		//storedlistaasunto
		Map output = storedlistaasuntopopub.execute();    	
		List asuntos = (ArrayList)output.get("asunto");
		return asuntos;
	}
	
	public List<TiposBean> busqueda_asuntos_estandar(String opcion_asunto, String detalle_asunto){		
		Map output = storedbusquedaasuntos.execute(opcion_asunto, detalle_asunto);  	
		List busquedaasunto = (ArrayList)output.get("busquedaasunto");
		return busquedaasunto;
	}
	   

	public void setStoredlistaasuntopopub(StoredListaAsuntoPopup storedlistaasuntopopub) {
		this.storedlistaasuntopopub = storedlistaasuntopopub;
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
    
    public List<AreaBean> areas_seleccionadas(int anio, int origen, String tipodoc, long correlativo){
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
				"SELECT D.NCODAREA as codigo,upper(A.VABREVIATURA)||' - '||upper(A.VDESCRIPCION) as nombre "+
				"FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A "+
				"WHERE  D.NANO="+anio+" AND D.NORIGEN="+origen+" AND D.VTIPODOC='"+tipodoc+"' AND D.NCORRELATIVO="+correlativo+" " +
				"AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1 ORDER BY A.VABREVIATURA",
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
    
    public List<RemitenteBean> remitentes(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(				
				"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY VDESCRIPCION",
				//"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				//"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 AND NCODREMITENTE="+area+"",
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
    
    public List<TiposBean> tipos(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO TIPOS:" + nowIn);
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
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO TIPOS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO TIPOS:" + nowDif);		
		return results;	
	}	
    
    
	    
    public List<TrabajadorBean> trabajador(int area){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO AREAS:" + nowIn);
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
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO AREAS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO AREAS:" + nowDif);
		return results1;
	}
    
    public List<TrabajadorBean> trabajador_derivador(int area){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO TRABAJADOR_DERIVADOR:" + nowIn);
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
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO DERIVADOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO DERIVADOR:" + nowDif);
		return results3;
	}
    
    public List<RepresentanteBean> representante(int codigo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO REPRESENTANTE:" + nowIn);
		final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();		
		jdbcTemplate.query(				
				"SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante " +
				"FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE= "+codigo+"ORDER BY VNOMBRE",	
				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RepresentanteBean bean4 = new RepresentanteBean();
						bean4.setCodrepresentante(rs.getInt("codigo"));
						bean4.setVnombre(rs.getString("representante"));						
						results5.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO REPRESENTANTE:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO REPRESENTANTE:" + nowDif);
		return results5;
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
    
    public List<EstadosBean> estados(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO ESTADOS:" + nowIn);
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
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO ESTADOS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO ESTADOS:" + nowDif);
		return results6;
	}
    
 public List<AnioBean> anio(){    		
		
		final List<AnioBean> results = new ArrayList<AnioBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS DESCRIPCION  FROM ATD_TIPO WHERE VFLAT='ANO' AND NESTADO=1 AND NINDICADOR=1 ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AnioBean bean2 = new AnioBean();
						bean2.setCodigo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});
		
		return results;		
	}
    
    public List<ServidorBean> servidor(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO SERVIDOR:" + nowIn);
		final List<ServidorBean> results7 = new ArrayList<ServidorBean>();
		jdbcTemplate.query(
				//"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=3",	
				"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=7",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ServidorBean bean7 = new ServidorBean();						
						bean7.setDescripcion(rs.getString("descripcion"));
						results7.add(bean7);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO SERVIDOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO SERVIDOR:" + nowDif);
		return results7;	
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
    
    public void UpdateEmpresa(int codigo, String empresa, int area, String estado, int indicador, String login) {
    	    	
    	String sql = "insert into atd_remitente (ncodremitente, vdescripcion, ncodarea, vestado, nindicador, vrescre)"
    		+ " values(?,?,?,?,?,?)";
    	 Object[] parametros = new Object[] { codigo, empresa, area, estado, indicador, login};
		 int rs = jdbcTemplate.update(sql, parametros);
		 
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

    public void UpdateRepresentante(int codigo, int codrepresentante, String nombre, String estado, int indicador, String login) {
    	
    	String sql = "insert into atd_representante (ncodremitente, ncodrepresentante, vnombre, vestado, nindicador, vrescre)"
    		+ " values(?,?,?,?,?,?)";
    	 Object[] parametros = new Object[] { codigo, codrepresentante, nombre, estado, indicador, login};
		 int rs1 = jdbcTemplate.update(sql, parametros);
		 
	}
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    public boolean updatedirigido(long correlativo, int area, int nano) {		  
		  String sql = "update atd_doc_entr_dirigido set nestado=2 where ncorrelativo= " +correlativo+" and  ncodarea="+area+" and nano="+nano+" ";		 
		  
		  int rs = jdbcTemplate.update(sql);
		  if (rs > 0)
		  return true;
		  return false;
	 }
    
    @Override
	public String actualizaCombos(String nombreEmpresa, String codArea,
			String login) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
    	Map output = storedCombo.execute(nombreEmpresa,codArea, login);	
    	String out = (String) output.get("out");		
    	nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_NUEVO_REMITENTE_DOC Duracion:" + nowDif);
		return out;
	}
    
	

	
	public void setStoredbusquedaentrantemesalinea(StoredBusquedaEntrantesMesaLinea storedbusquedaentrantemesalinea) {
		this.storedbusquedaentrantemesalinea = storedbusquedaentrantemesalinea;
	}

	public List<EntranteMesaBean> BusquedaSP(String area, String opcion, String detalle) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedbusquedaentrantemesalinea.execute(area, opcion, detalle);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_BUSQUEDA_DOC_ENT_MESA Duracion:" + nowDif);		
		
		List busqueda_mesa = (ArrayList)output.get("busquedas_mesa");
		return busqueda_mesa;
	}
	
	

	

	public void setStoredentrantesmesalinea(StoredEntrantesMesaLinea storedentrantesmesalinea) {
		this.storedentrantesmesalinea = storedentrantesmesalinea;
	}

	@Override
	public void eliminarSPEnt(String anno, String origen, String tipodoc,String codigo, String login) {
		// TODO Auto-generated method stub
		Map output = storedEliminarEntMesa.execute(anno, origen, tipodoc, codigo, login);	
		
	}

	@Override
	public List<EntranteMesaBean> entrantesmesa() {
		// TODO Auto-generated method stub
		return null;
	}
	

	public void setStorednuevodocumentoentradamesa(
			StoredNuevoDocumentoEntradaMesa storednuevodocumentoentradamesa) {
		this.storednuevodocumentoentradamesa = storednuevodocumentoentradamesa;
	}
	
	public List<EntranteMesaBean> filtrosSP(String fecha1, String fecha2, String tipos, String area) {		
		Map output = storedfiltrosentmesa.execute(fecha1, fecha2, tipos, area);
		List documentos = (ArrayList)output.get("filtros_mesa");
		return documentos;
	}
	
	public void setStoredFiltrosEnt(StoredFiltrosEntMesa storedfiltrosentmesa) {
		this.storedfiltrosentmesa = storedfiltrosentmesa;
	}
	
	@Override
	public Map actualizarSPEntMesa(EntranteMesaBean entranteMesaBean) {
		Map output = storedactualizarentmesa.execute(entranteMesaBean);	
		return output;
	}
	
	@Override
	public Map nuevoSPEnt(EntranteMesaBean entrantemesaBean) {
		Map output = storednuevodocumentoentradamesa.execute(entrantemesaBean);
		//String out = (String) output.get("out");
		return output;	
	}
	
	public void setStoredActualizarEntMesa(StoredActualizarEntMesa storedactualizarentmesa) {
		this.storedactualizarentmesa = storedactualizarentmesa;
	}
	
	
	
	

	public void setStoredEliminarEntMesa(StoredEliminarEntMesa storedEliminarEntMesa) {
		this.storedEliminarEntMesa = storedEliminarEntMesa;
	}

	
	public StoredCombo getStoredCombo() {
		return storedCombo;
	}

	public void setStoredCombo(StoredCombo storedCombo) {
		this.storedCombo = storedCombo;
	}

	public void setStoredbusquedaasuntos(StoredBusquedaAsuntos storedbusquedaasuntos) {
		this.storedbusquedaasuntos = storedbusquedaasuntos;
	}

	public void setStoredactualizarentmesa(StoredActualizarEntMesa storedactualizarentmesa) {
		this.storedactualizarentmesa = storedactualizarentmesa;
	}

	public void setStoredfiltrosentmesa(StoredFiltrosEntMesa storedfiltrosentmesa) {
		this.storedfiltrosentmesa = storedfiltrosentmesa;
	}

	
	
	
	
	
}
