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








import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.SecretariaBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.IDocumentoSalidaDAO;
import com.sedapal.tramite.beans.Seguir;

public class DocumentoSalidaDAO implements IDocumentoSalidaDAO{
	
	private JdbcTemplate jdbcTemplate;
    private StoredDocumentoSaliente storeddocumentosaliente;
    private StoredActualizarDocumentoSalida storedactualizarDocumentoSalida;
    private StoredNuevoDocumentoSaliente storednuevoDocumentoSalida;    							   
    private StoredFiltrosSalientes storedFiltrosSalientes;
    private StoredDeleteDocumentoSalida storeddeleteDocumentoSalida;
    private StoredBusquedaDocSalientes StoredBusquedaDocSalientes;
    private StoredCriteriosSalientes storedCriteriosSalientes;
    private StoredCombo storedCombo;
    private StoredEntrateSalida storedentratesalida;
    private StoredBusquedaEntrateSalida storedbusquedaentratesalida;
    private StoredRemitentesSP storedremitentessp;
    private StoredListaAsuntoPopup storedlistaasuntopopub;
    private StoredBusquedaAsuntos storedbusquedaasuntos;
    
    private StoredConsultaDocumentoBPM storedConsultaDocumentoBPM; //SED-REQ-00001
  
    
    //private static Logger logger = Logger.getLogger("R1");
    
        
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
    
       
    public List<LlavesBean> indicador(int nano, int correlativo) {
		final List<LlavesBean> results = new ArrayList<LlavesBean>();
		jdbcTemplate.query(
				"select nindicador as codigo from atd_doc_entr where nano="+nano+" and ncorrelativo = "+correlativo+" ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						LlavesBean bean = new LlavesBean();
						bean.setCodigo(rs.getInt("codigo"));                        
                        results.add(bean);
					}
				});
		return results;
	}
    
       
    @Override
	public List<DocumentoSalidaBean> documentoSP(String area) {
    	///acediendo a sesion http
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		//String area;
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		area=String.valueOf(usuario.getCodarea());
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		//area="313";
    	Map output = storeddocumentosaliente.execute(area);  
    	nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_DOCUMENTO_SALIENTE Duracion:" + nowDif);
		List documento = (ArrayList)output.get("documento");
		return documento;
	}
    
    @Override
	public List<EntranteBean> documentoentradaSP(String area) {		
		Map output = storedentratesalida.execute(area);	
		List entradasalida = (ArrayList)output.get("entradasalida");		
		return entradasalida;
	}
    
    
    public List<RemitenteBean> personaExterna(String cadenaDigitada) {
    	
    	Map output = storedremitentessp.execute(cadenaDigitada);
		List remitentesactivos = (ArrayList)output.get("remitentesactivos");
		System.out.println("Estamos Aqui Eli- Remitentes");
		System.out.println(remitentesactivos);
		return remitentesactivos;
	}
    
    public List<TiposBean> busqueda_asuntos_estandar(String opcion_asunto, String detalle_asunto){		
		Map output = storedbusquedaasuntos.execute(opcion_asunto, detalle_asunto);  	
		List busquedaasunto = (ArrayList)output.get("busquedaasunto");
		return busquedaasunto;
	}
    
    public List<TiposBean> tipoconsulta(){
    	
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				//"select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo "
				//+ "where vflat='FLT' and nestado=1 ORDER BY codigo",
				"select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo "
				+ "where vflat='FIL' and nestado=1 ORDER BY codigo",
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
    
    
    public List<SecretariaBean> secretaria(int area){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO JEFE:" + nowIn);
		final List<SecretariaBean> results9 = new ArrayList<SecretariaBean>();
		jdbcTemplate.query(								
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre, vcorreo as correo from atd_secretarias where vcodestado='EIRC01' and ncodarea= "+area+" ",				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						SecretariaBean bean = new SecretariaBean();
						bean.setFicha(rs.getInt("ficha"));
						bean.setVnombre(rs.getString("nombre"));
						bean.setVcorreo(rs.getString("correo"));
						results9.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO JEFE:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO JEFE:" + nowDif);
		return results9;	
	}	
    
	    
    
    public void setStoredremitentessp(StoredRemitentesSP storedremitentessp) {
		this.storedremitentessp = storedremitentessp;
	}
    
    public List<TiposBean> asuntos_estandares(){
		//storedlistaasunto
		Map output = storedlistaasuntopopub.execute();    	
		List asuntos = (ArrayList)output.get("asunto");
		return asuntos;
	}

	@Override
	public List<EntranteBean> busquedadocumentoentradaSP(String area, String opcion_entrada, String detalle_entrada) {		
		Map output = storedbusquedaentratesalida.execute(area, opcion_entrada, detalle_entrada);	
		List busquedaentradasalida = (ArrayList)output.get("busquedaentradasalida");
		return busquedaentradasalida;
	}
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<AreaBean> areas(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO AREAS:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(				
				//"SELECT A.NCODAREA as codigo, A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "+
				//"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.NCODAREA",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
				"FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.VABREVIATURA",               
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setNombre(rs.getString("nombre"));
						result.add(bean1);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO AREA:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO AREA:" + nowDif);
		return result;
		
	}
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<TiposBean> tipos(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	int area;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		area= usuario.getCodarea();
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				//"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' AND" +
				//" VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO CODIGO,VDESCRIPCION DESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NCODAREA=1 " +
				"UNION SELECT VCODTIPO CODIGO,VDESCRIPCION DESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NESTADO=1 AND NCODAREA="+area+" ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setTipo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO TIPOS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO TIPOS:" + nowDif);
		return results;		
	}
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<TiposBean> estadomantenimiento(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	int area;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		area= usuario.getCodarea();
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				//"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' AND" +
				//" VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS DESCRIPCION FROM ATD_TIPO WHERE NESTADO=1  AND VFLAT='PSM' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setTipo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO ESTADO MANTENIMIENTO:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO ESTADO MANTENIMIENTO:" + nowDif);
		return results;		
	}
    
        
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<TiposBean> estadoderivador(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	int area;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		area= usuario.getCodarea();
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				//"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' AND" +
				//" VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS DESCRIPCION FROM ATD_TIPO WHERE NESTADO=1 AND VFLAT='PSD' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setTipo(rs.getString("codigo"));						
						bean.setDescripcion(rs.getString("descripcion"));
						results.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO ESTADO DERIVADOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO ESTADO DERIVADOR:" + nowDif);
		return results;		
	}
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<TiposBean> estadojefe(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	int area;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		area= usuario.getCodarea();
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				//"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' AND" +
				//" VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS DESCRIPCION FROM ATD_TIPO WHERE NESTADO=1 AND VFLAT='PSJ' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setTipo(rs.getString("codigo"));						
						bean.setDescripcion(rs.getString("descripcion"));
						results.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO ESTADO DERIVADOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO ESTADO DERIVADOR:" + nowDif);
		return results;		
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
    
    @Override
	public void eliminarSP(String codigo, String anno, String origen, String tipodoc, String area, String login) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		Map output = storeddeleteDocumentoSalida.execute(codigo, anno, origen, tipodoc, area, login);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_DELETE_DOCUMENTO_SALIENTE Duracion:" + nowDif);
	}
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<TrabajadorBean> trabajador(int area){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO TRABAJADOR:" + nowIn);
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();		
		jdbcTemplate.query(				
				//"select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','||VNOMBRES AS nombre from trabajador " +
				//"where vcodestado in ('EIRC01','EIRC03') and ind_email='0' and vcodtipo in ('TIPT001','TIPT005') and ncodarea= "+area+"order by VAPEPATERNO",
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "+
				"from trabajador where vcodestado in ('EIRC01') and IND_EMAIL in (0,1) and ncodarea= "+area+"order by VAPEPATERNO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean2 = new TrabajadorBean();
						bean2.setFicha(rs.getLong("ficha"));
						bean2.setNombre_completo(rs.getString("nombre"));						
						results1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO TRABAJADOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO TRABAJADOR:" + nowDif);
		return results1;
		
	}
    
    
    
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<AreaBean> areas_seleccionadas(int anio, int origen, String tipodoc, int area, int correlativo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO AREAS_SELECCIONADAS:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(
				//"SELECT S.NAREADIRIGIDO as codigo,s.vtipo as tipo,upper(A.VDESCRIPCION) as nombre "+
				//"FROM ATD_DOC_SAL_DIRIGIDO S, AREA A "+
				//"WHERE S.NESTADO=1 AND S.NAREADIRIGIDO=A.NCODAREA AND S.NANO="+anio+" AND " +
				//"S.NORIGEN="+origen+" AND S.VTIPODOC='"+tipodoc+"' AND S.NCODAREA="+area+" AND S.NCODSAL="+correlativo +				
				//" UNION SELECT S.NAREADIRIGIDO as codigo,s.vtipo as tipo,upper(A.VDESCRIPCION) as nombre "+
				//"FROM ATD_DOC_SAL_DIRIGIDO S, ATD_REMITENTE A "+
				//"WHERE S.NESTADO=1 AND S.NAREADIRIGIDO=A.NCODREMITENTE AND S.NANO="+anio+" AND "+
				//"S.NORIGEN="+origen+" AND S.VTIPODOC='"+tipodoc+"' AND S.NCODAREA="+area+" AND S.NCODSAL="+correlativo+ "ORDER BY TIPO ",
				//,nombre
				//S.NAREADIRIGIDO= T.NCODAREA AND
				
				// ELI COMENTA EL CODIGO ORIGINAL22/05/2023  ///////////////
				//"SELECT S.NAREADIRIGIDO as codigo,s.vtipo as tipo,upper(A.VDESCRIPCION) as nombre, T.VAPEPATERNO ||' '||T.VAPEMATERNO||','||T.VNOMBRES AS nombrecompleto, T.NFICHA AS FICHA "+
				//"FROM ATD_DOC_SAL_DIRIGIDO S, AREA A, TRABAJADOR T " +
				//"WHERE S.NESTADO=1 AND S.NAREADIRIGIDO=A.NCODAREA AND " +
				//"S.NFICHADERIVADO=T.NFICHA AND T.VCODESTADO IN ('EIRC01','EIRC02','EIRC03') AND " +
				//"S.NANO="+anio+" AND VTIPO IN ('CC','AA') AND " +
				//"S.NORIGEN="+origen+" AND S.VTIPODOC='"+tipodoc+"' AND S.NCODAREA="+area+" AND S.NCODSAL="+correlativo+ "	" +		
				//"UNION " +
				//"SELECT S.NAREADIRIGIDO as codigo,s.vtipo as tipo,upper(A.VDESCRIPCION) as nombre, R.VNOMBRE AS nombrecompleto, R.NCODREPRESENTANTE AS FICHA " +
				//"FROM ATD_DOC_SAL_DIRIGIDO S, ATD_REMITENTE A, ATD_REPRESENTANTE R " +
				//"WHERE S.NESTADO=1 AND S.NAREADIRIGIDO=A.NCODREMITENTE AND " + 
				//"S.NAREADIRIGIDO=R.NCODREMITENTE AND " +
				//"S.NFICHADERIVADO=R.NCODREPRESENTANTE AND " +
				//"S.NANO="+anio+" AND VTIPO IN ('CC','AA') AND " +
				//"S.NORIGEN="+origen+" AND S.VTIPODOC='"+tipodoc+"' AND S.NCODAREA="+area+" AND S.NCODSAL="+correlativo+ " ORDER BY TIPO ",
				// FIN QUE ELI COMENTA 22/05/2023 /////
				
				"SELECT S.NAREADIRIGIDO as codigo,s.vtipo as tipo,upper(A.VDESCRIPCION) as nombre, T.VAPEPATERNO ||' '||T.VAPEMATERNO||','||T.VNOMBRES AS nombrecompleto, T.NFICHA AS FICHA "+
						"FROM ATD_DOC_SAL_DIRIGIDO S, AREA A, TRABAJADOR T " +
						"WHERE S.NESTADO=1 AND S.NAREADIRIGIDO=A.NCODAREA AND " +
						"S.NFICHADERIVADO=T.NFICHA AND T.VCODESTADO IN ('EIRC01','EIRC02','EIRC03') AND " +
						"S.NANO="+anio+" AND VTIPO IN ('CC','AA') AND " +
						"S.NORIGEN="+origen+" AND S.VTIPODOC='"+tipodoc+"' AND S.NCODAREA="+area+" AND S.NCODSAL="+correlativo+ " " +		
						"UNION " +
						"SELECT S.NAREADIRIGIDO as codigo,s.vtipo as tipo,upper(A.VNOMBRE) as nombre, R.VNOMBRE AS nombrecompleto, R.NCODREPRESENTANTE AS FICHA " +
						"FROM ATD_DOC_SAL_DIRIGIDO S, ATD_REMITENTE_BPM A, ATD_REPRESENTANTE_BPM R, ATD_DOC_SAL D " +
						"WHERE S.NESTADO=1 AND S.NAREADIRIGIDO=A.NCORRELATIVO AND " + 
						"S.NAREADIRIGIDO=R.NCODREMITENTE AND " +
						"S.NFICHADERIVADO=R.NCODREPRESENTANTE AND " +
						"S.NANO="+anio+" AND VTIPO IN ('CC','AA') AND " +
						"S.NORIGEN="+origen+" AND S.VTIPODOC='"+tipodoc+"' AND S.NCODAREA="+area+" AND S.NCODSAL="+correlativo+ " " +
						"AND D.NANO = S.NANO AND D.NCODSAL=S.NCODSAL AND D.NINDREMITENTE=2  "+ 
						"UNION " +
						"SELECT S.NAREADIRIGIDO as codigo,s.vtipo as tipo,upper(A.VDESCRIPCION) as nombre, R.VNOMBRE AS nombrecompleto, R.NCODREPRESENTANTE AS FICHA " +
						"FROM ATD_DOC_SAL_DIRIGIDO S, ATD_REMITENTE A, ATD_REPRESENTANTE R, ATD_DOC_SAL D " +
						"WHERE S.NESTADO=1 AND S.NAREADIRIGIDO=A.NCODREMITENTE AND " + 
						"S.NAREADIRIGIDO=R.NCODREMITENTE AND " +
						"S.NFICHADERIVADO=R.NCODREPRESENTANTE AND " +
						"S.NANO="+anio+" AND VTIPO IN ('CC','AA') AND " +
						"D.NANO = S.NANO AND D.NCODSAL=S.NCODSAL AND D.NINDREMITENTE<>2 AND "+ 
						"S.NORIGEN="+origen+" AND S.VTIPODOC='"+tipodoc+"' AND S.NCODAREA="+area+" AND S.NCODSAL="+correlativo+ " ORDER BY TIPO ",
				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setTipo(rs.getString("tipo"));
						bean1.setNombre(rs.getString("nombre"));
						bean1.setAbreviatura(rs.getString("nombrecompleto"));
						bean1.setFicha(rs.getLong("ficha"));
						result.add(bean1);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO AREAS_SELECCIONADAS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO AREAS_SELECCIONADAS:" + nowDif);
		return result;
	}

    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<RemitenteBean> remitentes_seleccionadas(int anio, int origen, String tipodoc, int area, int correlativo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO REMITENTES_SELECCIONADAS:" + nowIn);
		final List<RemitenteBean> result = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(				
				"SELECT S.NAREADIRIGIDO as codigo,upper(A.VDESCRIPCION) as nombre "+ 
				"FROM ATD_DOC_SAL_DIRIGIDO S, ATD_REMITENTE A "+
				"WHERE S.NAREADIRIGIDO=A.NCODREMITENTE AND A.VESTADO='A' AND S.NANO="+anio+" AND " +
				" S.NORIGEN="+origen+" AND S.VTIPODOC='"+tipodoc+"' AND S.NCODAREA="+area+" AND S.NCODSAL="+correlativo,			
				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean1 = new RemitenteBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setDescripcion(rs.getString("nombre"));
						result.add(bean1);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO REMITENTES_SELECCIONADAS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO REMITENTES_SELECCIONADAS:" + nowDif);
		return result;
	}
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
     public List<EntranteBean> entrante(int areas){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
    	Usuario usuario = null;
    	usuario=(Usuario)session.getAttribute("sUsuario");
       nowIn = System.currentTimeMillis();
      //logger.debug("Inicio SALIDADAO ENTRANTE:" + nowIn);
    final List<EntranteBean> results2 = new ArrayList<EntranteBean>();		
    jdbcTemplate.query(
    		"SELECT D.NCORRELATIVO AS CORRELATIVO,E.VNUMDOC AS NUMDOC, E.VASUNTO AS ASUNTO "+
    		"FROM ATD_DOC_ENTR_DIRIGIDO D,ATD_DOC_ENTR E "+
    		"WHERE D.NCODAREA="+areas+" AND D.NANO=E.NANO AND D.NORIGEN=E.NORIGEN AND D.VTIPODOC=E.VTIPODOC AND "+
    		"D.NCORRELATIVO=E.NCORRELATIVO AND E.VESTADO='ESTA003' AND E.NINDICA_SALIDA=0 ORDER BY D.NCORRELATIVO DESC",				
    		new RowCallbackHandler() {
    			public void processRow(ResultSet rs) throws SQLException {
    				EntranteBean bean3 = new EntranteBean();
    				bean3.setNcorrelativo(rs.getInt("correlativo"));
    				bean3.setVnumdoc(rs.getString("numdoc"));
    				bean3.setVasunto(rs.getString("asunto"));
    				results2.add(bean3);
    			}
    		});
    nowOut = System.currentTimeMillis();
      //logger.debug("["+usuario+"]"+"Final SALIDADAO ENTRANTE:" + nowOut);
      nowDif = nowOut - nowIn;
      //logger.debug("["+usuario+"]"+"Duracion SALIDADAO ENTRANTE:" + nowDif);
    return results2;		
    }
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<EntranteBean> entrante_buscar(int areas, long correlativo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO ENTRANTE:" + nowIn);
		final List<EntranteBean> results2 = new ArrayList<EntranteBean>();		
		jdbcTemplate.query(
				"SELECT D.NCORRELATIVO AS CORRELATIVO,E.VNUMDOC AS NUMDOC, E.VASUNTO AS ASUNTO "+
				"FROM ATD_DOC_ENTR_DIRIGIDO D,ATD_DOC_ENTR E "+
				"WHERE D.NCODAREA="+areas+" AND D.NANO=E.NANO AND D.NORIGEN=E.NORIGEN AND D.VTIPODOC=E.VTIPODOC AND "+
				"D.NCORRELATIVO=E.NCORRELATIVO AND E.VESTADO='ESTA003' AND E.NINDICA_SALIDA=0 AND D.NCORRELATIVO= "+correlativo+" ORDER BY D.NCORRELATIVO DESC",				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EntranteBean bean3 = new EntranteBean();
						bean3.setNcorrelativo(rs.getInt("correlativo"));
						bean3.setVnumdoc(rs.getString("numdoc"));
						bean3.setVasunto(rs.getString("asunto"));
						results2.add(bean3);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO ENTRANTE:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO ENTRANTE:" + nowDif);
		return results2;
		
	}
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<TiposBean> criterio(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO TIPO:" + nowIn);
		final List<TiposBean> results3 = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' AND VOBSERVACIONES='CRITERIO' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean4 = new TiposBean();
						bean4.setTipo(rs.getString("codigo"));
						bean4.setDescripcion(rs.getString("descripcion"));
						results3.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO TIPO:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO TIPO:" + nowDif);
		return results3;		
	}
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<TrabajadorBean> trabajador_remitente(int area_origen){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO TRABAJADOR_REMITENTE:" + nowIn);
		final List<TrabajadorBean> results5 = new ArrayList<TrabajadorBean>();		
		jdbcTemplate.query(
				//"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre from trabajador where " +
				//"vcodestado IN ('EIRC01','EIRC02','EIRC03') and ncodarea= "+area_origen+"order by VAPEPATERNO",
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre from trabajador where " +
				"vcodestado IN ('EIRC01','EIRC02','EIRC03') order by VAPEPATERNO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean5 = new TrabajadorBean();
						bean5.setFicha(rs.getLong("ficha"));
						bean5.setNombre_completo(rs.getString("nombre"));						
						results5.add(bean5);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO TRABAJADOR_REMITENTE:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO TRABAJADOR_REMITENTE:" + nowDif);
		return results5;
	}
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<RemitenteBean> remitentes(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(				
				// SED-FON-161
				//"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				//"FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY VDESCRIPCION",
				"SELECT NCORRELATIVO AS codigo, UPPER(VNOMBRE) AS nombre " + 
				"FROM ATD_REMITENTE_BPM  ORDER BY VNOMBRE ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO REMITENTES:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO REMITENTES:" + nowDif);
		return result1;
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
				//"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' AND VOBSERVACIONES='ORIGEN ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM ATD_TIPO WHERE NESTADO=1 AND VFLAT='ORI' ORDER BY VCODTIPO",
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
    public List<TiposBean> tipodoc(int area, String ntipodoc){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO ORIGEN:" + nowIn);
		final List<TiposBean> results9 = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"SELECT VDESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NESTADO = 1 AND NCODAREA="+area+" AND VCODTIPO='"+ntipodoc+"'",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean9 = new TiposBean();						
						bean9.setDescripcion(rs.getString("vdescripcion"));
						results9.add(bean9);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO ORIGEN:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO ORIGEN:" + nowDif);
		return results9;
		
	}
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<ServidorBean> servidor(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO SERVIDOR:" + nowIn);
		final List<ServidorBean> results9 = new ArrayList<ServidorBean>();
		jdbcTemplate.query(
				//"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=2",
				//"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=6",
				"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE VOBSERVACIONES='SERVIDORNEW' AND NVALOR=2 AND NESTADO=1 AND NVALOR2=6",
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
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
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
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<JefeBean> jefe_grupo(int area){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio SALIDADAO JEFE:" + nowIn);
		final List<JefeBean> results9 = new ArrayList<JefeBean>();
		jdbcTemplate.query(								
				//"select nficha as ficha from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= "+area+" ",
				//"select ncodarea as ncodarea, nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre from atd_jefes_equipos where ncodarea = "+area+" ",
				"select j.ncodarea as ncodarea, upper(a.VABREVIATURA)||' - ' ||upper(a.VDESCRIPCION) as nombrearea, j.nficha as ficha, UPPER(j.VAPEPATERNO ||' '||j.VAPEMATERNO ||','||j.VNOMBRES) AS nombre "+
				"from atd_jefes_equipos j, area a where j.ncodarea=a.ncodarea and a.NINDICADOR = 0 AND a.CTIPAREA IN  ('E ','G', 'D','P') AND a.NESTADO IN (0,4) and "+
				"j.ncodarea = "+area+" ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						JefeBean bean = new JefeBean();						
						bean.setFicha(rs.getInt("ficha"));	
						bean.setNombre(rs.getString("nombre"));
						bean.setNcodarea(rs.getInt("ncodarea"));
						bean.setNombrearea(rs.getString("nombrearea"));
						results9.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final SALIDADAO JEFE:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO JEFE:" + nowDif);
		return results9;	
	}	
    
    
    @Transactional(propagation=Propagation.NEVER, readOnly=true)
    public List<EstadosBean> estados(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio DOCUMENTOSALIDADAO ESTADOS:" + nowIn);
		final List<EstadosBean> results6 = new ArrayList<EstadosBean>();		
		jdbcTemplate.query(				
				//"SELECT VCODTIPO AS codigo,VDESCRIPCION AS descripcion FROM TIPO WHERE VOBSERVACIONES='ESTADO ATD' AND VESTADO='A'",
				"SELECT VCODTIPO AS codigo,VDESCRIPCION AS descripcion FROM ATD_TIPO WHERE VFLAT='CRS' AND NESTADO=1 AND VOBSERVACIONES='CRITERIO ATD SALIDA'",
				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EstadosBean bean6 = new EstadosBean();
						bean6.setCodigo(rs.getString("codigo"));
						bean6.setDescripcion(rs.getString("descripcion"));						
						results6.add(bean6);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final DOCUMENTOSALIDADAO ESTADOS:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion DOCUMENTOSALIDADAO ESTADOS:" + nowDif);
		return results6;
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
				//ELI COMENTA CODIGO ORIGINAL
				//"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
				//"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P') AND NESTADO IN (0,4) AND UPPER(A.VABREVIATURA) LIKE UPPER('"+cadenaDigitada+"%')",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre, A.NINDICADOR as indicador "+ 
				"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P') AND NESTADO IN (0,4) "+
				"AND UPPER(A.VABREVIATURA) LIKE UPPER('"+cadenaDigitada+"%') "+
				"UNION "+
				"SELECT G.NCODGRUPODESTINO as codigo, upper(G.VDESCRIPCION) as nombre, G.NINDICADOR as indicador "+ 
				"FROM ATD_GRUPO_DESTINATARIOS G WHERE G.NESTADO=1 AND G.NINDICADOR=1 "+
				"AND UPPER(G.VDESCRIPCION) LIKE UPPER('"+cadenaDigitada+"%')" ,
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						bean2.setIndicador(rs.getInt("indicador"));
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO REMITENTES:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO REMITENTES:" + nowDif);		
		return result1;
	}    
    
    public List<RemitenteBean> personaIntenaA(String cadenaDigitada){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(				
				//ELI COMENTA CODIGO ORIGINAL
				//"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
				//"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P') AND NESTADO IN (0,4) AND UPPER(A.VABREVIATURA) LIKE UPPER('"+cadenaDigitada+"%')",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre, A.NINDICADOR as indicador "+ 
				"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P') AND NESTADO IN (0,4) "+
				"AND UPPER(A.VABREVIATURA) LIKE UPPER('"+cadenaDigitada+"%') ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						bean2.setIndicador(rs.getInt("indicador"));
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO REMITENTES:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO REMITENTES:" + nowDif);		
		return result1;
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
    
    public List<GrupoBean> grupo(int codigo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio LISTA DE GRUPO" + nowIn);
		final List<GrupoBean> lista_grupo = new ArrayList<GrupoBean>();
		jdbcTemplate.query(								
				//"select nficha as ficha from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= "+area+" ",
				"select  ncoddestinatarios as codigo, vdescripcion as nombre from atd_destinatarios where nestado=1 and ncodgrupodestino= "+codigo+" ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						GrupoBean bean = new GrupoBean();						
						bean.setCodigo(rs.getInt("codigo"));
						bean.setNombre(rs.getString("nombre"));
						lista_grupo.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final LISTA DE GRUPO" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion LISTA DE GRUPO" + nowDif);
		return lista_grupo;	
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
				//SED-FON-161
				//"SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "+
				//"FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE="+codigo+"",
				"SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "+
				"FROM ATD_REPRESENTANTE_BPM WHERE VESTADO = 'A' AND NCODREMITENTE="+codigo+"",
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
    
    public List<RepresentanteBean> getRemitenteBPM(int codigo){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO REPRESENTANTE :" + nowIn);
		final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();		
		jdbcTemplate.query(
				"SELECT ncorrelativo AS codigo, UPPER(VNOMBRE) AS representante   "+
				"FROM atd_remitente_bpm WHERE ncorrelativo="+codigo+"",
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

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    public boolean updateentrada(long correlativo) {
		  System.out.println("Evento Update Doc Entrada");
		  System.out.println(correlativo);	
		  // String sql = "update atd_secuencial set ncorrelativo = " +correlativo+" where norigen=1 and nano=2011 and vcodtipo='"+tipodoc+"' and nestado=1";	
		  String sql = "update atd_doc_entr set nindica_salida = 1 where ncorrelativo= " +correlativo+"";		 
		  
		  int rs = jdbcTemplate.update(sql);
		  if (rs > 0)
		  return true;
		  return false;
	}
    	

	@Override
	public Map actualizarDocumentoSalidaSP(DocumentoSalidaBean documentosalidaBean) {
		Map output = storedactualizarDocumentoSalida.execute(documentosalidaBean);
		//String out = (String) output.get("out");
		return output;
		
	}
	
	

	@Override
	public Map nuevoSP(DocumentoSalidaBean documentosalidaBean) {
		Map output = storednuevoDocumentoSalida.execute(documentosalidaBean);
		//String out = (String) output.get("out");
		return output;
	}
	
	@Override
	public List<DocumentoSalidaBean> documento() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<EntranteBean> entradasalida() {
		// TODO Auto-generated method stub
		return null;
	}
		
	
	public List<DocumentoSalidaBean> filtrosSP(String fecha1, String fecha2, String opcion, String area, String vtipo, String areaseleccionado) {
		
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		
		Map output = storedFiltrosSalientes.execute(fecha1, fecha2, opcion, area, vtipo, areaseleccionado);
		
		
	    
		List documento = (ArrayList)output.get("filtros");
		System.out.println("Cantidad de registro del filtro");
		System.out.println(documento.size());
		return documento;
	}
	
	
	
	//date1,date2,this.getItemSeleccionado(),this.getItem3Seleccionado(),area,detallecriterio
	public List<DocumentoSalidaBean> criteriosSP(String fecha1, String fecha2, String tipos, String criterios, String area, String detallecriterio) {
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        
        
		Map output = storedCriteriosSalientes.execute(fecha1, fecha2, tipos, criterios, area,detallecriterio);
		
		nowOut = System.currentTimeMillis();	    
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion Stored ATDC_CRITERIO_SALIENTE:" + nowDif);
	    
		List documentos = (ArrayList)output.get("criterios");
		return documentos;
	}
	
	public List<DocumentoSalidaBean> BusquedaSP(String area, String opcion, String detalle) {
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        
		Map output = StoredBusquedaDocSalientes.execute(area, opcion, detalle);
		
		nowOut = System.currentTimeMillis();	    
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOCUMENTOS:" + nowDif);
	    
		List documentos = (ArrayList)output.get("busquedassalida");
		return documentos;
	}
	
	public List<DocumentoSalidaBean> BusquedaEntradaSP(String area,String opcion, String detalle) {
		long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        
		Map output = StoredBusquedaDocSalientes.execute(area,opcion,detalle);
		
		nowOut = System.currentTimeMillis();	    
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOCUMENTOS ENTRADA:" + nowDif);
	    
		List entrada = (ArrayList)output.get("busquedasentrada");
		return entrada;
	}
	
	
	
	
	
	public void setStoredNuevoDocumentoSalida(StoredNuevoDocumentoSaliente storednuevoDocumentoSalida) {
		this.storednuevoDocumentoSalida = storednuevoDocumentoSalida;
	}
    
	public void setStoredActualizarDocumentoSalida(StoredActualizarDocumentoSalida storedactualizarDocumentoSalida) {
		this.storedactualizarDocumentoSalida = storedactualizarDocumentoSalida;
	}
    
    
	
	
	public void setStoredBusqueda(StoredBusquedaDocSalientes storedBuquedaDocSalientes) {
		this.StoredBusquedaDocSalientes = storedBuquedaDocSalientes;
	}
	
	public void setStoredBusquedaDocSalientes(StoredBusquedaDocSalientes storedBusquedaDocSalientes) {
		StoredBusquedaDocSalientes = storedBusquedaDocSalientes;
	}

	public void setStoredCriteriosSalientes(StoredCriteriosSalientes storedCriteriosSalientes) {
		this.storedCriteriosSalientes = storedCriteriosSalientes;
	}

	 public void setStoredDeleteDocumentoSalida(StoredDeleteDocumentoSalida storeddeleteDocumentoSalida) {
			this.storeddeleteDocumentoSalida = storeddeleteDocumentoSalida;
		}
	 
	 public void setStoredDocumentoSalida(StoredDocumentoSaliente storeddocumentosaliente) {
			this.storeddocumentosaliente = storeddocumentosaliente;
		}
	 
	public void setStoredentratesalida(StoredEntrateSalida storedentratesalida) {
		this.storedentratesalida = storedentratesalida;
	}

	public StoredCombo getStoredCombo() {
			return storedCombo;
		}

		public void setStoredCombo(StoredCombo storedCombo) {
			this.storedCombo = storedCombo;
		}

		public void setStoredbusquedaentratesalida(
				StoredBusquedaEntrateSalida storedbusquedaentratesalida) {
			this.storedbusquedaentratesalida = storedbusquedaentratesalida;
		}
		/*
		public void setStoredremitentessp(StoredRemitentesSP storedremitentessp) {
			this.storedremitentessp = storedremitentessp;
		}
		*/

		public void setStoredlistaasuntopopub(
				StoredListaAsuntoPopup storedlistaasuntopopub) {
			this.storedlistaasuntopopub = storedlistaasuntopopub;
		}

		public StoredBusquedaAsuntos getStoredbusquedaasuntos() {
			return storedbusquedaasuntos;
		}

		public void setStoredbusquedaasuntos(StoredBusquedaAsuntos storedbusquedaasuntos) {
			this.storedbusquedaasuntos = storedbusquedaasuntos;
		}

		public void setStoreddocumentosaliente(
				StoredDocumentoSaliente storeddocumentosaliente) {
			this.storeddocumentosaliente = storeddocumentosaliente;
		}

		public void setStoredactualizarDocumentoSalida(
				StoredActualizarDocumentoSalida storedactualizarDocumentoSalida) {
			this.storedactualizarDocumentoSalida = storedactualizarDocumentoSalida;
		}

		public void setStorednuevoDocumentoSalida(
				StoredNuevoDocumentoSaliente storednuevoDocumentoSalida) {
			this.storednuevoDocumentoSalida = storednuevoDocumentoSalida;
		}

	

		public void setStoreddeleteDocumentoSalida(
				StoredDeleteDocumentoSalida storeddeleteDocumentoSalida) {
			this.storeddeleteDocumentoSalida = storeddeleteDocumentoSalida;
		}

		public void setStoredFiltrosSalientes(StoredFiltrosSalientes storedFiltrosSalientes) {
			this.storedFiltrosSalientes = storedFiltrosSalientes;
		}

		//SED-FON-161
		 public List<Seguir> personaBPM(Integer correlativo, Integer anio){
		    	long nowIn =0, nowOut=0, nowDif =0;
		    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
				Usuario usuario = null;
				usuario=(Usuario)session.getAttribute("sUsuario");
		        nowIn = System.currentTimeMillis();
		        //logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
				final List<Seguir> result1 = new ArrayList<Seguir>();
				jdbcTemplate.query(				
					    "select b.ncorrelativo, UPPER(b.vnombre) as vnombre, d.nindremitente, " +
					    " b.vtipdoc, b.vnumdoc " +
				        "from atd_doc_entr d " +
				        "inner join atd_remitente_bpm b on d.ndirigido = b.ncorrelativo "+
				        "where d.ncorrelativo=" + correlativo + " and d.nano=" + anio + 
				        " and  d.nindremitente=2"
						,
						new RowCallbackHandler() {
							public void processRow(ResultSet rs) throws SQLException {
								Seguir bean2 = new Seguir();
								bean2.setEstado("AA");
								bean2.setArea(rs.getString("ncorrelativo"));
								bean2.setCodArea(rs.getString("vnombre"));
								bean2.setNombreTrabajador(rs.getString("vnombre"));
								bean2.setnIndRemitente(rs.getInt("nindremitente"));
								bean2.setVnumdoc(rs.getString("vnumdoc"));
								bean2.setVtipdoc(rs.getString("vtipdoc"));
								bean2.setCodArea("");							
								result1.add(bean2);
							}
						});
				nowOut = System.currentTimeMillis();
			    nowDif = nowOut - nowIn;
				return result1;
			}    
		
		//SED-FON-161
		 @Transactional(propagation=Propagation.NEVER, readOnly=true)
		    public RemitenteBean getRemitenteByNombre(String nombre){
		    	long nowIn =0, nowOut=0, nowDif =0;
		    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
				Usuario usuario = null;
				usuario=(Usuario)session.getAttribute("sUsuario");
		        nowIn = System.currentTimeMillis();
				final RemitenteBean result1 = new RemitenteBean();
				jdbcTemplate.query(				
						"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre  " + 
						"FROM ATD_REMITENTE  " +
						" WHERE VDESCRIPCION='" + nombre + "'",
						new RowCallbackHandler() {
							public void processRow(ResultSet rs) throws SQLException {
								result1.setCodigo(rs.getInt("codigo"));
								result1.setDescripcion(rs.getString("nombre"));								
							}
						});
				nowOut = System.currentTimeMillis();
			    nowDif = nowOut - nowIn;
				return result1;
			}  
		//SED-FON-161
		 public List<RepresentanteBean> representanteInterno(int codigo){
		    	long nowIn =0, nowOut=0, nowDif =0;
		    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
				Usuario usuario = null;
				usuario=(Usuario)session.getAttribute("sUsuario");
		        nowIn = System.currentTimeMillis();
		        //logger.debug("Inicio ENTRANTEDAO REPRESENTANTE :" + nowIn);
				final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();		
				jdbcTemplate.query(
						"SELECT NCODREMITENTE AS codigo, UPPER(VNOMBRE) AS representante "+
						"FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE="+codigo+"",
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
	
		 //SED-REQ-00001
		 
		 
		  public RemitenteBPM consultaDocumentoBPM(Long correlativo) {
				Map output = storedConsultaDocumentoBPM.execute(correlativo);    
				List<RemitenteBPM> remitentes = new ArrayList<RemitenteBPM>();
					remitentes=	(ArrayList)output.get("CUR_DATOS");
				if (remitentes.size()>0) {
					return remitentes.get(0);
				}
				return null;
			}


		public StoredConsultaDocumentoBPM getStoredConsultaDocumentoBPM() {
			return storedConsultaDocumentoBPM;
		}


		public void setStoredConsultaDocumentoBPM(StoredConsultaDocumentoBPM storedConsultaDocumentoBPM) {
			this.storedConsultaDocumentoBPM = storedConsultaDocumentoBPM;
		}
}
