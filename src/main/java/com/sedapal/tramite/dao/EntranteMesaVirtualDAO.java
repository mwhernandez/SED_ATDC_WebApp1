package com.sedapal.tramite.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
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
import com.sedapal.tramite.beans.EntranteMesaVirtualBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.ParametroCorreoMesaVirtualBean;
import com.sedapal.tramite.beans.PlantillaCorreoMesaVirtualBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;


public class EntranteMesaVirtualDAO implements IEntranteMesaVirtualDAO{

	private JdbcTemplate jdbcTemplate;
    private StoredEntrantesMesaVirtual storedEntrantesmesavirtual;   
    private StoredActualizarEntMesaVirtual storedactualizarentmesavirtual;    
    private StoredFiltrosEntMesaVirtual storedfiltrosentmesavirtual;       
    private StoredBusquedaEntrantesMesaVirtual storedbusquedaentrantemesavirtual;
    private StoredEliminarEntMesaVirtual storedeliminarentmesavirtual;
    private StoredCombo storedCombo;
    private StoredRemitentesSP storedremitentessp;
    private StoredConsultaEntrantesMesaVirtual storedconsultaentrantesmesavirtual;
    private StoredAtencionEntrantesMesa storedatencionentrantesMesa;
   
    
    public List<EntranteMesaVirtualBean> buscarSP(String fecha1, String fecha2, String area) {		
		Map output = storedconsultaentrantesmesavirtual.execute(fecha1, fecha2, area);
		List entrantesmesavirtual = (ArrayList)output.get("busquedas_mesa");
		return entrantesmesavirtual;
	}
    
    public List<EntranteMesaVirtualBean> buscarAtencionMesaSP(String fecha1, String fecha2, String area) {		
		Map output = storedatencionentrantesMesa.execute(fecha1, fecha2, area);
		List entrantesmesavirtual = (ArrayList)output.get("busquedas_mesa");
		return entrantesmesavirtual;
	}
	
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
	public List<EntranteMesaVirtualBean> entrantesSP(String ncodarea) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		ncodarea=String.valueOf(usuario.getCodarea());
		Map output = storedEntrantesmesavirtual.execute(ncodarea);    	
		List entrantesmesavirtual = (ArrayList)output.get("entrantesmesavirtual");
		return entrantesmesavirtual;
	}
	
	public List<PlantillaCorreoMesaVirtualBean> plantillaCorreo(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO SERVIDOR:" + nowIn);
		final List<PlantillaCorreoMesaVirtualBean> results3 = new ArrayList<PlantillaCorreoMesaVirtualBean>();
		jdbcTemplate.query(
				//"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=1",
				"SELECT NVALORCLOB AS VPLANTILLA FROM ATD_PARAMETROS  WHERE NVALOR=0 AND NVALOR2=0 AND NESTADO=1",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						PlantillaCorreoMesaVirtualBean bean4 = new PlantillaCorreoMesaVirtualBean();	
						
						 Clob clob = rs.getClob("VPLANTILLA");
				         Reader r = clob.getCharacterStream();
				         StringBuffer buffer = new StringBuffer();
				         int ch;
				         try {
							while ((ch = r.read()) !=-1) {
							    buffer.append(""+(char)ch);
							 }
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						bean4.setNvalorclob(buffer.toString());
						System.out.println(bean4.getNvalorclob());
						results3.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO SERVIDOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO SERVIDOR:" + nowDif);
		return results3;	
	}
	
	
	public List<ParametroCorreoMesaVirtualBean> parametroCorreo(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO SERVIDOR:" + nowIn);
		final List<ParametroCorreoMesaVirtualBean> results3 = new ArrayList<ParametroCorreoMesaVirtualBean>();
		jdbcTemplate.query(
				//"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=1",
				"SELECT VOBSERVACIONES AS VHOST, VALOR1 AS VCORREO, VALOR2 AS VPASSWORD FROM ATD_PARAMETROS WHERE  NVALOR=0 AND NVALOR2=1 AND NESTADO=1 ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ParametroCorreoMesaVirtualBean bean4 = new ParametroCorreoMesaVirtualBean();						
						bean4.setVhost(rs.getString("VHOST"));
						bean4.setVcorreo(rs.getString("VCORREO"));
						bean4.setVpassword(rs.getString("VPASSWORD"));
						results3.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO SERVIDOR:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO SERVIDOR:" + nowDif);
		return results3;	
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
	
	public List<AreaBean> mesa(){
    	
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(
				
				"SELECT a.ncodarea as codigo, upper(a.vdescripcion) as nombre from AREA a where a.nindicador=0 AND a.ncodarea IN (100,538,539,540,541,542,543,544,545) order by a.ncodarea",
				
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
    
    public List<ServidorBean> servidororigen(){    		
		
		final List<ServidorBean> results = new ArrayList<ServidorBean>();
		jdbcTemplate.query(
				"SELECT vdescripcion as descripcion  FROM atd_tipo WHERE vflat='SER' AND NESTADO=1 AND NINDICADOR=1 ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ServidorBean bean2 = new ServidorBean();
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});
		
		return results;		
	}
    
    public List<ServidorBean> servidordestino(){    		
		
		final List<ServidorBean> results = new ArrayList<ServidorBean>();
		jdbcTemplate.query(
				"SELECT vdescripcion as descripcion  FROM atd_tipo WHERE vflat='SER' AND NESTADO=1 AND NINDICADOR=2 ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ServidorBean bean2 = new ServidorBean();
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
    
    public List<TiposBean> tipo_motivo() {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"select vcodtipo as codigo, vobservaciones as descripcion from atd_tipo where vflat='MOT' and nestado=1 and nindicador=1 order by vcodtipo",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setCodigo(rs.getString("codigo")); 
						bean.setDescripcion(rs.getString("descripcion"));
                        results.add(bean);
					}
				});
		return results;
	}
    
    public List<TiposBean> tipo_motivo_final() {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"select vcodtipo as codigo, vobservaciones as descripcion from atd_tipo where vflat='MOT' and vcodtipo <> 'MOT001' and nestado=1 and nindicador=1 order by vcodtipo",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setCodigo(rs.getString("codigo")); 
						bean.setDescripcion(rs.getString("descripcion"));
                        results.add(bean);
					}
				});
		return results;
	}
    
    public List<TiposBean> tipo_motivo_uno() {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"select vcodtipo as codigo, vobservaciones as descripcion from atd_tipo where vflat='MOT' and nestado=1 and nindicador=1 order by vcodtipo",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setCodigo(rs.getString("codigo")); 
						bean.setDescripcion(rs.getString("descripcion"));
                        results.add(bean);
					}
				});
		return results;
	}
    
    public List<TiposBean> motivo(String motivo) {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				//"select vcodtipo as codigo, vobservaciones as descripcion from atd_tipo where vflat='TMT' and nestado=1 and nindicador=1 order by vcodtipo",
				"select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo where vflat='TMT' and vobservaciones='SELECCIONAR' and nestado=1 and nindicador=1"
				+ " union select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo where vflat='TMT' and vobservaciones='"+motivo+"' and nestado=1 and nindicador=1",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setCodigo(rs.getString("codigo")); 
						bean.setDescripcion(rs.getString("descripcion"));
						results.add(bean);
					}
				});
		return results;
	}
    
    
    
    public List<TiposBean> motivo_uno() {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo where vflat='TMT' and nestado=1 and nindicador=1 order by vcodtipo",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setCodigo(rs.getString("codigo")); 
						bean.setDescripcion(rs.getString("descripcion"));
                        results.add(bean);
					}
				});
		return results;
	}
    
    
    public List<TiposBean> motivo_dos() {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo where vflat='TMT' and nestado=1 and nindicador=1 order by vcodtipo",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setCodigo(rs.getString("codigo")); 
						bean.setDescripcion(rs.getString("descripcion"));
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
    	
	
	public List<EntranteMesaVirtualBean> BusquedaSP(String area, String opcion, String detalle) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedbusquedaentrantemesavirtual.execute(area, opcion, detalle);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		//logger.debug("["+usuario+"]"+"Stored ATDC_BUSQUEDA_DOC_ENT_MESA Duracion:" + nowDif);		
		
		List busqueda_mesa = (ArrayList)output.get("busquedas_mesa");
		return busqueda_mesa;
	}
	
	@Override
	public void eliminarSPEntVirtual(String anno, String origen, String codigo, String login, String tipoingreso, String motivoingreso) {
		// TODO Auto-generated method stub
		Map output = storedeliminarentmesavirtual.execute(anno, origen, codigo, login, tipoingreso, motivoingreso);
		
	}
		
	
	
	

	
	public List<EntranteMesaVirtualBean> filtrosSP(String fecha1, String fecha2, String area) {		
		Map output = storedfiltrosentmesavirtual.execute(fecha1, fecha2, area);
		List documentos = (ArrayList)output.get("filtros_mesa");
		return documentos;
	}
	
	
	
	
	@Override
	public Map actualizarSPEntMesaVitual(EntranteMesaVirtualBean entrantemesavirtualBean) {
		Map output = storedactualizarentmesavirtual.execute(entrantemesavirtualBean);	
		return output;
	}

	
	
	public StoredCombo getStoredCombo() {
		return storedCombo;
	}

	public void setStoredCombo(StoredCombo storedCombo) {
		this.storedCombo = storedCombo;
	}

	public void setStoredEntrantesmesavirtual(StoredEntrantesMesaVirtual storedEntrantesmesavirtual) {
		this.storedEntrantesmesavirtual = storedEntrantesmesavirtual;
	}

	public void setStoredactualizarentmesavirtual(StoredActualizarEntMesaVirtual storedactualizarentmesavirtual) {
		this.storedactualizarentmesavirtual = storedactualizarentmesavirtual;
	}

	public void setStoredfiltrosentmesavirtual(StoredFiltrosEntMesaVirtual storedfiltrosentmesavirtual) {
		this.storedfiltrosentmesavirtual = storedfiltrosentmesavirtual;
	}

	public void setStoredbusquedaentrantemesavirtual(StoredBusquedaEntrantesMesaVirtual storedbusquedaentrantemesavirtual) {
		this.storedbusquedaentrantemesavirtual = storedbusquedaentrantemesavirtual;
	}

	public void setStoredeliminarentmesavirtual(StoredEliminarEntMesaVirtual storedeliminarentmesavirtual) {
		this.storedeliminarentmesavirtual = storedeliminarentmesavirtual;
	}

	@Override
	public List<EntranteMesaVirtualBean> entrantesmesavirtual() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStoredconsultaentrantesmesavirtual(StoredConsultaEntrantesMesaVirtual storedconsultaentrantesmesavirtual) {
		this.storedconsultaentrantesmesavirtual = storedconsultaentrantesmesavirtual;
	}

	public void setStoredatencionentrantesMesa(StoredAtencionEntrantesMesa storedatencionentrantesMesa) {
		this.storedatencionentrantesMesa = storedatencionentrantesMesa;
	}

	

	
	
	
	
	
	
}
